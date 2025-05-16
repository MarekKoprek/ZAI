package com.example.fitshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterDTO {
    double minPrice;
    double maxPrice;
    double minRating;
}
