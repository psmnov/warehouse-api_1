package com.smirnov.warehouse.repository;

import com.smirnov.warehouse.model.IncludedInOrder;
import com.smirnov.warehouse.model.IncludedInOrderId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Второй generic-параметр — IncludedInOrderId, а не Long: для сущностей
// с составным ключом JpaRepository параметризуется классом-ключом
// целиком, а не типом одного из его полей.
public interface IncludedInOrderRepository extends JpaRepository<IncludedInOrder, IncludedInOrderId> {

    List<IncludedInOrder> findBySupplyId(Long supplyId);
}
