package com.saathratri.developer.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.blog.domain.BlogDTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BlogDTOId implements Serializable {

    private String category;
    private UUID blogId;

    public BlogDTOId() {}

    public BlogDTOId(String category, UUID blogId) {
        this.category = category;
        this.blogId = blogId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        if (!(o instanceof BlogDTO)) {
            return false;
        }

        BlogDTO blogDTO = (BlogDTO) o;
        if (this.category == null && this.blogId == null) {
            return false;
        }
        return (
            Objects.equals(this.category, blogDTO.getCompositeId().getCategory()) &&
            Objects.equals(this.blogId, blogDTO.getCompositeId().getBlogId())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.category, this.blogId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlogDTOId { " +
            "category='" + getCategory() + "'" +
            ",blogId='" + getBlogId() + "'" +
            " }";
    }
}
