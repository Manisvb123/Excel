package com.merit.tlgscg.scorecard.team;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

/**
 * This class implements a method to generate all scores for a team at each primary dimension level
 * @author rekha
 *
 */
public class TeamPrimaryDimensionScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(TeamPrimaryDimensionScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID, String teamID)
			throws Exception {
		JSONArray toolPDScore = null;
		JSONObject szJObj1 = null;
		String metricName;
		String entityType;
		String entityID;
		String dimensionType;
		String dimensionID;
		String parentScoreCardType;
		String childScoreCardType;
		double metricValue;
		String label;
		float benchmark;
		Timestamp currentTS;
		int szRes = -1;
		try {
			System.out.println("TLG Team Primary Dimension Scorecard Generation started");
			toolPDScore = DBUtility.getTeamPrimaryDimensionScore(projectID, toolID, questionnaireID, teamID);
			entityType = "team";
			entityID = teamID;
			dimensionType = "Primary Dimension";
			dimensionID = "";
			benchmark = 0;
			parentScoreCardType = "TSC";
			childScoreCardType = "TPDSC";
			label = "";
			for (int i = 0; i < toolPDScore.length(); i++) {
				szJObj1 = (JSONObject) toolPDScore.get(i);
				System.out.println("PD Score::" + szJObj1);
				dimensionID = szJObj1.get("primary_dimension_id").toString();
				currentTS = new Timestamp(new Date().getTime());
				/*
				 * // TPDS generation //
				 * =============================================================================
				 * ======== metricName = "TPDS"; metricValue = (double) szJObj1.get("pd_score");
				 * szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue,
				 * toolID, entityType, dimensionID, dimensionType, toolID, parentScoreCardType,
				 * childScoreCardType, label, benchmark, currentTS, currentTS);
				 * System.out.println("TPDS generation result::" + szRes); if (szRes != 1) {
				 * System.out.println("TPDS generation failed"); return false; }
				 */

				// TPDPS generation
				// =====================================================================================
				metricName = "TPDPS";
				metricValue = (szJObj1.getDouble("pd_score") / szJObj1.getDouble("pd_max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, entityID, entityType,
						dimensionID, dimensionType, toolID, parentScoreCardType, childScoreCardType, label, benchmark,
						currentTS, currentTS);
				System.out.println("TPDPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("TPDPS generation failed");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e);
			e.printStackTrace();
			throw e;
		} finally {
			toolPDScore = null;
			szJObj1 = null;
			currentTS = null;
		}
	}
}
