package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A InventoryUsed.
 */
@Entity
@Table(name = "inventory_used")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryUsed extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock")
    private Double stock;
    
    @Column(name = "capcity")
    private Double capcity;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventoryUseds", allowSetters = true)
    private Inventory inventory;

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

    public InventoryUsed stock(Double stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getCapcity() {
        return capcity;
    }

    public InventoryUsed capcity(Double capcity) {
        this.capcity = capcity;
        return this;
    }

    public void setCapcity(Double capcity) {
        this.capcity = capcity;
    }

    public String getComment() {
        return comment;
    }

    public InventoryUsed comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public Inventory getInventory() {
        return inventory;
    }

    public InventoryUsed inventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryUsed)) {
            return false;
        }
        return id != null && id.equals(((InventoryUsed) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryUsed{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", capcity=" + getCapcity() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}