package com.techvg.covid.care.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.InventoryUsed} entity.
 */
public class InventoryUsedDTO implements Serializable {
    
    private Long id;

    private double stock;

    private double capcity;

    private String comment;

    private Instant lastModified;

    private String lastModifiedBy;


    private Long inventoryId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getCapcity() {
        return capcity;
    }

    public void setCapcity(double capcity) {
        this.capcity = capcity;
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

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryUsedDTO)) {
            return false;
        }

        return id != null && id.equals(((InventoryUsedDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryUsedDTO{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", capcity=" + getCapcity() +
            ", comment='" + getComment() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", inventoryId=" + getInventoryId() +
            "}";
    }
}