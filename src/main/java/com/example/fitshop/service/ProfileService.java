package com.example.fitshop.service;

import com.example.fitshop.converter.UserToUserDto;
import com.example.fitshop.dto.UserDto;
import com.example.fitshop.model.Address;
import com.example.fitshop.model.AppUser;
import com.example.fitshop.repository.AddressRepo;
import com.example.fitshop.repository.AppUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final AuthService authService;
    private final AppUserRepo appUserRepo;
    private final AddressRepo addressRepo;
    private final UserToUserDto userToUserDto;

    public UserDto updateProfile(UserDto userDto) {
        AppUser user = authService.getAuthenticatedUser();
        if (user != null) {
            if (userDto.getAddress() != null) {
                boolean wasNull = false;
                Address address = user.getAddress();
                if (address == null) {
                    address = new Address();
                    wasNull = true;
                }
                address.setCountry(userDto.getAddress().getCountry());
                address.setCity(userDto.getAddress().getCity());
                address.setStreet(userDto.getAddress().getStreet());
                address.setBuilding(userDto.getAddress().getBuilding());
                address.setApartment(userDto.getAddress().getApartment());
                address = addressRepo.save(address);
                if (wasNull) {
                    user.setAddress(address);
                }
            }
            if (userDto.getEmail() != null) {
                user.setEmail(userDto.getEmail());
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setPhone(userDto.getPhone());
            }
            return userToUserDto.convert(appUserRepo.save(user));
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
