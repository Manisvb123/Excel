package com.merit.tlgscg.scorecard.tools;

import com.merit.tlgscg.scorecard.organization.OrganizationElementScoreCard;
import com.merit.tlgscg.scorecard.organization.OrganizationPrimaryDimensionScoreCard;
import com.merit.tlgscg.scorecard.organization.OrganizationToolScoreCard;
import com.merit.tlgscg.scorecard.team.TeamElementScoreCard;
import com.merit.tlgscg.scorecard.team.TeamPrimaryDimensionScoreCard;
import com.merit.tlgscg.scorecard.team.TeamToolScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementMetricScoreCard;
import com.merit.tlgscg.scorecard.user.UserElementScoreCard;
import com.merit.tlgscg.scorecard.user.UserPrimaryDimensionScoreCard;
import com.merit.tlgscg.scorecard.user.UserToolScoreCard;

/**
 * This class implements a method to generate all scorecards for SST
 * @author rekha
 *
 */
public class SSTScoreCardGenerator {
	public static boolean generateScoreCards(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		boolean scoreCardGenResult;
		System.out.println("SSTScoreCardGenerator....................");

		try {
			System.out.println("TLG SST Scorecard Generation started");
			scoreCardGenResult = UserElementMetricScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("SST UserElementMetricScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = UserElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID, userID);
			System.out.println("SST UserElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = UserPrimaryDimensionScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID,
					userID);
			System.out.println("SST UserPrimaryDimensionScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = UserToolScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID, userID);
			System.out.println("SST UserToolScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = TeamElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID);
			System.out.println("SST TeamElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = TeamPrimaryDimensionScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID);
			System.out.println("SST TeamPrimaryDimensionScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = TeamToolScoreCard.generateScoreCard(projectID, toolID, questionnaireID, teamID);
			System.out.println("SST TeamToolScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationElementScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("SST OrganizationElementScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationPrimaryDimensionScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("SST OrganizationPrimaryDimensionScoreCard generated::" + scoreCardGenResult);

			scoreCardGenResult = OrganizationToolScoreCard.generateScoreCard(projectID, toolID, questionnaireID);
			System.out.println("SST OrganizationToolScoreCard generated::" + scoreCardGenResult);
			return true;
		} catch (Exception e) {
			System.out.println("Error::" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
