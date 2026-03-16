package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.TajUserAsserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.TajUser;
import com.saathratri.developer.blog.repository.TajUserRepository;
import com.saathratri.developer.blog.service.dto.TajUserDTO;
import com.saathratri.developer.blog.service.mapper.TajUserMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link TajUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TajUserResourceIT {

    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final UUID UPDATED_ID = UUID.randomUUID();

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/taj-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TajUserRepository tajUserRepository;

    @Autowired
    private TajUserMapper tajUserMapper;

    @Autowired
    private MockMvc restTajUserMockMvc;

    private TajUser tajUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TajUser createEntity() {
        TajUser tajUser = new TajUser().id(DEFAULT_ID).login(DEFAULT_LOGIN);
        return tajUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TajUser createUpdatedEntity() {
        TajUser tajUser = new TajUser().id(UPDATED_ID).login(UPDATED_LOGIN);
        return tajUser;
    }

    @BeforeEach
    public void initTest() {
        tajUserRepository.deleteAll();
        tajUser = createEntity();
    }

    @Test
    void createTajUser() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TajUser
        TajUserDTO tajUserDTO = tajUserMapper.toDto(tajUser);
        var returnedTajUserDTO = om.readValue(
            restTajUserMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tajUserDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TajUserDTO.class
        );

        // Validate the TajUser in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTajUser = tajUserMapper.toEntity(returnedTajUserDTO);
        assertTajUserUpdatableFieldsEquals(returnedTajUser, getPersistedTajUser(returnedTajUser));
    }

    @Test
    void createTajUserWithExistingId() throws Exception {
        // Create the TajUser with an existing ID
        tajUser.setId(UUID.randomUUID());
        TajUserDTO tajUserDTO = tajUserMapper.toDto(tajUser);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTajUserMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tajUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkLoginIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tajUser.setLogin(null);

        // Create the TajUser, which fails.
        TajUserDTO tajUserDTO = tajUserMapper.toDto(tajUser);

        restTajUserMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tajUserDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllTajUsers() throws Exception {
        // Initialize the database
        tajUser.setId(UUID.randomUUID());
        tajUserRepository.save(tajUser);

        // Get all the tajUserList
        restTajUserMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tajUser.getId().toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)));
    }

    @Test
    void getTajUser() throws Exception {
        // Initialize the database
        tajUser.setId(UUID.randomUUID());
        tajUserRepository.save(tajUser);

        // Get the tajUser
        restTajUserMockMvc
            .perform(get(ENTITY_API_URL_ID, tajUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tajUser.getId().toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)));
    }

    @Test
    void getNonExistingTajUser() throws Exception {
        // Get the tajUser
        restTajUserMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingTajUser() throws Exception {
        // Initialize the database
        tajUser.setId(UUID.randomUUID());
        tajUserRepository.save(tajUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tajUser
        TajUser updatedTajUser = tajUserRepository.findById(tajUser.getId()).orElseThrow();
        updatedTajUser.id(UPDATED_ID).login(UPDATED_LOGIN);
        TajUserDTO tajUserDTO = tajUserMapper.toDto(updatedTajUser);

        restTajUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tajUserDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tajUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the TajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTajUserToMatchAllProperties(updatedTajUser);
    }

    @Test
    void putNonExistingTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tajUser.setId(UUID.randomUUID());

        // Create the TajUser
        TajUserDTO tajUserDTO = tajUserMapper.toDto(tajUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTajUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tajUserDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tajUser.setId(UUID.randomUUID());
        // Create the TajUser
        TajUserDTO tajUserDTO = tajUserMapper.toDto(tajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTajUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tajUser.setId(UUID.randomUUID());

        // Create the TajUser
        TajUserDTO tajUserDTO = tajUserMapper.toDto(tajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTajUserMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tajUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTajUserWithPatch() throws Exception {
        // Initialize the database
        tajUser.setId(UUID.randomUUID());
        tajUserRepository.save(tajUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tajUser using partial update
        TajUser partialUpdatedTajUser = new TajUser();
        partialUpdatedTajUser.setId(tajUser.getId());

        partialUpdatedTajUser.login(UPDATED_LOGIN);

        restTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTajUser.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTajUser))
            )
            .andExpect(status().isOk());

        // Validate the TajUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTajUserUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTajUser, tajUser), getPersistedTajUser(tajUser));
    }

    @Test
    void fullUpdateTajUserWithPatch() throws Exception {
        // Initialize the database
        tajUser.setId(UUID.randomUUID());
        tajUserRepository.save(tajUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tajUser using partial update
        TajUser partialUpdatedTajUser = new TajUser();
        partialUpdatedTajUser.setId(tajUser.getId());

        partialUpdatedTajUser.login(UPDATED_LOGIN);

        restTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTajUser.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTajUser))
            )
            .andExpect(status().isOk());

        // Validate the TajUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTajUserUpdatableFieldsEquals(partialUpdatedTajUser, getPersistedTajUser(partialUpdatedTajUser));
    }

    @Test
    void patchNonExistingTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tajUser.setId(UUID.randomUUID());

        // Create the TajUser
        TajUserDTO tajUserDTO = tajUserMapper.toDto(tajUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tajUserDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tajUser.setId(UUID.randomUUID());

        // Create the TajUser
        TajUserDTO tajUserDTO = tajUserMapper.toDto(tajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tajUser.setId(UUID.randomUUID());

        // Create the TajUser
        TajUserDTO tajUserDTO = tajUserMapper.toDto(tajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tajUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTajUser() throws Exception {
        // Initialize the database
        tajUser.setId(UUID.randomUUID());
        tajUserRepository.save(tajUser);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tajUser
        restTajUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, tajUser.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tajUserRepository.count();
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

    protected TajUser getPersistedTajUser(TajUser tajUser) {
        return tajUserRepository.findById(tajUser.getId()).orElseThrow();
    }

    protected void assertPersistedTajUserToMatchAllProperties(TajUser expectedTajUser) {
        assertTajUserAllPropertiesEquals(expectedTajUser, getPersistedTajUser(expectedTajUser));
    }

    protected void assertPersistedTajUserToMatchUpdatableProperties(TajUser expectedTajUser) {
        assertTajUserAllUpdatablePropertiesEquals(expectedTajUser, getPersistedTajUser(expectedTajUser));
    }
}
