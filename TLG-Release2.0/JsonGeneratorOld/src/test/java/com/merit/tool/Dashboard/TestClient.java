package com.merit.tool.Dashboard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merit.tlg.dashboard.business.SSTDashboardService;
import com.merit.tlg.dashboard.business.DashboardServiceWrapper;
import com.merit.tlg.dashboard.business.SSTBrexitDashboardService;
import com.merit.tlg.dashboard.dto.DashboardDTO;
import com.merit.tlg.dashboard.dto.MetricSearchDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.persistance.DashboardDao;
import com.merit.tlg.dashboard.persistance.DashboardDaoImpl;
import com.merit.tlg.dashboard.persistance.ScoreCardDao;
import com.merit.tlg.dashboard.persistance.ScoreCardDaoImpl;
import com.merit.tlg.dashboard.persistance.common.BaseDaoImpl;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.CommonUtils;
import com.merit.tlg.dashboard.utils.DataServiceFactory;
import com.merit.tlg.dashboard.utils.TlgProperties;

public class TestClient {
	final static Logger logger = Logger.getLogger(TestClient.class);

	public static void main(String[] args) {

		// readPropertiesFile();
		// createDashboard();
		try {
			// DashboardDao dashboardDao =
			// DataServiceFactory.getDaoFactoryInstance().getDashboardDao();
			// DashboardDTO dashboardDto = dashboardDao.fetchDashboardDetails("ORG-001",
			// "SST-001", "PR-001", CommonConstants.SST_INSIGHT_DASHBOARD_ID);
			// String fileWithPath = "/pushpa/projects" +
			// dashboardDto.getDashboardJsonUrl();
			// System.out.println("URL " + dirPath + dashboardDto.getDashboardJsonUrl());

			DashboardServiceWrapper dbservice = new DashboardServiceWrapper();
			
			dbservice.generateAllJsonsForSST("ORG-001", "SST-001", "PR-001");
			dbservice.generateAllJsonsForSSTBR("ORG-001", "SSTBR-001", "PR-001");
			dbservice.generateAllJsonsForAM("ORG-001", "AM-001", "PR-001");
			dbservice.generateAllJsonsForBEF("ORG-001", "BEF-001", "PR-001");
			dbservice.generateAllJsonsForCB("ORG-001", "CB-001", "PR-001");
			dbservice.generateAllJsonsForLIF("ORG-001", "LIF-001", "PR-001");
			dbservice.generateAllJsonsForSD("ORG-001", "SD-001", "PR-001");
			dbservice.generateAllJsonsForSPP("ORG-001", "SPP-001", "PR-001");
			
			
		   // SSTBrexitDashboardService sstService = new SSTBrexitDashboardService();
			//sstService.generateSSTBRInsightData("ORG-001", "SSTBR-001", "PR-001");
			
			
			// Insight Data
			//ScoreCardDaoImpl dao = new ScoreCardDaoImpl();
//			MetricSearchData mData = null;
//			ArrayList<MetricSearchData> mList = new ArrayList<MetricSearchData>();
//			ScoreCardSearchData srchData = new ScoreCardSearchData();
//
//			srchData.setToolId("SST-001");
//			srchData.setProjectId("PR-001");
//			srchData.setQuesDetailsReq(true);
//			mData = new MetricSearchData();
//			mData.setEntityType(CommonConstants.ORGANISATION);
//			mData.setEntityIdReq(true);
//			mData.setEntityId("ORG-001");
//			mData.setDimensionType(CommonConstants.QUESTION);
//			mData.setDimIdReq(false);
//			mData.setMetricName(CommonConstants.OEPS);
//			mList.add(mData);
//			mData = new MetricSearchData();
//			mData.setEntityType(CommonConstants.ORGANISATION);
//			mData.setEntityIdReq(true);
//			mData.setEntityId("ORG-001");
//			mData.setDimensionType(CommonConstants.QUESTION);
//			mData.setDimIdReq(false);
//			mData.setMetricName(CommonConstants.OESDS);
//			mList.add(mData);
//			srchData.setMetricList(mList);
//			dao.getScoreCardDetails(srchData);
			
			//SSTDashboardService service = new SSTDashboardService();
			//service.generateSSTIndexData("ORG-001", "SST-001", "PR-001");
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static DashboardDTO createDashboard() {
		DashboardDTO dbDto = new DashboardDTO();
		dbDto.setToolId("SST-001");
		dbDto.setProjectId("PR-001");
		dbDto.setOrgdId("ORG-001");
		dbDto.setDashboardId("DBD-003");
		dbDto.setDashboardName("SST Insight Dashboard");
		dbDto.setDashboardJsonTemplate("C:\\Users\\jsonfiles\\SSTInsight.json");
		dbDto.setDashboardJsonUrl("C:\\Users\\jsonfiles\\SSTInsight.json");
		dbDto.setStatus("Success");
		dbDto.setGenerationDate(CommonUtils.getCurrentTimeStamp());

		// DashboardDaoImpl dao = new DashboardDaoImpl();
		// Boolean insFlag = dao.InsertDashboardDetails(dbDto);

		// logger.debug("Insert " + insFlag);
		return dbDto;
	}

	private static void readPropertiesFile() {
		TlgProperties tlgProperties = TlgProperties.getInstance();
		logger.debug("************** Fetched Properties File ***************");
		String dbPath = tlgProperties.getProperty("tlg.dashboard.path");
		logger.debug("************** Fetched Properties dbPath ***************" + dbPath);

	}

	private static void testConnection() {
		/*
		 * BaseDaoImpl dao = new BaseDaoImpl(); Connection connection = null; try {
		 * connection = dao.getConnection(); logger.debug("established connection"); }
		 * catch (Exception e) { e.printStackTrace(); } finally {
		 * logger.debug("Terminated connection"); dao.closeAll(null, null, connection);
		 * }
		 */
	}

}
