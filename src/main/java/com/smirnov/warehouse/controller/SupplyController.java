package com.smirnov.warehouse.controller;

import com.smirnov.warehouse.dto.AddProductToSupplyRequest;
import com.smirnov.warehouse.dto.SupplyCreateRequest;
import com.smirnov.warehouse.dto.SupplyResponse;
import com.smirnov.warehouse.service.SupplyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для операций с поставками.
 * Базовый путь /api/supplies — соответствует ресурсу "supply" множественного
 * числа, это распространённое соглашение REST (ресурсы во множественном числе).
 */
@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    // POST /api/supplies — создать новую поставку.
    // @Valid запускает проверку аннотаций (@NotNull и т.д.) из
    // SupplyCreateRequest до того, как метод выполнится; если проверка
    // не пройдена, Spring сам вернёт 400 Bad Request с описанием ошибки,
    // тело метода ничего для этого писать не нужно.
    @PostMapping
    public ResponseEntity<SupplyResponse> createSupply(@Valid @RequestBody SupplyCreateRequest request) {
        SupplyResponse response = supplyService.createSupply(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/supplies/{id} — получить одну поставку по id.
    // {id} в пути и @PathVariable Long id в сигнатуре метода связаны по имени.
    @GetMapping("/{id}")
    public ResponseEntity<SupplyResponse> getSupply(@PathVariable Long id) {
        return ResponseEntity.ok(supplyService.getSupply(id));
    }

    // GET /api/supplies?providerId=2 — список поставок конкретного поставщика.
    // @RequestParam читает значение из query-строки запроса, а не из пути.
    @GetMapping
    public ResponseEntity<List<SupplyResponse>> getSuppliesByProvider(@RequestParam Long providerId) {
        return ResponseEntity.ok(supplyService.getSuppliesByProvider(providerId));
    }

    // POST /api/supplies/{id}/items — добавить товар в состав поставки.
    // Вложенный путь /items означает "позиции внутри конкретной поставки id" —
    // это прямое отражение отношения "поставка содержит состав поставки"
    // из исходной ER-диаграммы.
    @PostMapping("/{id}/items")
    public ResponseEntity<Void> addProductToSupply(@PathVariable Long id,
                                                     @Valid @RequestBody AddProductToSupplyRequest request) {
        supplyService.addProductToSupply(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
