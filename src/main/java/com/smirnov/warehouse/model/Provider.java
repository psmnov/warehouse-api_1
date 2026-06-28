package com.smirnov.warehouse.model;

import jakarta.persistence.*;

/**
 * Поставщик. Соответствует таблице provider (PROVIDER в исходной MySQL-схеме ЛР3).
 *
 * Поля NAME, ADRESS, PHONE, INN из оригинала переименованы в нижний camelCase
 * без опечаток (ADRESS -> address): это сознательное исправление орфографической
 * ошибки в названии колонки, а не расхождение схем по недосмотру.
 */
@Entity
@Table(name = "provider")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    // В исходной модели поле адреса было единственным необязательным —
    // это сохранено: nullable = true (значение по умолчанию для @Column).
    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "inn", nullable = false, length = 12)
    private String inn;

    protected Provider() {
        // пустой конструктор обязателен для JPA: Hibernate создаёт объекты
        // через reflection и должен иметь возможность вызвать конструктор
        // без аргументов, прежде чем заполнить поля через сеттеры/рефлексию.
    }

    public Provider(String name, String address, String phone, String inn) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inn = inn;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }
}
