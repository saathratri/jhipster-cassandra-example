package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.LandingPageByOrganizationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LandingPageByOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandingPageByOrganization.class);
        LandingPageByOrganization landingPageByOrganization1 = getLandingPageByOrganizationSample1();
        LandingPageByOrganization landingPageByOrganization2 = new LandingPageByOrganization();
        assertThat(landingPageByOrganization1).isNotEqualTo(landingPageByOrganization2);

        landingPageByOrganization2.setOrganizationId(landingPageByOrganization1.getOrganizationId());
        assertThat(landingPageByOrganization1).isEqualTo(landingPageByOrganization2);
        landingPageByOrganization2 = getLandingPageByOrganizationSample2();
        assertThat(landingPageByOrganization1).isNotEqualTo(landingPageByOrganization2);
    }
}
