package com.example.fitshop.repository;

import com.example.fitshop.model.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipperRepo extends JpaRepository<Shipper, Long> {
    Shipper findByName(String name);
}
