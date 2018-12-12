package com.merit.tlgscg.scorecard.user;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

public class UserElementMetricScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(UserElementMetricScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		JSONArray userMetricScore = null, szResJ = null;
		String metricName;
		String entityType;
		String dimensionType;
		String dimensionID;
		String parentScoreCardType;
		String childScoreCardType;
		String questionID;
		double metricValue;
		String label;
		String responseMetricID;
		float benchmark;
		Timestamp currentTS;
		JSONObject szJObj1 = null, szJObj2 = null;
		int szRes = -1;
		try {
			System.out.println("TLG User Element Metric Scorecard Generation started");
			userMetricScore = DBUtility.getUserElementMetricScore(projectID, toolID, questionnaireID, teamID, userID);
			entityType = "user";
			dimensionType = "Response Metric";
			dimensionID = "";
			benchmark = 0;
			for (int i = 0; i < userMetricScore.length(); i++) {
				szJObj1 = (JSONObject) userMetricScore.get(i);
				System.out.println("Question::" + szJObj1);
				parentScoreCardType = "UsC";
				childScoreCardType = "UEMSC";
				questionID = szJObj1.get("question_id").toString();
				label = "";
				responseMetricID = szJObj1.get("response_metric_id").toString();
				szResJ = DBUtility.getResponseMetric(toolID, questionnaireID, questionID, responseMetricID);
				for (int j = 0; j < szResJ.length(); j++) {
					szJObj2 = (JSONObject) szResJ.get(j);
					System.out.println("ResponseMetric::" + szJObj2);
					dimensionID = responseMetricID;
					if (!szJObj2.isEmpty()) {
						benchmark = szJObj2.getFloat("response_metric_benchmark");
					}
				}
				currentTS = new Timestamp(new Date().getTime());

				// EMS generation
				// =====================================================================================
				metricName = "EMS";
				metricValue = szJObj1.getFloat("score");
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("EMS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("EMS generation failed");
					return false;
				}

				// MEMS generation
				// =====================================================================================
				metricName = "MEMS";
				metricValue = szJObj1.getFloat("max_score");
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("MEMS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("MEMS generation failed");
					return false;
				}

				// EMPS generation
				// =====================================================================================
				metricName = "EMPS";
				metricValue = szJObj1.getFloat("percentage_score");
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("EMPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("EMPS generation failed");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error::"+ e);
			e.printStackTrace();
			throw e;
		} finally {
			szJObj1 = null;
			szJObj2 = null;
			userMetricScore = null;
			szResJ = null;
			currentTS = null;
		}
	}
}
