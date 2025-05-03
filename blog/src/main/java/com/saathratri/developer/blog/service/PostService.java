package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.PostId;
import com.saathratri.developer.blog.service.dto.PostDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.Post}.
 */
public interface PostService {
    /**
     * Save a post.
     *
     * @param postDTO the entity to save.
     * @return the persisted entity.
     */
    PostDTO save(PostDTO postDTO);

    /**
     * Updates a post.
     *
     * @param postDTO the entity to update.
     * @return the persisted entity.
     */
    PostDTO update(PostDTO postDTO);

    /**
     * Partially updates a post.
     *
     * @param postDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostDTO> partialUpdate(PostDTO postDTO);

    /**
     * Get all the posts.
     *
     * @return the list of entities.
     */
    List<PostDTO> findAll();

    /**
     * Get the "id" post.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostDTO> findOne(PostId id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity.
     */
    void delete(PostId id);

    List<PostDTO> findAllByCompositeIdCreatedDate(final Long createdDate);
    List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(final Long createdDate, final Long addedDateTime);
    List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(final Long createdDate, final Long addedDateTime);
    List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(final Long createdDate, final Long addedDateTime);
    Optional<PostDTO> findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
        final Long createdDate,
        final Long addedDateTime,
        final UUID postId
    );
}
