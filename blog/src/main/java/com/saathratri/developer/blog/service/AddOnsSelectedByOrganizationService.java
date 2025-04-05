package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.AddOnsSelectedByOrganizationId;
import com.saathratri.developer.blog.service.dto.AddOnsSelectedByOrganizationDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.AddOnsSelectedByOrganization}.
 */
public interface AddOnsSelectedByOrganizationService {
    /**
     * Save a addOnsSelectedByOrganization.
     *
     * @param addOnsSelectedByOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    AddOnsSelectedByOrganizationDTO save(AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO);

    /**
     * Updates a addOnsSelectedByOrganization.
     *
     * @param addOnsSelectedByOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    AddOnsSelectedByOrganizationDTO update(AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO);

    /**
     * Partially updates a addOnsSelectedByOrganization.
     *
     * @param addOnsSelectedByOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AddOnsSelectedByOrganizationDTO> partialUpdate(AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO);

    /**
     * Get all the addOnsSelectedByOrganizations.
     *
     * @return the list of entities.
     */
    List<AddOnsSelectedByOrganizationDTO> findAll();

    /**
     * Get the "id" addOnsSelectedByOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddOnsSelectedByOrganizationDTO> findOne(AddOnsSelectedByOrganizationId id);

    /**
     * Delete the "id" addOnsSelectedByOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(AddOnsSelectedByOrganizationId id);

    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationId(final UUID organizationId);
    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
    Optional<
        AddOnsSelectedByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    AddOnsSelectedByOrganizationDTO findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
}
