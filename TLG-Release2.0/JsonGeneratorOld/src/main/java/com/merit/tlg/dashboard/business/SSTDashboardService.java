package com.merit.tlg.dashboard.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DimensionDTO;
import com.merit.tlg.dashboard.dto.MetricSearchDTO;
import com.merit.tlg.dashboard.dto.QuestionDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.dto.TeamDetailsDTO;
import com.merit.tlg.dashboard.dto.UserDetailsDTO;
import com.merit.tlg.dashboard.model.IndexData;
import com.merit.tlg.dashboard.model.IndexKPIData;
import com.merit.tlg.dashboard.model.AxesData;
import com.merit.tlg.dashboard.model.CellData;
import com.merit.tlg.dashboard.model.SSTInsightData;
import com.merit.tlg.dashboard.model.InsightDataPoints;
import com.merit.tlg.dashboard.model.LegendData;
import com.merit.tlg.dashboard.model.RegionData;
import com.merit.tlg.dashboard.model.SSTInsightSummaryTable;
import com.merit.tlg.dashboard.model.SSTOrgTeamDimData;
import com.merit.tlg.dashboard.model.TeamData;
import com.merit.tlg.dashboard.model.UserData;
import com.merit.tlg.dashboard.model.UserTeamData;
import com.merit.tlg.dashboard.persistance.QuestionDao;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.CommonUtils;
import com.merit.tlg.dashboard.utils.DataServiceFactory;

/**
 * This Class is used to generate all the JsonFiles required to generate the
 * different charts required by Strategy Stress Test
 * 
 */
public class SSTDashboardService extends BaseDashboardService {

	final static Logger logger = Logger.getLogger(SSTDashboardService.class);
	private UserTeamDBDataService userTeamDataService;
	private IndexDBDataService indexDashboardService;

	/**
	 * Constructor method instantiating the Index and Userteam service
	 * 
	 */
	public SSTDashboardService() {
		userTeamDataService = new UserTeamDBDataService();
		indexDashboardService = new IndexDBDataService();

	}

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
	public SSTInsightData generateSSTInsightData(String orgId, String toolId, String projectId) {

		ArrayList<InsightDataPoints> dataPoints = null;
		SSTInsightData insData = new SSTInsightData();
		ArrayList<ScoreCardDTO> scData = new ArrayList<>();

		populateSSTInsightStaticData(insData);
		logger.debug("Populated Static Data");

		ScoreCardDao scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		// scData = scoreCardDao.getSSTInsightData(orgId, toolId, projectId);

		ScoreCardSearchDTO srchData = populateSearchDataForInsightDB(orgId, toolId, projectId);
		scData = scoreCardDao.getScoreCardDetails(srchData);
		logger.debug(" Insight Data from DB " + scData.toString());

		dataPoints = populatSSTInsightDynamicData(scData, toolId);
		insData.setDataPoints(dataPoints);
		logger.debug("Populated Dynamic Data " + insData.toString());

		writeToFileAndUpdateDashboard(insData, orgId, toolId, projectId, CommonConstants.SST_INSIGHT_DASHBOARD_ID);

		return insData;
	}

