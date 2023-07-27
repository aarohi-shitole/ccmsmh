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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import com.techvg.covid.care.service.HospitalQueryService;
import com.techvg.covid.care.service.InventoryQueryService;
import com.techvg.covid.care.service.TripDetailsQueryService;
import com.techvg.covid.care.service.TripDetailsService;
import com.techvg.covid.care.service.TripService;
import com.techvg.covid.care.service.dto.InventoryCriteria;
import com.techvg.covid.care.service.dto.InventoryDTO;
import com.techvg.covid.care.service.dto.TripDTO;
import com.techvg.covid.care.service.dto.TripDetailsCriteria;
import com.techvg.covid.care.service.dto.TripDetailsDTO;
import com.techvg.covid.care.web.rest.errors.AlreadyExitsException;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.techvg.covid.care.domain.TripDetails}.
 */
@RestController
@RequestMapping("/api")
public class TripDetailsResource {

	private final Logger log = LoggerFactory.getLogger(TripDetailsResource.class);

	private static final String ENTITY_NAME = "tripDetails";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final TripDetailsService tripDetailsService;
	@Autowired
	private TripService tripService;
	private final TripDetailsQueryService tripDetailsQueryService;

	@Autowired
	private InventoryQueryService inventoryQueryService;

	@Autowired
	private HospitalQueryService hospitalQueryService;

	public TripDetailsResource(TripDetailsService tripDetailsService, TripDetailsQueryService tripDetailsQueryService) {
		this.tripDetailsService = tripDetailsService;
		this.tripDetailsQueryService = tripDetailsQueryService;
	}

