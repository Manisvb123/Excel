package com.merit.tlg.dashboard.persistance.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.merit.tlg.dashboard.utils.CommonConstants;
import com.merit.tlg.dashboard.utils.CommonUtils;
import com.merit.tlg.dashboard.utils.TlgProperties;



public class BaseDaoImpl {

	private static DataSource dataSource;

	/**
	 * Returns a connection object.
	 * 
	 * @return Returns an object of Connection object.
	 */
	protected Connection getConnection() {
		Connection connection = null;

		try {
			
			/*if (dataSource == null)
				dataSource = initDataSource();
			connection = dataSource.getConnection();*/
			String dbUserName = TlgProperties.getInstance().getProperty("tlg.dbusername");
			String dbPwd = TlgProperties.getInstance().getProperty("tlg.dbpassword");
			String dbUrl = TlgProperties.getInstance().getProperty("tlg.dburl");
			//For JDBC Connection to test local API's to be remove later
			String myDriver = "com.mysql.cj.jdbc.Driver";
		    Class.forName(myDriver);
		    connection = DriverManager.getConnection(dbUrl, dbUserName, dbPwd);
		      
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return connection;
	}

	
	/**
	 * Initialize the data source object
	 * 
	 * @param the data source name
	 */
	protected DataSource initDataSource() {
		Context ctx = null;
		DataSource ds = null;

		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(CommonConstants.DATASOURCE_JNDI);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return ds;
	}

	/**
	 * Closes the connection, resultset and preparedstatement object.
	 * 
	 * @param Resultset         object to be closed.
	 * @param PreparedStatement object to be closed.
	 * @param connection        object to be closed.
	 * 
	 */
	protected void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {

		try {
			// Close the resultset object if not null.
			if (resultSet != null) {
				resultSet.close();
			}

			// Close the prepared Statement object if not null.
			if (preparedStatement != null) {
				preparedStatement.close();
			}

			// Close the connection object if not null.
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper method to return the sql select query, given the tablename,
	 * selectfields and whereclause
	 * 
	 * @param tableName    the name of the table
	 * @param selectFields the fields to be selected
	 * @param the          wherclause for selection
	 * 
	 * @return returns the Sql Select Query.
	 */
	protected static String getSelectSQL(String tableName, String selectFields, String whereClause) {

		StringBuffer sqlQuery = new StringBuffer();

		sqlQuery.append("SELECT ").append(selectFields).append(" FROM ").append(tableName);
		if (!CommonUtils.isEmpty(whereClause)) {
			sqlQuery.append(" WHERE ").append(whereClause);
		}
		return sqlQuery.toString();
	}

	/**
	 * Helper method to return the sql insert query, given the tablename,
	 * insertFields and insertValues
	 * 
	 * @param tableName    the name of the table
	 * @param insertFields the fields to be inserted
	 * @param insertValues the values to be inserted (? in case of Prepared
	 *                     statements)
	 * 
	 * @return returns the Sql Insert Query.
	 */
	protected static String getInsertSQL(String tableName, String insertFields, String insertValues) {

		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("INSERT INTO ").append(tableName).append(" ( ").append(insertFields).append(" ) VALUES ( ")
				.append(insertValues).append(" )");
		return sqlInsert.toString();

	}

	/**
	 * Helper method to return the sql insert query, given the tablename,
	 * insertFields and insertValues
	 * 
	 * @param tableName    the name of the table
	 * @param insertFields the fields to be inserted
	 * 
	 * @return returns the Sql Insert Query.
	 */
	protected static String getInsertSQL(String tableName, String insertFields) {

		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("INSERT INTO ").append(tableName).append(" ( ").append(insertFields).append(" ) VALUES ( ");

		int len = sqlInsert.length();
		int columnCount = 0;
		for (int i = 0; i < len; i++) {
			if (sqlInsert.charAt(i) == ',') {
				columnCount++;
			}
		}
		for (; columnCount > 0; columnCount--) {
			sqlInsert.append("?, ");
		}

		sqlInsert.append("? )");
		return sqlInsert.toString();
	}

	/**
	 * Helper method to return the sql update query, given the tablename,
	 * updateFields and whereclause
	 * 
	 * @param tableName    the name of the table
	 * @param updateFields the fields to be updated
	 * @param whereClause  the wherclause for updation
	 * 
	 * @return returns the Sql Update Query.
	 */
	protected String getUpdateSQL(String tableName, String updateFields, String whereClause) {

		StringBuffer sqlUpdate = new StringBuffer();
		sqlUpdate.append("UPDATE ").append(tableName).append(" SET ").append(updateFields);
		if (!CommonUtils.isEmpty(whereClause)) {
			sqlUpdate.append(" WHERE ").append(whereClause);
		}
		return sqlUpdate.toString();
	}

	/**
	 * Helper method to return the sql delete query, given the tablename and
	 * whereclause
	 * 
	 * @param tableName   the name of the table
	 * @param whereClause the wherclause for updation
	 * 
	 * @return returns the Sql Delete Query.
	 */
	protected String getDeleteSQL(String tableName, String whereClause) {
		StringBuffer sqlDelete = new StringBuffer();
		sqlDelete.append("DELETE FROM ").append(tableName);
		if (!CommonUtils.isEmpty(whereClause)) {
			sqlDelete.append(" WHERE ").append(whereClause);
		}
		return sqlDelete.toString();
	}

}
