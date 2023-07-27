package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.repository.BedInventoryRepository;
import com.techvg.covid.care.repository.InventoryRepository;
import com.techvg.covid.care.service.dto.HospitalDTO;

import java.util.List;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Hospital} and its DTO {@link HospitalDTO}.
 */
@Mapper(componentModel = "spring", uses = {StateMapper.class, DistrictMapper.class, TalukaMapper.class, CityMapper.class, MunicipalCorpMapper.class, HospitalTypeMapper.class, SupplierMapper.class})
public abstract class HospitalMapper implements EntityMapper<HospitalDTO, Hospital> {

    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "state.name", target = "stateName")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "district.name", target = "districtName")
    @Mapping(source = "taluka.id", target = "talukaId")
    @Mapping(source = "taluka.name", target = "talukaName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "municipalCorp.id", target = "municipalCorpId")
    @Mapping(source = "municipalCorp.name", target = "municipalCorpName")
    @Mapping(source = "hospitalType.id", target = "hospitalTypeId")
    @Mapping(source = "hospitalType.name", target = "hospitalTypeName")
    public abstract HospitalDTO toDto(Hospital hospital);

    @Mapping(source = "stateId", target = "state")
    @Mapping(source = "districtId", target = "district")
    @Mapping(source = "talukaId", target = "taluka")
    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "municipalCorpId", target = "municipalCorp")
    @Mapping(source = "hospitalTypeId", target = "hospitalType")
    @Mapping(target = "removeSupplier", ignore = true)
    public abstract Hospital toEntity(HospitalDTO hospitalDTO);

	@Autowired
	InventoryRepository inventoryRepo;
	
	@Autowired
	InventoryMapper inventoryMapper;
	
	@Autowired
	BedInventoryRepository bedInventoryRepo;
	
	@Autowired
	BedInventoryMapper bedInventoryMapper;

    Hospital fromId(Long id) {
        if (id == null) {
            return null;
        }
        Hospital hospital = new Hospital();
        hospital.setId(id);
        return hospital;
    }
    
    @AfterMapping
   	public void populateRelations(Hospital entity, @MappingTarget HospitalDTO dto) {   	
    	
    	List<Inventory> invlist = inventoryRepo.findByHospitalId(entity.getId());    	
    	if (!invlist.isEmpty()) {
    		dto.setInventory(inventoryMapper.toDto(invlist));
    	}
    	
    	List<BedInventory> bedInvlist = bedInventoryRepo.findByHospitalId(entity.getId());    	
    	if (!bedInvlist.isEmpty()) {
    		dto.setBedInventory(bedInventoryMapper.toDto(bedInvlist));
    	}
    
    }
}
