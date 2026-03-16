package com.saathratri.developer.store.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).title("title1").addedDate(1L);
    }

    public static Product getProductSample2() {
        return new Product().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).title("title2").addedDate(2L);
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product().id(UUID.randomUUID()).title(UUID.randomUUID().toString()).addedDate(longCount.incrementAndGet());
    }
}
