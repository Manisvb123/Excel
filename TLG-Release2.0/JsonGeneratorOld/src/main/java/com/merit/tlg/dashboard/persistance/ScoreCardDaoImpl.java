package com.merit.tlg.dashboard.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DimensionDTO;
import com.merit.tlg.dashboard.dto.MetricSearchDTO;
import com.merit.tlg.dashboard.dto.QuestionDTO;
import com.merit.tlg.dashboard.dto.ResponseMetricDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
import com.merit.tlg.dashboard.dto.TeamDetailsDTO;
import com.merit.tlg.dashboard.dto.UserDetailsDTO;
import com.merit.tlg.dashboard.persistance.common.BaseDaoImpl;
import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.CommonUtils;

/**
 * This class fetches the scorecard details for the different dashboards
 * 
 */
public class ScoreCardDaoImpl extends BaseDaoImpl implements ScoreCardDao {
	final static Logger logger = Logger.getLogger(ScoreCardDaoImpl.class);

	/**
	 * Gets the scorecard details for the SST Insight dashboard
	 *
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id
	 * 
	 * @return ArrayList<ScoreCardDTO> -- A list with the scorecard details.
	 *
	 */
	public ArrayList<ScoreCardDTO> getSSTInsightData(String orgId, String toolId, String projectId) {
		Connection connection = null;
		PreparedStatement psSSTInsightData = null;
		ResultSet rsSSTInsightData = null;

		ScoreCardDTO scData = null;
		ArrayList<ScoreCardDTO> sstDataList = null;
		HashMap<String, DimensionDTO> dimMap = null;
		String quesDimId = null;
		DimensionDTO dimData = null;
		QuestionDTO questionData = null;

		logger.debug("getSSTInsightData: Input Params OrgId " + orgId + " ToolId " + toolId + " ProjectId " + projectId);
		try {

			dimMap = fetchParentDimensionData(toolId);
			
			StringBuilder sqlQuery = new StringBuilder(
					"select sc.dimension_id, sc.metric_name, sc.metric_value, ")
							.append("qb.question_description, qb.dimension_id ").append("from tabScoreCard as sc ")
							.append("left join tabQuestionBank as qb on sc.dimension_id = qb.question_id and sc.tool_id = qb.tool_id ")
							.append("where sc.tool_id = '").append(toolId).append("' and sc.project_id = '")
							.append(projectId).append("'");
			if (!CommonUtils.isEmpty(orgId)) {
				sqlQuery.append(" and  sc.entity_type = '").append(CommonConstants.ORGANISATION)
						.append("' and sc.entity_id = '").append(orgId).append("'");
			}
			sqlQuery.append(" and sc.dimension_type = '").append(CommonConstants.QUESTION).append("'");
			sqlQuery.append(" and sc.metric_name in ('").append(CommonConstants.OEPS).append("','").append(CommonConstants.OESDS).append("')");
			
			sqlQuery.append(" order by sc.dimension_id");

			logger.debug("getSSTInsightData: SQL Query in DAO " + sqlQuery);

			connection = getConnection();
			psSSTInsightData = connection.prepareStatement(sqlQuery.toString());
			rsSSTInsightData = psSSTInsightData.executeQuery();
			sstDataList = new ArrayList<>();

			while (rsSSTInsightData.next()) {
				scData = new ScoreCardDTO();
				quesDimId = (rsSSTInsightData.getString("qb.dimension_id"));

				//scData.setMetricId(rsSSTInsightData.getString("SC.METRIC_ID"));
				scData.setMetricName(rsSSTInsightData.getString("sc.metric_name"));
				scData.setMetricValue(rsSSTInsightData.getFloat("sc.metric_value"));

				questionData = new QuestionDTO();
				questionData.setQuestionId(rsSSTInsightData.getString("sc.dimension_id"));
				questionData.setQuestionDesc(rsSSTInsightData.getString("qb.question_description"));
				questionData.setQuestionDimension(quesDimId);
				scData.setQuestionData(questionData);

				if (!CommonUtils.isEmpty(quesDimId) && dimMap.containsKey(quesDimId)) {
					dimData = dimMap.get(quesDimId);
					if (dimData != null) {
						scData.setDimensionData(dimData);
					}
				}
				sstDataList.add(scData);
			}
			logger.debug("getSSTInsightData: Query Results " + sstDataList.size());
			
		} catch (SQLException exception) {
			logger.error("getSSTInsightData: SQLException " + exception);
			exception.printStackTrace();
		} finally {
			closeAll(rsSSTInsightData, psSSTInsightData, connection);
		}

		return sstDataList;
	}
	
