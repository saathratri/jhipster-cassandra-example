package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.PostTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Post.class);
        Post post1 = getPostSample1();
        Post post2 = new Post();
        assertThat(post1).isNotEqualTo(post2);

        post2.setCreatedDate(post1.getCreatedDate());
        assertThat(post1).isEqualTo(post2);

        post2 = getPostSample2();
        assertThat(post1).isNotEqualTo(post2);
    }
}