	/*
	 * public SSTIndexData generateSSTIndexData(String orgId, String toolId, String
	 * projectId) {
	 * 
	 * ArrayList<ScoreCardData> scData = null; ArrayList<SSTIndexKPIData> kpiList =
	 * null; SSTIndexKPIData kpiData = null;
	 * 
	 * SSTIndexData indexData = new SSTIndexData();
	 * indexData.setTitle("Strategy Stress Test Index"); indexData.setType("Guage");
	 * indexData.setDescription(
	 * "SST Index is one of the Key Performance Indicator (KPI) expressed as a percentage score against the benchmark."
	 * ); ScoreCardDao scoreCardDao =
	 * DataServiceFactory.getDaoFactoryInstance().getScoreCardDao(); scData =
	 * scoreCardDao.getSSTIndexKPIData(orgId, toolId, projectId);
	 * 
	 * kpiList = new ArrayList<>(); for (Iterator iterator = scData.iterator();
	 * iterator.hasNext();) { ScoreCardData dbData = (ScoreCardData)
	 * iterator.next(); kpiData = new SSTIndexKPIData(); if
	 * (CommonConstants.PD.equalsIgnoreCase(dbData.getDimensionType())) {
	 * kpiData.setKpiName(dbData.getMetricName() + " " +
	 * dbData.getDimensionData().getDimensionName()); } else {
	 * kpiData.setKpiName(dbData.getMetricName()); }
	 * kpiData.setKpiValue(dbData.getMetricValue());
	 * kpiData.setKpiBenchmark(dbData.getBenchMark());
	 * populateKPIStaticData(kpiData); kpiList.add(kpiData); }
	 * 
	 * indexData.setKpiData(kpiList);
	 * 
	 * writeToFileAndUpdateDashboard(indexData, orgId, toolId, projectId,
	 * CommonConstants.SST_KPIINDEX_DASHBOARD_ID); return indexData; }
	 */

	/**
	 * This method generates the JSON file required for generating the Index Chart
	 * for SST .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return IndexData -- The bean containing the Index Data.
	 *
	 */
	public IndexData generateSSTIndexData(String orgId, String toolId, String projectId) {

		ArrayList<ScoreCardDTO> scData = null;
		ArrayList<IndexKPIData> kpiList = null;
		IndexKPIData kpiData = null;
		IndexKPIData indexStaticData = null;

		logger.debug("generateSSTIndexData: The input Parameters {" + orgId + "," + toolId + "," + projectId + "}");
		IndexData indexData = new IndexData();
		indexData.setTitle("Strategy Stress Test Index");
		indexData.setType("Guage");
		indexData.setDescription(
				"SST Index is one of the Key Performance Indicator (KPI) expressed as a percentage score against the benchmark.");

		ScoreCardSearchDTO searchdata = populateSearchDataForKPIIndex(orgId, toolId, projectId);
		indexStaticData = populateKPIStaticData();
		indexData = indexDashboardService.generateIndexData(searchdata, indexData, indexStaticData);
		logger.debug("generateIndexData: Index data from the index service " + indexData);

		/*
		 * ScoreCardDao scoreCardDao =
		 * DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		 * ScoreCardSearchDTO srchData = populateSearchDataForKPIIndex(orgId, toolId,
		 * projectId); scData = scoreCardDao.getScoreCardDetails(srchData);
		 * 
		 * logger.debug(" KPI Index Data from DB " + scData.toString());
		 * 
		 * kpiList = new ArrayList<>(); for (Iterator iterator = scData.iterator();
		 * iterator.hasNext();) { ScoreCardDTO dbData = (ScoreCardDTO) iterator.next();
		 * kpiData = new IndexKPIData(); if
		 * (CommonConstants.PD.equalsIgnoreCase(dbData.getDimensionType())) {
		 * kpiData.setKpiName(dbData.getMetricName() + " " +
		 * dbData.getDimensionData().getDimensionName()); } else {
		 * kpiData.setKpiName(dbData.getMetricName()); }
		 * kpiData.setKpiValue(dbData.getMetricValue());
		 * kpiData.setKpiBenchmark(dbData.getBenchMark());
		 * populateKPIStaticData(kpiData); kpiList.add(kpiData); }
		 * 
		 * indexData.setKpiData(kpiList);
		 */

		writeToFileAndUpdateDashboard(indexData, orgId, toolId, projectId, CommonConstants.SST_KPIINDEX_DASHBOARD_ID);
		logger.debug("generateIndexData: Completed Writing the index json file for SST in the specified folder");
		return indexData;
	}