	/**
	 * Gets the scorecard details for the SST Index dashboard
	 *
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id
	 * 
	 * @return ArrayList<ScoreCardDTO> -- A list with the scorecard details.
	 *
	 */
	public ArrayList<ScoreCardDTO> getSSTIndexKPIData(String orgId, String toolId, String projectId) {
		Connection connection = null;
		PreparedStatement psKPIData = null;
		ResultSet rsKPIData = null;
		ArrayList<ScoreCardDTO> sstKPIList = null;
		ScoreCardDTO sstKPIData = null;
		String dimId = null;
		logger.debug("getSSSIndexKPIData: Input Param OrgId " + orgId + " ToolId " + toolId + " Project ID " + projectId);
		DimensionDTO dimData = null;

		HashMap<String, DimensionDTO> dimMap = null;
		dimMap = fetchPriDimensionData(toolId);

				
		StringBuilder sqlQuery = new StringBuilder(
				"select sc.dimension_id, sc.dimension_type, sc.metric_name, sc.metric_value, ")
						.append("sc.benchmark ").append("from tabScoreCard as sc ")
						// .append("LEFT JOIN TABMETRICSLIST AS ML on SC.METRIC_ID = ML.METRIC_ID AND
						// SC.TOOL_ID = ML.TOOL_ID ")
						.append("where sc.tool_id = '").append(toolId).append("' and sc.project_id =  '")
						.append(projectId).append("'");
		if (!CommonUtils.isEmpty(orgId)) {
			sqlQuery.append(" and  sc.entity_type = '").append(CommonConstants.ORGANISATION)
					.append("' and sc.entity_id =  '").append(orgId).append("'");
		}
		sqlQuery.append(" and ((sc.dimension_type = '").append(CommonConstants.TOOL).append("' and sc.metric_name = '")
				.append(CommonConstants.OTI).append("') or (sc.dimension_type  = '").append(CommonConstants.PD)
				.append("' and sc.metric_name  = '").append(CommonConstants.OPDPS).append("'))");

		logger.debug("getSSTIndexKPIData: SQL Query: " + sqlQuery);

		try {
			connection = getConnection();
			psKPIData = connection.prepareStatement(sqlQuery.toString());
			rsKPIData = psKPIData.executeQuery();
			sstKPIList = new ArrayList();
			while (rsKPIData.next()) {
				sstKPIData = new ScoreCardDTO();
				//sstKPIData.setMetricId(rsKPIData.getString("SC.METRIC_ID"));
				sstKPIData.setMetricName(rsKPIData.getString("sc.metric_name"));
				sstKPIData.setMetricValue(rsKPIData.getFloat("sc.metric_value"));
				sstKPIData.setBenchMark(rsKPIData.getFloat("sc.benchmark"));
				sstKPIData.setDimensionId(rsKPIData.getString("sc.dimension_id"));
				sstKPIData.setDimensionType(rsKPIData.getString("sc.dimension_type"));

				if (CommonConstants.PD.equalsIgnoreCase(rsKPIData.getString("sc.dimension_type"))) {
					dimId = rsKPIData.getString("sc.dimension_id");
					if (!CommonUtils.isEmpty(dimId) && dimMap.containsKey(dimId)) {
						dimData = dimMap.get(dimId);
						if (dimData != null) {
							sstKPIData.setDimensionData(dimData);
						}
					}
				}
				sstKPIList.add(sstKPIData);
			}
			logger.debug("getSSTIndexKPIData: Query Results " + sstKPIList.size());
		} catch (SQLException exception) {
			logger.error("getSSTIndexKPIData: SQLException " + exception);
			exception.printStackTrace();
		} finally {
			closeAll(rsKPIData, psKPIData, connection);
		}

		return sstKPIList;
	}
	
