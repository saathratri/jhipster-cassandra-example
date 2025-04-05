package com.saathratri.developer.blog.web.rest;

import com.saathratri.developer.blog.repository.LandingPageByOrganizationRepository;
import com.saathratri.developer.blog.service.LandingPageByOrganizationService;
import com.saathratri.developer.blog.service.dto.LandingPageByOrganizationDTO;
import com.saathratri.developer.blog.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.saathratri.developer.blog.domain.LandingPageByOrganization}.
 */
@RestController
@RequestMapping("/api/landing-page-by-organizations")
public class LandingPageByOrganizationResource {

    private static final Logger LOG = LoggerFactory.getLogger(LandingPageByOrganizationResource.class);

    private static final String ENTITY_NAME = "blogLandingPageByOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LandingPageByOrganizationService landingPageByOrganizationService;

    private final LandingPageByOrganizationRepository landingPageByOrganizationRepository;

    public LandingPageByOrganizationResource(
        LandingPageByOrganizationService landingPageByOrganizationService,
        LandingPageByOrganizationRepository landingPageByOrganizationRepository
    ) {
        this.landingPageByOrganizationService = landingPageByOrganizationService;
        this.landingPageByOrganizationRepository = landingPageByOrganizationRepository;
    }

    /**
     * {@code POST  /landing-page-by-organizations} : Create a new landingPageByOrganization.
     *
     * @param landingPageByOrganizationDTO the landingPageByOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new landingPageByOrganizationDTO, or with status {@code 400 (Bad Request)} if the landingPageByOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LandingPageByOrganizationDTO> createLandingPageByOrganization(
        @RequestBody LandingPageByOrganizationDTO landingPageByOrganizationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save LandingPageByOrganization : {}", landingPageByOrganizationDTO);

        // Single-value Primary Key Code
        if (landingPageByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("A new landingPageByOrganization must have an ID", ENTITY_NAME, "idinvalid");
        }

        landingPageByOrganizationDTO = landingPageByOrganizationService.save(landingPageByOrganizationDTO);
        // Single-value Primary Key Code
        return ResponseEntity.created(new URI("/api/landing-page-by-organizations/" + landingPageByOrganizationDTO.getOrganizationId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    landingPageByOrganizationDTO.getOrganizationId().toString()
                )
            )
            .body(landingPageByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PUT  /landing-page-by-organizations/:organizationId} : Updates an existing landingPageByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the landingPageByOrganizationDTO to save.
     * @param landingPageByOrganizationDTO the landingPageByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landingPageByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the landingPageByOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the landingPageByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PutMapping("/{organizationId}")
    public ResponseEntity<LandingPageByOrganizationDTO> updateLandingPageByOrganization(
        // Single-value Primary Key Code
        @PathVariable(value = "organizationId", required = false) final UUID organizationId,
        @RequestBody LandingPageByOrganizationDTO landingPageByOrganizationDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to update LandingPageByOrganization : {}, {}", organizationId, landingPageByOrganizationDTO);
        // Single-value Primary Key Code
        if (landingPageByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(organizationId, landingPageByOrganizationDTO.getOrganizationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!landingPageByOrganizationRepository.existsById(organizationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        landingPageByOrganizationDTO = landingPageByOrganizationService.update(landingPageByOrganizationDTO);
        // Single-value Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    landingPageByOrganizationDTO.getOrganizationId().toString()
                )
            )
            .body(landingPageByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PATCH  /landing-page-by-organizations/:organizationId} : Partial updates given fields of an existing landingPageByOrganization, field will ignore if it is null
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the landingPageByOrganizationDTO to save.
     * @param landingPageByOrganizationDTO the landingPageByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landingPageByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the landingPageByOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the landingPageByOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the landingPageByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PatchMapping(value = "/{organizationId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LandingPageByOrganizationDTO> partialUpdateLandingPageByOrganization(
        // Single-value Primary Key Code
        @PathVariable(value = "organizationId", required = false) final UUID organizationId,
        @RequestBody LandingPageByOrganizationDTO landingPageByOrganizationDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug(
            "REST request to partial update LandingPageByOrganization partially : {}, {}",
            organizationId,
            landingPageByOrganizationDTO
        );
        // Single-value Primary Key Code
        if (landingPageByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(organizationId, landingPageByOrganizationDTO.getOrganizationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!landingPageByOrganizationRepository.existsById(organizationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LandingPageByOrganizationDTO> result = landingPageByOrganizationService.partialUpdate(landingPageByOrganizationDTO);

        // Single-value Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                landingPageByOrganizationDTO.getOrganizationId().toString()
            )
        );
    }

    /**
     * {@code GET  /landing-page-by-organizations} : get all the landingPageByOrganizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of landingPageByOrganizations in body.
     */
    @GetMapping("")
    public List<LandingPageByOrganizationDTO> getAllLandingPageByOrganizations() {
        LOG.debug("REST request to get all LandingPageByOrganizations");
        return landingPageByOrganizationService.findAll();
    }

    /**
     * // Single-value Primary Key Code
     * {@code GET  /:organizationId} : get the "organizationId" landingPageByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the landingPageByOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the landingPageByOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    // Single-value Primary Key Code
    @GetMapping("/{organizationId}")
    // Single-value Primary Key Code
    public ResponseEntity<LandingPageByOrganizationDTO> getLandingPageByOrganization(@PathVariable("organizationId") UUID organizationId) {
        // Single-value Primary Key Code
        LOG.debug("REST request to get LandingPageByOrganization : {}", organizationId);

        Optional<LandingPageByOrganizationDTO> landingPageByOrganizationDTO = landingPageByOrganizationService.findOne(organizationId);
        return ResponseUtil.wrapOrNotFound(landingPageByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code DELETE  /:organizationId} : delete the "organizationId" landingPageByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the landingPageByOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Single-value Primary Key Code
    @DeleteMapping("/{organizationId}")
    // Single-value Primary Key Code
    public ResponseEntity<Void> deleteLandingPageByOrganization(@PathVariable("organizationId") UUID organizationId) {
        // Single-value Primary Key Code
        LOG.debug("REST request to delete LandingPageByOrganization : {}", organizationId);
        landingPageByOrganizationService.delete(organizationId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, organizationId.toString()))
            .build();
    }

    private String getUrlEncodedParameterValue(String parameterValue) {
        String encodedValue = null;
        try {
            encodedValue = URLEncoder.encode(parameterValue, StandardCharsets.UTF_8);
            LOG.info("Encoded String '{}' is '{}'.", parameterValue, encodedValue);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return encodedValue;
    }
}
