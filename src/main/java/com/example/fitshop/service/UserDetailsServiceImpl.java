package com.example.fitshop.service;

import com.example.fitshop.model.AppUser;
import com.example.fitshop.repository.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(user.getLogin(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getType().name())));
    }
}
