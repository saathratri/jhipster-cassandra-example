package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganization;
import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganizationId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the AddOnsAvailableByOrganization entity.
 */
@Repository
public interface AddOnsAvailableByOrganizationRepository
    extends CassandraRepository<AddOnsAvailableByOrganization, AddOnsAvailableByOrganizationId>
{
    List<AddOnsAvailableByOrganization> findAllByCompositeIdOrganizationId(final UUID organizationId);
    Slice<AddOnsAvailableByOrganization> findAllByCompositeIdOrganizationId(final UUID organizationId, Pageable pageable);
    List<AddOnsAvailableByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType
    );
    Slice<AddOnsAvailableByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType,
        Pageable pageable
    );
    List<AddOnsAvailableByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId
    );
    Slice<AddOnsAvailableByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        Pageable pageable
    );
    Optional<
        AddOnsAvailableByOrganization
    > findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        final UUID addOnId
    );
}
