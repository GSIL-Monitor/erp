package com;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.GdexTrackHandler;
import com.stosz.tms.service.transport.impl.GdexTransportHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试和正式环境下单，更新重量,查询轨迹均通  预先导入运单号
 */
public class GdexTest extends JUnitBaseTest{

    @Autowired
    GdexTransportHandler transportHandler;
    @Autowired
    GdexTrackHandler trackHandler;
//    @Autowired
//    GdexUpdateWeight gdexUpdateWeight;

    @Test
    public void testAdd() {
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend extendInfo = new ShippingExtend();
        OrderLinkDto orderLinkDto = new OrderLinkDto();
        orderLinkDto.setFirstName("test");
        orderLinkDto.setLastName("test");
        orderLinkDto.setAddress("test Address");
        orderLinkDto.setCity("shanghai");
        orderLinkDto.setProvince("shagnhai");
        orderLinkDto.setZipcode("474107");
        orderLinkDto.setTelphone("13333333333");
        orderRequest.setOrderLinkDto(orderLinkDto);
        orderRequest.setTrackNo("85641254666");//运单号
        orderRequest.setTrackNo("85641258119");//运单号
        orderRequest.setOrderAmount(new BigDecimal("2"));//订单金额
        orderRequest.setGoodsQty(1);
        List<TransportOrderDetail> listProduct = new ArrayList<>();
        TransportOrderDetail detail = new TransportOrderDetail();
        listProduct.add(detail);
        orderRequest.setOrderDetails(listProduct);

        extendInfo.setMd5Key("vV9d4gBNZUiWxvX2Cn5P5ObzdxpCqMgIIzyFNWm7ZQY=");
        //测试环境
        extendInfo.setInterfaceUrl("http://uat.gdexapi.com/api/key/PreAlert/Upload?src=Suya");
        //生产环境
//        extendInfo.setInterfaceUrl("https://www.gdexapi.com/api/key/PreAlert/Upload?src=Suya");
        try {
            transportHandler.transportPlaceOrder(orderRequest,extendInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void testQuery(){
        ShippingExtend shippingExtend = new ShippingExtend();
        //生产环境
        shippingExtend.setWaybilltrackUrl("https://www.gdexapi.com/api/key/PreAlert/DeliveryStatus/");
        shippingExtend.setMd5Key("vV9d4gBNZUiWxvX2Cn5P5ObzdxpCqMgIIzyFNWm7ZQY=");
        trackHandler.captureTransTrack(new TrackRequest("8715175700"),shippingExtend);
    }

    @Test
    public void testUpdateWeight(){
        String trackNo = "8564125889";
        //测试环境
        String url = "http://uat.gdexapi.com/api/key/PreAlert/BatchUpdateWeight";
        //生产环境
//        String url = "https://www.gdexapi.com/api/key/PreAlert/BatchUpdateWeight";
        Double weight = new Double("1.11");
        transportHandler.updateWeight(trackNo,url,weight);
    }

}
