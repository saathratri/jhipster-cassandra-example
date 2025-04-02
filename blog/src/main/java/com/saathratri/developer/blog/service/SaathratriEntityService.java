package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.service.dto.SaathratriEntityDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity}.
 */
public interface SaathratriEntityService {
    /**
     * Save a saathratriEntity.
     *
     * @param saathratriEntityDTO the entity to save.
     * @return the persisted entity.
     */
    SaathratriEntityDTO save(SaathratriEntityDTO saathratriEntityDTO);

    /**
     * Updates a saathratriEntity.
     *
     * @param saathratriEntityDTO the entity to update.
     * @return the persisted entity.
     */
    SaathratriEntityDTO update(SaathratriEntityDTO saathratriEntityDTO);

    /**
     * Partially updates a saathratriEntity.
     *
     * @param saathratriEntityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaathratriEntityDTO> partialUpdate(SaathratriEntityDTO saathratriEntityDTO);

    /**
     * Get all the saathratriEntities.
     *
     * @return the list of entities.
     */
    List<SaathratriEntityDTO> findAll();

    /**
     * Get the "id" saathratriEntity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaathratriEntityDTO> findOne(UUID entityId);

    /**
     * Delete the "id" saathratriEntity.
     *
     * @param id the id of the entity.
     */
    void delete(UUID entityId);
}
