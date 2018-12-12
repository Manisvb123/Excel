package com.merit.tlg.dashboard.business;

import org.apache.log4j.Logger;

/**
 * This is a wrapper Class used to create JsonFiles for all the tools in the
 * configured location.
 */
public class DashboardServiceWrapper {
	final static Logger logger = Logger.getLogger(DashboardServiceWrapper.class);

	/**
	 * This method generates all the JSON files required for generating the
	 * different Charts for SST, SSTBR, AM, BEF, CB, LIF, RG, SD and SPP.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 *
	 */
	public void generateAllJsonsForTool(String orgId, String toolId, String projectId) throws Exception {
		if (toolId.startsWith("SSTBR")) {
			generateAllJsonsForSSTBR(orgId, toolId, projectId);
		} else if (toolId.startsWith("SST")) {
			generateAllJsonsForSST(orgId, toolId, projectId);
		} else if (toolId.startsWith("AM")) {
			generateAllJsonsForAM(orgId, toolId, projectId);
		} else if (toolId.startsWith("BEF")) {
			generateAllJsonsForBEF(orgId, toolId, projectId);
		} else if (toolId.startsWith("CB")) {
			generateAllJsonsForCB(orgId, toolId, projectId);
		} else if (toolId.startsWith("LIF")) {
			generateAllJsonsForLIF(orgId, toolId, projectId);
		} else if (toolId.startsWith("RG")) {
			generateAllJsonsForRANDG(orgId, toolId, projectId);
		} else if (toolId.startsWith("SD")) {
			generateAllJsonsForSD(orgId, toolId, projectId);
		} else if (toolId.startsWith("SPP")) {
			generateAllJsonsForSPP(orgId, toolId, projectId);
		}
	}

	/**
	 * This method generates all the JSON files required for generating the
	 * different Charts for Strategy Stress Test.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 *
	 */
	public void generateAllJsonsForSST(String orgId, String toolId, String projectId) throws Exception {

		logger.debug("generateAllJsonsForSST: Started Generating JSONs");
		SSTDashboardService sstService = new SSTDashboardService();
		sstService.generateSSTInsightData(orgId, toolId, projectId);
		sstService.generateSSTIndexData(orgId, toolId, projectId);
		sstService.generateUserTeamDataAllQuestions(orgId, toolId, projectId);
		logger.debug("generateAllJsonsForSST: Completed Generating JSONs");
	}

	/**
	 * This method generates all the JSON files required for generating the
	 * different Charts for SST for Brexit .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 *
	 */
	public void generateAllJsonsForSSTBR(String orgId, String toolId, String projectId) throws Exception {
		logger.debug("generateAllJsonsForSSTBR: Started Generating JSONs");
		SSTBrexitDashboardService service = new SSTBrexitDashboardService();
		service.generateSSTBRInsightData(orgId, toolId, projectId);
		service.generateIndexData(orgId, toolId, projectId);
		service.generateUserTeamDataAllQuestions(orgId, toolId, projectId);
		logger.debug("generateAllJsonsForSSTBR: Completed Generating JSONs");
	}

	/**
	 * This method generates all the JSON files required for generating the
	 * different Charts for Strategic Planning Process .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 *
	 */
	public void generateAllJsonsForSPP(String orgId, String toolId, String projectId) throws Exception {
		logger.debug("generateAllJsonsForSPP: Started Generating JSONs");
		SPPDashboardService service = new SPPDashboardService();
		service.generateInsightData(orgId, toolId, projectId);
		service.generateIndexData(orgId, toolId, projectId);
		service.generateUserTeamDataAllQuestions(orgId, toolId, projectId);
		logger.debug("generateAllJsonsForSPP: Completed Generating JSONs");
	}

	/**
	 * This method generates all the JSON files required for generating the
	 * different Charts for Alignment and Motivation .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 *
	 */
	public void generateAllJsonsForAM(String orgId, String toolId, String projectId) throws Exception {
		logger.debug("generateAllJsonsForAM: Started Generating JSONs");
		AMDashboardService service = new AMDashboardService();
		service.generateInsightData(orgId, toolId, projectId);
		service.generateIndexData(orgId, toolId, projectId);
		service.generateUserTeamDataAllQuestions(orgId, toolId, projectId);
		logger.debug("generateAllJsonsForAM: Completed Generating JSONs");
	}

	/**
	 * This method generates all the JSON files required for generating the
	 * different Charts for Culture and Behaviour.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 *
	 */
	public void generateAllJsonsForCB(String orgId, String toolId, String projectId) throws Exception {
		logger.debug("generateAllJsonsForCB: Started Generating JSONs");
		CBDashboardService service = new CBDashboardService();
		service.generateInsightData(orgId, toolId, projectId);
		service.generateIndexData(orgId, toolId, projectId);
		service.generateUserTeamDataAllQuestions(orgId, toolId, projectId);
		logger.debug("generateAllJsonsForCB: Completed Generating JSONs");
	}

	/**
	 * This method generates all the JSON files required for generating the
	 * different Charts for Leaders Impact Framework.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 *
	 */
	public void generateAllJsonsForLIF(String orgId, String toolId, String projectId) throws Exception {
		logger.debug("generateAllJsonsForLIF: Started Generating JSONs");
		LIFDashboardService service = new LIFDashboardService();
		service.generateInsightData(orgId, toolId, projectId);
		service.generateIndexData(orgId, toolId, projectId);
		service.generateUserTeamDataAllQuestions(orgId, toolId, projectId);
		logger.debug("generateAllJsonsForLIF: Completed Generating JSONs");
	}

	/**
	 * This method generates all the JSON files required for generating the
	 * different Charts for Board Effectiveness Framework.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 *
	 */
	public void generateAllJsonsForBEF(String orgId, String toolId, String projectId) throws Exception {
		logger.debug("generateAllJsonsForBEF: Started Generating JSONs");
		BEFDashboardService service = new BEFDashboardService();
		service.generateInsightData(orgId, toolId, projectId);
		service.generateIndexData(orgId, toolId, projectId);
		service.generateUserTeamDataAllQuestions(orgId, toolId, projectId);
		logger.debug("generateAllJsonsForBEF: Completed Generating JSONs");
	}

	/**
	 * This method generates all the JSON files required for generating the
	 * different Charts for Structural Dynamics.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 *
	 */
	public void generateAllJsonsForSD(String orgId, String toolId, String projectId) throws Exception {
		logger.debug("generateAllJsonsForSD: Started Generating JSONs");
		SDDashboardService service = new SDDashboardService();
		service.generateInsightData(orgId, toolId, projectId);
		service.generateIndexData(orgId, toolId, projectId);
		service.generateUserTeamDataAllQuestions(orgId, toolId, projectId);
		logger.debug("generateAllJsonsForSD: Completed Generating JSONs");
	}

	/**
	 * This method generates all the JSON files required for generating the
	 * different Charts for Risk and Governance.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 *
	 */
	public void generateAllJsonsForRANDG(String orgId, String toolId, String projectId) throws Exception {
		logger.debug("generateAllJsonsForRANDG: Started Generating JSONs");
		RandGDashboardService service = new RandGDashboardService();
		service.generateInsightData(orgId, toolId, projectId);
		service.generateIndexData(orgId, toolId, projectId);
		service.generateUserTeamDataAllQuestions(orgId, toolId, projectId);
		logger.debug("generateAllJsonsForRANDG: Completed Generating JSONs");
	}
}
