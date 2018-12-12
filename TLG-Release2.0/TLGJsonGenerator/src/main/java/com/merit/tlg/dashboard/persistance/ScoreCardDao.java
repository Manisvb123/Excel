package com.merit.tlg.dashboard.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.merit.tlg.dashboard.dto.DimensionDTO;
import com.merit.tlg.dashboard.dto.ScoreCardDTO;
import com.merit.tlg.dashboard.dto.ScoreCardSearchDTO;
/**
 * This interface fetches the scorecard details for the different dashboards
 * 
 */
public interface ScoreCardDao {
	
	
	/**
	 * Gets the scorecard details for all the dashboard based on the search parameters.
	 *
	 * @param searchData: The bean containing the search parameters
	 * 
	 * @return ArrayList<ScoreCardDTO> -- A list with the scorecard details.
	 *
	 */
	public ArrayList<ScoreCardDTO> getScoreCardDetails(ScoreCardSearchDTO searchData);
	
	
	/**
	 * Fetches the Primary dimensions data for the given dimension.
	 *
	 * @param toolId: The bean containing the search parameters
	 * 
	 * @return HashMap<String, DimensionDTO> -- A Map with DimesionId as key and Primary Dimension data as value.
	 *
	 */
	public HashMap<String, DimensionDTO> fetchParentDimensionData(String toolId);
	
}

