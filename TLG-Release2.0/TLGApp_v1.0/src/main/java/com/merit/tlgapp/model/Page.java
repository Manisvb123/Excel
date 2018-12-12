package com.merit.tlgapp.model;

import java.util.ArrayList;

public class Page {
	private Key pageKey;
	private ArrayList<PageQuestion> pageQuestions;

	public Key getPageKey() {
		return pageKey;
	}

	public void setPageKey(Key pageKey) {
		this.pageKey = pageKey;
	}

	public ArrayList<PageQuestion> getPageQuestions() {
		return pageQuestions;
	}

	public void setPageQuestions(ArrayList<PageQuestion> pageQuestions) {
		this.pageQuestions = pageQuestions;
	}

	public void addPageQuestion(PageQuestion szPQ) {
		if (pageQuestions == null)
			pageQuestions = new ArrayList<PageQuestion>();
		this.pageQuestions.add(szPQ);
	}

	/*public PageQuestion findQuestionForDimension(int priDimID) {
		if (pageQuestions == null)
			return null;
		for (int i = 0; i < pageQuestions.size(); i++) {
			if (pageQuestions.get(i).getPdID() == priDimID) {
				return pageQuestions.get(i);
			}
		}
		return null;
	}*/
}
