package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.BedInventoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BedInventory} and its DTO {@link BedInventoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {BedTypeMapper.class, HospitalMapper.class})
public interface BedInventoryMapper extends EntityMapper<BedInventoryDTO, BedInventory> {

    @Mapping(source = "bedType.id", target = "bedTypeId")
    @Mapping(source = "bedType.name", target = "bedTypeName")
    @Mapping(source = "hospital.id", target = "hospitalId")
    @Mapping(source = "hospital.name", target = "hospitalName")
    BedInventoryDTO toDto(BedInventory bedInventory);

    @Mapping(source = "bedTypeId", target = "bedType")
    @Mapping(source = "hospitalId", target = "hospital")
    BedInventory toEntity(BedInventoryDTO bedInventoryDTO);

    default BedInventory fromId(Long id) {
        if (id == null) {
            return null;
        }
        BedInventory bedInventory = new BedInventory();
        bedInventory.setId(id);
        return bedInventory;
    }
}
