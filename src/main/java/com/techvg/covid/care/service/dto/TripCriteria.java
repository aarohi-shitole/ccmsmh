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
 * Criteria class for the {@link com.techvg.covid.care.domain.Trip} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.TripResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trips?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TripCriteria implements Serializable, Criteria {
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

    private StringFilter trackingNo;

    private LongFilter mobaId;

    private StringFilter numberPlate;

    private DoubleFilter stock;

    private TransactionStatusFilter status;

    private InstantFilter createdDate;

    private StringFilter createdBy;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter tripDetailsId;

    private LongFilter supplierId;

    public TripCriteria() {
    }

    public TripCriteria(TripCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.trackingNo = other.trackingNo == null ? null : other.trackingNo.copy();
        this.mobaId = other.mobaId == null ? null : other.mobaId.copy();
        this.numberPlate = other.numberPlate == null ? null : other.numberPlate.copy();
        this.stock = other.stock == null ? null : other.stock.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.tripDetailsId = other.tripDetailsId == null ? null : other.tripDetailsId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
    }

    @Override
    public TripCriteria copy() {
        return new TripCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(StringFilter trackingNo) {
        this.trackingNo = trackingNo;
    }

    public LongFilter getMobaId() {
        return mobaId;
    }

    public void setMobaId(LongFilter mobaId) {
        this.mobaId = mobaId;
    }

    public StringFilter getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(StringFilter numberPlate) {
        this.numberPlate = numberPlate;
    }

    public DoubleFilter getStock() {
        return stock;
    }

    public void setStock(DoubleFilter stock) {
        this.stock = stock;
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

    public LongFilter getTripDetailsId() {
        return tripDetailsId;
    }

    public void setTripDetailsId(LongFilter tripDetailsId) {
        this.tripDetailsId = tripDetailsId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TripCriteria that = (TripCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(trackingNo, that.trackingNo) &&
            Objects.equals(mobaId, that.mobaId) &&
            Objects.equals(numberPlate, that.numberPlate) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(tripDetailsId, that.tripDetailsId) &&
            Objects.equals(supplierId, that.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        trackingNo,
        mobaId,
        numberPlate,
        stock,
        status,
        createdDate,
        createdBy,
        lastModified,
        lastModifiedBy,
        tripDetailsId,
        supplierId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (trackingNo != null ? "trackingNo=" + trackingNo + ", " : "") +
                (mobaId != null ? "mobaId=" + mobaId + ", " : "") +
                (numberPlate != null ? "numberPlate=" + numberPlate + ", " : "") +
                (stock != null ? "stock=" + stock + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (tripDetailsId != null ? "tripDetailsId=" + tripDetailsId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
            "}";
    }

}
