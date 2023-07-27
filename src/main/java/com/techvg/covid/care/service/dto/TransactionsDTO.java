package com.techvg.covid.care.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.Transactions} entity.
 */
public class TransactionsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long stockReq;

    private Long stockProvided;

    @NotNull
    private TransactionStatus status;

    private String comment;

    private Instant lastModified;

    private String lastModifiedBy;


    private Long supplierId;

    private String supplierName;

    private Long inventoryId;
    
    private Long hospitalId;
    
    private String hospitalName;
    
    private Long inventoryMasterId;
    
    private String inventoryMasterName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockReq() {
        return stockReq;
    }

    public void setStockReq(Long stockReq) {
        this.stockReq = stockReq;
    }

    public Long getStockProvided() {
        return stockProvided;
    }

    public void setStockProvided(Long stockProvided) {
        this.stockProvided = stockProvided;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
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
	
	public Long getInventoryMasterId() {
		return inventoryMasterId;
	}

	public void setInventoryMasterId(Long inventoryMasterId) {
		this.inventoryMasterId = inventoryMasterId;
	}

	public String getInventoryMasterName() {
		return inventoryMasterName;
	}

	public void setInventoryMasterName(String inventoryMasterName) {
		this.inventoryMasterName = inventoryMasterName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionsDTO)) {
            return false;
        }

        return id != null && id.equals(((TransactionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionsDTO{" +
            "id=" + getId() +
            ", stockReq=" + getStockReq() +
            ", stockProvided=" + getStockProvided() +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", inventoryId=" + getInventoryId() +
            "}";
    }
}
