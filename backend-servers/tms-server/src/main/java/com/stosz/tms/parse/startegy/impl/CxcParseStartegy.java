package com.stosz.tms.parse.startegy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.cxc.CxcResponse;
import com.stosz.tms.model.cxc.CxcResponseDetails;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.utils.JsonUtils;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component("cxcParseStartegy")
public class CxcParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(CxcParseStartegy.class);

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			List<CxcResponseDetails> detailList = this.getCxcResponseDetailList(response);
			if( CollectionUtils.isNullOrEmpty(detailList)){
				logger.trace("查询为空");
				return null;
			}
			trackDetails = detailList.stream().map(item ->{
				TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();
				shippingTrackDetail.setTrackRecord(item.getBdBptypecode());//轨迹记录
				shippingTrackDetail.setCityName(item.getEventLocation());//地点
				shippingTrackDetail.setTrackNo(item.getBillCode());//运单号
				shippingTrackDetail.setTrackReason(item.getDescription());//原因
				shippingTrackDetail.setTrackTime(item.getEventDate());
				shippingTrackDetail.setTrackStatus(item.getBdBptypecode());//节点状态
				shippingTrackDetail.setCityName(item.getEventLocation());//地点
				return shippingTrackDetail;
			}).collect(Collectors.toList());

			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("CxcParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

	/**
	 * 获得响应detailList
	 * @param response
	 * @return
	 */
	private List<CxcResponseDetails> getCxcResponseDetailList(String response) throws ParseException {
		ArrayList<CxcResponseDetails> returnList = new ArrayList<>();
		LinkedHashMap outerMap = JsonUtils.jsonToLinkedMap(response,LinkedHashMap.class);
		if(outerMap.get("success").equals("false")){
			return returnList;
		}
		Object data = outerMap.get("data");
		String datastr = JsonUtils.toJson(data);
		LinkedHashMap dataMap = JsonUtils.jsonToLinkedMap(datastr,LinkedHashMap.class);
		Iterator<Map.Entry<String,ArrayList>> it = dataMap.entrySet().iterator();
		String key = "";
		ArrayList<LinkedHashMap> valueList = null;
		while (it.hasNext()) {
            Map.Entry<String, ArrayList> entry = it.next();
            key = entry.getKey();
			valueList = entry.getValue();
        }
        for(LinkedHashMap map:valueList){
			CxcResponseDetails cxcResponseDetails = new CxcResponseDetails();
			cxcResponseDetails.setBdBptypecode(map.get("bdBptypecode")+"");
			cxcResponseDetails.setBillCode(map.get("billCode")+"");
			cxcResponseDetails.setDescription(map.get("description")+"");
			cxcResponseDetails.setEmp(map.get("emp")+"");
			cxcResponseDetails.setEmpPhone(map.get("empPhone")+"");
			cxcResponseDetails.setEventLocation((map.get("eventLocation")+""));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cxcResponseDetails.setEventDate(sdf.parse(map.get("eventDate")+""));
			returnList.add(cxcResponseDetails);
		}
		return returnList;
	}

}
