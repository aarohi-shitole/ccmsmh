package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.techvg.covid.care.domain.enumeration.SupplierType;
import com.techvg.covid.care.domain.enumeration.StatusInd;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.techvg.covid.care.domain.Supplier} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.SupplierResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /suppliers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplierCriteria implements Serializable, Criteria {
    /**
     * Class for filtering SupplierType
     */
    public static class SupplierTypeFilter extends Filter<SupplierType> {

        public SupplierTypeFilter() {
        }

        public SupplierTypeFilter(SupplierTypeFilter filter) {
            super(filter);
        }

        @Override
        public SupplierTypeFilter copy() {
            return new SupplierTypeFilter(this);
        }

    }
    /**
     * Class for filtering StatusInd
     */
    public static class StatusIndFilter extends Filter<StatusInd> {

        public StatusIndFilter() {
        }

        public StatusIndFilter(StatusIndFilter filter) {
            super(filter);
        }

        @Override
        public StatusIndFilter copy() {
            return new StatusIndFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private SupplierTypeFilter supplierType;

    private StringFilter contactNo;

    private StringFilter latitude;

    private StringFilter longitude;

    private StringFilter email;

    private StringFilter registrationNo;

    private StringFilter address1;

    private StringFilter address2;

    private StringFilter area;

    private StringFilter pinCode;

    private StatusIndFilter statusInd;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter stateId;

    private LongFilter districtId;

    private LongFilter talukaId;

    private LongFilter cityId;

    private LongFilter inventoryTypeId;

    private LongFilter hospitalId;

    public SupplierCriteria() {
    }

    public SupplierCriteria(SupplierCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.supplierType = other.supplierType == null ? null : other.supplierType.copy();
        this.contactNo = other.contactNo == null ? null : other.contactNo.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.registrationNo = other.registrationNo == null ? null : other.registrationNo.copy();
        this.address1 = other.address1 == null ? null : other.address1.copy();
        this.address2 = other.address2 == null ? null : other.address2.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.pinCode = other.pinCode == null ? null : other.pinCode.copy();
        this.statusInd = other.statusInd == null ? null : other.statusInd.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.stateId = other.stateId == null ? null : other.stateId.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.talukaId = other.talukaId == null ? null : other.talukaId.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.inventoryTypeId = other.inventoryTypeId == null ? null : other.inventoryTypeId.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
    }

    @Override
    public SupplierCriteria copy() {
        return new SupplierCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public SupplierTypeFilter getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(SupplierTypeFilter supplierType) {
        this.supplierType = supplierType;
    }

    public StringFilter getContactNo() {
        return contactNo;
    }

    public void setContactNo(StringFilter contactNo) {
        this.contactNo = contactNo;
    }

    public StringFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(StringFilter latitude) {
        this.latitude = latitude;
    }

    public StringFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(StringFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(StringFilter registrationNo) {
        this.registrationNo = registrationNo;
    }

    public StringFilter getAddress1() {
        return address1;
    }

    public void setAddress1(StringFilter address1) {
        this.address1 = address1;
    }

    public StringFilter getAddress2() {
        return address2;
    }

    public void setAddress2(StringFilter address2) {
        this.address2 = address2;
    }

    public StringFilter getArea() {
        return area;
    }

    public void setArea(StringFilter area) {
        this.area = area;
    }

    public StringFilter getPinCode() {
        return pinCode;
    }

    public void setPinCode(StringFilter pinCode) {
        this.pinCode = pinCode;
    }

    public StatusIndFilter getStatusInd() {
        return statusInd;
    }

    public void setStatusInd(StatusIndFilter statusInd) {
        this.statusInd = statusInd;
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

    public LongFilter getStateId() {
        return stateId;
    }

    public void setStateId(LongFilter stateId) {
        this.stateId = stateId;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
    }

    public LongFilter getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(LongFilter talukaId) {
        this.talukaId = talukaId;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public LongFilter getInventoryTypeId() {
        return inventoryTypeId;
    }

    public void setInventoryTypeId(LongFilter inventoryTypeId) {
        this.inventoryTypeId = inventoryTypeId;
    }

    public LongFilter getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(LongFilter hospitalId) {
        this.hospitalId = hospitalId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplierCriteria that = (SupplierCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(supplierType, that.supplierType) &&
            Objects.equals(contactNo, that.contactNo) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(email, that.email) &&
            Objects.equals(registrationNo, that.registrationNo) &&
            Objects.equals(address1, that.address1) &&
            Objects.equals(address2, that.address2) &&
            Objects.equals(area, that.area) &&
            Objects.equals(pinCode, that.pinCode) &&
            Objects.equals(statusInd, that.statusInd) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(stateId, that.stateId) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(talukaId, that.talukaId) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(inventoryTypeId, that.inventoryTypeId) &&
            Objects.equals(hospitalId, that.hospitalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        supplierType,
        contactNo,
        latitude,
        longitude,
        email,
        registrationNo,
        address1,
        address2,
        area,
        pinCode,
        statusInd,
        lastModified,
        lastModifiedBy,
        stateId,
        districtId,
        talukaId,
        cityId,
        inventoryTypeId,
        hospitalId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (supplierType != null ? "supplierType=" + supplierType + ", " : "") +
                (contactNo != null ? "contactNo=" + contactNo + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (registrationNo != null ? "registrationNo=" + registrationNo + ", " : "") +
                (address1 != null ? "address1=" + address1 + ", " : "") +
                (address2 != null ? "address2=" + address2 + ", " : "") +
                (area != null ? "area=" + area + ", " : "") +
                (pinCode != null ? "pinCode=" + pinCode + ", " : "") +
                (statusInd != null ? "statusInd=" + statusInd + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (stateId != null ? "stateId=" + stateId + ", " : "") +
                (districtId != null ? "districtId=" + districtId + ", " : "") +
                (talukaId != null ? "talukaId=" + talukaId + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
                (inventoryTypeId != null ? "inventoryTypeId=" + inventoryTypeId + ", " : "") +
                (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
            "}";
    }

}
