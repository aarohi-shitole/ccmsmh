package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.InventoryTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InventoryType} and its DTO {@link InventoryTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InventoryTypeMapper extends EntityMapper<InventoryTypeDTO, InventoryType> {



    default InventoryType fromId(Long id) {
        if (id == null) {
            return null;
        }
        InventoryType inventoryType = new InventoryType();
        inventoryType.setId(id);
        return inventoryType;
    }
}
