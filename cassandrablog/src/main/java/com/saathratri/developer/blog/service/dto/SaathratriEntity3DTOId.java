package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.blog.domain.SaathratriEntity3DTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntity3DTOId implements Serializable {

    private String entityType;
    private UUID createdTimeId;

    public SaathratriEntity3DTOId() {}

    public SaathratriEntity3DTOId(String entityType, UUID createdTimeId) {
        this.entityType = entityType;
        this.createdTimeId = createdTimeId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public UUID getCreatedTimeId() {
        return createdTimeId;
    }

    public void setCreatedTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaathratriEntity3DTO)) {
            return false;
        }

        SaathratriEntity3DTO saathratriEntity3DTO = (SaathratriEntity3DTO) o;
        if (this.entityType == null && this.createdTimeId == null) {
            return false;
        }
        return (
            Objects.equals(this.entityType, saathratriEntity3DTO.getCompositeId().getEntityType()) &&
            Objects.equals(this.createdTimeId, saathratriEntity3DTO.getCompositeId().getCreatedTimeId())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entityType, this.createdTimeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity3DTOId { " +
            "entityType='" + getEntityType() + "'" +
            ",createdTimeId='" + getCreatedTimeId() + "'" +
            " }";
    }
}
