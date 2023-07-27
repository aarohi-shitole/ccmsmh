package com.techvg.covid.care.service.mapper;


import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.repository.InventoryRepository;
import com.techvg.covid.care.repository.SupplierRepository;
import com.techvg.covid.care.repository.TripDetailsRepository;
import com.techvg.covid.care.service.SupplierService;
import com.techvg.covid.care.service.dto.SupplierDTO;
import com.techvg.covid.care.service.dto.TripDetailsDTO;

import java.util.List;
import java.util.Optional;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link TripDetails} and its DTO {@link TripDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { SupplierMapper.class, HospitalMapper.class, TransactionsMapper.class,
		TripMapper.class })
public abstract class TripDetailsMapper implements EntityMapper<TripDetailsDTO, TripDetails> {

	@Mapping(source = "supplier.id", target = "supplierId")
	@Mapping(source = "supplier.name", target = "supplierName")
	@Mapping(source = "hospital.id", target = "hospitalId")
	@Mapping(source = "hospital.name", target = "hospitalName")
	@Mapping(source = "transactions.id", target = "transactionsId")
	@Mapping(source = "trip.id", target = "tripId")
	public abstract TripDetailsDTO toDto(TripDetails tripDetails);

	@Mapping(source = "supplierId", target = "supplier")
	@Mapping(source = "hospitalId", target = "hospital")
	@Mapping(source = "transactionsId", target = "transactions")
	@Mapping(source = "tripId", target = "trip")
	public abstract TripDetails toEntity(TripDetailsDTO tripDetailsDTO);
	
	@Autowired
	private SupplierService supplierService;

	TripDetails fromId(Long id) {
		if (id == null) {
			return null;
		}
		TripDetails tripDetails = new TripDetails();
		tripDetails.setId(id);
		return tripDetails;
	}

	@AfterMapping
	public void populateRelations(TripDetails entity, @MappingTarget TripDetailsDTO dto) {

		Trip trip = entity.getTrip();
		if (trip != null) {

			Supplier supplier = trip.getSupplier();
			if(supplier!=null) {
			dto.setSupplierName(supplier.getName());
			dto.setSupplierType(supplier.getSupplierType().getValue());
			}
			
//			if (dto.getSupplierId() != null) {
//				long supplierId = dto.getSupplierId();
//
//				Optional<SupplierDTO> supplierDTO = supplierService.findOne(supplierId);
//				//Optional<Supplier> supplierlist = supplierRepository.findById(supplierId);
//				if (supplierDTO.isPresent()) {
//
//					SupplierDTO supplier = supplierDTO.get();
//					dto.setSupplierName(supplier.getName());
//					dto.setSupplierType(supplier.getSupplierType().getValue());
//				}
//
//			}

			if (trip.getNumberPlate() != null) {
				dto.setTanckerNumberPlate(trip.getNumberPlate());
			}

			if (trip.getTrackingNo() != null) {
				dto.setTrackingNo(trip.getTrackingNo());
			}
		}

	}
}
