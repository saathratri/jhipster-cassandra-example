package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class PostDTOTest {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostDTO.class);
        PostDTO postDTO1 = new PostDTO();
        postDTO1.setCompositeId(new PostDTOId(longCount.incrementAndGet(), longCount.incrementAndGet(), UUID.randomUUID()));
        PostDTO postDTO2 = new PostDTO();
        assertThat(postDTO1).isNotEqualTo(postDTO2);
        postDTO2.setCompositeId(postDTO1.getCompositeId());
        assertThat(postDTO1).isEqualTo(postDTO2);
        postDTO2.setCompositeId(new PostDTOId(longCount.incrementAndGet(), longCount.incrementAndGet(), UUID.randomUUID()));
        assertThat(postDTO1).isNotEqualTo(postDTO2);
        postDTO1.setCompositeId(null);
        assertThat(postDTO1).isNotEqualTo(postDTO2);
    }
}
