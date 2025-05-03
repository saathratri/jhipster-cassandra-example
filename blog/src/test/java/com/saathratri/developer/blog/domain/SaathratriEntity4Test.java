package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.SaathratriEntity4TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SaathratriEntity4Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity4.class);
        SaathratriEntity4 saathratriEntity41 = getSaathratriEntity4Sample1();
        saathratriEntity41.setCompositeId(new SaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));
        SaathratriEntity4 saathratriEntity42 = new SaathratriEntity4();
        assertThat(saathratriEntity41).isNotEqualTo(saathratriEntity42);

        saathratriEntity42.setCompositeId(saathratriEntity41.getCompositeId());
        assertThat(saathratriEntity41).isEqualTo(saathratriEntity42);
        saathratriEntity42.setCompositeId(new SaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));
        saathratriEntity42 = getSaathratriEntity4Sample2();
        assertThat(saathratriEntity41).isNotEqualTo(saathratriEntity42);
    }
}
