package com.merit.tlg.dashboard.business;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.MetricSearchDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.model.IndexData;
import com.merit.tlg.dashboard.model.IndexKPIData;
import com.merit.tlg.dashboard.model.InsightData;
import com.merit.tlg.dashboard.model.UserTeamData;
import com.merit.tlg.dashboard.utils.CommonConstants;

/**
 * This Class is used to generate all the JsonFiles required to generate the
 * different charts required by Board Effectiveness Framework
 * 
 */
public class BEFDashboardService  extends BaseDashboardService {
	final static Logger logger = Logger.getLogger(BEFDashboardService.class);
	private UserTeamDBDataService userTeamDataService;
	private IndexDBDataService indexDashboardService;
	private InsightDashboardService insightDashboardService;

	/**
	 * Constructor method instantiating the Index and Userteam service
	 * 
	 */
	public BEFDashboardService() {
		userTeamDataService = new UserTeamDBDataService();
		indexDashboardService = new IndexDBDataService();
		insightDashboardService = new InsightDashboardService();
	}
	
	/**
	 * This method generates the JSON file required for generating the Insight Chart for BEF.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return InsightData -- The bean containing the InsightData.
	 *
	 */
	public InsightData generateInsightData(String orgId, String toolId, String projectId) {
		InsightData insData = new InsightData();
		insData.setTitle("BEF Insight");
		insData.setDescription("BEF insights shows how good the strategy is ");
		insData.setType("X-Y Scatter plot");
		insData.setDataPtShape("Square");
		
		insData = insightDashboardService.generateInsightData(insData, orgId,
				toolId, projectId, CommonConstants.BEF_INSIGHT_DASHBOARD_ID);
		logger.debug("generateInsightData: Insight data from BEF " + insData);
		
		return insData;
	}
	
	/**
	 * This method generates the JSON file required for generating the Index Chart for BEF .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return IndexData -- The bean containing the Index Data.
	 *
	 */
	public IndexData generateIndexData(String orgId, String toolId, String projectId) {

		logger.debug("generateIndexData: The input Parameters {" + orgId + "," + toolId + "," + projectId + "}");
		IndexData indexData = new IndexData();
		indexData.setTitle("BEF Index ");
		indexData.setType("Guage");
		indexData.setDescription(
				"BEF Index is one of the Key Performance Indicator (KPI), BEF Factor Score expressed as a percentage score against the benchmark.");
		
		ScoreCardSearchDTO searchdata = populateSearchDataForIndex(orgId, toolId, projectId);
		IndexKPIData indexStaticData = populateIndexStaticData();
		indexData = indexDashboardService.generateIndexData(searchdata, indexData, indexStaticData);
		logger.debug("generateIndexData: Index data from the index service " + indexData);
		
		writeToFileAndUpdateDashboard(indexData, orgId, toolId, projectId, CommonConstants.BEF_INDEX_DASHBOARD_ID);
		logger.debug("generateIndexData: Completed Writing the index json file for BEF in the specified folder");
		
		return indexData;
	}

	/**
	 * This method generates the JSON file required for generating the UserTeam Chart for BEF for a particular question .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param qId: The question Id. 
	 * @return UserTeamData -- The bean containing the UserTeam Data.
	 *
	 */
	public UserTeamData generateUserTeamData(String orgId, String toolId, String projectId, String qId) {

		UserTeamData userTeamData = userTeamDataService.generateUserTeamData("BEF User Team",
				CommonConstants.BEF_USERTEAM_DASHBOARD_ID, orgId, toolId, projectId, qId);

		logger.debug("generateUserTeamData: QuestionId " + qId + " Userteamdata " + userTeamData);
		return userTeamData;

	}
	
	/**
	 * This method generates the JSON file required for generating the UserTeam Chart for BEF for all the questions.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return UserTeamData -- An arraylist containing the UserTeam Data.
	 *
	 */
	public ArrayList<UserTeamData> generateUserTeamDataAllQuestions(String orgId, String toolId, String projectId) {

		ArrayList<UserTeamData> userTeamData = userTeamDataService.generateUserTeamDataAllQuestions("BEF User Team",
				CommonConstants.BEF_USERTEAM_DASHBOARD_ID, orgId, toolId, projectId);
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
		mData.setDimensionType(CommonConstants.PD);
		mData.setDimIdReq(false);
		mData.setMetricName(CommonConstants.OPDPS);
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
		IndexKPIData kpiData =  new IndexKPIData();
		kpiData.setRegion1Threshold(CommonConstants.FORTY_FIVE);
		kpiData.setRegion1Label(CommonConstants.IMPROVE);
		kpiData.setRegion1Color(CommonConstants.RED);
		kpiData.setRegion2Threshold(CommonConstants.HUNDRED);
		kpiData.setRegion2Label(CommonConstants.SAFE);
		kpiData.setRegion2Color(CommonConstants.GREEN);
		return kpiData;
	}
}
