package com.example.fitshop.converter;

import com.example.fitshop.dto.ProductImageDTO;
import com.example.fitshop.model.ProductImage;
import org.springframework.stereotype.Component;

@Component
public class ProductImageToProductImageDTO {
    public ProductImageDTO convert(ProductImage productImage){
        return new ProductImageDTO(productImage.getUrl());
    }
}
