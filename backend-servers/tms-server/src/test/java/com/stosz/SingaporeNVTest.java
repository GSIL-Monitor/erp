//package com.stosz;
//
//import com.stosz.tms.base.JUnitBaseTest;
//import com.stosz.tms.dto.TrackRequest;
//import com.stosz.tms.model.api.ShippingExtend;
//import com.stosz.tms.service.track.impl.FeifanTrackHandler;
//import com.stosz.tms.service.track.impl.SingaporeNVTrackHandler;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// *  新加坡NV(新加坡YJY 壹加壹)查询轨迹
// *  （新加坡NV的没有下单接口 获取物流轨迹接口有问题 物流部没有督促解决  暂停对接）
// */
//public class SingaporeNVTest extends JUnitBaseTest {
//
//    @Autowired
//    SingaporeNVTrackHandler trackHandler;
//
//    @Test
//    public void testAdd() {
//
//    }
//
//    @Test
//    public void testQuery(){
//        ShippingExtend shippingExtend = new ShippingExtend();
//        shippingExtend.setWaybilltrackUrl("https://api.ninjavan.co/sg/shipperpanel/app/tracking");//TODO 生产环境
//        trackHandler.captureTransTrack(new TrackRequest("1"),shippingExtend);
//    }
//
//}
