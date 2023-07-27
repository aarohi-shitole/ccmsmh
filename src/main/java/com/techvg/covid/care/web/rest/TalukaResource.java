package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.TalukaService;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.TalukaDTO;
import com.techvg.covid.care.service.dto.TalukaCriteria;
import com.techvg.covid.care.service.TalukaQueryService;

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
 * REST controller for managing {@link com.techvg.covid.care.domain.Taluka}.
 */
@RestController
@RequestMapping("/api")
public class TalukaResource {

    private final Logger log = LoggerFactory.getLogger(TalukaResource.class);

    private static final String ENTITY_NAME = "taluka";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TalukaService talukaService;

    private final TalukaQueryService talukaQueryService;

    public TalukaResource(TalukaService talukaService, TalukaQueryService talukaQueryService) {
        this.talukaService = talukaService;
        this.talukaQueryService = talukaQueryService;
    }

    /**
     * {@code POST  /talukas} : Create a new taluka.
     *
     * @param talukaDTO the talukaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new talukaDTO, or with status {@code 400 (Bad Request)} if the taluka has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/talukas")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<TalukaDTO> createTaluka(@Valid @RequestBody TalukaDTO talukaDTO) throws URISyntaxException {
        log.debug("REST request to save Taluka : {}", talukaDTO);
        if (talukaDTO.getId() != null) {
            throw new BadRequestAlertException("A new taluka cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TalukaDTO result = talukaService.save(talukaDTO);
        return ResponseEntity.created(new URI("/api/talukas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /talukas} : Updates an existing taluka.
     *
     * @param talukaDTO the talukaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated talukaDTO,
     * or with status {@code 400 (Bad Request)} if the talukaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the talukaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/talukas")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<TalukaDTO> updateTaluka(@Valid @RequestBody TalukaDTO talukaDTO) throws URISyntaxException {
        log.debug("REST request to update Taluka : {}", talukaDTO);
        if (talukaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TalukaDTO result = talukaService.save(talukaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, talukaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /talukas} : get all the talukas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of talukas in body.
     */
    @GetMapping("/talukas")
    public ResponseEntity<List<TalukaDTO>> getAllTalukas(TalukaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Talukas by criteria: {}", criteria);
        Page<TalukaDTO> page = talukaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /talukas/count} : count all the talukas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/talukas/count")
    public ResponseEntity<Long> countTalukas(TalukaCriteria criteria) {
        log.debug("REST request to count Talukas by criteria: {}", criteria);
        return ResponseEntity.ok().body(talukaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /talukas/:id} : get the "id" taluka.
     *
     * @param id the id of the talukaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the talukaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/talukas/{id}")
    public ResponseEntity<TalukaDTO> getTaluka(@PathVariable Long id) {
        log.debug("REST request to get Taluka : {}", id);
        Optional<TalukaDTO> talukaDTO = talukaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(talukaDTO);
    }

    /**
     * {@code DELETE  /talukas/:id} : delete the "id" taluka.
     *
     * @param id the id of the talukaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/talukas/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteTaluka(@PathVariable Long id) {
        log.debug("REST request to delete Taluka : {}", id);
        talukaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