	/**
	 * Gets the scorecard details for the SST User Team dashboard
	 *
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id
	 * 
	 * @return ArrayList<ScoreCardDTO> -- A list with the scorecard details.
	 *
	 */
	public ArrayList<ScoreCardDTO> getSSTUserTeamData(String toolId, String projectId, String quesId) {
		Connection connection = null;
		PreparedStatement psUserData = null;
		ResultSet rsUserData = null;
		
		ArrayList<ScoreCardDTO> sstUserTeamList = null;
		ScoreCardDTO sstUserTeamData = null;
		String entityType = null;
		
		logger.debug("getSSTUserTeamData: Input Param projectId " + projectId + " ToolId " + toolId + " quesId " + quesId);
		
		
		StringBuilder sqlQuery = new StringBuilder(
				"select sc.dimension_id, sc.entity_type, sc.entity_id, sc.metric_name, sc.metric_value, sc.benchmark, qb.question_description, ")
				.append("qb.dimension_id, tuser.user_id, tuser.first_name, tuser.team_list ")
				.append("from tabScoreCard as sc ")
				.append("left join tabQuestionBank as qb on sc.dimension_id = qb.question_id and sc.tool_id = qb.tool_id ")
				.append("left join tabUser as tuser on sc.entity_type = '").append(CommonConstants.USER).append("' and sc.entity_id = tuser.id ")
				.append("where sc.tool_id = '").append(toolId).append("' and sc.project_id = '").append(projectId)
				.append("' and  sc.dimension_type = '").append(CommonConstants.QUESTION).append("' and sc.dimension_id = '").append(quesId)
				.append("' and ((sc.entity_type = '").append(CommonConstants.TEAM).append("' and sc.metric_name = '").append(CommonConstants.TEPS)
				.append("') or (sc.entity_type = '").append(CommonConstants.USER).append("' and sc.metric_name = '").append(CommonConstants.EPS).append("'))");
		
		logger.debug("getSSTUserTeamData: SQL Query " + sqlQuery);

		try {
			connection = getConnection();
			psUserData = connection.prepareStatement(sqlQuery.toString());
			rsUserData = psUserData.executeQuery();
			sstUserTeamList = new ArrayList();
			
			QuestionDTO questionData = new  QuestionDTO();
			while (rsUserData.next()) {
				sstUserTeamData = new ScoreCardDTO();
				//sstUserTeamData.setMetricId(rsUserData.getString("SC.METRIC_ID"));
				sstUserTeamData.setMetricName(rsUserData.getString("sc.metric_name"));
				sstUserTeamData.setMetricValue(rsUserData.getFloat("sc.metric_value"));
				sstUserTeamData.setBenchMark(rsUserData.getFloat("sc.benchmark"));
				questionData.setQuestionId(quesId);
				questionData.setQuestionDesc(rsUserData.getString("qb.question_description"));
				sstUserTeamData.setQuestionData(questionData);
				entityType  = rsUserData.getString("sc.entity_type");
				if (CommonConstants.TEAM.equalsIgnoreCase(entityType)) {
					TeamDetailsDTO teamData = new TeamDetailsDTO();
					teamData.setTeamId( rsUserData.getString("sc.entity_id"));
					sstUserTeamData.setTeamData(teamData);
				} else if (CommonConstants.USER.equalsIgnoreCase(entityType)) {
					UserDetailsDTO userData = new UserDetailsDTO();
					userData.setUserId( rsUserData.getString("tuser.user_id"));
					userData.setUserFirstName(rsUserData.getString("tuser.first_name"));
					userData.setUserTeamId(rsUserData.getString("tuser.team_list"));
					//userData.setUserTeamName(rsUserData.getString("TUSER.TEAM_NAME"));
					sstUserTeamData.setUserData(userData);
				}
				
				sstUserTeamList.add(sstUserTeamData);
			}
			logger.debug("getSSTUserTeamData: Query Results " + sstUserTeamList.size());
		} catch (SQLException exception) {
			logger.error("getSSTUserTeamData: SQLException " + exception);
			exception.printStackTrace();
		} finally {
			closeAll(rsUserData, psUserData, connection);
		}
		
		return sstUserTeamList;
	}
	
