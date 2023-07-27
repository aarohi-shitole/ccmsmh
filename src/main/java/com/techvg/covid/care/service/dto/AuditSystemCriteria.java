package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.techvg.covid.care.domain.AuditSystem} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.AuditSystemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /audit-systems?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AuditSystemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter auditorName;

    private LongFilter defectCount;

    private LongFilter defectFixCount;

    private InstantFilter inspectionDate;

    private StringFilter remark;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter hospitalId;

    private LongFilter supplierId;

    private LongFilter auditTypeId;

    public AuditSystemCriteria() {
    }

    public AuditSystemCriteria(AuditSystemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.auditorName = other.auditorName == null ? null : other.auditorName.copy();
        this.defectCount = other.defectCount == null ? null : other.defectCount.copy();
        this.defectFixCount = other.defectFixCount == null ? null : other.defectFixCount.copy();
        this.inspectionDate = other.inspectionDate == null ? null : other.inspectionDate.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.auditTypeId = other.auditTypeId == null ? null : other.auditTypeId.copy();
    }

    @Override
    public AuditSystemCriteria copy() {
        return new AuditSystemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(StringFilter auditorName) {
        this.auditorName = auditorName;
    }

    public LongFilter getDefectCount() {
        return defectCount;
    }

    public void setDefectCount(LongFilter defectCount) {
        this.defectCount = defectCount;
    }

    public LongFilter getDefectFixCount() {
        return defectFixCount;
    }

    public void setDefectFixCount(LongFilter defectFixCount) {
        this.defectFixCount = defectFixCount;
    }

    public InstantFilter getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(InstantFilter inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(LongFilter hospitalId) {
        this.hospitalId = hospitalId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getAuditTypeId() {
        return auditTypeId;
    }

    public void setAuditTypeId(LongFilter auditTypeId) {
        this.auditTypeId = auditTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AuditSystemCriteria that = (AuditSystemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(auditorName, that.auditorName) &&
            Objects.equals(defectCount, that.defectCount) &&
            Objects.equals(defectFixCount, that.defectFixCount) &&
            Objects.equals(inspectionDate, that.inspectionDate) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(hospitalId, that.hospitalId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(auditTypeId, that.auditTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        auditorName,
        defectCount,
        defectFixCount,
        inspectionDate,
        remark,
        status,
        lastModified,
        lastModifiedBy,
        hospitalId,
        supplierId,
        auditTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditSystemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (auditorName != null ? "auditorName=" + auditorName + ", " : "") +
                (defectCount != null ? "defectCount=" + defectCount + ", " : "") +
                (defectFixCount != null ? "defectFixCount=" + defectFixCount + ", " : "") +
                (inspectionDate != null ? "inspectionDate=" + inspectionDate + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (auditTypeId != null ? "auditTypeId=" + auditTypeId + ", " : "") +
            "}";
    }

}
