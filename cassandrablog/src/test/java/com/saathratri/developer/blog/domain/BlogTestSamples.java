package com.saathratri.developer.blog.domain;

import java.util.UUID;

public class BlogTestSamples {

    public static Blog getBlogSample1() {
        return new Blog()
            .compositeId(new BlogId().category("category1").blogId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")))
            .handle("handle1")
            .content("content1");
    }

    public static Blog getBlogSample2() {
        return new Blog()
            .compositeId(new BlogId().category("category2").blogId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")))
            .handle("handle1")
            .content("content1");
    }

    public static Blog getBlogRandomSampleGenerator() {
        return new Blog()
            .compositeId(new BlogId().category(UUID.randomUUID().toString()).blogId(UUID.randomUUID()))
            .handle("handle1")
            .content("content1");
    }
}
