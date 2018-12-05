package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.TrackingTaskService;
import com.stosz.tms.service.track.impl.DfTrackHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 新加坡东丰  手工下单,物流轨迹通
 */
public class DfTest extends JUnitBaseTest {

    @Autowired
    DfTrackHandler trackHandler;

    @Autowired
    TrackingTaskService service;

    @Test
    public void testAdd() {

    }

    @Test
    public void testQuery(){
        ShippingExtend extendInfo = new ShippingExtend();
        extendInfo.setWaybilltrackUrl("http://118.201.157.11/cgi-bin/GInfo.dll?EmmisTrack");//TODO 生产环境
        String trackingNo = "080019679283";
        trackHandler.captureTransTrack(new TrackRequest(trackingNo),extendInfo);
    }


}
