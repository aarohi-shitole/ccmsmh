package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.InventoryUsed;
import com.techvg.covid.care.domain.Inventory;
import com.techvg.covid.care.repository.InventoryUsedRepository;
import com.techvg.covid.care.service.InventoryUsedService;
import com.techvg.covid.care.service.dto.InventoryUsedDTO;
import com.techvg.covid.care.service.mapper.InventoryUsedMapper;
import com.techvg.covid.care.service.dto.InventoryUsedCriteria;
import com.techvg.covid.care.service.InventoryUsedQueryService;

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
 * Integration tests for the {@link InventoryUsedResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryUsedResourceIT {

    private static final Long DEFAULT_STOCK = 1L;
    private static final Long UPDATED_STOCK = 2L;
    private static final Long SMALLER_STOCK = 1L - 1L;

    private static final Long DEFAULT_CAPCITY = 1L;
    private static final Long UPDATED_CAPCITY = 2L;
    private static final Long SMALLER_CAPCITY = 1L - 1L;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private InventoryUsedRepository inventoryUsedRepository;

    @Autowired
    private InventoryUsedMapper inventoryUsedMapper;

    @Autowired
    private InventoryUsedService inventoryUsedService;

    @Autowired
    private InventoryUsedQueryService inventoryUsedQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryUsedMockMvc;

    private InventoryUsed inventoryUsed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryUsed createEntity(EntityManager em) {
        InventoryUsed inventoryUsed = new InventoryUsed()
            .stock(DEFAULT_STOCK)
            .capcity(DEFAULT_CAPCITY)
            .comment(DEFAULT_COMMENT)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return inventoryUsed;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryUsed createUpdatedEntity(EntityManager em) {
        InventoryUsed inventoryUsed = new InventoryUsed()
            .stock(UPDATED_STOCK)
            .capcity(UPDATED_CAPCITY)
            .comment(UPDATED_COMMENT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return inventoryUsed;
    }

    @BeforeEach
    public void initTest() {
        inventoryUsed = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryUsed() throws Exception {
        int databaseSizeBeforeCreate = inventoryUsedRepository.findAll().size();
        // Create the InventoryUsed
        InventoryUsedDTO inventoryUsedDTO = inventoryUsedMapper.toDto(inventoryUsed);
        restInventoryUsedMockMvc.perform(post("/api/inventory-useds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryUsedDTO)))
            .andExpect(status().isCreated());

        // Validate the InventoryUsed in the database
        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryUsed testInventoryUsed = inventoryUsedList.get(inventoryUsedList.size() - 1);
        assertThat(testInventoryUsed.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testInventoryUsed.getCapcity()).isEqualTo(DEFAULT_CAPCITY);
        assertThat(testInventoryUsed.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testInventoryUsed.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testInventoryUsed.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createInventoryUsedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryUsedRepository.findAll().size();

        // Create the InventoryUsed with an existing ID
        inventoryUsed.setId(1L);
        InventoryUsedDTO inventoryUsedDTO = inventoryUsedMapper.toDto(inventoryUsed);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryUsedMockMvc.perform(post("/api/inventory-useds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryUsedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryUsed in the database
        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryUsedRepository.findAll().size();
        // set the field null
        inventoryUsed.setLastModified(null);

        // Create the InventoryUsed, which fails.
        InventoryUsedDTO inventoryUsedDTO = inventoryUsedMapper.toDto(inventoryUsed);


        restInventoryUsedMockMvc.perform(post("/api/inventory-useds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryUsedDTO)))
            .andExpect(status().isBadRequest());

        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryUsedRepository.findAll().size();
        // set the field null
        inventoryUsed.setLastModifiedBy(null);

        // Create the InventoryUsed, which fails.
        InventoryUsedDTO inventoryUsedDTO = inventoryUsedMapper.toDto(inventoryUsed);


        restInventoryUsedMockMvc.perform(post("/api/inventory-useds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryUsedDTO)))
            .andExpect(status().isBadRequest());

        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInventoryUseds() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList
        restInventoryUsedMockMvc.perform(get("/api/inventory-useds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryUsed.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].capcity").value(hasItem(DEFAULT_CAPCITY.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getInventoryUsed() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get the inventoryUsed
        restInventoryUsedMockMvc.perform(get("/api/inventory-useds/{id}", inventoryUsed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryUsed.getId().intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.intValue()))
            .andExpect(jsonPath("$.capcity").value(DEFAULT_CAPCITY.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getInventoryUsedsByIdFiltering() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        Long id = inventoryUsed.getId();

        defaultInventoryUsedShouldBeFound("id.equals=" + id);
        defaultInventoryUsedShouldNotBeFound("id.notEquals=" + id);

        defaultInventoryUsedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInventoryUsedShouldNotBeFound("id.greaterThan=" + id);

        defaultInventoryUsedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInventoryUsedShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInventoryUsedsByStockIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where stock equals to DEFAULT_STOCK
        defaultInventoryUsedShouldBeFound("stock.equals=" + DEFAULT_STOCK);

        // Get all the inventoryUsedList where stock equals to UPDATED_STOCK
        defaultInventoryUsedShouldNotBeFound("stock.equals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByStockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where stock not equals to DEFAULT_STOCK
        defaultInventoryUsedShouldNotBeFound("stock.notEquals=" + DEFAULT_STOCK);

        // Get all the inventoryUsedList where stock not equals to UPDATED_STOCK
        defaultInventoryUsedShouldBeFound("stock.notEquals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByStockIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where stock in DEFAULT_STOCK or UPDATED_STOCK
        defaultInventoryUsedShouldBeFound("stock.in=" + DEFAULT_STOCK + "," + UPDATED_STOCK);

        // Get all the inventoryUsedList where stock equals to UPDATED_STOCK
        defaultInventoryUsedShouldNotBeFound("stock.in=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where stock is not null
        defaultInventoryUsedShouldBeFound("stock.specified=true");

        // Get all the inventoryUsedList where stock is null
        defaultInventoryUsedShouldNotBeFound("stock.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where stock is greater than or equal to DEFAULT_STOCK
        defaultInventoryUsedShouldBeFound("stock.greaterThanOrEqual=" + DEFAULT_STOCK);

        // Get all the inventoryUsedList where stock is greater than or equal to UPDATED_STOCK
        defaultInventoryUsedShouldNotBeFound("stock.greaterThanOrEqual=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByStockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where stock is less than or equal to DEFAULT_STOCK
        defaultInventoryUsedShouldBeFound("stock.lessThanOrEqual=" + DEFAULT_STOCK);

        // Get all the inventoryUsedList where stock is less than or equal to SMALLER_STOCK
        defaultInventoryUsedShouldNotBeFound("stock.lessThanOrEqual=" + SMALLER_STOCK);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByStockIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where stock is less than DEFAULT_STOCK
        defaultInventoryUsedShouldNotBeFound("stock.lessThan=" + DEFAULT_STOCK);

        // Get all the inventoryUsedList where stock is less than UPDATED_STOCK
        defaultInventoryUsedShouldBeFound("stock.lessThan=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByStockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where stock is greater than DEFAULT_STOCK
        defaultInventoryUsedShouldNotBeFound("stock.greaterThan=" + DEFAULT_STOCK);

        // Get all the inventoryUsedList where stock is greater than SMALLER_STOCK
        defaultInventoryUsedShouldBeFound("stock.greaterThan=" + SMALLER_STOCK);
    }


    @Test
    @Transactional
    public void getAllInventoryUsedsByCapcityIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where capcity equals to DEFAULT_CAPCITY
        defaultInventoryUsedShouldBeFound("capcity.equals=" + DEFAULT_CAPCITY);

        // Get all the inventoryUsedList where capcity equals to UPDATED_CAPCITY
        defaultInventoryUsedShouldNotBeFound("capcity.equals=" + UPDATED_CAPCITY);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCapcityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where capcity not equals to DEFAULT_CAPCITY
        defaultInventoryUsedShouldNotBeFound("capcity.notEquals=" + DEFAULT_CAPCITY);

        // Get all the inventoryUsedList where capcity not equals to UPDATED_CAPCITY
        defaultInventoryUsedShouldBeFound("capcity.notEquals=" + UPDATED_CAPCITY);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCapcityIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where capcity in DEFAULT_CAPCITY or UPDATED_CAPCITY
        defaultInventoryUsedShouldBeFound("capcity.in=" + DEFAULT_CAPCITY + "," + UPDATED_CAPCITY);

        // Get all the inventoryUsedList where capcity equals to UPDATED_CAPCITY
        defaultInventoryUsedShouldNotBeFound("capcity.in=" + UPDATED_CAPCITY);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCapcityIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where capcity is not null
        defaultInventoryUsedShouldBeFound("capcity.specified=true");

        // Get all the inventoryUsedList where capcity is null
        defaultInventoryUsedShouldNotBeFound("capcity.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCapcityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where capcity is greater than or equal to DEFAULT_CAPCITY
        defaultInventoryUsedShouldBeFound("capcity.greaterThanOrEqual=" + DEFAULT_CAPCITY);

        // Get all the inventoryUsedList where capcity is greater than or equal to UPDATED_CAPCITY
        defaultInventoryUsedShouldNotBeFound("capcity.greaterThanOrEqual=" + UPDATED_CAPCITY);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCapcityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where capcity is less than or equal to DEFAULT_CAPCITY
        defaultInventoryUsedShouldBeFound("capcity.lessThanOrEqual=" + DEFAULT_CAPCITY);

        // Get all the inventoryUsedList where capcity is less than or equal to SMALLER_CAPCITY
        defaultInventoryUsedShouldNotBeFound("capcity.lessThanOrEqual=" + SMALLER_CAPCITY);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCapcityIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where capcity is less than DEFAULT_CAPCITY
        defaultInventoryUsedShouldNotBeFound("capcity.lessThan=" + DEFAULT_CAPCITY);

        // Get all the inventoryUsedList where capcity is less than UPDATED_CAPCITY
        defaultInventoryUsedShouldBeFound("capcity.lessThan=" + UPDATED_CAPCITY);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCapcityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where capcity is greater than DEFAULT_CAPCITY
        defaultInventoryUsedShouldNotBeFound("capcity.greaterThan=" + DEFAULT_CAPCITY);

        // Get all the inventoryUsedList where capcity is greater than SMALLER_CAPCITY
        defaultInventoryUsedShouldBeFound("capcity.greaterThan=" + SMALLER_CAPCITY);
    }


    @Test
    @Transactional
    public void getAllInventoryUsedsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where comment equals to DEFAULT_COMMENT
        defaultInventoryUsedShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the inventoryUsedList where comment equals to UPDATED_COMMENT
        defaultInventoryUsedShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where comment not equals to DEFAULT_COMMENT
        defaultInventoryUsedShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the inventoryUsedList where comment not equals to UPDATED_COMMENT
        defaultInventoryUsedShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultInventoryUsedShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the inventoryUsedList where comment equals to UPDATED_COMMENT
        defaultInventoryUsedShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where comment is not null
        defaultInventoryUsedShouldBeFound("comment.specified=true");

        // Get all the inventoryUsedList where comment is null
        defaultInventoryUsedShouldNotBeFound("comment.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryUsedsByCommentContainsSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where comment contains DEFAULT_COMMENT
        defaultInventoryUsedShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the inventoryUsedList where comment contains UPDATED_COMMENT
        defaultInventoryUsedShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where comment does not contain DEFAULT_COMMENT
        defaultInventoryUsedShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the inventoryUsedList where comment does not contain UPDATED_COMMENT
        defaultInventoryUsedShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }


    @Test
    @Transactional
    public void getAllInventoryUsedsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultInventoryUsedShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the inventoryUsedList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultInventoryUsedShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultInventoryUsedShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the inventoryUsedList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultInventoryUsedShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultInventoryUsedShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the inventoryUsedList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultInventoryUsedShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where lastModified is not null
        defaultInventoryUsedShouldBeFound("lastModified.specified=true");

        // Get all the inventoryUsedList where lastModified is null
        defaultInventoryUsedShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultInventoryUsedShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryUsedList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryUsedShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultInventoryUsedShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryUsedList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryUsedShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultInventoryUsedShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the inventoryUsedList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryUsedShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where lastModifiedBy is not null
        defaultInventoryUsedShouldBeFound("lastModifiedBy.specified=true");

        // Get all the inventoryUsedList where lastModifiedBy is null
        defaultInventoryUsedShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryUsedsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultInventoryUsedShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryUsedList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultInventoryUsedShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllInventoryUsedsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultInventoryUsedShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryUsedList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultInventoryUsedShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllInventoryUsedsByInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);
        Inventory inventory = InventoryResourceIT.createEntity(em);
        em.persist(inventory);
        em.flush();
        inventoryUsed.setInventory(inventory);
        inventoryUsedRepository.saveAndFlush(inventoryUsed);
        Long inventoryId = inventory.getId();

        // Get all the inventoryUsedList where inventory equals to inventoryId
        defaultInventoryUsedShouldBeFound("inventoryId.equals=" + inventoryId);

        // Get all the inventoryUsedList where inventory equals to inventoryId + 1
        defaultInventoryUsedShouldNotBeFound("inventoryId.equals=" + (inventoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryUsedShouldBeFound(String filter) throws Exception {
        restInventoryUsedMockMvc.perform(get("/api/inventory-useds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryUsed.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].capcity").value(hasItem(DEFAULT_CAPCITY.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restInventoryUsedMockMvc.perform(get("/api/inventory-useds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryUsedShouldNotBeFound(String filter) throws Exception {
        restInventoryUsedMockMvc.perform(get("/api/inventory-useds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryUsedMockMvc.perform(get("/api/inventory-useds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryUsed() throws Exception {
        // Get the inventoryUsed
        restInventoryUsedMockMvc.perform(get("/api/inventory-useds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryUsed() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        int databaseSizeBeforeUpdate = inventoryUsedRepository.findAll().size();

        // Update the inventoryUsed
        InventoryUsed updatedInventoryUsed = inventoryUsedRepository.findById(inventoryUsed.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryUsed are not directly saved in db
        em.detach(updatedInventoryUsed);
        updatedInventoryUsed
            .stock(UPDATED_STOCK)
            .capcity(UPDATED_CAPCITY)
            .comment(UPDATED_COMMENT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        InventoryUsedDTO inventoryUsedDTO = inventoryUsedMapper.toDto(updatedInventoryUsed);

        restInventoryUsedMockMvc.perform(put("/api/inventory-useds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryUsedDTO)))
            .andExpect(status().isOk());

        // Validate the InventoryUsed in the database
        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeUpdate);
        InventoryUsed testInventoryUsed = inventoryUsedList.get(inventoryUsedList.size() - 1);
        assertThat(testInventoryUsed.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testInventoryUsed.getCapcity()).isEqualTo(UPDATED_CAPCITY);
        assertThat(testInventoryUsed.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testInventoryUsed.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testInventoryUsed.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryUsed() throws Exception {
        int databaseSizeBeforeUpdate = inventoryUsedRepository.findAll().size();

        // Create the InventoryUsed
        InventoryUsedDTO inventoryUsedDTO = inventoryUsedMapper.toDto(inventoryUsed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryUsedMockMvc.perform(put("/api/inventory-useds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryUsedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryUsed in the database
        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryUsed() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        int databaseSizeBeforeDelete = inventoryUsedRepository.findAll().size();

        // Delete the inventoryUsed
        restInventoryUsedMockMvc.perform(delete("/api/inventory-useds/{id}", inventoryUsed.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
