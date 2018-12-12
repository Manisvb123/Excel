package com.merit.tlg.dashboard.dto;

public class ResponseMetricDTO {
	public String responseMetricId;
	public String responseMetricName;
	/**
	 * @return the responseMetricId
	 */
	public String getResponseMetricId() {
		return responseMetricId;
	}
	/**
	 * @param responseMetricId the responseMetricId to set
	 */
	public void setResponseMetricId(String responseMetricId) {
		this.responseMetricId = responseMetricId;
	}
	/**
	 * @return the responseMetricName
	 */
	public String getResponseMetricName() {
		return responseMetricName;
	}
	/**
	 * @param responseMetricName the responseMetricName to set
	 */
	public void setResponseMetricName(String responseMetricName) {
		this.responseMetricName = responseMetricName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResponseMetricData [responseMetricId=" + responseMetricId + ", responseMetricName=" + responseMetricName
				+ "]";
	}
	
	
}
