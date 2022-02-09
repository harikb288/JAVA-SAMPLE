package obs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQuery(name="user.login", query="select u from User u where concat(u.countryDialCode,u.phone)= :id or u.email = :id or u.phone= :id")
@Table(name = "tbl_user")
public class User implements Serializable { 

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "DN_ID", nullable = false, unique = true)
	private String userId;
		
	@Column(name="DC_USERNAME")
	private String userName;
	
	@Column(name="DC_EMAil")
	private String email;
	
	public User(String userId) {
		super();
		this.userId = userId;
	}

	@Column(name="DN_PHONE")
	private String phone;
	
	@Column(name="DC_COUNTRY_DIAL_CODE")
	private String countryDialCode;	
	
	@OneToOne ( targetEntity = Country.class, fetch = FetchType.EAGER )
	@JoinColumn (name = "DC_COUNTRY")
	private Country country;

	@Column(name = "DD_CREATED_ON")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createdOn;

	@Column(name = "DB_ACTIVE",columnDefinition="tinyint(1) default 1")
	private boolean active;

	@Column(name = "DB_DELETED",columnDefinition="tinyint(1) default 0")
	private boolean deleted;
	
	@Column(name = "DB_PHONE_VERIFIED",columnDefinition="tinyint(1) default 0")
	private boolean phoneVerified;
	
	@OneToOne (targetEntity = UserType.class, fetch = FetchType.EAGER)
	@JoinColumn (name = "DN_USER_TYPE")
	private UserType userType;	
	
	@Column(name="DN_INVALID_ATTEMPT")
	private int invalidAttempt;
	
	@Column(name = "DD_BLOCKED_ON")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date blockedOn;
	
	@Column(name = "DB_BLOCKED",columnDefinition="tinyint(1) default 0")
	private boolean blocked;

	@Column(name="DB_NOTIFICATION",columnDefinition="tinyint(1) default 1")
	private boolean notification;
	
	@OneToOne ( targetEntity = AccountStatus.class, fetch = FetchType.EAGER )
	@JoinColumn (name = "DN_USER_ACCOUNT_STATUS")
	private AccountStatus accountstatus;
	
	@Column(name="DC_FIRST_NAME")
	private String firstName;
	
	@Column(name="DC_LAST_NAME")
	private String lastName;
	
	@Column(name = "DD_DATE_OF_BIRTH")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
	private Date dob;
	
	@Column(name="DC_TWITTER_ID")
	private String twitterId;
	
	@Column(name="DC_GOOGLE_ID")
	private String googleId;
	
	@Column(name="DC_FACEBOOK_ID")
	private String facebookId;

	@Transient
	@JsonIgnore
	private String accessToken;
	
	@Transient
	@JsonIgnore
	private boolean accessTokenValid;
	
	public String getUserId() {
		return userId;
	}

	public User() {
		super();
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountryDialCode() {
		return countryDialCode;
	}

	public void setCountryDialCode(String countryDialCode) {
		this.countryDialCode = countryDialCode;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isAccessTokenValid() {
		return accessTokenValid;
	}

	public void setAccessTokenValid(boolean accessTokenValid) {
		this.accessTokenValid = accessTokenValid;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public boolean isNotification() {
		return notification;
	}

	public void setNotification(boolean notification) {
		this.notification = notification;
	}

	public AccountStatus getAccountstatus() {
		return accountstatus;
	}

	public void setAccountstatus(AccountStatus accountstatus) {
		this.accountstatus = accountstatus;
	}

	public int getInvalidAttempt() {
		return invalidAttempt;
	}

	public void setInvalidAttempt(int invalidAttempt) {
		this.invalidAttempt = invalidAttempt;
	}

	public Date getBlockedOn() {
		return blockedOn;
	}

	public void setBlockedOn(Date blockedOn) {
		this.blockedOn = blockedOn;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isPhoneVerified() {
		return phoneVerified;
	}

	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
	
}