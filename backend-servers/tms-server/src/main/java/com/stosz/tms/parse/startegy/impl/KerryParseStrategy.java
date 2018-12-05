package com.stosz.tms.parse.startegy.impl;

import com.stosz.tms.dto.Parameter;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class KerryParseStrategy extends AbstractParseStartegy {
    private Logger logger = LoggerFactory.getLogger(KerryParseStrategy.class);

    public List<TrackingTaskDetail> parseTrackContent(String response) {
        List<TrackingTaskDetail> trackDetails = new ArrayList<>();
        try {
            Parameter<String, Object> kerryResp = JsonUtils.jsonToObject(response, Parameter.class);
            LinkedHashMap res = kerryResp.getObject("res");

            LinkedHashMap status = (LinkedHashMap) res.get("status");
            TrackingTaskDetail trackingTaskDetail = new TrackingTaskDetail();
            trackingTaskDetail.setTrackNo((String)status.get("con_no"));

        } catch (Exception e) {

        }
        return null;
    }
}
