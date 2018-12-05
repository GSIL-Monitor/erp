package com.stosz.order.service;

import com.stosz.order.ext.model.OrderWarehouse;
import com.stosz.order.mapper.order.OrderWarehouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @auther carter
 * create time    2017-11-22
 */
@Deprecated
@Service
public class OrderWarehouseService {

    @Autowired
    private OrderWarehouseMapper orderWarehouseMapper;

    public OrderWarehouse findByOrderId(Integer orderId) {
        Assert.isTrue(null!=orderId && orderId.intValue()> 0,"请输入正确的orderId");
        return orderWarehouseMapper.findById(orderId);
    }
}
