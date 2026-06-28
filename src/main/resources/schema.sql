-- ============================================================================
-- Схема БД складского учёта поставок продукции (вариант 43).
--
-- Перенесена из лабораторных работ по дисциплине "Базы данных" (MySQL)
-- на PostgreSQL. Отличия от оригинальной MySQL-схемы и почему они внесены:
--
-- 1. AUTO_INCREMENT (MySQL) -> GENERATED ALWAYS AS IDENTITY (PostgreSQL).
--    Это стандартный способ автоинкремента в PostgreSQL начиная с версии 10,
--    рекомендован вместо устаревшего типа SERIAL.
--
-- 2. ENUM('коробка','пакет',...) как inline-тип колонки (MySQL) ->
--    отдельный пользовательский тип CREATE TYPE (PostgreSQL).
--    В PostgreSQL ENUM не объявляется прямо в определении колонки,
--    тип создаётся один раз и переиспользуется.
--
-- 3. DECIMAL(15,0) для телефона и DECIMAL(12,0) для ИНН (MySQL) -> VARCHAR.
--    Это исправление, а не просто перенос: хранить телефон и ИНН как число
--    некорректно само по себе — десятичный тип не допускает ведущие нули,
--    знак "+", пробелы и дефисы в номере, а также не нужна арифметика
--    над этими значениями. VARCHAR — корректный тип для идентификаторов
--    такого рода независимо от используемой СУБД.
--
-- 4. Добавлено поле sum_of_supply в таблицу supply — в оригинале появилось
--    позже как результат работы триггера (см. ЛР5), здесь сразу включено
--    в DDL для упрощения.
-- ============================================================================

CREATE TYPE package_type AS ENUM ('BOX', 'BAG', 'STACK', 'PACK', 'SPECIAL');

CREATE TABLE provider (
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name      VARCHAR(50) NOT NULL,
    address   VARCHAR(50),
    phone     VARCHAR(20) NOT NULL,
    inn       VARCHAR(12) NOT NULL
);

CREATE TABLE product (
    article     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    type_of_pack package_type NOT NULL DEFAULT 'BOX',
    weight      INTEGER NOT NULL,
    description VARCHAR(40)
);

CREATE TABLE supply (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    provider_id    BIGINT NOT NULL REFERENCES provider (id),
    supply_date    TIMESTAMP NOT NULL DEFAULT now(),
    sum_of_supply  NUMERIC(14, 2) NOT NULL DEFAULT 0
);

-- Таблица-связь m:n между supply и product (соответствует include_in_order)
CREATE TABLE included_in_order (
    supply_id BIGINT NOT NULL REFERENCES supply (id),
    article   BIGINT NOT NULL REFERENCES product (article),
    quantity  INTEGER NOT NULL CHECK (quantity > 0),
    PRIMARY KEY (supply_id, article)
);

-- История цен хранения товара
CREATE TABLE price_for_keeping (
    article    BIGINT NOT NULL REFERENCES product (article),
    price      NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    start_date TIMESTAMP NOT NULL DEFAULT now(),
    end_date   TIMESTAMP NOT NULL,
    PRIMARY KEY (article, start_date),
    CHECK (end_date > start_date)
);

CREATE INDEX idx_supply_provider ON supply (provider_id);
CREATE INDEX idx_included_in_order_article ON included_in_order (article);
CREATE INDEX idx_price_for_keeping_article ON price_for_keeping (article);

-- ============================================================================
-- Триггер: пересчёт суммы поставки при добавлении новой позиции товара.
-- Аналог оригинального триггера UPDATE_SUM из ЛР5, переписан под PL/pgSQL
-- (синтаксис триггеров в PostgreSQL отличается от MySQL: требуется отдельная
-- функция-обработчик и отдельное объявление CREATE TRIGGER, привязывающее
-- функцию к событию).
-- ============================================================================

CREATE OR REPLACE FUNCTION recalc_supply_sum() RETURNS TRIGGER AS $$
DECLARE
    current_price NUMERIC(10, 2);
BEGIN
    SELECT price INTO current_price
    FROM price_for_keeping
    WHERE article = NEW.article
      AND now() BETWEEN start_date AND end_date
    ORDER BY start_date DESC
    LIMIT 1;

    IF current_price IS NULL THEN
        current_price := 0;
    END IF;

    UPDATE supply
    SET sum_of_supply = sum_of_supply + NEW.quantity * current_price
    WHERE id = NEW.supply_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_sum
    AFTER INSERT ON included_in_order
    FOR EACH ROW
    EXECUTE FUNCTION recalc_supply_sum();

-- ============================================================================
-- Функция: самый закупаемый товар у конкретного поставщика.
-- Аналог MOST_POPULAR_PRODUCT_FOR_PROVIDER из ЛР5, переписан декларативно
-- через агрегацию вместо процедурного цикла WHILE по артикулам — в
-- PostgreSQL это естественнее выражается через GROUP BY + ORDER BY + LIMIT,
-- а не через ручной перебор как в оригинальной MySQL-функции.
-- ============================================================================

CREATE OR REPLACE FUNCTION most_popular_product_for_provider(p_provider_id BIGINT)
RETURNS VARCHAR AS $$
DECLARE
    result_name VARCHAR;
BEGIN
    SELECT pr.name INTO result_name
    FROM included_in_order io
    JOIN supply s ON s.id = io.supply_id
    JOIN product pr ON pr.article = io.article
    WHERE s.provider_id = p_provider_id
    GROUP BY pr.article, pr.name
    ORDER BY SUM(io.quantity) DESC
    LIMIT 1;

    RETURN result_name;
END;
$$ LANGUAGE plpgsql;
