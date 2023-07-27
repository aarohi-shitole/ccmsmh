package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.Inventory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Inventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {
	List<Inventory> findByHospitalId(Long id);
	List<Inventory> findBySupplierId(Long id);
	Optional<Inventory> findByHospitalIdAndInventoryMasterId(Long hospitalId, Long inventoryMasterId);
	Optional<Inventory> findBySupplierIdAndInventoryMasterId(Long supplierId, Long inventoryMasterId);
}
