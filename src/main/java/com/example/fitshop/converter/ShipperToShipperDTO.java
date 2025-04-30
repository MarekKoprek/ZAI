package com.example.fitshop.converter;

import com.example.fitshop.dto.ShipperDTO;
import com.example.fitshop.model.Shipper;
import org.springframework.stereotype.Component;

@Component
public class ShipperToShipperDTO {
    public ShipperDTO convert(Shipper shipper){
        return new ShipperDTO(shipper.getName(), shipper.getImage());
    }
}
