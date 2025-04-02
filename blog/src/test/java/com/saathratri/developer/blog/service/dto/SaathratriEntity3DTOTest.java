package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SaathratriEntity3DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity3DTO.class);
        SaathratriEntity3DTO saathratriEntity3DTO1 = new SaathratriEntity3DTO();
        saathratriEntity3DTO1.setCompositeId(new SaathratriEntity3DTOId("id1", UUID.randomUUID()));
        SaathratriEntity3DTO saathratriEntity3DTO2 = new SaathratriEntity3DTO();
        assertThat(saathratriEntity3DTO1).isNotEqualTo(saathratriEntity3DTO2);
        saathratriEntity3DTO2.setCompositeId(saathratriEntity3DTO1.getCompositeId());
        assertThat(saathratriEntity3DTO1).isEqualTo(saathratriEntity3DTO2);
        saathratriEntity3DTO2.setCompositeId(new SaathratriEntity3DTOId("id1", UUID.randomUUID()));
        assertThat(saathratriEntity3DTO1).isNotEqualTo(saathratriEntity3DTO2);
        saathratriEntity3DTO1.setCompositeId(null);
        assertThat(saathratriEntity3DTO1).isNotEqualTo(saathratriEntity3DTO2);
    }
}
