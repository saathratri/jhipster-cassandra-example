package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.SaathratriEntity5Id;
import com.saathratri.developer.blog.service.dto.SaathratriEntity5DTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity5}.
 */
public interface SaathratriEntity5Service {
    /**
     * Save a saathratriEntity5.
     *
     * @param saathratriEntity5DTO the entity to save.
     * @return the persisted entity.
     */
    SaathratriEntity5DTO save(SaathratriEntity5DTO saathratriEntity5DTO);

    /**
     * Updates a saathratriEntity5.
     *
     * @param saathratriEntity5DTO the entity to update.
     * @return the persisted entity.
     */
    SaathratriEntity5DTO update(SaathratriEntity5DTO saathratriEntity5DTO);

    /**
     * Partially updates a saathratriEntity5.
     *
     * @param saathratriEntity5DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaathratriEntity5DTO> partialUpdate(SaathratriEntity5DTO saathratriEntity5DTO);

    /**
     * Get all the saathratriEntity5s.
     *
     * @return the list of entities.
     */
    List<SaathratriEntity5DTO> findAll();

    /**
     * Get the "id" saathratriEntity5.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaathratriEntity5DTO> findOne(SaathratriEntity5Id id);

    /**
     * Delete the "id" saathratriEntity5.
     *
     * @param id the id of the entity.
     */
    void delete(SaathratriEntity5Id id);

    List<SaathratriEntity5DTO> findAllByCompositeIdOrganizationId(final UUID organizationId);
    List<SaathratriEntity5DTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType
    );
    List<SaathratriEntity5DTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId
    );
    Optional<SaathratriEntity5DTO> findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        final UUID addOnId
    );
}
