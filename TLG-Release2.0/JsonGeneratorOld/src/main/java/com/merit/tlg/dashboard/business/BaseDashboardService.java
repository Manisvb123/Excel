package com.merit.tlg.dashboard.business;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DashboardDTO;
import com.merit.tlg.dashboard.persistance.DashboardDao;
import com.merit.tlg.dashboard.utils.CommonUtils;
import com.merit.tlg.dashboard.utils.DataServiceFactory;
import com.merit.tlg.dashboard.utils.JsonUtil;

/**
 * This is the base class extended by all the Json generating services
 *
 */
public class BaseDashboardService {
	
	final static Logger logger = Logger.getLogger(BaseDashboardService.class);
	
	/**
	 * This method retrieves the dashboard url for the required dashbaord, 
	 * generates the Json File, Saves the file in the specified location and updates the generated date in the Dashboard table
	 * 
	 * @param jsonBean: The bean that needs to be converted to JSon
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param dashboardId: The Dashboard Id.
	 * 
	 * @return  -- A boolean flag indicating if the file generation was successful
	 *
	 */
	protected boolean writeToFileAndUpdateDashboard(Object jsonBean, String orgId, String toolId, String projectId,
			String dashboardId) {

		boolean isWriteSucess = false;
		boolean isUpdateSuccess = false;

		logger.debug("writeToFileAndUpdateDashboard Input : dashboardId " + dashboardId);
		
		DashboardDao dashboardDao = DataServiceFactory.getDaoFactoryInstance().getDashboardDao();
		DashboardDTO dashboardDto = dashboardDao.fetchDashboardDetails(orgId, toolId, projectId, dashboardId);
		logger.debug("writeToFileAndUpdateDashboard: Fetched Dashboard Details " + dashboardDto);
		
		if (dashboardDto != null && !CommonUtils.isEmpty(dashboardDto.getDashboardJsonUrl())) {
			isWriteSucess = JsonUtil.writeToJsonFile(jsonBean, dashboardDto.getDashboardJsonUrl());
		} else {
			logger.error("No Dashboard URL Found for " + dashboardId);
		}

		if (isWriteSucess) {
			dashboardDto = new DashboardDTO();
			dashboardDto.setOrgdId(orgId);
			dashboardDto.setToolId(toolId);
			dashboardDto.setProjectId(projectId);
			dashboardDto.setDashboardId(dashboardId);
			dashboardDto.setGenerationDate(CommonUtils.getCurrentTimeStamp());
			isUpdateSuccess = dashboardDao.UpdateDashboardGenerationTime(dashboardDto);
			if (!isUpdateSuccess) {
				logger.error("Update not successful for " + dashboardId);
			}
		}

		return isUpdateSuccess;
	}
	
	
}
