package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.MetricSearchDTO;
import com.merit.tlg.dashboard.dto.QuestionDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.dto.TeamDetailsDTO;
import com.merit.tlg.dashboard.dto.UserDetailsDTO;
import com.merit.tlg.dashboard.model.UserTeamData;
import com.merit.tlg.dashboard.model.TeamData;
import com.merit.tlg.dashboard.model.UserData;
import com.merit.tlg.dashboard.persistance.QuestionDao;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.CommonUtils;
import com.merit.tlg.dashboard.utils.DataServiceFactory;

/**
 * This Class is a common class used by other classes to generate the JsonFiles required to generate the Userteam Chart
 * 
 */
public class UserTeamDBDataService extends BaseDashboardService {

	final static Logger logger = Logger.getLogger(UserTeamDBDataService.class);

	
	/**
	 * Method to generate the UserTeam bean for all the questions required for the UserTeam Chart.
	 * 
	 * @param title: The Title for the chart.
	 * @param dbId: The Dashboard ID.
	 * @param orgId: The Organization Id.
	 * @param toolId: The Tool Id.
	 * @param projectId: The Project Id.
	 * @return ArrayList<UserTeamData> -- The List of UserData with populated dynamic and static data.
	 *
	 */
	public ArrayList<UserTeamData> generateUserTeamDataAllQuestions(String title, String dbId, String orgId, 
			String toolId, String projectId) {

		ArrayList<UserTeamData> userTeamList = new ArrayList<UserTeamData>();
		QuestionDao questionDao = DataServiceFactory.getDaoFactoryInstance().getQuestionDao();
		ArrayList<QuestionDTO> questions = questionDao.fetchAllQuestions(orgId, toolId, projectId);

		for (Iterator<QuestionDTO> iterator = questions.iterator(); iterator.hasNext();) {
			QuestionDTO questionData = (QuestionDTO) iterator.next();
			logger.debug("Generate Json for " + questionData.getQuestionId());
			UserTeamData userTeamData;
			
			userTeamData = generateUserTeamData(title, dbId, orgId, toolId, projectId,
					questionData.getQuestionId());
			
				
			userTeamList.add(userTeamData);
			if (userTeamData == null) {
				logger.warn("The Json is not generated for " + questionData.getQuestionId());
			}
		}
		
		return userTeamList;
	}

	/**
	 * Method to generate the UserTeam bean for the question required for the UserTeam Chart.
	 * 
	 * @param title: The Title for the chart.
	 * @param dbId: The Dashboard ID.
	 * @param orgId: The Organization Id.
	 * @param toolId: The Tool Id.
	 * @param projectId: The Project Id.
	 * @param questionId: The Question ID.
	 * @return UserTeamData -- The UserTeamData with populated dynamic and static data.
	 *
	 */
	public UserTeamData generateUserTeamData(String title, String dbId, String orgId, String toolId,
			String projectId, String questionId) {

		ArrayList<ScoreCardDTO> scTeamData = null;
		ArrayList<ScoreCardDTO> scUserData = null;
		HashMap<String, UserData> teamUsers = null;
		TeamData teamData = null;
		UserData userData = null;
		String qDesc = null;
		String teamId = null;
		UserTeamData userTeamData = new UserTeamData();
		ScoreCardDao scoreCardDao = null;
		ScoreCardSearchDTO searchData = null;
		
		searchData = populateSearchDataForTeam(orgId, toolId, projectId, questionId);
		scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		scTeamData = scoreCardDao.getScoreCardDetails(searchData);
		if (scTeamData.size() == 0) {
			logger.warn("No Team Data for the given question " + questionId);
			return userTeamData;
		}

		searchData = populateSearchDataForUser(orgId, toolId, projectId, questionId);
		scUserData = scoreCardDao.getScoreCardDetails(searchData);
		if (scTeamData.size() == 0) {
			logger.warn("No User Data for the given question " + questionId);
		}

		HashMap<String, TeamData> teamList = new HashMap<String, TeamData>();
		for (Iterator iterator = scTeamData.iterator(); iterator.hasNext();) {
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
					for (Iterator userIter = scUserData.iterator(); userIter.hasNext();) {
						ScoreCardDTO dbUserSCData = (ScoreCardDTO) userIter.next();
						UserDetailsDTO userDetails = dbUserSCData.getUserData();
						logger.debug("userDetails.getUserTeamId() " + userDetails.getUserTeamId() + "team Id" + teamId);
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
		
		userTeamData.setTitle(title);
		userTeamData.setType("Multipl Bar Chart");
		userTeamData.setDescription("Will show how different users and teams have scored for the question");
		userTeamData.setQuestionId(questionId);
		userTeamData.setQuestionDesc(qDesc);
		Collection<TeamData> teams = teamList.values();        
		ArrayList<TeamData> tList = new ArrayList<TeamData>(teams);
		userTeamData.setTeamData(tList);

		String dashboardId = dbId + "-" + questionId;
		writeToFileAndUpdateDashboard(userTeamData, orgId, toolId, projectId, dashboardId);
		
		return userTeamData;
	}

	/**
	 * Method to create the Search Data object for fetching the team metrics
	 * 
	 * @param orgId: The Organization Id.
	 * @param toolId: The Tool Id.
	 * @param projectId: The Project Id.
	 * @param questionId: The Question Id.
	 * @return ScoreCardSearchDTO -- The Search Data.
	 *
	 */
	private ScoreCardSearchDTO populateSearchDataForTeam(String orgId, String toolId, String projectId,
			String questionId) {

		MetricSearchDTO mData = null;
		ArrayList<MetricSearchDTO> mList = new ArrayList<MetricSearchDTO>();
		ScoreCardSearchDTO srchData = new ScoreCardSearchDTO();

		srchData.setToolId(toolId);
		srchData.setOrgId(orgId);
		srchData.setProjectId(projectId);
		srchData.setQuesDetailsReq(true);
		srchData.setUserDetailsReq(true);
		mData = new MetricSearchDTO();
		mData.setEntityType(CommonConstants.TEAM);
		mData.setEntityIdReq(false);
		mData.setDimensionType(CommonConstants.QUESTION);
		mData.setDimIdReq(true);
		mData.setDimensionId(questionId);
		mData.setMetricName(CommonConstants.TEPS);
		mList.add(mData);
		srchData.setMetricList(mList);
		return srchData;
	}

	/**
	 * Method to create the Search Data object for fetching the user metrics
	 * 
	 * @param orgId: The Organization Id.
	 * @param toolId: The Tool Id.
	 * @param projectId: The Project Id.
	 * @param questionId: The Question Id.
	 * @return ScoreCardSearchDTO -- The Search Data.
	 *
	 */
	private ScoreCardSearchDTO populateSearchDataForUser(String orgId, String toolId, String projectId,
			String questionId) {

		MetricSearchDTO mData = null;
		ArrayList<MetricSearchDTO> mList = new ArrayList<MetricSearchDTO>();
		ScoreCardSearchDTO srchData = new ScoreCardSearchDTO();

		srchData.setToolId(toolId);
		srchData.setOrgId(orgId);
		srchData.setProjectId(projectId);
		srchData.setQuesDetailsReq(true);
		srchData.setUserDetailsReq(true);
		mData = new MetricSearchDTO();
		mData.setEntityType(CommonConstants.USER);
		mData.setEntityIdReq(false);
		mData.setDimensionType(CommonConstants.QUESTION);
		mData.setDimIdReq(true);
		mData.setDimensionId(questionId);
		mData.setMetricName(CommonConstants.EPS);
		mList.add(mData);
		srchData.setMetricList(mList);
		return srchData;
	}

}
