package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.techvg.covid.care.domain.enumeration.AccessLevel;

/**
 * A UserAccess.
 */
@Entity
@Table(name = "user_access")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserAccess extends AbstractAuditingEntity  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private AccessLevel level;

    @Column(name = "access_id")
    private Long accessId;

    @ManyToOne
    @JsonIgnoreProperties(value = "userAccesses", allowSetters = true)
    private SecurityUser securityUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccessLevel getLevel() {
        return level;
    }

    public UserAccess level(AccessLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(AccessLevel level) {
        this.level = level;
    }

    public Long getAccessId() {
        return accessId;
    }

    public UserAccess accessId(Long accessId) {
        this.accessId = accessId;
        return this;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    public UserAccess securityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
        return this;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccess)) {
            return false;
        }
        return id != null && id.equals(((UserAccess) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAccess{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", accessId=" + getAccessId() +
            "}";
    }
}
