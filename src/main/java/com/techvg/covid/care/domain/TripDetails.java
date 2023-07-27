package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.techvg.covid.care.domain.enumeration.TransactionStatus;

/**
 * A TripDetails.
 */
@Entity
@Table(name = "trip_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TripDetails extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "stock_sent", nullable = false)
    private Long stockSent;
    
    
    @Column(name = "comment", nullable = true)
    private String comment;


    public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	@Column(name = "receiver_comment", nullable = true)
    private String receiverComment;


    public String getReceiverComment() {
		return receiverComment;
	}

	public void setReceiverComment(String receiverComment) {
		this.receiverComment = receiverComment;
	}

	@Column(name = "stock_rec")
    private Long stockRec;
 
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;
    
   

    
    @CreatedDate
    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @CreatedBy
    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "tripDetails", allowSetters = true)
    private Supplier supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "tripDetails", allowSetters = true)
    private Hospital hospital;

    @ManyToOne
    @JsonIgnoreProperties(value = "tripDetails", allowSetters = true)
    private Transactions transactions;

    @ManyToOne
    @JsonIgnoreProperties(value = "tripDetails", allowSetters = true)
    private Trip trip;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockSent() {
        return stockSent;
    }

    public TripDetails stockSent(Long stockSent) {
        this.stockSent = stockSent;
        return this;
    }

    public TripDetails comment(String comment) {
        this.comment = comment;
        return this;
    }

    
    public void setStockSent(Long stockSent) {
        this.stockSent = stockSent;
    }

    public Long getStockRec() {
        return stockRec;
    }

    public TripDetails stockRec(Long stockRec) {
        this.stockRec = stockRec;
        return this;
    }
    
    public TripDetails receiverComment(String receiverComment) {
        this.receiverComment = receiverComment;
        return this;
    }

    public void setStockRec(Long stockRec) {
        this.stockRec = stockRec;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public TripDetails status(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public TripDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TripDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public TripDetails supplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public TripDetails hospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Transactions getTransactions() {
        return transactions;
    }

    public TripDetails transactions(Transactions transactions) {
        this.transactions = transactions;
        return this;
    }

    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    public Trip getTrip() {
        return trip;
    }

    public TripDetails trip(Trip trip) {
        this.trip = trip;
        return this;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TripDetails)) {
            return false;
        }
        return id != null && id.equals(((TripDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripDetails{" +
            "id=" + getId() +
            ", stockSent=" + getStockSent() +
            ", stockRec=" + getStockRec() +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            ", receiverComment='" + getReceiverComment() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
