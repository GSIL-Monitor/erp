package com.stosz.tms.parse.startegy.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractHtmlParseStartegy;

@Component("indonesiajtParseStartegy")
public class IndonesiajtParseStartegy extends AbstractHtmlParseStartegy {

	private Logger logger = LoggerFactory.getLogger(IndonesiajtParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> details = new ArrayList<>();
		try{
			Map responseMap = JsonUtils.jsonToMap(response, LinkedHashMap.class);
			String trackNo = responseMap.get("awb") + "";
			ArrayList<LinkedHashMap> history = (ArrayList<LinkedHashMap>) responseMap.get("history");
			if (ArrayUtils.isNotEmpty(history)) {
				details = history.stream().map(item -> {
					TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();
					shippingTrackDetail.setTrackNo(trackNo);
					shippingTrackDetail.setTrackTime(new Date(item.get("date_time") + ""));
					shippingTrackDetail.setCityName(item.get("city_name") + "");
					shippingTrackDetail.setTrackStatus(item.get("status_code") + "");
					shippingTrackDetail.setTrackRecord(item.get("status") + "");
					return shippingTrackDetail;
				}).collect(Collectors.toList());
			}
			sortTrackByTrackTimeAsc(details);// 排序
			return details;
		}catch (Exception e){
			logger.error("IndonesiajtParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

}