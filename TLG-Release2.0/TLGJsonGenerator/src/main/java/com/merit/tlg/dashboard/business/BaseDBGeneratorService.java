package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DashboardDTO;
import com.merit.tlg.dashboard.dto.MetricSearchDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.model.MetricParamData;
import com.merit.tlg.dashboard.model.MetricSearchData;
import com.merit.tlg.dashboard.persistance.DashboardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.CommonUtils;
import com.merit.tlg.dashboard.utils.DataServiceFactory;
import com.merit.tlg.dashboard.utils.JaxbUtil;
import com.merit.tlg.dashboard.utils.JsonGenException;
import com.merit.tlg.dashboard.utils.JsonUtil;

/**
 * This is the base class extended by all the Generator services for generating
 * JSONs for different types of dashboards.
 *
 */
public class BaseDBGeneratorService {

	final static Logger logger = Logger.getLogger(BaseDBGeneratorService.class);

	/**
	 * This method retrieves the dashboard url for the required dashbaord, generates
	 * the Json File, Saves the file in the specified location and updates the
	 * generated date in the Dashboard table
	 * 
	 * @param jsonBean: The bean that needs to be converted to JSon
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param dashboardId: The Dashboard Id.
	 * @param dashboardUrl: The Dashboard URL where it needs to be saved.
	 * 
	 * @return -- A boolean flag indicating if the file generation was successful
	 *
	 */
	protected boolean writeToFileAndUpdateDashboard(Object jsonBean, String orgId, String toolId, String projectId,
			String dashboardId, String dashboardUrl) throws JsonGenException {

		boolean isWriteSucess = false;
		boolean isUpdateSuccess = false;
		DashboardDTO dashboardDto = null;
		
		logger.debug("writeToFileAndUpdateDashboard Input : dashboardId " );

		DashboardDao dashboardDao = DataServiceFactory.getDaoFactoryInstance().getDashboardDao();
		//DashboardDTO dashboardDto = dashboardDao.fetchDashboardDetails(orgId, toolId, projectId, dashboardId);
		//logger.debug("writeToFileAndUpdateDashboard: Fetched Dashboard Details " + dashboardDto);

		if (!CommonUtils.isEmpty(dashboardUrl)) {
			isWriteSucess = JsonUtil.writeToJsonFile(jsonBean, dashboardUrl);
		} else {
			logger.error("No Dashboard URL Found " + dashboardUrl);
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
		logger.debug("writeToFileAndUpdateDashboard: Updated Dashboard Details");
		return isUpdateSuccess;
	}

	/**
	 * This method populates the search parameters required for retrieving the
	 * appropriate metric data from the database.|
	 * 
	 * @param searchId: The Search Id for picking the search parameters from metricsearchdata.xml.
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * 
	 * @return ScoreCardSearchDTO -- Search Object.
	 *
	 */
	protected ScoreCardSearchDTO populateSearchData(String orgId, String toolId, String projectId, String searchId)
			throws JsonGenException {
		return populateSearchData(orgId, toolId, projectId, searchId, null, null, null);
	}

	/**
	 * This method populates the search parameters required for retrieving the
	 * appropriate metric data from the database.
	 * 
	 * @param searchId:  The Search Id for picking the search parameters from metricsearchdata.xml.
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param questionId: The question Id.
	 * @param dimensionId: The dimension Id.
	 * @param responseMetricId: The response metric Id.
	 * 
	 * @return ScoreCardSearchDTO -- Search Object.
	 *
	 */
	protected ScoreCardSearchDTO populateSearchData(String orgId, String toolId, String projectId, String searchId,
			String questionId, String dimensionId, String responseMetricId) throws JsonGenException {

		MetricSearchDTO mData = null;
		ArrayList<MetricSearchDTO> mList = new ArrayList<MetricSearchDTO>();
		ScoreCardSearchDTO srchData = new ScoreCardSearchDTO();

		srchData.setToolId(toolId);
		srchData.setOrgId(orgId);
		srchData.setProjectId(projectId);

		MetricSearchData msd = JaxbUtil.createSearchAttributeBean(searchId);
		logger.debug("Metric Search Data Population " + searchId + " , " + msd);
		ArrayList<MetricParamData> mpdList = msd.getMetricParams();
		for (Iterator<MetricParamData> iterator = mpdList.iterator(); iterator.hasNext();) {
			MetricParamData metricParamData = (MetricParamData) iterator.next();
			mData = new MetricSearchDTO();
			mData.setEntityType(metricParamData.getEntityType());
			mData.setEntityIdReq(metricParamData.isEntityIdReq());
			if (metricParamData.isEntityIdReq()) {
				if (CommonConstants.ORGANISATION.equals(metricParamData.getEntityType())) {
					mData.setEntityId(orgId);
				}
			}
			mData.setDimensionType(metricParamData.getDimType());
			mData.setDimIdReq(metricParamData.isDimIdReq());
			if (metricParamData.isDimIdReq()) {
				if (CommonConstants.TOOL.equals(metricParamData.getDimType())) {
					mData.setDimensionId(toolId);
				} else if (CommonConstants.QUESTION.equals(metricParamData.getDimType())) {
					mData.setDimensionId(questionId);
				} else if (CommonConstants.PD.equals(metricParamData.getDimType())
						|| CommonConstants.SD.equals(metricParamData.getDimType())) {
					mData.setDimensionId(dimensionId);
				} else if (CommonConstants.RESPONSE_METRIC.equals(metricParamData.getDimType())) {
					mData.setDimensionId(responseMetricId);
				}
			}
			mData.setMetricName(metricParamData.getMetricName());
			mList.add(mData);
		}
		srchData.setMetricList(mList);

		logger.debug("populateSearchDataForInsightDB: SearchData " + srchData);
		return srchData;
	}

}
