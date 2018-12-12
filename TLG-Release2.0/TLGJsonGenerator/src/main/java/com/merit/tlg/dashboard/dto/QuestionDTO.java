package com.merit.tlg.dashboard.dto;

public class QuestionDTO {
	public String questionId;
	public String questionName;
	public String questionContext;
	public String questionDesc;
	public String version;
	public String questionDimension;
	/**
	 * @return the questionId
	 */
	public String getQuestionId() {
		return questionId;
	}
	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	/**
	 * @return the questionName
	 */
	public String getQuestionName() {
		return questionName;
	}
	/**
	 * @param questionName the questionName to set
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	/**
	 * @return the questionContext
	 */
	public String getQuestionContext() {
		return questionContext;
	}
	/**
	 * @param questionContext the questionContext to set
	 */
	public void setQuestionContext(String questionContext) {
		this.questionContext = questionContext;
	}
	/**
	 * @return the questionDesc
	 */
	public String getQuestionDesc() {
		return questionDesc;
	}
	/**
	 * @param questionDesc the questionDesc to set
	 */
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the questionDimension
	 */
	public String getQuestionDimension() {
		return questionDimension;
	}
	/**
	 * @param questionDimension the questionDimension to set
	 */
	public void setQuestionDimension(String questionDimension) {
		this.questionDimension = questionDimension;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QuestionDTO [questionId=" + questionId + ", questionName=" + questionName + ", questionContext="
				+ questionContext + ", questionDesc=" + questionDesc + ", version=" + version + ", questionDimension="
				+ questionDimension + "]";
	}
	
	

}
