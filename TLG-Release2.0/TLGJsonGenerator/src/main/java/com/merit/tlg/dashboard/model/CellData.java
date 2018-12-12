package com.merit.tlg.dashboard.model;

import java.io.Serializable;

public class CellData implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	public int rowNumber;
	public int colNumber;
	public String cellText;
	public String cellColor;
	public boolean isHeader;
	
	/**
	 * @return the rowNumber
	 */
	public int getRowNumber() {
		return rowNumber;
	}
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	/**
	 * @return the colNumber
	 */
	public int getColNumber() {
		return colNumber;
	}
	/**
	 * @param colNumber the colNumber to set
	 */
	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
	}
	/**
	 * @return the cellText
	 */
	public String getCellText() {
		return cellText;
	}
	/**
	 * @param cellText the cellText to set
	 */
	public void setCellText(String cellText) {
		this.cellText = cellText;
	}
	/**
	 * @return the cellColor
	 */
	public String getCellColor() {
		return cellColor;
	}
	/**
	 * @param cellColor the cellColor to set
	 */
	public void setCellColor(String cellColor) {
		this.cellColor = cellColor;
	}
	/**
	 * @return the isHeader
	 */
	public boolean isHeader() {
		return isHeader;
	}
	/**
	 * @param isHeader the isHeader to set
	 */
	public void setHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SSTInsightCellData [rowNumber=" + rowNumber + ", colNumber=" + colNumber + ", cellText=" + cellText
				+ ", cellColor=" + cellColor + ", isHeader=" + isHeader + "]";
	}
	

}
