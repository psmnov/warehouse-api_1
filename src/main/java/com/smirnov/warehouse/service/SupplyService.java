package com.smirnov.warehouse.service;

import com.smirnov.warehouse.dto.AddProductToSupplyRequest;
import com.smirnov.warehouse.dto.SupplyCreateRequest;
import com.smirnov.warehouse.dto.SupplyResponse;
import com.smirnov.warehouse.model.IncludedInOrder;
import com.smirnov.warehouse.model.Provider;
import com.smirnov.warehouse.model.Supply;
import com.smirnov.warehouse.repository.IncludedInOrderRepository;
import com.smirnov.warehouse.repository.ProviderRepository;
import com.smirnov.warehouse.repository.SupplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 Сервисный слой: здесь находится бизнес-логика, контроллер только
 принимает HTTP-запрос и делегирует работу сюда. Разделение важно,

 */
@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final ProviderRepository providerRepository;
    private final IncludedInOrderRepository includedInOrderRepository;

    public SupplyService(SupplyRepository supplyRepository,
                          ProviderRepository providerRepository,
                          IncludedInOrderRepository includedInOrderRepository) {
        this.supplyRepository = supplyRepository;
        this.providerRepository = providerRepository;
        this.includedInOrderRepository = includedInOrderRepository;
    }

    @Transactional
    public SupplyResponse createSupply(SupplyCreateRequest request) {
        Provider provider = providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Поставщик с id=" + request.getProviderId() + " не найден"));

        Supply supply = new Supply(provider, LocalDateTime.now());
        Supply saved = supplyRepository.save(supply);

        return toResponse(saved);
    }


    @Transactional
    public void addProductToSupply(Long supplyId, AddProductToSupplyRequest request) {
        if (!supplyRepository.existsById(supplyId)) {
            throw new NoSuchElementException("Поставка с id=" + supplyId + " не найдена");
        }

        IncludedInOrder item = new IncludedInOrder(supplyId, request.getArticle(), request.getQuantity());
        includedInOrderRepository.save(item);
    }

    public SupplyResponse getSupply(Long id) {
        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Поставка с id=" + id + " не найдена"));
        return toResponse(supply);
    }

    public List<SupplyResponse> getSuppliesByProvider(Long providerId) {
        return supplyRepository.findByProviderId(providerId).stream()
                .map(this::toResponse)
                .toList();
    }

    // Маппинг entity -> DTO вынесен в приватный метод: вызывается из трёх
    // публичных методов выше, дублировать пять строк в каждом не нужно.
    private SupplyResponse toResponse(Supply supply) {
        return new SupplyResponse(
                supply.getId(),
                supply.getProvider().getName(),
                supply.getSupplyDate(),
                supply.getSumOfSupply()
        );
    }
}
