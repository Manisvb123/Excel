package com.merit.tlg.dashboard.dto;

public class MetricSearchDTO {
	public String entityType;
	public String entityId;
	public String dimensionType;
	public String dimensionId;
	public String metricName;
	public boolean isEntityIdReq;
	public boolean isDimIdReq;
	
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
	 * @return the isEntityIdReq
	 */
	public boolean isEntityIdReq() {
		return isEntityIdReq;
	}
	/**
	 * @param isEntityIdReq the isEntityIdReq to set
	 */
	public void setEntityIdReq(boolean isEntityIdReq) {
		this.isEntityIdReq = isEntityIdReq;
	}
	/**
	 * @return the isDimIdReq
	 */
	public boolean isDimIdReq() {
		return isDimIdReq;
	}
	/**
	 * @param isDimIdReq the isDimIdReq to set
	 */
	public void setDimIdReq(boolean isDimIdReq) {
		this.isDimIdReq = isDimIdReq;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MetricSearchData [entityType=" + entityType + ", entityId=" + entityId + ", dimensionType="
				+ dimensionType + ", dimensionId=" + dimensionId + ", metricName=" + metricName + ", isEntityIdReq="
				+ isEntityIdReq + ", isDimIdReq=" + isDimIdReq + "]";
	}
	
	
	
}
