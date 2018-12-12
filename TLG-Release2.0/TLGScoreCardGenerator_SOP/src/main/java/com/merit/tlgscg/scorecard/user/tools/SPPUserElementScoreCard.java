package com.merit.tlgscg.scorecard.user.tools;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

public class SPPUserElementScoreCard {
	// static Logger szLogger =
	// LogManager.getLogger(UserElementScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		JSONArray userMetricScore = null;
		String metricName;
		String entityType;
		String dimensionType;
		String dimensionID;
		String parentScoreCardType;
		String childScoreCardType;
		String questionID;
		double metricValue, max_score;
		String label;
		float benchmark;
		Timestamp currentTS;
		JSONObject szJObj1 = null;
		int szRes = -1;
		metricValue = 1;
		try {
			System.out.println("TLG SPP User Element Scorecard Generation started");
			userMetricScore = DBUtility.getUserElementScoreForSPP(projectID, toolID, questionnaireID, teamID, userID);
			entityType = "user";
			dimensionType = "Question";
			dimensionID = "";
			benchmark = 0;
			parentScoreCardType = "UEsC";
			childScoreCardType = "UEMSC";
			for (int i = 0; i < userMetricScore.length(); i++) {
				szJObj1 = (JSONObject) userMetricScore.get(i);
				System.out.println("Question::" + szJObj1);
				questionID = szJObj1.get("question_id").toString();
				dimensionID = questionID;
				label = "";

				// ES generation
				// =====================================================================================
				metricName = "ES";
				metricValue = szJObj1.getFloat("res_metric_score");
				currentTS = new Timestamp(new Date().getTime());
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("ES generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("ES generation failed");
					return false;
				}
				
				// EPS generation
				// =====================================================================================
				metricName = "EPS";
				max_score = szJObj1.getFloat("max_score");
				metricValue = (metricValue / max_score) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("EPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("EPS generation failed");
					return false;
				}
			}

			/*userMetricScore = DBUtility.getUserElementScore(projectID, toolID, questionnaireID, teamID, userID);
			dimensionID = "";
			benchmark = 0;
			for (int i = 0; i < userMetricScore.length(); i++) {
				szJObj1 = (JSONObject) userMetricScore.get(i);
				System.out.println("Question::" + szJObj1);
				questionID = szJObj1.get("question_id").toString();
				dimensionID = questionID;
				label = "";
				currentTS = new Timestamp(new Date().getTime());

				// EPS generation
				// =====================================================================================
				metricName = "EPS";
				metricValue = (szJObj1.getFloat("score") / szJObj1.getFloat("max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("EPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("EPS generation failed");
					return false;
				}
			}*/
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			szJObj1 = null;
			userMetricScore = null;
			currentTS = null;
		}
	}
}
