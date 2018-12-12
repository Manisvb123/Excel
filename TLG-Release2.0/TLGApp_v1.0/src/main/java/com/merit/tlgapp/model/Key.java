package com.merit.tlgapp.model;

public class Key {
	private String userID;
	private String teamID;
	private String toolID;
	private String projectID;
	private String questionnaireID;
	private String pageID;
	private int no_of_questions;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getTeamID() {
		return teamID;
	}
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	
	public String getToolID() {
		return toolID;
	}
	public void setToolID(String toolID) {
		this.toolID = toolID;
	}
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
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
	public int getNo_of_questions() {
		return no_of_questions;
	}
	public void setNo_of_questions(int no_of_questions) {
		this.no_of_questions = no_of_questions;
	}
	public Key(String userID, String teamID, String toolID, String projectID, String questionnaireID, String pageID, int no_of_questions) {
		super();
		this.userID = userID;
		this.teamID = teamID;
		this.toolID = toolID;
		this.projectID = projectID;
		this.questionnaireID = questionnaireID;
		this.pageID = pageID;
		this.no_of_questions = no_of_questions;
	}
	public Key() {
		super();
		// TODO Auto-generated constructor stub
	}
}
