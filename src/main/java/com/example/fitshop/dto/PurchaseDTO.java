package com.example.fitshop.dto;

import com.example.fitshop.model.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseDTO {
    List<CartDTO> carts;
    String shipperName;
    String paymentType;
    double amount;
    AddressDTO address;
}
