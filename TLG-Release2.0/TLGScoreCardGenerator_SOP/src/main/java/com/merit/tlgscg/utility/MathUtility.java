package com.merit.tlgscg.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MathUtility {

	//static Logger szLogger = LogManager.getLogger(StandardDeviation.class.getName());

	public static double calculateSD(Object numArray[]) {
		double sum = 0.0, standardDeviation = 0.0;
		int length;

		try {
			length = numArray.length;

			for (Object num : numArray) {
				sum += (Float) num;
			}

			double mean = sum / length;

			for (Object num : numArray) {
				standardDeviation += Math.pow((Float) num - mean, 2);
			}

			return Math.sqrt(standardDeviation / length);
		} catch (Exception e) {
			System.out.println("error::" + e.toString());
			e.printStackTrace();
		}
		return 0;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}