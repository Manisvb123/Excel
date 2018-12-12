package com.merit.tlgscg.scorecard.user.tools;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

public class SSTBrexitUserElementScoreCard {

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
		String questionID = null;
		double metricValue;
		String label;
		float benchmark;
		Timestamp currentTS;
		JSONObject szJObj1 = null;
		int szRes = -1;
		try {
			System.out.println("TLG User Element Scorecard Generation started");
			userMetricScore = DBUtility.getSSTBrexitUserElementMetricScore(projectID, toolID, questionnaireID, teamID,
					userID);
			entityType = "user";
			dimensionType = "Question";
			dimensionID = "";
			benchmark = 0;
			metricValue = 1;
			for (int i = 0; i < userMetricScore.length(); i++) {
				szJObj1 = (JSONObject) userMetricScore.get(i);
				System.out.println("Question::" + szJObj1);
				questionID = szJObj1.get("question_id").toString();
				dimensionID = questionID;
				// ES generation
				// =====================================================================================
				metricName = "ES";
				metricValue = szJObj1.getFloat("score");
				parentScoreCardType = "UEsC";
				childScoreCardType = "UEMSC";
				label = "";
				currentTS = new Timestamp(new Date().getTime());
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("ES generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("ES generation failed");
					return false;
				}

				// MES generation
				// =====================================================================================
				metricName = "MES";
				metricValue = szJObj1.getFloat("max_score");
				currentTS = new Timestamp(new Date().getTime());
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("MES generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("MES generation failed");
					return false;
				}

				// EPS generation
				// =====================================================================================
				metricName = "EPS";
				metricValue = szJObj1.getFloat("score") / szJObj1.getFloat("max_score") * 100;
				currentTS = new Timestamp(new Date().getTime());
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("EPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("EPS generation failed");
					return false;
				}
			}

			userMetricScore = null;
			userMetricScore = DBUtility.getUserToolElementMetricScore(projectID, toolID, questionnaireID, teamID, userID);
			entityType = "user";
			dimensionType = "Response Metric";
			dimensionID = "";
			benchmark = 0;
			parentScoreCardType = "UEsC";
			childScoreCardType = "UEMSC";
			label = "";
			for (int i = 0; i < userMetricScore.length(); i++) {
				szJObj1 = (JSONObject) userMetricScore.get(i);
				System.out.println("Tool Score::" + szJObj1);
				dimensionID = szJObj1.getString("response_metric_id");
				currentTS = new Timestamp(new Date().getTime());
				// TEMPS generation
				// =====================================================================================
				metricName = "TEMPS";
				metricValue = (szJObj1.getDouble("score") / szJObj1.getDouble("max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, userID, parentScoreCardType, childScoreCardType, label, benchmark,
						currentTS, currentTS);
				System.out.println("TEMPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("TEMPS generation failed");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e);
			e.printStackTrace();
			throw e;
		} finally {
			szJObj1 = null;
			userMetricScore = null;
			currentTS = null;
		}
	}

	public static void main(String args[]) {
		try {
			System.out.println("response::" + generateScoreCard("PR-001", "SST-001", "TMP-001", "TM-001", "3"));
		} catch (Exception E) {
			E.printStackTrace();
		}

	}
}
