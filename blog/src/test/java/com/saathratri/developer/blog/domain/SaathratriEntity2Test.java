package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.SaathratriEntity2TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaathratriEntity2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity2.class);
        SaathratriEntity2 saathratriEntity21 = getSaathratriEntity2Sample1();
        SaathratriEntity2 saathratriEntity22 = new SaathratriEntity2();
        assertThat(saathratriEntity21).isNotEqualTo(saathratriEntity22);

        saathratriEntity22.setEntityTypeId(saathratriEntity21.getEntityTypeId());
        assertThat(saathratriEntity21).isEqualTo(saathratriEntity22);

        saathratriEntity22 = getSaathratriEntity2Sample2();
        assertThat(saathratriEntity21).isNotEqualTo(saathratriEntity22);
    }
}
