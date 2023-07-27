package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.SecurityRole;
import com.techvg.covid.care.domain.SecurityUser;
import com.techvg.covid.care.service.dto.SecurityUserDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link SecurityUser} and its DTO
 * {@link SecurityUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityPermissionMapper.class, SecurityRoleMapper.class })
public interface SecurityUserMapper extends EntityMapper<SecurityUserDTO, SecurityUser> {

	@Mapping(target = "removeSecurityPermission", ignore = true)
	@Mapping(target = "removeSecurityRole", ignore = true)

	default SecurityUser fromId(Long id) {
		if (id == null) {
			return null;
		}
		SecurityUser securityUser = new SecurityUser();
		securityUser.setId(id);
		return securityUser;
	}
}
