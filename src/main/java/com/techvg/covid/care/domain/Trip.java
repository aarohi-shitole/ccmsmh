package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.techvg.covid.care.domain.enumeration.TransactionStatus;

/**
 * A Trip.
 */
@Entity
@Table(name = "trip")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Trip  extends AbstractAuditingEntity  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tracking_no", nullable = false)
    private String trackingNo;

    @NotNull
    @Column(name = "moba_id", nullable = false)
    private Long mobaId;

    @NotNull
    @Column(name = "number_plate", nullable = false)
    private String numberPlate;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Double stock;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @CreatedDate
    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @CreatedBy
    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;


    @OneToMany(mappedBy = "trip")
    private Set<TripDetails> tripDetails = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "trips", allowSetters = true)
    private Supplier supplier;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public Trip trackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
        return this;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Long getMobaId() {
        return mobaId;
    }

    public Trip mobaId(Long mobaId) {
        this.mobaId = mobaId;
        return this;
    }

    public void setMobaId(Long mobaId) {
        this.mobaId = mobaId;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public Trip numberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
        return this;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Double getStock() {
        return stock;
    }

    public Trip stock(Double stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Trip status(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Trip createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Trip createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public Set<TripDetails> getTripDetails() {
        return tripDetails;
    }

    public Trip tripDetails(Set<TripDetails> tripDetails) {
        this.tripDetails = tripDetails;
        return this;
    }

    public Trip addTripDetails(TripDetails tripDetails) {
        this.tripDetails.add(tripDetails);
        tripDetails.setTrip(this);
        return this;
    }

    public Trip removeTripDetails(TripDetails tripDetails) {
        this.tripDetails.remove(tripDetails);
        tripDetails.setTrip(null);
        return this;
    }

    public void setTripDetails(Set<TripDetails> tripDetails) {
        this.tripDetails = tripDetails;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Trip supplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trip)) {
            return false;
        }
        return id != null && id.equals(((Trip) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trip{" +
            "id=" + getId() +
            ", trackingNo='" + getTrackingNo() + "'" +
            ", mobaId=" + getMobaId() +
            ", numberPlate='" + getNumberPlate() + "'" +
            ", stock=" + getStock() +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
