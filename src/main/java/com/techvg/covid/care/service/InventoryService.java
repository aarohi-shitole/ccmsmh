package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.Inventory;
import com.techvg.covid.care.odas.ODASMQCommHelper;
import com.techvg.covid.care.odas.model.CunsumptionInfo;
import com.techvg.covid.care.odas.model.OxygenConsumption;
import com.techvg.covid.care.odas.model.OxygenInfo;
import com.techvg.covid.care.odas.model.OxygenInfra;
import com.techvg.covid.care.repository.InventoryRepository;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.dto.InventoryDTO;
import com.techvg.covid.care.service.mapper.InventoryMapper;
import com.techvg.covid.care.web.rest.errors.AlreadyExitsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Inventory}.
 */
@Service
@Transactional
public class InventoryService {

	private final Logger log = LoggerFactory.getLogger(InventoryService.class);

	private final InventoryRepository inventoryRepository;

	private final InventoryMapper inventoryMapper;
	@Autowired
	ODASMQCommHelper rabitMQCommHelper;

	@Lazy
	@Autowired
	private HospitalService hospitalService;

	public InventoryService(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
		this.inventoryRepository = inventoryRepository;
		this.inventoryMapper = inventoryMapper;
	}

	/**
	 * Save a inventory.
	 *
	 * @param inventoryDTO the entity to save.
	 * @return the persisted entity.
	 */
	public InventoryDTO save(InventoryDTO inventoryDTO) {
		log.debug("Request to save Inventory : {}", inventoryDTO);

		Optional<Inventory> hospitalInventoryOptional = null;

		if (inventoryDTO.getHospitalId() != null) {
			hospitalInventoryOptional = inventoryRepository.findByHospitalIdAndInventoryMasterId(
					inventoryDTO.getHospitalId(), inventoryDTO.getInventoryMasterId());
		} else {
			hospitalInventoryOptional = inventoryRepository.findBySupplierIdAndInventoryMasterId(
					inventoryDTO.getSupplierId(), inventoryDTO.getInventoryMasterId());
		}

		if (inventoryDTO.getId() == null && hospitalInventoryOptional.isPresent()) {
			throw new AlreadyExitsException("Inventory already exists.");
		} else {
			Inventory inventory = inventoryMapper.toEntity(inventoryDTO);
			inventory = inventoryRepository.save(inventory);
			return inventoryMapper.toDto(inventory);
		}
	}

