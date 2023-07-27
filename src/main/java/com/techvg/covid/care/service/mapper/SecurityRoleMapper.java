package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.SecurityRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecurityRole} and its DTO {@link SecurityRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {SecurityPermissionMapper.class})
public interface SecurityRoleMapper extends EntityMapper<SecurityRoleDTO, SecurityRole> {


    @Mapping(target = "removeSecurityPermission", ignore = true)
    @Mapping(target = "securityUsers", ignore = true)
    @Mapping(target = "removeSecurityUser", ignore = true)
    SecurityRole toEntity(SecurityRoleDTO securityRoleDTO);

    default SecurityRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        SecurityRole securityRole = new SecurityRole();
        securityRole.setId(id);
        return securityRole;
    }
}
