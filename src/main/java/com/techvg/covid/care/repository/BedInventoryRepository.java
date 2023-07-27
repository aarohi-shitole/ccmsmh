package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.BedInventory;
import com.techvg.covid.care.domain.Inventory;
import com.techvg.covid.care.service.dto.BedInventoryDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BedInventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BedInventoryRepository extends JpaRepository<BedInventory, Long>, JpaSpecificationExecutor<BedInventory> {
	List<BedInventory> findByHospitalId(Long id);

	Optional<BedInventory> findByHospitalIdAndBedTypeId(Long hospitalId, Long bedTypeId);
}
