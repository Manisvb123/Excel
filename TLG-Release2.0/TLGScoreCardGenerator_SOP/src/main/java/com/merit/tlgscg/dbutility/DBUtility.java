package com.merit.tlgscg.dbutility;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;
import org.json.JSONArray;
import com.merit.tlgscg.utility.MathUtility;
import com.merit.tlgscg.utility.ResultSetConverter;

/**
 * This class implements methods to retrieve data from database
 * @author rekha
 *
 */
public class DBUtility {

	// static Logger szLogger = LogManager.getLogger(DBUtility.class.getName());

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
			System.out.println("JDBC_DB_URL---------->" + JDBC_DB_URL);
			USER_NAME = prop.getProperty("tlg.dbusername");
			System.out.println("USER_NAME---------->" + USER_NAME);
			PASSWORD = prop.getProperty("tlg.dbpassword");
			System.out.println("PASSWORD---------->" + PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method establishes a connection with the database and returns a JDBC Connection instance
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		Connection con = null;
		try {
			con = DriverManager.getConnection(JDBC_DB_URL, USER_NAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return con;
	}

	/**
	 * This method retrieves score, percentage_score and max_score at response_metric level for all questions of a given userID and tool
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getUserElementMetricScore(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select response_metric_id, score, percentage_score, max_score, question_id from "
					+ "tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID
					+ "' and questionnaire_id='" + questionnaireID + "' and team_id='" + teamID + "' and user_id='" + userID
					+ "' and status='submit'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns sum(question_score*question_weightage) for all questions of a given userID and tool
	 * specific to SPP
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getUserElementScoreForSPP(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			// szQuery = "select exp(sum(log(coalesce((QR.score * QB.weightage),1)))) as
			// res_metric_score, QR.question_id, sum(QR.max_score) as max_score from
			// tabQuestionnaireResponses as QR JOIN tabQuestionBank as QB on
			// (QR.question_id=QB.question_id and QR.tool_id=QB.tool_id and
			// QR.response_metric=QB.response_metric_id) where QR.project_id='" + projectID
			// + "' and QR.tool_id='" + toolID + "' and QR.template_id='" + templateID + "'
			// and QR.team_id='" + teamID + "' and QR.user_id='" + userID + "' and
			// QR.status='submit' group by QR.question_id";
			szQuery = "select sum(QR.score * pag.question_weightage) as res_metric_score, QR.question_id, sum(QR.max_score) as max_score from tabQuestionnaireResponses as QR JOIN tabPages as pag on (QR.tool_id=pag.tool_id and QR.questionnaire_id=pag.questionnaire_id and QR.response_metric_id=pag.response_metric_id and QR.question_id=pag.question_id) where QR.project_id='"
					+ projectID + "' and QR.tool_id='" + toolID + "' and QR.questionnaire_id='" + questionnaireID
					+ "' and QR.team_id='" + teamID + "' and QR.user_id='" + userID
					+ "' and QR.status='submit' group by QR.question_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score, percentage_score, max_score of all questions for a given userID and tool
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getUserElementScore(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select sum(score) as score, sum(percentage_score) as percentage_score, sum(max_score) as max_score, question_id from "
					+ "tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID
					+ "' and questionnaire_id='" + questionnaireID + "' and team_id='" + teamID + "' and user_id='" + userID
					+ "' and status='submit' group by question_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score and max_score of all questions for a given userID and tool
	 * element_score = response_metric_score1 * response_metric_score2 * ... 
	 * specific to Brexit
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getSSTBrexitUserElementMetricScore(String projectID, String toolID, String questionnaireID,
			String teamID, String userID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select exp(sum(log(coalesce(score,1)))) as score, exp(sum(log(coalesce(max_score,1)))) as max_score, question_id from "
					+ "tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID
					+ "' and questionnaire_id='" + questionnaireID + "' and team_id='" + teamID + "' and user_id='" + userID
					+ "' and status='submit' group by question_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns the weightage of a given response_metric of a question
	 * @param toolID
	 * @param questionID
	 * @param responseMetricID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getResponseMetricWeight(String toolID, String questionnaireID, String questionID, String responseMetricID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select response_metric_weightage from " + "tabPages where tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and question_id='" + questionID + "' and response_metric_id='" + responseMetricID + "'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns the percentage_score of all questions of all users for a given tool
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getElementPercentScore(String projectID, String toolID, String questionnaireID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select percentage_score, question_id from " + "tabQuestionnaireResponses where project_id='"
					+ projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID
					+ "' and status='submit'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns the benchmark of a given response_metric of a question
	 * @param toolID
	 * @param questionID
	 * @param resMetricID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getResponseMetric(String toolID, String questionnaireID, String questionID, String resMetricID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select response_metric_benchmark from tabPages where " + "questionnaire_id='" + questionnaireID + "' and question_id = '" + questionID	+ "' and tool_id='" + toolID + "' and response_metric_id='" + resMetricID + "'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method inserts/updates a scorecard
	 * @param toolID
	 * @param projectID
	 * @param metricName
	 * @param metricValue
	 * @param entityID
	 * @param entityType
	 * @param dimensionID
	 * @param dimensionType
	 * @param questionID
	 * @param parentScoreCardType
	 * @param childScoreCardType
	 * @param label
	 * @param benchmark
	 * @param creationTS
	 * @param updateTS
	 * @return
	 * @throws Exception
	 */
	public static int addScoreCard(String toolID, String projectID, String metricName, double metricValue,
			String entityID, String entityType, String dimensionID, String dimensionType, String questionID,
			String parentScoreCardType, String childScoreCardType, String label, float benchmark, Timestamp creationTS,
			Timestamp updateTS) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		int szRes;
		try {
			metricValue = MathUtility.round(metricValue, 1);
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "update tabScoreCard set metric_value = " + metricValue + " where tool_id = '" + toolID
					+ "' and project_id = '" + projectID + "' and metric_name = '" + metricName + "' and entity_id = '"
					+ entityID + "' and entity_type = '" + entityType + "' and dimension_id = '" + dimensionID
					+ "' and dimension_type = '" + dimensionType + "' and question_id='" + questionID
					+ "' and parent_score_card_type = '" + parentScoreCardType + "' and child_score_card_type = '"
					+ childScoreCardType + "'";
			szRes = szStmnt.executeUpdate(szQuery);
			if (szRes == 0) {
				szQuery = "insert into tabScoreCard(tool_id,project_id, metric_name, metric_value, entity_id, "
						+ "entity_type, dimension_id, dimension_type, question_id, parent_score_card_type, "
						+ "child_score_card_type, label, benchmark, creation_date, last_modified_date) " + "values ('"
						+ toolID + "','" + projectID + "','" + metricName + "','" + metricValue + "','" + entityID
						+ "','" + entityType + "','" + dimensionID + "','" + dimensionType + "','" + questionID + "','"
						+ parentScoreCardType + "','" + childScoreCardType + "','" + label + "','" + benchmark + "','"
						+ creationTS + "','" + updateTS + "')";
				System.out.println("szQuery::" + szQuery);
				szRes = szStmnt.executeUpdate(szQuery);
				System.out.println("Scorecard inserted");
			} else {
				System.out.println("Scorecard updated");
			}
			return szRes;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score, max_score, average_score of a given userID at all primary dimensions level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getUserPrimaryDimensionScore(String projectID, String toolID, String questionnaireID,
			String teamID, String userID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select primary_dimension_id, sum(score) as pd_score, sum(max_score) as pd_max_score, avg(score) as avg_score from tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and team_id='" + teamID + "' and user_id='" + userID + "' and status='submit' group by primary_dimension_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score, max_score of a given userID at tool level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getUserToolScore(String projectID, String toolID, String questionnaireID, String teamID,
			String userID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select sum(score) as tool_score, sum(max_score) as tool_max_score from tabQuestionnaireResponses where project_id='"
					+ projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and team_id='"
					+ teamID + "' and user_id='" + userID + "' and status='submit'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score, max_score of a given userID at each reponse_metric level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getUserToolElementMetricScore(String projectID, String toolID, String questionnaireID,
			String teamID, String userID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select sum(score) as score, sum(max_score) as max_score, response_metric_id  from tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and team_id='" + teamID + "' and user_id='" + userID + "' and status='submit' group by response_metric_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score, max_score of a given organization/project at each reponse_metric level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getOrgElementMetricScore(String projectID, String toolID, String questionnaireID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select sum(score) as score, sum(max_score) as max_score, response_metric_id, question_id  from tabQuestionnaireResponses where project_id='"	+ projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID
					+ "' and status='submit' group by response_metric_id, question_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score, max_score of a given tool for all questions
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getToolElementScore(String projectID, String toolID, String questionnaireID, String teamID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select question_id, sum(score) as tool_score, sum(max_score) as tool_max_score from tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and team_id='" + teamID + "' and status='submit' group by question_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns average of percentage_score of a given tool for all questions
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getElementAveragePercentScore(String projectID, String toolID, String questionnaireID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select question_id, avg(percentage_score) as avg_perc_score from tabQuestionnaireResponses where project_id='"
					+ projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID
					+ "' and status='submit' group by question_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score, max_score of a given tool at all primary dimensions level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getTeamPrimaryDimensionScore(String projectID, String toolID, String questionnaireID,
			String teamID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select primary_dimension_id, sum(score) as pd_score, sum(max_score) as pd_max_score from tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and team_id='" + teamID + "' and status='submit' group by primary_dimension_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score, max_score of a given userID for all questions under all secondary dimensions of a given tool
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getUserSecondaryDimensionElementScore(String projectID, String toolID, String questionnaireID,
			String teamID, String userID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select secondary_dimension_id, score, max_score, question_id from tabQuestionnaireResponses where project_id='"
					+ projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and team_id='"
					+ teamID + "' and user_id=" + userID + " and status='submit'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score and max_score of a team at a tool level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @param teamID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getTeamToolScore(String projectID, String toolID, String questionnaireID, String teamID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select sum(score) as tool_score, sum(max_score) as tool_max_score from tabQuestionnaireResponses where project_id='"
					+ projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and team_id='"
					+ teamID + "' and status='submit'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score and max_score of all the questions at the organization/project level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getOrgElementScore(String projectID, String toolID, String questionnaireID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select question_id, sum(score) as org_score, sum(max_score) as org_max_score from tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and status='submit' group by question_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score and max_score of all the questions at the organization/project level
	 * specific to Brexit
	 * score = response_metric_score1 * response_metric_score2 * ...
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getSSTBrexitOrgElementScore(String projectID, String toolID, String questionnaireID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select question_id, exp(sum(log(coalesce(score,1)))) as org_score, exp(sum(log(coalesce(max_score,1)))) as org_max_score from tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID
					+ "' and status='submit' group by question_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score and max_score of a given Organization/Project at all primary dimensions level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getOrgPrimaryDimensionScore(String projectID, String toolID, String questionnaireID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select primary_dimension_id, sum(score) as pd_score, sum(max_score) as pd_max_score from tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and status='submit' group by primary_dimension_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score and max_score of a given Organization/Project at all secondary dimensions level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getOrgSecondaryDimensionScore(String projectID, String toolID, String questionnaireID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select secondary_dimension_id, sum(score) as sd_score, sum(max_score) as sd_max_score from tabQuestionnaireResponses where project_id='"	+ projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID	+ "' and status='submit' group by secondary_dimension_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score and max_score of an organization/project at the tool level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getOrgToolScore(String projectID, String toolID, String questionnaireID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select sum(score) as org_score, sum(max_score) as org_max_score from tabQuestionnaireResponses where project_id='"
					+ projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID
					+ "' and status='submit'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns the associated organization ID of a given project
	 * @param projectID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getOrgForProject(String projectID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select organization_id from tabProject where project_id='" + projectID + "'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns the weightage of a given dimension
	 * @param dimensionID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getDimensionWeight(int dimensionID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select weightage from tabQuestionDimensions where dimension_id='" + dimensionID + "'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns score and max_score of a given organization/project at each response_metric level
	 * @param projectID
	 * @param toolID
	 * @param questionnaireID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getOrgToolElementMetricScore(String projectID, String toolID, String questionnaireID)
			throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select sum(score) as score, sum(max_score) as max_score, response_metric_id  from tabQuestionnaireResponses where project_id='" + projectID + "' and tool_id='" + toolID + "' and questionnaire_id='" + questionnaireID + "' and status='submit' group by response_metric_id";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns the benchmark of a given tool
	 * @param toolID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getToolBenchmark(String toolID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select benchmark from tabTools where tool_id='" + toolID + "'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

	/**
	 * This method returns the benchmark of a given dimension
	 * @param dimensionID
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getDimensionBenchmark(String dimensionID) throws Exception {
		Connection szDBCon = null;
		Statement szStmnt = null;
		String szQuery;
		ResultSet szRes = null;
		try {
			szDBCon = getConnection();
			szStmnt = szDBCon.createStatement();
			szQuery = "select benchmark from tabQuestionDimensions where dimension_id='" + dimensionID + "'";
			System.out.println("szQuery::" + szQuery);
			szRes = szStmnt.executeQuery(szQuery);
			JSONArray szResultJSON = ResultSetConverter.convert(szRes);
			return szResultJSON;
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
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

}
