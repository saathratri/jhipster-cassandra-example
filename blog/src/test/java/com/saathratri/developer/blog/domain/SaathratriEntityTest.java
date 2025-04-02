package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.SaathratriEntityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaathratriEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity.class);
        SaathratriEntity saathratriEntity1 = getSaathratriEntitySample1();
        SaathratriEntity saathratriEntity2 = new SaathratriEntity();
        assertThat(saathratriEntity1).isNotEqualTo(saathratriEntity2);

        saathratriEntity2.setEntityId(saathratriEntity1.getEntityId());
        assertThat(saathratriEntity1).isEqualTo(saathratriEntity2);
        saathratriEntity2 = getSaathratriEntitySample2();
        assertThat(saathratriEntity1).isNotEqualTo(saathratriEntity2);
    }
}
