package com.example.fitshop.controller;

import com.example.fitshop.converter.ProductToProductDTO;
import com.example.fitshop.dto.OpinionDTO;
import com.example.fitshop.dto.ProductDTO;
import com.example.fitshop.dto.ProductFilterDTO;
import com.example.fitshop.model.AppUser;
import com.example.fitshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductToProductDTO productToProductDTO;

    private final ProductService productService;

    @GetMapping("/get/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(productToProductDTO.convert(productService.getProductById(id)));
    }

    @GetMapping("/get/products")
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestBody ProductFilterDTO productFilterDTO){
        return ResponseEntity.ok().body(productService.getProducts(productFilterDTO));
    }

    @GetMapping("/get/products/sub/category/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsBySubCategoryId(@PathVariable("id") Long id,
                                                                       @RequestBody ProductFilterDTO productFilterDTO){
        return ResponseEntity.ok().body(productService.getProductsBySubCategoryId(id, productFilterDTO));
    }

    @GetMapping("/get/products/category/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable("id") Long id,
                                                                    @RequestBody ProductFilterDTO productFilterDTO){
        return ResponseEntity.ok().body(productService.getProductsByCategoryId(id, productFilterDTO));
    }

    @PostMapping("/add/product/opinion/{productId}")
    public ResponseEntity<Void> addProductOpinion(@PathVariable("productId") Long productId,
                                                  @RequestBody OpinionDTO opinionDTO,
                                                  @AuthenticationPrincipal AppUser appUser){
        productService.addProductOpinion(productId, opinionDTO, appUser);
        return ResponseEntity.ok().build();
    }
}
