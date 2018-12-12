package com.merit.tlg.dashboard.persistance.common;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.business.SSTDashboardService;
import com.merit.tlg.dashboard.persistance.DashboardDao;
import com.merit.tlg.dashboard.persistance.QuestionDao;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;

public abstract class DaoFactory {
	final static Logger logger = Logger.getLogger(DaoFactory.class);
	/**
	 * Default Constructor
	 */
	protected DaoFactory() {
	}

	/**
	 * Instantiates the appropriate Implementation class for the given interface.
	 * 
	 * @return appropriate implementation class.
	 */
	protected BaseDaoImpl getDAOImplementation(Class interfaceClass) {
		BaseDaoImpl daoImpl = null;

		try {
			//String strInterName = interfaceClass.getName();
			//String implClassName = strInterName.replaceFirst("interfaces", "impl") + "Impl";
			String implClassName = interfaceClass.getName() + "Impl";
			logger.debug(" Impl Class " + implClassName);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			daoImpl = (BaseDaoImpl) classLoader.loadClass(implClassName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return daoImpl;
	}

	/**
	 * Gets the ScoreCardDao Interface
	 * 
	 * @return ScoreCardDao Object
	 */
	public abstract ScoreCardDao getScoreCardDao();
	
	/**
	 * Gets the DashboardDao Interface
	 * 
	 * @return DashBoardDao Object
	 */
	public abstract DashboardDao getDashboardDao();
	
	/**
	 * Gets the QuestionDao interface
	 * 
	 * @return QuestionDao Object
	 */
	public abstract QuestionDao getQuestionDao();


}
