package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.techvg.covid.care.domain.enumeration.AccessLevel;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.techvg.covid.care.domain.UserAccess} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.UserAccessResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-accesses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserAccessCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AccessLevel
     */
    public static class AccessLevelFilter extends Filter<AccessLevel> {

        public AccessLevelFilter() {
        }

        public AccessLevelFilter(AccessLevelFilter filter) {
            super(filter);
        }

        @Override
        public AccessLevelFilter copy() {
            return new AccessLevelFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AccessLevelFilter level;

    private LongFilter accessId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter securityUserId;

    public UserAccessCriteria() {
    }

    public UserAccessCriteria(UserAccessCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.level = other.level == null ? null : other.level.copy();
        this.accessId = other.accessId == null ? null : other.accessId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
    }

    @Override
    public UserAccessCriteria copy() {
        return new UserAccessCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public AccessLevelFilter getLevel() {
        return level;
    }

    public void setLevel(AccessLevelFilter level) {
        this.level = level;
    }

    public LongFilter getAccessId() {
        return accessId;
    }

    public void setAccessId(LongFilter accessId) {
        this.accessId = accessId;
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

    public LongFilter getSecurityUserId() {
        return securityUserId;
    }

    public void setSecurityUserId(LongFilter securityUserId) {
        this.securityUserId = securityUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserAccessCriteria that = (UserAccessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(level, that.level) &&
            Objects.equals(accessId, that.accessId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(securityUserId, that.securityUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        level,
        accessId,
        lastModified,
        lastModifiedBy,
        securityUserId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAccessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (level != null ? "level=" + level + ", " : "") +
                (accessId != null ? "accessId=" + accessId + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            "}";
    }

}
