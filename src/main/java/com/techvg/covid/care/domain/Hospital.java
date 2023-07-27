package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.techvg.covid.care.domain.enumeration.HospitalCategory;

import com.techvg.covid.care.domain.enumeration.HospitalSubCategory;

import com.techvg.covid.care.domain.enumeration.StatusInd;

/**
 * A Hospital.
 */
@Entity
@Table(name = "hospital")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hospital extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private HospitalCategory category;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sub_category", nullable = false)
    private HospitalSubCategory subCategory;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "doc_count")
    private Integer docCount;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "registration_no")
    private String registrationNo;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "area")
    private String area;

    @NotNull
    @Column(name = "pin_code", nullable = false)
    private String pinCode;

    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "odas_facility_id")
    private String odasFacilityId;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_ind")
    private StatusInd statusInd;

    @ManyToOne
    @JsonIgnoreProperties(value = "hospitals", allowSetters = true)
    private State state;

    @ManyToOne
    @JsonIgnoreProperties(value = "hospitals", allowSetters = true)
    private District district;

    @ManyToOne
    @JsonIgnoreProperties(value = "hospitals", allowSetters = true)
    private Taluka taluka;

    @ManyToOne
    @JsonIgnoreProperties(value = "hospitals", allowSetters = true)
    private City city;

    @ManyToOne
    @JsonIgnoreProperties(value = "hospitals", allowSetters = true)
    private MunicipalCorp municipalCorp;

    @ManyToOne
    @JsonIgnoreProperties(value = "hospitals", allowSetters = true)
    private HospitalType hospitalType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "hospital_supplier",
               joinColumns = @JoinColumn(name = "hospital_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "supplier_id", referencedColumnName = "id"))
    private Set<Supplier> suppliers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HospitalCategory getCategory() {
        return category;
    }

    public Hospital category(HospitalCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(HospitalCategory category) {
        this.category = category;
    }

    public HospitalSubCategory getSubCategory() {
        return subCategory;
    }

    public Hospital subCategory(HospitalSubCategory subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public void setSubCategory(HospitalSubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getContactNo() {
        return contactNo;
    }

    public Hospital contactNo(String contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLatitude() {
        return latitude;
    }

    public Hospital latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Hospital longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getDocCount() {
        return docCount;
    }

    public Hospital docCount(Integer docCount) {
        this.docCount = docCount;
        return this;
    }

    public void setDocCount(Integer docCount) {
        this.docCount = docCount;
    }

    public String getEmail() {
        return email;
    }

    public Hospital email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Hospital name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public Hospital registrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
        return this;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getAddress1() {
        return address1;
    }

    public Hospital address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public Hospital address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getArea() {
        return area;
    }

    public Hospital area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPinCode() {
        return pinCode;
    }

    public Hospital pinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public Hospital hospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
        return this;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getOdasFacilityId() {
        return odasFacilityId;
    }

    public Hospital odasFacilityId(String odasFacilityId) {
        this.odasFacilityId = odasFacilityId;
        return this;
    }

    public void setOdasFacilityId(String odasFacilityId) {
        this.odasFacilityId = odasFacilityId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public Hospital referenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public StatusInd getStatusInd() {
        return statusInd;
    }

    public Hospital statusInd(StatusInd statusInd) {
        this.statusInd = statusInd;
        return this;
    }

    public void setStatusInd(StatusInd statusInd) {
        this.statusInd = statusInd;
    }

    public State getState() {
        return state;
    }

    public Hospital state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public District getDistrict() {
        return district;
    }

    public Hospital district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Taluka getTaluka() {
        return taluka;
    }

    public Hospital taluka(Taluka taluka) {
        this.taluka = taluka;
        return this;
    }

    public void setTaluka(Taluka taluka) {
        this.taluka = taluka;
    }

    public City getCity() {
        return city;
    }

    public Hospital city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public MunicipalCorp getMunicipalCorp() {
        return municipalCorp;
    }

    public Hospital municipalCorp(MunicipalCorp municipalCorp) {
        this.municipalCorp = municipalCorp;
        return this;
    }

    public void setMunicipalCorp(MunicipalCorp municipalCorp) {
        this.municipalCorp = municipalCorp;
    }

    public HospitalType getHospitalType() {
        return hospitalType;
    }

    public Hospital hospitalType(HospitalType hospitalType) {
        this.hospitalType = hospitalType;
        return this;
    }

    public void setHospitalType(HospitalType hospitalType) {
        this.hospitalType = hospitalType;
    }

    public Set<Supplier> getSuppliers() {
        return suppliers;
    }

    public Hospital suppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
        return this;
    }

    public Hospital addSupplier(Supplier supplier) {
        this.suppliers.add(supplier);
        supplier.getHospitals().add(this);
        return this;
    }

    public Hospital removeSupplier(Supplier supplier) {
        this.suppliers.remove(supplier);
        supplier.getHospitals().remove(this);
        return this;
    }

    public void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hospital)) {
            return false;
        }
        return id != null && id.equals(((Hospital) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hospital{" +
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
            "}";
    }
}
