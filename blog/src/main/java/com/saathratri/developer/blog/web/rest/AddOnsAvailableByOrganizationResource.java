package com.saathratri.developer.blog.web.rest;

import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganizationId;
import com.saathratri.developer.blog.repository.AddOnsAvailableByOrganizationRepository;
import com.saathratri.developer.blog.service.AddOnsAvailableByOrganizationService;
import com.saathratri.developer.blog.service.dto.AddOnsAvailableByOrganizationDTO;
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
 * REST controller for managing {@link com.saathratri.developer.blog.domain.AddOnsAvailableByOrganization}.
 */
@RestController
@RequestMapping("/api/add-ons-available-by-organizations")
public class AddOnsAvailableByOrganizationResource {

    private static final Logger LOG = LoggerFactory.getLogger(AddOnsAvailableByOrganizationResource.class);

    private static final String ENTITY_NAME = "blogAddOnsAvailableByOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddOnsAvailableByOrganizationService addOnsAvailableByOrganizationService;

    private final AddOnsAvailableByOrganizationRepository addOnsAvailableByOrganizationRepository;

    public AddOnsAvailableByOrganizationResource(
        AddOnsAvailableByOrganizationService addOnsAvailableByOrganizationService,
        AddOnsAvailableByOrganizationRepository addOnsAvailableByOrganizationRepository
    ) {
        this.addOnsAvailableByOrganizationService = addOnsAvailableByOrganizationService;
        this.addOnsAvailableByOrganizationRepository = addOnsAvailableByOrganizationRepository;
    }

