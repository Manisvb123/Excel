package com.merit.tlgapp.model;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

public class Tools {
	private String toolName;
	private String toolID;
	private String creationDate;
	private String version;
	private String description;
	private String description_url;
	private String owner;
	private String lastModifiedDate;
	private int benchmark;
	private String scenario;
	private ArrayList<Tools> toolRes;
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolID() {
		return toolID;
	}

	public void setToolID(String toolID) {
		this.toolID = toolID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription_url() {
		return description_url;
	}

	public void setDescription_url(String description_url) {
		this.description_url = description_url;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(int benchmark) {
		this.benchmark = benchmark;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}
	public ArrayList<Tools> getTools() {
		return toolRes;
	}
	public void setTools(ArrayList<Tools> toolRes) {
		this.toolRes = toolRes;
	}
	public void addTools(Tools toolRes) {
		if(this.toolRes == null) {
			this.toolRes = new ArrayList<Tools>();
		}
		this.toolRes.add(toolRes);
	}
	
	
}
	

