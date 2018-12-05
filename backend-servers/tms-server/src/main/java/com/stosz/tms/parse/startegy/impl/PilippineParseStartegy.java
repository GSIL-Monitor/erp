package com.stosz.tms.parse.startegy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.tms.model.philippine.PhilippineTrackResponseData;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.philippine.PhilippineTrackDetails;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("pilippineParseStartegy")
public class PilippineParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(PilippineParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			PhilippineTrackDetails details = objectMapper.readValue(response, PhilippineTrackDetails.class);
			List<PhilippineTrackResponseData> list = details.getData();
			for (PhilippineTrackResponseData data : list) {
				TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();
				shippingTrackDetail.setTrackTime(new Date(data.getOperateTime()));// 时间
				shippingTrackDetail.setTrackStatus(data.getOperateFlag());// 状态
				shippingTrackDetail.setTrackReason(data.getShortAttr());
				shippingTrackDetail.setTrackRecord(data.getOperateAttr());

				trackDetails.add(shippingTrackDetail);
			}
			sortTrackByTrackTimeAsc(trackDetails);// 排序
		} catch (IOException e) {
			logger.trace("PilippineParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return trackDetails;
	}

}
