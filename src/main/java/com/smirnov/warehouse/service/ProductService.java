package com.smirnov.warehouse.service;

import com.smirnov.warehouse.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String getMostPopularProductForProvider(Long providerId) {
        return productRepository.findMostPopularProductForProvider(providerId);
    }
}
