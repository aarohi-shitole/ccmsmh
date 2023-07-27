package com.techvg.covid.care.odas.model;

import org.springframework.stereotype.Component;

@Component
public class IsolationRequest {

	public String username;
    public String password;
    public boolean rememberMe;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
    
    
}
