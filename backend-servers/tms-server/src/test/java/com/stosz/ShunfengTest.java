package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.ShunfengTrackHandler;
import com.stosz.tms.service.transport.impl.ShunfengTransportHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 下单 查询通
 */
public class ShunfengTest extends JUnitBaseTest {

    @Autowired
    ShunfengTransportHandler transportHandler;

    @Autowired
    ShunfengTrackHandler trackHandler;
    @Test
    public void testAddShip(){
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend extendInfo = new ShippingExtend();
        OrderLinkDto orderLinkDto = new OrderLinkDto();


        orderRequest.setOrderNo("testsf1234567821");//客户订单
        orderRequest.setWeight(1.22D);//重量
        orderRequest.setOrderAmount(new BigDecimal("2.2699"));

        extendInfo.setSender("布谷鸟测试333");//寄件公司名称
        extendInfo.setSenderContactName("测试联系人");//寄件公司联系人
        extendInfo.setSenderTelphone("13333333333");//寄件方联系人电话
        extendInfo.setSenderCountry("中国");//寄件方国家
        extendInfo.setSenderProvince("广东省");//寄件方省
        extendInfo.setSenderCity("深圳市");//寄件方市
        extendInfo.setSenderTown("南山区");//寄件方区
        extendInfo.setSenderAddress("测试测试寄件方地址");
        extendInfo.setSenderZipcode("518000");//寄件方邮编
//        extendInfo.setMd5Key("j8DzkIFgmlomPt0aLuwU");//TODO 测试秘钥
        extendInfo.setMd5Key("QdxNmnsuykSOFxd5ScfYdFbmlAGFgqHs");//TODO 生产秘钥


        orderLinkDto.setCountryCode("SIN01D");//到件方国家编码
        orderLinkDto.setCountry("新加坡");
        orderLinkDto.setFirstName("测试人");
        orderLinkDto.setLastName("测试人");
        orderLinkDto.setTelphone("13333333333");
        orderLinkDto.setProvince("Singapore");
        orderLinkDto.setCity("Singapore");
        orderLinkDto.setArea("Singapore");
        orderLinkDto.setAddress("测试地址");
        orderLinkDto.setZipcode("628105");//邮编

        TransportOrderDetail transportOrderDetail = new TransportOrderDetail();
        transportOrderDetail.setProductTitle("testname");
        transportOrderDetail.setTotalAmount(new BigDecimal("1.111"));
        transportOrderDetail.setOrderDetailQty(1);//数量
        transportOrderDetail.setUnit("box");//单位

        List<TransportOrderDetail> list = new ArrayList<>();
        list.add(transportOrderDetail);
        orderRequest.setOrderLinkDto(orderLinkDto);
        orderRequest.setOrderDetails(list);

//        extendInfo.setInterfaceUrl("http://bsp-ois.sit.sf-express.com:9080/bsp-ois/sfexpressService");// TODO 测试地址
        extendInfo.setInterfaceUrl("http://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService");// TODO 生产地址

        try {
            transportHandler.transportPlaceOrder(orderRequest,extendInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void testQueryRoute(){
        ShippingExtend shippingExtend = new ShippingExtend();
//        shippingExtend.setWaybilltrackUrl("http://bsp-ois.sit.sf-express.com:9080/bsp-ois/sfexpressService");//TODO 测试地址
        shippingExtend.setWaybilltrackUrl("http://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService");//TODO 生产地址
//          todo 测试秘钥
//        shippingExtend.setMd5Key("j8DzkIFgmlomPt0aLuwU");
        //todo 生产秘钥
        shippingExtend.setMd5Key("QdxNmnsuykSOFxd5ScfYdFbmlAGFgqHs");
        //测试环境运单号：444031845310  生产环境运单号：617606288493
        trackHandler.captureTransTrack(new TrackRequest("953152528339"),shippingExtend);
    }

}
