package com.saathratri.developer.blog.domain;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class SaathratriEntity2Id implements java.io.Serializable {

    @PrimaryKeyColumn(name = "entity_type_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID entityTypeId;

    @PrimaryKeyColumn(name = "year_of_date_added", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long yearOfDateAdded;

    @PrimaryKeyColumn(name = "arrival_date", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long arrivalDate;

    @PrimaryKeyColumn(name = "blog_id", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.TIMEUUID)
    private UUID blogId;

    public SaathratriEntity2Id() {}

    public SaathratriEntity2Id(UUID entityTypeId, Long yearOfDateAdded, Long arrivalDate, UUID blogId) {
        this.entityTypeId = entityTypeId;
        this.yearOfDateAdded = yearOfDateAdded;
        this.arrivalDate = arrivalDate;
        this.blogId = blogId;
    }

    public UUID getEntityTypeId() {
        return this.entityTypeId;
    }

    public void setEntityTypeId(UUID entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public SaathratriEntity2Id entityTypeId(UUID entityTypeId) {
        this.entityTypeId = entityTypeId;
        return this;
    }

    public Long getYearOfDateAdded() {
        return this.yearOfDateAdded;
    }

    public void setYearOfDateAdded(Long yearOfDateAdded) {
        this.yearOfDateAdded = yearOfDateAdded;
    }

    public SaathratriEntity2Id yearOfDateAdded(Long yearOfDateAdded) {
        this.yearOfDateAdded = yearOfDateAdded;
        return this;
    }

    public Long getArrivalDate() {
        return this.arrivalDate;
    }

    public void setArrivalDate(Long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public SaathratriEntity2Id arrivalDate(Long arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public UUID getBlogId() {
        return this.blogId;
    }

    public void setBlogId(UUID blogId) {
        this.blogId = blogId;
    }

    public SaathratriEntity2Id blogId(UUID blogId) {
        this.blogId = blogId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaathratriEntity2Id)) {
            return false;
        }

        SaathratriEntity2Id saathratriEntity2Id = (SaathratriEntity2Id) o;
        return (
            Objects.equals(entityTypeId, saathratriEntity2Id.entityTypeId) &&
            Objects.equals(yearOfDateAdded, saathratriEntity2Id.yearOfDateAdded) &&
            Objects.equals(arrivalDate, saathratriEntity2Id.arrivalDate) &&
            Objects.equals(blogId, saathratriEntity2Id.blogId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityTypeId, yearOfDateAdded, arrivalDate, blogId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity2Id { " +
                "entityTypeId='" + getEntityTypeId() + "'" +
                ",yearOfDateAdded=" + getYearOfDateAdded() +
                ",arrivalDate=" + getArrivalDate() +
                ",blogId='" + getBlogId() + "'" +
                " }";
    }
}
