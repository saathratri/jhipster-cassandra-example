package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.SaathratriEntity6Id;
import com.saathratri.developer.blog.service.dto.SaathratriEntity6DTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity6}.
 */
public interface SaathratriEntity6Service {
    /**
     * Save a saathratriEntity6.
     *
     * @param saathratriEntity6DTO the entity to save.
     * @return the persisted entity.
     */
    SaathratriEntity6DTO save(SaathratriEntity6DTO saathratriEntity6DTO);

    /**
     * Updates a saathratriEntity6.
     *
     * @param saathratriEntity6DTO the entity to update.
     * @return the persisted entity.
     */
    SaathratriEntity6DTO update(SaathratriEntity6DTO saathratriEntity6DTO);

    /**
     * Partially updates a saathratriEntity6.
     *
     * @param saathratriEntity6DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaathratriEntity6DTO> partialUpdate(SaathratriEntity6DTO saathratriEntity6DTO);

    /**
     * Get all the saathratriEntity6s.
     *
     * @return the list of entities.
     */
    List<SaathratriEntity6DTO> findAll();

    /**
     * Get the "id" saathratriEntity6.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaathratriEntity6DTO> findOne(SaathratriEntity6Id id);

    /**
     * Delete the "id" saathratriEntity6.
     *
     * @param id the id of the entity.
     */
    void delete(SaathratriEntity6Id id);

    List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationId(final UUID organizationId);
    List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<SaathratriEntity6DTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
    Optional<
        SaathratriEntity6DTO
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    List<
        SaathratriEntity6DTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    List<
        SaathratriEntity6DTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    SaathratriEntity6DTO findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
}
