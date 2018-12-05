package com.stosz.order.sync.mq.consumer;

import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.order.ext.enums.WarehouseTypeEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.ext.mq.MatchLogisticsModel;
import com.stosz.order.service.OrderItemsService;
import com.stosz.order.service.OrderService;
import com.stosz.order.service.OrdersLinkService;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportRequest;
import com.stosz.tms.dto.TransportResponse;
import com.stosz.tms.enums.OrderTypeEnum;
import com.stosz.tms.service.ITransportFacadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @auther carter
 * create time    2017-11-10
 */
@Component
public class MatchLogisticsHandler extends AbstractHandler<MatchLogisticsModel> {

    public static final Logger logger = LoggerFactory.getLogger(MatchLogisticsHandler.class);

    @Autowired
    private FsmProxyService<Orders> ordersFsmProxyService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private ITransportFacadeService transportFacadeService;

    @Autowired
    private OrdersLinkService ordersLinkService;

    @Override
    public boolean handleMessage(String method, MatchLogisticsModel dataItem) {

        /**
         * 做物流匹配，分两种仓库类型去处理；自建仓返回运单号，推送到wms，wms回送确认信息;
         * 转寄仓人工干预，人工发送物流信息到转寄仓库，人工回传转寄仓库的确认信息；
         */

        WarehouseTypeEnum warehouseTypeEnum = dataItem.getWarehouseTypeEnum();
        Integer orderId = dataItem.getOrderId();

        Assert.isTrue(null!=orderId && orderId>0,"订单id为正整数");
        Orders orders = orderService.findOrderById(orderId);

        switch (warehouseTypeEnum) {
            case normal:   doNormalWarehouseLogisticsMath(orders);   break;
            case forward:  doForwardWarehouseLogisticsMath(orderId);  break;
        }


        return true;
    }

    /**
     * 处理转寄仓的物流匹配信息
     * @param orderId
     */
    private void doForwardWarehouseLogisticsMath(Integer orderId) {

        Orders order = orderService.findOrderById(orderId);
        //直接修改为待发货状态
        ordersFsmProxyService.processEvent(order, OrderEventEnum.forwardResponseOk,OrderEventEnum.forwardResponseOk.display());

    }

