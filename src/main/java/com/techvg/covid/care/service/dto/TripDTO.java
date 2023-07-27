package com.techvg.covid.care.service.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.Trip} entity.
 */
public class TripDTO implements Serializable {
    
    private Long id;

    private String trackingNo;

    @NotNull
    private Long mobaId;

    @NotNull
    private String numberPlate;

    @NotNull
    private Double stock;

    @NotNull
    private TransactionStatus status;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModified;

    private String lastModifiedBy;


    private Long supplierId;

    private String supplierName;
    
	private List<TripDetailsDTO> tripDetails = new ArrayList<TripDetailsDTO>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Long getMobaId() {
        return mobaId;
    }

    public void setMobaId(Long mobaId) {
        this.mobaId = mobaId;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<TripDetailsDTO> getTripDetails() {
		return tripDetails;
	}

	public void setTripDetails(List<TripDetailsDTO> tripDetails) {
		this.tripDetails = tripDetails;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TripDTO)) {
            return false;
        }

        return id != null && id.equals(((TripDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripDTO{" +
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
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            "}";
    }
}
