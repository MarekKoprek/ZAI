package com.example.fitshop.converter;

import com.example.fitshop.dto.CategoryDTO;
import com.example.fitshop.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryDTO {
    public CategoryDTO convert(Category category){
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription(), category.getImage());
    }
}
