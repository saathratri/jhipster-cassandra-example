package com.saathratri.developer.blog.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SaathratriEntity6TestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SaathratriEntity6 getSaathratriEntity6Sample1() {
        return new SaathratriEntity6()
            .organizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .arrivalDate(1L)
            .accountNumber("accountNumber1")
            .createdTimeId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .departureDate(1L)
            .customerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .customerFirstName("customerFirstName1")
            .customerLastName("customerLastName1")
            .customerUpdatedEmail("customerUpdatedEmail1")
            .customerUpdatedPhoneNumber("customerUpdatedPhoneNumber1")
            .customerEstimatedArrivalTime("customerEstimatedArrivalTime1")
            .tinyUrlShortCode("tinyUrlShortCode1")
            .addOnDetailsText("addOnDetailsText1")
            .addOnDetailsBigInt(1L);
    }

    public static SaathratriEntity6 getSaathratriEntity6Sample2() {
        return new SaathratriEntity6()
            .organizationId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .arrivalDate(2L)
            .accountNumber("accountNumber2")
            .createdTimeId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .departureDate(2L)
            .customerId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .customerFirstName("customerFirstName2")
            .customerLastName("customerLastName2")
            .customerUpdatedEmail("customerUpdatedEmail2")
            .customerUpdatedPhoneNumber("customerUpdatedPhoneNumber2")
            .customerEstimatedArrivalTime("customerEstimatedArrivalTime2")
            .tinyUrlShortCode("tinyUrlShortCode2")
            .addOnDetailsText("addOnDetailsText2")
            .addOnDetailsBigInt(2L);
    }

    public static SaathratriEntity6 getSaathratriEntity6RandomSampleGenerator() {
        return new SaathratriEntity6()
            .organizationId(UUID.randomUUID())
            .arrivalDate(longCount.incrementAndGet())
            .accountNumber(UUID.randomUUID().toString())
            .createdTimeId(UUID.randomUUID())
            .departureDate(longCount.incrementAndGet())
            .customerId(UUID.randomUUID())
            .customerFirstName(UUID.randomUUID().toString())
            .customerLastName(UUID.randomUUID().toString())
            .customerUpdatedEmail(UUID.randomUUID().toString())
            .customerUpdatedPhoneNumber(UUID.randomUUID().toString())
            .customerEstimatedArrivalTime(UUID.randomUUID().toString())
            .tinyUrlShortCode(UUID.randomUUID().toString())
            .addOnDetailsText(UUID.randomUUID().toString())
            .addOnDetailsBigInt(longCount.incrementAndGet());
    }
}
