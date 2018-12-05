package com.stosz.order.service.outsystem.logistics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.ext.mq.MatchLogisticsModel;
import com.stosz.order.ext.mq.MatchLogisticsStatusModel;
import com.stosz.order.service.OrderItemsService;
import com.stosz.order.service.OrderService;
import com.stosz.order.service.OrdersLinkService;
import com.stosz.order.service.outsystem.logistics.model.CustomerInfo;
import com.stosz.order.service.outsystem.logistics.model.OrderInfo;
import com.stosz.order.service.outsystem.logistics.model.OrderNotifyLogistics;
import com.stosz.order.service.outsystem.logistics.model.ProductInfo;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.RestClient;
import com.stosz.product.ext.model.AttributeValue;
import com.stosz.product.ext.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单推送到物流接口
 * 
 * @author liusl
 */
@Component
public class LogisticsServiceImpl implements ILogisticsService {

    private Logger logger = LoggerFactory.getLogger(LogisticsServiceImpl.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdersLinkService ordersLinkService;
    @Autowired
    private IProductService productService;
    @Autowired
    private OrderItemsService orderItemsService;

    @Value("${project.erp.logistics.url}")
    private String preLogisticsUrl;// 物流信息的接口地址

    @Override
    public boolean notifyOrderToLogistics(MatchLogisticsModel matchLogisticsModel) {
        try {
            Assert.notNull(matchLogisticsModel, "实体不能为空");
            Integer orderId = matchLogisticsModel.getOrderId();
            Assert.notNull(orderId, "订单ID不能为空");
            // 根据订单ID查找订单信息
            Orders orders = orderService.findOrderById(orderId);
            // 根据订单ID查找商品信息
            List<OrdersItem> orderItems = orderItemsService.getByOrderId(orderId);
            // 根据订单ID查找联系人信息
            OrdersLink ordersLink = ordersLinkService.getByOrderId(orderId);
            OrderNotifyLogistics orderNotifyLogistics = convertToOrderNotifyLogistics(orders, orderItems, ordersLink);
            orderNotifyLogistics.setKey("key从哪里取值啊?");
            logger.info("LogisticsServiceImpl notifyOrderToLogistics() 推送消息到物流系统开始 请求参数 request-->{}", new ObjectMapper().writeValueAsString(orderNotifyLogistics));
            Map resultMap = RestClient.getClient().postForObject(preLogisticsUrl + "/newerp/order/create_order", orderNotifyLogistics, Map.class);
            Assert.notNull(resultMap,"未知原因导致返回结果为空，请与物流系统相关人员确认");
            String result = (String)resultMap.get("result");
            if (result.equals("success")) {
                // TODO 调用状态机将状态变更为待发货 同时更新物流信息到订单表
                orderService.updateOrder(orders);

            } else {
                // TODO 调用状态机将状态变更为指派物流失败 同时需要写入到订单上行队列
                orderService.updateOrder(orders);
            }
            logger.info("LogisticsServiceImpl notifyOrderToLogistics() 推送消息到物流系统结束 返回信息 response--> {}", resultMap.toString());
            return true;
        }
        catch (Exception e) {
            logger.error("推送信息失败,失败信息 {}", e.getMessage());
            return false;// 调用失败的时候，让消息队列阻塞在这里，重复调用
        }
    }

    /**
     * 组装成能直接推送到物流系统的数据
     * 
     * @return
     * @date 2017年11月22日
     * @author liusl
     */
    public OrderNotifyLogistics convertToOrderNotifyLogistics(Orders orders, List<OrdersItem> orderItems, OrdersLink ordersLink) {
        OrderNotifyLogistics orderNotifyLogistics = new OrderNotifyLogistics();
        OrderInfo orderInfo = convertToOrderInfo(orders);
        orderNotifyLogistics.setOrder_info(orderInfo);
        List<ProductInfo> productInfos = convertToProductInfo(orderItems);
        orderNotifyLogistics.setProduct_info(productInfos);
        CustomerInfo customerInfo = convertToCustomerInfo(ordersLink,orders.getZoneId());
        orderNotifyLogistics.setCustomer_info(customerInfo);
        return orderNotifyLogistics;
    }

    /**
     * 组装成能推送到物流系统订单信息
     * 
     * @return
     * @date 2017年11月22日
     * @author liusl
     */
    public OrderInfo convertToOrderInfo(Orders orders) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCurrency_code(orders.getCurrencyCode());
        orderInfo.setDate_purchase(orders.getPurchaserAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        orderInfo.setId_department(orders.getSeoDepartmentId());
        orderInfo.setId_increment(String.valueOf(orders.getId()));
        orderInfo.setId_users(orders.getSeoUserId());
        orderInfo.setId_warehouse(orders.getWarehouseId());
        orderInfo.setPayment_method(orders.getPaymentMethod());
        orderInfo.setPrice_total(orders.getOrderAmount());
        orderInfo.setRemark(orders.getMemo());
        return orderInfo;
    }

    /**
     * 组装成能直接推送到物流系统的商品信息
     * 
     * @return
     * @date 2017年11月22日
     * @author liusl
     */
    public List<ProductInfo> convertToProductInfo(List<OrdersItem> orderItems) {
        List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
        for (OrdersItem orderItem : orderItems) {
            ProductInfo productInfo = new ProductInfo();
            List<AttributeValue> attributeValues = productService.getValueListBySku(orderItem.getSku());
            List<String> attrs = new ArrayList<String>();
            List<String> attrsTitles = new ArrayList<String>();
            for (AttributeValue attributeValue : attributeValues) {
                attrs.add(attributeValue.getId().toString());
                attrsTitles.add(attributeValue.getTitle());
            }
            productInfo.setAttrs(attrs);
            productInfo.setAttrs_title(attrsTitles);
            productInfo.setId_product(orderItem.getCreatorId());
            productInfo.setId_product_sku(orderItem.getSkuId());
            productInfo.setSku(orderItem.getSku());
            productInfo.setPrice(orderItem.getSingleAmount());
            productInfo.setProduct_title(orderItem.getProductTitle());
            productInfo.setQty(orderItem.getQty());
            productInfo.setSale_title(orderItem.getForeignTitle());
            productInfos.add(productInfo);
        }
        return productInfos;
    }

    /**
     * 组装成能推送到物流系统客户信息
     * 
     * @return
     * @date 2017年11月22日
     * @author liusl
     */
    private CustomerInfo convertToCustomerInfo(OrdersLink ordersLink, Integer zoneId) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setAddress(ordersLink.getAddress());
        customerInfo.setArea(ordersLink.getArea());
        customerInfo.setCity(ordersLink.getCity());
        customerInfo.setCountry(ordersLink.getCountry());
        customerInfo.setEmail(ordersLink.getEmail());
        customerInfo.setFirst_name(ordersLink.getFirstName());
        customerInfo.setId_zone(zoneId);
        customerInfo.setLast_name(ordersLink.getLastName());
        customerInfo.setProvince(ordersLink.getProvince());
        customerInfo.setTel(ordersLink.getTelphone());
        customerInfo.setZipcode(ordersLink.getZipcode());
        return customerInfo;
    }

