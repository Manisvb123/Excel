package com.merit.tlg.dashboard.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;  

@XmlRootElement
public class MetricSearchData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String searchId;
	private boolean questionDetailsReq;
	private boolean pdDetailsReq;
	private boolean sdDetailsReq;
	private ArrayList<MetricParamData> metricParams;
	
	/**
	 * @return the searchId
	 */
	@XmlAttribute
	public String getSearchId() {
		return searchId;
	}
	/**
	 * @param searchId the searchId to set
	 */
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
	
	/**
	 * @return the questionDetailsReq
	 */
	@XmlElement
	public boolean isQuestionDetailsReq() {
		return questionDetailsReq;
	}
	/**
	 * @param questionDetailsReq the questionDetailsReq to set
	 */
	public void setQuestionDetailsReq(boolean questionDetailsReq) {
		this.questionDetailsReq = questionDetailsReq;
	}
	/**
	 * @return the pdDetailsReq
	 */
	@XmlElement
	public boolean isPdDetailsReq() {
		return pdDetailsReq;
	}
	/**
	 * @param pdDetailsReq the pdDetailsReq to set
	 */
	public void setPdDetailsReq(boolean pdDetailsReq) {
		this.pdDetailsReq = pdDetailsReq;
	}
	/**
	 * @return the sdDetailsReq
	 */
	@XmlElement
	public boolean isSdDetailsReq() {
		return sdDetailsReq;
	}
	/**
	 * @param sdDetailsReq the sdDetailsReq to set
	 */
	public void setSdDetailsReq(boolean sdDetailsReq) {
		this.sdDetailsReq = sdDetailsReq;
	}
	/**
	 * @return the metricParams
	 */
	@XmlElement
	public ArrayList<MetricParamData> getMetricParams() {
		return metricParams;
	}
	/**
	 * @param metricParams the metricParams to set
	 */
	public void setMetricParams(ArrayList<MetricParamData> metricParams) {
		this.metricParams = metricParams;
	}	

}
