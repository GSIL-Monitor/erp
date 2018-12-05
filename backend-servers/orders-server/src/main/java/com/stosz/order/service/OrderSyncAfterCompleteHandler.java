package com.stosz.order.service;

import com.stosz.order.ext.model.BlackList;
import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.mapper.order.BlackListMapper;
import com.stosz.order.mapper.order.OrdersLinkMapper;
import com.stosz.order.mapper.order.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 同步老ERP订单数据完成之后 **手动** 调用该方法计算 黑名单，IP重复购买数，及重复信息
 * 因为订单和订单项不是一个线程同步 ，而计算时会同时用到订单和订单项两个表
 *
 * @author wangqian
 * @date 2018-1-3
 */
@Service
public class OrderSyncAfterCompleteHandler {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersLinkMapper ordersLinkMapper;

    @Autowired
    private BlackListMapper blackListMapper;

    //更新前两个月内容IP的购买量
    private void handlerIpCount(String ip, LocalDateTime beginAt, LocalDateTime endAt){
        List<Orders> ordersList = ordersMapper.findByIpAfterBetweenCreateAt(ip, beginAt, endAt);
        if(ordersList == null || ordersList.size() == 0){
            return ;
        }
        List<Integer> orderIds = ordersList.stream().map(e -> e.getId()).collect(Collectors.toList());
        ordersMapper.updateIpIOrderQtyByOrderId(orderIds, orderIds.size());
    }


    //处理重复信息
    private void handleRepeatInfo(String ip, String domain,String tel, Orders orders ,List<OrdersItem> items,LocalDateTime beginAt, LocalDateTime endAt){
        List<Orders> repeatOrder = ordersMapper.findByIpAndDomainBetweenCreateAt(ip,domain,LocalDateTime.now().minusMonths(2),LocalDateTime.now());
        String repeatInfo = "";
        if(repeatOrder != null && repeatOrder.size() > 1){//不包括刚插入的
            repeatInfo = "域名+IP";
        }else{
            List<String> skuList = items.stream().map(i -> i.getSku()).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
//           @Param("tel") String tel, @Param("skuList") List<String> skuList, @Param("beginAt") LocalDateTime beginAt, @Param("endAt") LocalDateTime endAt
            map.put("tel",tel);
            map.put("skuList",skuList);
            map.put("beginAt", beginAt);
            map.put("endAt",endAt);
            List<OrdersLink> ordersLinks = ordersLinkMapper.findByTelAndSkuListBetweenCreateAt(map);
            if(ordersLinks != null && ordersLinks.size() != 0){
                repeatInfo = "手机号+SKU";
            }
        }
        //更新当前订单的重复信息
        ordersMapper.updateRepeatInfoByOrderId(orders.getId(),repeatInfo);
    }

    private BlackList handleBlackFiled(String ip, String tel, String email, String address,String name) {
        return blackListMapper.findByIpOrTelOrEmailOrAddrOrName(ip,tel,email,address,name);
    }


}
