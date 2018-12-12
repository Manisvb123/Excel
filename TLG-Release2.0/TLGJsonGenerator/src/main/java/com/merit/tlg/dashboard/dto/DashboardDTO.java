package com.merit.tlg.dashboard.dto;

import java.sql.Timestamp;

public class DashboardDTO {
	public String orgdId;
	public String toolId;
	public String projectId;
	public String dashboardId;
	public String dashboardName;
	public String dashboardJsonTemplate;
	public String dashboardJsonUrl;
	public String status;
	public Timestamp analysisDate;
	public Timestamp generationDate;
	public Timestamp creationDate;
	public Timestamp modifiedDate;
	
	/**
	 * @return the orgdId
	 */
	public String getOrgdId() {
		return orgdId;
	}
	/**
	 * @param orgdId the orgdId to set
	 */
	public void setOrgdId(String orgdId) {
		this.orgdId = orgdId;
	}
	/**
	 * @return the toolId
	 */
	public String getToolId() {
		return toolId;
	}
	/**
	 * @param toolId the toolId to set
	 */
	public void setToolId(String toolId) {
		this.toolId = toolId;
	}
	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * @return the dashboardId
	 */
	public String getDashboardId() {
		return dashboardId;
	}
	/**
	 * @param dashboardId the dashboardId to set
	 */
	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}
	/**
	 * @return the dashboardName
	 */
	public String getDashboardName() {
		return dashboardName;
	}
	/**
	 * @param dashboardName the dashboardName to set
	 */
	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName;
	}
	/**
	 * @return the dashboardJsonTemplate
	 */
	public String getDashboardJsonTemplate() {
		return dashboardJsonTemplate;
	}
	/**
	 * @param dashboardJsonTemplate the dashboardJsonTemplate to set
	 */
	public void setDashboardJsonTemplate(String dashboardJsonTemplate) {
		this.dashboardJsonTemplate = dashboardJsonTemplate;
	}
	/**
	 * @return the dashboardJsonUrl
	 */
	public String getDashboardJsonUrl() {
		return dashboardJsonUrl;
	}
	/**
	 * @param dashboardJsonUrl the dashboardJsonUrl to set
	 */
	public void setDashboardJsonUrl(String dashboardJsonUrl) {
		this.dashboardJsonUrl = dashboardJsonUrl;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the analysisDate
	 */
	public Timestamp getAnalysisDate() {
		return analysisDate;
	}
	/**
	 * @param analysisDate the analysisDate to set
	 */
	public void setAnalysisDate(Timestamp analysisDate) {
		this.analysisDate = analysisDate;
	}
	/**
	 * @return the generationDate
	 */
	public Timestamp getGenerationDate() {
		return generationDate;
	}
	/**
	 * @param generationDate the generationDate to set
	 */
	public void setGenerationDate(Timestamp generationDate) {
		this.generationDate = generationDate;
	}
	/**
	 * @return the creationDate
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DashboardDTO [orgdId=" + orgdId + ", toolId=" + toolId + ", projectId=" + projectId + ", dashboardId="
				+ dashboardId + ", dashboardName=" + dashboardName + ", dashboardJsonTemplate=" + dashboardJsonTemplate
				+ ", dashboardJsonUrl=" + dashboardJsonUrl + ", status=" + status + ", analysisDate=" + analysisDate
				+ ", generationDate=" + generationDate + ", creationDate=" + creationDate + ", modifiedDate="
				+ modifiedDate + "]";
	}
	

}
