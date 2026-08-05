package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO ID for the {@link com.saathratri.developer.blog.domain.AddOnsSelectedByOrganizationDTOId} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddOnsSelectedByOrganizationDTOId implements Serializable {

    private UUID organizationId;
    private Long arrivalDate;
    private String accountNumber;
    private UUID createdTimeId;

    public AddOnsSelectedByOrganizationDTOId() {}

    public AddOnsSelectedByOrganizationDTOId(UUID organizationId, Long arrivalDate, String accountNumber, UUID createdTimeId) {
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
        if (!(o instanceof AddOnsSelectedByOrganizationDTO)) {
            return false;
        }

        AddOnsSelectedByOrganizationDTO addOnsSelectedByOrganizationDTO = (AddOnsSelectedByOrganizationDTO) o;
        if (this.organizationId == null && this.arrivalDate == null && this.accountNumber == null && this.createdTimeId == null) {
            return false;
        }
        return (
            Objects.equals(this.organizationId, addOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId()) &&
            Objects.equals(this.arrivalDate, addOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate()) &&
            Objects.equals(this.accountNumber, addOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber()) &&
            Objects.equals(this.createdTimeId, addOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.organizationId, this.arrivalDate, this.accountNumber, this.createdTimeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddOnsSelectedByOrganizationDTOId { " +
            "organizationId='" + getOrganizationId() + "'" +
            ",arrivalDate=" + getArrivalDate() +
            ",accountNumber='" + getAccountNumber() + "'" +
            ",createdTimeId='" + getCreatedTimeId() + "'" +
            " }";
    }
}
