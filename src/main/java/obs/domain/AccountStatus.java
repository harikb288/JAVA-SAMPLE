package obs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_user_accountstatus")
public class AccountStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DN_ID" , nullable = false )
	private Long id;
	
	@Column(name="DC_ACCOUNT_STATUS")
	private String accountStatusName;

	public Long getId() {
		return id;
	}

	public AccountStatus(Long id) {
		super();
		this.id = id;
	}

	public AccountStatus() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountStatusName() {
		return accountStatusName;
	}

	public void setAccountStatusName(String accountStatusName) {
		this.accountStatusName = accountStatusName;
	}


	
	
}
