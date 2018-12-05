package com.stosz.order.web;

import com.stosz.order.ext.model.OrderItemsDO;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.service.OrderItemsService;
import com.stosz.order.service.OrderService;
import com.stosz.plat.common.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单补发
 *
 * @auther tangtao
 * create time  2017/12/15
 */
@RestController
@RequestMapping("orders/supplement")
public class OrdersSupplementController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemsService orderItemsService;

    /**
     * 获取订单商品
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "items", method = RequestMethod.GET)
    public RestResult items(Integer orderId) {
        RestResult restResult = new RestResult();

        Orders order = orderService.findOrderById(orderId);

        List<OrdersItem> ordersItemList = orderItemsService.getByOrderId(orderId);
        List<OrderItemsDO> itemList = new ArrayList<>(ordersItemList.size());

        ordersItemList.stream().forEach(item -> {
            OrderItemsDO orderItemsDO = new OrderItemsDO();

            orderItemsDO.setOrderId(item.getOrdersId());
            orderItemsDO.setProductId(item.getProductId());
            orderItemsDO.setProductTitle(item.getProductTitle());
            orderItemsDO.setAttrTitleArray(item.getAttrTitleArray());
            orderItemsDO.setSku(item.getSku());
            orderItemsDO.setQty(item.getQty());
            orderItemsDO.setSingleAmount(item.getSingleAmount());
            orderItemsDO.setTotalAmount(item.getTotalAmount());

            itemList.add(orderItemsDO);
        });

        Map<String, Object> dataItem = new HashMap<>();
        dataItem.put("itemList", itemList);
        dataItem.put("orderAmount", order.getConfirmAmount());

        restResult.setItem(dataItem);
        return restResult;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult add(String supplementReasonEnumName, String addAmount, String memo, @RequestParam(value = "itemIdArray[]") String[] itemIdArray) {
        RestResult restResult = new RestResult();
//        TODO
//        restResult.setItem(orderSupplementDTO);

        return restResult;
    }
}


