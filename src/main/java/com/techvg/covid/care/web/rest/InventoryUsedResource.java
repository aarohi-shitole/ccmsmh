package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.InventoryUsedService;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.InventoryUsedDTO;
import com.techvg.covid.care.service.dto.InventoryCriteria;
import com.techvg.covid.care.service.dto.InventoryDTO;
import com.techvg.covid.care.service.dto.InventoryUsedCriteria;
import com.techvg.covid.care.service.InventoryQueryService;
import com.techvg.covid.care.service.InventoryService;
import com.techvg.covid.care.service.InventoryUsedQueryService;

import io.github.jhipster.service.filter.LongFilter;
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
 * REST controller for managing
 * {@link com.techvg.covid.care.domain.InventoryUsed}.
 */
@RestController
@RequestMapping("/api")
public class InventoryUsedResource {

	private final Logger log = LoggerFactory.getLogger(InventoryUsedResource.class);

	private static final String ENTITY_NAME = "inventoryUsed";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final InventoryUsedService inventoryUsedService;
	private final InventoryService inventoryService;

	@Autowired
	private InventoryQueryService inventoryQueryService;
	private final InventoryUsedQueryService inventoryUsedQueryService;

	@Value("${ODAS.shouldSendData}")
	private boolean shouldSendDataToODAS;

	public InventoryUsedResource(InventoryService inventoryService, InventoryUsedService inventoryUsedService,
			InventoryUsedQueryService inventoryUsedQueryService) {
		this.inventoryService = inventoryService;
		this.inventoryUsedService = inventoryUsedService;
		this.inventoryUsedQueryService = inventoryUsedQueryService;
	}

