package com.merit.tlg.dashboard.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TlgProperties {

	final static Logger logger = Logger.getLogger(TlgProperties.class);
	private static TlgProperties instance;
	private static final String tlg_config = "tlgconfig.properties";

	private Properties props;

	/**
	 * @return
	 */
	public static TlgProperties getInstance() throws JsonGenException {
		if (instance == null)
			instance = new TlgProperties();
		return instance;
	}

	/**
	 * 
	 */
	private TlgProperties() throws JsonGenException {
		InputStream stream = null;

		try {
			stream = this.getClass().getClassLoader().getResourceAsStream(tlg_config);

			if (stream == null) {
				logger.debug("********* Unable to find file ++++++++++++ " + tlg_config);
				stream = ClassLoader.getSystemResourceAsStream(tlg_config);
			}

			if (stream == null) {
				logger.debug("********* Unable to read file through classloader ++++++++++++ " + tlg_config);
			} else {
				logger.debug(" read Stream " + stream.toString());
				props = new Properties();
				props.load(stream);
			}

		} catch (IOException e) {
			logger.error(" IO Exception  " + e);
			throw new JsonGenException("Not able to load the properties file ", e, ErrorCode.INVALID_FILE);
			
		} catch (Exception e) {
			logger.error(" Exception  " + e);
			throw new JsonGenException("Not able to find the properties file ", e, ErrorCode.FILE_NOT_FOUND);
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				logger.error(" Unable to close stream  " + e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return props.getProperty(key);
	}

	/**
	 * @param key
	 * @return
	 */
	public int getIntegerProperty(String key) {
		int i = 0;
		String propValue = props.getProperty(key);
		try {
			if (propValue.trim().length() > 0)
				i = Integer.parseInt(propValue.trim());
			else
				i = 0;
		} catch (Exception e) {
			e.printStackTrace();
			i = 0;
		}
		return i;
	}

}
