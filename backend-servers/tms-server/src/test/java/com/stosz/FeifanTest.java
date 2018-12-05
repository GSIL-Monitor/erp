package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.CxcTrackHandler;
import com.stosz.tms.service.track.impl.FeifanTrackHandler;
import com.stosz.tms.service.transport.impl.CxcTransportHandler;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 非凡 查询轨迹通
 */
public class FeifanTest extends JUnitBaseTest {

    @Autowired
    FeifanTrackHandler trackHandler;

    @Test
    public void testAdd() throws UnsupportedEncodingException {
    }

    /**
     * 运单号月份太久，物流商直接返回错误
     *
     */
    @Test
    public void testQuery(){
        //620091209532  03455124124874 06200912095320 91226049
        String trackNo = "55495124124874";
        ShippingExtend shippingExtend = new ShippingExtend();
        //TODO 生产环境
        shippingExtend.setWaybilltrackUrl("http://220.132.209.89/API/esp.php");
        TransportTrackResponse transportTrackResponse = trackHandler.captureTransTrack(new TrackRequest(trackNo), shippingExtend);
        transportTrackResponse.getTrackDetails();
        System.out.println(transportTrackResponse.getTrackDetails().size());
    }

}
