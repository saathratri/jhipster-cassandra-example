package com.saathratri.developer.blog.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A Post.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("post")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long createdDate;

    private Long addedDateTime;

    private UUID postId;

    @NotNull
    @Column("title")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String title;

    @NotNull
    @Column("content")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String content;

    @Column("published_date_time")
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long publishedDateTime;

    @Column("sent_date")
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long sentDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getCreatedDate() {
        return this.createdDate;
    }

    public Post createdDate(Long createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getAddedDateTime() {
        return this.addedDateTime;
    }

    public Post addedDateTime(Long addedDateTime) {
        this.setAddedDateTime(addedDateTime);
        return this;
    }

    public void setAddedDateTime(Long addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    public UUID getPostId() {
        return this.postId;
    }

    public Post postId(UUID postId) {
        this.setPostId(postId);
        return this;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return this.title;
    }

    public Post title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public Post content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPublishedDateTime() {
        return this.publishedDateTime;
    }

    public Post publishedDateTime(Long publishedDateTime) {
        this.setPublishedDateTime(publishedDateTime);
        return this;
    }

    public void setPublishedDateTime(Long publishedDateTime) {
        this.publishedDateTime = publishedDateTime;
    }

    public Long getSentDate() {
        return this.sentDate;
    }

    public Post sentDate(Long sentDate) {
        this.setSentDate(sentDate);
        return this;
    }

    public void setSentDate(Long sentDate) {
        this.sentDate = sentDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return getCreatedDate() != null && getCreatedDate().equals(((Post) o).getCreatedDate());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "createdDate=" + getCreatedDate() +
            ", addedDateTime=" + getAddedDateTime() +
            ", postId='" + getPostId() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", publishedDateTime=" + getPublishedDateTime() +
            ", sentDate=" + getSentDate() +
            "}";
    }
}
