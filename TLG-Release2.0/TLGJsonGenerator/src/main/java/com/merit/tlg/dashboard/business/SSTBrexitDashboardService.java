package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DashboardDTO;
import com.merit.tlg.dashboard.dto.QuestionDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.model.IndexData;
import com.merit.tlg.dashboard.model.InsightData;
import com.merit.tlg.dashboard.model.InsightDataPoints;
import com.merit.tlg.dashboard.model.MetricData;
import com.merit.tlg.dashboard.model.UserTeamData;
import com.merit.tlg.dashboard.persistance.DashboardDao;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.DataServiceFactory;
import com.merit.tlg.dashboard.utils.JsonGenException;
import com.merit.tlg.dashboard.utils.ToolsBeanFactory;

/**
 * This Class is used to generate all the JsonFiles required to generate the
 * different charts required by SST For Brexit.
 * 
 */
public class SSTBrexitDashboardService extends BaseDashboardService {
	final static Logger logger = Logger.getLogger(SSTBrexitDashboardService.class);

	/**
	 * This method creates the JSON file for generating the Insight Chart for SST
	 * For Brexit Tool in the specified location and also returns the Insight Data
	 * in the form of a bean. SST for Brexit uses the metrics at the element level
	 * to generate this chart and is different from the standard insight metrics.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return IndexData -- The bean containing the Insight Data.
	 *
	 */
	public InsightData generateSSTBRInsightData(String orgId, String toolId, String projectId) throws JsonGenException {

		ArrayList<InsightDataPoints> dataPoints = null;
		ArrayList<ScoreCardDTO> scData = new ArrayList<>();

		logger.debug("generateSSTBRInsightData: The input Parameters {" + orgId + "," + toolId + "," + projectId + "}");
		String dashBoardId = CommonConstants.SST_INSIGHT_DASHBOARD_ID;
		DashboardDao dashboardDao = DataServiceFactory.getDaoFactoryInstance().getDashboardDao();
		DashboardDTO dashboardDto = dashboardDao.fetchDashboardDetails(orgId, toolId, projectId, dashBoardId);

		InsightData insData = (InsightData) ToolsBeanFactory.getDashboardBean(InsightData.class,
				dashboardDto.getDashboardJsonTemplate());
		logger.debug("Populated Static Data for SST " + insData);

		ScoreCardSearchDTO srchData = insightDashboardService.populateSearchData(orgId, toolId, projectId,
				CommonConstants.SSTBR_INSIGHT_DASHBOARD_ID);
		logger.debug("Populated Search Data " + srchData);

		ScoreCardDao scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		scData = scoreCardDao.getScoreCardDetails(srchData);
		logger.debug("Insight Data from DB " + scData.toString());

		dataPoints = populatInsightDynamicData(scData, toolId);
		insData.setDataPoints(dataPoints);

		insightDashboardService.writeToFileAndUpdateDashboard(insData, orgId, toolId, projectId,
				 dashBoardId, dashboardDto.getDashboardJsonUrl());
		logger.debug(
				"generateSSTBRInsightData: Completed Writing the insight json file for SST Brexit in the specified folder");

		return insData;
	}

	/**
	 * This method creates the standard JSON file for generating the Index Chart for
	 * SST for brexit Tool in the specified location and also returns the Index Data
	 * in the form of a bean.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return IndexData -- The bean containing the Index Data.
	 *
	 */
	public IndexData generateIndexData(String orgId, String toolId, String projectId) throws JsonGenException {
		logger.debug("generateIndexData for SST Brexit: The input Parameters {" + orgId + "," + toolId + "," + projectId
				+ "}");
		return super.generateIndexData(orgId, toolId, projectId, CommonConstants.SSTBR_INDEX_DASHBOARD_ID,
				CommonConstants.SSTBR_INDEX_DASHBOARD_ID);
	}

	/**
	 * This method creates the JSON file required for generating the UserTeam Chart
	 * for SST for Brexit Tool for a particular question in the specified location
	 * and also returns the UserTeam Data in the form of a bean.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param qId: The question Id.
	 * @return UserTeamData -- The bean containing the UserTeam Data.
	 *
	 */
	public UserTeamData generateUserTeamData(String orgId, String toolId, String projectId, String qId)
			throws JsonGenException {
		return super.generateUserTeamData(orgId, toolId, projectId, CommonConstants.SSTBR_USERTEAM_DASHBOARD_ID, qId);
	}

	/**
	 * This method creates the JSON file required for generating the UserTeam Chart
	 * for SST for brexit Tool for all the questions for the tool in the specified
	 * location and also returns the list of UserTeam Data.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return UserTeamData -- An arraylist containing the UserTeam Data.
	 *
	 */
	public ArrayList<UserTeamData> generateUserTeamDataAllQuestions(String orgId, String toolId, String projectId)
			throws JsonGenException {
		return super.generateUserTeamDataAllQuestions(orgId, toolId, projectId,
				CommonConstants.SSTBR_USERTEAM_DASHBOARD_ID);
	}

	/*
	 * Method to populate the insightBean with the Response metric level data from
	 * the database.
	 */
	private ArrayList<InsightDataPoints> populatInsightDynamicData(ArrayList<ScoreCardDTO> scData, String toolId) {

		ArrayList<InsightDataPoints> dataPoints = null;
		InsightDataPoints dataPoint = null;
		String questionId = null;
		QuestionDTO questionData = null;
		MetricData metricData = null;
		ArrayList<MetricData> metricsList = null;

		dataPoints = new ArrayList<InsightDataPoints>();
		HashMap<String, InsightDataPoints> sstDataMap = new HashMap<String, InsightDataPoints>();
		dataPoints = new ArrayList<InsightDataPoints>();
		for (Iterator<ScoreCardDTO> iterator = scData.iterator(); iterator.hasNext();) {
			ScoreCardDTO dbData = (ScoreCardDTO) iterator.next();
			questionData = dbData.getQuestionData();
			questionId = questionData.getQuestionId();
			if (sstDataMap.containsKey(questionId)) {
				dataPoint = (InsightDataPoints) sstDataMap.get(questionId);
				metricsList = dataPoint.getMetrics();
				metricData = new MetricData();
				metricData.setMetricName(dbData.getResponseMetricData().getResponseMetricName());
				metricData.setMetricValue(dbData.getMetricValue());
				metricsList.add(metricData);
				dataPoint.setMetrics(metricsList);
				sstDataMap.put(questionId, dataPoint);
			} else {
				dataPoint = new InsightDataPoints();
				dataPoint.setQuestionId(questionId);
				dataPoint.setDataPointLabel(questionData.getQuestionDesc());
				dataPoint.setIndicator(dbData.getDimensionData().getDimensionName());
				dataPoint.setColor(CommonConstants.RED);
				metricsList = new ArrayList<MetricData>();
				metricData = new MetricData();
				metricData.setMetricName(dbData.getResponseMetricData().getResponseMetricName());
				metricData.setMetricValue(dbData.getMetricValue());
				metricsList.add(metricData);
				dataPoint.setMetrics(metricsList);
				sstDataMap.put(questionId, dataPoint);
				dataPoints.add(dataPoint);
			}

		}
		return dataPoints;
	}
}
