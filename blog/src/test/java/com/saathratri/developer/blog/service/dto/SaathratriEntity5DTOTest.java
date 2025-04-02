package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SaathratriEntity5DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity5DTO.class);
        SaathratriEntity5DTO saathratriEntity5DTO1 = new SaathratriEntity5DTO();
        saathratriEntity5DTO1.setCompositeId(new SaathratriEntity5DTOId(UUID.randomUUID(), "id1", UUID.randomUUID(), UUID.randomUUID()));
        SaathratriEntity5DTO saathratriEntity5DTO2 = new SaathratriEntity5DTO();
        assertThat(saathratriEntity5DTO1).isNotEqualTo(saathratriEntity5DTO2);
        saathratriEntity5DTO2.setCompositeId(saathratriEntity5DTO1.getCompositeId());
        assertThat(saathratriEntity5DTO1).isEqualTo(saathratriEntity5DTO2);
        saathratriEntity5DTO2.setCompositeId(new SaathratriEntity5DTOId(UUID.randomUUID(), "id1", UUID.randomUUID(), UUID.randomUUID()));
        assertThat(saathratriEntity5DTO1).isNotEqualTo(saathratriEntity5DTO2);
        saathratriEntity5DTO1.setCompositeId(null);
        assertThat(saathratriEntity5DTO1).isNotEqualTo(saathratriEntity5DTO2);
    }
}
