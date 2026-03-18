package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.SaathratriEntity2;
import com.saathratri.developer.blog.domain.SaathratriEntity2Id;
import java.util.List;
import java.util.Optional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the SaathratriEntity2 entity.
 */
@Repository
public interface SaathratriEntity2Repository extends CassandraRepository<SaathratriEntity2, SaathratriEntity2Id> {
    List<SaathratriEntity2> findAllByCompositeIdEntityTypeId(final UUID entityTypeId);
    Slice<SaathratriEntity2> findAllByCompositeIdEntityTypeId(final UUID entityTypeId, Pageable pageable);
    List<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(
        final UUID entityTypeId,
        final Long yearOfDateAdded
    );
    Slice<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        Pageable pageable
    );
    List<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<SaathratriEntity2> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    Optional<SaathratriEntity2> findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    List<
        SaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        SaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );
    List<
        SaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        SaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );
    List<
        SaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        SaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );
    List<
        SaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        SaathratriEntity2
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );

    @Query("SELECT * FROM saathratri_entity_2 WHERE entity_type_id = ?0 AND year_of_date_added = ?1 AND arrival_date = ?2 LIMIT 1")
    Optional<SaathratriEntity2> findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
}
