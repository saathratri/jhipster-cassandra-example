package com.saathratri.developer.blog.domain;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class SaathratriEntity3Id implements java.io.Serializable {

    @PrimaryKeyColumn(name = "entity_type", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String entityType;

    @PrimaryKeyColumn(name = "created_time_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.TIMEUUID)
    private UUID createdTimeId;

    public SaathratriEntity3Id() {}

    public SaathratriEntity3Id(String entityType, UUID createdTimeId) {
        this.entityType = entityType;
        this.createdTimeId = createdTimeId;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public SaathratriEntity3Id entityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public UUID getCreatedTimeId() {
        return this.createdTimeId;
    }

    public void setCreatedTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
    }

    public SaathratriEntity3Id createdTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaathratriEntity3Id)) {
            return false;
        }

        SaathratriEntity3Id saathratriEntity3Id = (SaathratriEntity3Id) o;
        return (
            Objects.equals(entityType, saathratriEntity3Id.entityType) && Objects.equals(createdTimeId, saathratriEntity3Id.createdTimeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityType, createdTimeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity3Id { " +
                "entityType='" + getEntityType() + "'" +
                ",createdTimeId='" + getCreatedTimeId() + "'" +
                " }";
    }
}
