package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DimensionDTO;
import com.merit.tlg.dashboard.dto.MetricSearchDTO;
import com.merit.tlg.dashboard.dto.QuestionDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.model.AxesData;
import com.merit.tlg.dashboard.model.IndexData;
import com.merit.tlg.dashboard.model.IndexKPIData;
import com.merit.tlg.dashboard.model.InsightDataPoints;
import com.merit.tlg.dashboard.model.MetricData;
import com.merit.tlg.dashboard.model.RegionData;
import com.merit.tlg.dashboard.model.SSTInsightData;
import com.merit.tlg.dashboard.model.UserTeamData;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.DataServiceFactory;

/**
 * This Class is used to generate all the JsonFiles required to generate the
 * different charts required by SST For Brexit.
 * 
 */
public class SSTBrexitDashboardService extends BaseDashboardService {
	final static Logger logger = Logger.getLogger(SSTBrexitDashboardService.class);
	private UserTeamDBDataService userTeamDataService;
	private IndexDBDataService indexDashboardService;

	/**
	 * Constructor method instantiating the Index and Userteam service
	 * 
	 */
	public SSTBrexitDashboardService() {
		userTeamDataService = new UserTeamDBDataService();
		indexDashboardService = new IndexDBDataService();
	}

	/**
	 * This method generates the JSON file required for generating the Insight Chart
	 * for SST Brexit .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return IndexData -- The bean containing the Index Data.
	 *
	 */
	public SSTInsightData generateSSTBRInsightData(String orgId, String toolId, String projectId) {

		ArrayList<InsightDataPoints> dataPoints = null;
		SSTInsightData insData = new SSTInsightData();
		ArrayList<ScoreCardDTO> scData = new ArrayList<>();

		logger.debug("generateSSTBRInsightData: The input Parameters {" + orgId + "," + toolId + "," + projectId + "}");
		populateSSTInsightStaticData(insData);

		ScoreCardDao scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		ScoreCardSearchDTO srchData = populateSearchDataForInsightDB(orgId, toolId, projectId);
		scData = scoreCardDao.getScoreCardDetails(srchData);

		dataPoints = populatInsightDynamicData(scData, toolId);
		insData.setDataPoints(dataPoints);
		logger.debug("generateSSTBRInsightData: Insight Data from DB " + insData.toString());

		writeToFileAndUpdateDashboard(insData, orgId, toolId, projectId, CommonConstants.SSTBR_INSIGHT_DASHBOARD_ID);
		logger.debug(
				"generateSSTBRInsightData: Completed Writing the insight json file for SST Brexit in the specified folder");

		return insData;
	}



