package com.example.fitshop.controller;

import com.example.fitshop.dto.CartDTO;
import com.example.fitshop.dto.CartTotalDTO;
import com.example.fitshop.dto.PurchaseDTO;
import com.example.fitshop.model.AppUser;
import com.example.fitshop.model.ClientOrder;
import com.example.fitshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
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

    @GetMapping("/cart/total/value")
    public ResponseEntity<CartTotalDTO> getTotalCartValue(){
        return ResponseEntity.ok().body(cartService.getTotalCartValue());
    }

    @PostMapping("/cart/purchase")
    public ResponseEntity<Void> purchaseCart(@RequestBody PurchaseDTO purchaseDTO){
        cartService.purchaseCart(purchaseDTO);
        return ResponseEntity.ok().build();
    }
}
