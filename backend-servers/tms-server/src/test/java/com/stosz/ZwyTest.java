package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.transport.impl.ZwyTransportHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 下单 通（弃用）
 */
public class ZwyTest extends JUnitBaseTest {


    @Autowired
    ZwyTransportHandler transportHandler;

    @Test
    public void testAddShip(){
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend extendInfo = new ShippingExtend();
        OrderLinkDto orderLinkDto = new OrderLinkDto();


        orderRequest.setOrderNo("testsf123456789");//客户订单
        orderRequest.setWeight(1.22D);//重量
        orderRequest.setOrderAmount(new BigDecimal("2.2699"));//订单金额
        orderRequest.setPayState(0);//未付款
        orderRequest.setGoodsQty(1);
        extendInfo.setSenderContactName("zhangshan");//TODO 发件人英文名
        extendInfo.setSenderTelphone("13333333333");//寄件方联系人电话
        extendInfo.setSenderProvince("guangdong province");//TODO 寄件方省 英文
        extendInfo.setSenderCity("shengzheng city");//TODO 寄件方市
        extendInfo.setSenderAddress("test english address");// TODO 发件人英文地址
        extendInfo.setAccount("testonce");//用户名
        extendInfo.setMd5Key("testonce123");//密码

        orderLinkDto.setCity("uman");
        orderLinkDto.setProvince("uman");
        orderLinkDto.setAddress("test english address");//TODO 收件人地址 英文
        orderLinkDto.setCountryCode("UA");//到件方国家二字码
        orderLinkDto.setCountry("新加坡");
        orderLinkDto.setFirstName("zhang");//TODO 收件人 英文
        orderLinkDto.setLastName("shan");//TODO 收件人 英文
        orderLinkDto.setTelphone("13333333333");//收件人电话
        orderLinkDto.setZipcode("3228");//收件人邮编

        TransportOrderDetail transportOrderDetail = new TransportOrderDetail();
        transportOrderDetail.setProductNameEN("book");//品名 英文
        transportOrderDetail.setProductNameCN("书本");//品名 中文
        transportOrderDetail.setProductTitle("testname");
        transportOrderDetail.setTotalAmount(new BigDecimal(1.111));
        transportOrderDetail.setOrderDetailQty(1);//数量
        transportOrderDetail.setUnit("册");//数量单位
        transportOrderDetail.setTotalWeight(new BigDecimal(3.36));//明细总重

        List<TransportOrderDetail> list = new ArrayList<>();
        list.add(transportOrderDetail);

        orderRequest.setOrderLinkDto(orderLinkDto);
        orderRequest.setOrderDetails(list);

        extendInfo.setInterfaceUrl("http://ceosgatewaytest.sinoair.com/services/UploadOrder?wsdl");// TODO 测试地址
        try {
            transportHandler.transportPlaceOrder(orderRequest,extendInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void testQueryRoute(){
        ShippingExtend shippingExtend = new ShippingExtend();
        shippingExtend.setWaybilltrackUrl("http://bsp-ois.sit.sf-express.com:9080/bsp-ois/sfexpressService");//TODO 测试地址
        shippingExtend.setMd5Key("j8DzkIFgmlomPt0aLuwU");
//        trackHandler.captureTransTrack("444031845310",shippingExtend);
    }

}
