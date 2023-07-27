package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.repository.InventoryRepository;
import com.techvg.covid.care.service.dto.SupplierDTO;

import java.util.List;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Supplier} and its DTO {@link SupplierDTO}.
 */
@Mapper(componentModel = "spring", uses = {StateMapper.class, DistrictMapper.class, TalukaMapper.class, CityMapper.class, InventoryTypeMapper.class})
public abstract class SupplierMapper implements EntityMapper<SupplierDTO, Supplier> {

    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "state.name", target = "stateName")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "district.name", target = "districtName")
    @Mapping(source = "taluka.id", target = "talukaId")
    @Mapping(source = "taluka.name", target = "talukaName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "inventoryType.id", target = "inventoryTypeId")
    @Mapping(source = "inventoryType.name", target = "inventoryTypeName")
    public abstract SupplierDTO toDto(Supplier supplier);

    @Mapping(source = "stateId", target = "state")
    @Mapping(source = "districtId", target = "district")
    @Mapping(source = "talukaId", target = "taluka")
    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "inventoryTypeId", target = "inventoryType")
    @Mapping(target = "hospitals", ignore = true)
    @Mapping(target = "removeHospital", ignore = true)
    public abstract Supplier toEntity(SupplierDTO supplierDTO);
    
	@Autowired
	InventoryRepository inventoryRepo;
	
	@Autowired
	InventoryMapper inventoryMapper;

    Supplier fromId(Long id) {
        if (id == null) {
            return null;
        }
        Supplier supplier = new Supplier();
        supplier.setId(id);
        return supplier;
    }
    
    @AfterMapping
   	public void populateRelations(Supplier entity, @MappingTarget SupplierDTO dto) {   	
    	
    	List<Inventory> invlist = inventoryRepo.findBySupplierId(entity.getId());    	
    	if (!invlist.isEmpty()) {
    		dto.setInventory(inventoryMapper.toDto(invlist));
    	}

    }
}
