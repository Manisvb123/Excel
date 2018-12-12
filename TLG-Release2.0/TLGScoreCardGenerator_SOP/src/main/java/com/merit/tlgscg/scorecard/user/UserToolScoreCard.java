package com.merit.tlgscg.scorecard.user;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

public class UserToolScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(UserToolScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		JSONArray userToolScore = null;
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
			System.out.println("TLG User Tool Scorecard Generation started");
			userToolScore = DBUtility.getUserToolScore(projectID, toolID, questionnaireID, teamID, userID);
			entityType = "user";
			dimensionType = "Tool";
			dimensionID = "";
			benchmark = 0;
			parentScoreCardType = "TsC";
			childScoreCardType = "UTSC";
			label = "";
			for (int i = 0; i < userToolScore.length(); i++) {
				szJObj1 = (JSONObject) userToolScore.get(i);
				System.out.println("Tool Score::" + szJObj1);
				dimensionID = toolID;
				currentTS = new Timestamp(new Date().getTime());
				// TPS generation
				// =====================================================================================
				metricName = "TPS";
				metricValue = (szJObj1.getDouble("tool_score") / szJObj1.getDouble("tool_max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, userID, parentScoreCardType, childScoreCardType, label, benchmark,
						currentTS, currentTS);
				System.out.println("TPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("TPS generation failed");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e);
			e.printStackTrace();
			throw e;
		} finally {
			userToolScore = null;
			szJObj1 = null;
			currentTS = null;
		}
	}
}
