package com.merit.tlgapp.model;

public class OrganizationRequest {
	private String contact;
	private String name;
	private String description;
	private String companyURL;
	private String orgID;
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	public OrganizationRequest() {
		super();
	}
	public OrganizationRequest(String name,String contact,String description,String companyURL) {
		super();
		
		this.contact = contact;
		this.name = name;
		this.description = description;
		this.companyURL = companyURL;
	}
	public OrganizationRequest(String orgID,String name,String contact,String description,String companyURL) {
		super();
		this.orgID=orgID;
		this.contact = contact;
		this.name = name;
		this.description = description;
		this.companyURL = companyURL;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCompanyURL() {
		return companyURL;
	}
	public void setCompanyURL(String companyURL) {
		this.companyURL = companyURL;
	}	
}
