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
 * Criteria class for the {@link com.techvg.covid.care.domain.InventoryMaster} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.InventoryMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventory-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoryMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private DoubleFilter volume;

    private StringFilter unit;

    private DoubleFilter calulateVolume;

    private StringFilter dimensions;

    private StringFilter subTypeInd;

    private BooleanFilter deleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter inventoryTypeId;

    public InventoryMasterCriteria() {
    }

    public InventoryMasterCriteria(InventoryMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.volume = other.volume == null ? null : other.volume.copy();
        this.unit = other.unit == null ? null : other.unit.copy();
        this.calulateVolume = other.calulateVolume == null ? null : other.calulateVolume.copy();
        this.dimensions = other.dimensions == null ? null : other.dimensions.copy();
        this.subTypeInd = other.subTypeInd == null ? null : other.subTypeInd.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.inventoryTypeId = other.inventoryTypeId == null ? null : other.inventoryTypeId.copy();
    }

    @Override
    public InventoryMasterCriteria copy() {
        return new InventoryMasterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public DoubleFilter getVolume() {
        return volume;
    }

    public void setVolume(DoubleFilter volume) {
        this.volume = volume;
    }

    public StringFilter getUnit() {
        return unit;
    }

    public void setUnit(StringFilter unit) {
        this.unit = unit;
    }

    public DoubleFilter getCalulateVolume() {
        return calulateVolume;
    }

    public void setCalulateVolume(DoubleFilter calulateVolume) {
        this.calulateVolume = calulateVolume;
    }

    public StringFilter getDimensions() {
        return dimensions;
    }

    public void setDimensions(StringFilter dimensions) {
        this.dimensions = dimensions;
    }

    public StringFilter getSubTypeInd() {
        return subTypeInd;
    }

    public void setSubTypeInd(StringFilter subTypeInd) {
        this.subTypeInd = subTypeInd;
    }

    public BooleanFilter getDeleted() {
        return deleted;
    }

    public void setDeleted(BooleanFilter deleted) {
        this.deleted = deleted;
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

    public LongFilter getInventoryTypeId() {
        return inventoryTypeId;
    }

    public void setInventoryTypeId(LongFilter inventoryTypeId) {
        this.inventoryTypeId = inventoryTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InventoryMasterCriteria that = (InventoryMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(volume, that.volume) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(calulateVolume, that.calulateVolume) &&
            Objects.equals(dimensions, that.dimensions) &&
            Objects.equals(subTypeInd, that.subTypeInd) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(inventoryTypeId, that.inventoryTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        volume,
        unit,
        calulateVolume,
        dimensions,
        subTypeInd,
        deleted,
        lastModified,
        lastModifiedBy,
        inventoryTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (volume != null ? "volume=" + volume + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (calulateVolume != null ? "calulateVolume=" + calulateVolume + ", " : "") +
                (dimensions != null ? "dimensions=" + dimensions + ", " : "") +
                (subTypeInd != null ? "subTypeInd=" + subTypeInd + ", " : "") +
                (deleted != null ? "deleted=" + deleted + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (inventoryTypeId != null ? "inventoryTypeId=" + inventoryTypeId + ", " : "") +
            "}";
    }

}
