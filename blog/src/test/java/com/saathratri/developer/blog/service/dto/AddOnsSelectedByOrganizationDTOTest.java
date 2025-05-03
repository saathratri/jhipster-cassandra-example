package com.saathratri.developer.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class AddOnsSelectedByOrganizationDTOTest {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddOnsSelectedByOrganizationDTO.class);
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO1 = new AddOnsSelectedByOrganizationDTO();
        addOnsSelectedByOrganizationDTO1.setCompositeId(
            new AddOnsSelectedByOrganizationDTOId(UUID.randomUUID(), longCount.incrementAndGet(), 1L, UUID.randomUUID())
        );
        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO2 = new AddOnsSelectedByOrganizationDTO();
        assertThat(addOnsSelectedByOrganizationDTO1).isNotEqualTo(addOnsSelectedByOrganizationDTO2);
        addOnsSelectedByOrganizationDTO2.setCompositeId(addOnsSelectedByOrganizationDTO1.getCompositeId());
        assertThat(addOnsSelectedByOrganizationDTO1).isEqualTo(addOnsSelectedByOrganizationDTO2);
        addOnsSelectedByOrganizationDTO2.setCompositeId(
            new AddOnsSelectedByOrganizationDTOId(UUID.randomUUID(), longCount.incrementAndGet(), 1L, UUID.randomUUID())
        );
        assertThat(addOnsSelectedByOrganizationDTO1).isNotEqualTo(addOnsSelectedByOrganizationDTO2);
        addOnsSelectedByOrganizationDTO1.setCompositeId(null);
        assertThat(addOnsSelectedByOrganizationDTO1).isNotEqualTo(addOnsSelectedByOrganizationDTO2);
    }
}
