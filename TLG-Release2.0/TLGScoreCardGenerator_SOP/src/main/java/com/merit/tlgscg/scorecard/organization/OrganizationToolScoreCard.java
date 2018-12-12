package com.merit.tlgscg.scorecard.organization;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

public class OrganizationToolScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(OrganizationToolScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID) throws Exception {
		JSONArray orgPDScore = null, orgToolScore = null, orgJSON = null, toolBM = null;
		JSONObject szJObj1 = null, szJObj2 = null;
		String metricName;
		String entityType;
		String dimensionType;
		String dimensionID;
		String parentScoreCardType;
		String childScoreCardType;
		String client_id = null;
		double metricValue = 0;
		double szTemp;
		String label;
		float benchmark;
		Timestamp currentTS;
		int szRes = -1;
		float weightage = 0;
		try {
			System.out.println("TLG Organization Tool Scorecard Generation started");
			orgToolScore = DBUtility.getOrgToolScore(projectID, toolID, questionnaireID);
			orgJSON = DBUtility.getOrgForProject(projectID);
			System.out.println("Organization::" + orgJSON);
			for (int i = 0; i < orgJSON.length(); i++) {
				szJObj1 = (JSONObject) orgJSON.get(i);
				client_id = szJObj1.get("organization_id").toString();
			}
			entityType = "organization";
			dimensionType = "Tool";
			dimensionID = "";
			benchmark = 0;
			parentScoreCardType = "OSC";
			childScoreCardType = "OTSC";
			label = "";
			for (int i = 0; i < orgToolScore.length(); i++) {
				szJObj1 = (JSONObject) orgToolScore.get(i);
				System.out.println("Tool Score::" + szJObj1);
				dimensionID = toolID;
				currentTS = new Timestamp(new Date().getTime());
				// OTPS generation
				// =====================================================================================
				metricName = "OTPS";
				metricValue = (szJObj1.getDouble("org_score") / szJObj1.getDouble("org_max_score")) * 100;
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, client_id, entityType,
						dimensionID, dimensionType, client_id, parentScoreCardType, childScoreCardType, label,
						benchmark, currentTS, currentTS);
				System.out.println("OTPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("OTPS generation failed");
					return false;
				}
			}

			orgPDScore = DBUtility.getOrgPrimaryDimensionScore(projectID, toolID, questionnaireID);
			metricValue = 0;
			for (int i = 0; i < orgPDScore.length(); i++) {
				szJObj1 = (JSONObject) orgPDScore.get(i);
				System.out.println("PD Score::" + szJObj1);
				dimensionID = toolID;
				orgJSON = DBUtility.getDimensionWeight(szJObj1.getInt("primary_dimension_id"));
				System.out.println("Weightage::" + orgJSON);
				for (int j = 0; j < orgJSON.length(); j++) {
					szJObj2 = (JSONObject) orgJSON.get(j);
					weightage = szJObj2.getFloat("weightage");
				}
				szTemp = weightage * ((szJObj1.getDouble("pd_score") / szJObj1.getDouble("pd_max_score")) * 100);
				metricValue = metricValue + szTemp;
			}

			toolBM = DBUtility.getToolBenchmark(toolID);
			benchmark = 0;
			for (int i = 0; i < toolBM.length(); i++) {
				szJObj1 = (JSONObject) toolBM.get(i);
				benchmark = szJObj1.getFloat("benchmark");
			}
			currentTS = new Timestamp(new Date().getTime());
			// OTI generation
			// =====================================================================================
			metricName = "OTI";
			szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, client_id, entityType,
					dimensionID, dimensionType, toolID, parentScoreCardType, childScoreCardType, label, benchmark,
					currentTS, currentTS);
			System.out.println("OTI generation result::" + szRes);
			if (szRes != 1) {
				System.out.println("OTI generation failed");
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e);
			e.printStackTrace();
			throw e;
		} finally {
			orgToolScore = null;
			orgJSON = null;
			szJObj1 = null;
			szJObj2 = null;
			currentTS = null;
			toolBM = null;
		}
	}
}
