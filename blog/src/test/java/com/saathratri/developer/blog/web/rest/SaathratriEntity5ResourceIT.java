package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.SaathratriEntity5Asserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static com.saathratri.developer.blog.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.SaathratriEntity5;
import com.saathratri.developer.blog.domain.SaathratriEntity5Id;
import com.saathratri.developer.blog.repository.SaathratriEntity5Repository;
import com.saathratri.developer.blog.service.dto.SaathratriEntity5DTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntity5Mapper;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link SaathratriEntity5Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaathratriEntity5ResourceIT {

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

    private static final String DEFAULT_ADD_ON_DETAILS_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ADD_ON_DETAILS_TEXT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ADD_ON_DETAILS_DECIMAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADD_ON_DETAILS_DECIMAL = new BigDecimal(2);

    private static final Boolean DEFAULT_ADD_ON_DETAILS_BOOLEAN = false;
    private static final Boolean UPDATED_ADD_ON_DETAILS_BOOLEAN = true;

    private static final Long DEFAULT_ADD_ON_DETAILS_BIG_INT = 1L;
    private static final Long UPDATED_ADD_ON_DETAILS_BIG_INT = 2L;

    private static final String ENTITY_API_URL = "/api/saathratri-entity-5-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SaathratriEntity5Repository saathratriEntity5Repository;

    @Autowired
    private SaathratriEntity5Mapper saathratriEntity5Mapper;

    @Autowired
    private MockMvc restSaathratriEntity5MockMvc;

    private SaathratriEntity5 saathratriEntity5;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity5 createEntity() {
        SaathratriEntity5 saathratriEntity5 = new SaathratriEntity5()
            .compositeId(
                new SaathratriEntity5Id()
                    .organizationId(DEFAULT_ORGANIZATION_ID)
                    .entityType(DEFAULT_ENTITY_TYPE)
                    .entityId(DEFAULT_ENTITY_ID)
                    .addOnId(DEFAULT_ADD_ON_ID)
            )
            .addOnType("addOnType1")
            .addOnDetailsText("addOnDetailsText1")
            .addOnDetailsDecimal(new BigDecimal(1))
            .addOnDetailsBoolean(false)
            .addOnDetailsBigInt(1L);
        saathratriEntity5.setCompositeId(
            new SaathratriEntity5Id(DEFAULT_ORGANIZATION_ID, DEFAULT_ENTITY_TYPE, DEFAULT_ENTITY_ID, DEFAULT_ADD_ON_ID)
        );
        return saathratriEntity5;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity5 createUpdatedEntity() {
        SaathratriEntity5 saathratriEntity5 = new SaathratriEntity5()
            .compositeId(
                new SaathratriEntity5Id()
                    .organizationId(UPDATED_ORGANIZATION_ID)
                    .entityType(UPDATED_ENTITY_TYPE)
                    .entityId(UPDATED_ENTITY_ID)
                    .addOnId(UPDATED_ADD_ON_ID)
            )
            .addOnType("addOnType1")
            .addOnDetailsText("addOnDetailsText1")
            .addOnDetailsDecimal(new BigDecimal(1))
            .addOnDetailsBoolean(false)
            .addOnDetailsBigInt(1L);
        saathratriEntity5.setCompositeId(
            new SaathratriEntity5Id(UPDATED_ORGANIZATION_ID, UPDATED_ENTITY_TYPE, UPDATED_ENTITY_ID, UPDATED_ADD_ON_ID)
        );
        return saathratriEntity5;
    }

    @BeforeEach
    public void initTest() {
        saathratriEntity5Repository.deleteAll();
        saathratriEntity5 = createEntity();
    }

    @Test
    void createSaathratriEntity5() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SaathratriEntity5
        SaathratriEntity5DTO saathratriEntity5DTO = saathratriEntity5Mapper.toDto(saathratriEntity5);
        var returnedSaathratriEntity5DTO = om.readValue(
            restSaathratriEntity5MockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(saathratriEntity5DTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SaathratriEntity5DTO.class
        );

        // Validate the SaathratriEntity5 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSaathratriEntity5 = saathratriEntity5Mapper.toEntity(returnedSaathratriEntity5DTO);
        assertSaathratriEntity5UpdatableFieldsEquals(returnedSaathratriEntity5, getPersistedSaathratriEntity5(returnedSaathratriEntity5));
    }

    @Test
    void createSaathratriEntity5WithExistingId() throws Exception {
        // Create the SaathratriEntity5 with an existing ID
        saathratriEntity5.setCompositeId(
            new SaathratriEntity5Id(DEFAULT_ORGANIZATION_ID, DEFAULT_ENTITY_TYPE, DEFAULT_ENTITY_ID, DEFAULT_ADD_ON_ID)
        );
        SaathratriEntity5DTO saathratriEntity5DTO = saathratriEntity5Mapper.toDto(saathratriEntity5);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaathratriEntity5MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity5DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity5 in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSaathratriEntity5s() throws Exception {
        // Initialize the database
        saathratriEntity5.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity5.getCompositeId().setEntityId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setAddOnId(UUID.randomUUID());
        saathratriEntity5Repository.save(saathratriEntity5);

        // Get all the saathratriEntity5List
        restSaathratriEntity5MockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(saathratriEntity5.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.entityType").value(hasItem(saathratriEntity5.getCompositeId().getEntityType().toString()))
            )
            .andExpect(jsonPath("$.[*].compositeId.entityId").value(hasItem(saathratriEntity5.getCompositeId().getEntityId().toString())))
            .andExpect(jsonPath("$.[*].compositeId.addOnId").value(hasItem(saathratriEntity5.getCompositeId().getAddOnId().toString())))
            .andExpect(jsonPath("$.[*].addOnType").value(hasItem(DEFAULT_ADD_ON_TYPE)))
            .andExpect(jsonPath("$.[*].addOnDetailsText").value(hasItem(DEFAULT_ADD_ON_DETAILS_TEXT)))
            .andExpect(jsonPath("$.[*].addOnDetailsDecimal").value(hasItem(sameNumber(DEFAULT_ADD_ON_DETAILS_DECIMAL))))
            .andExpect(jsonPath("$.[*].addOnDetailsBoolean").value(hasItem(DEFAULT_ADD_ON_DETAILS_BOOLEAN.booleanValue())))
            .andExpect(jsonPath("$.[*].addOnDetailsBigInt").value(hasItem(DEFAULT_ADD_ON_DETAILS_BIG_INT.intValue())));
    }

    @Test
    void getSaathratriEntity5() throws Exception {
        // Initialize the database
        saathratriEntity5.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity5.getCompositeId().setEntityId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setAddOnId(UUID.randomUUID());
        saathratriEntity5Repository.save(saathratriEntity5);

        // Get the saathratriEntity5
        restSaathratriEntity5MockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    saathratriEntity5.getCompositeId().getOrganizationId() +
                    "/" +
                    saathratriEntity5.getCompositeId().getEntityType() +
                    "/" +
                    saathratriEntity5.getCompositeId().getEntityId() +
                    "/" +
                    saathratriEntity5.getCompositeId().getAddOnId()
                )
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(saathratriEntity5.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.entityType").value(hasItem(saathratriEntity5.getCompositeId().getEntityType().toString()))
            )
            .andExpect(jsonPath("$.[*].compositeId.entityId").value(hasItem(saathratriEntity5.getCompositeId().getEntityId().toString())))
            .andExpect(jsonPath("$.[*].compositeId.addOnId").value(hasItem(saathratriEntity5.getCompositeId().getAddOnId().toString())))
            .andExpect(jsonPath("$.[*].addOnType").value(hasItem(DEFAULT_ADD_ON_TYPE)))
            .andExpect(jsonPath("$.[*].addOnDetailsText").value(hasItem(DEFAULT_ADD_ON_DETAILS_TEXT)))
            .andExpect(jsonPath("$.[*].addOnDetailsDecimal").value(hasItem(sameNumber(DEFAULT_ADD_ON_DETAILS_DECIMAL))))
            .andExpect(jsonPath("$.[*].addOnDetailsBoolean").value(hasItem(DEFAULT_ADD_ON_DETAILS_BOOLEAN.booleanValue())))
            .andExpect(jsonPath("$.[*].addOnDetailsBigInt").value(hasItem(DEFAULT_ADD_ON_DETAILS_BIG_INT.intValue())));
    }

    @Test
    void getNonExistingSaathratriEntity5() throws Exception {
        // Get the saathratriEntity5
        restSaathratriEntity5MockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    saathratriEntity5.getCompositeId().getOrganizationId() +
                    "/" +
                    saathratriEntity5.getCompositeId().getEntityType() +
                    "/" +
                    saathratriEntity5.getCompositeId().getEntityId() +
                    "/" +
                    saathratriEntity5.getCompositeId().getAddOnId()
                )
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingSaathratriEntity5() throws Exception {
        // Initialize the database
        saathratriEntity5.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity5.getCompositeId().setEntityId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setAddOnId(UUID.randomUUID());
        saathratriEntity5Repository.save(saathratriEntity5);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity5
        SaathratriEntity5 updatedSaathratriEntity5 = saathratriEntity5Repository.findById(saathratriEntity5.getCompositeId()).orElseThrow();
        updatedSaathratriEntity5
            .addOnType(UPDATED_ADD_ON_TYPE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);
        SaathratriEntity5DTO saathratriEntity5DTO = saathratriEntity5Mapper.toDto(updatedSaathratriEntity5);

        restSaathratriEntity5MockMvc
            .perform(
                put(ENTITY_API_URL_ID, saathratriEntity5DTO)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity5DTO))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity5 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSaathratriEntity5ToMatchAllProperties(updatedSaathratriEntity5);
    }

    @Test
    void putNonExistingSaathratriEntity5() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity5.setCompositeId(
            new SaathratriEntity5Id(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the SaathratriEntity5
        SaathratriEntity5DTO saathratriEntity5DTO = saathratriEntity5Mapper.toDto(saathratriEntity5);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntity5MockMvc
            .perform(
                put(
                    ENTITY_API_URL_ID,
                    saathratriEntity5.getCompositeId().getOrganizationId() +
                    "/" +
                    saathratriEntity5.getCompositeId().getEntityType() +
                    "/" +
                    saathratriEntity5.getCompositeId().getEntityId() +
                    "/" +
                    saathratriEntity5.getCompositeId().getAddOnId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity5DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity5 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSaathratriEntity5() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity5.setCompositeId(
            new SaathratriEntity5Id(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        // Create the SaathratriEntity5
        SaathratriEntity5DTO saathratriEntity5DTO = saathratriEntity5Mapper.toDto(saathratriEntity5);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity5MockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity5DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity5 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSaathratriEntity5() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity5.setCompositeId(
            new SaathratriEntity5Id(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the SaathratriEntity5
        SaathratriEntity5DTO saathratriEntity5DTO = saathratriEntity5Mapper.toDto(saathratriEntity5);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity5MockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(saathratriEntity5DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity5 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSaathratriEntity5WithPatch() throws Exception {
        // Initialize the database
        saathratriEntity5.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity5.getCompositeId().setEntityId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setAddOnId(UUID.randomUUID());
        saathratriEntity5Repository.save(saathratriEntity5);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity5 using partial update
        SaathratriEntity5 partialUpdatedSaathratriEntity5 = new SaathratriEntity5();
        partialUpdatedSaathratriEntity5.setCompositeId(saathratriEntity5.getCompositeId());

        partialUpdatedSaathratriEntity5
            .addOnType(UPDATED_ADD_ON_TYPE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);

        restSaathratriEntity5MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaathratriEntity5.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity5))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity5 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntity5UpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSaathratriEntity5, saathratriEntity5),
            getPersistedSaathratriEntity5(saathratriEntity5)
        );
    }

    @Test
    void fullUpdateSaathratriEntity5WithPatch() throws Exception {
        // Initialize the database
        saathratriEntity5.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity5.getCompositeId().setEntityId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setAddOnId(UUID.randomUUID());
        saathratriEntity5Repository.save(saathratriEntity5);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity5 using partial update
        SaathratriEntity5 partialUpdatedSaathratriEntity5 = new SaathratriEntity5();
        partialUpdatedSaathratriEntity5.setCompositeId(saathratriEntity5.getCompositeId());

        partialUpdatedSaathratriEntity5
            .addOnType(UPDATED_ADD_ON_TYPE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);

        restSaathratriEntity5MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaathratriEntity5.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity5))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity5 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntity5UpdatableFieldsEquals(
            partialUpdatedSaathratriEntity5,
            getPersistedSaathratriEntity5(partialUpdatedSaathratriEntity5)
        );
    }

    @Test
    void patchNonExistingSaathratriEntity5() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity5.setCompositeId(
            new SaathratriEntity5Id(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the SaathratriEntity5
        SaathratriEntity5DTO saathratriEntity5DTO = saathratriEntity5Mapper.toDto(saathratriEntity5);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntity5MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saathratriEntity5DTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity5DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity5 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSaathratriEntity5() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity5.setCompositeId(
            new SaathratriEntity5Id(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the SaathratriEntity5
        SaathratriEntity5DTO saathratriEntity5DTO = saathratriEntity5Mapper.toDto(saathratriEntity5);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity5MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saathratriEntity5DTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity5DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity5 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSaathratriEntity5() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity5.setCompositeId(
            new SaathratriEntity5Id(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the SaathratriEntity5
        SaathratriEntity5DTO saathratriEntity5DTO = saathratriEntity5Mapper.toDto(saathratriEntity5);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity5MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity5DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity5 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSaathratriEntity5() throws Exception {
        // Initialize the database
        saathratriEntity5.setCompositeId(new SaathratriEntity5Id());
        saathratriEntity5.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity5.getCompositeId().setEntityId(UUID.randomUUID());
        saathratriEntity5.getCompositeId().setAddOnId(UUID.randomUUID());
        saathratriEntity5Repository.save(saathratriEntity5);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the saathratriEntity5
        restSaathratriEntity5MockMvc
            .perform(delete(ENTITY_API_URL_ID, saathratriEntity5.getCompositeId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return saathratriEntity5Repository.count();
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

    protected SaathratriEntity5 getPersistedSaathratriEntity5(SaathratriEntity5 saathratriEntity5) {
        return saathratriEntity5Repository.findById(saathratriEntity5.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedSaathratriEntity5ToMatchAllProperties(SaathratriEntity5 expectedSaathratriEntity5) {
        assertSaathratriEntity5AllPropertiesEquals(expectedSaathratriEntity5, getPersistedSaathratriEntity5(expectedSaathratriEntity5));
    }

    protected void assertPersistedSaathratriEntity5ToMatchUpdatableProperties(SaathratriEntity5 expectedSaathratriEntity5) {
        assertSaathratriEntity5AllUpdatablePropertiesEquals(
            expectedSaathratriEntity5,
            getPersistedSaathratriEntity5(expectedSaathratriEntity5)
        );
    }
}
