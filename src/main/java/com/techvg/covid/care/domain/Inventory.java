package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Inventory.
 */
@Entity
@Table(name = "inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inventory extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Double stock;

    @Column(name = "capcity")
    private Double capcity;

    @Column(name = "installed_capcity")
    private Double installedCapcity;
    @ManyToOne
    @JsonIgnoreProperties(value = "inventories", allowSetters = true)
    private InventoryMaster inventoryMaster;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventories", allowSetters = true)
    private Supplier supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventories", allowSetters = true)
    private Hospital hospital;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStock() {
        return stock;
    }

    public Inventory stock(Double stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getCapcity() {
        return capcity;
    }

    public Inventory capcity(Double capcity) {
        this.capcity = capcity;
        return this;
    }

    public void setCapcity(Double capcity) {
        this.capcity = capcity;
    }

    public Double getInstalledCapcity() {
        return installedCapcity;
    }

    public Inventory installedCapcity(Double installedCapcity) {
        this.installedCapcity = installedCapcity;
        return this;
    }

    public void setInstalledCapcity(Double installedCapcity) {
        this.installedCapcity = installedCapcity;
    }
    
    public InventoryMaster getInventoryMaster() {
        return inventoryMaster;
    }

    public Inventory inventoryMaster(InventoryMaster inventoryMaster) {
        this.inventoryMaster = inventoryMaster;
        return this;
    }

    public void setInventoryMaster(InventoryMaster inventoryMaster) {
        this.inventoryMaster = inventoryMaster;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Inventory supplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Inventory hospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventory)) {
            return false;
        }
        return id != null && id.equals(((Inventory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", capcity=" + getCapcity() +
            ", installedCapcity=" + getInstalledCapcity() +
            "}";
    }
}
