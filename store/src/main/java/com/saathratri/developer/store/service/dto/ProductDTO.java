package com.saathratri.developer.store.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.store.domain.Product} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDTO implements Serializable {

    private UUID id;
    private String title;
    private BigDecimal price;
    private ByteBuffer image;
    private Long addedDate;
    private String imageContentType;

    public ProductDTO() {
        // Empty constructor needed for Jackson.
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ProductDTO id(UUID id) {
        this.id = id;
        return this;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public ProductDTO imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProductDTO title(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductDTO price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ByteBuffer getImage() {
        return image;
    }

    public void setImage(ByteBuffer image) {
        this.image = image;
    }

    public ProductDTO image(ByteBuffer image) {
        this.image = image;
        return this;
    }

    public Long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Long addedDate) {
        this.addedDate = addedDate;
    }

    public ProductDTO addedDate(Long addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO)) return false;

        ProductDTO that = (ProductDTO) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO {" +"id = " + getId() +
            ", title='" + getTitle() + "'" +
            ", price=" + getPrice() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", addedDate=" + getAddedDate() +
            "}";
    }
}
