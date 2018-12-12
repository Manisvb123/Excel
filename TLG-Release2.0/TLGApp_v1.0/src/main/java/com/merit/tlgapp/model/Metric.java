package com.merit.tlgapp.model;

import java.util.ArrayList;

public class Metric {
	private String metricID;
	private String metricName;
	private ArrayList<Option> options;

	public String getMetricID() {
		return metricID;
	}

	public void setMetricID(String metricID) {
		this.metricID = metricID;
	}

	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public ArrayList<Option> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<Option> options) {
		this.options = options;
	}

	public void addOption(Option opt) {
		if(this.options == null) {
			this.options = new ArrayList<Option>();
		}
		this.options.add(opt);
	}
}
