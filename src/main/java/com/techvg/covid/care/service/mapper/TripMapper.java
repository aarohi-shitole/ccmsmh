package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.*;
import com.techvg.covid.care.service.SupplierService;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.dto.SupplierDTO;
import com.techvg.covid.care.service.dto.TripDTO;
import com.techvg.covid.care.service.dto.TripDetailsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Trip} and its DTO {@link TripDTO}.
 */
@Mapper(componentModel = "spring", uses = { SupplierMapper.class })
public abstract class TripMapper implements EntityMapper<TripDTO, Trip> {

	@Mapping(source = "supplier.id", target = "supplierId")
	@Mapping(source = "supplier.name", target = "supplierName")
	public abstract TripDTO toDto(Trip trip);

	@Mapping(target = "tripDetails", ignore = true)
	@Mapping(target = "removeTripDetails", ignore = true)
	@Mapping(source = "supplierId", target = "supplier")
	public abstract Trip toEntity(TripDTO tripDTO);

	@Autowired
	TripDetailsMapper tripDetailsMapper;
	@Autowired
	private SupplierService supplierService;

	Trip fromId(Long id) {
		if (id == null) {
			return null;
		}
		Trip trip = new Trip();
		trip.setId(id);
		return trip;
	}

	@AfterMapping
	public void populateRelations(Trip trip, @MappingTarget TripDTO dto) {
		if (!trip.getTripDetails().isEmpty()) {

			List<TripDetailsDTO> tripDetailsLsit = tripDetailsMapper.toDto(new ArrayList<>(trip.getTripDetails()));

			for (TripDetailsDTO tripDetailsDTO : tripDetailsLsit) {

				if (tripDetailsDTO.getSupplierId() != null) {

					long supplierId = tripDetailsDTO.getSupplierId();
					Optional<SupplierDTO> supplierDTO = supplierService.findOne(supplierId);

					if (supplierDTO.isPresent()) {
						SupplierDTO supplier = supplierDTO.get();
						tripDetailsDTO.setSupplierName(supplier.getName());
						tripDetailsDTO.setSupplierType(supplier.getSupplierType().getValue());
					}
				}
			}

			dto.setTripDetails(tripDetailsLsit);

		}
	}
}
