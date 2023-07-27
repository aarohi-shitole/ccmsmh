package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.AuditType;
import com.techvg.covid.care.repository.AuditTypeRepository;
import com.techvg.covid.care.service.AuditTypeService;
import com.techvg.covid.care.service.dto.AuditTypeDTO;
import com.techvg.covid.care.service.mapper.AuditTypeMapper;
import com.techvg.covid.care.service.dto.AuditTypeCriteria;
import com.techvg.covid.care.service.AuditTypeQueryService;

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
 * Integration tests for the {@link AuditTypeResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AuditTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private AuditTypeRepository auditTypeRepository;

    @Autowired
    private AuditTypeMapper auditTypeMapper;

    @Autowired
    private AuditTypeService auditTypeService;

    @Autowired
    private AuditTypeQueryService auditTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditTypeMockMvc;

    private AuditType auditType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditType createEntity(EntityManager em) {
        AuditType auditType = new AuditType()
            .name(DEFAULT_NAME)
            .deleted(DEFAULT_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return auditType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditType createUpdatedEntity(EntityManager em) {
        AuditType auditType = new AuditType()
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return auditType;
    }

    @BeforeEach
    public void initTest() {
        auditType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditType() throws Exception {
        int databaseSizeBeforeCreate = auditTypeRepository.findAll().size();
        // Create the AuditType
        AuditTypeDTO auditTypeDTO = auditTypeMapper.toDto(auditType);
        restAuditTypeMockMvc.perform(post("/api/audit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AuditType in the database
        List<AuditType> auditTypeList = auditTypeRepository.findAll();
        assertThat(auditTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AuditType testAuditType = auditTypeList.get(auditTypeList.size() - 1);
        assertThat(testAuditType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuditType.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testAuditType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAuditType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createAuditTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditTypeRepository.findAll().size();

        // Create the AuditType with an existing ID
        auditType.setId(1L);
        AuditTypeDTO auditTypeDTO = auditTypeMapper.toDto(auditType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditTypeMockMvc.perform(post("/api/audit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditType in the database
        List<AuditType> auditTypeList = auditTypeRepository.findAll();
        assertThat(auditTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTypeRepository.findAll().size();
        // set the field null
        auditType.setName(null);

        // Create the AuditType, which fails.
        AuditTypeDTO auditTypeDTO = auditTypeMapper.toDto(auditType);


        restAuditTypeMockMvc.perform(post("/api/audit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AuditType> auditTypeList = auditTypeRepository.findAll();
        assertThat(auditTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTypeRepository.findAll().size();
        // set the field null
        auditType.setLastModified(null);

        // Create the AuditType, which fails.
        AuditTypeDTO auditTypeDTO = auditTypeMapper.toDto(auditType);


        restAuditTypeMockMvc.perform(post("/api/audit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AuditType> auditTypeList = auditTypeRepository.findAll();
        assertThat(auditTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditTypeRepository.findAll().size();
        // set the field null
        auditType.setLastModifiedBy(null);

        // Create the AuditType, which fails.
        AuditTypeDTO auditTypeDTO = auditTypeMapper.toDto(auditType);


        restAuditTypeMockMvc.perform(post("/api/audit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AuditType> auditTypeList = auditTypeRepository.findAll();
        assertThat(auditTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAuditTypes() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList
        restAuditTypeMockMvc.perform(get("/api/audit-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getAuditType() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get the auditType
        restAuditTypeMockMvc.perform(get("/api/audit-types/{id}", auditType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getAuditTypesByIdFiltering() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        Long id = auditType.getId();

        defaultAuditTypeShouldBeFound("id.equals=" + id);
        defaultAuditTypeShouldNotBeFound("id.notEquals=" + id);

        defaultAuditTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAuditTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultAuditTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAuditTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAuditTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where name equals to DEFAULT_NAME
        defaultAuditTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the auditTypeList where name equals to UPDATED_NAME
        defaultAuditTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where name not equals to DEFAULT_NAME
        defaultAuditTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the auditTypeList where name not equals to UPDATED_NAME
        defaultAuditTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAuditTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the auditTypeList where name equals to UPDATED_NAME
        defaultAuditTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where name is not null
        defaultAuditTypeShouldBeFound("name.specified=true");

        // Get all the auditTypeList where name is null
        defaultAuditTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAuditTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where name contains DEFAULT_NAME
        defaultAuditTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the auditTypeList where name contains UPDATED_NAME
        defaultAuditTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where name does not contain DEFAULT_NAME
        defaultAuditTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the auditTypeList where name does not contain UPDATED_NAME
        defaultAuditTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAuditTypesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where deleted equals to DEFAULT_DELETED
        defaultAuditTypeShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the auditTypeList where deleted equals to UPDATED_DELETED
        defaultAuditTypeShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where deleted not equals to DEFAULT_DELETED
        defaultAuditTypeShouldNotBeFound("deleted.notEquals=" + DEFAULT_DELETED);

        // Get all the auditTypeList where deleted not equals to UPDATED_DELETED
        defaultAuditTypeShouldBeFound("deleted.notEquals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultAuditTypeShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the auditTypeList where deleted equals to UPDATED_DELETED
        defaultAuditTypeShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where deleted is not null
        defaultAuditTypeShouldBeFound("deleted.specified=true");

        // Get all the auditTypeList where deleted is null
        defaultAuditTypeShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuditTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAuditTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the auditTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAuditTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultAuditTypeShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the auditTypeList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultAuditTypeShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAuditTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the auditTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAuditTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where lastModified is not null
        defaultAuditTypeShouldBeFound("lastModified.specified=true");

        // Get all the auditTypeList where lastModified is null
        defaultAuditTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuditTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAuditTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the auditTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAuditTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultAuditTypeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the auditTypeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultAuditTypeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAuditTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the auditTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAuditTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where lastModifiedBy is not null
        defaultAuditTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the auditTypeList where lastModifiedBy is null
        defaultAuditTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllAuditTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAuditTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the auditTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAuditTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAuditTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        // Get all the auditTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAuditTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the auditTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAuditTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuditTypeShouldBeFound(String filter) throws Exception {
        restAuditTypeMockMvc.perform(get("/api/audit-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAuditTypeMockMvc.perform(get("/api/audit-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuditTypeShouldNotBeFound(String filter) throws Exception {
        restAuditTypeMockMvc.perform(get("/api/audit-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuditTypeMockMvc.perform(get("/api/audit-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAuditType() throws Exception {
        // Get the auditType
        restAuditTypeMockMvc.perform(get("/api/audit-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditType() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        int databaseSizeBeforeUpdate = auditTypeRepository.findAll().size();

        // Update the auditType
        AuditType updatedAuditType = auditTypeRepository.findById(auditType.getId()).get();
        // Disconnect from session so that the updates on updatedAuditType are not directly saved in db
        em.detach(updatedAuditType);
        updatedAuditType
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AuditTypeDTO auditTypeDTO = auditTypeMapper.toDto(updatedAuditType);

        restAuditTypeMockMvc.perform(put("/api/audit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditTypeDTO)))
            .andExpect(status().isOk());

        // Validate the AuditType in the database
        List<AuditType> auditTypeList = auditTypeRepository.findAll();
        assertThat(auditTypeList).hasSize(databaseSizeBeforeUpdate);
        AuditType testAuditType = auditTypeList.get(auditTypeList.size() - 1);
        assertThat(testAuditType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuditType.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testAuditType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAuditType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditType() throws Exception {
        int databaseSizeBeforeUpdate = auditTypeRepository.findAll().size();

        // Create the AuditType
        AuditTypeDTO auditTypeDTO = auditTypeMapper.toDto(auditType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditTypeMockMvc.perform(put("/api/audit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditType in the database
        List<AuditType> auditTypeList = auditTypeRepository.findAll();
        assertThat(auditTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuditType() throws Exception {
        // Initialize the database
        auditTypeRepository.saveAndFlush(auditType);

        int databaseSizeBeforeDelete = auditTypeRepository.findAll().size();

        // Delete the auditType
        restAuditTypeMockMvc.perform(delete("/api/audit-types/{id}", auditType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditType> auditTypeList = auditTypeRepository.findAll();
        assertThat(auditTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
