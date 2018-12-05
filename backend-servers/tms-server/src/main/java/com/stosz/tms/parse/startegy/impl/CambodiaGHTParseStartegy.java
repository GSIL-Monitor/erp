package com.stosz.tms.parse.startegy.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.DateUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.utils.JsonUtils;

@Component
public class CambodiaGHTParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(CambodiaGHTParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		if(response==null){
			return null;
		}
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			Map<String,List<LinkedHashMap<String,String>>> resultMap = JsonUtils.jsonToMap(response,LinkedHashMap.class);
			for (Map.Entry<String, List<LinkedHashMap<String,String>>> entry : resultMap.entrySet()) {
				TrackingTaskDetail trackDetail = new TrackingTaskDetail();
				List<LinkedHashMap<String,String>> list = new ArrayList<LinkedHashMap<String,String>>();
				String trackNo = entry.getKey();
				list = entry.getValue();
				String status = list.get(0).get("opertion");
				String trackTime = list.get(0).get("opTime");
				trackDetail.setTrackNo(trackNo);
				trackDetail.setTrackStatus(status);
				trackDetail.setTrackTime(DateUtils.str2Date(trackTime,"yyyy-MM-dd HH:mm:ss"));
				trackDetails.add(trackDetail);
			}
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("CambodiaGHTParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}


}
