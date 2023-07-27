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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.techvg.covid.care.odas.model.IsolationCount;
import com.techvg.covid.care.odas.model.ResponseMessage;

import com.techvg.covid.care.service.IsolationService;
import com.techvg.covid.care.service.dto.IsolationCriteria;
import com.techvg.covid.care.service.dto.IsolationDTO;
import com.techvg.covid.care.service.dto.IsolationDashboardCount;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.techvg.covid.care.isolation.domain.Isolation}.
 */
@RestController
@RequestMapping("/api")
public class IsolationResource {

	private final Logger log = LoggerFactory.getLogger(IsolationResource.class);

	private static final String ENTITY_NAME = "isolation";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final IsolationService isolationService;


	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static String TYPE1 = "application/vnd.ms-excel";

	
	public IsolationResource(IsolationService isolationService) {
		this.isolationService = isolationService;
		
	}

	public boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	/**
	 * {@code POST  /isolations} : Create a new isolation.
	 *
	 * @param isolationDTO the isolationDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new isolationDTO, or with status {@code 400 (Bad Request)}
	 *         if the isolation has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/isolations")
	@PreAuthorize("hasAuthority('ISOLATION_EDIT')")
	public ResponseEntity<IsolationDTO> createIsolation(@Valid @RequestBody IsolationDTO isolationDTO)
			throws URISyntaxException {
		log.debug("REST request to save Isolation : {}", isolationDTO);
		if (isolationDTO.getId() != null) {
			throw new BadRequestAlertException("A new isolation cannot already have an ID", ENTITY_NAME, "idexists");
		}
		IsolationDTO result = isolationService.save(isolationDTO);
		return ResponseEntity
				.created(new URI("/api/isolations/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@PostMapping("/isolations/upload")
	@PreAuthorize("hasAuthority('ISOLATION_EDIT')")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (hasExcelFormat(file)) {
			try {
				isolationService.save(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getName() + "!";
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload an excel file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	/**
	 * {@code PUT  /isolations} : Updates an existing isolation.
	 *
	 * @param isolationDTO the isolationDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated isolationDTO, or with status {@code 400 (Bad Request)} if
	 *         the isolationDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the isolationDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/isolations")
	@PreAuthorize("hasAuthority('ISOLATION_EDIT')")
	public ResponseEntity<IsolationDTO> updateIsolation(@Valid @RequestBody IsolationDTO isolationDTO)
			throws URISyntaxException {
		log.debug("REST request to update Isolation : {}", isolationDTO);
		if (isolationDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		IsolationDTO result = isolationService.save(isolationDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				isolationDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /isolations} : get all the isolations.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of isolations in body.
	 */
	@GetMapping("/isolations")
	@PreAuthorize("hasAnyAuthority('ISOLATION_EDIT','DASHBOARD_ISOLATION')")
	public ResponseEntity<List<IsolationDTO>> getAllIsolations(IsolationCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Isolations by criteria: {}", criteria);
		
		return isolationService.findByCriteria(criteria, pageable);
	}

	/**
	 * {@code GET  /isolations/count} : count all the isolations.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/isolations/count")
	@PreAuthorize("hasAnyAuthority('ISOLATION_VIEW','DASHBOARD_ISOLATION')")
	public ResponseEntity<?> countIsolations(IsolationCriteria criteria) {
		log.debug("REST request to count Isolations by criteria: {}", criteria);
		return ResponseEntity.ok().body(isolationService.countByCriteria(false,criteria));
	}
	
	
	/**
	 * {@code GET  /isolations/count} : count all the isolations.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/isolations/count/district")
	@PreAuthorize("hasAnyAuthority('ISOLATION_VIEW','DASHBOARD_ISOLATION')")
	public ResponseEntity<?> countOfDistrictIsolations(IsolationCriteria criteria) {
		log.debug("REST request to count Isolations by criteria: {}", criteria);
		return ResponseEntity.ok().body(isolationService.countByCriteria(true,criteria));
	}

	/**
	 * {@code GET  /isolations/:id} : get the "id" isolation.
	 *
	 * @param id the id of the isolationDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the isolationDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/isolations/{id}")
	@PreAuthorize("hasAuthority('ISOLATION_VIEW')")
	public ResponseEntity<IsolationDTO> getIsolation(@PathVariable Long id) {
		log.debug("REST request to get Isolation : {}", id);
		Optional<IsolationDTO> isolationDTO = isolationService.findOne(id);
		return ResponseUtil.wrapOrNotFound(isolationDTO);
	}

//	/**
//	 * {@code DELETE  /isolations/:id} : delete the "id" isolation.
//	 *
//	 * @param id the id of the isolationDTO to delete.
//	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//	 */
//	@DeleteMapping("/isolations/{id}")
//	public ResponseEntity<Void> deleteIsolation(@PathVariable Long id) {
//		log.debug("REST request to delete Isolation : {}", id);
//		isolationService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
//	}
}
