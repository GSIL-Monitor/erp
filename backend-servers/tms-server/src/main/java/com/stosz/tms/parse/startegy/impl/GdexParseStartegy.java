package com.stosz.tms.parse.startegy.impl;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GdexParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(GdexParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			trackDetails = this.getResponseDetailList(response);
			if(ArrayUtils.isEmpty(trackDetails)){
				return null;
			}
			sortTrackByTrackTimeAsc(trackDetails);// 排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("GdexParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

	/**
	 * 获得响应detailList
	 * @param response
	 * @return
	 */
	private List<TrackingTaskDetail> getResponseDetailList(String response) {
		List<TrackingTaskDetail> details = new ArrayList<>();
		try{
			Map<String,Object> map = JsonUtils.jsonToObject(response, LinkedHashMap.class);
			if(ArrayUtils.isEmpty(map)){
				return null;
			}
			String s = map.get("s")+"";
			if(!("success").equals(s)){
				logger.trace("GdexParseStartegy 查询轨迹返回失败{}",map.get("e")+"");
				return null;
			}

			List<Map> history = (List<Map>) ((Map<String,Object>)map.get("r")).get("History");
			if (ArrayUtils.isEmpty(history)) {
				return null;
			}
			String trackNo = ((Map<String,Object>)map.get("r")).get("CN")+"";//运单号
			details = history.stream().map(item -> {
				TrackingTaskDetail taskDetail = new TrackingTaskDetail();
				taskDetail.setTrackNo(trackNo);
				taskDetail.setTrackStatus(item.get("StatusCode")+"");
				taskDetail.setTrackTime(DateUtils.str2Date(item.get("StatusDateTime")+"","yyyy-MM-dd HH:mm:ss"));
				return taskDetail;
			}).collect(Collectors.toList());
			return details;
		}catch (Exception e) {
			logger.error("GdexParseStartegy类getResponseDetailList()方法出错 ");

		}
		return null;
	}


}
