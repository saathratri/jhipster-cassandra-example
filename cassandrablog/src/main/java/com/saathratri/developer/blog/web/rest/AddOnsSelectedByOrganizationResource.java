package com.saathratri.developer.blog.web.rest;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.saathratri.developer.blog.domain.AddOnsSelectedByOrganizationId;
import com.saathratri.developer.blog.repository.AddOnsSelectedByOrganizationRepository;
import com.saathratri.developer.blog.service.AddOnsSelectedByOrganizationService;
import com.saathratri.developer.blog.service.dto.AddOnsSelectedByOrganizationDTO;
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
 * REST controller for managing {@link com.saathratri.developer.blog.domain.AddOnsSelectedByOrganization}.
 */
@RestController
@RequestMapping("/api/add-ons-selected-by-organizations")
public class AddOnsSelectedByOrganizationResource {

    private static final Logger LOG = LoggerFactory.getLogger(AddOnsSelectedByOrganizationResource.class);

    private static final String ENTITY_NAME = "cassandrablogAddOnsSelectedByOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddOnsSelectedByOrganizationService addOnsSelectedByOrganizationService;

    private final AddOnsSelectedByOrganizationRepository addOnsSelectedByOrganizationRepository;

    public AddOnsSelectedByOrganizationResource(
        AddOnsSelectedByOrganizationService addOnsSelectedByOrganizationService,
        AddOnsSelectedByOrganizationRepository addOnsSelectedByOrganizationRepository
    ) {
        this.addOnsSelectedByOrganizationService = addOnsSelectedByOrganizationService;
        this.addOnsSelectedByOrganizationRepository = addOnsSelectedByOrganizationRepository;
    }

