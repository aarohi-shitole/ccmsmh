package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.service.SecurityUserService;
import com.techvg.covid.care.service.UserAccessQueryService;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import com.techvg.covid.care.service.dto.SecurityUserDTO;
import com.techvg.covid.care.service.dto.UserAccessCriteria;
import com.techvg.covid.care.service.dto.UserAccessCriteria.AccessLevelFilter;
import com.techvg.covid.care.service.dto.UserAccessDTO;
import com.techvg.covid.care.service.dto.HospitalCriteria;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.dto.SecurityRoleDTO;
import com.techvg.covid.care.service.dto.SecurityUserCriteria;
import com.techvg.covid.care.domain.enumeration.AccessLevel;
import com.techvg.covid.care.service.HospitalQueryService;
import com.techvg.covid.care.service.SecurityUserQueryService;

import io.github.jhipster.service.filter.LongFilter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing
 * {@link com.techvg.covid.care.domain.SecurityUser}.
 */
@RestController
@RequestMapping("/api")
public class SecurityUserResource {

	private final Logger log = LoggerFactory.getLogger(SecurityUserResource.class);

	private static final String ENTITY_NAME = "securityUser";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final SecurityUserService securityUserService;

	private final SecurityUserQueryService securityUserQueryService;

	private final HospitalQueryService hospitalQueryService;

	private final UserAccessQueryService accessQueryService;

	public SecurityUserResource(SecurityUserService securityUserService,
			SecurityUserQueryService securityUserQueryService, HospitalQueryService hospitalQueryService,
			UserAccessQueryService accessQueryService) {
		this.securityUserService = securityUserService;
		this.securityUserQueryService = securityUserQueryService;
		this.hospitalQueryService = hospitalQueryService;
		this.accessQueryService = accessQueryService;
	}

	/**
	 * {@code POST  /security-users} : Create a new securityUser.
	 *
	 * @param securityUserDTO the securityUserDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new securityUserDTO, or with status
	 *         {@code 400 (Bad Request)} if the securityUser has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/security-users")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','USERS_EDIT')")
	public ResponseEntity<SecurityUserDTO> createSecurityUser(@Valid @RequestBody SecurityUserDTO securityUserDTO)
			throws URISyntaxException {
		log.debug("REST request to save SecurityUser : {}", securityUserDTO);
		if (securityUserDTO.getId() != null) {
			throw new BadRequestAlertException("A new securityUser cannot already have an ID", ENTITY_NAME, "idexists");
		}
		SecurityUserDTO result = securityUserService.save(securityUserDTO);
		return ResponseEntity
				.created(new URI("/api/security-users/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /security-users} : Updates an existing securityUser.
	 *
	 * @param securityUserDTO the securityUserDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated securityUserDTO, or with status {@code 400 (Bad Request)}
	 *         if the securityUserDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the securityUserDTO couldn't
	 *         be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/security-users")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','USERS_EDIT')")
	public ResponseEntity<SecurityUserDTO> updateSecurityUser(@Valid @RequestBody SecurityUserDTO securityUserDTO)
			throws URISyntaxException {
		log.debug("REST request to update SecurityUser : {}", securityUserDTO);
		if (securityUserDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		SecurityUserDTO result = securityUserService.save(securityUserDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				securityUserDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /security-users} : get all the securityUsers.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of securityUsers in body.
	 */
	@GetMapping("/security-users")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','USERS_EDIT','USERS_LIST')")
	public ResponseEntity<List<SecurityUserDTO>> getAllSecurityUsers(SecurityUserCriteria criteria, Pageable pageable) {
		log.debug("REST request to get SecurityUsers by criteria: {}", criteria);
		Page<SecurityUserDTO> page = securityUserQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/district-securityuser")
	//@PreAuthorize("hasAuthority('HOSPITAL_EDIT')")
	//	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','USERS_EDIT','USERS_LIST')")
	public ResponseEntity<List<SecurityUserDTO>> getSecurityUser(Long district_id, Pageable pageable) {
		int cnt = 0;
		ArrayList<SecurityUserDTO> securityusersDtos = new ArrayList<>();
		Optional<SecurityUserDTO> securityuser;

		UserAccessCriteria criteria = new UserAccessCriteria();
		LongFilter districtfilter = new LongFilter();
		AccessLevelFilter levelfilter = new AccessLevelFilter();
		districtfilter.setEquals(district_id);
		levelfilter.setEquals(AccessLevel.DISTRICT);
		criteria.setAccessId(districtfilter);
		criteria.setLevel(levelfilter);

		Page<UserAccessDTO> page = accessQueryService.findByCriteria(criteria, pageable);

		List<UserAccessDTO> useraccess = page.getContent();
		int countActiveUsers = 0;
		for (int i = 0; i < useraccess.size(); i++) {
			if(countActiveUsers == 2 )
				break;

			securityuser = securityUserService.findOne(useraccess.get(i).getSecurityUserId());

			boolean isDistAdmin = false;
			Set<SecurityRoleDTO> securityRoles = securityuser.get().getSecurityRoles();
			for (SecurityRoleDTO securityRoleDTO : securityRoles) {
				if(securityRoleDTO.getName().equals("DISTRICT_DIV_ADMIN")) {
					isDistAdmin = true;
				}
				if(securityuser.get().getMobileNo()!=null && isDistAdmin) {
					securityusersDtos.add(securityuser.get());
					countActiveUsers++;
				}
			}
		}
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(securityusersDtos);
	}


	//    @GetMapping("/security-users")
	//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','USERS_EDIT','USERS_LIST')")
	//    public ResponseEntity<List<SecurityUserDTO>> getAllSecurityUsersoptions(SecurityUserCriteria criteria, Pageable pageable) {
	//        log.debug("REST request to get SecurityUsers by criteria: {}", criteria);
	//        Page<SecurityUserDTO> page = securityUserQueryService.findByCriteria(criteria, pageable);
	//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	//        return ResponseEntity.ok().headers(headers).body(page.getContent());
	//    }

	/**
	 * {@code GET  /security-users/count} : count all the securityUsers.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/security-users/count")   
	public ResponseEntity<Long> countSecurityUsers(SecurityUserCriteria criteria) {
		log.debug("REST request to count SecurityUsers by criteria: {}", criteria);
		return ResponseEntity.ok().body(securityUserQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /security-users/:id} : get the "id" securityUser.
	 *
	 * @param id the id of the securityUserDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *      the securityUserDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/security-users/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','USERS_EDIT','USERS_LIST')")
	public ResponseEntity<SecurityUserDTO> getSecurityUser(@PathVariable Long id) {
		log.debug("REST request to get SecurityUser : {}", id);
		Optional<SecurityUserDTO> securityUserDTO = securityUserService.findOne(id);
		return ResponseUtil.wrapOrNotFound(securityUserDTO);
	}

	/**
	 * {@code DELETE  /security-users/:id} : delete the "id" securityUser.
	 *
	 * @param id the id of the securityUserDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/security-users/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','USERS_EDIT')")
	public ResponseEntity<Void> deleteSecurityUser(@PathVariable Long id) {
		log.debug("REST request to delete SecurityUser : {}", id);
		securityUserService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
