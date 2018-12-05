package com.stosz.tms.parse.startegy.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.stosz.plat.utils.DateUtils;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.nim.Signature;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.utils.JsonUtils;

@Component("nimParseStartegy")
public class NimParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(NimParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		if(response.contains("isError")){
			return null;
		}
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			trackDetails = this.getNimResponseDetailList(response);
			sortTrackByTrackTimeAsc(trackDetails);// 排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("NimParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

	/**
	 * 获得响应detailList
	 * @param response
	 * @return
	 */
	private List<TrackingTaskDetail> getNimResponseDetailList(String response) {
		List<TrackingTaskDetail> details = new ArrayList<>();
		List<LinkedHashMap> list = JsonUtils.jsonToObject(response, ArrayList.class);
		if (ArrayUtils.isEmpty(list)) {
			return null;
		}
		int i=0;
		String trackNo=null;
		while(trackNo==null){
			trackNo = list.get(i).get("billNo")+"";
			i++;
		}
		for (LinkedHashMap map : list) {
			TrackingTaskDetail detail = new TrackingTaskDetail();
			detail.setTrackNo(trackNo);
			detail.setTrackStatus(map.get("stepCode") + "");
			detail.setTrackRecord(map.get("stepNameShortEN") + "");
			detail.setTrackTime(DateUtils.str2Date(map.get("dateTime") + "", "yyyyMMddHHmmsss"));
			details.add(detail);
		}
		return details;
	}

	/**
	 * 按时间倒序排序
	 * @param trackDetails
	 */
	private void sortDateList(List<Date> trackDetails) {
		if (ArrayUtils.isNotEmpty(trackDetails)) {
			trackDetails.sort((src, dest) -> {
				if (src != null && dest != null) {
					return dest.compareTo(src);
				} else if (src != null) {
					return -1;
				} else if (dest != null) {
					return 1;
				}
				return 0;
			});
		}
	}

}
