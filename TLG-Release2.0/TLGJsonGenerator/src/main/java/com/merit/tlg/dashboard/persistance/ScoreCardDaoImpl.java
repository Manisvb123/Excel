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
import com.merit.tlg.dashboard.dto.ToolsDTO;
import com.merit.tlg.dashboard.dto.UserDetailsDTO;
import com.merit.tlg.dashboard.persistance.common.BaseDaoImpl;
import com.merit.tlg.dashboard.utils.CommonConstants;

/**
 * This class fetches the scorecard details for the different dashboards
 * 
 */
public class ScoreCardDaoImpl extends BaseDaoImpl implements ScoreCardDao {
	final static Logger logger = Logger.getLogger(ScoreCardDaoImpl.class);

	
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
				for (Iterator<MetricSearchDTO> iterator = metricList.iterator(); iterator.hasNext();) {
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
	 * Fetches the Primary dimensions data for the given dimension.
	 *
	 * @param toolId: The bean containing the search parameters
	 * 
	 * @return HashMap<String, DimensionDTO> -- A Map with DimesionId as key and
	 *         Primary Dimension data as value.
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
				"select t1.dimension_id as parent_id, t1.dimension_name as parent_name, ").append(
						"t2.dimension_id as dim_id, t2.dimension_name as dim_name, t2.description as dim_desc, ")
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

	public HashMap<String, DimensionDTO> fetchPriDimensionData(String toolId) {
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

		
			if (CommonConstants.TOOL.equals(metricSearchData.getDimensionType())) {
				selectClause.append(", tm.tool_name, tm.description");
				joinClause.append("left join tabTools as tm on sc.tool_id = tm.tool_id ");
			} else if (CommonConstants.QUESTION.equals(metricSearchData.getDimensionType())) {
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

				if (CommonConstants.TOOL.equals(rsScoreCardData.getString("sc.dimension_type"))) {
					ToolsDTO toolsData = new ToolsDTO();
					toolsData.setToolId(rsScoreCardData.getString("sc.dimension_id"));
					toolsData.setToolName(rsScoreCardData.getString("tm.tool_name"));
					toolsData.setDescription(rsScoreCardData.getString("tm.description"));
					scoreCardData.setToolsData(toolsData);
				} else if (CommonConstants.QUESTION.equals(rsScoreCardData.getString("sc.dimension_type"))) {
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
