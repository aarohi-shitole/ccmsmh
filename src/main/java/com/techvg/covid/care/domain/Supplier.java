package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.techvg.covid.care.domain.enumeration.SupplierType;

import com.techvg.covid.care.domain.enumeration.StatusInd;

/**
 * A Supplier.
 */
@Entity
@Table(name = "supplier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Supplier extends AbstractAuditingEntity  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "supplier_type", nullable = false)
    private SupplierType supplierType;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "email")
    private String email;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status_ind")
    private StatusInd statusInd;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private State state;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private District district;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private Taluka taluka;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private City city;

    @ManyToOne
    @JsonIgnoreProperties(value = "suppliers", allowSetters = true)
    private InventoryType inventoryType;

    @ManyToMany(mappedBy = "suppliers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Hospital> hospitals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Supplier name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupplierType getSupplierType() {
        return supplierType;
    }

    public Supplier supplierType(SupplierType supplierType) {
        this.supplierType = supplierType;
        return this;
    }

    public void setSupplierType(SupplierType supplierType) {
        this.supplierType = supplierType;
    }

    public String getContactNo() {
        return contactNo;
    }

    public Supplier contactNo(String contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLatitude() {
        return latitude;
    }

    public Supplier latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Supplier longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public Supplier email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public Supplier registrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
        return this;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getAddress1() {
        return address1;
    }

    public Supplier address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public Supplier address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getArea() {
        return area;
    }

    public Supplier area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPinCode() {
        return pinCode;
    }

    public Supplier pinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public StatusInd getStatusInd() {
        return statusInd;
    }

    public Supplier statusInd(StatusInd statusInd) {
        this.statusInd = statusInd;
        return this;
    }

    public void setStatusInd(StatusInd statusInd) {
        this.statusInd = statusInd;
    }
    
    public State getState() {
        return state;
    }

    public Supplier state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public District getDistrict() {
        return district;
    }

    public Supplier district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Taluka getTaluka() {
        return taluka;
    }

    public Supplier taluka(Taluka taluka) {
        this.taluka = taluka;
        return this;
    }

    public void setTaluka(Taluka taluka) {
        this.taluka = taluka;
    }

    public City getCity() {
        return city;
    }

    public Supplier city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public Supplier inventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
        return this;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Set<Hospital> getHospitals() {
        return hospitals;
    }

    public Supplier hospitals(Set<Hospital> hospitals) {
        this.hospitals = hospitals;
        return this;
    }

    public Supplier addHospital(Hospital hospital) {
        this.hospitals.add(hospital);
        hospital.getSuppliers().add(this);
        return this;
    }

    public Supplier removeHospital(Hospital hospital) {
        this.hospitals.remove(hospital);
        hospital.getSuppliers().remove(this);
        return this;
    }

    public void setHospitals(Set<Hospital> hospitals) {
        this.hospitals = hospitals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supplier)) {
            return false;
        }
        return id != null && id.equals(((Supplier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Supplier{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", supplierType='" + getSupplierType() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", email='" + getEmail() + "'" +
            ", registrationNo='" + getRegistrationNo() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", area='" + getArea() + "'" +
            ", pinCode='" + getPinCode() + "'" +
            ", statusInd='" + getStatusInd() + "'" +
            "}";
    }
}
