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
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("yufengParseStartegy")
public class YufengParseStartegy extends AbstractParseStartegy {

	private Logger logger = LoggerFactory.getLogger(YufengParseStartegy.class);

	//查询成功响应
	private static final String HAS = "true";
	//查询失败响应
	private static final String NOTHAS = "false";

	@Override
	public List<TrackingTaskDetail> parseTrackContent(String response) {
		List<TrackingTaskDetail> trackDetails = new ArrayList<>();
		try {
			Document doc = DocumentHelper.parseText(response);
			Element rootElt = doc.getRootElement(); // 获取根节点
			Element infoEle = rootElt.element("info");
			String jobno = infoEle.element("jobno").getStringValue();//运单号
			if("false".equals(infoEle.element("ishas").getStringValue())){
				logger.trace("查询顺丰物流轨迹失败不存在该运单,接口返回{}",response);
				return null;
			}
			if("false".equals(infoEle.element("ishaspod").getStringValue())){
				logger.trace("查询顺丰物流轨迹,存在订单,但未生成物流信息",response);
				return null;
			}
			Element podlistEle = infoEle.element("podlist");
			List<Element> list = podlistEle.elements();
			trackDetails = list.stream().map(item -> {
				TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String datastr = item.element("scandate").getStringValue().concat(" ").concat(item.element("scandime").getStringValue());//时间
				String scanstation = item.element("scanstation").getStringValue();//位置
				String scanstatusname = item.element("scanstatusname").getStringValue();//节点状态
				shippingTrackDetail.setTrackNo(jobno);//运单号
				try {
					shippingTrackDetail.setTrackTime(sdf.parse(datastr));//时间
				} catch (ParseException e) {
					logger.trace("YufengParseStartegy 错误，{}",e);
				}
				shippingTrackDetail.setCityName(scanstation);//城市
				shippingTrackDetail.setTrackStatus(scanstatusname);//节点状态
				shippingTrackDetail.setTrackRecord(scanstatusname);//节点记录
				return shippingTrackDetail;
			}).collect(Collectors.toList());
			sortTrackByTrackTimeAsc(trackDetails);//排序
			return trackDetails;
		} catch (Exception e) {
			logger.trace("YufengParseStartegy 错误，入参response={}，e={}",response,e);
		}
		return null;
	}

}
