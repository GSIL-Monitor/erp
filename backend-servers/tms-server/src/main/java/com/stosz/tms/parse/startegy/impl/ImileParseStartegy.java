package com.stosz.tms.parse.startegy.impl;

import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ImileParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(ImileParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			response = response.replace("\"data\":\"","\"data\":").replace("]\"}","]}");
			Map<String,Object> resultMap = JsonUtils.jsonToMap(response, LinkedHashMap.class);
 			if(("4").equals(resultMap.get("stauts"))){
 				ArrayList<LinkedHashMap> list = (ArrayList<LinkedHashMap>) resultMap.get("data");
 				for(LinkedHashMap item:list) {
					Set<Map.Entry<String, Object>> set = item.entrySet();
					Iterator<Map.Entry<String, Object>> it = set.iterator();
					TrackingTaskDetail detail = new TrackingTaskDetail();
					while (it.hasNext()) {
						Map.Entry<String, Object> entry = it.next();
						String trackNo = entry.getKey();
						ArrayList<LinkedHashMap> mapArrayList = (ArrayList) entry.getValue();
						for (Map<String, String> map : mapArrayList) {
							detail.setTrackNo(trackNo);
							detail.setTrackTime(DateUtils.str2Date(map.get("time") + "", "yyyy-MM-dd HH:mm:ss"));
							detail.setTrackStatus(map.get("description"));
							trackDetails.add(detail);
						}
					}
				}
			}
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("ImileParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

}
