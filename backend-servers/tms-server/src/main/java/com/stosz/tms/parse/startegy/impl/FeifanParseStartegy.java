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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FeifanParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(FeifanParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			if(response.toLowerCase().contains("input information error") || response.toLowerCase().contains("no input information")){
				return null;
			}
			Map resultMap = JsonUtils.jsonToMap(response,LinkedHashMap.class);
			if(CollectionUtils.isEmpty(resultMap)){
				return null;
			}
			ArrayList<String> list = (ArrayList<String>) resultMap.get("states_t");
			ArrayList<String> statusList = (ArrayList<String>) resultMap.get("states_s");
			if(list.size()!=statusList.size()){
				logger.info("FeifanParseStartegy 返回的states_t和states_s大小不一致");
				return null;
			}
			for(int i = 0;i<list.size();i++){
				TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();
				String[] arr = list.get(i).split(" ");
				shippingTrackDetail.setTrackTime(DateUtils.str2Date(arr[0],"yyyyMMdd"));
				shippingTrackDetail.setTrackRecord(arr[1]);
				shippingTrackDetail.setTrackStatus(statusList.get(i));
				trackDetails.add(shippingTrackDetail);
			}
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("FeifanParseStartegy 错误，入参response={}，e={}",response,e);
			return null;
		}
	}

}
