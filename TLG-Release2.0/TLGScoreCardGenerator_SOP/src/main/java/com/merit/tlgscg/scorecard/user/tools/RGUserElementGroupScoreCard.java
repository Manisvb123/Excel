package com.merit.tlgscg.scorecard.user.tools;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;
import static com.merit.tlgscg.utility.RGQuestionGroup.RG_Question_Groups;

public class RGUserElementGroupScoreCard {
	// static Logger szLogger =
	// LogManager.getLogger(RGUserElementGroupScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		JSONArray userElementScore = null;
		String metricName;
		String entityType;
		String dimensionType;
		String dimensionID;
		String parentScoreCardType;
		String childScoreCardType;
		double metricValue, metricValue2, metricValue3, sumEGS, sumMEGS, toolEGS, toolMEGS;
		String label;
		float benchmark;
		Timestamp currentTS;
		String secDimensionID;
		JSONObject szJObj1 = null;
		int szRes = -1;
		HashMap<String, HashMap<String, Float>> secDimScores = null;
		HashMap<String, HashMap<String, Float>> secDimMaxScores = null;
		String[] grpQuestions = null;
		Iterator<String> secDimensionIterator = null;
		HashMap<String, Float> quesScore = null;
		HashMap<String, Float> quesMaxScore = null;
		try {
			System.out.println("TLG RG User Element Group Scorecard Generation started");
			userElementScore = DBUtility.getUserSecondaryDimensionElementScore(projectID, toolID, questionnaireID, teamID,
					userID);
			entityType = "user";
			dimensionID = "";
			benchmark = 0;
			secDimScores = new HashMap<String, HashMap<String, Float>>();
			secDimMaxScores = new HashMap<String, HashMap<String, Float>>();
			label = "";
			for (int i = 0; i < userElementScore.length(); i++) {
				szJObj1 = (JSONObject) userElementScore.get(i);
				System.out.println("Secondary Dimension::" + szJObj1.toString());
				secDimensionID = szJObj1.get("secondary_dimension_id").toString();
				if (secDimScores.containsKey(secDimensionID)) {
					quesScore = secDimScores.get(secDimensionID);
					quesScore.put(szJObj1.getString("question_id"), szJObj1.getFloat("score"));
					quesMaxScore = secDimMaxScores.get(secDimensionID);
					quesMaxScore.put(szJObj1.getString("question_id"), szJObj1.getFloat("max_score"));
				} else {
					quesScore = new HashMap<String, Float>();
					quesMaxScore = new HashMap<String, Float>();
					quesScore.put(szJObj1.getString("question_id"), szJObj1.getFloat("score"));
					quesMaxScore.put(szJObj1.getString("question_id"), szJObj1.getFloat("max_score"));
					secDimScores.put(secDimensionID, quesScore);
					secDimMaxScores.put(secDimensionID, quesMaxScore);
				}
			}
			System.out.println("secDimScores::" + secDimScores.toString());
			secDimensionIterator = secDimScores.keySet().iterator();
			toolEGS = 0;
			toolMEGS = 0;
			while (secDimensionIterator.hasNext()) {
				sumEGS = 0;
				sumMEGS = 0;
				dimensionID = secDimensionIterator.next().toString();
				quesScore = secDimScores.get(dimensionID);
				quesMaxScore = secDimMaxScores.get(dimensionID);
				for (int i = 0; i < RG_Question_Groups.size(); i++) {
					grpQuestions = RG_Question_Groups.get(i);
					metricValue = 1;
					metricValue2 = 1;
					metricValue3 = 0;
					for (int j = 0; j < grpQuestions.length; j++) {
						System.out.println("quesScore::" + quesScore);
						System.out.println("quesMaxScore::" + quesMaxScore);
						System.out.println("grpQuestions[" + j + "]::" + grpQuestions[j]);
						System.out.println("quesScore.get(grpQuestions[" + j + "])::" + quesScore.get(grpQuestions[j]));
						if (quesScore.get(grpQuestions[j]) != null) {
							metricValue = metricValue * quesScore.get(grpQuestions[j]);
							metricValue2 = metricValue2 * quesMaxScore.get(grpQuestions[j]);
						}
					}
					sumEGS = sumEGS + metricValue;
					sumMEGS = sumMEGS + metricValue2;
					toolEGS = toolEGS + sumEGS;
					toolMEGS = toolMEGS + sumMEGS;
					// EGS generation
					// =====================================================================================
					metricName = "EGS";
					parentScoreCardType = "UEGSC";
					childScoreCardType = "UEGSC";
					currentTS = new Timestamp(new Date().getTime());
					szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
							"GRP_" + i, "Group", userID, parentScoreCardType, childScoreCardType, label, benchmark,
							currentTS, currentTS);
					System.out.println("EGS generation result::" + szRes);
					if (szRes != 1) {
						System.out.println("EGS generation failed");
						return false;
					}

					// MEGS generation
					// ====================================================================================
					metricName = "MEGS";
					parentScoreCardType = "UEGSC";
					childScoreCardType = "UEGSC";
					currentTS = new Timestamp(new Date().getTime());
					szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue2, userID, entityType,
							"GRP_" + i, "Group", userID, parentScoreCardType, childScoreCardType, label, benchmark,
							currentTS, currentTS);
					System.out.println("MEGS generation result::" + szRes);
					if (szRes != 1) {
						System.out.println("MEGS generation failed");
						return false;
					}

					// EGPS generation
					// ====================================================================================
					metricName = "EGPS";
					parentScoreCardType = "UEGSC";
					childScoreCardType = "UEGSC";
					metricValue3 = metricValue / metricValue2 * 100;
					currentTS = new Timestamp(new Date().getTime());
					szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue3, userID, entityType,
							"GRP_" + i, "Group", userID, parentScoreCardType, childScoreCardType, label, benchmark,
							currentTS, currentTS);
					System.out.println("EGPS generation result::" + szRes);
					if (szRes != 1) {
						System.out.println("EGPS generation failed");
						return false;
					}
				}

				// SDPAS generation
				// ====================================================================================
				parentScoreCardType = "USDSC";
				childScoreCardType = "USDSC";
				metricName = "SDPAS";
				dimensionType = "Secondary Dimension";
				currentTS = new Timestamp(new Date().getTime());
				metricValue = sumEGS / sumMEGS * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, userID, parentScoreCardType, childScoreCardType, label, benchmark,
						currentTS, currentTS);
				System.out.println("SDPAS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("SDPAS generation failed");
					return false;
				}
			}

			// TI generation
			// ====================================================================================
			parentScoreCardType = "USDSC";
			childScoreCardType = "USDSC";
			metricName = "TI";
			dimensionType = "Tool";
			currentTS = new Timestamp(new Date().getTime());
			metricValue = toolEGS / toolMEGS * 100;
			szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType, toolID,
					dimensionType, userID, parentScoreCardType, childScoreCardType, label, benchmark, currentTS,
					currentTS);
			System.out.println("TI generation result::" + szRes);
			if (szRes != 1) {
				System.out.println("TI generation failed");
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e);
			e.printStackTrace();
			throw e;
		} finally {
			szJObj1 = null;
			userElementScore = null;
			currentTS = null;
			secDimensionIterator = null;

		}
	}
}
