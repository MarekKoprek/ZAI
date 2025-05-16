package com.example.fitshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String country;
    private String city;
    private String street;
    private String building;
    private String apartment;
}
