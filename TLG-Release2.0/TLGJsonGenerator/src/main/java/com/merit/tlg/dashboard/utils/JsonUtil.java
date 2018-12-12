package com.merit.tlg.dashboard.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is a utility class which has methods to convert Java Beans to Json and Json to Java beans.
 * Makes use of Jackson Apis for the same
 *
 */
public class JsonUtil {
	final static Logger logger = Logger.getLogger(JsonUtil.class);
	
	/**
	 * Method to convert the given bean to JSON and write it to the file specified.
	 * Throws Exception id the bean is not valid or if not able to create the specified file
	 * 
	 * jsonBean : The bean to be converted to json
	 * fileName : The file where the converted json is to be written and stored
	 *
	 */

	public static boolean writeToJsonFile(Object jsonBean, String fileName) throws JsonGenException {

		ObjectMapper mapper = new ObjectMapper();
		String fileWithPath = null;
		boolean isSucess = false;

		/**
		 * Write Prettified JSON to file
		 */

		try {

			String dbPath = TlgProperties.getInstance().getProperty("tlg.dashboard.path");
			logger.debug("DB Properties Path " + dbPath + " json Url from DB " + fileName);
			fileWithPath = dbPath + fileName;
			int endIndex = fileWithPath.lastIndexOf("/");
			String dirPath = fileWithPath.substring(0, endIndex);
			logger.debug("File Path " + fileWithPath + " Dir Path " + dirPath);
			Path path = Paths.get(dirPath);

			if (!Files.exists(path)) {
				logger.debug("************** Creating Directories ***************" + path);
				Files.createDirectories(path);
			}
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileWithPath), jsonBean);
			isSucess = true;
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException  " + e);
			throw new JsonGenException("Invalid Bean, Not able to map to JSON ", e, ErrorCode.INVALID_BEAN);
		} catch (JsonGenerationException e) {
			logger.error("JsonGenerationException  " + e);
			throw new JsonGenException("Invalid Bean, Not able to generate JSON ", e, ErrorCode.INVALID_BEAN);
		} catch (IOException e) {
			logger.error("IOException  " + e);
			throw new JsonGenException(
					"Not able to create directories or not able to write to specified file " + fileWithPath, e,
					ErrorCode.INVALID_FILE);
		}

		return isSucess;
	}

}
