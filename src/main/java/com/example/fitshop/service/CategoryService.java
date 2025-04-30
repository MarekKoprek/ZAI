package com.example.fitshop.service;

import com.example.fitshop.converter.CategoryToCategoryDTO;
import com.example.fitshop.converter.SubCategoryToSubCategoryDTO;
import com.example.fitshop.dto.CategorySubCategoryDTO;
import com.example.fitshop.model.Category;
import com.example.fitshop.model.SubCategory;
import com.example.fitshop.repository.CategoryRepo;
import com.example.fitshop.repository.SubCategoryRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final SubCategoryToSubCategoryDTO subCategoryToSubCategoryDTO;
    private final CategoryToCategoryDTO categoryToCategoryDTO;

    private final CategoryRepo categoryRepo;
    private final SubCategoryRepo subCategoryRepo;

    public Category getCategoryById(Long id) {
        Category category = categoryRepo.findFirstById(id);
        if(category == null){
            throw new EntityNotFoundException("Brak kategorii o takim id");
        }
        return category;
    }

    public List<SubCategory> getSubCategories(Long categoryId){
        return subCategoryRepo.findAllByCategoryId(categoryId);
    }

    public CategorySubCategoryDTO createCategorySubCategoryDTO(Long id){
        return new CategorySubCategoryDTO(
                categoryToCategoryDTO.convert(getCategoryById(id)),
                getSubCategories(id).stream()
                        .map(subCategoryToSubCategoryDTO::convert)
                        .collect(Collectors.toList()));
    }

    public List<CategorySubCategoryDTO> getCategories(){
        return categoryRepo.findAll().stream()
                .map(category -> createCategorySubCategoryDTO(category.getId()))
                .collect(Collectors.toList());
    }
}