	/*
	 * public SSTUserTeamData generateSSTUserTeamData(String orgId, String toolId,
	 * String projectId, String qId) { SSTUserTeamData userTeamData = null;
	 * HashMap<String, TeamData> teamMap = null; ArrayList<ScoreCardDTO> scData =
	 * null; ArrayList<UserData> teamUsers = null; TeamData teamData = null;
	 * UserData userData = null; String qDesc = null; String teamId = null; String
	 * dashboardId = null;
	 * 
	 * ScoreCardDao scoreCardDao =
	 * DataServiceFactory.getDaoFactoryInstance().getScoreCardDao(); scData =
	 * scoreCardDao.getSSTUserTeamData(toolId, projectId, qId); if (scData.size() ==
	 * 0) { logger.warn("UserTeamData No Data for the given question " + qId);
	 * return userTeamData; }
	 * 
	 * userTeamData = new SSTUserTeamData();
	 * userTeamData.setTitle("Strategy Stress Test User Team");
	 * userTeamData.setType("Multipl Bar Chart"); userTeamData.
	 * setDescription("Will show how different users and teams have scored for the question"
	 * ); userTeamData.setQuestionId(qId);
	 * 
	 * teamMap = new HashMap(); for (Iterator iterator = scData.iterator();
	 * iterator.hasNext();) { ScoreCardDTO dbData = (ScoreCardDTO) iterator.next();
	 * qDesc = dbData.getQuestionData().getQuestionDesc();
	 * 
	 * TeamDetailsDTO dbTeamData = dbData.getTeamData(); UserDetailsDTO dbUserData =
	 * dbData.getUserData();
	 * 
	 * if (!CommonUtils.isNull(dbTeamData)) { teamId = dbTeamData.getTeamId(); if
	 * (!CommonUtils.isEmpty(teamId)) { logger.debug("Team Data is not null " +
	 * teamId); if (!teamMap.containsKey(teamId)) {
	 * logger.debug("Team not in Map creating new "); teamData = new TeamData();
	 * teamData.setTeamId(teamId); //
	 * teamData.setTeamName(dbTeamData.getTeamName());
	 * teamData.setTeamScore(dbData.metricValue); teamUsers = new
	 * ArrayList<UserData>(); teamData.setUserData(teamUsers); teamMap.put(teamId,
	 * teamData); } else { logger.debug("Team already  in Map setting value new " +
	 * dbData.metricValue); teamData = teamMap.get(teamId);
	 * teamData.setTeamScore(dbData.metricValue); } } } else if
	 * (!CommonUtils.isNull(dbUserData)) { String teamIds =
	 * dbUserData.getUserTeamId(); logger.debug("User Data is not null " + teamIds);
	 * if (!CommonUtils.isEmpty(teamIds)) { String[] arr = teamIds.split(","); for
	 * (int i = 0; i < arr.length; i++) { String userTeamId = arr[i].trim();
	 * logger.debug("User Data is not null " + userTeamId); if
	 * (teamMap.containsKey(userTeamId)) {
	 * logger.debug("Team already created userId " + dbUserData.getUserId());
	 * teamData = teamMap.get(userTeamId); teamUsers = teamData.getUserData();
	 * userData = new UserData(); userData.setUserId(dbUserData.getUserId());
	 * userData.setUserName(dbUserData.getUserFirstName());
	 * userData.setMetricName(dbData.getMetricName());
	 * userData.setMetricValue(dbData.getMetricValue()); teamUsers.add(userData);
	 * teamData.setUserData(teamUsers); } else {
	 * logger.debug("Team not created for userId " + dbUserData.getUserId());
	 * teamData = new TeamData(); teamData.setTeamId(userTeamId); //
	 * teamData.setTeamName(dbUserData.getUserTeamName()); userData = new
	 * UserData(); userData.setUserId(dbUserData.getUserId());
	 * userData.setUserName(dbUserData.getUserFirstName());
	 * userData.setMetricName(dbData.getMetricName());
	 * userData.setMetricValue(dbData.getMetricValue()); teamUsers = new
	 * ArrayList<UserData>(); teamUsers.add(userData);
	 * 
	 * teamData.setUserData(teamUsers); teamMap.put(userTeamId, teamData); } } } } }
	 * 
	 * Set<String> keySet = teamMap.keySet(); logger.debug("Map Size " +
	 * keySet.size()); ArrayList<TeamData> teamList = new ArrayList<TeamData>(); for
	 * (Iterator iterator = keySet.iterator(); iterator.hasNext();) { String tId =
	 * (String) iterator.next(); logger.debug("Set team from map to bean " + tId);
	 * TeamData tData = teamMap.get(tId); teamList.add(tData); }
	 * 
	 * userTeamData.setQuestionDesc(qDesc); userTeamData.setTeamData(teamList);
	 * 
	 * dashboardId = CommonConstants.SST_USERTEAM_DASHBOARD_ID + "-" + qId;
	 * writeToFileAndUpdateDashboard(userTeamData, orgId, toolId, projectId,
	 * dashboardId);
	 * 
	 * return userTeamData; }
	 */

