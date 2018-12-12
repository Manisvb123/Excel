package com.merit.tlgscg.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class RestClient {

	//static Logger szLogger = LogManager.getLogger(RestClient.class.getName());

	public static JSONObject HTTPGet(String httpURL) {
		HttpURLConnection conn = null;
		BufferedReader br = null;
		String output = null;
		URL szURL = null;
		try {
			System.out.println("Connecting to " + httpURL);
			szURL = new URL(httpURL);
			conn = (HttpURLConnection) szURL.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();
		} catch (Exception e) {
			System.out.println("Error::" + e.toString());
			e.printStackTrace();
		} finally {
			conn.disconnect();
			conn = null;
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			br = null;
			szURL = null;
		}
		return null;
	}

	public static void main(String args[]) {
		HTTPGet("http://localhost:8080/quest/rest/dbdata/insight/SST-001/PR-001/ORG-001");
	}
}
