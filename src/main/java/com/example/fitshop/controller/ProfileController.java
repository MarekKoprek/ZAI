package com.example.fitshop.controller;

import com.example.fitshop.dto.ProductDTO;
import com.example.fitshop.dto.UserDto;
import com.example.fitshop.service.ProductService;
import com.example.fitshop.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("edit/profile")
    public ResponseEntity<UserDto> editProfile(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(profileService.updateProfile(userDto));
    }
}
