package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.AddOnsSelectedByOrganizationAsserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.AddOnsSelectedByOrganization;
import com.saathratri.developer.blog.domain.AddOnsSelectedByOrganizationId;
import com.saathratri.developer.blog.repository.AddOnsSelectedByOrganizationRepository;
import com.saathratri.developer.blog.service.dto.AddOnsSelectedByOrganizationDTO;
import com.saathratri.developer.blog.service.mapper.AddOnsSelectedByOrganizationMapper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link AddOnsSelectedByOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddOnsSelectedByOrganizationResourceIT {

    private static final UUID DEFAULT_ORGANIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORGANIZATION_ID = UUID.randomUUID();

    private static final Long DEFAULT_ARRIVAL_DATE = 1L;
    private static final Long UPDATED_ARRIVAL_DATE = 2L;

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final UUID DEFAULT_CREATED_TIME_ID = UUID.randomUUID();
    private static final UUID UPDATED_CREATED_TIME_ID = UUID.randomUUID();

    private static final Long DEFAULT_DEPARTURE_DATE = 1L;
    private static final Long UPDATED_DEPARTURE_DATE = 2L;

    private static final UUID DEFAULT_CUSTOMER_ID = UUID.randomUUID();
    private static final UUID UPDATED_CUSTOMER_ID = UUID.randomUUID();

    private static final String DEFAULT_CUSTOMER_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_UPDATED_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_UPDATED_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ESTIMATED_ARRIVAL_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ESTIMATED_ARRIVAL_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_TINY_URL_SHORT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TINY_URL_SHORT_CODE = "BBBBBBBBBB";

    private static final Map<String, String> DEFAULT_ADD_ON_DETAILS_TEXT = new HashMap<String, String>();
    private static final Map<String, String> UPDATED_ADD_ON_DETAILS_TEXT = new HashMap<String, String>();

    static {
        DEFAULT_ADD_ON_DETAILS_TEXT.put("AAAAAAAAAA", "1");
        UPDATED_ADD_ON_DETAILS_TEXT.put("AAAAAAAAAA", "2");
    }

    private static final Map<String, BigDecimal> DEFAULT_ADD_ON_DETAILS_DECIMAL = new HashMap<String, BigDecimal>();
    private static final Map<String, BigDecimal> UPDATED_ADD_ON_DETAILS_DECIMAL = new HashMap<String, BigDecimal>();

    static {
        DEFAULT_ADD_ON_DETAILS_DECIMAL.put("AAAAAAAAAA", new BigDecimal(1));
        UPDATED_ADD_ON_DETAILS_DECIMAL.put("AAAAAAAAAA", new BigDecimal(2));
    }

    private static final Map<String, Boolean> DEFAULT_ADD_ON_DETAILS_BOOLEAN = new HashMap<String, Boolean>();
    private static final Map<String, Boolean> UPDATED_ADD_ON_DETAILS_BOOLEAN = new HashMap<String, Boolean>();

    static {
        DEFAULT_ADD_ON_DETAILS_BOOLEAN.put("AAAAAAAAAA", false);
        UPDATED_ADD_ON_DETAILS_BOOLEAN.put("AAAAAAAAAA", true);
    }

    private static final Map<String, Long> DEFAULT_ADD_ON_DETAILS_BIG_INT = new HashMap<String, Long>();
    private static final Map<String, Long> UPDATED_ADD_ON_DETAILS_BIG_INT = new HashMap<String, Long>();

    static {
        DEFAULT_ADD_ON_DETAILS_BIG_INT.put("AAAAAAAAAA", 1L);
        UPDATED_ADD_ON_DETAILS_BIG_INT.put("AAAAAAAAAA", 2L);
    }

    private static final String ENTITY_API_URL = "/api/add-ons-selected-by-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AddOnsSelectedByOrganizationRepository addOnsSelectedByOrganizationRepository;

    @Autowired
    private AddOnsSelectedByOrganizationMapper addOnsSelectedByOrganizationMapper;

    @Autowired
    private MockMvc restAddOnsSelectedByOrganizationMockMvc;

    private AddOnsSelectedByOrganization addOnsSelectedByOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddOnsSelectedByOrganization createEntity() {
        AddOnsSelectedByOrganization addOnsSelectedByOrganization = new AddOnsSelectedByOrganization()
            .compositeId(
                new AddOnsSelectedByOrganizationId()
                    .organizationId(DEFAULT_ORGANIZATION_ID)
                    .arrivalDate(DEFAULT_ARRIVAL_DATE)
                    .accountNumber(DEFAULT_ACCOUNT_NUMBER)
                    .createdTimeId(DEFAULT_CREATED_TIME_ID)
            )
            .departureDate(1L)
            .customerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .customerFirstName("customerFirstName1")
            .customerLastName("customerLastName1")
            .customerUpdatedEmail("customerUpdatedEmail1")
            .customerUpdatedPhoneNumber("customerUpdatedPhoneNumber1")
            .customerEstimatedArrivalTime("customerEstimatedArrivalTime1")
            .tinyUrlShortCode("tinyUrlShortCode1")
            .addOnDetailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("addOnDetailsText1", "addOnDetailsText1");
                    }
                }
            )
            .addOnDetailsDecimal(
                new java.util.HashMap<String, BigDecimal>() {
                    {
                        put("addOnDetailsDecimal1", new BigDecimal(1));
                    }
                }
            )
            .addOnDetailsBoolean(
                new java.util.HashMap<String, Boolean>() {
                    {
                        put("addOnDetailsBoolean1", false);
                    }
                }
            )
            .addOnDetailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("addOnDetailsBigInt1", 1L);
                    }
                }
            );
        addOnsSelectedByOrganization.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                DEFAULT_ORGANIZATION_ID,
                DEFAULT_ARRIVAL_DATE,
                DEFAULT_ACCOUNT_NUMBER,
                DEFAULT_CREATED_TIME_ID
            )
        );
        return addOnsSelectedByOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddOnsSelectedByOrganization createUpdatedEntity() {
        AddOnsSelectedByOrganization addOnsSelectedByOrganization = new AddOnsSelectedByOrganization()
            .compositeId(
                new AddOnsSelectedByOrganizationId()
                    .organizationId(UPDATED_ORGANIZATION_ID)
                    .arrivalDate(UPDATED_ARRIVAL_DATE)
                    .accountNumber(UPDATED_ACCOUNT_NUMBER)
                    .createdTimeId(UPDATED_CREATED_TIME_ID)
            )
            .departureDate(1L)
            .customerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .customerFirstName("customerFirstName1")
            .customerLastName("customerLastName1")
            .customerUpdatedEmail("customerUpdatedEmail1")
            .customerUpdatedPhoneNumber("customerUpdatedPhoneNumber1")
            .customerEstimatedArrivalTime("customerEstimatedArrivalTime1")
            .tinyUrlShortCode("tinyUrlShortCode1")
            .addOnDetailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("addOnDetailsText1", "addOnDetailsText1");
                    }
                }
            )
            .addOnDetailsDecimal(
                new java.util.HashMap<String, BigDecimal>() {
                    {
                        put("addOnDetailsDecimal1", new BigDecimal(1));
                    }
                }
            )
            .addOnDetailsBoolean(
                new java.util.HashMap<String, Boolean>() {
                    {
                        put("addOnDetailsBoolean1", false);
                    }
                }
            )
            .addOnDetailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("addOnDetailsBigInt1", 1L);
                    }
                }
            );
        addOnsSelectedByOrganization.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                UPDATED_ORGANIZATION_ID,
                UPDATED_ARRIVAL_DATE,
                UPDATED_ACCOUNT_NUMBER,
                UPDATED_CREATED_TIME_ID
            )
        );
        return addOnsSelectedByOrganization;
    }

    @BeforeEach
    public void initTest() {
        addOnsSelectedByOrganizationRepository.deleteAll();
        addOnsSelectedByOrganization = createEntity();
    }

    @Test
    void createAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AddOnsSelectedByOrganization
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationMapper.toDto(
            addOnsSelectedByOrganization
        );
        var returnedAddOnsSelectedByOrganizationDTO = om.readValue(
            restAddOnsSelectedByOrganizationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(addOnsSelectedByOrganizationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AddOnsSelectedByOrganizationDTO.class
        );

        // Validate the AddOnsSelectedByOrganization in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAddOnsSelectedByOrganization = addOnsSelectedByOrganizationMapper.toEntity(returnedAddOnsSelectedByOrganizationDTO);
        assertAddOnsSelectedByOrganizationUpdatableFieldsEquals(
            returnedAddOnsSelectedByOrganization,
            getPersistedAddOnsSelectedByOrganization(returnedAddOnsSelectedByOrganization)
        );
    }

    @Test
    void createAddOnsSelectedByOrganizationWithExistingId() throws Exception {
        // Create the AddOnsSelectedByOrganization with an existing ID
        addOnsSelectedByOrganization.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                DEFAULT_ORGANIZATION_ID,
                DEFAULT_ARRIVAL_DATE,
                DEFAULT_ACCOUNT_NUMBER,
                DEFAULT_CREATED_TIME_ID
            )
        );
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationMapper.toDto(
            addOnsSelectedByOrganization
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAddOnsSelectedByOrganizations() throws Exception {
        // Initialize the database
        addOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        addOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        addOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        addOnsSelectedByOrganizationRepository.save(addOnsSelectedByOrganization);

        // Get all the addOnsSelectedByOrganizationList
        restAddOnsSelectedByOrganizationMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(addOnsSelectedByOrganization.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.arrivalDate").value(
                    hasItem(addOnsSelectedByOrganization.getCompositeId().getArrivalDate().intValue())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.accountNumber").value(
                    hasItem(addOnsSelectedByOrganization.getCompositeId().getAccountNumber().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.createdTimeId").value(
                    hasItem(addOnsSelectedByOrganization.getCompositeId().getCreatedTimeId().toString())
                )
            )
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.toString())))
            .andExpect(jsonPath("$.[*].customerFirstName").value(hasItem(DEFAULT_CUSTOMER_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].customerLastName").value(hasItem(DEFAULT_CUSTOMER_LAST_NAME)))
            .andExpect(jsonPath("$.[*].customerUpdatedEmail").value(hasItem(DEFAULT_CUSTOMER_UPDATED_EMAIL)))
            .andExpect(jsonPath("$.[*].customerUpdatedPhoneNumber").value(hasItem(DEFAULT_CUSTOMER_UPDATED_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].customerEstimatedArrivalTime").value(hasItem(DEFAULT_CUSTOMER_ESTIMATED_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.[*].tinyUrlShortCode").value(hasItem(DEFAULT_TINY_URL_SHORT_CODE)))
            .andExpect(jsonPath("$.[*].addOnDetailsText['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_TEXT.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsDecimal['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_DECIMAL.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsBoolean['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_BOOLEAN.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsBigInt['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_BIG_INT.get("AAAAAAAAAA")));
    }

    @Test
    void getAddOnsSelectedByOrganization() throws Exception {
        // Initialize the database
        addOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        addOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        addOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        addOnsSelectedByOrganizationRepository.save(addOnsSelectedByOrganization);

        // Get the addOnsSelectedByOrganization
        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    addOnsSelectedByOrganization.getCompositeId().getOrganizationId() +
                    "/" +
                    addOnsSelectedByOrganization.getCompositeId().getArrivalDate() +
                    "/" +
                    addOnsSelectedByOrganization.getCompositeId().getAccountNumber() +
                    "/" +
                    addOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()
                )
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(addOnsSelectedByOrganization.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.arrivalDate").value(
                    hasItem(addOnsSelectedByOrganization.getCompositeId().getArrivalDate().intValue())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.accountNumber").value(
                    hasItem(addOnsSelectedByOrganization.getCompositeId().getAccountNumber().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.createdTimeId").value(
                    hasItem(addOnsSelectedByOrganization.getCompositeId().getCreatedTimeId().toString())
                )
            )
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.toString())))
            .andExpect(jsonPath("$.[*].customerFirstName").value(hasItem(DEFAULT_CUSTOMER_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].customerLastName").value(hasItem(DEFAULT_CUSTOMER_LAST_NAME)))
            .andExpect(jsonPath("$.[*].customerUpdatedEmail").value(hasItem(DEFAULT_CUSTOMER_UPDATED_EMAIL)))
            .andExpect(jsonPath("$.[*].customerUpdatedPhoneNumber").value(hasItem(DEFAULT_CUSTOMER_UPDATED_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].customerEstimatedArrivalTime").value(hasItem(DEFAULT_CUSTOMER_ESTIMATED_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.[*].tinyUrlShortCode").value(hasItem(DEFAULT_TINY_URL_SHORT_CODE)))
            .andExpect(jsonPath("$.[*].addOnDetailsText['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_TEXT.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsDecimal['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_DECIMAL.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsBoolean['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_BOOLEAN.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsBigInt['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_BIG_INT.get("AAAAAAAAAA")));
    }

    @Test
    void getNonExistingAddOnsSelectedByOrganization() throws Exception {
        // Get the addOnsSelectedByOrganization
        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    addOnsSelectedByOrganization.getCompositeId().getOrganizationId() +
                    "/" +
                    addOnsSelectedByOrganization.getCompositeId().getArrivalDate() +
                    "/" +
                    addOnsSelectedByOrganization.getCompositeId().getAccountNumber() +
                    "/" +
                    addOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()
                )
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingAddOnsSelectedByOrganization() throws Exception {
        // Initialize the database
        addOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        addOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        addOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        addOnsSelectedByOrganizationRepository.save(addOnsSelectedByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the addOnsSelectedByOrganization
        AddOnsSelectedByOrganization updatedAddOnsSelectedByOrganization = addOnsSelectedByOrganizationRepository
            .findById(addOnsSelectedByOrganization.getCompositeId())
            .orElseThrow();
        updatedAddOnsSelectedByOrganization
            .departureDate(UPDATED_DEPARTURE_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerFirstName(UPDATED_CUSTOMER_FIRST_NAME)
            .customerLastName(UPDATED_CUSTOMER_LAST_NAME)
            .customerUpdatedEmail(UPDATED_CUSTOMER_UPDATED_EMAIL)
            .customerUpdatedPhoneNumber(UPDATED_CUSTOMER_UPDATED_PHONE_NUMBER)
            .customerEstimatedArrivalTime(UPDATED_CUSTOMER_ESTIMATED_ARRIVAL_TIME)
            .tinyUrlShortCode(UPDATED_TINY_URL_SHORT_CODE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationMapper.toDto(
            updatedAddOnsSelectedByOrganization
        );

        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addOnsSelectedByOrganizationDTO)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the AddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAddOnsSelectedByOrganizationToMatchAllProperties(updatedAddOnsSelectedByOrganization);
    }

    @Test
    void putNonExistingAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsSelectedByOrganization.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );

        // Create the AddOnsSelectedByOrganization
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationMapper.toDto(
            addOnsSelectedByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                put(
                    ENTITY_API_URL_ID,
                    addOnsSelectedByOrganization.getCompositeId().getOrganizationId() +
                    "/" +
                    addOnsSelectedByOrganization.getCompositeId().getArrivalDate() +
                    "/" +
                    addOnsSelectedByOrganization.getCompositeId().getAccountNumber() +
                    "/" +
                    addOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsSelectedByOrganization.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );
        // Create the AddOnsSelectedByOrganization
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationMapper.toDto(
            addOnsSelectedByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsSelectedByOrganization.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );

        // Create the AddOnsSelectedByOrganization
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationMapper.toDto(
            addOnsSelectedByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAddOnsSelectedByOrganizationWithPatch() throws Exception {
        // Initialize the database
        addOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        addOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        addOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        addOnsSelectedByOrganizationRepository.save(addOnsSelectedByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the addOnsSelectedByOrganization using partial update
        AddOnsSelectedByOrganization partialUpdatedAddOnsSelectedByOrganization = new AddOnsSelectedByOrganization();
        partialUpdatedAddOnsSelectedByOrganization.setCompositeId(addOnsSelectedByOrganization.getCompositeId());

        partialUpdatedAddOnsSelectedByOrganization
            .departureDate(UPDATED_DEPARTURE_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerFirstName(UPDATED_CUSTOMER_FIRST_NAME)
            .customerLastName(UPDATED_CUSTOMER_LAST_NAME)
            .customerUpdatedEmail(UPDATED_CUSTOMER_UPDATED_EMAIL)
            .customerUpdatedPhoneNumber(UPDATED_CUSTOMER_UPDATED_PHONE_NUMBER)
            .customerEstimatedArrivalTime(UPDATED_CUSTOMER_ESTIMATED_ARRIVAL_TIME)
            .tinyUrlShortCode(UPDATED_TINY_URL_SHORT_CODE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);

        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddOnsSelectedByOrganization.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAddOnsSelectedByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the AddOnsSelectedByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAddOnsSelectedByOrganizationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAddOnsSelectedByOrganization, addOnsSelectedByOrganization),
            getPersistedAddOnsSelectedByOrganization(addOnsSelectedByOrganization)
        );
    }

    @Test
    void fullUpdateAddOnsSelectedByOrganizationWithPatch() throws Exception {
        // Initialize the database
        addOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        addOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        addOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        addOnsSelectedByOrganizationRepository.save(addOnsSelectedByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the addOnsSelectedByOrganization using partial update
        AddOnsSelectedByOrganization partialUpdatedAddOnsSelectedByOrganization = new AddOnsSelectedByOrganization();
        partialUpdatedAddOnsSelectedByOrganization.setCompositeId(addOnsSelectedByOrganization.getCompositeId());

        partialUpdatedAddOnsSelectedByOrganization
            .departureDate(UPDATED_DEPARTURE_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerFirstName(UPDATED_CUSTOMER_FIRST_NAME)
            .customerLastName(UPDATED_CUSTOMER_LAST_NAME)
            .customerUpdatedEmail(UPDATED_CUSTOMER_UPDATED_EMAIL)
            .customerUpdatedPhoneNumber(UPDATED_CUSTOMER_UPDATED_PHONE_NUMBER)
            .customerEstimatedArrivalTime(UPDATED_CUSTOMER_ESTIMATED_ARRIVAL_TIME)
            .tinyUrlShortCode(UPDATED_TINY_URL_SHORT_CODE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);

        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddOnsSelectedByOrganization.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAddOnsSelectedByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the AddOnsSelectedByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAddOnsSelectedByOrganizationUpdatableFieldsEquals(
            partialUpdatedAddOnsSelectedByOrganization,
            getPersistedAddOnsSelectedByOrganization(partialUpdatedAddOnsSelectedByOrganization)
        );
    }

    @Test
    void patchNonExistingAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsSelectedByOrganization.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );

        // Create the AddOnsSelectedByOrganization
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationMapper.toDto(
            addOnsSelectedByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addOnsSelectedByOrganizationDTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(addOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsSelectedByOrganization.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );

        // Create the AddOnsSelectedByOrganization
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationMapper.toDto(
            addOnsSelectedByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addOnsSelectedByOrganizationDTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(addOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsSelectedByOrganization.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );

        // Create the AddOnsSelectedByOrganization
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationMapper.toDto(
            addOnsSelectedByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(addOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAddOnsSelectedByOrganization() throws Exception {
        // Initialize the database
        addOnsSelectedByOrganization.setCompositeId(new AddOnsSelectedByOrganizationId());
        addOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        addOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        addOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        addOnsSelectedByOrganizationRepository.save(addOnsSelectedByOrganization);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the addOnsSelectedByOrganization
        restAddOnsSelectedByOrganizationMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, addOnsSelectedByOrganization.getCompositeId()).with(csrf()).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return addOnsSelectedByOrganizationRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected AddOnsSelectedByOrganization getPersistedAddOnsSelectedByOrganization(
        AddOnsSelectedByOrganization addOnsSelectedByOrganization
    ) {
        return addOnsSelectedByOrganizationRepository.findById(addOnsSelectedByOrganization.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedAddOnsSelectedByOrganizationToMatchAllProperties(
        AddOnsSelectedByOrganization expectedAddOnsSelectedByOrganization
    ) {
        assertAddOnsSelectedByOrganizationAllPropertiesEquals(
            expectedAddOnsSelectedByOrganization,
            getPersistedAddOnsSelectedByOrganization(expectedAddOnsSelectedByOrganization)
        );
    }

    protected void assertPersistedAddOnsSelectedByOrganizationToMatchUpdatableProperties(
        AddOnsSelectedByOrganization expectedAddOnsSelectedByOrganization
    ) {
        assertAddOnsSelectedByOrganizationAllUpdatablePropertiesEquals(
            expectedAddOnsSelectedByOrganization,
            getPersistedAddOnsSelectedByOrganization(expectedAddOnsSelectedByOrganization)
        );
    }
}
