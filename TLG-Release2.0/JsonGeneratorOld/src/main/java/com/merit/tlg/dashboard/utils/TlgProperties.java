package com.merit.tlg.dashboard.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.business.SSTDashboardService;

public class TlgProperties {
	
	final static Logger logger = Logger.getLogger(TlgProperties.class);
	private static TlgProperties instance = new TlgProperties();
	private static final String tlg_config = "tlgconfig.properties";

	private Properties props;

	/**
	 * @return
	 */
	public static TlgProperties getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private TlgProperties() {
		InputStream stream = null;

		try {
			stream = this.getClass().getClassLoader().getResourceAsStream(tlg_config);

			if (stream == null) {
				logger.debug("********* Unable to find file ++++++++++++ " + tlg_config );
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
			logger.debug(" IO Exception ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug(" Exception ");
			e.printStackTrace();
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
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
