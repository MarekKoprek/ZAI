package com.example.fitshop.controller;

import com.example.fitshop.dto.OrderDTO;
import com.example.fitshop.dto.ProductDTO;
import com.example.fitshop.dto.UserDto;
import com.example.fitshop.model.AppUser;
import com.example.fitshop.model.ClientOrder;
import com.example.fitshop.repository.AppUserRepo;
import com.example.fitshop.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/product/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok().body(adminService.addProduct(productDTO));
    }

    @PostMapping("/product/upload/images")
    public ResponseEntity<ProductDTO> uploadImages(@RequestParam("files") MultipartFile[] files,
                                                   @RequestParam("productId") Long productId) {
        return ResponseEntity.ok().body(adminService.uploadImages(files, productId));
    }

    @GetMapping("/get/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok().body(adminService.getUsers());
    }

    @GetMapping("/get/orders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return ResponseEntity.ok().body(adminService.getOrders());
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        adminService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
