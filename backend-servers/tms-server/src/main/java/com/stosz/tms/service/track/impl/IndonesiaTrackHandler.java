package com.stosz.tms.service.track.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.parse.startegy.impl.IndonesiaParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;

/**
 * 印尼HY物流轨迹抓取
 * @author feiheping
 * @version [1.0,2017年12月18日]
 */
@Component
public class IndonesiaTrackHandler extends AbstractSingleTrackHandler {
	private static  final Logger logger= LoggerFactory.getLogger(IndonesiaTrackHandler.class);

	public IndonesiaTrackHandler(@Qualifier("indonesiaParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		HashMap<String, Object> requestMap = new HashMap<>();
		requestMap.put("__VIEWSTATE", "/wEPDwUKMTg2MTYyMDc5M2RkyxV1MHctQb5TbjgP/inlT008aOE=");
		requestMap.put("__VIEWSTATEGENERATOR", "EDD8C9AE");
		requestMap.put("__EVENTVALIDATION", "/wEWAwLwruPsDAKvgcuQCQKM54rGBgW1F0FjdWgE6PCmAxYmU5pBx101");
		requestMap.put("TrackNum", request.getTrackingNo());
		requestMap.put("Button1", "追踪");
		try {
			long t2=System.currentTimeMillis();
			String response = HttpClientUtils.doPost(shippingExtend.getWaybilltrackUrl(), requestMap, "UTF-8");
			long t3=System.currentTimeMillis();
			if((t3-t2)/1000>15){
				logger.info("EXTREMELY CALL OVER TIMEtime{} seconds",(t3-t2)/1000);
			}
			if((t3-t2)/1000>3){
				logger.info(" CALL OVER TIME seconds",(t3-t2)/1000);
			}
			List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
			trackResponse.setCode(TransportTrackResponse.SUCCESS);
			trackResponse.setTrackDetails(trackDetails);
		} catch (Exception e) {
			logger.error("IndonesiaTrackHandler trackingNo={},Exception={}", request.getTrackingNo(), e);
			trackResponse.fail("印尼HY物流轨迹抓取失败");
		}
		return trackResponse;
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.INDONESIA;
	}

	public static void main(String[] args) {
		IndonesiaTrackHandler trackHandler = new IndonesiaTrackHandler(new IndonesiaParseStartegy());
		ShippingExtend shippingExtend = new ShippingExtend();
		shippingExtend.setWaybilltrackUrl("http://111.230.142.168:8010/query.aspx");
		trackHandler.captureTransTrack(new TrackRequest("JH0000001734"), shippingExtend);
	}

}
