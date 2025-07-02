package com.example.fitshop.controller;

import com.example.fitshop.dto.CategorySubCategoryDTO;
import com.example.fitshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get/category/{id}")
    public ResponseEntity<CategorySubCategoryDTO> getSubCategories(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(categoryService.createCategorySubCategoryDTO(id));
    }

    @GetMapping("/get/categories")
    public ResponseEntity<List<CategorySubCategoryDTO>> getCategories(){
        return ResponseEntity.ok().body(categoryService.getCategories());
    }
}
