package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.Post;
import com.saathratri.developer.blog.domain.PostId;
import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the Post entity.
 */
@Repository
public interface PostRepository extends CassandraRepository<Post, PostId> {
    List<Post> findAllByCompositeIdCreatedDate(final Long createdDate);

    List<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(final Long createdDate, final Long addedDateTime);

    List<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(final Long createdDate, final Long addedDateTime);

    List<Post> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(final Long createdDate, final Long addedDateTime);
}
