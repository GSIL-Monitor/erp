package com.stosz.tms.service.track.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stosz.plat.utils.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;

/**nim 查询物流轨迹
 * @version [1.0,2017年12月14日]
 */
@Component
public class NimTrackHandler extends AbstractSingleTrackHandler {
	private static  final Logger logger= LoggerFactory.getLogger(NimTrackHandler.class);
	public NimTrackHandler(@Qualifier("nimParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		try {
			String wayBillTrackUrl = getRequestAction(shippingExtend.getWaybilltrackUrl());
			StringBuilder requestAction = new StringBuilder(wayBillTrackUrl);
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("bill_no", request.getTrackingNo());
			long t2=System.currentTimeMillis();
			String response = HttpClientUtils.get(requestAction.toString(), paramMap, "UTF-8");
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
			logger.trace("nim 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
			trackResponse.fail("Nim物流轨迹抓取异常");
		}
		return trackResponse;
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.NIM;
	}

}
