package com.stosz.order.service;


import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.mapper.order.OrdersItemsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单项业务类
 * 一句话功能简述
 * @author liusl
 */
@Service
public class OrderItemsService {
    
    @Autowired
    private OrdersItemsMapper ordersItemsMapper;

    /**
     * 订单摘要
     * @param orderId
     * @return
     */
    public List<OrdersItem> getByOrderId(Integer orderId){
        List<OrdersItem> items = ordersItemsMapper.getByOrderId(orderId);
        return items;
    }

    /**
     * 订单条目
     * @param orderIds
     * @return
     */
    public List<OrdersItem> getByOrderIds(List<Integer> orderIds){
        List<OrdersItem> items = ordersItemsMapper.getByOrderIds(orderIds);
        return items;
    }

}
