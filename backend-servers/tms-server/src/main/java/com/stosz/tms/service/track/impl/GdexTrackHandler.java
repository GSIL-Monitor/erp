package com.stosz.tms.service.track.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;

/**gdex 查询物流轨迹
 * @version [1.0,2017年12月28日]
 */
@Component
public class GdexTrackHandler extends AbstractSingleTrackHandler {
	private static final Logger logger= LoggerFactory.getLogger(GdexTrackHandler.class);

	public GdexTrackHandler(@Qualifier("gdexParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(shippingExtend.getWaybilltrackUrl());
			sb.append(request.getTrackingNo());
			Map<String, String> headMap = new HashMap<>();
			headMap.put("ApiKey", shippingExtend.getMd5Key());
			long t2=System.currentTimeMillis();
			String response = HttpClientUtils.get(sb.toString(), "UTF-8", headMap);
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
			logger.trace("GdexTrackHandler 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
		}
		return trackResponse;
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.GDEX;
	}

}
