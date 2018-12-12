package com.merit.tlg.dashboard.business;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.model.IndexData;
import com.merit.tlg.dashboard.model.InsightData;
import com.merit.tlg.dashboard.model.UserTeamData;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.JsonGenException;

/**
 * This is the base class extended by all the othere dashboard services like AM,
 * BEF, SST etc.
 *
 */
public class BaseDashboardService {

	final static Logger logger = Logger.getLogger(BaseDashboardService.class);

	protected UserTeamDBDataService userTeamDataService;
	protected IndexDBDataService indexDashboardService;
	protected InsightDashboardService insightDashboardService;

	/**
	 * Constructor method instantiating different dashboard generation services like
	 * Insight, Index, UserTeam etc.
	 * 
	 */
	public BaseDashboardService() {
		userTeamDataService = new UserTeamDBDataService();
		indexDashboardService = new IndexDBDataService();
		insightDashboardService = new InsightDashboardService();
	}

	/**
	 * This method creates the JSON file required for generating the standard Index
	 * Chart. This method will be overridden by the individual dashboard service if
	 * a tool specific index chart is required.
	 * 
	 *
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param dashboardId: The dashboard ID.
	 * 
	 * @return IndexData -- The bean containing the Index Data.
	 *
	 */
	protected IndexData generateIndexData(String orgId, String toolId, String projectId, String dashboardId)
			throws JsonGenException {

		logger.debug("generateIndexData: The input Parameters {" + dashboardId + "," + orgId + "," + toolId + ","
				+ projectId + "}");
		IndexData indexData = indexDashboardService.generateIndexData(orgId, toolId, projectId, dashboardId,
				CommonConstants.SEARCH_INDEX_DEFAULT);
		logger.debug("Completed generating IndexData for {" + dashboardId + "} ," + indexData);
		return indexData;
	}

	/**
	 * This method creates the JSON file required for generating the standard Index
	 * Chart. This method will be overridden by the individual dashboard service if
	 * a tool specific index chart is required.
	 * 
	 *
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param dashboardId: The dashboard ID.
	 * @param searchId: The Search ID.
	 * 
	 * @return IndexData -- The bean containing the Index Data.
	 *
	 */
	protected IndexData generateIndexData(String orgId, String toolId, String projectId, String dashboardId,
			String searchId) throws JsonGenException {

		logger.debug("generateIndexData: The input Parameters {" + dashboardId + "," + orgId + "," + toolId + ","
				+ projectId + "," + searchId + "}");
		IndexData indexData = indexDashboardService.generateIndexData(orgId, toolId, projectId, dashboardId, searchId);
		logger.debug("Completed generating IndexData for {" + dashboardId + "} ," + indexData);
		return indexData;
	}

	/**
	 * This method creates the JSON file required for generating the standard
	 * Insight Chart. This method will be overridden by the individual dashboard
	 * service if a tool specific Insight chart is required.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param dashboardId: The dashboard ID.
	 * 
	 * @return InsightData -- The bean containing the InsightData Data.
	 *
	 */
	protected InsightData generateInsightData(String orgId, String toolId, String projectId, String dashboardId)
			throws JsonGenException {

		logger.debug("generateInsightData: The input Parameters {" + dashboardId + "," + orgId + "," + toolId + ","
				+ projectId + "}");
		InsightData insightData = insightDashboardService.generateInsightData(orgId, toolId, projectId, dashboardId,
				CommonConstants.SEARCH_INSIGHT_DEFAULT);
		logger.debug("generateInsightData: InsightData for {" + dashboardId + "} " + insightData);

		return insightData;

	}

	/**
	 * This method creates the JSON file required for generating the standard
	 * Insight Chart. This method will be overridden by the individual dashboard
	 * service if a tool specific Insight chart is required.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param dashboardId: The dashboard ID.
	 * @param searchId: The Search ID.
	 * 
	 * @return InsightData -- The bean containing the InsightData Data.
	 *
	 */
	protected InsightData generateInsightData(String orgId, String toolId, String projectId, String dashboardId,
			String searchId) throws JsonGenException {

		logger.debug("generateInsightData: The input Parameters {" + dashboardId + "," + orgId + "," + toolId + ","
				+ projectId + ", " + searchId + "}");
		InsightData insightData = insightDashboardService.generateInsightData(orgId, toolId, projectId, dashboardId,
				searchId);
		logger.debug("generateInsightData: InsightData for {" + dashboardId + "} " + insightData);

		return insightData;

	}

	/**
	 * This method creates the JSON file required for generating the standard
	 * Userteam Chart for a question. This method will be overridden by the
	 * individual dashboard service if a tool specific Userteam chart is required.
	 * 
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param dashboardId: The dashboard ID.
	 * @param qId: The question Id.
	 * 
	 * @return UserTeamData -- The bean containing the UserTeam Data.
	 *
	 */
	protected UserTeamData generateUserTeamData(String orgId, String toolId, String projectId, String dashboardId,
			String qId) throws JsonGenException {

		logger.debug("generateUserTeamData: The input Parameters {" + dashboardId + "," + orgId + "," + toolId + ","
				+ projectId + "}");
		UserTeamData userTeamData = userTeamDataService.generateUserTeamData(orgId, toolId, projectId, dashboardId,
				qId);
		return userTeamData;

	}

	/**
	 * This method creates the JSON file required for generating the standard
	 * Userteam Chart for all questions. This method will be overridden by the
	 * individual dashboard service if a tool specific Userteam charts are required.
	 * 
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param dashboardId: The dashboard ID.
	 * @return UserTeamData -- An arraylist containing the UserTeam Data.
	 *
	 */
	public ArrayList<UserTeamData> generateUserTeamDataAllQuestions(String orgId, String toolId,
			String projectId, String dashboardId) throws JsonGenException {
		logger.debug("generateUserTeamDataAllQuestions: The input Parameters {" + dashboardId + "," + orgId + ","
				+ toolId + "," + projectId + "}");
		ArrayList<UserTeamData> userTeamData = userTeamDataService.generateUserTeamDataAllQuestions(orgId,
				toolId, projectId, dashboardId);
		return userTeamData;
	}

}
