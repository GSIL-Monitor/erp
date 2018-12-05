package com.stosz.tms.parse.startegy.impl;

import com.stosz.plat.utils.DateUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractHtmlParseStartegy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class HkcoeParseStartegy extends AbstractHtmlParseStartegy {

	private Logger logger = LoggerFactory.getLogger(HkcoeParseStartegy.class);

	private Pattern pattern = Pattern.compile(".*没有.*");

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> details = new ArrayList<>();
		try{
			Document document = Jsoup.parse(response);
			Elements headelements = document.getElementsByClass("teh-r-t");
			//运单号
			String trackNo = headelements.get(0).getAllElements().get(1).getElementsByTag("span").get(1).html();
			// 物流轨迹详情
			Element element = document.getElementsByClass("list-box").get(0);
			ArrayList<LinkedHashMap> reponseList = this.getDetailList(element);
			if(reponseList==null){
				return null;
			}
			details = reponseList.stream().map(map ->{
				TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();
				String trackStatus = map.get("trackStatus")+"";
				String cityName = map.get("cityName")+"";
				Date trackTime = (Date) map.get("trackTime");
				shippingTrackDetail.setTrackNo(trackNo);
				shippingTrackDetail.setCityName(cityName);
				shippingTrackDetail.setTrackStatus(trackStatus);
				shippingTrackDetail.setTrackTime(trackTime);
				return shippingTrackDetail;
			}).collect(Collectors.toList());
			sortTrackByTrackTimeAsc(details);//排序
		}catch (Exception e){
			logger.trace("HkcoeParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return details;
	}

	/**
	 * 获取轨迹详情
	 * @return
	 */
	private ArrayList getDetailList(Element element){
		ArrayList<LinkedHashMap> detailList = new ArrayList<>();
		Elements elements = element.getElementsByClass("trace-ele-list");
		if(elements.size()>0){
			for(Element ele : elements){
				Element divEle = ele.getElementsByTag("div").get(0);
				String trackTimestr = divEle.getElementsByTag("span").get(0).text();
				if(pattern.matcher(trackTimestr).find()){
					return null;
				}
				Date trackTime = DateUtils.str2Date(trackTimestr,"yyyy-MM-dd HH:mm:ss");
				String cityName = divEle.getElementsByTag("span").get(1).text();
				String trackStatus = divEle.getElementsByTag("span").get(2).text();
				LinkedHashMap detalMap = new LinkedHashMap();
				detalMap.put("trackStatus",trackStatus);
				detalMap.put("cityName",cityName);
				detalMap.put("trackTime",trackTime);
				detailList.add(detalMap);
			}
			return detailList;
		}
		return null;
	}

}
