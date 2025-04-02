package com.saathratri.developer.blog.web.rest;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.saathratri.developer.blog.domain.SaathratriEntity2Id;
import com.saathratri.developer.blog.repository.SaathratriEntity2Repository;
import com.saathratri.developer.blog.service.SaathratriEntity2Service;
import com.saathratri.developer.blog.service.dto.SaathratriEntity2DTO;
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
 * REST controller for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity2}.
 */
@RestController
@RequestMapping("/api/saathratri-entity-2-s")
public class SaathratriEntity2Resource {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntity2Resource.class);

    private static final String ENTITY_NAME = "blogSaathratriEntity2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaathratriEntity2Service saathratriEntity2Service;

    private final SaathratriEntity2Repository saathratriEntity2Repository;

    public SaathratriEntity2Resource(
        SaathratriEntity2Service saathratriEntity2Service,
        SaathratriEntity2Repository saathratriEntity2Repository
    ) {
        this.saathratriEntity2Service = saathratriEntity2Service;
        this.saathratriEntity2Repository = saathratriEntity2Repository;
    }

    /**
     * {@code POST  /saathratri-entity-2-s} : Create a new saathratriEntity2.
     *
     * @param saathratriEntity2DTO the saathratriEntity2DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saathratriEntity2DTO, or with status {@code 400 (Bad Request)} if the saathratriEntity2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SaathratriEntity2DTO> createSaathratriEntity2(@RequestBody SaathratriEntity2DTO saathratriEntity2DTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SaathratriEntity2 : {}", saathratriEntity2DTO);

        // Composite Primary Key Code

        // Generate a TimeUUID for the Primary Key composite fields.

        saathratriEntity2DTO.getCompositeId().setBlogId(Uuids.timeBased());

        if (
            saathratriEntity2DTO.getCompositeId().getEntityTypeId() == null ||
            saathratriEntity2DTO.getCompositeId().getYearOfDateAdded() == null ||
            saathratriEntity2DTO.getCompositeId().getArrivalDate() == null ||
            saathratriEntity2DTO.getCompositeId().getBlogId() == null
        ) {
            throw new BadRequestAlertException("A new saathratriEntity2 cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        saathratriEntity2DTO = saathratriEntity2Service.save(saathratriEntity2DTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/saathratri-entity-2-s/" +
                saathratriEntity2DTO.getCompositeId().getEntityTypeId() +
                "/" +
                saathratriEntity2DTO.getCompositeId().getYearOfDateAdded() +
                "/" +
                saathratriEntity2DTO.getCompositeId().getArrivalDate() +
                "/" +
                saathratriEntity2DTO.getCompositeId().getBlogId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, saathratriEntity2DTO.getCompositeId().toString())
            )
            .body(saathratriEntity2DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /saathratri-entity-2-s/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : Updates an existing saathratriEntity2.
     *
     * // Composite Primary Key Code
     * @param entityTypeId the Entity Type Id of the saathratriEntity2 to update.
     * @param yearOfDateAdded the Year Of Date Added of the saathratriEntity2 to update.
     * @param arrivalDate the Arrival Date of the saathratriEntity2 to update.
     * @param blogId the Blog Id of the saathratriEntity2 to update.
     * @param saathratriEntity2DTO the saathratriEntity2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntity2DTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntity2DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntity2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}")
    public ResponseEntity<SaathratriEntity2DTO> updateSaathratriEntity2(
        // Composite Primary Key Code
        @PathVariable(value = "entityTypeId", required = true) final UUID entityTypeId,
        @PathVariable(value = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "blogId", required = true) final UUID blogId,
        @RequestBody SaathratriEntity2DTO saathratriEntity2DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update SaathratriEntity2 with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}, saathratriEntity2DTO: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId,
            saathratriEntity2DTO
        );
        // Composite Primary Key Code
        if (
            saathratriEntity2DTO.getCompositeId().getEntityTypeId() == null ||
            saathratriEntity2DTO.getCompositeId().getYearOfDateAdded() == null ||
            saathratriEntity2DTO.getCompositeId().getArrivalDate() == null ||
            saathratriEntity2DTO.getCompositeId().getBlogId() == null
        ) {
            throw new BadRequestAlertException("Invalid entityTypeId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(entityTypeId, saathratriEntity2DTO.getCompositeId().getEntityTypeId()) ||
            !Objects.equals(yearOfDateAdded, saathratriEntity2DTO.getCompositeId().getYearOfDateAdded()) ||
            !Objects.equals(arrivalDate, saathratriEntity2DTO.getCompositeId().getArrivalDate()) ||
            !Objects.equals(blogId, saathratriEntity2DTO.getCompositeId().getBlogId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !saathratriEntity2Repository.existsById(
                new SaathratriEntity2Id(
                    saathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    saathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    saathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    saathratriEntity2DTO.getCompositeId().getBlogId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        saathratriEntity2DTO = saathratriEntity2Service.update(saathratriEntity2DTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntity2DTO.getCompositeId().toString())
            )
            .body(saathratriEntity2DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /saathratri-entity-2-s/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : Partial updates given fields of an existing saathratriEntity2, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param entityTypeId the Entity Type Id of the saathratriEntity2 to partially update.
     * @param yearOfDateAdded the Year Of Date Added of the saathratriEntity2 to partially update.
     * @param arrivalDate the Arrival Date of the saathratriEntity2 to partially update.
     * @param blogId the Blog Id of the saathratriEntity2 to partially update.
     * @param saathratriEntity2DTO the saathratriEntity2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntity2DTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntity2DTO is not valid,
     * or with status {@code 404 (Not Found)} if the saathratriEntity2DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntity2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(
        value = "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<SaathratriEntity2DTO> partialUpdateSaathratriEntity2(
        // Composite Primary Key Code
        @PathVariable(value = "entityTypeId", required = true) final UUID entityTypeId,
        @PathVariable(value = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "blogId", required = true) final UUID blogId,
        @RequestBody SaathratriEntity2DTO saathratriEntity2DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update SaathratriEntity2 with the parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}, saathratriEntity2DTO: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId,
            saathratriEntity2DTO
        );
        // Composite Primary Key Code
        if (
            saathratriEntity2DTO.getCompositeId().getEntityTypeId() == null ||
            saathratriEntity2DTO.getCompositeId().getYearOfDateAdded() == null ||
            saathratriEntity2DTO.getCompositeId().getArrivalDate() == null ||
            saathratriEntity2DTO.getCompositeId().getBlogId() == null
        ) {
            throw new BadRequestAlertException("Invalid entityTypeId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(entityTypeId, saathratriEntity2DTO.getCompositeId().getEntityTypeId()) ||
            !Objects.equals(yearOfDateAdded, saathratriEntity2DTO.getCompositeId().getYearOfDateAdded()) ||
            !Objects.equals(arrivalDate, saathratriEntity2DTO.getCompositeId().getArrivalDate()) ||
            !Objects.equals(blogId, saathratriEntity2DTO.getCompositeId().getBlogId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !saathratriEntity2Repository.existsById(
                new SaathratriEntity2Id(
                    saathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    saathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    saathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    saathratriEntity2DTO.getCompositeId().getBlogId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaathratriEntity2DTO> result = saathratriEntity2Service.partialUpdate(saathratriEntity2DTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntity2DTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /saathratri-entity-2-s} : get all the saathratriEntity2s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saathratriEntity2s in body.
     */
    @GetMapping("")
    public List<SaathratriEntity2DTO> getAllSaathratriEntity2s() {
        LOG.debug("REST request to get all SaathratriEntity2s");
        return saathratriEntity2Service.findAll();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET  /:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : get the "entityTypeId" saathratriEntity2.
     *
     * // Composite Primary Key Code
     * @param entityTypeId the Entity Type Id of the saathratriEntity2 to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the saathratriEntity2 to retrieve.
     * @param arrivalDate the Arrival Date of the saathratriEntity2 to retrieve.
     * @param blogId the Blog Id of the saathratriEntity2 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saathratriEntity2DTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<SaathratriEntity2DTO> getSaathratriEntity2(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get SaathratriEntity2 with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        // Composite Primary Key Code
        SaathratriEntity2Id compositeId = new SaathratriEntity2Id();
        compositeId.setEntityTypeId(entityTypeId);
        compositeId.setYearOfDateAdded(yearOfDateAdded);
        compositeId.setArrivalDate(arrivalDate);
        compositeId.setBlogId(blogId);

        Optional<SaathratriEntity2DTO> saathratriEntity2DTO = saathratriEntity2Service.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(saathratriEntity2DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : delete the "compositeId" saathratriEntity2.
     *
     * // Composite Primary Key Code
     * @param entityTypeId the Entity Type Id of the saathratriEntity2 to delete.
     * @param yearOfDateAdded the Year Of Date Added of the saathratriEntity2 to delete.
     * @param arrivalDate the Arrival Date of the saathratriEntity2 to delete.
     * @param blogId the Blog Id of the saathratriEntity2 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteSaathratriEntity2(
        @PathVariable(value = "entityTypeId", required = true) final UUID entityTypeId,
        @PathVariable(value = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete SaathratriEntity2 with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        // Composite Primary Key Code
        SaathratriEntity2Id compositeId = new SaathratriEntity2Id();
        compositeId.setEntityTypeId(entityTypeId);
        compositeId.setYearOfDateAdded(yearOfDateAdded);
        compositeId.setArrivalDate(arrivalDate);
        compositeId.setBlogId(blogId);
        saathratriEntity2Service.delete(compositeId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, entityTypeId.toString()))
            .build();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-id/:entityTypeId}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id")
    public List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeId(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeId method for SaathratriEntity2s with parameteres entityTypeId: {}",
            entityTypeId
        );
        return saathratriEntity2Service.findAllByCompositeIdEntityTypeId(entityTypeId);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added/:entityTypeId/:yearOfDateAdded}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added")
    public List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded method for SaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}",
            entityTypeId,
            yearOfDateAdded
        );
        return saathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(entityTypeId, yearOfDateAdded);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date/:entityTypeId/:yearOfDateAdded/:arrivalDate}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date")
    public List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate method for SaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
        return saathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than/:entityTypeId/:yearOfDateAdded/:arrivalDate}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than")
    public List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan method for SaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
        return saathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than/:entityTypeId/:yearOfDateAdded/:arrivalDate}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than")
    public List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan method for SaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
        return saathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id"
    )
    public Optional<
        SaathratriEntity2DTO
    > findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId method for SaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        return saathratriEntity2Service.findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than"
    )
    public List<
        SaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan method for SaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        return saathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than"
    )
    public List<
        SaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan method for SaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        return saathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-latest-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date/:entityTypeId/:yearOfDateAdded/:arrivalDate}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-latest-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date")
    public SaathratriEntity2DTO findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate method for SaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
        return saathratriEntity2Service.findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
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
