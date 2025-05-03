package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.AddOnsAvailableByOrganizationAsserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganization;
import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganizationId;
import com.saathratri.developer.blog.repository.AddOnsAvailableByOrganizationRepository;
import com.saathratri.developer.blog.service.dto.AddOnsAvailableByOrganizationDTO;
import com.saathratri.developer.blog.service.mapper.AddOnsAvailableByOrganizationMapper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link AddOnsAvailableByOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddOnsAvailableByOrganizationResourceIT {

    private static final UUID DEFAULT_ORGANIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORGANIZATION_ID = UUID.randomUUID();

    private static final String DEFAULT_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final UUID DEFAULT_ENTITY_ID = UUID.randomUUID();
    private static final UUID UPDATED_ENTITY_ID = UUID.randomUUID();

    private static final UUID DEFAULT_ADD_ON_ID = UUID.randomUUID();
    private static final UUID UPDATED_ADD_ON_ID = UUID.randomUUID();

    private static final String DEFAULT_ADD_ON_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ADD_ON_TYPE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/add-ons-available-by-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AddOnsAvailableByOrganizationRepository addOnsAvailableByOrganizationRepository;

    @Autowired
    private AddOnsAvailableByOrganizationMapper addOnsAvailableByOrganizationMapper;

    @Autowired
    private MockMvc restAddOnsAvailableByOrganizationMockMvc;

    private AddOnsAvailableByOrganization addOnsAvailableByOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddOnsAvailableByOrganization createEntity() {
        AddOnsAvailableByOrganization addOnsAvailableByOrganization = new AddOnsAvailableByOrganization()
            .compositeId(
                new AddOnsAvailableByOrganizationId()
                    .organizationId(DEFAULT_ORGANIZATION_ID)
                    .entityType(DEFAULT_ENTITY_TYPE)
                    .entityId(DEFAULT_ENTITY_ID)
                    .addOnId(DEFAULT_ADD_ON_ID)
            )
            .addOnType("addOnType1")
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
        addOnsAvailableByOrganization.setCompositeId(
            new AddOnsAvailableByOrganizationId(DEFAULT_ORGANIZATION_ID, DEFAULT_ENTITY_TYPE, DEFAULT_ENTITY_ID, DEFAULT_ADD_ON_ID)
        );
        return addOnsAvailableByOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddOnsAvailableByOrganization createUpdatedEntity() {
        AddOnsAvailableByOrganization addOnsAvailableByOrganization = new AddOnsAvailableByOrganization()
            .compositeId(
                new AddOnsAvailableByOrganizationId()
                    .organizationId(UPDATED_ORGANIZATION_ID)
                    .entityType(UPDATED_ENTITY_TYPE)
                    .entityId(UPDATED_ENTITY_ID)
                    .addOnId(UPDATED_ADD_ON_ID)
            )
            .addOnType("addOnType1")
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
        addOnsAvailableByOrganization.setCompositeId(
            new AddOnsAvailableByOrganizationId(UPDATED_ORGANIZATION_ID, UPDATED_ENTITY_TYPE, UPDATED_ENTITY_ID, UPDATED_ADD_ON_ID)
        );
        return addOnsAvailableByOrganization;
    }

    @BeforeEach
    public void initTest() {
        addOnsAvailableByOrganizationRepository.deleteAll();
        addOnsAvailableByOrganization = createEntity();
    }

    @Test
    void createAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AddOnsAvailableByOrganization
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationMapper.toDto(
            addOnsAvailableByOrganization
        );
        var returnedAddOnsAvailableByOrganizationDTO = om.readValue(
            restAddOnsAvailableByOrganizationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(addOnsAvailableByOrganizationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AddOnsAvailableByOrganizationDTO.class
        );

        // Validate the AddOnsAvailableByOrganization in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAddOnsAvailableByOrganization = addOnsAvailableByOrganizationMapper.toEntity(returnedAddOnsAvailableByOrganizationDTO);
        assertAddOnsAvailableByOrganizationUpdatableFieldsEquals(
            returnedAddOnsAvailableByOrganization,
            getPersistedAddOnsAvailableByOrganization(returnedAddOnsAvailableByOrganization)
        );
    }

    @Test
    void createAddOnsAvailableByOrganizationWithExistingId() throws Exception {
        // Create the AddOnsAvailableByOrganization with an existing ID
        addOnsAvailableByOrganization.setCompositeId(
            new AddOnsAvailableByOrganizationId(DEFAULT_ORGANIZATION_ID, DEFAULT_ENTITY_TYPE, DEFAULT_ENTITY_ID, DEFAULT_ADD_ON_ID)
        );
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationMapper.toDto(
            addOnsAvailableByOrganization
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAddOnsAvailableByOrganizations() throws Exception {
        // Initialize the database
        addOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        addOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        addOnsAvailableByOrganizationRepository.save(addOnsAvailableByOrganization);

        // Get all the addOnsAvailableByOrganizationList
        restAddOnsAvailableByOrganizationMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(addOnsAvailableByOrganization.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.entityType").value(
                    hasItem(addOnsAvailableByOrganization.getCompositeId().getEntityType().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.entityId").value(
                    hasItem(addOnsAvailableByOrganization.getCompositeId().getEntityId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.addOnId").value(hasItem(addOnsAvailableByOrganization.getCompositeId().getAddOnId().toString()))
            )
            .andExpect(jsonPath("$.[*].addOnType").value(hasItem(DEFAULT_ADD_ON_TYPE)))
            .andExpect(jsonPath("$.[*].addOnDetailsText['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_TEXT.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsDecimal['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_DECIMAL.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsBoolean['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_BOOLEAN.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsBigInt['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_BIG_INT.get("AAAAAAAAAA")));
    }

    @Test
    void getAddOnsAvailableByOrganization() throws Exception {
        // Initialize the database
        addOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        addOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        addOnsAvailableByOrganizationRepository.save(addOnsAvailableByOrganization);

        // Get the addOnsAvailableByOrganization
        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    addOnsAvailableByOrganization.getCompositeId().getOrganizationId() +
                    "/" +
                    addOnsAvailableByOrganization.getCompositeId().getEntityType() +
                    "/" +
                    addOnsAvailableByOrganization.getCompositeId().getEntityId() +
                    "/" +
                    addOnsAvailableByOrganization.getCompositeId().getAddOnId()
                )
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(addOnsAvailableByOrganization.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.entityType").value(
                    hasItem(addOnsAvailableByOrganization.getCompositeId().getEntityType().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.entityId").value(
                    hasItem(addOnsAvailableByOrganization.getCompositeId().getEntityId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.addOnId").value(hasItem(addOnsAvailableByOrganization.getCompositeId().getAddOnId().toString()))
            )
            .andExpect(jsonPath("$.[*].addOnType").value(hasItem(DEFAULT_ADD_ON_TYPE)))
            .andExpect(jsonPath("$.[*].addOnDetailsText['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_TEXT.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsDecimal['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_DECIMAL.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsBoolean['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_BOOLEAN.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].addOnDetailsBigInt['AAAAAAAAAA']").value(DEFAULT_ADD_ON_DETAILS_BIG_INT.get("AAAAAAAAAA")));
    }

    @Test
    void getNonExistingAddOnsAvailableByOrganization() throws Exception {
        // Get the addOnsAvailableByOrganization
        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    addOnsAvailableByOrganization.getCompositeId().getOrganizationId() +
                    "/" +
                    addOnsAvailableByOrganization.getCompositeId().getEntityType() +
                    "/" +
                    addOnsAvailableByOrganization.getCompositeId().getEntityId() +
                    "/" +
                    addOnsAvailableByOrganization.getCompositeId().getAddOnId()
                )
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingAddOnsAvailableByOrganization() throws Exception {
        // Initialize the database
        addOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        addOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        addOnsAvailableByOrganizationRepository.save(addOnsAvailableByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the addOnsAvailableByOrganization
        AddOnsAvailableByOrganization updatedAddOnsAvailableByOrganization = addOnsAvailableByOrganizationRepository
            .findById(addOnsAvailableByOrganization.getCompositeId())
            .orElseThrow();
        updatedAddOnsAvailableByOrganization
            .addOnType(UPDATED_ADD_ON_TYPE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationMapper.toDto(
            updatedAddOnsAvailableByOrganization
        );

        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addOnsAvailableByOrganizationDTO)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the AddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAddOnsAvailableByOrganizationToMatchAllProperties(updatedAddOnsAvailableByOrganization);
    }

    @Test
    void putNonExistingAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsAvailableByOrganization.setCompositeId(
            new AddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the AddOnsAvailableByOrganization
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationMapper.toDto(
            addOnsAvailableByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                put(
                    ENTITY_API_URL_ID,
                    addOnsAvailableByOrganization.getCompositeId().getOrganizationId() +
                    "/" +
                    addOnsAvailableByOrganization.getCompositeId().getEntityType() +
                    "/" +
                    addOnsAvailableByOrganization.getCompositeId().getEntityId() +
                    "/" +
                    addOnsAvailableByOrganization.getCompositeId().getAddOnId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsAvailableByOrganization.setCompositeId(
            new AddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        // Create the AddOnsAvailableByOrganization
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationMapper.toDto(
            addOnsAvailableByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsAvailableByOrganization.setCompositeId(
            new AddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the AddOnsAvailableByOrganization
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationMapper.toDto(
            addOnsAvailableByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAddOnsAvailableByOrganizationWithPatch() throws Exception {
        // Initialize the database
        addOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        addOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        addOnsAvailableByOrganizationRepository.save(addOnsAvailableByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the addOnsAvailableByOrganization using partial update
        AddOnsAvailableByOrganization partialUpdatedAddOnsAvailableByOrganization = new AddOnsAvailableByOrganization();
        partialUpdatedAddOnsAvailableByOrganization.setCompositeId(addOnsAvailableByOrganization.getCompositeId());

        partialUpdatedAddOnsAvailableByOrganization
            .addOnType(UPDATED_ADD_ON_TYPE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);

        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddOnsAvailableByOrganization.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAddOnsAvailableByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the AddOnsAvailableByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAddOnsAvailableByOrganizationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAddOnsAvailableByOrganization, addOnsAvailableByOrganization),
            getPersistedAddOnsAvailableByOrganization(addOnsAvailableByOrganization)
        );
    }

    @Test
    void fullUpdateAddOnsAvailableByOrganizationWithPatch() throws Exception {
        // Initialize the database
        addOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        addOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        addOnsAvailableByOrganizationRepository.save(addOnsAvailableByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the addOnsAvailableByOrganization using partial update
        AddOnsAvailableByOrganization partialUpdatedAddOnsAvailableByOrganization = new AddOnsAvailableByOrganization();
        partialUpdatedAddOnsAvailableByOrganization.setCompositeId(addOnsAvailableByOrganization.getCompositeId());

        partialUpdatedAddOnsAvailableByOrganization
            .addOnType(UPDATED_ADD_ON_TYPE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);

        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddOnsAvailableByOrganization.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAddOnsAvailableByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the AddOnsAvailableByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAddOnsAvailableByOrganizationUpdatableFieldsEquals(
            partialUpdatedAddOnsAvailableByOrganization,
            getPersistedAddOnsAvailableByOrganization(partialUpdatedAddOnsAvailableByOrganization)
        );
    }

    @Test
    void patchNonExistingAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsAvailableByOrganization.setCompositeId(
            new AddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the AddOnsAvailableByOrganization
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationMapper.toDto(
            addOnsAvailableByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addOnsAvailableByOrganizationDTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(addOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsAvailableByOrganization.setCompositeId(
            new AddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the AddOnsAvailableByOrganization
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationMapper.toDto(
            addOnsAvailableByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addOnsAvailableByOrganizationDTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(addOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        addOnsAvailableByOrganization.setCompositeId(
            new AddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the AddOnsAvailableByOrganization
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationMapper.toDto(
            addOnsAvailableByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(addOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAddOnsAvailableByOrganization() throws Exception {
        // Initialize the database
        addOnsAvailableByOrganization.setCompositeId(new AddOnsAvailableByOrganizationId());
        addOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        addOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        addOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        addOnsAvailableByOrganizationRepository.save(addOnsAvailableByOrganization);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the addOnsAvailableByOrganization
        restAddOnsAvailableByOrganizationMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, addOnsAvailableByOrganization.getCompositeId()).with(csrf()).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return addOnsAvailableByOrganizationRepository.count();
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

    protected AddOnsAvailableByOrganization getPersistedAddOnsAvailableByOrganization(
        AddOnsAvailableByOrganization addOnsAvailableByOrganization
    ) {
        return addOnsAvailableByOrganizationRepository.findById(addOnsAvailableByOrganization.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedAddOnsAvailableByOrganizationToMatchAllProperties(
        AddOnsAvailableByOrganization expectedAddOnsAvailableByOrganization
    ) {
        assertAddOnsAvailableByOrganizationAllPropertiesEquals(
            expectedAddOnsAvailableByOrganization,
            getPersistedAddOnsAvailableByOrganization(expectedAddOnsAvailableByOrganization)
        );
    }

    protected void assertPersistedAddOnsAvailableByOrganizationToMatchUpdatableProperties(
        AddOnsAvailableByOrganization expectedAddOnsAvailableByOrganization
    ) {
        assertAddOnsAvailableByOrganizationAllUpdatablePropertiesEquals(
            expectedAddOnsAvailableByOrganization,
            getPersistedAddOnsAvailableByOrganization(expectedAddOnsAvailableByOrganization)
        );
    }
}
