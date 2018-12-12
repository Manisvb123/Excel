package com.merit.tlg.dashboard.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.merit.tlg.dashboard.model.MetricParamData;
import com.merit.tlg.dashboard.model.MetricSearchData;
import com.merit.tlg.dashboard.model.MetricSearchDataWrapper;

/**
 * @author Admin
 *
 */
public class JaxbUtil {

	/**
	 * 
	 */
	final static Logger logger = Logger.getLogger(JaxbUtil.class);

	/**
	 * @param dashBoardId
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public static MetricSearchData createSearchAttributeBean(String searchId)
			throws JsonGenException {
		MetricSearchData msd = null;
		InputStream xmlFileStream = null;
		try {
			logger.debug("Started generating search bean");
			JAXBContext context = JAXBContext.newInstance(MetricSearchDataWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			xmlFileStream= FileReaderUtil.readFile(CommonConstants.METRIC_SEARCHDATA_XML);
			MetricSearchDataWrapper searchList = (MetricSearchDataWrapper) um.unmarshal(xmlFileStream);
			
			ArrayList<MetricSearchData> list = searchList.getMetricSearchData();
			for (Iterator<MetricSearchData> iterator = list.iterator(); iterator.hasNext();) {
				msd = (MetricSearchData) iterator.next();
				logger.debug("SearchID " + searchId + "   Metric Search Id " + msd.getSearchId());
				if (searchId.equalsIgnoreCase(msd.getSearchId().trim())) {
					logger.debug("Found");
					return msd;
				}
			}

		} catch (JAXBException e) {
			logger.error(" Invalid XML Exception  " + e);
			throw new JsonGenException("Not a Valid XML File For Search Data ", e, ErrorCode.INVALID_XML);
		} finally {
			try {
				if (xmlFileStream != null)
					xmlFileStream.close();
			} catch (IOException e) {
				logger.error(" Unable to close stream  " + e);
				e.printStackTrace();
			}
		}

		return msd;
	}

	/**
	 * 
	 */
	public static void createSearchAttributeXML() {

		try {

			logger.debug("Starting XML Generation");
			ArrayList<MetricSearchData> searchDataList = createSearchDataList();
			MetricSearchDataWrapper searchDataWrapper = new MetricSearchDataWrapper();
			searchDataWrapper.setMetricSearchData(searchDataList);

			// create JAXB context and instantiate marshaller
			JAXBContext context = JAXBContext.newInstance(MetricSearchDataWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			logger.debug("Writing XML ");
			// Write to File
			m.marshal(searchDataWrapper, new File(CommonConstants.METRIC_SEARCHDATA_XML));
			logger.debug("Completed XML Creation ");

		} catch (JAXBException e) {
			logger.debug(" Exception  " + e);
		}

	}

	/**
	 * @return
	 */
	public static ArrayList<MetricSearchData> createSearchDataList() {
		ArrayList<MetricSearchData> searchDataList = new ArrayList<>();

		MetricSearchData msd = new MetricSearchData();
		msd.setSearchId(CommonConstants.SST_INSIGHT_DASHBOARD_ID);
		ArrayList<MetricParamData> mList = new ArrayList<>();

		MetricParamData mData = new MetricParamData();
		mData.setEntityType(CommonConstants.ORGANISATION);
		mData.setEntityIdReq(true);
		mData.setDimType(CommonConstants.QUESTION);
		mData.setDimIdReq(false);
		mData.setMetricName(CommonConstants.OEPS);
		mList.add(mData);

		mData = new MetricParamData();
		mData.setEntityType(CommonConstants.ORGANISATION);
		mData.setEntityIdReq(true);
		mData.setDimType(CommonConstants.QUESTION);
		mData.setDimIdReq(false);
		mData.setMetricName(CommonConstants.OESDS);
		mList.add(mData);
		msd.setMetricParams(mList);
		searchDataList.add(msd);

		MetricSearchData msd1 = new MetricSearchData();
		msd1.setSearchId(CommonConstants.SST_INDEX_DASHBOARD_ID);

		ArrayList<MetricParamData> mList1 = new ArrayList<>();
		MetricParamData mData1 = new MetricParamData();
		mData1.setEntityType(CommonConstants.ORGANISATION);
		mData1.setEntityIdReq(true);
		mData1.setDimType(CommonConstants.TOOL);
		mData1.setDimIdReq(false);
		mData1.setMetricName(CommonConstants.OTI);
		mList1.add(mData1);

		mData1 = new MetricParamData();
		mData1.setEntityType(CommonConstants.ORGANISATION);
		mData1.setEntityIdReq(true);
		mData1.setDimType(CommonConstants.PD);
		mData1.setDimIdReq(false);
		mData1.setMetricName(CommonConstants.OPDPS);
		mList1.add(mData1);
		msd1.setMetricParams(mList1);
		searchDataList.add(msd1);
		return searchDataList;
	}

}
