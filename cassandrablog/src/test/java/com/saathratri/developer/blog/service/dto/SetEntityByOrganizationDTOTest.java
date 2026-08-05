package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SetEntityByOrganizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SetEntityByOrganizationDTO.class);
        SetEntityByOrganizationDTO setEntityByOrganizationDTO1 = new SetEntityByOrganizationDTO();
        setEntityByOrganizationDTO1.setOrganizationId(UUID.randomUUID());
        SetEntityByOrganizationDTO setEntityByOrganizationDTO2 = new SetEntityByOrganizationDTO();
        assertThat(setEntityByOrganizationDTO1).isNotEqualTo(setEntityByOrganizationDTO2);
        setEntityByOrganizationDTO2.setOrganizationId(setEntityByOrganizationDTO1.getOrganizationId());
        assertThat(setEntityByOrganizationDTO1).isEqualTo(setEntityByOrganizationDTO2);
        setEntityByOrganizationDTO2.setOrganizationId(UUID.randomUUID());
        assertThat(setEntityByOrganizationDTO1).isNotEqualTo(setEntityByOrganizationDTO2);
        setEntityByOrganizationDTO1.setOrganizationId(null);
        assertThat(setEntityByOrganizationDTO1).isNotEqualTo(setEntityByOrganizationDTO2);
    }
}
