package com.example.fitshop.service;

import com.example.fitshop.converter.AddressDTOtoAddress;
import com.example.fitshop.converter.CartToCartDTO;
import com.example.fitshop.dto.CartDTO;
import com.example.fitshop.dto.CartTotalDTO;
import com.example.fitshop.dto.PurchaseDTO;
import com.example.fitshop.model.*;
import com.example.fitshop.repository.AddressRepo;
import com.example.fitshop.repository.CartRepo;
import com.example.fitshop.repository.ClientOrderRepo;
import com.example.fitshop.repository.ShipperRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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

    public Cart getCartById(Long id){
        Cart cart = cartRepo.findFirstById(id);
        if(cart == null){
            throw new EntityNotFoundException("Brak koszyka o takim id");
        }
        return cart;
    }

    public List<Cart> getCartsByUser(AppUser appUser){
        return cartRepo.findAllByUser(appUser);
    }

    public CartDTO addProduct(Long productId, AppUser appUser){
        Product product = productService.getProductById(productId);
        return cartToCartDTO.convert(cartRepo.save(new Cart(null, 1, appUser, product)));
    }

    public void removeCart(Long id, AppUser appUser){
        Cart cart = getCartById(id);
        if(cart.getUser() != appUser){
            throw new AccessDeniedException("Niepoprawny użytkownik");
        }
        cartRepo.delete(cart);
    }

    public List<CartDTO> getCartProducts(AppUser appUser){
        return getCartsByUser(appUser).stream()
                .map(cartToCartDTO::convert)
                .collect(Collectors.toList());
    }

    public CartDTO addCartQuantity(Long id, AppUser appUser){
        Cart cart = getCartById(id);
        if(cart.getUser() != appUser){
            throw new AccessDeniedException("Niepoprawny użytkownik");
        }
        int quantity = Math.min(cart.getProduct().getQuantity(), cart.getQuantity() + 1);
        cart.setQuantity(quantity);
        return cartToCartDTO.convert(cart);
    }

    public CartDTO lowerCartQuantity(Long id, AppUser appUser){
        Cart cart = getCartById(id);
        if(cart.getUser() != appUser){
            throw new AccessDeniedException("Niepoprawny użytkownik");
        }
        int quantity = Math.max(0, cart.getQuantity() - 1);
        cart.setQuantity(quantity);
        return cartToCartDTO.convert(cart);
    }

    public CartTotalDTO getTotalCartValue(Long userId, AppUser appUser){
        if(!appUser.getId().equals(userId)){
            throw new AccessDeniedException("Niepoprawny użytkownik");
        }
        List<Cart> carts = getCartsByUser(appUser);
        return new CartTotalDTO(carts.stream().mapToDouble(cart -> cart.getProduct().getPrice()).sum());
    }

    @Transactional
    public ClientOrder purchaseCart(Long userId, AppUser appUser, PurchaseDTO purchaseDTO){
        if(!appUser.getId().equals(userId)){
            throw new AccessDeniedException("Niepoprawny użytkownik");
        }
        ClientOrder clientOrder = new ClientOrder();
        purchaseDTO.getCarts().forEach(cart -> {
            clientOrder.getProducts().add(productService.getProductById(cart.getProductDTO().getId()));
        });
        clientOrder.setDate(LocalDate.now());
        clientOrder.setPaymentType(purchaseDTO.getPaymentType());
        clientOrder.setAmount(purchaseDTO.getAmount());
        clientOrder.setUser(appUser);
        if(purchaseDTO.getAddress().getCountry().isEmpty()){
            clientOrder.setAddress(appUser.getAddress());
        }
        else{
            clientOrder.setAddress(addressRepo.save(addressDTOtoAddress.convert(purchaseDTO.getAddress())));
        }
        clientOrder.setShipper(shipperRepo.findByName(purchaseDTO.getShipper().getName()));
        return clientOrderRepo.save(clientOrder);
    }
}
