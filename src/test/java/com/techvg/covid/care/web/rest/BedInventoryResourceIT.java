package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.BedInventory;
import com.techvg.covid.care.domain.BedType;
import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.repository.BedInventoryRepository;
import com.techvg.covid.care.service.BedInventoryService;
import com.techvg.covid.care.service.dto.BedInventoryDTO;
import com.techvg.covid.care.service.mapper.BedInventoryMapper;
import com.techvg.covid.care.service.dto.BedInventoryCriteria;
import com.techvg.covid.care.service.BedInventoryQueryService;

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
 * Integration tests for the {@link BedInventoryResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BedInventoryResourceIT {

    private static final Long DEFAULT_BED_COUNT = 1L;
    private static final Long UPDATED_BED_COUNT = 2L;
    private static final Long SMALLER_BED_COUNT = 1L - 1L;

    private static final Long DEFAULT_OCCUPIED = 1L;
    private static final Long UPDATED_OCCUPIED = 2L;
    private static final Long SMALLER_OCCUPIED = 1L - 1L;

    private static final Long DEFAULT_ON_CYLINDER = 1L;
    private static final Long UPDATED_ON_CYLINDER = 2L;
    private static final Long SMALLER_ON_CYLINDER = 1L - 1L;

    private static final Long DEFAULT_ON_LMO = 1L;
    private static final Long UPDATED_ON_LMO = 2L;
    private static final Long SMALLER_ON_LMO = 1L - 1L;

    private static final Long DEFAULT_ON_CONCENTRATORS = 1L;
    private static final Long UPDATED_ON_CONCENTRATORS = 2L;
    private static final Long SMALLER_ON_CONCENTRATORS = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private BedInventoryRepository bedInventoryRepository;

    @Autowired
    private BedInventoryMapper bedInventoryMapper;

    @Autowired
    private BedInventoryService bedInventoryService;

    @Autowired
    private BedInventoryQueryService bedInventoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBedInventoryMockMvc;

    private BedInventory bedInventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BedInventory createEntity(EntityManager em) {
        BedInventory bedInventory = new BedInventory()
            .bedCount(DEFAULT_BED_COUNT)
            .occupied(DEFAULT_OCCUPIED)
            .onCylinder(DEFAULT_ON_CYLINDER)
            .onLMO(DEFAULT_ON_LMO)
            .onConcentrators(DEFAULT_ON_CONCENTRATORS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return bedInventory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BedInventory createUpdatedEntity(EntityManager em) {
        BedInventory bedInventory = new BedInventory()
            .bedCount(UPDATED_BED_COUNT)
            .occupied(UPDATED_OCCUPIED)
            .onCylinder(UPDATED_ON_CYLINDER)
            .onLMO(UPDATED_ON_LMO)
            .onConcentrators(UPDATED_ON_CONCENTRATORS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return bedInventory;
    }

    @BeforeEach
    public void initTest() {
        bedInventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createBedInventory() throws Exception {
        int databaseSizeBeforeCreate = bedInventoryRepository.findAll().size();
        // Create the BedInventory
        BedInventoryDTO bedInventoryDTO = bedInventoryMapper.toDto(bedInventory);
        restBedInventoryMockMvc.perform(post("/api/bed-inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedInventoryDTO)))
            .andExpect(status().isCreated());

        // Validate the BedInventory in the database
        List<BedInventory> bedInventoryList = bedInventoryRepository.findAll();
        assertThat(bedInventoryList).hasSize(databaseSizeBeforeCreate + 1);
        BedInventory testBedInventory = bedInventoryList.get(bedInventoryList.size() - 1);
        assertThat(testBedInventory.getBedCount()).isEqualTo(DEFAULT_BED_COUNT);
        assertThat(testBedInventory.getOccupied()).isEqualTo(DEFAULT_OCCUPIED);
        assertThat(testBedInventory.getOnCylinder()).isEqualTo(DEFAULT_ON_CYLINDER);
        assertThat(testBedInventory.getOnLMO()).isEqualTo(DEFAULT_ON_LMO);
        assertThat(testBedInventory.getOnConcentrators()).isEqualTo(DEFAULT_ON_CONCENTRATORS);
        assertThat(testBedInventory.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBedInventory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createBedInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bedInventoryRepository.findAll().size();

        // Create the BedInventory with an existing ID
        bedInventory.setId(1L);
        BedInventoryDTO bedInventoryDTO = bedInventoryMapper.toDto(bedInventory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBedInventoryMockMvc.perform(post("/api/bed-inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedInventoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BedInventory in the database
        List<BedInventory> bedInventoryList = bedInventoryRepository.findAll();
        assertThat(bedInventoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBedCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = bedInventoryRepository.findAll().size();
        // set the field null
        bedInventory.setBedCount(null);

        // Create the BedInventory, which fails.
        BedInventoryDTO bedInventoryDTO = bedInventoryMapper.toDto(bedInventory);


        restBedInventoryMockMvc.perform(post("/api/bed-inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedInventoryDTO)))
            .andExpect(status().isBadRequest());

        List<BedInventory> bedInventoryList = bedInventoryRepository.findAll();
        assertThat(bedInventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOccupiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = bedInventoryRepository.findAll().size();
        // set the field null
        bedInventory.setOccupied(null);

        // Create the BedInventory, which fails.
        BedInventoryDTO bedInventoryDTO = bedInventoryMapper.toDto(bedInventory);


        restBedInventoryMockMvc.perform(post("/api/bed-inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedInventoryDTO)))
            .andExpect(status().isBadRequest());

        List<BedInventory> bedInventoryList = bedInventoryRepository.findAll();
        assertThat(bedInventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = bedInventoryRepository.findAll().size();
        // set the field null
        bedInventory.setLastModified(null);

        // Create the BedInventory, which fails.
        BedInventoryDTO bedInventoryDTO = bedInventoryMapper.toDto(bedInventory);


        restBedInventoryMockMvc.perform(post("/api/bed-inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedInventoryDTO)))
            .andExpect(status().isBadRequest());

        List<BedInventory> bedInventoryList = bedInventoryRepository.findAll();
        assertThat(bedInventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = bedInventoryRepository.findAll().size();
        // set the field null
        bedInventory.setLastModifiedBy(null);

        // Create the BedInventory, which fails.
        BedInventoryDTO bedInventoryDTO = bedInventoryMapper.toDto(bedInventory);


        restBedInventoryMockMvc.perform(post("/api/bed-inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedInventoryDTO)))
            .andExpect(status().isBadRequest());

        List<BedInventory> bedInventoryList = bedInventoryRepository.findAll();
        assertThat(bedInventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBedInventories() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList
        restBedInventoryMockMvc.perform(get("/api/bed-inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bedInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedCount").value(hasItem(DEFAULT_BED_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].occupied").value(hasItem(DEFAULT_OCCUPIED.intValue())))
            .andExpect(jsonPath("$.[*].onCylinder").value(hasItem(DEFAULT_ON_CYLINDER.intValue())))
            .andExpect(jsonPath("$.[*].onLMO").value(hasItem(DEFAULT_ON_LMO.intValue())))
            .andExpect(jsonPath("$.[*].onConcentrators").value(hasItem(DEFAULT_ON_CONCENTRATORS.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getBedInventory() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get the bedInventory
        restBedInventoryMockMvc.perform(get("/api/bed-inventories/{id}", bedInventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bedInventory.getId().intValue()))
            .andExpect(jsonPath("$.bedCount").value(DEFAULT_BED_COUNT.intValue()))
            .andExpect(jsonPath("$.occupied").value(DEFAULT_OCCUPIED.intValue()))
            .andExpect(jsonPath("$.onCylinder").value(DEFAULT_ON_CYLINDER.intValue()))
            .andExpect(jsonPath("$.onLMO").value(DEFAULT_ON_LMO.intValue()))
            .andExpect(jsonPath("$.onConcentrators").value(DEFAULT_ON_CONCENTRATORS.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getBedInventoriesByIdFiltering() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        Long id = bedInventory.getId();

        defaultBedInventoryShouldBeFound("id.equals=" + id);
        defaultBedInventoryShouldNotBeFound("id.notEquals=" + id);

        defaultBedInventoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBedInventoryShouldNotBeFound("id.greaterThan=" + id);

        defaultBedInventoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBedInventoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBedInventoriesByBedCountIsEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where bedCount equals to DEFAULT_BED_COUNT
        defaultBedInventoryShouldBeFound("bedCount.equals=" + DEFAULT_BED_COUNT);

        // Get all the bedInventoryList where bedCount equals to UPDATED_BED_COUNT
        defaultBedInventoryShouldNotBeFound("bedCount.equals=" + UPDATED_BED_COUNT);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByBedCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where bedCount not equals to DEFAULT_BED_COUNT
        defaultBedInventoryShouldNotBeFound("bedCount.notEquals=" + DEFAULT_BED_COUNT);

        // Get all the bedInventoryList where bedCount not equals to UPDATED_BED_COUNT
        defaultBedInventoryShouldBeFound("bedCount.notEquals=" + UPDATED_BED_COUNT);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByBedCountIsInShouldWork() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where bedCount in DEFAULT_BED_COUNT or UPDATED_BED_COUNT
        defaultBedInventoryShouldBeFound("bedCount.in=" + DEFAULT_BED_COUNT + "," + UPDATED_BED_COUNT);

        // Get all the bedInventoryList where bedCount equals to UPDATED_BED_COUNT
        defaultBedInventoryShouldNotBeFound("bedCount.in=" + UPDATED_BED_COUNT);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByBedCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where bedCount is not null
        defaultBedInventoryShouldBeFound("bedCount.specified=true");

        // Get all the bedInventoryList where bedCount is null
        defaultBedInventoryShouldNotBeFound("bedCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByBedCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where bedCount is greater than or equal to DEFAULT_BED_COUNT
        defaultBedInventoryShouldBeFound("bedCount.greaterThanOrEqual=" + DEFAULT_BED_COUNT);

        // Get all the bedInventoryList where bedCount is greater than or equal to UPDATED_BED_COUNT
        defaultBedInventoryShouldNotBeFound("bedCount.greaterThanOrEqual=" + UPDATED_BED_COUNT);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByBedCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where bedCount is less than or equal to DEFAULT_BED_COUNT
        defaultBedInventoryShouldBeFound("bedCount.lessThanOrEqual=" + DEFAULT_BED_COUNT);

        // Get all the bedInventoryList where bedCount is less than or equal to SMALLER_BED_COUNT
        defaultBedInventoryShouldNotBeFound("bedCount.lessThanOrEqual=" + SMALLER_BED_COUNT);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByBedCountIsLessThanSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where bedCount is less than DEFAULT_BED_COUNT
        defaultBedInventoryShouldNotBeFound("bedCount.lessThan=" + DEFAULT_BED_COUNT);

        // Get all the bedInventoryList where bedCount is less than UPDATED_BED_COUNT
        defaultBedInventoryShouldBeFound("bedCount.lessThan=" + UPDATED_BED_COUNT);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByBedCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where bedCount is greater than DEFAULT_BED_COUNT
        defaultBedInventoryShouldNotBeFound("bedCount.greaterThan=" + DEFAULT_BED_COUNT);

        // Get all the bedInventoryList where bedCount is greater than SMALLER_BED_COUNT
        defaultBedInventoryShouldBeFound("bedCount.greaterThan=" + SMALLER_BED_COUNT);
    }


    @Test
    @Transactional
    public void getAllBedInventoriesByOccupiedIsEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where occupied equals to DEFAULT_OCCUPIED
        defaultBedInventoryShouldBeFound("occupied.equals=" + DEFAULT_OCCUPIED);

        // Get all the bedInventoryList where occupied equals to UPDATED_OCCUPIED
        defaultBedInventoryShouldNotBeFound("occupied.equals=" + UPDATED_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOccupiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where occupied not equals to DEFAULT_OCCUPIED
        defaultBedInventoryShouldNotBeFound("occupied.notEquals=" + DEFAULT_OCCUPIED);

        // Get all the bedInventoryList where occupied not equals to UPDATED_OCCUPIED
        defaultBedInventoryShouldBeFound("occupied.notEquals=" + UPDATED_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOccupiedIsInShouldWork() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where occupied in DEFAULT_OCCUPIED or UPDATED_OCCUPIED
        defaultBedInventoryShouldBeFound("occupied.in=" + DEFAULT_OCCUPIED + "," + UPDATED_OCCUPIED);

        // Get all the bedInventoryList where occupied equals to UPDATED_OCCUPIED
        defaultBedInventoryShouldNotBeFound("occupied.in=" + UPDATED_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOccupiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where occupied is not null
        defaultBedInventoryShouldBeFound("occupied.specified=true");

        // Get all the bedInventoryList where occupied is null
        defaultBedInventoryShouldNotBeFound("occupied.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOccupiedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where occupied is greater than or equal to DEFAULT_OCCUPIED
        defaultBedInventoryShouldBeFound("occupied.greaterThanOrEqual=" + DEFAULT_OCCUPIED);

        // Get all the bedInventoryList where occupied is greater than or equal to UPDATED_OCCUPIED
        defaultBedInventoryShouldNotBeFound("occupied.greaterThanOrEqual=" + UPDATED_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOccupiedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where occupied is less than or equal to DEFAULT_OCCUPIED
        defaultBedInventoryShouldBeFound("occupied.lessThanOrEqual=" + DEFAULT_OCCUPIED);

        // Get all the bedInventoryList where occupied is less than or equal to SMALLER_OCCUPIED
        defaultBedInventoryShouldNotBeFound("occupied.lessThanOrEqual=" + SMALLER_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOccupiedIsLessThanSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where occupied is less than DEFAULT_OCCUPIED
        defaultBedInventoryShouldNotBeFound("occupied.lessThan=" + DEFAULT_OCCUPIED);

        // Get all the bedInventoryList where occupied is less than UPDATED_OCCUPIED
        defaultBedInventoryShouldBeFound("occupied.lessThan=" + UPDATED_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOccupiedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where occupied is greater than DEFAULT_OCCUPIED
        defaultBedInventoryShouldNotBeFound("occupied.greaterThan=" + DEFAULT_OCCUPIED);

        // Get all the bedInventoryList where occupied is greater than SMALLER_OCCUPIED
        defaultBedInventoryShouldBeFound("occupied.greaterThan=" + SMALLER_OCCUPIED);
    }


    @Test
    @Transactional
    public void getAllBedInventoriesByOnCylinderIsEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onCylinder equals to DEFAULT_ON_CYLINDER
        defaultBedInventoryShouldBeFound("onCylinder.equals=" + DEFAULT_ON_CYLINDER);

        // Get all the bedInventoryList where onCylinder equals to UPDATED_ON_CYLINDER
        defaultBedInventoryShouldNotBeFound("onCylinder.equals=" + UPDATED_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnCylinderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onCylinder not equals to DEFAULT_ON_CYLINDER
        defaultBedInventoryShouldNotBeFound("onCylinder.notEquals=" + DEFAULT_ON_CYLINDER);

        // Get all the bedInventoryList where onCylinder not equals to UPDATED_ON_CYLINDER
        defaultBedInventoryShouldBeFound("onCylinder.notEquals=" + UPDATED_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnCylinderIsInShouldWork() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onCylinder in DEFAULT_ON_CYLINDER or UPDATED_ON_CYLINDER
        defaultBedInventoryShouldBeFound("onCylinder.in=" + DEFAULT_ON_CYLINDER + "," + UPDATED_ON_CYLINDER);

        // Get all the bedInventoryList where onCylinder equals to UPDATED_ON_CYLINDER
        defaultBedInventoryShouldNotBeFound("onCylinder.in=" + UPDATED_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnCylinderIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onCylinder is not null
        defaultBedInventoryShouldBeFound("onCylinder.specified=true");

        // Get all the bedInventoryList where onCylinder is null
        defaultBedInventoryShouldNotBeFound("onCylinder.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnCylinderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onCylinder is greater than or equal to DEFAULT_ON_CYLINDER
        defaultBedInventoryShouldBeFound("onCylinder.greaterThanOrEqual=" + DEFAULT_ON_CYLINDER);

        // Get all the bedInventoryList where onCylinder is greater than or equal to UPDATED_ON_CYLINDER
        defaultBedInventoryShouldNotBeFound("onCylinder.greaterThanOrEqual=" + UPDATED_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnCylinderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onCylinder is less than or equal to DEFAULT_ON_CYLINDER
        defaultBedInventoryShouldBeFound("onCylinder.lessThanOrEqual=" + DEFAULT_ON_CYLINDER);

        // Get all the bedInventoryList where onCylinder is less than or equal to SMALLER_ON_CYLINDER
        defaultBedInventoryShouldNotBeFound("onCylinder.lessThanOrEqual=" + SMALLER_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnCylinderIsLessThanSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onCylinder is less than DEFAULT_ON_CYLINDER
        defaultBedInventoryShouldNotBeFound("onCylinder.lessThan=" + DEFAULT_ON_CYLINDER);

        // Get all the bedInventoryList where onCylinder is less than UPDATED_ON_CYLINDER
        defaultBedInventoryShouldBeFound("onCylinder.lessThan=" + UPDATED_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnCylinderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onCylinder is greater than DEFAULT_ON_CYLINDER
        defaultBedInventoryShouldNotBeFound("onCylinder.greaterThan=" + DEFAULT_ON_CYLINDER);

        // Get all the bedInventoryList where onCylinder is greater than SMALLER_ON_CYLINDER
        defaultBedInventoryShouldBeFound("onCylinder.greaterThan=" + SMALLER_ON_CYLINDER);
    }


    @Test
    @Transactional
    public void getAllBedInventoriesByOnLMOIsEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onLMO equals to DEFAULT_ON_LMO
        defaultBedInventoryShouldBeFound("onLMO.equals=" + DEFAULT_ON_LMO);

        // Get all the bedInventoryList where onLMO equals to UPDATED_ON_LMO
        defaultBedInventoryShouldNotBeFound("onLMO.equals=" + UPDATED_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnLMOIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onLMO not equals to DEFAULT_ON_LMO
        defaultBedInventoryShouldNotBeFound("onLMO.notEquals=" + DEFAULT_ON_LMO);

        // Get all the bedInventoryList where onLMO not equals to UPDATED_ON_LMO
        defaultBedInventoryShouldBeFound("onLMO.notEquals=" + UPDATED_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnLMOIsInShouldWork() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onLMO in DEFAULT_ON_LMO or UPDATED_ON_LMO
        defaultBedInventoryShouldBeFound("onLMO.in=" + DEFAULT_ON_LMO + "," + UPDATED_ON_LMO);

        // Get all the bedInventoryList where onLMO equals to UPDATED_ON_LMO
        defaultBedInventoryShouldNotBeFound("onLMO.in=" + UPDATED_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnLMOIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onLMO is not null
        defaultBedInventoryShouldBeFound("onLMO.specified=true");

        // Get all the bedInventoryList where onLMO is null
        defaultBedInventoryShouldNotBeFound("onLMO.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnLMOIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onLMO is greater than or equal to DEFAULT_ON_LMO
        defaultBedInventoryShouldBeFound("onLMO.greaterThanOrEqual=" + DEFAULT_ON_LMO);

        // Get all the bedInventoryList where onLMO is greater than or equal to UPDATED_ON_LMO
        defaultBedInventoryShouldNotBeFound("onLMO.greaterThanOrEqual=" + UPDATED_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnLMOIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onLMO is less than or equal to DEFAULT_ON_LMO
        defaultBedInventoryShouldBeFound("onLMO.lessThanOrEqual=" + DEFAULT_ON_LMO);

        // Get all the bedInventoryList where onLMO is less than or equal to SMALLER_ON_LMO
        defaultBedInventoryShouldNotBeFound("onLMO.lessThanOrEqual=" + SMALLER_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnLMOIsLessThanSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onLMO is less than DEFAULT_ON_LMO
        defaultBedInventoryShouldNotBeFound("onLMO.lessThan=" + DEFAULT_ON_LMO);

        // Get all the bedInventoryList where onLMO is less than UPDATED_ON_LMO
        defaultBedInventoryShouldBeFound("onLMO.lessThan=" + UPDATED_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnLMOIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onLMO is greater than DEFAULT_ON_LMO
        defaultBedInventoryShouldNotBeFound("onLMO.greaterThan=" + DEFAULT_ON_LMO);

        // Get all the bedInventoryList where onLMO is greater than SMALLER_ON_LMO
        defaultBedInventoryShouldBeFound("onLMO.greaterThan=" + SMALLER_ON_LMO);
    }


    @Test
    @Transactional
    public void getAllBedInventoriesByOnConcentratorsIsEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onConcentrators equals to DEFAULT_ON_CONCENTRATORS
        defaultBedInventoryShouldBeFound("onConcentrators.equals=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedInventoryList where onConcentrators equals to UPDATED_ON_CONCENTRATORS
        defaultBedInventoryShouldNotBeFound("onConcentrators.equals=" + UPDATED_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnConcentratorsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onConcentrators not equals to DEFAULT_ON_CONCENTRATORS
        defaultBedInventoryShouldNotBeFound("onConcentrators.notEquals=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedInventoryList where onConcentrators not equals to UPDATED_ON_CONCENTRATORS
        defaultBedInventoryShouldBeFound("onConcentrators.notEquals=" + UPDATED_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnConcentratorsIsInShouldWork() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onConcentrators in DEFAULT_ON_CONCENTRATORS or UPDATED_ON_CONCENTRATORS
        defaultBedInventoryShouldBeFound("onConcentrators.in=" + DEFAULT_ON_CONCENTRATORS + "," + UPDATED_ON_CONCENTRATORS);

        // Get all the bedInventoryList where onConcentrators equals to UPDATED_ON_CONCENTRATORS
        defaultBedInventoryShouldNotBeFound("onConcentrators.in=" + UPDATED_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnConcentratorsIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onConcentrators is not null
        defaultBedInventoryShouldBeFound("onConcentrators.specified=true");

        // Get all the bedInventoryList where onConcentrators is null
        defaultBedInventoryShouldNotBeFound("onConcentrators.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnConcentratorsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onConcentrators is greater than or equal to DEFAULT_ON_CONCENTRATORS
        defaultBedInventoryShouldBeFound("onConcentrators.greaterThanOrEqual=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedInventoryList where onConcentrators is greater than or equal to UPDATED_ON_CONCENTRATORS
        defaultBedInventoryShouldNotBeFound("onConcentrators.greaterThanOrEqual=" + UPDATED_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnConcentratorsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onConcentrators is less than or equal to DEFAULT_ON_CONCENTRATORS
        defaultBedInventoryShouldBeFound("onConcentrators.lessThanOrEqual=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedInventoryList where onConcentrators is less than or equal to SMALLER_ON_CONCENTRATORS
        defaultBedInventoryShouldNotBeFound("onConcentrators.lessThanOrEqual=" + SMALLER_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnConcentratorsIsLessThanSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onConcentrators is less than DEFAULT_ON_CONCENTRATORS
        defaultBedInventoryShouldNotBeFound("onConcentrators.lessThan=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedInventoryList where onConcentrators is less than UPDATED_ON_CONCENTRATORS
        defaultBedInventoryShouldBeFound("onConcentrators.lessThan=" + UPDATED_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByOnConcentratorsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where onConcentrators is greater than DEFAULT_ON_CONCENTRATORS
        defaultBedInventoryShouldNotBeFound("onConcentrators.greaterThan=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedInventoryList where onConcentrators is greater than SMALLER_ON_CONCENTRATORS
        defaultBedInventoryShouldBeFound("onConcentrators.greaterThan=" + SMALLER_ON_CONCENTRATORS);
    }


    @Test
    @Transactional
    public void getAllBedInventoriesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultBedInventoryShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the bedInventoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBedInventoryShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultBedInventoryShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the bedInventoryList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultBedInventoryShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultBedInventoryShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the bedInventoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBedInventoryShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where lastModified is not null
        defaultBedInventoryShouldBeFound("lastModified.specified=true");

        // Get all the bedInventoryList where lastModified is null
        defaultBedInventoryShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultBedInventoryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedInventoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBedInventoryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultBedInventoryShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedInventoryList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultBedInventoryShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultBedInventoryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the bedInventoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBedInventoryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where lastModifiedBy is not null
        defaultBedInventoryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the bedInventoryList where lastModifiedBy is null
        defaultBedInventoryShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllBedInventoriesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultBedInventoryShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedInventoryList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultBedInventoryShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedInventoriesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        // Get all the bedInventoryList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultBedInventoryShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedInventoryList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultBedInventoryShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllBedInventoriesByBedTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);
        BedType bedType = BedTypeResourceIT.createEntity(em);
        em.persist(bedType);
        em.flush();
        bedInventory.setBedType(bedType);
        bedInventoryRepository.saveAndFlush(bedInventory);
        Long bedTypeId = bedType.getId();

        // Get all the bedInventoryList where bedType equals to bedTypeId
        defaultBedInventoryShouldBeFound("bedTypeId.equals=" + bedTypeId);

        // Get all the bedInventoryList where bedType equals to bedTypeId + 1
        defaultBedInventoryShouldNotBeFound("bedTypeId.equals=" + (bedTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllBedInventoriesByHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);
        Hospital hospital = HospitalResourceIT.createEntity(em);
        em.persist(hospital);
        em.flush();
        bedInventory.setHospital(hospital);
        bedInventoryRepository.saveAndFlush(bedInventory);
        Long hospitalId = hospital.getId();

        // Get all the bedInventoryList where hospital equals to hospitalId
        defaultBedInventoryShouldBeFound("hospitalId.equals=" + hospitalId);

        // Get all the bedInventoryList where hospital equals to hospitalId + 1
        defaultBedInventoryShouldNotBeFound("hospitalId.equals=" + (hospitalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBedInventoryShouldBeFound(String filter) throws Exception {
        restBedInventoryMockMvc.perform(get("/api/bed-inventories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bedInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedCount").value(hasItem(DEFAULT_BED_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].occupied").value(hasItem(DEFAULT_OCCUPIED.intValue())))
            .andExpect(jsonPath("$.[*].onCylinder").value(hasItem(DEFAULT_ON_CYLINDER.intValue())))
            .andExpect(jsonPath("$.[*].onLMO").value(hasItem(DEFAULT_ON_LMO.intValue())))
            .andExpect(jsonPath("$.[*].onConcentrators").value(hasItem(DEFAULT_ON_CONCENTRATORS.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restBedInventoryMockMvc.perform(get("/api/bed-inventories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBedInventoryShouldNotBeFound(String filter) throws Exception {
        restBedInventoryMockMvc.perform(get("/api/bed-inventories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBedInventoryMockMvc.perform(get("/api/bed-inventories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBedInventory() throws Exception {
        // Get the bedInventory
        restBedInventoryMockMvc.perform(get("/api/bed-inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBedInventory() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        int databaseSizeBeforeUpdate = bedInventoryRepository.findAll().size();

        // Update the bedInventory
        BedInventory updatedBedInventory = bedInventoryRepository.findById(bedInventory.getId()).get();
        // Disconnect from session so that the updates on updatedBedInventory are not directly saved in db
        em.detach(updatedBedInventory);
        updatedBedInventory
            .bedCount(UPDATED_BED_COUNT)
            .occupied(UPDATED_OCCUPIED)
            .onCylinder(UPDATED_ON_CYLINDER)
            .onLMO(UPDATED_ON_LMO)
            .onConcentrators(UPDATED_ON_CONCENTRATORS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        BedInventoryDTO bedInventoryDTO = bedInventoryMapper.toDto(updatedBedInventory);

        restBedInventoryMockMvc.perform(put("/api/bed-inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedInventoryDTO)))
            .andExpect(status().isOk());

        // Validate the BedInventory in the database
        List<BedInventory> bedInventoryList = bedInventoryRepository.findAll();
        assertThat(bedInventoryList).hasSize(databaseSizeBeforeUpdate);
        BedInventory testBedInventory = bedInventoryList.get(bedInventoryList.size() - 1);
        assertThat(testBedInventory.getBedCount()).isEqualTo(UPDATED_BED_COUNT);
        assertThat(testBedInventory.getOccupied()).isEqualTo(UPDATED_OCCUPIED);
        assertThat(testBedInventory.getOnCylinder()).isEqualTo(UPDATED_ON_CYLINDER);
        assertThat(testBedInventory.getOnLMO()).isEqualTo(UPDATED_ON_LMO);
        assertThat(testBedInventory.getOnConcentrators()).isEqualTo(UPDATED_ON_CONCENTRATORS);
        assertThat(testBedInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBedInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingBedInventory() throws Exception {
        int databaseSizeBeforeUpdate = bedInventoryRepository.findAll().size();

        // Create the BedInventory
        BedInventoryDTO bedInventoryDTO = bedInventoryMapper.toDto(bedInventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedInventoryMockMvc.perform(put("/api/bed-inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedInventoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BedInventory in the database
        List<BedInventory> bedInventoryList = bedInventoryRepository.findAll();
        assertThat(bedInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBedInventory() throws Exception {
        // Initialize the database
        bedInventoryRepository.saveAndFlush(bedInventory);

        int databaseSizeBeforeDelete = bedInventoryRepository.findAll().size();

        // Delete the bedInventory
        restBedInventoryMockMvc.perform(delete("/api/bed-inventories/{id}", bedInventory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BedInventory> bedInventoryList = bedInventoryRepository.findAll();
        assertThat(bedInventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
