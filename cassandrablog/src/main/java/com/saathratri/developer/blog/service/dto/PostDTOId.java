package com.saathratri.developer.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.blog.domain.PostDTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostDTOId implements Serializable {

    private Long createdDate;
    private Long addedDateTime;
    private UUID postId;

    public PostDTOId() {}

    public PostDTOId(Long createdDate, Long addedDateTime, UUID postId) {
        this.createdDate = createdDate;
        this.addedDateTime = addedDateTime;
        this.postId = postId;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(Long addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (this.createdDate == null && this.addedDateTime == null && this.postId == null) {
            return false;
        }
        return (
            Objects.equals(this.createdDate, postDTO.getCompositeId().getCreatedDate()) &&
            Objects.equals(this.addedDateTime, postDTO.getCompositeId().getAddedDateTime()) &&
            Objects.equals(this.postId, postDTO.getCompositeId().getPostId())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.createdDate, this.addedDateTime, this.postId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTOId { " +
            "createdDate=" + getCreatedDate() +
            ",addedDateTime=" + getAddedDateTime() +
            ",postId='" + getPostId() + "'" +
            " }";
    }
}
