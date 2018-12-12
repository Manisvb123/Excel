package com.merit.tlgscg.scorecard.organization;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;
import com.merit.tlgscg.utility.MathUtility;

/**
 * This class implements a method to generate all scores for an organization/project at each question level
 * @author rekha
 *
 */
public class OrganizationElementScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(OrganizationElementScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID) throws Exception {
		JSONArray orgElementScore = null, orgJSON = null, orgElementAvgPercScore = null, orgElementPercScore = null;
		String metricName;
		String entityType;
		String dimensionType;
		String dimensionID;
		String parentScoreCardType;
		String childScoreCardType;
		String questionID;
		String client_id = null;
		double metricValue;
		String label;
		float benchmark;
		Timestamp currentTS;
		JSONObject szJObj1 = null;
		int szRes = -1;
		HashMap<String, ArrayList<Float>> szSDDataPoints;
		ArrayList<Float> scores;
		Object[] szQIDs;
		float szTemp;
		try {
			System.out.println("TLG Organization Element Scorecard Generation started");
			orgElementScore = DBUtility.getOrgElementScore(projectID, toolID, questionnaireID);
			orgJSON = DBUtility.getOrgForProject(projectID);
			System.out.println("Organization::" + orgJSON);
			for (int i = 0; i < orgJSON.length(); i++) {
				szJObj1 = (JSONObject) orgJSON.get(i);
				client_id = szJObj1.get("organization_id").toString();
			}
			entityType = "organization";
			dimensionType = "Question";
			dimensionID = "";
			benchmark = 0;

			for (int i = 0; i < orgElementScore.length(); i++) {
				szJObj1 = (JSONObject) orgElementScore.get(i);
				System.out.println("Question::" + szJObj1);
				parentScoreCardType = "OSC";
				childScoreCardType = "OESC";
				questionID = szJObj1.get("question_id").toString();
				dimensionID = questionID;
				label = "";
				currentTS = new Timestamp(new Date().getTime());

				// OEPS generation
				// =====================================================================================
				metricName = "OEPS";
				metricValue = (szJObj1.getDouble("org_score") / szJObj1.getDouble("org_max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, client_id, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("OEPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("OEPS generation failed");
					return false;
				}
			}

			// OEAPS generation
			// =====================================================================================
			orgElementAvgPercScore = DBUtility.getElementAveragePercentScore(projectID, toolID, questionnaireID);
			for (int i = 0; i < orgElementAvgPercScore.length(); i++) {
				szJObj1 = (JSONObject) orgElementAvgPercScore.get(i);
				System.out.println("Question::" + szJObj1);
				parentScoreCardType = "OSC";
				childScoreCardType = "OESC";
				questionID = szJObj1.get("question_id").toString();
				dimensionID = questionID;
				label = "";
				currentTS = new Timestamp(new Date().getTime());
				metricName = "OEAPS";
				metricValue = szJObj1.getDouble("avg_perc_score");
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, client_id, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("OEAPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("OEAPS generation failed");
					return false;
				}
			}

			// OESDS generation
			// =====================================================================================
			orgElementPercScore = DBUtility.getElementPercentScore(projectID, toolID, questionnaireID);
			szSDDataPoints = new HashMap<String, ArrayList<Float>>();
			for (int i = 0; i < orgElementPercScore.length(); i++) {
				szJObj1 = (JSONObject) orgElementPercScore.get(i);
				System.out.println("Question::" + szJObj1);
				questionID = szJObj1.get("question_id").toString();
				szTemp = szJObj1.getFloat("percentage_score");
				if (szSDDataPoints.containsKey(questionID)) {
					szSDDataPoints.get(questionID).add(szTemp);
				} else {
					scores = new ArrayList<Float>();
					scores.add(szTemp);
					szSDDataPoints.put(questionID, scores);
				}
			}
			parentScoreCardType = "OSC";
			childScoreCardType = "OESC";
			szQIDs = szSDDataPoints.keySet().toArray();
			for (int i = 0; i < szQIDs.length; i++) {
				questionID = szQIDs[i].toString();
				dimensionID = questionID;
				label = "";
				currentTS = new Timestamp(new Date().getTime());
				metricName = "OESDS";
				metricValue = MathUtility.calculateSD(szSDDataPoints.get(dimensionID).toArray());
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, client_id, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("OESDS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("OESDS generation failed");
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
			orgElementScore = null;
			orgJSON = null;
			currentTS = null;
		}
	}
}
