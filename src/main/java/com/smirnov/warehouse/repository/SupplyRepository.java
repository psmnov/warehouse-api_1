package com.smirnov.warehouse.repository;

import com.smirnov.warehouse.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    // Spring Data JPA сам строит SQL-запрос из имени метода
    List<Supply> findByProviderId(Long providerId);
}
