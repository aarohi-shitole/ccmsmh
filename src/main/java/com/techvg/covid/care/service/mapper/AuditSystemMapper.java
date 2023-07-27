package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.AuditSystemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditSystem} and its DTO {@link AuditSystemDTO}.
 */
@Mapper(componentModel = "spring", uses = {HospitalMapper.class, SupplierMapper.class, AuditTypeMapper.class})
public interface AuditSystemMapper extends EntityMapper<AuditSystemDTO, AuditSystem> {

    @Mapping(source = "hospital.id", target = "hospitalId")
    @Mapping(source = "hospital.name", target = "hospitalName")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "auditType.id", target = "auditTypeId")
    @Mapping(source = "auditType.name", target = "auditTypeName")
    AuditSystemDTO toDto(AuditSystem auditSystem);

    @Mapping(source = "hospitalId", target = "hospital")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "auditTypeId", target = "auditType")
    AuditSystem toEntity(AuditSystemDTO auditSystemDTO);

    default AuditSystem fromId(Long id) {
        if (id == null) {
            return null;
        }
        AuditSystem auditSystem = new AuditSystem();
        auditSystem.setId(id);
        return auditSystem;
    }
}
