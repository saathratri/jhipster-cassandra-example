package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SaathratriEntityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntityDTO.class);
        SaathratriEntityDTO saathratriEntityDTO1 = new SaathratriEntityDTO();
        saathratriEntityDTO1.setEntityId(UUID.randomUUID());
        SaathratriEntityDTO saathratriEntityDTO2 = new SaathratriEntityDTO();
        assertThat(saathratriEntityDTO1).isNotEqualTo(saathratriEntityDTO2);
        saathratriEntityDTO2.setEntityId(saathratriEntityDTO1.getEntityId());
        assertThat(saathratriEntityDTO1).isEqualTo(saathratriEntityDTO2);
        saathratriEntityDTO2.setEntityId(UUID.randomUUID());
        assertThat(saathratriEntityDTO1).isNotEqualTo(saathratriEntityDTO2);
        saathratriEntityDTO1.setEntityId(null);
        assertThat(saathratriEntityDTO1).isNotEqualTo(saathratriEntityDTO2);
    }
}
