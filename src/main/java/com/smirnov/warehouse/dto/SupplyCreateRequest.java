package com.smirnov.warehouse.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) для тела запроса на создание поставки.
 *
 * Зачем отдельный класс, а не Supply прямо в контроллере:
 * 1. Entity Supply ссылается на Provider через @ManyToOne — клиент API
 *    не передаёт целый объект поставщика, только его id. DTO описывает
 *    именно то, что реально приходит в JSON, а не структуру таблицы в БД.
 * 2. Entity знает про JPA-аннотации и про внутренние поля (sumOfSupply,
 *    которое заполняется триггером и не должно прийти от клиента).
 *    DTO не даёт клиенту шанса подсунуть это поле во входящем JSON.
 */
public class SupplyCreateRequest {

    @NotNull(message = "providerId обязателен")
    private Long providerId;

    protected SupplyCreateRequest() {
        // пустой конструктор нужен Jackson для десериализации JSON -> объект
    }

    public SupplyCreateRequest(Long providerId) {
        this.providerId = providerId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
}
