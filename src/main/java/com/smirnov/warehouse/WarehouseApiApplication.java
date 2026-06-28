package com.smirnov.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Точка входа приложения. @SpringBootApplication — это сокращение для трёх
 * аннотаций одновременно: @Configuration (класс может объявлять бины),
 * @EnableAutoConfiguration (Spring Boot сам настраивает Tomcat, Hibernate,
 * пул соединений и т.п. по найденным в classpath библиотекам) и
 * @ComponentScan (Spring ищет @Service, @RestController, @Repository
 * в этом пакете и во всех вложенных — поэтому controller/service/repository
 * лежат внутри com.smirnov.warehouse, а не снаружи).
 */
@SpringBootApplication
public class WarehouseApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApiApplication.class, args);
    }
}
