package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.techvg.covid.care.domain.enumeration.TransactionStatus;

/**
 * A Transactions.
 */
@Entity
@Table(name = "transactions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transactions extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "stock_req", nullable = false)
    private Long stockReq;

    @Column(name = "stock_provided")
    private Long stockProvided;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private Supplier supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private Inventory inventory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockReq() {
        return stockReq;
    }

    public Transactions stockReq(Long stockReq) {
        this.stockReq = stockReq;
        return this;
    }

    public void setStockReq(Long stockReq) {
        this.stockReq = stockReq;
    }

    public Long getStockProvided() {
        return stockProvided;
    }

    public Transactions stockProvided(Long stockProvided) {
        this.stockProvided = stockProvided;
        return this;
    }

    public void setStockProvided(Long stockProvided) {
        this.stockProvided = stockProvided;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Transactions status(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public Transactions comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public Supplier getSupplier() {
        return supplier;
    }

    public Transactions supplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Transactions inventory(Inventory inventory) {
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
        if (!(o instanceof Transactions)) {
            return false;
        }
        return id != null && id.equals(((Transactions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transactions{" +
            "id=" + getId() +
            ", stockReq=" + getStockReq() +
            ", stockProvided=" + getStockProvided() +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
