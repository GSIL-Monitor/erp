package com.stosz.order.service;

import com.stosz.order.ext.model.OrdersWebInterfaceRecordDetail;
import com.stosz.order.mapper.order.OrdersWebInterfaceRecordDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @auther carter
 * create time    2018-01-24
 */
@Service
public class OrdersWebInterfaceRecordDetailService {


    @Autowired
    private OrdersWebInterfaceRecordDetailMapper ordersWebInterfaceRecordDetailMapper;

    public List<OrdersWebInterfaceRecordDetail> findDetailListById(Integer id) {
        Assert.isTrue(null!=id && id.intValue() > 0, "id必须为正整数");
        return ordersWebInterfaceRecordDetailMapper.findDetailListById(id);
    }
}