    /**
     * {@code POST  /add-ons-available-by-organizations} : Create a new addOnsAvailableByOrganization.
     *
     * @param addOnsAvailableByOrganizationDTO the addOnsAvailableByOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addOnsAvailableByOrganizationDTO, or with status {@code 400 (Bad Request)} if the addOnsAvailableByOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AddOnsAvailableByOrganizationDTO> createAddOnsAvailableByOrganization(
        @RequestBody AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save AddOnsAvailableByOrganization : {}", addOnsAvailableByOrganizationDTO);

        // Composite Primary Key Code
        if (
            addOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            addOnsAvailableByOrganizationDTO.getCompositeId().getEntityType() == null ||
            addOnsAvailableByOrganizationDTO.getCompositeId().getEntityId() == null ||
            addOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId() == null
        ) {
            throw new BadRequestAlertException("A new addOnsAvailableByOrganization cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationService.save(addOnsAvailableByOrganizationDTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/add-ons-available-by-organizations/" +
                addOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId() +
                "/" +
                getUrlEncodedParameterValue(addOnsAvailableByOrganizationDTO.getCompositeId().getEntityType()) +
                "/" +
                addOnsAvailableByOrganizationDTO.getCompositeId().getEntityId() +
                "/" +
                addOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    addOnsAvailableByOrganizationDTO.getCompositeId().toString()
                )
            )
            .body(addOnsAvailableByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /add-ons-available-by-organizations/:organizationId/:entityType/:entityId/:addOnId} : Updates an existing addOnsAvailableByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the addOnsAvailableByOrganization to update.
     * @param entityType the Entity Type of the addOnsAvailableByOrganization to update.
     * @param entityId the Entity Id of the addOnsAvailableByOrganization to update.
     * @param addOnId the Add On Id of the addOnsAvailableByOrganization to update.
     * @param addOnsAvailableByOrganizationDTO the addOnsAvailableByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addOnsAvailableByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the addOnsAvailableByOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addOnsAvailableByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{organizationId}/{entityType}/{entityId}/{addOnId}")
    public ResponseEntity<AddOnsAvailableByOrganizationDTO> updateAddOnsAvailableByOrganization(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "entityId", required = true) final UUID entityId,
        @PathVariable(value = "addOnId", required = true) final UUID addOnId,
        @RequestBody AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update AddOnsAvailableByOrganization with parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}, addOnsAvailableByOrganizationDTO: {}",
            organizationId,
            entityType,
            entityId,
            addOnId,
            addOnsAvailableByOrganizationDTO
        );
        // Composite Primary Key Code
        if (
            addOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            addOnsAvailableByOrganizationDTO.getCompositeId().getEntityType() == null ||
            addOnsAvailableByOrganizationDTO.getCompositeId().getEntityId() == null ||
            addOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, addOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(entityType, addOnsAvailableByOrganizationDTO.getCompositeId().getEntityType()) ||
            !Objects.equals(entityId, addOnsAvailableByOrganizationDTO.getCompositeId().getEntityId()) ||
            !Objects.equals(addOnId, addOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !addOnsAvailableByOrganizationRepository.existsById(
                new AddOnsAvailableByOrganizationId(
                    addOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId(),
                    addOnsAvailableByOrganizationDTO.getCompositeId().getEntityType(),
                    addOnsAvailableByOrganizationDTO.getCompositeId().getEntityId(),
                    addOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationService.update(addOnsAvailableByOrganizationDTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    addOnsAvailableByOrganizationDTO.getCompositeId().toString()
                )
            )
            .body(addOnsAvailableByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /add-ons-available-by-organizations/:organizationId/:entityType/:entityId/:addOnId} : Partial updates given fields of an existing addOnsAvailableByOrganization, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the addOnsAvailableByOrganization to partially update.
     * @param entityType the Entity Type of the addOnsAvailableByOrganization to partially update.
     * @param entityId the Entity Id of the addOnsAvailableByOrganization to partially update.
     * @param addOnId the Add On Id of the addOnsAvailableByOrganization to partially update.
     * @param addOnsAvailableByOrganizationDTO the addOnsAvailableByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addOnsAvailableByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the addOnsAvailableByOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the addOnsAvailableByOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the addOnsAvailableByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(
        value = "/{organizationId}/{entityType}/{entityId}/{addOnId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<AddOnsAvailableByOrganizationDTO> partialUpdateAddOnsAvailableByOrganization(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "entityId", required = true) final UUID entityId,
        @PathVariable(value = "addOnId", required = true) final UUID addOnId,
        @RequestBody AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update AddOnsAvailableByOrganization with the parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}, addOnsAvailableByOrganizationDTO: {}",
            organizationId,
            entityType,
            entityId,
            addOnId,
            addOnsAvailableByOrganizationDTO
        );
        // Composite Primary Key Code
        if (
            addOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            addOnsAvailableByOrganizationDTO.getCompositeId().getEntityType() == null ||
            addOnsAvailableByOrganizationDTO.getCompositeId().getEntityId() == null ||
            addOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, addOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(entityType, addOnsAvailableByOrganizationDTO.getCompositeId().getEntityType()) ||
            !Objects.equals(entityId, addOnsAvailableByOrganizationDTO.getCompositeId().getEntityId()) ||
            !Objects.equals(addOnId, addOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !addOnsAvailableByOrganizationRepository.existsById(
                new AddOnsAvailableByOrganizationId(
                    addOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId(),
                    addOnsAvailableByOrganizationDTO.getCompositeId().getEntityType(),
                    addOnsAvailableByOrganizationDTO.getCompositeId().getEntityId(),
                    addOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AddOnsAvailableByOrganizationDTO> result = addOnsAvailableByOrganizationService.partialUpdate(
            addOnsAvailableByOrganizationDTO
        );

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                addOnsAvailableByOrganizationDTO.getCompositeId().toString()
            )
        );
    }

    /**
     * {@code GET  /add-ons-available-by-organizations} : get all the addOnsAvailableByOrganizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addOnsAvailableByOrganizations in body.
     */
    @GetMapping("")
    public List<AddOnsAvailableByOrganizationDTO> getAllAddOnsAvailableByOrganizations() {
        LOG.debug("REST request to get all AddOnsAvailableByOrganizations");
        return addOnsAvailableByOrganizationService.findAll();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET  /:organizationId/:entityType/:entityId/:addOnId} : get the "organizationId" addOnsAvailableByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the addOnsAvailableByOrganization to retrieve.
     * @param entityType the Entity Type of the addOnsAvailableByOrganization to retrieve.
     * @param entityId the Entity Id of the addOnsAvailableByOrganization to retrieve.
     * @param addOnId the Add On Id of the addOnsAvailableByOrganization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addOnsAvailableByOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<AddOnsAvailableByOrganizationDTO> getAddOnsAvailableByOrganization(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "entityId", required = true) final UUID entityId,
        @RequestParam(name = "addOnId", required = true) final UUID addOnId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get AddOnsAvailableByOrganization with parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}",
            organizationId,
            entityType,
            entityId,
            addOnId
        );
        // Composite Primary Key Code
        AddOnsAvailableByOrganizationId compositeId = new AddOnsAvailableByOrganizationId();
        compositeId.setOrganizationId(organizationId);
        compositeId.setEntityType(entityType);
        compositeId.setEntityId(entityId);
        compositeId.setAddOnId(addOnId);

        Optional<AddOnsAvailableByOrganizationDTO> addOnsAvailableByOrganizationDTO = addOnsAvailableByOrganizationService.findOne(
            compositeId
        );
        return ResponseUtil.wrapOrNotFound(addOnsAvailableByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:organizationId/:entityType/:entityId/:addOnId} : delete the "compositeId" addOnsAvailableByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the addOnsAvailableByOrganization to delete.
     * @param entityType the Entity Type of the addOnsAvailableByOrganization to delete.
     * @param entityId the Entity Id of the addOnsAvailableByOrganization to delete.
     * @param addOnId the Add On Id of the addOnsAvailableByOrganization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{organizationId}/{entityType}/{entityId}/{addOnId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteAddOnsAvailableByOrganization(
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "entityId", required = true) final UUID entityId,
        @PathVariable(value = "addOnId", required = true) final UUID addOnId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete AddOnsAvailableByOrganization with parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}",
            organizationId,
            entityType,
            entityId,
            addOnId
        );
        // Composite Primary Key Code
        AddOnsAvailableByOrganizationId compositeId = new AddOnsAvailableByOrganizationId();
        compositeId.setOrganizationId(organizationId);
        compositeId.setEntityType(entityType);
        compositeId.setEntityId(entityId);
        compositeId.setAddOnId(addOnId);
        addOnsAvailableByOrganizationService.delete(compositeId);
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsAvailableByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id")
    public List<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationId method for AddOnsAvailableByOrganizations with parameteres organizationId: {}",
            organizationId
        );
        return addOnsAvailableByOrganizationService.findAllByCompositeIdOrganizationId(organizationId);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-entity-type/:organizationId/:entityType}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param entityType the Entity Type of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsAvailableByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-entity-type")
    public List<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityType method for AddOnsAvailableByOrganizations with parameteres organizationId: {}, entityType: {}",
            organizationId,
            entityType
        );
        return addOnsAvailableByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(organizationId, entityType);
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsAvailableByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id")
    public List<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "entityId", required = true) final UUID entityId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId method for AddOnsAvailableByOrganizations with parameteres organizationId: {}, entityType: {}, entityId: {}",
            organizationId,
            entityType,
            entityId
        );
        return addOnsAvailableByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsAvailableByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-and-composite-id-add-on-id")
    public Optional<
        AddOnsAvailableByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "entityId", required = true) final UUID entityId,
        @RequestParam(name = "addOnId", required = true) final UUID addOnId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId method for AddOnsAvailableByOrganizations with parameteres organizationId: {}, entityType: {}, entityId: {}, addOnId: {}",
            organizationId,
            entityType,
            entityId,
            addOnId
        );
        return addOnsAvailableByOrganizationService.findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
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
