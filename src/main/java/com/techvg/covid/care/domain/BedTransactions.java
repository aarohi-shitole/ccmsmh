package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BedTransactions.
 */
@Entity
@Table(name = "bed_transactions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BedTransactions extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @JsonIgnoreProperties(value = "bedTransactions", allowSetters = true)
    private BedType bedType;

    @ManyToOne
    @JsonIgnoreProperties(value = "bedTransactions", allowSetters = true)
    private Hospital hospital;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOccupied() {
        return occupied;
    }

    public BedTransactions occupied(Long occupied) {
        this.occupied = occupied;
        return this;
    }

    public void setOccupied(Long occupied) {
        this.occupied = occupied;
    }

    public Long getOnCylinder() {
        return onCylinder;
    }

    public BedTransactions onCylinder(Long onCylinder) {
        this.onCylinder = onCylinder;
        return this;
    }

    public void setOnCylinder(Long onCylinder) {
        this.onCylinder = onCylinder;
    }

    public Long getOnLMO() {
        return onLMO;
    }

    public BedTransactions onLMO(Long onLMO) {
        this.onLMO = onLMO;
        return this;
    }

    public void setOnLMO(Long onLMO) {
        this.onLMO = onLMO;
    }

    public Long getOnConcentrators() {
        return onConcentrators;
    }

    public BedTransactions onConcentrators(Long onConcentrators) {
        this.onConcentrators = onConcentrators;
        return this;
    }

    public void setOnConcentrators(Long onConcentrators) {
        this.onConcentrators = onConcentrators;
    }

    public BedType getBedType() {
        return bedType;
    }

    public BedTransactions bedType(BedType bedType) {
        this.bedType = bedType;
        return this;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public BedTransactions hospital(Hospital hospital) {
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
        if (!(o instanceof BedTransactions)) {
            return false;
        }
        return id != null && id.equals(((BedTransactions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BedTransactions{" +
            "id=" + getId() +
            ", occupied=" + getOccupied() +
            ", onCylinder=" + getOnCylinder() +
            ", onLMO=" + getOnLMO() +
            ", onConcentrators=" + getOnConcentrators() +
            "}";
    }
}
