package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.InventoryMaster;
import com.techvg.covid.care.domain.SecurityRole;
import com.techvg.covid.care.domain.SecurityUser;
import com.techvg.covid.care.service.dto.InventoryMasterDTO;
import com.techvg.covid.care.service.dto.LoginUserDTO;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link SecurityUser} and its DTO {@link LoginUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LoginUserMapper extends EntityMapper<LoginUserDTO, SecurityUser> {

	@Mapping(source = "userAccess", target = "userAccess")
	LoginUserDTO toDto(SecurityUser securityUser);

    @Mapping(target = "removeSecurityPermission", ignore = true)
    @Mapping(target = "removeSecurityRole", ignore = true)
    SecurityUser toEntity(LoginUserDTO loginUserDTO);
        
    default SecurityUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setId(id);
        return securityUser;
    }

    @AfterMapping
    default void populateAuthorities(SecurityUser entity, @MappingTarget LoginUserDTO dto) {
        Set<String> authorities = new HashSet<>();
        Set<String> authorities1 = new HashSet<>();

        authorities = entity.getSecurityPermissions().stream().map(permission->{
            return permission.getName();
        }).collect(Collectors.toSet());

        for (SecurityRole role : entity.getSecurityRoles()) {
            authorities1 = role.getSecurityPermissions().stream().map(permission->{return permission.getName();}).collect(Collectors.toSet());
            authorities.addAll(authorities1);
        }
        dto.setAuthorities(authorities);
    }
}
