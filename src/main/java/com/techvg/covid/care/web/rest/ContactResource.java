package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.ContactService;
import com.techvg.covid.care.service.HospitalService;
import com.techvg.covid.care.web.rest.errors.AlreadyExitsException;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.ContactDTO;
import com.techvg.covid.care.service.dto.HospitalCriteria;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.dto.ContactCriteria;
import com.techvg.covid.care.odas.model.FacilityInfo;
import com.techvg.covid.care.service.ContactQueryService;

import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.techvg.covid.care.domain.Contact}.
 */
@RestController
@RequestMapping("/api")
public class ContactResource {

	private final Logger log = LoggerFactory.getLogger(ContactResource.class);

	private static final String ENTITY_NAME = "contact";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ContactService contactService;

	private final ContactQueryService contactQueryService;
	
	@Autowired
	private HospitalService hospitalService;

	public ContactResource(ContactService contactService, ContactQueryService contactQueryService) {
		this.contactService = contactService;
		this.contactQueryService = contactQueryService;
	}

	/**
	 * {@code POST  /contacts} : Create a new contact.
	 *
	 * @param contactDTO the contactDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new contactDTO, or with status {@code 400 (Bad Request)} if
	 *         the contact has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/contacts")
	@PreAuthorize("hasAnyAuthority('CONTACT_EDIT')")
	public ResponseEntity<ContactDTO> createContact(@Valid @RequestBody ContactDTO contactDTO)
			throws URISyntaxException {
		log.debug("REST request to save Contact : {}", contactDTO);
		if (contactDTO.getId() != null) {
			throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
		}

//		Pageable pageable = PageRequest.of(0, 30);
//
//		if (contactDTO.getContactNo() != null) {
//
//			ContactCriteria criteria = new ContactCriteria();
//			StringFilter regStringFilter = new StringFilter();
//			regStringFilter.setEquals(contactDTO.getContactNo());
//			criteria.setContactNo(regStringFilter);
//			String messageForReponce = hasContactAlreadyExistWithcriteria(criteria, pageable);
//			if (messageForReponce != null) {
//				throw new AlreadyExitsException(messageForReponce);
//			}
//
//		}

		ContactDTO result = contactService.save(contactDTO);

		// -----------------------------------------------------------------------------------------
		// Send Hospital Data to ODAS System
		if (result!=null && result.getHospitalId() != null && result.getContactTypeId() == 3) {

			Optional<HospitalDTO> hospital = hospitalService.findOne(result.getHospitalId());
			if (hospital.isPresent()) {
				HospitalDTO hospitalDTO = hospital.get();
				FacilityInfo facilityInfo = hospitalService.sendFacilityInfoToODAS(hospitalDTO);
			}

		}
		// -----------------------------------------------------------------------------------------

		return ResponseEntity
				.created(new URI("/api/contacts/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

//	private String hasContactAlreadyExistWithcriteria(ContactCriteria criteria, Pageable pageable) {
//
//		String messageForReponce = null;
//
//		Page<ContactDTO> page = contactQueryService.findByCriteria(criteria, pageable);
//
//		List<ContactDTO> list = page.getContent();
//
//		if (list != null && list.size() > 0) {
//
//			for (ContactDTO ontactDTO : list) {
//
//				if (ontactDTO.getId() != null) {
//					if (!ontactDTO.getId().equals(ontactDTO.getId()) && ontactDTO.getContactNo() != null
//							&& ontactDTO.getContactNo().equalsIgnoreCase(ontactDTO.getContactNo())) {
//						messageForReponce = "Conatct already exist with same contact :" + ontactDTO.getContactNo();
//						break;
//					}
//				} else {
//					if (ontactDTO.getContactNo() != null
//							&& ontactDTO.getContactNo().equalsIgnoreCase(ontactDTO.getContactNo())) {
//						messageForReponce = "Conatct already exist with same contact :" + ontactDTO.getContactNo();
//						break;
//					}
//				}
//			}
//
//		}
//		// ----------------------------------------------------------------------------------------------------------
//		return messageForReponce;
//	}

	/**
	 * {@code PUT  /contacts} : Updates an existing contact.
	 *
	 * @param contactDTO the contactDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated contactDTO, or with status {@code 400 (Bad Request)} if
	 *         the contactDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the contactDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/contacts")
	@PreAuthorize("hasAnyAuthority('CONTACT_EDIT')")
	public ResponseEntity<ContactDTO> updateContact(@Valid @RequestBody ContactDTO contactDTO)
			throws URISyntaxException {
		log.debug("REST request to update Contact : {}", contactDTO);
		if (contactDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		// ----------------------------------------------------------
//		Pageable pageable = PageRequest.of(0, 30);
//
//		if (contactDTO.getContactNo() != null) {
//
//			ContactCriteria criteria = new ContactCriteria();
//			StringFilter regStringFilter = new StringFilter();
//			regStringFilter.setEquals(contactDTO.getContactNo());
//			criteria.setContactNo(regStringFilter);
//			String messageForReponce = hasContactAlreadyExistWithcriteria(criteria, pageable, contactDTO);
//			if (messageForReponce != null) {
//				throw new AlreadyExitsException(messageForReponce);
//			}
//
//		}
		// -----------------------------------------------------

		ContactDTO result = contactService.save(contactDTO);

		// -----------------------------------------------------------------------------------------
		// Send Hospital Data to ODAS System
		if (contactDTO.getHospitalId() != null && contactDTO.getContactTypeId() == 3) {

			Optional<HospitalDTO> hospital = hospitalService.findOne(contactDTO.getHospitalId());
			if (hospital.isPresent()) {
				HospitalDTO hospitalDTO = hospital.get();
				FacilityInfo facilityInfo = hospitalService.sendFacilityInfoToODAS(hospitalDTO);
			}

		}
		// -----------------------------------------------------------------------------------------

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contactDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /contacts} : get all the contacts.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of contacts in body.
	 */
	@GetMapping("/contacts")
	@PreAuthorize("hasAnyAuthority('CONTACT_LIST')")
	public ResponseEntity<List<ContactDTO>> getAllContacts(ContactCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Contacts by criteria: {}", criteria);
		Page<ContactDTO> page = contactQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /contacts/count} : count all the contacts.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/contacts/count")
	public ResponseEntity<Long> countContacts(ContactCriteria criteria) {
		log.debug("REST request to count Contacts by criteria: {}", criteria);
		return ResponseEntity.ok().body(contactQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /contacts/:id} : get the "id" contact.
	 *
	 * @param id the id of the contactDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the contactDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/contacts/{id}")
	public ResponseEntity<ContactDTO> getContact(@PathVariable Long id) {
		log.debug("REST request to get Contact : {}", id);
		Optional<ContactDTO> contactDTO = contactService.findOne(id);
		return ResponseUtil.wrapOrNotFound(contactDTO);
	}

	/**
	 * {@code DELETE  /contacts/:id} : delete the "id" contact.
	 *
	 * @param id the id of the contactDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/contacts/{id}")
	@PreAuthorize("hasAnyAuthority('CONTACT_EDIT')")
	public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
		log.debug("REST request to delete Contact : {}", id);
		contactService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
