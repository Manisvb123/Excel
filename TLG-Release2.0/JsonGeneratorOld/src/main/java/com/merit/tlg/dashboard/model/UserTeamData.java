package com.merit.tlg.dashboard.model;

import java.util.ArrayList;

public class UserTeamData {
	public String title;
	public String description;
	public String type;
	public String questionId;
	public String questionDesc;
	public ArrayList<TeamData> teamData;
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the questionId
	 */
	public String getQuestionId() {
		return questionId;
	}
	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	/**
	 * @return the questionDesc
	 */
	public String getQuestionDesc() {
		return questionDesc;
	}
	/**
	 * @param questionDesc the questionDesc to set
	 */
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
	/**
	 * @return the teamData
	 */
	public ArrayList<TeamData> getTeamData() {
		return teamData;
	}
	/**
	 * @param teamData the teamData to set
	 */
	public void setTeamData(ArrayList<TeamData> teamData) {
		this.teamData = teamData;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSTUserTeamData [title=" + title + ", description=" + description + ", type=" + type + ", questionId="
				+ questionId + ", questionDesc=" + questionDesc + ", teamData=" + teamData + "]";
	}
	
	
	


}
