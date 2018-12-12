package com.merit.tlg.dashboard.model;

import java.util.ArrayList;

public class SSTInsightSummaryTable {
	public int numberOfRows;
	public int numberOfColumns;
	public ArrayList<CellData> cellData;
	/**
	 * @return the numberOfRows
	 */
	public int getNumberOfRows() {
		return numberOfRows;
	}
	/**
	 * @param numberOfRows the numberOfRows to set
	 */
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	/**
	 * @return the numberOfColumns
	 */
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	/**
	 * @param numberOfColumns the numberOfColumns to set
	 */
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	/**
	 * @return the cellData
	 */
	public ArrayList<CellData> getCellData() {
		return cellData;
	}
	/**
	 * @param cellData the cellData to set
	 */
	public void setCellData(ArrayList<CellData> cellData) {
		this.cellData = cellData;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSTInsightSummaryTable [numberOfRows=" + numberOfRows + ", numberOfColumns=" + numberOfColumns
				+ ", cellData=" + cellData + "]";
	}
}
