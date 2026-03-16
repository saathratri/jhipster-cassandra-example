package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.SaathratriEntityAsserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static com.saathratri.developer.blog.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.SaathratriEntity;
import com.saathratri.developer.blog.repository.SaathratriEntityRepository;
import com.saathratri.developer.blog.service.dto.SaathratriEntityDTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntityMapper;
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
 * Integration tests for the {@link SaathratriEntityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaathratriEntityResourceIT {

    private static final UUID DEFAULT_ENTITY_ID = UUID.randomUUID();
    private static final UUID UPDATED_ENTITY_ID = UUID.randomUUID();

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ENTITY_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_ENTITY_COST = new BigDecimal(2);

    private static final UUID DEFAULT_CREATED_ID = UUID.randomUUID();
    private static final UUID UPDATED_CREATED_ID = UUID.randomUUID();

    private static final UUID DEFAULT_CREATED_TIME_ID = UUID.randomUUID();
    private static final UUID UPDATED_CREATED_TIME_ID = UUID.randomUUID();

    private static final String ENTITY_API_URL = "/api/saathratri-entities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{entityId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SaathratriEntityRepository saathratriEntityRepository;

    @Autowired
    private SaathratriEntityMapper saathratriEntityMapper;

    @Autowired
    private MockMvc restSaathratriEntityMockMvc;

    private SaathratriEntity saathratriEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity createEntity() {
        SaathratriEntity saathratriEntity = new SaathratriEntity()
            .entityId(DEFAULT_ENTITY_ID)
            .entityName(DEFAULT_ENTITY_NAME)
            .entityDescription(DEFAULT_ENTITY_DESCRIPTION)
            .entityCost(DEFAULT_ENTITY_COST)
            .createdId(DEFAULT_CREATED_ID)
            .createdTimeId(DEFAULT_CREATED_TIME_ID);
        return saathratriEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity createUpdatedEntity() {
        SaathratriEntity saathratriEntity = new SaathratriEntity()
            .entityId(UPDATED_ENTITY_ID)
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .createdId(UPDATED_CREATED_ID)
            .createdTimeId(UPDATED_CREATED_TIME_ID);
        return saathratriEntity;
    }

    @BeforeEach
    public void initTest() {
        saathratriEntityRepository.deleteAll();
        saathratriEntity = createEntity();
    }

    @Test
    void createSaathratriEntity() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SaathratriEntity
        SaathratriEntityDTO saathratriEntityDTO = saathratriEntityMapper.toDto(saathratriEntity);
        var returnedSaathratriEntityDTO = om.readValue(
            restSaathratriEntityMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(saathratriEntityDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SaathratriEntityDTO.class
        );

        // Validate the SaathratriEntity in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSaathratriEntity = saathratriEntityMapper.toEntity(returnedSaathratriEntityDTO);
        assertSaathratriEntityUpdatableFieldsEquals(returnedSaathratriEntity, getPersistedSaathratriEntity(returnedSaathratriEntity));
    }

    @Test
    void createSaathratriEntityWithExistingId() throws Exception {
        // Create the SaathratriEntity with an existing ID
        saathratriEntity.setEntityId(UUID.randomUUID());
        SaathratriEntityDTO saathratriEntityDTO = saathratriEntityMapper.toDto(saathratriEntity);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaathratriEntityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(saathratriEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSaathratriEntities() throws Exception {
        // Initialize the database
        saathratriEntity.setEntityId(UUID.randomUUID());
        saathratriEntityRepository.save(saathratriEntity);

        // Get all the saathratriEntityList
        restSaathratriEntityMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(saathratriEntity.getEntityId().toString())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].entityDescription").value(hasItem(DEFAULT_ENTITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].entityCost").value(hasItem(sameNumber(DEFAULT_ENTITY_COST))))
            .andExpect(jsonPath("$.[*].createdId").value(hasItem(DEFAULT_CREATED_ID.toString())))
            .andExpect(jsonPath("$.[*].createdTimeId").value(hasItem(DEFAULT_CREATED_TIME_ID.toString())));
    }

    @Test
    void getSaathratriEntity() throws Exception {
        // Initialize the database
        saathratriEntity.setEntityId(UUID.randomUUID());
        saathratriEntityRepository.save(saathratriEntity);

        // Get the saathratriEntity
        restSaathratriEntityMockMvc
            .perform(get(ENTITY_API_URL_ID, saathratriEntity.getEntityId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(saathratriEntity.getEntityId().toString())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].entityDescription").value(hasItem(DEFAULT_ENTITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].entityCost").value(hasItem(sameNumber(DEFAULT_ENTITY_COST))))
            .andExpect(jsonPath("$.[*].createdId").value(hasItem(DEFAULT_CREATED_ID.toString())))
            .andExpect(jsonPath("$.[*].createdTimeId").value(hasItem(DEFAULT_CREATED_TIME_ID.toString())));
    }

    @Test
    void getNonExistingSaathratriEntity() throws Exception {
        // Get the saathratriEntity
        restSaathratriEntityMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSaathratriEntity() throws Exception {
        // Initialize the database
        saathratriEntity.setEntityId(UUID.randomUUID());
        saathratriEntityRepository.save(saathratriEntity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity
        SaathratriEntity updatedSaathratriEntity = saathratriEntityRepository.findById(saathratriEntity.getEntityId()).orElseThrow();
        updatedSaathratriEntity
            .entityId(UPDATED_ENTITY_ID)
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .createdId(UPDATED_CREATED_ID)
            .createdTimeId(UPDATED_CREATED_TIME_ID);
        SaathratriEntityDTO saathratriEntityDTO = saathratriEntityMapper.toDto(updatedSaathratriEntity);

        restSaathratriEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saathratriEntityDTO.getEntityId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntityDTO))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSaathratriEntityToMatchAllProperties(updatedSaathratriEntity);
    }

    @Test
    void putNonExistingSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity.setEntityId(UUID.randomUUID());

        // Create the SaathratriEntity
        SaathratriEntityDTO saathratriEntityDTO = saathratriEntityMapper.toDto(saathratriEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saathratriEntityDTO.getEntityId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity.setEntityId(UUID.randomUUID());
        // Create the SaathratriEntity
        SaathratriEntityDTO saathratriEntityDTO = saathratriEntityMapper.toDto(saathratriEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity.setEntityId(UUID.randomUUID());

        // Create the SaathratriEntity
        SaathratriEntityDTO saathratriEntityDTO = saathratriEntityMapper.toDto(saathratriEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntityMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(saathratriEntityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSaathratriEntityWithPatch() throws Exception {
        // Initialize the database
        saathratriEntity.setEntityId(UUID.randomUUID());
        saathratriEntityRepository.save(saathratriEntity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity using partial update
        SaathratriEntity partialUpdatedSaathratriEntity = new SaathratriEntity();
        partialUpdatedSaathratriEntity.setEntityId(saathratriEntity.getEntityId());

        partialUpdatedSaathratriEntity
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .createdId(UPDATED_CREATED_ID)
            .createdTimeId(UPDATED_CREATED_TIME_ID);

        restSaathratriEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaathratriEntity.getEntityId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntityUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSaathratriEntity, saathratriEntity),
            getPersistedSaathratriEntity(saathratriEntity)
        );
    }

    @Test
    void fullUpdateSaathratriEntityWithPatch() throws Exception {
        // Initialize the database
        saathratriEntity.setEntityId(UUID.randomUUID());
        saathratriEntityRepository.save(saathratriEntity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity using partial update
        SaathratriEntity partialUpdatedSaathratriEntity = new SaathratriEntity();
        partialUpdatedSaathratriEntity.setEntityId(saathratriEntity.getEntityId());

        partialUpdatedSaathratriEntity
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .createdId(UPDATED_CREATED_ID)
            .createdTimeId(UPDATED_CREATED_TIME_ID);

        restSaathratriEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaathratriEntity.getEntityId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntityUpdatableFieldsEquals(
            partialUpdatedSaathratriEntity,
            getPersistedSaathratriEntity(partialUpdatedSaathratriEntity)
        );
    }

    @Test
    void patchNonExistingSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity.setEntityId(UUID.randomUUID());

        // Create the SaathratriEntity
        SaathratriEntityDTO saathratriEntityDTO = saathratriEntityMapper.toDto(saathratriEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saathratriEntityDTO.getEntityId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity.setEntityId(UUID.randomUUID());

        // Create the SaathratriEntity
        SaathratriEntityDTO saathratriEntityDTO = saathratriEntityMapper.toDto(saathratriEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity.setEntityId(UUID.randomUUID());

        // Create the SaathratriEntity
        SaathratriEntityDTO saathratriEntityDTO = saathratriEntityMapper.toDto(saathratriEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSaathratriEntity() throws Exception {
        // Initialize the database
        saathratriEntity.setEntityId(UUID.randomUUID());
        saathratriEntityRepository.save(saathratriEntity);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the saathratriEntity
        restSaathratriEntityMockMvc
            .perform(delete(ENTITY_API_URL_ID, saathratriEntity.getEntityId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return saathratriEntityRepository.count();
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

    protected SaathratriEntity getPersistedSaathratriEntity(SaathratriEntity saathratriEntity) {
        return saathratriEntityRepository.findById(saathratriEntity.getEntityId()).orElseThrow();
    }

    protected void assertPersistedSaathratriEntityToMatchAllProperties(SaathratriEntity expectedSaathratriEntity) {
        assertSaathratriEntityAllPropertiesEquals(expectedSaathratriEntity, getPersistedSaathratriEntity(expectedSaathratriEntity));
    }

    protected void assertPersistedSaathratriEntityToMatchUpdatableProperties(SaathratriEntity expectedSaathratriEntity) {
        assertSaathratriEntityAllUpdatablePropertiesEquals(
            expectedSaathratriEntity,
            getPersistedSaathratriEntity(expectedSaathratriEntity)
        );
    }
}
