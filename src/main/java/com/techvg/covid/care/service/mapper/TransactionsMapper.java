package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.dto.TransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transactions} and its DTO {@link TransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SupplierMapper.class, InventoryMapper.class,InventoryMapper.class,InventoryMasterMapper.class})
public interface TransactionsMapper extends EntityMapper<TransactionsDTO, Transactions> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "inventory.id", target = "inventoryId")
    @Mapping(source = "inventory.hospital.id", target = "hospitalId")
    @Mapping(source = "inventory.hospital.name", target = "hospitalName")
    @Mapping(source = "inventory.inventoryMaster.id", target = "inventoryMasterId")
    @Mapping(source = "inventory.inventoryMaster.name", target = "inventoryMasterName")
    TransactionsDTO toDto(Transactions transactions);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "inventoryId", target = "inventory")
    Transactions toEntity(TransactionsDTO transactionsDTO);

    default Transactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transactions transactions = new Transactions();
        transactions.setId(id);
        return transactions;
    }
}
