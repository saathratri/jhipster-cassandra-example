package com.saathratri.developer.blog.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * A SaathratriEntity6.
 */
// Since spring-data-cassandra 3.4.2, table names needs to be in lowercase
// See https://github.com/spring-projects/spring-data-cassandra/issues/1293#issuecomment-1192555467
@Table("saathratri_entity_6")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntity6 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private SaathratriEntity6Id compositeId;

    @Column("departure_date")
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long departureDate;

    @Column("customer_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID customerId;

    @Column("customer_first_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String customerFirstName;

    @Column("customer_last_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String customerLastName;

    @Column("customer_updated_email")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String customerUpdatedEmail;

    @Column("customer_updated_phone_number")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String customerUpdatedPhoneNumber;

    @Column("customer_estimated_arrival_time")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String customerEstimatedArrivalTime;

    @Column("tiny_url_short_code")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String tinyUrlShortCode;

    @Column("add_on_details_text")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.TEXT })
    private Map<String, String> addOnDetailsText;

    @Column("add_on_details_decimal")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.DECIMAL })
    private Map<String, BigDecimal> addOnDetailsDecimal;

    @Column("add_on_details_boolean")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.BOOLEAN })
    private Map<String, Boolean> addOnDetailsBoolean;

    @Column("add_on_details_big_int")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.BIGINT })
    private Map<String, Long> addOnDetailsBigInt;

    public SaathratriEntity6Id getCompositeId() {
        return this.compositeId;
    }

    public void setCompositeId(SaathratriEntity6Id compositeId) {
        this.compositeId = compositeId;
    }

    public SaathratriEntity6 compositeId(SaathratriEntity6Id compositeId) {
        this.compositeId = compositeId;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getDepartureDate() {
        return this.departureDate;
    }

    public SaathratriEntity6 departureDate(Long departureDate) {
        this.setDepartureDate(departureDate);
        return this;
    }

    public void setDepartureDate(Long departureDate) {
        this.departureDate = departureDate;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public SaathratriEntity6 customerId(UUID customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFirstName() {
        return this.customerFirstName;
    }

    public SaathratriEntity6 customerFirstName(String customerFirstName) {
        this.setCustomerFirstName(customerFirstName);
        return this;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return this.customerLastName;
    }

    public SaathratriEntity6 customerLastName(String customerLastName) {
        this.setCustomerLastName(customerLastName);
        return this;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerUpdatedEmail() {
        return this.customerUpdatedEmail;
    }

    public SaathratriEntity6 customerUpdatedEmail(String customerUpdatedEmail) {
        this.setCustomerUpdatedEmail(customerUpdatedEmail);
        return this;
    }

    public void setCustomerUpdatedEmail(String customerUpdatedEmail) {
        this.customerUpdatedEmail = customerUpdatedEmail;
    }

    public String getCustomerUpdatedPhoneNumber() {
        return this.customerUpdatedPhoneNumber;
    }

    public SaathratriEntity6 customerUpdatedPhoneNumber(String customerUpdatedPhoneNumber) {
        this.setCustomerUpdatedPhoneNumber(customerUpdatedPhoneNumber);
        return this;
    }

    public void setCustomerUpdatedPhoneNumber(String customerUpdatedPhoneNumber) {
        this.customerUpdatedPhoneNumber = customerUpdatedPhoneNumber;
    }

    public String getCustomerEstimatedArrivalTime() {
        return this.customerEstimatedArrivalTime;
    }

    public SaathratriEntity6 customerEstimatedArrivalTime(String customerEstimatedArrivalTime) {
        this.setCustomerEstimatedArrivalTime(customerEstimatedArrivalTime);
        return this;
    }

    public void setCustomerEstimatedArrivalTime(String customerEstimatedArrivalTime) {
        this.customerEstimatedArrivalTime = customerEstimatedArrivalTime;
    }

    public String getTinyUrlShortCode() {
        return this.tinyUrlShortCode;
    }

    public SaathratriEntity6 tinyUrlShortCode(String tinyUrlShortCode) {
        this.setTinyUrlShortCode(tinyUrlShortCode);
        return this;
    }

    public void setTinyUrlShortCode(String tinyUrlShortCode) {
        this.tinyUrlShortCode = tinyUrlShortCode;
    }

    public Map<String, String> getAddOnDetailsText() {
        return this.addOnDetailsText;
    }

    public SaathratriEntity6 addOnDetailsText(Map<String, String> addOnDetailsText) {
        this.setAddOnDetailsText(addOnDetailsText);
        return this;
    }

    public void setAddOnDetailsText(Map<String, String> addOnDetailsText) {
        this.addOnDetailsText = addOnDetailsText;
    }

    public Map<String, BigDecimal> getAddOnDetailsDecimal() {
        return this.addOnDetailsDecimal;
    }

    public SaathratriEntity6 addOnDetailsDecimal(Map<String, BigDecimal> addOnDetailsDecimal) {
        this.setAddOnDetailsDecimal(addOnDetailsDecimal);
        return this;
    }

    public void setAddOnDetailsDecimal(Map<String, BigDecimal> addOnDetailsDecimal) {
        this.addOnDetailsDecimal = addOnDetailsDecimal;
    }

    public Map<String, Boolean> getAddOnDetailsBoolean() {
        return this.addOnDetailsBoolean;
    }

    public SaathratriEntity6 addOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
        this.setAddOnDetailsBoolean(addOnDetailsBoolean);
        return this;
    }

    public void setAddOnDetailsBoolean(Map<String, Boolean> addOnDetailsBoolean) {
        this.addOnDetailsBoolean = addOnDetailsBoolean;
    }

    public Map<String, Long> getAddOnDetailsBigInt() {
        return this.addOnDetailsBigInt;
    }

    public SaathratriEntity6 addOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
        this.setAddOnDetailsBigInt(addOnDetailsBigInt);
        return this;
    }

    public void setAddOnDetailsBigInt(Map<String, Long> addOnDetailsBigInt) {
        this.addOnDetailsBigInt = addOnDetailsBigInt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaathratriEntity6)) {
            return false;
        }
        return getCompositeId() != null && getCompositeId().equals(((SaathratriEntity6) o).getCompositeId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity6{" +
            "compositeId=" + getCompositeId() +
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
