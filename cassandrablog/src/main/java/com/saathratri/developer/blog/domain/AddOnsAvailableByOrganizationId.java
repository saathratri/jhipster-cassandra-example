package com.saathratri.developer.blog.domain;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class AddOnsAvailableByOrganizationId implements java.io.Serializable {

    @PrimaryKeyColumn(name = "organization_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID organizationId;

    @PrimaryKeyColumn(name = "entity_type", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String entityType;

    @PrimaryKeyColumn(name = "entity_id", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID entityId;

    @PrimaryKeyColumn(name = "add_on_id", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID addOnId;

    public AddOnsAvailableByOrganizationId() {}

    public AddOnsAvailableByOrganizationId(UUID organizationId, String entityType, UUID entityId, UUID addOnId) {
        this.organizationId = organizationId;
        this.entityType = entityType;
        this.entityId = entityId;
        this.addOnId = addOnId;
    }

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public AddOnsAvailableByOrganizationId organizationId(UUID organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public AddOnsAvailableByOrganizationId entityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public UUID getEntityId() {
        return this.entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public AddOnsAvailableByOrganizationId entityId(UUID entityId) {
        this.entityId = entityId;
        return this;
    }

    public UUID getAddOnId() {
        return this.addOnId;
    }

    public void setAddOnId(UUID addOnId) {
        this.addOnId = addOnId;
    }

    public AddOnsAvailableByOrganizationId addOnId(UUID addOnId) {
        this.addOnId = addOnId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddOnsAvailableByOrganizationId)) {
            return false;
        }

        AddOnsAvailableByOrganizationId addOnsAvailableByOrganizationId = (AddOnsAvailableByOrganizationId) o;
        return (
            Objects.equals(organizationId, addOnsAvailableByOrganizationId.organizationId) &&
            Objects.equals(entityType, addOnsAvailableByOrganizationId.entityType) &&
            Objects.equals(entityId, addOnsAvailableByOrganizationId.entityId) &&
            Objects.equals(addOnId, addOnsAvailableByOrganizationId.addOnId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, entityType, entityId, addOnId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddOnsAvailableByOrganizationId { " +
                "organizationId='" + getOrganizationId() + "'" +
                ",entityType='" + getEntityType() + "'" +
                ",entityId='" + getEntityId() + "'" +
                ",addOnId='" + getAddOnId() + "'" +
                " }";
    }
}
