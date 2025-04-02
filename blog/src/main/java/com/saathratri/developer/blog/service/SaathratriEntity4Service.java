package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.SaathratriEntity4Id;
import com.saathratri.developer.blog.service.dto.SaathratriEntity4DTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity4}.
 */
public interface SaathratriEntity4Service {
    /**
     * Save a saathratriEntity4.
     *
     * @param saathratriEntity4DTO the entity to save.
     * @return the persisted entity.
     */
    SaathratriEntity4DTO save(SaathratriEntity4DTO saathratriEntity4DTO);

    /**
     * Updates a saathratriEntity4.
     *
     * @param saathratriEntity4DTO the entity to update.
     * @return the persisted entity.
     */
    SaathratriEntity4DTO update(SaathratriEntity4DTO saathratriEntity4DTO);

    /**
     * Partially updates a saathratriEntity4.
     *
     * @param saathratriEntity4DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaathratriEntity4DTO> partialUpdate(SaathratriEntity4DTO saathratriEntity4DTO);

    /**
     * Get all the saathratriEntity4s.
     *
     * @return the list of entities.
     */
    List<SaathratriEntity4DTO> findAll();

    /**
     * Get the "id" saathratriEntity4.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaathratriEntity4DTO> findOne(SaathratriEntity4Id id);

    /**
     * Delete the "id" saathratriEntity4.
     *
     * @param id the id of the entity.
     */
    void delete(SaathratriEntity4Id id);

    List<SaathratriEntity4DTO> findAllByCompositeIdOrganizationId(final UUID organizationId);
}
