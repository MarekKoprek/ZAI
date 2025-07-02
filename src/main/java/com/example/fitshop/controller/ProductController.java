package com.example.fitshop.controller;

import com.example.fitshop.converter.ProductToProductDTO;
import com.example.fitshop.dto.OpinionDTO;
import com.example.fitshop.dto.ProductDTO;
import com.example.fitshop.dto.ProductFilterDTO;
import com.example.fitshop.model.AppUser;
import com.example.fitshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductToProductDTO productToProductDTO;

    private final ProductService productService;

    @GetMapping("/get/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(productToProductDTO.convert(productService.getProductById(id)));
    }

    @GetMapping("/get/products")
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam(required = false) Double minPrice,
                                                        @RequestParam(required = false) Double maxPrice,
                                                        @RequestParam(required = false) Double minRating){
        return ResponseEntity.ok().body(productService.getProducts(minPrice, maxPrice, minRating));
    }

    @GetMapping("/get/products/sub/category/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsBySubCategoryId(@PathVariable("id") Long id,
                                                                       @RequestParam(required = false) Double minPrice,
                                                                       @RequestParam(required = false) Double maxPrice,
                                                                       @RequestParam(required = false) Double minRating){
        return ResponseEntity.ok().body(productService.getProductsBySubCategoryId(id, minPrice, maxPrice, minRating));
    }

    @GetMapping("/get/products/category/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable("id") Long id,
                                                                    @RequestBody ProductFilterDTO productFilterDTO){
        return ResponseEntity.ok().body(productService.getProductsByCategoryId(id, productFilterDTO));
    }

    @PostMapping("/add/product/opinion/{productId}")
    public ResponseEntity<Void> addProductOpinion(@PathVariable("productId") Long productId,
                                                  @RequestBody OpinionDTO opinionDTO){
        productService.addProductOpinion(productId, opinionDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/products/paged")
    public ResponseEntity<Page<ProductDTO>> getPagedProducts(@RequestParam("page") int page,
                                                             @RequestParam("size") int size) {
        return ResponseEntity.ok().body(productService.getPagedProducts(PageRequest.of(page, size)));
    }
}
