package com.saathratri.developer.blog.domain;

import static com.saathratri.developer.blog.domain.AddOnsSelectedByOrganizationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AddOnsSelectedByOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddOnsSelectedByOrganization.class);
        AddOnsSelectedByOrganization addOnsSelectedByOrganization1 = getAddOnsSelectedByOrganizationSample1();
        addOnsSelectedByOrganization1.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );
        AddOnsSelectedByOrganization addOnsSelectedByOrganization2 = new AddOnsSelectedByOrganization();
        assertThat(addOnsSelectedByOrganization1).isNotEqualTo(addOnsSelectedByOrganization2);

        addOnsSelectedByOrganization2.setCompositeId(addOnsSelectedByOrganization1.getCompositeId());
        assertThat(addOnsSelectedByOrganization1).isEqualTo(addOnsSelectedByOrganization2);
        addOnsSelectedByOrganization2.setCompositeId(
            new AddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );
        addOnsSelectedByOrganization2 = getAddOnsSelectedByOrganizationSample2();
        assertThat(addOnsSelectedByOrganization1).isNotEqualTo(addOnsSelectedByOrganization2);
    }
}
