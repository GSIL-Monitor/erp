package com.stosz.tms.service.transport.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.tms.model.api.*;
import com.stosz.tms.utils.JsonUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.stereotype.Component;

import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.enums.OrderTypeEnum;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.XStreamUtils;
import com.thoughtworks.xstream.XStream;
import org.springframework.util.Assert;

/**
 * Kerry下单
 */
@Component
public class KerryTransportHandler extends AbstractPlaceOrderTransportHandler {

    private Logger logger = LoggerFactory.getLogger(KerryTransportHandler.class);

    @Override
    public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
        TransportOrderResponse response = new TransportOrderResponse(true);
        String requestBody = null;
        String responseBody = null;
        try {
            KerryContent kerryContent = getKerryContent(orderRequest, extendInfo);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(kerryContent);
            String req = "{\"req\": {\"shipment\":" + json + "}}";

            responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), req, ContentType.TEXT_PLAIN, "UTF-8", this.getPostHeader(extendInfo));
            Parameter<String, Object> kerryResp = JsonUtils.jsonToObject(responseBody, Parameter.class);
            LinkedHashMap res = kerryResp.getObject("res");
            LinkedHashMap shipment = (LinkedHashMap) res.get("shipment");
            if (shipment.get("status_code").equals("000")) {
                response.setCode(TransportOrderResponse.SUCCESS);
                response.setTrackingNo((String) shipment.get("con_no"));
            } else {
                response.setCode(TransportOrderResponse.FAIL);
                response.setErrorMsg((String) shipment.get("con_no"));
            }
        } catch (Exception e) {
            logger.info("YufengTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
        } finally {
            logService.addLog(getCode(), orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
        }

        return response;
    }

    private KerryContent getKerryContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
        OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
        KerryContent kerryContent = new KerryContent();

        kerryContent.setCon_no(orderRequest.getTrackNo());
        kerryContent.setS_name(extendInfo.getSender());
        kerryContent.setS_address(extendInfo.getSenderAddress());
        kerryContent.setS_zipcode(extendInfo.getSenderZipcode());
        kerryContent.setS_mobile1(extendInfo.getSenderTelphone());
        kerryContent.setR_name(orderLinkDto.getFirstName() + orderLinkDto.getLastName());
        kerryContent.setR_address(orderLinkDto.getAddress());
        kerryContent.setR_zipcode(orderLinkDto.getZipcode());
        kerryContent.setR_mobile1(orderLinkDto.getTelphone());
        kerryContent.setService_code("ND");
        kerryContent.setTot_pkg(orderRequest.getGoodsQty());
        return kerryContent;
    }

    @Override
    protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
        Assert.notNull(extendInfo, "配置信息不能为空");
        Assert.notNull(orderRequest, "订单信息不能为空");
        Assert.hasText(orderRequest.getTrackNo(), "运单号码不能为空");
        Assert.notNull(orderRequest.getGoodsQty(), "订单数量不能为空");
//        Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");
        Assert.notNull(extendInfo, "寄件配置信息不能为空");
        Assert.notNull(extendInfo.getInterfaceUrl(), "下单请求接口地址不能为空");
        Assert.isTrue(extendInfo.getSenderZipcode().length()==5,"ZipCode是5位数");

        OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
        Assert.notNull(orderLinkDto, "收件人信息不能为空");
        String name = null;
        if (orderLinkDto.getFirstName() != null) {
            name += orderLinkDto.getFirstName();
        }
        if (orderLinkDto.getLastName() != null) {
            name += orderLinkDto.getLastName();
        }
        Assert.notNull(name, "收件人姓名能为空");
        Assert.hasText(orderLinkDto.getProvince(), "收件人省份不能为空");
        Assert.hasText(orderLinkDto.getCity(), "收件人城市不能为空");
        Assert.hasText(orderLinkDto.getArea(), "收件人区不能为空");
        Assert.hasText(orderLinkDto.getAddress(), "收件人地址不能为空");
        Assert.hasText(orderLinkDto.getTelphone(), "收件人电话不能为空");

    }

    private Map<String, String> getPostHeader(ShippingExtend extendInfo) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("content-Type", "application/json; charset=UTF-8 ");
        headers.put("app_id", "CUCKOO");
        headers.put("app_key", "525942c3-8d4e-4b68-bb1c-f509300e72f5");
        return headers;
    }

    @Override
    public HandlerTypeEnum getCode() {
        return HandlerTypeEnum.KERRY;
    }

}
