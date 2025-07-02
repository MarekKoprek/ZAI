package com.example.fitshop.converter;

import com.example.fitshop.dto.UserDto;
import com.example.fitshop.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToUserDto {

    private final AddressToAddressDTO addressToAddressDTO;

    public UserDto convert(AppUser user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setLogin(user.getLogin());
        userDto.setPhone(user.getPhone());
        userDto.setAddress(user.getAddress() != null ? addressToAddressDTO.convert(user.getAddress()) : null);
        return userDto;
    }
}
