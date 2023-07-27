package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BedInventory.
 */
@Entity
@Table(name = "bed_inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BedInventory extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "bed_count", nullable = false)
    private Long bedCount;

    @NotNull
    @Column(name = "occupied", nullable = false)
    private Long occupied;

    @Column(name = "on_cylinder")
    private Long onCylinder;

    @Column(name = "on_lmo")
    private Long onLMO;

    @Column(name = "on_concentrators")
    private Long onConcentrators;
    @ManyToOne
    @JsonIgnoreProperties(value = "bedInventories", allowSetters = true)
    private BedType bedType;

    @ManyToOne
    @JsonIgnoreProperties(value = "bedInventories", allowSetters = true)
    private Hospital hospital;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBedCount() {
        return bedCount;
    }

    public BedInventory bedCount(Long bedCount) {
        this.bedCount = bedCount;
        return this;
    }

    public void setBedCount(Long bedCount) {
        this.bedCount = bedCount;
    }

    public Long getOccupied() {
        return occupied;
    }

    public BedInventory occupied(Long occupied) {
        this.occupied = occupied;
        return this;
    }

    public void setOccupied(Long occupied) {
        this.occupied = occupied;
    }

    public Long getOnCylinder() {
        return onCylinder;
    }

    public BedInventory onCylinder(Long onCylinder) {
        this.onCylinder = onCylinder;
        return this;
    }

    public void setOnCylinder(Long onCylinder) {
        this.onCylinder = onCylinder;
    }

    public Long getOnLMO() {
        return onLMO;
    }

    public BedInventory onLMO(Long onLMO) {
        this.onLMO = onLMO;
        return this;
    }

    public void setOnLMO(Long onLMO) {
        this.onLMO = onLMO;
    }

    public Long getOnConcentrators() {
        return onConcentrators;
    }

    public BedInventory onConcentrators(Long onConcentrators) {
        this.onConcentrators = onConcentrators;
        return this;
    }

    public void setOnConcentrators(Long onConcentrators) {
        this.onConcentrators = onConcentrators;
    }
    public BedType getBedType() {
        return bedType;
    }

    public BedInventory bedType(BedType bedType) {
        this.bedType = bedType;
        return this;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public BedInventory hospital(Hospital hospital) {
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
        if (!(o instanceof BedInventory)) {
            return false;
        }
        return id != null && id.equals(((BedInventory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BedInventory{" +
            "id=" + getId() +
            ", bedCount=" + getBedCount() +
            ", occupied=" + getOccupied() +
            ", onCylinder=" + getOnCylinder() +
            ", onLMO=" + getOnLMO() +
            ", onConcentrators=" + getOnConcentrators() +
            "}";
    }
}
