package com.merit.tlgscg.scorecard.user;

import java.sql.Timestamp;
import static com.merit.tlgscg.utility.TLGConstants.*;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

public class UserElementScoreCard {

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
		double metricValue;
		String label;
		float benchmark;
		Timestamp currentTS;
		JSONObject szJObj1 = null;
		int szRes = -1;
		try {
			System.out.println("TLG User Element Scorecard Generation started");
			userMetricScore = DBUtility.getUserElementScore(projectID, toolID, questionnaireID, teamID, userID);
			entityType = "user";
			dimensionType = "Question";
			dimensionID = "";
			benchmark = 0;
			for (int i = 0; i < userMetricScore.length(); i++) {
				szJObj1 = (JSONObject) userMetricScore.get(i);
				System.out.println("Question::" + szJObj1);
				parentScoreCardType = "UEsC";
				childScoreCardType = "UEMSC";
				questionID = szJObj1.get("question_id").toString();
				label = "";
				dimensionID = questionID;
				currentTS = new Timestamp(new Date().getTime());

				// EMS generation
				// =====================================================================================
				if (!(toolID.equalsIgnoreCase(CB_ID)) && (!(toolID.equalsIgnoreCase(SD_ID)))
						&& (!(toolID.equalsIgnoreCase(BEF_ID))) && (!(toolID.equalsIgnoreCase(RG_ID)))) {
					metricName = "ES";
					metricValue = szJObj1.getFloat("score");
					szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
							dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
							benchmark, currentTS, currentTS);
					System.out.println("ES generation result::" + szRes);
					if (szRes != 1) {
						System.out.println("ES generation failed");
						return false;
					}
				}

				// MEMS generation
				// =====================================================================================
				if ((!(toolID.equalsIgnoreCase(LIF_ID))) && (!(toolID.equalsIgnoreCase(AM_ID)))
						&& (!(toolID.equalsIgnoreCase(CB_ID))) && (!(toolID.equalsIgnoreCase(SD_ID)))
						&& (!(toolID.equalsIgnoreCase(BEF_ID))) && (!(toolID.equalsIgnoreCase(RG_ID)))) {
					metricName = "MES";
					metricValue = szJObj1.getFloat("max_score");
					szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
							dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
							benchmark, currentTS, currentTS);
					System.out.println("MES generation result::" + szRes);
					if (szRes != 1) {
						System.out.println("MES generation failed");
						return false;
					}
				}

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
}
