package com.example.fitshop.controller;

import com.example.fitshop.dto.CartDTO;
import com.example.fitshop.model.Cart;
import com.example.fitshop.service.CartService;
import com.example.fitshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @GetMapping("/get/cart")
    public ResponseEntity<List<CartDTO>> getCartProducts(){
        return ResponseEntity.ok().body(cartService.getCartProducts());
    }

    @PostMapping("/add/cart/product/{id}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(cartService.addProduct(id));
    }

    @PostMapping("/remove/cart/{id}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable("id") Long id){
        cartService.removeCart(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/cart/quantity/{id}")
    public ResponseEntity<CartDTO> addCartQuantity(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(cartService.addCartQuantity(id));
    }

    @PostMapping("/lower/cart/quantity/{id}")
    public ResponseEntity<CartDTO> lowerCartQuantity(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(cartService.lowerCartQuantity(id));
    }

}
