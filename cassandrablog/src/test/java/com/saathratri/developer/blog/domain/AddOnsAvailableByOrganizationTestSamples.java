package com.saathratri.developer.blog.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AddOnsAvailableByOrganizationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AddOnsAvailableByOrganization getAddOnsAvailableByOrganizationSample1() {
        return new AddOnsAvailableByOrganization()
            .compositeId(
                new AddOnsAvailableByOrganizationId()
                    .organizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
                    .entityType("entityType1")
                    .entityId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
                    .addOnId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            )
            .addOnType("addOnType1")
            .addOnDetailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("addOnDetailsText1", "addOnDetailsText1");
                    }
                }
            )
            .addOnDetailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("addOnDetailsBigInt1", 1L);
                    }
                }
            );
    }

    public static AddOnsAvailableByOrganization getAddOnsAvailableByOrganizationSample2() {
        return new AddOnsAvailableByOrganization()
            .compositeId(
                new AddOnsAvailableByOrganizationId()
                    .organizationId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
                    .entityType("entityType2")
                    .entityId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
                    .addOnId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            )
            .addOnType("addOnType1")
            .addOnDetailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("addOnDetailsText1", "addOnDetailsText1");
                    }
                }
            )
            .addOnDetailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("addOnDetailsBigInt1", 1L);
                    }
                }
            );
    }

    public static AddOnsAvailableByOrganization getAddOnsAvailableByOrganizationRandomSampleGenerator() {
        return new AddOnsAvailableByOrganization()
            .compositeId(
                new AddOnsAvailableByOrganizationId()
                    .organizationId(UUID.randomUUID())
                    .entityType(UUID.randomUUID().toString())
                    .entityId(UUID.randomUUID())
                    .addOnId(UUID.randomUUID())
            )
            .addOnType("addOnType1")
            .addOnDetailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("addOnDetailsText1", "addOnDetailsText1");
                    }
                }
            )
            .addOnDetailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("addOnDetailsBigInt1", 1L);
                    }
                }
            );
    }
}
