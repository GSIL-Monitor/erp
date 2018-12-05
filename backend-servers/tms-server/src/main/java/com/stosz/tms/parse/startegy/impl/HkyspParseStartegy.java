package com.stosz.tms.parse.startegy.impl;

import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.utils.JsonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HkyspParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(HkyspParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {

		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			if(StringUtils.isEmpty(response)){
				return null;
			}
			String[] arr = response.split("\r\n");
			if(!"100".equals(arr[0])){
				return null;
			}
			StringBuilder sb = new StringBuilder();
			for(int i = 1;i<arr.length;i++){
				sb.append(arr[i]);
			}
			Document document = Jsoup.parse(response);

			Element trackEle = document.getElementsByTag("TRCKING_NBR").first();
			Element dataEle = document.getElementsByTag("TRACK_DATA").first();
			TrackingTaskDetail trackDetail = new TrackingTaskDetail();
			trackDetail.setTrackNo(trackEle.text());
			trackDetail.setTrackStatus(dataEle.getElementsByTag("INFO").first().text());//节点状态
			trackDetail.setTrackTime(DateUtils.str2Date(dataEle.getElementsByTag("DATETIME").first().text(),"yyyy-MM-dd"));
			trackDetail.setCityName(dataEle.getElementsByTag("PLACE").first().text());//地点
			trackDetails.add(trackDetail);
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("HkyspParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

}
