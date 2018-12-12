package com.merit.tlg.dashboard.model;

public class MetricData {
	private String metricName;
	private float metricValue;
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
		return "MetricData [metricName=" + metricName + ", metricValue=" + metricValue + "]";
	}
	
	

}