    /**
     * {@code POST  /add-ons-selected-by-organizations} : Create a new addOnsSelectedByOrganization.
     *
     * @param addOnsSelectedByOrganizationDTO the addOnsSelectedByOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addOnsSelectedByOrganizationDTO, or with status {@code 400 (Bad Request)} if the addOnsSelectedByOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AddOnsSelectedByOrganizationDTO> createAddOnsSelectedByOrganization(
        @RequestBody AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save AddOnsSelectedByOrganization : {}", addOnsSelectedByOrganizationDTO);

        // Composite Primary Key Code

        // Generate a TimeUUID for the Primary Key composite fields.

        addOnsSelectedByOrganizationDTO.getCompositeId().setCreatedTimeId(Uuids.timeBased());

        if (
            addOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            addOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate() == null ||
            addOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber() == null ||
            addOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("A new addOnsSelectedByOrganization cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationService.save(addOnsSelectedByOrganizationDTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/add-ons-selected-by-organizations/" +
                    addOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId() +
                    "/" +
                    addOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate() +
                    "/" +
                    getUrlEncodedParameterValue(addOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber()) +
                    "/" +
                    addOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    addOnsSelectedByOrganizationDTO.getCompositeId().toString()
                )
            )
            .body(addOnsSelectedByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /add-ons-selected-by-organizations/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : Updates an existing addOnsSelectedByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the addOnsSelectedByOrganization to update.
     * @param arrivalDate the Arrival Date of the addOnsSelectedByOrganization to update.
     * @param accountNumber the Account Number of the addOnsSelectedByOrganization to update.
     * @param createdTimeId the Created Time Id of the addOnsSelectedByOrganization to update.
     * @param addOnsSelectedByOrganizationDTO the addOnsSelectedByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addOnsSelectedByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the addOnsSelectedByOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addOnsSelectedByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}")
    public ResponseEntity<AddOnsSelectedByOrganizationDTO> updateAddOnsSelectedByOrganization(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "accountNumber", required = true) final String accountNumber,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestBody AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update AddOnsSelectedByOrganization with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, addOnsSelectedByOrganizationDTO: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId,
            addOnsSelectedByOrganizationDTO
        );
        // Composite Primary Key Code
        if (
            addOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            addOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate() == null ||
            addOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber() == null ||
            addOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, addOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(arrivalDate, addOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate()) ||
            !Objects.equals(accountNumber, addOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber()) ||
            !Objects.equals(createdTimeId, addOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !addOnsSelectedByOrganizationRepository.existsById(
                new AddOnsSelectedByOrganizationId(
                    addOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId(),
                    addOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate(),
                    addOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber(),
                    addOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationService.update(addOnsSelectedByOrganizationDTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    addOnsSelectedByOrganizationDTO.getCompositeId().toString()
                )
            )
            .body(addOnsSelectedByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /add-ons-selected-by-organizations/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : Partial updates given fields of an existing addOnsSelectedByOrganization, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the addOnsSelectedByOrganization to partially update.
     * @param arrivalDate the Arrival Date of the addOnsSelectedByOrganization to partially update.
     * @param accountNumber the Account Number of the addOnsSelectedByOrganization to partially update.
     * @param createdTimeId the Created Time Id of the addOnsSelectedByOrganization to partially update.
     * @param addOnsSelectedByOrganizationDTO the addOnsSelectedByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addOnsSelectedByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the addOnsSelectedByOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the addOnsSelectedByOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the addOnsSelectedByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(
        value = "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<AddOnsSelectedByOrganizationDTO> partialUpdateAddOnsSelectedByOrganization(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "accountNumber", required = true) final String accountNumber,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestBody AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update AddOnsSelectedByOrganization with the parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, addOnsSelectedByOrganizationDTO: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId,
            addOnsSelectedByOrganizationDTO
        );
        // Composite Primary Key Code
        if (
            addOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            addOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate() == null ||
            addOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber() == null ||
            addOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, addOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(arrivalDate, addOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate()) ||
            !Objects.equals(accountNumber, addOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber()) ||
            !Objects.equals(createdTimeId, addOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !addOnsSelectedByOrganizationRepository.existsById(
                new AddOnsSelectedByOrganizationId(
                    addOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId(),
                    addOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate(),
                    addOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber(),
                    addOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AddOnsSelectedByOrganizationDTO> result = addOnsSelectedByOrganizationService.partialUpdate(
            addOnsSelectedByOrganizationDTO
        );

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                addOnsSelectedByOrganizationDTO.getCompositeId().toString()
            )
        );
    }

    /**
     * {@code GET  /add-ons-selected-by-organizations} : get all the addOnsSelectedByOrganizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addOnsSelectedByOrganizations in body.
     */
    @GetMapping("")
    public List<AddOnsSelectedByOrganizationDTO> getAllAddOnsSelectedByOrganizations() {
        LOG.debug("REST request to get all AddOnsSelectedByOrganizations");
        return addOnsSelectedByOrganizationService.findAll();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET  /:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get the "organizationId" addOnsSelectedByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the addOnsSelectedByOrganization to retrieve.
     * @param arrivalDate the Arrival Date of the addOnsSelectedByOrganization to retrieve.
     * @param accountNumber the Account Number of the addOnsSelectedByOrganization to retrieve.
     * @param createdTimeId the Created Time Id of the addOnsSelectedByOrganization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addOnsSelectedByOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<AddOnsSelectedByOrganizationDTO> getAddOnsSelectedByOrganization(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get AddOnsSelectedByOrganization with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        // Composite Primary Key Code
        AddOnsSelectedByOrganizationId compositeId = new AddOnsSelectedByOrganizationId();
        compositeId.setOrganizationId(organizationId);
        compositeId.setArrivalDate(arrivalDate);
        compositeId.setAccountNumber(accountNumber);
        compositeId.setCreatedTimeId(createdTimeId);

        Optional<AddOnsSelectedByOrganizationDTO> addOnsSelectedByOrganizationDTO = addOnsSelectedByOrganizationService.findOne(
            compositeId
        );
        return ResponseUtil.wrapOrNotFound(addOnsSelectedByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : delete the "compositeId" addOnsSelectedByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the addOnsSelectedByOrganization to delete.
     * @param arrivalDate the Arrival Date of the addOnsSelectedByOrganization to delete.
     * @param accountNumber the Account Number of the addOnsSelectedByOrganization to delete.
     * @param createdTimeId the Created Time Id of the addOnsSelectedByOrganization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteAddOnsSelectedByOrganization(
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "accountNumber", required = true) final String accountNumber,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete AddOnsSelectedByOrganization with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        // Composite Primary Key Code
        AddOnsSelectedByOrganizationId compositeId = new AddOnsSelectedByOrganizationId();
        compositeId.setOrganizationId(organizationId);
        compositeId.setArrivalDate(arrivalDate);
        compositeId.setAccountNumber(accountNumber);
        compositeId.setCreatedTimeId(createdTimeId);
        addOnsSelectedByOrganizationService.delete(compositeId);
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id")
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationId method for AddOnsSelectedByOrganizations with parameteres organizationId: {}",
            organizationId
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationId(organizationId);
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-pageable/:organizationId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-pageable")
    public ResponseEntity<List<AddOnsSelectedByOrganizationDTO>> findAllByCompositeIdOrganizationIdPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, pagingState: {}, size: {}",
            organizationId,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice = addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdPageable(
            organizationId,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date")
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(organizationId, arrivalDate);
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-pageable/:organizationId/:arrivalDate} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-pageable")
    public ResponseEntity<List<AddOnsSelectedByOrganizationDTO>> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice =
            addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable(
                organizationId,
                arrivalDate,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than")
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
            organizationId,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-pageable/:organizationId/:arrivalDate} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-pageable")
    public ResponseEntity<
        List<AddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice =
            addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable(
                organizationId,
                arrivalDate,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal")
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(
            organizationId,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal-pageable/:organizationId/:arrivalDate} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal-pageable")
    public ResponseEntity<
        List<AddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice =
            addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable(
                organizationId,
                arrivalDate,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than")
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
            organizationId,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-pageable/:organizationId/:arrivalDate} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-pageable")
    public ResponseEntity<
        List<AddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice =
            addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable(
                organizationId,
                arrivalDate,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal")
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(
            organizationId,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal-pageable/:organizationId/:arrivalDate} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal-pageable")
    public ResponseEntity<
        List<AddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice =
            addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable(
                organizationId,
                arrivalDate,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number/:organizationId/:arrivalDate/:accountNumber}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number")
    public List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}",
            organizationId,
            arrivalDate,
            accountNumber
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
            organizationId,
            arrivalDate,
            accountNumber
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-pageable/:organizationId/:arrivalDate/:accountNumber} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-pageable")
    public ResponseEntity<
        List<AddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
            accountNumber,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice =
            addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
                organizationId,
                arrivalDate,
                accountNumber,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id/:organizationId/:arrivalDate/:accountNumber/:createdTimeId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id"
    )
    public Optional<
        AddOnsSelectedByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return addOnsSelectedByOrganizationService.findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than/:organizationId/:arrivalDate/:accountNumber/:createdTimeId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than"
    )
    public List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-pageable/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-pageable"
    )
    public ResponseEntity<
        List<AddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
            accountNumber,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice =
            addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
                organizationId,
                arrivalDate,
                accountNumber,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal/:organizationId/:arrivalDate/:accountNumber/:createdTimeId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal"
    )
    public List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal-pageable/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal-pageable"
    )
    public ResponseEntity<
        List<AddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
            accountNumber,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice =
            addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
                organizationId,
                arrivalDate,
                accountNumber,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than/:organizationId/:arrivalDate/:accountNumber/:createdTimeId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than"
    )
    public List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-pageable/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-pageable"
    )
    public ResponseEntity<
        List<AddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
            accountNumber,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice =
            addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
                organizationId,
                arrivalDate,
                accountNumber,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal/:organizationId/:arrivalDate/:accountNumber/:createdTimeId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal"
    )
    public List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal-pageable/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal-pageable"
    )
    public ResponseEntity<
        List<AddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated AddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
            accountNumber,
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
                LOG.error("Invalid paging state for AddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<AddOnsSelectedByOrganizationDTO> slice =
            addOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
                organizationId,
                arrivalDate,
                accountNumber,
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
                LOG.warn("Unable to resolve next paging state for AddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-latest-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number/:organizationId/:arrivalDate/:accountNumber}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the AddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-latest-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number")
    public AddOnsSelectedByOrganizationDTO findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber method for AddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}",
            organizationId,
            arrivalDate,
            accountNumber
        );
        return addOnsSelectedByOrganizationService.findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
            organizationId,
            arrivalDate,
            accountNumber
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
