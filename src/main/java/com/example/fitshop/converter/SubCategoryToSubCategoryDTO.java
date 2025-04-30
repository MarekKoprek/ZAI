package com.example.fitshop.converter;

import com.example.fitshop.dto.SubCategoryDTO;
import com.example.fitshop.model.SubCategory;
import org.springframework.stereotype.Component;

@Component
public class SubCategoryToSubCategoryDTO {
    public SubCategoryDTO convert(SubCategory subCategory){
        return new SubCategoryDTO(subCategory.getId(), subCategory.getName());
    }
}
