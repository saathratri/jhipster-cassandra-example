package com.saathratri.developer.blog.domain;

import jakarta.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class BlogId implements java.io.Serializable {

    @PrimaryKeyColumn(name = "category", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String category;

    @PrimaryKeyColumn(name = "blog_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.TIMEUUID)
    private UUID blogId;

    public BlogId() {}

    public BlogId(String category, UUID blogId) {
        this.category = category;
        this.blogId = blogId;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BlogId category(String category) {
        this.category = category;
        return this;
    }

    public UUID getBlogId() {
        return this.blogId;
    }

    public void setBlogId(UUID blogId) {
        this.blogId = blogId;
    }

    public BlogId blogId(UUID blogId) {
        this.blogId = blogId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogId)) {
            return false;
        }

        BlogId blogId = (BlogId) o;
        return Objects.equals(category, blogId.category) && Objects.equals(blogId, blogId.blogId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, blogId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlogId { " +
                "category='" + getCategory() + "'" +
                ",blogId='" + getBlogId() + "'" +
                " }";
    }
}
