package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.InventoryMasterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InventoryMaster} and its DTO {@link InventoryMasterDTO}.
 */
@Mapper(componentModel = "spring", uses = {InventoryTypeMapper.class})
public interface InventoryMasterMapper extends EntityMapper<InventoryMasterDTO, InventoryMaster> {

    @Mapping(source = "inventoryType.id", target = "inventoryTypeId")
    @Mapping(source = "inventoryType.name", target = "inventoryTypeName")
    InventoryMasterDTO toDto(InventoryMaster inventoryMaster);

    @Mapping(source = "inventoryTypeId", target = "inventoryType")
    InventoryMaster toEntity(InventoryMasterDTO inventoryMasterDTO);

    default InventoryMaster fromId(Long id) {
        if (id == null) {
            return null;
        }
        InventoryMaster inventoryMaster = new InventoryMaster();
        inventoryMaster.setId(id);
        return inventoryMaster;
    }
}
