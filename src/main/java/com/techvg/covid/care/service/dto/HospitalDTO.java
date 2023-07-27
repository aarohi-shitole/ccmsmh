package com.techvg.covid.care.service.dto;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.techvg.covid.care.domain.enumeration.HospitalCategory;
import com.techvg.covid.care.domain.enumeration.HospitalSubCategory;
import com.techvg.covid.care.domain.enumeration.StatusInd;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.Hospital} entity.
 */
public class HospitalDTO implements Serializable {
    
    private Long id;

    @NotNull
    private HospitalCategory category;

    @NotNull
    private HospitalSubCategory subCategory;

    private String contactNo;

    private String latitude;

    private String longitude;

    private Integer docCount;

    private String email;

    @NotNull
    private String name;

    private String registrationNo;

    private String address1;

    private String address2;

    private String area;

    @NotNull
    private String pinCode;

    private Long hospitalId;

    private String odasFacilityId;

    private String referenceNumber;

    private StatusInd statusInd;


    private Instant lastModified;


    private String lastModifiedBy;


    private Long stateId;

    private String stateName;

    private Long districtId;

    private String districtName;

    private Long talukaId;

    private String talukaName;

    private Long cityId;

    private String cityName;

    private Long municipalCorpId;

    private String municipalCorpName;

    private Long hospitalTypeId;

    private String hospitalTypeName;
    private Set<SupplierDTO> suppliers = new HashSet<>();

    private List<InventoryDTO> inventory;

    private List<BedInventoryDTO> bedInventory;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HospitalCategory getCategory() {
        return category;
    }

    public void setCategory(HospitalCategory category) {
        this.category = category;
    }

    public HospitalSubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(HospitalSubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
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

    public Integer getDocCount() {
        return docCount;
    }

    public void setDocCount(Integer docCount) {
        this.docCount = docCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getOdasFacilityId() {
        return odasFacilityId;
    }

    public void setOdasFacilityId(String odasFacilityId) {
        this.odasFacilityId = odasFacilityId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public StatusInd getStatusInd() {
        return statusInd;
    }

    public void setStatusInd(StatusInd statusInd) {
        this.statusInd = statusInd;
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

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(Long talukaId) {
        this.talukaId = talukaId;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getMunicipalCorpId() {
        return municipalCorpId;
    }

    public void setMunicipalCorpId(Long municipalCorpId) {
        this.municipalCorpId = municipalCorpId;
    }

    public String getMunicipalCorpName() {
        return municipalCorpName;
    }

    public void setMunicipalCorpName(String municipalCorpName) {
        this.municipalCorpName = municipalCorpName;
    }

    public Long getHospitalTypeId() {
        return hospitalTypeId;
    }

    public void setHospitalTypeId(Long hospitalTypeId) {
        this.hospitalTypeId = hospitalTypeId;
    }

    public String getHospitalTypeName() {
        return hospitalTypeName;
    }

    public void setHospitalTypeName(String hospitalTypeName) {
        this.hospitalTypeName = hospitalTypeName;
    }


    public List<InventoryDTO> getInventory() {
		return inventory;
	}

	public void setInventory(List<InventoryDTO> inventory) {
		this.inventory = inventory;
	}

	public List<BedInventoryDTO> getBedInventory() {
		return bedInventory;
	}

	public void setBedInventory(List<BedInventoryDTO> bedInventory) {
		this.bedInventory = bedInventory;
	}


    public Set<SupplierDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<SupplierDTO> suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HospitalDTO)) {
            return false;
        }

        return id != null && id.equals(((HospitalDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HospitalDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", subCategory='" + getSubCategory() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", docCount=" + getDocCount() +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", registrationNo='" + getRegistrationNo() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", area='" + getArea() + "'" +
            ", pinCode='" + getPinCode() + "'" +
            ", hospitalId=" + getHospitalId() +
            ", odasFacilityId=" + getOdasFacilityId() +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", statusInd='" + getStatusInd() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", stateId=" + getStateId() +
            ", stateName='" + getStateName() + "'" +
            ", districtId=" + getDistrictId() +
            ", districtName='" + getDistrictName() + "'" +
            ", talukaId=" + getTalukaId() +
            ", talukaName='" + getTalukaName() + "'" +
            ", cityId=" + getCityId() +
            ", cityName='" + getCityName() + "'" +
            ", municipalCorpId=" + getMunicipalCorpId() +
            ", municipalCorpName='" + getMunicipalCorpName() + "'" +
            ", hospitalTypeId=" + getHospitalTypeId() +
            ", hospitalTypeName='" + getHospitalTypeName() + "'" +
            ", suppliers='" + getSuppliers() + "'" +
            "}";
    }

}
