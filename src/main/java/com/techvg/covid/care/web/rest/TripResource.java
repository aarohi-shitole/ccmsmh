package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.TripService;
import com.techvg.covid.care.web.rest.errors.AlreadyExitsException;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.TripDTO;
import com.techvg.covid.care.service.dto.TripDetailsCriteria;
import com.techvg.covid.care.service.dto.TripDetailsDTO;
import com.techvg.covid.care.service.dto.BedTypeDTO;
import com.techvg.covid.care.service.dto.InventoryCriteria;
import com.techvg.covid.care.service.dto.InventoryDTO;
import com.techvg.covid.care.service.dto.SupplierDTO;
import com.techvg.covid.care.service.dto.TripCriteria;
import com.techvg.covid.care.service.dto.TripCriteria.TransactionStatusFilter;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import com.techvg.covid.care.repository.TripRepository;
import com.techvg.covid.care.service.InventoryQueryService;
import com.techvg.covid.care.service.SupplierService;
import com.techvg.covid.care.service.TripDetailsQueryService;
import com.techvg.covid.care.service.TripDetailsService;
import com.techvg.covid.care.service.TripQueryService;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import net.logstash.logback.encoder.com.lmax.disruptor.AlertException;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.techvg.covid.care.domain.Trip}.
 */
@RestController
@RequestMapping("/api")
public class TripResource {

	private final Logger log = LoggerFactory.getLogger(TripResource.class);

	private static final String ENTITY_NAME = "trip";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final TripService tripService;
	private final TripQueryService tripQueryService;

	@Autowired
	private TripDetailsQueryService tripDetailsQueryService;

	@Autowired
	private TripDetailsService tripDetailsService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private InventoryQueryService inventoryQueryService;

	public TripResource(TripService tripService, TripQueryService tripQueryService) {
		this.tripService = tripService;
		this.tripQueryService = tripQueryService;
	}

