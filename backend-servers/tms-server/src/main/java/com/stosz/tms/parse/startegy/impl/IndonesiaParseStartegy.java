package com.stosz.tms.parse.startegy.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractHtmlParseStartegy;

@Component("indonesiaParseStartegy")
public class IndonesiaParseStartegy extends AbstractHtmlParseStartegy {

	private Logger logger = LoggerFactory.getLogger(IndonesiaParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> details = new ArrayList<>();
		try{
			Document document = Jsoup.parse(response);
			Elements elements = document.getElementsByClass("HtmlText");
			if (elements.size() > 1) {
				List<LinkedHashMap<String, String>> linkedHashMaps = getTableData(elements.get(1), 1);
				if (linkedHashMaps.size() > 0) {
					String time = linkedHashMaps.get(0).get("当地时间");
					String trackinfo = linkedHashMaps.get(0).get("最新状态");
					Date trackTime = parseDate(time);
					details.add(new TrackingTaskDetail(trackinfo, trackinfo, trackTime));
				}
			}
			// 物流轨迹详情
			if (elements.size() > 2) {
				details.clear();
				List<LinkedHashMap<String, String>> linkedHashMaps = getTableData(elements.get(2), 1);
				for (LinkedHashMap<String, String> trackItem : linkedHashMaps) {
					String date = trackItem.get("日期");
					String time = trackItem.get("时间");
					String trackinfo = trackItem.get("跟踪记录");
					Date dateTime = parseDate(StringUtil.concat(date, " ", time));
					TrackingTaskDetail trackDetail = new TrackingTaskDetail(trackinfo, trackinfo, dateTime);
					details.add(trackDetail);
				}
			}
		}catch (Exception e){
			logger.trace("IndonesiaParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return details;
	}
}
