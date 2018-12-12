package com.merit.tlg.dashboard.dto;

public class ToolsDTO {
	public String toolId;
	public String toolName;
	public String description;
	/**
	 * @return the toolId
	 */
	public String getToolId() {
		return toolId;
	}
	/**
	 * @param toolId the toolId to set
	 */
	public void setToolId(String toolId) {
		this.toolId = toolId;
	}
	/**
	 * @return the toolName
	 */
	public String getToolName() {
		return toolName;
	}
	/**
	 * @param toolName the toolName to set
	 */
	public void setToolName(String toolName) {
		this.toolName = toolName;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ToolsDTO [toolId=" + toolId + ", toolName=" + toolName + ", description=" + description + "]";
	}
	
	

}
