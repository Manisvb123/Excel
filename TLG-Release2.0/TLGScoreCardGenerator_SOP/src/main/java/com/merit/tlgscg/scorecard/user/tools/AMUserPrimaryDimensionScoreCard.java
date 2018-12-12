package com.merit.tlgscg.scorecard.user.tools;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

public class AMUserPrimaryDimensionScoreCard {
	
	//static Logger szLogger = LogManager.getLogger(UserPrimaryDimensionScoreCard.class.getName());
	
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
		double metricValue;
		String label;
		float benchmark;
		double pd_weightage_score, pd_max_score;
		float weightage;
		double szTemp;
		Timestamp currentTS;
		int szRes = -1;
		try {
			System.out.println("TLG AM User Primary Dimension Scorecard Generation started");
			userPDScore = DBUtility.getUserPrimaryDimensionScore(projectID, toolID, questionnaireID, teamID, userID);
			entityType = "user";
			dimensionType = "Primary Dimension";
			dimensionID = "";
			benchmark = 0;
			parentScoreCardType = "UsC";
			childScoreCardType = "UPDSC";
			label = "";
			pd_weightage_score = 0;
			for (int i = 0; i < userPDScore.length(); i++) {
				szJObj1 = (JSONObject) userPDScore.get(i);
				System.out.println("PD Score::" + szJObj1);
				dimensionID = szJObj1.get("primary_dimension_id").toString();
				pd_max_score = szJObj1.getDouble("pd_max_score");
				currentTS = new Timestamp(new Date().getTime());
				/*// PDS generation
				// =====================================================================================
				metricName = "PDS";
				metricValue = (double) szJObj1.get("pd_score");
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, userID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("PDS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("PDS generation failed");
					return false;
				}*/
				
				// PDAS generation
				// =====================================================================================
				metricName = "PDAS";
				metricValue = szJObj1.getDouble("avg_score");
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
						dimensionID, dimensionType, userID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("PDAS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("PDAS generation failed");
					return false;
				}
				
				weightage = 0;
				weightJSON = DBUtility.getDimensionWeight(szJObj1.getInt("primary_dimension_id"));
				System.out.println("Weightage::" + weightJSON);
				for (int j = 0; j < weightJSON.length(); j++) {
					szJObj1 = (JSONObject) weightJSON.get(j);
					weightage = szJObj1.getFloat("weightage");
				}
				szTemp = weightage
						* ((metricValue / pd_max_score) * 100);
				pd_weightage_score = pd_weightage_score + szTemp;
			}
			// TI generation
			// =====================================================================================
			metricName = "TI";
			metricValue = pd_weightage_score;
			currentTS = new Timestamp(new Date().getTime());
			szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, userID, entityType,
					dimensionID, dimensionType, userID, parentScoreCardType, childScoreCardType, label,
					benchmark, currentTS, currentTS);
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
}
