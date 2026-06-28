package com.smirnov.warehouse.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Составной первичный ключ для included_in_order (supply_id, article).
 *
 * В оригинальной MySQL-таблице PRIMARY KEY(ord_id, article) — это обычная
 * SQL-конструкция, не требующая ничего особенного. В JPA для составного
 * ключа нужен отдельный класс с двумя обязательными условиями:
 *
 * 1. implements Serializable — Hibernate должен уметь сериализовать ID
 *    для кэширования и логирования.
 * 2. переопределённые equals() и hashCode() — Hibernate сравнивает сущности
 *    по их ID при работе с коллекциями (Set, кэш первого уровня), и без
 *    корректного equals/hashCode две записи с одинаковыми supplyId/article
 *    не будут считаться одной и той же записью.
 */
@Embeddable
public class IncludedInOrderId implements Serializable {

    private Long supplyId;
    private Long article;

    protected IncludedInOrderId() {
    }

    public IncludedInOrderId(Long supplyId, Long article) {
        this.supplyId = supplyId;
        this.article = article;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public Long getArticle() {
        return article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IncludedInOrderId)) return false;
        IncludedInOrderId that = (IncludedInOrderId) o;
        return Objects.equals(supplyId, that.supplyId) && Objects.equals(article, that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplyId, article);
    }
}
