package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.HospitalType;
import com.techvg.covid.care.repository.HospitalTypeRepository;
import com.techvg.covid.care.service.HospitalTypeService;
import com.techvg.covid.care.service.dto.HospitalTypeDTO;
import com.techvg.covid.care.service.mapper.HospitalTypeMapper;
import com.techvg.covid.care.service.dto.HospitalTypeCriteria;
import com.techvg.covid.care.service.HospitalTypeQueryService;

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
 * Integration tests for the {@link HospitalTypeResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HospitalTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private HospitalTypeRepository hospitalTypeRepository;

    @Autowired
    private HospitalTypeMapper hospitalTypeMapper;

    @Autowired
    private HospitalTypeService hospitalTypeService;

    @Autowired
    private HospitalTypeQueryService hospitalTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHospitalTypeMockMvc;

    private HospitalType hospitalType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HospitalType createEntity(EntityManager em) {
        HospitalType hospitalType = new HospitalType()
            .name(DEFAULT_NAME)
            .desciption(DEFAULT_DESCIPTION)
            .deleted(DEFAULT_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return hospitalType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HospitalType createUpdatedEntity(EntityManager em) {
        HospitalType hospitalType = new HospitalType()
            .name(UPDATED_NAME)
            .desciption(UPDATED_DESCIPTION)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return hospitalType;
    }

    @BeforeEach
    public void initTest() {
        hospitalType = createEntity(em);
    }

    @Test
    @Transactional
    public void createHospitalType() throws Exception {
        int databaseSizeBeforeCreate = hospitalTypeRepository.findAll().size();
        // Create the HospitalType
        HospitalTypeDTO hospitalTypeDTO = hospitalTypeMapper.toDto(hospitalType);
        restHospitalTypeMockMvc.perform(post("/api/hospital-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the HospitalType in the database
        List<HospitalType> hospitalTypeList = hospitalTypeRepository.findAll();
        assertThat(hospitalTypeList).hasSize(databaseSizeBeforeCreate + 1);
        HospitalType testHospitalType = hospitalTypeList.get(hospitalTypeList.size() - 1);
        assertThat(testHospitalType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHospitalType.getDesciption()).isEqualTo(DEFAULT_DESCIPTION);
        assertThat(testHospitalType.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testHospitalType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testHospitalType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createHospitalTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hospitalTypeRepository.findAll().size();

        // Create the HospitalType with an existing ID
        hospitalType.setId(1L);
        HospitalTypeDTO hospitalTypeDTO = hospitalTypeMapper.toDto(hospitalType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospitalTypeMockMvc.perform(post("/api/hospital-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HospitalType in the database
        List<HospitalType> hospitalTypeList = hospitalTypeRepository.findAll();
        assertThat(hospitalTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalTypeRepository.findAll().size();
        // set the field null
        hospitalType.setName(null);

        // Create the HospitalType, which fails.
        HospitalTypeDTO hospitalTypeDTO = hospitalTypeMapper.toDto(hospitalType);


        restHospitalTypeMockMvc.perform(post("/api/hospital-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalTypeDTO)))
            .andExpect(status().isBadRequest());

        List<HospitalType> hospitalTypeList = hospitalTypeRepository.findAll();
        assertThat(hospitalTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalTypeRepository.findAll().size();
        // set the field null
        hospitalType.setLastModified(null);

        // Create the HospitalType, which fails.
        HospitalTypeDTO hospitalTypeDTO = hospitalTypeMapper.toDto(hospitalType);


        restHospitalTypeMockMvc.perform(post("/api/hospital-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalTypeDTO)))
            .andExpect(status().isBadRequest());

        List<HospitalType> hospitalTypeList = hospitalTypeRepository.findAll();
        assertThat(hospitalTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalTypeRepository.findAll().size();
        // set the field null
        hospitalType.setLastModifiedBy(null);

        // Create the HospitalType, which fails.
        HospitalTypeDTO hospitalTypeDTO = hospitalTypeMapper.toDto(hospitalType);


        restHospitalTypeMockMvc.perform(post("/api/hospital-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalTypeDTO)))
            .andExpect(status().isBadRequest());

        List<HospitalType> hospitalTypeList = hospitalTypeRepository.findAll();
        assertThat(hospitalTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHospitalTypes() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList
        restHospitalTypeMockMvc.perform(get("/api/hospital-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospitalType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desciption").value(hasItem(DEFAULT_DESCIPTION)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getHospitalType() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get the hospitalType
        restHospitalTypeMockMvc.perform(get("/api/hospital-types/{id}", hospitalType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hospitalType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desciption").value(DEFAULT_DESCIPTION))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getHospitalTypesByIdFiltering() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        Long id = hospitalType.getId();

        defaultHospitalTypeShouldBeFound("id.equals=" + id);
        defaultHospitalTypeShouldNotBeFound("id.notEquals=" + id);

        defaultHospitalTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHospitalTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultHospitalTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHospitalTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHospitalTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where name equals to DEFAULT_NAME
        defaultHospitalTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the hospitalTypeList where name equals to UPDATED_NAME
        defaultHospitalTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where name not equals to DEFAULT_NAME
        defaultHospitalTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the hospitalTypeList where name not equals to UPDATED_NAME
        defaultHospitalTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultHospitalTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the hospitalTypeList where name equals to UPDATED_NAME
        defaultHospitalTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where name is not null
        defaultHospitalTypeShouldBeFound("name.specified=true");

        // Get all the hospitalTypeList where name is null
        defaultHospitalTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllHospitalTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where name contains DEFAULT_NAME
        defaultHospitalTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the hospitalTypeList where name contains UPDATED_NAME
        defaultHospitalTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where name does not contain DEFAULT_NAME
        defaultHospitalTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the hospitalTypeList where name does not contain UPDATED_NAME
        defaultHospitalTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllHospitalTypesByDesciptionIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where desciption equals to DEFAULT_DESCIPTION
        defaultHospitalTypeShouldBeFound("desciption.equals=" + DEFAULT_DESCIPTION);

        // Get all the hospitalTypeList where desciption equals to UPDATED_DESCIPTION
        defaultHospitalTypeShouldNotBeFound("desciption.equals=" + UPDATED_DESCIPTION);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByDesciptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where desciption not equals to DEFAULT_DESCIPTION
        defaultHospitalTypeShouldNotBeFound("desciption.notEquals=" + DEFAULT_DESCIPTION);

        // Get all the hospitalTypeList where desciption not equals to UPDATED_DESCIPTION
        defaultHospitalTypeShouldBeFound("desciption.notEquals=" + UPDATED_DESCIPTION);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByDesciptionIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where desciption in DEFAULT_DESCIPTION or UPDATED_DESCIPTION
        defaultHospitalTypeShouldBeFound("desciption.in=" + DEFAULT_DESCIPTION + "," + UPDATED_DESCIPTION);

        // Get all the hospitalTypeList where desciption equals to UPDATED_DESCIPTION
        defaultHospitalTypeShouldNotBeFound("desciption.in=" + UPDATED_DESCIPTION);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByDesciptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where desciption is not null
        defaultHospitalTypeShouldBeFound("desciption.specified=true");

        // Get all the hospitalTypeList where desciption is null
        defaultHospitalTypeShouldNotBeFound("desciption.specified=false");
    }
                @Test
    @Transactional
    public void getAllHospitalTypesByDesciptionContainsSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where desciption contains DEFAULT_DESCIPTION
        defaultHospitalTypeShouldBeFound("desciption.contains=" + DEFAULT_DESCIPTION);

        // Get all the hospitalTypeList where desciption contains UPDATED_DESCIPTION
        defaultHospitalTypeShouldNotBeFound("desciption.contains=" + UPDATED_DESCIPTION);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByDesciptionNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where desciption does not contain DEFAULT_DESCIPTION
        defaultHospitalTypeShouldNotBeFound("desciption.doesNotContain=" + DEFAULT_DESCIPTION);

        // Get all the hospitalTypeList where desciption does not contain UPDATED_DESCIPTION
        defaultHospitalTypeShouldBeFound("desciption.doesNotContain=" + UPDATED_DESCIPTION);
    }


    @Test
    @Transactional
    public void getAllHospitalTypesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where deleted equals to DEFAULT_DELETED
        defaultHospitalTypeShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the hospitalTypeList where deleted equals to UPDATED_DELETED
        defaultHospitalTypeShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where deleted not equals to DEFAULT_DELETED
        defaultHospitalTypeShouldNotBeFound("deleted.notEquals=" + DEFAULT_DELETED);

        // Get all the hospitalTypeList where deleted not equals to UPDATED_DELETED
        defaultHospitalTypeShouldBeFound("deleted.notEquals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultHospitalTypeShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the hospitalTypeList where deleted equals to UPDATED_DELETED
        defaultHospitalTypeShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where deleted is not null
        defaultHospitalTypeShouldBeFound("deleted.specified=true");

        // Get all the hospitalTypeList where deleted is null
        defaultHospitalTypeShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultHospitalTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the hospitalTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultHospitalTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultHospitalTypeShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the hospitalTypeList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultHospitalTypeShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultHospitalTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the hospitalTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultHospitalTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where lastModified is not null
        defaultHospitalTypeShouldBeFound("lastModified.specified=true");

        // Get all the hospitalTypeList where lastModified is null
        defaultHospitalTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultHospitalTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hospitalTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultHospitalTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultHospitalTypeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hospitalTypeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultHospitalTypeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultHospitalTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the hospitalTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultHospitalTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where lastModifiedBy is not null
        defaultHospitalTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the hospitalTypeList where lastModifiedBy is null
        defaultHospitalTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllHospitalTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultHospitalTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hospitalTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultHospitalTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllHospitalTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        // Get all the hospitalTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultHospitalTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hospitalTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultHospitalTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHospitalTypeShouldBeFound(String filter) throws Exception {
        restHospitalTypeMockMvc.perform(get("/api/hospital-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospitalType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desciption").value(hasItem(DEFAULT_DESCIPTION)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restHospitalTypeMockMvc.perform(get("/api/hospital-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHospitalTypeShouldNotBeFound(String filter) throws Exception {
        restHospitalTypeMockMvc.perform(get("/api/hospital-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHospitalTypeMockMvc.perform(get("/api/hospital-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingHospitalType() throws Exception {
        // Get the hospitalType
        restHospitalTypeMockMvc.perform(get("/api/hospital-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHospitalType() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        int databaseSizeBeforeUpdate = hospitalTypeRepository.findAll().size();

        // Update the hospitalType
        HospitalType updatedHospitalType = hospitalTypeRepository.findById(hospitalType.getId()).get();
        // Disconnect from session so that the updates on updatedHospitalType are not directly saved in db
        em.detach(updatedHospitalType);
        updatedHospitalType
            .name(UPDATED_NAME)
            .desciption(UPDATED_DESCIPTION)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        HospitalTypeDTO hospitalTypeDTO = hospitalTypeMapper.toDto(updatedHospitalType);

        restHospitalTypeMockMvc.perform(put("/api/hospital-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalTypeDTO)))
            .andExpect(status().isOk());

        // Validate the HospitalType in the database
        List<HospitalType> hospitalTypeList = hospitalTypeRepository.findAll();
        assertThat(hospitalTypeList).hasSize(databaseSizeBeforeUpdate);
        HospitalType testHospitalType = hospitalTypeList.get(hospitalTypeList.size() - 1);
        assertThat(testHospitalType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHospitalType.getDesciption()).isEqualTo(UPDATED_DESCIPTION);
        assertThat(testHospitalType.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testHospitalType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testHospitalType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingHospitalType() throws Exception {
        int databaseSizeBeforeUpdate = hospitalTypeRepository.findAll().size();

        // Create the HospitalType
        HospitalTypeDTO hospitalTypeDTO = hospitalTypeMapper.toDto(hospitalType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalTypeMockMvc.perform(put("/api/hospital-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HospitalType in the database
        List<HospitalType> hospitalTypeList = hospitalTypeRepository.findAll();
        assertThat(hospitalTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHospitalType() throws Exception {
        // Initialize the database
        hospitalTypeRepository.saveAndFlush(hospitalType);

        int databaseSizeBeforeDelete = hospitalTypeRepository.findAll().size();

        // Delete the hospitalType
        restHospitalTypeMockMvc.perform(delete("/api/hospital-types/{id}", hospitalType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HospitalType> hospitalTypeList = hospitalTypeRepository.findAll();
        assertThat(hospitalTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
