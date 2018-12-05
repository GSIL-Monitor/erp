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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class XinzhuParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(XinzhuParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			Document document = Jsoup.parse(response);
			String trackNo = document.getElementById("L_invno").text();
			if(StringUtils.isEmpty(trackNo)){
				return null;
			}
			Element tableEle = document.getElementsByClass("cargotable").first();
			Elements trEle  = tableEle.getElementsByTag("tr");
			trEle.remove(0);
			trackDetails = trEle.stream().map(item -> {
				TrackingTaskDetail taskDetail = new TrackingTaskDetail();
				Date date = DateUtils.str2Date(item.getElementsByTag("span").first().text(),"yyyy/MM/dd HH:mm");
				String cityName = item.getElementsByTag("span").last().getElementsByTag("a").text();
				String recorde = item.getElementsByTag("span").last().text();
				taskDetail.setTrackNo(trackNo);
				taskDetail.setTrackTime(date);
				taskDetail.setCityName(cityName);
				taskDetail.setTrackRecord(recorde);
				taskDetail.setTrackStatus(recorde);
				return taskDetail;
			}).collect(Collectors.toList());
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("XinzhuParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

//	public static void main(String[] args) {
//		String text = "貨件已抵達<a href=\"tel:047579288\">鹿港營業所</a>，分貨中。";
//		String patternString1 = "(<a.*</a>)";
//		Pattern pattern = Pattern.compile(patternString1);
//		Matcher matcher = pattern.matcher(text);
//		logger.info("groupCount is -->" + matcher.groupCount());
//		while (matcher.find()) {
//			logger.info("found: " + matcher.group(1));
//		}
//	}

}
