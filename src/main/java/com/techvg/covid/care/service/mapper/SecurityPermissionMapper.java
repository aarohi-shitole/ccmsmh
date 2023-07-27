package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.SecurityPermissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecurityPermission} and its DTO {@link SecurityPermissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SecurityPermissionMapper extends EntityMapper<SecurityPermissionDTO, SecurityPermission> {


    @Mapping(target = "securityRoles", ignore = true)
    @Mapping(target = "removeSecurityRole", ignore = true)
    @Mapping(target = "securityUsers", ignore = true)
    @Mapping(target = "removeSecurityUser", ignore = true)
    SecurityPermission toEntity(SecurityPermissionDTO securityPermissionDTO);

    default SecurityPermission fromId(Long id) {
        if (id == null) {
            return null;
        }
        SecurityPermission securityPermission = new SecurityPermission();
        securityPermission.setId(id);
        return securityPermission;
    }
}
