package com.merit.tlgscg.scorecard.team;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

/**
 * This class implements a method to generate all scores for a team at each question level
 * @author rekha
 *
 */
public class TeamElementScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(TeamElementScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID, String teamID)
			throws Exception {
		JSONArray toolElementScore = null;
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
			System.out.println("TLG Team Element Scorecard Generation started");
			toolElementScore = DBUtility.getToolElementScore(projectID, toolID, questionnaireID, teamID);
			entityType = "team";
			dimensionType = "Question";
			dimensionID = "";
			benchmark = 0;
			for (int i = 0; i < toolElementScore.length(); i++) {
				szJObj1 = (JSONObject) toolElementScore.get(i);
				System.out.println("Question::" + szJObj1);
				parentScoreCardType = "TSC";
				childScoreCardType = "TESC";
				questionID = szJObj1.get("question_id").toString();
				dimensionID = questionID;
				label = "";
				dimensionID = questionID;
				currentTS = new Timestamp(new Date().getTime());

				// TEPS generation
				// =====================================================================================
				metricName = "TEPS";
				metricValue = (szJObj1.getDouble("tool_score") / szJObj1.getDouble("tool_max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, teamID, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("TEPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("TEPS generation failed");
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
			toolElementScore = null;
			currentTS = null;
		}
	}
}
