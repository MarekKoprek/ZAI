package com.example.fitshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
}
