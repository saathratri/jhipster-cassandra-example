package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.SaathratriEntity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntityDTO implements Serializable {

    private UUID entityId;
    private String entityName;
    private String entityDescription;
    private BigDecimal entityCost;
    private UUID createdId;
    private UUID createdTimeId;

    public SaathratriEntityDTO() {
        // Empty constructor needed for Jackson.
    }

    public UUID getEntityId() {
        return this.entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public SaathratriEntityDTO entityId(UUID entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public SaathratriEntityDTO entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    public SaathratriEntityDTO entityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
        return this;
    }

    public BigDecimal getEntityCost() {
        return entityCost;
    }

    public void setEntityCost(BigDecimal entityCost) {
        this.entityCost = entityCost;
    }

    public SaathratriEntityDTO entityCost(BigDecimal entityCost) {
        this.entityCost = entityCost;
        return this;
    }

    public UUID getCreatedId() {
        return createdId;
    }

    public void setCreatedId(UUID createdId) {
        this.createdId = createdId;
    }

    public SaathratriEntityDTO createdId(UUID createdId) {
        this.createdId = createdId;
        return this;
    }

    public UUID getCreatedTimeId() {
        return createdTimeId;
    }

    public void setCreatedTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
    }

    public SaathratriEntityDTO createdTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaathratriEntityDTO)) return false;

        SaathratriEntityDTO that = (SaathratriEntityDTO) o;
        return Objects.equals(getEntityId(), that.getEntityId());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntityDTO {" +"entityId = " + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", entityDescription='" + getEntityDescription() + "'" +
            ", entityCost=" + getEntityCost() +
            ", createdId='" + getCreatedId() + "'" +
            ", createdTimeId='" + getCreatedTimeId() + "'" +
            "}";
    }
}
