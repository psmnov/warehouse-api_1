package com.smirnov.warehouse.model;

import jakarta.persistence.*;

/**
 * Состав поставки — таблица-связь m:n между supply и product.
 * Соответствует INCLUDED_IN_ORDER из ЛР2/ЛР3.
 *
 * @IdClass(IncludedInOrderId.class) говорит Hibernate: "первичный ключ этой
 * сущности составной, описан в отдельном классе IncludedInOrderId, а поля
 * с тем же именем и типом, что в ID-классе, нужно пометить @Id прямо здесь".
 * Это альтернатива @EmbeddedId — выбрана, потому что позволяет обращаться
 * к supplyId и article как к обычным полям сущности, а не через объект id.
 */
@Entity
@Table(name = "included_in_order")
@IdClass(IncludedInOrderId.class)
public class IncludedInOrder {

    @Id
    @Column(name = "supply_id")
    private Long supplyId;

    @Id
    @Column(name = "article")
    private Long article;

    // supplyId/article дублируются как простые поля (для составного ключа)
    // и как связи @ManyToOne (для удобной навигации к связанным объектам).
    // insertable = false, updatable = false на связях — обязательны, иначе
    // Hibernate попытается записать одно и то же значение FK дважды:
    // один раз из supplyId, второй раз из supply.getId() — и выбросит ошибку.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_id", insertable = false, updatable = false)
    private Supply supply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article", insertable = false, updatable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    protected IncludedInOrder() {
    }

    public IncludedInOrder(Long supplyId, Long article, Integer quantity) {
        this.supplyId = supplyId;
        this.article = article;
        this.quantity = quantity;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public Long getArticle() {
        return article;
    }

    public Supply getSupply() {
        return supply;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
