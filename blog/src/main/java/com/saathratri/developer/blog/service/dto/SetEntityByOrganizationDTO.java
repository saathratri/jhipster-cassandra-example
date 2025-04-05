package com.saathratri.developer.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.SetEntityByOrganization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SetEntityByOrganizationDTO implements Serializable {

    private UUID organizationId;
    private Set<String> tags;

    public SetEntityByOrganizationDTO() {
        // Empty constructor needed for Jackson.
    }

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public SetEntityByOrganizationDTO organizationId(UUID organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public SetEntityByOrganizationDTO tags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SetEntityByOrganizationDTO)) return false;

        SetEntityByOrganizationDTO that = (SetEntityByOrganizationDTO) o;
        return Objects.equals(getOrganizationId(), that.getOrganizationId());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SetEntityByOrganizationDTO {" +"organizationId = " + getOrganizationId() +
            ", tags='" + getTags() + "'" +
            "}";
    }
}
