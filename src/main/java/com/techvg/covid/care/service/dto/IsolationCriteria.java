package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.techvg.covid.care.domain.enumeration.HealthCondition;
import com.techvg.covid.care.domain.enumeration.IsolationStatus;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.techvg.covid.care.isolation.domain.Isolation} entity. This class is used
 * in {@link com.techvg.covid.care.isolation.web.rest.IsolationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /isolations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IsolationCriteria implements Serializable, Criteria {
    /**
     * Class for filtering IsolationStatus
     */
    public static class IsolationStatusFilter extends Filter<IsolationStatus> {

        public IsolationStatusFilter() {
        }

        public IsolationStatusFilter(IsolationStatusFilter filter) {
            super(filter);
        }

        @Override
        public IsolationStatusFilter copy() {
            return new IsolationStatusFilter(this);
        }

    }
    /**
     * Class for filtering HealthCondition
     */
    public static class HealthConditionFilter extends Filter<HealthCondition> {

        public HealthConditionFilter() {
        }

        public HealthConditionFilter(HealthConditionFilter filter) {
            super(filter);
        }

        @Override
        public HealthConditionFilter copy() {
            return new HealthConditionFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter icmrId;

    private StringFilter rtpcrId;

    private StringFilter ratId;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter latitude;

    private StringFilter longitude;

    private StringFilter email;

    private StringFilter imageUrl;

    private BooleanFilter activated;

    private StringFilter mobileNo;

    private StringFilter passwordHash;

    private StringFilter secondaryContactNo;

    private StringFilter aadharCardNo;

    private IsolationStatusFilter status;

    private StringFilter age;

    private StringFilter gender;

    private LongFilter stateId;

    private LongFilter districtId;

    private LongFilter talukaId;

    private LongFilter cityId;

    private StringFilter address;

    private StringFilter pincode;

    private InstantFilter collectionDate;

    private BooleanFilter hospitalized;

    private LongFilter hospitalId;

    private StringFilter addressLatitude;

    private StringFilter addressLongitude;

    private StringFilter currentLatitude;

    private StringFilter currentLongitude;

    private InstantFilter hospitalizationDate;

    private HealthConditionFilter healthCondition;

    private StringFilter remarks;

    private BooleanFilter symptomatic;

    private StringFilter ccmsLogin;

    private BooleanFilter selfRegistered;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private InstantFilter isolationStartDate;

    private InstantFilter isolationEndDate;

    private LongFilter isolationDetailsId;

    private LongFilter isolationId;
    
	private LongFilter userId;


    public IsolationCriteria() {
    }

    public IsolationCriteria(IsolationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.icmrId = other.icmrId == null ? null : other.icmrId.copy();
        this.rtpcrId = other.rtpcrId == null ? null : other.rtpcrId.copy();
        this.ratId = other.ratId == null ? null : other.ratId.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.passwordHash = other.passwordHash == null ? null : other.passwordHash.copy();
        this.secondaryContactNo = other.secondaryContactNo == null ? null : other.secondaryContactNo.copy();
        this.aadharCardNo = other.aadharCardNo == null ? null : other.aadharCardNo.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.stateId = other.stateId == null ? null : other.stateId.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.talukaId = other.talukaId == null ? null : other.talukaId.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.pincode = other.pincode == null ? null : other.pincode.copy();
        this.collectionDate = other.collectionDate == null ? null : other.collectionDate.copy();
        this.hospitalized = other.hospitalized == null ? null : other.hospitalized.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
        this.addressLatitude = other.addressLatitude == null ? null : other.addressLatitude.copy();
        this.addressLongitude = other.addressLongitude == null ? null : other.addressLongitude.copy();
        this.currentLatitude = other.currentLatitude == null ? null : other.currentLatitude.copy();
        this.currentLongitude = other.currentLongitude == null ? null : other.currentLongitude.copy();
        this.hospitalizationDate = other.hospitalizationDate == null ? null : other.hospitalizationDate.copy();
        this.healthCondition = other.healthCondition == null ? null : other.healthCondition.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.symptomatic = other.symptomatic == null ? null : other.symptomatic.copy();
        this.ccmsLogin = other.ccmsLogin == null ? null : other.ccmsLogin.copy();
        this.selfRegistered = other.selfRegistered == null ? null : other.selfRegistered.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.isolationStartDate = other.isolationStartDate == null ? null : other.isolationStartDate.copy();
        this.isolationEndDate = other.isolationEndDate == null ? null : other.isolationEndDate.copy();
        this.isolationDetailsId = other.isolationDetailsId == null ? null : other.isolationDetailsId.copy();
        this.isolationId = other.isolationId == null ? null : other.isolationId.copy();
    }

    @Override
    public IsolationCriteria copy() {
        return new IsolationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIcmrId() {
        return icmrId;
    }

    public void setIcmrId(StringFilter icmrId) {
        this.icmrId = icmrId;
    }

    public StringFilter getRtpcrId() {
        return rtpcrId;
    }

    public void setRtpcrId(StringFilter rtpcrId) {
        this.rtpcrId = rtpcrId;
    }

    public StringFilter getRatId() {
        return ratId;
    }

    public void setRatId(StringFilter ratId) {
        this.ratId = ratId;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
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

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(StringFilter passwordHash) {
        this.passwordHash = passwordHash;
    }

    public StringFilter getSecondaryContactNo() {
        return secondaryContactNo;
    }

    public void setSecondaryContactNo(StringFilter secondaryContactNo) {
        this.secondaryContactNo = secondaryContactNo;
    }

    public StringFilter getAadharCardNo() {
        return aadharCardNo;
    }

    public void setAadharCardNo(StringFilter aadharCardNo) {
        this.aadharCardNo = aadharCardNo;
    }

    public IsolationStatusFilter getStatus() {
        return status;
    }

    public void setStatus(IsolationStatusFilter status) {
        this.status = status;
    }

    public StringFilter getAge() {
        return age;
    }

    public void setAge(StringFilter age) {
        this.age = age;
    }

    public StringFilter getGender() {
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
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

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getPincode() {
        return pincode;
    }

    public void setPincode(StringFilter pincode) {
        this.pincode = pincode;
    }

    public InstantFilter getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(InstantFilter collectionDate) {
        this.collectionDate = collectionDate;
    }

    public BooleanFilter getHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(BooleanFilter hospitalized) {
        this.hospitalized = hospitalized;
    }

    public LongFilter getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(LongFilter hospitalId) {
        this.hospitalId = hospitalId;
    }

    public StringFilter getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(StringFilter addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public StringFilter getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(StringFilter addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public StringFilter getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(StringFilter currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public StringFilter getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(StringFilter currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public InstantFilter getHospitalizationDate() {
        return hospitalizationDate;
    }

    public void setHospitalizationDate(InstantFilter hospitalizationDate) {
        this.hospitalizationDate = hospitalizationDate;
    }

    public HealthConditionFilter getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(HealthConditionFilter healthCondition) {
        this.healthCondition = healthCondition;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public BooleanFilter getSymptomatic() {
        return symptomatic;
    }

    public void setSymptomatic(BooleanFilter symptomatic) {
        this.symptomatic = symptomatic;
    }

    public StringFilter getCcmsLogin() {
        return ccmsLogin;
    }

    public void setCcmsLogin(StringFilter ccmsLogin) {
        this.ccmsLogin = ccmsLogin;
    }

    public BooleanFilter getSelfRegistered() {
        return selfRegistered;
    }

    public void setSelfRegistered(BooleanFilter selfRegistered) {
        this.selfRegistered = selfRegistered;
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

    public InstantFilter getIsolationStartDate() {
        return isolationStartDate;
    }

    public void setIsolationStartDate(InstantFilter isolationStartDate) {
        this.isolationStartDate = isolationStartDate;
    }

    public InstantFilter getIsolationEndDate() {
        return isolationEndDate;
    }

    public void setIsolationEndDate(InstantFilter isolationEndDate) {
        this.isolationEndDate = isolationEndDate;
    }

    public LongFilter getIsolationDetailsId() {
        return isolationDetailsId;
    }

    public void setIsolationDetailsId(LongFilter isolationDetailsId) {
        this.isolationDetailsId = isolationDetailsId;
    }

    public LongFilter getIsolationId() {
        return isolationId;
    }

    public void setIsolationId(LongFilter isolationId) {
        this.isolationId = isolationId;
    }

	public LongFilter getUserId() {
		return userId;
	}

	public void setUserId(LongFilter userId) {
		this.userId = userId;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IsolationCriteria that = (IsolationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(icmrId, that.icmrId) &&
            Objects.equals(rtpcrId, that.rtpcrId) &&
            Objects.equals(ratId, that.ratId) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(email, that.email) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(passwordHash, that.passwordHash) &&
            Objects.equals(secondaryContactNo, that.secondaryContactNo) &&
            Objects.equals(aadharCardNo, that.aadharCardNo) &&
            Objects.equals(status, that.status) &&
            Objects.equals(age, that.age) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(stateId, that.stateId) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(talukaId, that.talukaId) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(address, that.address) &&
            Objects.equals(pincode, that.pincode) &&
            Objects.equals(collectionDate, that.collectionDate) &&
            Objects.equals(hospitalized, that.hospitalized) &&
            Objects.equals(hospitalId, that.hospitalId) &&
            Objects.equals(addressLatitude, that.addressLatitude) &&
            Objects.equals(addressLongitude, that.addressLongitude) &&
            Objects.equals(currentLatitude, that.currentLatitude) &&
            Objects.equals(currentLongitude, that.currentLongitude) &&
            Objects.equals(hospitalizationDate, that.hospitalizationDate) &&
            Objects.equals(healthCondition, that.healthCondition) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(symptomatic, that.symptomatic) &&
            Objects.equals(ccmsLogin, that.ccmsLogin) &&
            Objects.equals(selfRegistered, that.selfRegistered) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(isolationStartDate, that.isolationStartDate) &&
            Objects.equals(isolationEndDate, that.isolationEndDate) &&
            Objects.equals(isolationDetailsId, that.isolationDetailsId) &&
            Objects.equals(isolationId, that.isolationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        icmrId,
        rtpcrId,
        ratId,
        firstName,
        lastName,
        latitude,
        longitude,
        email,
        imageUrl,
        activated,
        mobileNo,
        passwordHash,
        secondaryContactNo,
        aadharCardNo,
        status,
        age,
        gender,
        stateId,
        districtId,
        talukaId,
        cityId,
        address,
        pincode,
        collectionDate,
        hospitalized,
        hospitalId,
        addressLatitude,
        addressLongitude,
        currentLatitude,
        currentLongitude,
        hospitalizationDate,
        healthCondition,
        remarks,
        symptomatic,
        ccmsLogin,
        selfRegistered,
        lastModified,
        lastModifiedBy,
        isolationStartDate,
        isolationEndDate,
        isolationDetailsId,
        isolationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsolationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (icmrId != null ? "icmrId=" + icmrId + ", " : "") +
                (rtpcrId != null ? "rtpcrId=" + rtpcrId + ", " : "") +
                (ratId != null ? "ratId=" + ratId + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
                (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
                (passwordHash != null ? "passwordHash=" + passwordHash + ", " : "") +
                (secondaryContactNo != null ? "secondaryContactNo=" + secondaryContactNo + ", " : "") +
                (aadharCardNo != null ? "aadharCardNo=" + aadharCardNo + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (age != null ? "age=" + age + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (stateId != null ? "stateId=" + stateId + ", " : "") +
                (districtId != null ? "districtId=" + districtId + ", " : "") +
                (talukaId != null ? "talukaId=" + talukaId + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (pincode != null ? "pincode=" + pincode + ", " : "") +
                (collectionDate != null ? "collectionDate=" + collectionDate + ", " : "") +
                (hospitalized != null ? "hospitalized=" + hospitalized + ", " : "") +
                (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
                (addressLatitude != null ? "addressLatitude=" + addressLatitude + ", " : "") +
                (addressLongitude != null ? "addressLongitude=" + addressLongitude + ", " : "") +
                (currentLatitude != null ? "currentLatitude=" + currentLatitude + ", " : "") +
                (currentLongitude != null ? "currentLongitude=" + currentLongitude + ", " : "") +
                (hospitalizationDate != null ? "hospitalizationDate=" + hospitalizationDate + ", " : "") +
                (healthCondition != null ? "healthCondition=" + healthCondition + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (symptomatic != null ? "symptomatic=" + symptomatic + ", " : "") +
                (ccmsLogin != null ? "ccmsLogin=" + ccmsLogin + ", " : "") +
                (selfRegistered != null ? "selfRegistered=" + selfRegistered + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (isolationStartDate != null ? "isolationStartDate=" + isolationStartDate + ", " : "") +
                (isolationEndDate != null ? "isolationEndDate=" + isolationEndDate + ", " : "") +
                (isolationDetailsId != null ? "isolationDetailsId=" + isolationDetailsId + ", " : "") +
                (isolationId != null ? "isolationId=" + isolationId + ", " : "") +
            "}";
    }

}
