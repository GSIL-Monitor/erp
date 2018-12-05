package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.BlackCatTrackHandler;
import com.stosz.tms.service.track.impl.ShunfengTrackHandler;
import com.stosz.tms.service.transport.impl.ShunfengTransportHandler;
import com.stosz.tms.task.BlackCatTrackTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 下单 查询通
 */
public class BlackcatTest extends JUnitBaseTest {

    @Autowired
    BlackCatTrackHandler trackHandler;


    @Autowired
    BlackCatTrackTask blackCatTrackTask;

    @Test
    public void testQueryRoute(){

//        trackHandler.captureTransTrack();
    }

    @Test
    public void testSeceduled(){
        blackCatTrackTask.trackCaptureDispatch();
    }
}
