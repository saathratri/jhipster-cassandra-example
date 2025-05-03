package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.SaathratriEntity3Id;
import com.saathratri.developer.blog.service.dto.SaathratriEntity3DTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity3}.
 */
public interface SaathratriEntity3Service {
    /**
     * Save a saathratriEntity3.
     *
     * @param saathratriEntity3DTO the entity to save.
     * @return the persisted entity.
     */
    SaathratriEntity3DTO save(SaathratriEntity3DTO saathratriEntity3DTO);

    /**
     * Updates a saathratriEntity3.
     *
     * @param saathratriEntity3DTO the entity to update.
     * @return the persisted entity.
     */
    SaathratriEntity3DTO update(SaathratriEntity3DTO saathratriEntity3DTO);

    /**
     * Partially updates a saathratriEntity3.
     *
     * @param saathratriEntity3DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaathratriEntity3DTO> partialUpdate(SaathratriEntity3DTO saathratriEntity3DTO);

    /**
     * Get all the saathratriEntity3s.
     *
     * @return the list of entities.
     */
    List<SaathratriEntity3DTO> findAll();

    /**
     * Get the "id" saathratriEntity3.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaathratriEntity3DTO> findOne(SaathratriEntity3Id id);

    /**
     * Delete the "id" saathratriEntity3.
     *
     * @param id the id of the entity.
     */
    void delete(SaathratriEntity3Id id);

    List<SaathratriEntity3DTO> findAllByCompositeIdEntityType(final String entityType);
    Optional<SaathratriEntity3DTO> findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(
        final String entityType,
        final UUID createdTimeId
    );
    List<SaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(
        final String entityType,
        final UUID createdTimeId
    );
    List<SaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(
        final String entityType,
        final UUID createdTimeId
    );
    SaathratriEntity3DTO findLatestByCompositeIdEntityType(final String entityType);
}
