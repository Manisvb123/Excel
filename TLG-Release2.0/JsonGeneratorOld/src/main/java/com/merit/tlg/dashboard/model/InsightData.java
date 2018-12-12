package com.merit.tlg.dashboard.model;

import java.util.ArrayList;

public class InsightData {

	public String title;
	public String description;
	public String type;
	public String dataPtShape;
	public ArrayList<InsightDataPoints> dataPoints;
	public ArrayList<RegionData> regions;
	public ArrayList<LegendData> legends;
	public AxesData axes;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the dataPtShape
	 */
	public String getDataPtShape() {
		return dataPtShape;
	}

	/**
	 * @param dataPtShape the dataPtShape to set
	 */
	public void setDataPtShape(String dataPtShape) {
		this.dataPtShape = dataPtShape;
	}

	/**
	 * @return the dataPoints
	 */
	public ArrayList<InsightDataPoints> getDataPoints() {
		return dataPoints;
	}

	/**
	 * @param dataPoints the dataPoints to set
	 */
	public void setDataPoints(ArrayList<InsightDataPoints> dataPoints) {
		this.dataPoints = dataPoints;
	}

	/**
	 * @return the regions
	 */
	public ArrayList<RegionData> getRegions() {
		return regions;
	}

	/**
	 * @param regions the regions to set
	 */
	public void setRegions(ArrayList<RegionData> regions) {
		this.regions = regions;
	}

	/**
	 * @return the legends
	 */
	public ArrayList<LegendData> getLegends() {
		return legends;
	}

	/**
	 * @param legends the legends to set
	 */
	public void setLegends(ArrayList<LegendData> legends) {
		this.legends = legends;
	}

	/**
	 * @return the axes
	 */
	public AxesData getAxes() {
		return axes;
	}

	/**
	 * @param axes the axes to set
	 */
	public void setAxes(AxesData axes) {
		this.axes = axes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InsightData [title=" + title + ", description=" + description + ", type=" + type + ", dataPtShape="
				+ dataPtShape + ", dataPoints=" + dataPoints + ", regions=" + regions + ", legends=" + legends
				+ ", axes=" + axes + "]";
	}

}
