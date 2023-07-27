package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.InventoryUsedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InventoryUsed} and its DTO {@link InventoryUsedDTO}.
 */
@Mapper(componentModel = "spring", uses = {InventoryMapper.class})
public interface InventoryUsedMapper extends EntityMapper<InventoryUsedDTO, InventoryUsed> {

    @Mapping(source = "inventory.id", target = "inventoryId")
    InventoryUsedDTO toDto(InventoryUsed inventoryUsed);

    @Mapping(source = "inventoryId", target = "inventory")
    InventoryUsed toEntity(InventoryUsedDTO inventoryUsedDTO);

    default InventoryUsed fromId(Long id) {
        if (id == null) {
            return null;
        }
        InventoryUsed inventoryUsed = new InventoryUsed();
        inventoryUsed.setId(id);
        return inventoryUsed;
    }
}
