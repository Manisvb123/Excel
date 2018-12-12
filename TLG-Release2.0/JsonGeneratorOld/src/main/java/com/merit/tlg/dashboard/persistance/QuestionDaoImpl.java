package com.merit.tlg.dashboard.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DimensionDTO;
import com.merit.tlg.dashboard.dto.QuestionDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.persistance.common.BaseDaoImpl;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.CommonUtils;

public class QuestionDaoImpl extends BaseDaoImpl implements QuestionDao {
	final static Logger logger = Logger.getLogger(ScoreCardDaoImpl.class);
	
	/**
	 * Fetches all the questions and its details for the given parameters from the Questionbank table in the database
	 *
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id
	 * 
	 * @return ArrayList<QuestionDTO> -- A List with the question details.
	 *
	 */
	public ArrayList<QuestionDTO> fetchAllQuestions(String orgId, String toolId, String projectId) {
		Connection connection = null;
		PreparedStatement psQuestionData = null;
		ResultSet rsQuestionData = null;

		QuestionDTO questionData = null;
		ArrayList<QuestionDTO> questions = null;

		try {

			StringBuilder sqlQuery = new StringBuilder("select distinct question_id from tabQuestionBank ")
							.append("where tool_id = '").append(toolId).append("' order by question_id");
			
			logger.debug("QuestionDao: SQL Query in fetchAllQuestions " + sqlQuery);

			connection = getConnection();
			psQuestionData = connection.prepareStatement(sqlQuery.toString());
			rsQuestionData = psQuestionData.executeQuery();
			
			questions = new ArrayList<QuestionDTO>();
			while (rsQuestionData.next()) {
				questionData = new QuestionDTO();
				questionData.setQuestionId(rsQuestionData.getString("question_id"));
				//questionData.setQuestionContext(rsQuestionData.getString("question_context"));
				//questionData.setQuestionDesc(rsQuestionData.getString("question_description"));
				questions.add(questionData);
			}
			logger.debug("QuestionDao: fetchAllQuestions size " + questions.size());
			
		} catch (SQLException exception) {
			logger.error("QuestionDao: SQLException " + exception);
			exception.printStackTrace();
		} finally {
			closeAll(rsQuestionData, psQuestionData, connection);
		}

		return questions;
	}

}
