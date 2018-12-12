package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DashboardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.model.IndexData;
import com.merit.tlg.dashboard.model.IndexKPIData;
import com.merit.tlg.dashboard.persistance.DashboardDao;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.DataServiceFactory;
import com.merit.tlg.dashboard.utils.JsonGenException;
import com.merit.tlg.dashboard.utils.ToolsBeanFactory;

/**
 * This Class is a common class used by other classes to create the JsonFiles
 * required for generating the Index Chart
 * 
 */
public class IndexDBDataService extends BaseDBGeneratorService {

	final static Logger logger = Logger.getLogger(IndexDBDataService.class);

	/**
	 * This Method picks the static data for the specific tool from the json
	 * template, formulates the generic query for picking up the standard index
	 * metrics, retrieves the metric data (dynamic) from the Database and constructs
	 * the bean in the required json format, which will be converted to a json file
	 * and stored in the configured location.
	 * 
	 * @param orgId: The Organization Id.
	 * @param toolId: The Tool Id.
	 * @param projectId: The Project Id.
	 * @param dashBoardId: The Dashboard ID.
	 * @param searchId: The Search Id.
	 * 
	 * @return indexData -- The indexdata with populated dynamic and static data.
	 *
	 */
	public IndexData generateIndexData(String orgId, String toolId, String projectId, String dashBoardId,
			String searchId) throws JsonGenException {

		ArrayList<ScoreCardDTO> scData = null;
		ArrayList<IndexKPIData> kpiList = null;
		IndexKPIData kpiData = null;

		DashboardDao dashboardDao = DataServiceFactory.getDaoFactoryInstance().getDashboardDao();
		DashboardDTO dashboardDto = dashboardDao.fetchDashboardDetails(orgId, toolId, projectId, dashBoardId);
		logger.debug("Fetched Dashboard Data from DB  " + dashboardDto);

		IndexData indexData = (IndexData) ToolsBeanFactory.getDashboardBean(IndexData.class,
				dashboardDto.getDashboardJsonTemplate());
		logger.debug("Populated Static Data  " + indexData);

		ScoreCardSearchDTO searchdata = populateSearchData(orgId, toolId, projectId, searchId);
		logger.debug("Populated Search Data  " + searchdata);

		IndexKPIData indexStaticData = populateIndexRegionData();

		ScoreCardDao scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		scData = scoreCardDao.getScoreCardDetails(searchdata);

		logger.debug("generateIndexData: Index Data from DB " + scData.toString());
		kpiList = new ArrayList<>();
		for (Iterator<ScoreCardDTO> iterator = scData.iterator(); iterator.hasNext();) {
			ScoreCardDTO dbData = (ScoreCardDTO) iterator.next();
			kpiData = new IndexKPIData();
			if (CommonConstants.PD.equalsIgnoreCase(dbData.getDimensionType())
					|| CommonConstants.SD.equalsIgnoreCase(dbData.getDimensionType())) {
				kpiData.setKpiDisplayName(dbData.getDimensionData().getDimensionName() + " " + "Index");
			} else if (CommonConstants.RESPONSE_METRIC.equalsIgnoreCase(dbData.getDimensionType())) {
				kpiData.setKpiDisplayName(dbData.getResponseMetricData().getResponseMetricName() + " " + "Index");
			} else if (CommonConstants.TOOL.equalsIgnoreCase(dbData.getDimensionType())) {
				kpiData.setKpiDisplayName(dbData.getToolsData().getToolName() + " " + "Index");
			} else {
				kpiData.setKpiDisplayName(dbData.getMetricName());
			}
			kpiData.setKpiName(dbData.getMetricName());
			kpiData.setKpiValue(dbData.getMetricValue());
			kpiData.setKpiBenchmark(dbData.getBenchMark());
			kpiData.setRegion1Threshold(indexStaticData.getRegion1Threshold());
			kpiData.setRegion1Label(indexStaticData.getRegion1Label());
			kpiData.setRegion1Color(indexStaticData.getRegion1Color());
			kpiData.setRegion2Threshold(indexStaticData.getRegion2Threshold());
			kpiData.setRegion2Label(indexStaticData.getRegion2Label());
			kpiData.setRegion2Color(indexStaticData.getRegion2Color());
			kpiData.setRegion3Threshold(indexStaticData.getRegion3Threshold());
			kpiData.setRegion3Label(indexStaticData.getRegion3Label());
			kpiData.setRegion3Color(indexStaticData.getRegion3Color());
			kpiList.add(kpiData);
		}

		indexData.setKpiData(kpiList);

		writeToFileAndUpdateDashboard(indexData, orgId, toolId, projectId,
				dashBoardId, dashboardDto.getDashboardJsonUrl());
		logger.debug("generateIndexData: Completed Writing the json file in the specified folder");

		return indexData;
	}

	/**
	 * This method populates the static data for the AM Index chart.
	 * 
	 * @return IndexKPIData -- The bean containing the static data.
	 *
	 */
	private IndexKPIData populateIndexRegionData() {
		IndexKPIData kpiData = new IndexKPIData();
		kpiData.setRegion1Threshold(CommonConstants.FORTY_FIVE);
		kpiData.setRegion1Label(CommonConstants.IMPROVE);
		kpiData.setRegion1Color(CommonConstants.RED);
		kpiData.setRegion2Threshold(CommonConstants.HUNDRED);
		kpiData.setRegion2Label(CommonConstants.SAFE);
		kpiData.setRegion2Color(CommonConstants.GREEN);
		return kpiData;
	}

}
