package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.PatientInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientInfo} and its DTO {@link PatientInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {StateMapper.class, DistrictMapper.class})
public interface PatientInfoMapper extends EntityMapper<PatientInfoDTO, PatientInfo> {

    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "state.name", target = "stateName")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "district.name", target = "districtName")
    PatientInfoDTO toDto(PatientInfo patientInfo);

    @Mapping(source = "stateId", target = "state")
    @Mapping(source = "districtId", target = "district")
    PatientInfo toEntity(PatientInfoDTO patientInfoDTO);

    default PatientInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setId(id);
        return patientInfo;
    }
}
