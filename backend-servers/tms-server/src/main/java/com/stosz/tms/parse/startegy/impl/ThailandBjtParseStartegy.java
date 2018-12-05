package com.stosz.tms.parse.startegy.impl;

import com.stosz.plat.utils.DateUtils;
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

@Component("thailandBjtParseStartegy")
public class ThailandBjtParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(ThailandBjtParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			Document doc = Jsoup.parse(response);
			String trackNo = doc.getElementsByClass("info").first().getElementsByTag("span").first().text();//运单号
			if(StringUtils.isEmpty(trackNo)){
				return null;
			}
			Elements listEle = doc.getElementsByClass("col colStatus").first().getElementsByAttributeValueMatching("class","status");
			trackDetails = listEle.stream().map(item->{
				TrackingTaskDetail trackDetail = new TrackingTaskDetail();
				Element dateEle = item.getElementsByClass("date").first();
				Element descEle = item.getElementsByClass("desc").first();
				StringBuilder sb = new StringBuilder();
				sb.append(dateEle.getElementsMatchingOwnText("Date").text());
				sb.append(dateEle.getElementsMatchingOwnText("Time").text());
				String datestr = sb.toString().replace("Date","").trim().replace("Time","");
				Date date = DateUtils.enStr2Date(datestr,"dd MMM yy HH:mm");
				String status = descEle.getElementsByClass("d1").first().text();
				trackDetail.setTrackNo(trackNo);//运单号
				trackDetail.setTrackTime(date);//日期
				trackDetail.setTrackStatus(status);//节点状态
				return trackDetail;
			}).collect(Collectors.toList());
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("ThailandBjtParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}


}
