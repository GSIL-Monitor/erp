package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.TrackingTaskService;
import com.stosz.tms.service.track.impl.MalayDfTrackHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 马来东丰  手工下单,物流轨迹通
 */
public class MalayDfTest extends JUnitBaseTest {

    @Autowired
    MalayDfTrackHandler trackHandler;

    @Autowired
    TrackingTaskService service;

    @Test
    public void testAdd() {

    }

    @Test
    public void testQuery(){
        ShippingExtend extendInfo = new ShippingExtend();
        //TODO 生产环境
        extendInfo.setWaybilltrackUrl("http://etrace.9625taqbin.com/gli_trace/GDXTX010S10Action_doSearch.action");
        String trackingNo = "114502843873";//114502843873  114502773361
        trackHandler.captureTransTrack(new TrackRequest(trackingNo),extendInfo);
    }


}
