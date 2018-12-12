package com.merit.tlgapp.model;

public class PageDetails {
/*
 * {
	"Page": {
		"toolver": "v1.0",
		"toolid": "SST-001",
		"tmplname": "SST-Template",
		"pgid": "PG-001",
		"numqns": "",
		"prjid": "PR-001",
		"pgno": 1,
		"commonInfo": {
			"instructions_to_fill": "Please enter on a 4 point scale how much you Agree or Disagree with the statements below.",
			"scoring_guidelines": "some guidelines",
			"remarks": "Remarks",
			"title": "SST"
		},
		"userid": "Rekhas",
		"tmplver": "tmplver"
	}
}
 */
	
	private String toolVersion;
	private String toolID;
	private String questionnaireName;
	private String questionnaireVersion;
	private String pageID;
	private int pageNo;
	private CommonInfo commonInfo;
	private String userID;
	public String getToolVersion() {
		return toolVersion;
	}
	public void setToolVersion(String toolVersion) {
		this.toolVersion = toolVersion;
	}
	public String getToolID() {
		return toolID;
	}
	public void setToolID(String toolID) {
		this.toolID = toolID;
	}
	public String getQuestionnaireName() {
		return questionnaireName;
	}
	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}
	public String getQuestionnaireVersion() {
		return questionnaireVersion;
	}
	public void setQuestionnaireVersion(String questionnaireVersion) {
		this.questionnaireVersion = questionnaireVersion;
	}
	public String getPageID() {
		return pageID;
	}
	public void setPageID(String pageID) {
		this.pageID = pageID;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public CommonInfo getCommonInfo() {
		return commonInfo;
	}
	public void setCommonInfo(CommonInfo commonInfo) {
		this.commonInfo = commonInfo;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
