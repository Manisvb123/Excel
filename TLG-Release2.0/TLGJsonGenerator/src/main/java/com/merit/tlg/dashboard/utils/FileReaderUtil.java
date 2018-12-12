package com.merit.tlg.dashboard.utils;

import java.io.InputStream;

import org.apache.log4j.Logger;

public class FileReaderUtil {

	final static Logger logger = Logger.getLogger(FileReaderUtil.class);
	
	/**
	 * Reads the given file 
	 */
	public static InputStream readFile(String fileName) throws JsonGenException {

		InputStream is = null;
		Class className = FileReaderUtil.class;
		try {
			is = className.getClassLoader().getResourceAsStream(fileName);
			if (is == null) {
				logger.debug("********* Unable to find file in Resource ++++++++++++ " + fileName);
				is = ClassLoader.getSystemResourceAsStream(fileName);
			}
		} catch (Exception e) {
			logger.debug(" File Not Found Exception  " + e);
			throw new JsonGenException(fileName + " Not Found", e, ErrorCode.FILE_NOT_FOUND);
		} 

		if (is == null) {
			throw new JsonGenException(fileName + " Not Found", ErrorCode.FILE_NOT_FOUND);
		}
		
		return is ;
	}

}
