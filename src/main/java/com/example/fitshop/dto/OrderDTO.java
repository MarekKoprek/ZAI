package com.example.fitshop.dto;

import com.example.fitshop.model.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private LocalDate date;
    private String paymentType;
    private double amount;
    private List<ProductDTO> products;
    private UserDto user;
    private AddressDTO address;
    private ShipperDTO shipper;
}
