package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.AuditTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditType} and its DTO {@link AuditTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuditTypeMapper extends EntityMapper<AuditTypeDTO, AuditType> {



    default AuditType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AuditType auditType = new AuditType();
        auditType.setId(id);
        return auditType;
    }
}
