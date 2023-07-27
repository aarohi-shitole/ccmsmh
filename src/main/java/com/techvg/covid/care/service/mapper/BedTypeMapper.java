package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.BedTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BedType} and its DTO {@link BedTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BedTypeMapper extends EntityMapper<BedTypeDTO, BedType> {



    default BedType fromId(Long id) {
        if (id == null) {
            return null;
        }
        BedType bedType = new BedType();
        bedType.setId(id);
        return bedType;
    }
}
