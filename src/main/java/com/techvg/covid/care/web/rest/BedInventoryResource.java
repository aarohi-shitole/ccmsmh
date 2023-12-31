package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.BedInventoryService;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.BedInventoryDTO;
import com.techvg.covid.care.service.dto.BedInventoryCriteria;
import com.techvg.covid.care.service.BedInventoryQueryService;

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
 * REST controller for managing
 * {@link com.techvg.covid.care.domain.BedInventory}.
 */
@RestController
@RequestMapping("/api")
public class BedInventoryResource {

	private final Logger log = LoggerFactory.getLogger(BedInventoryResource.class);

	private static final String ENTITY_NAME = "bedInventory";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final BedInventoryService bedInventoryService;

	private final BedInventoryQueryService bedInventoryQueryService;

	@Value("${ODAS.shouldSendData}")
	private boolean shouldSendDataToODAS;

	public BedInventoryResource(BedInventoryService bedInventoryService,
			BedInventoryQueryService bedInventoryQueryService) {
		this.bedInventoryService = bedInventoryService;
		this.bedInventoryQueryService = bedInventoryQueryService;
	}

	/**
	 * {@code POST  /bed-inventories} : Create a new bedInventory.
	 *
	 * @param bedInventoryDTO the bedInventoryDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new bedInventoryDTO, or with status
	 *         {@code 400 (Bad Request)} if the bedInventory has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/bed-inventories")
	@PreAuthorize("hasAnyAuthority('HOSPITAL_EDIT')")
	public ResponseEntity<BedInventoryDTO> createBedInventory(@Valid @RequestBody BedInventoryDTO bedInventoryDTO)
			throws URISyntaxException {
		log.debug("REST request to save BedInventory : {}", bedInventoryDTO);
		if (bedInventoryDTO.getId() != null) {
			throw new BadRequestAlertException("A new bedInventory cannot already have an ID", ENTITY_NAME, "idexists");
		}
		BedInventoryDTO result = bedInventoryService.save(bedInventoryDTO);

		// --------------------------------------------------------------------------------------
		// After update the send odas facility id in our database
		// update the bes information to odas

		if (shouldSendDataToODAS) {

			Pageable pageable = PageRequest.of(0, 30);
			BedInventoryCriteria criteria = new BedInventoryCriteria();
			LongFilter longFilter = new LongFilter();
			longFilter.setEquals(result.getHospitalId());
			criteria.setHospitalId(longFilter);

			Page<BedInventoryDTO> page = bedInventoryQueryService.findByCriteria(criteria, pageable);

			List<BedInventoryDTO> list = page.getContent();
			if (list != null && list.size() > 0) {

				bedInventoryService.sendFacilityBedsInfoToODAS(result.getHospitalId(), list, false);

			}
		}
		// -----------------------------------------------------------------------------------------------

		return ResponseEntity
				.created(new URI("/api/bed-inventories/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /bed-inventories} : Updates an existing bedInventory.
	 *
	 * @param bedInventoryDTO the bedInventoryDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated bedInventoryDTO, or with status {@code 400 (Bad Request)}
	 *         if the bedInventoryDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the bedInventoryDTO couldn't
	 *         be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/bed-inventories")
	@PreAuthorize("hasAnyAuthority('HOSPITAL_EDIT')")
	public ResponseEntity<BedInventoryDTO> updateBedInventory(@Valid @RequestBody BedInventoryDTO bedInventoryDTO)
			throws URISyntaxException {
		log.debug("REST request to update BedInventory : {}", bedInventoryDTO);
		if (bedInventoryDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		BedInventoryDTO result = bedInventoryService.save(bedInventoryDTO);

		// --------------------------------------------------------------------------------------
		// After update the send odas facility id in our database
		// update the bes information to odas

		if (shouldSendDataToODAS) {

			Pageable pageable = PageRequest.of(0, 30);
			BedInventoryCriteria criteria = new BedInventoryCriteria();
			LongFilter longFilter = new LongFilter();
			longFilter.setEquals(result.getHospitalId());
			criteria.setHospitalId(longFilter);

			Page<BedInventoryDTO> page = bedInventoryQueryService.findByCriteria(criteria, pageable);

			List<BedInventoryDTO> list = page.getContent();
			if (list != null && list.size() > 0) {

				bedInventoryService.sendFacilityBedsInfoToODAS(result.getHospitalId(), list, false);

			}
		}
		// -----------------------------------------------------------------------------------------------

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				bedInventoryDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /bed-inventories} : get all the bedInventories.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of bedInventories in body.
	 */
	@GetMapping("/bed-inventories")
	public ResponseEntity<List<BedInventoryDTO>> getAllBedInventories(BedInventoryCriteria criteria,
			Pageable pageable) {
		log.debug("REST request to get BedInventories by criteria: {}", criteria);
		Page<BedInventoryDTO> page = bedInventoryQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /bed-inventories/count} : count all the bedInventories.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/bed-inventories/count")
	public ResponseEntity<Long> countBedInventories(BedInventoryCriteria criteria) {
		log.debug("REST request to count BedInventories by criteria: {}", criteria);
		return ResponseEntity.ok().body(bedInventoryQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /bed-inventories/:id} : get the "id" bedInventory.
	 *
	 * @param id the id of the bedInventoryDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the bedInventoryDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/bed-inventories/{id}")
	public ResponseEntity<BedInventoryDTO> getBedInventory(@PathVariable Long id) {
		log.debug("REST request to get BedInventory : {}", id);
		Optional<BedInventoryDTO> bedInventoryDTO = bedInventoryService.findOne(id);
		return ResponseUtil.wrapOrNotFound(bedInventoryDTO);
	}

	/**
	 * {@code DELETE  /bed-inventories/:id} : delete the "id" bedInventory.
	 *
	 * @param id the id of the bedInventoryDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/bed-inventories/{id}")
	@PreAuthorize("hasAnyAuthority('HOSPITAL_EDIT')")
	public ResponseEntity<Void> deleteBedInventory(@PathVariable Long id) {
		log.debug("REST request to delete BedInventory : {}", id);
		bedInventoryService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
