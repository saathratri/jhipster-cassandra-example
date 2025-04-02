package com.saathratri.developer.blog.web.rest;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.saathratri.developer.blog.domain.SaathratriEntity6Id;
import com.saathratri.developer.blog.repository.SaathratriEntity6Repository;
import com.saathratri.developer.blog.service.SaathratriEntity6Service;
import com.saathratri.developer.blog.service.dto.SaathratriEntity6DTO;
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
 * REST controller for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity6}.
 */
@RestController
@RequestMapping("/api/saathratri-entity-6-s")
public class SaathratriEntity6Resource {

    private static final Logger LOG = LoggerFactory.getLogger(SaathratriEntity6Resource.class);

    private static final String ENTITY_NAME = "blogSaathratriEntity6";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaathratriEntity6Service saathratriEntity6Service;

    private final SaathratriEntity6Repository saathratriEntity6Repository;

    public SaathratriEntity6Resource(
        SaathratriEntity6Service saathratriEntity6Service,
        SaathratriEntity6Repository saathratriEntity6Repository
    ) {
        this.saathratriEntity6Service = saathratriEntity6Service;
        this.saathratriEntity6Repository = saathratriEntity6Repository;
    }

    /**
     * {@code POST  /saathratri-entity-6-s} : Create a new saathratriEntity6.
     *
     * @param saathratriEntity6DTO the saathratriEntity6DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saathratriEntity6DTO, or with status {@code 400 (Bad Request)} if the saathratriEntity6 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SaathratriEntity6DTO> createSaathratriEntity6(@RequestBody SaathratriEntity6DTO saathratriEntity6DTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SaathratriEntity6 : {}", saathratriEntity6DTO);

        // Composite Primary Key Code

        // Generate a TimeUUID for the Primary Key composite fields.

        saathratriEntity6DTO.getCompositeId().setCreatedTimeId(Uuids.timeBased());

        if (
            saathratriEntity6DTO.getCompositeId().getOrganizationId() == null ||
            saathratriEntity6DTO.getCompositeId().getArrivalDate() == null ||
            saathratriEntity6DTO.getCompositeId().getAccountNumber() == null ||
            saathratriEntity6DTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("A new saathratriEntity6 cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        saathratriEntity6DTO = saathratriEntity6Service.save(saathratriEntity6DTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/saathratri-entity-6-s/" +
                saathratriEntity6DTO.getCompositeId().getOrganizationId() +
                "/" +
                saathratriEntity6DTO.getCompositeId().getArrivalDate() +
                "/" +
                getUrlEncodedParameterValue(saathratriEntity6DTO.getCompositeId().getAccountNumber()) +
                "/" +
                saathratriEntity6DTO.getCompositeId().getCreatedTimeId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, saathratriEntity6DTO.getCompositeId().toString())
            )
            .body(saathratriEntity6DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /saathratri-entity-6-s/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : Updates an existing saathratriEntity6.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity6 to update.
     * @param arrivalDate the Arrival Date of the saathratriEntity6 to update.
     * @param accountNumber the Account Number of the saathratriEntity6 to update.
     * @param createdTimeId the Created Time Id of the saathratriEntity6 to update.
     * @param saathratriEntity6DTO the saathratriEntity6DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntity6DTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntity6DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntity6DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}")
    public ResponseEntity<SaathratriEntity6DTO> updateSaathratriEntity6(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "accountNumber", required = true) final String accountNumber,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestBody SaathratriEntity6DTO saathratriEntity6DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update SaathratriEntity6 with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, saathratriEntity6DTO: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId,
            saathratriEntity6DTO
        );
        // Composite Primary Key Code
        if (
            saathratriEntity6DTO.getCompositeId().getOrganizationId() == null ||
            saathratriEntity6DTO.getCompositeId().getArrivalDate() == null ||
            saathratriEntity6DTO.getCompositeId().getAccountNumber() == null ||
            saathratriEntity6DTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, saathratriEntity6DTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(arrivalDate, saathratriEntity6DTO.getCompositeId().getArrivalDate()) ||
            !Objects.equals(accountNumber, saathratriEntity6DTO.getCompositeId().getAccountNumber()) ||
            !Objects.equals(createdTimeId, saathratriEntity6DTO.getCompositeId().getCreatedTimeId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !saathratriEntity6Repository.existsById(
                new SaathratriEntity6Id(
                    saathratriEntity6DTO.getCompositeId().getOrganizationId(),
                    saathratriEntity6DTO.getCompositeId().getArrivalDate(),
                    saathratriEntity6DTO.getCompositeId().getAccountNumber(),
                    saathratriEntity6DTO.getCompositeId().getCreatedTimeId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        saathratriEntity6DTO = saathratriEntity6Service.update(saathratriEntity6DTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntity6DTO.getCompositeId().toString())
            )
            .body(saathratriEntity6DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /saathratri-entity-6-s/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : Partial updates given fields of an existing saathratriEntity6, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity6 to partially update.
     * @param arrivalDate the Arrival Date of the saathratriEntity6 to partially update.
     * @param accountNumber the Account Number of the saathratriEntity6 to partially update.
     * @param createdTimeId the Created Time Id of the saathratriEntity6 to partially update.
     * @param saathratriEntity6DTO the saathratriEntity6DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saathratriEntity6DTO,
     * or with status {@code 400 (Bad Request)} if the saathratriEntity6DTO is not valid,
     * or with status {@code 404 (Not Found)} if the saathratriEntity6DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the saathratriEntity6DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(
        value = "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<SaathratriEntity6DTO> partialUpdateSaathratriEntity6(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "accountNumber", required = true) final String accountNumber,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestBody SaathratriEntity6DTO saathratriEntity6DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update SaathratriEntity6 with the parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, saathratriEntity6DTO: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId,
            saathratriEntity6DTO
        );
        // Composite Primary Key Code
        if (
            saathratriEntity6DTO.getCompositeId().getOrganizationId() == null ||
            saathratriEntity6DTO.getCompositeId().getArrivalDate() == null ||
            saathratriEntity6DTO.getCompositeId().getAccountNumber() == null ||
            saathratriEntity6DTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, saathratriEntity6DTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(arrivalDate, saathratriEntity6DTO.getCompositeId().getArrivalDate()) ||
            !Objects.equals(accountNumber, saathratriEntity6DTO.getCompositeId().getAccountNumber()) ||
            !Objects.equals(createdTimeId, saathratriEntity6DTO.getCompositeId().getCreatedTimeId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !saathratriEntity6Repository.existsById(
                new SaathratriEntity6Id(
                    saathratriEntity6DTO.getCompositeId().getOrganizationId(),
                    saathratriEntity6DTO.getCompositeId().getArrivalDate(),
                    saathratriEntity6DTO.getCompositeId().getAccountNumber(),
                    saathratriEntity6DTO.getCompositeId().getCreatedTimeId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaathratriEntity6DTO> result = saathratriEntity6Service.partialUpdate(saathratriEntity6DTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saathratriEntity6DTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /saathratri-entity-6-s} : get all the saathratriEntity6s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saathratriEntity6s in body.
     */
    @GetMapping("")
    public List<SaathratriEntity6DTO> getAllSaathratriEntity6s() {
        LOG.debug("REST request to get all SaathratriEntity6s");
        return saathratriEntity6Service.findAll();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET  /:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get the "organizationId" saathratriEntity6.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity6 to retrieve.
     * @param arrivalDate the Arrival Date of the saathratriEntity6 to retrieve.
     * @param accountNumber the Account Number of the saathratriEntity6 to retrieve.
     * @param createdTimeId the Created Time Id of the saathratriEntity6 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saathratriEntity6DTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<SaathratriEntity6DTO> getSaathratriEntity6(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get SaathratriEntity6 with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        // Composite Primary Key Code
        SaathratriEntity6Id compositeId = new SaathratriEntity6Id();
        compositeId.setOrganizationId(organizationId);
        compositeId.setArrivalDate(arrivalDate);
        compositeId.setAccountNumber(accountNumber);
        compositeId.setCreatedTimeId(createdTimeId);

        Optional<SaathratriEntity6DTO> saathratriEntity6DTO = saathratriEntity6Service.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(saathratriEntity6DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : delete the "compositeId" saathratriEntity6.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the saathratriEntity6 to delete.
     * @param arrivalDate the Arrival Date of the saathratriEntity6 to delete.
     * @param accountNumber the Account Number of the saathratriEntity6 to delete.
     * @param createdTimeId the Created Time Id of the saathratriEntity6 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteSaathratriEntity6(
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "accountNumber", required = true) final String accountNumber,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete SaathratriEntity6 with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        // Composite Primary Key Code
        SaathratriEntity6Id compositeId = new SaathratriEntity6Id();
        compositeId.setOrganizationId(organizationId);
        compositeId.setArrivalDate(arrivalDate);
        compositeId.setAccountNumber(accountNumber);
        compositeId.setCreatedTimeId(createdTimeId);
        saathratriEntity6Service.delete(compositeId);
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity6, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id")
    public List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationId method for SaathratriEntity6s with parameteres organizationId: {}",
            organizationId
        );
        return saathratriEntity6Service.findAllByCompositeIdOrganizationId(organizationId);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity6, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date")
    public List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate method for SaathratriEntity6s with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return saathratriEntity6Service.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(organizationId, arrivalDate);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity6, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than")
    public List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan method for SaathratriEntity6s with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return saathratriEntity6Service.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(organizationId, arrivalDate);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity6, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than")
    public List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan method for SaathratriEntity6s with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return saathratriEntity6Service.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(organizationId, arrivalDate);
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity6, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number")
    public List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber method for SaathratriEntity6s with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}",
            organizationId,
            arrivalDate,
            accountNumber
        );
        return saathratriEntity6Service.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity6, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id"
    )
    public Optional<
        SaathratriEntity6DTO
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId method for SaathratriEntity6s with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return saathratriEntity6Service.findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity6, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than"
    )
    public List<
        SaathratriEntity6DTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan method for SaathratriEntity6s with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return saathratriEntity6Service.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity6, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than"
    )
    public List<
        SaathratriEntity6DTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan method for SaathratriEntity6s with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return saathratriEntity6Service.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SaathratriEntity6, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-latest-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number")
    public SaathratriEntity6DTO findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber method for SaathratriEntity6s with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}",
            organizationId,
            arrivalDate,
            accountNumber
        );
        return saathratriEntity6Service.findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
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
