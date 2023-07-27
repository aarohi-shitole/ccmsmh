package com.techvg.covid.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.domain.enumeration.HospitalCategory;
import com.techvg.covid.care.domain.enumeration.HospitalSubCategory;
import com.techvg.covid.care.odas.ODASMQCommHelper;
import com.techvg.covid.care.odas.model.Address;
import com.techvg.covid.care.odas.model.Facility;
import com.techvg.covid.care.odas.model.FacilityInfo;
import com.techvg.covid.care.odas.model.Nodalcontact;
import com.techvg.covid.care.repository.HospitalRepository;
import com.techvg.covid.care.service.dto.CityDTO;
import com.techvg.covid.care.service.dto.ContactCriteria;
import com.techvg.covid.care.service.dto.ContactDTO;
import com.techvg.covid.care.service.dto.DistrictDTO;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.dto.StateDTO;
import com.techvg.covid.care.service.dto.TalukaDTO;
import com.techvg.covid.care.service.mapper.HospitalMapper;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing {@link Hospital}.
 */
@Service
@Transactional
public class HospitalService {

	private final Logger log = LoggerFactory.getLogger(HospitalService.class);

	private final HospitalRepository hospitalRepository;

	private final HospitalMapper hospitalMapper;

	@Autowired
	private ContactQueryService contactQueryService;

	@Autowired
	ODASMQCommHelper rabitMQCommHelper;

	@Autowired
	private DistrictService districtService;

	@Lazy
	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private StateService stateService;

	@Autowired
	private CityService cityService;

	@Autowired
	private TalukaService talukaService;

	public HospitalService(HospitalRepository hospitalRepository, HospitalMapper hospitalMapper) {
		this.hospitalRepository = hospitalRepository;
		this.hospitalMapper = hospitalMapper;
	}

	/**
	 * Save a hospital.
	 *
	 * @param hospitalDTO the entity to save.
	 * @return the persisted entity.
	 */
	public HospitalDTO save(HospitalDTO hospitalDTO) {
		log.debug("Request to save Hospital : {}", hospitalDTO);
		Hospital hospital = hospitalMapper.toEntity(hospitalDTO);
		hospital = hospitalRepository.save(hospital);
		HospitalDTO result = hospitalMapper.toDto(hospital);
	
		return result;
	}

