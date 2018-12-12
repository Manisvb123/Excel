package com.merit.tlgscg.scorecard.tools;

import com.merit.tlgscg.scorecard.organization.OrganizationElementScoreCard;
import com.merit.tlgscg.scorecard.organization.OrganizationPrimaryDimensionScoreCard;
import com.merit.tlgscg.scorecard.team.TeamElementScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementMetricScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementScoreCard;
import com.merit.tlgscg.scorecard.user.tools.AMUserPrimaryDimensionScoreCard;

/**
 * This class implements a method to generate all scorecards for AM
 * @author rekha
 *
 */
public class AMScoreCardGenerator {
	public static boolean generateScoreCards(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		boolean scoreCardGenResult;
		System.out.println("AM ScoreCardGenerator....................");

		try {
			System.out.println("TLG AM Scorecard Generation started");
			scoreCardGenResult = UserElementMetricScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("AM UserElementMetricScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = UserElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID, userID);
			System.out.println("AM UserElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = AMUserPrimaryDimensionScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("AM UserPrimaryDimensionScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = TeamElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID);
			System.out.println("AM TeamElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("AM OrganizationElementScoreCard generated::" + scoreCardGenResult);
			
			scoreCardGenResult = OrganizationPrimaryDimensionScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("AM OrganizationPrimaryDimensionScoreCard generated::" + scoreCardGenResult);

			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