	/**
	 * This method generates the JSON file required for generating the UserTeam
	 * Chart for SST for a particular question .
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @param qId: The question Id.
	 * @return UserTeamData -- The bean containing the UserTeam Data.
	 *
	 */
	public UserTeamData generateUserTeamData(String orgId, String toolId, String projectId, String qId) {

		UserTeamData userTeamData = userTeamDataService.generateUserTeamData("Strategy Stress Test User Team",
				CommonConstants.SST_USERTEAM_DASHBOARD_ID, orgId, toolId, projectId, qId);
		logger.debug("generateUserTeamData: QuestionId " + qId + " Userteamdata " + userTeamData);
		return userTeamData;

	}

	/**
	 * This method generates the JSON file required for generating the UserTeam
	 * Chart for SST for all the questions.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return UserTeamData -- An arraylist containing the UserTeam Data.
	 *
	 */
	public ArrayList<UserTeamData> generateUserTeamDataAllQuestions(String orgId, String toolId, String projectId) {

		ArrayList<UserTeamData> userTeamData = userTeamDataService.generateUserTeamDataAllQuestions(
				"Strategy Stress Test User Team", CommonConstants.SST_USERTEAM_DASHBOARD_ID, orgId, toolId, projectId);
		logger.debug("generateUserTeamDataAllQuestions: Userteamdata " + userTeamData);
		return userTeamData;
	}

