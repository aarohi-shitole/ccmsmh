package com.techvg.covid.care.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.AuditSystem} entity.
 */
public class AuditSystemDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String auditorName;

    private Long defectCount;

    private Long defectFixCount;

    @NotNull
    private Instant inspectionDate;

    private String remark;

    private String status;

 
    private Instant lastModified;


    private String lastModifiedBy;


    private Long hospitalId;

    private String hospitalName;

    private Long supplierId;

    private String supplierName;

    private Long auditTypeId;

    private String auditTypeName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public Long getDefectCount() {
        return defectCount;
    }

    public void setDefectCount(Long defectCount) {
        this.defectCount = defectCount;
    }

    public Long getDefectFixCount() {
        return defectFixCount;
    }

    public void setDefectFixCount(Long defectFixCount) {
        this.defectFixCount = defectFixCount;
    }

    public Instant getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Instant inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getAuditTypeId() {
        return auditTypeId;
    }

    public void setAuditTypeId(Long auditTypeId) {
        this.auditTypeId = auditTypeId;
    }

    public String getAuditTypeName() {
        return auditTypeName;
    }

    public void setAuditTypeName(String auditTypeName) {
        this.auditTypeName = auditTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditSystemDTO)) {
            return false;
        }

        return id != null && id.equals(((AuditSystemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditSystemDTO{" +
            "id=" + getId() +
            ", auditorName='" + getAuditorName() + "'" +
            ", defectCount=" + getDefectCount() +
            ", defectFixCount=" + getDefectFixCount() +
            ", inspectionDate='" + getInspectionDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", hospitalId=" + getHospitalId() +
            ", hospitalName='" + getHospitalName() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", auditTypeId=" + getAuditTypeId() +
            ", auditTypeName='" + getAuditTypeName() + "'" +
            "}";
    }
}
