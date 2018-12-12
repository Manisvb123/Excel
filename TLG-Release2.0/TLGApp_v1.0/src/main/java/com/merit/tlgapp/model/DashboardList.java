package com.merit.tlgapp.model;

import java.util.ArrayList;

public class DashboardList {
	private String projectID;
	private String toolID;
	private ArrayList<Dashboard> dashboards;

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getToolID() {
		return toolID;
	}

	public void setToolID(String toolID) {
		this.toolID = toolID;
	}

	public ArrayList<Dashboard> getDashboards() {
		return dashboards;
	}

	public void setDashboards(ArrayList<Dashboard> dashboards) {
		this.dashboards = dashboards;
	}

	public void addDashboard(Dashboard dab) {
		if (this.dashboards == null) {
			this.dashboards = new ArrayList<Dashboard>();
		}
		this.dashboards.add(dab);
	}

	/*public Dashboard getDashboard(String dasID) {
		System.out.println("dasID----*****" + dasID);
		if (dashboards == null)
			return null;
		for (int i = 0; i < dashboards.size(); i++) {
			if (dashboards.get(i).getID().equalsIgnoreCase(dasID)) {
				return dashboards.get(i);
			}
		}
		return null;
	}*/
}