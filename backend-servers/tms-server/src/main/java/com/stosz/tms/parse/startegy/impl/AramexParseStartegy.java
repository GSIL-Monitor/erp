package com.stosz.tms.parse.startegy.impl;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.tms.external.aramex.trace.ShipmentTrackingResponse;
import com.stosz.tms.external.aramex.trace.TrackingResult;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.tms.external.aramex.trace.ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.text.ParseException;
import java.util.*;

@Component("aramexParseStartegy")
public class AramexParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(AramexParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ShipmentTrackingResponse responseBody = objectMapper.readValue(response,ShipmentTrackingResponse.class);
			trackDetails = this.getAramexResponseDetailList(responseBody);
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("AramexParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

	/**
	 * 获得响应detailList
	 * @param response
	 * @return
	 */
	private List<TrackingTaskDetail> getAramexResponseDetailList(ShipmentTrackingResponse response) throws ParseException {
		Assert.isTrue(!response.getHasErrors(),"请求参数有误接口返回失败");
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		if(ArrayUtils.isEmpty(response.getTrackingResults())) {
			return trackDetails;
		}
		ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY trackingKVresults = response.getTrackingResults()[0];
		String trackNo = trackingKVresults.getKey();//运单号
		int num = trackingKVresults.getValue().length;
		TrackingResult[] trackingResultsArry = trackingKVresults.getValue();
		for(int i = 0;i<num;i++){
			TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();
			TrackingResult trackingResult = trackingResultsArry[i];
			shippingTrackDetail.setTrackRecord(trackingResult.getUpdateDescription()==null?"":trackingResult.getUpdateDescription());//更新记录
			shippingTrackDetail.setCityName(trackingResult.getUpdateLocation());//城市
			shippingTrackDetail.setTrackNo(trackNo);//运单号
			shippingTrackDetail.setTrackStatus(trackingResult.getUpdateDescription());//节点状态
			shippingTrackDetail.setTrackReason(trackingResult.getComments());
			shippingTrackDetail.setTrackRecord(trackingResult.getUpdateCode());
			shippingTrackDetail.setTrackTime(trackingResult.getUpdateDateTime().getTime());//更新日期
			trackDetails.add(shippingTrackDetail);
		}
		sortTrackByTrackTimeAsc(trackDetails);//排序
		return trackDetails;
	}

}
