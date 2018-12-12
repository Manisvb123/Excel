package com.merit.tlgscg.scorecard.organization;

import java.sql.Timestamp;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

import com.merit.tlgscg.dbutility.DBUtility;

/**
 * This class implements a method to generate all scores for an organization/project at the primary dimension level
 * @author rekha
 *
 */
public class OrganizationPrimaryDimensionScoreCard {

	// static Logger szLogger =
	// LogManager.getLogger(OrganizationPrimaryDimensionScoreCard.class.getName());

	public static boolean generateScoreCard(String projectID, String toolID, String questionnaireID) throws Exception {
		JSONArray orgPDScore = null, orgJSON = null, primDimBM = null;
		JSONObject szJObj1 = null;
		String metricName;
		String entityType;
		String dimensionType;
		String dimensionID;
		String parentScoreCardType;
		String childScoreCardType;
		String client_id = null;
		double metricValue;
		String label;
		float benchmark;
		Timestamp currentTS;
		int szRes = -1;
		try {
			System.out.println("TLG Organization Primary Dimension Scorecard Generation started");
			orgPDScore = DBUtility.getOrgPrimaryDimensionScore(projectID, toolID, questionnaireID);
			orgJSON = DBUtility.getOrgForProject(projectID);
			System.out.println("Organization::" + orgJSON);
			for (int i = 0; i < orgJSON.length(); i++) {
				szJObj1 = (JSONObject) orgJSON.get(i);
				client_id = szJObj1.get("organization_id").toString();
			}
			entityType = "organization";
			dimensionType = "Primary Dimension";
			dimensionID = "";
			benchmark = 0;
			parentScoreCardType = "OSC";
			childScoreCardType = "OPDSC";
			label = "";
			for (int i = 0; i < orgPDScore.length(); i++) {
				szJObj1 = (JSONObject) orgPDScore.get(i);
				System.out.println("PD Score::" + szJObj1);
				dimensionID = szJObj1.get("primary_dimension_id").toString();
				currentTS = new Timestamp(new Date().getTime());
				/*
				 * // OPDS generation //
				 * =============================================================================
				 * ======== metricName = "OPDS"; metricValue = (double) szJObj1.get("pd_score");
				 * szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue,
				 * client_id, entityType, dimensionID, dimensionType, toolID,
				 * parentScoreCardType, childScoreCardType, label, benchmark, currentTS,
				 * currentTS); System.out.println("OPDS generation result::" + szRes); if (szRes
				 * != 1) { System.out.println("OPDS generation failed"); return false; }
				 */

				// OPDPS generation
				// =====================================================================================
				metricName = "OPDPS";
				metricValue = (szJObj1.getDouble("pd_score") / szJObj1.getDouble("pd_max_score")) * 100;

				primDimBM = DBUtility.getDimensionBenchmark(toolID);
				benchmark = 0;
				for (int j = 0; j < primDimBM.length(); j++) {
					szJObj1 = (JSONObject) primDimBM.get(j);
					benchmark = szJObj1.getFloat("benchmark");
				}
				szRes = DBUtility.addScoreCard(toolID, projectID, metricName, metricValue, client_id, entityType,
						dimensionID, dimensionType, toolID, parentScoreCardType, childScoreCardType, label, benchmark,
						currentTS, currentTS);
				System.out.println("OPDPS generation result::" + szRes);
				if (szRes != 1) {
					System.out.println("OPDPS generation failed");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e);
			e.printStackTrace();
			throw e;
		} finally {
			orgPDScore = null;
			orgJSON = null;
			szJObj1 = null;
			currentTS = null;
			primDimBM = null;
		}
	}
}
