package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SecurityPermission.
 */
@Entity
@Table(name = "security_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecurityPermission extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "securityPermissions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<SecurityRole> securityRoles = new HashSet<>();

    @ManyToMany(mappedBy = "securityPermissions")
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

    public SecurityPermission name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public SecurityPermission description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SecurityRole> getSecurityRoles() {
        return securityRoles;
    }

    public SecurityPermission securityRoles(Set<SecurityRole> securityRoles) {
        this.securityRoles = securityRoles;
        return this;
    }

    public SecurityPermission addSecurityRole(SecurityRole securityRole) {
        this.securityRoles.add(securityRole);
        securityRole.getSecurityPermissions().add(this);
        return this;
    }

    public SecurityPermission removeSecurityRole(SecurityRole securityRole) {
        this.securityRoles.remove(securityRole);
        securityRole.getSecurityPermissions().remove(this);
        return this;
    }

    public void setSecurityRoles(Set<SecurityRole> securityRoles) {
        this.securityRoles = securityRoles;
    }

    public Set<SecurityUser> getSecurityUsers() {
        return securityUsers;
    }

    public SecurityPermission securityUsers(Set<SecurityUser> securityUsers) {
        this.securityUsers = securityUsers;
        return this;
    }

    public SecurityPermission addSecurityUser(SecurityUser securityUser) {
        this.securityUsers.add(securityUser);
        securityUser.getSecurityPermissions().add(this);
        return this;
    }

    public SecurityPermission removeSecurityUser(SecurityUser securityUser) {
        this.securityUsers.remove(securityUser);
        securityUser.getSecurityPermissions().remove(this);
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
        if (!(o instanceof SecurityPermission)) {
            return false;
        }
        return id != null && id.equals(((SecurityPermission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityPermission{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
