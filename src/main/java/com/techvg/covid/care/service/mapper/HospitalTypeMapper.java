package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.HospitalTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link HospitalType} and its DTO {@link HospitalTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HospitalTypeMapper extends EntityMapper<HospitalTypeDTO, HospitalType> {



    default HospitalType fromId(Long id) {
        if (id == null) {
            return null;
        }
        HospitalType hospitalType = new HospitalType();
        hospitalType.setId(id);
        return hospitalType;
    }
}
