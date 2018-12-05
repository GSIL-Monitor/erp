package com.stosz.order.service;


import com.stosz.order.ext.model.OrdersRmaBillItem;
import com.stosz.order.mapper.order.OrdersRmaBillItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 退换货订单项业务类
 * @author liusl
 */
@Service
public class OrdersRmaBillItemsService {
    
    @Autowired
    private OrdersRmaBillItemMapper ordersRmaBillItemMapper;
    
    
    void batchInsert(List<OrdersRmaBillItem> items){
        ordersRmaBillItemMapper.batchInsert(items);
    }


    public List<OrdersRmaBillItem> getOrdersRmaItemByRmaId(Integer rmaId) {
        List<OrdersRmaBillItem> items = ordersRmaBillItemMapper.getOrdersRmaItemByRmaId(rmaId);
        return items;
    }

    public void updateRmaBillItemByRmaId(Integer rmaId,String storageLocation) {
        ordersRmaBillItemMapper.updateRmaBillItemByRmaId(rmaId,storageLocation);
    }
}
