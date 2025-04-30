package com.example.fitshop.service;

import com.example.fitshop.converter.CartToCartDTO;
import com.example.fitshop.dto.CartDTO;
import com.example.fitshop.model.Cart;
import com.example.fitshop.model.Product;
import com.example.fitshop.repository.CartRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public CartDTO addProduct(Long productId){
        Product product = productService.getProductById(productId);
        return cartToCartDTO.convert(cartRepo.save(new Cart(null, 1, null, product)));
    }

    public void removeCart(Long id){
        Cart cart = getCartById(id);
        cartRepo.delete(cart);
    }

    public List<CartDTO> getCartProducts(){
        return cartRepo.findAll().stream()
                .map(cartToCartDTO::convert)
                .collect(Collectors.toList());
    }

    public CartDTO addCartQuantity(Long id){
        Cart cart = getCartById(id);
        int quantity = Math.min(cart.getProduct().getQuantity(), cart.getQuantity() + 1);
        cart.setQuantity(quantity);
        return cartToCartDTO.convert(cart);
    }

    public CartDTO lowerCartQuantity(Long id){
        Cart cart = getCartById(id);
        int quantity = Math.max(0, cart.getQuantity() - 1);
        cart.setQuantity(quantity);
        return cartToCartDTO.convert(cart);
    }

}
