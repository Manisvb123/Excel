package com.merit.tlgscg.scorecard.tools;

import com.merit.tlgscg.scorecard.organization.OrganizationElementScoreCard;
import com.merit.tlgscg.scorecard.organization.OrganizationPrimaryDimensionScoreCard;
import com.merit.tlgscg.scorecard.team.TeamElementScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementMetricScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementScoreCard;

/**
 * This class implements a method to generate all scorecards for SD
 * @author rekha
 *
 */
public class SDScoreCardGenerator {
	public static boolean generateScoreCards(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		boolean scoreCardGenResult;
		System.out.println("SD ScoreCardGenerator....................");

		try {
			System.out.println("TLG SD Scorecard Generation started");
			scoreCardGenResult = UserElementMetricScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("SD UserElementMetricScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = UserElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID, userID);
			System.out.println("SD UserElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = TeamElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID);
			System.out.println("SD TeamElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("SD OrganizationElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationPrimaryDimensionScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("SD OrganizationPrimaryDimensionScoreCard generated::" + scoreCardGenResult);

			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
