package com.stosz.tms.service.track.impl;

import java.io.StringWriter;
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

import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.shunfeng.RouteRequest;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.shunfeng.ShunfengUtil;

/**
 * 顺丰 查询物流轨迹
 * @version [1.0,2017年12月12日]
 */
@Component
public class ShunfengTrackHandler extends AbstractSingleTrackHandler {
	private static final Logger logger= LoggerFactory.getLogger(ShunfengTrackHandler.class);


	//报文请求头部测试环境
//	private static final String HEAD = "<Request service='RouteService' lang='zh-CN'>\n<Head>BSPdevelop</Head>\n<Body>";
	//报文请求头部生产环境
	private static final String HEAD = "<Request service='RouteService' lang='zh-CN'>\n<Head>7550026054</Head>\n<Body>";
	//报文请求尾部
	private static final String END = "</Body>\n</Request>";

	public ShunfengTrackHandler(@Qualifier("shunfengParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse transportTrackResponse = new TransportTrackResponse();
		try {
			String wayBillTrackUrl = getRequestAction(shippingExtend.getWaybilltrackUrl());
			StringBuilder requestAction = new StringBuilder(wayBillTrackUrl);
			Map paramMap = new HashMap<>();
			String xmlstr = this.getRouteQueryXmlStr(request.getTrackingNo());
			String verfyCode = this.generateVerfyCode(xmlstr,shippingExtend);
			paramMap.put("xml",xmlstr);
			paramMap.put("verifyCode",verfyCode);
			long t2=System.currentTimeMillis();
			String response = HttpClientUtils.doPost(requestAction.toString(),paramMap, "UTF-8");
			long t3=System.currentTimeMillis();
			if((t3-t2)/1000>15){
				logger.info("EXTREMELY CALL OVER TIMEtime{} seconds",(t3-t2)/1000);
			}
			if((t3-t2)/1000>3){
				logger.info(" CALL OVER TIME seconds",(t3-t2)/1000);
			}
			List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
			transportTrackResponse.setTrackDetails(trackDetails);
			transportTrackResponse.setCode(TransportTrackResponse.SUCCESS);
		} catch (Exception e) {
			logger.trace("顺丰 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
			transportTrackResponse.fail("顺丰物流轨迹抓取异常");
		}
		return transportTrackResponse;
	}

	public Map<String, String> getHeader(ShippingExtend shippingExtend) {
		Map<String, String> headMap = new HashMap<>();
		headMap.put("Authorization", StringUtil.concat("Bearer ", shippingExtend.getMd5Key()));
		return headMap;
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.SHUNFENG;
	}

	private String getRouteQueryXmlStr(String trackingNo) {
		Assert.notNull(trackingNo,"运单号为空");
		StringBuilder sb = new StringBuilder();
		StringWriter writer = new StringWriter();
		RouteRequest routeRequest = new RouteRequest();
		try{
			routeRequest.setTracking_type(1);
			routeRequest.setMethod_type(1);
			routeRequest.setTracking_number(trackingNo);

			JAXBContext jc = JAXBContext.newInstance(RouteRequest.class);
			Marshaller ma = jc.createMarshaller();
			ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 去掉生成xml的默认报文头
			ma.setProperty(Marshaller.JAXB_FRAGMENT, true);
			ma.marshal(routeRequest, writer);
			sb.append(HEAD);
			sb.append(writer.toString());
			sb.append(END);
			return sb.toString();
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
