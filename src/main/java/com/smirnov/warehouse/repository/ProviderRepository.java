package com.smirnov.warehouse.repository;

import com.smirnov.warehouse.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
