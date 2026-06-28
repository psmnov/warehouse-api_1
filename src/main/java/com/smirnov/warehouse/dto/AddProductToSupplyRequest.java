package com.smirnov.warehouse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 DTO тела запроса на добавление товара в состав поставки.
 прямой аналог операции
 INSERT INTO included_in_order VALUES (...), которая запускала триггер UPDATE_SUM. Здесь тот же эффект
 достигается через сохранение IncludedInOrder в БД, что приводит к
 срабатыванию trg_update_sum на стороне PostgreSQL (см. schema.sql).
 */
public class AddProductToSupplyRequest {

    @NotNull(message = "article обязателен")
    private Long article;

    @NotNull(message = "quantity обязателен")
    @Min(value = 1, message = "quantity должен быть положительным")
    private Integer quantity;

    protected AddProductToSupplyRequest() {
    }

    public AddProductToSupplyRequest(Long article, Integer quantity) {
        this.article = article;
        this.quantity = quantity;
    }

    public Long getArticle() {
        return article;
    }

    public void setArticle(Long article) {
        this.article = article;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
