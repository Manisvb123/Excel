package com.merit.tlgapp.model;

import java.util.ArrayList;

public class Dimension {
	private String name;
	private int id;
	private ArrayList<Question> questions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public void addQuestion(Question qtn) {
		if(this.questions == null) {
			this.questions = new ArrayList<Question>();
		}
		this.questions.add(qtn);
	}
}
