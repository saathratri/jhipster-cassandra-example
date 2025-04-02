package com.saathratri.developer.blog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.blog.domain.TajUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TajUserDTO implements Serializable {

    private UUID id;
    private String login;

    public TajUserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TajUserDTO id(UUID id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public TajUserDTO login(String login) {
        this.login = login;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TajUserDTO)) return false;

        TajUserDTO that = (TajUserDTO) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TajUserDTO {" +"id = " + getId() +
            ", login='" + getLogin() + "'" +
            "}";
    }
}
