package com.merit.tlgscg.scorecard.team;

import java.sql.Timestamp;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

/**
 * This class implements a method to generate all scores for a team at the tool level
 * @author rekha
 *
 */
public class TeamToolScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(TeamToolScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID, String teamID)
			throws Exception {
		JSONArray teamToolScore = null;
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
			System.out.println("TLG Team Tool Scorecard Generation started");
			teamToolScore = DBUtility.getTeamToolScore(projectID, toolID, questionnaireID, teamID);
			entityType = "team";
			dimensionType = "Tool";
			dimensionID = "";
			benchmark = 0;
			parentScoreCardType = "TSC";
			childScoreCardType = "TTSC";
			label = "";
			for (int i = 0; i < teamToolScore.length(); i++) {
				szJObj1 = (JSONObject) teamToolScore.get(i);
				System.out.println("Tool Score::" + szJObj1);
				dimensionID = toolID;
				currentTS = new Timestamp(new Date().getTime());
				// TTPS generation
				// =====================================================================================
				metricName = "TTPS";
				metricValue = (szJObj1.getDouble("tool_score") / szJObj1.getDouble("tool_max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, teamID, entityType,
						dimensionID, dimensionType, teamID, parentScoreCardType, childScoreCardType, label, benchmark,
						currentTS, currentTS);
				System.out.println("TTPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("TTPS generation failed");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e);
			e.printStackTrace();
			throw e;
		} finally {
			teamToolScore = null;
			szJObj1 = null;
			currentTS = null;
		}
	}
}
