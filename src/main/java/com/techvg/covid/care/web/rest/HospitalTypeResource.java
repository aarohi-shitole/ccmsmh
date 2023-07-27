package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.HospitalTypeService;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.HospitalTypeDTO;
import com.techvg.covid.care.service.dto.HospitalTypeCriteria;
import com.techvg.covid.care.service.HospitalTypeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.HospitalType}.
 */
@RestController
@RequestMapping("/api")
public class HospitalTypeResource {

    private final Logger log = LoggerFactory.getLogger(HospitalTypeResource.class);

    private static final String ENTITY_NAME = "hospitalType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HospitalTypeService hospitalTypeService;

    private final HospitalTypeQueryService hospitalTypeQueryService;

    public HospitalTypeResource(HospitalTypeService hospitalTypeService, HospitalTypeQueryService hospitalTypeQueryService) {
        this.hospitalTypeService = hospitalTypeService;
        this.hospitalTypeQueryService = hospitalTypeQueryService;
    }

    /**
     * {@code POST  /hospital-types} : Create a new hospitalType.
     *
     * @param hospitalTypeDTO the hospitalTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hospitalTypeDTO, or with status {@code 400 (Bad Request)} if the hospitalType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hospital-types")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<HospitalTypeDTO> createHospitalType(@Valid @RequestBody HospitalTypeDTO hospitalTypeDTO) throws URISyntaxException {
        log.debug("REST request to save HospitalType : {}", hospitalTypeDTO);
        if (hospitalTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new hospitalType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HospitalTypeDTO result = hospitalTypeService.save(hospitalTypeDTO);
        return ResponseEntity.created(new URI("/api/hospital-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hospital-types} : Updates an existing hospitalType.
     *
     * @param hospitalTypeDTO the hospitalTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalTypeDTO,
     * or with status {@code 400 (Bad Request)} if the hospitalTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hospitalTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hospital-types")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<HospitalTypeDTO> updateHospitalType(@Valid @RequestBody HospitalTypeDTO hospitalTypeDTO) throws URISyntaxException {
        log.debug("REST request to update HospitalType : {}", hospitalTypeDTO);
        if (hospitalTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HospitalTypeDTO result = hospitalTypeService.save(hospitalTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hospitalTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hospital-types} : get all the hospitalTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hospitalTypes in body.
     */
    @GetMapping("/hospital-types")
    public ResponseEntity<List<HospitalTypeDTO>> getAllHospitalTypes(HospitalTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get HospitalTypes by criteria: {}", criteria);
        Page<HospitalTypeDTO> page = hospitalTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hospital-types/count} : count all the hospitalTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/hospital-types/count")
    public ResponseEntity<Long> countHospitalTypes(HospitalTypeCriteria criteria) {
        log.debug("REST request to count HospitalTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(hospitalTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hospital-types/:id} : get the "id" hospitalType.
     *
     * @param id the id of the hospitalTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hospitalTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hospital-types/{id}")
    public ResponseEntity<HospitalTypeDTO> getHospitalType(@PathVariable Long id) {
        log.debug("REST request to get HospitalType : {}", id);
        Optional<HospitalTypeDTO> hospitalTypeDTO = hospitalTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hospitalTypeDTO);
    }

    /**
     * {@code DELETE  /hospital-types/:id} : delete the "id" hospitalType.
     *
     * @param id the id of the hospitalTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hospital-types/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteHospitalType(@PathVariable Long id) {
        log.debug("REST request to delete HospitalType : {}", id);
        hospitalTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
