package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.SaathratriEntity5;
import com.saathratri.developer.blog.domain.SaathratriEntity5Id;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the SaathratriEntity5 entity.
 */
@Repository
public interface SaathratriEntity5Repository extends CassandraRepository<SaathratriEntity5, SaathratriEntity5Id> {
    List<SaathratriEntity5> findAllByCompositeIdOrganizationId(final UUID organizationId);
    List<SaathratriEntity5> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(final UUID organizationId, final String entityType);
    List<SaathratriEntity5> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId
    );
    Optional<SaathratriEntity5> findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        final UUID addOnId
    );
}
