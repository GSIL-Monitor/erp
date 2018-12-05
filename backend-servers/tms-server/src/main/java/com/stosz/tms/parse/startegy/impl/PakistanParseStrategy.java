package com.stosz.tms.parse.startegy.impl;

import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("pakistanParseStrategy")
public class PakistanParseStrategy extends AbstractParseStartegy {

    //查询成功响应
    private static final String OK = "OK";
    //查询失败响应
    private static final String ERR = "ERR";
    private Logger logger = LoggerFactory.getLogger(PakistanParseStrategy.class);
    @Override
    public List<TrackingTaskDetail> parseTrackContent(String response) {
        List<TrackingTaskDetail> trackDetails = new ArrayList<>();
        TrackingTaskDetail shippingTrackDetail = new TrackingTaskDetail();

        return trackDetails;
    }
}
