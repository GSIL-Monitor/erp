package com.stosz.order.service.outsystem.wms;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.stosz.order.ext.dto.WmsResponse;
import com.stosz.order.ext.enums.OrderTypeEnum;
import com.stosz.order.ext.enums.PayMethodEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersAddition;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.service.OrderService;
import com.stosz.order.service.OrdersWebInterfaceRecordService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.enums.InterfaceNameEnum;
import com.stosz.plat.enums.InterfaceTypeEnum;
import com.stosz.plat.enums.ResponseResultEnum;
import com.stosz.plat.wms.SendWmsUtils;
import com.stosz.plat.wms.bean.*;
import com.stosz.plat.wms.model.WmsConfig;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * 订单跟wms的业务实现层
 * @author liusl
 */
@Service
public class WmsOrderService implements IWmsOrderService {

    private Logger logger = LoggerFactory.getLogger(WmsOrderService.class);


    @Autowired
    private OrdersWebInterfaceRecordService ordersWebInterfaceRecordService;
    @Autowired
    private OrderService orderService;

    private static ObjectMapper objectMapper;

    public  ObjectMapper getObjectMapper(){
        if(null == objectMapper )
        {
            objectMapper =  new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            SimpleModule module = new SimpleModule();
            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            objectMapper.registerModule(module);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return  objectMapper;
        }
    /**
    调用WMS 系统的接口 TODO  具体的参数需要探讨
     * @param orders
     * @param ordersAddition
     * @param ordersItems
     * @author liusl
     */
    @Override
    public void subCreateSaleOrder(Orders orders, OrdersAddition ordersAddition, List<OrdersItem> ordersItems, OrdersLink ordersLink) {

        Integer  recordDetailId = 0 ;
        try {
            SaleOrderCreateRequest saleOrderCreateRequest = convertToSaleOrderCreateRequest(orders, ordersAddition, ordersItems, ordersLink);
            logger.info("WMS新增销售订单接口调用开始 WmsOrderService.saleOrderCreateRequest 请求参数 request-->{}",getObjectMapper().writeValueAsString(saleOrderCreateRequest));

            //准备参数
            Pair<String, String> urlParamPair = SendWmsUtils.prepareWmsPushParams(saleOrderCreateRequest, WmsConfig.Service_subCreateSaleOrder);
            recordDetailId = ordersWebInterfaceRecordService.addOrUpdateRequest(InterfaceTypeEnum.orders, InterfaceNameEnum.addSaleOrder,orders.getId(),urlParamPair.getKey(),urlParamPair.getValue());
            //发送请求
            Pair<ResponseResultEnum, String> responseResultEnumContentPair = SendWmsUtils.requestWms(urlParamPair.getKey(), urlParamPair.getValue());
            //记录响应结果
            String wmsResponseContent = responseResultEnumContentPair.getValue();
            ResponseResultEnum responseResultEnum = responseResultEnumContentPair.getKey();

            ordersWebInterfaceRecordService.saveResponse(recordDetailId, wmsResponseContent, responseResultEnum);

            Assert.hasText(wmsResponseContent,"未知原因导致返回结果为空，请与wms系统相关人员确认");
            Map<String,Object> resultMap = getObjectMapper().readValue(wmsResponseContent, Map.class);
            logger.info("WMS新增销售订单调用结束 返回参数 response-->{}", wmsResponseContent);
        }
        catch (Exception e) {
            logger.info("WMS新增销售订单调用失败 失败原因 error-->{}",e.getMessage());
            //调用wms失败了  不用进行事务的回滚操作 直接重新推送就好
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
    /**
     *
     * 将数据转换成请求wms的请求数据
     * @param order
     * @return
     * @date 2017年11月24日
     * @author liusl
     */
    public SaleOrderCreateRequest convertToSaleOrderCreateRequest(Orders order, OrdersAddition ordersAddition, List<OrdersItem> ordersItems, OrdersLink ordersLink){
        SaleOrderCreateRequest saleOrderCreateRequest= new SaleOrderCreateRequest();
        List<SaleOrderHeadInfoBean> headList = new ArrayList<>();
        //这个是当个的数据，如果需要批量 循环的调用即可
        SaleOrderHeadInfoBean saleOrderHeadInfoBean = convertToSaleOrderHeadInfoBean(order,ordersAddition, ordersLink, ordersItems);
        headList.add(saleOrderHeadInfoBean);
        saleOrderCreateRequest.setHeadList(headList);
        return  saleOrderCreateRequest;
    }
    /**
     *
     * 转换成SaleOrderHeadInfoBean
     * @param order
     * @return
     * @date 2017年11月24日
     * @author liusl
     */
    private SaleOrderHeadInfoBean convertToSaleOrderHeadInfoBean(Orders order, OrdersAddition ordersAddition, OrdersLink ordersLink, List<OrdersItem> ordersItems) {
        SaleOrderHeadInfoBean saleOrderHeadInfoBean = new SaleOrderHeadInfoBean();
        saleOrderHeadInfoBean.setOrderCode(order.getMerchantOrderNo());//配货单号
        saleOrderHeadInfoBean.setWarehouseCode("Y");//TODO 仓库编码
        String wmsBity = "";
        if (OrderTypeEnum.fromId(order.getOrderTypeEnum()).name().equals("normal")){
            wmsBity="EBUS";
        }else if(OrderTypeEnum.fromId(order.getOrderTypeEnum()).name().equals("internal")){
            wmsBity="OFFLINE";
        }else{
            wmsBity="SPECIAL";
        }
        saleOrderHeadInfoBean.setBity(wmsBity);//订单类型
        saleOrderHeadInfoBean.setConsignee(ordersLink.getLastName()+ ordersLink.getFirstName());
        saleOrderHeadInfoBean.setProvinceName(ordersLink.getProvince()==null?"--":ordersLink.getProvince());
        saleOrderHeadInfoBean.setCityName(StringUtils.isEmpty(ordersLink.getCity())?"--":ordersLink.getCity());
        saleOrderHeadInfoBean.setAreaName(StringUtils.isEmpty(ordersLink.getArea())?"--":ordersLink.getArea());
        saleOrderHeadInfoBean.setAddress(ordersLink.getAddress());
        saleOrderHeadInfoBean.setMobile(StringUtils.isEmpty(ordersLink.getTelphone())?"--":ordersLink.getTelphone());
        saleOrderHeadInfoBean.setShopCode("BGN");
        saleOrderHeadInfoBean.setShopName("布谷鸟");
        saleOrderHeadInfoBean.setIsDeliveryPay("true");
        saleOrderHeadInfoBean.setOrderPrice(order.getOrderAmount().toString());
        saleOrderHeadInfoBean.setAmountReceivable(order.getConfirmAmount()==null?"0":order.getConfirmAmount().toString());
        saleOrderHeadInfoBean.setActualPayment("100");
        saleOrderHeadInfoBean.setAuditTime(order.getStateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));//审核时间
        //saleOrderHeadInfoBean.setFromType(fromType);//来源类型 TODO
        saleOrderHeadInfoBean.setFromCode(ordersAddition.getOrderFrom());//来源单号 TODO
        saleOrderHeadInfoBean.setPlatformCode("2");//来源平台编码
        saleOrderHeadInfoBean.setPlatformName("布谷鸟");//来源平台名称 从数据字典里
        saleOrderHeadInfoBean.getIsUrgency();//是否紧急（0 普通，1紧急）TODO
        saleOrderHeadInfoBean.setDownDate(order.getPurchaserAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));//下单时间
        saleOrderHeadInfoBean.setPayTime(order.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));//支付时间
        //saleOrderHeadInfoBean.setAuditUserCode(auditUserCode);//审核人编码 TODO
        saleOrderHeadInfoBean.setLogisticsCompanyCode(order.getLogisticsId().toString());//物流公司编码
        saleOrderHeadInfoBean.setLogisticsCompanyName(order.getLogisticsName());//物流公司名称
        saleOrderHeadInfoBean.setExpressNo(StringUtils.isEmpty(order.getTrackingNo())?"":order.getTrackingNo());//物流单号tConfirmAmount().setScale(4, BigDecimal.ROUND_HALF_UP).toString());//应收金额
        //saleOrderHeadInfoBean.setIsPostagePay(isPostagePay);//邮费是否到付 TODO
        //saleOrderHeadInfoBean.setIsInvoice(isInvoice);//是否需要发票 TODO
        if(order.getPaymentMethod().equals(PayMethodEnum.cod.ordinal())){
            saleOrderHeadInfoBean.setActualPayment("0");//货到付款 实际支付金额
        }else {
            BigDecimal confirmAmount = order.getConfirmAmount();
            saleOrderHeadInfoBean.setActualPayment(confirmAmount.toString());//预付款实际支付金额
        }
        // saleOrderHeadInfoBean.setInvoiceName(invoiceName);//发票抬头 TODO
        //saleOrderHeadInfoBean.setInvoicePrice(invoicePrice);//发票金额 TODO
        //saleOrderHeadInfoBean.setInvoiceText(invoiceText);//发票内容 TODO
        saleOrderHeadInfoBean.setGoodsOwner(MBox.BGN_COMPANY_NO);//货主
        /* TODO 去物流系统拉取那些特殊物流公司的必填字段
        saleOrderHeadInfoBean.setGwf1();
        saleOrderHeadInfoBean.setGwf2();
        saleOrderHeadInfoBean.setGwf3();
        saleOrderHeadInfoBean.setGwf4();
        saleOrderHeadInfoBean.setGwf5();*/
        saleOrderHeadInfoBean.setBuyerMessage(order.getMemo());
        List<SaleOrderDetailInfoBean> detailList = convertToSaleOrderDetailInfoBean(ordersItems);
        saleOrderHeadInfoBean.setDetailList(detailList);
        return saleOrderHeadInfoBean;
    }
    /**
     *
     * 转换成SaleOrderDetailInfoBean集合
     * @return
     * @date 2017年11月24日
     * @author liusl
     */

