package com.example.fitshop.controller;

import com.example.fitshop.dto.CategorySubCategoryDTO;
import com.example.fitshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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
