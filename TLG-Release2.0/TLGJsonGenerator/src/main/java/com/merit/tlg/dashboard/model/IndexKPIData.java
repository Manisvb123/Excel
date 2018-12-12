package com.merit.tlg.dashboard.model;

import java.io.Serializable;

public class IndexKPIData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public String kpiName;
	public String kpiDisplayName;
	public float kpiValue;
	public float kpiBenchmark;
	public float region1Threshold;
	public String region1Label;
	public String region1Color;
	public float region2Threshold; 
	public String region2Label;
	public String region2Color;
	public float region3Threshold; 
	public String region3Label;
	public String region3Color;
	
	/**
	 * @return the kpiName
	 */
	public String getKpiName() {
		return kpiName;
	}
	/**
	 * @param kpiName the kpiName to set
	 */
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	/**
	 * @return the kpiDisplayName
	 */
	public String getKpiDisplayName() {
		return kpiDisplayName;
	}
	/**
	 * @param kpiDisplayName the kpiDisplayName to set
	 */
	public void setKpiDisplayName(String kpiDisplayName) {
		this.kpiDisplayName = kpiDisplayName;
	}
	/**
	 * @return the kpiValue
	 */
	public float getKpiValue() {
		return kpiValue;
	}
	/**
	 * @param kpiValue the kpiValue to set
	 */
	public void setKpiValue(float kpiValue) {
		this.kpiValue = kpiValue;
	}
	/**
	 * @return the kpiBenchmark
	 */
	public float getKpiBenchmark() {
		return kpiBenchmark;
	}
	/**
	 * @param kpiBenchmark the kpiBenchmark to set
	 */
	public void setKpiBenchmark(float kpiBenchmark) {
		this.kpiBenchmark = kpiBenchmark;
	}
	/**
	 * @return the region1Threshold
	 */
	public float getRegion1Threshold() {
		return region1Threshold;
	}
	/**
	 * @param region1Threshold the region1Threshold to set
	 */
	public void setRegion1Threshold(float region1Threshold) {
		this.region1Threshold = region1Threshold;
	}
	/**
	 * @return the region1Label
	 */
	public String getRegion1Label() {
		return region1Label;
	}
	/**
	 * @param region1Label the region1Label to set
	 */
	public void setRegion1Label(String region1Label) {
		this.region1Label = region1Label;
	}
	/**
	 * @return the region1Color
	 */
	public String getRegion1Color() {
		return region1Color;
	}
	/**
	 * @param region1Color the region1Color to set
	 */
	public void setRegion1Color(String region1Color) {
		this.region1Color = region1Color;
	}
	/**
	 * @return the region2Threshold
	 */
	public float getRegion2Threshold() {
		return region2Threshold;
	}
	/**
	 * @param region2Threshold the region2Threshold to set
	 */
	public void setRegion2Threshold(float region2Threshold) {
		this.region2Threshold = region2Threshold;
	}
	/**
	 * @return the region2Label
	 */
	public String getRegion2Label() {
		return region2Label;
	}
	/**
	 * @param region2Label the region2Label to set
	 */
	public void setRegion2Label(String region2Label) {
		this.region2Label = region2Label;
	}
	/**
	 * @return the region2Color
	 */
	public String getRegion2Color() {
		return region2Color;
	}
	/**
	 * @param region2Color the region2Color to set
	 */
	public void setRegion2Color(String region2Color) {
		this.region2Color = region2Color;
	}
	/**
	 * @return the region3Threshold
	 */
	public float getRegion3Threshold() {
		return region3Threshold;
	}
	/**
	 * @param region3Threshold the region3Threshold to set
	 */
	public void setRegion3Threshold(float region3Threshold) {
		this.region3Threshold = region3Threshold;
	}
	/**
	 * @return the region3Label
	 */
	public String getRegion3Label() {
		return region3Label;
	}
	/**
	 * @param region3Label the region3Label to set
	 */
	public void setRegion3Label(String region3Label) {
		this.region3Label = region3Label;
	}
	/**
	 * @return the region3Color
	 */
	public String getRegion3Color() {
		return region3Color;
	}
	/**
	 * @param region3Color the region3Color to set
	 */
	public void setRegion3Color(String region3Color) {
		this.region3Color = region3Color;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndexKPIData [kpiName=" + kpiName + ", kpiDisplayName=" + kpiDisplayName + ", kpiValue=" + kpiValue
				+ ", kpiBenchmark=" + kpiBenchmark + ", region1Threshold=" + region1Threshold + ", region1Label="
				+ region1Label + ", region1Color=" + region1Color + ", region2Threshold=" + region2Threshold
				+ ", region2Label=" + region2Label + ", region2Color=" + region2Color + ", region3Threshold="
				+ region3Threshold + ", region3Label=" + region3Label + ", region3Color=" + region3Color + "]";
	}
	

}
