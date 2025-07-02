package com.example.fitshop.dto;

import com.example.fitshop.model.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;

    private String login;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private UserType type;

    private AddressDTO address;
}
