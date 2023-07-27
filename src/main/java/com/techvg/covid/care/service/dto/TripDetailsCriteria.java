package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.techvg.covid.care.domain.TripDetails} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.TripDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trip-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TripDetailsCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TransactionStatus
     */
    public static class TransactionStatusFilter extends Filter<TransactionStatus> {

        public TransactionStatusFilter() {
        }

        public TransactionStatusFilter(TransactionStatusFilter filter) {
            super(filter);
        }

        @Override
        public TransactionStatusFilter copy() {
            return new TransactionStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter stockSent;

    private DoubleFilter stockRec;

    private TransactionStatusFilter status;

    private InstantFilter createdDate;

    private StringFilter createdBy;
    
    private StringFilter comment;
    
    private StringFilter receiverComment;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter supplierId;

    private LongFilter hospitalId;

    private LongFilter transactionsId;

    private LongFilter tripId;
    
    private StringFilter trackingNo;

    public TripDetailsCriteria() {
    }

    public TripDetailsCriteria(TripDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stockSent = other.stockSent == null ? null : other.stockSent.copy();
        this.stockRec = other.stockRec == null ? null : other.stockRec.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.receiverComment = other.receiverComment == null ? null : other.receiverComment.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
        this.transactionsId = other.transactionsId == null ? null : other.transactionsId.copy();
        this.tripId = other.tripId == null ? null : other.tripId.copy();
        this.trackingNo = other.trackingNo == null ? null : other.trackingNo.copy();
    }

    @Override
    public TripDetailsCriteria copy() {
        return new TripDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getStockSent() {
        return stockSent;
    }

    public void setStockSent(DoubleFilter stockSent) {
        this.stockSent = stockSent;
    }

    public DoubleFilter getStockRec() {
        return stockRec;
    }

    public void setStockRec(DoubleFilter stockRec) {
        this.stockRec = stockRec;
    }

    public TransactionStatusFilter getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(LongFilter hospitalId) {
        this.hospitalId = hospitalId;
    }

    public LongFilter getTransactionsId() {
        return transactionsId;
    }

    public void setTransactionsId(LongFilter transactionsId) {
        this.transactionsId = transactionsId;
    }

    public LongFilter getTripId() {
        return tripId;
    }

    public void setTripId(LongFilter tripId) {
        this.tripId = tripId;
    }

	public StringFilter getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(StringFilter trackingNo) {
		this.trackingNo = trackingNo;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TripDetailsCriteria that = (TripDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stockSent, that.stockSent) &&
            Objects.equals(stockRec, that.stockRec) &&
            Objects.equals(status, that.status) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(receiverComment, that.receiverComment) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(hospitalId, that.hospitalId) &&
            Objects.equals(transactionsId, that.transactionsId) &&
            Objects.equals(tripId, that.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stockSent,
        stockRec,
        status,
        comment,
        receiverComment,
        createdDate,
        createdBy,
        lastModified,
        lastModifiedBy,
        supplierId,
        hospitalId,
        transactionsId,
        tripId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stockSent != null ? "stockSent=" + stockSent + ", " : "") +
                (stockRec != null ? "stockRec=" + stockRec + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (receiverComment != null ? "receiverComment=" + receiverComment + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
                (transactionsId != null ? "transactionsId=" + transactionsId + ", " : "") +
                (tripId != null ? "tripId=" + tripId + ", " : "") +
            "}";
    }

}
