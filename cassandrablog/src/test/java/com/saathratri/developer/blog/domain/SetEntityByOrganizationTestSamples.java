package com.saathratri.developer.blog.domain;

import java.util.UUID;

public class SetEntityByOrganizationTestSamples {

    public static SetEntityByOrganization getSetEntityByOrganizationSample1() {
        return new SetEntityByOrganization()
            .organizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags1");
                    }
                }
            );
    }

    public static SetEntityByOrganization getSetEntityByOrganizationSample2() {
        return new SetEntityByOrganization()
            .organizationId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags2");
                    }
                }
            );
    }

    public static SetEntityByOrganization getSetEntityByOrganizationRandomSampleGenerator() {
        return new SetEntityByOrganization()
            .organizationId(UUID.randomUUID())
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags1");
                    }
                }
            );
    }
}
