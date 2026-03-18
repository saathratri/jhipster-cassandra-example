package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.PostId;
import com.saathratri.developer.blog.service.dto.PostDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

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
    Slice<PostDTO> findAllByCompositeIdCreatedDatePageable(final Long createdDate, Pageable pageable);
    List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(final Long createdDate, final Long addedDateTime);
    Slice<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(final Long createdDate, final Long addedDateTime);
    Slice<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(final Long createdDate, final Long addedDateTime);
    Slice<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(final Long createdDate, final Long addedDateTime);
    Slice<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(
        final Long createdDate,
        final Long addedDateTime
    );
    Slice<PostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    Optional<PostDTO> findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
        final Long createdDate,
        final Long addedDateTime,
        final UUID postId
    );
}
