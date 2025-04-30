package com.example.fitshop.repository;

import com.example.fitshop.model.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientOrderRepo extends JpaRepository<ClientOrder, Long> {
}
