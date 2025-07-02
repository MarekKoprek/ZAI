package com.example.fitshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
