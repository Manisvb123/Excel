package com.merit.tlg.dashboard.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	final static Logger logger = Logger.getLogger(JsonUtil.class);

	public static boolean writeToJsonFile(Object jsonBean, String fileName) {

		ObjectMapper mapper = new ObjectMapper();
		String fileWithPath = null;
		boolean isSucess = false;

		/**
		 * Write Prettified JSON to file
		 */
		try {

			String dbPath = TlgProperties.getInstance().getProperty("tlg.dashboard.path");
			fileWithPath = dbPath + fileName;
			int endIndex = fileWithPath.lastIndexOf("/");
			String dirPath = fileWithPath.substring(0, endIndex);
			logger.debug("File Path " + fileWithPath + " Dir Path " + dirPath);
			Path path = Paths.get(dirPath);
			
			if (!Files.exists(path)) {
				logger.debug("************** Creating Directories ***************" + path);
				Files.createDirectories(path);
			}
			logger.debug("Writing to File ");
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileWithPath), jsonBean);
			isSucess = true;
		} catch (Exception e) {
			logger.error("Error while writing to file " + fileWithPath, e);
			e.printStackTrace();
		}
		return isSucess;
	}

}
