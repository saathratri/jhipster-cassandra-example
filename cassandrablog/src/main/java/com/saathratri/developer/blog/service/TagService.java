package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.service.dto.TagDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.Tag}.
 */
public interface TagService {
    /**
     * Save a tag.
     *
     * @param tagDTO the entity to save.
     * @return the persisted entity.
     */
    TagDTO save(TagDTO tagDTO);

    /**
     * Updates a tag.
     *
     * @param tagDTO the entity to update.
     * @return the persisted entity.
     */
    TagDTO update(TagDTO tagDTO);

    /**
     * Partially updates a tag.
     *
     * @param tagDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TagDTO> partialUpdate(TagDTO tagDTO);

    /**
     * Get all the tags.
     *
     * @return the list of entities.
     */
    List<TagDTO> findAll();

    /**
     * Get the "id" tag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagDTO> findOne(UUID id);

    /**
     * Delete the "id" tag.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);

    // ==================== AI Text Search ====================

    /**
     * Search for tags using AI-powered semantic similarity.
     *
     * @param query the text query to search for
     * @param limit maximum number of results to return
     * @param fields optional list of vector field names to search in (null or empty searches all)
     * @return list of semantically similar tags
     */
    List<TagDTO> aiSearch(String query, int limit, List<String> fields);

    /**
     * Find tags similar to the given embedding vector using nameEmbedding.
     */
    List<TagDTO> findSimilarByNameEmbedding(com.datastax.oss.driver.api.core.data.CqlVector<Float> embedding, int limit);
    /**
     * Find tags similar to the given embedding vector using descriptionEmbedding.
     */
    List<TagDTO> findSimilarByDescriptionEmbedding(com.datastax.oss.driver.api.core.data.CqlVector<Float> embedding, int limit);
}
