package com.smirnov.warehouse.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Поставка. Соответствует таблице supply.
 */
@Entity
@Table(name = "supply")
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(name = "supply_date", nullable = false)
    private LocalDateTime supplyDate;

    // insertable = false, updatable = false: поле управляется триггером БД,
    // Java-код только читает значение, никогда не пишет его напрямую.
    // Если разрешить Hibernate писать это поле, можно случайно затереть
    // результат пересчёта триггера значением из памяти приложения.
    @Column(name = "sum_of_supply", nullable = false, insertable = false, updatable = false)
    private BigDecimal sumOfSupply;

    protected Supply() {
    }

    public Supply(Provider provider, LocalDateTime supplyDate) {
        this.provider = provider;
        this.supplyDate = supplyDate;
    }

    public Long getId() {
        return id;
    }

    public Provider getProvider() {
        return provider;
    }

    public LocalDateTime getSupplyDate() {
        return supplyDate;
    }

    public BigDecimal getSumOfSupply() {
        return sumOfSupply;
    }
}
