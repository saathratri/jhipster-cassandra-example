package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.SetEntityByOrganizationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SetEntityByOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SetEntityByOrganization.class);
        SetEntityByOrganization setEntityByOrganization1 = getSetEntityByOrganizationSample1();
        SetEntityByOrganization setEntityByOrganization2 = new SetEntityByOrganization();
        assertThat(setEntityByOrganization1).isNotEqualTo(setEntityByOrganization2);

        setEntityByOrganization2.setOrganizationId(setEntityByOrganization1.getOrganizationId());
        assertThat(setEntityByOrganization1).isEqualTo(setEntityByOrganization2);
        setEntityByOrganization2 = getSetEntityByOrganizationSample2();
        assertThat(setEntityByOrganization1).isNotEqualTo(setEntityByOrganization2);
    }
}