	/**
	 * Gets the scorecard details for the SST Org team dashboard
	 *
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id
	 * 
	 * @return ArrayList<ScoreCardDTO> -- A list with the scorecard details.
	 *
	 */
	public ArrayList<ScoreCardDTO> getSSTOrgTeamPDData(String orgId, String toolId, String projectId) {
		Connection connection = null;
		PreparedStatement psOrgTeamData = null;
		ResultSet rsOrgTeamData = null;
		
		ArrayList<ScoreCardDTO> sstOrgTeamList = null;
		ScoreCardDTO sstOrgTeamData = null;
		logger.debug("getSSTOrgTeamPDData  OrgId " + orgId + " ToolId " + toolId + "Project ID " + projectId);
		
		
		StringBuilder sqlQuery = new StringBuilder(
				"select sc.entity_type, sc.entity_id, sc.dimension_type, sc.dimension_id, sc.metric_name, sc.metric_value, sc.benchmark, qd.dimension_name, qd.benchmark ")
				.append("from tabScoreCard as sc ")
				.append("left join tabQuestionDimensions as qd on sc.dimension_type = '").append(CommonConstants.PD)
				.append("' and sc.dimension_id = qd.dimension_id and sc.tool_id = qd.tool_id")
				.append(" where sc.tool_id = '").append(toolId).append("' and sc.project_id = '").append(projectId).append("'")
				.append(" and  ((sc.entity_type = '").append(CommonConstants.ORGANISATION).append("' and sc.dimension_type = '").append(CommonConstants.TOOL)
				.append("' and sc.dimension_id = '").append(toolId).append("' and metric_name = '").append(CommonConstants.OTPS).append("')")
				.append(" or  (sc.entity_type = '").append(CommonConstants.TEAM).append("' and sc.dimension_type = '").append(CommonConstants.TOOL)
				.append("' and sc.dimension_id = '").append(toolId).append("' and metric_name = '").append(CommonConstants.TTPS).append("')")
				.append(" or  (sc.entity_type = '").append(CommonConstants.TEAM).append("' and sc.dimension_type = '").append(CommonConstants.PD)
				.append("' and metric_name = '").append(CommonConstants.TPDPS).append("'))");
				
				
		logger.debug("getSSTOrgTeamPDData: SQL Query " + sqlQuery);

		try {
			connection = getConnection();
			psOrgTeamData = connection.prepareStatement(sqlQuery.toString());
			rsOrgTeamData = psOrgTeamData.executeQuery();
			sstOrgTeamList = new ArrayList();
			while (rsOrgTeamData.next()) {
				sstOrgTeamData = new ScoreCardDTO();
				//sstUserTeamData.setMetricId(rsUserData.getString("SC.METRIC_ID"));
				sstOrgTeamData.setEntityType(rsOrgTeamData.getString("sc.entity_type"));
				sstOrgTeamData.setEntityId(rsOrgTeamData.getString("sc.entity_id"));
				sstOrgTeamData.setDimensionType(rsOrgTeamData.getString("sc.dimension_type"));
				sstOrgTeamData.setDimensionId(rsOrgTeamData.getString("sc.dimension_id"));
				sstOrgTeamData.setMetricName(rsOrgTeamData.getString("sc.metric_name"));
				sstOrgTeamData.setMetricValue(rsOrgTeamData.getFloat("sc.metric_value"));
				sstOrgTeamData.setBenchMark(rsOrgTeamData.getFloat("sc.benchmark"));
				if (CommonConstants.PD.equalsIgnoreCase(rsOrgTeamData.getString("sc.dimension_type"))) {
					DimensionDTO dimData = new DimensionDTO();
					dimData.setDimensionId(rsOrgTeamData.getString("sc.dimension_id"));
					dimData.setDimensionName(rsOrgTeamData.getString("qd.dimension_name"));
					dimData.setBenchmark(rsOrgTeamData.getFloat("qd.benchmark"));
					sstOrgTeamData.setDimensionData(dimData);
				}
				sstOrgTeamList.add(sstOrgTeamData);
			}
			logger.debug("getSSTOrgTeamPDData: Query Result Size " + sstOrgTeamList.size());
		} catch (SQLException exception) {
			logger.error("getSSTOrgTeamPDData: SQL Exception " + exception);
			exception.printStackTrace();
		} finally {
			closeAll(rsOrgTeamData, psOrgTeamData, connection);
		}
		
		return sstOrgTeamList;
		
		
	}
	
	/**
	 * Fetches the Primary dimensions data for the given dimension.
	 *
	 * @param toolId: The bean containing the search parameters
	 * 
	 * @return HashMap<String, DimensionDTO> -- A Map with DimesionId as key and Primary Dimension data as value.
	 *
	 */
	public HashMap<String, DimensionDTO> fetchParentDimensionData(String toolId) {
		Connection connection = null;
		PreparedStatement psDim = null;
		ResultSet rsDim = null;
		HashMap<String, DimensionDTO> dimMap = null;
		DimensionDTO dimData = null;
		String dimId = null;


		StringBuilder sqlQuery = new StringBuilder(
				"select t1.dimension_id as parent_id, t1.dimension_name as parent_name, ")
						.append("t2.dimension_id as dim_id, t2.dimension_name as dim_name, t2.description as dim_desc, ")
						.append("t2.weightage as dim_weight, t2.max_score as dim_maxscore, t2.benchmark as benchmark ")
						.append("from tabQuestionDimensions as t1 ")
						.append("left join tabQuestionDimensions as t2 on t2.parent_dimension = t1.dimension_id ")
						.append("where t2.dimension_id is not null and t2.tool_id = '").append(toolId).append("'");
		
		logger.debug("fetchParentDimensionData: SQL Query " + sqlQuery);
		
		try {
			connection = getConnection();
			psDim = connection.prepareStatement(sqlQuery.toString());
			rsDim = psDim.executeQuery();

			dimMap = new HashMap<>();
			while (rsDim.next()) {
				dimData = new DimensionDTO();
				dimId = rsDim.getString("dim_id");
				dimData.setDimensionId(dimId);
				dimData.setDimensionName(rsDim.getString("dim_name"));
				dimData.setDimensionDesc(rsDim.getString("dim_desc"));
				dimData.setParentDimId(rsDim.getString("parent_id"));
				dimData.setParentDimName(rsDim.getString("parent_name"));
				dimData.setWeightage(rsDim.getFloat("dim_weight"));
				dimData.setMaxScore(rsDim.getFloat("dim_maxscore"));
				dimData.setBenchmark(rsDim.getFloat("benchmark"));
				dimMap.put(dimId, dimData);
			}
			logger.debug("fetchParentDimensionData:  Query Result " + dimMap.size());
		} catch (SQLException exception) {
			logger.error("fetchParentDimensionData:  SQLException " + exception);
			exception.printStackTrace();
		} finally {
			closeAll(rsDim, psDim, connection);
		}

		return dimMap;

	}

