package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.SaathratriEntity4;
import com.saathratri.developer.blog.domain.SaathratriEntity4Id;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the SaathratriEntity4 entity.
 */
@Repository
public interface SaathratriEntity4Repository extends CassandraRepository<SaathratriEntity4, SaathratriEntity4Id> {
    List<SaathratriEntity4> findAllByCompositeIdOrganizationId(final UUID organizationId);
}
