package obs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_user_security")
public class UserSecurity implements Serializable { 
	private static final long serialVersionUID = 1L;
	public UserSecurity() {
		super();
	}
	public UserSecurity(String userSecurityId) {
		super();
		this.userSecurityId = userSecurityId;
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name="DN_ID", nullable = false, unique=true)
	private String userSecurityId;
	
	@OneToOne (targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn (name = "DN_USER")
	@JsonIgnore
	private User user;
	
	@JsonIgnore
	@Column(name = "DC_PASSWORD")
	private String password;
	
	@Column(name="DC_USERNAME")
	private String userName;

	public String getUserSecurityId() {
		return userSecurityId;
	}

	public void setUserSecurityId(String userSecurityId) {
		this.userSecurityId = userSecurityId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
