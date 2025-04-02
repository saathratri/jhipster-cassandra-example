package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.SaathratriEntity2Id;
import com.saathratri.developer.blog.service.dto.SaathratriEntity2DTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.SaathratriEntity2}.
 */
public interface SaathratriEntity2Service {
    /**
     * Save a saathratriEntity2.
     *
     * @param saathratriEntity2DTO the entity to save.
     * @return the persisted entity.
     */
    SaathratriEntity2DTO save(SaathratriEntity2DTO saathratriEntity2DTO);

    /**
     * Updates a saathratriEntity2.
     *
     * @param saathratriEntity2DTO the entity to update.
     * @return the persisted entity.
     */
    SaathratriEntity2DTO update(SaathratriEntity2DTO saathratriEntity2DTO);

    /**
     * Partially updates a saathratriEntity2.
     *
     * @param saathratriEntity2DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaathratriEntity2DTO> partialUpdate(SaathratriEntity2DTO saathratriEntity2DTO);

    /**
     * Get all the saathratriEntity2s.
     *
     * @return the list of entities.
     */
    List<SaathratriEntity2DTO> findAll();

    /**
     * Get the "id" saathratriEntity2.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaathratriEntity2DTO> findOne(SaathratriEntity2Id id);

    /**
     * Delete the "id" saathratriEntity2.
     *
     * @param id the id of the entity.
     */
    void delete(SaathratriEntity2Id id);

    List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeId(final UUID entityTypeId);
    List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(
        final UUID entityTypeId,
        final Long yearOfDateAdded
    );
    List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    List<SaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Optional<SaathratriEntity2DTO> findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    List<
        SaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    List<
        SaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    SaathratriEntity2DTO findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
}