	/**
	 * {@code POST  /inventory-useds} : Create a new inventoryUsed.
	 *
	 * @param inventoryUsedDTO the inventoryUsedDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new inventoryUsedDTO, or with status
	 *         {@code 400 (Bad Request)} if the inventoryUsed has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/inventory-useds")
	@PreAuthorize("hasAnyAuthority('INVENTORY_STOCK_UPDATE')")
	public ResponseEntity<InventoryUsedDTO> createInventoryUsed(@Valid @RequestBody InventoryUsedDTO inventoryUsedDTO)
			throws URISyntaxException {
		log.debug("REST request to save InventoryUsed : {}", inventoryUsedDTO);
		if (inventoryUsedDTO.getId() != null) {
			throw new BadRequestAlertException("A new inventoryUsed cannot already have an ID", ENTITY_NAME,
					"idexists");
		}

		// ---------------------------------------------------------------------------------------------
		// Added code for Update consumptions in Inventory table as well while adding
		// Inventory Uses
		Optional<InventoryDTO> inventory = inventoryService.findOne(inventoryUsedDTO.getInventoryId());

		if (inventory.isPresent()) {
			InventoryDTO inventoryDTO = inventory.get();
			// Long currentStock=inventoryDTO.getStock()-inventoryUsedDTO.getStock();
			inventoryDTO.setStock(inventoryUsedDTO.getStock());
			inventoryDTO.setCapcity(inventoryUsedDTO.getCapcity());
			inventoryDTO.setLastModified(null);
			inventoryDTO.setLastModifiedBy(null);
			inventoryService.save(inventoryDTO);
		}
		// -------------------------------------------------------------------------------------------------

		InventoryUsedDTO result = inventoryUsedService.save(inventoryUsedDTO);

		// ------------------------------------------------------------------------------------------------
		// Send data to ODAS System

		if (shouldSendDataToODAS) {

			if (inventory.isPresent()) {
				InventoryDTO inventoryDTO = inventory.get();

				if (inventoryDTO.getHospitalId() != null) {
					Pageable pageable = PageRequest.of(0, 30);

					LongFilter longFilter = new LongFilter();
					InventoryCriteria criteria = new InventoryCriteria();

					longFilter.setEquals(inventoryDTO.getHospitalId());
					criteria.setHospitalId(longFilter);

					Long hospitalId = inventoryDTO.getHospitalId();

					Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteria, pageable);
					List<InventoryDTO> list = page.getContent();

					inventoryService.sendFacilityOxygenConsumptionToODAS(hospitalId, list, false);
				}

				// -------------------------------------------------------------------------------------------------

			}
		}

		return ResponseEntity
				.created(new URI("/api/inventory-useds/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /inventory-useds} : Updates an existing inventoryUsed.
	 *
	 * @param inventoryUsedDTO the inventoryUsedDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated inventoryUsedDTO, or with status
	 *         {@code 400 (Bad Request)} if the inventoryUsedDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         inventoryUsedDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/inventory-useds")
	@PreAuthorize("hasAnyAuthority('INVENTORY_STOCK_UPDATE')")
	public ResponseEntity<InventoryUsedDTO> updateInventoryUsed(@Valid @RequestBody InventoryUsedDTO inventoryUsedDTO)
			throws URISyntaxException {
		log.debug("REST request to update InventoryUsed : {}", inventoryUsedDTO);
		if (inventoryUsedDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		InventoryUsedDTO result = inventoryUsedService.save(inventoryUsedDTO);

		// ------------------------------------------------------------------------------------------------
		// Send data to ODAS System

		if (shouldSendDataToODAS) {
			Optional<InventoryDTO> inventory = inventoryService.findOne(result.getInventoryId());

			if (inventory.isPresent()) {
				InventoryDTO inventoryDTO = inventory.get();

				if (inventoryDTO.getHospitalId() != null) {
					Pageable pageable = PageRequest.of(0, 30);

					LongFilter longFilter = new LongFilter();
					InventoryCriteria criteria = new InventoryCriteria();

					longFilter.setEquals(inventoryDTO.getHospitalId());
					criteria.setHospitalId(longFilter);

					Long hospitalId = inventoryDTO.getHospitalId();

					Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteria, pageable);
					List<InventoryDTO> list = page.getContent();

					inventoryService.sendFacilityOxygenConsumptionToODAS(hospitalId, list, false);
				}
			}
		}
		// -------------------------------------------------------------------------------------------------

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				inventoryUsedDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /inventory-useds} : get all the inventoryUseds.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of inventoryUseds in body.
	 */
	@GetMapping("/inventory-useds")
	public ResponseEntity<List<InventoryUsedDTO>> getAllInventoryUseds(InventoryUsedCriteria criteria,
			Pageable pageable) {
		log.debug("REST request to get InventoryUseds by criteria: {}", criteria);
		Page<InventoryUsedDTO> page = inventoryUsedQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /inventory-useds/count} : count all the inventoryUseds.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/inventory-useds/count")
	public ResponseEntity<Long> countInventoryUseds(InventoryUsedCriteria criteria) {
		log.debug("REST request to count InventoryUseds by criteria: {}", criteria);
		return ResponseEntity.ok().body(inventoryUsedQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /inventory-useds/:id} : get the "id" inventoryUsed.
	 *
	 * @param id the id of the inventoryUsedDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the inventoryUsedDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/inventory-useds/{id}")
	public ResponseEntity<InventoryUsedDTO> getInventoryUsed(@PathVariable Long id) {
		log.debug("REST request to get InventoryUsed : {}", id);
		Optional<InventoryUsedDTO> inventoryUsedDTO = inventoryUsedService.findOne(id);
		return ResponseUtil.wrapOrNotFound(inventoryUsedDTO);
	}

	/**
	 * {@code DELETE  /inventory-useds/:id} : delete the "id" inventoryUsed.
	 *
	 * @param id the id of the inventoryUsedDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/inventory-useds/{id}")
	@PreAuthorize("hasAnyAuthority('INVENTORY_STOCK_UPDATE')")
	public ResponseEntity<Void> deleteInventoryUsed(@PathVariable Long id) {
		log.debug("REST request to delete InventoryUsed : {}", id);
		inventoryUsedService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
