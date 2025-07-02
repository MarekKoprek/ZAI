package com.example.fitshop.controller;

import com.example.fitshop.converter.UserToUserDto;
import com.example.fitshop.dto.*;
import com.example.fitshop.security.JwtService;
import com.example.fitshop.service.AuthService;
import com.example.fitshop.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final UserToUserDto userToUserDto;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.ok().body(
                authService.loginUser(loginUserDto)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok().body(
                userToUserDto.convert(authService.registerUser(registerUserDto))
        );
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> currentUser() {
        return ResponseEntity.ok().body(
                userToUserDto.convert(authService.getAuthenticatedUser())
        );
    }
}
