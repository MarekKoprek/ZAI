package com.example.fitshop.converter;

import com.example.fitshop.dto.ProductDTO;
import com.example.fitshop.dto.SubCategoryDTO;
import com.example.fitshop.model.Opinion;
import com.example.fitshop.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductToProductDTO {

    private final ProductImageToProductImageDTO productImageToProductImageDTO;
    private final ShipperToShipperDTO shipperToShipperDTO;

    public ProductDTO convert(Product product){
        double rating = product.getOpinions().stream()
                .mapToInt(Opinion::getRating)
                .average()
                .orElse(0.0);
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getImages().stream()
                        .map(productImageToProductImageDTO::convert)
                        .collect(Collectors.toList()),
                rating,
                product.getShippers().stream()
                        .map(shipperToShipperDTO::convert)
                        .collect(Collectors.toList()),
                new SubCategoryDTO(product.getSubCategory().getId(), product.getSubCategory().getName())
        );
    }
}