    /**
     * 调用物流接口查询物流轨迹 接口规范待定 TODO
     */
    public RestResult findLogisticHistory(Map<String,String> param) {
        RestResult restResult = new RestResult();
        String key = param.get("key");
        String shipping_no = param.get("shipping_no");
        try {
            String historyLogistic = RestClient.getClient().getForObject(preLogisticsUrl + "?key="+key+"&shipping_no="+shipping_no, String.class);
            Map<String, Object> resultMap = new ObjectMapper().readValue(historyLogistic, Map.class);
            Assert.notNull(resultMap,"未知原因导致返回结果为空，请与wms系统相关人员确认");
            String reult = String.valueOf(resultMap.get("result"));
            if(reult.equals("success")){
                restResult.setCode(RestResult.OK);  
            }else{
                restResult.setCode(RestResult.FAIL);
            }
            restResult.setDesc(String.valueOf(resultMap.get("msg")));
            restResult.setItem(String.valueOf(resultMap.get("data")));
            return restResult;
        }
        catch (Exception e) {
            logger.error("查询物流轨迹失败 {}", e.getMessage());
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("查询物流轨迹失败，请重试");
        }
        return restResult;
    }

    /**
     * 修改订单的物流状态(通过爬虫抓取信息写入mq并更新状态)
     */
    public boolean updateOrderLogisticsStatus(MatchLogisticsStatusModel matchLogisticsStatusModel) {
        Assert.notNull(matchLogisticsStatusModel, "实体不能为空");
        Orders order = new Orders();
        try {
            Assert.notNull(matchLogisticsStatusModel.getOrderId(), "订单ID不能为空");
            order.setId(matchLogisticsStatusModel.getOrderId());
            order.setTrackingStatus(matchLogisticsStatusModel.getTrackingStatus());
            orderService.updateOrderLogisticsStatus(order);
            return true;
        }
        catch (Exception e) {
            logger.error("修改订单物流状态失败 LogisticsServiceImpl.updateOrderLogisticsStatus{}", e.getMessage());
            return false;
        }
    }

    /**
     * 修改订单状态 TODO(给转寄仓以及需要修改状态的接口调用)
     */
    @Override
    public boolean updateOrderStatus(Map param) {
        // TODO Auto-generated method stub
        return false;
    }

}
