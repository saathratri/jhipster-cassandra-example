package com.saathratri.developer.blog.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A SaathratriEntity5.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("saathratri_entity_5")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntity5 implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID organizationId;

    private String entityType;

    private UUID entityId;

    private UUID addOnId;

    @Column("add_on_type")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String addOnType;

    @Column("add_on_details_text")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String addOnDetailsText;

    @Column("add_on_details_decimal")
    @CassandraType(type = CassandraType.Name.DECIMAL)
    private BigDecimal addOnDetailsDecimal;

    @Column("add_on_details_boolean")
    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private Boolean addOnDetailsBoolean;

    @Column("add_on_details_big_int")
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long addOnDetailsBigInt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public SaathratriEntity5 organizationId(UUID organizationId) {
        this.setOrganizationId(organizationId);
        return this;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public SaathratriEntity5 entityType(String entityType) {
        this.setEntityType(entityType);
        return this;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public UUID getEntityId() {
        return this.entityId;
    }

    public SaathratriEntity5 entityId(UUID entityId) {
        this.setEntityId(entityId);
        return this;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public UUID getAddOnId() {
        return this.addOnId;
    }

    public SaathratriEntity5 addOnId(UUID addOnId) {
        this.setAddOnId(addOnId);
        return this;
    }

    public void setAddOnId(UUID addOnId) {
        this.addOnId = addOnId;
    }

    public String getAddOnType() {
        return this.addOnType;
    }

    public SaathratriEntity5 addOnType(String addOnType) {
        this.setAddOnType(addOnType);
        return this;
    }

    public void setAddOnType(String addOnType) {
        this.addOnType = addOnType;
    }

    public String getAddOnDetailsText() {
        return this.addOnDetailsText;
    }

    public SaathratriEntity5 addOnDetailsText(String addOnDetailsText) {
        this.setAddOnDetailsText(addOnDetailsText);
        return this;
    }

    public void setAddOnDetailsText(String addOnDetailsText) {
        this.addOnDetailsText = addOnDetailsText;
    }

    public BigDecimal getAddOnDetailsDecimal() {
        return this.addOnDetailsDecimal;
    }

    public SaathratriEntity5 addOnDetailsDecimal(BigDecimal addOnDetailsDecimal) {
        this.setAddOnDetailsDecimal(addOnDetailsDecimal);
        return this;
    }

    public void setAddOnDetailsDecimal(BigDecimal addOnDetailsDecimal) {
        this.addOnDetailsDecimal = addOnDetailsDecimal;
    }

    public Boolean getAddOnDetailsBoolean() {
        return this.addOnDetailsBoolean;
    }

    public SaathratriEntity5 addOnDetailsBoolean(Boolean addOnDetailsBoolean) {
        this.setAddOnDetailsBoolean(addOnDetailsBoolean);
        return this;
    }

    public void setAddOnDetailsBoolean(Boolean addOnDetailsBoolean) {
        this.addOnDetailsBoolean = addOnDetailsBoolean;
    }

    public Long getAddOnDetailsBigInt() {
        return this.addOnDetailsBigInt;
    }

    public SaathratriEntity5 addOnDetailsBigInt(Long addOnDetailsBigInt) {
        this.setAddOnDetailsBigInt(addOnDetailsBigInt);
        return this;
    }

    public void setAddOnDetailsBigInt(Long addOnDetailsBigInt) {
        this.addOnDetailsBigInt = addOnDetailsBigInt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaathratriEntity5)) {
            return false;
        }
        return getOrganizationId() != null && getOrganizationId().equals(((SaathratriEntity5) o).getOrganizationId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity5{" +
            "organizationId=" + getOrganizationId() +
            ", entityType='" + getEntityType() + "'" +
            ", entityId='" + getEntityId() + "'" +
            ", addOnId='" + getAddOnId() + "'" +
            ", addOnType='" + getAddOnType() + "'" +
            ", addOnDetailsText='" + getAddOnDetailsText() + "'" +
            ", addOnDetailsDecimal=" + getAddOnDetailsDecimal() +
            ", addOnDetailsBoolean='" + getAddOnDetailsBoolean() + "'" +
            ", addOnDetailsBigInt=" + getAddOnDetailsBigInt() +
            "}";
    }
}