	/**
	 * Get all the inventories.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<InventoryDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Inventories");
		return inventoryRepository.findAll(pageable).map(inventoryMapper::toDto);
	}

	/**
	 * Get one inventory by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<InventoryDTO> findOne(Long id) {
		log.debug("Request to get Inventory : {}", id);
		return inventoryRepository.findById(id).map(inventoryMapper::toDto);
	}

	/**
	 * Delete the inventory by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Inventory : {}", id);
		inventoryRepository.deleteById(id);
	}

	@Async
	public void sendFacilityOxygenInfoToODAS(Long hospitalId, List<InventoryDTO> list, boolean hasUpdateOpration) {

		Optional<HospitalDTO> hospital = hospitalService.findOne(hospitalId);

		HospitalDTO hospitalDTO = null;

		if (hospital.isPresent()) {

			hospitalDTO = hospital.get();
			String odasFacilityId = hospitalDTO.getOdasFacilityId();
			String odasReferenceNumber = hospitalDTO.getReferenceNumber();

			if (odasFacilityId != null) {
				OxygenInfo oxygenInfo = getMappedOxygenInventoryToOxygenInfo(odasFacilityId, odasReferenceNumber, list,
						hasUpdateOpration);
				rabitMQCommHelper.publishFacilityOxygenInfo(oxygenInfo);
			} else {
				hospitalService.sendFacilityInfoToODAS(hospitalDTO);
			}

		}

	}

	private OxygenInfo getMappedOxygenInventoryToOxygenInfo(String odasFacilityId, String odasReferenceNumber,
			List<InventoryDTO> list, boolean hasUpdateOpration) {

		OxygenInfo oxygenInfo = new OxygenInfo();

		OxygenInfra o2Infra = new OxygenInfra();

		if (list != null & list.size() > 0) {
			for (InventoryDTO inventoryDTO : list) {

				Long inventoryMasterId = inventoryDTO.getInventoryMasterId();

				// -----------------------------------------------------------------------------------------
				// Type A Oxygen
				if (inventoryMasterId == 16) {
					o2Infra.setCylinder_a_type_yn("Y");
					o2Infra.setCylinder_a_type_capacity(String.valueOf(inventoryDTO.getCapcity()));
				} else {
					o2Infra.setCylinder_a_type_yn("N");
					o2Infra.setCylinder_a_type_capacity(String.valueOf(0));
				}
				// --------------------------------------------------------------------------------------------

				// -----------------------------------------------------------------------------------------
				// Type B Oxygen
				if (inventoryMasterId == 10) {
					o2Infra.setCylinder_b_type_yn("Y");
					o2Infra.setCylinder_b_type_capacity(String.valueOf(inventoryDTO.getCapcity()));
				} else {
					o2Infra.setCylinder_b_type_yn("N");
					o2Infra.setCylinder_b_type_capacity(String.valueOf(0));
				}
				// --------------------------------------------------------------------------------------------

				// -----------------------------------------------------------------------------------------
				// Type C20 Oxygen
				if (inventoryMasterId == 17) {
					o2Infra.setCylinder_c20_type_yn("Y");
					o2Infra.setCylinder_c20_type_capacity(String.valueOf(inventoryDTO.getCapcity()));
				} else {
					o2Infra.setCylinder_c20_type_yn("N");
					o2Infra.setCylinder_c20_type_capacity(String.valueOf(0));
				}
				// --------------------------------------------------------------------------------------------

				// -----------------------------------------------------------------------------------------
				// Type C35 Oxygen
				if (inventoryMasterId == 18) {
					o2Infra.setCylinder_c35_type_yn("Y");
					o2Infra.setCylinder_c35_type_capacity(String.valueOf(inventoryDTO.getCapcity()));
				} else {
					o2Infra.setCylinder_c35_type_yn("N");
					o2Infra.setCylinder_c35_type_capacity(String.valueOf(0));
				}
				// --------------------------------------------------------------------------------------------

				// -----------------------------------------------------------------------------------------
				// Type c45 Oxygen
				if (inventoryMasterId == 19) {
					o2Infra.setCylinder_c45_type_yn("Y");
					o2Infra.setCylinder_c45_type_capacity(String.valueOf(inventoryDTO.getCapcity()));
				} else {
					o2Infra.setCylinder_c45_type_yn("N");
					o2Infra.setCylinder_c45_type_capacity(String.valueOf(0));
				}
				// --------------------------------------------------------------------------------------------

				// -----------------------------------------------------------------------------------------
				// Type D6 Oxygen
				if (inventoryMasterId == 9) {
					o2Infra.setCylinder_d6_type_yn("Y");
					o2Infra.setCylinder_d6_type_capacity(String.valueOf(inventoryDTO.getCapcity()));
				} else {
					o2Infra.setCylinder_d6_type_yn("N");
					o2Infra.setCylinder_d6_type_capacity(String.valueOf(0));
				}
				// --------------------------------------------------------------------------------------------

				// -----------------------------------------------------------------------------------------
				// Type D7 Oxygen
				if (inventoryMasterId == 20) {
					o2Infra.setCylinder_d7_type_yn("Y");
					o2Infra.setCylinder_d7_type_capacity(String.valueOf(inventoryDTO.getCapcity()));
				} else {
					o2Infra.setCylinder_d7_type_yn("N");
					o2Infra.setCylinder_d7_type_capacity(String.valueOf(0));
				}
				// --------------------------------------------------------------------------------------------

				// -----------------------------------------------------------------------------------------
				// Type LMO Oxygen
				if (inventoryMasterId == 7) {
					o2Infra.setLmo_available_yn("Y");
					o2Infra.setLmo_current_stock(String.valueOf(inventoryDTO.getStock()));
					o2Infra.setLmo_storage_capacity(String.valueOf(inventoryDTO.getCapcity()));
				} else {
					o2Infra.setLmo_available_yn("N");
					o2Infra.setLmo_current_stock(String.valueOf(0));
					o2Infra.setLmo_storage_capacity(String.valueOf(0));
				}
				// --------------------------------------------------------------------------------------------

				// -----------------------------------------------------------------------------------------
				// Type PSA Oxygen
				if (inventoryMasterId == 6) {
					o2Infra.setPsa_available_yn("Y");
					o2Infra.setPsa_gen_capacity(String.valueOf(inventoryDTO.getCapcity()));
					o2Infra.setPsa_storage_capacity(String.valueOf(inventoryDTO.getCapcity()));
				} else {
					o2Infra.setPsa_available_yn("N");
					o2Infra.setPsa_gen_capacity(String.valueOf(0));
					o2Infra.setPsa_storage_capacity(String.valueOf(0));
				}
				// --------------------------------------------------------------------------------------------

			}
		} else {

			// -----------------------------------------------------------------------------------------
			// Type A Oxygen

			o2Infra.setCylinder_a_type_yn("N");
			o2Infra.setCylinder_a_type_capacity(String.valueOf(0));

			// --------------------------------------------------------------------------------------------

			// -----------------------------------------------------------------------------------------
			// Type B Oxygen

			o2Infra.setCylinder_b_type_yn("N");
			o2Infra.setCylinder_b_type_capacity(String.valueOf(0));

			// --------------------------------------------------------------------------------------------

			// -----------------------------------------------------------------------------------------
			// Type C20 Oxygen

			o2Infra.setCylinder_c20_type_yn("N");
			o2Infra.setCylinder_c20_type_capacity(String.valueOf(0));

			// --------------------------------------------------------------------------------------------

			// -----------------------------------------------------------------------------------------
			// Type C35 Oxygen

			o2Infra.setCylinder_c35_type_yn("N");
			o2Infra.setCylinder_c35_type_capacity(String.valueOf(0));

			// --------------------------------------------------------------------------------------------

			// -----------------------------------------------------------------------------------------
			// Type c45 Oxygen

			o2Infra.setCylinder_c45_type_yn("N");
			o2Infra.setCylinder_c45_type_capacity(String.valueOf(0));

			// --------------------------------------------------------------------------------------------

			// -----------------------------------------------------------------------------------------
			// Type D6 Oxygen

			o2Infra.setCylinder_d6_type_yn("N");
			o2Infra.setCylinder_d6_type_capacity(String.valueOf(0));

			// --------------------------------------------------------------------------------------------

			// -----------------------------------------------------------------------------------------
			// Type D7 Oxygen

			o2Infra.setCylinder_d7_type_yn("N");
			o2Infra.setCylinder_d7_type_capacity(String.valueOf(0));

			// --------------------------------------------------------------------------------------------

			// -----------------------------------------------------------------------------------------
			// Type LMO Oxygen

			o2Infra.setLmo_available_yn("N");
			o2Infra.setLmo_current_stock(String.valueOf(0));
			o2Infra.setLmo_storage_capacity(String.valueOf(0));

			// --------------------------------------------------------------------------------------------

			// -----------------------------------------------------------------------------------------
			// Type PSA Oxygen

			o2Infra.setPsa_available_yn("N");
			o2Infra.setPsa_gen_capacity(String.valueOf(0));
			o2Infra.setPsa_storage_capacity(String.valueOf(0));
		}
		// --------------------------------------------------------------------------------------------

		o2Infra.setPsa_has_mgp_option_yn("N");
		o2Infra.setPsa_has_refil_option_yn("N");
		oxygenInfo.setO2Infra(o2Infra);

		oxygenInfo.setFacilityid(odasFacilityId);
		oxygenInfo.setRequestId(odasReferenceNumber);

//		Optional<HospitalDTO> hospital = hospitalService.findOne(hospitalId);
//
//		HospitalDTO hospitalDTO = null;
//
//		if (hospital.isPresent()) {
//
//			hospitalDTO = hospital.get();
//			oxygenInfo.setFacilityid(hospitalDTO.getOdasFacilityId());
//			oxygenInfo.setRequestId(hospitalDTO.getReferenceNumber());
//
//		}

		return oxygenInfo;
	}

	@Async
	public void sendFacilityOxygenConsumptionToODAS(Long hospitalId, List<InventoryDTO> list,
			boolean hasUpdateOpration) {

		Optional<HospitalDTO> hospital = hospitalService.findOne(hospitalId);

		HospitalDTO hospitalDTO = null;

		if (hospital.isPresent()) {

			hospitalDTO = hospital.get();
			String odasFacilityId = hospitalDTO.getOdasFacilityId();
			String odasReferenceNumber = hospitalDTO.getReferenceNumber();

			if (odasFacilityId != null) {
				OxygenConsumption oxygenConsumption = getMappedOxygenInventoryToOxygenConsumption(odasFacilityId,
						odasReferenceNumber, list, hasUpdateOpration);
				rabitMQCommHelper.publishFacilityOxygenConsumption(oxygenConsumption);
			} else {
				// If facility id not in our database then we have update hospital first and
				// then update Inventory.
				hospitalService.sendFacilityInfoToODAS(hospitalDTO);
			}

		}

	}

	private OxygenConsumption getMappedOxygenInventoryToOxygenConsumption(String odasFacilityId,
			String odasReferenceNumber, List<InventoryDTO> list, boolean hasUpdateOpration) {

		OxygenConsumption oxygenConsumption = new OxygenConsumption();

		CunsumptionInfo consumptionInfo = new CunsumptionInfo();

		oxygenConsumption.setConsumptionInfo(consumptionInfo);


		Double total_oxygen_consumed = 0d;
		Double total_oxygen_delivered = 0d;
		Double total_oxygen_generated = 0d;

		if (list != null & list.size() > 0) {

			for (InventoryDTO inventoryDTO : list) {

				Long inventoryMasterId = inventoryDTO.getInventoryMasterId();

				if (inventoryDTO.getCapcity() != null && inventoryDTO.getStock() != null) {
					Double indivisualConsumption =  (inventoryDTO.getCapcity() - inventoryDTO.getStock());

					total_oxygen_consumed =  (total_oxygen_consumed + indivisualConsumption);
				} else {

					total_oxygen_consumed = total_oxygen_consumed + 0;
				}

				if (inventoryDTO.getCapcity() != null) {
					Double indivisualDelivered = inventoryDTO.getStock();

					total_oxygen_delivered =  (total_oxygen_delivered + indivisualDelivered);
				} else {

					total_oxygen_delivered = total_oxygen_delivered + 0;
				}


				if(inventoryMasterId==6)
				{
					//Here 6 means PSA plant generation of Oxygen
					total_oxygen_generated= inventoryDTO.getStock();
				}

			}

		}

		consumptionInfo.setTotal_oxygen_consumed(String.valueOf(total_oxygen_consumed));
		consumptionInfo.setTotal_oxygen_delivered(String.valueOf(total_oxygen_delivered));
		consumptionInfo.setTotal_oxygen_generated(String.valueOf(total_oxygen_generated));

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();

		consumptionInfo.setConsumption_for_date(dateFormat.format(now));
		consumptionInfo.setConsumption_updated_date(dateFormat.format(now));
		// --------------------------------------------------------------------------------------------

		oxygenConsumption.setFacilityid(odasFacilityId);
		oxygenConsumption.setRequestId(odasReferenceNumber);

//		Optional<HospitalDTO> hospital = hospitalService.findOne(hospitalId);
//
//		HospitalDTO hospitalDTO = null;
//
//		if (hospital.isPresent()) {
//
//			hospitalDTO = hospital.get();
//			oxygenConsumption.setFacilityid(hospitalDTO.getOdasFacilityId());
//			oxygenConsumption.setRequestId(hospitalDTO.getReferenceNumber());
//
//		}

		return oxygenConsumption;
	}
}
