package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DashboardDTO;
import com.merit.tlg.dashboard.dto.QuestionDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.model.InsightData;
import com.merit.tlg.dashboard.model.InsightDataPoints;
import com.merit.tlg.dashboard.model.MetricData;
import com.merit.tlg.dashboard.persistance.DashboardDao;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.DataServiceFactory;
import com.merit.tlg.dashboard.utils.JsonGenException;
import com.merit.tlg.dashboard.utils.ToolsBeanFactory;

/**
 * This Class is a common class used by other classes to create the JsonFiles required for generating the Insight Chart
 * 
 */
public class InsightDashboardService extends BaseDBGeneratorService {

	final static Logger logger = Logger.getLogger(InsightDashboardService.class);

	/**
	 * Method to create the Insight bean used by the standard Insight Chart. The
	 * Static data is created using the Json File for the specified dashboard/tool. The
	 * Dynamic data is retrieved from the database based on the insight metrics given tool. 
	 * The Generated Insight JSon is saved in the configured folder.
	 * 
	 *
	 * @param orgId: The Organization Id.
	 * @param toolId: The Tool Id.
	 * @param projectId: The Project Id.
	 * @param dbId: The Dashboard ID.
	 * @param searchId: The Search Id.
	 * 
	 * @return InsightData -- The InsightData bean.
	 *
	 */
	public InsightData generateInsightData(String orgId, String toolId, String projectId, String dbId, String searchId)
			throws JsonGenException {

		ArrayList<InsightDataPoints> dataPoints = null;

		DashboardDao dashboardDao = DataServiceFactory.getDaoFactoryInstance().getDashboardDao();
		DashboardDTO dashboardDto = dashboardDao.fetchDashboardDetails(orgId, toolId, projectId, dbId);
		
		InsightData insData = (InsightData) ToolsBeanFactory.getDashboardBean(InsightData.class, dashboardDto.getDashboardJsonTemplate());
		logger.debug("Populated Static Data " + insData);

		dataPoints = populateDataPoints(orgId, toolId, projectId,searchId);
		insData.setDataPoints(dataPoints);

		writeToFileAndUpdateDashboard(insData, orgId, toolId, projectId, dbId, dashboardDto.getDashboardJsonUrl());

		return insData;

	}

	/**
	 * Method to populate the dynamic data required by the standard Insight Chart
	 * 
	 * @param orgId: The Organization Id.
	 * @param toolId: The Tool Id.
	 * @param projectId: The Project Id.
	 * @param searchId: The Search ID.
	 * 
	 * @return ArrayList<InsightDataPoints> -- The Arraylist of Insight Data Points.
	 *
	 */
	public ArrayList<InsightDataPoints> populateDataPoints(String orgId, String toolId, String projectId, String searchId)
			throws JsonGenException {

		ArrayList<ScoreCardDTO> scData = new ArrayList<>();
		ArrayList<InsightDataPoints> dataPoints = null;

		ScoreCardSearchDTO srchData = populateSearchData(orgId, toolId, projectId, searchId);
		logger.debug("Populated Search Data " + srchData);

		ScoreCardDao scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		scData = scoreCardDao.getScoreCardDetails(srchData);
		logger.debug("Insight Data from DB " + scData.toString());

		dataPoints = populatSSTInsightDynamicData(scData, toolId);
		logger.debug("Populated Dynamic Data " + dataPoints);
		return dataPoints;
	}

	/**
	 * This method populates the Metric data for Insight Chart from the DB in the
	 * required Json Bean Format
	 * 
	 * @param ArrayList<ScoreCardDTO> scData: Score Card Data from DB
	 * @param toolId: The tool Id.
	 * 
	 * @return ArrayList<InsightDataPoints> -- List of metrics with values from DB
	 *
	 */
	private ArrayList<InsightDataPoints> populatSSTInsightDynamicData(ArrayList<ScoreCardDTO> scData, String toolId) {
		ArrayList<InsightDataPoints> dataPoints = null;
		InsightDataPoints dataPoint = null;
		String questionId = null;
		QuestionDTO questionData = null;
		MetricData metricData = null;
		ArrayList<MetricData> metricsList = null;

		dataPoints = new ArrayList<InsightDataPoints>();
		HashMap<String, InsightDataPoints> sstDataMap = new HashMap<String, InsightDataPoints>();

		for (Iterator<ScoreCardDTO> iterator = scData.iterator(); iterator.hasNext();) {
			ScoreCardDTO dbData = (ScoreCardDTO) iterator.next();
			questionData = dbData.getQuestionData();
			questionId = questionData.getQuestionId();
			if (sstDataMap.containsKey(questionId)) {
				dataPoint = (InsightDataPoints) sstDataMap.get(questionId);
				metricsList = dataPoint.getMetrics();
				metricData = new MetricData();
				setInsightMetricValues(metricData, dbData);
				metricsList.add(metricData);
				dataPoint.setMetrics(metricsList);
				sstDataMap.put(questionId, dataPoint);
			} else {
				dataPoint = new InsightDataPoints();
				dataPoint.setQuestionId(questionId);
				dataPoint.setDataPointLabel(questionData.getQuestionDesc());
				metricsList = new ArrayList<MetricData>();
				metricData = new MetricData();
				setInsightMetricValues(metricData, dbData);
				metricsList.add(metricData);
				dataPoint.setMetrics(metricsList);
				sstDataMap.put(questionId, dataPoint);
				dataPoints.add(dataPoint);
			}

		}
		return dataPoints;
	}

	/**
	 * Method to segregate the Mean and Stddev from the DB Results
	 * 
	 * @param MetricData   metricData,
	 * @param ScoreCardDTO dbData
	 * 
	 */
	private void setInsightMetricValues(MetricData metricData, ScoreCardDTO dbData) {
		if (CommonConstants.OEPS.equalsIgnoreCase(dbData.getMetricName())) {
			metricData.setMetricName(CommonConstants.MEAN);
			metricData.setMetricValue(dbData.getMetricValue());
		} else if (CommonConstants.OESDS.equalsIgnoreCase(dbData.getMetricName())) {
			metricData.setMetricName(CommonConstants.STDDEV);
			metricData.setMetricValue(dbData.getMetricValue());
		}
	}
}
