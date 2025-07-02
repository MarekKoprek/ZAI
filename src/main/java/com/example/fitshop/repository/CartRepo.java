package com.example.fitshop.repository;

import com.example.fitshop.model.AppUser;
import com.example.fitshop.model.Cart;
import com.example.fitshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {

    Cart findFirstById(Long id);

    List<Cart> findAllByUser(AppUser appUser);

    Optional<Cart> findCartByProductAndUser(Product product, AppUser user);

    void deleteAllByUser(AppUser appUser);
}
