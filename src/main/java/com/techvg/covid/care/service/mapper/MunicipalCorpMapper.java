package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.MunicipalCorpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MunicipalCorp} and its DTO {@link MunicipalCorpDTO}.
 */
@Mapper(componentModel = "spring", uses = {DistrictMapper.class})
public interface MunicipalCorpMapper extends EntityMapper<MunicipalCorpDTO, MunicipalCorp> {

    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "district.name", target = "districtName")
    MunicipalCorpDTO toDto(MunicipalCorp municipalCorp);

    @Mapping(source = "districtId", target = "district")
    MunicipalCorp toEntity(MunicipalCorpDTO municipalCorpDTO);

    default MunicipalCorp fromId(Long id) {
        if (id == null) {
            return null;
        }
        MunicipalCorp municipalCorp = new MunicipalCorp();
        municipalCorp.setId(id);
        return municipalCorp;
    }
}
