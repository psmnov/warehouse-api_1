package com.smirnov.warehouse.model;

import jakarta.persistence.*;

/**
 * Продукция. Соответствует таблице product (PRODUCT в ЛР3).
 * Первичный ключ — артикул (article)
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article")
    private Long article;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    // @Enumerated(STRING) хранит в БД текстовое имя константы ('BOX', 'BAG'...),

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_pack", nullable = false)
    private PackageType typeOfPack;

    @Column(name = "weight", nullable = false)
    private Integer weight;


    @Column(name = "description", length = 40)
    private String description;

    protected Product() {
    }

    public Product(String name, PackageType typeOfPack, Integer weight, String description) {
        this.name = name;
        this.typeOfPack = typeOfPack;
        this.weight = weight;
        this.description = description;
    }

    public Long getArticle() {
        return article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PackageType getTypeOfPack() {
        return typeOfPack;
    }

    public void setTypeOfPack(PackageType typeOfPack) {
        this.typeOfPack = typeOfPack;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
