package com.saathratri.developer.blog.web.rest;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.saathratri.developer.blog.domain.SaathratriEntity3Id;
import com.saathratri.developer.blog.repository.SaathratriEntity3Repository;
import com.saathratri.developer.blog.service.SaathratriEntity3Service;
import com.saathratri.developer.blog.service.dto.SaathratriEntity3DTO;
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
 * REST controller for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity3}.
 */
@RestController
@RequestMapping("/api/saathratri-entity-3-s")
public class SaathratriEntity3Resource {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntity3Resource.class);

    private static final String ENTITY_NAME = "cassandrablogSaathratriEntity3";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaathratriEntity3Service saathratriEntity3Service;

    private final SaathratriEntity3Repository saathratriEntity3Repository;

    public SaathratriEntity3Resource(
        SaathratriEntity3Service saathratriEntity3Service,
        SaathratriEntity3Repository saathratriEntity3Repository
    ) {
        this.saathratriEntity3Service = saathratriEntity3Service;
        this.saathratriEntity3Repository = saathratriEntity3Repository;
    }

    /**
     * {@code POST  /saathratri-entity-3-s} : Create a new saathratriEntity3.
     *
     * @param saathratriEntity3DTO the saathratriEntity3DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saathratriEntity3DTO, or with status {@code 400 (Bad Request)} if the saathratriEntity3 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SaathratriEntity3DTO> createSaathratriEntity3(@RequestBody SaathratriEntity3DTO saathratriEntity3DTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SaathratriEntity3 : {}", saathratriEntity3DTO);

        // Generate a TimeUUID for the Primary Key composite fields.

        saathratriEntity3DTO.getCompositeId().setCreatedTimeId(Uuids.timeBased());

        // Composite Primary Key Code
        if (
            saathratriEntity3DTO.getCompositeId().getEntityType() == null ||
            saathratriEntity3DTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("A new saathratriEntity3 cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        saathratriEntity3DTO = saathratriEntity3Service.save(saathratriEntity3DTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/saathratri-entity-3-s/" +
                    getUrlEncodedParameterValue(saathratriEntity3DTO.getCompositeId().getEntityType()) +
                    "/" +
                    saathratriEntity3DTO.getCompositeId().getCreatedTimeId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, saathratriEntity3DTO.getCompositeId().toString())
            )
            .body(saathratriEntity3DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /saathratri-entity-3-s/:entityType/:createdTimeId} : Updates an existing saathratriEntity3.
     *
     * // Composite Primary Key Code
     * @param entityType the Entity Type of the saathratriEntity3 to update.
     * @param createdTimeId the Created Time Id of the saathratriEntity3 to update.
     * @param saathratriEntity3DTO the saathratriEntity3DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntity3DTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntity3DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntity3DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{entityType}/{createdTimeId}")
    public ResponseEntity<SaathratriEntity3DTO> updateSaathratriEntity3(
        // Composite Primary Key Code
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestBody SaathratriEntity3DTO saathratriEntity3DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update SaathratriEntity3 with parameters entityType: {}, createdTimeId: {}, saathratriEntity3DTO: {}",
            entityType,
            createdTimeId,
            saathratriEntity3DTO
        );
        // Composite Primary Key Code
        if (
            saathratriEntity3DTO.getCompositeId().getEntityType() == null ||
            saathratriEntity3DTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("Invalid entityType", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(entityType, saathratriEntity3DTO.getCompositeId().getEntityType()) ||
            !Objects.equals(createdTimeId, saathratriEntity3DTO.getCompositeId().getCreatedTimeId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !saathratriEntity3Repository.existsById(
                new SaathratriEntity3Id(
                    saathratriEntity3DTO.getCompositeId().getEntityType(),
                    saathratriEntity3DTO.getCompositeId().getCreatedTimeId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        saathratriEntity3DTO = saathratriEntity3Service.update(saathratriEntity3DTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntity3DTO.getCompositeId().toString())
            )
            .body(saathratriEntity3DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /saathratri-entity-3-s/:entityType/:createdTimeId} : Partial updates given fields of an existing saathratriEntity3, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param entityType the Entity Type of the saathratriEntity3 to partially update.
     * @param createdTimeId the Created Time Id of the saathratriEntity3 to partially update.
     * @param saathratriEntity3DTO the saathratriEntity3DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntity3DTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntity3DTO is not valid,
     * or with status {@code 404 (Not Found)} if the saathratriEntity3DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntity3DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(value = "/{entityType}/{createdTimeId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaathratriEntity3DTO> partialUpdateSaathratriEntity3(
        // Composite Primary Key Code
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestBody SaathratriEntity3DTO saathratriEntity3DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update SaathratriEntity3 with the parameters entityType: {}, createdTimeId: {}, saathratriEntity3DTO: {}",
            entityType,
            createdTimeId,
            saathratriEntity3DTO
        );
        // Composite Primary Key Code
        if (
            saathratriEntity3DTO.getCompositeId().getEntityType() == null ||
            saathratriEntity3DTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("Invalid entityType", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(entityType, saathratriEntity3DTO.getCompositeId().getEntityType()) ||
            !Objects.equals(createdTimeId, saathratriEntity3DTO.getCompositeId().getCreatedTimeId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !saathratriEntity3Repository.existsById(
                new SaathratriEntity3Id(
                    saathratriEntity3DTO.getCompositeId().getEntityType(),
                    saathratriEntity3DTO.getCompositeId().getCreatedTimeId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaathratriEntity3DTO> result = saathratriEntity3Service.partialUpdate(saathratriEntity3DTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntity3DTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /saathratri-entity-3-s} : get all the saathratriEntity3s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saathratriEntity3s in body.
     */
    @GetMapping("")
    public List<SaathratriEntity3DTO> getAllSaathratriEntity3s() {
        LOG.debug("REST request to get all SaathratriEntity3s");
        return saathratriEntity3Service.findAll();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET  /:entityType/:createdTimeId} : get the "entityType" saathratriEntity3.
     *
     * // Composite Primary Key Code
     * @param entityType the Entity Type of the saathratriEntity3 to retrieve.
     * @param createdTimeId the Created Time Id of the saathratriEntity3 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saathratriEntity3DTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<SaathratriEntity3DTO> getSaathratriEntity3(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug("REST request to get SaathratriEntity3 with parameters entityType: {}, createdTimeId: {}", entityType, createdTimeId);
        // Composite Primary Key Code
        SaathratriEntity3Id compositeId = new SaathratriEntity3Id();
        compositeId.setEntityType(entityType);
        compositeId.setCreatedTimeId(createdTimeId);

        Optional<SaathratriEntity3DTO> saathratriEntity3DTO = saathratriEntity3Service.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(saathratriEntity3DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:entityType/:createdTimeId} : delete the "compositeId" saathratriEntity3.
     *
     * // Composite Primary Key Code
     * @param entityType the Entity Type of the saathratriEntity3 to delete.
     * @param createdTimeId the Created Time Id of the saathratriEntity3 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{entityType}/{createdTimeId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteSaathratriEntity3(
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug("REST request to delete SaathratriEntity3 with parameters entityType: {}, createdTimeId: {}", entityType, createdTimeId);
        // Composite Primary Key Code
        SaathratriEntity3Id compositeId = new SaathratriEntity3Id();
        compositeId.setEntityType(entityType);
        compositeId.setCreatedTimeId(createdTimeId);
        saathratriEntity3Service.delete(compositeId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, entityType))
            .build();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type/:entityType}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type")
    public List<SaathratriEntity3DTO> findAllByCompositeIdEntityType(
        @RequestParam(name = "entityType", required = true) final String entityType
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityType method for SaathratriEntity3s with parameteres entityType: {}",
            entityType
        );
        return saathratriEntity3Service.findAllByCompositeIdEntityType(entityType);
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-pageable/:entityType} : get paginated entities by composite key.
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-pageable")
    public ResponseEntity<List<SaathratriEntity3DTO>> findAllByCompositeIdEntityTypePageable(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated SaathratriEntity3s with parameters entityType: {}, pagingState: {}, size: {}",
            entityType,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for SaathratriEntity3s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<SaathratriEntity3DTO> slice = saathratriEntity3Service.findAllByCompositeIdEntityTypePageable(
            entityType,
            cassandraPageRequest
        );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for SaathratriEntity3s", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-by-composite-id-entity-type-and-composite-id-created-time-id/:entityType/:createdTimeId}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-composite-id-entity-type-and-composite-id-created-time-id")
    public Optional<SaathratriEntity3DTO> findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId method for SaathratriEntity3s with parameteres entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        return saathratriEntity3Service.findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(entityType, createdTimeId);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than/:entityType/:createdTimeId}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than")
    public List<SaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan method for SaathratriEntity3s with parameteres entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        return saathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(entityType, createdTimeId);
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-pageable/:entityType/:createdTimeId} : get paginated entities by composite key.
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-pageable")
    public ResponseEntity<List<SaathratriEntity3DTO>> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated SaathratriEntity3s with parameters entityType: {}, createdTimeId: {}, pagingState: {}, size: {}",
            entityType,
            createdTimeId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for SaathratriEntity3s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<SaathratriEntity3DTO> slice =
            saathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable(
                entityType,
                createdTimeId,
                cassandraPageRequest
            );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for SaathratriEntity3s", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal/:entityType/:createdTimeId}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal")
    public List<SaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual method for SaathratriEntity3s with parameteres entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        return saathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(entityType, createdTimeId);
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal-pageable/:entityType/:createdTimeId} : get paginated entities by composite key.
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal-pageable")
    public ResponseEntity<List<SaathratriEntity3DTO>> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated SaathratriEntity3s with parameters entityType: {}, createdTimeId: {}, pagingState: {}, size: {}",
            entityType,
            createdTimeId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for SaathratriEntity3s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<SaathratriEntity3DTO> slice =
            saathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable(
                entityType,
                createdTimeId,
                cassandraPageRequest
            );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for SaathratriEntity3s", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than/:entityType/:createdTimeId}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than")
    public List<SaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan method for SaathratriEntity3s with parameteres entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        return saathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(entityType, createdTimeId);
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-pageable/:entityType/:createdTimeId} : get paginated entities by composite key.
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-pageable")
    public ResponseEntity<List<SaathratriEntity3DTO>> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated SaathratriEntity3s with parameters entityType: {}, createdTimeId: {}, pagingState: {}, size: {}",
            entityType,
            createdTimeId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for SaathratriEntity3s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<SaathratriEntity3DTO> slice =
            saathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable(
                entityType,
                createdTimeId,
                cassandraPageRequest
            );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for SaathratriEntity3s", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal/:entityType/:createdTimeId}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal")
    public List<SaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual method for SaathratriEntity3s with parameteres entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        return saathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(
            entityType,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal-pageable/:entityType/:createdTimeId} : get paginated entities by composite key.
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal-pageable")
    public ResponseEntity<List<SaathratriEntity3DTO>> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated SaathratriEntity3s with parameters entityType: {}, createdTimeId: {}, pagingState: {}, size: {}",
            entityType,
            createdTimeId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for SaathratriEntity3s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<SaathratriEntity3DTO> slice =
            saathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
                entityType,
                createdTimeId,
                cassandraPageRequest
            );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for SaathratriEntity3s", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-latest-by-composite-id-entity-type/:entityType}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-latest-by-composite-id-entity-type")
    public SaathratriEntity3DTO findLatestByCompositeIdEntityType(
        @RequestParam(name = "entityType", required = true) final String entityType
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findLatestByCompositeIdEntityType method for SaathratriEntity3s with parameteres entityType: {}",
            entityType
        );
        return saathratriEntity3Service.findLatestByCompositeIdEntityType(entityType);
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
