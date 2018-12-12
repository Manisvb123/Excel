package com.merit.tlg.dashboard.dto;

import java.util.ArrayList;

public class ScoreCardSearchDTO {
	
	public String toolId;
	public String projectId;
	public String orgId;
	public ArrayList<MetricSearchDTO> metricList;
	
	public boolean quesDetailsReq;
	public boolean pdDetailsReq;
	public boolean sdDetailsReq;
	public boolean userDetailsReq;
	public boolean responseMetricDetailsReq;
	
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
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the metricList
	 */
	public ArrayList<MetricSearchDTO> getMetricList() {
		return metricList;
	}
	/**
	 * @param metricList the metricList to set
	 */
	public void setMetricList(ArrayList<MetricSearchDTO> metricList) {
		this.metricList = metricList;
	}
	/**
	 * @return the quesDetailsReq
	 */
	public boolean isQuesDetailsReq() {
		return quesDetailsReq;
	}
	/**
	 * @param quesDetailsReq the quesDetailsReq to set
	 */
	public void setQuesDetailsReq(boolean quesDetailsReq) {
		this.quesDetailsReq = quesDetailsReq;
	}
	/**
	 * @return the pdDetailsReq
	 */
	public boolean isPdDetailsReq() {
		return pdDetailsReq;
	}
	/**
	 * @param pdDetailsReq the pdDetailsReq to set
	 */
	public void setPdDetailsReq(boolean pdDetailsReq) {
		this.pdDetailsReq = pdDetailsReq;
	}
	/**
	 * @return the sdDetailsReq
	 */
	public boolean isSdDetailsReq() {
		return sdDetailsReq;
	}
	/**
	 * @param sdDetailsReq the sdDetailsReq to set
	 */
	public void setSdDetailsReq(boolean sdDetailsReq) {
		this.sdDetailsReq = sdDetailsReq;
	}
	/**
	 * @return the userDetailsReq
	 */
	public boolean isUserDetailsReq() {
		return userDetailsReq;
	}
	/**
	 * @param userDetailsReq the userDetailsReq to set
	 */
	public void setUserDetailsReq(boolean userDetailsReq) {
		this.userDetailsReq = userDetailsReq;
	}
	/**
	 * @return the responseMetricDetailsReq
	 */
	public boolean isResponseMetricDetailsReq() {
		return responseMetricDetailsReq;
	}
	/**
	 * @param responseMetricDetailsReq the responseMetricDetailsReq to set
	 */
	public void setResponseMetricDetailsReq(boolean responseMetricDetailsReq) {
		this.responseMetricDetailsReq = responseMetricDetailsReq;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ScoreCardSearchData [toolId=" + toolId + ", projectId=" + projectId + ", orgId=" + orgId
				+ ", metricList=" + metricList + ", quesDetailsReq=" + quesDetailsReq + ", pdDetailsReq=" + pdDetailsReq
				+ ", sdDetailsReq=" + sdDetailsReq + ", userDetailsReq=" + userDetailsReq
				+ ", responseMetricDetailsReq=" + responseMetricDetailsReq + "]";
	}
	
	

}
