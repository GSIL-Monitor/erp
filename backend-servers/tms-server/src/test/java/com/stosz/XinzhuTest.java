package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.NimTrackHandler;
import com.stosz.tms.service.track.impl.XinzhuTrackHandler;
import com.stosz.tms.service.transport.impl.NimTransportHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 新竹 手工下单,物流轨迹 通
 */
public class XinzhuTest extends JUnitBaseTest{


    @Autowired
    XinzhuTrackHandler trackHandler;

    @Test
    public void testAdd() {

    }

    @Test
    public void testQuery(){
        ShippingExtend shippingExtend = new ShippingExtend();
        //TODO 生产环境
        shippingExtend.setMd5Key("7856A92C813BFEE003AAEB434545ACC3");
        shippingExtend.setWaybilltrackUrl("https://www.hct.com.tw/phone/searchGoods_Main.aspx");
        trackHandler.captureTransTrack(new TrackRequest("6702527985"),shippingExtend);
    }


}
