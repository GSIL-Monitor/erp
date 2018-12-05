package com.stosz.tms.service.track.impl;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 香港coe物流轨迹抓取
 * @author 谢鹏程
 * @version [1.0,2017年12月22日]
 */
@Component
public class HkcoeTrackHandler extends AbstractSingleTrackHandler {
	private static final Logger logger= LoggerFactory.getLogger(HkcoeTrackHandler.class);

	public HkcoeTrackHandler(@Qualifier("hkcoeParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		HashMap<String, Object> requestMap = new HashMap<>();
		requestMap.put("nos", request.getTrackingNo());
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
			if(ArrayUtils.isEmpty(trackDetails)){
				trackResponse.setCode(TransportTrackResponse.FAIL);
			}else {
				trackResponse.setTrackDetails(trackDetails);
				trackResponse.setCode(TransportTrackResponse.SUCCESS);
			}
		} catch (Exception e) {
			logger.error("HkcoeTrackHandler trackingNo={},Exception={}", request.getTrackingNo(), e);
			trackResponse.fail("香港COE物流轨迹抓取异常");
		}
		return trackResponse;
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.HKCOE;
	}

	public static void main(String[] args) {
		// HkcoeTrackHandler trackHandler = new HkcoeTrackHandler(new
		// IndonesiaParseStartegy());
		// ShippingExtend shippingExtend = new ShippingExtend();
		// shippingExtend.setWaybilltrackUrl("http://111.230.142.168:8010/query.aspx");
		// trackHandler.captureTransTrack("JH0000001734", shippingExtend);

		// HashMap<String, String> test = new HashMap<>();
		// test.put("1", "2");
		// (h = key.hashCode()) ^ (h >>> 16)
		String test = "12212121186668";
		int hash = test.hashCode() ^ (test.hashCode() >>> 16);
		logger.info(""+hash);
	}

}
