package com.merit.tlg.dashboard.model;

public class DimensionData {
	public String dimName;
	public String dimId;
	public String metricName;
	public float metricValue;
	/**
	 * @return the dimName
	 */
	public String getDimName() {
		return dimName;
	}
	/**
	 * @param dimName the dimName to set
	 */
	public void setDimName(String dimName) {
		this.dimName = dimName;
	}
	/**
	 * @return the dimId
	 */
	public String getDimId() {
		return dimId;
	}
	/**
	 * @param dimId the dimId to set
	 */
	public void setDimId(String dimId) {
		this.dimId = dimId;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSTDimensionData [dimName=" + dimName + ", dimId=" + dimId + ", metricName=" + metricName
				+ ", metricValue=" + metricValue + "]";
	}

}
