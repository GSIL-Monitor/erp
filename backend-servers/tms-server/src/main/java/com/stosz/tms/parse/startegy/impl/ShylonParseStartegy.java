package com.stosz.tms.parse.startegy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.cxc.CxcResponseDetails;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.utils.JsonUtils;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ShylonParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(ShylonParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			ArrayList<LinkedHashMap> list =  JsonUtils.jsonToObject(response,ArrayList.class);
			String trackNo = list.get(0).get("Jobno")+"";
			ArrayList<LinkedHashMap> details = new ArrayList<LinkedHashMap>();
			details = (ArrayList<LinkedHashMap>) list.get(0).get("detail");
			trackDetails = details.stream().map(item ->{
				TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();
				shippingTrackDetail.setTrackRecord(item.get("scantype")+"");//轨迹记录
				shippingTrackDetail.setCityName(item.get("location")+"");//地点
				shippingTrackDetail.setTrackNo(trackNo);//运单号
				shippingTrackDetail.setTrackReason(item.get("memo")+"");//原因
//				shippingTrackDetail.setTrackTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.get("time")+""));
				Date date = DateUtils.str2Date(item.get("time")+"","yyyy-MM-dd HH:mm:ss");
				shippingTrackDetail.setTrackTime(date);
				return shippingTrackDetail;
			}).collect(Collectors.toList());

			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("ShylonParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}


}
