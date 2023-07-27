package com.techvg.covid.care.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.techvg.covid.care.odas.model.AddCCMSHospitalIDReq;
import com.techvg.covid.care.odas.model.FacilityInfo;
import com.techvg.covid.care.service.BedInventoryQueryService;
import com.techvg.covid.care.service.BedInventoryService;
import com.techvg.covid.care.service.BedTypeQueryService;
import com.techvg.covid.care.service.HospitalQueryService;
import com.techvg.covid.care.service.HospitalService;
import com.techvg.covid.care.service.InventoryQueryService;
import com.techvg.covid.care.service.InventoryService;
import com.techvg.covid.care.service.dto.BedInventoryCriteria;
import com.techvg.covid.care.service.dto.BedInventoryDTO;
import com.techvg.covid.care.service.dto.BedTypeDTO;
import com.techvg.covid.care.service.dto.HospitalCriteria;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.dto.InventoryCriteria;
import com.techvg.covid.care.service.dto.InventoryDTO;
import com.techvg.covid.care.web.rest.errors.AlreadyExitsException;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.web.rest.errors.CustomMessageException;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.covid.care.domain.Hospital}.
 */
@RestController
@RequestMapping("/api")
public class HospitalResource {

	private final Logger log = LoggerFactory.getLogger(HospitalResource.class);

	private static final String ENTITY_NAME = "hospital";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private BedTypeQueryService bedTypeQueryService;

	@Autowired
	private BedInventoryQueryService bedInventoryQueryService;

	@Autowired
	private BedInventoryService bedInventoryService;

	private final HospitalService hospitalService;

	private final HospitalQueryService hospitalQueryService;
	
	private InventoryQueryService inventoryQueryService;
	
	private InventoryService inventoryService;

	@Value("${ODAS.shouldSendData}")
	private boolean shouldSendDataToODAS;

	public HospitalResource(HospitalService hospitalService, HospitalQueryService hospitalQueryService) {
		this.hospitalService = hospitalService;
		this.hospitalQueryService = hospitalQueryService;
	}

