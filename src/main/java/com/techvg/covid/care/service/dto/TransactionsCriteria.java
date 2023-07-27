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
 * Criteria class for the {@link com.techvg.covid.care.domain.Transactions} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.TransactionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionsCriteria implements Serializable, Criteria {
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

    private LongFilter stockReq;

    private LongFilter stockProvided;

    private TransactionStatusFilter status;

    private StringFilter comment;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter supplierId;

    private LongFilter inventoryId;

    public TransactionsCriteria() {
    }

    public TransactionsCriteria(TransactionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stockReq = other.stockReq == null ? null : other.stockReq.copy();
        this.stockProvided = other.stockProvided == null ? null : other.stockProvided.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.inventoryId = other.inventoryId == null ? null : other.inventoryId.copy();
    }

    @Override
    public TransactionsCriteria copy() {
        return new TransactionsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getStockReq() {
        return stockReq;
    }

    public void setStockReq(LongFilter stockReq) {
        this.stockReq = stockReq;
    }

    public LongFilter getStockProvided() {
        return stockProvided;
    }

    public void setStockProvided(LongFilter stockProvided) {
        this.stockProvided = stockProvided;
    }

    public TransactionStatusFilter getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusFilter status) {
        this.status = status;
    }

    public StringFilter getComment() {
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
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

    public LongFilter getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(LongFilter inventoryId) {
        this.inventoryId = inventoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransactionsCriteria that = (TransactionsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stockReq, that.stockReq) &&
            Objects.equals(stockProvided, that.stockProvided) &&
            Objects.equals(status, that.status) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(inventoryId, that.inventoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stockReq,
        stockProvided,
        status,
        comment,
        lastModified,
        lastModifiedBy,
        supplierId,
        inventoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stockReq != null ? "stockReq=" + stockReq + ", " : "") +
                (stockProvided != null ? "stockProvided=" + stockProvided + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (inventoryId != null ? "inventoryId=" + inventoryId + ", " : "") +
            "}";
    }

}
