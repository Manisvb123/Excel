package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.model.IndexData;
import com.merit.tlg.dashboard.model.IndexKPIData;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.DataServiceFactory;

/**
 * This Class is a common class used by other classes to generate the JsonFiles required to generate the Index Chart
 * 
 */
public class IndexDBDataService {
	
	final static Logger logger = Logger.getLogger(IndexDBDataService.class);
	
	/**
	 * Method to generate the IndexData bean required for the Index Chart based on the the search criteria.
	 * 
	 * @param searchdata: The search criteria data.
	 * @param indexData: The Index data with input params.
	 * @param indexStaticData: The Static Index data that needs to be in Index data.
	 * @return indexData -- The indexdata with populated dynamic and static data.
	 *
	 */
	public IndexData generateIndexData(ScoreCardSearchDTO searchdata, IndexData indexData, IndexKPIData indexStaticData) {
		ArrayList<ScoreCardDTO> scData = null;
		ArrayList<IndexKPIData> kpiList = null;
		IndexKPIData kpiData = null;

		ScoreCardDao scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		scData = scoreCardDao.getScoreCardDetails(searchdata);

		logger.debug("generateIndexData: Index Data from DB " + scData.toString());
		kpiList = new ArrayList<>();
		for (Iterator<ScoreCardDTO> iterator = scData.iterator(); iterator.hasNext();) {
			ScoreCardDTO dbData = (ScoreCardDTO) iterator.next();
			kpiData = new IndexKPIData();
		
			kpiData.setKpiName(dbData.getMetricName());
			if (CommonConstants.PD.equalsIgnoreCase(dbData.getDimensionType()) || 
					CommonConstants.SD.equalsIgnoreCase(dbData.getDimensionType())	) {
				kpiData.setKpiDisplayName(dbData.getDimensionData().getDimensionName() + " Index");
			} else if (CommonConstants.RESPONSE_METRIC.equalsIgnoreCase(dbData.getDimensionType())) {
				kpiData.setKpiDisplayName(dbData.getResponseMetricData().getResponseMetricName()  + " Index");
			} else if (CommonConstants.TOOL.equalsIgnoreCase(dbData.getDimensionType())) {
				kpiData.setKpiDisplayName(dbData.getDimensionId() + " " + " Index");
			} else {
				kpiData.setKpiDisplayName(dbData.getMetricName());
			}
			logger.debug("generateIndexData: Index Data from DB " + kpiData.getKpiName());
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
		return indexData;
	}
	
	

}
