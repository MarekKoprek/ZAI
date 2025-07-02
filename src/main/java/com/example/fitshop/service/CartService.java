package com.example.fitshop.service;

import com.example.fitshop.converter.AddressDTOtoAddress;
import com.example.fitshop.converter.CartToCartDTO;
import com.example.fitshop.dto.CartDTO;
import com.example.fitshop.dto.CartTotalDTO;
import com.example.fitshop.dto.PurchaseDTO;
import com.example.fitshop.model.*;
import com.example.fitshop.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartToCartDTO cartToCartDTO;
    private final AddressDTOtoAddress addressDTOtoAddress;

    private final CartRepo cartRepo;
    private final ClientOrderRepo clientOrderRepo;
    private final ProductService productService;
    private final AddressRepo addressRepo;
    private final ShipperRepo shipperRepo;

    private final AuthService authService;
    private final ProductRepo productRepo;

    public Cart getCartById(Long id){
        Cart cart = cartRepo.findFirstById(id);
        if(cart == null){
            throw new EntityNotFoundException("Brak koszyka o takim id");
        }
        return cart;
    }

    public List<Cart> getCartsByUser(){
        AppUser user = authService.getAuthenticatedUser();
        return cartRepo.findAllByUser(user);
    }

    public CartDTO addProduct(Long productId){
        AppUser user = authService.getAuthenticatedUser();
        Product product = productService.getProductById(productId);
        Optional<Cart> cart = cartRepo.findCartByProductAndUser(product, user);
        if(cart.isPresent()){
            throw new IllegalArgumentException("Produkt już w koszyku");
        }
        return cartToCartDTO.convert(cartRepo.save(new Cart(null, 1, user, product)));
    }

    public void removeCart(Long id){
        Cart cart = getCartById(id);
        AppUser user = authService.getAuthenticatedUser();
        if(cart.getUser() != user){
            throw new AccessDeniedException("Niepoprawny użytkownik");
        }
        cartRepo.delete(cart);
    }

    public List<CartDTO> getCartProducts(){
        return getCartsByUser().stream()
                .map(cartToCartDTO::convert)
                .collect(Collectors.toList());
    }

    public CartDTO addCartQuantity(Long id){
        Cart cart = getCartById(id);
        AppUser user = authService.getAuthenticatedUser();
        if(cart.getUser() != user){
            throw new AccessDeniedException("Niepoprawny użytkownik");
        }
        int quantity = Math.min(cart.getProduct().getQuantity(), cart.getQuantity() + 1);
        cart.setQuantity(quantity);
        return cartToCartDTO.convert(cartRepo.save(cart));
    }

    public CartDTO lowerCartQuantity(Long id){
        Cart cart = getCartById(id);
        AppUser user = authService.getAuthenticatedUser();
        if(cart.getUser() != user){
            throw new AccessDeniedException("Niepoprawny użytkownik");
        }
        int quantity = Math.max(1, cart.getQuantity() - 1);
        cart.setQuantity(quantity);
        return cartToCartDTO.convert(cartRepo.save(cart));
    }

    public CartTotalDTO getTotalCartValue(){
        List<Cart> carts = getCartsByUser();
        return new CartTotalDTO(carts.stream().mapToDouble(cart -> cart.getProduct().getPrice() * cart.getQuantity()).sum());
    }

    @Transactional
    public ClientOrder purchaseCart(PurchaseDTO purchaseDTO){
        AppUser user = authService.getAuthenticatedUser();
        ClientOrder clientOrder = new ClientOrder();
        List<Cart> carts = getCartsByUser();
        clientOrder.setDate(LocalDate.now());
        clientOrder.setPaymentType(purchaseDTO.getPaymentType().equals("card") ? PaymentType.CARD : PaymentType.BLIK);
        clientOrder.setAmount(purchaseDTO.getAmount());
        clientOrder.setUser(user);
        clientOrder.setAddress(addressRepo.save(addressDTOtoAddress.convert(purchaseDTO.getAddress())));
        clientOrder.setShipper(shipperRepo.findByName(purchaseDTO.getShipperName()));
        ClientOrder order = clientOrderRepo.save(clientOrder);

        carts.forEach(cart -> {
            Product product = cart.getProduct();
            order.getProducts().add(product);
            product.setQuantity(product.getQuantity() - cart.getQuantity());
            productRepo.save(product);
        });

        cartRepo.deleteAllByUser(user);

        return order;
    }
}
