package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.SupplierService;
import com.techvg.covid.care.web.rest.errors.AlreadyExitsException;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.web.rest.errors.CustomMessageException;
import com.techvg.covid.care.service.dto.SupplierDTO;
import com.techvg.covid.care.service.dto.HospitalCriteria;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.dto.InventoryDTO;
import com.techvg.covid.care.service.dto.SupplierCriteria;
import com.techvg.covid.care.domain.enumeration.SupplierType;
import com.techvg.covid.care.security.AuthoritiesConstants;
import com.techvg.covid.care.service.InventoryService;
import com.techvg.covid.care.service.SupplierQueryService;

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
 * REST controller for managing {@link com.techvg.covid.care.domain.Supplier}.
 */
@RestController
@RequestMapping("/api")
public class SupplierResource {

	private final Logger log = LoggerFactory.getLogger(SupplierResource.class);

	private static final String ENTITY_NAME = "supplier";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final SupplierService supplierService;

	@Autowired
	private InventoryService inventoryService;

	private final SupplierQueryService supplierQueryService;

	public SupplierResource(SupplierService supplierService, SupplierQueryService supplierQueryService) {
		this.supplierService = supplierService;
		this.supplierQueryService = supplierQueryService;
	}

	/**
	 * {@code POST  /suppliers} : Create a new supplier.
	 *
	 * @param supplierDTO the supplierDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new supplierDTO, or with status {@code 400 (Bad Request)} if
	 *         the supplier has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/suppliers")
	@PreAuthorize("hasAuthority('SUPLLIER_EDIT')")
	public ResponseEntity<SupplierDTO> createSupplier(@Valid @RequestBody SupplierDTO supplierDTO)
			throws URISyntaxException {
		log.debug("REST request to save Supplier : {}", supplierDTO);
		if (supplierDTO.getId() != null) {
			throw new BadRequestAlertException("A new supplier cannot already have an ID", ENTITY_NAME, "idexists");
		}

		// ------------------------------------------------------------------------------------------------------
		// Added check point for Supplier already with registration id, Email and
		// Contact No
		Pageable pageable = PageRequest.of(0, 30);

		if (supplierDTO.getRegistrationNo() != null && !supplierDTO.getRegistrationNo().isEmpty()) {

			SupplierCriteria criteria = new SupplierCriteria();
			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(supplierDTO.getRegistrationNo());
			criteria.setRegistrationNo(regStringFilter);
			String messageForReponce = hasSupplierAlreadyExistWithcriteria(criteria, pageable, supplierDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		if (supplierDTO.getEmail() != null && !supplierDTO.getEmail().isEmpty()) {

			SupplierCriteria criteria = new SupplierCriteria();
			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(supplierDTO.getEmail());
			criteria.setEmail(regStringFilter);
			String messageForReponce = hasSupplierAlreadyExistWithcriteria(criteria, pageable, supplierDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		if (supplierDTO.getContactNo() != null && !supplierDTO.getContactNo().isEmpty()) {

			SupplierCriteria criteria = new SupplierCriteria();
			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(supplierDTO.getContactNo());
			criteria.setContactNo(regStringFilter);
			String messageForReponce = hasSupplierAlreadyExistWithcriteria(criteria, pageable, supplierDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		// ----------------------------------------------------------------------------------------------------------

		SupplierDTO result = supplierService.save(supplierDTO);

		// ----------------------------------------------------------------------------------------------
		// Type if Manufacture LMO is by default zero

		SupplierType suppliertype = supplierDTO.getSupplierType();

		if (suppliertype != null && suppliertype.name().equals("MANUFACTURER") || suppliertype.name().equals("INDUSTRY")) {

			InventoryDTO inventoryDTO = new InventoryDTO();
			inventoryDTO.setSupplierId(result.getId());
			inventoryDTO.setSupplierName(result.getName());

			Double capcity = (double) 0;
			inventoryDTO.setCapcity(capcity);
			inventoryDTO.setStock(capcity);
			inventoryDTO.setInstalledCapcity(capcity);
			Long inventoryMasterId = (long) 7;// Here 7 is represent for LMO Storage in Inventory_master table
			inventoryDTO.setInventoryMasterId(inventoryMasterId);

			InventoryDTO resultInventoryDto = inventoryService.save(inventoryDTO);

			if (resultInventoryDto == null) {
				log.error("Error while create default inventory while creating manufacturee");
			}

		} else if (suppliertype != null && suppliertype.name().equals("REFILLER")) {

			InventoryDTO inventoryDTO = new InventoryDTO();
			inventoryDTO.setSupplierId(result.getId());
			inventoryDTO.setSupplierName(result.getName());

			Double capcity = (double) 0;
			inventoryDTO.setCapcity(capcity);
			inventoryDTO.setStock(capcity);
			inventoryDTO.setInstalledCapcity(capcity);
			Long inventoryMasterId = (long) 6;// Here 6 is represent for PSA Plant in Inventory_master table
			inventoryDTO.setInventoryMasterId(inventoryMasterId);

			InventoryDTO resultInventoryDto = inventoryService.save(inventoryDTO);
			if (resultInventoryDto == null) {
				log.error("Error while create default inventory while creating manufacturee");
			}

			// --------------------------------------------------------------------------------------------
			inventoryMasterId = (long) 8;// Here 8 is represent for Dura Cylinders in Inventory_master table
			inventoryDTO.setInventoryMasterId(inventoryMasterId);

			InventoryDTO resultInventoryDto2 = inventoryService.save(inventoryDTO);
			if (resultInventoryDto2 == null) {
				log.error("Error while create default inventory while creating manufacturee");
			}

		}
		// ------------------------------------------------------------------------------------------

		return ResponseEntity
				.created(new URI("/api/suppliers/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	private String hasSupplierAlreadyExistWithcriteria(SupplierCriteria criteria, Pageable pageable,
			SupplierDTO supplierDTO) {

		String messageForReponce = null;

		Page<SupplierDTO> page = supplierQueryService.findByCriteria(criteria, pageable);

		List<SupplierDTO> list = page.getContent();

		if (list != null && list.size() > 0) {

			for (SupplierDTO dtoSupplier : list) {

				if (supplierDTO.getId() != null) {
					// This for in update case
					if (!supplierDTO.getId().equals(dtoSupplier.getId()) && dtoSupplier.getRegistrationNo() != null
							&& dtoSupplier.getRegistrationNo().equalsIgnoreCase(supplierDTO.getRegistrationNo())) {
						messageForReponce = "Supplier already exist with same registration no :"
								+ supplierDTO.getRegistrationNo();
						break;
					} else if (!supplierDTO.getId().equals(dtoSupplier.getId()) && dtoSupplier.getEmail() != null
							&& dtoSupplier.getEmail().equalsIgnoreCase(supplierDTO.getEmail())) {
						messageForReponce = "Supplier already exist with same email :" + supplierDTO.getEmail();
						break;
					} else if (!supplierDTO.getId().equals(dtoSupplier.getId()) && dtoSupplier.getContactNo() != null
							&& dtoSupplier.getContactNo().equalsIgnoreCase(supplierDTO.getContactNo())) {
						messageForReponce = "Supplier already exist with same contact :" + supplierDTO.getContactNo();
						break;
					}
				} else {
					if (dtoSupplier.getRegistrationNo() != null
							&& dtoSupplier.getRegistrationNo().equalsIgnoreCase(supplierDTO.getRegistrationNo())) {
						messageForReponce = "Supplier already exist with same registration no :"
								+ supplierDTO.getRegistrationNo();
						break;
					} else if (dtoSupplier.getEmail() != null
							&& dtoSupplier.getEmail().equalsIgnoreCase(supplierDTO.getEmail())) {
						messageForReponce = "Supplier already exist with same email :" + supplierDTO.getEmail();
						break;
					} else if (dtoSupplier.getContactNo() != null
							&& dtoSupplier.getContactNo().equalsIgnoreCase(supplierDTO.getContactNo())) {
						messageForReponce = "Supplier already exist with same contact :" + supplierDTO.getContactNo();
						break;
					}
				}
			}

		}
		// ----------------------------------------------------------------------------------------------------------
		return messageForReponce;
	}

	/**
	 * {@code PUT  /suppliers} : Updates an existing supplier.
	 *
	 * @param supplierDTO the supplierDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated supplierDTO, or with status {@code 400 (Bad Request)} if
	 *         the supplierDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the supplierDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/suppliers")
	@PreAuthorize("hasAuthority('SUPLLIER_EDIT')")
	public ResponseEntity<SupplierDTO> updateSupplier(@Valid @RequestBody SupplierDTO supplierDTO)
			throws URISyntaxException {
		log.debug("REST request to update Supplier : {}", supplierDTO);
		if (supplierDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		// ------------------------------------------------------------------------------------------------------
		// Added check point for Supplier already with registration id, Email and
		// Contact No
		Pageable pageable = PageRequest.of(0, 30);

		if (supplierDTO.getRegistrationNo() != null && !supplierDTO.getRegistrationNo().isEmpty()) {

			SupplierCriteria criteria = new SupplierCriteria();
			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(supplierDTO.getRegistrationNo());
			criteria.setRegistrationNo(regStringFilter);
			String messageForReponce = hasSupplierAlreadyExistWithcriteria(criteria, pageable, supplierDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		if (supplierDTO.getEmail() != null && !supplierDTO.getEmail().isEmpty()) {

			SupplierCriteria criteria = new SupplierCriteria();
			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(supplierDTO.getEmail());
			criteria.setEmail(regStringFilter);
			String messageForReponce = hasSupplierAlreadyExistWithcriteria(criteria, pageable, supplierDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		if (supplierDTO.getContactNo() != null && !supplierDTO.getContactNo().isEmpty()) {

			SupplierCriteria criteria = new SupplierCriteria();
			StringFilter regStringFilter = new StringFilter();
			regStringFilter.setEquals(supplierDTO.getContactNo());
			criteria.setContactNo(regStringFilter);
			String messageForReponce = hasSupplierAlreadyExistWithcriteria(criteria, pageable, supplierDTO);
			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}

		}

		// ----------------------------------------------------------------------------------------------------------

		SupplierDTO result = supplierService.save(supplierDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, supplierDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /suppliers} : get all the suppliers.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of suppliers in body.
	 */
	@GetMapping("/suppliers")
	public ResponseEntity<List<SupplierDTO>> getAllSuppliers(SupplierCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Suppliers by criteria: {}", criteria);
		Page<SupplierDTO> page = supplierQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /suppliers/count} : count all the suppliers.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/suppliers/count")
	public ResponseEntity<Long> countSuppliers(SupplierCriteria criteria) {
		log.debug("REST request to count Suppliers by criteria: {}", criteria);
		return ResponseEntity.ok().body(supplierQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /suppliers/:id} : get the "id" supplier.
	 *
	 * @param id the id of the supplierDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the supplierDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/suppliers/{id}")
	@PreAuthorize("hasAnyAuthority('SUPPLIER_VIEW','SUPPLIER_USER')")
	public ResponseEntity<SupplierDTO> getSupplier(@PathVariable Long id) {
		log.debug("REST request to get Supplier : {}", id);
		Optional<SupplierDTO> supplierDTO = supplierService.findOne(id);
		return ResponseUtil.wrapOrNotFound(supplierDTO);
	}

	/**
	 * {@code DELETE  /suppliers/:id} : delete the "id" supplier.
	 *
	 * @param id the id of the supplierDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/suppliers/{id}")
	@PreAuthorize("hasAuthority('SUPLLIER_EDIT')")
	public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
		log.debug("REST request to delete Supplier : {}", id);
		
		throw new CustomMessageException("You can't prform this operation!!");
		
//		supplierService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
	}
}