    /**
     * 处理普通仓库的物流匹配信息
     * @param orders
     */
    private void doNormalWarehouseLogisticsMath(Orders orders) {

        TransportRequest param = new TransportRequest();

       /***订单号*/
        param.setOrderId(orders.getId());
        /**建站内部订单编号*/
        param.setOrderNo(orders.getMerchantOrderNo());
        /***订单金额*/
        param.setOrderAmount(orders.getConfirmAmount());
        /***商品数量*/
        param.setGoodsQty(orders.getGoodsQty());
        /***重量 =============没有，默认不传递了*/
        param.setWeight(null);
        /**支付状态*/
        param.setPayState(orders.getPayState());
        /**订单类型**/
        param.setOrderTypeEnum(OrderTypeEnum.normal);
        /**区域ID*/
        param.setZoneId(orders.getZoneId());
        /**仓库ID*/
        param.setWarehouseId(orders.getWarehouseId());
        /**
         * 客服备注
         */
        param.setServiceRemark(orders.getMemo());
        /**
         * 客户留言
         */
        param.setRemark(orders.getCustomerMessage());
        /***订单客户信息*/
        OrderLinkDto orderLinkDto = Optional.ofNullable(ordersLinkService.getByOrderId(orders.getId())).map(item->{

            OrderLinkDto orderLinkDtox = new OrderLinkDto();
//            private String firstName;//名字
            orderLinkDtox.setFirstName(Optional.ofNullable(item.getFirstName()).orElse(""));
//            private String lastName;//姓
            orderLinkDtox.setLastName(Optional.ofNullable(item.getLastName()).orElse(""));
//            private String telphone;//电话
            orderLinkDtox.setTelphone(item.getTelphone());
//            private String email;//邮箱
            orderLinkDtox.setEmail(item.getEmail());
//            private String province;//省
            orderLinkDtox.setProvince(item.getProvince());
//            private String city;//城市
            orderLinkDtox.setCity(item.getCity());
//            private String area;//区域
            orderLinkDtox.setArea(item.getArea());
//            private String address;//地址
            orderLinkDtox.setAddress(item.getAddress());
//            private String zipcode;//邮编
            orderLinkDtox.setZipcode(item.getZipcode());
//            private String country;//国家
            orderLinkDtox.setCountry(item.getCountry());
//            private Long customerId;//客户ID
            orderLinkDtox.setCustomerId(item.getCustomerId());
//            private String countryCode;//国家编码
            orderLinkDtox.setCountryCode(item.getCountry());//todo  no country code
//            private String companyName;//公司名称
            orderLinkDtox.setCompanyName("buguniao");//todo   no  company name

            return orderLinkDtox;
        }).get();
        param.setOrderLinkDto(orderLinkDto);
        List<OrdersItem> itemList =  orderItemsService.getByOrderId(orders.getId());
         List<TransportOrderDetail> orderDetails = getTransportOrderDetailList(itemList);
        param.setOrderDetails(orderDetails);
//        private OrderTypeEnum orderTypeEnum;// 订单类型 订货订单 换货订单
//
//        private Integer payState;// 支付状态（0：未支付，1：已支付）
//
//        private Date orderDate;//订单时间
//
//        private String remark;//备注
//
//        private String currencyCode;//货币代码 CNY 人民币
//
//        private Integer warehouseId;//发货仓ID
//
//        private Integer zoneId;//区域ID
        logger.info("开始指派物流，请求参数{}", JsonUtil.toJson(param));
        TransportResponse transportResponse = transportFacadeService.addTransportOrder(param);
        logger.info("指派物流结束，返回结果{}", JsonUtil.toJson(transportResponse));
        Optional.ofNullable(transportResponse).ifPresent(item->{
            String doSuccess = item.getCode();
            if(doSuccess.equals("success")){
                //update logistic company , and  trans_no
                com.stosz.order.ext.dto.TransportRequest transportRequest = getTransportRequest(orders.getId(),item);
                orderService.updateOrderLogisticsInfo(transportRequest);
                Orders order = orderService.findOrderById(orders.getId());
                ordersFsmProxyService.processEvent(order,OrderEventEnum.logisticResponseOk,"指派物流["+transportResponse.getShippingName()+"]成功");
            }else if (doSuccess.equals("success_after_notify")){
                com.stosz.order.ext.dto.TransportRequest transportRequest = getTransportRequest(orders.getId(),item);
                orderService.updateOrderLogisticsInfo(transportRequest);
            }else{
                logger.error("指派物流失败,请联系物流相关人员");
            }
        });



    }

    private List<TransportOrderDetail> getTransportOrderDetailList(List<OrdersItem> itemList) {
        List<TransportOrderDetail> transportOrderDetails = new ArrayList<TransportOrderDetail>();
        itemList.stream().forEach(e->{
            TransportOrderDetail transportOrderDetail = new TransportOrderDetail();
            transportOrderDetail.setOrderItemId(e.getId().longValue());
            transportOrderDetail.setSku(e.getSku());
            transportOrderDetail.setProductTitle(e.getProductTitle());
            transportOrderDetail.setProductNameEN(e.getForeignTitle());
            transportOrderDetail.setProductNameCN(e.getProductTitle());
            transportOrderDetail.setOrderDetailQty(e.getQty());
            transportOrderDetail.setPrice(e.getSingleAmount());
            transportOrderDetail.setTotalAmount(e.getTotalAmount());
            transportOrderDetails.add(transportOrderDetail);
        });
        return  transportOrderDetails;
    }

    private com.stosz.order.ext.dto.TransportRequest getTransportRequest(Integer id, TransportResponse item) {
        com.stosz.order.ext.dto.TransportRequest transportRequest = new com.stosz.order.ext.dto.TransportRequest();
        transportRequest.setOrderId(id);
        transportRequest.setShippingWayId(item.getShippingWayId());
        transportRequest.setShippingName(item.getShippingName());
        transportRequest.setTrackingNo(item.getTrackingNo());
        return  transportRequest;
    }

    @Override
    public Class<MatchLogisticsModel> getTClass() {
        return MatchLogisticsModel.class;
    }



}
