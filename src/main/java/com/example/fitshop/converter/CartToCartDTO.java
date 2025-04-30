package com.example.fitshop.converter;

import com.example.fitshop.dto.CartDTO;
import com.example.fitshop.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartToCartDTO {

    private final ProductToProductDTO productToProductDTO;

    public CartDTO convert(Cart cart){
        return new CartDTO(cart.getId(), cart.getQuantity(), productToProductDTO.convert(cart.getProduct()));
    }
}
