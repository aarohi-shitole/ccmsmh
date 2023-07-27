package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.BedInventory;
import com.techvg.covid.care.odas.ODASMQCommHelper;
import com.techvg.covid.care.odas.model.BedInfo;
import com.techvg.covid.care.odas.model.Beds;
import com.techvg.covid.care.odas.model.BedsOccupancy;
import com.techvg.covid.care.repository.BedInventoryRepository;
import com.techvg.covid.care.service.dto.BedInventoryCriteria;
import com.techvg.covid.care.service.dto.BedInventoryDTO;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.mapper.BedInventoryMapper;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BedInventory}.
 */
@Service
@Transactional
public class BedInventoryService {

	private final Logger log = LoggerFactory.getLogger(BedInventoryService.class);

	private final BedInventoryRepository bedInventoryRepository;

	private final BedInventoryMapper bedInventoryMapper;

	@Value("${ODAS.shouldSendData}")
	private boolean shouldSendDataToODAS;

	@Lazy
	@Autowired
	private HospitalService hospitalService;

	@Lazy
	@Autowired
	private BedInventoryQueryService bedInventoryQueryService;

	@Lazy
	@Autowired
	private BedInventoryService bedInventoryService;

	@Autowired
	ODASMQCommHelper rabitMQCommHelper;

	public BedInventoryService(BedInventoryRepository bedInventoryRepository, BedInventoryMapper bedInventoryMapper) {
		this.bedInventoryRepository = bedInventoryRepository;
		this.bedInventoryMapper = bedInventoryMapper;
	}

	/**
	 * Save a bedInventory.
	 *
	 * @param bedInventoryDTO the entity to save.
	 * @return the persisted entity.
	 */
	public BedInventoryDTO save(BedInventoryDTO bedInventoryDTO) {
		log.debug("Request to save BedInventory : {}", bedInventoryDTO);
		BedInventory bedInventory = bedInventoryMapper.toEntity(bedInventoryDTO);
		bedInventory = bedInventoryRepository.save(bedInventory);
		BedInventoryDTO result = bedInventoryMapper.toDto(bedInventory);


		return result;
	}

