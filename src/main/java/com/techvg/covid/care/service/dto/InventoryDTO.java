package com.techvg.covid.care.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.Inventory} entity.
 */
public class InventoryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double stock;

    private Double capcity;

    private Double installedCapcity;


    private Instant lastModified;


    private String lastModifiedBy;


    private Long inventoryMasterId;

    private String inventoryMasterName;

    private Long supplierId;

    private String supplierName;

    private Long hospitalId;

    private String hospitalName;
    
	private Long inventoryTypeId;
	
	private String inventoryTypeName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getCapcity() {
        return capcity;
    }

    public void setCapcity(Double capcity) {
        this.capcity = capcity;
    }

    public Double getInstalledCapcity() {
        return installedCapcity;
    }

    public void setInstalledCapcity(Double installedCapcity) {
        this.installedCapcity = installedCapcity;
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

	public Long getInventoryTypeId() {
		return inventoryTypeId;
	}

	public void setInventoryTypeId(Long inventoryTypeId) {
		this.inventoryTypeId = inventoryTypeId;
	}

	public String getInventoryTypeName() {
		return inventoryTypeName;
	}

	public void setInventoryTypeName(String inventoryTypeName) {
		this.inventoryTypeName = inventoryTypeName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryDTO)) {
            return false;
        }

        return id != null && id.equals(((InventoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryDTO{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", capcity=" + getCapcity() +
            ", installedCapcity=" + getInstalledCapcity() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", inventoryMasterId=" + getInventoryMasterId() +
            ", inventoryMasterName='" + getInventoryMasterName() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", hospitalId=" + getHospitalId() +
            ", hospitalName='" + getHospitalName() + "'" +
            "}";
    }
}
