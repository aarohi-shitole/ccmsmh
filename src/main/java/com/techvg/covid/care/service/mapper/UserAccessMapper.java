package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.UserAccessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAccess} and its DTO {@link UserAccessDTO}.
 */
@Mapper(componentModel = "spring", uses = {SecurityUserMapper.class})
public interface UserAccessMapper extends EntityMapper<UserAccessDTO, UserAccess> {

    @Mapping(source = "securityUser.id", target = "securityUserId")
    @Mapping(source = "securityUser.login", target = "securityUserLogin")
    UserAccessDTO toDto(UserAccess userAccess);

    @Mapping(source = "securityUserId", target = "securityUser")
    UserAccess toEntity(UserAccessDTO userAccessDTO);

    default UserAccess fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAccess userAccess = new UserAccess();
        userAccess.setId(id);
        return userAccess;
    }
}