	/**
	 * Get all the bedInventories.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<BedInventoryDTO> findAll(Pageable pageable) {
		log.debug("Request to get all BedInventories");
		return bedInventoryRepository.findAll(pageable).map(bedInventoryMapper::toDto);
	}

	/**
	 * Get one bedInventory by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<BedInventoryDTO> findOne(Long id) {
		log.debug("Request to get BedInventory : {}", id);
		return bedInventoryRepository.findById(id).map(bedInventoryMapper::toDto);
	}

	/**
	 * Get one bedInventory by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<BedInventoryDTO> findBedInventoryOnHospitalIdAndBedTypeId(Long hospitalId, Long bedTypeId) {
		log.debug("Request to get findBedInventoryOnHospitalIdAndBedTypeId : {}", hospitalId);
		return bedInventoryRepository.findByHospitalIdAndBedTypeId(hospitalId, bedTypeId)
				.map(bedInventoryMapper::toDto);
	}

	/**
	 * Delete the bedInventory by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete BedInventory : {}", id);
		bedInventoryRepository.deleteById(id);
	}

	@Async 
	synchronized public void sendFacilityBedsInfoToODAS(Long hospitalId, List<BedInventoryDTO> list, boolean hasUpdateOpration) {
		
		Optional<HospitalDTO> hospital = hospitalService.findOne(hospitalId);

		HospitalDTO hospitalDTO = null;

		if (hospital.isPresent()) {

			hospitalDTO = hospital.get();
			String odasFacilityId = hospitalDTO.getOdasFacilityId();
			String odasReferanceNumber = hospitalDTO.getReferenceNumber();

			if (odasFacilityId != null) {
				BedInfo facilityBedInfo = getMappedBedInventoryToBedInfo(odasFacilityId, odasReferanceNumber,list, hasUpdateOpration);
				rabitMQCommHelper.publishFacilityBedsInfo(facilityBedInfo);
			}else {
				// In this case we have send hospital Information first to ODAS
				hospitalService.sendFacilityInfoToODAS(hospitalDTO);
			}
 

		}
	}

	private BedInfo getMappedBedInventoryToBedInfo(String odasFacilityId,String odasReferanceNumber, List<BedInventoryDTO> list,
			boolean hasUpdateOpration) {

		BedInfo bedInfo = new BedInfo();

		Beds beds = new Beds();

		if (list != null) {

			BedInventoryDTO bedInventoryICU = null;
			BedInventoryDTO bedInventoryVENT = null;

			for (BedInventoryDTO bedInventoryDTO : list) {

				String bedTypeId = String.valueOf(bedInventoryDTO.getBedTypeId());

				switch (bedTypeId) {

				case "1":

					Long availableISOBed = bedInventoryDTO.getBedCount() - bedInventoryDTO.getOccupied();
					// beds.setNo_gen_beds(String.valueOf(availableISOBed));
					break;

				case "2":
					Long availableO2Bed = bedInventoryDTO.getBedCount();
					beds.setNo_gen_beds(String.valueOf(availableO2Bed));
					break;

				case "3":
					bedInventoryICU = bedInventoryDTO;
					Long availableICUBed = bedInventoryDTO.getBedCount();
					beds.setNo_icu_beds(String.valueOf(availableICUBed));
					break;

				case "4":
					bedInventoryVENT = bedInventoryDTO;
					Long availableVENTBed = bedInventoryDTO.getBedCount();
					beds.setNo_vent_beds(String.valueOf(availableVENTBed));

					break;

				}
			}

		} else {
			beds.setNo_gen_beds(String.valueOf(0));

			beds.setNo_o2_concentrators(String.valueOf(0));

			beds.setNo_icu_beds(String.valueOf(0));

			beds.setNo_vent_beds(String.valueOf(0));
		}

		beds.setNo_hdu_beds("0");
		beds.setNo_o2_concentrators("0");
		bedInfo.setBeds(beds);
		
		bedInfo.setFacilityid(odasFacilityId);
		bedInfo.setRequestId(odasReferanceNumber);

//		Optional<HospitalDTO> hospital = hospitalService.findOne(hospitalID);
//
//		HospitalDTO hospitalDTO = null;
//
//		if (hospital.isPresent()) {
//
//			hospitalDTO = hospital.get();
//			bedInfo.setFacilityid(hospitalDTO.getOdasFacilityId());
//			bedInfo.setRequestId(hospitalDTO.getReferenceNumber());
//
//		}

		return bedInfo;
	}

    @Async 
	public void sendFacilityBedsOccupancyToODAS(Long hospitalId, List<BedInventoryDTO> list,
			boolean hasUpdateOpration) {

		Optional<HospitalDTO> hospital = hospitalService.findOne(hospitalId);

		HospitalDTO hospitalDTO = null;

		if (hospital.isPresent()) {

			hospitalDTO = hospital.get();
			String odasFacilityId = hospitalDTO.getOdasFacilityId();
			String odasReferanceNumber = hospitalDTO.getReferenceNumber();

			if (odasFacilityId != null) {
				BedsOccupancy facilityBedOccupancy = getMappedBedInventoryToBedOccupancy(odasFacilityId,
						odasReferanceNumber, list, hasUpdateOpration);
				rabitMQCommHelper.publishFacilityBedsOccupancy(facilityBedOccupancy);
			} else {
				// In this case we have send hospital Information first to ODAS
				hospitalService.sendFacilityInfoToODAS(hospitalDTO);
			}

		}

	}

	private BedsOccupancy getMappedBedInventoryToBedOccupancy(String odasFacilityId, String odasReferanceNumber,
			List<BedInventoryDTO> list, boolean hasUpdateOpration) {

		BedsOccupancy bedsOccupancy = new BedsOccupancy();

		Beds beds = new Beds();

		if (list != null && list.size() > 0) {

			BedInventoryDTO bedInventoryICU = null;
			BedInventoryDTO bedInventoryVENT = null;

			for (BedInventoryDTO bedInventoryDTO : list) {

				String bedTypeId = String.valueOf(bedInventoryDTO.getBedTypeId());

				switch (bedTypeId) {

				case "1":

					Long availableISOBed = bedInventoryDTO.getBedCount() - bedInventoryDTO.getOccupied();
					// beds.setNo_gen_beds(String.valueOf(availableISOBed));
					break;

				case "2":
					Long availableO2Bed = bedInventoryDTO.getBedCount() - bedInventoryDTO.getOccupied();
					beds.setNo_gen_beds(String.valueOf(availableO2Bed));
					break;

				case "3":
					bedInventoryICU = bedInventoryDTO;
					Long availableICUBed = bedInventoryDTO.getBedCount() - bedInventoryDTO.getOccupied();
					beds.setNo_icu_beds(String.valueOf(availableICUBed));
					break;

				case "4":
					bedInventoryVENT = bedInventoryDTO;
					Long availableVENTBed = bedInventoryDTO.getBedCount() - bedInventoryDTO.getOccupied();
					beds.setNo_vent_beds(String.valueOf(availableVENTBed));

					break;

				}
			}

			try {

				Long no_vent_beds = bedInventoryVENT.getBedCount();
				Long no_icu_beds = bedInventoryICU.getBedCount();

				if (no_vent_beds > no_icu_beds) {
					beds.setNo_vent_beds(String.valueOf(no_icu_beds));
				}
			} catch (Exception e) {
				log.info("While update bed to ODAS " + e.toString());
			}
		} else {

			beds.setNo_gen_beds(String.valueOf(0));

			beds.setNo_o2_concentrators(String.valueOf(0));

			beds.setNo_icu_beds(String.valueOf(0));

			beds.setNo_vent_beds(String.valueOf(0));
		}

		beds.setNo_hdu_beds("0");
		beds.setNo_o2_concentrators(String.valueOf(0));
		bedsOccupancy.setBeds(beds);

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();

		bedsOccupancy.setOccupancyDate(dateFormat.format(now));

		bedsOccupancy.setFacilityid(odasFacilityId);
		bedsOccupancy.setRequestId(odasReferanceNumber);

//		Optional<HospitalDTO> hospital = hospitalService.findOne(hospitalId);
//
//		HospitalDTO hospitalDTO = null;
//
//		if (hospital.isPresent()) {
//
//			hospitalDTO = hospital.get();
//			bedsOccupancy.setFacilityid(hospitalDTO.getOdasFacilityId());
//			bedsOccupancy.setRequestId(hospitalDTO.getReferenceNumber());
//
//		}

		return bedsOccupancy;
	}

}
