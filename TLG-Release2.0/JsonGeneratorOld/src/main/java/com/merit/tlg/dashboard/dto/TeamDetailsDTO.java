package com.merit.tlg.dashboard.dto;

public class TeamDetailsDTO {
	public String teamId;
	public String teamName;
	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}
	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}
	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TeamData [teamId=" + teamId + ", teamName=" + teamName + "]";
	}
	

}
