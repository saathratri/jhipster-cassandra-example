package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.SaathratriEntity4Asserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.SaathratriEntity4;
import com.saathratri.developer.blog.domain.SaathratriEntity4Id;
import com.saathratri.developer.blog.repository.SaathratriEntity4Repository;
import com.saathratri.developer.blog.service.dto.SaathratriEntity4DTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntity4Mapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link SaathratriEntity4Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaathratriEntity4ResourceIT {

    private static final UUID DEFAULT_ORGANIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORGANIZATION_ID = UUID.randomUUID();

    private static final String DEFAULT_ATTRIBUTE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/saathratri-entity-4-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SaathratriEntity4Repository saathratriEntity4Repository;

    @Autowired
    private SaathratriEntity4Mapper saathratriEntity4Mapper;

    @Autowired
    private MockMvc restSaathratriEntity4MockMvc;

    private SaathratriEntity4 saathratriEntity4;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity4 createEntity() {
        SaathratriEntity4 saathratriEntity4 = new SaathratriEntity4()
            .compositeId(new SaathratriEntity4Id().organizationId(DEFAULT_ORGANIZATION_ID).attributeKey(DEFAULT_ATTRIBUTE_KEY))
            .attributeValue("attributeValue1");
        saathratriEntity4.setCompositeId(new SaathratriEntity4Id(DEFAULT_ORGANIZATION_ID, DEFAULT_ATTRIBUTE_KEY));
        return saathratriEntity4;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity4 createUpdatedEntity() {
        SaathratriEntity4 saathratriEntity4 = new SaathratriEntity4()
            .compositeId(new SaathratriEntity4Id().organizationId(UPDATED_ORGANIZATION_ID).attributeKey(UPDATED_ATTRIBUTE_KEY))
            .attributeValue("attributeValue1");
        saathratriEntity4.setCompositeId(new SaathratriEntity4Id(UPDATED_ORGANIZATION_ID, UPDATED_ATTRIBUTE_KEY));
        return saathratriEntity4;
    }

    @BeforeEach
    public void initTest() {
        saathratriEntity4Repository.deleteAll();
        saathratriEntity4 = createEntity();
    }

    @Test
    void createSaathratriEntity4() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SaathratriEntity4
        SaathratriEntity4DTO saathratriEntity4DTO = saathratriEntity4Mapper.toDto(saathratriEntity4);
        var returnedSaathratriEntity4DTO = om.readValue(
            restSaathratriEntity4MockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(saathratriEntity4DTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SaathratriEntity4DTO.class
        );

        // Validate the SaathratriEntity4 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSaathratriEntity4 = saathratriEntity4Mapper.toEntity(returnedSaathratriEntity4DTO);
        assertSaathratriEntity4UpdatableFieldsEquals(returnedSaathratriEntity4, getPersistedSaathratriEntity4(returnedSaathratriEntity4));
    }

    @Test
    void createSaathratriEntity4WithExistingId() throws Exception {
        // Create the SaathratriEntity4 with an existing ID
        saathratriEntity4.setCompositeId(new SaathratriEntity4Id(DEFAULT_ORGANIZATION_ID, DEFAULT_ATTRIBUTE_KEY));
        SaathratriEntity4DTO saathratriEntity4DTO = saathratriEntity4Mapper.toDto(saathratriEntity4);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaathratriEntity4MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity4DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSaathratriEntity4s() throws Exception {
        // Initialize the database
        saathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        saathratriEntity4Repository.save(saathratriEntity4);

        // Get all the saathratriEntity4List
        restSaathratriEntity4MockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(saathratriEntity4.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.attributeKey").value(hasItem(saathratriEntity4.getCompositeId().getAttributeKey().toString()))
            )
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    void getSaathratriEntity4() throws Exception {
        // Initialize the database
        saathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        saathratriEntity4Repository.save(saathratriEntity4);

        // Get the saathratriEntity4
        restSaathratriEntity4MockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    saathratriEntity4.getCompositeId().getOrganizationId() + "/" + saathratriEntity4.getCompositeId().getAttributeKey()
                )
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(saathratriEntity4.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.attributeKey").value(hasItem(saathratriEntity4.getCompositeId().getAttributeKey().toString()))
            )
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    void getNonExistingSaathratriEntity4() throws Exception {
        // Get the saathratriEntity4
        restSaathratriEntity4MockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    saathratriEntity4.getCompositeId().getOrganizationId() + "/" + saathratriEntity4.getCompositeId().getAttributeKey()
                )
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingSaathratriEntity4() throws Exception {
        // Initialize the database
        saathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        saathratriEntity4Repository.save(saathratriEntity4);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity4
        SaathratriEntity4 updatedSaathratriEntity4 = saathratriEntity4Repository.findById(saathratriEntity4.getCompositeId()).orElseThrow();
        updatedSaathratriEntity4.attributeValue(UPDATED_ATTRIBUTE_VALUE);
        SaathratriEntity4DTO saathratriEntity4DTO = saathratriEntity4Mapper.toDto(updatedSaathratriEntity4);

        restSaathratriEntity4MockMvc
            .perform(
                put(ENTITY_API_URL_ID, saathratriEntity4DTO)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity4DTO))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSaathratriEntity4ToMatchAllProperties(updatedSaathratriEntity4);
    }

    @Test
    void putNonExistingSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity4.setCompositeId(new SaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));

        // Create the SaathratriEntity4
        SaathratriEntity4DTO saathratriEntity4DTO = saathratriEntity4Mapper.toDto(saathratriEntity4);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntity4MockMvc
            .perform(
                put(
                    ENTITY_API_URL_ID,
                    saathratriEntity4.getCompositeId().getOrganizationId() + "/" + saathratriEntity4.getCompositeId().getAttributeKey()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity4DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity4.setCompositeId(new SaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));
        // Create the SaathratriEntity4
        SaathratriEntity4DTO saathratriEntity4DTO = saathratriEntity4Mapper.toDto(saathratriEntity4);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity4MockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity4DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity4.setCompositeId(new SaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));

        // Create the SaathratriEntity4
        SaathratriEntity4DTO saathratriEntity4DTO = saathratriEntity4Mapper.toDto(saathratriEntity4);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity4MockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(saathratriEntity4DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSaathratriEntity4WithPatch() throws Exception {
        // Initialize the database
        saathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        saathratriEntity4Repository.save(saathratriEntity4);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity4 using partial update
        SaathratriEntity4 partialUpdatedSaathratriEntity4 = new SaathratriEntity4();
        partialUpdatedSaathratriEntity4.setCompositeId(saathratriEntity4.getCompositeId());

        partialUpdatedSaathratriEntity4.attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restSaathratriEntity4MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaathratriEntity4.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity4))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity4 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntity4UpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSaathratriEntity4, saathratriEntity4),
            getPersistedSaathratriEntity4(saathratriEntity4)
        );
    }

    @Test
    void fullUpdateSaathratriEntity4WithPatch() throws Exception {
        // Initialize the database
        saathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        saathratriEntity4Repository.save(saathratriEntity4);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity4 using partial update
        SaathratriEntity4 partialUpdatedSaathratriEntity4 = new SaathratriEntity4();
        partialUpdatedSaathratriEntity4.setCompositeId(saathratriEntity4.getCompositeId());

        partialUpdatedSaathratriEntity4.attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restSaathratriEntity4MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaathratriEntity4.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity4))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity4 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntity4UpdatableFieldsEquals(
            partialUpdatedSaathratriEntity4,
            getPersistedSaathratriEntity4(partialUpdatedSaathratriEntity4)
        );
    }

    @Test
    void patchNonExistingSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity4.setCompositeId(new SaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));

        // Create the SaathratriEntity4
        SaathratriEntity4DTO saathratriEntity4DTO = saathratriEntity4Mapper.toDto(saathratriEntity4);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntity4MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saathratriEntity4DTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity4DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity4.setCompositeId(new SaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));

        // Create the SaathratriEntity4
        SaathratriEntity4DTO saathratriEntity4DTO = saathratriEntity4Mapper.toDto(saathratriEntity4);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity4MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saathratriEntity4DTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity4DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity4.setCompositeId(new SaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));

        // Create the SaathratriEntity4
        SaathratriEntity4DTO saathratriEntity4DTO = saathratriEntity4Mapper.toDto(saathratriEntity4);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity4MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity4DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSaathratriEntity4() throws Exception {
        // Initialize the database
        saathratriEntity4.setCompositeId(new SaathratriEntity4Id());
        saathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        saathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        saathratriEntity4Repository.save(saathratriEntity4);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the saathratriEntity4
        restSaathratriEntity4MockMvc
            .perform(delete(ENTITY_API_URL_ID, saathratriEntity4.getCompositeId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return saathratriEntity4Repository.count();
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

    protected SaathratriEntity4 getPersistedSaathratriEntity4(SaathratriEntity4 saathratriEntity4) {
        return saathratriEntity4Repository.findById(saathratriEntity4.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedSaathratriEntity4ToMatchAllProperties(SaathratriEntity4 expectedSaathratriEntity4) {
        assertSaathratriEntity4AllPropertiesEquals(expectedSaathratriEntity4, getPersistedSaathratriEntity4(expectedSaathratriEntity4));
    }

    protected void assertPersistedSaathratriEntity4ToMatchUpdatableProperties(SaathratriEntity4 expectedSaathratriEntity4) {
        assertSaathratriEntity4AllUpdatablePropertiesEquals(
            expectedSaathratriEntity4,
            getPersistedSaathratriEntity4(expectedSaathratriEntity4)
        );
    }
}
