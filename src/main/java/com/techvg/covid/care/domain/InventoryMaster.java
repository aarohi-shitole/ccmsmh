package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A InventoryMaster.
 */
@Entity
@Table(name = "inventory_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryMaster extends AbstractAuditingEntity  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "volume")
    private Double volume;

    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "calulate_volume")
    private Double calulateVolume;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "sub_type_ind")
    private String subTypeInd;

    @Column(name = "deleted",columnDefinition = "boolean default false")
    private Boolean deleted;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryMasters", allowSetters = true)
    private InventoryType inventoryType;

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

    public InventoryMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public InventoryMaster description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getVolume() {
        return volume;
    }

    public InventoryMaster volume(Double volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getUnit() {
        return unit;
    }

    public InventoryMaster unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getCalulateVolume() {
        return calulateVolume;
    }

    public InventoryMaster calulateVolume(Double calulateVolume) {
        this.calulateVolume = calulateVolume;
        return this;
    }

    public void setCalulateVolume(Double calulateVolume) {
        this.calulateVolume = calulateVolume;
    }

    public String getDimensions() {
        return dimensions;
    }

    public InventoryMaster dimensions(String dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getSubTypeInd() {
        return subTypeInd;
    }

    public InventoryMaster subTypeInd(String subTypeInd) {
        this.subTypeInd = subTypeInd;
        return this;
    }

    public void setSubTypeInd(String subTypeInd) {
        this.subTypeInd = subTypeInd;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public InventoryMaster deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public InventoryMaster inventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
        return this;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryMaster)) {
            return false;
        }
        return id != null && id.equals(((InventoryMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", volume=" + getVolume() +
            ", unit='" + getUnit() + "'" +
            ", calulateVolume=" + getCalulateVolume() +
            ", dimensions='" + getDimensions() + "'" +
            ", subTypeInd='" + getSubTypeInd() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
