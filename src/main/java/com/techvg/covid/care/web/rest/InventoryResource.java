package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.InventoryService;
import com.techvg.covid.care.web.rest.errors.AlreadyExitsException;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.InventoryDTO;
import com.techvg.covid.care.service.dto.InventoryCriteria;
import com.techvg.covid.care.service.InventoryQueryService;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.Inventory}.
 */
@RestController
@RequestMapping("/api")
public class InventoryResource {

	private final Logger log = LoggerFactory.getLogger(InventoryResource.class);

	private static final String ENTITY_NAME = "inventory";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final InventoryService inventoryService;

	private final InventoryQueryService inventoryQueryService;

	@Value("${ODAS.shouldSendData}")
	private boolean shouldSendDataToODAS;

	public InventoryResource(InventoryService inventoryService, InventoryQueryService inventoryQueryService) {
		this.inventoryService = inventoryService;
		this.inventoryQueryService = inventoryQueryService;
	}

	/**
	 * {@code POST  /inventories} : Create a new inventory.
	 *
	 * @param inventoryDTO the inventoryDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new inventoryDTO, or with status {@code 400 (Bad Request)}
	 *         if the inventory has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/inventories")
	@PreAuthorize("hasAnyAuthority('INVENTORY_ADD')")
	public ResponseEntity<InventoryDTO> createInventory(@Valid @RequestBody InventoryDTO inventoryDTO)
			throws URISyntaxException {
		log.debug("REST request to save Inventory : {}", inventoryDTO);
		if (inventoryDTO.getId() != null) {
			throw new BadRequestAlertException("A new inventory cannot already have an ID", ENTITY_NAME, "idexists");
		}

		Pageable pageable = PageRequest.of(0, 30);

		LongFilter longFilter = new LongFilter();
		InventoryCriteria criteria = new InventoryCriteria();

		String messageForReponce = null;

		if (inventoryDTO.getSupplierId() != null) {

			longFilter.setEquals(inventoryDTO.getSupplierId());
			criteria.setSupplierId(longFilter);

			Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteria, pageable);
			List<InventoryDTO> list = page.getContent();

			Long suplierId = inventoryDTO.getSupplierId();
			Long inventoryMaterId = inventoryDTO.getInventoryMasterId();

			if (list != null && list.size() > 0) {

				for (InventoryDTO inventoryDTO2 : list) {

					Long localSupplierId = inventoryDTO2.getSupplierId();
					Long localInventoryMaterId = inventoryDTO2.getInventoryMasterId();

					if (suplierId != null && inventoryMaterId != null && localSupplierId != null
							&& localInventoryMaterId != null
							&& (localSupplierId == suplierId || suplierId.equals(localSupplierId))
							&& (localInventoryMaterId == inventoryMaterId
									|| localInventoryMaterId.equals(inventoryMaterId))) {
						messageForReponce = "Inventory already exit this supplier";
					}

				}
			}

		} else if (inventoryDTO.getHospitalId() != null) {

			longFilter.setEquals(inventoryDTO.getHospitalId());
			criteria.setHospitalId(longFilter);

			Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteria, pageable);
			List<InventoryDTO> list = page.getContent();

			Long hospitalId = inventoryDTO.getHospitalId();
			Long inventoryMaterId = inventoryDTO.getInventoryMasterId();

			if (list != null && list.size() > 0) {

				for (InventoryDTO inventoryDTO2 : list) {

					Long localSupplierId = inventoryDTO2.getSupplierId();
					Long localInventoryMaterId = inventoryDTO2.getInventoryMasterId();

					if (hospitalId != null && inventoryMaterId != null && localSupplierId != null
							&& localInventoryMaterId != null
							&& (localSupplierId == hospitalId || hospitalId.equals(localSupplierId))
							&& (localInventoryMaterId == inventoryMaterId
									|| localInventoryMaterId.equals(inventoryMaterId))) {
						messageForReponce = "Inventory already exit this hospital";
					}

				}
			}
		}

		if (messageForReponce != null) {
			throw new AlreadyExitsException(messageForReponce);
		}

		InventoryDTO result = inventoryService.save(inventoryDTO);

		// ------------------------------------------------------------------------------------------------
		// Send data to ODAS System

		// ------------------------------------------------------------------------------------------------
		// Send data to ODAS System

		if (shouldSendDataToODAS) {
			Pageable pageableOdas = PageRequest.of(0, 30);

			LongFilter longFilterODAS = new LongFilter();
			InventoryCriteria criteriaODAS = new InventoryCriteria();

			if (result.getHospitalId() != null) {

				longFilterODAS.setEquals(result.getHospitalId());
				criteriaODAS.setHospitalId(longFilter);

				Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteriaODAS, pageableOdas);
				List<InventoryDTO> list = page.getContent();

				if (result.getHospitalId() != null) {

					inventoryService.sendFacilityOxygenInfoToODAS(result.getHospitalId(), list, false);
				}

			}
		}

		// -------------------------------------------------------------------------------------------------

		// -------------------------------------------------------------------------------------------------

		return ResponseEntity
				.created(new URI("/api/inventories/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /inventories} : Updates an existing inventory.
	 *
	 * @param inventoryDTO the inventoryDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated inventoryDTO, or with status {@code 400 (Bad Request)} if
	 *         the inventoryDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the inventoryDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/inventories")
	@PreAuthorize("hasAnyAuthority('INVENTORY_EDIT')")
	public ResponseEntity<InventoryDTO> updateInventory(@Valid @RequestBody InventoryDTO inventoryDTO)
			throws URISyntaxException {
		log.debug("REST request to update Inventory : {}", inventoryDTO);
		if (inventoryDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		InventoryDTO result = inventoryService.save(inventoryDTO);

		// ------------------------------------------------------------------------------------------------
		// Send data to ODAS System

		if (shouldSendDataToODAS) {
			Pageable pageable = PageRequest.of(0, 30);

			LongFilter longFilter = new LongFilter();
			InventoryCriteria criteria = new InventoryCriteria();

			if (result.getHospitalId() != null) {

				longFilter.setEquals(result.getHospitalId());
				criteria.setHospitalId(longFilter);

				Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteria, pageable);
				List<InventoryDTO> list = page.getContent();

				if (result.getHospitalId() != null) {

					inventoryService.sendFacilityOxygenInfoToODAS(result.getHospitalId(), list, false);
				}

			}
		}

		// -------------------------------------------------------------------------------------------------

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				inventoryDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /inventories} : get all the inventories.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of inventories in body.
	 */
	@GetMapping("/inventories")
	public ResponseEntity<List<InventoryDTO>> getAllInventories(InventoryCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Inventories by criteria: {}", criteria);
		Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /inventories/count} : count all the inventories.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/inventories/count")
	public ResponseEntity<Long> countInventories(InventoryCriteria criteria) {
		log.debug("REST request to count Inventories by criteria: {}", criteria);
		return ResponseEntity.ok().body(inventoryQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /inventories/:id} : get the "id" inventory.
	 *
	 * @param id the id of the inventoryDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the inventoryDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/inventories/{id}")
	public ResponseEntity<InventoryDTO> getInventory(@PathVariable Long id) {
		log.debug("REST request to get Inventory : {}", id);
		Optional<InventoryDTO> inventoryDTO = inventoryService.findOne(id);
		return ResponseUtil.wrapOrNotFound(inventoryDTO);
	}

	/**
	 * {@code DELETE  /inventories/:id} : delete the "id" inventory.
	 *
	 * @param id the id of the inventoryDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/inventories/{id}")
	@PreAuthorize("hasAnyAuthority('INVENTORY_EDIT')")
	public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
		log.debug("REST request to delete Inventory : {}", id);
		inventoryService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
