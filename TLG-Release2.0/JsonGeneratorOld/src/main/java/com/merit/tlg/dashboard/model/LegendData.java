package com.merit.tlg.dashboard.model;

public class LegendData {
	public String geoShape;
	public String txtLabel;
	public String color;
	
	/**
	 * @return the geoShape
	 */
	public String getGeoShape() {
		return geoShape;
	}
	/**
	 * @param geoShape the geoShape to set
	 */
	public void setGeoShape(String geoShape) {
		this.geoShape = geoShape;
	}
	/**
	 * @return the txtLabel
	 */
	public String getTxtLabel() {
		return txtLabel;
	}
	/**
	 * @param txtLabel the txtLabel to set
	 */
	public void setTxtLabel(String txtLabel) {
		this.txtLabel = txtLabel;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSTInsightLegends [geoShape=" + geoShape + ", txtLabel=" + txtLabel + ", color=" + color + "]";
	}
	
		
}
