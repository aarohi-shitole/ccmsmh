package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A SecurityRole.
 */
@Entity
@Table(name = "security_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecurityRole extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "security_role_security_permission",
               joinColumns = @JoinColumn(name = "security_role_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "security_permission_id", referencedColumnName = "id"))
    private Set<SecurityPermission> securityPermissions = new HashSet<>();

    @ManyToMany(mappedBy = "securityRoles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<SecurityUser> securityUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SecurityRole name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public SecurityRole description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SecurityPermission> getSecurityPermissions() {
        return securityPermissions;
    }

    public SecurityRole securityPermissions(Set<SecurityPermission> securityPermissions) {
        this.securityPermissions = securityPermissions;
        return this;
    }

    public SecurityRole addSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.add(securityPermission);
        securityPermission.getSecurityRoles().add(this);
        return this;
    }

    public SecurityRole removeSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.remove(securityPermission);
        securityPermission.getSecurityRoles().remove(this);
        return this;
    }

    public void setSecurityPermissions(Set<SecurityPermission> securityPermissions) {
        this.securityPermissions = securityPermissions;
    }

    public Set<SecurityUser> getSecurityUsers() {
        return securityUsers;
    }

    public SecurityRole securityUsers(Set<SecurityUser> securityUsers) {
        this.securityUsers = securityUsers;
        return this;
    }

    public SecurityRole addSecurityUser(SecurityUser securityUser) {
        this.securityUsers.add(securityUser);
        securityUser.getSecurityRoles().add(this);
        return this;
    }

    public SecurityRole removeSecurityUser(SecurityUser securityUser) {
        this.securityUsers.remove(securityUser);
        securityUser.getSecurityRoles().remove(this);
        return this;
    }

    public void setSecurityUsers(Set<SecurityUser> securityUsers) {
        this.securityUsers = securityUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityRole)) {
            return false;
        }
        return id != null && id.equals(((SecurityRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityRole{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
