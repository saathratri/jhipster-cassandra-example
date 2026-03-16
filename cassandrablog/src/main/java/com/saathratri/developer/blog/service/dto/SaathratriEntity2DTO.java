package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.SaathratriEntity2} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntity2DTO implements Serializable {

    private SaathratriEntity2DTOId compositeId;
    private String entityName;
    private String entityDescription;
    private BigDecimal entityCost;
    private Long departureDate;

    public SaathratriEntity2DTO() {
        // Empty constructor needed for Jackson.
    }

    public SaathratriEntity2DTOId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(SaathratriEntity2DTOId compositeId) {
        this.compositeId = compositeId;
    }

    public SaathratriEntity2DTO compositeId(SaathratriEntity2DTOId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public SaathratriEntity2DTO entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    public SaathratriEntity2DTO entityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
        return this;
    }

    public BigDecimal getEntityCost() {
        return entityCost;
    }

    public void setEntityCost(BigDecimal entityCost) {
        this.entityCost = entityCost;
    }

    public SaathratriEntity2DTO entityCost(BigDecimal entityCost) {
        this.entityCost = entityCost;
        return this;
    }

    public Long getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Long departureDate) {
        this.departureDate = departureDate;
    }

    public SaathratriEntity2DTO departureDate(Long departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaathratriEntity2DTO)) return false;

        SaathratriEntity2DTO that = (SaathratriEntity2DTO) o;
        return Objects.equals(getCompositeId(), that.getCompositeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity2DTO {" +"compositeId = " + getCompositeId() +
            ", entityName='" + getEntityName() + "'" +
            ", entityDescription='" + getEntityDescription() + "'" +
            ", entityCost=" + getEntityCost() +
            ", departureDate=" + getDepartureDate() +
            "}";
    }
}
