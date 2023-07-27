package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.BedTypeService;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.BedTypeDTO;
import com.techvg.covid.care.service.dto.BedTypeCriteria;
import com.techvg.covid.care.service.BedTypeQueryService;

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
 * REST controller for managing {@link com.techvg.covid.care.domain.BedType}.
 */
@RestController
@RequestMapping("/api")
public class BedTypeResource {

    private final Logger log = LoggerFactory.getLogger(BedTypeResource.class);

    private static final String ENTITY_NAME = "bedType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BedTypeService bedTypeService;

    private final BedTypeQueryService bedTypeQueryService;

    public BedTypeResource(BedTypeService bedTypeService, BedTypeQueryService bedTypeQueryService) {
        this.bedTypeService = bedTypeService;
        this.bedTypeQueryService = bedTypeQueryService;
    }

    /**
     * {@code POST  /bed-types} : Create a new bedType.
     *
     * @param bedTypeDTO the bedTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bedTypeDTO, or with status {@code 400 (Bad Request)} if the bedType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bed-types")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<BedTypeDTO> createBedType(@Valid @RequestBody BedTypeDTO bedTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BedType : {}", bedTypeDTO);
        if (bedTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new bedType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BedTypeDTO result = bedTypeService.save(bedTypeDTO);
        return ResponseEntity.created(new URI("/api/bed-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bed-types} : Updates an existing bedType.
     *
     * @param bedTypeDTO the bedTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedTypeDTO,
     * or with status {@code 400 (Bad Request)} if the bedTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bedTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bed-types")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<BedTypeDTO> updateBedType(@Valid @RequestBody BedTypeDTO bedTypeDTO) throws URISyntaxException {
        log.debug("REST request to update BedType : {}", bedTypeDTO);
        if (bedTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BedTypeDTO result = bedTypeService.save(bedTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bedTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bed-types} : get all the bedTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bedTypes in body.
     */
    @GetMapping("/bed-types")
    public ResponseEntity<List<BedTypeDTO>> getAllBedTypes(BedTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BedTypes by criteria: {}", criteria);
        Page<BedTypeDTO> page = bedTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bed-types/count} : count all the bedTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bed-types/count")
    public ResponseEntity<Long> countBedTypes(BedTypeCriteria criteria) {
        log.debug("REST request to count BedTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(bedTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bed-types/:id} : get the "id" bedType.
     *
     * @param id the id of the bedTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bedTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bed-types/{id}")
    public ResponseEntity<BedTypeDTO> getBedType(@PathVariable Long id) {
        log.debug("REST request to get BedType : {}", id);
        Optional<BedTypeDTO> bedTypeDTO = bedTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bedTypeDTO);
    }

    /**
     * {@code DELETE  /bed-types/:id} : delete the "id" bedType.
     *
     * @param id the id of the bedTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bed-types/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteBedType(@PathVariable Long id) {
        log.debug("REST request to delete BedType : {}", id);
        bedTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
