package com.smirnov.warehouse.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 История цен хранения товара.
 Каждая запись это цена, действовавшая для конкретного артикула
 интервале [startDate, endDate].
 */
@Entity
@Table(name = "price_for_keeping")
@IdClass(PriceForKeepingId.class)
public class PriceForKeeping {

    @Id
    @Column(name = "article")
    private Long article;

    @Id
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article", insertable = false, updatable = false)
    private Product product;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    protected PriceForKeeping() {
    }

    public PriceForKeeping(Long article, BigDecimal price, LocalDateTime startDate, LocalDateTime endDate) {
        this.article = article;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getArticle() {
        return article;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