	private HashMap<String, DimensionDTO> fetchPriDimensionData(String toolId) {
		Connection connection = null;
		PreparedStatement psDim = null;
		ResultSet rsDim = null;
		HashMap<String, DimensionDTO> dimMap = null;
		DimensionDTO dimData = null;
		String dimId = null;

		StringBuilder sqlQuery = new StringBuilder("select * from tabQuestionDimensions where tool_id = '")
				.append(toolId).append("' and parent_dimension is null");

		logger.debug("fetchPriDimensionData:  sqlQuery " + sqlQuery);
		try {
			connection = getConnection();
			psDim = connection.prepareStatement(sqlQuery.toString());
			rsDim = psDim.executeQuery();

			dimMap = new HashMap<>();
			while (rsDim.next()) {
				dimData = new DimensionDTO();
				dimId = rsDim.getString("dimension_id");
				dimData.setDimensionId(dimId);
				dimData.setDimensionName(rsDim.getString("dimension_name"));
				dimData.setDimensionDesc(rsDim.getString("description"));
				dimData.setWeightage(rsDim.getFloat("weightage"));
				dimData.setMaxScore(rsDim.getFloat("max_score"));
				dimData.setBenchmark(rsDim.getFloat("benchmark"));
				dimMap.put(dimId, dimData);
			}
			logger.debug("fetchPriDimensionData:  Query Result " + dimMap.size());
		} catch (SQLException exception) {
			logger.error("fetchPriDimensionData:  SQLException " + exception);
			exception.printStackTrace();
		} finally {
			closeAll(rsDim, psDim, connection);
		}

		return dimMap;

	}
	
	/**
	 * Gets the scorecard details for all the dashboard based on the search
	 * parameters.
	 *
	 * @param searchData: The bean containing the search parameters
	 * 
	 * @return ArrayList<ScoreCardDTO> -- A list with the scorecard details.
	 *
	 */
	public ArrayList<ScoreCardDTO> getScoreCardDetails(ScoreCardSearchDTO searchData) {
		ArrayList<ScoreCardDTO> scoreCardAllList = new ArrayList<ScoreCardDTO>();
		ArrayList<ScoreCardDTO> scoreCardList = null;
		try {
			ArrayList<MetricSearchDTO> metricList = searchData.getMetricList();
			if (metricList != null && metricList.size() > 0) {
				for (Iterator iterator = metricList.iterator(); iterator.hasNext();) {
					MetricSearchDTO metricSearchData = (MetricSearchDTO) iterator.next();
					scoreCardList = getScoreCardDetailsForOneMetric(searchData, metricSearchData);
					if (scoreCardList != null && scoreCardList.size() > 0) {
						scoreCardAllList.addAll(scoreCardList);
					}
				}
			}
		} catch (SQLException exception) {
			logger.error("Exception in ScoreCardDao " + exception);
			exception.printStackTrace();
		}
		logger.debug("getScoreCardDetails:  Results Size For all Metrics" + scoreCardAllList.size());
		return scoreCardAllList;
	}
	
