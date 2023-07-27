package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.CityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring", uses = {TalukaMapper.class})
public interface CityMapper extends EntityMapper<CityDTO, City> {

    @Mapping(source = "taluka.id", target = "talukaId")
    @Mapping(source = "taluka.name", target = "talukaName")
    CityDTO toDto(City city);

    @Mapping(source = "talukaId", target = "taluka")
    City toEntity(CityDTO cityDTO);

    default City fromId(Long id) {
        if (id == null) {
            return null;
        }
        City city = new City();
        city.setId(id);
        return city;
    }
}
