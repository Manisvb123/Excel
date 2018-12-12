package com.merit.tlgapp.model;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.Response;

public class Project {
	private String name;
	private String projectID;
	private String organizationID;
	private String startTime;
	private String status;
	private String date;
	private String lastModifiedDate;
	private Response jwsResponse; 
	private ArrayList<Project> projectRes;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Response getJwsResponse() {
		return jwsResponse;
	}

	public void setJwsResponse(Response jwsResponse) {
		this.jwsResponse = jwsResponse;
	}
	public ArrayList<Project> getProject() {
		return projectRes;
	}
	public void setProject(ArrayList<Project> projectRes) {
		this.projectRes = projectRes;
	}
	public void addProject(Project projectRes) {
		if(this.projectRes == null) {
			this.projectRes = new ArrayList<Project>();
		}
		this.projectRes.add(projectRes);
	}
	
}
