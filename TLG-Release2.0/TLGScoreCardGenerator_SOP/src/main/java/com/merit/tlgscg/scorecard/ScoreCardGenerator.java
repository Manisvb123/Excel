package com.merit.tlgscg.scorecard;

import com.merit.tlgscg.scorecard.tools.AMScoreCardGenerator;
import com.merit.tlgscg.scorecard.tools.BEFScoreCardGenerator;
import com.merit.tlgscg.scorecard.tools.CBScoreCardGenerator;
import com.merit.tlgscg.scorecard.tools.LIFScoreCardGenerator;
import com.merit.tlgscg.scorecard.tools.RGScoreCardGenerator;
import com.merit.tlgscg.scorecard.tools.SDScoreCardGenerator;
import com.merit.tlgscg.scorecard.tools.SPPScoreCardGenerator;
import com.merit.tlgscg.scorecard.tools.SSTBrexitScoreCardGenerator;
import com.merit.tlgscg.scorecard.tools.SSTScoreCardGenerator;
import static com.merit.tlgscg.utility.TLGConstants.*;

public class ScoreCardGenerator {
//	static {
//		System.setProperty("log4j.configurationFile", "resources/log4j.properties");
//	}
//
//	static Logger szLogger = LogManager.getLogger(ScoreCardGenerator.class.getName());

	/**
	 * This method generates all the score cards for a given userID and teamID
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public static boolean generateScoreCards(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		boolean scoreCardGenResult = false;
		System.out.println("ScoreCardGenerator....................");

		try {
			System.out.println("TLG Scorecard Generation started");
			if (toolID.equals(SST_ID)) {
				scoreCardGenResult = SSTScoreCardGenerator.generateScoreCards(projectID, toolID, questionnaireID, teamID,
						userID);
			} else if (toolID.equals(SSTBrexit_ID)) {
				scoreCardGenResult = SSTBrexitScoreCardGenerator.generateScoreCards(projectID, toolID, questionnaireID,
						teamID, userID);
			} else if (toolID.equals(LIF_ID)) {
				scoreCardGenResult = LIFScoreCardGenerator.generateScoreCards(projectID, toolID, questionnaireID, teamID,
						userID);
			} else if (toolID.equals(AM_ID)) {
				scoreCardGenResult = AMScoreCardGenerator.generateScoreCards(projectID, toolID, questionnaireID, teamID,
						userID);
			} else if (toolID.equals(SPP_ID)) {
				scoreCardGenResult = SPPScoreCardGenerator.generateScoreCards(projectID, toolID, questionnaireID, teamID,
						userID);
			} else if (toolID.equals(CB_ID)) {
				scoreCardGenResult = CBScoreCardGenerator.generateScoreCards(projectID, toolID, questionnaireID, teamID,
						userID);
			} else if (toolID.equals(SD_ID)) {
				scoreCardGenResult = SDScoreCardGenerator.generateScoreCards(projectID, toolID, questionnaireID, teamID,
						userID);
			} else if (toolID.equals(RG_ID)) {
				scoreCardGenResult = RGScoreCardGenerator.generateScoreCards(projectID, toolID, questionnaireID, teamID,
						userID);
			} else if (toolID.equals(BEF_ID)) {
				scoreCardGenResult = BEFScoreCardGenerator.generateScoreCards(projectID, toolID, questionnaireID, teamID,
						userID);
			}
			return scoreCardGenResult;
		} catch (Exception e) {
			System.out.println("Error::" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public static void main(String args[]) {
		try {
			System.out.println("response::" + generateScoreCards("PR-001", "SST-001", "TMP-001", "TM-001", "3"));
			// SSTDashboardServiceWrapper szSSTDBS = new SSTDashboardServiceWrapper();
			// szSSTDBS.generateAllJsonsForSST( "ORG-001", "SST-001", "PR-001");
		} catch (Exception E) {
			E.printStackTrace();
		}

	}

}
