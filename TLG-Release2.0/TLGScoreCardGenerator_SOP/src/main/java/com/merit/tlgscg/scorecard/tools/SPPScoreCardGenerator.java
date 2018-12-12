package com.merit.tlgscg.scorecard.tools;

import com.merit.tlgscg.scorecard.organization.OrganizationElementScoreCard;
import com.merit.tlgscg.scorecard.organization.OrganizationSecondaryDimensionScoreCard;
import com.merit.tlgscg.scorecard.organization.OrganizationToolScoreCard;
import com.merit.tlgscg.scorecard.team.TeamElementScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementMetricScoreCard;
import com.merit.tlgscg.scorecard.user.tools.SPPUserElementScoreCard;
import com.merit.tlgscg.scorecard.user.tools.SPPUserPrimaryDimensionScoreCard;

/**
 * This class implements a method to generate all scorecards for SPP
 * @author rekha
 *
 */
public class SPPScoreCardGenerator {
	public static boolean generateScoreCards(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		boolean scoreCardGenResult;
		System.out.println("SPP ScoreCardGenerator....................");

		try {
			System.out.println("TLG SPP Scorecard Generation started");
			scoreCardGenResult = UserElementMetricScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("SPP UserElementMetricScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = SPPUserElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("SPP UserElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = SPPUserPrimaryDimensionScoreCard.generateScoreCard(projectID, toolID, questionnaireID,
					teamID, userID);
			System.out.println("SPP UserPrimaryDimensionScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = TeamElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID);
			System.out.println("SPP TeamElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("SPP OrganizationElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationSecondaryDimensionScoreCard.generateScoreCard(projectID, toolID,
					questionnaireID);
			System.out.println("SPP OrganizationSecondaryDimensionScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationToolScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("SPP OrganizationToolScoreCard generated::" + scoreCardGenResult);
			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