    private List<SaleOrderDetailInfoBean> convertToSaleOrderDetailInfoBean(List<OrdersItem> ordersItems) {
        List<SaleOrderDetailInfoBean> saleOrderDetailInfoBeans = new ArrayList<SaleOrderDetailInfoBean>();
        ordersItems.forEach(e->{
        	SaleOrderDetailInfoBean saleOrderDetailInfoBean = new SaleOrderDetailInfoBean();
            saleOrderDetailInfoBean.setSku(e.getSku());//sku编码
            saleOrderDetailInfoBean.setQty(e.getQty().toString());//数量
            saleOrderDetailInfoBean.setPrice(e.getSingleAmount().toString());//单价
            //saleOrderDetailInfoBean.setActualAmount(actualAmount);//实际金额 TODO
            //saleOrderDetailInfoBean.setDiscount(discount);//折扣 TODO
            //saleOrderDetailInfoBean.setAmountPayable(amountPayable);//应付金额 TODO
            //saleOrderDetailInfoBean.setExpireDate(expireDate);//商品过期日期 TODO
            //saleOrderDetailInfoBean.setProduceCode(produceCode);//生产批次 TODO
            //saleOrderDetailInfoBean.setBatchCode(batchCode);//批次编码 TODO
            saleOrderDetailInfoBeans.add(saleOrderDetailInfoBean);
        });
        return saleOrderDetailInfoBeans;
    }
    /**
     * 取消订单
     */
    @Override
    public WmsResponse subCancelOrder(OrderCancelRequest orderCancelRequest) {
        WmsResponse restResult = new WmsResponse();

        Integer recordDetailId = 0 ;
        try {
            CancelOrderRequest cancelOrderRequest = convertToCancelOrderRequest(orderCancelRequest);
            logger.info("WMS取消订单接口调用 WmsOrderService.subCancelOrder 请求参数 request-->{}",getObjectMapper().writeValueAsString(cancelOrderRequest));


            //准备参数
            Pair<String, String> urlParamPair = SendWmsUtils.prepareWmsPushParams(cancelOrderRequest, WmsConfig.Service_subCancelOrder);

             Orders orders = orderService.findOrderByMerchantOrderNos(Arrays.asList(orderCancelRequest.getOrderId())).stream().findFirst().get() ;
            //记录请求信息
            recordDetailId = ordersWebInterfaceRecordService.addOrUpdateRequest(InterfaceTypeEnum.orders,InterfaceNameEnum.cancelSaleOrder,orders.getId(),urlParamPair.getKey(),urlParamPair.getValue());

//            String result = SendWmsUtils.sendWmsRequest(cancelOrderRequest,WmsConfig.Service_subCancelOrder);

            //发送请求
            Pair<ResponseResultEnum, String> responseResultEnumContentPair = SendWmsUtils.requestWms(urlParamPair.getKey(), urlParamPair.getValue());
            String responseContent = responseResultEnumContentPair.getValue();
            ResponseResultEnum responseResultEnum = responseResultEnumContentPair.getKey();

            //记录响应信息
            ordersWebInterfaceRecordService.saveResponse(recordDetailId,responseContent,responseResultEnum);

            Assert.hasText(responseContent,"未知原因导致取消订单接口返回结果为空，请与wms系统相关人员确认");
            Map<String,Object> resultMap = getObjectMapper().readValue(responseContent, Map.class);
            String isSuccess = resultMap.get("isSuccess").toString();
            restResult.setDesc(String.valueOf(resultMap.get("body")));
            if(isSuccess.equals("false")){
                restResult.setCode(WmsResponse.FAIL);
            }
            logger.info("WMS取消订单接口调用结束 返回参数 response-->{}",responseContent);
        }
        catch (Exception e) {
            logger.error("WMS取消订单接口调用失败 失败原因 error-->{}",e.getMessage());
            restResult.setCode(WmsResponse.FAIL);
            restResult.setDesc("WMS取消订单接口调用失败,请重试或联系管理员");
        }
        return restResult;
    }
    /**
     *
     * 将请求参数转换成取消订单接口的请求参数
     * @param orderCancelRequest
     * @return
     * @date 2017年11月24日
     * @author liusl
     */
    private CancelOrderRequest convertToCancelOrderRequest(OrderCancelRequest orderCancelRequest) {
        CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();
        cancelOrderRequest.setOrderCode(orderCancelRequest.getOrderId());//取消单号
        cancelOrderRequest.setOrderType(orderCancelRequest.getOrderType());//订单类型
        cancelOrderRequest.setGoodsOwner(orderCancelRequest.getGoodsOwner());//货主
        cancelOrderRequest.setRemark(orderCancelRequest.getMemo());//备注
        return cancelOrderRequest;
    }

}

