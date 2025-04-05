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

    private static final String ENTITY_NAME = "blogAddOnsSelectedByOrganization";

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
