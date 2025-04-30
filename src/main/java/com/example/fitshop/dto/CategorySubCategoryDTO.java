package com.example.fitshop.dto;

import com.example.fitshop.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CategorySubCategoryDTO {
    private CategoryDTO category;
    private List<SubCategoryDTO> subCategories;
}
