package com.merit.tlgscg.scorecard.organization.tools;

import java.sql.Timestamp;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

/**
 * This class implements a method to generate all scores for an organization/project at each question level specific to a pattern observed in Brexit
 * @author rekha
 *
 */
public class SSTBrexitOrganizationElementScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(OrganizationElementScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID) throws Exception {
		JSONArray orgElementScore = null, orgJSON = null;
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
		/*
		 * HashMap<String, ArrayList<Float>> szSDDataPoints; ArrayList<Float> scores;
		 * Object[] szQIDs; float szTemp;
		 */
		try {
			System.out.println("TLG Organization Element Scorecard Generation started");
			orgElementScore = DBUtility.getOrgElementScore(projectID, toolID, questionnaireID);
			parentScoreCardType = "OSC";
			childScoreCardType = "OESC";
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
				questionID = szJObj1.get("question_id").toString();
				dimensionID = questionID;
				label = "";
				currentTS = new Timestamp(new Date().getTime());

				// OES generation
				// =====================================================================================
				metricName = "OES";
				metricValue = szJObj1.getDouble("org_score");
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, client_id, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("OES generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("OES generation failed");
					return false;
				}

				// MOES generation
				// =====================================================================================
				metricName = "MOES";
				metricValue = szJObj1.getDouble("org_max_score");
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, client_id, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("MOES generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("MOES generation failed");
					return false;
				}
			}

			orgElementScore = DBUtility.getOrgElementScore(projectID, toolID, questionnaireID);
			dimensionID = "";
			benchmark = 0;

			for (int i = 0; i < orgElementScore.length(); i++) {
				szJObj1 = (JSONObject) orgElementScore.get(i);
				System.out.println("Question::" + szJObj1);
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

			orgElementScore = null;
			orgElementScore = DBUtility.getOrgElementMetricScore(projectID, toolID, questionnaireID);
			dimensionType = "Response Metric";
			dimensionID = "";
			benchmark = 0;
			label = "";
			for (int i = 0; i < orgElementScore.length(); i++) {
				szJObj1 = (JSONObject) orgElementScore.get(i);
				System.out.println("Org Score::" + szJObj1);
				dimensionID = szJObj1.getString("response_metric_id");
				questionID = szJObj1.getString("question_id");
				currentTS = new Timestamp(new Date().getTime());
				// OEMPS generation
				// =====================================================================================
				metricName = "OEMPS";
				metricValue = (szJObj1.getDouble("score") / szJObj1.getDouble("max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, client_id, entityType,
						dimensionID, dimensionType, questionID, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("OEMPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("OEMPS generation failed");
					return false;
				}
			}

			orgElementScore = null;
			orgElementScore = DBUtility.getOrgToolElementMetricScore(projectID, toolID, questionnaireID);
			dimensionType = "Response Metric";
			dimensionID = "";
			benchmark = 0;
			label = "";
			for (int i = 0; i < orgElementScore.length(); i++) {
				szJObj1 = (JSONObject) orgElementScore.get(i);
				System.out.println("Org Score::" + szJObj1);
				dimensionID = szJObj1.getString("response_metric_id");
				currentTS = new Timestamp(new Date().getTime());
				// OTEMPS generation
				// =====================================================================================
				metricName = "OTEMPS";
				metricValue = (szJObj1.getDouble("score") / szJObj1.getDouble("max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, client_id, entityType,
						dimensionID, dimensionType, client_id, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("OTEMPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("OTEMPS generation failed");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			szJObj1 = null;
			orgElementScore = null;
			orgJSON = null;
			currentTS = null;
		}
	}

	public static void main(String args[]) {
		try {
			System.out.println("response::" + generateScoreCard("PR-001", "SST-001", "TMP-001"));
		} catch (Exception E) {
			E.printStackTrace();
		}

	}
}
