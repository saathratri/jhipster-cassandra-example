package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.SaathratriEntity3TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaathratriEntity3Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity3.class);
        SaathratriEntity3 saathratriEntity31 = getSaathratriEntity3Sample1();
        SaathratriEntity3 saathratriEntity32 = new SaathratriEntity3();
        assertThat(saathratriEntity31).isNotEqualTo(saathratriEntity32);

        saathratriEntity32.setEntityType(saathratriEntity31.getEntityType());
        assertThat(saathratriEntity31).isEqualTo(saathratriEntity32);

        saathratriEntity32 = getSaathratriEntity3Sample2();
        assertThat(saathratriEntity31).isNotEqualTo(saathratriEntity32);
    }
}
