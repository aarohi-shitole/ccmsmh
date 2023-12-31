package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.BedType;
import com.techvg.covid.care.repository.BedTypeRepository;
import com.techvg.covid.care.service.BedTypeService;
import com.techvg.covid.care.service.dto.BedTypeDTO;
import com.techvg.covid.care.service.mapper.BedTypeMapper;
import com.techvg.covid.care.service.dto.BedTypeCriteria;
import com.techvg.covid.care.service.BedTypeQueryService;

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
 * Integration tests for the {@link BedTypeResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BedTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PER_DAY_OX = 1L;
    private static final Long UPDATED_PER_DAY_OX = 2L;
    private static final Long SMALLER_PER_DAY_OX = 1L - 1L;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private BedTypeRepository bedTypeRepository;

    @Autowired
    private BedTypeMapper bedTypeMapper;

    @Autowired
    private BedTypeService bedTypeService;

    @Autowired
    private BedTypeQueryService bedTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBedTypeMockMvc;

    private BedType bedType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BedType createEntity(EntityManager em) {
        BedType bedType = new BedType()
            .name(DEFAULT_NAME)
            .perDayOX(DEFAULT_PER_DAY_OX)
            .deleted(DEFAULT_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return bedType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BedType createUpdatedEntity(EntityManager em) {
        BedType bedType = new BedType()
            .name(UPDATED_NAME)
            .perDayOX(UPDATED_PER_DAY_OX)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return bedType;
    }

    @BeforeEach
    public void initTest() {
        bedType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBedType() throws Exception {
        int databaseSizeBeforeCreate = bedTypeRepository.findAll().size();
        // Create the BedType
        BedTypeDTO bedTypeDTO = bedTypeMapper.toDto(bedType);
        restBedTypeMockMvc.perform(post("/api/bed-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the BedType in the database
        List<BedType> bedTypeList = bedTypeRepository.findAll();
        assertThat(bedTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BedType testBedType = bedTypeList.get(bedTypeList.size() - 1);
        assertThat(testBedType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBedType.getPerDayOX()).isEqualTo(DEFAULT_PER_DAY_OX);
        assertThat(testBedType.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBedType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBedType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createBedTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bedTypeRepository.findAll().size();

        // Create the BedType with an existing ID
        bedType.setId(1L);
        BedTypeDTO bedTypeDTO = bedTypeMapper.toDto(bedType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBedTypeMockMvc.perform(post("/api/bed-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BedType in the database
        List<BedType> bedTypeList = bedTypeRepository.findAll();
        assertThat(bedTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bedTypeRepository.findAll().size();
        // set the field null
        bedType.setName(null);

        // Create the BedType, which fails.
        BedTypeDTO bedTypeDTO = bedTypeMapper.toDto(bedType);


        restBedTypeMockMvc.perform(post("/api/bed-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTypeDTO)))
            .andExpect(status().isBadRequest());

        List<BedType> bedTypeList = bedTypeRepository.findAll();
        assertThat(bedTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = bedTypeRepository.findAll().size();
        // set the field null
        bedType.setLastModified(null);

        // Create the BedType, which fails.
        BedTypeDTO bedTypeDTO = bedTypeMapper.toDto(bedType);


        restBedTypeMockMvc.perform(post("/api/bed-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTypeDTO)))
            .andExpect(status().isBadRequest());

        List<BedType> bedTypeList = bedTypeRepository.findAll();
        assertThat(bedTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = bedTypeRepository.findAll().size();
        // set the field null
        bedType.setLastModifiedBy(null);

        // Create the BedType, which fails.
        BedTypeDTO bedTypeDTO = bedTypeMapper.toDto(bedType);


        restBedTypeMockMvc.perform(post("/api/bed-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTypeDTO)))
            .andExpect(status().isBadRequest());

        List<BedType> bedTypeList = bedTypeRepository.findAll();
        assertThat(bedTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBedTypes() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList
        restBedTypeMockMvc.perform(get("/api/bed-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bedType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].perDayOX").value(hasItem(DEFAULT_PER_DAY_OX.intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getBedType() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get the bedType
        restBedTypeMockMvc.perform(get("/api/bed-types/{id}", bedType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bedType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.perDayOX").value(DEFAULT_PER_DAY_OX.intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getBedTypesByIdFiltering() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        Long id = bedType.getId();

        defaultBedTypeShouldBeFound("id.equals=" + id);
        defaultBedTypeShouldNotBeFound("id.notEquals=" + id);

        defaultBedTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBedTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultBedTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBedTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBedTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where name equals to DEFAULT_NAME
        defaultBedTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the bedTypeList where name equals to UPDATED_NAME
        defaultBedTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBedTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where name not equals to DEFAULT_NAME
        defaultBedTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the bedTypeList where name not equals to UPDATED_NAME
        defaultBedTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBedTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBedTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the bedTypeList where name equals to UPDATED_NAME
        defaultBedTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBedTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where name is not null
        defaultBedTypeShouldBeFound("name.specified=true");

        // Get all the bedTypeList where name is null
        defaultBedTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllBedTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where name contains DEFAULT_NAME
        defaultBedTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the bedTypeList where name contains UPDATED_NAME
        defaultBedTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBedTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where name does not contain DEFAULT_NAME
        defaultBedTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the bedTypeList where name does not contain UPDATED_NAME
        defaultBedTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllBedTypesByPerDayOXIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where perDayOX equals to DEFAULT_PER_DAY_OX
        defaultBedTypeShouldBeFound("perDayOX.equals=" + DEFAULT_PER_DAY_OX);

        // Get all the bedTypeList where perDayOX equals to UPDATED_PER_DAY_OX
        defaultBedTypeShouldNotBeFound("perDayOX.equals=" + UPDATED_PER_DAY_OX);
    }

    @Test
    @Transactional
    public void getAllBedTypesByPerDayOXIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where perDayOX not equals to DEFAULT_PER_DAY_OX
        defaultBedTypeShouldNotBeFound("perDayOX.notEquals=" + DEFAULT_PER_DAY_OX);

        // Get all the bedTypeList where perDayOX not equals to UPDATED_PER_DAY_OX
        defaultBedTypeShouldBeFound("perDayOX.notEquals=" + UPDATED_PER_DAY_OX);
    }

    @Test
    @Transactional
    public void getAllBedTypesByPerDayOXIsInShouldWork() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where perDayOX in DEFAULT_PER_DAY_OX or UPDATED_PER_DAY_OX
        defaultBedTypeShouldBeFound("perDayOX.in=" + DEFAULT_PER_DAY_OX + "," + UPDATED_PER_DAY_OX);

        // Get all the bedTypeList where perDayOX equals to UPDATED_PER_DAY_OX
        defaultBedTypeShouldNotBeFound("perDayOX.in=" + UPDATED_PER_DAY_OX);
    }

    @Test
    @Transactional
    public void getAllBedTypesByPerDayOXIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where perDayOX is not null
        defaultBedTypeShouldBeFound("perDayOX.specified=true");

        // Get all the bedTypeList where perDayOX is null
        defaultBedTypeShouldNotBeFound("perDayOX.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedTypesByPerDayOXIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where perDayOX is greater than or equal to DEFAULT_PER_DAY_OX
        defaultBedTypeShouldBeFound("perDayOX.greaterThanOrEqual=" + DEFAULT_PER_DAY_OX);

        // Get all the bedTypeList where perDayOX is greater than or equal to UPDATED_PER_DAY_OX
        defaultBedTypeShouldNotBeFound("perDayOX.greaterThanOrEqual=" + UPDATED_PER_DAY_OX);
    }

    @Test
    @Transactional
    public void getAllBedTypesByPerDayOXIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where perDayOX is less than or equal to DEFAULT_PER_DAY_OX
        defaultBedTypeShouldBeFound("perDayOX.lessThanOrEqual=" + DEFAULT_PER_DAY_OX);

        // Get all the bedTypeList where perDayOX is less than or equal to SMALLER_PER_DAY_OX
        defaultBedTypeShouldNotBeFound("perDayOX.lessThanOrEqual=" + SMALLER_PER_DAY_OX);
    }

    @Test
    @Transactional
    public void getAllBedTypesByPerDayOXIsLessThanSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where perDayOX is less than DEFAULT_PER_DAY_OX
        defaultBedTypeShouldNotBeFound("perDayOX.lessThan=" + DEFAULT_PER_DAY_OX);

        // Get all the bedTypeList where perDayOX is less than UPDATED_PER_DAY_OX
        defaultBedTypeShouldBeFound("perDayOX.lessThan=" + UPDATED_PER_DAY_OX);
    }

    @Test
    @Transactional
    public void getAllBedTypesByPerDayOXIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where perDayOX is greater than DEFAULT_PER_DAY_OX
        defaultBedTypeShouldNotBeFound("perDayOX.greaterThan=" + DEFAULT_PER_DAY_OX);

        // Get all the bedTypeList where perDayOX is greater than SMALLER_PER_DAY_OX
        defaultBedTypeShouldBeFound("perDayOX.greaterThan=" + SMALLER_PER_DAY_OX);
    }


    @Test
    @Transactional
    public void getAllBedTypesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where deleted equals to DEFAULT_DELETED
        defaultBedTypeShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the bedTypeList where deleted equals to UPDATED_DELETED
        defaultBedTypeShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllBedTypesByDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where deleted not equals to DEFAULT_DELETED
        defaultBedTypeShouldNotBeFound("deleted.notEquals=" + DEFAULT_DELETED);

        // Get all the bedTypeList where deleted not equals to UPDATED_DELETED
        defaultBedTypeShouldBeFound("deleted.notEquals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllBedTypesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultBedTypeShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the bedTypeList where deleted equals to UPDATED_DELETED
        defaultBedTypeShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllBedTypesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where deleted is not null
        defaultBedTypeShouldBeFound("deleted.specified=true");

        // Get all the bedTypeList where deleted is null
        defaultBedTypeShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultBedTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the bedTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBedTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllBedTypesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultBedTypeShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the bedTypeList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultBedTypeShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllBedTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultBedTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the bedTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBedTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllBedTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where lastModified is not null
        defaultBedTypeShouldBeFound("lastModified.specified=true");

        // Get all the bedTypeList where lastModified is null
        defaultBedTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultBedTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBedTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedTypesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultBedTypeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedTypeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultBedTypeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultBedTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the bedTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBedTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where lastModifiedBy is not null
        defaultBedTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the bedTypeList where lastModifiedBy is null
        defaultBedTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllBedTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultBedTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultBedTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        // Get all the bedTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultBedTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultBedTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBedTypeShouldBeFound(String filter) throws Exception {
        restBedTypeMockMvc.perform(get("/api/bed-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bedType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].perDayOX").value(hasItem(DEFAULT_PER_DAY_OX.intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restBedTypeMockMvc.perform(get("/api/bed-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBedTypeShouldNotBeFound(String filter) throws Exception {
        restBedTypeMockMvc.perform(get("/api/bed-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBedTypeMockMvc.perform(get("/api/bed-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBedType() throws Exception {
        // Get the bedType
        restBedTypeMockMvc.perform(get("/api/bed-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBedType() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        int databaseSizeBeforeUpdate = bedTypeRepository.findAll().size();

        // Update the bedType
        BedType updatedBedType = bedTypeRepository.findById(bedType.getId()).get();
        // Disconnect from session so that the updates on updatedBedType are not directly saved in db
        em.detach(updatedBedType);
        updatedBedType
            .name(UPDATED_NAME)
            .perDayOX(UPDATED_PER_DAY_OX)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        BedTypeDTO bedTypeDTO = bedTypeMapper.toDto(updatedBedType);

        restBedTypeMockMvc.perform(put("/api/bed-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTypeDTO)))
            .andExpect(status().isOk());

        // Validate the BedType in the database
        List<BedType> bedTypeList = bedTypeRepository.findAll();
        assertThat(bedTypeList).hasSize(databaseSizeBeforeUpdate);
        BedType testBedType = bedTypeList.get(bedTypeList.size() - 1);
        assertThat(testBedType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBedType.getPerDayOX()).isEqualTo(UPDATED_PER_DAY_OX);
        assertThat(testBedType.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBedType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBedType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingBedType() throws Exception {
        int databaseSizeBeforeUpdate = bedTypeRepository.findAll().size();

        // Create the BedType
        BedTypeDTO bedTypeDTO = bedTypeMapper.toDto(bedType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedTypeMockMvc.perform(put("/api/bed-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BedType in the database
        List<BedType> bedTypeList = bedTypeRepository.findAll();
        assertThat(bedTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBedType() throws Exception {
        // Initialize the database
        bedTypeRepository.saveAndFlush(bedType);

        int databaseSizeBeforeDelete = bedTypeRepository.findAll().size();

        // Delete the bedType
        restBedTypeMockMvc.perform(delete("/api/bed-types/{id}", bedType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BedType> bedTypeList = bedTypeRepository.findAll();
        assertThat(bedTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
