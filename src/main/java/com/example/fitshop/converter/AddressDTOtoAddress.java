package com.example.fitshop.converter;

import com.example.fitshop.dto.AddressDTO;
import com.example.fitshop.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressDTOtoAddress {
    public Address convert(AddressDTO addressDTO){
        return new Address(null,
                addressDTO.getCountry(),
                addressDTO.getCity(),
                addressDTO.getStreet(),
                addressDTO.getBuilding(),
                addressDTO.getApartment());
    }
}