	/**
	 * Get all the hospitals.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<HospitalDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Hospitals");
		return hospitalRepository.findAll(pageable).map(hospitalMapper::toDto);
	}

	/**
	 * Get all the hospitals with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	public Page<HospitalDTO> findAllWithEagerRelationships(Pageable pageable) {
		return hospitalRepository.findAllWithEagerRelationships(pageable).map(hospitalMapper::toDto);
	}

	/**
	 * Get one hospital by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<HospitalDTO> findOne(Long id) {
		log.debug("Request to get Hospital : {}", id);
		return hospitalRepository.findOneWithEagerRelationships(id).map(hospitalMapper::toDto);
	}

	/**
	 * Delete the hospital by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Hospital : {}", id);
		hospitalRepository.deleteById(id);
	}

	@Async 
	public FacilityInfo sendFacilityInfoToODAS(@Valid HospitalDTO hospitalDTO) {

		// RabitMQCommHelper rabitMQCommHelper=new RabitMQCommHelper();

		FacilityInfo facilityInfo = getMappedHospitalToFacilityInfo(hospitalDTO);

		rabitMQCommHelper.publishFacilityInfo(facilityInfo);

		return facilityInfo;
	}

	public FacilityInfo getMappedHospitalToFacilityInfo(@Valid HospitalDTO hospitalDTO) {

		if (hospitalDTO.getStateId() != null) {
			Optional<StateDTO> stateDTO = stateService.findOne(hospitalDTO.getStateId());

			if (stateDTO.isPresent()) {
				
				hospitalDTO.setStateName(String.valueOf(stateDTO.get().getLgdCode()));
			}
		}

		if (hospitalDTO.getDistrictId() != null) {
			Optional<DistrictDTO> districtDTO = districtService.findOne(hospitalDTO.getDistrictId());
			if (districtDTO.isPresent()) {
				hospitalDTO.setDistrictName(String.valueOf(districtDTO.get().getLgdCode()));

			}
		}

		if (hospitalDTO.getTalukaId() != null) {
			Optional<TalukaDTO> talukaDTO = talukaService.findOne(hospitalDTO.getTalukaId());
			if (talukaDTO.isPresent()) {
				hospitalDTO.setTalukaName(String.valueOf(talukaDTO.get().getLgdCode()));

			}
		}

		if (hospitalDTO.getCityId() != null) {
			Optional<CityDTO> cityDTO = cityService.findOne(hospitalDTO.getCityId());
			if (cityDTO.isPresent()) {
				hospitalDTO.setCityName(String.valueOf(cityDTO.get().getLgdCode()));

			}
		}

		FacilityInfo facilityInfo = new FacilityInfo();

		facilityInfo.setCCMSHospitalId(String.valueOf(hospitalDTO.getId()));
		// ----------------------------------------------------
		// Mapping for Hospital to Facility here
		Facility facility = new Facility();
		facility.setId(hospitalDTO.getOdasFacilityId() != null && !hospitalDTO.getOdasFacilityId().isEmpty() ? String.valueOf(hospitalDTO.getOdasFacilityId()) : "");
		facility.setName(hospitalDTO.getName() != null && !hospitalDTO.getName().isEmpty()  ? hospitalDTO.getName() : "");
		facility.setFacilitytype("8");// Other Converted Facility always keep same
		facility.setFacilitysubtype(hospitalDTO.getSubCategory().getValue());
		facility.setLangitude(hospitalDTO.getLongitude() != null && !hospitalDTO.getLongitude().isEmpty() ? hospitalDTO.getLongitude() : "00.00000");
		facility.setLatitude(hospitalDTO.getLatitude() != null && !hospitalDTO.getLatitude().isEmpty() ? hospitalDTO.getLatitude() : "00.000000");
		String ownershipType = getOwnershipType(hospitalDTO.getCategory());
		facility.setOwnershiptype(ownershipType);
		String ownershipSubType = getOwnershipSubType(hospitalDTO.getCategory(), hospitalDTO.getSubCategory());
		facility.setOwnershipsubtype(ownershipSubType);

		// ----Address----------------------------------------
		Address address = new Address();
		address.setAddressLine1(hospitalDTO.getAddress1() != null && !hospitalDTO.getAddress1().isEmpty() ? hospitalDTO.getAddress1() : "");
		address.setAddressLine2(hospitalDTO.getAddress2() != null && !hospitalDTO.getAddress2().isEmpty() ? hospitalDTO.getAddress2() : "");
		address.setCity(hospitalDTO.getCityName());
		address.setDistrict(hospitalDTO.getDistrictName());
		address.setSubdistrict(hospitalDTO.getTalukaName());
		address.setPincode(hospitalDTO.getPinCode());
		address.setState(hospitalDTO.getStateName());
		facility.setAddress(address);
		// ----Address----------------------------------------

		// ------Nodal Contact----------------------------------------
		List<Nodalcontact> nodalcontacts = new ArrayList<Nodalcontact>();

		Pageable pageable = PageRequest.of(0, 30);

		if (hospitalDTO.getId() != null) {

			ContactCriteria criteria = new ContactCriteria();
			LongFilter regStringFilter = new LongFilter();
			regStringFilter.setEquals(hospitalDTO.getId());
			criteria.setHospitalId(regStringFilter);

			LongFilter longFilter = new LongFilter();
			longFilter.equals(3);// 3 is there nodel officer
			criteria.setContactTypeId(longFilter);

			Page<ContactDTO> page = contactQueryService.findByCriteria(criteria, pageable);

			List<ContactDTO> list = page.getContent();

			if (list != null && list.size() > 0) {

				for (ContactDTO ontactDTO : list) {

					Nodalcontact nodalcontact = new Nodalcontact();
					nodalcontact.setFirstname(ontactDTO.getName() != null && !hospitalDTO.getName().isEmpty() ? ontactDTO.getName() : "NA");
					nodalcontact.setMiddlename("");
					nodalcontact.setLastname("");
					nodalcontact.setCountrycode("+91");
					nodalcontact.setDesignation("Facility Manager");
					nodalcontact.setEmail(ontactDTO.getEmail() != null && !ontactDTO.getEmail().isEmpty() ? ontactDTO.getEmail() : "na@gmail.com");
					nodalcontact.setSalutation("Dr.");
					nodalcontact.setMobilenumber(
							ontactDTO.getContactNo() != null && !ontactDTO.getContactNo().isEmpty() ? ontactDTO.getContactNo() : "0000000000");
					nodalcontacts.add(nodalcontact);
				}

			} else {
				// Send it with default values
				Nodalcontact nodalcontact = new Nodalcontact();
				nodalcontact.setFirstname("NA");
				nodalcontact.setMiddlename("NA");
				nodalcontact.setLastname("NA");
				nodalcontact.setCountrycode("+91");
				nodalcontact.setDesignation("Facility Manager");
				nodalcontact.setEmail("na@gmail.com");
				nodalcontact.setSalutation("Dr.");
				nodalcontact.setMobilenumber("0000000000");
				nodalcontacts.add(nodalcontact);
			}
			facilityInfo.setNodalcontacts(nodalcontacts);

			// ------Nodal Contact----------------------------------------
		} else {
			// Send it with default values
			Nodalcontact nodalcontact = new Nodalcontact();
			nodalcontact.setFirstname("NA");
			nodalcontact.setMiddlename("NA");
			nodalcontact.setLastname("NA");
			nodalcontact.setCountrycode("+91");
			nodalcontact.setDesignation("Facility Manager");
			nodalcontact.setEmail("na@gmail.com");
			nodalcontact.setSalutation("Dr.");
			nodalcontact.setMobilenumber("0000000000");
			nodalcontacts.add(nodalcontact);
		}

		facilityInfo.setNodalcontacts(nodalcontacts);

		// facilityInfo.setTimestamp(new Date());
		// ------------------------------------------------------
		facilityInfo.setFacility(facility);

		return facilityInfo;
	}

	private String getOwnershipSubType(HospitalCategory category, HospitalSubCategory subCategory) {

		String ownershipSubType = "S";

		if (category.equals(HospitalCategory.CENTRAL)) {
			ownershipSubType = "C";
		} else if (category.equals(HospitalCategory.GOVT)) {
			ownershipSubType = "S";
		} else if (category.equals(HospitalCategory.PRIVATE)) {

			ownershipSubType = "NP";
		}
		return ownershipSubType;
	}

	private String getOwnershipType(HospitalCategory category) {
		String ownershipType = "P";

		if (category.equals(HospitalCategory.GOVT) || category.equals(HospitalCategory.CENTRAL)) {

			ownershipType = "G";

		} else if (category.equals(HospitalCategory.PRIVATE)) {
			ownershipType = "P";
		}
		return ownershipType;
	}

//	public Optional<Object> findByRegistration_no(String registration_no) {
//		log.debug("Request to get Hospital : {}", registration_no);
//        return hospitalRepository.findByRegistration_No(registration_no).map(hospitalMapper::toDto);
//	}
}
