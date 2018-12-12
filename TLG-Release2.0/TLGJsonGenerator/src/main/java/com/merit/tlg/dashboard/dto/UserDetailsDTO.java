package com.merit.tlg.dashboard.dto;

public class UserDetailsDTO {
	
	public String userId;
	public String userFirstName;
	public String userLastName;
	public String userOrg;
	public String userType;
	public String userDept;
	public String userTeamId;
	public String userTeamName;
	public String jobRole;
	public String contactEmail;
	public String contactPhone;
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}
	/**
	 * @param userFirstName the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	/**
	 * @return the userLastName
	 */
	public String getUserLastName() {
		return userLastName;
	}
	/**
	 * @param userLastName the userLastName to set
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	/**
	 * @return the userOrg
	 */
	public String getUserOrg() {
		return userOrg;
	}
	/**
	 * @param userOrg the userOrg to set
	 */
	public void setUserOrg(String userOrg) {
		this.userOrg = userOrg;
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return the userDept
	 */
	public String getUserDept() {
		return userDept;
	}
	/**
	 * @param userDept the userDept to set
	 */
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	
	/**
	 * @return the userTeamId
	 */
	public String getUserTeamId() {
		return userTeamId;
	}
	/**
	 * @param userTeamId the userTeamId to set
	 */
	public void setUserTeamId(String userTeamId) {
		this.userTeamId = userTeamId;
	}
	/**
	 * @return the userTeamName
	 */
	public String getUserTeamName() {
		return userTeamName;
	}
	/**
	 * @param userTeamName the userTeamName to set
	 */
	public void setUserTeamName(String userTeamName) {
		this.userTeamName = userTeamName;
	}
	/**
	 * @return the jobRole
	 */
	public String getJobRole() {
		return jobRole;
	}
	/**
	 * @param jobRole the jobRole to set
	 */
	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}
	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}
	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}
	/**
	 * @param contactPhone the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserData [userId=" + userId + ", userFirstName=" + userFirstName + ", userLastName=" + userLastName
				+ ", userOrg=" + userOrg + ", userType=" + userType + ", userDept=" + userDept + ", userTeamId="
				+ userTeamId + ", userTeamName=" + userTeamName + ", jobRole=" + jobRole + ", contactEmail="
				+ contactEmail + ", contactPhone=" + contactPhone + "]";
	}
	

}
