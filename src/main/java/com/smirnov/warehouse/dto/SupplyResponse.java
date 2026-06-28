package com.smirnov.warehouse.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO для ответа клиенту. Java record (появился в Java 16) — компактный
 * способ описать неизменяемый класс-данные: компилятор сам генерирует
 * конструктор, геттеры (без префикса get, просто id(), providerName() и
 * т.д.), equals, hashCode и toString из списка полей в скобках.
 * Для DTO, у которых нет своей логики (только хранение данных для ответа),
 * это короче и понятнее, чем писать класс с полями и геттерами руками,
 * как сделано в entity-классах выше.
 */
public record SupplyResponse(
        Long id,
        String providerName,
        LocalDateTime supplyDate,
        BigDecimal sumOfSupply
) {
}
