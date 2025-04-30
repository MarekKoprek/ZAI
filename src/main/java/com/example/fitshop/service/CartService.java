package com.example.fitshop.service;

import com.example.fitshop.converter.CartToCartDTO;
import com.example.fitshop.dto.CartDTO;
import com.example.fitshop.model.AppUser;
import com.example.fitshop.model.Cart;
import com.example.fitshop.model.Product;
import com.example.fitshop.repository.CartRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartToCartDTO cartToCartDTO;

    private final CartRepo cartRepo;
    private final ProductService productService;

    public Cart getCartById(Long id){
        Cart cart = cartRepo.findFirstById(id);
        if(cart == null){
            throw new EntityNotFoundException("Brak koszyka o takim id");
        }
        return cart;
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
        return cartRepo.findAllByUser(appUser).stream()
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

}
