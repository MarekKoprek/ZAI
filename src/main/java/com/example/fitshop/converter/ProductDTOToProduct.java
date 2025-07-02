package com.example.fitshop.converter;

import com.example.fitshop.dto.ProductDTO;
import com.example.fitshop.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOToProduct {
    public Product convert(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return product;
    }
}
