package com.smirnov.warehouse.controller;

import com.smirnov.warehouse.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/providers")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/providers/{providerId}/most-popular-product
    // Эндпоинт — обёртка над PL/pgSQL-функцией most_popular_product_for_provider,
    // переносом MOST_POPULAR_PRODUCT_FOR_PROVIDER из ЛР5 на PostgreSQL.
    @GetMapping("/{providerId}/most-popular-product")
    public ResponseEntity<String> getMostPopularProduct(@PathVariable Long providerId) {
        String productName = productService.getMostPopularProductForProvider(providerId);
        if (productName == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productName);
    }
}