	/**
	 * {@code POST  /trip-details} : Create a new tripDetails.
	 *
	 * @param tripDetailsDTO the tripDetailsDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tripDetailsDTO, or with status {@code 400 (Bad Request)}
	 *         if the tripDetails has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	/**
	 * @param tripDetailsDTO
	 * @return
	 * @throws URISyntaxException
	 */
	@PostMapping("/trip-details")
	@PreAuthorize("hasAnyAuthority('SUPPLIER_TRIP_DETAILS_EDIT', 'HOSPITAL_TRIP_DETAILS_EDIT','SUPPLIER_TRIP_EDIT')")
	public ResponseEntity<TripDetailsDTO> createTripDetails(@Valid @RequestBody TripDetailsDTO tripDetailsDTO)
			throws URISyntaxException {
		log.debug("REST request to save TripDetails : {}", tripDetailsDTO);
		if (tripDetailsDTO.getId() != null) {
			throw new BadRequestAlertException("A new tripDetails cannot already have an ID", ENTITY_NAME, "idexists");
		}
		// ----------------------------------------------------------------------------------
		// Added check for Allocation of Oxygen for Hospital, Oxygen

//		// --------------------------------------------------------------------------------------------------
//		// Check for LMO storage is there for Supplier and Hospital
//		boolean hasFoundLMOStorage = hasLMOStorage(tripDetailsDTO);
//		if (!hasFoundLMOStorage) {
//			throw new AlreadyExitsException(
//					"A receiver don't have LMO storage capability. Please select anather one and try again.");
//		}
//		// ---------------------------------------------------------------------------------------------------

		Pageable pageable = PageRequest.of(0, 30);

		TripDetailsCriteria criteria = new TripDetailsCriteria();
		LongFilter longFilterTripId = new LongFilter();
		longFilterTripId.setEquals(tripDetailsDTO.getTripId());
		criteria.setTripId(longFilterTripId);

		Page<TripDetailsDTO> page = tripDetailsQueryService.findByCriteria(criteria, pageable);

		List<TripDetailsDTO> list = page.getContent();
		String messageForReponce = null;

		Double totalSentStoct = tripDetailsDTO.getStockSent();

		if (list != null && list.size() > 0) {

			for (TripDetailsDTO detailsDTO : list) {

				String dtoStatus = detailsDTO.getStatus().getValue();
				String openStatus = TransactionStatus.OPEN.getValue();
				String transitStatus = TransactionStatus.TRANSIT.getValue();

				if (tripDetailsDTO.getHospitalId() != null
  						&& tripDetailsDTO.getHospitalId().equals(detailsDTO.getHospitalId())
						&& dtoStatus.equals(openStatus)) {

					messageForReponce = "Rout already added in your list";
					break;
				} else if (tripDetailsDTO.getHospitalId() != null
						&& tripDetailsDTO.getHospitalId().equals(detailsDTO.getHospitalId())
						&& dtoStatus.equals(transitStatus)) {

					messageForReponce = "This rout already in Transit state";
					break;
				} else if (tripDetailsDTO.getSupplierId() != null
						&& tripDetailsDTO.getSupplierId().equals(detailsDTO.getSupplierId())
						&& dtoStatus.equals(openStatus)) {

					messageForReponce = "Rout already added in your list";
					break;
				} else if (tripDetailsDTO.getSupplierId() != null
						&& tripDetailsDTO.getSupplierId().equals(detailsDTO.getSupplierId())
						&& dtoStatus.equals(transitStatus)) {
					messageForReponce = "This rout already in Transit state";
					break;
				}

				String cancelStatus = TransactionStatus.CANCELLED.getValue();

				if (!dtoStatus.equals(cancelStatus)) {
					totalSentStoct = totalSentStoct + detailsDTO.getStockSent();
				}

			}

		}

		if (messageForReponce == null) {// Added this check for avoid the code if we found error

			Optional<TripDTO> tripDTOOptional = tripService.findOne(tripDetailsDTO.getTripId());

			if (tripDTOOptional.isPresent()) {
				TripDTO tripDTO = tripDTOOptional.get();

				if (totalSentStoct > tripDTO.getStock()) {
				//messageForReponce = "You can't send beyound the trip allocated stock";
				messageForReponce ="Stock is less than the trip requirement-Please Check The Stock Details";
				}

				Long senderId = tripDTO.getSupplierId();
				Long receiverId = tripDetailsDTO.getSupplierId();

				if (senderId.equals(receiverId) || senderId == receiverId) {
					messageForReponce = "Sender and receiver should not be same.";
				}

				// -----------------------------------------------------------------------------------------------
				// If trip already started then you add new route in that case rout should be in
				// Transit state
				String tripStatus = tripDTO.getStatus().getValue();
				String transitStatus = TransactionStatus.TRANSIT.getValue();

				if (tripStatus.equals(transitStatus)) {
					tripDetailsDTO.setStatus(TransactionStatus.TRANSIT);
				}
				// ---------------------------------------------------------------------------------------------
			}
		}

		if (messageForReponce != null) {
			throw new AlreadyExitsException(messageForReponce);
		}

		// ----------------------------------------------------------------------------------------------------------

		TripDetailsDTO result = tripDetailsService.save(tripDetailsDTO);
		return ResponseEntity
				.created(new URI("/api/trip-details/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	private boolean hasLMOStorage(TripDetailsDTO tripDetailsDTO) {

		boolean hasFoundLMOStorage = false;

		Pageable pageableLMO = PageRequest.of(0, 30);
		InventoryCriteria criteriaLMO = new InventoryCriteria();

		if (tripDetailsDTO.getSupplierId() != null) {

			long supplierId = tripDetailsDTO.getSupplierId();
			LongFilter longFilter = new LongFilter();
			longFilter.setEquals(supplierId);
			criteriaLMO.setSupplierId(longFilter);

		} else if (tripDetailsDTO.getHospitalId() != null) {

			long hospitalId = tripDetailsDTO.getHospitalId();
			LongFilter longFilter = new LongFilter();
			longFilter.setEquals(hospitalId);
			criteriaLMO.setHospitalId(longFilter);
		}
		Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteriaLMO, pageableLMO);

		List<InventoryDTO> lst = page.getContent();

		if (!lst.isEmpty()) {
			for (InventoryDTO inventoryDTO : lst) {

				if (inventoryDTO.getInventoryMasterId() == 7 || inventoryDTO.getInventoryMasterId().equals(7)) {
					hasFoundLMOStorage = true;
				}

			} 
		}

		return hasFoundLMOStorage;
	}

	/**
	 * {@code PUT  /trip-details} : Updates an existing tripDetails.
	 *
	 * @param tripDetailsDTO the tripDetailsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated tripDetailsDTO, or with status {@code 400 (Bad Request)}
	 *         if the tripDetailsDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the tripDetailsDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/trip-details")
	@PreAuthorize("hasAnyAuthority('SUPPLIER_TRIP_DETAILS_EDIT', 'HOSPITAL_TRIP_DETAILS_EDIT','SUPPLIER_TRIP_EDIT')")
	public ResponseEntity<TripDetailsDTO> updateTripDetails(@Valid @RequestBody TripDetailsDTO tripDetailsDTO)
			throws URISyntaxException {
		log.debug("REST request to update TripDetails : {}", tripDetailsDTO);
		if (tripDetailsDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		// ---------------------------------------------------------------------------------------------
		// Check for Stock should not greater than LMO storage capacity is there for
		// Supplier
		if (tripDetailsDTO.getStatus().getValue().equals(TransactionStatus.RECEIVED.getValue())) {
			String hasStockMoreThanLMOStorage = checkLMOStorageCapacity(tripDetailsDTO);
			if (hasStockMoreThanLMOStorage != null) {
				throw new AlreadyExitsException(hasStockMoreThanLMOStorage);
			}
		}
		// ------------------------------------------------------------------------------------------------

		TripDetailsDTO result = tripDetailsService.save(tripDetailsDTO);

		// --------------------------------------------------------------------------------------------------
		// Every update need to check the all route is received then we have update trip
		// to Received Status
		boolean hasTripComplted = hasTripComplted(tripDetailsDTO);

		if (hasTripComplted) {
			// Need get all details about this trip change state
			Optional<TripDTO> tripDTOOptional = tripService.findOne(tripDetailsDTO.getTripId());

			if (tripDTOOptional.isPresent()) {
				TripDTO tripDTO = tripDTOOptional.get();
				tripDTO.setStatus(TransactionStatus.RECEIVED);
				tripService.save(tripDTO);
			}
		}
		// -------------------------------------------------------------------------------------------------

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				tripDetailsDTO.getId().toString())).body(result);
	}

	private String checkLMOStorageCapacity(@Valid TripDetailsDTO tripDetailsDTO) {

		String errorMessage = null;

		if (tripDetailsDTO.getSupplierId() != null) {

			long supplierId = tripDetailsDTO.getSupplierId();

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
						if (tripDetailsDTO.getStockRec() > inventoryDTO.getCapcity()) {
							errorMessage = "You can't receive stock greater than your LMO Storage capacity.";
						}
					}

				}
			}

		} else if (tripDetailsDTO.getHospitalId() != null) {

			long hospitalId = tripDetailsDTO.getHospitalId();

			Pageable pageable = PageRequest.of(0, 30);

			InventoryCriteria criteria = new InventoryCriteria();
			LongFilter longFilter = new LongFilter();
			longFilter.setEquals(hospitalId);

			criteria.setHospitalId(longFilter);

			Page<InventoryDTO> page = inventoryQueryService.findByCriteria(criteria, pageable);

			List<InventoryDTO> lst = page.getContent();

			if (!lst.isEmpty()) {
				for (InventoryDTO inventoryDTO : lst) {

					Long localInventoryMasterId = inventoryDTO.getInventoryMasterId();

					if (localInventoryMasterId != null
							&& (localInventoryMasterId == 7 || localInventoryMasterId.equals(7))) {
						if (tripDetailsDTO.getStockRec() > inventoryDTO.getCapcity()) {
							errorMessage = "You can't receive stock greater than your LMO Storage capacity.";
						}
					}

				}
			}

		}

		return errorMessage;
	}

	private boolean hasTripComplted(@Valid TripDetailsDTO tripDetailsDTO) {

		boolean hasTripCompleted = false;
		int totalReceivedRoute = 0;
		int totalCancelledRoute = 0;

		Pageable pageable = PageRequest.of(0, 30);

		TripDetailsCriteria criteria = new TripDetailsCriteria();
		LongFilter longFilterTripId = new LongFilter();
		longFilterTripId.setEquals(tripDetailsDTO.getTripId());
		criteria.setTripId(longFilterTripId);

		Page<TripDetailsDTO> page = tripDetailsQueryService.findByCriteria(criteria, pageable);

		List<TripDetailsDTO> list = page.getContent();

		int totalRouts = list.size();

		if (list != null && list.size() > 0) {
			for (TripDetailsDTO detailsDTO : list) {

				String currentStatus = detailsDTO.getStatus().getValue();
				String receivedStatus = TransactionStatus.RECEIVED.getValue();
				String cancelledStatus = TransactionStatus.CANCELLED.getValue();

				if (currentStatus.equals(receivedStatus)) {
					totalReceivedRoute++;
				}

				if (currentStatus.equals(cancelledStatus)) {
					totalCancelledRoute++;
				}
			}
		} else {
			hasTripCompleted = false;
		}

		if (totalReceivedRoute + totalCancelledRoute == totalRouts) {
			hasTripCompleted = true;
		} else {
			hasTripCompleted = false;
		}

		return hasTripCompleted;
	}

	/**
	 * {@code GET  /trip-details} : get all the tripDetails.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tripDetails in body.
	 */
	@GetMapping("/trip-details")
	@PreAuthorize("hasAnyAuthority('HOSPITAL_TRIP_DETAILS_LIST', 'SUPPLIER_TRIP_DETAILS_LIST')")
	public ResponseEntity<List<TripDetailsDTO>> getAllTripDetails(TripDetailsCriteria criteria, Pageable pageable) {
		log.debug("REST request to get TripDetails by criteria: {}", criteria);
		Page<TripDetailsDTO> page = tripDetailsQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /trip-details/count} : count all the tripDetails.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/trip-details/count")
	public ResponseEntity<Long> countTripDetails(TripDetailsCriteria criteria) {
		log.debug("REST request to count TripDetails by criteria: {}", criteria);
		return ResponseEntity.ok().body(tripDetailsQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /trip-details/:id} : get the "id" tripDetails.
	 *
	 * @param id the id of the tripDetailsDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the tripDetailsDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/trip-details/{id}")
	public ResponseEntity<TripDetailsDTO> getTripDetails(@PathVariable Long id) {
		log.debug("REST request to get TripDetails : {}", id);
		Optional<TripDetailsDTO> tripDetailsDTO = tripDetailsService.findOne(id);
		return ResponseUtil.wrapOrNotFound(tripDetailsDTO);
	}

//	/**
//	 * {@code DELETE  /trip-details/:id} : delete the "id" tripDetails.
//	 *
//	 * @param id the id of the tripDetailsDTO to delete.
//	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//	 */
//	@DeleteMapping("/trip-details/{id}")
//	public ResponseEntity<Void> deleteTripDetails(@PathVariable Long id) {
//		log.debug("REST request to delete TripDetails : {}", id);
//		tripDetailsService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
//	}
}
