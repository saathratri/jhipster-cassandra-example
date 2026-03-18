package com.saathratri.developer.blog.domain;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class SaathratriEntity4Id implements java.io.Serializable {

    @PrimaryKeyColumn(name = "organization_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID organizationId;

    @PrimaryKeyColumn(name = "attribute_key", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String attributeKey;

    public SaathratriEntity4Id() {}

    public SaathratriEntity4Id(UUID organizationId, String attributeKey) {
        this.organizationId = organizationId;
        this.attributeKey = attributeKey;
    }

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public SaathratriEntity4Id organizationId(UUID organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public String getAttributeKey() {
        return this.attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public SaathratriEntity4Id attributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaathratriEntity4Id)) {
            return false;
        }

        SaathratriEntity4Id saathratriEntity4Id = (SaathratriEntity4Id) o;
        return (
            Objects.equals(organizationId, saathratriEntity4Id.organizationId) &&
            Objects.equals(attributeKey, saathratriEntity4Id.attributeKey)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, attributeKey);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity4Id { " +
                "organizationId='" + getOrganizationId() + "'" +
                ",attributeKey='" + getAttributeKey() + "'" +
                " }";
    }
}
