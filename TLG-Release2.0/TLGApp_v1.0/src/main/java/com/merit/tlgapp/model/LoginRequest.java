package com.merit.tlgapp.model;

public class LoginRequest {
	private String username;
	private String password;

	public LoginRequest() {
		super();
	}

	public LoginRequest(String userName,String Password) {
		super();
		
		this.username = userName;
		this.password=Password;
	}

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
	
}
