package com.stosz.tms.service.track.impl;

import java.util.LinkedHashMap;
import java.util.List;

import com.stosz.plat.utils.ArrayUtils;
import org.apache.http.entity.ContentType;
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
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;

/**
 * 印尼JT物流轨迹抓取
 * @author xiepengcheng
 * @version [1.0,2017年12月21日]
 */
@Component
public class IndonesiajtTrackHandler extends AbstractSingleTrackHandler {
	private static  final Logger logger= LoggerFactory.getLogger(IndonesiajtTrackHandler.class);

	public IndonesiajtTrackHandler(@Qualifier("indonesiajtParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		LinkedHashMap<String, Object> requestMap = new LinkedHashMap<>();
		requestMap.put("awb", request.getTrackingNo());
		try {
			long t2=System.currentTimeMillis();
			String response = HttpClientUtils.doPost(shippingExtend.getWaybilltrackUrl(), JsonUtils.toJson(requestMap), ContentType.APPLICATION_JSON,
					"UTF-8");

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
			logger.error("IndonesiajtTrackHandler trackingNo={},Exception={}", request.getTrackingNo(), e);
			trackResponse.fail("印尼JT物流轨迹抓取异常");
		}
		return trackResponse;
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.INDONESIAJT;
	}

}
