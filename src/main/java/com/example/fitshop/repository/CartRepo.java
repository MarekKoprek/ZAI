package com.example.fitshop.repository;

import com.example.fitshop.model.AppUser;
import com.example.fitshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {

    Cart findFirstById(Long id);

    List<Cart> findAllByUser(AppUser appUser);

}
