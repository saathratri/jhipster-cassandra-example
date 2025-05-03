package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.AddOnsSelectedByOrganization;
import com.saathratri.developer.blog.domain.AddOnsSelectedByOrganizationId;
import java.util.List;
import java.util.Optional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the AddOnsSelectedByOrganization entity.
 */
@Repository
public interface AddOnsSelectedByOrganizationRepository
    extends CassandraRepository<AddOnsSelectedByOrganization, AddOnsSelectedByOrganizationId> {
    List<AddOnsSelectedByOrganization> findAllByCompositeIdOrganizationId(final UUID organizationId);
    List<AddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<AddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<AddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<AddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
    Optional<
        AddOnsSelectedByOrganization
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    List<
        AddOnsSelectedByOrganization
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    List<
        AddOnsSelectedByOrganization
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );

    @Query("SELECT * FROM add_ons_selected_by_organization WHERE  LIMIT 1")
    Optional<AddOnsSelectedByOrganization> findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
}
