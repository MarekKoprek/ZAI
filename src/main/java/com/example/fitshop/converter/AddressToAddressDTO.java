package com.example.fitshop.converter;

import com.example.fitshop.dto.AddressDTO;
import com.example.fitshop.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressToAddressDTO {
    public AddressDTO convert(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setBuilding(address.getBuilding());
        addressDTO.setApartment(address.getApartment());
        return addressDTO;
    }
}
