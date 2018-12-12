package com.merit.tlg.dashboard.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.dto.DashboardDTO;
import com.merit.tlg.dashboard.persistance.common.BaseDaoImpl;
import com.merit.tlg.dashboard.utils.CommonUtils;

/**
 * This class is used for performing the CRUD operations on the Dashboard Table in the database
 * 
 */
public class DashboardDaoImpl extends BaseDaoImpl implements DashboardDao {
	final static Logger logger = Logger.getLogger(DashboardDaoImpl.class);

	/**
	 * Inserts the given dashboard details in the dashboard table in the database
	 * 
	 * @param dashboardDto: The dashboard details.
	 * 
	 * @return boolean -- A flag indicating if the insert is successful
	 *
	 */
	public boolean InsertDashboardDetails(DashboardDTO dashboardDto) {
		boolean insertFlag = false;

		Connection connection = null;
		PreparedStatement psDashboardData = null;

		try {

			StringBuilder sqlQuery = new StringBuilder("Insert into tabDashboardData (organization_id, tool_id, ")
					.append("project_id, dashboard_id, dashboard_name, dashboard_json_template, dashboard_json, status,")
					.append("analysis_date,generation_date,creation_date, last_modified_date) ")
					.append("values(?,?,?,?,?,?,?,?,?,?,?,?)");

			logger.debug("InsertDashboardDetails: SQL Query  " + sqlQuery);

			connection = getConnection();
			psDashboardData = connection.prepareStatement(sqlQuery.toString());
			psDashboardData.setString(1, dashboardDto.getOrgdId());
			psDashboardData.setString(2, dashboardDto.getToolId());
			psDashboardData.setString(3, dashboardDto.getProjectId());
			psDashboardData.setString(4, dashboardDto.getDashboardId());
			psDashboardData.setString(5, dashboardDto.getDashboardName());
			psDashboardData.setString(6, dashboardDto.getDashboardJsonTemplate());
			psDashboardData.setString(7, dashboardDto.getDashboardJsonUrl());
			psDashboardData.setString(8, dashboardDto.getStatus());
			psDashboardData.setTimestamp(9, null);
			psDashboardData.setTimestamp(10, CommonUtils.getCurrentTimeStamp());
			psDashboardData.setTimestamp(11, CommonUtils.getCurrentTimeStamp());
			psDashboardData.setTimestamp(12, CommonUtils.getCurrentTimeStamp());

			psDashboardData.execute();
			insertFlag = true;
			logger.debug("InsertDashboardDetails: Successfully inserted dashboard data " + sqlQuery);

		} catch (SQLException exception) {
			logger.error("InsertDashboardDetails: SQLException  " + exception);
			exception.printStackTrace();
		} finally {
			logger.debug("InsertDashboardDetails: Closing All Connections ");
			closeAll(null, psDashboardData, connection);
		}

		return insertFlag;
	}

	/**
	 * Updates the given dashboard details in the dashboard table in the database
	 * 
	 * @param dashboardDto: The dashboard details.
	 * 
	 * @return boolean -- A flag indicating if the update is successful
	 *
	 */
	public boolean UpdateDashboardDetails(DashboardDTO dashboardDto) {
		boolean updateFlag = false;

		Connection connection = null;
		PreparedStatement psDashboardData = null;

		try {

			StringBuilder sqlQuery = new StringBuilder("Update tabDashboardData set (").append("dashboard_name = '")
					.append(dashboardDto.getDashboardId()).append("', ").append("dashboard_json_template = '")
					.append(dashboardDto.getDashboardJsonTemplate()).append("', ").append("dashboard_json = '")
					.append(dashboardDto.getDashboardJsonUrl()).append("', ").append("status = '")
					.append(dashboardDto.getStatus()).append("', ").append("generation_date = '")
					.append(CommonUtils.getCurrentTimeStamp()).append("', ").append("last_modified_date = '")
					.append(CommonUtils.getCurrentTimeStamp()).append("') where ").append("organization_id = '")
					.append(dashboardDto.getOrgdId()).append("' and ").append("tool_id = '")
					.append(dashboardDto.getToolId()).append("' and ").append("project_id = '")
					.append(dashboardDto.getProjectId()).append("' and ").append("dashboard_id = '")
					.append(dashboardDto.getDashboardId()).append("'");

			logger.debug("UpdateDashboardDetails: SQL Query " + sqlQuery);

			connection = getConnection();
			psDashboardData = connection.prepareStatement(sqlQuery.toString());
			psDashboardData.execute();
			updateFlag = true;
			logger.debug("UpdateDashboardDetails: Successfully Updated data ");

		} catch (SQLException exception) {
			logger.error("UpdateDashboardDetails: SQLException :  " + exception);
			exception.printStackTrace();
		} finally {
			logger.debug("UpdateDashboardDetails: Closing All Connections ");
			closeAll(null, psDashboardData, connection);
		}

		return updateFlag;
	}

