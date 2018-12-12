package com.merit.tlgapp.model.generic;

public class GenericRequest {
	private String userID;
	private String projectID;
	private String toolID;
	private String questionnaireID;
	private String pageID;
	private String teamID;

	public GenericRequest() {
		super();
	}

	public GenericRequest(String userID, String projectID, String toolID, String questionnaireID, String pageID, String teamID) {
		super();
		this.userID = userID;
		this.projectID = projectID;
		this.toolID = toolID;
		this.questionnaireID = questionnaireID;
		this.pageID = pageID;
		this.teamID = teamID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getToolID() {
		return toolID;
	}

	public void setToolID(String toolID) {
		this.toolID = toolID;
	}

	public String getQuestionnaireID() {
		return questionnaireID;
	}

	public void setQuestionnaireID(String questionnaireID) {
		this.questionnaireID = questionnaireID;
	}

	public String getPageID() {
		return pageID;
	}

	public void setPageID(String pageID) {
		this.pageID = pageID;
	}

	public String getTeamID() {
		return teamID;
	}

	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
}
