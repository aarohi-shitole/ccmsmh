package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.TalukaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Taluka} and its DTO {@link TalukaDTO}.
 */
@Mapper(componentModel = "spring", uses = {DistrictMapper.class})
public interface TalukaMapper extends EntityMapper<TalukaDTO, Taluka> {

    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "district.name", target = "districtName")
    TalukaDTO toDto(Taluka taluka);

    @Mapping(source = "districtId", target = "district")
    Taluka toEntity(TalukaDTO talukaDTO);

    default Taluka fromId(Long id) {
        if (id == null) {
            return null;
        }
        Taluka taluka = new Taluka();
        taluka.setId(id);
        return taluka;
    }
}
