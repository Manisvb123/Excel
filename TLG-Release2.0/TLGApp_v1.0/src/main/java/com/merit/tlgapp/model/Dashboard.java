package com.merit.tlgapp.model;

public class Dashboard {
	private String name;
	private String ID;
	private String entityType;
	private String entityID;
	private String JSONURL;
	
	public String getJSONURL() {
		return JSONURL;
	}
	public void setJSONURL(String jSONURL) {
		JSONURL = jSONURL;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntityID() {
		return entityID;
	}
	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}
}