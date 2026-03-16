package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.Blog;
import com.saathratri.developer.blog.domain.BlogId;
import java.util.List;
import java.util.Optional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the Blog entity.
 */
@Repository
public interface BlogRepository extends CassandraRepository<Blog, BlogId> {
    List<Blog> findAllByCompositeIdCategory(final String category);
    Slice<Blog> findAllByCompositeIdCategory(final String category, Pageable pageable);
    Optional<Blog> findByCompositeIdCategoryAndCompositeIdBlogId(final String category, final UUID blogId);
    List<Blog> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(final String category, final UUID blogId);
    Slice<Blog> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(final String category, final UUID blogId, Pageable pageable);
    List<Blog> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(final String category, final UUID blogId);
    Slice<Blog> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(final String category, final UUID blogId, Pageable pageable);
    List<Blog> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(final String category, final UUID blogId);
    Slice<Blog> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(final String category, final UUID blogId, Pageable pageable);
    List<Blog> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(final String category, final UUID blogId);
    Slice<Blog> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(
        final String category,
        final UUID blogId,
        Pageable pageable
    );

    @Query("SELECT * FROM blog WHERE category = ?0 LIMIT 1")
    Optional<Blog> findLatestByCompositeIdCategory(final String category);
}
