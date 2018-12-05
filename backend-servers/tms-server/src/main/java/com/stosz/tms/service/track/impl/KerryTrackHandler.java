package com.stosz.tms.service.track.impl;

import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class KerryTrackHandler extends AbstractSingleTrackHandler {
    public KerryTrackHandler(@Qualifier("kerryParseStrategy")AbstractParseStartegy parseStartegy) {
        super(parseStartegy);
    }

    @Override
    public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
        TransportTrackResponse trackResponse = new TransportTrackResponse();
        try {
            String wayBillTrackUrl = getRequestAction(shippingExtend.getWaybilltrackUrl());
            StringBuilder requestAction = new StringBuilder(wayBillTrackUrl);
            Map paramMap = new HashMap<>();
            requestAction.append("/");
            requestAction.append(request.getTrackingNo());
            requestAction.append(StringUtil.concat("?token=", shippingExtend.getToken()));
            String response = HttpClientUtils.get(requestAction.toString(), "UTF-8", getHeader(shippingExtend));
            List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
            trackResponse.setTrackDetails(trackDetails);
            trackResponse.setCode(TransportTrackResponse.SUCCESS);
        }catch (Exception e) {
            logger.info("times 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
            trackResponse.fail("times物流轨迹抓取异常");
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
        return TrackApiTypeEnum.KEERY;
    }
}
