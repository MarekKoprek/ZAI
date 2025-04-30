package com.example.fitshop.repository;

import com.example.fitshop.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {

    List<SubCategory> findAllByCategoryId(Long categoryId);
}
