package com.stosz.tms.service.track.impl;

import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PakistanTrackHandler extends AbstractSingleTrackHandler {
    private static  final Logger logger= LoggerFactory.getLogger(PakistanTrackHandler.class);
    public PakistanTrackHandler(@Qualifier("pakistanParseStrategy")AbstractParseStartegy parseStartegy) {
        super(parseStartegy);
    }

    @Override
    public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
        TransportTrackResponse transportTrackResponse = new TransportTrackResponse();
        try {
            String wayBillTrackUrl = getRequestAction(shippingExtend.getWaybilltrackUrl());
            StringBuilder requestAction = new StringBuilder(wayBillTrackUrl);
            Map paramMap = new HashMap<>();
            paramMap.put("secretkey","434a2ed2-8056-4579-9a47-cfaa48676ffb80000");
            paramMap.put("Orderid",request.getTrackingNo());
            String requestBody= JsonUtils.toJson(paramMap);
            String trackingNo=request.getTrackingNo();
            Assert.notNull(trackingNo,"运单号为空");
//            String verfyCode = this.generateVerfyCode(xmlstr,shippingExtend);
//            paramMap.put("xml",xmlstr);
//            paramMap.put("verifyCode",verfyCode);
            String response = HttpClientUtils.doPost(requestAction.toString(),requestBody, ContentType.APPLICATION_JSON, "UTF-8");
            List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
            transportTrackResponse.setTrackDetails(trackDetails);
            transportTrackResponse.setCode(TransportTrackResponse.SUCCESS);
        } catch (Exception e) {
            logger.trace("Royal物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
            transportTrackResponse.fail("顺丰物流轨迹抓取异常");
        }
        return transportTrackResponse;
    }

    @Override
    public TrackApiTypeEnum getCode() {
        return TrackApiTypeEnum.PAKISTAN;
    }
}
