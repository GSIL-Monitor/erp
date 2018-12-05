package com;

import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.impl.PilippineParseStartegy;
import com.stosz.tms.service.track.impl.PhilippineXhTrackHandler;
import com.stosz.tms.service.transport.impl.PhilippineXhTransportHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * philineXH新增、查询均调通
 */
public class PhilippineTest{


    public static final Logger logger = LoggerFactory.getLogger(PhilippineTest.class);

    @Autowired
    private PhilippineXhTransportHandler philippineXhTransportHandler;

    @Test
    public void testAddShip() {
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend extendInfo = new ShippingExtend();
        OrderLinkDto orderLinkDto = new OrderLinkDto();

        orderLinkDto.setCustomerId(new Long("123123"));
//        extendInfo.setCode("Cuckoo");
        extendInfo.setSender("testCCC");
        extendInfo.setSenderTelphone("13333333344");
        extendInfo.setSenderAddress("testCCC");
        extendInfo.setSenderProvince("ceship");
        extendInfo.setSenderCity("TESTCITYCCC");
        extendInfo.setSenderTown("TESTtownCCC");
        extendInfo.setMd5Key("L6PfT1qKA3nOyoA1");//todo 生产md5key

        orderLinkDto.setFirstName("testaaaFirstNameCCC");
        orderLinkDto.setLastName("testaaaLastNameCCC");
        orderLinkDto.setTelphone("14444444455");
        orderLinkDto.setAddress("testbCCC");
        orderLinkDto.setProvince("testCCC");
        orderLinkDto.setCity("testCCC");
        orderLinkDto.setArea("testCCC");

        orderRequest.setOrderNo("testCCC");
        orderRequest.setTrackNo("test6666556");
//        orderRequest.setWeight(new Double("1.0"));
        orderRequest.setOrderAmount(new BigDecimal("1.0"));
        orderRequest.setRemark("testbbb");
        orderRequest.setPayState(0);

        orderRequest.setOrderLinkDto(orderLinkDto);

        extendInfo.setInterfaceUrl("http://47.52.70.244/api/TransBill/AddTransBill");// TODO 测试地址
//        extendInfo.setInterfaceUrl("http://admin.gex.ph/api/TransBill/AddTransBill");// TODO 生产地址


        try {
            philippineXhTransportHandler.transportPlaceOrder(orderRequest,extendInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void testQueryShip(){
        ShippingExtend shippingExtend = new ShippingExtend();
        //98990016595测试环境运单号
        //989300100887 生产环境运单号
//        shippingExtend.setWaybilltrackUrl("http://47.52.70.244/open_api/V1.1/route/info");//TODO 测试地址
        shippingExtend.setWaybilltrackUrl("http://admin.gex.ph/open_api/V1.1/route/info");// TODO 生产地址
        PilippineParseStartegy pilippineParseStartegy = new PilippineParseStartegy();
        PhilippineXhTrackHandler philippineXhTrackHandler = new PhilippineXhTrackHandler(pilippineParseStartegy);
        philippineXhTrackHandler.captureTransTrack(new TrackRequest("989300100859"),shippingExtend);

    }
}
