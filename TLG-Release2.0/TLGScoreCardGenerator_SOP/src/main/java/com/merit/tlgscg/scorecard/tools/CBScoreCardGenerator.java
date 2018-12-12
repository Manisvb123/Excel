package com.merit.tlgscg.scorecard.tools;

import com.merit.tlgscg.scorecard.organization.OrganizationElementScoreCard;
import com.merit.tlgscg.scorecard.organization.OrganizationPrimaryDimensionScoreCard;
import com.merit.tlgscg.scorecard.team.TeamElementScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementMetricScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementScoreCard;

/**
 * This class implements a method to generate all scorecards for CB
 * @author rekha
 *
 */
public class CBScoreCardGenerator {
	public static boolean generateScoreCards(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception{
		boolean scoreCardGenResult;
		System.out.println("CB ScoreCardGenerator....................");

		try {
			System.out.println("TLG CB Scorecard Generation started");
			scoreCardGenResult = UserElementMetricScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("CB UserElementMetricScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = UserElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID, userID);
			System.out.println("CB UserElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = TeamElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID);
			System.out.println("CB TeamElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("CB OrganizationElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationPrimaryDimensionScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("CB OrganizationPrimaryDimensionScoreCard generated::" + scoreCardGenResult);

			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
