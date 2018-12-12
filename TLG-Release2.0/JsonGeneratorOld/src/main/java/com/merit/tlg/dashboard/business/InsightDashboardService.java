package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DimensionDTO;
import com.merit.tlg.dashboard.dto.MetricSearchDTO;
import com.merit.tlg.dashboard.dto.QuestionDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.model.AxesData;
import com.merit.tlg.dashboard.model.InsightData;
import com.merit.tlg.dashboard.model.InsightDataPoints;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.CommonUtils;
import com.merit.tlg.dashboard.utils.DataServiceFactory;

public class InsightDashboardService extends BaseDashboardService {
	
	final static Logger logger = Logger.getLogger(InsightDashboardService.class);
	
	/**
	 * This method generates the JSON file required for generating the Insight Chart
	 * for SPP .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return IndexData -- The bean containing the Index Data.
	 *
	 */
	public InsightData generateInsightData(InsightData insData, 
				String orgId, String toolId, String projectId, String dashboardId) {

		ArrayList<InsightDataPoints> dataPoints = null;
		ArrayList<ScoreCardDTO> scData = new ArrayList<>();		

		ScoreCardDao scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		// scData = scoreCardDao.getSSTInsightData(orgId, toolId, projectId);

		ScoreCardSearchDTO srchData = populateSearchDataForInsightDB(orgId, toolId, projectId);
		scData = scoreCardDao.getScoreCardDetails(srchData);
		logger.debug(" Insight Data from DB " + scData.toString());

		dataPoints = populatSSTInsightDynamicData(scData, toolId);
		insData.setDataPoints(dataPoints);
		insData.setAxes(new AxesData("Organization-wide Score"," Lack of Alignment Among Respondents"));
		logger.debug("Populated Dynamic Data " + insData.toString());

		writeToFileAndUpdateDashboard(insData, orgId, toolId, projectId, dashboardId);

		return insData;
	}


	
	
	/**
	 * This method populates the search data required for generating the Insight
	 * Data.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return ScoreCardSearchDTO -- Search Object.
	 *
	 */
	private ScoreCardSearchDTO populateSearchDataForInsightDB(String orgId, String toolId, String projectId) {
		// Insight Data
		MetricSearchDTO mData = null;
		ArrayList<MetricSearchDTO> mList = new ArrayList<MetricSearchDTO>();
		ScoreCardSearchDTO srchData = new ScoreCardSearchDTO();

		srchData.setToolId(toolId);
		srchData.setOrgId(orgId);
		srchData.setProjectId(projectId);
		srchData.setQuesDetailsReq(true);
		srchData.setPdDetailsReq(true);
		mData = new MetricSearchDTO();
		mData.setEntityType(CommonConstants.ORGANISATION);
		mData.setEntityIdReq(true);
		mData.setEntityId(orgId);
		mData.setDimensionType(CommonConstants.QUESTION);
		mData.setDimIdReq(false);
		mData.setMetricName(CommonConstants.OEPS);
		mList.add(mData);
		mData = new MetricSearchDTO();
		mData.setEntityType(CommonConstants.ORGANISATION);
		mData.setEntityIdReq(true);
		mData.setEntityId(orgId);
		mData.setDimensionType(CommonConstants.QUESTION);
		mData.setDimIdReq(false);
		mData.setMetricName(CommonConstants.OESDS);
		mList.add(mData);
		srchData.setMetricList(mList);
		logger.debug("populateSearchDataForInsightDB: SearchData " + srchData);
		return srchData;
	}
	
	/**
	 * This method populates the bean with the Insight Data for SST from the DB.
	 * 
	 * @param ArrayList<ScoreCardDTO> scData: Score Card Data from DB
	 * @param toolId: The tool Id.
	 * 
	 * @return ArrayList<SSTInsightDataPoints> -- List of metrics with values from
	 *         DB
	 *
	 */
	private ArrayList<InsightDataPoints> populatSSTInsightDynamicData(ArrayList<ScoreCardDTO> scData,
			String toolId) {
		ArrayList<InsightDataPoints> dataPoints = new ArrayList<>();

		InsightDataPoints insightData = null;
		String questionId = null;
		String parentDimName = null;
		QuestionDTO questionData = null;

		HashMap<String, DimensionDTO> dimMap = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao()
				.fetchParentDimensionData(toolId);

		dataPoints = new ArrayList();
		HashMap<String, InsightDataPoints> sstDataMap = new HashMap<String, InsightDataPoints>();
		for (Iterator iterator = scData.iterator(); iterator.hasNext();) {
			ScoreCardDTO dbData = (ScoreCardDTO) iterator.next();
			questionData = dbData.getQuestionData();
			questionId = questionData.getQuestionId();
			if (sstDataMap.containsKey(questionId)) {
				insightData = (InsightDataPoints) sstDataMap.get(questionId);
				if (CommonConstants.OEPS.equalsIgnoreCase(dbData.getMetricName())) {
					insightData.setMean(dbData.getMetricValue());
					logger.debug("Setting mean for existing question");
				} else if (CommonConstants.OESDS.equalsIgnoreCase(dbData.getMetricName())) {
					insightData.setStdDev(dbData.getMetricValue());
					logger.debug("Setting STD for existing question");
				}
				sstDataMap.put(questionId, insightData);
			} else {
				insightData = new InsightDataPoints();
				insightData.setQuestionId(questionId);
				insightData.setDataPointLabel(questionData.getQuestionDesc());
				String quesDimId = questionData.getQuestionDimension();
				if (!CommonUtils.isEmpty(quesDimId) && dimMap.containsKey(quesDimId)) {
					DimensionDTO dimData = dimMap.get(quesDimId);
					if (dimData != null) {
						parentDimName = dimData.getParentDimName();
						insightData.setIndicator(parentDimName);
					}
				}
				if (CommonConstants.OEPS.equalsIgnoreCase(dbData.getMetricName())) {
					insightData.setMean(dbData.getMetricValue());
					logger.debug("Setting mean for new question");
				} else if (CommonConstants.OESDS.equalsIgnoreCase(dbData.getMetricName())) {
					insightData.setStdDev(dbData.getMetricValue());
					logger.debug("Setting std for new question");
				}
				sstDataMap.put(questionId, insightData);
				dataPoints.add(insightData);
			}

		}
		return dataPoints;
	}
}
