package obs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_country")
public class Country implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DN_COUNTRY_ID" , nullable = false )
	private Long id;
	
	@Column(name="DC_COUNTRY_NAME")
	private String countryName;
	
	@Column(name="DC_COUNTRY_CODE")
	private String countryCode;
	
	public Country(Long id) {
		super();
		this.id = id;
	}


	@Column(name="DC_COUNTRY_DIAL_CODE")
	private String countryDialCode;
		
	@JsonIgnore
	@Column(name="DC_ISO")
	private String iso;
	
	@JsonIgnore
	@Column(name="DC_CURRENCY_NUMERIC")
	private String currencyNumeric;
	
	@JsonIgnore
	@Column(name="DC_COUNTRY_CODE_THREE")
	private String countryCodeThreeLetter;
	
	@JsonIgnore
	@Column(name="DC_NATIONALITY")
	private String nationality;
	
	@Column(name="DN_TOTAL_NUMBER")
	private int totalNumber;
	
	@Column(name="DB_MULTIPLE_FORMAT",columnDefinition="tinyint(1) default 1")
	private boolean multipleFormat;
	
	@Column(name="DC_PHONE_FORMAT")
	private String phoneFormat;

	public Long getId() {
		return id;
	}


	public String getCountryCode() {
		return countryCode;
	}

	public String getIso() {
		return iso;
	}

	public String getCountryCodeThreeLetter() {
		return countryCodeThreeLetter;
	}

	public String getNationality() {
		return nationality;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public void setCountryCodeThreeLetter(String countryCodeThreeLetter) {
		this.countryCodeThreeLetter = countryCodeThreeLetter;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCurrencyNumeric() {
		return currencyNumeric;
	}

	public void setCurrencyNumeric(String currencyNumeric) {
		this.currencyNumeric = currencyNumeric;
	}

	public String getCountryDialCode() {
		return countryDialCode;
	}

	public void setCountryDialCode(String countryDialCode) {
		this.countryDialCode = countryDialCode;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public boolean isMultipleFormat() {
		return multipleFormat;
	}

	public void setMultipleFormat(boolean multipleFormat) {
		this.multipleFormat = multipleFormat;
	}

	public String getPhoneFormat() {
		return phoneFormat;
	}

	public void setPhoneFormat(String phoneFormat) {
		this.phoneFormat = phoneFormat;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
