package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.service.dto.LandingPageByOrganizationDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.LandingPageByOrganization}.
 */
public interface LandingPageByOrganizationService {
    /**
     * Save a landingPageByOrganization.
     *
     * @param landingPageByOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    LandingPageByOrganizationDTO save(LandingPageByOrganizationDTO landingPageByOrganizationDTO);

    /**
     * Updates a landingPageByOrganization.
     *
     * @param landingPageByOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    LandingPageByOrganizationDTO update(LandingPageByOrganizationDTO landingPageByOrganizationDTO);

    /**
     * Partially updates a landingPageByOrganization.
     *
     * @param landingPageByOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LandingPageByOrganizationDTO> partialUpdate(LandingPageByOrganizationDTO landingPageByOrganizationDTO);

    /**
     * Get all the landingPageByOrganizations.
     *
     * @return the list of entities.
     */
    List<LandingPageByOrganizationDTO> findAll();

    /**
     * Get the "id" landingPageByOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LandingPageByOrganizationDTO> findOne(UUID organizationId);

    /**
     * Delete the "id" landingPageByOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(UUID organizationId);
}
