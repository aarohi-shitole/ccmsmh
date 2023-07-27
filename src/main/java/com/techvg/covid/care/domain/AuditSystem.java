package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AuditSystem.
 */
@Entity
@Table(name = "audit_system")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuditSystem extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "auditor_name", nullable = false)
    private String auditorName;

    @Column(name = "defect_count")
    private Long defectCount;

    @Column(name = "defect_fix_count")
    private Long defectFixCount;

    @NotNull
    @Column(name = "inspection_date", nullable = false)
    private Instant inspectionDate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status")
    private String status;


    @ManyToOne
    @JsonIgnoreProperties(value = "auditSystems", allowSetters = true)
    private Hospital hospital;

    @ManyToOne
    @JsonIgnoreProperties(value = "auditSystems", allowSetters = true)
    private Supplier supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "auditSystems", allowSetters = true)
    private AuditType auditType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public AuditSystem auditorName(String auditorName) {
        this.auditorName = auditorName;
        return this;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public Long getDefectCount() {
        return defectCount;
    }

    public AuditSystem defectCount(Long defectCount) {
        this.defectCount = defectCount;
        return this;
    }

    public void setDefectCount(Long defectCount) {
        this.defectCount = defectCount;
    }

    public Long getDefectFixCount() {
        return defectFixCount;
    }

    public AuditSystem defectFixCount(Long defectFixCount) {
        this.defectFixCount = defectFixCount;
        return this;
    }

    public void setDefectFixCount(Long defectFixCount) {
        this.defectFixCount = defectFixCount;
    }

    public Instant getInspectionDate() {
        return inspectionDate;
    }

    public AuditSystem inspectionDate(Instant inspectionDate) {
        this.inspectionDate = inspectionDate;
        return this;
    }

    public void setInspectionDate(Instant inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getRemark() {
        return remark;
    }

    public AuditSystem remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public AuditSystem status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public AuditSystem hospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public AuditSystem supplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public AuditType getAuditType() {
        return auditType;
    }

    public AuditSystem auditType(AuditType auditType) {
        this.auditType = auditType;
        return this;
    }

    public void setAuditType(AuditType auditType) {
        this.auditType = auditType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditSystem)) {
            return false;
        }
        return id != null && id.equals(((AuditSystem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditSystem{" +
            "id=" + getId() +
            ", auditorName='" + getAuditorName() + "'" +
            ", defectCount=" + getDefectCount() +
            ", defectFixCount=" + getDefectFixCount() +
            ", inspectionDate='" + getInspectionDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
