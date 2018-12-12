package com.merit.tlgapp.model;

public class ToolsRequest {
	
	private String projectID;
	
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public ToolsRequest() {
		super();
	}
	public ToolsRequest(String projectID) {
		super();
		
		this.projectID = projectID;
		
	}

}
