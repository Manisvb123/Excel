package com.merit.tlg.dashboard.persistance;

import java.util.ArrayList;

import com.merit.tlg.dashboard.dto.QuestionDTO;

/**
 * This interface is used for performing the CRUD operations on the QuestionBank Table in the database
 * 
 */
public interface QuestionDao {

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
	public ArrayList<QuestionDTO> fetchAllQuestions( String orgId, String toolId, String projectId);
}
