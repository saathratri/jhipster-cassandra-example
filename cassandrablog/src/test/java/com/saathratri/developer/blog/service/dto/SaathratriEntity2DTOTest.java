package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class SaathratriEntity2DTOTest {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity2DTO.class);
        SaathratriEntity2DTO saathratriEntity2DTO1 = new SaathratriEntity2DTO();
        saathratriEntity2DTO1.setCompositeId(
            new SaathratriEntity2DTOId(UUID.randomUUID(), longCount.incrementAndGet(), longCount.incrementAndGet(), UUID.randomUUID())
        );
        SaathratriEntity2DTO saathratriEntity2DTO2 = new SaathratriEntity2DTO();
        assertThat(saathratriEntity2DTO1).isNotEqualTo(saathratriEntity2DTO2);
        saathratriEntity2DTO2.setCompositeId(saathratriEntity2DTO1.getCompositeId());
        assertThat(saathratriEntity2DTO1).isEqualTo(saathratriEntity2DTO2);
        saathratriEntity2DTO2.setCompositeId(
            new SaathratriEntity2DTOId(UUID.randomUUID(), longCount.incrementAndGet(), longCount.incrementAndGet(), UUID.randomUUID())
        );
        assertThat(saathratriEntity2DTO1).isNotEqualTo(saathratriEntity2DTO2);
        saathratriEntity2DTO1.setCompositeId(null);
        assertThat(saathratriEntity2DTO1).isNotEqualTo(saathratriEntity2DTO2);
    }
}
