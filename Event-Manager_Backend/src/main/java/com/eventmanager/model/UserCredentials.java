package com.eventmanager.model;

public class UserCredentials {

    private String password;
    private String email;
	
    public UserCredentials() {
    	
	}
    
	public UserCredentials(String password, String email) {
		this.password = password;
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}