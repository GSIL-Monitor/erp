package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.store.ext.dto.response.MatchPackRes;
import com.stosz.store.ext.enums.TransitStateEnum;
import com.stosz.store.ext.model.TransitStock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄仓库
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class TransitStockServiceImplTest extends BaseTest {


    @Autowired
    private TransitStockService service;

    @Autowired
    private TransitStockServiceImpl serviceImpl;


    static void test() {

    }

    @Test
    public void update() throws Exception {
        TransitStock transitStock = new TransitStock();
        transitStock.setId(1);
        transitStock.setSku("sku11122333333");
        transitStock.setDeptId(1);
        transitStock.setCreateAt(LocalDateTime.now());

        transitStock.setOrderIdOld(12L);
        transitStock.setOrderIdNew(34l);
        transitStock.setState(TransitStateEnum.transferring.name());
        transitStock.setUpdateAt(LocalDateTime.now());

/*
        int result = service.updateTransitStateByOrder(transitStock);
        //失败-0
        logger.info("result:" + result);*/
    }

    @Test
    public void match() throws Exception {
        List<OrdersItem> list = new ArrayList<>();
        OrdersItem item1 = new OrdersItem();
        item1.setSku("111");
        item1.setQty(1);
        list.add(item1);
        OrdersItem item2 = new OrdersItem();
        item2.setSku("222");
        item2.setQty(4);
        list.add(item2);
        Long begin=System.currentTimeMillis();
        logger.info("befor:"+begin);
        MatchPackRes packRes = serviceImpl.occupyStockQty(3, 3, "",list,1,LocalDateTime.now());
        Long end=System.currentTimeMillis();
        logger.info("end:"+end+" used Time:"+(end-begin));
        logger.info("result:" + JsonUtils.toJson(packRes));
    }

    @Test
    public void match2() throws Exception {
        List<OrdersItem> list = new ArrayList<>();
        OrdersItem item1 = new OrdersItem();
        item1.setSku("111");
        item1.setQty(1);
        list.add(item1);
        OrdersItem item2 = new OrdersItem();
        item2.setSku("222");
        item2.setQty(4);
        list.add(item2);
        Long begin=System.currentTimeMillis();
        logger.info("befor:"+begin);
        MatchPackRes packRes = serviceImpl.occupyStockQty(3, 3,"", list,1,LocalDateTime.now());
        Long end=System.currentTimeMillis();
        logger.info("end:"+end+" used Time:"+(end-begin));
        logger.info("优化减少查询 result:" + JsonUtils.toJson(packRes));
    }

}
