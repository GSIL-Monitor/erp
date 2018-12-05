package com.stosz.tms.parse.startegy.impl;

import com.stosz.plat.utils.DateUtils;
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

@Component
public class DfParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(DfParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			Document doc = Jsoup.parse(response);
			Element trackEle = doc.getElementById("HeaderNum");
			String trackNo = trackEle.text().split("：")[1];//运单号
//			String status = doc.getElementsByClass("HeaderState").first().text().split(":")[1];//状态
			Element tableEle = doc.getElementsByClass("trackContentTable").first();
			Elements trEle = tableEle.getElementsByTag("tr");
			for(int i=1;i<trEle.size();i++){
				TrackingTaskDetail detail = new TrackingTaskDetail();
				String datestr = trEle.get(i).getElementsByTag("td").get(0).text();
				Date date = DateUtils.str2Date(datestr,"yyyy-MM-dd HH:mm");
				String location = trEle.get(i).getElementsByTag("td").get(1).text();
				String status = trEle.get(i).getElementsByTag("td").get(2).text();
				detail.setTrackNo(trackNo);
				detail.setTrackStatus(status);
				detail.setTrackTime(date);
				detail.setCityName(location);
				trackDetails.add(detail);
			}
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("YufengParseStartegy 错误，入参response={},e={}",response,e);
		}
		return null;
	}

}
