package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contact} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContactTypeMapper.class, HospitalMapper.class, SupplierMapper.class})
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

    @Mapping(source = "contactType.id", target = "contactTypeId")
    @Mapping(source = "contactType.name", target = "contactTypeName")
    @Mapping(source = "hospital.id", target = "hospitalId")
    @Mapping(source = "hospital.name", target = "hospitalName")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    ContactDTO toDto(Contact contact);

    @Mapping(source = "contactTypeId", target = "contactType")
    @Mapping(source = "hospitalId", target = "hospital")
    @Mapping(source = "supplierId", target = "supplier")
    Contact toEntity(ContactDTO contactDTO);

    default Contact fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}
