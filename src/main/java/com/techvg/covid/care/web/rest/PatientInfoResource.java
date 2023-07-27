package com.techvg.covid.care.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.techvg.covid.care.domain.Count;
import com.techvg.covid.care.domain.PatientInfo;
import com.techvg.covid.care.service.PatientInfoQueryService;
import com.techvg.covid.care.service.PatientInfoService;
import com.techvg.covid.care.service.dto.PatientInfoCriteria;
import com.techvg.covid.care.service.dto.PatientInfoDTO;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.techvg.covid.care.domain.PatientInfo}.
 */
@RestController
@RequestMapping("/api")
public class PatientInfoResource {

	private final Logger log = LoggerFactory.getLogger(PatientInfoResource.class);

	private static final String ENTITY_NAME = "patientInfo";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PatientInfoService patientInfoService;

	private final PatientInfoQueryService patientInfoQueryService;

	public PatientInfoResource(PatientInfoService patientInfoService, PatientInfoQueryService patientInfoQueryService) {
		this.patientInfoService = patientInfoService;
		this.patientInfoQueryService = patientInfoQueryService;
	}

	/**
	 * {@code POST  /patient-infos} : Create a new patientInfo.
	 *
	 * @param patientInfoDTO the patientInfoDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new patientInfoDTO, or with status {@code 400 (Bad Request)}
	 *         if the patientInfo has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/patient-infos")
	public ResponseEntity<PatientInfoDTO> createPatientInfo(@Valid @RequestBody PatientInfoDTO patientInfoDTO)
			throws URISyntaxException {
		log.debug("REST request to save PatientInfo : {}", patientInfoDTO);
		if (patientInfoDTO.getId() != null) {
			throw new BadRequestAlertException("A new patientInfo cannot already have an ID", ENTITY_NAME, "idexists");
		}
		PatientInfoDTO result = patientInfoService.save(patientInfoDTO);
		return ResponseEntity
				.created(new URI("/api/patient-infos/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /patient-infos} : Updates an existing patientInfo.
	 *
	 * @param patientInfoDTO the patientInfoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated patientInfoDTO, or with status {@code 400 (Bad Request)}
	 *         if the patientInfoDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the patientInfoDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/patient-infos")
	public ResponseEntity<PatientInfoDTO> updatePatientInfo(@Valid @RequestBody PatientInfoDTO patientInfoDTO)
			throws URISyntaxException {
		log.debug("REST request to update PatientInfo : {}", patientInfoDTO);
		if (patientInfoDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PatientInfoDTO result = patientInfoService.save(patientInfoDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				patientInfoDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /patient-infos} : get all the patientInfos.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of patientInfos in body.
	 */
	@GetMapping("/patient-infos")
	public ResponseEntity<List<PatientInfoDTO>> getAllPatientInfos(PatientInfoCriteria criteria, Pageable pageable) {
		log.debug("REST request to get PatientInfos by criteria: {}", criteria);
		Page<PatientInfoDTO> page = patientInfoQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /patient-infos/count} : count all the patientInfos.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/patient-infos/count")
	public ResponseEntity<Count> countPatientInfos(PatientInfoCriteria criteria) {
		log.debug("REST request to count PatientInfos by criteria: {}", criteria);

		Count count = new Count();
		count.setCount(String.valueOf(patientInfoQueryService.countByCriteria(criteria)));

		return ResponseEntity.ok().body(count);
	}

	/**
	 * {@code GET  /patient-infos/count} : count all the patientInfos.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/patient-infos/lastupdatedon")
	public ResponseEntity<PatientInfo> getLastUpdatedOnPatientInfos() {

		PatientInfo patientInfo = patientInfoService.findByOrderByCcmsPullDateByDesc();
		return ResponseEntity.ok().body(patientInfo);
	}

	/**
	 * {@code GET  /patient-infos/:id} : get the "id" patientInfo.
	 *
	 * @param id the id of the patientInfoDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the patientInfoDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/patient-infos/{id}")
	public ResponseEntity<PatientInfoDTO> getPatientInfo(@PathVariable Long id) {
		log.debug("REST request to get PatientInfo : {}", id);
		Optional<PatientInfoDTO> patientInfoDTO = patientInfoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(patientInfoDTO);
	}

	/**
	 * {@code DELETE  /patient-infos/:id} : delete the "id" patientInfo.
	 *
	 * @param id the id of the patientInfoDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/patient-infos/{id}")
	public ResponseEntity<Void> deletePatientInfo(@PathVariable Long id) {
		log.debug("REST request to delete PatientInfo : {}", id);
		patientInfoService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