	/**
	 * {@code POST  /hospitals} : Create a new hospital.
	 *
	 * @param hospitalDTO the hospitalDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new hospitalDTO, or with status {@code 400 (Bad Request)} if
	 *         the hospital has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/hospitals")
	@PreAuthorize("hasAuthority('HOSPITAL_EDIT')")
	public ResponseEntity<HospitalDTO> createHospital(@Valid @RequestBody HospitalDTO hospitalDTO)
			throws URISyntaxException {
		log.debug("REST request to save Hospital : {}", hospitalDTO);
		if (hospitalDTO.getId() != null) {
			throw new BadRequestAlertException("A new hospital cannot already have an ID", ENTITY_NAME, "idexists");
		}

		// ------------------------------------------------------------------------------------------------------
		// Added check point for Hospital already with registration id, Email and
		// Contact No
		Pageable pageable = PageRequest.of(0, 30);

		if (hospitalDTO.getRegistrationNo() != null && !hospitalDTO.getRegistrationNo().isEmpty()) {

			HospitalCriteria criteria = new HospitalCriteria();
			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(hospitalDTO.getRegistrationNo());
			criteria.setRegistrationNo(regStringFilter);
			String messageForReponce = hasHospitalAlreadyExistWithcriteria(criteria, pageable, hospitalDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		if (hospitalDTO.getEmail() != null && !hospitalDTO.getEmail().isEmpty()) {

			HospitalCriteria criteria = new HospitalCriteria();
			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(hospitalDTO.getEmail());
			criteria.setEmail(regStringFilter);
			String messageForReponce = hasHospitalAlreadyExistWithcriteria(criteria, pageable, hospitalDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		if (hospitalDTO.getContactNo() != null && !hospitalDTO.getContactNo().isEmpty()) {

			HospitalCriteria criteria = new HospitalCriteria();
			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(hospitalDTO.getContactNo());
			criteria.setContactNo(regStringFilter);
			String messageForReponce = hasHospitalAlreadyExistWithcriteria(criteria, pageable, hospitalDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		// ----------------------------------------------------------------------------------------------------------

		HospitalDTO result = hospitalService.save(hospitalDTO);

		// ----------------------------------------------------------------------------------------------------------

		if (shouldSendDataToODAS) {
			// Send Hospital Data to ODAS System
			FacilityInfo facilityInfo = hospitalService.sendFacilityInfoToODAS(result);
		}

		// -----------------------------------------------------------------------------------------

		// -----------------------------------------------------------------------------------------
		// To add bed inventory with value 0 while creating new hospital

		Page<BedTypeDTO> page = bedTypeQueryService.findByCriteria(null, pageable);
		if (page != null) {

			List<BedTypeDTO> bedTypeList = page.getContent();

			if (bedTypeList != null) {
				for (BedTypeDTO bedTypeDto : bedTypeList) {

					BedInventoryDTO bedInventoryDTO = new BedInventoryDTO();
					bedInventoryDTO.setBedTypeId(bedTypeDto.getId());
					bedInventoryDTO.setBedTypeName(bedTypeDto.getName());

					bedInventoryDTO.setHospitalId(result.getId());
					bedInventoryDTO.setHospitalName(result.getName());
					Long bedCount = (long) 0;
					bedInventoryDTO.setBedCount(bedCount);
					bedInventoryDTO.setOccupied(bedCount);
					bedInventoryDTO.setOnConcentrators(bedCount);
					bedInventoryDTO.setOnCylinder(bedCount);
					bedInventoryDTO.setOnLMO(bedCount);

					BedInventoryDTO resultBed = bedInventoryService.save(bedInventoryDTO);

					if (resultBed != null) {
//						System.out.println("Bed Inventry created successfully!!");
					}
				}

			}
		}

		return ResponseEntity
				.created(new URI("/api/hospitals/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
		// }
	}

	private String hasHospitalAlreadyExistWithcriteria(HospitalCriteria criteria, Pageable pageable,
			HospitalDTO hospitalDTO) {

		String messageForReponce = null;

		Page<HospitalDTO> pageHospital = hospitalQueryService.findByCriteria(criteria, pageable);

		List<HospitalDTO> list = pageHospital.getContent();

		if (list != null && list.size() > 0) {

			for (HospitalDTO dtoHospitalDTO : list) {

				if (hospitalDTO.getId() != null) {
					// This for in update case
					if (!hospitalDTO.getId().equals(dtoHospitalDTO.getId())
							&& dtoHospitalDTO.getRegistrationNo() != null
							&& dtoHospitalDTO.getRegistrationNo().equalsIgnoreCase(hospitalDTO.getRegistrationNo())) {
						messageForReponce = "Hospital already exist with same registration no :"
								+ hospitalDTO.getRegistrationNo();
						break;
					} else if (!hospitalDTO.getId().equals(dtoHospitalDTO.getId()) && dtoHospitalDTO.getEmail() != null
							&& dtoHospitalDTO.getEmail().equalsIgnoreCase(hospitalDTO.getEmail())) {
						messageForReponce = "Hospital already exist with same email :" + hospitalDTO.getEmail();
						break;
					} else if (!hospitalDTO.getId().equals(dtoHospitalDTO.getId())
							&& dtoHospitalDTO.getContactNo() != null
							&& dtoHospitalDTO.getContactNo().equalsIgnoreCase(hospitalDTO.getContactNo())) {
						messageForReponce = "Hospital already exist with same contact :" + hospitalDTO.getContactNo();
						break;
					}
				} else {
					if (dtoHospitalDTO.getRegistrationNo() != null
							&& dtoHospitalDTO.getRegistrationNo().equalsIgnoreCase(hospitalDTO.getRegistrationNo())) {
						messageForReponce = "Hospital already exist with same registration no :"
								+ hospitalDTO.getRegistrationNo();
						break;
					} else if (dtoHospitalDTO.getEmail() != null
							&& dtoHospitalDTO.getEmail().equalsIgnoreCase(hospitalDTO.getEmail())) {
						messageForReponce = "Hospital already exist with same email :" + hospitalDTO.getEmail();
						break;
					} else if (dtoHospitalDTO.getContactNo() != null
							&& dtoHospitalDTO.getContactNo().equalsIgnoreCase(hospitalDTO.getContactNo())) {
						messageForReponce = "Hospital already exist with same contact :" + hospitalDTO.getContactNo();
						break;
					}
				}
			}

		}
		// ----------------------------------------------------------------------------------------------------------
		return messageForReponce;
	}

	/**
	 * {@code PUT  /hospitals} : Updates an existing hospital.
	 *
	 * @param hospitalDTO the hospitalDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated hospitalDTO, or with status {@code 400 (Bad Request)} if
	 *         the hospitalDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the hospitalDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/hospitals")
	@PreAuthorize("hasAuthority('HOSPITAL_EDIT')")
	public ResponseEntity<HospitalDTO> updateHospital(@Valid @RequestBody HospitalDTO hospitalDTO)
			throws URISyntaxException {
		log.debug("REST request to update Hospital : {}", hospitalDTO);
		if (hospitalDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		// ------------------------------------------------------------------------------------------------------
		// Added check point for Hospital already with registration id, Email and
		// Contact No
		Pageable pageable = PageRequest.of(0, 30);

		if (hospitalDTO.getRegistrationNo() != null && !hospitalDTO.getRegistrationNo().isEmpty()) {

			HospitalCriteria criteria = new HospitalCriteria();

			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(hospitalDTO.getRegistrationNo());
			criteria.setRegistrationNo(regStringFilter);
			String messageForReponce = hasHospitalAlreadyExistWithcriteria(criteria, pageable, hospitalDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		if (hospitalDTO.getEmail() != null && !hospitalDTO.getEmail().isEmpty()) {
			HospitalCriteria criteria = new HospitalCriteria();

			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(hospitalDTO.getEmail());
			criteria.setEmail(regStringFilter);
			String messageForReponce = hasHospitalAlreadyExistWithcriteria(criteria, pageable, hospitalDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		if (hospitalDTO.getContactNo() != null && !hospitalDTO.getContactNo().isEmpty()) {
			HospitalCriteria criteria = new HospitalCriteria();

			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(hospitalDTO.getContactNo());
			criteria.setContactNo(regStringFilter);
			String messageForReponce = hasHospitalAlreadyExistWithcriteria(criteria, pageable, hospitalDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		// ----------------------------------------------------------------------------------------------------------

		HospitalDTO result = hospitalService.save(hospitalDTO);

		// ----------------------------------------------------------------------------------------------------------

		// Send Hospital Data to ODAS System
		if (shouldSendDataToODAS) {
			FacilityInfo facilityInfo = hospitalService.sendFacilityInfoToODAS(result);
		}

		// -----------------------------------------------------------------------------------------

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hospitalDTO.getId().toString()))
				.body(result);
	}

	@PutMapping("/hospitals/odasfacilityid")
	public ResponseEntity<HospitalDTO> updateHospitalODASFacility(
			@Valid @RequestBody AddCCMSHospitalIDReq falInfoResponse) throws URISyntaxException {
		log.debug("REST request to update Hospital : {}", falInfoResponse);
		if (falInfoResponse.getCcmsHospitalId() == null) {
			throw new BadRequestAlertException("Invalid hospitalId", ENTITY_NAME, "idnull");
		}

		log.info("Received information from ODAS::::::" + falInfoResponse);

		long localHospitaId = falInfoResponse.getCcmsHospitalId();
		Optional<HospitalDTO> hospital = hospitalService.findOne(localHospitaId);

		HospitalDTO hospitalDTO = null;

		if (hospital.isPresent()) {
			hospitalDTO = hospital.get();
			hospitalDTO.setOdasFacilityId(falInfoResponse.getOdasFacilityId());
			hospitalDTO.setReferenceNumber(falInfoResponse.getReferencenumber());
		}

		HospitalDTO result = hospitalService.save(hospitalDTO);

		// --------------------------------------------------------------------------------------
		// After update the send odas facility id in our database
		// update the bes information to odas

		Pageable pageable = PageRequest.of(0, 30);
		BedInventoryCriteria criteria = new BedInventoryCriteria();
		LongFilter longFilter = new LongFilter();
		longFilter.setEquals(result.getId());
		criteria.setHospitalId(longFilter);

		Page<BedInventoryDTO> page = bedInventoryQueryService.findByCriteria(criteria, pageable);

		List<BedInventoryDTO> list = page.getContent();
		if (list != null && list.size() > 0) {

			bedInventoryService.sendFacilityBedsInfoToODAS(result.getId(), list, false);

		}
		// -----------------------------------------------------------------------------------------------
		
		// After update the send odas facility id in our database
		// update the Inventory information to odas

		Pageable pageableOdas = PageRequest.of(0, 30);

		LongFilter longFilterODAS = new LongFilter();
		InventoryCriteria criteriaODAS = new InventoryCriteria();

		if (result.getHospitalId() != null) {

			longFilterODAS.setEquals(result.getHospitalId());
			criteriaODAS.setHospitalId(longFilter);

			Page<InventoryDTO> pageInventory = inventoryQueryService.findByCriteria(criteriaODAS, pageableOdas);
			List<InventoryDTO> listInventory = pageInventory.getContent();

			if (result.getHospitalId() != null) {

				inventoryService.sendFacilityOxygenInfoToODAS(result.getHospitalId(), listInventory, false);
			}

		}
		//--------------------------------------------------------------------------------------------------------------

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hospitalDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /hospitals} : get all the hospitals.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of hospitals in body.
	 */
	@GetMapping("/hospitals")
	public ResponseEntity<List<HospitalDTO>> getAllHospitals(HospitalCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Hospitals by criteria: {}", criteria);
		Page<HospitalDTO> page = hospitalQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /hospitals/count} : count all the hospitals.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/hospitals/count")
	public ResponseEntity<Long> countHospitals(HospitalCriteria criteria) {
		log.debug("REST request to count Hospitals by criteria: {}", criteria);
		return ResponseEntity.ok().body(hospitalQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /hospitals/:id} : get the "id" hospital.
	 *
	 * @param id the id of the hospitalDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the hospitalDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/hospitals/{id}")
	//@PreAuthorize("hasAnyAuthority('HOSPITAL_VIEW','HOSPITAL_USER')")
	public ResponseEntity<HospitalDTO> getHospital(@PathVariable Long id) {
		log.debug("REST request to get Hospital : {}", id);
		Optional<HospitalDTO> hospitalDTO = hospitalService.findOne(id);
		return ResponseUtil.wrapOrNotFound(hospitalDTO);
	}

	/**
	 * {@code DELETE  /hospitals/:id} : delete the "id" hospital.
	 *
	 * @param id the id of the hospitalDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/hospitals/{id}")
	@PreAuthorize("hasAuthority('HOSPITAL_EDIT')")
	public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
		log.debug("REST request to delete Hospital : {}", id);

		throw new CustomMessageException("You can't prform this operation!!");

//		hospitalService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
	}

}
