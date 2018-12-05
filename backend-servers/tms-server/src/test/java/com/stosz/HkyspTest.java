package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.FeifanTrackHandler;
import com.stosz.tms.service.track.impl.HkyspTrackHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 香港ysp 查询轨迹通，手工下单
 */
public class HkyspTest extends JUnitBaseTest {

    @Autowired
    HkyspTrackHandler trackHandler;

    @Test
    public void testAdd() {

    }

    @Test
    public void testQuery(){
        String trackNo = "789287474069";
        ShippingExtend shippingExtend = new ShippingExtend();
        //TODO 生产环境
        shippingExtend.setWaybilltrackUrl("http://119.23.28.96/cgi-bin/GInfo.dll");
        Date date = new Date();
        long time = date.getTime();
        trackHandler.captureTransTrack(new TrackRequest(trackNo),shippingExtend);
        Date date1 = new Date();
        long time1 = date1.getTime();
        /*System.out.println(time1-time);*/
    }

}
