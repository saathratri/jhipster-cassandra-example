package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.AddOnsSelectedByOrganizationId;
import com.saathratri.developer.blog.service.dto.AddOnsSelectedByOrganizationDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

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

    /**
     * Get all the addOnsSelectedByOrganizations with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<AddOnsSelectedByOrganizationDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);

    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationId(final UUID organizationId);
    Slice<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdPageable(final UUID organizationId, Pageable pageable);
    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
    Slice<AddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        Pageable pageable
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
    Slice<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        AddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    AddOnsSelectedByOrganizationDTO findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
}
