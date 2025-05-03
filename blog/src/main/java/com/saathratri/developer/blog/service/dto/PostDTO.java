package com.saathratri.developer.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.Post} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostDTO implements Serializable {

    private PostDTOId compositeId;
    private String title;
    private String content;
    private Long publishedDateTime;
    private Long sentDate;

    public PostDTO() {
        // Empty constructor needed for Jackson.
    }

    public PostDTOId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(PostDTOId compositeId) {
        this.compositeId = compositeId;
    }

    public PostDTO compositeId(PostDTOId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PostDTO title(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostDTO content(String content) {
        this.content = content;
        return this;
    }

    public Long getPublishedDateTime() {
        return publishedDateTime;
    }

    public void setPublishedDateTime(Long publishedDateTime) {
        this.publishedDateTime = publishedDateTime;
    }

    public PostDTO publishedDateTime(Long publishedDateTime) {
        this.publishedDateTime = publishedDateTime;
        return this;
    }

    public Long getSentDate() {
        return sentDate;
    }

    public void setSentDate(Long sentDate) {
        this.sentDate = sentDate;
    }

    public PostDTO sentDate(Long sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostDTO)) return false;

        PostDTO that = (PostDTO) o;
        return Objects.equals(getCompositeId(), that.getCompositeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO {" +"compositeId = " + getCompositeId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", publishedDateTime=" + getPublishedDateTime() +
            ", sentDate=" + getSentDate() +
            "}";
    }
}
