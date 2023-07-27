package com.techvg.covid.care.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.techvg.covid.care.config.Constants;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A SecurityUser.
 */
@Entity
@Table(name = "security_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecurityUser extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "designation")
    private String designation;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;


    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated = false;

    @Column(name = "lang_key")
    private String langKey;

    @Column(name = "activation_key")
    private String activationKey;

    @Column(name = "reset_key")
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "one_time_password")
    private String oneTimePassword;

    @Column(name = "otp_expiry_time")
    private Instant otpExpiryTime;

	@ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "security_user_security_permission",
               joinColumns = @JoinColumn(name = "security_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "security_permission_id", referencedColumnName = "id"))
    private Set<SecurityPermission> securityPermissions = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "security_user_security_role",
               joinColumns = @JoinColumn(name = "security_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "security_role_id", referencedColumnName = "id"))
    private Set<SecurityRole> securityRoles = new HashSet<>();
    
    @OneToMany(mappedBy = "securityUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserAccess> userAccess = new ArrayList<UserAccess>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public SecurityUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public SecurityUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public SecurityUser designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLogin() {
        return login;
    }

    public SecurityUser login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public SecurityUser passwordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public SecurityUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public SecurityUser imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isActivated() {
        return activated;
    }

    public SecurityUser activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public SecurityUser langKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public SecurityUser activationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public SecurityUser resetKey(String resetKey) {
        this.resetKey = resetKey;
        return this;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public SecurityUser resetDate(Instant resetDate) {
        this.resetDate = resetDate;
        return this;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public SecurityUser mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public SecurityUser oneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
        return this;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public Instant getOtpExpiryTime() {
        return otpExpiryTime;
    }

    public SecurityUser otpExpiryTime(Instant otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
        return this;
    }

    public void setOtpExpiryTime(Instant otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
    }

    public Set<SecurityPermission> getSecurityPermissions() {
        return securityPermissions;
    }

    public SecurityUser securityPermissions(Set<SecurityPermission> securityPermissions) {
        this.securityPermissions = securityPermissions;
        return this;
    }

    public SecurityUser addSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.add(securityPermission);
        securityPermission.getSecurityUsers().add(this);
        return this;
    }

    public SecurityUser removeSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.remove(securityPermission);
        securityPermission.getSecurityUsers().remove(this);
        return this;
    }

    public void setSecurityPermissions(Set<SecurityPermission> securityPermissions) {
        this.securityPermissions = securityPermissions;
    }

    public Set<SecurityRole> getSecurityRoles() {
        return securityRoles;
    }

    public SecurityUser securityRoles(Set<SecurityRole> securityRoles) {
        this.securityRoles = securityRoles;
        return this;
    }

    public SecurityUser addSecurityRole(SecurityRole securityRole) {
        this.securityRoles.add(securityRole);
        securityRole.getSecurityUsers().add(this);
        return this;
    }

    public SecurityUser removeSecurityRole(SecurityRole securityRole) {
        this.securityRoles.remove(securityRole);
        securityRole.getSecurityUsers().remove(this);
        return this;
    }

    public void setSecurityRoles(Set<SecurityRole> securityRoles) {
        this.securityRoles = securityRoles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public List<UserAccess> getUserAccess() {
		return userAccess;
	}

	public void setUserAccess(List<UserAccess> userAccess) {
		this.userAccess = userAccess;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecurityUser other = (SecurityUser) obj;
		if (activated != other.activated)
			return false;
		if (activationKey == null) {
			if (other.activationKey != null)
				return false;
		} else if (!activationKey.equals(other.activationKey))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (langKey == null) {
			if (other.langKey != null)
				return false;
		} else if (!langKey.equals(other.langKey))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (mobileNo == null) {
			if (other.mobileNo != null)
				return false;
		} else if (!mobileNo.equals(other.mobileNo))
			return false;
		if (oneTimePassword == null) {
			if (other.oneTimePassword != null)
				return false;
		} else if (!oneTimePassword.equals(other.oneTimePassword))
			return false;
		if (otpExpiryTime == null) {
			if (other.otpExpiryTime != null)
				return false;
		} else if (!otpExpiryTime.equals(other.otpExpiryTime))
			return false;
		if (passwordHash == null) {
			if (other.passwordHash != null)
				return false;
		} else if (!passwordHash.equals(other.passwordHash))
			return false;
		if (resetDate == null) {
			if (other.resetDate != null)
				return false;
		} else if (!resetDate.equals(other.resetDate))
			return false;
		if (resetKey == null) {
			if (other.resetKey != null)
				return false;
		} else if (!resetKey.equals(other.resetKey))
			return false;
		if (securityPermissions == null) {
			if (other.securityPermissions != null)
				return false;
		} else if (!securityPermissions.equals(other.securityPermissions))
			return false;
		if (securityRoles == null) {
			if (other.securityRoles != null)
				return false;
		} else if (!securityRoles.equals(other.securityRoles))
			return false;
		if (userAccess == null) {
			if (other.userAccess != null)
				return false;
		} else if (!userAccess.equals(other.userAccess))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activated ? 1231 : 1237);
		result = prime * result + ((activationKey == null) ? 0 : activationKey.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((langKey == null) ? 0 : langKey.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((mobileNo == null) ? 0 : mobileNo.hashCode());
		result = prime * result + ((oneTimePassword == null) ? 0 : oneTimePassword.hashCode());
		result = prime * result + ((otpExpiryTime == null) ? 0 : otpExpiryTime.hashCode());
		result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
		result = prime * result + ((resetDate == null) ? 0 : resetDate.hashCode());
		result = prime * result + ((resetKey == null) ? 0 : resetKey.hashCode());
		result = prime * result + ((securityPermissions == null) ? 0 : securityPermissions.hashCode());
		result = prime * result + ((securityRoles == null) ? 0 : securityRoles.hashCode());
		result = prime * result + ((userAccess == null) ? 0 : userAccess.hashCode());
		return result;
	}

    @Override
	public String toString() {
		return "SecurityUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", login=" + login
				+ ", passwordHash=" + passwordHash + ", email=" + email + ", imageUrl=" + imageUrl + ", activated="
				+ activated + ", langKey=" + langKey + ", activationKey=" + activationKey + ", resetKey=" + resetKey
				+ ", resetDate=" + resetDate + ", mobileNo=" + mobileNo + ", oneTimePassword=" + oneTimePassword
				+ ", otpExpiryTime=" + otpExpiryTime + ", securityPermissions=" + securityPermissions
				+ ", securityRoles=" + securityRoles + ", userAccess=" + userAccess + "]";
	}
}
