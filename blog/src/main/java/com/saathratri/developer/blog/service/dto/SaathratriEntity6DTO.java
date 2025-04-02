package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.SaathratriEntity6} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntity6DTO implements Serializable {

    private SaathratriEntity6DTOId compositeId;
    private Long departureDate;
    private UUID customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerUpdatedEmail;
    private String customerUpdatedPhoneNumber;
    private String customerEstimatedArrivalTime;
    private String tinyUrlShortCode;
    private Map<String, String> addOnDetailsText;
    private Map<String, BigDecimal> addOnDetailsDecimal;
    private Map<String, Boolean> addOnDetailsBoolean;
    private Map<String, Long> addOnDetailsBigInt;

    public SaathratriEntity6DTO() {
        // Empty constructor needed for Jackson.
    }

    public SaathratriEntity6DTOId getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(SaathratriEntity6DTOId compositeId) {
        this.compositeId = compositeId;
    }

    public SaathratriEntity6DTO compositeId(SaathratriEntity6DTOId compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    public Long getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Long departureDate) {
        this.departureDate = departureDate;
    }

    public SaathratriEntity6DTO departureDate(Long departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public SaathratriEntity6DTO customerId(UUID customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public SaathratriEntity6DTO customerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
        return this;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public SaathratriEntity6DTO customerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
        return this;
    }

    public String getCustomerUpdatedEmail() {
        return customerUpdatedEmail;
    }

    public void setCustomerUpdatedEmail(String customerUpdatedEmail) {
        this.customerUpdatedEmail = customerUpdatedEmail;
    }

    public SaathratriEntity6DTO customerUpdatedEmail(String customerUpdatedEmail) {
        this.customerUpdatedEmail = customerUpdatedEmail;
        return this;
    }

    public String getCustomerUpdatedPhoneNumber() {
        return customerUpdatedPhoneNumber;
    }

    public void setCustomerUpdatedPhoneNumber(String customerUpdatedPhoneNumber) {
        this.customerUpdatedPhoneNumber = customerUpdatedPhoneNumber;
    }

    public SaathratriEntity6DTO customerUpdatedPhoneNumber(String customerUpdatedPhoneNumber) {
        this.customerUpdatedPhoneNumber = customerUpdatedPhoneNumber;
        return this;
    }

    public String getCustomerEstimatedArrivalTime() {
        return customerEstimatedArrivalTime;
    }

    public void setCustomerEstimatedArrivalTime(String customerEstimatedArrivalTime) {
        this.customerEstimatedArrivalTime = customerEstimatedArrivalTime;
    }

    public SaathratriEntity6DTO customerEstimatedArrivalTime(String customerEstimatedArrivalTime) {
        this.customerEstimatedArrivalTime = customerEstimatedArrivalTime;
        return this;
    }

    public String getTinyUrlShortCode() {
        return tinyUrlShortCode;
    }

    public void setTinyUrlShortCode(String tinyUrlShortCode) {
        this.tinyUrlShortCode = tinyUrlShortCode;
    }

    public SaathratriEntity6DTO tinyUrlShortCode(String tinyUrlShortCode) {
        this.tinyUrlShortCode = tinyUrlShortCode;
        return this;
    }

    public Map<String, String> getAddOnDetailsText() {
        return addOnDetailsText;
    }

    public void setAddOnDetailsText(Map<String, String> addOnDetailsText) {
        this.addOnDetailsText = addOnDetailsText;
    }

    public SaathratriEntity6DTO addOnDetailsText(Map<String, String> addOnDetailsText) {
        this.addOnDetailsText = addOnDetailsText;
        return this;
    }

    public Map<String, BigDecimal> getAddOnDetailsDecimal() {
        return addOnDetailsDecimal;
    }

    public void setAddOnDetailsDecimal(Map<String, BigDecimal> addOnDetailsDecimal) {
        this.addOnDetailsDecimal = addOnDetailsDecimal;
    }

    public SaathratriEntity6DTO addOnDetailsDecimal(Map<String, BigDecimal> addOnDetailsDecimal) {
        this.addOnDetailsDecimal = addOnDetailsDecimal;
        return this;
    }

    public Map<String, Boolean> getAddOnDetailsBoolean() {
        return addOnDetailsBoolean;
    }

    public void setAddOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
        this.addOnDetailsBoolean = addOnDetailsBoolean;
    }

    public SaathratriEntity6DTO addOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
        this.addOnDetailsBoolean = addOnDetailsBoolean;
        return this;
    }

    public Map<String, Long> getAddOnDetailsBigInt() {
        return addOnDetailsBigInt;
    }

    public void setAddOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
        this.addOnDetailsBigInt = addOnDetailsBigInt;
    }

    public SaathratriEntity6DTO addOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
        this.addOnDetailsBigInt = addOnDetailsBigInt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaathratriEntity6DTO)) return false;

        SaathratriEntity6DTO that = (SaathratriEntity6DTO) o;
        return Objects.equals(getCompositeId(), that.getCompositeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity6DTO {" +"compositeId = " + getCompositeId() +
            ", departureDate=" + getDepartureDate() +
            ", customerId='" + getCustomerId() + "'" +
            ", customerFirstName='" + getCustomerFirstName() + "'" +
            ", customerLastName='" + getCustomerLastName() + "'" +
            ", customerUpdatedEmail='" + getCustomerUpdatedEmail() + "'" +
            ", customerUpdatedPhoneNumber='" + getCustomerUpdatedPhoneNumber() + "'" +
            ", customerEstimatedArrivalTime='" + getCustomerEstimatedArrivalTime() + "'" +
            ", tinyUrlShortCode='" + getTinyUrlShortCode() + "'" +
            ", addOnDetailsText='" + getAddOnDetailsText() + "'" +
            ", addOnDetailsDecimal=" + getAddOnDetailsDecimal() +
            ", addOnDetailsBoolean='" + getAddOnDetailsBoolean() + "'" +
            ", addOnDetailsBigInt=" + getAddOnDetailsBigInt() +
            "}";
    }
}
