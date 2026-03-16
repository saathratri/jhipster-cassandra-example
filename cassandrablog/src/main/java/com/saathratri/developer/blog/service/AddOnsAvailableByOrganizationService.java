package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganizationId;
import com.saathratri.developer.blog.service.dto.AddOnsAvailableByOrganizationDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.AddOnsAvailableByOrganization}.
 */
public interface AddOnsAvailableByOrganizationService {
    /**
     * Save a addOnsAvailableByOrganization.
     *
     * @param addOnsAvailableByOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    AddOnsAvailableByOrganizationDTO save(AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO);

    /**
     * Updates a addOnsAvailableByOrganization.
     *
     * @param addOnsAvailableByOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    AddOnsAvailableByOrganizationDTO update(AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO);

    /**
     * Partially updates a addOnsAvailableByOrganization.
     *
     * @param addOnsAvailableByOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AddOnsAvailableByOrganizationDTO> partialUpdate(AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO);

    /**
     * Get all the addOnsAvailableByOrganizations.
     *
     * @return the list of entities.
     */
    List<AddOnsAvailableByOrganizationDTO> findAll();

    /**
     * Get the "id" addOnsAvailableByOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddOnsAvailableByOrganizationDTO> findOne(AddOnsAvailableByOrganizationId id);

    /**
     * Delete the "id" addOnsAvailableByOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(AddOnsAvailableByOrganizationId id);

    List<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationId(final UUID organizationId);
    Slice<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdPageable(final UUID organizationId, Pageable pageable);
    List<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType
    );
    Slice<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable(
        final UUID organizationId,
        final String entityType,
        Pageable pageable
    );
    List<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId
    );
    Slice<AddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        Pageable pageable
    );
    Optional<
        AddOnsAvailableByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        final UUID addOnId
    );
}
