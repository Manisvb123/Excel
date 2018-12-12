package com.merit.tlg.dashboard.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "com.merit.tlg.dashboard.model")
public class MetricSearchDataWrapper implements Serializable {

	private static final long serialVersionUID = 1L;
	//@XmlElementWrapper(name = "metricsList")

	// XmlElement sets the name of the entities
	
	private ArrayList<MetricSearchData> metricSearchData;

	/**
	 * @return the metricSearchList
	 */
	@XmlElement
	public ArrayList<MetricSearchData> getMetricSearchData() {
		return metricSearchData;
	}

	/**
	 * @param metricsearchlist the metricSearchList to set
	 */
	public void setMetricSearchData(ArrayList<MetricSearchData> metricSearchData) {
		this.metricSearchData = metricSearchData;
	}
	
	
}
