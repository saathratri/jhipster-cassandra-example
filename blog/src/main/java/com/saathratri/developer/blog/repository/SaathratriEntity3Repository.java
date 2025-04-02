package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.SaathratriEntity3;
import com.saathratri.developer.blog.domain.SaathratriEntity3Id;
import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the SaathratriEntity3 entity.
 */
@Repository
public interface SaathratriEntity3Repository extends CassandraRepository<SaathratriEntity3, SaathratriEntity3Id> {
    List<SaathratriEntity3> findAllByCompositeIdEntityType(final String entityType);
}
