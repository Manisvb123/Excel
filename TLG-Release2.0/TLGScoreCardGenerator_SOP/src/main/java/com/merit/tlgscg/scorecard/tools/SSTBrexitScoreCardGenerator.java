package com.merit.tlgscg.scorecard.tools;

import com.merit.tlgscg.scorecard.organization.tools.SSTBrexitOrganizationElementScoreCard;
import com.merit.tlgscg.scorecard.team.TeamElementScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementMetricScoreCard;
import com.merit.tlgscg.scorecard.user.tools.SSTBrexitUserElementScoreCard;
import com.merit.tlgscg.scorecard.user.tools.SSTBrexitUserPrimaryDimensionScoreCard;

/**
 * This class implements a method to generate all scorecards for Brexit
 * @author rekha
 *
 */
public class SSTBrexitScoreCardGenerator {
	public static boolean generateScoreCards(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		boolean scoreCardGenResult;
		System.out.println("SSTBrexit ScoreCardGenerator....................");

		try {
			System.out.println("TLG SSTBrexit Scorecard Generation started");
			scoreCardGenResult = UserElementMetricScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("SSTBrexit UserElementMetricScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = SSTBrexitUserElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("SSTBrexit UserElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = SSTBrexitUserPrimaryDimensionScoreCard.generateScoreCard(projectID, toolID, questionnaireID,
					teamID, userID);
			System.out.println("SSTBrexit UserPrimaryDimensionScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = TeamElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID);
			System.out.println("SSTBrexit TeamElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = SSTBrexitOrganizationElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("SSTBrexit OrganizationElementScoreCard generated::" + scoreCardGenResult);

			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
