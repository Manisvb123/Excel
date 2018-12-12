package com.merit.tlgapp.model;

import java.util.ArrayList;

public class PageQuestion {
	private String pdName;
	private int pdID;
	private ArrayList<Dimension> sdlist;
	public String getPdName() {
		return pdName;
	}
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}
	public int getPdID() {
		return pdID;
	}
	public void setPdID(int pdID) {
		this.pdID = pdID;
	}
	public ArrayList<Dimension> getSdlist() {
		return sdlist;
	}
	public void setSdlist(ArrayList<Dimension> sdlist) {
		this.sdlist = sdlist;
	}

	public void addSD(Dimension dim) {
		if(this.sdlist == null) {
			this.sdlist = new ArrayList<Dimension>();
		}
		this.sdlist.add(dim);
	}
}
