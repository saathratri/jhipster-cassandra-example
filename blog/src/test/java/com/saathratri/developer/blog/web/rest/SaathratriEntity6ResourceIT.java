package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.SaathratriEntity6Asserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.SaathratriEntity6;
import com.saathratri.developer.blog.domain.SaathratriEntity6Id;
import com.saathratri.developer.blog.repository.SaathratriEntity6Repository;
import com.saathratri.developer.blog.service.dto.SaathratriEntity6DTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntity6Mapper;
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
 * Integration tests for the {@link SaathratriEntity6Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaathratriEntity6ResourceIT {

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

    private static final String ENTITY_API_URL = "/api/saathratri-entity-6-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SaathratriEntity6Repository saathratriEntity6Repository;

    @Autowired
    private SaathratriEntity6Mapper saathratriEntity6Mapper;

    @Autowired
    private MockMvc restSaathratriEntity6MockMvc;

    private SaathratriEntity6 saathratriEntity6;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity6 createEntity() {
        SaathratriEntity6 saathratriEntity6 = new SaathratriEntity6()
            .compositeId(
                new SaathratriEntity6Id()
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
        saathratriEntity6.setCompositeId(
            new SaathratriEntity6Id(DEFAULT_ORGANIZATION_ID, DEFAULT_ARRIVAL_DATE, DEFAULT_ACCOUNT_NUMBER, DEFAULT_CREATED_TIME_ID)
        );
        return saathratriEntity6;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity6 createUpdatedEntity() {
        SaathratriEntity6 saathratriEntity6 = new SaathratriEntity6()
            .compositeId(
                new SaathratriEntity6Id()
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
        saathratriEntity6.setCompositeId(
            new SaathratriEntity6Id(UPDATED_ORGANIZATION_ID, UPDATED_ARRIVAL_DATE, UPDATED_ACCOUNT_NUMBER, UPDATED_CREATED_TIME_ID)
        );
        return saathratriEntity6;
    }

    @BeforeEach
    public void initTest() {
        saathratriEntity6Repository.deleteAll();
        saathratriEntity6 = createEntity();
    }

    @Test
    void createSaathratriEntity6() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SaathratriEntity6
        SaathratriEntity6DTO saathratriEntity6DTO = saathratriEntity6Mapper.toDto(saathratriEntity6);
        var returnedSaathratriEntity6DTO = om.readValue(
            restSaathratriEntity6MockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(saathratriEntity6DTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SaathratriEntity6DTO.class
        );

        // Validate the SaathratriEntity6 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSaathratriEntity6 = saathratriEntity6Mapper.toEntity(returnedSaathratriEntity6DTO);
        assertSaathratriEntity6UpdatableFieldsEquals(returnedSaathratriEntity6, getPersistedSaathratriEntity6(returnedSaathratriEntity6));
    }

    @Test
    void createSaathratriEntity6WithExistingId() throws Exception {
        // Create the SaathratriEntity6 with an existing ID
        saathratriEntity6.setCompositeId(
            new SaathratriEntity6Id(DEFAULT_ORGANIZATION_ID, DEFAULT_ARRIVAL_DATE, DEFAULT_ACCOUNT_NUMBER, DEFAULT_CREATED_TIME_ID)
        );
        SaathratriEntity6DTO saathratriEntity6DTO = saathratriEntity6Mapper.toDto(saathratriEntity6);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaathratriEntity6MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity6DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity6 in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSaathratriEntity6s() throws Exception {
        // Initialize the database
        saathratriEntity6.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity6.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity6.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        saathratriEntity6.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity6Repository.save(saathratriEntity6);

        // Get all the saathratriEntity6List
        restSaathratriEntity6MockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(saathratriEntity6.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.arrivalDate").value(hasItem(saathratriEntity6.getCompositeId().getArrivalDate().intValue()))
            )
            .andExpect(
                jsonPath("$.[*].compositeId.accountNumber").value(hasItem(saathratriEntity6.getCompositeId().getAccountNumber().toString()))
            )
            .andExpect(
                jsonPath("$.[*].compositeId.createdTimeId").value(hasItem(saathratriEntity6.getCompositeId().getCreatedTimeId().toString()))
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
    void getSaathratriEntity6() throws Exception {
        // Initialize the database
        saathratriEntity6.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity6.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity6.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        saathratriEntity6.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity6Repository.save(saathratriEntity6);

        // Get the saathratriEntity6
        restSaathratriEntity6MockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    saathratriEntity6.getCompositeId().getOrganizationId() +
                    "/" +
                    saathratriEntity6.getCompositeId().getArrivalDate() +
                    "/" +
                    saathratriEntity6.getCompositeId().getAccountNumber() +
                    "/" +
                    saathratriEntity6.getCompositeId().getCreatedTimeId()
                )
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(saathratriEntity6.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.arrivalDate").value(hasItem(saathratriEntity6.getCompositeId().getArrivalDate().intValue()))
            )
            .andExpect(
                jsonPath("$.[*].compositeId.accountNumber").value(hasItem(saathratriEntity6.getCompositeId().getAccountNumber().toString()))
            )
            .andExpect(
                jsonPath("$.[*].compositeId.createdTimeId").value(hasItem(saathratriEntity6.getCompositeId().getCreatedTimeId().toString()))
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
    void getNonExistingSaathratriEntity6() throws Exception {
        // Get the saathratriEntity6
        restSaathratriEntity6MockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    saathratriEntity6.getCompositeId().getOrganizationId() +
                    "/" +
                    saathratriEntity6.getCompositeId().getArrivalDate() +
                    "/" +
                    saathratriEntity6.getCompositeId().getAccountNumber() +
                    "/" +
                    saathratriEntity6.getCompositeId().getCreatedTimeId()
                )
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingSaathratriEntity6() throws Exception {
        // Initialize the database
        saathratriEntity6.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity6.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity6.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        saathratriEntity6.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity6Repository.save(saathratriEntity6);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity6
        SaathratriEntity6 updatedSaathratriEntity6 = saathratriEntity6Repository.findById(saathratriEntity6.getCompositeId()).orElseThrow();
        updatedSaathratriEntity6
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
        SaathratriEntity6DTO saathratriEntity6DTO = saathratriEntity6Mapper.toDto(updatedSaathratriEntity6);

        restSaathratriEntity6MockMvc
            .perform(
                put(ENTITY_API_URL_ID, saathratriEntity6DTO)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity6DTO))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity6 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSaathratriEntity6ToMatchAllProperties(updatedSaathratriEntity6);
    }

    @Test
    void putNonExistingSaathratriEntity6() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity6.setCompositeId(
            new SaathratriEntity6Id(UUID.randomUUID(), new java.util.Random().nextLong(), UUID.randomUUID().toString(), UUID.randomUUID())
        );

        // Create the SaathratriEntity6
        SaathratriEntity6DTO saathratriEntity6DTO = saathratriEntity6Mapper.toDto(saathratriEntity6);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntity6MockMvc
            .perform(
                put(
                    ENTITY_API_URL_ID,
                    saathratriEntity6.getCompositeId().getOrganizationId() +
                    "/" +
                    saathratriEntity6.getCompositeId().getArrivalDate() +
                    "/" +
                    saathratriEntity6.getCompositeId().getAccountNumber() +
                    "/" +
                    saathratriEntity6.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity6DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity6 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSaathratriEntity6() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity6.setCompositeId(
            new SaathratriEntity6Id(UUID.randomUUID(), new java.util.Random().nextLong(), UUID.randomUUID().toString(), UUID.randomUUID())
        );
        // Create the SaathratriEntity6
        SaathratriEntity6DTO saathratriEntity6DTO = saathratriEntity6Mapper.toDto(saathratriEntity6);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity6MockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity6DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity6 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSaathratriEntity6() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity6.setCompositeId(
            new SaathratriEntity6Id(UUID.randomUUID(), new java.util.Random().nextLong(), UUID.randomUUID().toString(), UUID.randomUUID())
        );

        // Create the SaathratriEntity6
        SaathratriEntity6DTO saathratriEntity6DTO = saathratriEntity6Mapper.toDto(saathratriEntity6);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity6MockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(saathratriEntity6DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity6 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSaathratriEntity6WithPatch() throws Exception {
        // Initialize the database
        saathratriEntity6.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity6.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity6.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        saathratriEntity6.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity6Repository.save(saathratriEntity6);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity6 using partial update
        SaathratriEntity6 partialUpdatedSaathratriEntity6 = new SaathratriEntity6();
        partialUpdatedSaathratriEntity6.setCompositeId(saathratriEntity6.getCompositeId());

        partialUpdatedSaathratriEntity6
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

        restSaathratriEntity6MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaathratriEntity6.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity6))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity6 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntity6UpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSaathratriEntity6, saathratriEntity6),
            getPersistedSaathratriEntity6(saathratriEntity6)
        );
    }

    @Test
    void fullUpdateSaathratriEntity6WithPatch() throws Exception {
        // Initialize the database
        saathratriEntity6.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity6.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity6.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        saathratriEntity6.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity6Repository.save(saathratriEntity6);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity6 using partial update
        SaathratriEntity6 partialUpdatedSaathratriEntity6 = new SaathratriEntity6();
        partialUpdatedSaathratriEntity6.setCompositeId(saathratriEntity6.getCompositeId());

        partialUpdatedSaathratriEntity6
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

        restSaathratriEntity6MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaathratriEntity6.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity6))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity6 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntity6UpdatableFieldsEquals(
            partialUpdatedSaathratriEntity6,
            getPersistedSaathratriEntity6(partialUpdatedSaathratriEntity6)
        );
    }

    @Test
    void patchNonExistingSaathratriEntity6() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity6.setCompositeId(
            new SaathratriEntity6Id(UUID.randomUUID(), new java.util.Random().nextLong(), UUID.randomUUID().toString(), UUID.randomUUID())
        );

        // Create the SaathratriEntity6
        SaathratriEntity6DTO saathratriEntity6DTO = saathratriEntity6Mapper.toDto(saathratriEntity6);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntity6MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saathratriEntity6DTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity6DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity6 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSaathratriEntity6() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity6.setCompositeId(
            new SaathratriEntity6Id(UUID.randomUUID(), new java.util.Random().nextLong(), UUID.randomUUID().toString(), UUID.randomUUID())
        );

        // Create the SaathratriEntity6
        SaathratriEntity6DTO saathratriEntity6DTO = saathratriEntity6Mapper.toDto(saathratriEntity6);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity6MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saathratriEntity6DTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity6DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity6 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSaathratriEntity6() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity6.setCompositeId(
            new SaathratriEntity6Id(UUID.randomUUID(), new java.util.Random().nextLong(), UUID.randomUUID().toString(), UUID.randomUUID())
        );

        // Create the SaathratriEntity6
        SaathratriEntity6DTO saathratriEntity6DTO = saathratriEntity6Mapper.toDto(saathratriEntity6);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity6MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity6DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity6 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSaathratriEntity6() throws Exception {
        // Initialize the database
        saathratriEntity6.setCompositeId(new SaathratriEntity6Id());
        saathratriEntity6.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity6.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        saathratriEntity6.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        saathratriEntity6.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity6Repository.save(saathratriEntity6);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the saathratriEntity6
        restSaathratriEntity6MockMvc
            .perform(delete(ENTITY_API_URL_ID, saathratriEntity6.getCompositeId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return saathratriEntity6Repository.count();
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

    protected SaathratriEntity6 getPersistedSaathratriEntity6(SaathratriEntity6 saathratriEntity6) {
        return saathratriEntity6Repository.findById(saathratriEntity6.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedSaathratriEntity6ToMatchAllProperties(SaathratriEntity6 expectedSaathratriEntity6) {
        assertSaathratriEntity6AllPropertiesEquals(expectedSaathratriEntity6, getPersistedSaathratriEntity6(expectedSaathratriEntity6));
    }

    protected void assertPersistedSaathratriEntity6ToMatchUpdatableProperties(SaathratriEntity6 expectedSaathratriEntity6) {
        assertSaathratriEntity6AllUpdatablePropertiesEquals(
            expectedSaathratriEntity6,
            getPersistedSaathratriEntity6(expectedSaathratriEntity6)
        );
    }
}
