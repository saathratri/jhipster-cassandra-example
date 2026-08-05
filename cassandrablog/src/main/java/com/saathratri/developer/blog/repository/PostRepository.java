package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.Post;
import com.saathratri.developer.blog.domain.PostId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the Post entity.
 */
@Repository
public interface PostRepository extends CassandraRepository<Post, PostId> {
    List<Post> findAllByCompositeIdCreatedDate(final Long createdDate);
    Slice<Post> findAllByCompositeIdCreatedDate(final Long createdDate, Pageable pageable);
    List<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(final Long createdDate, final Long addedDateTime);
    Slice<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(final Long createdDate, final Long addedDateTime);
    Slice<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(final Long createdDate, final Long addedDateTime);
    Slice<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(final Long createdDate, final Long addedDateTime);
    Slice<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(final Long createdDate, final Long addedDateTime);
    Slice<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    Optional<Post> findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
        final Long createdDate,
        final Long addedDateTime,
        final UUID postId
    );
}
