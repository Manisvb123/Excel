package com.merit.tlg.dashboard.persistance;

import java.util.ArrayList;

import com.merit.tlg.dashboard.dto.DashboardDTO;

/**
 * This interface is used for performing the CRUD operations on the Dashboard Table in the database
 * 
 */
public interface DashboardDao {
	
	/**
	 * Inserts the given dashboard details in the dashboard table in the database
	 * 
	 * @param dashboardDto: The dashboard details.
	 * 
	 * @return boolean -- A flag indicating if the insert is successful
	 *
	 */
	public boolean InsertDashboardDetails(DashboardDTO dashboardDto);
	
	/**
	 * Updates the given dashboard details in the dashboard table in the database
	 * 
	 * @param dashboardDto: The dashboard details.
	 * 
	 * @return boolean -- A flag indicating if the update is successful
	 *
	 */
	public boolean UpdateDashboardDetails(DashboardDTO dashboardDto);
	
	/**
	 * Updates the Json generation time in the dashboard table in the database
	 * 
	 * @param dashboardDto: The dashboard details.
	 * 
	 * @return boolean -- A flag indicating if the update is successful
	 *
	 */
	public boolean UpdateDashboardGenerationTime(DashboardDTO dashboardDto);
	
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
	public DashboardDTO fetchDashboardDetails(String orgId, String toolId, String projectId, String dashboardId);
	
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
	public ArrayList<DashboardDTO> fetchAllDashboards(String orgId, String toolId, String projectId);
	

}
