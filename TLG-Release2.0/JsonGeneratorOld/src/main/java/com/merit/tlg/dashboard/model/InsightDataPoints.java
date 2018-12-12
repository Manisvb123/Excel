package com.merit.tlg.dashboard.model;

import java.util.ArrayList;

public class InsightDataPoints {
	
	public String questionId;
	public float mean;
	public float stdDev;
	public String dataPointLabel;
	public String indicator;
	public String color;
	private ArrayList<MetricData> metrics;
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
	 * @return the mean
	 */
	public float getMean() {
		return mean;
	}
	/**
	 * @param mean the mean to set
	 */
	public void setMean(float mean) {
		this.mean = mean;
	}
	/**
	 * @return the stdDev
	 */
	public float getStdDev() {
		return stdDev;
	}
	/**
	 * @param stdDev the stdDev to set
	 */
	public void setStdDev(float stdDev) {
		this.stdDev = stdDev;
	}
	/**
	 * @return the dataPointLabel
	 */
	public String getDataPointLabel() {
		return dataPointLabel;
	}
	/**
	 * @param dataPointLabel the dataPointLabel to set
	 */
	public void setDataPointLabel(String dataPointLabel) {
		this.dataPointLabel = dataPointLabel;
	}
	/**
	 * @return the indicator
	 */
	public String getIndicator() {
		return indicator;
	}
	/**
	 * @param indicator the indicator to set
	 */
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the metrics
	 */
	public ArrayList<MetricData> getMetrics() {
		return metrics;
	}
	/**
	 * @param metrics the metrics to set
	 */
	public void setMetrics(ArrayList<MetricData> metrics) {
		this.metrics = metrics;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSTInsightDataPoints [questionId=" + questionId + ", mean=" + mean + ", stdDev=" + stdDev
				+ ", dataPointLabel=" + dataPointLabel + ", indicator=" + indicator + ", color=" + color + "]";
	}
	
}
