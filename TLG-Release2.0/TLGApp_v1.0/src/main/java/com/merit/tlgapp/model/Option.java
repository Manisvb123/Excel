package com.merit.tlgapp.model;

public class Option {
	/**
	 * 
	 */
	public Option() {
		// TODO Auto-generated constructor stub
	}

	/* {"labelname":"Strongly Agree","labelid":"1","selected":false} */
	private String label;
	private int ID;
	private String type;
	private boolean selected;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Option(String label, int iD, String type, boolean selected) {
		super();
		this.label = label;
		ID = iD;
		this.type = type;
		this.selected = selected;
	}
}
