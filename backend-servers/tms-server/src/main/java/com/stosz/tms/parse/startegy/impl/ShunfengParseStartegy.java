package com.stosz.tms.parse.startegy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("shunfengParseStartegy")
public class ShunfengParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(ShunfengParseStartegy.class);

	//查询成功响应
	private static final String OK = "OK";
	//查询失败响应
	private static final String ERR = "ERR";

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			Document doc = DocumentHelper.parseText(response);
			Element rootElt = doc.getRootElement(); // 获取根节点
			Element headEle = rootElt.element("Head");
			if(headEle.getStringValue().equals(ERR)){
				logger.trace("查询顺丰物流轨迹失败,接口返回{}",response);
				return null;
			}
			Element bodyEle = rootElt.element("Body");
			List<Element> list = bodyEle.elements();
			trackDetails = list.stream().map(item -> {
				TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();
				String mailno = item.attributeValue("mailno");
				shippingTrackDetail.setTrackNo(mailno);//运单号
				Element routeEle = item.element("Route");
				if(routeEle==null){
					return shippingTrackDetail;
				}
				String accept_time = routeEle.attributeValue("accept_time");
				String accept_address = routeEle.attributeValue("accept_address");
				String remark = routeEle.attributeValue("remark");

				shippingTrackDetail.setTrackTime(new Date(accept_time));//节点时间
				shippingTrackDetail.setTrackRecord(remark);//节点状态
				shippingTrackDetail.setCityName(accept_address);//节点地点
				return shippingTrackDetail;
			}).collect(Collectors.toList());
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("ShunfengParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

}
