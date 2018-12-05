package com.stosz.tms.parse.startegy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.tms.external.aramex.trace.ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY;
import com.stosz.tms.external.aramex.trace.ShipmentTrackingResponse;
import com.stosz.tms.external.aramex.trace.TrackingResult;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.utils.JsonUtils;
import com.stosz.tms.utils.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class QhyParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(QhyParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		if(StringUtils.isEmpty(response)){
			return null;
		}
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			Map<String,Object> resultMap = U.parse(response);
			String Jsonstr = resultMap.get("response").toString();
			resultMap.clear();
			resultMap = JsonUtils.jsonToMap(Jsonstr, LinkedHashMap.class);
			if(!("success").equalsIgnoreCase(resultMap.get("ask").toString())){
				return null;
			}
			LinkedHashMap<String,Object> orderMap = ((ArrayList<LinkedHashMap<String,Object>>)resultMap.get("Data")).get(0);
			String trackNo = orderMap.get("Code").toString();
			ArrayList<LinkedHashMap<String,Object>> detailList = (ArrayList<LinkedHashMap<String,Object>>)orderMap.get("Detail");
			trackDetails = detailList.stream().map(item->{
				TrackingTaskDetail detail = new TrackingTaskDetail();
				detail.setTrackNo(trackNo);
				detail.setTrackTime(DateUtils.str2Date(item.get("Occur_date").toString(),"yyyy-MM-dd HH:mm:ss"));
				detail.setTrackStatus(item.get("Comment").toString());
				return detail;
			}).collect(Collectors.toList());
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("QhyParseStartegy 错误，入参response={}，e={}",response,e);
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
