package com.stosz.tms.parse.startegy.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.russia.TrackOrder;
import com.stosz.tms.model.russia.TrackState;
import com.stosz.tms.model.russia.TrackStatus;
import com.stosz.tms.model.russia.TrackStatusReport;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import org.springframework.stereotype.Component;

@Component
public class RussiaParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(RussiaParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(TrackStatusReport.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			TrackStatusReport statusReport = (TrackStatusReport) unmarshaller.unmarshal(new StringReader(response));
			if (statusReport == null || statusReport.getOrder() == null) {
				logger.info("RussiaParseStartegy parseTrackContent() response={}", response);
				return trackDetails;
			}
			TrackOrder trackOrder = statusReport.getOrder();
			TrackStatus trackStatus = trackOrder.getTrackStatus();

			List<TrackState> trackStates = trackStatus.getTrackStates();
			if (ArrayUtils.isNotEmpty(trackStates)) {
				for (TrackState trackState : trackStates) {
					Date detailDate = tryParseDate(trackState.getDate(), FORMATE_2);
					String desc = trackState.getDescription();
					String cityName = trackState.getCityName();
					TrackingTaskDetail trackDetail = transferTrackDetail(detailDate, desc, cityName);
					trackDetails.add(trackDetail);
				}
				sortTrackByTrackTimeAsc(trackDetails);
			} else {
				Date detailDate = tryParseDate(trackStatus.getDate(), FORMATE_2);
				trackDetails.add(transferTrackDetail(detailDate, trackStatus.getDescription(), trackStatus.getCityName()));
			}
			// 最后物流动态等于签收返回的数据里面才会有”Package, Item” 标签.
			if (trackOrder.getPackageItem() != null && ArrayUtils.isNotEmpty(trackOrder.getPackageItem().getTrackItem())) {
				trackDetails.add(transferTrackDetail(new Date(), "已经签收", null));
			}
		} catch (Exception e) {
			logger.error("RussiaParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return trackDetails;
	}

	private TrackingTaskDetail transferTrackDetail(Date detailDate, String desc, String cityName) {
		TrackingTaskDetail trackDetail = new TrackingTaskDetail();
		trackDetail.setTrackStatus("");
		trackDetail.setTrackTime(detailDate);// 2017-12-12T05:49:10+00:00
		trackDetail.setTrackRecord(desc);
		trackDetail.setCityName(cityName);
		return trackDetail;
	}

}
