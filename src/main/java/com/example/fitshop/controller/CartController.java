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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @GetMapping("/get/cart")
    public ResponseEntity<List<CartDTO>> getCartProducts(@AuthenticationPrincipal AppUser appUser){
        return ResponseEntity.ok().body(cartService.getCartProducts(appUser));
    }

    @PostMapping("/add/cart/product/{id}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable("id") Long id,
                                                    @AuthenticationPrincipal AppUser appUser){
        return ResponseEntity.ok().body(cartService.addProduct(id, appUser));
    }

    @PostMapping("/remove/cart/{id}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable("id") Long id,
                                                      @AuthenticationPrincipal AppUser appUser){
        cartService.removeCart(id, appUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/cart/quantity/{id}")
    public ResponseEntity<CartDTO> addCartQuantity(@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal AppUser appUser){
        return ResponseEntity.ok().body(cartService.addCartQuantity(id, appUser));
    }

    @PostMapping("/lower/cart/quantity/{id}")
    public ResponseEntity<CartDTO> lowerCartQuantity(@PathVariable("id") Long id,
                                                     @AuthenticationPrincipal AppUser appUser){
        return ResponseEntity.ok().body(cartService.lowerCartQuantity(id, appUser));
    }

    @GetMapping("/cart/total/value/{userId}")
    public ResponseEntity<CartTotalDTO> getTotalCartValue(@PathVariable("userId") Long id,
                                                          @AuthenticationPrincipal AppUser appUser){
        return ResponseEntity.ok().body(cartService.getTotalCartValue(id, appUser));
    }

    @PostMapping("/cart/purchase/{userId}")
    public ResponseEntity<ClientOrder> purchaseCart(@PathVariable("userId") Long id,
                                                    @RequestBody PurchaseDTO purchaseDTO,
                                                    @AuthenticationPrincipal AppUser appUser){
        return ResponseEntity.ok().body(cartService.purchaseCart(id, appUser, purchaseDTO));
    }
}
