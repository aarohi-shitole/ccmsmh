package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.BedTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BedTransactions} and its DTO {@link BedTransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {BedTypeMapper.class, HospitalMapper.class})
public interface BedTransactionsMapper extends EntityMapper<BedTransactionsDTO, BedTransactions> {

    @Mapping(source = "bedType.id", target = "bedTypeId")
    @Mapping(source = "bedType.name", target = "bedTypeName")
    @Mapping(source = "hospital.id", target = "hospitalId")
    @Mapping(source = "hospital.name", target = "hospitalName")
    BedTransactionsDTO toDto(BedTransactions bedTransactions);

    @Mapping(source = "bedTypeId", target = "bedType")
    @Mapping(source = "hospitalId", target = "hospital")
    BedTransactions toEntity(BedTransactionsDTO bedTransactionsDTO);

    default BedTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        BedTransactions bedTransactions = new BedTransactions();
        bedTransactions.setId(id);
        return bedTransactions;
    }
}
