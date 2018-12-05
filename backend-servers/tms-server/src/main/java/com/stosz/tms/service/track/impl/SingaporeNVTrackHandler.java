//package com.stosz.tms.service.track.impl;
//
//import com.stosz.tms.dto.TrackRequest;
//import com.stosz.tms.dto.TransportTrackResponse;
//import com.stosz.tms.enums.HandlerTypeEnum;
//import com.stosz.tms.model.TrackingTaskDetail;
//import com.stosz.tms.model.api.ShippingExtend;
//import com.stosz.tms.parse.startegy.AbstractParseStartegy;
//import com.stosz.tms.service.track.AbstractSingleTrackHandler;
//import com.stosz.tms.utils.HttpClientUtils;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 新加坡NV 查询物流轨迹
// * @version [1.0,2017年12月27日]
// */
//@Component
//public class SingaporeNVTrackHandler extends AbstractSingleTrackHandler {
//
//	public SingaporeNVTrackHandler(@Qualifier("singaporeNVParseStartegy") AbstractParseStartegy parseStartegy) {
//		super(parseStartegy);
//	}
//
//	@Override
//	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
//		TransportTrackResponse trackResponse = new TransportTrackResponse();
//		try {
//			String wayBillTrackUrl = getRequestAction(shippingExtend.getWaybilltrackUrl());
//			StringBuilder requestAction = new StringBuilder(wayBillTrackUrl);
//			Map<String, String> paramMap = new HashMap<>();
//			paramMap.put("id",request.getTrackingNo());
//			String response = HttpClientUtils.get(requestAction.toString(), paramMap, "UTF-8");
//			List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
//			trackResponse.setTrackDetails(trackDetails);
//			trackResponse.setCode(TransportTrackResponse.SUCCESS);
//		} catch (Exception e) {
//			logger.trace("SingaporeNVTrackHandler 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
//			trackResponse.fail("SingaporeNVTrackHandler 物流轨迹抓取异常");
//		}
//		return trackResponse;
//	}
//
//	@Override
//	public HandlerTypeEnum getCode() {
//		return HandlerTypeEnum.SINGAPORENV;
//	}
//
//}
