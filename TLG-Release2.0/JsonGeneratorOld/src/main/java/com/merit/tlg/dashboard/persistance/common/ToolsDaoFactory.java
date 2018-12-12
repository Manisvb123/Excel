package com.merit.tlg.dashboard.persistance.common;

import com.merit.tlg.dashboard.persistance.DashboardDao;
import com.merit.tlg.dashboard.persistance.QuestionDao;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;

public class ToolsDaoFactory extends DaoFactory {

	/**
	 * Default Constructor
	 */
	public ToolsDaoFactory() {
	}

	/**
	 * Gets the ScoreCardDao interface
	 * 
	 * @return ScoreCardDao
	 */
	public ScoreCardDao getScoreCardDao() {
		return (ScoreCardDao) getDAOImplementation(ScoreCardDao.class);
	}
	
	/**
	 * Gets the DashboardDao interface
	 * 
	 * @return DashboardDao
	 */
	public DashboardDao getDashboardDao() {
		return (DashboardDao) getDAOImplementation(DashboardDao.class);
	}

	/**
	 * Gets the QuestionDao interface
	 * 
	 * @return QuestionDao
	 */
	public QuestionDao getQuestionDao() {
		return (QuestionDao) getDAOImplementation(QuestionDao.class);
	}
}
