package com.merit.tlgscg.scorecard.user;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

public class UserPrimaryDimensionScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(UserPrimaryDimensionScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		JSONArray userPDScore = null;
		JSONObject szJObj1 = null;
		String metricName;
		String entityType;
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
			System.out.println("TLG User Primary Dimension Scorecard Generation started");
			userPDScore = DBUtility.getUserPrimaryDimensionScore(projectID, toolID, questionnaireID, teamID, userID);
			entityType = "user";
			dimensionType = "Primary Dimension";
			dimensionID = "";
			benchmark = 0;
			parentScoreCardType = "UsC";
			childScoreCardType = "UPDSC";
			label = "";
			for (int i = 0; i < userPDScore.length(); i++) {
				szJObj1 = (JSONObject) userPDScore.get(i);
				System.out.println("PD Score::" + szJObj1);
				dimensionID = szJObj1.get("primary_dimension_id").toString();
				currentTS = new Timestamp(new Date().getTime());
				/*
				 * // PDS generation //
				 * =============================================================================
				 * ======== metricName = "PDS"; metricValue = (double) szJObj1.get("pd_score");
				 * szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue,
				 * userID, entityType, dimensionID, dimensionType, userID, parentScoreCardType,
				 * childScoreCardType, label, benchmark, currentTS, currentTS);
				 * System.out.println("PDS generation result::" + szRes); if (szRes != 1) {
				 * System.out.println("PDS generation failed"); return false; }
				 */

				// PDPS generation
				// =====================================================================================
				metricName = "PDPS";
				metricValue = (szJObj1.getDouble("pd_score") / szJObj1.getDouble("pd_max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, userID, parentScoreCardType, childScoreCardType, label, benchmark,
						currentTS, currentTS);
				System.out.println("PDPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("PDPS generation failed");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e);
			e.printStackTrace();
			throw e;
		} finally {
			userPDScore = null;
			szJObj1 = null;
			currentTS = null;
		}
	}
}
