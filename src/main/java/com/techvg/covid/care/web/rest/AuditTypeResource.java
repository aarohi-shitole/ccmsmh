package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.AuditTypeService;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.AuditTypeDTO;
import com.techvg.covid.care.service.dto.AuditTypeCriteria;
import com.techvg.covid.care.service.AuditTypeQueryService;

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
 * REST controller for managing {@link com.techvg.covid.care.domain.AuditType}.
 */
@RestController
@RequestMapping("/api")
public class AuditTypeResource {

    private final Logger log = LoggerFactory.getLogger(AuditTypeResource.class);

    private static final String ENTITY_NAME = "auditType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditTypeService auditTypeService;

    private final AuditTypeQueryService auditTypeQueryService;

    public AuditTypeResource(AuditTypeService auditTypeService, AuditTypeQueryService auditTypeQueryService) {
        this.auditTypeService = auditTypeService;
        this.auditTypeQueryService = auditTypeQueryService;
    }

    /**
     * {@code POST  /audit-types} : Create a new auditType.
     *
     * @param auditTypeDTO the auditTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditTypeDTO, or with status {@code 400 (Bad Request)} if the auditType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/audit-types")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<AuditTypeDTO> createAuditType(@Valid @RequestBody AuditTypeDTO auditTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AuditType : {}", auditTypeDTO);
        if (auditTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new auditType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditTypeDTO result = auditTypeService.save(auditTypeDTO);
        return ResponseEntity.created(new URI("/api/audit-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /audit-types} : Updates an existing auditType.
     *
     * @param auditTypeDTO the auditTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditTypeDTO,
     * or with status {@code 400 (Bad Request)} if the auditTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/audit-types")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<AuditTypeDTO> updateAuditType(@Valid @RequestBody AuditTypeDTO auditTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AuditType : {}", auditTypeDTO);
        if (auditTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AuditTypeDTO result = auditTypeService.save(auditTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, auditTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /audit-types} : get all the auditTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditTypes in body.
     */
    @GetMapping("/audit-types")
    public ResponseEntity<List<AuditTypeDTO>> getAllAuditTypes(AuditTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AuditTypes by criteria: {}", criteria);
        Page<AuditTypeDTO> page = auditTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /audit-types/count} : count all the auditTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/audit-types/count")
    public ResponseEntity<Long> countAuditTypes(AuditTypeCriteria criteria) {
        log.debug("REST request to count AuditTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(auditTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /audit-types/:id} : get the "id" auditType.
     *
     * @param id the id of the auditTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/audit-types/{id}")
    public ResponseEntity<AuditTypeDTO> getAuditType(@PathVariable Long id) {
        log.debug("REST request to get AuditType : {}", id);
        Optional<AuditTypeDTO> auditTypeDTO = auditTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditTypeDTO);
    }

    /**
     * {@code DELETE  /audit-types/:id} : delete the "id" auditType.
     *
     * @param id the id of the auditTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/audit-types/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteAuditType(@PathVariable Long id) {
        log.debug("REST request to delete AuditType : {}", id);
        auditTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
