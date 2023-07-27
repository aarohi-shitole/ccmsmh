package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.techvg.covid.care.domain.InventoryUsed} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.InventoryUsedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventory-useds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoryUsedCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter stock;

    private DoubleFilter capcity;

    private StringFilter comment;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter inventoryId;

    public InventoryUsedCriteria() {
    }

    public InventoryUsedCriteria(InventoryUsedCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stock = other.stock == null ? null : other.stock.copy();
        this.capcity = other.capcity == null ? null : other.capcity.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.inventoryId = other.inventoryId == null ? null : other.inventoryId.copy();
    }

    @Override
    public InventoryUsedCriteria copy() {
        return new InventoryUsedCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getStock() {
        return stock;
    }

    public void setStock(DoubleFilter stock) {
        this.stock = stock;
    }

    public DoubleFilter getCapcity() {
        return capcity;
    }

    public void setCapcity(DoubleFilter capcity) {
        this.capcity = capcity;
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
        final InventoryUsedCriteria that = (InventoryUsedCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(capcity, that.capcity) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(inventoryId, that.inventoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stock,
        capcity,
        comment,
        lastModified,
        lastModifiedBy,
        inventoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryUsedCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stock != null ? "stock=" + stock + ", " : "") +
                (capcity != null ? "capcity=" + capcity + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (inventoryId != null ? "inventoryId=" + inventoryId + ", " : "") +
            "}";
    }

}
