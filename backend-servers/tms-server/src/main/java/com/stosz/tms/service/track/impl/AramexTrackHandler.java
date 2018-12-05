package com.stosz.tms.service.track.impl;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.rpc.Service;

import com.stosz.plat.utils.ArrayUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.external.aramex.Service_1_0_ServiceLocator;
import com.stosz.tms.external.aramex.trace.BasicHttpBinding_Service_1_01Stub;
import com.stosz.tms.external.aramex.trace.ShipmentTrackingRequest;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;

/**
 * aramex 查询物流轨迹
 * @version [1.0,2017年12月18日]
 */
@Component
public class AramexTrackHandler extends AbstractSingleTrackHandler {
	private static final org.slf4j.Logger logger= LoggerFactory.getLogger(AramexTrackHandler.class);

	public AramexTrackHandler(@Qualifier("aramexParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		try {
			long t1=System.currentTimeMillis();
			// 抓取物流轨迹地址
			URL url = new URL(shippingExtend.getWaybilltrackUrl());
			Service service = new Service_1_0_ServiceLocator();
			BasicHttpBinding_Service_1_01Stub stub = new BasicHttpBinding_Service_1_01Stub(url, service);
			ShipmentTrackingRequest trackingRequest = new ShipmentTrackingRequest();
			com.stosz.tms.external.aramex.trace.ClientInfo clientInfo = new com.stosz.tms.external.aramex.trace.ClientInfo();
			clientInfo.setAccountCountryCode("HK");
			clientInfo.setAccountEntity("HKG");
			clientInfo.setAccountNumber("134289");
			clientInfo.setAccountPin("321321");
			clientInfo.setUserName("wangyuanhua@stosz.com");
			clientInfo.setPassword("Bugo123456789*");
			clientInfo.setVersion("v1");

			// clientInfo
			trackingRequest.setClientInfo(clientInfo);
			trackingRequest.setGetLastTrackingUpdateOnly(false);
			trackingRequest.setShipments(new String[] { request.getTrackingNo() });
			com.stosz.tms.external.aramex.trace.Transaction transaction = new com.stosz.tms.external.aramex.trace.Transaction();

			trackingRequest.setTransaction(transaction);
			long t2=System.currentTimeMillis();
			com.stosz.tms.external.aramex.trace.ShipmentTrackingResponse responseBody = stub.trackShipments(trackingRequest);
			long t3=System.currentTimeMillis();
			if((t3-t2)/1000>15){
                logger.info("EXTREMELY CALL OVER TIMEtime{} seconds",(t3-t2)/1000);
			}
			if((t3-t2)/1000>3){
				logger.info(" CALL OVER TIME seconds",(t3-t2)/1000);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			String responsestr = objectMapper.writeValueAsString(responseBody);
			List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(responsestr);
			if(ArrayUtils.isEmpty(trackDetails)){
				trackResponse.setCode(TransportTrackResponse.FAIL);
			}else {
				trackResponse.setTrackDetails(trackDetails);
				trackResponse.setCode(TransportTrackResponse.SUCCESS);
			}
		} catch (Exception e) {
			logger.trace("aramex 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
			trackResponse.fail("Aramex物流轨迹抓取失败");
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
		return TrackApiTypeEnum.ARAMEX;
	}

}
