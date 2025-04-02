package com.saathratri.developer.blog.web.rest;

import com.saathratri.developer.blog.domain.SaathratriEntity5Id;
import com.saathratri.developer.blog.repository.SaathratriEntity5Repository;
import com.saathratri.developer.blog.service.SaathratriEntity5Service;
import com.saathratri.developer.blog.service.dto.SaathratriEntity5DTO;
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
 * REST controller for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity5}.
 */
@RestController
@RequestMapping("/api/saathratri-entity-5-s")
public class SaathratriEntity5Resource {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntity5Resource.class);

    private static final String ENTITY_NAME = "blogSaathratriEntity5";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaathratriEntity5Service saathratriEntity5Service;

    private final SaathratriEntity5Repository saathratriEntity5Repository;

    public SaathratriEntity5Resource(
        SaathratriEntity5Service saathratriEntity5Service,
        SaathratriEntity5Repository saathratriEntity5Repository
    ) {
        this.saathratriEntity5Service = saathratriEntity5Service;
        this.saathratriEntity5Repository = saathratriEntity5Repository;
    }

    /**
     * {@code POST  /saathratri-entity-5-s} : Create a new saathratriEntity5.
     *
     * @param saathratriEntity5DTO the saathratriEntity5DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saathratriEntity5DTO, or with status {@code 400 (Bad Request)} if the saathratriEntity5 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SaathratriEntity5DTO> createSaathratriEntity5(@RequestBody SaathratriEntity5DTO saathratriEntity5DTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SaathratriEntity5 : {}", saathratriEntity5DTO);

        // Composite Primary Key Code
        if (
            saathratriEntity5DTO.getCompositeId().getOrganizationId() == null ||
            saathratriEntity5DTO.getCompositeId().getEntityType() == null ||
            saathratriEntity5DTO.getCompositeId().getEntityId() == null ||
            saathratriEntity5DTO.getCompositeId().getAddOnId() == null
        ) {
            throw new BadRequestAlertException("A new saathratriEntity5 cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        saathratriEntity5DTO = saathratriEntity5Service.save(saathratriEntity5DTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/saathratri-entity-5-s/" +
                saathratriEntity5DTO.getCompositeId().getOrganizationId() +
                "/" +
                getUrlEncodedParameterValue(saathratriEntity5DTO.getCompositeId().getEntityType()) +
                "/" +
                saathratriEntity5DTO.getCompositeId().getEntityId() +
                "/" +
                saathratriEntity5DTO.getCompositeId().getAddOnId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, saathratriEntity5DTO.getCompositeId().toString())
            )
            .body(saathratriEntity5DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /saathratri-entity-5-s/:organizationId/:entityType/:entityId/:addOnId} : Updates an existing saathratriEntity5.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity5 to update.
     * @param entityType the Entity Type of the saathratriEntity5 to update.
     * @param entityId the Entity Id of the saathratriEntity5 to update.
     * @param addOnId the Add On Id of the saathratriEntity5 to update.
     * @param saathratriEntity5DTO the saathratriEntity5DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntity5DTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntity5DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntity5DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{organizationId}/{entityType}/{entityId}/{addOnId}")
    public ResponseEntity<SaathratriEntity5DTO> updateSaathratriEntity5(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "entityId", required = true) final UUID entityId,
        @PathVariable(value = "addOnId", required = true) final UUID addOnId,
        @RequestBody SaathratriEntity5DTO saathratriEntity5DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update SaathratriEntity5 with parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}, saathratriEntity5DTO: {}",
            organizationId,
            entityType,
            entityId,
            addOnId,
            saathratriEntity5DTO
        );
        // Composite Primary Key Code
        if (
            saathratriEntity5DTO.getCompositeId().getOrganizationId() == null ||
            saathratriEntity5DTO.getCompositeId().getEntityType() == null ||
            saathratriEntity5DTO.getCompositeId().getEntityId() == null ||
            saathratriEntity5DTO.getCompositeId().getAddOnId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, saathratriEntity5DTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(entityType, saathratriEntity5DTO.getCompositeId().getEntityType()) ||
            !Objects.equals(entityId, saathratriEntity5DTO.getCompositeId().getEntityId()) ||
            !Objects.equals(addOnId, saathratriEntity5DTO.getCompositeId().getAddOnId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !saathratriEntity5Repository.existsById(
                new SaathratriEntity5Id(
                    saathratriEntity5DTO.getCompositeId().getOrganizationId(),
                    saathratriEntity5DTO.getCompositeId().getEntityType(),
                    saathratriEntity5DTO.getCompositeId().getEntityId(),
                    saathratriEntity5DTO.getCompositeId().getAddOnId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        saathratriEntity5DTO = saathratriEntity5Service.update(saathratriEntity5DTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntity5DTO.getCompositeId().toString())
            )
            .body(saathratriEntity5DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /saathratri-entity-5-s/:organizationId/:entityType/:entityId/:addOnId} : Partial updates given fields of an existing saathratriEntity5, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity5 to partially update.
     * @param entityType the Entity Type of the saathratriEntity5 to partially update.
     * @param entityId the Entity Id of the saathratriEntity5 to partially update.
     * @param addOnId the Add On Id of the saathratriEntity5 to partially update.
     * @param saathratriEntity5DTO the saathratriEntity5DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntity5DTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntity5DTO is not valid,
     * or with status {@code 404 (Not Found)} if the saathratriEntity5DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntity5DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(
        value = "/{organizationId}/{entityType}/{entityId}/{addOnId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<SaathratriEntity5DTO> partialUpdateSaathratriEntity5(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "entityId", required = true) final UUID entityId,
        @PathVariable(value = "addOnId", required = true) final UUID addOnId,
        @RequestBody SaathratriEntity5DTO saathratriEntity5DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update SaathratriEntity5 with the parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}, saathratriEntity5DTO: {}",
            organizationId,
            entityType,
            entityId,
            addOnId,
            saathratriEntity5DTO
        );
        // Composite Primary Key Code
        if (
            saathratriEntity5DTO.getCompositeId().getOrganizationId() == null ||
            saathratriEntity5DTO.getCompositeId().getEntityType() == null ||
            saathratriEntity5DTO.getCompositeId().getEntityId() == null ||
            saathratriEntity5DTO.getCompositeId().getAddOnId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, saathratriEntity5DTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(entityType, saathratriEntity5DTO.getCompositeId().getEntityType()) ||
            !Objects.equals(entityId, saathratriEntity5DTO.getCompositeId().getEntityId()) ||
            !Objects.equals(addOnId, saathratriEntity5DTO.getCompositeId().getAddOnId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !saathratriEntity5Repository.existsById(
                new SaathratriEntity5Id(
                    saathratriEntity5DTO.getCompositeId().getOrganizationId(),
                    saathratriEntity5DTO.getCompositeId().getEntityType(),
                    saathratriEntity5DTO.getCompositeId().getEntityId(),
                    saathratriEntity5DTO.getCompositeId().getAddOnId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaathratriEntity5DTO> result = saathratriEntity5Service.partialUpdate(saathratriEntity5DTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntity5DTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /saathratri-entity-5-s} : get all the saathratriEntity5s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saathratriEntity5s in body.
     */
    @GetMapping("")
    public List<SaathratriEntity5DTO> getAllSaathratriEntity5s() {
        LOG.debug("REST request to get all SaathratriEntity5s");
        return saathratriEntity5Service.findAll();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET  /:organizationId/:entityType/:entityId/:addOnId} : get the "organizationId" saathratriEntity5.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity5 to retrieve.
     * @param entityType the Entity Type of the saathratriEntity5 to retrieve.
     * @param entityId the Entity Id of the saathratriEntity5 to retrieve.
     * @param addOnId the Add On Id of the saathratriEntity5 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saathratriEntity5DTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<SaathratriEntity5DTO> getSaathratriEntity5(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "entityId", required = true) final UUID entityId,
        @RequestParam(name = "addOnId", required = true) final UUID addOnId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get SaathratriEntity5 with parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}",
            organizationId,
            entityType,
            entityId,
            addOnId
        );
        // Composite Primary Key Code
        SaathratriEntity5Id compositeId = new SaathratriEntity5Id();
        compositeId.setOrganizationId(organizationId);
        compositeId.setEntityType(entityType);
        compositeId.setEntityId(entityId);
        compositeId.setAddOnId(addOnId);

        Optional<SaathratriEntity5DTO> saathratriEntity5DTO = saathratriEntity5Service.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(saathratriEntity5DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:organizationId/:entityType/:entityId/:addOnId} : delete the "compositeId" saathratriEntity5.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity5 to delete.
     * @param entityType the Entity Type of the saathratriEntity5 to delete.
     * @param entityId the Entity Id of the saathratriEntity5 to delete.
     * @param addOnId the Add On Id of the saathratriEntity5 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{organizationId}/{entityType}/{entityId}/{addOnId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteSaathratriEntity5(
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "entityId", required = true) final UUID entityId,
        @PathVariable(value = "addOnId", required = true) final UUID addOnId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete SaathratriEntity5 with parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}",
            organizationId,
            entityType,
            entityId,
            addOnId
        );
        // Composite Primary Key Code
        SaathratriEntity5Id compositeId = new SaathratriEntity5Id();
        compositeId.setOrganizationId(organizationId);
        compositeId.setEntityType(entityType);
        compositeId.setEntityId(entityId);
        compositeId.setAddOnId(addOnId);
        saathratriEntity5Service.delete(compositeId);
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity5, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id")
    public List<SaathratriEntity5DTO> findAllByCompositeIdOrganizationId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationId method for SaathratriEntity5s with parameteres organizationId: {}",
            organizationId
        );
        return saathratriEntity5Service.findAllByCompositeIdOrganizationId(organizationId);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-entity-type/:organizationId/:entityType}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param entityType the Entity Type of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity5, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-entity-type")
    public List<SaathratriEntity5DTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityType method for SaathratriEntity5s with parameteres organizationId: {}, entityType: {}",
            organizationId,
            entityType
        );
        return saathratriEntity5Service.findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(organizationId, entityType);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id/:organizationId/:entityType/:entityId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param entityType the Entity Type of the entity to retrieve.
     * @param entityId the Entity Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity5, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id")
    public List<SaathratriEntity5DTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "entityId", required = true) final UUID entityId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId method for SaathratriEntity5s with parameteres organizationId: {}, entityType: {}, entityId: {}",
            organizationId,
            entityType,
            entityId
        );
        return saathratriEntity5Service.findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
            organizationId,
            entityType,
            entityId
        );
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-and-composite-id-add-on-id/:organizationId/:entityType/:entityId/:addOnId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param entityType the Entity Type of the entity to retrieve.
     * @param entityId the Entity Id of the entity to retrieve.
     * @param addOnId the Add On Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity5, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-and-composite-id-add-on-id")
    public Optional<
        SaathratriEntity5DTO
    > findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "entityId", required = true) final UUID entityId,
        @RequestParam(name = "addOnId", required = true) final UUID addOnId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId method for SaathratriEntity5s with parameteres organizationId: {}, entityType: {}, entityId: {}, addOnId: {}",
            organizationId,
            entityType,
            entityId,
            addOnId
        );
        return saathratriEntity5Service.findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
            organizationId,
            entityType,
            entityId,
            addOnId
        );
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
