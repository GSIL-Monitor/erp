package com.stosz.order.service;


import com.github.pagehelper.PageHelper;
import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.mapper.order.OrdersLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * 联系人业务层
 * @author liusl
 */
@Service
public class OrdersLinkService {
    
    @Autowired
    private OrdersLinkMapper ordersLinkMapper;

    /**
     * 订单摘要
     * @param orderId
     * @return
     */
    public OrdersLink getByOrderId(Integer orderId){
        OrdersLink items = ordersLinkMapper.getByOrderId(orderId);
        return items;
    }


    /**
     * 通过手机集合来查找订单
     * @date 2017-11-27
     * @author wangqian
     * @param tels
     * @return
     */
    public List<OrdersLink> findOrderLinkByTels(List<String> tels){
        return ordersLinkMapper.findOrderLinkByTels(tels);
    }



    /**
     * 通过手机号查询订单联系人
     * @param tel
     * @return
     */
    public List<OrdersLink> findByTel(String tel){
        return ordersLinkMapper.findByTel(tel);
    }


}
