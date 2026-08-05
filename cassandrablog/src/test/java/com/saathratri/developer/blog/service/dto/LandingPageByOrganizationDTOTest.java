package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LandingPageByOrganizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandingPageByOrganizationDTO.class);
        LandingPageByOrganizationDTO landingPageByOrganizationDTO1 = new LandingPageByOrganizationDTO();
        landingPageByOrganizationDTO1.setOrganizationId(UUID.randomUUID());
        LandingPageByOrganizationDTO landingPageByOrganizationDTO2 = new LandingPageByOrganizationDTO();
        assertThat(landingPageByOrganizationDTO1).isNotEqualTo(landingPageByOrganizationDTO2);
        landingPageByOrganizationDTO2.setOrganizationId(landingPageByOrganizationDTO1.getOrganizationId());
        assertThat(landingPageByOrganizationDTO1).isEqualTo(landingPageByOrganizationDTO2);
        landingPageByOrganizationDTO2.setOrganizationId(UUID.randomUUID());
        assertThat(landingPageByOrganizationDTO1).isNotEqualTo(landingPageByOrganizationDTO2);
        landingPageByOrganizationDTO1.setOrganizationId(null);
        assertThat(landingPageByOrganizationDTO1).isNotEqualTo(landingPageByOrganizationDTO2);
    }
}
