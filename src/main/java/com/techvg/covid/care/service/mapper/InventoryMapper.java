package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import com.techvg.covid.care.repository.InventoryMasterRepository;
import com.techvg.covid.care.repository.InventoryRepository;
import com.techvg.covid.care.repository.TransactionsRepository;
import com.techvg.covid.care.service.dto.InventoryDTO;
import com.techvg.covid.care.service.dto.InventoryMasterDTO;
import com.techvg.covid.care.service.dto.SupplierDTO;

import java.util.List;
import java.util.Optional;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Inventory} and its DTO {@link InventoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { InventoryMasterMapper.class, SupplierMapper.class, HospitalMapper.class,InventoryTypeMapper.class})
public abstract class InventoryMapper implements EntityMapper<InventoryDTO, Inventory> {

	@Mapping(source = "inventoryMaster.id", target = "inventoryMasterId")
	@Mapping(source = "inventoryMaster.name", target = "inventoryMasterName")
	@Mapping(source = "supplier.id", target = "supplierId")
	@Mapping(source = "supplier.name", target = "supplierName")
	@Mapping(source = "hospital.id", target = "hospitalId")
	@Mapping(source = "hospital.name", target = "hospitalName")
	@Mapping(source = "inventoryMaster.inventoryType.name", target = "inventoryTypeName")
	@Mapping(source = "inventoryMaster.inventoryType.id", target = "inventoryTypeId")
	public abstract InventoryDTO toDto(Inventory inventory);

	@Mapping(source = "inventoryMasterId", target = "inventoryMaster")
	@Mapping(source = "supplierId", target = "supplier")
	@Mapping(source = "hospitalId", target = "hospital")
	public abstract Inventory toEntity(InventoryDTO inventoryDTO);

	Inventory fromId(Long id) {
		if (id == null) {
			return null;
		}
		Inventory inventory = new Inventory();
		inventory.setId(id);
		return inventory;
	}

}