	/**
	 * {@code POST  /trips} : Create a new trip.
	 *
	 * @param tripDTO the tripDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tripDTO, or with status {@code 400 (Bad Request)} if the
	 *         trip has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/trips")
	@PreAuthorize("hasAnyAuthority('SUPPLIER_TRIP_EDIT')")
	public ResponseEntity<TripDTO> createTrip(@Valid @RequestBody TripDTO tripDTO) throws URISyntaxException {
		log.debug("REST request to save Trip : {}", tripDTO);
		if (tripDTO.getId() != null) {
			throw new BadRequestAlertException("A new trip cannot already have an ID", ENTITY_NAME, "idexists");
		}

		// ---------------------------------------------------------------------------------------------
		// Check for LMO storage is there for Supplier
		boolean hasFoundLMOStorage = hasLMOStorage(tripDTO);
		if (!hasFoundLMOStorage) {
			throw new AlreadyExitsException(
					"A sender don't have LMO storage capability. Please select anather one and try again.");
		}
		// ------------------------------------------------------------------------------------------------

		// ---------------------------------------------------------------------------------------------
		// Check for Stock should not greater than LMO storage capacity is there for Supplier
		String hasStockMoreThanLMOStorage = checkLMOStorageCapacity(tripDTO);
		if (hasStockMoreThanLMOStorage != null) {
			throw new AlreadyExitsException(hasStockMoreThanLMOStorage);
		}
		// ------------------------------------------------------------------------------------------------
      
		// ------------------------------------------------------------------------------------------------------
		// Added check point for Trip already exist with some criteria like MobId and
		// Open and Transit status
		Pageable pageable = PageRequest.of(0, 30);

		TripCriteria criteria = new TripCriteria();
		LongFilter longFilter = new LongFilter();
		longFilter.setEquals(tripDTO.getMobaId());
		criteria.setMobaId(longFilter);

		TransactionStatusFilter tsf = new TransactionStatusFilter();
		List<TransactionStatus> listTS = new ArrayList<TransactionStatus>();
		listTS.add(TransactionStatus.OPEN);
		listTS.add(TransactionStatus.TRANSIT);

		criteria.setStatus(tsf);

		Page<TripDTO> page = tripQueryService.findByCriteria(criteria, pageable);

		List<TripDTO> list = page.getContent();
		String messageForReponce = null;
		if (list != null && list.size() > 0) {
			for (TripDTO tripDTO2 : list) {

				if (tripDTO2.getStatus().equals(TransactionStatus.OPEN)) {
					messageForReponce = "Trip already scheduled for selected number :" + tripDTO.getNumberPlate();
					break;
				}

				if (tripDTO2.getStatus().equals(TransactionStatus.TRANSIT)) {
					messageForReponce = "Trip already in " + TransactionStatus.TRANSIT + "state for selected number :"
							+ tripDTO.getNumberPlate();
					break;
				}
			}

			if (messageForReponce != null) {
				throw new AlreadyExitsException(messageForReponce);
			}
		}
		// ----------------------------------------------------------------------------------------------------------

		tripDTO.setTrackingNo("" + (new Date()).getTime());
		TripDTO result = tripService.save(tripDTO);
		return ResponseEntity
				.created(new URI("/api/trips/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	private boolean hasLMOStorage(@Valid TripDTO tripDTO) {

		boolean hasFoundLMOStorage = false;

		if (tripDTO.getSupplierId() != null) {

			long supplierId = tripDTO.getSupplierId();

			Pageable pageable = PageRequest.of(0, 30);

			InventoryCriteria criteria = new InventoryCriteria();
			LongFilter longFilter = new LongFilter();
			longFilter.setEquals(supplierId);

			criteria.setSupplierId(longFilter);

			Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteria, pageable);

			List<InventoryDTO> lst = page.getContent();

			if (!lst.isEmpty()) {
				for (InventoryDTO inventoryDTO : lst) {

					Long localInventoryMasterId = inventoryDTO.getInventoryMasterId();
					if (localInventoryMasterId != null
							&& (localInventoryMasterId == 7 || localInventoryMasterId.equals(7))) {
						hasFoundLMOStorage = true;
					}

				}
			}

		}

		return hasFoundLMOStorage;
	}

	private String checkLMOStorageCapacity(@Valid TripDTO tripDTO) {

		String errorMessage = null;

		if (tripDTO.getSupplierId() != null) {

			long supplierId = tripDTO.getSupplierId();

			Pageable pageable = PageRequest.of(0, 30);

			InventoryCriteria criteria = new InventoryCriteria();
			LongFilter longFilter = new LongFilter();
			longFilter.setEquals(supplierId);

			criteria.setSupplierId(longFilter);

			Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteria, pageable);

			List<InventoryDTO> lst = page.getContent();

			if (!lst.isEmpty()) {
				for (InventoryDTO inventoryDTO : lst) {

					Long localInventoryMasterId = inventoryDTO.getInventoryMasterId();
					if (localInventoryMasterId != null
							&& (localInventoryMasterId == 7 || localInventoryMasterId.equals(7))) {
						if (tripDTO.getStock() > inventoryDTO.getStock()) {
							errorMessage = "Trip stock should not greater than LMO Storage stock.";
						}
					}

				}
			}

		}

		return errorMessage;
	}

	/**
	 * {@code PUT  /trips} : Updates an existing trip.
	 *
	 * @param tripDTO the tripDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated tripDTO, or with status {@code 400 (Bad Request)} if the
	 *         tripDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the tripDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/trips")
	@PreAuthorize("hasAnyAuthority('SUPPLIER_TRIP_EDIT')")
	public ResponseEntity<TripDTO> updateTrip(@Valid @RequestBody TripDTO tripDTO) throws URISyntaxException {
		log.debug("REST request to update Trip : {}", tripDTO);
		if (tripDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		TripDTO result = tripService.save(tripDTO);

		// ------------------------------------------------------------------------------------------------------
		// When trip start then for transit state then route also goes in Transit state

		Pageable pageable = PageRequest.of(0, 30);

		TripDetailsCriteria criteria = new TripDetailsCriteria();
		LongFilter longFilterTripId = new LongFilter();
		longFilterTripId.setEquals(tripDTO.getId());
		criteria.setTripId(longFilterTripId);

		Page<TripDetailsDTO> page = tripDetailsQueryService.findByCriteria(criteria, pageable);

		List<TripDetailsDTO> list = page.getContent();
 
		if (list != null && list.size() > 0) {

			for (TripDetailsDTO tripDetailsDTO : list) {

				String incomingStatus=tripDTO.getStatus().getValue();
				String currentStatus = tripDetailsDTO.getStatus().getValue();
				String cancelStatus = TransactionStatus.CANCELLED.getValue();

				if (!currentStatus.equals(cancelStatus) || incomingStatus.equals(cancelStatus)) {
					tripDetailsDTO.setStatus(tripDTO.getStatus());
					tripDetailsService.save(tripDetailsDTO);
				}

			}
		}

		// ----------------------------------------------------------------------------------------------------------

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tripDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /trips} : get all the trips.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of trips in body.
	 */
	@GetMapping("/trips")
	@PreAuthorize("hasAnyAuthority('SUPPLIER_TRIP_LIST')")
	public ResponseEntity<List<TripDTO>> getAllTrips(TripCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Trips by criteria: {}", criteria);
		Page<TripDTO> page = tripQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /trips/count} : count all the trips.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/trips/count")
	public ResponseEntity<Long> countTrips(TripCriteria criteria) {
		log.debug("REST request to count Trips by criteria: {}", criteria);
		return ResponseEntity.ok().body(tripQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /trips/:id} : get the "id" trip.
	 *
	 * @param id the id of the tripDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the tripDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/trips/{id}")
	@PreAuthorize("hasAnyAuthority('SUPPLIER_TRIP_LIST')")
	public ResponseEntity<TripDTO> getTrip(@PathVariable Long id) {
		log.debug("REST request to get Trip : {}", id);
		Optional<TripDTO> tripDTO = tripService.findOne(id);
		return ResponseUtil.wrapOrNotFound(tripDTO);
	}

//	/**
//	 * {@code DELETE  /trips/:id} : delete the "id" trip.
//	 *
//	 * @param id the id of the tripDTO to delete.
//	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//	 */
//	@DeleteMapping("/trips/{id}")
//	public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
//		log.debug("REST request to delete Trip : {}", id);
//		tripService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
//	}
}
