package com.example.fitshop.repository;

import com.example.fitshop.model.AppUser;
import com.example.fitshop.model.Opinion;
import com.example.fitshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpinionRepo extends JpaRepository<Opinion, Long> {

    Optional<Opinion> findByProductAndUser(Product product, AppUser appUser);

}
