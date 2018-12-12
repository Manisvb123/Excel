package com.merit.tlg.dashboard.dto;

public class ScoreCardDTO {
	public String scoreCardId;
	public String toolId;
	public String projectId;
	public String entityType;
	public String entityId;
	public String dimensionType;
	public String dimensionId;
	public String questionId;
	public String metricName;
	public float metricValue;
	public float benchMark;
	public String label;
	public String parentScoreCardType;
	public String childScoreCardType;
	public UserDetailsDTO userData;
	public TeamDetailsDTO teamData;
	public OrgDTO	orgData;
	public QuestionDTO	questionData;
	public ResponseMetricDTO responseMetricData;
	public DimensionDTO dimensionData;
	public ToolsDTO toolsData;
	/**
	 * @return the scoreCardId
	 */
	public String getScoreCardId() {
		return scoreCardId;
	}
	/**
	 * @param scoreCardId the scoreCardId to set
	 */
	public void setScoreCardId(String scoreCardId) {
		this.scoreCardId = scoreCardId;
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
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}
	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	/**
	 * @return the entityId
	 */
	public String getEntityId() {
		return entityId;
	}
	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	/**
	 * @return the dimensionType
	 */
	public String getDimensionType() {
		return dimensionType;
	}
	/**
	 * @param dimensionType the dimensionType to set
	 */
	public void setDimensionType(String dimensionType) {
		this.dimensionType = dimensionType;
	}
	/**
	 * @return the dimensionId
	 */
	public String getDimensionId() {
		return dimensionId;
	}
	/**
	 * @param dimensionId the dimensionId to set
	 */
	public void setDimensionId(String dimensionId) {
		this.dimensionId = dimensionId;
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
	 * @return the metricName
	 */
	public String getMetricName() {
		return metricName;
	}
	/**
	 * @param metricName the metricName to set
	 */
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}
	/**
	 * @return the metricValue
	 */
	public float getMetricValue() {
		return metricValue;
	}
	/**
	 * @param metricValue the metricValue to set
	 */
	public void setMetricValue(float metricValue) {
		this.metricValue = metricValue;
	}
	/**
	 * @return the benchMark
	 */
	public float getBenchMark() {
		return benchMark;
	}
	/**
	 * @param benchMark the benchMark to set
	 */
	public void setBenchMark(float benchMark) {
		this.benchMark = benchMark;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the parentScoreCardType
	 */
	public String getParentScoreCardType() {
		return parentScoreCardType;
	}
	/**
	 * @param parentScoreCardType the parentScoreCardType to set
	 */
	public void setParentScoreCardType(String parentScoreCardType) {
		this.parentScoreCardType = parentScoreCardType;
	}
	/**
	 * @return the childScoreCardType
	 */
	public String getChildScoreCardType() {
		return childScoreCardType;
	}
	/**
	 * @param childScoreCardType the childScoreCardType to set
	 */
	public void setChildScoreCardType(String childScoreCardType) {
		this.childScoreCardType = childScoreCardType;
	}
	/**
	 * @return the userData
	 */
	public UserDetailsDTO getUserData() {
		return userData;
	}
	/**
	 * @param userData the userData to set
	 */
	public void setUserData(UserDetailsDTO userData) {
		this.userData = userData;
	}
	/**
	 * @return the teamData
	 */
	public TeamDetailsDTO getTeamData() {
		return teamData;
	}
	/**
	 * @param teamData the teamData to set
	 */
	public void setTeamData(TeamDetailsDTO teamData) {
		this.teamData = teamData;
	}
	/**
	 * @return the orgData
	 */
	public OrgDTO getOrgData() {
		return orgData;
	}
	/**
	 * @param orgData the orgData to set
	 */
	public void setOrgData(OrgDTO orgData) {
		this.orgData = orgData;
	}
	/**
	 * @return the questionData
	 */
	public QuestionDTO getQuestionData() {
		return questionData;
	}
	/**
	 * @param questionData the questionData to set
	 */
	public void setQuestionData(QuestionDTO questionData) {
		this.questionData = questionData;
	}
	/**
	 * @return the responseMetricData
	 */
	public ResponseMetricDTO getResponseMetricData() {
		return responseMetricData;
	}
	/**
	 * @param responseMetricData the responseMetricData to set
	 */
	public void setResponseMetricData(ResponseMetricDTO responseMetricData) {
		this.responseMetricData = responseMetricData;
	}
	/**
	 * @return the dimensionData
	 */
	public DimensionDTO getDimensionData() {
		return dimensionData;
	}
	/**
	 * @param dimensionData the dimensionData to set
	 */
	public void setDimensionData(DimensionDTO dimensionData) {
		this.dimensionData = dimensionData;
	}
	
	/**
	 * @return the toolsData
	 */
	public ToolsDTO getToolsData() {
		return toolsData;
	}
	/**
	 * @param toolsData the toolsData to set
	 */
	public void setToolsData(ToolsDTO toolsData) {
		this.toolsData = toolsData;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ScoreCardData [scoreCardId=" + scoreCardId + ", toolId=" + toolId + ", projectId=" + projectId
				+ ", entityType=" + entityType + ", entityId=" + entityId + ", dimensionType=" + dimensionType
				+ ", dimensionId=" + dimensionId + ", metricName=" + metricName + ", metricValue=" + metricValue
				+ ", benchMark=" + benchMark + ", label=" + label + ", parentScoreCardType=" + parentScoreCardType
				+ ", childScoreCardType=" + childScoreCardType + ", userData=" + userData + ", teamData=" + teamData
				+ ", orgData=" + orgData + ", questionData=" + questionData + ", responseMetricData="
				+ responseMetricData + ", dimensionData=" + dimensionData + "]";
	}
	
}
