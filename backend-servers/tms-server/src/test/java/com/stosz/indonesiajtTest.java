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
public class indonesiajtTest extends JUnitBaseTest {


    @Autowired
    IndonesiajtTransportHandler transportHandler;

    @Autowired
    IndonesiajtTrackHandler trackHandler;

    @Test
    public  void testAdd() {
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend extendInfo = new ShippingExtend();
        OrderLinkDto orderLinkDto = new OrderLinkDto();

        orderRequest.setOrderNo("testsf1289991109");//客户订单
        orderRequest.setOrderTypeEnum(OrderTypeEnum.normal);
        orderRequest.setOrderAmount(new BigDecimal("2.2699"));//订单金额
        orderRequest.setPayState(0);//未付款
        orderRequest.setGoodsQty(1);
        orderRequest.setSendstarttime(new Date());
        orderRequest.setSendendtime(DateUtils.getNlateDate(365));
        orderRequest.setOrderDate(new Date());
        orderRequest.setTrackNo("testbn5ao569910");

        extendInfo.setSenderContactName("testname000");//发件人
        extendInfo.setSenderZipcode("14442");
        extendInfo.setSenderTelphone("13333333333");
        extendInfo.setSenderProvince("GUANDONG");
        extendInfo.setSenderCity("shenzhen");
        extendInfo.setSenderAddress("南山区动漫园address");
        //生产地址
        extendInfo.setMd5Key("779a66a12c8aba0fd87e1173c32a3899");//秘钥
        //测试秘钥
        //extendInfo.setMd5Key("a620da4a08f8da77090b45ebea823f63");//秘钥

        orderLinkDto.setCity("JAKARTA");
        orderLinkDto.setProvince("DKI JAKARTA");
        orderLinkDto.setAddress("TEST ALAMAT PENGIRIM");//
        orderLinkDto.setFirstName("cuckoo");//
        orderLinkDto.setTelphone("13333333333");//收件人电话
        orderLinkDto.setZipcode("15555");//收件人邮编
        orderLinkDto.setArea("南山区");

        TransportOrderDetail transportOrderDetail = new TransportOrderDetail();
        transportOrderDetail.setProductTitle("testname");
        transportOrderDetail.setTotalAmount(new BigDecimal(1.111));
        transportOrderDetail.setOrderDetailQty(1);//数量

        List<TransportOrderDetail> list = new ArrayList<>();
        list.add(transportOrderDetail);

        orderRequest.setOrderLinkDto(orderLinkDto);
        orderRequest.setOrderDetails(list);

        // TODO 测试地址
        //extendInfo.setInterfaceUrl("http://202.159.30.42:22223/jandt_web/szcuckoo/orderAction!createOrder.action");
        // TODO 生产地址
        extendInfo.setInterfaceUrl("http://jk.jet.co.id:22261/jant_szcuckoo_web/szcuckoo/orderAction!createOrder.action");
        try {
            IndonesiajtTransportHandler transportHandler = new IndonesiajtTransportHandler();
            transportHandler.transportPlaceOrder(orderRequest,extendInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void testQueryRoute(){
        ShippingExtend shippingExtend = new ShippingExtend();
        String trackNo = "JK0000002633"; //JK0000002633  JK0000000011

        //测试环境
//        shippingExtend.setWaybilltrackUrl("http://202.159.30.42:22223/jandt_web/szcuckoo/trackingAction!tracking.action");

        //TODO 生产环境
        shippingExtend.setWaybilltrackUrl("http://jk.jet.co.id:22261/jant_szcuckoo_web/szcuckoo/trackingAction!tracking.action");

        trackHandler.captureTransTrack(new TrackRequest(trackNo),shippingExtend);
    }

}
