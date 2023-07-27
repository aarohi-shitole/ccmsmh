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
 * Criteria class for the {@link com.techvg.covid.care.domain.Inventory} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.InventoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter stock;

    private DoubleFilter capcity;

    private DoubleFilter installedCapcity;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter inventoryMasterId;

    private LongFilter supplierId;

    private LongFilter hospitalId;

    public InventoryCriteria() {
    }

    public InventoryCriteria(InventoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stock = other.stock == null ? null : other.stock.copy();
        this.capcity = other.capcity == null ? null : other.capcity.copy();
        this.installedCapcity = other.installedCapcity == null ? null : other.installedCapcity.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.inventoryMasterId = other.inventoryMasterId == null ? null : other.inventoryMasterId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
    }

    @Override
    public InventoryCriteria copy() {
        return new InventoryCriteria(this);
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

    public DoubleFilter getInstalledCapcity() {
        return installedCapcity;
    }

    public void setInstalledCapcity(DoubleFilter installedCapcity) {
        this.installedCapcity = installedCapcity;
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

    public LongFilter getInventoryMasterId() {
        return inventoryMasterId;
    }

    public void setInventoryMasterId(LongFilter inventoryMasterId) {
        this.inventoryMasterId = inventoryMasterId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InventoryCriteria that = (InventoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(capcity, that.capcity) &&
            Objects.equals(installedCapcity, that.installedCapcity) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(inventoryMasterId, that.inventoryMasterId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(hospitalId, that.hospitalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stock,
        capcity,
        installedCapcity,
        lastModified,
        lastModifiedBy,
        inventoryMasterId,
        supplierId,
        hospitalId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stock != null ? "stock=" + stock + ", " : "") +
                (capcity != null ? "capcity=" + capcity + ", " : "") +
                (installedCapcity != null ? "installedCapcity=" + installedCapcity + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (inventoryMasterId != null ? "inventoryMasterId=" + inventoryMasterId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
            "}";
    }

}
