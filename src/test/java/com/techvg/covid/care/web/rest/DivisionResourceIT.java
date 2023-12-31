package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.Division;
import com.techvg.covid.care.repository.DivisionRepository;
import com.techvg.covid.care.service.DivisionService;
import com.techvg.covid.care.service.dto.DivisionDTO;
import com.techvg.covid.care.service.mapper.DivisionMapper;
import com.techvg.covid.care.service.dto.DivisionCriteria;
import com.techvg.covid.care.service.DivisionQueryService;

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
 * Integration tests for the {@link DivisionResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DivisionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_LGD_CODE = 1L;
    private static final Long UPDATED_LGD_CODE = 2L;
    private static final Long SMALLER_LGD_CODE = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private DivisionMapper divisionMapper;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private DivisionQueryService divisionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDivisionMockMvc;

    private Division division;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Division createEntity(EntityManager em) {
        Division division = new Division()
            .name(DEFAULT_NAME)
            .deleted(DEFAULT_DELETED)
            .lgdCode(DEFAULT_LGD_CODE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return division;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Division createUpdatedEntity(EntityManager em) {
        Division division = new Division()
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return division;
    }

    @BeforeEach
    public void initTest() {
        division = createEntity(em);
    }

    @Test
    @Transactional
    public void createDivision() throws Exception {
        int databaseSizeBeforeCreate = divisionRepository.findAll().size();
        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isCreated());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate + 1);
        Division testDivision = divisionList.get(divisionList.size() - 1);
        assertThat(testDivision.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDivision.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testDivision.getLgdCode()).isEqualTo(DEFAULT_LGD_CODE);
        assertThat(testDivision.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDivision.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createDivisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = divisionRepository.findAll().size();

        // Create the Division with an existing ID
        division.setId(1L);
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionRepository.findAll().size();
        // set the field null
        division.setName(null);

        // Create the Division, which fails.
        DivisionDTO divisionDTO = divisionMapper.toDto(division);


        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionRepository.findAll().size();
        // set the field null
        division.setLastModified(null);

        // Create the Division, which fails.
        DivisionDTO divisionDTO = divisionMapper.toDto(division);


        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionRepository.findAll().size();
        // set the field null
        division.setLastModifiedBy(null);

        // Create the Division, which fails.
        DivisionDTO divisionDTO = divisionMapper.toDto(division);


        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDivisions() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(division.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lgdCode").value(hasItem(DEFAULT_LGD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", division.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(division.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lgdCode").value(DEFAULT_LGD_CODE.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getDivisionsByIdFiltering() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        Long id = division.getId();

        defaultDivisionShouldBeFound("id.equals=" + id);
        defaultDivisionShouldNotBeFound("id.notEquals=" + id);

        defaultDivisionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDivisionShouldNotBeFound("id.greaterThan=" + id);

        defaultDivisionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDivisionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDivisionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name equals to DEFAULT_NAME
        defaultDivisionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the divisionList where name equals to UPDATED_NAME
        defaultDivisionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name not equals to DEFAULT_NAME
        defaultDivisionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the divisionList where name not equals to UPDATED_NAME
        defaultDivisionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDivisionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the divisionList where name equals to UPDATED_NAME
        defaultDivisionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name is not null
        defaultDivisionShouldBeFound("name.specified=true");

        // Get all the divisionList where name is null
        defaultDivisionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDivisionsByNameContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name contains DEFAULT_NAME
        defaultDivisionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the divisionList where name contains UPDATED_NAME
        defaultDivisionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name does not contain DEFAULT_NAME
        defaultDivisionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the divisionList where name does not contain UPDATED_NAME
        defaultDivisionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDivisionsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where deleted equals to DEFAULT_DELETED
        defaultDivisionShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the divisionList where deleted equals to UPDATED_DELETED
        defaultDivisionShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllDivisionsByDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where deleted not equals to DEFAULT_DELETED
        defaultDivisionShouldNotBeFound("deleted.notEquals=" + DEFAULT_DELETED);

        // Get all the divisionList where deleted not equals to UPDATED_DELETED
        defaultDivisionShouldBeFound("deleted.notEquals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllDivisionsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultDivisionShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the divisionList where deleted equals to UPDATED_DELETED
        defaultDivisionShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllDivisionsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where deleted is not null
        defaultDivisionShouldBeFound("deleted.specified=true");

        // Get all the divisionList where deleted is null
        defaultDivisionShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllDivisionsByLgdCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lgdCode equals to DEFAULT_LGD_CODE
        defaultDivisionShouldBeFound("lgdCode.equals=" + DEFAULT_LGD_CODE);

        // Get all the divisionList where lgdCode equals to UPDATED_LGD_CODE
        defaultDivisionShouldNotBeFound("lgdCode.equals=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLgdCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lgdCode not equals to DEFAULT_LGD_CODE
        defaultDivisionShouldNotBeFound("lgdCode.notEquals=" + DEFAULT_LGD_CODE);

        // Get all the divisionList where lgdCode not equals to UPDATED_LGD_CODE
        defaultDivisionShouldBeFound("lgdCode.notEquals=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLgdCodeIsInShouldWork() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lgdCode in DEFAULT_LGD_CODE or UPDATED_LGD_CODE
        defaultDivisionShouldBeFound("lgdCode.in=" + DEFAULT_LGD_CODE + "," + UPDATED_LGD_CODE);

        // Get all the divisionList where lgdCode equals to UPDATED_LGD_CODE
        defaultDivisionShouldNotBeFound("lgdCode.in=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLgdCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lgdCode is not null
        defaultDivisionShouldBeFound("lgdCode.specified=true");

        // Get all the divisionList where lgdCode is null
        defaultDivisionShouldNotBeFound("lgdCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllDivisionsByLgdCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lgdCode is greater than or equal to DEFAULT_LGD_CODE
        defaultDivisionShouldBeFound("lgdCode.greaterThanOrEqual=" + DEFAULT_LGD_CODE);

        // Get all the divisionList where lgdCode is greater than or equal to UPDATED_LGD_CODE
        defaultDivisionShouldNotBeFound("lgdCode.greaterThanOrEqual=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLgdCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lgdCode is less than or equal to DEFAULT_LGD_CODE
        defaultDivisionShouldBeFound("lgdCode.lessThanOrEqual=" + DEFAULT_LGD_CODE);

        // Get all the divisionList where lgdCode is less than or equal to SMALLER_LGD_CODE
        defaultDivisionShouldNotBeFound("lgdCode.lessThanOrEqual=" + SMALLER_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLgdCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lgdCode is less than DEFAULT_LGD_CODE
        defaultDivisionShouldNotBeFound("lgdCode.lessThan=" + DEFAULT_LGD_CODE);

        // Get all the divisionList where lgdCode is less than UPDATED_LGD_CODE
        defaultDivisionShouldBeFound("lgdCode.lessThan=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLgdCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lgdCode is greater than DEFAULT_LGD_CODE
        defaultDivisionShouldNotBeFound("lgdCode.greaterThan=" + DEFAULT_LGD_CODE);

        // Get all the divisionList where lgdCode is greater than SMALLER_LGD_CODE
        defaultDivisionShouldBeFound("lgdCode.greaterThan=" + SMALLER_LGD_CODE);
    }


    @Test
    @Transactional
    public void getAllDivisionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultDivisionShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the divisionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDivisionShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultDivisionShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the divisionList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultDivisionShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultDivisionShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the divisionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDivisionShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lastModified is not null
        defaultDivisionShouldBeFound("lastModified.specified=true");

        // Get all the divisionList where lastModified is null
        defaultDivisionShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllDivisionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDivisionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the divisionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDivisionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultDivisionShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the divisionList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultDivisionShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDivisionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the divisionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDivisionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lastModifiedBy is not null
        defaultDivisionShouldBeFound("lastModifiedBy.specified=true");

        // Get all the divisionList where lastModifiedBy is null
        defaultDivisionShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllDivisionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultDivisionShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the divisionList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultDivisionShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDivisionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultDivisionShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the divisionList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultDivisionShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDivisionShouldBeFound(String filter) throws Exception {
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(division.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lgdCode").value(hasItem(DEFAULT_LGD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restDivisionMockMvc.perform(get("/api/divisions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDivisionShouldNotBeFound(String filter) throws Exception {
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDivisionMockMvc.perform(get("/api/divisions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDivision() throws Exception {
        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        int databaseSizeBeforeUpdate = divisionRepository.findAll().size();

        // Update the division
        Division updatedDivision = divisionRepository.findById(division.getId()).get();
        // Disconnect from session so that the updates on updatedDivision are not directly saved in db
        em.detach(updatedDivision);
        updatedDivision
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        DivisionDTO divisionDTO = divisionMapper.toDto(updatedDivision);

        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isOk());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate);
        Division testDivision = divisionList.get(divisionList.size() - 1);
        assertThat(testDivision.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDivision.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testDivision.getLgdCode()).isEqualTo(UPDATED_LGD_CODE);
        assertThat(testDivision.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDivision.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingDivision() throws Exception {
        int databaseSizeBeforeUpdate = divisionRepository.findAll().size();

        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        int databaseSizeBeforeDelete = divisionRepository.findAll().size();

        // Delete the division
        restDivisionMockMvc.perform(delete("/api/divisions/{id}", division.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
