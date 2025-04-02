package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class SaathratriEntity6DTOTest {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity6DTO.class);
        SaathratriEntity6DTO saathratriEntity6DTO1 = new SaathratriEntity6DTO();
        saathratriEntity6DTO1.setCompositeId(
            new SaathratriEntity6DTOId(UUID.randomUUID(), longCount.incrementAndGet(), "id1", UUID.randomUUID())
        );
        SaathratriEntity6DTO saathratriEntity6DTO2 = new SaathratriEntity6DTO();
        assertThat(saathratriEntity6DTO1).isNotEqualTo(saathratriEntity6DTO2);
        saathratriEntity6DTO2.setCompositeId(saathratriEntity6DTO1.getCompositeId());
        assertThat(saathratriEntity6DTO1).isEqualTo(saathratriEntity6DTO2);
        saathratriEntity6DTO2.setCompositeId(
            new SaathratriEntity6DTOId(UUID.randomUUID(), longCount.incrementAndGet(), "id1", UUID.randomUUID())
        );
        assertThat(saathratriEntity6DTO1).isNotEqualTo(saathratriEntity6DTO2);
        saathratriEntity6DTO1.setCompositeId(null);
        assertThat(saathratriEntity6DTO1).isNotEqualTo(saathratriEntity6DTO2);
    }
}
