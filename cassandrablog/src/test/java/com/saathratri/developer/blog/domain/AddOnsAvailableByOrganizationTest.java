package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.AddOnsAvailableByOrganizationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AddOnsAvailableByOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddOnsAvailableByOrganization.class);
        AddOnsAvailableByOrganization addOnsAvailableByOrganization1 = getAddOnsAvailableByOrganizationSample1();
        addOnsAvailableByOrganization1.setCompositeId(
            new AddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        AddOnsAvailableByOrganization addOnsAvailableByOrganization2 = new AddOnsAvailableByOrganization();
        assertThat(addOnsAvailableByOrganization1).isNotEqualTo(addOnsAvailableByOrganization2);

        addOnsAvailableByOrganization2.setCompositeId(addOnsAvailableByOrganization1.getCompositeId());
        assertThat(addOnsAvailableByOrganization1).isEqualTo(addOnsAvailableByOrganization2);
        addOnsAvailableByOrganization2.setCompositeId(
            new AddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        addOnsAvailableByOrganization2 = getAddOnsAvailableByOrganizationSample2();
        assertThat(addOnsAvailableByOrganization1).isNotEqualTo(addOnsAvailableByOrganization2);
    }
}
