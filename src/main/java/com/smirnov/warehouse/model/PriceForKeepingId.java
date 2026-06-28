package com.smirnov.warehouse.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 Составной первичный ключ (article, start_date) для price_for_keeping.
 Соответствует PRIMARY KEY(дата начала хранения, дата окончания хранения)
 */
public class PriceForKeepingId implements Serializable {

    private Long article;
    private LocalDateTime startDate;

    protected PriceForKeepingId() {
    }

    public PriceForKeepingId(Long article, LocalDateTime startDate) {
        this.article = article;
        this.startDate = startDate;
    }

    public Long getArticle() {
        return article;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceForKeepingId)) return false;
        PriceForKeepingId that = (PriceForKeepingId) o;
        return Objects.equals(article, that.article) && Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, startDate);
    }
}
