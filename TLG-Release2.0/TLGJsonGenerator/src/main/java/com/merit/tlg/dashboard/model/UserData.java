package com.merit.tlg.dashboard.model;

import java.io.Serializable;

public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;
	public String userName;
	public String userId;
	public String metricName;
	public float metricValue;
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
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
		return "SSTUserData [userName=" + userName + ", userId=" + userId + ", metricName=" + metricName
				+ ", metricValue=" + metricValue + "]";
	}
    
	

}
