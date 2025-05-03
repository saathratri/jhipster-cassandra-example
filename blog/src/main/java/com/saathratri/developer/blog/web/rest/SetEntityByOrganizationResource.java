package com.saathratri.developer.blog.web.rest;

import com.saathratri.developer.blog.repository.SetEntityByOrganizationRepository;
import com.saathratri.developer.blog.service.SetEntityByOrganizationService;
import com.saathratri.developer.blog.service.dto.SetEntityByOrganizationDTO;
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
 * REST controller for managing {@link com.saathratri.developer.blog.domain.SetEntityByOrganization}.
 */
@RestController
@RequestMapping("/api/set-entity-by-organizations")
public class SetEntityByOrganizationResource {

    private static final Logger LOG = LoggerFactory.getLogger(SetEntityByOrganizationResource.class);

    private static final String ENTITY_NAME = "blogSetEntityByOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SetEntityByOrganizationService setEntityByOrganizationService;

    private final SetEntityByOrganizationRepository setEntityByOrganizationRepository;

    public SetEntityByOrganizationResource(
        SetEntityByOrganizationService setEntityByOrganizationService,
        SetEntityByOrganizationRepository setEntityByOrganizationRepository
    ) {
        this.setEntityByOrganizationService = setEntityByOrganizationService;
        this.setEntityByOrganizationRepository = setEntityByOrganizationRepository;
    }

    /**
     * {@code POST  /set-entity-by-organizations} : Create a new setEntityByOrganization.
     *
     * @param setEntityByOrganizationDTO the setEntityByOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new setEntityByOrganizationDTO, or with status {@code 400 (Bad Request)} if the setEntityByOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SetEntityByOrganizationDTO> createSetEntityByOrganization(
        @RequestBody SetEntityByOrganizationDTO setEntityByOrganizationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save SetEntityByOrganization : {}", setEntityByOrganizationDTO);

        // Single-value Primary Key Code
        if (setEntityByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("A new setEntityByOrganization must have an ID", ENTITY_NAME, "idinvalid");
        }

        setEntityByOrganizationDTO = setEntityByOrganizationService.save(setEntityByOrganizationDTO);
        // Single-value Primary Key Code
        return ResponseEntity.created(new URI("/api/set-entity-by-organizations/" + setEntityByOrganizationDTO.getOrganizationId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    setEntityByOrganizationDTO.getOrganizationId().toString()
                )
            )
            .body(setEntityByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PUT  /set-entity-by-organizations/:organizationId} : Updates an existing setEntityByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the setEntityByOrganizationDTO to save.
     * @param setEntityByOrganizationDTO the setEntityByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setEntityByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the setEntityByOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the setEntityByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PutMapping("/{organizationId}")
    public ResponseEntity<SetEntityByOrganizationDTO> updateSetEntityByOrganization(
        // Single-value Primary Key Code
        @PathVariable(value = "organizationId", required = false) final UUID organizationId,
        @RequestBody SetEntityByOrganizationDTO setEntityByOrganizationDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to update SetEntityByOrganization : {}, {}", organizationId, setEntityByOrganizationDTO);
        // Single-value Primary Key Code
        if (setEntityByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(organizationId, setEntityByOrganizationDTO.getOrganizationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!setEntityByOrganizationRepository.existsById(organizationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        setEntityByOrganizationDTO = setEntityByOrganizationService.update(setEntityByOrganizationDTO);
        // Single-value Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    setEntityByOrganizationDTO.getOrganizationId().toString()
                )
            )
            .body(setEntityByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PATCH  /set-entity-by-organizations/:organizationId} : Partial updates given fields of an existing setEntityByOrganization, field will ignore if it is null
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the setEntityByOrganizationDTO to save.
     * @param setEntityByOrganizationDTO the setEntityByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setEntityByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the setEntityByOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the setEntityByOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the setEntityByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PatchMapping(value = "/{organizationId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SetEntityByOrganizationDTO> partialUpdateSetEntityByOrganization(
        // Single-value Primary Key Code
        @PathVariable(value = "organizationId", required = false) final UUID organizationId,
        @RequestBody SetEntityByOrganizationDTO setEntityByOrganizationDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to partial update SetEntityByOrganization partially : {}, {}", organizationId, setEntityByOrganizationDTO);
        // Single-value Primary Key Code
        if (setEntityByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(organizationId, setEntityByOrganizationDTO.getOrganizationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!setEntityByOrganizationRepository.existsById(organizationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SetEntityByOrganizationDTO> result = setEntityByOrganizationService.partialUpdate(setEntityByOrganizationDTO);

        // Single-value Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                setEntityByOrganizationDTO.getOrganizationId().toString()
            )
        );
    }

    /**
     * {@code GET  /set-entity-by-organizations} : get all the setEntityByOrganizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of setEntityByOrganizations in body.
     */
    @GetMapping("")
    public List<SetEntityByOrganizationDTO> getAllSetEntityByOrganizations() {
        LOG.debug("REST request to get all SetEntityByOrganizations");
        return setEntityByOrganizationService.findAll();
    }

    /**
     * // Single-value Primary Key Code
     * {@code GET  /:organizationId} : get the "organizationId" setEntityByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the setEntityByOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the setEntityByOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    // Single-value Primary Key Code
    @GetMapping("/{organizationId}")
    // Single-value Primary Key Code
    public ResponseEntity<SetEntityByOrganizationDTO> getSetEntityByOrganization(@PathVariable("organizationId") UUID organizationId) {
        // Single-value Primary Key Code
        LOG.debug("REST request to get SetEntityByOrganization : {}", organizationId);

        Optional<SetEntityByOrganizationDTO> setEntityByOrganizationDTO = setEntityByOrganizationService.findOne(organizationId);
        return ResponseUtil.wrapOrNotFound(setEntityByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code DELETE  /:organizationId} : delete the "organizationId" setEntityByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the setEntityByOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Single-value Primary Key Code
    @DeleteMapping("/{organizationId}")
    // Single-value Primary Key Code
    public ResponseEntity<Void> deleteSetEntityByOrganization(@PathVariable("organizationId") UUID organizationId) {
        // Single-value Primary Key Code
        LOG.debug("REST request to delete SetEntityByOrganization : {}", organizationId);
        setEntityByOrganizationService.delete(organizationId);
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
