package com.merit.tlgapp.model;

public class ProjectRequest {
	private String userID;
	
	public ProjectRequest() {
		super();
	}
	public ProjectRequest(String userID) {
		super();
		
		this.userID = userID;
		
	}
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}
