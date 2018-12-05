package com.stosz.tms.parse.startegy.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;

@Component("timesParseStartegy")
public class TimesParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(TimesParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		if (!StringUtils.hasText(response)) {
			return null;
		}
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try{
			Parameter<String, Object> parameter = JsonUtils.jsonToObject(response, Parameter.class);
			LinkedHashMap<String, String> milestoneMap = (LinkedHashMap<String, String>) parameter.get("milestones");
			if (ArrayUtils.isEmpty(milestoneMap)) {
				return null;
			}
			List<String> parseNodeList = this.getParseNodes();
			for (String childNode : parseNodeList) {
				String childValue = milestoneMap.get(childNode);
				String reasonNode = StringUtil.concat(childNode, "_reason");
				String reasonValue = milestoneMap.get(reasonNode);
				if (StringUtils.hasText(childValue)) {
					TrackingTaskDetail trackDetail = new TrackingTaskDetail();
					trackDetail.setTrackRecord(childValue);
					trackDetail.setTrackTime(parseDate(childValue));
					trackDetail.setTrackStatus(childNode);
					if (StringUtils.hasText(reasonValue)) {
						trackDetail.setTrackReason(reasonValue);
					}
					trackDetails.add(trackDetail);
				}
			}
			sortTrackByTrackTimeAsc(trackDetails);
		}catch (Exception e){
			logger.trace("TimesParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return trackDetails;
	}

	private List<String> getParseNodes() {
		return Arrays.asList("receive", "return", "reject", "pending", "delivering", "handover_lastmile", "uplift", "sort_in", "sort_out", "pickup",
				"lost");
	}

}
