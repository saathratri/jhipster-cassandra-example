package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.LandingPageByOrganization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LandingPageByOrganizationDTO implements Serializable {

    private UUID organizationId;
    private Map<String, String> detailsText;
    private Map<String, BigDecimal> detailsDecimal;
    private Map<String, Boolean> detailsBoolean;
    private Map<String, Long> detailsBigInt;

    public LandingPageByOrganizationDTO() {
        // Empty constructor needed for Jackson.
    }

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public LandingPageByOrganizationDTO organizationId(UUID organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public Map<String, String> getDetailsText() {
        return detailsText;
    }

    public void setDetailsText(Map<String, String> detailsText) {
        this.detailsText = detailsText;
    }

    public LandingPageByOrganizationDTO detailsText(Map<String, String> detailsText) {
        this.detailsText = detailsText;
        return this;
    }

    public Map<String, BigDecimal> getDetailsDecimal() {
        return detailsDecimal;
    }

    public void setDetailsDecimal(Map<String, BigDecimal> detailsDecimal) {
        this.detailsDecimal = detailsDecimal;
    }

    public LandingPageByOrganizationDTO detailsDecimal(Map<String, BigDecimal> detailsDecimal) {
        this.detailsDecimal = detailsDecimal;
        return this;
    }

    public Map<String, Boolean> getDetailsBoolean() {
        return detailsBoolean;
    }

    public void setDetailsBoolean(Map<String, Boolean> detailsBoolean) {
        this.detailsBoolean = detailsBoolean;
    }

    public LandingPageByOrganizationDTO detailsBoolean(Map<String, Boolean> detailsBoolean) {
        this.detailsBoolean = detailsBoolean;
        return this;
    }

    public Map<String, Long> getDetailsBigInt() {
        return detailsBigInt;
    }

    public void setDetailsBigInt(Map<String, Long> detailsBigInt) {
        this.detailsBigInt = detailsBigInt;
    }

    public LandingPageByOrganizationDTO detailsBigInt(Map<String, Long> detailsBigInt) {
        this.detailsBigInt = detailsBigInt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LandingPageByOrganizationDTO)) return false;

        LandingPageByOrganizationDTO that = (LandingPageByOrganizationDTO) o;
        return Objects.equals(getOrganizationId(), that.getOrganizationId());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LandingPageByOrganizationDTO {" +"organizationId = " + getOrganizationId() +
            ", detailsText='" + getDetailsText() + "'" +
            ", detailsDecimal=" + getDetailsDecimal() +
            ", detailsBoolean='" + getDetailsBoolean() + "'" +
            ", detailsBigInt=" + getDetailsBigInt() +
            "}";
    }
}
