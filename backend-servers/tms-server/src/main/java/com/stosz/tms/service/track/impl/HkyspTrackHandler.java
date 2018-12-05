package com.stosz.tms.service.track.impl;

import java.util.List;

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

/**
 * 香港ysp 查询物流轨迹
 * @version [1.0,2017年12月26日]
 */
@Component
public class HkyspTrackHandler extends AbstractSingleTrackHandler {
	private static final Logger logger= LoggerFactory.getLogger(HkyspTrackHandler.class);

	public HkyspTrackHandler(@Qualifier("hkyspParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		try {
			String wayBillTrackUrl = getRequestAction(shippingExtend.getWaybilltrackUrl());
			StringBuilder requestAction = new StringBuilder(wayBillTrackUrl);

			StringBuilder sb = new StringBuilder();
			sb.append(shippingExtend.getWaybilltrackUrl());
			sb.append("?EmsApiTrack&cno=");
			sb.append(request.getTrackingNo());
			long t2=System.currentTimeMillis();

			String response = HttpClientUtils.get(sb.toString(), "gbk");
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
			logger.trace("HkyspTrackHandler 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
			trackResponse.fail("HkyspTrackHandler 物流轨迹抓取异常");
		}
		return trackResponse;
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.HKYSP;
	}

}
