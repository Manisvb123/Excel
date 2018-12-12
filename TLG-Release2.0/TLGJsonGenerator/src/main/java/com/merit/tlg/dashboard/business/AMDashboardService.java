package com.merit.tlg.dashboard.business;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.model.IndexData;
import com.merit.tlg.dashboard.model.InsightData;
import com.merit.tlg.dashboard.model.UserTeamData;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.JsonGenException;

/**
 * This Class is used to create the JsonFiles required to generate the different
 * charts required by Alignment and Motivation tool.
 * 
 */
public class AMDashboardService extends BaseDashboardService {
	final static Logger logger = Logger.getLogger(AMDashboardService.class);

	/**
	 * This method creates the JSON file for generating the Index Chart for
	 * Alignment and Motivation in the specified location and also returns the Index
	 * Data in the form of a bean.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * 
	 * @return IndexData -- The bean containing the Index Data.
	 */
	public IndexData generateIndexData(String orgId, String toolId, String projectId) throws JsonGenException {

		return super.generateIndexData(orgId, toolId, projectId, CommonConstants.AM_INDEX_DASHBOARD_ID,
				CommonConstants.SEARCH_INDEX_DEFAULT_OTI);
	}

	/**
	 * This method creates the JSON file for generating the Insight Chart for
	 * Alignment and Motivation in the specified location and also returns the
	 * Insight Data in the form of a bean.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * 
	 * @return InsightData -- The bean containing the InsightData Data.
	 *
	 */
	public InsightData generateInsightData(String orgId, String toolId, String projectId) throws JsonGenException {
		return super.generateInsightData(orgId, toolId, projectId, CommonConstants.AM_INSIGHT_DASHBOARD_ID);
	}

	/**
	 * This method creates the JSON file required for generating the UserTeam Chart
	 * for Alignment and Motivation for a particular question in the specified
	 * location and also returns the UserTeam Data in the form of a bean.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param qId: The question Id.
	 * 
	 * @return UserTeamData -- The bean containing the UserTeam Data.
	 *
	 */
	public UserTeamData generateUserTeamData(String orgId, String toolId, String projectId, String qId)
			throws JsonGenException {

		return super.generateUserTeamData(orgId, toolId, projectId, CommonConstants.AM_USERTEAM_DASHBOARD_ID, qId);
	}

	/**
	 * This method generates the JSON file required for generating the UserTeam
	 * Chart for Alignment and Motivation for all the questions for the tool in the
	 * specified location and also returns the list of UserTeam Data.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * 
	 * @return UserTeamData -- An arraylist containing the UserTeam Data.
	 *
	 */
	public ArrayList<UserTeamData> generateUserTeamDataAllQuestions(String orgId, String toolId, String projectId)
			throws JsonGenException {
		return super.generateUserTeamDataAllQuestions(orgId, toolId, projectId,
				CommonConstants.AM_USERTEAM_DASHBOARD_ID);
	}

}
