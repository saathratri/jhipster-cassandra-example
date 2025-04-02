package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.SaathratriEntity3} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntity3DTO implements Serializable {

    private SaathratriEntity3DTOId compositeId;
    private String entityName;
    private String entityDescription;
    private BigDecimal entityCost;
    private Long departureDate;
    private Set<String> tags;

    public SaathratriEntity3DTO() {
        // Empty constructor needed for Jackson.
    }

    public SaathratriEntity3DTOId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(SaathratriEntity3DTOId compositeId) {
        this.compositeId = compositeId;
    }

    public SaathratriEntity3DTO compositeId(SaathratriEntity3DTOId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public SaathratriEntity3DTO entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    public SaathratriEntity3DTO entityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
        return this;
    }

    public BigDecimal getEntityCost() {
        return entityCost;
    }

    public void setEntityCost(BigDecimal entityCost) {
        this.entityCost = entityCost;
    }

    public SaathratriEntity3DTO entityCost(BigDecimal entityCost) {
        this.entityCost = entityCost;
        return this;
    }

    public Long getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Long departureDate) {
        this.departureDate = departureDate;
    }

    public SaathratriEntity3DTO departureDate(Long departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public SaathratriEntity3DTO tags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaathratriEntity3DTO)) return false;

        SaathratriEntity3DTO that = (SaathratriEntity3DTO) o;
        return Objects.equals(getCompositeId(), that.getCompositeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity3DTO {" +"compositeId = " + getCompositeId() +
            ", entityName='" + getEntityName() + "'" +
            ", entityDescription='" + getEntityDescription() + "'" +
            ", entityCost=" + getEntityCost() +
            ", departureDate=" + getDepartureDate() +
            ", tags='" + getTags() + "'" +
            "}";
    }
}
