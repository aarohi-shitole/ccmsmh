package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.ContactTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactType} and its DTO {@link ContactTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactTypeMapper extends EntityMapper<ContactTypeDTO, ContactType> {



    default ContactType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactType contactType = new ContactType();
        contactType.setId(id);
        return contactType;
    }
}
