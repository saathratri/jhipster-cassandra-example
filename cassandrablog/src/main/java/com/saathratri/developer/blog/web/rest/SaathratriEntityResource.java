package com.saathratri.developer.blog.web.rest;

import com.saathratri.developer.blog.repository.SaathratriEntityRepository;
import com.saathratri.developer.blog.service.SaathratriEntityService;
import com.saathratri.developer.blog.service.dto.SaathratriEntityDTO;
import com.saathratri.developer.blog.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity}.
 */
@RestController
@RequestMapping("/api/saathratri-entities")
public class SaathratriEntityResource {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntityResource.class);

    private static final String ENTITY_NAME = "cassandrablogSaathratriEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaathratriEntityService saathratriEntityService;

    private final SaathratriEntityRepository saathratriEntityRepository;

    public SaathratriEntityResource(
        SaathratriEntityService saathratriEntityService,
        SaathratriEntityRepository saathratriEntityRepository
    ) {
        this.saathratriEntityService = saathratriEntityService;
        this.saathratriEntityRepository = saathratriEntityRepository;
    }

    /**
     * {@code POST  /saathratri-entities} : Create a new saathratriEntity.
     *
     * @param saathratriEntityDTO the saathratriEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saathratriEntityDTO, or with status {@code 400 (Bad Request)} if the saathratriEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SaathratriEntityDTO> createSaathratriEntity(@RequestBody SaathratriEntityDTO saathratriEntityDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SaathratriEntity : {}", saathratriEntityDTO);

        // Single-value Primary Key Code
        if (saathratriEntityDTO.getEntityId() == null) {
            throw new BadRequestAlertException("A new saathratriEntity must have an ID", ENTITY_NAME, "idinvalid");
        }

        saathratriEntityDTO = saathratriEntityService.save(saathratriEntityDTO);
        // Single-value Primary Key Code
        return ResponseEntity.created(new URI("/api/saathratri-entities/" + saathratriEntityDTO.getEntityId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, saathratriEntityDTO.getEntityId().toString()))
            .body(saathratriEntityDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PUT  /saathratri-entities/:entityId} : Updates an existing saathratriEntity.
     *
     * // Single-value Primary Key Code
     * @param entityId the entityId of the saathratriEntityDTO to save.
     * @param saathratriEntityDTO the saathratriEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntityDTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PutMapping("/{entityId}")
    public ResponseEntity<SaathratriEntityDTO> updateSaathratriEntity(
        // Single-value Primary Key Code
        @PathVariable(value = "entityId", required = false) final UUID entityId,
        @RequestBody SaathratriEntityDTO saathratriEntityDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to update SaathratriEntity : {}, {}", entityId, saathratriEntityDTO);
        // Single-value Primary Key Code
        if (saathratriEntityDTO.getEntityId() == null) {
            throw new BadRequestAlertException("Invalid entityId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(entityId, saathratriEntityDTO.getEntityId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!saathratriEntityRepository.existsById(entityId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        saathratriEntityDTO = saathratriEntityService.update(saathratriEntityDTO);
        // Single-value Primary Key Code
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntityDTO.getEntityId().toString()))
            .body(saathratriEntityDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PATCH  /saathratri-entities/:entityId} : Partial updates given fields of an existing saathratriEntity, field will ignore if it is null
     *
     * // Single-value Primary Key Code
     * @param entityId the entityId of the saathratriEntityDTO to save.
     * @param saathratriEntityDTO the saathratriEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntityDTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the saathratriEntityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PatchMapping(value = "/{entityId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaathratriEntityDTO> partialUpdateSaathratriEntity(
        // Single-value Primary Key Code
        @PathVariable(value = "entityId", required = false) final UUID entityId,
        @RequestBody SaathratriEntityDTO saathratriEntityDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to partial update SaathratriEntity partially : {}, {}", entityId, saathratriEntityDTO);
        // Single-value Primary Key Code
        if (saathratriEntityDTO.getEntityId() == null) {
            throw new BadRequestAlertException("Invalid entityId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(entityId, saathratriEntityDTO.getEntityId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!saathratriEntityRepository.existsById(entityId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaathratriEntityDTO> result = saathratriEntityService.partialUpdate(saathratriEntityDTO);

        // Single-value Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntityDTO.getEntityId().toString())
        );
    }

    /**
     * {@code GET  /saathratri-entities} : get all the saathratriEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saathratriEntities in body.
     */
    @GetMapping("")
    public List<SaathratriEntityDTO> getAllSaathratriEntities() {
        LOG.debug("REST request to get all SaathratriEntities");
        return saathratriEntityService.findAll();
    }

    /**
     * {@code GET  /saathratri-entities/slice} : get saathratriEntities with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saathratriEntities in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<SaathratriEntityDTO>> getAllSaathratriEntitiesSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of SaathratriEntities, pagingState: {}, size: {}", pagingState, size);

        // Build CassandraPageRequest from pagingState parameter
        org.springframework.data.domain.Pageable cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                java.nio.ByteBuffer pagingStateBuffer;
                try {
                    pagingStateBuffer = java.nio.ByteBuffer.wrap(java.util.Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    pagingStateBuffer = java.nio.ByteBuffer.wrap(java.util.Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(org.springframework.data.domain.PageRequest.of(0, size), pagingStateBuffer);
            } catch (Exception e) {
                LOG.warn("Invalid paging state, starting from beginning", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<SaathratriEntityDTO> slice = saathratriEntityService.findAllSlice(cassandraPageRequest);
        List<SaathratriEntityDTO> result = slice.getContent();

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        boolean hasNext = slice.hasNext();
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            java.nio.ByteBuffer nextPagingState = null;
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            if (currentCassandraPageRequest.getPagingState() != null) {
                org.springframework.data.domain.Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            }
            if (nextPagingState != null) {
                byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
                nextPagingState.duplicate().get(pagingStateBytes);
                headers.add("X-Paging-State", java.util.Base64.getUrlEncoder().encodeToString(pagingStateBytes));
            }
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));
        headers.add("Access-Control-Expose-Headers", "X-Has-Next-Page, X-Paging-State, X-Total-Count");

        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * // Single-value Primary Key Code
     * {@code GET  /:entityId} : get the "entityId" saathratriEntity.
     *
     * // Single-value Primary Key Code
     * @param entityId the entityId of the saathratriEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saathratriEntityDTO, or with status {@code 404 (Not Found)}.
     */
    // Single-value Primary Key Code
    @GetMapping("/{entityId}")
    // Single-value Primary Key Code
    public ResponseEntity<SaathratriEntityDTO> getSaathratriEntity(@PathVariable("entityId") UUID entityId) {
        // Single-value Primary Key Code
        LOG.debug("REST request to get SaathratriEntity : {}", entityId);

        Optional<SaathratriEntityDTO> saathratriEntityDTO = saathratriEntityService.findOne(entityId);
        return ResponseUtil.wrapOrNotFound(saathratriEntityDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code DELETE  /:entityId} : delete the "entityId" saathratriEntity.
     *
     * // Single-value Primary Key Code
     * @param entityId the entityId of the saathratriEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Single-value Primary Key Code
    @DeleteMapping("/{entityId}")
    // Single-value Primary Key Code
    public ResponseEntity<Void> deleteSaathratriEntity(@PathVariable("entityId") UUID entityId) {
        // Single-value Primary Key Code
        LOG.debug("REST request to delete SaathratriEntity : {}", entityId);
        saathratriEntityService.delete(entityId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, entityId.toString()))
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
