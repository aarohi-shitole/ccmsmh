package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.SecurityRole;
import com.techvg.covid.care.domain.SecurityPermission;
import com.techvg.covid.care.domain.SecurityUser;
import com.techvg.covid.care.repository.SecurityRoleRepository;
import com.techvg.covid.care.service.SecurityRoleService;
import com.techvg.covid.care.service.dto.SecurityRoleDTO;
import com.techvg.covid.care.service.mapper.SecurityRoleMapper;
import com.techvg.covid.care.service.dto.SecurityRoleCriteria;
import com.techvg.covid.care.service.SecurityRoleQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SecurityRoleResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SecurityRoleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private SecurityRoleRepository securityRoleRepository;

    @Mock
    private SecurityRoleRepository securityRoleRepositoryMock;

    @Autowired
    private SecurityRoleMapper securityRoleMapper;

    @Mock
    private SecurityRoleService securityRoleServiceMock;

    @Autowired
    private SecurityRoleService securityRoleService;

    @Autowired
    private SecurityRoleQueryService securityRoleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecurityRoleMockMvc;

    private SecurityRole securityRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityRole createEntity(EntityManager em) {
        SecurityRole securityRole = new SecurityRole()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return securityRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityRole createUpdatedEntity(EntityManager em) {
        SecurityRole securityRole = new SecurityRole()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return securityRole;
    }

    @BeforeEach
    public void initTest() {
        securityRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecurityRole() throws Exception {
        int databaseSizeBeforeCreate = securityRoleRepository.findAll().size();
        // Create the SecurityRole
        SecurityRoleDTO securityRoleDTO = securityRoleMapper.toDto(securityRole);
        restSecurityRoleMockMvc.perform(post("/api/security-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the SecurityRole in the database
        List<SecurityRole> securityRoleList = securityRoleRepository.findAll();
        assertThat(securityRoleList).hasSize(databaseSizeBeforeCreate + 1);
        SecurityRole testSecurityRole = securityRoleList.get(securityRoleList.size() - 1);
        assertThat(testSecurityRole.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSecurityRole.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSecurityRole.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSecurityRole.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createSecurityRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = securityRoleRepository.findAll().size();

        // Create the SecurityRole with an existing ID
        securityRole.setId(1L);
        SecurityRoleDTO securityRoleDTO = securityRoleMapper.toDto(securityRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecurityRoleMockMvc.perform(post("/api/security-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecurityRole in the database
        List<SecurityRole> securityRoleList = securityRoleRepository.findAll();
        assertThat(securityRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityRoleRepository.findAll().size();
        // set the field null
        securityRole.setName(null);

        // Create the SecurityRole, which fails.
        SecurityRoleDTO securityRoleDTO = securityRoleMapper.toDto(securityRole);


        restSecurityRoleMockMvc.perform(post("/api/security-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityRoleDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityRole> securityRoleList = securityRoleRepository.findAll();
        assertThat(securityRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityRoleRepository.findAll().size();
        // set the field null
        securityRole.setLastModified(null);

        // Create the SecurityRole, which fails.
        SecurityRoleDTO securityRoleDTO = securityRoleMapper.toDto(securityRole);


        restSecurityRoleMockMvc.perform(post("/api/security-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityRoleDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityRole> securityRoleList = securityRoleRepository.findAll();
        assertThat(securityRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityRoleRepository.findAll().size();
        // set the field null
        securityRole.setLastModifiedBy(null);

        // Create the SecurityRole, which fails.
        SecurityRoleDTO securityRoleDTO = securityRoleMapper.toDto(securityRole);


        restSecurityRoleMockMvc.perform(post("/api/security-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityRoleDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityRole> securityRoleList = securityRoleRepository.findAll();
        assertThat(securityRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSecurityRoles() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList
        restSecurityRoleMockMvc.perform(get("/api/security-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securityRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSecurityRolesWithEagerRelationshipsIsEnabled() throws Exception {
        when(securityRoleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSecurityRoleMockMvc.perform(get("/api/security-roles?eagerload=true"))
            .andExpect(status().isOk());

        verify(securityRoleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSecurityRolesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(securityRoleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSecurityRoleMockMvc.perform(get("/api/security-roles?eagerload=true"))
            .andExpect(status().isOk());

        verify(securityRoleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSecurityRole() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get the securityRole
        restSecurityRoleMockMvc.perform(get("/api/security-roles/{id}", securityRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(securityRole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getSecurityRolesByIdFiltering() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        Long id = securityRole.getId();

        defaultSecurityRoleShouldBeFound("id.equals=" + id);
        defaultSecurityRoleShouldNotBeFound("id.notEquals=" + id);

        defaultSecurityRoleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSecurityRoleShouldNotBeFound("id.greaterThan=" + id);

        defaultSecurityRoleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSecurityRoleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSecurityRolesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where name equals to DEFAULT_NAME
        defaultSecurityRoleShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the securityRoleList where name equals to UPDATED_NAME
        defaultSecurityRoleShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where name not equals to DEFAULT_NAME
        defaultSecurityRoleShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the securityRoleList where name not equals to UPDATED_NAME
        defaultSecurityRoleShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSecurityRoleShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the securityRoleList where name equals to UPDATED_NAME
        defaultSecurityRoleShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where name is not null
        defaultSecurityRoleShouldBeFound("name.specified=true");

        // Get all the securityRoleList where name is null
        defaultSecurityRoleShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityRolesByNameContainsSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where name contains DEFAULT_NAME
        defaultSecurityRoleShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the securityRoleList where name contains UPDATED_NAME
        defaultSecurityRoleShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where name does not contain DEFAULT_NAME
        defaultSecurityRoleShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the securityRoleList where name does not contain UPDATED_NAME
        defaultSecurityRoleShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSecurityRolesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where description equals to DEFAULT_DESCRIPTION
        defaultSecurityRoleShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the securityRoleList where description equals to UPDATED_DESCRIPTION
        defaultSecurityRoleShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where description not equals to DEFAULT_DESCRIPTION
        defaultSecurityRoleShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the securityRoleList where description not equals to UPDATED_DESCRIPTION
        defaultSecurityRoleShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSecurityRoleShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the securityRoleList where description equals to UPDATED_DESCRIPTION
        defaultSecurityRoleShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where description is not null
        defaultSecurityRoleShouldBeFound("description.specified=true");

        // Get all the securityRoleList where description is null
        defaultSecurityRoleShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityRolesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where description contains DEFAULT_DESCRIPTION
        defaultSecurityRoleShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the securityRoleList where description contains UPDATED_DESCRIPTION
        defaultSecurityRoleShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where description does not contain DEFAULT_DESCRIPTION
        defaultSecurityRoleShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the securityRoleList where description does not contain UPDATED_DESCRIPTION
        defaultSecurityRoleShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSecurityRolesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSecurityRoleShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the securityRoleList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSecurityRoleShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultSecurityRoleShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the securityRoleList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultSecurityRoleShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSecurityRoleShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the securityRoleList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSecurityRoleShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where lastModified is not null
        defaultSecurityRoleShouldBeFound("lastModified.specified=true");

        // Get all the securityRoleList where lastModified is null
        defaultSecurityRoleShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSecurityRoleShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityRoleList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSecurityRoleShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultSecurityRoleShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityRoleList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultSecurityRoleShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSecurityRoleShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the securityRoleList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSecurityRoleShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where lastModifiedBy is not null
        defaultSecurityRoleShouldBeFound("lastModifiedBy.specified=true");

        // Get all the securityRoleList where lastModifiedBy is null
        defaultSecurityRoleShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityRolesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSecurityRoleShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityRoleList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSecurityRoleShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityRolesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        // Get all the securityRoleList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSecurityRoleShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityRoleList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSecurityRoleShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllSecurityRolesBySecurityPermissionIsEqualToSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);
        SecurityPermission securityPermission = SecurityPermissionResourceIT.createEntity(em);
        em.persist(securityPermission);
        em.flush();
        securityRole.addSecurityPermission(securityPermission);
        securityRoleRepository.saveAndFlush(securityRole);
        Long securityPermissionId = securityPermission.getId();

        // Get all the securityRoleList where securityPermission equals to securityPermissionId
        defaultSecurityRoleShouldBeFound("securityPermissionId.equals=" + securityPermissionId);

        // Get all the securityRoleList where securityPermission equals to securityPermissionId + 1
        defaultSecurityRoleShouldNotBeFound("securityPermissionId.equals=" + (securityPermissionId + 1));
    }


    @Test
    @Transactional
    public void getAllSecurityRolesBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);
        SecurityUser securityUser = SecurityUserResourceIT.createEntity(em);
        em.persist(securityUser);
        em.flush();
        securityRole.addSecurityUser(securityUser);
        securityRoleRepository.saveAndFlush(securityRole);
        Long securityUserId = securityUser.getId();

        // Get all the securityRoleList where securityUser equals to securityUserId
        defaultSecurityRoleShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the securityRoleList where securityUser equals to securityUserId + 1
        defaultSecurityRoleShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSecurityRoleShouldBeFound(String filter) throws Exception {
        restSecurityRoleMockMvc.perform(get("/api/security-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securityRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restSecurityRoleMockMvc.perform(get("/api/security-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSecurityRoleShouldNotBeFound(String filter) throws Exception {
        restSecurityRoleMockMvc.perform(get("/api/security-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSecurityRoleMockMvc.perform(get("/api/security-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSecurityRole() throws Exception {
        // Get the securityRole
        restSecurityRoleMockMvc.perform(get("/api/security-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecurityRole() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        int databaseSizeBeforeUpdate = securityRoleRepository.findAll().size();

        // Update the securityRole
        SecurityRole updatedSecurityRole = securityRoleRepository.findById(securityRole.getId()).get();
        // Disconnect from session so that the updates on updatedSecurityRole are not directly saved in db
        em.detach(updatedSecurityRole);
        updatedSecurityRole
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        SecurityRoleDTO securityRoleDTO = securityRoleMapper.toDto(updatedSecurityRole);

        restSecurityRoleMockMvc.perform(put("/api/security-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityRoleDTO)))
            .andExpect(status().isOk());

        // Validate the SecurityRole in the database
        List<SecurityRole> securityRoleList = securityRoleRepository.findAll();
        assertThat(securityRoleList).hasSize(databaseSizeBeforeUpdate);
        SecurityRole testSecurityRole = securityRoleList.get(securityRoleList.size() - 1);
        assertThat(testSecurityRole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSecurityRole.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSecurityRole.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSecurityRole.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingSecurityRole() throws Exception {
        int databaseSizeBeforeUpdate = securityRoleRepository.findAll().size();

        // Create the SecurityRole
        SecurityRoleDTO securityRoleDTO = securityRoleMapper.toDto(securityRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecurityRoleMockMvc.perform(put("/api/security-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecurityRole in the database
        List<SecurityRole> securityRoleList = securityRoleRepository.findAll();
        assertThat(securityRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSecurityRole() throws Exception {
        // Initialize the database
        securityRoleRepository.saveAndFlush(securityRole);

        int databaseSizeBeforeDelete = securityRoleRepository.findAll().size();

        // Delete the securityRole
        restSecurityRoleMockMvc.perform(delete("/api/security-roles/{id}", securityRole.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SecurityRole> securityRoleList = securityRoleRepository.findAll();
        assertThat(securityRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
