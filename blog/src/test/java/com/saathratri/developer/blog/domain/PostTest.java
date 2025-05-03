package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.PostTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Post.class);
        Post post1 = getPostSample1();
        post1.setCompositeId(new PostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));
        Post post2 = new Post();
        assertThat(post1).isNotEqualTo(post2);

        post2.setCompositeId(post1.getCompositeId());
        assertThat(post1).isEqualTo(post2);
        post2.setCompositeId(new PostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));
        post2 = getPostSample2();
        assertThat(post1).isNotEqualTo(post2);
    }
}