	/**
	 * Updates the Json generation time in the dashboard table in the database
	 * 
	 * @param dashboardDto: The dashboard details.
	 * 
	 * @return boolean -- A flag indicating if the update is successful
	 *
	 */
	public boolean UpdateDashboardGenerationTime(DashboardDTO dashboardDto) {
		boolean updateFlag = false;

		Connection connection = null;
		PreparedStatement psDashboardData = null;

		try {

			StringBuilder sqlQuery = new StringBuilder(
					"Update tabDashboardData set generation_date = ?, last_modified_date = ? where ")
							.append("organization_id = '").append(dashboardDto.getOrgdId()).append("' and ")
							.append("tool_id = '").append(dashboardDto.getToolId()).append("' and ")
							.append("project_id = '").append(dashboardDto.getProjectId()).append("' and ")
							.append("dashboard_id = '").append(dashboardDto.getDashboardId()).append("'");

			logger.debug("UpdateDashboardGenerationTime: SQL Query " + sqlQuery);

			connection = getConnection();
			psDashboardData = connection.prepareStatement(sqlQuery.toString());
			psDashboardData.setTimestamp(1, CommonUtils.getCurrentTimeStamp());
			psDashboardData.setTimestamp(2, CommonUtils.getCurrentTimeStamp());
			psDashboardData.execute();
			updateFlag = true;
			logger.debug("UpdateDashboardGenerationTime: Update of time successful " + updateFlag);
		} catch (SQLException exception) {
			logger.error("UpdateDashboardGenerationTime: SQLException " + exception);
			exception.printStackTrace();
		} finally {
			logger.debug("UpdateDashboardGenerationTime: Closing All Connections ");
			closeAll(null, psDashboardData, connection);
		}

		return updateFlag;
	}

	/**
	 * Fetches the dashboard details for the given parameters from the dashboard table in the database
	 *
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id
	 * @param dashboardId: The dashboard id.
	 * 
	 * @return DashboardDTO -- A bean with the dashboard details.
	 *
	 */
	public DashboardDTO fetchDashboardDetails(String orgId, String toolId, String projectId, String dashboardId) {
		Connection connection = null;
		PreparedStatement psDashboardData = null;
		ResultSet rsDashboardData = null;
		DashboardDTO dashboardDetails = null;

		try {

			StringBuilder sqlQuery = new StringBuilder("Select * from tabDashboardData where ")
					.append("organization_id = '").append(orgId).append("' and ").append("tool_id = '").append(toolId)
					.append("' and ").append("project_id = '").append(projectId).append("' and ")
					.append("dashboard_id = '").append(dashboardId).append("'");

			logger.debug("fetchDashboardDetails: SQL Query " + sqlQuery);

			connection = getConnection();
			psDashboardData = connection.prepareStatement(sqlQuery.toString());
			rsDashboardData = psDashboardData.executeQuery();

			while (rsDashboardData.next()) {
				dashboardDetails = new DashboardDTO();
				dashboardDetails.setOrgdId(orgId);
				dashboardDetails.setToolId(toolId);
				dashboardDetails.setProjectId(projectId);
				dashboardDetails.setDashboardId(dashboardId);
				dashboardDetails.setDashboardName(rsDashboardData.getString("dashboard_name"));
				dashboardDetails.setDashboardJsonTemplate(rsDashboardData.getString("dashboard_json_template"));
				dashboardDetails.setDashboardJsonUrl(rsDashboardData.getString("dashboard_json"));
				dashboardDetails.setStatus(rsDashboardData.getString("status"));
				dashboardDetails.setAnalysisDate(rsDashboardData.getTimestamp("analysis_date"));
				dashboardDetails.setGenerationDate(rsDashboardData.getTimestamp("generation_date"));
				dashboardDetails.setCreationDate(rsDashboardData.getTimestamp("creation_date"));
				dashboardDetails.setModifiedDate(rsDashboardData.getTimestamp("last_modified_date"));
			}

			logger.debug("fetchDashboardDetails: Dashboard Data " + dashboardDetails);

		} catch (SQLException exception) {
			logger.error("fetchDashboardDetails: SQLException " + exception);
			exception.printStackTrace();
		} finally {
			logger.debug("fetchDashboardDetails: Closing All Connections ");
			closeAll(rsDashboardData, psDashboardData, connection);
		}

		return dashboardDetails;

	}

	/**
	 * Fetches all the dashboards for the given parameters from the dashboard table in the database
	 *
	 * @param orgId: The organization Id.
	 * @param toolId: The tool Id.
	 * @param projectId: The project Id
	 * 
	 * @return ArrayList<DashboardDTO> -- A list of dashboard details.
	 *
	 */
	public ArrayList<DashboardDTO> fetchAllDashboards(String orgId, String toolId, String projectId) {
		ArrayList<DashboardDTO> dashBoards = null;
		return dashBoards;
	}

}
