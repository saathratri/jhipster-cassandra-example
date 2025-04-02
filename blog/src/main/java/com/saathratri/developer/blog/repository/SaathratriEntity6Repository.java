package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.SaathratriEntity6;
import com.saathratri.developer.blog.domain.SaathratriEntity6Id;
import java.util.List;
import java.util.Optional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the SaathratriEntity6 entity.
 */
@Repository
public interface SaathratriEntity6Repository extends CassandraRepository<SaathratriEntity6, SaathratriEntity6Id> {
    List<SaathratriEntity6> findAllByCompositeIdOrganizationId(final UUID organizationId);
    List<SaathratriEntity6> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(final UUID organizationId, final Long arrivalDate);
    List<SaathratriEntity6> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<SaathratriEntity6> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    List<SaathratriEntity6> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
    Optional<
        SaathratriEntity6
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    List<
        SaathratriEntity6
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    List<
        SaathratriEntity6
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );

    @Query("SELECT * FROM saathratri_entity_6 WHERE  LIMIT 1")
    Optional<SaathratriEntity6> findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
}
