package com.saathratri.developer.blog.web.rest;

import com.saathratri.developer.blog.domain.SaathratriEntity4Id;
import com.saathratri.developer.blog.repository.SaathratriEntity4Repository;
import com.saathratri.developer.blog.service.SaathratriEntity4Service;
import com.saathratri.developer.blog.service.dto.SaathratriEntity4DTO;
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
 * REST controller for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity4}.
 */
@RestController
@RequestMapping("/api/saathratri-entity-4-s")
public class SaathratriEntity4Resource {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntity4Resource.class);

    private static final String ENTITY_NAME = "blogSaathratriEntity4";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaathratriEntity4Service saathratriEntity4Service;

    private final SaathratriEntity4Repository saathratriEntity4Repository;

    public SaathratriEntity4Resource(
        SaathratriEntity4Service saathratriEntity4Service,
        SaathratriEntity4Repository saathratriEntity4Repository
    ) {
        this.saathratriEntity4Service = saathratriEntity4Service;
        this.saathratriEntity4Repository = saathratriEntity4Repository;
    }

    /**
     * {@code POST  /saathratri-entity-4-s} : Create a new saathratriEntity4.
     *
     * @param saathratriEntity4DTO the saathratriEntity4DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saathratriEntity4DTO, or with status {@code 400 (Bad Request)} if the saathratriEntity4 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SaathratriEntity4DTO> createSaathratriEntity4(@RequestBody SaathratriEntity4DTO saathratriEntity4DTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SaathratriEntity4 : {}", saathratriEntity4DTO);

        // Composite Primary Key Code
        if (
            saathratriEntity4DTO.getCompositeId().getOrganizationId() == null ||
            saathratriEntity4DTO.getCompositeId().getAttributeKey() == null
        ) {
            throw new BadRequestAlertException("A new saathratriEntity4 cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        saathratriEntity4DTO = saathratriEntity4Service.save(saathratriEntity4DTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/saathratri-entity-4-s/" +
                saathratriEntity4DTO.getCompositeId().getOrganizationId() +
                "/" +
                getUrlEncodedParameterValue(saathratriEntity4DTO.getCompositeId().getAttributeKey())
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, saathratriEntity4DTO.getCompositeId().toString())
            )
            .body(saathratriEntity4DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /saathratri-entity-4-s/:organizationId/:attributeKey} : Updates an existing saathratriEntity4.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity4 to update.
     * @param attributeKey the Attribute Key of the saathratriEntity4 to update.
     * @param saathratriEntity4DTO the saathratriEntity4DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntity4DTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntity4DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntity4DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{organizationId}/{attributeKey}")
    public ResponseEntity<SaathratriEntity4DTO> updateSaathratriEntity4(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "attributeKey", required = true) final String attributeKey,
        @RequestBody SaathratriEntity4DTO saathratriEntity4DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update SaathratriEntity4 with parameters organizationId: {}, attributeKey: {}, saathratriEntity4DTO: {}",
            organizationId,
            attributeKey,
            saathratriEntity4DTO
        );
        // Composite Primary Key Code
        if (
            saathratriEntity4DTO.getCompositeId().getOrganizationId() == null ||
            saathratriEntity4DTO.getCompositeId().getAttributeKey() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, saathratriEntity4DTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(attributeKey, saathratriEntity4DTO.getCompositeId().getAttributeKey())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !saathratriEntity4Repository.existsById(
                new SaathratriEntity4Id(
                    saathratriEntity4DTO.getCompositeId().getOrganizationId(),
                    saathratriEntity4DTO.getCompositeId().getAttributeKey()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        saathratriEntity4DTO = saathratriEntity4Service.update(saathratriEntity4DTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntity4DTO.getCompositeId().toString())
            )
            .body(saathratriEntity4DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /saathratri-entity-4-s/:organizationId/:attributeKey} : Partial updates given fields of an existing saathratriEntity4, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity4 to partially update.
     * @param attributeKey the Attribute Key of the saathratriEntity4 to partially update.
     * @param saathratriEntity4DTO the saathratriEntity4DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntity4DTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntity4DTO is not valid,
     * or with status {@code 404 (Not Found)} if the saathratriEntity4DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntity4DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(value = "/{organizationId}/{attributeKey}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaathratriEntity4DTO> partialUpdateSaathratriEntity4(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "attributeKey", required = true) final String attributeKey,
        @RequestBody SaathratriEntity4DTO saathratriEntity4DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update SaathratriEntity4 with the parameters organizationId: {}, attributeKey: {}, saathratriEntity4DTO: {}",
            organizationId,
            attributeKey,
            saathratriEntity4DTO
        );
        // Composite Primary Key Code
        if (
            saathratriEntity4DTO.getCompositeId().getOrganizationId() == null ||
            saathratriEntity4DTO.getCompositeId().getAttributeKey() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, saathratriEntity4DTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(attributeKey, saathratriEntity4DTO.getCompositeId().getAttributeKey())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !saathratriEntity4Repository.existsById(
                new SaathratriEntity4Id(
                    saathratriEntity4DTO.getCompositeId().getOrganizationId(),
                    saathratriEntity4DTO.getCompositeId().getAttributeKey()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaathratriEntity4DTO> result = saathratriEntity4Service.partialUpdate(saathratriEntity4DTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntity4DTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /saathratri-entity-4-s} : get all the saathratriEntity4s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saathratriEntity4s in body.
     */
    @GetMapping("")
    public List<SaathratriEntity4DTO> getAllSaathratriEntity4s() {
        LOG.debug("REST request to get all SaathratriEntity4s");
        return saathratriEntity4Service.findAll();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET  /:organizationId/:attributeKey} : get the "organizationId" saathratriEntity4.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity4 to retrieve.
     * @param attributeKey the Attribute Key of the saathratriEntity4 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saathratriEntity4DTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<SaathratriEntity4DTO> getSaathratriEntity4(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "attributeKey", required = true) final String attributeKey
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get SaathratriEntity4 with parameters organizationId: {}, attributeKey: {}",
            organizationId,
            attributeKey
        );
        // Composite Primary Key Code
        SaathratriEntity4Id compositeId = new SaathratriEntity4Id();
        compositeId.setOrganizationId(organizationId);
        compositeId.setAttributeKey(attributeKey);

        Optional<SaathratriEntity4DTO> saathratriEntity4DTO = saathratriEntity4Service.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(saathratriEntity4DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:organizationId/:attributeKey} : delete the "compositeId" saathratriEntity4.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity4 to delete.
     * @param attributeKey the Attribute Key of the saathratriEntity4 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{organizationId}/{attributeKey}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteSaathratriEntity4(
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "attributeKey", required = true) final String attributeKey
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete SaathratriEntity4 with parameters organizationId: {}, attributeKey: {}",
            organizationId,
            attributeKey
        );
        // Composite Primary Key Code
        SaathratriEntity4Id compositeId = new SaathratriEntity4Id();
        compositeId.setOrganizationId(organizationId);
        compositeId.setAttributeKey(attributeKey);
        saathratriEntity4Service.delete(compositeId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, organizationId.toString()))
            .build();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-organization-id/:organizationId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity4, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id")
    public List<SaathratriEntity4DTO> findAllByCompositeIdOrganizationId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationId method for SaathratriEntity4s with parameteres organizationId: {}",
            organizationId
        );
        return saathratriEntity4Service.findAllByCompositeIdOrganizationId(organizationId);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-by-composite-id-organization-id-and-composite-id-attribute-key/:organizationId/:attributeKey}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param attributeKey the Attribute Key of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity4, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-composite-id-organization-id-and-composite-id-attribute-key")
    public Optional<SaathratriEntity4DTO> findByCompositeIdOrganizationIdAndCompositeIdAttributeKey(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "attributeKey", required = true) final String attributeKey
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdOrganizationIdAndCompositeIdAttributeKey method for SaathratriEntity4s with parameteres organizationId: {}, attributeKey: {}",
            organizationId,
            attributeKey
        );
        return saathratriEntity4Service.findByCompositeIdOrganizationIdAndCompositeIdAttributeKey(organizationId, attributeKey);
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
