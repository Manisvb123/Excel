package com.merit.tlgapp.dbutility;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import com.merit.tlgapp.utility.ResultSetConverter;

public class DBUtility {
	static Logger szLogger = LogManager.getLogger(DBUtility.class.getName());

	// JDBC Driver Name & Database URL
	static String JDBC_DB_URL = null;
	static String USER_NAME = null;
	static String PASSWORD = null;
	static InputStream inputStream = null;
	Connection connObj = null;
	PreparedStatement pstmtObj = null;

	static {
		try {
			Properties prop = new Properties();
			String propFileName = "tlgconfig.properties";
			inputStream = DBUtility.class.getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			JDBC_DB_URL = prop.getProperty("tlg.dburl");
			szLogger.debug("JDBC_DB_URL::" + JDBC_DB_URL);
			USER_NAME = prop.getProperty("tlg.dbusername");
			szLogger.debug("USER_NAME::" + USER_NAME);
			PASSWORD = prop.getProperty("tlg.dbpassword");
			szLogger.debug("PASSWORD::>" + PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws Exception {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(JDBC_DB_URL, USER_NAME, PASSWORD);
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		}
		return con;
	}

	public static JSONArray getPage(String projectID, String toolID, String questionnaireID, String pageID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select * from tabPages where tool_id ='" + toolID + "' and questionnaire_id ='" + questionnaireID
					+ "' and page_id ='" + pageID + "' ORDER BY question_no ";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getResponseMetric(String metricID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select response_metric from tabResponseMetrics where response_metric_id ='" + metricID + "'";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getDimension(int dimID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select sd.dimension_name as dimension_name, sd.parent_dimension as parent_dimension, pd.dimension_name as parent_dimension_name from tabQuestionDimensions as sd JOIN tabQuestionDimensions as pd on sd.parent_dimension = pd.dimension_id where sd.dimension_id ='"
					+ dimID + "'";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getUserResponse(String userID, String toolID, String projectID, String questionnaireID,
			String pageID, String questID, int priDim, int secDim, String resMetricID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select option_id from tabQuestionnaireResponses where user_id='" + userID + "' and tool_id='"
					+ toolID + "' and project_id='" + projectID + "' and questionnaire_id='" + questionnaireID + "' and page_id='"
					+ pageID + "' and question_id='" + questID + "' and primary_dimension_id=" + priDim
					+ " and secondary_dimension_id=" + secDim + " and response_metric_id = '" + resMetricID
					+ "' and entity_type='Question' and entity_id='" + questID + "'";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getUserOptionScore(String toolID, String questionnaireID, String questID, String resMetricID, int optionID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select score from tabUserOptionScore where tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and question_id='" + questID
					+ "' and response_metric_id='"	+ resMetricID + "' and option_id='" + optionID + "'";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getQuestionMaxScore(String toolID, String questionnaireID, String pageID, String questID,
			int secDimID, String responseMetricID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "SELECT max_score FROM tabPages WHERE tool_id='" + toolID + "' AND  questionnaire_id='" + questionnaireID
					+ "' AND page_id='" + pageID + "' AND question_id='" + questID + "'  AND dimension_id=" + secDimID
					+ " AND response_metric_id='" + responseMetricID + "'";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static int updateInsertUserResponse(String userID, String teamID, String toolID, String projectID,
			String questionnaireID, String pageID, String questID, int priDim, int secDim, String resMetricID, String respType,
			int optionID, String optionLabel, float score, float per_score, float max_score) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		int szRes;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "update tabQuestionnaireResponses set number_of_updates = number_of_updates + 1, option_id='"
					+ optionID + "', option_label='" + optionLabel + "', score='" + score + "', percentage_score='"
					+ per_score + "', max_score='" + max_score
					+ "', last_modified_date=current_timestamp() where user_id='" + userID + "' and tool_id='" + toolID
					+ "' and project_id='" + projectID + "' and questionnaire_id='" + questionnaireID + "' and page_id='" + pageID
					+ "' and question_id='" + questID + "' and primary_dimension_id=" + priDim
					+ " and secondary_dimension_id=" + secDim
					+ " and response_metric_id = '"	+ resMetricID + "' and entity_type='Question' and entity_id='" + questID + "'";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeUpdate(szQuery);
			if (szRes == 0) {
				szQuery = "insert into tabQuestionnaireResponses values('" + questionnaireID + "','" + toolID + "','"
						+ projectID + "','" + userID + "','" + teamID + "',(select job_role from tabUser where id="
						+ userID + "),'" + questID + "'," + priDim + "," + secDim + ",'" + respType
						+ "','" + resMetricID + "'," + optionID + "," + score + "," + per_score + "," + max_score
						+ ",'draft',1,current_timestamp(),current_timestamp(),current_timestamp(),current_timestamp(),'"
						+ optionLabel + "','Question','" + pageID + "','" + questID + "')";
				szLogger.debug("szQuery::" + szQuery);
				szRes = szStmnt.executeUpdate(szQuery);
				szLogger.debug("Response inserted");
			} else {
				szLogger.debug("Response updated");
			}
			return szRes;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getDashboardList(String projectID, String toolID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		JSONArray szResultJSON = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select * from tabDashboardData where tool_id ='" + toolID + "' and project_id ='" + projectID
					+ "' ORDER BY tool_id ";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			szResultJSON = ResultSetConverter.convert(szRes);
			szLogger.debug("Dashboard data----#######------" + szResultJSON);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getQuestionnaire(String toolID, String questionnaireID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		JSONArray szResultJSON = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select * from tabQuestionnaire where tool_id='" + toolID + "' and questionnaire_id ='" + questionnaireID + "'";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			szResultJSON = ResultSetConverter.convert(szRes);
			szLogger.debug("szResultJSON::" + szResultJSON);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getPageDetails(String toolID, String questionnaireID, String pageID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		JSONArray szResultJSON = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select * from tabQuestionnaire where tool_id='" + toolID + "' and questionnaire ='" + questionnaireID + "' and page_id='" + pageID + "'";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			szResultJSON = ResultSetConverter.convert(szRes);
			szLogger.debug("szResultJSON::" + szResultJSON);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getToolVersion(String toolID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		JSONArray szResultJSON = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select version from tabTools where tool_id='" + toolID + "'";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			szResultJSON = ResultSetConverter.convert(szRes);
			szLogger.debug("szResultJSON::" + szResultJSON);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static int submitQuestionnaire(String toolID, String questionnaireID, String projectID, String userID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		int submitStatus = -1;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "UPDATE tabQuestionnaireResponses set status='submit' WHERE questionnaire_id='" + questionnaireID + "' "
					+ "AND tool_id='" + toolID + "' AND project_id ='" + projectID + "' AND user_id='" + userID + "'";
			szLogger.debug("szQuery::" + szQuery);
			submitStatus = szStmnt.executeUpdate(szQuery);
			return submitStatus;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getUserDetail(String username, String password) throws Exception {

		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		JSONArray szResultJSON = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select * from tabUser where user_id='" + username + "' and password='" + password + "'";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			szResultJSON = ResultSetConverter.convert(szRes);
			szLogger.debug("szResultJSON::" + szResultJSON);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray getOrganizationID(String projectID) throws Exception {

		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		JSONArray szResultJSON = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select * from  tabProject where project_id='" + projectID + "' ";
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			szResultJSON = ResultSetConverter.convert(szRes);
			szLogger.debug("szResultJSON::" + szResultJSON);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
public static JSONArray getProjectList(String userID) throws Exception {
		
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		JSONArray szResultJSON = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			String currentDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
			if(userID==null){
			szQuery = "select * from  tabProject ";
			}else {
			szQuery = "select * from  tabProject where project_id in( select project_id from tabProjectMembers where user_id='" + userID + "') AND status='active' AND end_date_time > '"+currentDate+"' ";
			}
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			szResultJSON = ResultSetConverter.convert(szRes);
			szLogger.debug("szResultJSON::" + szResultJSON);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
public static JSONArray getToolList(String projectID) throws Exception {
		
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		JSONArray szResultJSON = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			if(projectID==null){
			szQuery = "select * from  tabTools ";
			}else {
			szQuery = "select t.version ,pt.scenario_id,pt.scenario_name,t.owner,t.tool_name,t.tool_id,t.description,t.creation_date,t.benchmark,t.last_modified_date,t.description_url  from  tabTools t JOIN tabProjectTools pt on t.tool_id=pt.tool_id  where t.tool_id in( select pt.tool_id from tabProjectTools  where project_id='" + projectID + "' ) ";
			}
			szLogger.debug("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			szResultJSON = ResultSetConverter.convert(szRes);
			//szLogger.debug("szResultJSON::" + szResultJSON);
			return szResultJSON;
		} catch (Exception e) {
			szLogger.error("Error::" + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (szStmnt != null)
					szStmnt.close();
				szStmnt = null;
				if (szRes != null)
					szRes.close();
				szRes = null;
				if (szDBCon != null)
					szDBCon.close();
				szDBCon = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


public static int registerOrg(String name,String contact, String description, String companyURL) throws Exception{
	Connection szDBCon = null;
	Statement szStmnt = null;
	String szQuery;
	int szRes;
	try {
		szDBCon = getConnection();
		szStmnt = szDBCon.createStatement();
		String creationDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		String latModificationDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        szQuery = "insert into tabOrganization values('" + name + "','" + name +"','" + description + "','" + companyURL + "','" + contact + "','" + creationDate + "','" + latModificationDate + "')";;
		szLogger.debug("szQuery::" + szQuery);
		szRes = szStmnt.executeUpdate(szQuery);
		szLogger.debug("Response inserted");
		return szRes;
	} catch (Exception e) {
		szLogger.error("Error::" + e.toString());
		e.printStackTrace();
		throw e;
	} finally {
		try {
			if (szStmnt != null)
				szStmnt.close();
			szStmnt = null;
			
			if (szDBCon != null)
				szDBCon.close();
			szDBCon = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public static int updateOrg(String orgID, String name,String contact, String description, String companyURL) throws Exception{
	Connection szDBCon = null;
	Statement szStmnt = null;
	String szQuery;
	int szRes;
	try {
		szDBCon = getConnection();
		szStmnt = szDBCon.createStatement();
		//String creationDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		String latModificationDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		szQuery = "UPDATE tabOrganization set name='" + name + "',description='" + description + "', company_url='" + companyURL + "',contact='" + contact + "',last_modified_date='" + latModificationDate + "' where organization_id ='" + orgID + "'";
		szLogger.debug("szQuery::" + szQuery);
		szRes = szStmnt.executeUpdate(szQuery);
		szLogger.debug("Response inserted");
		return szRes;
	} catch (Exception e) {
		szLogger.error("Error::" + e.toString());
		e.printStackTrace();
		throw e;
	} finally {
		try {
			if (szStmnt != null)
				szStmnt.close();
			szStmnt = null;
			
			if (szDBCon != null)
				szDBCon.close();
			szDBCon = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
}
