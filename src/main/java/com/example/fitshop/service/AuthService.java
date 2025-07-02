package com.example.fitshop.service;

import com.example.fitshop.converter.UserToUserDto;
import com.example.fitshop.dto.LoginResponseDto;
import com.example.fitshop.dto.LoginUserDto;
import com.example.fitshop.dto.RegisterUserDto;
import com.example.fitshop.model.AppUser;
import com.example.fitshop.model.UserType;
import com.example.fitshop.repository.AppUserRepo;
import com.example.fitshop.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserToUserDto userToUserDto;

    public AppUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<AppUser> user = userRepo.findByLogin(username);
        return user.orElse(null);
    }

    public AppUser registerUser(RegisterUserDto registerUserDto) {
        AppUser user = new AppUser();
        user.setLogin(registerUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setEmail(registerUserDto.getEmail());
        user.setFirstName(registerUserDto.getFirstName());
        user.setLastName(registerUserDto.getLastName());
        user.setType(UserType.USER);
        return userRepo.save(user);
    }

    public LoginResponseDto loginUser(LoginUserDto loginUserDto) {
        log.info("Login user: {} {}", loginUserDto.getUsername(), loginUserDto.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginUserDto.getUsername());
        String jwt = jwtService.generateToken(userDetails);

        return new LoginResponseDto(
                loginUserDto.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority(),
                "Bearer " + jwt);
    }
}
