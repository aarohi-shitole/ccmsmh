package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.CovidCareApp;
import com.techvg.covid.care.domain.SecurityUser;
import com.techvg.covid.care.domain.SecurityPermission;
import com.techvg.covid.care.domain.SecurityRole;
import com.techvg.covid.care.repository.SecurityUserRepository;
import com.techvg.covid.care.service.SecurityUserService;
import com.techvg.covid.care.service.dto.SecurityUserDTO;
import com.techvg.covid.care.service.mapper.SecurityUserMapper;
import com.techvg.covid.care.service.dto.SecurityUserCriteria;
import com.techvg.covid.care.service.SecurityUserQueryService;

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
 * Integration tests for the {@link SecurityUserResource} REST controller.
 */
@SpringBootTest(classes = CovidCareApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SecurityUserResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_LANG_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LANG_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_RESET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_RESET_KEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESET_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESET_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ONE_TIME_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_ONE_TIME_PASSWORD = "BBBBBBBBBB";

    private static final Instant DEFAULT_OTP_EXPIRY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OTP_EXPIRY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Mock
    private SecurityUserRepository securityUserRepositoryMock;

    @Autowired
    private SecurityUserMapper securityUserMapper;

    @Mock
    private SecurityUserService securityUserServiceMock;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private SecurityUserQueryService securityUserQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecurityUserMockMvc;

    private SecurityUser securityUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityUser createEntity(EntityManager em) {
        SecurityUser securityUser = new SecurityUser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .designation(DEFAULT_DESIGNATION)
            .login(DEFAULT_LOGIN)
            .passwordHash(DEFAULT_PASSWORD_HASH)
            .email(DEFAULT_EMAIL)
            .imageUrl(DEFAULT_IMAGE_URL)
            .activated(DEFAULT_ACTIVATED)
            .langKey(DEFAULT_LANG_KEY)
            .activationKey(DEFAULT_ACTIVATION_KEY)
            .resetKey(DEFAULT_RESET_KEY)
            .resetDate(DEFAULT_RESET_DATE)
            .mobileNo(DEFAULT_MOBILE_NO)
            .oneTimePassword(DEFAULT_ONE_TIME_PASSWORD)
            .otpExpiryTime(DEFAULT_OTP_EXPIRY_TIME)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return securityUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecurityUser createUpdatedEntity(EntityManager em) {
        SecurityUser securityUser = new SecurityUser()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .designation(UPDATED_DESIGNATION)
            .login(UPDATED_LOGIN)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE)
            .mobileNo(UPDATED_MOBILE_NO)
            .oneTimePassword(UPDATED_ONE_TIME_PASSWORD)
            .otpExpiryTime(UPDATED_OTP_EXPIRY_TIME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return securityUser;
    }

    @BeforeEach
    public void initTest() {
        securityUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecurityUser() throws Exception {
        int databaseSizeBeforeCreate = securityUserRepository.findAll().size();
        // Create the SecurityUser
        SecurityUserDTO securityUserDTO = securityUserMapper.toDto(securityUser);
        restSecurityUserMockMvc.perform(post("/api/security-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityUserDTO)))
            .andExpect(status().isCreated());

        // Validate the SecurityUser in the database
        List<SecurityUser> securityUserList = securityUserRepository.findAll();
        assertThat(securityUserList).hasSize(databaseSizeBeforeCreate + 1);
        SecurityUser testSecurityUser = securityUserList.get(securityUserList.size() - 1);
        assertThat(testSecurityUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSecurityUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSecurityUser.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testSecurityUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testSecurityUser.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testSecurityUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSecurityUser.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testSecurityUser.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testSecurityUser.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
        assertThat(testSecurityUser.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testSecurityUser.getResetKey()).isEqualTo(DEFAULT_RESET_KEY);
        assertThat(testSecurityUser.getResetDate()).isEqualTo(DEFAULT_RESET_DATE);
        assertThat(testSecurityUser.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testSecurityUser.getOneTimePassword()).isEqualTo(DEFAULT_ONE_TIME_PASSWORD);
        assertThat(testSecurityUser.getOtpExpiryTime()).isEqualTo(DEFAULT_OTP_EXPIRY_TIME);
        assertThat(testSecurityUser.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSecurityUser.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createSecurityUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = securityUserRepository.findAll().size();

        // Create the SecurityUser with an existing ID
        securityUser.setId(1L);
        SecurityUserDTO securityUserDTO = securityUserMapper.toDto(securityUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecurityUserMockMvc.perform(post("/api/security-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecurityUser in the database
        List<SecurityUser> securityUserList = securityUserRepository.findAll();
        assertThat(securityUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityUserRepository.findAll().size();
        // set the field null
        securityUser.setLogin(null);

        // Create the SecurityUser, which fails.
        SecurityUserDTO securityUserDTO = securityUserMapper.toDto(securityUser);


        restSecurityUserMockMvc.perform(post("/api/security-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityUserDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityUser> securityUserList = securityUserRepository.findAll();
        assertThat(securityUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityUserRepository.findAll().size();
        // set the field null
        securityUser.setPasswordHash(null);

        // Create the SecurityUser, which fails.
        SecurityUserDTO securityUserDTO = securityUserMapper.toDto(securityUser);


        restSecurityUserMockMvc.perform(post("/api/security-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityUserDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityUser> securityUserList = securityUserRepository.findAll();
        assertThat(securityUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityUserRepository.findAll().size();
        // set the field null
        securityUser.setActivated(null);

        // Create the SecurityUser, which fails.
        SecurityUserDTO securityUserDTO = securityUserMapper.toDto(securityUser);


        restSecurityUserMockMvc.perform(post("/api/security-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityUserDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityUser> securityUserList = securityUserRepository.findAll();
        assertThat(securityUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityUserRepository.findAll().size();
        // set the field null
        securityUser.setLastModified(null);

        // Create the SecurityUser, which fails.
        SecurityUserDTO securityUserDTO = securityUserMapper.toDto(securityUser);


        restSecurityUserMockMvc.perform(post("/api/security-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityUserDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityUser> securityUserList = securityUserRepository.findAll();
        assertThat(securityUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityUserRepository.findAll().size();
        // set the field null
        securityUser.setLastModifiedBy(null);

        // Create the SecurityUser, which fails.
        SecurityUserDTO securityUserDTO = securityUserMapper.toDto(securityUser);


        restSecurityUserMockMvc.perform(post("/api/security-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityUserDTO)))
            .andExpect(status().isBadRequest());

        List<SecurityUser> securityUserList = securityUserRepository.findAll();
        assertThat(securityUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSecurityUsers() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList
        restSecurityUserMockMvc.perform(get("/api/security-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securityUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY)))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY)))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY)))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(DEFAULT_RESET_DATE.toString())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].oneTimePassword").value(hasItem(DEFAULT_ONE_TIME_PASSWORD)))
            .andExpect(jsonPath("$.[*].otpExpiryTime").value(hasItem(DEFAULT_OTP_EXPIRY_TIME.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSecurityUsersWithEagerRelationshipsIsEnabled() throws Exception {
        when(securityUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSecurityUserMockMvc.perform(get("/api/security-users?eagerload=true"))
            .andExpect(status().isOk());

        verify(securityUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSecurityUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(securityUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSecurityUserMockMvc.perform(get("/api/security-users?eagerload=true"))
            .andExpect(status().isOk());

        verify(securityUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSecurityUser() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get the securityUser
        restSecurityUserMockMvc.perform(get("/api/security-users/{id}", securityUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(securityUser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.passwordHash").value(DEFAULT_PASSWORD_HASH))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.langKey").value(DEFAULT_LANG_KEY))
            .andExpect(jsonPath("$.activationKey").value(DEFAULT_ACTIVATION_KEY))
            .andExpect(jsonPath("$.resetKey").value(DEFAULT_RESET_KEY))
            .andExpect(jsonPath("$.resetDate").value(DEFAULT_RESET_DATE.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.oneTimePassword").value(DEFAULT_ONE_TIME_PASSWORD))
            .andExpect(jsonPath("$.otpExpiryTime").value(DEFAULT_OTP_EXPIRY_TIME.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }


    @Test
    @Transactional
    public void getSecurityUsersByIdFiltering() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        Long id = securityUser.getId();

        defaultSecurityUserShouldBeFound("id.equals=" + id);
        defaultSecurityUserShouldNotBeFound("id.notEquals=" + id);

        defaultSecurityUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSecurityUserShouldNotBeFound("id.greaterThan=" + id);

        defaultSecurityUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSecurityUserShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where firstName equals to DEFAULT_FIRST_NAME
        defaultSecurityUserShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the securityUserList where firstName equals to UPDATED_FIRST_NAME
        defaultSecurityUserShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where firstName not equals to DEFAULT_FIRST_NAME
        defaultSecurityUserShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the securityUserList where firstName not equals to UPDATED_FIRST_NAME
        defaultSecurityUserShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultSecurityUserShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the securityUserList where firstName equals to UPDATED_FIRST_NAME
        defaultSecurityUserShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where firstName is not null
        defaultSecurityUserShouldBeFound("firstName.specified=true");

        // Get all the securityUserList where firstName is null
        defaultSecurityUserShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where firstName contains DEFAULT_FIRST_NAME
        defaultSecurityUserShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the securityUserList where firstName contains UPDATED_FIRST_NAME
        defaultSecurityUserShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where firstName does not contain DEFAULT_FIRST_NAME
        defaultSecurityUserShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the securityUserList where firstName does not contain UPDATED_FIRST_NAME
        defaultSecurityUserShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastName equals to DEFAULT_LAST_NAME
        defaultSecurityUserShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the securityUserList where lastName equals to UPDATED_LAST_NAME
        defaultSecurityUserShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastName not equals to DEFAULT_LAST_NAME
        defaultSecurityUserShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the securityUserList where lastName not equals to UPDATED_LAST_NAME
        defaultSecurityUserShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultSecurityUserShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the securityUserList where lastName equals to UPDATED_LAST_NAME
        defaultSecurityUserShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastName is not null
        defaultSecurityUserShouldBeFound("lastName.specified=true");

        // Get all the securityUserList where lastName is null
        defaultSecurityUserShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastName contains DEFAULT_LAST_NAME
        defaultSecurityUserShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the securityUserList where lastName contains UPDATED_LAST_NAME
        defaultSecurityUserShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastName does not contain DEFAULT_LAST_NAME
        defaultSecurityUserShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the securityUserList where lastName does not contain UPDATED_LAST_NAME
        defaultSecurityUserShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where designation equals to DEFAULT_DESIGNATION
        defaultSecurityUserShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the securityUserList where designation equals to UPDATED_DESIGNATION
        defaultSecurityUserShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where designation not equals to DEFAULT_DESIGNATION
        defaultSecurityUserShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the securityUserList where designation not equals to UPDATED_DESIGNATION
        defaultSecurityUserShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultSecurityUserShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the securityUserList where designation equals to UPDATED_DESIGNATION
        defaultSecurityUserShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where designation is not null
        defaultSecurityUserShouldBeFound("designation.specified=true");

        // Get all the securityUserList where designation is null
        defaultSecurityUserShouldNotBeFound("designation.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByDesignationContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where designation contains DEFAULT_DESIGNATION
        defaultSecurityUserShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the securityUserList where designation contains UPDATED_DESIGNATION
        defaultSecurityUserShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where designation does not contain DEFAULT_DESIGNATION
        defaultSecurityUserShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the securityUserList where designation does not contain UPDATED_DESIGNATION
        defaultSecurityUserShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where login equals to DEFAULT_LOGIN
        defaultSecurityUserShouldBeFound("login.equals=" + DEFAULT_LOGIN);

        // Get all the securityUserList where login equals to UPDATED_LOGIN
        defaultSecurityUserShouldNotBeFound("login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLoginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where login not equals to DEFAULT_LOGIN
        defaultSecurityUserShouldNotBeFound("login.notEquals=" + DEFAULT_LOGIN);

        // Get all the securityUserList where login not equals to UPDATED_LOGIN
        defaultSecurityUserShouldBeFound("login.notEquals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where login in DEFAULT_LOGIN or UPDATED_LOGIN
        defaultSecurityUserShouldBeFound("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN);

        // Get all the securityUserList where login equals to UPDATED_LOGIN
        defaultSecurityUserShouldNotBeFound("login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where login is not null
        defaultSecurityUserShouldBeFound("login.specified=true");

        // Get all the securityUserList where login is null
        defaultSecurityUserShouldNotBeFound("login.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByLoginContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where login contains DEFAULT_LOGIN
        defaultSecurityUserShouldBeFound("login.contains=" + DEFAULT_LOGIN);

        // Get all the securityUserList where login contains UPDATED_LOGIN
        defaultSecurityUserShouldNotBeFound("login.contains=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLoginNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where login does not contain DEFAULT_LOGIN
        defaultSecurityUserShouldNotBeFound("login.doesNotContain=" + DEFAULT_LOGIN);

        // Get all the securityUserList where login does not contain UPDATED_LOGIN
        defaultSecurityUserShouldBeFound("login.doesNotContain=" + UPDATED_LOGIN);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByPasswordHashIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where passwordHash equals to DEFAULT_PASSWORD_HASH
        defaultSecurityUserShouldBeFound("passwordHash.equals=" + DEFAULT_PASSWORD_HASH);

        // Get all the securityUserList where passwordHash equals to UPDATED_PASSWORD_HASH
        defaultSecurityUserShouldNotBeFound("passwordHash.equals=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByPasswordHashIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where passwordHash not equals to DEFAULT_PASSWORD_HASH
        defaultSecurityUserShouldNotBeFound("passwordHash.notEquals=" + DEFAULT_PASSWORD_HASH);

        // Get all the securityUserList where passwordHash not equals to UPDATED_PASSWORD_HASH
        defaultSecurityUserShouldBeFound("passwordHash.notEquals=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByPasswordHashIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where passwordHash in DEFAULT_PASSWORD_HASH or UPDATED_PASSWORD_HASH
        defaultSecurityUserShouldBeFound("passwordHash.in=" + DEFAULT_PASSWORD_HASH + "," + UPDATED_PASSWORD_HASH);

        // Get all the securityUserList where passwordHash equals to UPDATED_PASSWORD_HASH
        defaultSecurityUserShouldNotBeFound("passwordHash.in=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByPasswordHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where passwordHash is not null
        defaultSecurityUserShouldBeFound("passwordHash.specified=true");

        // Get all the securityUserList where passwordHash is null
        defaultSecurityUserShouldNotBeFound("passwordHash.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByPasswordHashContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where passwordHash contains DEFAULT_PASSWORD_HASH
        defaultSecurityUserShouldBeFound("passwordHash.contains=" + DEFAULT_PASSWORD_HASH);

        // Get all the securityUserList where passwordHash contains UPDATED_PASSWORD_HASH
        defaultSecurityUserShouldNotBeFound("passwordHash.contains=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByPasswordHashNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where passwordHash does not contain DEFAULT_PASSWORD_HASH
        defaultSecurityUserShouldNotBeFound("passwordHash.doesNotContain=" + DEFAULT_PASSWORD_HASH);

        // Get all the securityUserList where passwordHash does not contain UPDATED_PASSWORD_HASH
        defaultSecurityUserShouldBeFound("passwordHash.doesNotContain=" + UPDATED_PASSWORD_HASH);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where email equals to DEFAULT_EMAIL
        defaultSecurityUserShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the securityUserList where email equals to UPDATED_EMAIL
        defaultSecurityUserShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where email not equals to DEFAULT_EMAIL
        defaultSecurityUserShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the securityUserList where email not equals to UPDATED_EMAIL
        defaultSecurityUserShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultSecurityUserShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the securityUserList where email equals to UPDATED_EMAIL
        defaultSecurityUserShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where email is not null
        defaultSecurityUserShouldBeFound("email.specified=true");

        // Get all the securityUserList where email is null
        defaultSecurityUserShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByEmailContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where email contains DEFAULT_EMAIL
        defaultSecurityUserShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the securityUserList where email contains UPDATED_EMAIL
        defaultSecurityUserShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where email does not contain DEFAULT_EMAIL
        defaultSecurityUserShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the securityUserList where email does not contain UPDATED_EMAIL
        defaultSecurityUserShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultSecurityUserShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the securityUserList where imageUrl equals to UPDATED_IMAGE_URL
        defaultSecurityUserShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultSecurityUserShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the securityUserList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultSecurityUserShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultSecurityUserShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the securityUserList where imageUrl equals to UPDATED_IMAGE_URL
        defaultSecurityUserShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where imageUrl is not null
        defaultSecurityUserShouldBeFound("imageUrl.specified=true");

        // Get all the securityUserList where imageUrl is null
        defaultSecurityUserShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where imageUrl contains DEFAULT_IMAGE_URL
        defaultSecurityUserShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the securityUserList where imageUrl contains UPDATED_IMAGE_URL
        defaultSecurityUserShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultSecurityUserShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the securityUserList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultSecurityUserShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where activated equals to DEFAULT_ACTIVATED
        defaultSecurityUserShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the securityUserList where activated equals to UPDATED_ACTIVATED
        defaultSecurityUserShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where activated not equals to DEFAULT_ACTIVATED
        defaultSecurityUserShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the securityUserList where activated not equals to UPDATED_ACTIVATED
        defaultSecurityUserShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultSecurityUserShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the securityUserList where activated equals to UPDATED_ACTIVATED
        defaultSecurityUserShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where activated is not null
        defaultSecurityUserShouldBeFound("activated.specified=true");

        // Get all the securityUserList where activated is null
        defaultSecurityUserShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLangKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where langKey equals to DEFAULT_LANG_KEY
        defaultSecurityUserShouldBeFound("langKey.equals=" + DEFAULT_LANG_KEY);

        // Get all the securityUserList where langKey equals to UPDATED_LANG_KEY
        defaultSecurityUserShouldNotBeFound("langKey.equals=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLangKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where langKey not equals to DEFAULT_LANG_KEY
        defaultSecurityUserShouldNotBeFound("langKey.notEquals=" + DEFAULT_LANG_KEY);

        // Get all the securityUserList where langKey not equals to UPDATED_LANG_KEY
        defaultSecurityUserShouldBeFound("langKey.notEquals=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLangKeyIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where langKey in DEFAULT_LANG_KEY or UPDATED_LANG_KEY
        defaultSecurityUserShouldBeFound("langKey.in=" + DEFAULT_LANG_KEY + "," + UPDATED_LANG_KEY);

        // Get all the securityUserList where langKey equals to UPDATED_LANG_KEY
        defaultSecurityUserShouldNotBeFound("langKey.in=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLangKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where langKey is not null
        defaultSecurityUserShouldBeFound("langKey.specified=true");

        // Get all the securityUserList where langKey is null
        defaultSecurityUserShouldNotBeFound("langKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByLangKeyContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where langKey contains DEFAULT_LANG_KEY
        defaultSecurityUserShouldBeFound("langKey.contains=" + DEFAULT_LANG_KEY);

        // Get all the securityUserList where langKey contains UPDATED_LANG_KEY
        defaultSecurityUserShouldNotBeFound("langKey.contains=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLangKeyNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where langKey does not contain DEFAULT_LANG_KEY
        defaultSecurityUserShouldNotBeFound("langKey.doesNotContain=" + DEFAULT_LANG_KEY);

        // Get all the securityUserList where langKey does not contain UPDATED_LANG_KEY
        defaultSecurityUserShouldBeFound("langKey.doesNotContain=" + UPDATED_LANG_KEY);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByActivationKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where activationKey equals to DEFAULT_ACTIVATION_KEY
        defaultSecurityUserShouldBeFound("activationKey.equals=" + DEFAULT_ACTIVATION_KEY);

        // Get all the securityUserList where activationKey equals to UPDATED_ACTIVATION_KEY
        defaultSecurityUserShouldNotBeFound("activationKey.equals=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByActivationKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where activationKey not equals to DEFAULT_ACTIVATION_KEY
        defaultSecurityUserShouldNotBeFound("activationKey.notEquals=" + DEFAULT_ACTIVATION_KEY);

        // Get all the securityUserList where activationKey not equals to UPDATED_ACTIVATION_KEY
        defaultSecurityUserShouldBeFound("activationKey.notEquals=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByActivationKeyIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where activationKey in DEFAULT_ACTIVATION_KEY or UPDATED_ACTIVATION_KEY
        defaultSecurityUserShouldBeFound("activationKey.in=" + DEFAULT_ACTIVATION_KEY + "," + UPDATED_ACTIVATION_KEY);

        // Get all the securityUserList where activationKey equals to UPDATED_ACTIVATION_KEY
        defaultSecurityUserShouldNotBeFound("activationKey.in=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByActivationKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where activationKey is not null
        defaultSecurityUserShouldBeFound("activationKey.specified=true");

        // Get all the securityUserList where activationKey is null
        defaultSecurityUserShouldNotBeFound("activationKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByActivationKeyContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where activationKey contains DEFAULT_ACTIVATION_KEY
        defaultSecurityUserShouldBeFound("activationKey.contains=" + DEFAULT_ACTIVATION_KEY);

        // Get all the securityUserList where activationKey contains UPDATED_ACTIVATION_KEY
        defaultSecurityUserShouldNotBeFound("activationKey.contains=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByActivationKeyNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where activationKey does not contain DEFAULT_ACTIVATION_KEY
        defaultSecurityUserShouldNotBeFound("activationKey.doesNotContain=" + DEFAULT_ACTIVATION_KEY);

        // Get all the securityUserList where activationKey does not contain UPDATED_ACTIVATION_KEY
        defaultSecurityUserShouldBeFound("activationKey.doesNotContain=" + UPDATED_ACTIVATION_KEY);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByResetKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where resetKey equals to DEFAULT_RESET_KEY
        defaultSecurityUserShouldBeFound("resetKey.equals=" + DEFAULT_RESET_KEY);

        // Get all the securityUserList where resetKey equals to UPDATED_RESET_KEY
        defaultSecurityUserShouldNotBeFound("resetKey.equals=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByResetKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where resetKey not equals to DEFAULT_RESET_KEY
        defaultSecurityUserShouldNotBeFound("resetKey.notEquals=" + DEFAULT_RESET_KEY);

        // Get all the securityUserList where resetKey not equals to UPDATED_RESET_KEY
        defaultSecurityUserShouldBeFound("resetKey.notEquals=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByResetKeyIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where resetKey in DEFAULT_RESET_KEY or UPDATED_RESET_KEY
        defaultSecurityUserShouldBeFound("resetKey.in=" + DEFAULT_RESET_KEY + "," + UPDATED_RESET_KEY);

        // Get all the securityUserList where resetKey equals to UPDATED_RESET_KEY
        defaultSecurityUserShouldNotBeFound("resetKey.in=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByResetKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where resetKey is not null
        defaultSecurityUserShouldBeFound("resetKey.specified=true");

        // Get all the securityUserList where resetKey is null
        defaultSecurityUserShouldNotBeFound("resetKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByResetKeyContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where resetKey contains DEFAULT_RESET_KEY
        defaultSecurityUserShouldBeFound("resetKey.contains=" + DEFAULT_RESET_KEY);

        // Get all the securityUserList where resetKey contains UPDATED_RESET_KEY
        defaultSecurityUserShouldNotBeFound("resetKey.contains=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByResetKeyNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where resetKey does not contain DEFAULT_RESET_KEY
        defaultSecurityUserShouldNotBeFound("resetKey.doesNotContain=" + DEFAULT_RESET_KEY);

        // Get all the securityUserList where resetKey does not contain UPDATED_RESET_KEY
        defaultSecurityUserShouldBeFound("resetKey.doesNotContain=" + UPDATED_RESET_KEY);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByResetDateIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where resetDate equals to DEFAULT_RESET_DATE
        defaultSecurityUserShouldBeFound("resetDate.equals=" + DEFAULT_RESET_DATE);

        // Get all the securityUserList where resetDate equals to UPDATED_RESET_DATE
        defaultSecurityUserShouldNotBeFound("resetDate.equals=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByResetDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where resetDate not equals to DEFAULT_RESET_DATE
        defaultSecurityUserShouldNotBeFound("resetDate.notEquals=" + DEFAULT_RESET_DATE);

        // Get all the securityUserList where resetDate not equals to UPDATED_RESET_DATE
        defaultSecurityUserShouldBeFound("resetDate.notEquals=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByResetDateIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where resetDate in DEFAULT_RESET_DATE or UPDATED_RESET_DATE
        defaultSecurityUserShouldBeFound("resetDate.in=" + DEFAULT_RESET_DATE + "," + UPDATED_RESET_DATE);

        // Get all the securityUserList where resetDate equals to UPDATED_RESET_DATE
        defaultSecurityUserShouldNotBeFound("resetDate.in=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByResetDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where resetDate is not null
        defaultSecurityUserShouldBeFound("resetDate.specified=true");

        // Get all the securityUserList where resetDate is null
        defaultSecurityUserShouldNotBeFound("resetDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultSecurityUserShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the securityUserList where mobileNo equals to UPDATED_MOBILE_NO
        defaultSecurityUserShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByMobileNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where mobileNo not equals to DEFAULT_MOBILE_NO
        defaultSecurityUserShouldNotBeFound("mobileNo.notEquals=" + DEFAULT_MOBILE_NO);

        // Get all the securityUserList where mobileNo not equals to UPDATED_MOBILE_NO
        defaultSecurityUserShouldBeFound("mobileNo.notEquals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultSecurityUserShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the securityUserList where mobileNo equals to UPDATED_MOBILE_NO
        defaultSecurityUserShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where mobileNo is not null
        defaultSecurityUserShouldBeFound("mobileNo.specified=true");

        // Get all the securityUserList where mobileNo is null
        defaultSecurityUserShouldNotBeFound("mobileNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where mobileNo contains DEFAULT_MOBILE_NO
        defaultSecurityUserShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the securityUserList where mobileNo contains UPDATED_MOBILE_NO
        defaultSecurityUserShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultSecurityUserShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the securityUserList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultSecurityUserShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByOneTimePasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where oneTimePassword equals to DEFAULT_ONE_TIME_PASSWORD
        defaultSecurityUserShouldBeFound("oneTimePassword.equals=" + DEFAULT_ONE_TIME_PASSWORD);

        // Get all the securityUserList where oneTimePassword equals to UPDATED_ONE_TIME_PASSWORD
        defaultSecurityUserShouldNotBeFound("oneTimePassword.equals=" + UPDATED_ONE_TIME_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByOneTimePasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where oneTimePassword not equals to DEFAULT_ONE_TIME_PASSWORD
        defaultSecurityUserShouldNotBeFound("oneTimePassword.notEquals=" + DEFAULT_ONE_TIME_PASSWORD);

        // Get all the securityUserList where oneTimePassword not equals to UPDATED_ONE_TIME_PASSWORD
        defaultSecurityUserShouldBeFound("oneTimePassword.notEquals=" + UPDATED_ONE_TIME_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByOneTimePasswordIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where oneTimePassword in DEFAULT_ONE_TIME_PASSWORD or UPDATED_ONE_TIME_PASSWORD
        defaultSecurityUserShouldBeFound("oneTimePassword.in=" + DEFAULT_ONE_TIME_PASSWORD + "," + UPDATED_ONE_TIME_PASSWORD);

        // Get all the securityUserList where oneTimePassword equals to UPDATED_ONE_TIME_PASSWORD
        defaultSecurityUserShouldNotBeFound("oneTimePassword.in=" + UPDATED_ONE_TIME_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByOneTimePasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where oneTimePassword is not null
        defaultSecurityUserShouldBeFound("oneTimePassword.specified=true");

        // Get all the securityUserList where oneTimePassword is null
        defaultSecurityUserShouldNotBeFound("oneTimePassword.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByOneTimePasswordContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where oneTimePassword contains DEFAULT_ONE_TIME_PASSWORD
        defaultSecurityUserShouldBeFound("oneTimePassword.contains=" + DEFAULT_ONE_TIME_PASSWORD);

        // Get all the securityUserList where oneTimePassword contains UPDATED_ONE_TIME_PASSWORD
        defaultSecurityUserShouldNotBeFound("oneTimePassword.contains=" + UPDATED_ONE_TIME_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByOneTimePasswordNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where oneTimePassword does not contain DEFAULT_ONE_TIME_PASSWORD
        defaultSecurityUserShouldNotBeFound("oneTimePassword.doesNotContain=" + DEFAULT_ONE_TIME_PASSWORD);

        // Get all the securityUserList where oneTimePassword does not contain UPDATED_ONE_TIME_PASSWORD
        defaultSecurityUserShouldBeFound("oneTimePassword.doesNotContain=" + UPDATED_ONE_TIME_PASSWORD);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersByOtpExpiryTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where otpExpiryTime equals to DEFAULT_OTP_EXPIRY_TIME
        defaultSecurityUserShouldBeFound("otpExpiryTime.equals=" + DEFAULT_OTP_EXPIRY_TIME);

        // Get all the securityUserList where otpExpiryTime equals to UPDATED_OTP_EXPIRY_TIME
        defaultSecurityUserShouldNotBeFound("otpExpiryTime.equals=" + UPDATED_OTP_EXPIRY_TIME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByOtpExpiryTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where otpExpiryTime not equals to DEFAULT_OTP_EXPIRY_TIME
        defaultSecurityUserShouldNotBeFound("otpExpiryTime.notEquals=" + DEFAULT_OTP_EXPIRY_TIME);

        // Get all the securityUserList where otpExpiryTime not equals to UPDATED_OTP_EXPIRY_TIME
        defaultSecurityUserShouldBeFound("otpExpiryTime.notEquals=" + UPDATED_OTP_EXPIRY_TIME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByOtpExpiryTimeIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where otpExpiryTime in DEFAULT_OTP_EXPIRY_TIME or UPDATED_OTP_EXPIRY_TIME
        defaultSecurityUserShouldBeFound("otpExpiryTime.in=" + DEFAULT_OTP_EXPIRY_TIME + "," + UPDATED_OTP_EXPIRY_TIME);

        // Get all the securityUserList where otpExpiryTime equals to UPDATED_OTP_EXPIRY_TIME
        defaultSecurityUserShouldNotBeFound("otpExpiryTime.in=" + UPDATED_OTP_EXPIRY_TIME);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByOtpExpiryTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where otpExpiryTime is not null
        defaultSecurityUserShouldBeFound("otpExpiryTime.specified=true");

        // Get all the securityUserList where otpExpiryTime is null
        defaultSecurityUserShouldNotBeFound("otpExpiryTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSecurityUserShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the securityUserList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSecurityUserShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultSecurityUserShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the securityUserList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultSecurityUserShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSecurityUserShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the securityUserList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSecurityUserShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastModified is not null
        defaultSecurityUserShouldBeFound("lastModified.specified=true");

        // Get all the securityUserList where lastModified is null
        defaultSecurityUserShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSecurityUserShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityUserList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSecurityUserShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultSecurityUserShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityUserList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultSecurityUserShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSecurityUserShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the securityUserList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSecurityUserShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastModifiedBy is not null
        defaultSecurityUserShouldBeFound("lastModifiedBy.specified=true");

        // Get all the securityUserList where lastModifiedBy is null
        defaultSecurityUserShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecurityUsersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSecurityUserShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityUserList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSecurityUserShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSecurityUsersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        // Get all the securityUserList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSecurityUserShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the securityUserList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSecurityUserShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllSecurityUsersBySecurityPermissionIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);
        SecurityPermission securityPermission = SecurityPermissionResourceIT.createEntity(em);
        em.persist(securityPermission);
        em.flush();
        securityUser.addSecurityPermission(securityPermission);
        securityUserRepository.saveAndFlush(securityUser);
        Long securityPermissionId = securityPermission.getId();

        // Get all the securityUserList where securityPermission equals to securityPermissionId
        defaultSecurityUserShouldBeFound("securityPermissionId.equals=" + securityPermissionId);

        // Get all the securityUserList where securityPermission equals to securityPermissionId + 1
        defaultSecurityUserShouldNotBeFound("securityPermissionId.equals=" + (securityPermissionId + 1));
    }


    @Test
    @Transactional
    public void getAllSecurityUsersBySecurityRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);
        SecurityRole securityRole = SecurityRoleResourceIT.createEntity(em);
        em.persist(securityRole);
        em.flush();
        securityUser.addSecurityRole(securityRole);
        securityUserRepository.saveAndFlush(securityUser);
        Long securityRoleId = securityRole.getId();

        // Get all the securityUserList where securityRole equals to securityRoleId
        defaultSecurityUserShouldBeFound("securityRoleId.equals=" + securityRoleId);

        // Get all the securityUserList where securityRole equals to securityRoleId + 1
        defaultSecurityUserShouldNotBeFound("securityRoleId.equals=" + (securityRoleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSecurityUserShouldBeFound(String filter) throws Exception {
        restSecurityUserMockMvc.perform(get("/api/security-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securityUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY)))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY)))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY)))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(DEFAULT_RESET_DATE.toString())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].oneTimePassword").value(hasItem(DEFAULT_ONE_TIME_PASSWORD)))
            .andExpect(jsonPath("$.[*].otpExpiryTime").value(hasItem(DEFAULT_OTP_EXPIRY_TIME.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restSecurityUserMockMvc.perform(get("/api/security-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSecurityUserShouldNotBeFound(String filter) throws Exception {
        restSecurityUserMockMvc.perform(get("/api/security-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSecurityUserMockMvc.perform(get("/api/security-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSecurityUser() throws Exception {
        // Get the securityUser
        restSecurityUserMockMvc.perform(get("/api/security-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecurityUser() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        int databaseSizeBeforeUpdate = securityUserRepository.findAll().size();

        // Update the securityUser
        SecurityUser updatedSecurityUser = securityUserRepository.findById(securityUser.getId()).get();
        // Disconnect from session so that the updates on updatedSecurityUser are not directly saved in db
        em.detach(updatedSecurityUser);
        updatedSecurityUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .designation(UPDATED_DESIGNATION)
            .login(UPDATED_LOGIN)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE)
            .mobileNo(UPDATED_MOBILE_NO)
            .oneTimePassword(UPDATED_ONE_TIME_PASSWORD)
            .otpExpiryTime(UPDATED_OTP_EXPIRY_TIME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        SecurityUserDTO securityUserDTO = securityUserMapper.toDto(updatedSecurityUser);

        restSecurityUserMockMvc.perform(put("/api/security-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityUserDTO)))
            .andExpect(status().isOk());

        // Validate the SecurityUser in the database
        List<SecurityUser> securityUserList = securityUserRepository.findAll();
        assertThat(securityUserList).hasSize(databaseSizeBeforeUpdate);
        SecurityUser testSecurityUser = securityUserList.get(securityUserList.size() - 1);
        assertThat(testSecurityUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSecurityUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSecurityUser.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testSecurityUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testSecurityUser.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testSecurityUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSecurityUser.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSecurityUser.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testSecurityUser.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
        assertThat(testSecurityUser.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testSecurityUser.getResetKey()).isEqualTo(UPDATED_RESET_KEY);
        assertThat(testSecurityUser.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
        assertThat(testSecurityUser.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testSecurityUser.getOneTimePassword()).isEqualTo(UPDATED_ONE_TIME_PASSWORD);
        assertThat(testSecurityUser.getOtpExpiryTime()).isEqualTo(UPDATED_OTP_EXPIRY_TIME);
        assertThat(testSecurityUser.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSecurityUser.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingSecurityUser() throws Exception {
        int databaseSizeBeforeUpdate = securityUserRepository.findAll().size();

        // Create the SecurityUser
        SecurityUserDTO securityUserDTO = securityUserMapper.toDto(securityUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecurityUserMockMvc.perform(put("/api/security-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(securityUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecurityUser in the database
        List<SecurityUser> securityUserList = securityUserRepository.findAll();
        assertThat(securityUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSecurityUser() throws Exception {
        // Initialize the database
        securityUserRepository.saveAndFlush(securityUser);

        int databaseSizeBeforeDelete = securityUserRepository.findAll().size();

        // Delete the securityUser
        restSecurityUserMockMvc.perform(delete("/api/security-users/{id}", securityUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SecurityUser> securityUserList = securityUserRepository.findAll();
        assertThat(securityUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
