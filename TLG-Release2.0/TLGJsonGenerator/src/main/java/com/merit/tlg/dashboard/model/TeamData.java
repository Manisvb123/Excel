package com.merit.tlg.dashboard.model;

import java.io.Serializable;
import java.util.ArrayList;

public class TeamData implements Serializable {
	private static final long serialVersionUID = 1L;
	public String teamId;
	public String teamName;
	public float teamScore;
	public ArrayList<UserData> userData;
	public ArrayList<DimensionData> dimdata;
	
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
	/**
	 * @return the teamScore
	 */
	public float getTeamScore() {
		return teamScore;
	}
	/**
	 * @param teamScore the teamScore to set
	 */
	public void setTeamScore(float teamScore) {
		this.teamScore = teamScore;
	}
	/**
	 * @return the userData
	 */
	public ArrayList<UserData> getUserData() {
		return userData;
	}
	/**
	 * @param userData the userData to set
	 */
	public void setUserData(ArrayList<UserData> userData) {
		this.userData = userData;
	}
	/**
	 * @return the dimdata
	 */
	public ArrayList<DimensionData> getDimdata() {
		return dimdata;
	}
	/**
	 * @param dimdata the dimdata to set
	 */
	public void setDimdata(ArrayList<DimensionData> dimdata) {
		this.dimdata = dimdata;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSTTeamData [teamId=" + teamId + ", teamName=" + teamName + ", teamScore=" + teamScore + ", userData="
				+ userData + ", dimdata=" + dimdata + "]";
	}
	
	

}
