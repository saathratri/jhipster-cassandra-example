package com.saathratri.developer.blog.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A Blog.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("blog")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String category;

    private UUID blogId;

    @NotNull
    @Size(min = 3)
    @Column("handle")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String handle;

    @NotNull
    @Column("content")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String content;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCategory() {
        return this.category;
    }

    public Blog category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UUID getBlogId() {
        return this.blogId;
    }

    public Blog blogId(UUID blogId) {
        this.setBlogId(blogId);
        return this;
    }

    public void setBlogId(UUID blogId) {
        this.blogId = blogId;
    }

    public String getHandle() {
        return this.handle;
    }

    public Blog handle(String handle) {
        this.setHandle(handle);
        return this;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getContent() {
        return this.content;
    }

    public Blog content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Blog)) {
            return false;
        }
        return getCategory() != null && getCategory().equals(((Blog) o).getCategory());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Blog{" +
            "category=" + getCategory() +
            ", blogId='" + getBlogId() + "'" +
            ", handle='" + getHandle() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
