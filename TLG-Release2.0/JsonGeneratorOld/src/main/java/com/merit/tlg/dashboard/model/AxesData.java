package com.merit.tlg.dashboard.model;

public class AxesData {
	public String xAxisLabel;
	public String yAxisLabel;
	
	public AxesData() {
		
	}
	
	public AxesData(String xAxisLabel, String yAxisLabel) {
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
	}
	
	/**
	 * @return the xAxisLabel
	 */
	public String getxAxisLabel() {
		return xAxisLabel;
	}
	/**
	 * @param xAxisLabel the xAxisLabel to set
	 */
	public void setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
	}
	/**
	 * @return the yAxisLabel
	 */
	public String getyAxisLabel() {
		return yAxisLabel;
	}
	/**
	 * @param yAxisLabel the yAxisLabel to set
	 */
	public void setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSTInsightAxes [xAxisLabel=" + xAxisLabel + ", yAxisLabel=" + yAxisLabel + "]";
	}

	
}
