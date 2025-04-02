package com.saathratri.developer.blog.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SaathratriEntity6TestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SaathratriEntity6 getSaathratriEntity6Sample1() {
        return new SaathratriEntity6()
            .compositeId(
                new SaathratriEntity6Id()
                    .organizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
                    .arrivalDate(1L)
                    .accountNumber("accountNumber1")
                    .createdTimeId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            )
            .departureDate(1L)
            .customerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .customerFirstName("customerFirstName1")
            .customerLastName("customerLastName1")
            .customerUpdatedEmail("customerUpdatedEmail1")
            .customerUpdatedPhoneNumber("customerUpdatedPhoneNumber1")
            .customerEstimatedArrivalTime("customerEstimatedArrivalTime1")
            .tinyUrlShortCode("tinyUrlShortCode1")
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

    public static SaathratriEntity6 getSaathratriEntity6Sample2() {
        return new SaathratriEntity6()
            .compositeId(
                new SaathratriEntity6Id()
                    .organizationId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
                    .arrivalDate(2L)
                    .accountNumber("accountNumber2")
                    .createdTimeId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            )
            .departureDate(1L)
            .customerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .customerFirstName("customerFirstName1")
            .customerLastName("customerLastName1")
            .customerUpdatedEmail("customerUpdatedEmail1")
            .customerUpdatedPhoneNumber("customerUpdatedPhoneNumber1")
            .customerEstimatedArrivalTime("customerEstimatedArrivalTime1")
            .tinyUrlShortCode("tinyUrlShortCode1")
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

    public static SaathratriEntity6 getSaathratriEntity6RandomSampleGenerator() {
        return new SaathratriEntity6()
            .compositeId(
                new SaathratriEntity6Id()
                    .organizationId(UUID.randomUUID())
                    .arrivalDate(longCount.incrementAndGet())
                    .accountNumber(UUID.randomUUID().toString())
                    .createdTimeId(UUID.randomUUID())
            )
            .departureDate(1L)
            .customerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .customerFirstName("customerFirstName1")
            .customerLastName("customerLastName1")
            .customerUpdatedEmail("customerUpdatedEmail1")
            .customerUpdatedPhoneNumber("customerUpdatedPhoneNumber1")
            .customerEstimatedArrivalTime("customerEstimatedArrivalTime1")
            .tinyUrlShortCode("tinyUrlShortCode1")
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
