package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.MunicipalCorp;
import com.techvg.covid.care.domain.District;
import com.techvg.covid.care.repository.MunicipalCorpRepository;
import com.techvg.covid.care.service.MunicipalCorpService;
import com.techvg.covid.care.service.dto.MunicipalCorpDTO;
import com.techvg.covid.care.service.mapper.MunicipalCorpMapper;
import com.techvg.covid.care.service.dto.MunicipalCorpCriteria;
import com.techvg.covid.care.service.MunicipalCorpQueryService;

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
 * Integration tests for the {@link MunicipalCorpResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MunicipalCorpResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private MunicipalCorpRepository municipalCorpRepository;

    @Autowired
    private MunicipalCorpMapper municipalCorpMapper;

    @Autowired
    private MunicipalCorpService municipalCorpService;

    @Autowired
    private MunicipalCorpQueryService municipalCorpQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMunicipalCorpMockMvc;

    private MunicipalCorp municipalCorp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MunicipalCorp createEntity(EntityManager em) {
        MunicipalCorp municipalCorp = new MunicipalCorp()
            .name(DEFAULT_NAME)
            .deleted(DEFAULT_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return municipalCorp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MunicipalCorp createUpdatedEntity(EntityManager em) {
        MunicipalCorp municipalCorp = new MunicipalCorp()
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return municipalCorp;
    }

    @BeforeEach
    public void initTest() {
        municipalCorp = createEntity(em);
    }

    @Test
    @Transactional
    public void createMunicipalCorp() throws Exception {
        int databaseSizeBeforeCreate = municipalCorpRepository.findAll().size();
        // Create the MunicipalCorp
        MunicipalCorpDTO municipalCorpDTO = municipalCorpMapper.toDto(municipalCorp);
        restMunicipalCorpMockMvc.perform(post("/api/municipal-corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipalCorpDTO)))
            .andExpect(status().isCreated());

        // Validate the MunicipalCorp in the database
        List<MunicipalCorp> municipalCorpList = municipalCorpRepository.findAll();
        assertThat(municipalCorpList).hasSize(databaseSizeBeforeCreate + 1);
        MunicipalCorp testMunicipalCorp = municipalCorpList.get(municipalCorpList.size() - 1);
        assertThat(testMunicipalCorp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMunicipalCorp.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testMunicipalCorp.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMunicipalCorp.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createMunicipalCorpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = municipalCorpRepository.findAll().size();

        // Create the MunicipalCorp with an existing ID
        municipalCorp.setId(1L);
        MunicipalCorpDTO municipalCorpDTO = municipalCorpMapper.toDto(municipalCorp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMunicipalCorpMockMvc.perform(post("/api/municipal-corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipalCorpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MunicipalCorp in the database
        List<MunicipalCorp> municipalCorpList = municipalCorpRepository.findAll();
        assertThat(municipalCorpList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = municipalCorpRepository.findAll().size();
        // set the field null
        municipalCorp.setName(null);

        // Create the MunicipalCorp, which fails.
        MunicipalCorpDTO municipalCorpDTO = municipalCorpMapper.toDto(municipalCorp);


        restMunicipalCorpMockMvc.perform(post("/api/municipal-corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipalCorpDTO)))
            .andExpect(status().isBadRequest());

        List<MunicipalCorp> municipalCorpList = municipalCorpRepository.findAll();
        assertThat(municipalCorpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = municipalCorpRepository.findAll().size();
        // set the field null
        municipalCorp.setLastModified(null);

        // Create the MunicipalCorp, which fails.
        MunicipalCorpDTO municipalCorpDTO = municipalCorpMapper.toDto(municipalCorp);


        restMunicipalCorpMockMvc.perform(post("/api/municipal-corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipalCorpDTO)))
            .andExpect(status().isBadRequest());

        List<MunicipalCorp> municipalCorpList = municipalCorpRepository.findAll();
        assertThat(municipalCorpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = municipalCorpRepository.findAll().size();
        // set the field null
        municipalCorp.setLastModifiedBy(null);

        // Create the MunicipalCorp, which fails.
        MunicipalCorpDTO municipalCorpDTO = municipalCorpMapper.toDto(municipalCorp);


        restMunicipalCorpMockMvc.perform(post("/api/municipal-corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipalCorpDTO)))
            .andExpect(status().isBadRequest());

        List<MunicipalCorp> municipalCorpList = municipalCorpRepository.findAll();
        assertThat(municipalCorpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorps() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList
        restMunicipalCorpMockMvc.perform(get("/api/municipal-corps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipalCorp.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getMunicipalCorp() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get the municipalCorp
        restMunicipalCorpMockMvc.perform(get("/api/municipal-corps/{id}", municipalCorp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(municipalCorp.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getMunicipalCorpsByIdFiltering() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        Long id = municipalCorp.getId();

        defaultMunicipalCorpShouldBeFound("id.equals=" + id);
        defaultMunicipalCorpShouldNotBeFound("id.notEquals=" + id);

        defaultMunicipalCorpShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMunicipalCorpShouldNotBeFound("id.greaterThan=" + id);

        defaultMunicipalCorpShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMunicipalCorpShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMunicipalCorpsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where name equals to DEFAULT_NAME
        defaultMunicipalCorpShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the municipalCorpList where name equals to UPDATED_NAME
        defaultMunicipalCorpShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where name not equals to DEFAULT_NAME
        defaultMunicipalCorpShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the municipalCorpList where name not equals to UPDATED_NAME
        defaultMunicipalCorpShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMunicipalCorpShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the municipalCorpList where name equals to UPDATED_NAME
        defaultMunicipalCorpShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where name is not null
        defaultMunicipalCorpShouldBeFound("name.specified=true");

        // Get all the municipalCorpList where name is null
        defaultMunicipalCorpShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMunicipalCorpsByNameContainsSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where name contains DEFAULT_NAME
        defaultMunicipalCorpShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the municipalCorpList where name contains UPDATED_NAME
        defaultMunicipalCorpShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where name does not contain DEFAULT_NAME
        defaultMunicipalCorpShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the municipalCorpList where name does not contain UPDATED_NAME
        defaultMunicipalCorpShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMunicipalCorpsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where deleted equals to DEFAULT_DELETED
        defaultMunicipalCorpShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the municipalCorpList where deleted equals to UPDATED_DELETED
        defaultMunicipalCorpShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where deleted not equals to DEFAULT_DELETED
        defaultMunicipalCorpShouldNotBeFound("deleted.notEquals=" + DEFAULT_DELETED);

        // Get all the municipalCorpList where deleted not equals to UPDATED_DELETED
        defaultMunicipalCorpShouldBeFound("deleted.notEquals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultMunicipalCorpShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the municipalCorpList where deleted equals to UPDATED_DELETED
        defaultMunicipalCorpShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where deleted is not null
        defaultMunicipalCorpShouldBeFound("deleted.specified=true");

        // Get all the municipalCorpList where deleted is null
        defaultMunicipalCorpShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMunicipalCorpShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the municipalCorpList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMunicipalCorpShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultMunicipalCorpShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the municipalCorpList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultMunicipalCorpShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMunicipalCorpShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the municipalCorpList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMunicipalCorpShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where lastModified is not null
        defaultMunicipalCorpShouldBeFound("lastModified.specified=true");

        // Get all the municipalCorpList where lastModified is null
        defaultMunicipalCorpShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMunicipalCorpShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the municipalCorpList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMunicipalCorpShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultMunicipalCorpShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the municipalCorpList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultMunicipalCorpShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMunicipalCorpShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the municipalCorpList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMunicipalCorpShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where lastModifiedBy is not null
        defaultMunicipalCorpShouldBeFound("lastModifiedBy.specified=true");

        // Get all the municipalCorpList where lastModifiedBy is null
        defaultMunicipalCorpShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllMunicipalCorpsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMunicipalCorpShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the municipalCorpList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMunicipalCorpShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMunicipalCorpsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        // Get all the municipalCorpList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMunicipalCorpShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the municipalCorpList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMunicipalCorpShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllMunicipalCorpsByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);
        District district = DistrictResourceIT.createEntity(em);
        em.persist(district);
        em.flush();
        municipalCorp.setDistrict(district);
        municipalCorpRepository.saveAndFlush(municipalCorp);
        Long districtId = district.getId();

        // Get all the municipalCorpList where district equals to districtId
        defaultMunicipalCorpShouldBeFound("districtId.equals=" + districtId);

        // Get all the municipalCorpList where district equals to districtId + 1
        defaultMunicipalCorpShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMunicipalCorpShouldBeFound(String filter) throws Exception {
        restMunicipalCorpMockMvc.perform(get("/api/municipal-corps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipalCorp.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restMunicipalCorpMockMvc.perform(get("/api/municipal-corps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMunicipalCorpShouldNotBeFound(String filter) throws Exception {
        restMunicipalCorpMockMvc.perform(get("/api/municipal-corps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMunicipalCorpMockMvc.perform(get("/api/municipal-corps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMunicipalCorp() throws Exception {
        // Get the municipalCorp
        restMunicipalCorpMockMvc.perform(get("/api/municipal-corps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMunicipalCorp() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        int databaseSizeBeforeUpdate = municipalCorpRepository.findAll().size();

        // Update the municipalCorp
        MunicipalCorp updatedMunicipalCorp = municipalCorpRepository.findById(municipalCorp.getId()).get();
        // Disconnect from session so that the updates on updatedMunicipalCorp are not directly saved in db
        em.detach(updatedMunicipalCorp);
        updatedMunicipalCorp
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        MunicipalCorpDTO municipalCorpDTO = municipalCorpMapper.toDto(updatedMunicipalCorp);

        restMunicipalCorpMockMvc.perform(put("/api/municipal-corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipalCorpDTO)))
            .andExpect(status().isOk());

        // Validate the MunicipalCorp in the database
        List<MunicipalCorp> municipalCorpList = municipalCorpRepository.findAll();
        assertThat(municipalCorpList).hasSize(databaseSizeBeforeUpdate);
        MunicipalCorp testMunicipalCorp = municipalCorpList.get(municipalCorpList.size() - 1);
        assertThat(testMunicipalCorp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMunicipalCorp.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testMunicipalCorp.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMunicipalCorp.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingMunicipalCorp() throws Exception {
        int databaseSizeBeforeUpdate = municipalCorpRepository.findAll().size();

        // Create the MunicipalCorp
        MunicipalCorpDTO municipalCorpDTO = municipalCorpMapper.toDto(municipalCorp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMunicipalCorpMockMvc.perform(put("/api/municipal-corps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipalCorpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MunicipalCorp in the database
        List<MunicipalCorp> municipalCorpList = municipalCorpRepository.findAll();
        assertThat(municipalCorpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMunicipalCorp() throws Exception {
        // Initialize the database
        municipalCorpRepository.saveAndFlush(municipalCorp);

        int databaseSizeBeforeDelete = municipalCorpRepository.findAll().size();

        // Delete the municipalCorp
        restMunicipalCorpMockMvc.perform(delete("/api/municipal-corps/{id}", municipalCorp.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MunicipalCorp> municipalCorpList = municipalCorpRepository.findAll();
        assertThat(municipalCorpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
