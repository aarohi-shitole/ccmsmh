package com.techvg.covid.care.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.TripDetails} entity.
 */
public class TripDetailsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double stockSent;

    private Double stockRec;

    @NotNull
    private TransactionStatus status;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModified;

    private String lastModifiedBy;

    private Long supplierId;

    private String supplierName;
    
    private String supplierType;

    private Long hospitalId;

    private String hospitalName;
    
    private String tanckerNumberPlate;
    
    private String trackingNo;
    
    private String comment;
    

    private String receiverComment;
    
    
    public String getReceiverComment() {
		return receiverComment;
	}

	public void setReceiverComment(String receiverComment) {
		this.receiverComment = receiverComment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	private Long transactionsId;

    private Long tripId;
    
    public String getTanckerNumberPlate() {
		return tanckerNumberPlate;
	}

	public void setTanckerNumberPlate(String tanckerNumberPlate) {
		this.tanckerNumberPlate = tanckerNumberPlate;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStockSent() {
        return stockSent;
    }

    public void setStockSent(Double stockSent) {
        this.stockSent = stockSent;
    }

    public Double getStockRec() {
        return stockRec;
    }

    public void setStockRec(Double stockRec) {
        this.stockRec = stockRec;
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

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Long getTransactionsId() {
        return transactionsId;
    }

    public void setTransactionsId(Long transactionsId) {
        this.transactionsId = transactionsId;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TripDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((TripDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripDetailsDTO{" +
            "id=" + getId() +
            ", stockSent=" + getStockSent() +
            ", stockRec=" + getStockRec() +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            ", receiverComment='" + getReceiverComment() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", hospitalId=" + getHospitalId() +
            ", hospitalName='" + getHospitalName() + "'" +
            ", transactionsId=" + getTransactionsId() +
            ", tripId=" + getTripId() +
            "}";
    }
}
