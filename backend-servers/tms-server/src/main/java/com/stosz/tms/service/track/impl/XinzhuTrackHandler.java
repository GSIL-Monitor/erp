package com.stosz.tms.service.track.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.xinzhu.Order;
import com.stosz.tms.model.xinzhu.Qrylist;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.Base64;
import com.stosz.tms.utils.DESCryptography;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.shunfeng.ShunfengUtil;


/**
 * 新竹森鸿 查询物流轨迹
 * @version [1.0,2018年1月4日]
 */
@Component
public class XinzhuTrackHandler extends AbstractSingleTrackHandler {
	private static  final Logger logger= LoggerFactory.getLogger(XinzhuTrackHandler.class);

	private static final String iv = "VKXHKJVG";

	public XinzhuTrackHandler(@Qualifier("xinzhuParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		try {
			String wayBillTrackUrl = getRequestAction(shippingExtend.getWaybilltrackUrl());
			StringBuilder requestAction = new StringBuilder(wayBillTrackUrl);
			Map paramMap = new HashMap<>();
			String xmlstr = this.getRouteQueryXmlStr(request.getTrackingNo());
			paramMap.put("no",xmlstr);
			paramMap.put("v",shippingExtend.getMd5Key());
			long t2=System.currentTimeMillis();
			String response = HttpClientUtils.get(requestAction.toString(),paramMap, "UTF-8");
			long t3=System.currentTimeMillis();
			if((t3-t2)/1000>15){
				logger.info("EXTREMELY CALL OVER TIMEtime{} seconds",(t3-t2)/1000);
			}
			if((t3-t2)/1000>3){
				logger.info(" CALL OVER TIME seconds",(t3-t2)/1000);
			}
			List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
			if(CollectionUtils.isNullOrEmpty(trackDetails)){
				trackResponse.setCode(TransportTrackResponse.FAIL);
			}else{
				trackResponse.setTrackDetails(trackDetails);
				trackResponse.setCode(TransportTrackResponse.SUCCESS);
			}
		} catch (Exception e) {
			logger.trace("新竹 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
		}
		return trackResponse;
	}

	public Map<String, String> getHeader(ShippingExtend shippingExtend) {
		Map<String, String> headMap = new HashMap<>();
		headMap.put("Authorization", StringUtil.concat("Bearer ", shippingExtend.getMd5Key()));
		return headMap;
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.XINZHU;
	}

	/**
	 * 获取加密字符串 des cec(xml+金轮+加密向量)
	 * 加密向量=VKXHKJVGv  （固定值）
	 * @param trackingNo
	 * @return
	 */
	private String getRouteQueryXmlStr(String trackingNo) {
		Assert.notNull(trackingNo,"运单号为空");
        Qrylist qrylist = new Qrylist();
        Order order = new Order();
        order.setOrderid(trackingNo);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        qrylist.setOrderList(orderList);
		StringWriter writer = new StringWriter();
		StringBuilder sb = new StringBuilder();
		try{
			JAXBContext jc = JAXBContext.newInstance(Qrylist.class);
			Marshaller ma = jc.createMarshaller();
			ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ma.marshal(qrylist, writer);
			String goldKey = DateUtils.format(DateUtils.getNlateDate(40),"yyyyMMdd");
			String Content = Base64.encode(DESCryptography.DES_CBC_Encrypt(trackingNo.getBytes(), goldKey.getBytes(),iv.getBytes()));
			return Content;
		}catch (Exception e){
			logger.trace(e.getMessage());
		}
		return null;
	}

	/**
	 * 生成验证码
	 * @return
	 */
	private String generateVerfyCode(String xmlstr,ShippingExtend extendInfo){
		String verifyCode = ShunfengUtil.md5EncryptAndBase64(xmlstr + extendInfo.getMd5Key());
		return verifyCode;
	}
}
