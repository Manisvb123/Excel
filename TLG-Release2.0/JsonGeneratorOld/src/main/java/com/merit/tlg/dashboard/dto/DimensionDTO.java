package com.merit.tlg.dashboard.dto;

public class DimensionDTO {
	public String dimensionId;
	public String dimensionName;
	public String dimensionDesc;
	public String parentDimId;
	public String parentDimName;
	public float weightage;
	public float maxScore;
	public float benchmark;
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
	 * @return the dimensionName
	 */
	public String getDimensionName() {
		return dimensionName;
	}
	/**
	 * @param dimensionName the dimensionName to set
	 */
	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}
	/**
	 * @return the dimensionDesc
	 */
	public String getDimensionDesc() {
		return dimensionDesc;
	}
	/**
	 * @param dimensionDesc the dimensionDesc to set
	 */
	public void setDimensionDesc(String dimensionDesc) {
		this.dimensionDesc = dimensionDesc;
	}
	/**
	 * @return the parentDimId
	 */
	public String getParentDimId() {
		return parentDimId;
	}
	/**
	 * @param parentDimId the parentDimId to set
	 */
	public void setParentDimId(String parentDimId) {
		this.parentDimId = parentDimId;
	}
	/**
	 * @return the parentDimName
	 */
	public String getParentDimName() {
		return parentDimName;
	}
	/**
	 * @param parentDimName the parentDimName to set
	 */
	public void setParentDimName(String parentDimName) {
		this.parentDimName = parentDimName;
	}
	/**
	 * @return the weightage
	 */
	public float getWeightage() {
		return weightage;
	}
	/**
	 * @param weightage the weightage to set
	 */
	public void setWeightage(float weightage) {
		this.weightage = weightage;
	}
	/**
	 * @return the maxScore
	 */
	public float getMaxScore() {
		return maxScore;
	}
	/**
	 * @param maxScore the maxScore to set
	 */
	public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
	}
	/**
	 * @return the benchmark
	 */
	public float getBenchmark() {
		return benchmark;
	}
	/**
	 * @param benchmark the benchmark to set
	 */
	public void setBenchmark(float benchmark) {
		this.benchmark = benchmark;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DimensionData [dimensionId=" + dimensionId + ", dimensionName=" + dimensionName + ", dimensionDesc="
				+ dimensionDesc + ", parentDimId=" + parentDimId + ", parentDimName=" + parentDimName + ", weightage="
				+ weightage + ", maxScore=" + maxScore + ", benchmark=" + benchmark + "]";
	}
	
}
