package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.SaathratriEntity4} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntity4DTO implements Serializable {

    private SaathratriEntity4DTOId compositeId;
    private String attributeValue;

    public SaathratriEntity4DTO() {
        // Empty constructor needed for Jackson.
    }

    public SaathratriEntity4DTOId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(SaathratriEntity4DTOId compositeId) {
        this.compositeId = compositeId;
    }

    public SaathratriEntity4DTO compositeId(SaathratriEntity4DTOId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public SaathratriEntity4DTO attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaathratriEntity4DTO)) return false;

        SaathratriEntity4DTO that = (SaathratriEntity4DTO) o;
        return Objects.equals(getCompositeId(), that.getCompositeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity4DTO {" +"compositeId = " + getCompositeId() +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
