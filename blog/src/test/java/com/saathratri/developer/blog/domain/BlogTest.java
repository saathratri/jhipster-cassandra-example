package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.BlogTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BlogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Blog.class);
        Blog blog1 = getBlogSample1();
        blog1.setCompositeId(new BlogId(UUID.randomUUID().toString(), UUID.randomUUID()));
        Blog blog2 = new Blog();
        assertThat(blog1).isNotEqualTo(blog2);

        blog2.setCompositeId(blog1.getCompositeId());
        assertThat(blog1).isEqualTo(blog2);
        blog2.setCompositeId(new BlogId(UUID.randomUUID().toString(), UUID.randomUUID()));
        blog2 = getBlogSample2();
        assertThat(blog1).isNotEqualTo(blog2);
    }
}
