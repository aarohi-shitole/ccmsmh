package com.techvg.covid.care.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;

import com.techvg.covid.care.domain.enumeration.HealthCondition;
import com.techvg.covid.care.domain.enumeration.IsolationStatus;

import java.io.Serializable;

/**
 * A DTO for the {@link com.techvg.covid.care.isolation.domain.Isolation} entity.
 */
public class IsolationDTO implements Serializable {
    
    private Long id;

    private String icmrId;

    private String rtpcrId;

    private String ratId;

    private String firstName;

    private String lastName;

    private String latitude;

    private String longitude;

    private String email;

    private String imageUrl;

    private Boolean activated;

    @NotNull
    private String mobileNo;

    @NotNull
    private String passwordHash;

    private String secondaryContactNo;

    @NotNull
    private String aadharCardNo;

    private IsolationStatus status;

    private String age;

    private String gender;

    private Long stateId;

    private Long districtId;

    private Long talukaId;

    private Long cityId;

    private String address;

    private String pincode;

    private String collectionDate;

    private Boolean hospitalized;

    private Long hospitalId;

    private String addressLatitude;

    private String addressLongitude;

    private String currentLatitude;

    private String currentLongitude;

    private String hospitalizationDate;

    private HealthCondition healthCondition;

    private String remarks;

    private Boolean symptomatic;

    private String ccmsLogin;

    private Boolean selfRegistered;

    private Instant lastModified = Instant.now();;

    private String lastModifiedBy;

    private String isolationStartDate;

    private String isolationEndDate;

    private Long isolationDetailsId;

    private Long isolationId;
    
    private Long userId;
    
    private int voilationCount;
    
    private IsolationDetailsDTO IsolationDetails;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcmrId() {
        return icmrId;
    }

    public void setIcmrId(String icmrId) {
        this.icmrId = icmrId;
    }

    public String getRtpcrId() {
        return rtpcrId;
    }

    public void setRtpcrId(String rtpcrId) {
        this.rtpcrId = rtpcrId;
    }

    public String getRatId() {
        return ratId;
    }

    public void setRatId(String ratId) {
        this.ratId = ratId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSecondaryContactNo() {
        return secondaryContactNo;
    }

    public void setSecondaryContactNo(String secondaryContactNo) {
        this.secondaryContactNo = secondaryContactNo;
    }

    public String getAadharCardNo() {
        return aadharCardNo;
    }

    public void setAadharCardNo(String aadharCardNo) {
        this.aadharCardNo = aadharCardNo;
    }

    public IsolationStatus getStatus() {
        return status;
    }

    public void setStatus(IsolationStatus status) {
        this.status = status;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(Long talukaId) {
        this.talukaId = talukaId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Boolean isHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(Boolean hospitalized) {
        this.hospitalized = hospitalized;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(String addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public String getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(String addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public String getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(String currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public String getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(String currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public String getHospitalizationDate() {
        return hospitalizationDate;
    }

    public void setHospitalizationDate(String hospitalizationDate) {
        this.hospitalizationDate = hospitalizationDate;
    }

    public HealthCondition getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(HealthCondition healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean isSymptomatic() {
        return symptomatic;
    }

    public void setSymptomatic(Boolean symptomatic) {
        this.symptomatic = symptomatic;
    }

    public String getCcmsLogin() {
        return ccmsLogin;
    }

    public void setCcmsLogin(String ccmsLogin) {
        this.ccmsLogin = ccmsLogin;
    }

    public Boolean isSelfRegistered() {
        return selfRegistered;
    }

    public void setSelfRegistered(Boolean selfRegistered) {
        this.selfRegistered = selfRegistered;
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

    public String getIsolationStartDate() {
        return isolationStartDate;
    }

    public void setIsolationStartDate(String isolationStartDate) {
        this.isolationStartDate = isolationStartDate;
    }

    public String getIsolationEndDate() {
        return isolationEndDate;
    }

    public void setIsolationEndDate(String isolationEndDate) {
        this.isolationEndDate = isolationEndDate;
    }

    public Long getIsolationDetailsId() {
        return isolationDetailsId;
    }

    public void setIsolationDetailsId(Long isolationDetailsId) {
        this.isolationDetailsId = isolationDetailsId;
    }

    public Long getIsolationId() {
        return isolationId;
    }

    public void setIsolationId(Long isolationId) {
        this.isolationId = isolationId;
    }
    
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public int getVoilationCount() {
		return voilationCount;
	}

	public void setVoilationCount(int voilationCount) {
		this.voilationCount = voilationCount;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IsolationDTO)) {
            return false;
        }

        return id != null && id.equals(((IsolationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsolationDTO{" +
            "id=" + getId() +
            ", icmrId='" + getIcmrId() + "'" +
            ", rtpcrId='" + getRtpcrId() + "'" +
            ", ratId='" + getRatId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", email='" + getEmail() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", activated='" + isActivated() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", secondaryContactNo='" + getSecondaryContactNo() + "'" +
            ", aadharCardNo='" + getAadharCardNo() + "'" +
            ", status='" + getStatus() + "'" +
            ", age='" + getAge() + "'" +
            ", gender='" + getGender() + "'" +
            ", stateId=" + getStateId() +
            ", districtId=" + getDistrictId() +
            ", talukaId=" + getTalukaId() +
            ", cityId=" + getCityId() +
            ", address='" + getAddress() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", collectionDate='" + getCollectionDate() + "'" +
            ", hospitalized='" + isHospitalized() + "'" +
            ", hospitalId=" + getHospitalId() +
            ", addressLatitude='" + getAddressLatitude() + "'" +
            ", addressLongitude='" + getAddressLongitude() + "'" +
            ", currentLatitude='" + getCurrentLatitude() + "'" +
            ", currentLongitude='" + getCurrentLongitude() + "'" +
            ", hospitalizationDate='" + getHospitalizationDate() + "'" +
            ", healthCondition='" + getHealthCondition() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", symptomatic='" + isSymptomatic() + "'" +
            ", ccmsLogin='" + getCcmsLogin() + "'" +
            ", selfRegistered='" + isSelfRegistered() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", isolationStartDate='" + getIsolationStartDate() + "'" +
            ", isolationEndDate='" + getIsolationEndDate() + "'" +
            ", isolationDetailsId=" + getIsolationDetailsId() +
            ", isolationId=" + getIsolationId() +
            ", userId=" + getUserId() +
            ", voilationCount=" + getVoilationCount() +
            "}";
    }

	public IsolationDetailsDTO getIsolationDetails() {
		return IsolationDetails;
	}

	public void setIsolationDetails(IsolationDetailsDTO isolationDetails) {
		IsolationDetails = isolationDetails;
	}

}
