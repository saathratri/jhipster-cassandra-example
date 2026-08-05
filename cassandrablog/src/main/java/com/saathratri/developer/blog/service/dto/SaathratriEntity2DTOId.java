package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.blog.domain.SaathratriEntity2DTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntity2DTOId implements Serializable {

    private UUID entityTypeId;
    private Long yearOfDateAdded;
    private Long arrivalDate;
    private UUID blogId;

    public SaathratriEntity2DTOId() {}

    public SaathratriEntity2DTOId(UUID entityTypeId, Long yearOfDateAdded, Long arrivalDate, UUID blogId) {
        this.entityTypeId = entityTypeId;
        this.yearOfDateAdded = yearOfDateAdded;
        this.arrivalDate = arrivalDate;
        this.blogId = blogId;
    }

    public UUID getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(UUID entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public Long getYearOfDateAdded() {
        return yearOfDateAdded;
    }

    public void setYearOfDateAdded(Long yearOfDateAdded) {
        this.yearOfDateAdded = yearOfDateAdded;
    }

    public Long getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public UUID getBlogId() {
        return blogId;
    }

    public void setBlogId(UUID blogId) {
        this.blogId = blogId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaathratriEntity2DTO)) {
            return false;
        }

        SaathratriEntity2DTO saathratriEntity2DTO = (SaathratriEntity2DTO) o;
        if (this.entityTypeId == null && this.yearOfDateAdded == null && this.arrivalDate == null && this.blogId == null) {
            return false;
        }
        return (
            Objects.equals(this.entityTypeId, saathratriEntity2DTO.getCompositeId().getEntityTypeId()) &&
            Objects.equals(this.yearOfDateAdded, saathratriEntity2DTO.getCompositeId().getYearOfDateAdded()) &&
            Objects.equals(this.arrivalDate, saathratriEntity2DTO.getCompositeId().getArrivalDate()) &&
            Objects.equals(this.blogId, saathratriEntity2DTO.getCompositeId().getBlogId())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entityTypeId, this.yearOfDateAdded, this.arrivalDate, this.blogId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity2DTOId { " +
            "entityTypeId='" + getEntityTypeId() + "'" +
            ",yearOfDateAdded=" + getYearOfDateAdded() +
            ",arrivalDate=" + getArrivalDate() +
            ",blogId='" + getBlogId() + "'" +
            " }";
    }
}
