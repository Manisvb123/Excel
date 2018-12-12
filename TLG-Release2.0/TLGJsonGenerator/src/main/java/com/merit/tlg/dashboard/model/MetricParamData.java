package com.merit.tlg.dashboard.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MetricParamData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String entityType;
	private String entityId;
	private String dimType;
	private String dimId;
	private String metricName;
	private boolean entityIdReq;
	private boolean dimIdReq;
	/**
	 * @return the entityType
	 */
	@XmlElement
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
	@XmlElement
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
	 * @return the dimType
	 */
	@XmlElement
	public String getDimType() {
		return dimType;
	}
	/**
	 * @param dimType the dimType to set
	 */
	public void setDimType(String dimType) {
		this.dimType = dimType;
	}
	/**
	 * @return the dimId
	 */
	@XmlElement
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
	@XmlElement
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
	 * @return the entityIdReq
	 */
	@XmlElement
	public boolean isEntityIdReq() {
		return entityIdReq;
	}
	/**
	 * @param entityIdReq the entityIdReq to set
	 */
	public void setEntityIdReq(boolean entityIdReq) {
		this.entityIdReq = entityIdReq;
	}
	/**
	 * @return the dimIdReq
	 */
	@XmlElement
	public boolean isDimIdReq() {
		return dimIdReq;
	}
	/**
	 * @param dimIdReq the dimIdReq to set
	 */
	public void setDimIdReq(boolean dimIdReq) {
		this.dimIdReq = dimIdReq;
	}
	
	

}
