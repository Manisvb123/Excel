package com.merit.tlg.dashboard.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merit.tlg.dashboard.model.BaseDashboardData;

public class ToolsBeanFactory {

	final static Logger logger = Logger.getLogger(ToolsBeanFactory.class);

	public static BaseDashboardData getDashboardBean(Class beanType, String jsonTemplateURL) throws JsonGenException {
		InputStream jsonFileIS = null;
		ObjectMapper objectMapper = null;
		BaseDashboardData dashboardData = null;
		// create ObjectMapper instance
		try {
			objectMapper = new ObjectMapper();
			//String jsonFileName = "json/" + dashBoardId + ".json";
			jsonFileIS = FileReaderUtil.readFile(jsonTemplateURL);
			dashboardData = (BaseDashboardData) objectMapper.readValue(jsonFileIS, beanType);
			logger.debug("Mapping Json Template to Bean " +  jsonTemplateURL + ", " + dashboardData);
			
			/*if (CommonConstants.SST_INSIGHT_DASHBOARD_ID.equals(dashBoardId)) {
				// InputStream jsonFile = getBeanfromJson(SSTInsightData.class, jsonFileName);
				SSTInsightData insData = objectMapper.readValue(jsonFileIS, beanType);
				return insData;
			} else if (CommonConstants.SSTBR_INSIGHT_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.AM_INSIGHT_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.BEF_INSIGHT_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.CB_INSIGHT_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.LIF_INSIGHT_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.RG_INSIGHT_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.SD_INSIGHT_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.SPP_INSIGHT_DASHBOARD_ID.equals(dashBoardId)) {
				InsightData insData = objectMapper.readValue(jsonFileIS, InsightData.class);
				return insData;
			} else if (CommonConstants.SST_INDEX_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.SSTBR_INDEX_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.AM_INDEX_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.BEF_INDEX_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.CB_INDEX_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.LIF_INDEX_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.RG_INDEX_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.SD_INDEX_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.SPP_INDEX_DASHBOARD_ID.equals(dashBoardId)) {
				IndexData indData = objectMapper.readValue(jsonFileIS, IndexData.class);
				return indData;
			} else if (CommonConstants.SST_USERTEAM_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.SSTBR_USERTEAM_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.AM_USERTEAM_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.BEF_USERTEAM_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.CB_USERTEAM_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.LIF_USERTEAM_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.RG_USERTEAM_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.SD_USERTEAM_DASHBOARD_ID.equals(dashBoardId)
					|| CommonConstants.SPP_USERTEAM_DASHBOARD_ID.equals(dashBoardId)) {
				UserTeamData userTeamData = objectMapper.readValue(jsonFileIS, UserTeamData.class);
				return userTeamData;
			} */
		} catch (JsonMappingException e) {
			logger.error(" JsonMappingException  " + e);
			throw new JsonGenException("Not a map json to bean ", e, ErrorCode.INVALID_JSON);
		} catch (JsonParseException e) {
			logger.error(" JsonParseException  " + e);
			throw new JsonGenException("Not a valid Json file ", e, ErrorCode.INVALID_JSON);
		} catch (IOException e) {
			logger.error(" IOException  " + e);
			throw new JsonGenException("Not able to read from json file  ", e, ErrorCode.INVALID_FILE);
		} finally {
			try {
				if (jsonFileIS != null)
					jsonFileIS.close();
			} catch (IOException e) {
				logger.error(" Unable to close stream  " + e);
				e.printStackTrace();
			}
		}

		return dashboardData;
	}
}
