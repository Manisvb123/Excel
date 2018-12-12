package com.merit.tlg.dashboard.model;

import java.io.Serializable;
import java.util.ArrayList;

public class IndexData extends BaseDashboardData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public String title;
	public String description;
	public String type;
	public ArrayList<IndexKPIData> kpiData;
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
	 * @return the kpiData
	 */
	public ArrayList<IndexKPIData> getKpiData() {
		return kpiData;
	}
	/**
	 * @param kpiData the kpiData to set
	 */
	public void setKpiData(ArrayList<IndexKPIData> kpiData) {
		this.kpiData = kpiData;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSTIndexData [title=" + title + ", description=" + description + ", type=" + type + ", kpiData="
				+ kpiData + "]";
	}
	
}
