package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganization;
import com.saathratri.developer.blog.domain.AddOnsAvailableByOrganizationId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the AddOnsAvailableByOrganization entity.
 */
@Repository
public interface AddOnsAvailableByOrganizationRepository
    extends CassandraRepository<AddOnsAvailableByOrganization, AddOnsAvailableByOrganizationId>
{
    @org.springframework.data.cassandra.repository.AllowFiltering
    List<AddOnsAvailableByOrganization> findAllByCompositeIdOrganizationId(final UUID organizationId);

    @org.springframework.data.cassandra.repository.AllowFiltering
    Slice<AddOnsAvailableByOrganization> findAllByCompositeIdOrganizationId(final UUID organizationId, Pageable pageable);

    @org.springframework.data.cassandra.repository.AllowFiltering
    List<AddOnsAvailableByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType
    );

    @org.springframework.data.cassandra.repository.AllowFiltering
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
