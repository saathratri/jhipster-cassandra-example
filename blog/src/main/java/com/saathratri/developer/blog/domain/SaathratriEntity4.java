package com.saathratri.developer.blog.domain;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A SaathratriEntity4.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("saathratri_entity_4")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntity4 implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID organizationId;

    private String attributeKey;

    @Column("attribute_value")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String attributeValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public SaathratriEntity4 organizationId(UUID organizationId) {
        this.setOrganizationId(organizationId);
        return this;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public String getAttributeKey() {
        return this.attributeKey;
    }

    public SaathratriEntity4 attributeKey(String attributeKey) {
        this.setAttributeKey(attributeKey);
        return this;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public SaathratriEntity4 attributeValue(String attributeValue) {
        this.setAttributeValue(attributeValue);
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaathratriEntity4)) {
            return false;
        }
        return getOrganizationId() != null && getOrganizationId().equals(((SaathratriEntity4) o).getOrganizationId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity4{" +
            "organizationId=" + getOrganizationId() +
            ", attributeKey='" + getAttributeKey() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
