package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.AddOnsAvailableByOrganization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddOnsAvailableByOrganizationDTO implements Serializable {

    private AddOnsAvailableByOrganizationDTOId compositeId;
    private String addOnType;
    private Map<String, String> addOnDetailsText;
    private Map<String, BigDecimal> addOnDetailsDecimal;
    private Map<String, Boolean> addOnDetailsBoolean;
    private Map<String, Long> addOnDetailsBigInt;

    public AddOnsAvailableByOrganizationDTO() {
        // Empty constructor needed for Jackson.
    }

    public AddOnsAvailableByOrganizationDTOId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(AddOnsAvailableByOrganizationDTOId compositeId) {
        this.compositeId = compositeId;
    }

    public AddOnsAvailableByOrganizationDTO compositeId(AddOnsAvailableByOrganizationDTOId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    public String getAddOnType() {
        return addOnType;
    }

    public void setAddOnType(String addOnType) {
        this.addOnType = addOnType;
    }

    public AddOnsAvailableByOrganizationDTO addOnType(String addOnType) {
        this.addOnType = addOnType;
        return this;
    }

    public Map<String, String> getAddOnDetailsText() {
        return addOnDetailsText;
    }

    public void setAddOnDetailsText(Map<String, String> addOnDetailsText) {
        this.addOnDetailsText = addOnDetailsText;
    }

    public AddOnsAvailableByOrganizationDTO addOnDetailsText(Map<String, String> addOnDetailsText) {
        this.addOnDetailsText = addOnDetailsText;
        return this;
    }

    public Map<String, BigDecimal> getAddOnDetailsDecimal() {
        return addOnDetailsDecimal;
    }

    public void setAddOnDetailsDecimal(Map<String, BigDecimal> addOnDetailsDecimal) {
        this.addOnDetailsDecimal = addOnDetailsDecimal;
    }

    public AddOnsAvailableByOrganizationDTO addOnDetailsDecimal(Map<String, BigDecimal> addOnDetailsDecimal) {
        this.addOnDetailsDecimal = addOnDetailsDecimal;
        return this;
    }

    public Map<String, Boolean> getAddOnDetailsBoolean() {
        return addOnDetailsBoolean;
    }

    public void setAddOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
        this.addOnDetailsBoolean = addOnDetailsBoolean;
    }

    public AddOnsAvailableByOrganizationDTO addOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
        this.addOnDetailsBoolean = addOnDetailsBoolean;
        return this;
    }

    public Map<String, Long> getAddOnDetailsBigInt() {
        return addOnDetailsBigInt;
    }

    public void setAddOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
        this.addOnDetailsBigInt = addOnDetailsBigInt;
    }

    public AddOnsAvailableByOrganizationDTO addOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
        this.addOnDetailsBigInt = addOnDetailsBigInt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddOnsAvailableByOrganizationDTO)) return false;

        AddOnsAvailableByOrganizationDTO that = (AddOnsAvailableByOrganizationDTO) o;
        return Objects.equals(getCompositeId(), that.getCompositeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddOnsAvailableByOrganizationDTO {" +"compositeId = " + getCompositeId() +
            ", addOnType='" + getAddOnType() + "'" +
            ", addOnDetailsText='" + getAddOnDetailsText() + "'" +
            ", addOnDetailsDecimal=" + getAddOnDetailsDecimal() +
            ", addOnDetailsBoolean='" + getAddOnDetailsBoolean() + "'" +
            ", addOnDetailsBigInt=" + getAddOnDetailsBigInt() +
            "}";
    }
}
