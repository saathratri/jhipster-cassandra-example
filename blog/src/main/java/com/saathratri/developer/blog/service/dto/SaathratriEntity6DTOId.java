package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.blog.domain.SaathratriEntity6DTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaathratriEntity6DTOId implements Serializable {

    private UUID organizationId;
    private Long arrivalDate;
    private String accountNumber;
    private UUID createdTimeId;

    public SaathratriEntity6DTOId() {}

    public SaathratriEntity6DTOId(UUID organizationId, Long arrivalDate, String accountNumber, UUID createdTimeId) {
        this.organizationId = organizationId;
        this.arrivalDate = arrivalDate;
        this.accountNumber = accountNumber;
        this.createdTimeId = createdTimeId;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public Long getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public UUID getCreatedTimeId() {
        return createdTimeId;
    }

    public void setCreatedTimeId(UUID createdTimeId) {
        this.createdTimeId = createdTimeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaathratriEntity6DTO)) {
            return false;
        }

        SaathratriEntity6DTO saathratriEntity6DTO = (SaathratriEntity6DTO) o;
        if (this.organizationId == null && this.arrivalDate == null && this.accountNumber == null && this.createdTimeId == null) {
            return false;
        }
        return (
            Objects.equals(this.organizationId, saathratriEntity6DTO.getCompositeId().getOrganizationId()) &&
            Objects.equals(this.arrivalDate, saathratriEntity6DTO.getCompositeId().getArrivalDate()) &&
            Objects.equals(this.accountNumber, saathratriEntity6DTO.getCompositeId().getAccountNumber()) &&
            Objects.equals(this.createdTimeId, saathratriEntity6DTO.getCompositeId().getCreatedTimeId())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.organizationId, this.arrivalDate, this.accountNumber, this.createdTimeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaathratriEntity6DTOId { " +
            "organizationId='" + getOrganizationId() + "'" +
            ",arrivalDate=" + getArrivalDate() +
            ",accountNumber='" + getAccountNumber() + "'" +
            ",createdTimeId='" + getCreatedTimeId() + "'" +
            " }";
    }
}
