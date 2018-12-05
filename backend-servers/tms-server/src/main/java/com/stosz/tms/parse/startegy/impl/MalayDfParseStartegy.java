package com.stosz.tms.parse.startegy.impl;

import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MalayDfParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(MalayDfParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			Document document = Jsoup.parse(response);
			String trackingNo = document.getElementsByTag("table").get(5).getElementsByTag("tr").
					get(1).getElementsByTag("td").get(2).getElementsByTag("b").get(0).text();
			Elements detailEle = document.getElementsByClass("meisai").last().getElementsByTag("tr");
			for(int i = 1;i<detailEle.size();i++){
				TrackingTaskDetail detail = new TrackingTaskDetail();
				String status = detailEle.get(i).getElementsByTag("td").first().text();
				String datestr = StringUtil.concat(new SimpleDateFormat("yyyy").format(new Date()),"-",detailEle.get(i).getElementsByTag("td").get(1).text(), " ",
						detailEle.get(i).getElementsByTag("td").get(2).text());
				Date date = DateUtils.str2Date(datestr, "MM-dd HH:mm");
				detail.setTrackNo(trackingNo);
				detail.setTrackStatus(status);
				detail.setTrackTime(date);
				trackDetails.add(detail);
			}
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("MalayDfParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

}
