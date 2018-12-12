package com.merit.tlgscg.scorecard.user.tools;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

public class SSTBrexitUserPrimaryDimensionScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(SSTBrexitUserPrimaryDimensionScoreCard.class.getName());

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
		double metricValue = 0;
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
				metricValue = metricValue + (szJObj1.getDouble("pd_score") / szJObj1.getDouble("pd_max_score")) * 100;
			}
			metricName = "TI";
			dimensionID = szJObj1.getInt("primary_dimension_id") + "";
			currentTS = new Timestamp(new Date().getTime());
			metricValue = metricValue / userPDScore.length();
			szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType, dimensionID,
					dimensionType, userID, parentScoreCardType, childScoreCardType, label, benchmark, currentTS,
					currentTS);
			System.out.println("TI generation result::" + szRes);
			if (szRes != 1) {
				System.out.println("TI generation failed");
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
