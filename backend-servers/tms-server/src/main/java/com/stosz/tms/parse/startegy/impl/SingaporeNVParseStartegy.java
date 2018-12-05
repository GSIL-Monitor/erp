//package com.stosz.tms.parse.startegy.impl;
//
//import com.stosz.plat.utils.DateUtils;
//import com.stosz.tms.model.TrackingTaskDetail;
//import com.stosz.tms.parse.startegy.AbstractParseStartegy;
//import com.stosz.tms.utils.JsonUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//public class SingaporeNVParseStartegy extends AbstractParseStartegy {
//
//	private Logger logger = LoggerFactory.getLogger(SingaporeNVParseStartegy.class);
//
//	@Override
//	public List<TrackingTaskDetail> parseTrackContent(String response) {
//		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
//		try {
//			Map resultMap = JsonUtils.jsonToMap(response,LinkedHashMap.class);
//			if(resultMap.isEmpty()||resultMap.get("states_t")==null){
//				return null;
//			}
//			ArrayList<String> list = (ArrayList<String>) resultMap.get("states_t");
//			trackDetails = list.stream().map(item ->{
//				TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();
//				String[] arr = item.split(" ");
//				shippingTrackDetail.setTrackTime(DateUtils.str2Date(arr[0],"yyyyMMdd"));
//				shippingTrackDetail.setTrackStatus(arr[1]);
//				return shippingTrackDetail;
//			}).collect(Collectors.toList());
//			sortTrackByTrackTimeAsc(trackDetails);//排序
//			return trackDetails;
//		} catch (Exception e) {
//		}
//		return null;
//	}
//
//}
