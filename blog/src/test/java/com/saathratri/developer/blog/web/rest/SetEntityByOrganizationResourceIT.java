package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.SetEntityByOrganizationAsserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.SetEntityByOrganization;
import com.saathratri.developer.blog.repository.SetEntityByOrganizationRepository;
import com.saathratri.developer.blog.service.dto.SetEntityByOrganizationDTO;
import com.saathratri.developer.blog.service.mapper.SetEntityByOrganizationMapper;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link SetEntityByOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SetEntityByOrganizationResourceIT {

    private static final UUID DEFAULT_ORGANIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORGANIZATION_ID = UUID.randomUUID();

    private static final Set<String> DEFAULT_TAGS = new TreeSet<String>();
    private static final Set<String> UPDATED_TAGS = new TreeSet<String>();

    static {
        DEFAULT_TAGS.add("AAAAAAAAAA");
        UPDATED_TAGS.add("BBBBBBBBBB");
    }

    private static final String ENTITY_API_URL = "/api/set-entity-by-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SetEntityByOrganizationRepository setEntityByOrganizationRepository;

    @Autowired
    private SetEntityByOrganizationMapper setEntityByOrganizationMapper;

    @Autowired
    private MockMvc restSetEntityByOrganizationMockMvc;

    private SetEntityByOrganization setEntityByOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SetEntityByOrganization createEntity() {
        SetEntityByOrganization setEntityByOrganization = new SetEntityByOrganization()
            .organizationId(DEFAULT_ORGANIZATION_ID)
            .tags(DEFAULT_TAGS);
        return setEntityByOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SetEntityByOrganization createUpdatedEntity() {
        SetEntityByOrganization setEntityByOrganization = new SetEntityByOrganization()
            .organizationId(UPDATED_ORGANIZATION_ID)
            .tags(UPDATED_TAGS);
        return setEntityByOrganization;
    }

    @BeforeEach
    public void initTest() {
        setEntityByOrganizationRepository.deleteAll();
        setEntityByOrganization = createEntity();
    }

    @Test
    void createSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SetEntityByOrganization
        SetEntityByOrganizationDTO setEntityByOrganizationDTO = setEntityByOrganizationMapper.toDto(setEntityByOrganization);
        var returnedSetEntityByOrganizationDTO = om.readValue(
            restSetEntityByOrganizationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(setEntityByOrganizationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SetEntityByOrganizationDTO.class
        );

        // Validate the SetEntityByOrganization in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSetEntityByOrganization = setEntityByOrganizationMapper.toEntity(returnedSetEntityByOrganizationDTO);
        assertSetEntityByOrganizationUpdatableFieldsEquals(
            returnedSetEntityByOrganization,
            getPersistedSetEntityByOrganization(returnedSetEntityByOrganization)
        );
    }

    @Test
    void createSetEntityByOrganizationWithExistingId() throws Exception {
        // Create the SetEntityByOrganization with an existing ID
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());
        SetEntityByOrganizationDTO setEntityByOrganizationDTO = setEntityByOrganizationMapper.toDto(setEntityByOrganization);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSetEntityByOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(setEntityByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSetEntityByOrganizations() throws Exception {
        // Initialize the database
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());
        setEntityByOrganizationRepository.save(setEntityByOrganization);

        // Get all the setEntityByOrganizationList
        restSetEntityByOrganizationMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(setEntityByOrganization.getOrganizationId().toString())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)));
    }

    @Test
    void getSetEntityByOrganization() throws Exception {
        // Initialize the database
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());
        setEntityByOrganizationRepository.save(setEntityByOrganization);

        // Get the setEntityByOrganization
        restSetEntityByOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, setEntityByOrganization.getOrganizationId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(setEntityByOrganization.getOrganizationId().toString())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)));
    }

    @Test
    void getNonExistingSetEntityByOrganization() throws Exception {
        // Get the setEntityByOrganization
        restSetEntityByOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSetEntityByOrganization() throws Exception {
        // Initialize the database
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());
        setEntityByOrganizationRepository.save(setEntityByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the setEntityByOrganization
        SetEntityByOrganization updatedSetEntityByOrganization = setEntityByOrganizationRepository
            .findById(setEntityByOrganization.getOrganizationId())
            .orElseThrow();
        updatedSetEntityByOrganization.organizationId(UPDATED_ORGANIZATION_ID).tags(UPDATED_TAGS);
        SetEntityByOrganizationDTO setEntityByOrganizationDTO = setEntityByOrganizationMapper.toDto(updatedSetEntityByOrganization);

        restSetEntityByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, setEntityByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(setEntityByOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the SetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSetEntityByOrganizationToMatchAllProperties(updatedSetEntityByOrganization);
    }

    @Test
    void putNonExistingSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the SetEntityByOrganization
        SetEntityByOrganizationDTO setEntityByOrganizationDTO = setEntityByOrganizationMapper.toDto(setEntityByOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSetEntityByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, setEntityByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(setEntityByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());
        // Create the SetEntityByOrganization
        SetEntityByOrganizationDTO setEntityByOrganizationDTO = setEntityByOrganizationMapper.toDto(setEntityByOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetEntityByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(setEntityByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the SetEntityByOrganization
        SetEntityByOrganizationDTO setEntityByOrganizationDTO = setEntityByOrganizationMapper.toDto(setEntityByOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetEntityByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(setEntityByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSetEntityByOrganizationWithPatch() throws Exception {
        // Initialize the database
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());
        setEntityByOrganizationRepository.save(setEntityByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the setEntityByOrganization using partial update
        SetEntityByOrganization partialUpdatedSetEntityByOrganization = new SetEntityByOrganization();
        partialUpdatedSetEntityByOrganization.setOrganizationId(setEntityByOrganization.getOrganizationId());

        partialUpdatedSetEntityByOrganization.tags(UPDATED_TAGS);

        restSetEntityByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSetEntityByOrganization.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSetEntityByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the SetEntityByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSetEntityByOrganizationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSetEntityByOrganization, setEntityByOrganization),
            getPersistedSetEntityByOrganization(setEntityByOrganization)
        );
    }

    @Test
    void fullUpdateSetEntityByOrganizationWithPatch() throws Exception {
        // Initialize the database
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());
        setEntityByOrganizationRepository.save(setEntityByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the setEntityByOrganization using partial update
        SetEntityByOrganization partialUpdatedSetEntityByOrganization = new SetEntityByOrganization();
        partialUpdatedSetEntityByOrganization.setOrganizationId(setEntityByOrganization.getOrganizationId());

        partialUpdatedSetEntityByOrganization.tags(UPDATED_TAGS);

        restSetEntityByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSetEntityByOrganization.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSetEntityByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the SetEntityByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSetEntityByOrganizationUpdatableFieldsEquals(
            partialUpdatedSetEntityByOrganization,
            getPersistedSetEntityByOrganization(partialUpdatedSetEntityByOrganization)
        );
    }

    @Test
    void patchNonExistingSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the SetEntityByOrganization
        SetEntityByOrganizationDTO setEntityByOrganizationDTO = setEntityByOrganizationMapper.toDto(setEntityByOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSetEntityByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, setEntityByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(setEntityByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the SetEntityByOrganization
        SetEntityByOrganizationDTO setEntityByOrganizationDTO = setEntityByOrganizationMapper.toDto(setEntityByOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetEntityByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(setEntityByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the SetEntityByOrganization
        SetEntityByOrganizationDTO setEntityByOrganizationDTO = setEntityByOrganizationMapper.toDto(setEntityByOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetEntityByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(setEntityByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSetEntityByOrganization() throws Exception {
        // Initialize the database
        setEntityByOrganization.setOrganizationId(UUID.randomUUID());
        setEntityByOrganizationRepository.save(setEntityByOrganization);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the setEntityByOrganization
        restSetEntityByOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, setEntityByOrganization.getOrganizationId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return setEntityByOrganizationRepository.count();
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

    protected SetEntityByOrganization getPersistedSetEntityByOrganization(SetEntityByOrganization setEntityByOrganization) {
        return setEntityByOrganizationRepository.findById(setEntityByOrganization.getOrganizationId()).orElseThrow();
    }

    protected void assertPersistedSetEntityByOrganizationToMatchAllProperties(SetEntityByOrganization expectedSetEntityByOrganization) {
        assertSetEntityByOrganizationAllPropertiesEquals(
            expectedSetEntityByOrganization,
            getPersistedSetEntityByOrganization(expectedSetEntityByOrganization)
        );
    }

    protected void assertPersistedSetEntityByOrganizationToMatchUpdatableProperties(
        SetEntityByOrganization expectedSetEntityByOrganization
    ) {
        assertSetEntityByOrganizationAllUpdatablePropertiesEquals(
            expectedSetEntityByOrganization,
            getPersistedSetEntityByOrganization(expectedSetEntityByOrganization)
        );
    }
}
