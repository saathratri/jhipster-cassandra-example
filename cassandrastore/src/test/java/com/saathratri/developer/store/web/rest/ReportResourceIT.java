package com.saathratri.developer.store.web.rest;

import static com.saathratri.developer.store.domain.ReportAsserts.*;
import static com.saathratri.developer.store.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.store.IntegrationTest;
import com.saathratri.developer.store.domain.Report;
import com.saathratri.developer.store.repository.ReportRepository;
import com.saathratri.developer.store.service.dto.ReportDTO;
import com.saathratri.developer.store.service.mapper.ReportMapper;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReportResourceIT {

    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final UUID UPDATED_ID = UUID.randomUUID();

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_EXTENSION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATE_DATE = 1L;
    private static final Long UPDATED_CREATE_DATE = 2L;

    private static final ByteBuffer DEFAULT_FILE = ByteBuffer.wrap(TestUtil.createByteArray(1, "0"));
    private static final ByteBuffer UPDATED_FILE = ByteBuffer.wrap(TestUtil.createByteArray(1, "1"));
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final String ENTITY_API_URL = "/api/reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private MockMvc restReportMockMvc;

    private Report report;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Report createEntity() {
        Report report = new Report()
            .id(DEFAULT_ID)
            .fileName(DEFAULT_FILE_NAME)
            .fileExtension(DEFAULT_FILE_EXTENSION)
            .createDate(DEFAULT_CREATE_DATE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .approved(DEFAULT_APPROVED);
        return report;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Report createUpdatedEntity() {
        Report report = new Report()
            .id(UPDATED_ID)
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);
        return report;
    }

    @BeforeEach
    public void initTest() {
        reportRepository.deleteAll();
        report = createEntity();
    }

    @Test
    void createReport() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);
        var returnedReportDTO = om.readValue(
            restReportMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ReportDTO.class
        );

        // Validate the Report in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedReport = reportMapper.toEntity(returnedReportDTO);
        assertReportUpdatableFieldsEquals(returnedReport, getPersistedReport(returnedReport));
    }

    @Test
    void createReportWithExistingId() throws Exception {
        // Create the Report with an existing ID
        report.setId(UUID.randomUUID());
        ReportDTO reportDTO = reportMapper.toDto(report);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkFileNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        report.setFileName(null);

        // Create the Report, which fails.
        ReportDTO reportDTO = reportMapper.toDto(report);

        restReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFileExtensionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        report.setFileExtension(null);

        // Create the Report, which fails.
        ReportDTO reportDTO = reportMapper.toDto(report);

        restReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCreateDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        report.setCreateDate(null);

        // Create the Report, which fails.
        ReportDTO reportDTO = reportMapper.toDto(report);

        restReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllReports() throws Exception {
        // Initialize the database
        report.setId(UUID.randomUUID());
        reportRepository.save(report);

        // Get all the reportList
        restReportMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileExtension").value(hasItem(DEFAULT_FILE_EXTENSION)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FILE.array()))))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())));
    }

    @Test
    void getReport() throws Exception {
        // Initialize the database
        report.setId(UUID.randomUUID());
        reportRepository.save(report);

        // Get the report
        restReportMockMvc
            .perform(get(ENTITY_API_URL_ID, report.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileExtension").value(hasItem(DEFAULT_FILE_EXTENSION)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FILE.array()))))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())));
    }

    @Test
    void getNonExistingReport() throws Exception {
        // Get the report
        restReportMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingReport() throws Exception {
        // Initialize the database
        report.setId(UUID.randomUUID());
        reportRepository.save(report);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the report
        Report updatedReport = reportRepository.findById(report.getId()).orElseThrow();
        updatedReport
            .id(UPDATED_ID)
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);
        ReportDTO reportDTO = reportMapper.toDto(updatedReport);

        restReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reportDTO))
            )
            .andExpect(status().isOk());

        // Validate the Report in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReportToMatchAllProperties(updatedReport);
    }

    @Test
    void putNonExistingReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        report.setId(UUID.randomUUID());

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        report.setId(UUID.randomUUID());
        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        report.setId(UUID.randomUUID());

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Report in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateReportWithPatch() throws Exception {
        // Initialize the database
        report.setId(UUID.randomUUID());
        reportRepository.save(report);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the report using partial update
        Report partialUpdatedReport = new Report();
        partialUpdatedReport.setId(report.getId());

        partialUpdatedReport
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);

        restReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReport))
            )
            .andExpect(status().isOk());

        // Validate the Report in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReportUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedReport, report), getPersistedReport(report));
    }

    @Test
    void fullUpdateReportWithPatch() throws Exception {
        // Initialize the database
        report.setId(UUID.randomUUID());
        reportRepository.save(report);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the report using partial update
        Report partialUpdatedReport = new Report();
        partialUpdatedReport.setId(report.getId());

        partialUpdatedReport
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);

        restReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReport))
            )
            .andExpect(status().isOk());

        // Validate the Report in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReportUpdatableFieldsEquals(partialUpdatedReport, getPersistedReport(partialUpdatedReport));
    }

    @Test
    void patchNonExistingReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        report.setId(UUID.randomUUID());

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reportDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        report.setId(UUID.randomUUID());

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        report.setId(UUID.randomUUID());

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(reportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Report in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteReport() throws Exception {
        // Initialize the database
        report.setId(UUID.randomUUID());
        reportRepository.save(report);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the report
        restReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, report.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return reportRepository.count();
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

    protected Report getPersistedReport(Report report) {
        return reportRepository.findById(report.getId()).orElseThrow();
    }

    protected void assertPersistedReportToMatchAllProperties(Report expectedReport) {
        assertReportAllPropertiesEquals(expectedReport, getPersistedReport(expectedReport));
    }

    protected void assertPersistedReportToMatchUpdatableProperties(Report expectedReport) {
        assertReportAllUpdatablePropertiesEquals(expectedReport, getPersistedReport(expectedReport));
    }
}
