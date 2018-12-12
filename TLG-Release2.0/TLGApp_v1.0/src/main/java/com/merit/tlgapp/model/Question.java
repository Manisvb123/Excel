package com.merit.tlgapp.model;

import java.util.ArrayList;

public class Question {
	private int no;
	private String ID;
	private String context;
	private String description;
	private String type;
	private ArrayList<Metric> metrics;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Metric> getMetrics() {
		return metrics;
	}

	public void setMetrics(ArrayList<Metric> metrics) {
		this.metrics = metrics;
	}

	public void addMetric(Metric metric) {
		if(metrics == null) {
			metrics = new ArrayList<Metric>();
		}
		this.metrics.add(metric);
	}
}
