package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.SecurityRoleService;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.SecurityRoleDTO;
import com.techvg.covid.care.service.dto.SecurityRoleCriteria;
import com.techvg.covid.care.service.SecurityRoleQueryService;

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
 * REST controller for managing {@link com.techvg.covid.care.domain.SecurityRole}.
 */
@RestController
@RequestMapping("/api")
public class SecurityRoleResource {

    private final Logger log = LoggerFactory.getLogger(SecurityRoleResource.class);

    private static final String ENTITY_NAME = "securityRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecurityRoleService securityRoleService;

    private final SecurityRoleQueryService securityRoleQueryService;

    public SecurityRoleResource(SecurityRoleService securityRoleService, SecurityRoleQueryService securityRoleQueryService) {
        this.securityRoleService = securityRoleService;
        this.securityRoleQueryService = securityRoleQueryService;
    }

    /**
     * {@code POST  /security-roles} : Create a new securityRole.
     *
     * @param securityRoleDTO the securityRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new securityRoleDTO, or with status {@code 400 (Bad Request)} if the securityRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/security-roles")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<SecurityRoleDTO> createSecurityRole(@Valid @RequestBody SecurityRoleDTO securityRoleDTO) throws URISyntaxException {
        log.debug("REST request to save SecurityRole : {}", securityRoleDTO);
        if (securityRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new securityRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecurityRoleDTO result = securityRoleService.save(securityRoleDTO);
        return ResponseEntity.created(new URI("/api/security-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /security-roles} : Updates an existing securityRole.
     *
     * @param securityRoleDTO the securityRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityRoleDTO,
     * or with status {@code 400 (Bad Request)} if the securityRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the securityRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/security-roles")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<SecurityRoleDTO> updateSecurityRole(@Valid @RequestBody SecurityRoleDTO securityRoleDTO) throws URISyntaxException {
        log.debug("REST request to update SecurityRole : {}", securityRoleDTO);
        if (securityRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SecurityRoleDTO result = securityRoleService.save(securityRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, securityRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /security-roles} : get all the securityRoles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of securityRoles in body.
     */
    @GetMapping("/security-roles")
    public ResponseEntity<List<SecurityRoleDTO>> getAllSecurityRoles(SecurityRoleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SecurityRoles by criteria: {}", criteria);
        Page<SecurityRoleDTO> page = securityRoleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /security-roles/count} : count all the securityRoles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/security-roles/count")
    public ResponseEntity<Long> countSecurityRoles(SecurityRoleCriteria criteria) {
        log.debug("REST request to count SecurityRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(securityRoleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /security-roles/:id} : get the "id" securityRole.
     *
     * @param id the id of the securityRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the securityRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/security-roles/{id}")
    public ResponseEntity<SecurityRoleDTO> getSecurityRole(@PathVariable Long id) {
        log.debug("REST request to get SecurityRole : {}", id);
        Optional<SecurityRoleDTO> securityRoleDTO = securityRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(securityRoleDTO);
    }

    /**
     * {@code DELETE  /security-roles/:id} : delete the "id" securityRole.
     *
     * @param id the id of the securityRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/security-roles/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSecurityRole(@PathVariable Long id) {
        log.debug("REST request to delete SecurityRole : {}", id);
        securityRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
