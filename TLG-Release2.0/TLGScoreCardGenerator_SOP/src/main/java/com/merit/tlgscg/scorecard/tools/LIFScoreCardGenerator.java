package com.merit.tlgscg.scorecard.tools;

import com.merit.tlgscg.scorecard.organization.OrganizationElementScoreCard;
import com.merit.tlgscg.scorecard.organization.OrganizationPrimaryDimensionScoreCard;
import com.merit.tlgscg.scorecard.team.TeamElementScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementMetricScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementScoreCard;

/**
 * This class implements a method to generate all scorecards for LIF
 * @author rekha
 *
 */
public class LIFScoreCardGenerator {
	public static boolean generateScoreCards(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		boolean scoreCardGenResult;
		System.out.println("LIF ScoreCardGenerator....................");

		try {
			System.out.println("TLG LIF Scorecard Generation started");
			scoreCardGenResult = UserElementMetricScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("LIF UserElementMetricScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = UserElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID, userID);
			System.out.println("LIF UserElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = TeamElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID);
			System.out.println("LIF TeamElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("LIF OrganizationElementScoreCard generated::" + scoreCardGenResult);
			
			scoreCardGenResult = OrganizationPrimaryDimensionScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("LIF OrganizationPrimaryDimensionScoreCard generated::" + scoreCardGenResult);

			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
