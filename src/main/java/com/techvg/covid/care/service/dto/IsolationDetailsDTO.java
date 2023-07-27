package com.techvg.covid.care.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.techvg.covid.care.isolation.domain.IsolationDetails} entity.
 */
public class IsolationDetailsDTO implements Serializable {
    
    private Long id;

    private String referredDrName;

    private String referredDrMobile;

    private String prescriptionUrl;

    private String reportUrl;

    private String remarks;

    private Boolean selfRegistered;

    private Instant lastAssessment;

    private Instant lastModified;

    private String lastModifiedBy;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferredDrName() {
        return referredDrName;
    }

    public void setReferredDrName(String referredDrName) {
        this.referredDrName = referredDrName;
    }

    public String getReferredDrMobile() {
        return referredDrMobile;
    }

    public void setReferredDrMobile(String referredDrMobile) {
        this.referredDrMobile = referredDrMobile;
    }

    public String getPrescriptionUrl() {
        return prescriptionUrl;
    }

    public void setPrescriptionUrl(String prescriptionUrl) {
        this.prescriptionUrl = prescriptionUrl;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean isSelfRegistered() {
        return selfRegistered;
    }

    public void setSelfRegistered(Boolean selfRegistered) {
        this.selfRegistered = selfRegistered;
    }

    public Instant getLastAssessment() {
        return lastAssessment;
    }

    public void setLastAssessment(Instant lastAssessment) {
        this.lastAssessment = lastAssessment;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IsolationDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((IsolationDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsolationDetailsDTO{" +
            "id=" + getId() +
            ", referredDrName='" + getReferredDrName() + "'" +
            ", referredDrMobile='" + getReferredDrMobile() + "'" +
            ", prescriptionUrl='" + getPrescriptionUrl() + "'" +
            ", reportUrl='" + getReportUrl() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", selfRegistered='" + isSelfRegistered() + "'" +
            ", lastAssessment='" + getLastAssessment() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
