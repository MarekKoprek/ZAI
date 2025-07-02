package com.example.fitshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private List<ProductImageDTO> images;
    private double rating;
    private List<ShipperDTO> shippers;
    private SubCategoryDTO subCategory;
}
