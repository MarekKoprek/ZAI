package com.example.fitshop.dto;

import com.example.fitshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private int quantity;
    private ProductDTO productDTO;
}
