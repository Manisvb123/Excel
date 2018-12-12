package com.merit.tlg.dashboard.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SSTInsightData extends InsightData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public SSTInsightSummaryTable summaryTable;
	public ArrayList<String> summaryTxt;
	public ArrayList<String> notes;
	
	/**
	 * @return the summaryTable
	 */
	public SSTInsightSummaryTable getSummaryTable() {
		return summaryTable;
	}
	/**
	 * @param summaryTable the summaryTable to set
	 */
	public void setSummaryTable(SSTInsightSummaryTable summaryTable) {
		this.summaryTable = summaryTable;
	}
	/**
	 * @return the summaryTxt
	 */
	public ArrayList<String> getSummaryTxt() {
		return summaryTxt;
	}
	/**
	 * @param summaryTxt the summaryTxt to set
	 */
	public void setSummaryTxt(ArrayList<String> summaryTxt) {
		this.summaryTxt = summaryTxt;
	}
	/**
	 * @return the notes
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSTInsightData [summaryTable=" + summaryTable + ", summaryTxt=" + summaryTxt + ", notes=" + notes
				+ ", title=" + title + ", description=" + description + ", type=" + type + ", dataPtShape="
				+ dataPtShape + ", dataPoints=" + dataPoints + ", regions=" + regions + ", legends=" + legends
				+ ", axes=" + axes + "]";
	}

	
}
