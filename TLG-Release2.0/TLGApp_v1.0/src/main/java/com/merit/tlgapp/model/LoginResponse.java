package com.merit.tlgapp.model;

import javax.ws.rs.core.Response;

public class LoginResponse {
	private String userId;
	private int ID;
	private String firstName;
	private String lastName;
	private String teamID;
	private int status;
	private Response jwsResponse;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTeamID() {
		return teamID;
	}
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	public String getLastName() {
		return lastName;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Response getJwsResponse() {
		return jwsResponse;
	}
	public void setJwsResponse(Response jwsResposne) {
		this.jwsResponse = jwsResposne;
	}

	
}