	/**
	 * Gets the scorecard details for all the dashboard based on the search parameters.
	 *
	 * @param searchData: The bean containing the search parameters
	 * 
	 * @return ArrayList<ScoreCardDTO> -- A list with the scorecard details.
	 *
	 *//*
	public ArrayList<ScoreCardDTO> getScoreCardDetails(ScoreCardSearchDTO searchData) {
		Connection connection = null;
		PreparedStatement psScoreCardData = null;
		ResultSet rsScoreCardData = null;

		ScoreCardDTO scoreCardData = null;
		ArrayList<ScoreCardDTO> scoreCardList = null;
		
		
		try {

			StringBuilder selectClause = new StringBuilder("select sc.score_card_id, sc.metric_name, sc.metric_value, sc.entity_id, ")
							.append("sc.entity_type, sc.dimension_id, sc.dimension_type, sc.question_id, sc.parent_score_card_type, ")
							.append("sc.child_score_card_type, sc.label, sc.benchmark");
			StringBuilder fromClause = new StringBuilder(" from tabScoreCard as sc ");
			StringBuilder joinClause = new StringBuilder("");
			StringBuilder whereClause = new StringBuilder(" where sc.tool_id = '").append(searchData.getToolId())
					.append("' and sc.project_id =  '").append(searchData.getProjectId()).append("' ");
					
			if (searchData.isQuesDetailsReq()) {
				selectClause.append(", qb.question_description, qb.dimension_id");
				joinClause.append("left join tabQuestionBank as qb on sc.dimension_id = qb.question_id and sc.tool_id = qb.tool_id ");
			}
			if (searchData.isPdDetailsReq()) {
				selectClause.append(", qd.dimension_name, qd.weightage");
				joinClause.append("left join tabQuestionDimensions as qd on sc.dimension_type = '").append(CommonConstants.PD)
					.append("' and sc.dimension_id = qd.dimension_id and sc.tool_id = qd.tool_id ");
			}
			if (searchData.isUserDetailsReq()) {
				selectClause.append(", tuser.user_id, tuser.first_name, tuser.team_list");
				joinClause.append("left join tabUser as tuser on sc.entity_type = '").append(CommonConstants.USER)
					.append("' and sc.entity_id = tuser.id ");
				
			}
			if (searchData.isResponseMetricDetailsReq()) {
				selectClause.append(", rm.response_metric");
				joinClause.append("left join tabResponseMetrics as rm on sc.dimension_type = '").append(CommonConstants.RESPONSE_METRIC)
					.append("' and sc.dimension_id = rm.response_metric_id and sc.tool_id = qd.tool_id ");
			}
			ArrayList<MetricSearchDTO> metricList = searchData.getMetricList();
			if (metricList != null && metricList.size() > 0) {
				int count = 0;
				whereClause.append("and (");
				for (Iterator iterator = metricList.iterator(); iterator.hasNext();) {
					MetricSearchDTO metricSearchData = (MetricSearchDTO) iterator.next();
					if (count > 0) {
						whereClause.append(" or ");
					}
					whereClause.append("(sc.entity_type = '").append(metricSearchData.getEntityType()).append("' ");
					if (metricSearchData.isEntityIdReq) {
						whereClause.append("and sc.entity_id = '").append(metricSearchData.getEntityId()).append("' ");
					}
					whereClause.append("and sc.dimension_type = '").append(metricSearchData.getDimensionType()).append("' ");
					if (metricSearchData.isDimIdReq) {
						whereClause.append("and sc.dimension_id = '").append(metricSearchData.getDimensionId()).append("' ");
					}
					whereClause.append("and metric_name = '").append(metricSearchData.getMetricName()).append("')");
					count++;
				}
				whereClause.append(")");
			}
			
			StringBuilder sqlQuery = selectClause.append(fromClause).append(joinClause).append(whereClause);
			
			logger.debug("getScoreCardDetails: SQL Query : " + sqlQuery.toString());
			
			connection = getConnection();
			psScoreCardData = connection.prepareStatement(sqlQuery.toString());
			rsScoreCardData = psScoreCardData.executeQuery();
			scoreCardList = new ArrayList<>();
			while (rsScoreCardData.next()) {
				scoreCardData = new ScoreCardDTO();
				scoreCardData.setScoreCardId(rsScoreCardData.getString("sc.score_card_id"));
				scoreCardData.setMetricName(rsScoreCardData.getString("sc.metric_name"));
				scoreCardData.setMetricValue(rsScoreCardData.getFloat("sc.metric_value"));
				scoreCardData.setEntityId(rsScoreCardData.getString("sc.entity_id"));
				scoreCardData.setEntityType(rsScoreCardData.getString("sc.entity_type"));
				scoreCardData.setDimensionId(rsScoreCardData.getString("sc.dimension_id"));
				scoreCardData.setDimensionType(rsScoreCardData.getString("sc.dimension_type"));
				scoreCardData.setQuestionId(rsScoreCardData.getString("sc.question_id"));
				scoreCardData.setParentScoreCardType(rsScoreCardData.getString("sc.parent_score_card_type"));
				scoreCardData.setChildScoreCardType(rsScoreCardData.getString("sc.child_score_card_type"));
				scoreCardData.setLabel(rsScoreCardData.getString("sc.label"));
				scoreCardData.setBenchMark(rsScoreCardData.getFloat("sc.benchmark"));
				
				if (searchData.isQuesDetailsReq()) {
					QuestionDTO questionData = new QuestionDTO();
					if (CommonConstants.QUESTION.equals(rsScoreCardData.getString("sc.dimension_type"))) {
						questionData.setQuestionId(rsScoreCardData.getString("sc.dimension_id"));
					}
					questionData.setQuestionDesc(rsScoreCardData.getString("qb.question_description"));
					questionData.setQuestionDimension(rsScoreCardData.getString("qb.dimension_id"));
					scoreCardData.setQuestionData(questionData);
				}
				
				if (searchData.isPdDetailsReq()) {
					if (CommonConstants.PD.equalsIgnoreCase(rsScoreCardData.getString("sc.dimension_type"))) {
						DimensionDTO dimData = new DimensionDTO();
						dimData.setDimensionId(rsScoreCardData.getString("sc.dimension_id"));
						dimData.setDimensionName(rsScoreCardData.getString("qd.dimension_name"));
						dimData.setWeightage(rsScoreCardData.getFloat("qd.weightage"));
						scoreCardData.setDimensionData(dimData);
					}
				}
				
				if (searchData.isUserDetailsReq()) {
					if (CommonConstants.TEAM.equalsIgnoreCase(rsScoreCardData.getString("sc.entity_type"))) {
						TeamDetailsDTO teamData = new TeamDetailsDTO();
						teamData.setTeamId( rsScoreCardData.getString("sc.entity_id"));
						scoreCardData.setTeamData(teamData);
					} else if (CommonConstants.USER.equalsIgnoreCase(rsScoreCardData.getString("sc.entity_type"))) {
						UserDetailsDTO userData = new UserDetailsDTO();
						userData.setUserId(rsScoreCardData.getString("tuser.user_id"));
						userData.setUserFirstName(rsScoreCardData.getString("tuser.first_name"));
						userData.setUserTeamId(rsScoreCardData.getString("tuser.team_list"));
						scoreCardData.setUserData(userData);
					}
				}
				
				scoreCardList.add(scoreCardData);
			}
			logger.debug("getScoreCardDetails:  Query Results Size " + scoreCardList.size());
			
		} catch (SQLException exception) {
			logger.error("Exception in ScoreCardDao " + exception);
			exception.printStackTrace();
		} finally {
			closeAll(rsScoreCardData, psScoreCardData, connection);
		}
		
		return scoreCardList;
	}*/
	
