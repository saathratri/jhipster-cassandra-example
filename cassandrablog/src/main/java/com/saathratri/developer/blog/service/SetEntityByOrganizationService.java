package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.service.dto.SetEntityByOrganizationDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.SetEntityByOrganization}.
 */
public interface SetEntityByOrganizationService {
    /**
     * Save a setEntityByOrganization.
     *
     * @param setEntityByOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    SetEntityByOrganizationDTO save(SetEntityByOrganizationDTO setEntityByOrganizationDTO);

    /**
     * Updates a setEntityByOrganization.
     *
     * @param setEntityByOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    SetEntityByOrganizationDTO update(SetEntityByOrganizationDTO setEntityByOrganizationDTO);

    /**
     * Partially updates a setEntityByOrganization.
     *
     * @param setEntityByOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SetEntityByOrganizationDTO> partialUpdate(SetEntityByOrganizationDTO setEntityByOrganizationDTO);

    /**
     * Get all the setEntityByOrganizations.
     *
     * @return the list of entities.
     */
    List<SetEntityByOrganizationDTO> findAll();

    /**
     * Get the "id" setEntityByOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SetEntityByOrganizationDTO> findOne(UUID organizationId);

    /**
     * Delete the "id" setEntityByOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(UUID organizationId);
}
