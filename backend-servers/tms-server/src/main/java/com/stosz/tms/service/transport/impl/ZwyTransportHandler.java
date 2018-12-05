package com.stosz.tms.service.transport.impl;

import com.stosz.tms.dto.*;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.external.zwy.UploadOrderServiceLocator;
import com.stosz.tms.external.zwy.UploadOrderSoapBindingStub;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.zwy.*;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.DecimalUtils;
import com.stosz.tms.utils.U;
import com.stosz.tms.utils.XStreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.Service;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ZwyTransportHandler extends AbstractPlaceOrderTransportHandler {

    private static final Logger logger = LoggerFactory.getLogger(ZwyTransportHandler.class);

    @Override
    public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
        String requestBody = null;
        String responseBody = null;
        TransportOrderResponse response = new TransportOrderResponse(true);
        try {
            URL url = new URL(extendInfo.getInterfaceUrl());
            Service service = new UploadOrderServiceLocator();
            UploadOrderSoapBindingStub stub = new UploadOrderSoapBindingStub(url,service);
            requestBody = this.getTransportContent(orderRequest,extendInfo);
            responseBody = stub.uploadOrder(requestBody);

            Parameter<String, Object> parameter = XStreamUtils.xmlToMap(responseBody, null);

            String status = ((Parameter)((Parameter)parameter.get("Orders")).get("Order")).get("Status")+"";
            if(("Y").equalsIgnoreCase(status)){
                response.setCode(TransportOrderResponse.SUCCESS);
                String trackingNo = ((Parameter)((Parameter)parameter.get("Orders")).get("Order")).get("trackingNo")+"";
                response.setTrackingNo(trackingNo);
            }else {
                response.setCode(TransportOrderResponse.FAIL);
                String reasonCode = ((Parameter)((Parameter)parameter.get("Orders")).get("Order")).get("reasonCode")+"";
                response.setErrorMsg(reasonCode);
            }

        } catch (Exception e) {
            logger.info("ZwyTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
        } finally {
            logService.addLog(getCode(),orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
        }
        return response;
    }

    @Override
    public HandlerTypeEnum getCode() {
        return HandlerTypeEnum.ZWY;
    }

    /**
     * 获取请求参数
     * @param orderRequest
     * @param extendInfo
     * @return
     * @throws JAXBException
     */
    private String getTransportContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws JAXBException {
        Wsget wsget = new Wsget();
        Orders orders = new Orders();
        List<Order> orderList = new ArrayList<>();
        OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
        AccessRequest accessRequest = this.getAccessRequest(extendInfo);
        Order order = this.getOrder(orderRequest, extendInfo, orderLinkDto);
        orderList.add(order);
        orders.setOrders(orderList);
        wsget.setOrders(orders);
        wsget.setAccessRequest(accessRequest);
        String returnStr = U.getXmlString(wsget,false);
        return returnStr;
    }


    /**
     * 获得order
     * @param orderRequest
     * @param extendInfo
     * @param orderLinkDto
     * @return
     */
    private Order getOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo, OrderLinkDto orderLinkDto) {
        Order order = new Order();
        order.setOrderNo(orderRequest.getOrderNo());//订单号
        order.setDesCity(orderLinkDto.getCity());//目的城市名
        order.setDesState(orderLinkDto.getProvince());//目的省
        order.setDesSreet(orderLinkDto.getAddress());//目的街道名
        order.setAgent("FRM");//TODO 包装规则
        if (orderRequest.getPayState() == 0) {
            order.setCod("Y"); //到货付款
            order.setCodValue(orderRequest.getOrderAmount().longValue());//代收金额
        }
        order.setCodCurrency("USD");
        order.setDesCountry(orderLinkDto.getCountryCode());//目的国家二字码
        order.setSac_id("DSS");//TODO 口岸(外运提供)
        order.setShipperEN(extendInfo.getSenderContactName());//发件人 英文
        order.setShipperAddressEN(extendInfo.getSenderAddress());//发件人地址 英文
        order.setDepCity(extendInfo.getSenderCity());//始发城市 英文
        order.setDepState(extendInfo.getSenderProvince());//始发省 英文
        order.setShipperPhone(extendInfo.getSenderTelphone());//发件人电话
        order.setReceiverEN(orderLinkDto.getLastName() + orderLinkDto.getFirstName());// 收件人 英文
        order.setReceiverAddressEN(orderLinkDto.getAddress());//收件人地址 英文
        order.setReceiverPost(orderLinkDto.getZipcode());//邮编
        order.setReceiverPhone(orderLinkDto.getTelphone());//收件人电话

        BigDecimal amount = new BigDecimal(DecimalUtils.formateDecimal(orderRequest.getOrderAmount(), "#.##"));
        order.setDeclareValue(amount.longValue());//海关申报总价值
        BigDecimal weight = new BigDecimal(DecimalUtils.formateDecimal(new BigDecimal(orderRequest.getWeight()), "#.###"));
        order.setActualWeight(weight.longValue());//预报总重
        order.setPieces(orderRequest.getGoodsQty());//总件数
        order.setDesState(orderLinkDto.getProvince());//目的省
        order.setDesSteet(orderLinkDto.getAddress());//目的街道

        List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();
        List<Item> itemList = new ArrayList<>();
        itemList = orderDetails.stream().map(detail -> {
            Item item = new Item();
            String productNameEN = detail.getProductTitle();// 品名 英文
            String productNameCN = detail.getProductNameCN();//品名 中文
            Integer quantity = detail.getOrderDetailQty();//数量
            String unit = detail.getUnit();//单位
            Long declareValue = detail.getTotalAmount().longValue();
            Long productWeight = detail.getTotalWeight().longValue();

            item.setProductNameEN(productNameEN);//品名 英文
            item.setProductNameCN(productNameCN);//品名 中文
            item.setQuantity(quantity);
            item.setUnit(unit);
            item.setDeclareValue(declareValue);//海关申报总价
            item.setProductWeight(productWeight);//此品名总重
            return item;
        }).collect(Collectors.toList());
        Items items = new Items();
        items.setItems(itemList);
        order.setItems(items);
        return order;
    }

    /**
     * 获取AccessRequest
     * @param extendInfo
     * @return
     */
    private AccessRequest getAccessRequest(ShippingExtend extendInfo) {
        AccessRequest accessRequest = new AccessRequest();
        accessRequest.setUsername(extendInfo.getAccount());//username
        accessRequest.setPassword(extendInfo.getMd5Key());//password
        return accessRequest;
    }


    public static void main(String[] args) {
        String responseBody = "<?xml version='1.0' encoding='UTF-8' ?>\n" +
                "<WSRETURN>\n" +
                "\t<Orders>\t\t\n" +
                "\t\t<Order>\t\t\t\n" +
                "\t\t\t<OrderNo>testsf123456789</OrderNo>\t\t\t\n" +
                "\t\t\t<Status>Y</Status>\t\t\t\n" +
                "\t\t\t<SinoairNO>88024219970</SinoairNO>\t\t\t\n" +
                "\t\t\t<trackingNo>88024219970</trackingNo>\t\t\t\n" +
                "\t\t\t<Comments></Comments>\t\t\n" +
                "\t\t</Order>\n" +
                "\t</Orders>\n" +
                "</WSRETURN>";
        Parameter<String, Object> parameter = XStreamUtils.xmlToMap(responseBody, null);
        logger.info(""+parameter);
    }

}
