package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.BedTransactionsService;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.BedTransactionsDTO;
import com.techvg.covid.care.service.dto.BedInventoryCriteria;
import com.techvg.covid.care.service.dto.BedInventoryDTO;
import com.techvg.covid.care.service.dto.BedTransactionsCriteria;
import com.techvg.covid.care.repository.BedInventoryRepository;
import com.techvg.covid.care.service.BedInventoryQueryService;
import com.techvg.covid.care.service.BedInventoryService;
import com.techvg.covid.care.service.BedTransactionsQueryService;

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
 * {@link com.techvg.covid.care.domain.BedTransactions}.
 */
@RestController
@RequestMapping("/api")
public class BedTransactionsResource {

	private final Logger log = LoggerFactory.getLogger(BedTransactionsResource.class);

	private static final String ENTITY_NAME = "bedTransactions";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final BedTransactionsService bedTransactionsService;

	@Autowired
	private final BedInventoryService bedInventoryService;

	@Autowired
	private BedInventoryRepository bedInventoryRepository;

	@Autowired
	private BedInventoryQueryService bedInventoryQueryService;

	private final BedTransactionsQueryService bedTransactionsQueryService;

	@Value("${ODAS.shouldSendData}")
	private boolean shouldSendDataToODAS;

	public BedTransactionsResource(BedInventoryService bedInventoryService,
			BedTransactionsService bedTransactionsService, BedTransactionsQueryService bedTransactionsQueryService) {
		this.bedInventoryService = bedInventoryService;
		this.bedTransactionsService = bedTransactionsService;
		this.bedTransactionsQueryService = bedTransactionsQueryService;
	}

	/**
	 * {@code POST  /bed-transactions} : Create a new bedTransactions.
	 *
	 * @param bedTransactionsDTO the bedTransactionsDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new bedTransactionsDTO, or with status
	 *         {@code 400 (Bad Request)} if the bedTransactions has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/bed-transactions")
	@PreAuthorize("hasAnyAuthority('HOSPITAL_UPDATE_BED')")
	public ResponseEntity<BedTransactionsDTO> createBedTransactions(
			@Valid @RequestBody BedTransactionsDTO bedTransactionsDTO) throws URISyntaxException {
		log.debug("REST request to save BedTransactions : {}", bedTransactionsDTO);
		if (bedTransactionsDTO.getId() != null) {
			throw new BadRequestAlertException("A new bedTransactions cannot already have an ID", ENTITY_NAME,
					"idexists");
		}

		try {
			// ------------------------------------------------------------------------------
			// Added code update the Bed Transacations in Bed Inventory Table as well
			Optional<BedInventoryDTO> bedInventoryDTO = bedInventoryService.findBedInventoryOnHospitalIdAndBedTypeId(
					bedTransactionsDTO.getHospitalId(), bedTransactionsDTO.getBedTypeId());

			if (bedInventoryDTO.isPresent()) {
				BedInventoryDTO bedInventory = bedInventoryDTO.get();
				bedInventory.setOccupied(bedTransactionsDTO.getOccupied());
				bedInventory.setOnLMO(bedTransactionsDTO.getOnLMO());
				bedInventory.setOnCylinder(bedTransactionsDTO.getOnCylinder());
				bedInventory.setOnConcentrators(bedTransactionsDTO.getOnCylinder());
				bedInventory.setLastModified(null);
				bedInventory.setLastModifiedBy(null);
				bedInventoryService.save(bedInventory);
			}
			// -------------------------------------------------------------------------------
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		BedTransactionsDTO result = bedTransactionsService.save(bedTransactionsDTO);

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

				bedInventoryService.sendFacilityBedsOccupancyToODAS(result.getHospitalId(), list, false);

			}
		}
		// -----------------------------------------------------------------------------------------------

		return ResponseEntity
				.created(new URI("/api/bed-transactions/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

//    /**
//     * {@code PUT  /bed-transactions} : Updates an existing bedTransactions.
//     *
//     * @param bedTransactionsDTO the bedTransactionsDTO to update.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedTransactionsDTO,
//     * or with status {@code 400 (Bad Request)} if the bedTransactionsDTO is not valid,
//     * or with status {@code 500 (Internal Server Error)} if the bedTransactionsDTO couldn't be updated.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PutMapping("/bed-transactions")
//    public ResponseEntity<BedTransactionsDTO> updateBedTransactions(@Valid @RequestBody BedTransactionsDTO bedTransactionsDTO) throws URISyntaxException {
//        log.debug("REST request to update BedTransactions : {}", bedTransactionsDTO);
//        if (bedTransactionsDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        BedTransactionsDTO result = bedTransactionsService.save(bedTransactionsDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bedTransactionsDTO.getId().toString()))
//            .body(result);
//    }

	/**
	 * {@code GET  /bed-transactions} : get all the bedTransactions.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of bedTransactions in body.
	 */
	@GetMapping("/bed-transactions")
	public ResponseEntity<List<BedTransactionsDTO>> getAllBedTransactions(BedTransactionsCriteria criteria,
			Pageable pageable) {
		log.debug("REST request to get BedTransactions by criteria: {}", criteria);
		Page<BedTransactionsDTO> page = bedTransactionsQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /bed-transactions/count} : count all the bedTransactions.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/bed-transactions/count")
	public ResponseEntity<Long> countBedTransactions(BedTransactionsCriteria criteria) {
		log.debug("REST request to count BedTransactions by criteria: {}", criteria);
		return ResponseEntity.ok().body(bedTransactionsQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /bed-transactions/:id} : get the "id" bedTransactions.
	 *
	 * @param id the id of the bedTransactionsDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the bedTransactionsDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/bed-transactions/{id}")
	public ResponseEntity<BedTransactionsDTO> getBedTransactions(@PathVariable Long id) {
		log.debug("REST request to get BedTransactions : {}", id);
		Optional<BedTransactionsDTO> bedTransactionsDTO = bedTransactionsService.findOne(id);
		return ResponseUtil.wrapOrNotFound(bedTransactionsDTO);
	}

	/**
	 * {@code DELETE  /bed-transactions/:id} : delete the "id" bedTransactions.
	 *
	 * @param id the id of the bedTransactionsDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/bed-transactions/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER')")
	public ResponseEntity<Void> deleteBedTransactions(@PathVariable Long id) {
		log.debug("REST request to delete BedTransactions : {}", id);
		bedTransactionsService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
