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
 * Criteria class for the {@link com.mycompany.myapp.domain.ICMRDailyCount} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ICMRDailyCountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /icmr-daily-counts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ICMRDailyCountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter totalSamplesTested;

    private StringFilter totalNegative;

    private StringFilter totalPositive;

    private StringFilter currentPreviousMonthTest;

    private LongFilter districtId;

    private StringFilter remarks;

    private InstantFilter editedOn;

    private StringFilter updatedDate;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private Boolean distinct;

    public ICMRDailyCountCriteria() {}

    public ICMRDailyCountCriteria(ICMRDailyCountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.totalSamplesTested = other.totalSamplesTested == null ? null : other.totalSamplesTested.copy();
        this.totalNegative = other.totalNegative == null ? null : other.totalNegative.copy();
        this.totalPositive = other.totalPositive == null ? null : other.totalPositive.copy();
        this.currentPreviousMonthTest = other.currentPreviousMonthTest == null ? null : other.currentPreviousMonthTest.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.editedOn = other.editedOn == null ? null : other.editedOn.copy();
        this.updatedDate = other.updatedDate == null ? null : other.updatedDate.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ICMRDailyCountCriteria copy() {
        return new ICMRDailyCountCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTotalSamplesTested() {
        return totalSamplesTested;
    }

    public StringFilter totalSamplesTested() {
        if (totalSamplesTested == null) {
            totalSamplesTested = new StringFilter();
        }
        return totalSamplesTested;
    }

    public void setTotalSamplesTested(StringFilter totalSamplesTested) {
        this.totalSamplesTested = totalSamplesTested;
    }

    public StringFilter getTotalNegative() {
        return totalNegative;
    }

    public StringFilter totalNegative() {
        if (totalNegative == null) {
            totalNegative = new StringFilter();
        }
        return totalNegative;
    }

    public void setTotalNegative(StringFilter totalNegative) {
        this.totalNegative = totalNegative;
    }

    public StringFilter getTotalPositive() {
        return totalPositive;
    }

    public StringFilter totalPositive() {
        if (totalPositive == null) {
            totalPositive = new StringFilter();
        }
        return totalPositive;
    }

    public void setTotalPositive(StringFilter totalPositive) {
        this.totalPositive = totalPositive;
    }

    public StringFilter getCurrentPreviousMonthTest() {
        return currentPreviousMonthTest;
    }

    public StringFilter currentPreviousMonthTest() {
        if (currentPreviousMonthTest == null) {
            currentPreviousMonthTest = new StringFilter();
        }
        return currentPreviousMonthTest;
    }

    public void setCurrentPreviousMonthTest(StringFilter currentPreviousMonthTest) {
        this.currentPreviousMonthTest = currentPreviousMonthTest;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public LongFilter districtId() {
        if (districtId == null) {
            districtId = new LongFilter();
        }
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public StringFilter remarks() {
        if (remarks == null) {
            remarks = new StringFilter();
        }
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public InstantFilter getEditedOn() {
        return editedOn;
    }

    public InstantFilter editedOn() {
        if (editedOn == null) {
            editedOn = new InstantFilter();
        }
        return editedOn;
    }

    public void setEditedOn(InstantFilter editedOn) {
        this.editedOn = editedOn;
    }

    public StringFilter getUpdatedDate() {
        return updatedDate;
    }

    public StringFilter updatedDate() {
        if (updatedDate == null) {
            updatedDate = new StringFilter();
        }
        return updatedDate;
    }

    public void setUpdatedDate(StringFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ICMRDailyCountCriteria that = (ICMRDailyCountCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(totalSamplesTested, that.totalSamplesTested) &&
            Objects.equals(totalNegative, that.totalNegative) &&
            Objects.equals(totalPositive, that.totalPositive) &&
            Objects.equals(currentPreviousMonthTest, that.currentPreviousMonthTest) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(editedOn, that.editedOn) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            totalSamplesTested,
            totalNegative,
            totalPositive,
            currentPreviousMonthTest,
            districtId,
            remarks,
            editedOn,
            updatedDate,
            freeField1,
            freeField2,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ICMRDailyCountCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (totalSamplesTested != null ? "totalSamplesTested=" + totalSamplesTested + ", " : "") +
            (totalNegative != null ? "totalNegative=" + totalNegative + ", " : "") +
            (totalPositive != null ? "totalPositive=" + totalPositive + ", " : "") +
            (currentPreviousMonthTest != null ? "currentPreviousMonthTest=" + currentPreviousMonthTest + ", " : "") +
            (districtId != null ? "districtId=" + districtId + ", " : "") +
            (remarks != null ? "remarks=" + remarks + ", " : "") +
            (editedOn != null ? "editedOn=" + editedOn + ", " : "") +
            (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
