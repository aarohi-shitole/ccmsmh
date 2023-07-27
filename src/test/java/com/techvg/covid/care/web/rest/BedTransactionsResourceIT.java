package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.BedTransactions;
import com.techvg.covid.care.domain.BedType;
import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.repository.BedTransactionsRepository;
import com.techvg.covid.care.service.BedTransactionsService;
import com.techvg.covid.care.service.dto.BedTransactionsDTO;
import com.techvg.covid.care.service.mapper.BedTransactionsMapper;
import com.techvg.covid.care.service.dto.BedTransactionsCriteria;
import com.techvg.covid.care.service.BedTransactionsQueryService;

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
 * Integration tests for the {@link BedTransactionsResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BedTransactionsResourceIT {

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
    private BedTransactionsRepository bedTransactionsRepository;

    @Autowired
    private BedTransactionsMapper bedTransactionsMapper;

    @Autowired
    private BedTransactionsService bedTransactionsService;

    @Autowired
    private BedTransactionsQueryService bedTransactionsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBedTransactionsMockMvc;

    private BedTransactions bedTransactions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BedTransactions createEntity(EntityManager em) {
        BedTransactions bedTransactions = new BedTransactions()
            .occupied(DEFAULT_OCCUPIED)
            .onCylinder(DEFAULT_ON_CYLINDER)
            .onLMO(DEFAULT_ON_LMO)
            .onConcentrators(DEFAULT_ON_CONCENTRATORS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return bedTransactions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BedTransactions createUpdatedEntity(EntityManager em) {
        BedTransactions bedTransactions = new BedTransactions()
            .occupied(UPDATED_OCCUPIED)
            .onCylinder(UPDATED_ON_CYLINDER)
            .onLMO(UPDATED_ON_LMO)
            .onConcentrators(UPDATED_ON_CONCENTRATORS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return bedTransactions;
    }

    @BeforeEach
    public void initTest() {
        bedTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createBedTransactions() throws Exception {
        int databaseSizeBeforeCreate = bedTransactionsRepository.findAll().size();
        // Create the BedTransactions
        BedTransactionsDTO bedTransactionsDTO = bedTransactionsMapper.toDto(bedTransactions);
        restBedTransactionsMockMvc.perform(post("/api/bed-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the BedTransactions in the database
        List<BedTransactions> bedTransactionsList = bedTransactionsRepository.findAll();
        assertThat(bedTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        BedTransactions testBedTransactions = bedTransactionsList.get(bedTransactionsList.size() - 1);
        assertThat(testBedTransactions.getOccupied()).isEqualTo(DEFAULT_OCCUPIED);
        assertThat(testBedTransactions.getOnCylinder()).isEqualTo(DEFAULT_ON_CYLINDER);
        assertThat(testBedTransactions.getOnLMO()).isEqualTo(DEFAULT_ON_LMO);
        assertThat(testBedTransactions.getOnConcentrators()).isEqualTo(DEFAULT_ON_CONCENTRATORS);
        assertThat(testBedTransactions.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBedTransactions.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createBedTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bedTransactionsRepository.findAll().size();

        // Create the BedTransactions with an existing ID
        bedTransactions.setId(1L);
        BedTransactionsDTO bedTransactionsDTO = bedTransactionsMapper.toDto(bedTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBedTransactionsMockMvc.perform(post("/api/bed-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BedTransactions in the database
        List<BedTransactions> bedTransactionsList = bedTransactionsRepository.findAll();
        assertThat(bedTransactionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOccupiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = bedTransactionsRepository.findAll().size();
        // set the field null
        bedTransactions.setOccupied(null);

        // Create the BedTransactions, which fails.
        BedTransactionsDTO bedTransactionsDTO = bedTransactionsMapper.toDto(bedTransactions);


        restBedTransactionsMockMvc.perform(post("/api/bed-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<BedTransactions> bedTransactionsList = bedTransactionsRepository.findAll();
        assertThat(bedTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = bedTransactionsRepository.findAll().size();
        // set the field null
        bedTransactions.setLastModified(null);

        // Create the BedTransactions, which fails.
        BedTransactionsDTO bedTransactionsDTO = bedTransactionsMapper.toDto(bedTransactions);


        restBedTransactionsMockMvc.perform(post("/api/bed-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<BedTransactions> bedTransactionsList = bedTransactionsRepository.findAll();
        assertThat(bedTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = bedTransactionsRepository.findAll().size();
        // set the field null
        bedTransactions.setLastModifiedBy(null);

        // Create the BedTransactions, which fails.
        BedTransactionsDTO bedTransactionsDTO = bedTransactionsMapper.toDto(bedTransactions);


        restBedTransactionsMockMvc.perform(post("/api/bed-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<BedTransactions> bedTransactionsList = bedTransactionsRepository.findAll();
        assertThat(bedTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBedTransactions() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList
        restBedTransactionsMockMvc.perform(get("/api/bed-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bedTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].occupied").value(hasItem(DEFAULT_OCCUPIED.intValue())))
            .andExpect(jsonPath("$.[*].onCylinder").value(hasItem(DEFAULT_ON_CYLINDER.intValue())))
            .andExpect(jsonPath("$.[*].onLMO").value(hasItem(DEFAULT_ON_LMO.intValue())))
            .andExpect(jsonPath("$.[*].onConcentrators").value(hasItem(DEFAULT_ON_CONCENTRATORS.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getBedTransactions() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get the bedTransactions
        restBedTransactionsMockMvc.perform(get("/api/bed-transactions/{id}", bedTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bedTransactions.getId().intValue()))
            .andExpect(jsonPath("$.occupied").value(DEFAULT_OCCUPIED.intValue()))
            .andExpect(jsonPath("$.onCylinder").value(DEFAULT_ON_CYLINDER.intValue()))
            .andExpect(jsonPath("$.onLMO").value(DEFAULT_ON_LMO.intValue()))
            .andExpect(jsonPath("$.onConcentrators").value(DEFAULT_ON_CONCENTRATORS.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getBedTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        Long id = bedTransactions.getId();

        defaultBedTransactionsShouldBeFound("id.equals=" + id);
        defaultBedTransactionsShouldNotBeFound("id.notEquals=" + id);

        defaultBedTransactionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBedTransactionsShouldNotBeFound("id.greaterThan=" + id);

        defaultBedTransactionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBedTransactionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBedTransactionsByOccupiedIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where occupied equals to DEFAULT_OCCUPIED
        defaultBedTransactionsShouldBeFound("occupied.equals=" + DEFAULT_OCCUPIED);

        // Get all the bedTransactionsList where occupied equals to UPDATED_OCCUPIED
        defaultBedTransactionsShouldNotBeFound("occupied.equals=" + UPDATED_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOccupiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where occupied not equals to DEFAULT_OCCUPIED
        defaultBedTransactionsShouldNotBeFound("occupied.notEquals=" + DEFAULT_OCCUPIED);

        // Get all the bedTransactionsList where occupied not equals to UPDATED_OCCUPIED
        defaultBedTransactionsShouldBeFound("occupied.notEquals=" + UPDATED_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOccupiedIsInShouldWork() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where occupied in DEFAULT_OCCUPIED or UPDATED_OCCUPIED
        defaultBedTransactionsShouldBeFound("occupied.in=" + DEFAULT_OCCUPIED + "," + UPDATED_OCCUPIED);

        // Get all the bedTransactionsList where occupied equals to UPDATED_OCCUPIED
        defaultBedTransactionsShouldNotBeFound("occupied.in=" + UPDATED_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOccupiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where occupied is not null
        defaultBedTransactionsShouldBeFound("occupied.specified=true");

        // Get all the bedTransactionsList where occupied is null
        defaultBedTransactionsShouldNotBeFound("occupied.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOccupiedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where occupied is greater than or equal to DEFAULT_OCCUPIED
        defaultBedTransactionsShouldBeFound("occupied.greaterThanOrEqual=" + DEFAULT_OCCUPIED);

        // Get all the bedTransactionsList where occupied is greater than or equal to UPDATED_OCCUPIED
        defaultBedTransactionsShouldNotBeFound("occupied.greaterThanOrEqual=" + UPDATED_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOccupiedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where occupied is less than or equal to DEFAULT_OCCUPIED
        defaultBedTransactionsShouldBeFound("occupied.lessThanOrEqual=" + DEFAULT_OCCUPIED);

        // Get all the bedTransactionsList where occupied is less than or equal to SMALLER_OCCUPIED
        defaultBedTransactionsShouldNotBeFound("occupied.lessThanOrEqual=" + SMALLER_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOccupiedIsLessThanSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where occupied is less than DEFAULT_OCCUPIED
        defaultBedTransactionsShouldNotBeFound("occupied.lessThan=" + DEFAULT_OCCUPIED);

        // Get all the bedTransactionsList where occupied is less than UPDATED_OCCUPIED
        defaultBedTransactionsShouldBeFound("occupied.lessThan=" + UPDATED_OCCUPIED);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOccupiedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where occupied is greater than DEFAULT_OCCUPIED
        defaultBedTransactionsShouldNotBeFound("occupied.greaterThan=" + DEFAULT_OCCUPIED);

        // Get all the bedTransactionsList where occupied is greater than SMALLER_OCCUPIED
        defaultBedTransactionsShouldBeFound("occupied.greaterThan=" + SMALLER_OCCUPIED);
    }


    @Test
    @Transactional
    public void getAllBedTransactionsByOnCylinderIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onCylinder equals to DEFAULT_ON_CYLINDER
        defaultBedTransactionsShouldBeFound("onCylinder.equals=" + DEFAULT_ON_CYLINDER);

        // Get all the bedTransactionsList where onCylinder equals to UPDATED_ON_CYLINDER
        defaultBedTransactionsShouldNotBeFound("onCylinder.equals=" + UPDATED_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnCylinderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onCylinder not equals to DEFAULT_ON_CYLINDER
        defaultBedTransactionsShouldNotBeFound("onCylinder.notEquals=" + DEFAULT_ON_CYLINDER);

        // Get all the bedTransactionsList where onCylinder not equals to UPDATED_ON_CYLINDER
        defaultBedTransactionsShouldBeFound("onCylinder.notEquals=" + UPDATED_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnCylinderIsInShouldWork() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onCylinder in DEFAULT_ON_CYLINDER or UPDATED_ON_CYLINDER
        defaultBedTransactionsShouldBeFound("onCylinder.in=" + DEFAULT_ON_CYLINDER + "," + UPDATED_ON_CYLINDER);

        // Get all the bedTransactionsList where onCylinder equals to UPDATED_ON_CYLINDER
        defaultBedTransactionsShouldNotBeFound("onCylinder.in=" + UPDATED_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnCylinderIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onCylinder is not null
        defaultBedTransactionsShouldBeFound("onCylinder.specified=true");

        // Get all the bedTransactionsList where onCylinder is null
        defaultBedTransactionsShouldNotBeFound("onCylinder.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnCylinderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onCylinder is greater than or equal to DEFAULT_ON_CYLINDER
        defaultBedTransactionsShouldBeFound("onCylinder.greaterThanOrEqual=" + DEFAULT_ON_CYLINDER);

        // Get all the bedTransactionsList where onCylinder is greater than or equal to UPDATED_ON_CYLINDER
        defaultBedTransactionsShouldNotBeFound("onCylinder.greaterThanOrEqual=" + UPDATED_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnCylinderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onCylinder is less than or equal to DEFAULT_ON_CYLINDER
        defaultBedTransactionsShouldBeFound("onCylinder.lessThanOrEqual=" + DEFAULT_ON_CYLINDER);

        // Get all the bedTransactionsList where onCylinder is less than or equal to SMALLER_ON_CYLINDER
        defaultBedTransactionsShouldNotBeFound("onCylinder.lessThanOrEqual=" + SMALLER_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnCylinderIsLessThanSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onCylinder is less than DEFAULT_ON_CYLINDER
        defaultBedTransactionsShouldNotBeFound("onCylinder.lessThan=" + DEFAULT_ON_CYLINDER);

        // Get all the bedTransactionsList where onCylinder is less than UPDATED_ON_CYLINDER
        defaultBedTransactionsShouldBeFound("onCylinder.lessThan=" + UPDATED_ON_CYLINDER);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnCylinderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onCylinder is greater than DEFAULT_ON_CYLINDER
        defaultBedTransactionsShouldNotBeFound("onCylinder.greaterThan=" + DEFAULT_ON_CYLINDER);

        // Get all the bedTransactionsList where onCylinder is greater than SMALLER_ON_CYLINDER
        defaultBedTransactionsShouldBeFound("onCylinder.greaterThan=" + SMALLER_ON_CYLINDER);
    }


    @Test
    @Transactional
    public void getAllBedTransactionsByOnLMOIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onLMO equals to DEFAULT_ON_LMO
        defaultBedTransactionsShouldBeFound("onLMO.equals=" + DEFAULT_ON_LMO);

        // Get all the bedTransactionsList where onLMO equals to UPDATED_ON_LMO
        defaultBedTransactionsShouldNotBeFound("onLMO.equals=" + UPDATED_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnLMOIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onLMO not equals to DEFAULT_ON_LMO
        defaultBedTransactionsShouldNotBeFound("onLMO.notEquals=" + DEFAULT_ON_LMO);

        // Get all the bedTransactionsList where onLMO not equals to UPDATED_ON_LMO
        defaultBedTransactionsShouldBeFound("onLMO.notEquals=" + UPDATED_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnLMOIsInShouldWork() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onLMO in DEFAULT_ON_LMO or UPDATED_ON_LMO
        defaultBedTransactionsShouldBeFound("onLMO.in=" + DEFAULT_ON_LMO + "," + UPDATED_ON_LMO);

        // Get all the bedTransactionsList where onLMO equals to UPDATED_ON_LMO
        defaultBedTransactionsShouldNotBeFound("onLMO.in=" + UPDATED_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnLMOIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onLMO is not null
        defaultBedTransactionsShouldBeFound("onLMO.specified=true");

        // Get all the bedTransactionsList where onLMO is null
        defaultBedTransactionsShouldNotBeFound("onLMO.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnLMOIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onLMO is greater than or equal to DEFAULT_ON_LMO
        defaultBedTransactionsShouldBeFound("onLMO.greaterThanOrEqual=" + DEFAULT_ON_LMO);

        // Get all the bedTransactionsList where onLMO is greater than or equal to UPDATED_ON_LMO
        defaultBedTransactionsShouldNotBeFound("onLMO.greaterThanOrEqual=" + UPDATED_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnLMOIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onLMO is less than or equal to DEFAULT_ON_LMO
        defaultBedTransactionsShouldBeFound("onLMO.lessThanOrEqual=" + DEFAULT_ON_LMO);

        // Get all the bedTransactionsList where onLMO is less than or equal to SMALLER_ON_LMO
        defaultBedTransactionsShouldNotBeFound("onLMO.lessThanOrEqual=" + SMALLER_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnLMOIsLessThanSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onLMO is less than DEFAULT_ON_LMO
        defaultBedTransactionsShouldNotBeFound("onLMO.lessThan=" + DEFAULT_ON_LMO);

        // Get all the bedTransactionsList where onLMO is less than UPDATED_ON_LMO
        defaultBedTransactionsShouldBeFound("onLMO.lessThan=" + UPDATED_ON_LMO);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnLMOIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onLMO is greater than DEFAULT_ON_LMO
        defaultBedTransactionsShouldNotBeFound("onLMO.greaterThan=" + DEFAULT_ON_LMO);

        // Get all the bedTransactionsList where onLMO is greater than SMALLER_ON_LMO
        defaultBedTransactionsShouldBeFound("onLMO.greaterThan=" + SMALLER_ON_LMO);
    }


    @Test
    @Transactional
    public void getAllBedTransactionsByOnConcentratorsIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onConcentrators equals to DEFAULT_ON_CONCENTRATORS
        defaultBedTransactionsShouldBeFound("onConcentrators.equals=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedTransactionsList where onConcentrators equals to UPDATED_ON_CONCENTRATORS
        defaultBedTransactionsShouldNotBeFound("onConcentrators.equals=" + UPDATED_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnConcentratorsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onConcentrators not equals to DEFAULT_ON_CONCENTRATORS
        defaultBedTransactionsShouldNotBeFound("onConcentrators.notEquals=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedTransactionsList where onConcentrators not equals to UPDATED_ON_CONCENTRATORS
        defaultBedTransactionsShouldBeFound("onConcentrators.notEquals=" + UPDATED_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnConcentratorsIsInShouldWork() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onConcentrators in DEFAULT_ON_CONCENTRATORS or UPDATED_ON_CONCENTRATORS
        defaultBedTransactionsShouldBeFound("onConcentrators.in=" + DEFAULT_ON_CONCENTRATORS + "," + UPDATED_ON_CONCENTRATORS);

        // Get all the bedTransactionsList where onConcentrators equals to UPDATED_ON_CONCENTRATORS
        defaultBedTransactionsShouldNotBeFound("onConcentrators.in=" + UPDATED_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnConcentratorsIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onConcentrators is not null
        defaultBedTransactionsShouldBeFound("onConcentrators.specified=true");

        // Get all the bedTransactionsList where onConcentrators is null
        defaultBedTransactionsShouldNotBeFound("onConcentrators.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnConcentratorsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onConcentrators is greater than or equal to DEFAULT_ON_CONCENTRATORS
        defaultBedTransactionsShouldBeFound("onConcentrators.greaterThanOrEqual=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedTransactionsList where onConcentrators is greater than or equal to UPDATED_ON_CONCENTRATORS
        defaultBedTransactionsShouldNotBeFound("onConcentrators.greaterThanOrEqual=" + UPDATED_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnConcentratorsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onConcentrators is less than or equal to DEFAULT_ON_CONCENTRATORS
        defaultBedTransactionsShouldBeFound("onConcentrators.lessThanOrEqual=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedTransactionsList where onConcentrators is less than or equal to SMALLER_ON_CONCENTRATORS
        defaultBedTransactionsShouldNotBeFound("onConcentrators.lessThanOrEqual=" + SMALLER_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnConcentratorsIsLessThanSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onConcentrators is less than DEFAULT_ON_CONCENTRATORS
        defaultBedTransactionsShouldNotBeFound("onConcentrators.lessThan=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedTransactionsList where onConcentrators is less than UPDATED_ON_CONCENTRATORS
        defaultBedTransactionsShouldBeFound("onConcentrators.lessThan=" + UPDATED_ON_CONCENTRATORS);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByOnConcentratorsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where onConcentrators is greater than DEFAULT_ON_CONCENTRATORS
        defaultBedTransactionsShouldNotBeFound("onConcentrators.greaterThan=" + DEFAULT_ON_CONCENTRATORS);

        // Get all the bedTransactionsList where onConcentrators is greater than SMALLER_ON_CONCENTRATORS
        defaultBedTransactionsShouldBeFound("onConcentrators.greaterThan=" + SMALLER_ON_CONCENTRATORS);
    }


    @Test
    @Transactional
    public void getAllBedTransactionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultBedTransactionsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the bedTransactionsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBedTransactionsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultBedTransactionsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the bedTransactionsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultBedTransactionsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultBedTransactionsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the bedTransactionsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBedTransactionsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where lastModified is not null
        defaultBedTransactionsShouldBeFound("lastModified.specified=true");

        // Get all the bedTransactionsList where lastModified is null
        defaultBedTransactionsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultBedTransactionsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedTransactionsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBedTransactionsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultBedTransactionsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedTransactionsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultBedTransactionsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultBedTransactionsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the bedTransactionsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBedTransactionsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where lastModifiedBy is not null
        defaultBedTransactionsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the bedTransactionsList where lastModifiedBy is null
        defaultBedTransactionsShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllBedTransactionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultBedTransactionsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedTransactionsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultBedTransactionsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBedTransactionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        // Get all the bedTransactionsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultBedTransactionsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bedTransactionsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultBedTransactionsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllBedTransactionsByBedTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);
        BedType bedType = BedTypeResourceIT.createEntity(em);
        em.persist(bedType);
        em.flush();
        bedTransactions.setBedType(bedType);
        bedTransactionsRepository.saveAndFlush(bedTransactions);
        Long bedTypeId = bedType.getId();

        // Get all the bedTransactionsList where bedType equals to bedTypeId
        defaultBedTransactionsShouldBeFound("bedTypeId.equals=" + bedTypeId);

        // Get all the bedTransactionsList where bedType equals to bedTypeId + 1
        defaultBedTransactionsShouldNotBeFound("bedTypeId.equals=" + (bedTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllBedTransactionsByHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);
        Hospital hospital = HospitalResourceIT.createEntity(em);
        em.persist(hospital);
        em.flush();
        bedTransactions.setHospital(hospital);
        bedTransactionsRepository.saveAndFlush(bedTransactions);
        Long hospitalId = hospital.getId();

        // Get all the bedTransactionsList where hospital equals to hospitalId
        defaultBedTransactionsShouldBeFound("hospitalId.equals=" + hospitalId);

        // Get all the bedTransactionsList where hospital equals to hospitalId + 1
        defaultBedTransactionsShouldNotBeFound("hospitalId.equals=" + (hospitalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBedTransactionsShouldBeFound(String filter) throws Exception {
        restBedTransactionsMockMvc.perform(get("/api/bed-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bedTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].occupied").value(hasItem(DEFAULT_OCCUPIED.intValue())))
            .andExpect(jsonPath("$.[*].onCylinder").value(hasItem(DEFAULT_ON_CYLINDER.intValue())))
            .andExpect(jsonPath("$.[*].onLMO").value(hasItem(DEFAULT_ON_LMO.intValue())))
            .andExpect(jsonPath("$.[*].onConcentrators").value(hasItem(DEFAULT_ON_CONCENTRATORS.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restBedTransactionsMockMvc.perform(get("/api/bed-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBedTransactionsShouldNotBeFound(String filter) throws Exception {
        restBedTransactionsMockMvc.perform(get("/api/bed-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBedTransactionsMockMvc.perform(get("/api/bed-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBedTransactions() throws Exception {
        // Get the bedTransactions
        restBedTransactionsMockMvc.perform(get("/api/bed-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBedTransactions() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        int databaseSizeBeforeUpdate = bedTransactionsRepository.findAll().size();

        // Update the bedTransactions
        BedTransactions updatedBedTransactions = bedTransactionsRepository.findById(bedTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedBedTransactions are not directly saved in db
        em.detach(updatedBedTransactions);
        updatedBedTransactions
            .occupied(UPDATED_OCCUPIED)
            .onCylinder(UPDATED_ON_CYLINDER)
            .onLMO(UPDATED_ON_LMO)
            .onConcentrators(UPDATED_ON_CONCENTRATORS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        BedTransactionsDTO bedTransactionsDTO = bedTransactionsMapper.toDto(updatedBedTransactions);

        restBedTransactionsMockMvc.perform(put("/api/bed-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the BedTransactions in the database
        List<BedTransactions> bedTransactionsList = bedTransactionsRepository.findAll();
        assertThat(bedTransactionsList).hasSize(databaseSizeBeforeUpdate);
        BedTransactions testBedTransactions = bedTransactionsList.get(bedTransactionsList.size() - 1);
        assertThat(testBedTransactions.getOccupied()).isEqualTo(UPDATED_OCCUPIED);
        assertThat(testBedTransactions.getOnCylinder()).isEqualTo(UPDATED_ON_CYLINDER);
        assertThat(testBedTransactions.getOnLMO()).isEqualTo(UPDATED_ON_LMO);
        assertThat(testBedTransactions.getOnConcentrators()).isEqualTo(UPDATED_ON_CONCENTRATORS);
        assertThat(testBedTransactions.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBedTransactions.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingBedTransactions() throws Exception {
        int databaseSizeBeforeUpdate = bedTransactionsRepository.findAll().size();

        // Create the BedTransactions
        BedTransactionsDTO bedTransactionsDTO = bedTransactionsMapper.toDto(bedTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedTransactionsMockMvc.perform(put("/api/bed-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bedTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BedTransactions in the database
        List<BedTransactions> bedTransactionsList = bedTransactionsRepository.findAll();
        assertThat(bedTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBedTransactions() throws Exception {
        // Initialize the database
        bedTransactionsRepository.saveAndFlush(bedTransactions);

        int databaseSizeBeforeDelete = bedTransactionsRepository.findAll().size();

        // Delete the bedTransactions
        restBedTransactionsMockMvc.perform(delete("/api/bed-transactions/{id}", bedTransactions.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BedTransactions> bedTransactionsList = bedTransactionsRepository.findAll();
        assertThat(bedTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
