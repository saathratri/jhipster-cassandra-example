package com.saathratri.developer.blog.domain;

import java.util.UUID;

public class SaathratriEntityTestSamples {

    public static SaathratriEntity getSaathratriEntitySample1() {
        return new SaathratriEntity()
            .entityId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .createdId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .createdTimeId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static SaathratriEntity getSaathratriEntitySample2() {
        return new SaathratriEntity()
            .entityId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .entityName("entityName2")
            .entityDescription("entityDescription2")
            .createdId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .createdTimeId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static SaathratriEntity getSaathratriEntityRandomSampleGenerator() {
        return new SaathratriEntity()
            .entityId(UUID.randomUUID())
            .entityName(UUID.randomUUID().toString())
            .entityDescription(UUID.randomUUID().toString())
            .createdId(UUID.randomUUID())
            .createdTimeId(UUID.randomUUID());
    }
}