	/**
	 * This method generates the JSON file required for generating the OrgTeam Chart
	 * for SST for all the questions.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return UserTeamData -- An arraylist containing the UserTeam Data.
	 *
	 */
	public SSTOrgTeamDimData generateSSTOrgTeamData(String orgId, String toolId, String projectId) {

		ArrayList<ScoreCardDTO> scData = null;
		SSTOrgTeamDimData orgTeamData = new SSTOrgTeamDimData();
		orgTeamData.setTitle("Strategy Stress Test User Team");
		orgTeamData.setType("Multipl Bar Chart");
		orgTeamData.setDescription("Will show how different users and teams have scored for the question");

		ScoreCardDao scoreCardDao = DataServiceFactory.getDaoFactoryInstance().getScoreCardDao();
		scData = scoreCardDao.getSSTOrgTeamPDData(orgId, toolId, projectId);

		for (Iterator iterator = scData.iterator(); iterator.hasNext();) {
			ScoreCardDTO dbOrgTeamData = (ScoreCardDTO) iterator.next();
			if (dbOrgTeamData != null && CommonConstants.ORGANISATION.equals(dbOrgTeamData.entityType)) {

			}
		}

		return orgTeamData;

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

		InsightDataPoints sstInsightData = null;
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
			logger.debug("question ID" + questionId);
			if (sstDataMap.containsKey(questionId)) {
				sstInsightData = (InsightDataPoints) sstDataMap.get(questionId);
				if (CommonConstants.OEPS.equalsIgnoreCase(dbData.getMetricName())) {
					sstInsightData.setMean(dbData.getMetricValue());
					logger.debug("Setting mean for existing question");
				} else if (CommonConstants.OESDS.equalsIgnoreCase(dbData.getMetricName())) {
					sstInsightData.setStdDev(dbData.getMetricValue());
					logger.debug("Setting STD for existing question");
				}
				sstDataMap.put(questionId, sstInsightData);
			} else {
				sstInsightData = new InsightDataPoints();
				sstInsightData.setQuestionId(questionId);
				sstInsightData.setDataPointLabel(questionData.getQuestionDesc());
				String quesDimId = questionData.getQuestionDimension();
				if (!CommonUtils.isEmpty(quesDimId) && dimMap.containsKey(quesDimId)) {
					DimensionDTO dimData = dimMap.get(quesDimId);
					if (dimData != null) {
						parentDimName = dimData.getParentDimName();
						sstInsightData.setIndicator(parentDimName);
						if (!CommonUtils.isEmpty(parentDimName)) {
							if (CommonConstants.PD_ROBUST.equalsIgnoreCase(parentDimName)) {
								sstInsightData.setColor(CommonConstants.RED);
							} else if (CommonConstants.PD_RESILIENT.equalsIgnoreCase(parentDimName)) {
								sstInsightData.setColor(CommonConstants.GREEN);
							} else if (CommonConstants.PD_ROOTED.equalsIgnoreCase(parentDimName)) {
								sstInsightData.setColor(CommonConstants.BLUE);
							}
						}
					}
				}
				if (CommonConstants.OEPS.equalsIgnoreCase(dbData.getMetricName())) {
					sstInsightData.setMean(dbData.getMetricValue());
					logger.debug("Setting mean for new question");
				} else if (CommonConstants.OESDS.equalsIgnoreCase(dbData.getMetricName())) {
					sstInsightData.setStdDev(dbData.getMetricValue());
					logger.debug("Setting std for new question");
				}
				sstDataMap.put(questionId, sstInsightData);
				dataPoints.add(sstInsightData);
			}

		}
		return dataPoints;
	}

	/**
	 * This method populates the bean with the Static Insight Data for SST from the
	 * DB.
	 * 
	 * @param insData: Static Insight Data
	 * 
	 */
	public void populateSSTInsightStaticData(SSTInsightData insData) {
		insData.setTitle("Strategy Stress Test Insight");
		insData.setDescription("SST insights shows how good the strategy is in terms of robustness,"
				+ " resilience and readiness against the level of disagreement between respondents.");
		insData.setType("X-Y Scatter plot");
		insData.setDataPtShape("Square");

		ArrayList<RegionData> regions = new ArrayList<>();
		RegionData region1 = new RegionData();
		region1.setLabel("Region1");
		region1.setColor("Orange");
		region1.setX1Coordinate((float) 50);
		region1.setX2Coordinate((float) 50);
		region1.setX3Coordinate((float) 87);
		region1.setY1Coordinate((float) 0.54);
		region1.setY2Coordinate((float) 1.50);
		region1.setY3Coordinate((float) 1.50);
		regions.add(region1);

		RegionData region2 = new RegionData();
		region2.setLabel("Region2");
		region2.setLabel("pink");
		region2.setX1Coordinate((float) 50);
		region2.setX2Coordinate((float) 58);
		region2.setX3Coordinate((float) 88);
		region2.setX4Coordinate((float) 100);
		region2.setY1Coordinate((float) 0.54);
		region2.setY2Coordinate((float) 0.30);
		region2.setY3Coordinate((float) 1.5);
		region2.setY4Coordinate((float) 1.3);
		regions.add(region2);

		insData.setRegions(regions);

		ArrayList<LegendData> legends = new ArrayList<>();
		LegendData legend1 = new LegendData();
		legend1.setTxtLabel("Robust");
		legend1.setColor("Red");
		legend1.setGeoShape("Square");
		legends.add(legend1);
		LegendData legend2 = new LegendData();
		legend2.setTxtLabel("Resilience");
		legend2.setColor("Green");
		legend2.setGeoShape("Square");
		legends.add(legend2);
		LegendData legend3 = new LegendData();
		legend3.setTxtLabel("Rooted");
		legend3.setColor("Blue");
		legend3.setGeoShape("Square");
		insData.setLegends(legends);

		AxesData axes = new AxesData();
		axes.setxAxisLabel("Strategy is robust, resilient, and rooted");
		axes.setyAxisLabel("Lack of alignment amongst respondents");
		insData.setAxes(axes);

		SSTInsightSummaryTable summaryTable = new SSTInsightSummaryTable();
		summaryTable.setNumberOfRows(4);
		summaryTable.setNumberOfColumns(4);

		ArrayList<CellData> cellData = new ArrayList<>();
		CellData cell1 = new CellData();
		cell1.setRowNumber(1);
		cell1.setColNumber(2);
		cell1.setCellText("Little Concern");
		cell1.setCellColor("blue");
		cell1.setHeader(true);
		cellData.add(cell1);

		CellData cell2 = new CellData();
		cell2.setRowNumber(1);
		cell2.setColNumber(3);
		cell2.setCellText("Some Concerns");
		cell2.setCellColor("blue");
		cell2.setHeader(true);
		cellData.add(cell2);

		CellData cell3 = new CellData();
		cell3.setRowNumber(1);
		cell3.setColNumber(4);
		cell3.setCellText("Greatest Concerns");
		cell3.setCellColor("blue");
		cell3.setHeader(true);
		cellData.add(cell3);

		CellData cell4 = new CellData();
		cell4.setRowNumber(2);
		cell4.setColNumber(1);
		cell4.setCellText("Robust Factors");
		cell4.setCellColor("blue");
		cell4.setHeader(true);
		cellData.add(cell4);

		CellData cell5 = new CellData();
		cell5.setRowNumber(2);
		cell5.setColNumber(2);
		cell5.setCellText("60%");
		cell5.setCellColor("white");
		cell5.setHeader(false);
		cellData.add(cell5);

		CellData cell6 = new CellData();
		cell6.setRowNumber(2);
		cell6.setColNumber(3);
		cell6.setCellText("33%");
		cell6.setCellColor("white");
		cell6.setHeader(false);
		cellData.add(cell6);

		CellData cell7 = new CellData();
		cell7.setRowNumber(2);
		cell7.setColNumber(4);
		cell7.setCellText("17%");
		cell7.setCellColor("white");
		cell7.setHeader(false);
		cellData.add(cell7);

		summaryTable.setCellData(cellData);
		insData.setSummaryTable(summaryTable);

		ArrayList<String> summaryTxt = new ArrayList<>();
		summaryTxt.add("5 Resilience factors are in greatest concerns segment");
		summaryTxt.add("5 Rooted factors are in greatest or some concerns segment");
		insData.setSummaryTxt(summaryTxt);

		ArrayList<String> notes = new ArrayList<>();
		notes.add("X axis shows mean of responses accross the sample expressed as percentage");
		notes.add("Y axis shows standard deviation of all responses");
		insData.setNotes(notes);

	}

	private IndexKPIData populateKPIStaticData() {
		IndexKPIData kpiData = new IndexKPIData();
		kpiData.setRegion1Threshold(CommonConstants.SIXTY);
		kpiData.setRegion1Label(CommonConstants.IMPROVE);
		kpiData.setRegion1Color(CommonConstants.RED);
		kpiData.setRegion2Threshold(CommonConstants.HUNDRED);
		kpiData.setRegion2Label(CommonConstants.SAFE);
		kpiData.setRegion2Color(CommonConstants.GREEN);
		return kpiData;
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
	 * This method populates the search data required for generating the Index Data.
	 * 
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id.
	 * @return ScoreCardSearchDTO -- Search Object.
	 *
	 */
	private ScoreCardSearchDTO populateSearchDataForKPIIndex(String orgId, String toolId, String projectId) {

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
		mData.setDimensionType(CommonConstants.TOOL);
		mData.setDimIdReq(false);
		mData.setMetricName(CommonConstants.OTI);
		mList.add(mData);
		mData = new MetricSearchDTO();
		mData.setEntityType(CommonConstants.ORGANISATION);
		mData.setEntityIdReq(true);
		mData.setEntityId(orgId);
		mData.setDimensionType(CommonConstants.PD);
		mData.setDimIdReq(false);
		mData.setMetricName(CommonConstants.OPDPS);
		mList.add(mData);
		srchData.setMetricList(mList);
		logger.debug("populateSearchDataForIndex: SearchData " + srchData);
		return srchData;
	}

}
