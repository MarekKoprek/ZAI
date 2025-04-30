package com.example.fitshop.repository;

import com.example.fitshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findFirstById(Long id);

    @Query("SELECT p FROM Product p WHERE p.subCategory.id = :subCategoryId")
    List<Product> findAllBySubCategoryId(@Param("subCategoryId") Long subCategoryId);

    @Query("SELECT p FROM Product p WHERE p.subCategory.category.id = :categoryId")
    List<Product> findAllBySubCategoryCategoryId(@Param("categoryId") Long categoryId);
}
