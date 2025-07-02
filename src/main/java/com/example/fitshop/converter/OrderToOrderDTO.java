package com.example.fitshop.converter;

import com.example.fitshop.dto.OrderDTO;
import com.example.fitshop.model.ClientOrder;
import com.example.fitshop.model.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderToOrderDTO {

    private final AddressToAddressDTO addressToAddressDTO;
    private final ShipperToShipperDTO shipperToShipperDTO;
    private final ProductToProductDTO productToProductDTO;
    private final UserToUserDto userToUserDTO;

    public OrderDTO convert(ClientOrder order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setDate(order.getDate());
        orderDTO.setPaymentType(order.getPaymentType() == PaymentType.CARD ? "CARD" : "BLIK");
        orderDTO.setAmount(order.getAmount());
        orderDTO.setShipper(shipperToShipperDTO.convert(order.getShipper()));
        orderDTO.setAddress(addressToAddressDTO.convert(order.getAddress()));
        orderDTO.setProducts(order.getProducts().stream()
                            .map(productToProductDTO::convert)
                            .collect(Collectors.toList()));
        orderDTO.setUser(userToUserDTO.convert(order.getUser()));
        return orderDTO;
    }
}
