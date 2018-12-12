package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DashboardDTO;
import com.merit.tlg.dashboard.dto.DimensionDTO;
import com.merit.tlg.dashboard.dto.QuestionDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.model.IndexData;
import com.merit.tlg.dashboard.model.InsightDataPoints;
import com.merit.tlg.dashboard.model.MetricData;
import com.merit.tlg.dashboard.model.SSTInsightData;
import com.merit.tlg.dashboard.model.UserTeamData;
import com.merit.tlg.dashboard.persistance.DashboardDao;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.CommonUtils;
import com.merit.tlg.dashboard.utils.DataServiceFactory;
import com.merit.tlg.dashboard.utils.JsonGenException;
import com.merit.tlg.dashboard.utils.ToolsBeanFactory;

/**
 * This Class is used to generate all the JsonFiles required to generate the
 * different charts required by Strategy Stress Test
 * 
 */
public class SSTDashboardService extends BaseDashboardService {

	final static Logger logger = Logger.getLogger(SSTDashboardService.class);

	/**
	 * This method creates the JSON file for generating the Insight Chart for SST
	 * Tool in the specified location and also returns the Insight Data
	 * in the form of a bean. SST Static data has additional elements and hence overrides the generation of json.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return IndexData -- The bean containing the Index Data.
	 *
	 */
	public SSTInsightData generateSSTInsightData(String orgId, String toolId, String projectId)
			throws JsonGenException {
		ArrayList<ScoreCardDTO> scData = new ArrayList<>();
		ArrayList<InsightDataPoints> dataPoints = null;

		logger.debug("generateSSTInsightData: The input Parameters {" + orgId + "," + toolId + "," + projectId + "}");
		String dashBoardId = CommonConstants.SST_INSIGHT_DASHBOARD_ID;
		DashboardDao dashboardDao = DataServiceFactory.getDaoFactoryInstance().getDashboardDao();
		DashboardDTO dashboardDto = dashboardDao.fetchDashboardDetails(orgId, toolId, projectId, dashBoardId);

		SSTInsightData insData = (SSTInsightData) ToolsBeanFactory.getDashboardBean(SSTInsightData.class,
				dashboardDto.getDashboardJsonTemplate());
		logger.debug("Populated Static Data for SST " + insData);

		ScoreCardSearchDTO srchData = insightDashboardService.populateSearchData(orgId, toolId, projectId,
				CommonConstants.SEARCH_INSIGHT_DEFAULT);
		logger.debug("Populated Search Data " + srchData);

		ScoreCardDao scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		scData = scoreCardDao.getScoreCardDetails(srchData);
		logger.debug("Insight Data from DB " + scData.toString());

		dataPoints = populatSSTInsightDynamicData(scData, toolId);
		insData.setDataPoints(dataPoints);

		insightDashboardService.writeToFileAndUpdateDashboard(insData, orgId, toolId, projectId,
				 dashBoardId, dashboardDto.getDashboardJsonUrl());

		return insData;
	}

	/**
	 * This method creates the standard JSON file for generating the Index Chart for
	 * SST in the specified location and also returns the Index Data
	 * in the form of a bean.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return IndexData -- The bean containing the Index Data.
	 *
	 */
	public IndexData generateSSTIndexData(String orgId, String toolId, String projectId) throws JsonGenException {

		return super.generateIndexData(orgId, toolId, projectId, CommonConstants.SST_INDEX_DASHBOARD_ID,
				CommonConstants.SEARCH_INDEX_DEFAULT_OTI);

	}

	/**
	 * This method creates the JSON file required for generating the UserTeam Chart
	 * for SST Tool for a particular question in the specified location
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
		return super.generateUserTeamData(orgId, toolId, projectId, CommonConstants.SST_USERTEAM_DASHBOARD_ID, qId);
	}

	/**
	 * This method creates the JSON file required for generating the UserTeam Chart
	 * for SST Tool for all the questions for the tool in the specified
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
				CommonConstants.SST_USERTEAM_DASHBOARD_ID);
	}

	/**
	 * This method populates the bean with the Insight Data for SST from the DB.
	 * 
	 * @param ArrayList<ScoreCardDTO> scData: Score Card Data from DB
	 * @param toolId: The tool Id.
	 * 
	 * @return ArrayList<SSTInsightDataPoints> -- List of metrics with values from
	 *         DB
	 *
	 */
	private ArrayList<InsightDataPoints> populatSSTInsightDynamicData(ArrayList<ScoreCardDTO> scData, String toolId) {
		ArrayList<InsightDataPoints> dataPoints = null;
		InsightDataPoints dataPoint = null;
		String questionId = null;
		QuestionDTO questionData = null;
		MetricData metricData = null;
		ArrayList<MetricData> metricsList = null;

		HashMap<String, DimensionDTO> dimMap = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao()
				.fetchParentDimensionData(toolId);

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
				setSSTInsightMetrics(metricData, dbData);
				metricsList.add(metricData);
				dataPoint.setMetrics(metricsList);
				sstDataMap.put(questionId, dataPoint);
			} else {
				dataPoint = new InsightDataPoints();
				dataPoint.setQuestionId(questionId);
				dataPoint.setDataPointLabel(questionData.getQuestionDesc());
				metricsList = new ArrayList<MetricData>();
				metricData = new MetricData();
				setSSTInsightMetrics(metricData, dbData);
				metricsList.add(metricData);
				dataPoint.setMetrics(metricsList);

				setSSTInsightIndicatorLabel(dataPoint, questionData.getQuestionDimension(), dimMap);

				sstDataMap.put(questionId, dataPoint);
				dataPoints.add(dataPoint);
			}

		}
		return dataPoints;
	}

	private void setSSTInsightIndicatorLabel(InsightDataPoints dataPoint, String quesDimId,
			HashMap<String, DimensionDTO> dimMap) {

		String parentDimName = null;

		if (!CommonUtils.isEmpty(quesDimId) && dimMap.containsKey(quesDimId)) {
			DimensionDTO dimData = dimMap.get(quesDimId);
			if (dimData != null) {
				parentDimName = dimData.getParentDimName();
				dataPoint.setIndicator(parentDimName);
				if (!CommonUtils.isEmpty(parentDimName)) {
					if (CommonConstants.PD_ROBUST.equalsIgnoreCase(parentDimName)) {
						dataPoint.setColor(CommonConstants.RED);
					} else if (CommonConstants.PD_RESILIENT.equalsIgnoreCase(parentDimName)) {
						dataPoint.setColor(CommonConstants.GREEN);
					} else if (CommonConstants.PD_ROOTED.equalsIgnoreCase(parentDimName)) {
						dataPoint.setColor(CommonConstants.BLUE);
					}
				}
			}
		}
	}

	private void setSSTInsightMetrics(MetricData metricData, ScoreCardDTO dbData) {
		if (CommonConstants.OEPS.equalsIgnoreCase(dbData.getMetricName())) {
			metricData.setMetricName(CommonConstants.MEAN);
			metricData.setMetricValue(dbData.getMetricValue());
		} else if (CommonConstants.OESDS.equalsIgnoreCase(dbData.getMetricName())) {
			metricData.setMetricName(CommonConstants.STDDEV);
			metricData.setMetricValue(dbData.getMetricValue());
		}
	}

}
