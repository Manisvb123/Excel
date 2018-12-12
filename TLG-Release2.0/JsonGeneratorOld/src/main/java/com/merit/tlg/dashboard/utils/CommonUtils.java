package com.merit.tlg.dashboard.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CommonUtils {

	/**
	 * Returns true if the string is empty and false otherwise.
	 * 
	 * @param string
	 * 
	 * @return Returns true is the string is empty and false otherwise.
	 */
	public static boolean isEmpty(String string) {
		return getSize(string) <= 0;
	}

	/**
	 * Returns the length of the given string.
	 * 
	 * @param String
	 * @return Returns the length of the given string.
	 */
	public static int getSize(String string) {
		return string != null ? string.length() : 0;
	}

	/**
	 * Returns true is the collection is empty and false otherwise.
	 * 
	 * @param Collection
	 * @return Returns true is the Collection is empty and false otherwise.
	 */
	public static boolean isEmpty(ArrayList arrayList) {
		return getSize(arrayList) <= 0;
	}

	/**
	 * Returns the size of the given collection.
	 * 
	 * @param Collection
	 * @return Returns the size of the given collection.
	 */
	public static int getSize(ArrayList arrayList) {
		return arrayList != null ? arrayList.size() : 0;
	}

	/**
	 * Check if the object is null.
	 *
	 * @return boolean value of result
	 */
	public static boolean isNull(Object object) {
		if (object != null) {
			return false;
		} else {
			return true;
		}
	}

	public static String ConvertArraytoString(String[] metricIds) {
		
		StringBuilder sbArray = new StringBuilder();
		if (metricIds != null && metricIds.length > 0) {
			for (int i = 0; i < metricIds.length; i++) {
				if (i < metricIds.length - 1) {
					sbArray.append(metricIds[i]).append(",");
				} else {
					sbArray.append(metricIds[i]);
				}
			}
		}
		return sbArray.toString();

	}

	public static Timestamp getCurrentTimeStamp() {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		sdf.format(timestamp);
		return timestamp;
	}

}
