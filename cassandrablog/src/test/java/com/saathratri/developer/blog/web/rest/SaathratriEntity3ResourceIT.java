package com.saathratri.developer.blog.web.rest;

import static com.saathratri.developer.blog.domain.SaathratriEntity3Asserts.*;
import static com.saathratri.developer.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static com.saathratri.developer.blog.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.blog.IntegrationTest;
import com.saathratri.developer.blog.domain.SaathratriEntity3;
import com.saathratri.developer.blog.domain.SaathratriEntity3Id;
import com.saathratri.developer.blog.repository.SaathratriEntity3Repository;
import com.saathratri.developer.blog.service.dto.SaathratriEntity3DTO;
import com.saathratri.developer.blog.service.mapper.SaathratriEntity3Mapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link SaathratriEntity3Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaathratriEntity3ResourceIT {

    private static final String DEFAULT_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final UUID DEFAULT_CREATED_TIME_ID = UUID.randomUUID();
    private static final UUID UPDATED_CREATED_TIME_ID = UUID.randomUUID();

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ENTITY_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_ENTITY_COST = new BigDecimal(2);

    private static final Long DEFAULT_DEPARTURE_DATE = 1L;
    private static final Long UPDATED_DEPARTURE_DATE = 2L;

    private static final Set<String> DEFAULT_TAGS = new TreeSet<String>();
    private static final Set<String> UPDATED_TAGS = new TreeSet<String>();

    static {
        DEFAULT_TAGS.add("AAAAAAAAAA");
        UPDATED_TAGS.add("BBBBBBBBBB");
    }

    private static final String ENTITY_API_URL = "/api/saathratri-entity-3-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{entityType}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SaathratriEntity3Repository saathratriEntity3Repository;

    @Autowired
    private SaathratriEntity3Mapper saathratriEntity3Mapper;

    @Autowired
    private MockMvc restSaathratriEntity3MockMvc;

    private SaathratriEntity3 saathratriEntity3;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity3 createEntity() {
        SaathratriEntity3 saathratriEntity3 = new SaathratriEntity3()
            .compositeId(new SaathratriEntity3Id().entityType(DEFAULT_ENTITY_TYPE).createdTimeId(DEFAULT_CREATED_TIME_ID))
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .entityCost(new BigDecimal(1))
            .departureDate(1L)
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags1");
                    }
                }
            );
        saathratriEntity3.setCompositeId(new SaathratriEntity3Id(DEFAULT_ENTITY_TYPE, DEFAULT_CREATED_TIME_ID));
        return saathratriEntity3;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaathratriEntity3 createUpdatedEntity() {
        SaathratriEntity3 saathratriEntity3 = new SaathratriEntity3()
            .compositeId(new SaathratriEntity3Id().entityType(UPDATED_ENTITY_TYPE).createdTimeId(UPDATED_CREATED_TIME_ID))
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .entityCost(new BigDecimal(1))
            .departureDate(1L)
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags1");
                    }
                }
            );
        saathratriEntity3.setCompositeId(new SaathratriEntity3Id(UPDATED_ENTITY_TYPE, UPDATED_CREATED_TIME_ID));
        return saathratriEntity3;
    }

    @BeforeEach
    public void initTest() {
        saathratriEntity3Repository.deleteAll();
        saathratriEntity3 = createEntity();
    }

    @Test
    void createSaathratriEntity3() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SaathratriEntity3
        SaathratriEntity3DTO saathratriEntity3DTO = saathratriEntity3Mapper.toDto(saathratriEntity3);
        var returnedSaathratriEntity3DTO = om.readValue(
            restSaathratriEntity3MockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(saathratriEntity3DTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SaathratriEntity3DTO.class
        );

        // Validate the SaathratriEntity3 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSaathratriEntity3 = saathratriEntity3Mapper.toEntity(returnedSaathratriEntity3DTO);
        assertSaathratriEntity3UpdatableFieldsEquals(returnedSaathratriEntity3, getPersistedSaathratriEntity3(returnedSaathratriEntity3));
    }

    @Test
    void createSaathratriEntity3WithExistingId() throws Exception {
        // Create the SaathratriEntity3 with an existing ID
        saathratriEntity3Repository.save(saathratriEntity3);
        SaathratriEntity3DTO saathratriEntity3DTO = saathratriEntity3Mapper.toDto(saathratriEntity3);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaathratriEntity3MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSaathratriEntity3s() throws Exception {
        // Initialize the database
        saathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity3Repository.save(saathratriEntity3);

        // Get all the saathratriEntity3List
        restSaathratriEntity3MockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.entityType").value(hasItem(saathratriEntity3.getCompositeId().getEntityType().toString()))
            )
            .andExpect(
                jsonPath("$.[*].compositeId.createdTimeId").value(hasItem(saathratriEntity3.getCompositeId().getCreatedTimeId().toString()))
            )
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].entityDescription").value(hasItem(DEFAULT_ENTITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].entityCost").value(hasItem(sameNumber(DEFAULT_ENTITY_COST))))
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.intValue())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)));
    }

    @Test
    void getSaathratriEntity3() throws Exception {
        // Initialize the database
        saathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity3Repository.save(saathratriEntity3);

        // Get the saathratriEntity3
        restSaathratriEntity3MockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    saathratriEntity3.getCompositeId().getEntityType() + "/" + saathratriEntity3.getCompositeId().getCreatedTimeId()
                )
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.entityType").value(hasItem(saathratriEntity3.getCompositeId().getEntityType().toString()))
            )
            .andExpect(
                jsonPath("$.[*].compositeId.createdTimeId").value(hasItem(saathratriEntity3.getCompositeId().getCreatedTimeId().toString()))
            )
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].entityDescription").value(hasItem(DEFAULT_ENTITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].entityCost").value(hasItem(sameNumber(DEFAULT_ENTITY_COST))))
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.intValue())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)));
    }

    @Test
    void getNonExistingSaathratriEntity3() throws Exception {
        // Get the saathratriEntity3
        restSaathratriEntity3MockMvc
            .perform(
                get(
                    ENTITY_API_URL_ID,
                    saathratriEntity3.getCompositeId().getEntityType() + "/" + saathratriEntity3.getCompositeId().getCreatedTimeId()
                )
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingSaathratriEntity3() throws Exception {
        // Initialize the database
        saathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity3Repository.save(saathratriEntity3);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity3
        SaathratriEntity3 updatedSaathratriEntity3 = saathratriEntity3Repository.findById(saathratriEntity3.getCompositeId()).orElseThrow();
        updatedSaathratriEntity3
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .tags(UPDATED_TAGS);
        SaathratriEntity3DTO saathratriEntity3DTO = saathratriEntity3Mapper.toDto(updatedSaathratriEntity3);

        restSaathratriEntity3MockMvc
            .perform(
                put(ENTITY_API_URL_ID, saathratriEntity3DTO)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity3DTO))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSaathratriEntity3ToMatchAllProperties(updatedSaathratriEntity3);
    }

    @Test
    void putNonExistingSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity3.setCompositeId(new SaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the SaathratriEntity3
        SaathratriEntity3DTO saathratriEntity3DTO = saathratriEntity3Mapper.toDto(saathratriEntity3);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntity3MockMvc
            .perform(
                put(
                    ENTITY_API_URL_ID,
                    saathratriEntity3.getCompositeId().getEntityType() + "/" + saathratriEntity3.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity3.setCompositeId(new SaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));
        // Create the SaathratriEntity3
        SaathratriEntity3DTO saathratriEntity3DTO = saathratriEntity3Mapper.toDto(saathratriEntity3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity3MockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saathratriEntity3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity3.setCompositeId(new SaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the SaathratriEntity3
        SaathratriEntity3DTO saathratriEntity3DTO = saathratriEntity3Mapper.toDto(saathratriEntity3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity3MockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(saathratriEntity3DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSaathratriEntity3WithPatch() throws Exception {
        // Initialize the database
        saathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity3Repository.save(saathratriEntity3);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity3 using partial update
        SaathratriEntity3 partialUpdatedSaathratriEntity3 = new SaathratriEntity3();
        partialUpdatedSaathratriEntity3.setCompositeId(saathratriEntity3.getCompositeId());

        partialUpdatedSaathratriEntity3
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .tags(UPDATED_TAGS);

        restSaathratriEntity3MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaathratriEntity3.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity3))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity3 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntity3UpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSaathratriEntity3, saathratriEntity3),
            getPersistedSaathratriEntity3(saathratriEntity3)
        );
    }

    @Test
    void fullUpdateSaathratriEntity3WithPatch() throws Exception {
        // Initialize the database
        saathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity3Repository.save(saathratriEntity3);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saathratriEntity3 using partial update
        SaathratriEntity3 partialUpdatedSaathratriEntity3 = new SaathratriEntity3();
        partialUpdatedSaathratriEntity3.setCompositeId(saathratriEntity3.getCompositeId());

        partialUpdatedSaathratriEntity3
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .tags(UPDATED_TAGS);

        restSaathratriEntity3MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaathratriEntity3.getCompositeId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaathratriEntity3))
            )
            .andExpect(status().isOk());

        // Validate the SaathratriEntity3 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaathratriEntity3UpdatableFieldsEquals(
            partialUpdatedSaathratriEntity3,
            getPersistedSaathratriEntity3(partialUpdatedSaathratriEntity3)
        );
    }

    @Test
    void patchNonExistingSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity3.setCompositeId(new SaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the SaathratriEntity3
        SaathratriEntity3DTO saathratriEntity3DTO = saathratriEntity3Mapper.toDto(saathratriEntity3);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaathratriEntity3MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saathratriEntity3DTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity3.setCompositeId(new SaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the SaathratriEntity3
        SaathratriEntity3DTO saathratriEntity3DTO = saathratriEntity3Mapper.toDto(saathratriEntity3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity3MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saathratriEntity3DTO)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saathratriEntity3.setCompositeId(new SaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the SaathratriEntity3
        SaathratriEntity3DTO saathratriEntity3DTO = saathratriEntity3Mapper.toDto(saathratriEntity3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaathratriEntity3MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saathratriEntity3DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSaathratriEntity3() throws Exception {
        // Initialize the database
        saathratriEntity3.setCompositeId(new SaathratriEntity3Id());
        saathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        saathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        saathratriEntity3Repository.save(saathratriEntity3);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the saathratriEntity3
        restSaathratriEntity3MockMvc
            .perform(delete(ENTITY_API_URL_ID, saathratriEntity3.getCompositeId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return saathratriEntity3Repository.count();
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

    protected SaathratriEntity3 getPersistedSaathratriEntity3(SaathratriEntity3 saathratriEntity3) {
        return saathratriEntity3Repository.findById(saathratriEntity3.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedSaathratriEntity3ToMatchAllProperties(SaathratriEntity3 expectedSaathratriEntity3) {
        assertSaathratriEntity3AllPropertiesEquals(expectedSaathratriEntity3, getPersistedSaathratriEntity3(expectedSaathratriEntity3));
    }

    protected void assertPersistedSaathratriEntity3ToMatchUpdatableProperties(SaathratriEntity3 expectedSaathratriEntity3) {
        assertSaathratriEntity3AllUpdatablePropertiesEquals(
            expectedSaathratriEntity3,
            getPersistedSaathratriEntity3(expectedSaathratriEntity3)
        );
    }
}
