package com.merit.tlgapp.utility;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResultSetConverter {

	static Logger szLogger = LogManager.getLogger(ResultSetConverter.class.getName());

	public static JSONArray convert(ResultSet rs) {
		try {
			JSONArray json = new JSONArray();
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				int numColumns = rsmd.getColumnCount();
				JSONObject obj = new JSONObject();
				for (int i = 1; i < numColumns + 1; i++) {
					String column_name = rsmd.getColumnLabel(i);
					if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
						obj.put(column_name, rs.getArray(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
						obj.put(column_name, rs.getInt(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
						obj.put(column_name, rs.getBoolean(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
						obj.put(column_name, rs.getBlob(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
						obj.put(column_name, rs.getDouble(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
						obj.put(column_name, rs.getFloat(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
						obj.put(column_name, rs.getInt(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
						obj.put(column_name, rs.getNString(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
						if (rs.getString(i) == null) {
							obj.put(column_name, "");
						} else {
							obj.put(column_name, rs.getString(i));
						}
					} else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
						obj.put(column_name, rs.getInt(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
						obj.put(column_name, rs.getInt(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
						obj.put(column_name, rs.getDate(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
						obj.put(column_name, rs.getTimestamp(i));
					} else {
						obj.put(column_name, rs.getObject(i));
					}
				}
				json.put(obj);
			}
			return json;
		} catch (Exception e) {
			szLogger.error("error::" + e.toString());
			e.printStackTrace();
		}
		return null;
	}
}
