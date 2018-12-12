package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DashboardDTO;
import com.merit.tlg.dashboard.dto.QuestionDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.dto.TeamDetailsDTO;
import com.merit.tlg.dashboard.dto.UserDetailsDTO;
import com.merit.tlg.dashboard.model.TeamData;
import com.merit.tlg.dashboard.model.UserData;
import com.merit.tlg.dashboard.model.UserTeamData;
import com.merit.tlg.dashboard.persistance.DashboardDao;
import com.merit.tlg.dashboard.persistance.QuestionDao;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.CommonUtils;
import com.merit.tlg.dashboard.utils.DataServiceFactory;
import com.merit.tlg.dashboard.utils.JsonGenException;
import com.merit.tlg.dashboard.utils.ToolsBeanFactory;

/**
 * This Class is a common class used by other classes to generate the JsonFiles
 * required to generate the Userteam Chart
 * 
 */
public class UserTeamDBDataService extends BaseDBGeneratorService {

	final static Logger logger = Logger.getLogger(UserTeamDBDataService.class);

	/**
	 * Method to generate the UserTeam bean for the question required for the
	 * UserTeam Chart.
	 * 
	 * @param orgId: The Organization Id.
	 * @param toolId: The Tool Id.
	 * @param projectId: The Project Id.
	 * @param dbId: The Dashboard ID.
	 * @param questionId: The Question ID.
	 * 
	 * @return UserTeamData -- The UserTeamData with populated dynamic and static
	 *         data.
	 *
	 */
	public UserTeamData generateUserTeamData(String orgId, String toolId, String projectId, String dbId,
			String questionId) throws JsonGenException {

		ArrayList<ScoreCardDTO> scTeamData = null;
		ArrayList<ScoreCardDTO> scUserData = null;
		HashMap<String, UserData> teamUsers = null;
		TeamData teamData = null;
		UserData userData = null;
		String qDesc = null;
		String teamId = null;
		UserTeamData userTeamData = null;
		ScoreCardDao scoreCardDao = null;
		ScoreCardSearchDTO searchData = null;

		searchData = populateSearchData(orgId, toolId, projectId, CommonConstants.SEARCH_USER_TEAM_TEAM, questionId,
				null, null);
		logger.debug("Populated Search Data for UserTeam data " + searchData);
		// searchData = populateSearchDataForTeam(orgId, toolId, projectId, questionId);
		scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		scTeamData = scoreCardDao.getScoreCardDetails(searchData);
		if (scTeamData.size() == 0) {
			logger.warn("No Team Data for the given question " + questionId);
			return userTeamData;
		}

		// srchData = populateSearchDataForUser(orgId, toolId, projectId, questionId);
		searchData = populateSearchData(orgId, toolId, projectId, CommonConstants.SEARCH_USER_TEAM_USER, questionId,
				null, null);
		scUserData = scoreCardDao.getScoreCardDetails(searchData);
		if (scTeamData.size() == 0) {
			logger.warn("No User Data for the given question " + questionId);
		}

		HashMap<String, TeamData> teamList = new HashMap<String, TeamData>();
		for (Iterator<ScoreCardDTO> iterator = scTeamData.iterator(); iterator.hasNext();) {
			ScoreCardDTO dbTeamSCData = (ScoreCardDTO) iterator.next();
			qDesc = dbTeamSCData.getQuestionData().getQuestionDesc();
			TeamDetailsDTO dbTeamData = dbTeamSCData.getTeamData();

			if (!CommonUtils.isNull(dbTeamData)) {
				teamId = dbTeamData.getTeamId();
				if (!teamList.containsKey(teamId.trim())) {
					teamData = new TeamData();
					teamData.setTeamId(teamId);
					teamData.setTeamScore(dbTeamSCData.getMetricValue());
					teamUsers = new HashMap<String, UserData>();
					for (Iterator<ScoreCardDTO> userIter = scUserData.iterator(); userIter.hasNext();) {
						ScoreCardDTO dbUserSCData = (ScoreCardDTO) userIter.next();
						UserDetailsDTO userDetails = dbUserSCData.getUserData();
						if (userDetails != null && userDetails.getUserTeamId().contains(teamId)) {
							if (!teamUsers.containsKey(userDetails.getUserId())) {
								userData = new UserData();
								userData.setUserId(userDetails.getUserId());
								userData.setUserName(userDetails.getUserFirstName());
								userData.setMetricName(dbUserSCData.getMetricName());
								userData.setMetricValue(dbUserSCData.getMetricValue());
								teamUsers.put(userDetails.getUserId(), userData);
							}
						}
					}
					if (teamUsers.size() > 0) {
						Collection<UserData> values = teamUsers.values();
						ArrayList<UserData> tUsers = new ArrayList<UserData>(values);
						teamData.setUserData(tUsers);
					}
					teamList.put(teamId, teamData);
				}
			}
		}

		String dashboardId = dbId + "-" + questionId;
		DashboardDao dashboardDao = DataServiceFactory.getDaoFactoryInstance().getDashboardDao();
		DashboardDTO dashboardDto = dashboardDao.fetchDashboardDetails(orgId, toolId, projectId, dashboardId);

		userTeamData = (UserTeamData) ToolsBeanFactory.getDashboardBean(UserTeamData.class,
				dashboardDto.getDashboardJsonTemplate());
		userTeamData.setQuestionId(questionId);
		userTeamData.setQuestionDesc(qDesc);
		Collection<TeamData> teams = teamList.values();
		ArrayList<TeamData> tList = new ArrayList<TeamData>(teams);
		userTeamData.setTeamData(tList);

		writeToFileAndUpdateDashboard(userTeamData, orgId, toolId, projectId,
				dashboardId, dashboardDto.getDashboardJsonUrl());

		return userTeamData;
	}

	/**
	 * Method to generate the UserTeam bean for all the questions required for the
	 * UserTeam Chart.
	 * 
	 * @param orgId: The Organization Id.
	 * @param toolId: The Tool Id.
	 * @param projectId: The Project Id.
	 * @param dbId: The Dashboard ID.
	 * 
	 * @return ArrayList<UserTeamData> -- The List of UserData with populated
	 *         dynamic and static data.
	 *
	 */
	public ArrayList<UserTeamData> generateUserTeamDataAllQuestions(String orgId, String toolId, String projectId,
			String dbId) throws JsonGenException {

		ArrayList<UserTeamData> userTeamList = new ArrayList<UserTeamData>();
		QuestionDao questionDao = DataServiceFactory.getDaoFactoryInstance().getQuestionDao();
		ArrayList<QuestionDTO> questions = questionDao.fetchAllQuestions(orgId, toolId, projectId);

		for (Iterator<QuestionDTO> iterator = questions.iterator(); iterator.hasNext();) {
			QuestionDTO questionData = (QuestionDTO) iterator.next();
			UserTeamData userTeamData = generateUserTeamData(orgId, toolId, projectId, dbId,
					questionData.getQuestionId());
			logger.debug(
					"UserTeamData for Question " + questionData.getQuestionId() + " , userTeamData " + userTeamData);
			userTeamList.add(userTeamData);
			if (userTeamData == null) {
				logger.warn("The Json is not generated for " + questionData.getQuestionId());
			}
		}

		return userTeamList;
	}

}
