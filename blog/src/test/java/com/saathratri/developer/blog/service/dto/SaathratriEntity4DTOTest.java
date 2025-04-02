package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SaathratriEntity4DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity4DTO.class);
        SaathratriEntity4DTO saathratriEntity4DTO1 = new SaathratriEntity4DTO();
        saathratriEntity4DTO1.setCompositeId(new SaathratriEntity4DTOId(UUID.randomUUID(), "id1"));
        SaathratriEntity4DTO saathratriEntity4DTO2 = new SaathratriEntity4DTO();
        assertThat(saathratriEntity4DTO1).isNotEqualTo(saathratriEntity4DTO2);
        saathratriEntity4DTO2.setCompositeId(saathratriEntity4DTO1.getCompositeId());
        assertThat(saathratriEntity4DTO1).isEqualTo(saathratriEntity4DTO2);
        saathratriEntity4DTO2.setCompositeId(new SaathratriEntity4DTOId(UUID.randomUUID(), "id1"));
        assertThat(saathratriEntity4DTO1).isNotEqualTo(saathratriEntity4DTO2);
        saathratriEntity4DTO1.setCompositeId(null);
        assertThat(saathratriEntity4DTO1).isNotEqualTo(saathratriEntity4DTO2);
    }
}
