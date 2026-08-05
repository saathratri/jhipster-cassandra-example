package com.saathratri.developer.blog.domain;

import jakarta.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class PostId implements java.io.Serializable {

    @PrimaryKeyColumn(name = "created_date", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long createdDate;

    @PrimaryKeyColumn(name = "added_date_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long addedDateTime;

    @PrimaryKeyColumn(name = "post_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID postId;

    public PostId() {}

    public PostId(Long createdDate, Long addedDateTime, UUID postId) {
        this.createdDate = createdDate;
        this.addedDateTime = addedDateTime;
        this.postId = postId;
    }

    public Long getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public PostId createdDate(Long createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Long getAddedDateTime() {
        return this.addedDateTime;
    }

    public void setAddedDateTime(Long addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    public PostId addedDateTime(Long addedDateTime) {
        this.addedDateTime = addedDateTime;
        return this;
    }

    public UUID getPostId() {
        return this.postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public PostId postId(UUID postId) {
        this.postId = postId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostId)) {
            return false;
        }

        PostId postId = (PostId) o;
        return (
            Objects.equals(createdDate, postId.createdDate) &&
            Objects.equals(addedDateTime, postId.addedDateTime) &&
            Objects.equals(postId, postId.postId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDate, addedDateTime, postId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostId { " +
                "createdDate=" + getCreatedDate() +
                ",addedDateTime=" + getAddedDateTime() +
                ",postId='" + getPostId() + "'" +
                " }";
    }
}