	/**
	 * Gets the scorecard details for all the dashboards for each distinct metric
	 *
	 * @param searchData: The bean containing the search parameters
	 * 
	 * @return ArrayList<ScoreCardDTO> -- A list with the scorecard details.
	 *
	 */
	private ArrayList<ScoreCardDTO> getScoreCardDetailsForOneMetric(ScoreCardSearchDTO searchData,
			MetricSearchDTO metricSearchData) throws SQLException {
		Connection connection = null;
		PreparedStatement psScoreCardData = null;
		ResultSet rsScoreCardData = null;

		ScoreCardDTO scoreCardData = null;
		ArrayList<ScoreCardDTO> scoreCardList = null;

		try {

			StringBuilder selectClause = new StringBuilder(
					"select sc.score_card_id, sc.metric_name, sc.metric_value, sc.entity_id, ").append(
							"sc.entity_type, sc.dimension_id, sc.dimension_type, sc.question_id, sc.parent_score_card_type, ")
							.append("sc.child_score_card_type, sc.label, sc.benchmark");
			StringBuilder fromClause = new StringBuilder(" from tabScoreCard as sc ");
			StringBuilder joinClause = new StringBuilder("");
			StringBuilder whereClause = new StringBuilder(" where sc.tool_id = '").append(searchData.getToolId())
					.append("' and sc.project_id =  '").append(searchData.getProjectId()).append("' ");

		
			if (CommonConstants.QUESTION.equals(metricSearchData.getDimensionType())) {
				selectClause.append(", qb.question_description, qb.dimension_id");
				joinClause.append(
						"left join tabQuestionBank as qb on sc.dimension_id = qb.question_id and sc.tool_id = qb.tool_id ");
			} else if ((CommonConstants.PD.equals(metricSearchData.getDimensionType())
					|| CommonConstants.SD.equals(metricSearchData.getDimensionType()))) {
				selectClause.append(", qd.dimension_name, qd.weightage");
				joinClause.append("left join tabQuestionDimensions as qd on sc.dimension_id = qd.dimension_id "
						+ "and sc.tool_id = qd.tool_id ");
			} else if (CommonConstants.RESPONSE_METRIC.equals(metricSearchData.getDimensionType())) {
				selectClause.append(", qb.question_description, qb.dimension_id");
				joinClause.append("left join tabQuestionBank as qb on sc.dimension_id = qb.response_metric_id and "
						+ "sc.question_id = qb.question_id and sc.tool_id = qb.tool_id ");
				selectClause.append(", qd.dimension_name, qd.weightage");
				joinClause.append(
						"left join tabQuestionDimensions as qd on qb.dimension_id = qd.dimension_id and qd.tool_id = qb.tool_id ");
				selectClause.append(", rm.response_metric");
				joinClause.append("left join tabResponseMetrics as rm on sc.dimension_type = '")
						.append(CommonConstants.RESPONSE_METRIC)
						.append("' and sc.dimension_id = rm.response_metric_id ");

			}

			if (CommonConstants.USER.equals(metricSearchData.getEntityType())) {
				selectClause.append(", tuser.user_id, tuser.first_name, tuser.team_list");
				joinClause.append("left join tabUser as tuser on sc.entity_type = '").append(CommonConstants.USER)
						.append("' and sc.entity_id = tuser.id ");

			}

			whereClause.append("and (sc.entity_type = '").append(metricSearchData.getEntityType()).append("' ");
			if (metricSearchData.isEntityIdReq) {
				whereClause.append("and sc.entity_id = '").append(metricSearchData.getEntityId()).append("' ");
			}
			whereClause.append("and sc.dimension_type = '").append(metricSearchData.getDimensionType()).append("' ");
			if (metricSearchData.isDimIdReq) {
				whereClause.append("and sc.dimension_id = '").append(metricSearchData.getDimensionId()).append("' ");
			}
			whereClause.append("and metric_name = '").append(metricSearchData.getMetricName()).append("')");

			StringBuilder sqlQuery = selectClause.append(fromClause).append(joinClause).append(whereClause);

			logger.debug("getScoreCardDetails: SQL Query : " + sqlQuery.toString());

			connection = getConnection();
			psScoreCardData = connection.prepareStatement(sqlQuery.toString());
			rsScoreCardData = psScoreCardData.executeQuery();
			scoreCardList = new ArrayList<>();
			while (rsScoreCardData.next()) {
				scoreCardData = new ScoreCardDTO();
				scoreCardData.setScoreCardId(rsScoreCardData.getString("sc.score_card_id"));
				scoreCardData.setMetricName(rsScoreCardData.getString("sc.metric_name"));
				scoreCardData.setMetricValue(rsScoreCardData.getFloat("sc.metric_value"));
				scoreCardData.setEntityId(rsScoreCardData.getString("sc.entity_id"));
				scoreCardData.setEntityType(rsScoreCardData.getString("sc.entity_type"));
				scoreCardData.setDimensionId(rsScoreCardData.getString("sc.dimension_id"));
				scoreCardData.setDimensionType(rsScoreCardData.getString("sc.dimension_type"));
				scoreCardData.setQuestionId(rsScoreCardData.getString("sc.question_id"));
				scoreCardData.setParentScoreCardType(rsScoreCardData.getString("sc.parent_score_card_type"));
				scoreCardData.setChildScoreCardType(rsScoreCardData.getString("sc.child_score_card_type"));
				scoreCardData.setLabel(rsScoreCardData.getString("sc.label"));
				scoreCardData.setBenchMark(rsScoreCardData.getFloat("sc.benchmark"));

				if (CommonConstants.QUESTION.equals(rsScoreCardData.getString("sc.dimension_type"))) {
					QuestionDTO questionData = new QuestionDTO();
					questionData.setQuestionId(rsScoreCardData.getString("sc.dimension_id"));
					questionData.setQuestionDesc(rsScoreCardData.getString("qb.question_description"));
					questionData.setQuestionDimension(rsScoreCardData.getString("qb.dimension_id"));
					scoreCardData.setQuestionData(questionData);
				} else if (CommonConstants.PD.equals(rsScoreCardData.getString("sc.dimension_type"))
						|| CommonConstants.SD.equals(rsScoreCardData.getString("sc.dimension_type"))) {
					DimensionDTO dimData = new DimensionDTO();
					dimData.setDimensionId(rsScoreCardData.getString("sc.dimension_id"));
					dimData.setDimensionName(rsScoreCardData.getString("qd.dimension_name"));
					dimData.setWeightage(rsScoreCardData.getFloat("qd.weightage"));
					scoreCardData.setDimensionData(dimData);
				} else if (CommonConstants.RESPONSE_METRIC.equals(rsScoreCardData.getString("sc.dimension_type"))) {
					QuestionDTO questionData = new QuestionDTO();
					questionData.setQuestionId(rsScoreCardData.getString("sc.question_id"));
					questionData.setQuestionDesc(rsScoreCardData.getString("qb.question_description"));
					questionData.setQuestionDimension(rsScoreCardData.getString("qb.dimension_id"));
					scoreCardData.setQuestionData(questionData);
					DimensionDTO dimData = new DimensionDTO();
					dimData.setDimensionId(rsScoreCardData.getString("qb.dimension_id"));
					dimData.setDimensionName(rsScoreCardData.getString("qd.dimension_name"));
					dimData.setWeightage(rsScoreCardData.getFloat("qd.weightage"));
					scoreCardData.setDimensionData(dimData);
					ResponseMetricDTO rmData = new ResponseMetricDTO();
					rmData.setResponseMetricId(rsScoreCardData.getString("sc.dimension_id"));
					rmData.setResponseMetricName(rsScoreCardData.getString("rm.response_metric"));
					scoreCardData.setResponseMetricData(rmData);
				}

				if (CommonConstants.TEAM.equalsIgnoreCase(rsScoreCardData.getString("sc.entity_type"))) {
					TeamDetailsDTO teamData = new TeamDetailsDTO();
					teamData.setTeamId(rsScoreCardData.getString("sc.entity_id"));
					scoreCardData.setTeamData(teamData);
				} else if (CommonConstants.USER.equalsIgnoreCase(rsScoreCardData.getString("sc.entity_type"))) {
					UserDetailsDTO userData = new UserDetailsDTO();
					userData.setUserId(rsScoreCardData.getString("tuser.user_id"));
					userData.setUserFirstName(rsScoreCardData.getString("tuser.first_name"));
					userData.setUserTeamId(rsScoreCardData.getString("tuser.team_list"));
					scoreCardData.setUserData(userData);
				}
				scoreCardList.add(scoreCardData);
			}
			logger.debug("getScoreCardDetails:  Query Results Size " + scoreCardList.size());

		} finally {
			closeAll(rsScoreCardData, psScoreCardData, connection);
		}

		return scoreCardList;
	}
}
