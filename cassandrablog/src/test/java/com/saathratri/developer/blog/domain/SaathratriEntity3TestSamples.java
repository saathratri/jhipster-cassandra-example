package com.saathratri.developer.blog.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SaathratriEntity3TestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SaathratriEntity3 getSaathratriEntity3Sample1() {
        return new SaathratriEntity3()
            .compositeId(
                new SaathratriEntity3Id().entityType("entityType1").createdTimeId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            )
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .departureDate(1L)
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags1");
                    }
                }
            );
    }

    public static SaathratriEntity3 getSaathratriEntity3Sample2() {
        return new SaathratriEntity3()
            .compositeId(
                new SaathratriEntity3Id().entityType("entityType2").createdTimeId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            )
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .departureDate(1L)
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags1");
                    }
                }
            );
    }

    public static SaathratriEntity3 getSaathratriEntity3RandomSampleGenerator() {
        return new SaathratriEntity3()
            .compositeId(new SaathratriEntity3Id().entityType(UUID.randomUUID().toString()).createdTimeId(UUID.randomUUID()))
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .departureDate(1L)
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags1");
                    }
                }
            );
    }
}
