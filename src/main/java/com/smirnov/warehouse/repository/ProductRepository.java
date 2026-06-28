package com.smirnov.warehouse.repository;

import com.smirnov.warehouse.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /*
     nativeQuery = true, обычный SQL, который выполнится
     на стороне PostgreSQL без перевода.
     */
    @Query(value = "SELECT most_popular_product_for_provider(:providerId)", nativeQuery = true)
    String findMostPopularProductForProvider(@Param("providerId") Long providerId);
}
