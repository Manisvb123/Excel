package com.merit.tlgscg.scorecard.user.tools;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

public class SPPUserPrimaryDimensionScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(UserToolScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		JSONArray userPDScore = null, weightJSON = null;
		JSONObject szJObj1 = null;
		String metricName;
		String entityType;
		String dimensionType;
		String dimensionID;
		String parentScoreCardType;
		String childScoreCardType;
		double metricValue, pd_weightage_score, szTemp;
		String label;
		float benchmark, weightage;
		Timestamp currentTS;
		int szRes = -1;
		try {
			System.out.println("TLG SPP User Primary Dimension Scorecard Generation started");
			userPDScore = DBUtility.getUserPrimaryDimensionScore(projectID, toolID, questionnaireID, teamID, userID);
			entityType = "user";
			dimensionType = "Tool";
			dimensionID = toolID;
			benchmark = 0;
			parentScoreCardType = "UsC";
			childScoreCardType = "UPDSC";
			label = "";
			pd_weightage_score = 0;
			for (int i = 0; i < userPDScore.length(); i++) {
				szJObj1 = (JSONObject) userPDScore.get(i);
				System.out.println("PD Score::" + szJObj1);
				dimensionID = szJObj1.get("primary_dimension_id").toString();
				metricValue = (szJObj1.getDouble("pd_score") / szJObj1.getDouble("pd_max_score")) * 100;
				weightage = 0;
				weightJSON = DBUtility.getDimensionWeight(szJObj1.getInt("primary_dimension_id"));
				System.out.println("Weightage::" + weightJSON);
				for (int j = 0; j < weightJSON.length(); j++) {
					szJObj1 = (JSONObject) weightJSON.get(j);
					weightage = szJObj1.getFloat("weightage");
				}
				szTemp = weightage * metricValue;
				pd_weightage_score = pd_weightage_score + szTemp;
			}
			currentTS = new Timestamp(new Date().getTime());

			// TI generation
			// =====================================================================================
			metricName = "TI";
			szRes = DBUtility.addScoreCard(toolID, projectID, metricName, pd_weightage_score, userID, entityType,
					dimensionID, dimensionType, userID, parentScoreCardType, childScoreCardType, label, benchmark,
					currentTS, currentTS);
			System.out.println("TI generation result::" + szRes);
			if (szRes != 1) {
				System.out.println("PDPS generation failed");
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			userPDScore = null;
			szJObj1 = null;
			currentTS = null;
			weightJSON = null;
		}
	}
}
