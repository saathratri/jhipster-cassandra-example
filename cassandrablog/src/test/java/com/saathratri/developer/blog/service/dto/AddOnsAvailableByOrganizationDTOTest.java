package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AddOnsAvailableByOrganizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddOnsAvailableByOrganizationDTO.class);
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO1 = new AddOnsAvailableByOrganizationDTO();
        addOnsAvailableByOrganizationDTO1.setCompositeId(
            new AddOnsAvailableByOrganizationDTOId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        AddOnsAvailableByOrganizationDTO addOnsAvailableByOrganizationDTO2 = new AddOnsAvailableByOrganizationDTO();
        assertThat(addOnsAvailableByOrganizationDTO1).isNotEqualTo(addOnsAvailableByOrganizationDTO2);
        addOnsAvailableByOrganizationDTO2.setCompositeId(addOnsAvailableByOrganizationDTO1.getCompositeId());
        assertThat(addOnsAvailableByOrganizationDTO1).isEqualTo(addOnsAvailableByOrganizationDTO2);
        addOnsAvailableByOrganizationDTO2.setCompositeId(
            new AddOnsAvailableByOrganizationDTOId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        assertThat(addOnsAvailableByOrganizationDTO1).isNotEqualTo(addOnsAvailableByOrganizationDTO2);
        addOnsAvailableByOrganizationDTO1.setCompositeId(null);
        assertThat(addOnsAvailableByOrganizationDTO1).isNotEqualTo(addOnsAvailableByOrganizationDTO2);
    }
}
