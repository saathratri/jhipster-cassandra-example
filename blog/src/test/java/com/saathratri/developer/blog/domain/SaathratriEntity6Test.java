package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.SaathratriEntity6TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaathratriEntity6Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaathratriEntity6.class);
        SaathratriEntity6 saathratriEntity61 = getSaathratriEntity6Sample1();
        SaathratriEntity6 saathratriEntity62 = new SaathratriEntity6();
        assertThat(saathratriEntity61).isNotEqualTo(saathratriEntity62);

        saathratriEntity62.setOrganizationId(saathratriEntity61.getOrganizationId());
        assertThat(saathratriEntity61).isEqualTo(saathratriEntity62);

        saathratriEntity62 = getSaathratriEntity6Sample2();
        assertThat(saathratriEntity61).isNotEqualTo(saathratriEntity62);
    }
}
