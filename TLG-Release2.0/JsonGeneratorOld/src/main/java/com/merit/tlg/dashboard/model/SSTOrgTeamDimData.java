package com.merit.tlg.dashboard.model;

import java.util.ArrayList;

public class SSTOrgTeamDimData {
	public String title;
	public String description;
	public String type;
	public String orgName;
	public float orgScore;
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
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the orgScore
	 */
	public float getOrgScore() {
		return orgScore;
	}
	/**
	 * @param orgScore the orgScore to set
	 */
	public void setOrgScore(float orgScore) {
		this.orgScore = orgScore;
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
		return "SSTOrgTeamDimData [title=" + title + ", description=" + description + ", type=" + type + ", orgName="
				+ orgName + ", orgScore=" + orgScore + ", teamData=" + teamData + "]";
	}
	
	
	
}
