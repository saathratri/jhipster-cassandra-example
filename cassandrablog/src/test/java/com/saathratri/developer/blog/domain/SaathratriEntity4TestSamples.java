package com.saathratri.developer.blog.domain;

import java.util.UUID;

public class SaathratriEntity4TestSamples {

    public static SaathratriEntity4 getSaathratriEntity4Sample1() {
        return new SaathratriEntity4()
            .compositeId(
                new SaathratriEntity4Id()
                    .organizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
                    .attributeKey("attributeKey1")
            )
            .attributeValue("attributeValue1");
    }

    public static SaathratriEntity4 getSaathratriEntity4Sample2() {
        return new SaathratriEntity4()
            .compositeId(
                new SaathratriEntity4Id()
                    .organizationId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
                    .attributeKey("attributeKey2")
            )
            .attributeValue("attributeValue1");
    }

    public static SaathratriEntity4 getSaathratriEntity4RandomSampleGenerator() {
        return new SaathratriEntity4()
            .compositeId(new SaathratriEntity4Id().organizationId(UUID.randomUUID()).attributeKey(UUID.randomUUID().toString()))
            .attributeValue("attributeValue1");
    }
}
