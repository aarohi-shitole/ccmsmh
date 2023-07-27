package com.techvg.covid.care.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.InventoryMaster} entity.
 */
public class InventoryMasterDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Double volume;

    @NotNull
    private String unit;

    private Double calulateVolume;

    private String dimensions;

    private String subTypeInd;

    private Boolean deleted=false;

    private Instant lastModified;


    private String lastModifiedBy;


    private Long inventoryTypeId;

    private String inventoryTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getCalulateVolume() {
        return calulateVolume;
    }

    public void setCalulateVolume(Double calulateVolume) {
        this.calulateVolume = calulateVolume;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getSubTypeInd() {
        return subTypeInd;
    }

    public void setSubTypeInd(String subTypeInd) {
        this.subTypeInd = subTypeInd;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
        if (!(o instanceof InventoryMasterDTO)) {
            return false;
        }

        return id != null && id.equals(((InventoryMasterDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryMasterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", volume=" + getVolume() +
            ", unit='" + getUnit() + "'" +
            ", calulateVolume=" + getCalulateVolume() +
            ", dimensions='" + getDimensions() + "'" +
            ", subTypeInd='" + getSubTypeInd() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", inventoryTypeId=" + getInventoryTypeId() +
            ", inventoryTypeName='" + getInventoryTypeName() + "'" +
            "}";
    }
}
