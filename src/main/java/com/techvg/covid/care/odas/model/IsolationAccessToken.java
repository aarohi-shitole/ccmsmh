package com.techvg.covid.care.odas.model;

import org.springframework.stereotype.Component;

@Component
public class IsolationAccessToken {

	private String id_token;

	public String getId_token() {
		return id_token;
	}

	public void setId_token(String id_token) {
		this.id_token = id_token;
	}
	
	
	
}
