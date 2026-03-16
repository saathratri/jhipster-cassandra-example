package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.LandingPageByOrganizationAsserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.LandingPageByOrganization;
import com.saathratri.developer.blog.repository.LandingPageByOrganizationRepository;
import com.saathratri.developer.blog.service.dto.LandingPageByOrganizationDTO;
import com.saathratri.developer.blog.service.mapper.LandingPageByOrganizationMapper;
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
 * Integration tests for the {@link LandingPageByOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LandingPageByOrganizationResourceIT {

    private static final UUID DEFAULT_ORGANIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORGANIZATION_ID = UUID.randomUUID();

    private static final Map<String, String> DEFAULT_DETAILS_TEXT = new HashMap<String, String>();
    private static final Map<String, String> UPDATED_DETAILS_TEXT = new HashMap<String, String>();

    static {
        DEFAULT_DETAILS_TEXT.put("AAAAAAAAAA", "1");
        UPDATED_DETAILS_TEXT.put("AAAAAAAAAA", "2");
    }

    private static final Map<String, BigDecimal> DEFAULT_DETAILS_DECIMAL = new HashMap<String, BigDecimal>();
    private static final Map<String, BigDecimal> UPDATED_DETAILS_DECIMAL = new HashMap<String, BigDecimal>();

    static {
        DEFAULT_DETAILS_DECIMAL.put("AAAAAAAAAA", new BigDecimal(1));
        UPDATED_DETAILS_DECIMAL.put("AAAAAAAAAA", new BigDecimal(2));
    }

    private static final Map<String, Boolean> DEFAULT_DETAILS_BOOLEAN = new HashMap<String, Boolean>();
    private static final Map<String, Boolean> UPDATED_DETAILS_BOOLEAN = new HashMap<String, Boolean>();

    static {
        DEFAULT_DETAILS_BOOLEAN.put("AAAAAAAAAA", false);
        UPDATED_DETAILS_BOOLEAN.put("AAAAAAAAAA", true);
    }

    private static final Map<String, Long> DEFAULT_DETAILS_BIG_INT = new HashMap<String, Long>();
    private static final Map<String, Long> UPDATED_DETAILS_BIG_INT = new HashMap<String, Long>();

    static {
        DEFAULT_DETAILS_BIG_INT.put("AAAAAAAAAA", 1L);
        UPDATED_DETAILS_BIG_INT.put("AAAAAAAAAA", 2L);
    }

    private static final String ENTITY_API_URL = "/api/landing-page-by-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LandingPageByOrganizationRepository landingPageByOrganizationRepository;

    @Autowired
    private LandingPageByOrganizationMapper landingPageByOrganizationMapper;

    @Autowired
    private MockMvc restLandingPageByOrganizationMockMvc;

    private LandingPageByOrganization landingPageByOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LandingPageByOrganization createEntity() {
        LandingPageByOrganization landingPageByOrganization = new LandingPageByOrganization()
            .organizationId(DEFAULT_ORGANIZATION_ID)
            .detailsText(DEFAULT_DETAILS_TEXT)
            .detailsDecimal(DEFAULT_DETAILS_DECIMAL)
            .detailsBoolean(DEFAULT_DETAILS_BOOLEAN)
            .detailsBigInt(DEFAULT_DETAILS_BIG_INT);
        return landingPageByOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LandingPageByOrganization createUpdatedEntity() {
        LandingPageByOrganization landingPageByOrganization = new LandingPageByOrganization()
            .organizationId(UPDATED_ORGANIZATION_ID)
            .detailsText(UPDATED_DETAILS_TEXT)
            .detailsDecimal(UPDATED_DETAILS_DECIMAL)
            .detailsBoolean(UPDATED_DETAILS_BOOLEAN)
            .detailsBigInt(UPDATED_DETAILS_BIG_INT);
        return landingPageByOrganization;
    }

    @BeforeEach
    public void initTest() {
        landingPageByOrganizationRepository.deleteAll();
        landingPageByOrganization = createEntity();
    }

    @Test
    void createLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LandingPageByOrganization
        LandingPageByOrganizationDTO landingPageByOrganizationDTO = landingPageByOrganizationMapper.toDto(landingPageByOrganization);
        var returnedLandingPageByOrganizationDTO = om.readValue(
            restLandingPageByOrganizationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(landingPageByOrganizationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LandingPageByOrganizationDTO.class
        );

        // Validate the LandingPageByOrganization in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLandingPageByOrganization = landingPageByOrganizationMapper.toEntity(returnedLandingPageByOrganizationDTO);
        assertLandingPageByOrganizationUpdatableFieldsEquals(
            returnedLandingPageByOrganization,
            getPersistedLandingPageByOrganization(returnedLandingPageByOrganization)
        );
    }

    @Test
    void createLandingPageByOrganizationWithExistingId() throws Exception {
        // Create the LandingPageByOrganization with an existing ID
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());
        LandingPageByOrganizationDTO landingPageByOrganizationDTO = landingPageByOrganizationMapper.toDto(landingPageByOrganization);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandingPageByOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(landingPageByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllLandingPageByOrganizations() throws Exception {
        // Initialize the database
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());
        landingPageByOrganizationRepository.save(landingPageByOrganization);

        // Get all the landingPageByOrganizationList
        restLandingPageByOrganizationMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(landingPageByOrganization.getOrganizationId().toString())))
            .andExpect(jsonPath("$.[*].detailsText['AAAAAAAAAA']").value(DEFAULT_DETAILS_TEXT.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].detailsDecimal['AAAAAAAAAA']").value(DEFAULT_DETAILS_DECIMAL.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].detailsBoolean['AAAAAAAAAA']").value(DEFAULT_DETAILS_BOOLEAN.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].detailsBigInt['AAAAAAAAAA']").value(DEFAULT_DETAILS_BIG_INT.get("AAAAAAAAAA")));
    }

    @Test
    void getLandingPageByOrganization() throws Exception {
        // Initialize the database
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());
        landingPageByOrganizationRepository.save(landingPageByOrganization);

        // Get the landingPageByOrganization
        restLandingPageByOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, landingPageByOrganization.getOrganizationId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(landingPageByOrganization.getOrganizationId().toString())))
            .andExpect(jsonPath("$.[*].detailsText['AAAAAAAAAA']").value(DEFAULT_DETAILS_TEXT.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].detailsDecimal['AAAAAAAAAA']").value(DEFAULT_DETAILS_DECIMAL.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].detailsBoolean['AAAAAAAAAA']").value(DEFAULT_DETAILS_BOOLEAN.get("AAAAAAAAAA")))
            .andExpect(jsonPath("$.[*].detailsBigInt['AAAAAAAAAA']").value(DEFAULT_DETAILS_BIG_INT.get("AAAAAAAAAA")));
    }

    @Test
    void getNonExistingLandingPageByOrganization() throws Exception {
        // Get the landingPageByOrganization
        restLandingPageByOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingLandingPageByOrganization() throws Exception {
        // Initialize the database
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());
        landingPageByOrganizationRepository.save(landingPageByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the landingPageByOrganization
        LandingPageByOrganization updatedLandingPageByOrganization = landingPageByOrganizationRepository
            .findById(landingPageByOrganization.getOrganizationId())
            .orElseThrow();
        updatedLandingPageByOrganization
            .organizationId(UPDATED_ORGANIZATION_ID)
            .detailsText(UPDATED_DETAILS_TEXT)
            .detailsDecimal(UPDATED_DETAILS_DECIMAL)
            .detailsBoolean(UPDATED_DETAILS_BOOLEAN)
            .detailsBigInt(UPDATED_DETAILS_BIG_INT);
        LandingPageByOrganizationDTO landingPageByOrganizationDTO = landingPageByOrganizationMapper.toDto(updatedLandingPageByOrganization);

        restLandingPageByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landingPageByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(landingPageByOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the LandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLandingPageByOrganizationToMatchAllProperties(updatedLandingPageByOrganization);
    }

    @Test
    void putNonExistingLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the LandingPageByOrganization
        LandingPageByOrganizationDTO landingPageByOrganizationDTO = landingPageByOrganizationMapper.toDto(landingPageByOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandingPageByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landingPageByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(landingPageByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());
        // Create the LandingPageByOrganization
        LandingPageByOrganizationDTO landingPageByOrganizationDTO = landingPageByOrganizationMapper.toDto(landingPageByOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandingPageByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(landingPageByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the LandingPageByOrganization
        LandingPageByOrganizationDTO landingPageByOrganizationDTO = landingPageByOrganizationMapper.toDto(landingPageByOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandingPageByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(landingPageByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLandingPageByOrganizationWithPatch() throws Exception {
        // Initialize the database
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());
        landingPageByOrganizationRepository.save(landingPageByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the landingPageByOrganization using partial update
        LandingPageByOrganization partialUpdatedLandingPageByOrganization = new LandingPageByOrganization();
        partialUpdatedLandingPageByOrganization.setOrganizationId(landingPageByOrganization.getOrganizationId());

        partialUpdatedLandingPageByOrganization
            .detailsText(UPDATED_DETAILS_TEXT)
            .detailsDecimal(UPDATED_DETAILS_DECIMAL)
            .detailsBoolean(UPDATED_DETAILS_BOOLEAN)
            .detailsBigInt(UPDATED_DETAILS_BIG_INT);

        restLandingPageByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandingPageByOrganization.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLandingPageByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the LandingPageByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLandingPageByOrganizationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLandingPageByOrganization, landingPageByOrganization),
            getPersistedLandingPageByOrganization(landingPageByOrganization)
        );
    }

    @Test
    void fullUpdateLandingPageByOrganizationWithPatch() throws Exception {
        // Initialize the database
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());
        landingPageByOrganizationRepository.save(landingPageByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the landingPageByOrganization using partial update
        LandingPageByOrganization partialUpdatedLandingPageByOrganization = new LandingPageByOrganization();
        partialUpdatedLandingPageByOrganization.setOrganizationId(landingPageByOrganization.getOrganizationId());

        partialUpdatedLandingPageByOrganization
            .detailsText(UPDATED_DETAILS_TEXT)
            .detailsDecimal(UPDATED_DETAILS_DECIMAL)
            .detailsBoolean(UPDATED_DETAILS_BOOLEAN)
            .detailsBigInt(UPDATED_DETAILS_BIG_INT);

        restLandingPageByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandingPageByOrganization.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLandingPageByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the LandingPageByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLandingPageByOrganizationUpdatableFieldsEquals(
            partialUpdatedLandingPageByOrganization,
            getPersistedLandingPageByOrganization(partialUpdatedLandingPageByOrganization)
        );
    }

    @Test
    void patchNonExistingLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the LandingPageByOrganization
        LandingPageByOrganizationDTO landingPageByOrganizationDTO = landingPageByOrganizationMapper.toDto(landingPageByOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandingPageByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, landingPageByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(landingPageByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the LandingPageByOrganization
        LandingPageByOrganizationDTO landingPageByOrganizationDTO = landingPageByOrganizationMapper.toDto(landingPageByOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandingPageByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(landingPageByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the LandingPageByOrganization
        LandingPageByOrganizationDTO landingPageByOrganizationDTO = landingPageByOrganizationMapper.toDto(landingPageByOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandingPageByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(landingPageByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLandingPageByOrganization() throws Exception {
        // Initialize the database
        landingPageByOrganization.setOrganizationId(UUID.randomUUID());
        landingPageByOrganizationRepository.save(landingPageByOrganization);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the landingPageByOrganization
        restLandingPageByOrganizationMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, landingPageByOrganization.getOrganizationId()).with(csrf()).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return landingPageByOrganizationRepository.count();
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

    protected LandingPageByOrganization getPersistedLandingPageByOrganization(LandingPageByOrganization landingPageByOrganization) {
        return landingPageByOrganizationRepository.findById(landingPageByOrganization.getOrganizationId()).orElseThrow();
    }

    protected void assertPersistedLandingPageByOrganizationToMatchAllProperties(
        LandingPageByOrganization expectedLandingPageByOrganization
    ) {
        assertLandingPageByOrganizationAllPropertiesEquals(
            expectedLandingPageByOrganization,
            getPersistedLandingPageByOrganization(expectedLandingPageByOrganization)
        );
    }

    protected void assertPersistedLandingPageByOrganizationToMatchUpdatableProperties(
        LandingPageByOrganization expectedLandingPageByOrganization
    ) {
        assertLandingPageByOrganizationAllUpdatablePropertiesEquals(
            expectedLandingPageByOrganization,
            getPersistedLandingPageByOrganization(expectedLandingPageByOrganization)
        );
    }
}
