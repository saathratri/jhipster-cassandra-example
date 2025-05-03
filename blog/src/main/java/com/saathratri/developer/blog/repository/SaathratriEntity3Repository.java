package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.SaathratriEntity3;
import com.saathratri.developer.blog.domain.SaathratriEntity3Id;
import java.util.List;
import java.util.Optional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the SaathratriEntity3 entity.
 */
@Repository
public interface SaathratriEntity3Repository extends CassandraRepository<SaathratriEntity3, SaathratriEntity3Id> {
    List<SaathratriEntity3> findAllByCompositeIdEntityType(final String entityType);
    Optional<SaathratriEntity3> findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(final String entityType, final UUID createdTimeId);
    List<SaathratriEntity3> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(
        final String entityType,
        final UUID createdTimeId
    );
    List<SaathratriEntity3> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(
        final String entityType,
        final UUID createdTimeId
    );

    @Query("SELECT * FROM saathratri_entity_3 WHERE  LIMIT 1")
    Optional<SaathratriEntity3> findLatestByCompositeIdEntityType(final String entityType);
}
