package com.techvg.covid.care.web.rest.vm;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * View Model object for storing a user's credentials.
 */
public class JWTToken {

	@NotNull
	private String idToken;
	private String mobileNo;
	
	public JWTToken() {
	}

	public JWTToken(String idToken, String mobileNo) {
		this.idToken = idToken;
		this.mobileNo = mobileNo;
	}

	@JsonProperty("id_token")
	public String getIdToken() {
		return idToken;
	}

	void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	@JsonProperty("mobile_no")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
}
