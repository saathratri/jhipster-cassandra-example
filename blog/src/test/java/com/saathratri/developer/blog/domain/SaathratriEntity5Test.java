package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.SaathratriEntity5TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SaathratriEntity5Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity5.class);
        SaathratriEntity5 saathratriEntity51 = getSaathratriEntity5Sample1();
        saathratriEntity51.setCompositeId(
            new SaathratriEntity5Id(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        SaathratriEntity5 saathratriEntity52 = new SaathratriEntity5();
        assertThat(saathratriEntity51).isNotEqualTo(saathratriEntity52);

        saathratriEntity52.setCompositeId(saathratriEntity51.getCompositeId());
        assertThat(saathratriEntity51).isEqualTo(saathratriEntity52);
        saathratriEntity52.setCompositeId(
            new SaathratriEntity5Id(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        saathratriEntity52 = getSaathratriEntity5Sample2();
        assertThat(saathratriEntity51).isNotEqualTo(saathratriEntity52);
    }
}
