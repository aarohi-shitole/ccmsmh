package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.SecurityPermission;
import com.techvg.covid.care.domain.SecurityRole;
import com.techvg.covid.care.domain.SecurityUser;
import com.techvg.covid.care.repository.SecurityPermissionRepository;
import com.techvg.covid.care.service.SecurityPermissionService;
import com.techvg.covid.care.service.dto.SecurityPermissionDTO;
import com.techvg.covid.care.service.mapper.SecurityPermissionMapper;
import com.techvg.covid.care.service.dto.SecurityPermissionCriteria;
import com.techvg.covid.care.service.SecurityPermissionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SecurityPermissionResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SecurityPermissionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private SecurityPermissionRepository securityPermissionRepository;

    @Autowired
    private SecurityPermissionMapper securityPermissionMapper;

    @Autowired
    private SecurityPermissionService securityPermissionService;

    @Autowired
    private SecurityPermissionQueryService securityPermissionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecurityPermissionMockMvc;

    private SecurityPermission securityPermission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityPermission createEntity(EntityManager em) {
        SecurityPermission securityPermission = new SecurityPermission()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return securityPermission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityPermission createUpdatedEntity(EntityManager em) {
        SecurityPermission securityPermission = new SecurityPermission()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return securityPermission;
    }

    @BeforeEach
    public void initTest() {
        securityPermission = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecurityPermission() throws Exception {
        int databaseSizeBeforeCreate = securityPermissionRepository.findAll().size();
        // Create the SecurityPermission
        SecurityPermissionDTO securityPermissionDTO = securityPermissionMapper.toDto(securityPermission);
        restSecurityPermissionMockMvc.perform(post("/api/security-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityPermissionDTO)))
            .andExpect(status().isCreated());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeCreate + 1);
        SecurityPermission testSecurityPermission = securityPermissionList.get(securityPermissionList.size() - 1);
        assertThat(testSecurityPermission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSecurityPermission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSecurityPermission.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSecurityPermission.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createSecurityPermissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = securityPermissionRepository.findAll().size();

        // Create the SecurityPermission with an existing ID
        securityPermission.setId(1L);
        SecurityPermissionDTO securityPermissionDTO = securityPermissionMapper.toDto(securityPermission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecurityPermissionMockMvc.perform(post("/api/security-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityPermissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityPermissionRepository.findAll().size();
        // set the field null
        securityPermission.setName(null);

        // Create the SecurityPermission, which fails.
        SecurityPermissionDTO securityPermissionDTO = securityPermissionMapper.toDto(securityPermission);


        restSecurityPermissionMockMvc.perform(post("/api/security-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityPermissionDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityPermissionRepository.findAll().size();
        // set the field null
        securityPermission.setLastModified(null);

        // Create the SecurityPermission, which fails.
        SecurityPermissionDTO securityPermissionDTO = securityPermissionMapper.toDto(securityPermission);


        restSecurityPermissionMockMvc.perform(post("/api/security-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityPermissionDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityPermissionRepository.findAll().size();
        // set the field null
        securityPermission.setLastModifiedBy(null);

        // Create the SecurityPermission, which fails.
        SecurityPermissionDTO securityPermissionDTO = securityPermissionMapper.toDto(securityPermission);


        restSecurityPermissionMockMvc.perform(post("/api/security-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityPermissionDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissions() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList
        restSecurityPermissionMockMvc.perform(get("/api/security-permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securityPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getSecurityPermission() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get the securityPermission
        restSecurityPermissionMockMvc.perform(get("/api/security-permissions/{id}", securityPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(securityPermission.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getSecurityPermissionsByIdFiltering() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        Long id = securityPermission.getId();

        defaultSecurityPermissionShouldBeFound("id.equals=" + id);
        defaultSecurityPermissionShouldNotBeFound("id.notEquals=" + id);

        defaultSecurityPermissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSecurityPermissionShouldNotBeFound("id.greaterThan=" + id);

        defaultSecurityPermissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSecurityPermissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSecurityPermissionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where name equals to DEFAULT_NAME
        defaultSecurityPermissionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the securityPermissionList where name equals to UPDATED_NAME
        defaultSecurityPermissionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where name not equals to DEFAULT_NAME
        defaultSecurityPermissionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the securityPermissionList where name not equals to UPDATED_NAME
        defaultSecurityPermissionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSecurityPermissionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the securityPermissionList where name equals to UPDATED_NAME
        defaultSecurityPermissionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where name is not null
        defaultSecurityPermissionShouldBeFound("name.specified=true");

        // Get all the securityPermissionList where name is null
        defaultSecurityPermissionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityPermissionsByNameContainsSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where name contains DEFAULT_NAME
        defaultSecurityPermissionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the securityPermissionList where name contains UPDATED_NAME
        defaultSecurityPermissionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where name does not contain DEFAULT_NAME
        defaultSecurityPermissionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the securityPermissionList where name does not contain UPDATED_NAME
        defaultSecurityPermissionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSecurityPermissionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where description equals to DEFAULT_DESCRIPTION
        defaultSecurityPermissionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the securityPermissionList where description equals to UPDATED_DESCRIPTION
        defaultSecurityPermissionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where description not equals to DEFAULT_DESCRIPTION
        defaultSecurityPermissionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the securityPermissionList where description not equals to UPDATED_DESCRIPTION
        defaultSecurityPermissionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSecurityPermissionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the securityPermissionList where description equals to UPDATED_DESCRIPTION
        defaultSecurityPermissionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where description is not null
        defaultSecurityPermissionShouldBeFound("description.specified=true");

        // Get all the securityPermissionList where description is null
        defaultSecurityPermissionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityPermissionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where description contains DEFAULT_DESCRIPTION
        defaultSecurityPermissionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the securityPermissionList where description contains UPDATED_DESCRIPTION
        defaultSecurityPermissionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where description does not contain DEFAULT_DESCRIPTION
        defaultSecurityPermissionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the securityPermissionList where description does not contain UPDATED_DESCRIPTION
        defaultSecurityPermissionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSecurityPermissionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSecurityPermissionShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the securityPermissionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSecurityPermissionShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultSecurityPermissionShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the securityPermissionList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultSecurityPermissionShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSecurityPermissionShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the securityPermissionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSecurityPermissionShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where lastModified is not null
        defaultSecurityPermissionShouldBeFound("lastModified.specified=true");

        // Get all the securityPermissionList where lastModified is null
        defaultSecurityPermissionShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSecurityPermissionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityPermissionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSecurityPermissionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultSecurityPermissionShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityPermissionList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultSecurityPermissionShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSecurityPermissionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the securityPermissionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSecurityPermissionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where lastModifiedBy is not null
        defaultSecurityPermissionShouldBeFound("lastModifiedBy.specified=true");

        // Get all the securityPermissionList where lastModifiedBy is null
        defaultSecurityPermissionShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityPermissionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSecurityPermissionShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityPermissionList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSecurityPermissionShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityPermissionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        // Get all the securityPermissionList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSecurityPermissionShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityPermissionList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSecurityPermissionShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllSecurityPermissionsBySecurityRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);
        SecurityRole securityRole = SecurityRoleResourceIT.createEntity(em);
        em.persist(securityRole);
        em.flush();
        securityPermission.addSecurityRole(securityRole);
        securityPermissionRepository.saveAndFlush(securityPermission);
        Long securityRoleId = securityRole.getId();

        // Get all the securityPermissionList where securityRole equals to securityRoleId
        defaultSecurityPermissionShouldBeFound("securityRoleId.equals=" + securityRoleId);

        // Get all the securityPermissionList where securityRole equals to securityRoleId + 1
        defaultSecurityPermissionShouldNotBeFound("securityRoleId.equals=" + (securityRoleId + 1));
    }


    @Test
    @Transactional
    public void getAllSecurityPermissionsBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);
        SecurityUser securityUser = SecurityUserResourceIT.createEntity(em);
        em.persist(securityUser);
        em.flush();
        securityPermission.addSecurityUser(securityUser);
        securityPermissionRepository.saveAndFlush(securityPermission);
        Long securityUserId = securityUser.getId();

        // Get all the securityPermissionList where securityUser equals to securityUserId
        defaultSecurityPermissionShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the securityPermissionList where securityUser equals to securityUserId + 1
        defaultSecurityPermissionShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSecurityPermissionShouldBeFound(String filter) throws Exception {
        restSecurityPermissionMockMvc.perform(get("/api/security-permissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securityPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restSecurityPermissionMockMvc.perform(get("/api/security-permissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSecurityPermissionShouldNotBeFound(String filter) throws Exception {
        restSecurityPermissionMockMvc.perform(get("/api/security-permissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSecurityPermissionMockMvc.perform(get("/api/security-permissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSecurityPermission() throws Exception {
        // Get the securityPermission
        restSecurityPermissionMockMvc.perform(get("/api/security-permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecurityPermission() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();

        // Update the securityPermission
        SecurityPermission updatedSecurityPermission = securityPermissionRepository.findById(securityPermission.getId()).get();
        // Disconnect from session so that the updates on updatedSecurityPermission are not directly saved in db
        em.detach(updatedSecurityPermission);
        updatedSecurityPermission
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        SecurityPermissionDTO securityPermissionDTO = securityPermissionMapper.toDto(updatedSecurityPermission);

        restSecurityPermissionMockMvc.perform(put("/api/security-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityPermissionDTO)))
            .andExpect(status().isOk());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
        SecurityPermission testSecurityPermission = securityPermissionList.get(securityPermissionList.size() - 1);
        assertThat(testSecurityPermission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSecurityPermission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSecurityPermission.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSecurityPermission.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingSecurityPermission() throws Exception {
        int databaseSizeBeforeUpdate = securityPermissionRepository.findAll().size();

        // Create the SecurityPermission
        SecurityPermissionDTO securityPermissionDTO = securityPermissionMapper.toDto(securityPermission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecurityPermissionMockMvc.perform(put("/api/security-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityPermissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecurityPermission in the database
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSecurityPermission() throws Exception {
        // Initialize the database
        securityPermissionRepository.saveAndFlush(securityPermission);

        int databaseSizeBeforeDelete = securityPermissionRepository.findAll().size();

        // Delete the securityPermission
        restSecurityPermissionMockMvc.perform(delete("/api/security-permissions/{id}", securityPermission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SecurityPermission> securityPermissionList = securityPermissionRepository.findAll();
        assertThat(securityPermissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
