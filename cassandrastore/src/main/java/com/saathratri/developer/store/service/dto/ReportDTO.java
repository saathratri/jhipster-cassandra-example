package com.saathratri.developer.store.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.saathratri.developer.store.domain.Report} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportDTO implements Serializable {

    private UUID id;
    private String fileName;
    private String fileExtension;
    private Long createDate;
    private ByteBuffer file;
    private Boolean approved;

    public ReportDTO() {
        // Empty constructor needed for Jackson.
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ReportDTO id(UUID id) {
        this.id = id;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ReportDTO fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public ReportDTO fileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        return this;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public ReportDTO createDate(Long createDate) {
        this.createDate = createDate;
        return this;
    }

    public ByteBuffer getFile() {
        return file;
    }

    public void setFile(ByteBuffer file) {
        this.file = file;
    }

    public ReportDTO file(ByteBuffer file) {
        this.file = file;
        return this;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public ReportDTO approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportDTO)) return false;

        ReportDTO that = (ReportDTO) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportDTO {" +"id = " + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", createDate=" + getCreateDate() +
            ", file=" + getFile() +
            ", fileContentType='" + getFileContentType() + "'" +
            ", approved=" + getApproved() +
            "}";
    }
}
