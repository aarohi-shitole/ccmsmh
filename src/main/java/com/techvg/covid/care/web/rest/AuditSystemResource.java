package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.AuditSystemService;
import com.techvg.covid.care.web.rest.errors.AlreadyExitsException;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.AuditSystemDTO;
import com.techvg.covid.care.service.dto.AuditSystemCriteria;
import com.techvg.covid.care.service.AuditSystemQueryService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing
 * {@link com.techvg.covid.care.domain.AuditSystem}.
 */
@RestController
@RequestMapping("/api")
public class AuditSystemResource {

	private final Logger log = LoggerFactory.getLogger(AuditSystemResource.class);

	private static final String ENTITY_NAME = "auditSystem";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final AuditSystemService auditSystemService;

	private final AuditSystemQueryService auditSystemQueryService;

	public AuditSystemResource(AuditSystemService auditSystemService, AuditSystemQueryService auditSystemQueryService) {
		this.auditSystemService = auditSystemService;
		this.auditSystemQueryService = auditSystemQueryService;
	}

	/**
	 * {@code POST  /audit-systems} : Create a new auditSystem.
	 *
	 * @param auditSystemDTO the auditSystemDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new auditSystemDTO, or with status {@code 400 (Bad Request)}
	 *         if the auditSystem has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/audit-systems")
	@PreAuthorize("hasAnyAuthority('AUDIT_EDIT')")
	public ResponseEntity<AuditSystemDTO> createAuditSystem(@Valid @RequestBody AuditSystemDTO auditSystemDTO)
			throws URISyntaxException {
		log.debug("REST request to save AuditSystem : {}", auditSystemDTO);
		if (auditSystemDTO.getId() != null) {
			throw new BadRequestAlertException("A new auditSystem cannot already have an ID", ENTITY_NAME, "idexists");
		}

		// ------------------------------------------------------------------------------------
		// Added check for verify Audit already exists or not for given Audit type

		Pageable pageable = PageRequest.of(0, 30);

		AuditSystemCriteria criteria = new AuditSystemCriteria();
		LongFilter longFilter = new LongFilter();
		longFilter.setEquals(auditSystemDTO.getAuditTypeId());
		criteria.setAuditTypeId(longFilter);

		if (auditSystemDTO.getSupplierId() != null) {
			
			LongFilter longFilterSupplier = new LongFilter();
			longFilterSupplier.setEquals(auditSystemDTO.getSupplierId());
			criteria.setSupplierId(longFilterSupplier);

		} else if (auditSystemDTO.getHospitalId() != null) {
			
			LongFilter longFilterHospital = new LongFilter();
			longFilterHospital.setEquals(auditSystemDTO.getHospitalId());
			criteria.setHospitalId(longFilterHospital);
		}

		Page<AuditSystemDTO> page = auditSystemQueryService.findByCriteria(criteria, pageable);

		List<AuditSystemDTO> list = page.getContent();

		if (list.size() > 0) {

			boolean hasAuditTypeAlreadyExist = false;

			for (AuditSystemDTO auditSystemDTO2 : list) {

				if (auditSystemDTO.getAuditTypeId() == auditSystemDTO2.getAuditTypeId()) {
					hasAuditTypeAlreadyExist = true;
				}

			}

			if (hasAuditTypeAlreadyExist) {
				throw new AlreadyExitsException("Audit already exist.");
			}
		}
		// --------------------------------------------------------------------------------------

		AuditSystemDTO result = auditSystemService.save(auditSystemDTO);
		return ResponseEntity
				.created(new URI("/api/audit-systems/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /audit-systems} : Updates an existing auditSystem.
	 *
	 * @param auditSystemDTO the auditSystemDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated auditSystemDTO, or with status {@code 400 (Bad Request)}
	 *         if the auditSystemDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the auditSystemDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/audit-systems")
	@PreAuthorize("hasAnyAuthority('AUDIT_EDIT')")
	public ResponseEntity<AuditSystemDTO> updateAuditSystem(@Valid @RequestBody AuditSystemDTO auditSystemDTO)
			throws URISyntaxException {
		log.debug("REST request to update AuditSystem : {}", auditSystemDTO);
		if (auditSystemDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		AuditSystemDTO result = auditSystemService.save(auditSystemDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				auditSystemDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /audit-systems} : get all the auditSystems.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of auditSystems in body.
	 */
	@GetMapping("/audit-systems")
	@PreAuthorize("hasAnyAuthority('AUDIT_LIST')")
	public ResponseEntity<List<AuditSystemDTO>> getAllAuditSystems(AuditSystemCriteria criteria, Pageable pageable) {
		log.debug("REST request to get AuditSystems by criteria: {}", criteria);
		Page<AuditSystemDTO> page = auditSystemQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /audit-systems/count} : count all the auditSystems.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/audit-systems/count")
	public ResponseEntity<Long> countAuditSystems(AuditSystemCriteria criteria) {
		log.debug("REST request to count AuditSystems by criteria: {}", criteria);
		return ResponseEntity.ok().body(auditSystemQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /audit-systems/:id} : get the "id" auditSystem.
	 *
	 * @param id the id of the auditSystemDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the auditSystemDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/audit-systems/{id}")
	public ResponseEntity<AuditSystemDTO> getAuditSystem(@PathVariable Long id) {
		log.debug("REST request to get AuditSystem : {}", id);
		Optional<AuditSystemDTO> auditSystemDTO = auditSystemService.findOne(id);
		return ResponseUtil.wrapOrNotFound(auditSystemDTO);
	}

	/**
	 * {@code DELETE  /audit-systems/:id} : delete the "id" auditSystem.
	 *
	 * @param id the id of the auditSystemDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/audit-systems/{id}")
	@PreAuthorize("hasAnyAuthority('AUDIT_EDIT')")
	public ResponseEntity<Void> deleteAuditSystem(@PathVariable Long id) {
		log.debug("REST request to delete AuditSystem : {}", id);
		auditSystemService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
