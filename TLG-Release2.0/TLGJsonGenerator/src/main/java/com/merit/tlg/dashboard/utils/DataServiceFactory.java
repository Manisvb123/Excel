package com.merit.tlg.dashboard.utils;

import com.merit.tlg.dashboard.persistance.common.DaoFactory;
import com.merit.tlg.dashboard.persistance.common.ToolsDaoFactory;

public class DataServiceFactory {

	private static DaoFactory daoFactoryInstance = null;

	public static DaoFactory getDaoFactoryInstance() {

		if (daoFactoryInstance == null) {
			daoFactoryInstance = new ToolsDaoFactory();
		}

		return daoFactoryInstance;
	}
}
