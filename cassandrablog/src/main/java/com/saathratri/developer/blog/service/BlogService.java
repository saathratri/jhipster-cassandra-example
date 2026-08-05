package com.saathratri.developer.blog.service;

import com.saathratri.developer.blog.domain.BlogId;
import com.saathratri.developer.blog.service.dto.BlogDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.blog.domain.Blog}.
 */
public interface BlogService {
    /**
     * Save a blog.
     *
     * @param blogDTO the entity to save.
     * @return the persisted entity.
     */
    BlogDTO save(BlogDTO blogDTO);

    /**
     * Updates a blog.
     *
     * @param blogDTO the entity to update.
     * @return the persisted entity.
     */
    BlogDTO update(BlogDTO blogDTO);

    /**
     * Partially updates a blog.
     *
     * @param blogDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BlogDTO> partialUpdate(BlogDTO blogDTO);

    /**
     * Get all the blogs.
     *
     * @return the list of entities.
     */
    List<BlogDTO> findAll();

    /**
     * Get the "id" blog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlogDTO> findOne(BlogId id);

    /**
     * Delete the "id" blog.
     *
     * @param id the id of the entity.
     */
    void delete(BlogId id);

    /**
     * Get all the blogs with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<BlogDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);

    List<BlogDTO> findAllByCompositeIdCategory(final String category);
    Slice<BlogDTO> findAllByCompositeIdCategoryPageable(final String category, Pageable pageable);
    Optional<BlogDTO> findByCompositeIdCategoryAndCompositeIdBlogId(final String category, final UUID blogId);
    List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(final String category, final UUID blogId);
    Slice<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    );
    List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(final String category, final UUID blogId);
    Slice<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    );
    List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(final String category, final UUID blogId);
    Slice<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    );
    List<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(final String category, final UUID blogId);
    Slice<BlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    );
    BlogDTO findLatestByCompositeIdCategory(final String category);
}
