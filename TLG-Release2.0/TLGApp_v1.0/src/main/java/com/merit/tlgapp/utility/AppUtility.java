package com.merit.tlgapp.utility;

import java.util.ArrayList;

import com.merit.tlgapp.model.Dimension;
import com.merit.tlgapp.model.Page;
import com.merit.tlgapp.model.PageQuestion;
import com.merit.tlgapp.model.Question;

public class AppUtility {

	public static PageQuestion findQuestionForDimension(Page page, int priDimID) {
		ArrayList<PageQuestion> pageQuestions = null;
		try {
			pageQuestions = page.getPageQuestions();
			if (pageQuestions == null)
				return null;
			for (int i = 0; i < pageQuestions.size(); i++) {
				if (pageQuestions.get(i).getPdID() == priDimID) {
					return pageQuestions.get(i);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Dimension getSD(PageQuestion pageQuestion, int dimID) {
		ArrayList<Dimension> sdlist = null;
		try {
			sdlist = pageQuestion.getSdlist();
			if (sdlist == null)
				return null;
			for (int i = 0; i < sdlist.size(); i++) {
				if (sdlist.get(i).getId() == dimID) {
					return sdlist.get(i);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Question getQuestion(Dimension dim, String questionID) {
		ArrayList<Question> questions = null;
		try {
			questions = dim.getQuestions();
			if (questions == null)
				return null;
			for (int i = 0; i < questions.size(); i++) {
				if (questions.get(i).getID().equalsIgnoreCase(questionID)) {
					return questions.get(i);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