	private ArrayList<InsightDataPoints> populatInsightDynamicData(ArrayList<ScoreCardDTO> scData,
			String toolId) {
		
		ArrayList<InsightDataPoints> dataPoints = null;
		InsightDataPoints dataPoint = null;
		String questionId = null;
		QuestionDTO questionData = null;
		MetricData metricData = null;
		ArrayList<MetricData> metricsList = null;
		
		dataPoints = new ArrayList<InsightDataPoints>();
		HashMap<String, InsightDataPoints> sstDataMap = new HashMap<String, InsightDataPoints>();
		dataPoints = new ArrayList();
		for (Iterator iterator = scData.iterator(); iterator.hasNext();) {
			ScoreCardDTO dbData = (ScoreCardDTO) iterator.next();
			questionData = dbData.getQuestionData();
			questionId = questionData.getQuestionId();
			logger.debug("question ID" + questionId);
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

	/**
	 * This method generates the JSON file required for generating the Index Chart
	 * for SSt Brexit .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return IndexData -- The bean containing the Index Data.
	 *
	 */
	public IndexData generateIndexData(String orgId, String toolId, String projectId) {

		ArrayList<ScoreCardDTO> scData = null;
		ArrayList<IndexKPIData> kpiList = null;
		IndexKPIData kpiData = null;

		logger.debug("generateIndexData: The input Parameters {" + orgId + "," + toolId + "," + projectId + "}");
		IndexData indexData = new IndexData();
		indexData.setTitle("SST Brexit Index ");
		indexData.setType("Guage");
		indexData.setDescription(
				"SST Brexit Index (Combined Factor Score) is one of the Key Performance Indicator (KPI) expressed as a percentage score against the benchmark");

		/*
		 * ScoreCardDao scoreCardDao =
		 * DataServiceFactory.getDaoFactoryInstance().getScoreCardDao(); //scData =
		 * scoreCardDao.getSSTIndexKPIData(orgId, toolId, projectId); ScoreCardSearchDTO
		 * srchData = populateSearchDataForKPIIndex(orgId, toolId, projectId); scData =
		 * scoreCardDao.getScoreCardDetails(srchData);
		 * 
		 * logger.debug(" KPI Index Data from DB " + scData.toString());
		 * 
		 * kpiList = new ArrayList<>(); for (Iterator iterator = scData.iterator();
		 * iterator.hasNext();) { ScoreCardDTO dbData = (ScoreCardDTO) iterator.next();
		 * kpiData = new IndexKPIData(); if
		 * (CommonConstants.RESPONSE_METRIC.equalsIgnoreCase(dbData.getDimensionType()))
		 * { kpiData.setKpiName(dbData.getMetricName() + " " +
		 * dbData.getResponseMetricData().getResponseMetricName()); } else {
		 * kpiData.setKpiName(dbData.getMetricName()); }
		 * kpiData.setKpiValue(dbData.getMetricValue());
		 * kpiData.setKpiBenchmark(dbData.getBenchMark());
		 * populateIndexStaticData(kpiData); kpiList.add(kpiData); }
		 * 
		 * indexData.setKpiData(kpiList);
		 */

		ScoreCardSearchDTO searchdata = populateSearchDataForIndex(orgId, toolId, projectId);
		IndexKPIData indexStaticData = populateIndexStaticData();
		indexData = indexDashboardService.generateIndexData(searchdata, indexData, indexStaticData);
		logger.debug("generateIndexData: Index data from the index service " + indexData);

		writeToFileAndUpdateDashboard(indexData, orgId, toolId, projectId, CommonConstants.SSTBR_KPIINDEX_DASHBOARD_ID);
		logger.debug("generateIndexData: Completed Writing the index json file for SST Brexit in the specified folder");

		return indexData;
	}

	/**
	 * This method generates the JSON file required for generating the UserTeam
	 * Chart for SST Brexit for a particular question .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param qId: The question Id.
	 * @return UserTeamData -- The bean containing the UserTeam Data.
	 *
	 */
	public UserTeamData generateUserTeamData(String orgId, String toolId, String projectId, String qId) {

		UserTeamData userTeamData = userTeamDataService.generateUserTeamData("SST Brexit User Team",
				CommonConstants.SSTBR_USERTEAM_DASHBOARD_ID, orgId, toolId, projectId, qId);
		logger.debug("generateUserTeamData: QuestionId " + qId + " Userteamdata " + userTeamData);
		return userTeamData;

	}

	/**
	 * This method generates the JSON file required for generating the UserTeam
	 * Chart for SST Brexit for all the questions.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return UserTeamData -- An arraylist containing the UserTeam Data.
	 *
	 */
	public ArrayList<UserTeamData> generateUserTeamDataAllQuestions(String orgId, String toolId, String projectId) {

		ArrayList<UserTeamData> userTeamData = userTeamDataService.generateUserTeamDataAllQuestions(
				"SST Brexit User Team", CommonConstants.SSTBR_USERTEAM_DASHBOARD_ID, orgId, toolId, projectId);
		logger.debug("generateUserTeamDataAllQuestions: Userteamdata " + userTeamData);

		return userTeamData;
	}

	/**
	 * This method populates the search data required for generating the Index Data.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return ScoreCardSearchDTO -- Search Object.
	 *
	 */
	private ScoreCardSearchDTO populateSearchDataForIndex(String orgId, String toolId, String projectId) {
		MetricSearchDTO mData = null;
		ArrayList<MetricSearchDTO> mList = new ArrayList<MetricSearchDTO>();
		ScoreCardSearchDTO srchData = new ScoreCardSearchDTO();
		srchData.setToolId(toolId);
		srchData.setOrgId(orgId);
		srchData.setProjectId(projectId);
		srchData.setQuesDetailsReq(true);
		mData = new MetricSearchDTO();
		mData.setEntityType(CommonConstants.ORGANISATION);
		mData.setEntityIdReq(true);
		mData.setEntityId(orgId);
		mData.setDimensionType(CommonConstants.RESPONSE_METRIC);
		mData.setDimIdReq(false);
		mData.setMetricName(CommonConstants.OTEMPS);
		mList.add(mData);
		srchData.setMetricList(mList);
		logger.debug("populateSearchDataForIndex: SearchData " + srchData);
		return srchData;
	}

	/**
	 * This method populates the static data for the BEF Index chart.
	 * 
	 * @return IndexKPIData -- The bean containing the static data.
	 *
	 */
	private IndexKPIData populateIndexStaticData() {
		IndexKPIData kpiData = new IndexKPIData();
		kpiData.setRegion1Threshold(CommonConstants.FORTY_FIVE);
		kpiData.setRegion1Label(CommonConstants.IMPROVE);
		kpiData.setRegion1Color(CommonConstants.RED);
		kpiData.setRegion2Threshold(CommonConstants.HUNDRED);
		kpiData.setRegion2Label(CommonConstants.SAFE);
		kpiData.setRegion2Color(CommonConstants.GREEN);
		return kpiData;
	}

	/**
	 * This method populates the search data required for generating the Insight Data.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return ScoreCardSearchDTO -- Search Object.
	 *
	 */
	private ScoreCardSearchDTO populateSearchDataForInsightDB(String orgId, String toolId, String projectId) {
		// Insight Data
		MetricSearchDTO mData = null;
		ArrayList<MetricSearchDTO> mList = new ArrayList<MetricSearchDTO>();
		ScoreCardSearchDTO srchData = new ScoreCardSearchDTO();

		srchData.setToolId(toolId);
		srchData.setOrgId(orgId);
		srchData.setProjectId(projectId);
		srchData.setQuesDetailsReq(true);
		mData = new MetricSearchDTO();
		mData.setEntityType(CommonConstants.ORGANISATION);
		mData.setEntityIdReq(true);
		mData.setEntityId(orgId);
		mData.setDimensionType(CommonConstants.RESPONSE_METRIC);
		mData.setDimIdReq(false);
		mData.setMetricName(CommonConstants.OEMPS);
		mList.add(mData);
		srchData.setMetricList(mList);
		return srchData;
	}
	

	/**
	 * This method populates the bean with the Static Insight Data for SST from the
	 * DB.
	 * 
	 * @param insData: Static Insight Data
	 * 
	 */
	public void populateSSTInsightStaticData(SSTInsightData insData) {
		insData.setTitle("SST Brexit Insights");
		insData.setDescription("SST Brexit Insights");
		insData.setType("Bubble Chart");
		insData.setDataPtShape("Bubble");

		ArrayList<RegionData> regions = new ArrayList<>();
		RegionData region1 = new RegionData();
		region1.setLabel("Greatest Concern");
		//region1.setColor("Orange");
		regions.add(region1);
		insData.setRegions(regions);

		AxesData axes = new AxesData();
		axes.setxAxisLabel("Level of Confidence");
		axes.setyAxisLabel("Probability");
		insData.setAxes(axes);
		

	}

}
