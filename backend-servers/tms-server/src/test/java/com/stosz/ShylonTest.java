package com.stosz;

import com.stosz.plat.utils.DateUtils;
import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.enums.OrderTypeEnum;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.IndonesiajtTrackHandler;
import com.stosz.tms.service.track.impl.ShylonTrackHandler;
import com.stosz.tms.service.transport.impl.IndonesiajtTransportHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 下单 轨迹通
 */
public class ShylonTest extends JUnitBaseTest {


    @Autowired
    ShylonTrackHandler trackHandler;


    @Test
    public void testQueryRoute(){
        ShippingExtend shippingExtend = new ShippingExtend();
        String trackNo = "61880172";

        //TODO 生产环境
        shippingExtend.setWaybilltrackUrl("http://www.ldl.com.cn/software/track");



        trackHandler.captureTransTrack(new TrackRequest(trackNo),shippingExtend);
    }

}
