package com.saathratri.developer.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.Blog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BlogDTO implements Serializable {

    private BlogDTOId compositeId;
    private String handle;
    private String content;

    public BlogDTO() {
        // Empty constructor needed for Jackson.
    }

    public BlogDTOId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(BlogDTOId compositeId) {
        this.compositeId = compositeId;
    }

    public BlogDTO compositeId(BlogDTOId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public BlogDTO handle(String handle) {
        this.handle = handle;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BlogDTO content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogDTO)) return false;

        BlogDTO that = (BlogDTO) o;
        return Objects.equals(getCompositeId(), that.getCompositeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlogDTO {" +"compositeId = " + getCompositeId() +
            ", handle='" + getHandle() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
