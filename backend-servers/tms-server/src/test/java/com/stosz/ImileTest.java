package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.ImileTrackHandler;
import com.stosz.tms.service.transport.impl.ImileTransportHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 阿联酋imile 生产下单通
 */
public class ImileTest extends JUnitBaseTest {

    @Autowired
    ImileTransportHandler transportHandler;

    @Autowired
    ImileTrackHandler trackHandler;

    @Test
    public void testAdd() {
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend extendInfo = new ShippingExtend();
        OrderLinkDto orderLinkDto = new OrderLinkDto();

        orderRequest.setOrderNo("test123123");//订单号
        orderRequest.setOrderDate(new Date());//时间
        orderRequest.setGoodsQty(1);
        orderRequest.setOrderAmount(new BigDecimal(1));
        orderRequest.setPayState(0);

        orderLinkDto.setFirstName("李");
        orderLinkDto.setLastName("三");
        orderLinkDto.setProvince("广东省");
        orderLinkDto.setCity("深圳市");
        orderLinkDto.setArea("南山区");
        orderLinkDto.setAddress("动漫园");
        orderLinkDto.setTelphone("14444444444");

        extendInfo.setSenderContactName("张四");
        extendInfo.setSenderAddress("粤海5路测试地址");
        extendInfo.setSenderTelphone("13333333333");
        //todo 生产秘钥
//        extendInfo.setMd5Key("zK5Ji6hy76mY");//秘钥
        //todo 测试秘钥
        extendInfo.setMd5Key("gK5Ji6hy76mX");//秘钥
        List<TransportOrderDetail> list = new ArrayList<>();
        TransportOrderDetail detail = new TransportOrderDetail();
        detail.setProductTitle("测试商品");
        list.add(detail);
        orderRequest.setOrderDetails(list);
        orderRequest.setOrderLinkDto(orderLinkDto);
        //测试环境
        extendInfo.setInterfaceUrl("http://47.52.205.72:8713/IMILEDianShangInterface/K9/recivevOnLineOrder.do");
        //TODO 生产环境
//        extendInfo.setInterfaceUrl("http://47.52.205.72:8710/IMILEDianShangInterface/K9/recivevOnLineOrder.do");
        try {
            transportHandler.transportPlaceOrder(orderRequest,extendInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

    }

    @Test
    public void testQuery(){
        String trackNo = "100000000434";
        ShippingExtend shippingExtend = new ShippingExtend();
        //TODO 生产环境
        shippingExtend.setWaybilltrackUrl("http://47.52.205.72:8710/IMILEDianShangInterface/K9/getTrackingData.do");
        trackHandler.captureTransTrack(new TrackRequest(trackNo),shippingExtend);
    }

}
