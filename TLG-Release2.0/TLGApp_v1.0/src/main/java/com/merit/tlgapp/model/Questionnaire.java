package com.merit.tlgapp.model;

import java.util.ArrayList;

public class Questionnaire {
/*{
	"Questionnaire": {
		"toolver": "v1.0",
		"toolid": "SST-001",
		"tmplname": "SST-Template",
		"pagelst": [{
			"pgid": "PG-001",
			"pgno": 1
		}, {
			"pgid": "PG-002",
			"pgno": 2
		}, {
			"pgid": "PG-003",
			"pgno": 3
		}, {
			"pgid": "PG-004",
			"pgno": 4
		}, {
			"pgid": "PG-005",
			"pgno": 5
		}, {
			"pgid": "PG-006",
			"pgno": 6
		}],
		"prjid": "PR-001",
		"userid": "Rekhas",
		"tmplver": "TemplateVersion",
		"numpgs": 6
	}
}*/
	private String toolID;
	private String toolVersion;
	private String questionnaireID;
	private String questionnaireVersion;
	private String questionnaireName;
	private String userID;
	private String teamID;
	private int noOfPages;
	private ArrayList<PageSummary> pages;
	public String getToolID() {
		return toolID;
	}
	public void setToolID(String toolID) {
		this.toolID = toolID;
	}
	public String getToolVersion() {
		return toolVersion;
	}
	public void setToolVersion(String toolVersion) {
		this.toolVersion = toolVersion;
	}
	public String getQuestionnaireID() {
		return questionnaireID;
	}
	public void setQuestionnaireID(String questionnaireID) {
		this.questionnaireID = questionnaireID;
	}
	public String getQuestionnaireVersion() {
		return questionnaireVersion;
	}
	public void setQuestionnaireVersion(String questionnaireVersion) {
		this.questionnaireVersion = questionnaireVersion;
	}
	public String getQuestionnaireName() {
		return questionnaireName;
	}
	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getTeamID() {
		return teamID;
	}
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	public int getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}
	public ArrayList<PageSummary> getPages() {
		return pages;
	}
	public void setPages(ArrayList<PageSummary> pages) {
		this.pages = pages;
	}
	public void addPage(PageSummary page) {
		if(this.pages == null) {
			this.pages = new ArrayList<PageSummary>();
		}
		this.pages.add(page);
	}
}
