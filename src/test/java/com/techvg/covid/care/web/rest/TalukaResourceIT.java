package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.Taluka;
import com.techvg.covid.care.domain.District;
import com.techvg.covid.care.repository.TalukaRepository;
import com.techvg.covid.care.service.TalukaService;
import com.techvg.covid.care.service.dto.TalukaDTO;
import com.techvg.covid.care.service.mapper.TalukaMapper;
import com.techvg.covid.care.service.dto.TalukaCriteria;
import com.techvg.covid.care.service.TalukaQueryService;

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
 * Integration tests for the {@link TalukaResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TalukaResourceIT {

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
    private TalukaRepository talukaRepository;

    @Autowired
    private TalukaMapper talukaMapper;

    @Autowired
    private TalukaService talukaService;

    @Autowired
    private TalukaQueryService talukaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTalukaMockMvc;

    private Taluka taluka;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taluka createEntity(EntityManager em) {
        Taluka taluka = new Taluka()
            .name(DEFAULT_NAME)
            .deleted(DEFAULT_DELETED)
            .lgdCode(DEFAULT_LGD_CODE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return taluka;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taluka createUpdatedEntity(EntityManager em) {
        Taluka taluka = new Taluka()
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return taluka;
    }

    @BeforeEach
    public void initTest() {
        taluka = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaluka() throws Exception {
        int databaseSizeBeforeCreate = talukaRepository.findAll().size();
        // Create the Taluka
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);
        restTalukaMockMvc.perform(post("/api/talukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isCreated());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeCreate + 1);
        Taluka testTaluka = talukaList.get(talukaList.size() - 1);
        assertThat(testTaluka.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaluka.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testTaluka.getLgdCode()).isEqualTo(DEFAULT_LGD_CODE);
        assertThat(testTaluka.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTaluka.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createTalukaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = talukaRepository.findAll().size();

        // Create the Taluka with an existing ID
        taluka.setId(1L);
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTalukaMockMvc.perform(post("/api/talukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = talukaRepository.findAll().size();
        // set the field null
        taluka.setName(null);

        // Create the Taluka, which fails.
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);


        restTalukaMockMvc.perform(post("/api/talukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isBadRequest());

        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = talukaRepository.findAll().size();
        // set the field null
        taluka.setLastModified(null);

        // Create the Taluka, which fails.
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);


        restTalukaMockMvc.perform(post("/api/talukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isBadRequest());

        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = talukaRepository.findAll().size();
        // set the field null
        taluka.setLastModifiedBy(null);

        // Create the Taluka, which fails.
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);


        restTalukaMockMvc.perform(post("/api/talukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isBadRequest());

        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTalukas() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList
        restTalukaMockMvc.perform(get("/api/talukas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taluka.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lgdCode").value(hasItem(DEFAULT_LGD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getTaluka() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get the taluka
        restTalukaMockMvc.perform(get("/api/talukas/{id}", taluka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taluka.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lgdCode").value(DEFAULT_LGD_CODE.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getTalukasByIdFiltering() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        Long id = taluka.getId();

        defaultTalukaShouldBeFound("id.equals=" + id);
        defaultTalukaShouldNotBeFound("id.notEquals=" + id);

        defaultTalukaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTalukaShouldNotBeFound("id.greaterThan=" + id);

        defaultTalukaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTalukaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTalukasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where name equals to DEFAULT_NAME
        defaultTalukaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the talukaList where name equals to UPDATED_NAME
        defaultTalukaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTalukasByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where name not equals to DEFAULT_NAME
        defaultTalukaShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the talukaList where name not equals to UPDATED_NAME
        defaultTalukaShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTalukasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTalukaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the talukaList where name equals to UPDATED_NAME
        defaultTalukaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTalukasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where name is not null
        defaultTalukaShouldBeFound("name.specified=true");

        // Get all the talukaList where name is null
        defaultTalukaShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTalukasByNameContainsSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where name contains DEFAULT_NAME
        defaultTalukaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the talukaList where name contains UPDATED_NAME
        defaultTalukaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTalukasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where name does not contain DEFAULT_NAME
        defaultTalukaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the talukaList where name does not contain UPDATED_NAME
        defaultTalukaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTalukasByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where deleted equals to DEFAULT_DELETED
        defaultTalukaShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the talukaList where deleted equals to UPDATED_DELETED
        defaultTalukaShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllTalukasByDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where deleted not equals to DEFAULT_DELETED
        defaultTalukaShouldNotBeFound("deleted.notEquals=" + DEFAULT_DELETED);

        // Get all the talukaList where deleted not equals to UPDATED_DELETED
        defaultTalukaShouldBeFound("deleted.notEquals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllTalukasByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultTalukaShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the talukaList where deleted equals to UPDATED_DELETED
        defaultTalukaShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllTalukasByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where deleted is not null
        defaultTalukaShouldBeFound("deleted.specified=true");

        // Get all the talukaList where deleted is null
        defaultTalukaShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllTalukasByLgdCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode equals to DEFAULT_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.equals=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode equals to UPDATED_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.equals=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllTalukasByLgdCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode not equals to DEFAULT_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.notEquals=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode not equals to UPDATED_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.notEquals=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllTalukasByLgdCodeIsInShouldWork() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode in DEFAULT_LGD_CODE or UPDATED_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.in=" + DEFAULT_LGD_CODE + "," + UPDATED_LGD_CODE);

        // Get all the talukaList where lgdCode equals to UPDATED_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.in=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllTalukasByLgdCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode is not null
        defaultTalukaShouldBeFound("lgdCode.specified=true");

        // Get all the talukaList where lgdCode is null
        defaultTalukaShouldNotBeFound("lgdCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllTalukasByLgdCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode is greater than or equal to DEFAULT_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.greaterThanOrEqual=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode is greater than or equal to UPDATED_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.greaterThanOrEqual=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllTalukasByLgdCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode is less than or equal to DEFAULT_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.lessThanOrEqual=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode is less than or equal to SMALLER_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.lessThanOrEqual=" + SMALLER_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllTalukasByLgdCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode is less than DEFAULT_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.lessThan=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode is less than UPDATED_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.lessThan=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    public void getAllTalukasByLgdCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode is greater than DEFAULT_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.greaterThan=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode is greater than SMALLER_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.greaterThan=" + SMALLER_LGD_CODE);
    }


    @Test
    @Transactional
    public void getAllTalukasByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTalukaShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the talukaList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTalukaShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllTalukasByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTalukaShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the talukaList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTalukaShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllTalukasByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTalukaShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the talukaList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTalukaShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllTalukasByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModified is not null
        defaultTalukaShouldBeFound("lastModified.specified=true");

        // Get all the talukaList where lastModified is null
        defaultTalukaShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllTalukasByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTalukaShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the talukaList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTalukasByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTalukaShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the talukaList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTalukasByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the talukaList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTalukasByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy is not null
        defaultTalukaShouldBeFound("lastModifiedBy.specified=true");

        // Get all the talukaList where lastModifiedBy is null
        defaultTalukaShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllTalukasByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTalukaShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the talukaList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTalukasByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTalukaShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the talukaList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllTalukasByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);
        District district = DistrictResourceIT.createEntity(em);
        em.persist(district);
        em.flush();
        taluka.setDistrict(district);
        talukaRepository.saveAndFlush(taluka);
        Long districtId = district.getId();

        // Get all the talukaList where district equals to districtId
        defaultTalukaShouldBeFound("districtId.equals=" + districtId);

        // Get all the talukaList where district equals to districtId + 1
        defaultTalukaShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTalukaShouldBeFound(String filter) throws Exception {
        restTalukaMockMvc.perform(get("/api/talukas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taluka.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lgdCode").value(hasItem(DEFAULT_LGD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTalukaMockMvc.perform(get("/api/talukas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTalukaShouldNotBeFound(String filter) throws Exception {
        restTalukaMockMvc.perform(get("/api/talukas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTalukaMockMvc.perform(get("/api/talukas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTaluka() throws Exception {
        // Get the taluka
        restTalukaMockMvc.perform(get("/api/talukas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaluka() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();

        // Update the taluka
        Taluka updatedTaluka = talukaRepository.findById(taluka.getId()).get();
        // Disconnect from session so that the updates on updatedTaluka are not directly saved in db
        em.detach(updatedTaluka);
        updatedTaluka
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TalukaDTO talukaDTO = talukaMapper.toDto(updatedTaluka);

        restTalukaMockMvc.perform(put("/api/talukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isOk());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
        Taluka testTaluka = talukaList.get(talukaList.size() - 1);
        assertThat(testTaluka.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaluka.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testTaluka.getLgdCode()).isEqualTo(UPDATED_LGD_CODE);
        assertThat(testTaluka.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTaluka.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingTaluka() throws Exception {
        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();

        // Create the Taluka
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTalukaMockMvc.perform(put("/api/talukas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaluka() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        int databaseSizeBeforeDelete = talukaRepository.findAll().size();

        // Delete the taluka
        restTalukaMockMvc.perform(delete("/api/talukas/{id}", taluka.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
