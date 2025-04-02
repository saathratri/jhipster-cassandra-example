package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BlogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogDTO.class);
        BlogDTO blogDTO1 = new BlogDTO();
        blogDTO1.setCompositeId(new BlogDTOId("id1", UUID.randomUUID()));
        BlogDTO blogDTO2 = new BlogDTO();
        assertThat(blogDTO1).isNotEqualTo(blogDTO2);
        blogDTO2.setCompositeId(blogDTO1.getCompositeId());
        assertThat(blogDTO1).isEqualTo(blogDTO2);
        blogDTO2.setCompositeId(new BlogDTOId("id1", UUID.randomUUID()));
        assertThat(blogDTO1).isNotEqualTo(blogDTO2);
        blogDTO1.setCompositeId(null);
        assertThat(blogDTO1).isNotEqualTo(blogDTO2);
    }
}
