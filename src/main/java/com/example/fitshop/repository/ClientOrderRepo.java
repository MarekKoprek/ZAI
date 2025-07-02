package com.example.fitshop.repository;

import com.example.fitshop.model.AppUser;
import com.example.fitshop.model.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientOrderRepo extends JpaRepository<ClientOrder, Long> {
    List<ClientOrder> findAllByUser(AppUser user);
}
