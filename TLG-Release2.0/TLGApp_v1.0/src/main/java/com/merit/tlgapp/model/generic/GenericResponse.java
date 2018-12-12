package com.merit.tlgapp.model.generic;

public class GenericResponse {
	private String requestType;
	private int status;
	private String errorCode;
	private String description;
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public GenericResponse(String requestType, int status, String errorCode, String description) {
		super();
		this.requestType = requestType;
		this.status = status;
		this.errorCode = errorCode;
		this.description = description;
	}
}
