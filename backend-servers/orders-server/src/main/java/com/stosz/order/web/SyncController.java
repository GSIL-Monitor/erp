package com.stosz.order.web;

import com.stosz.order.sync.olderp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther carter
 * create time    2017-10-31
 */
@RestController
@RequestMapping("/test/sync")
public class SyncController {


    @Autowired
    private SycnBlackList sycnBlackList;

    @Autowired
    private SyncZoneWarehouseService syncZoneWarehouseService;

    @Autowired
    private SyncOrderService syncOrderService;

    @Autowired
    private SyncOrderOther syncOrderOther;

    @RequestMapping(value = "/blackList", method = RequestMethod.GET)
    public void sycnBlackList() {
//        sycnBlackList.pull();
    }

    @GetMapping("zone_warehouse")
    public String syncZoneWarehouseService() {
        syncZoneWarehouseService.sync();
        return "区域仓库同步成功";
    }

    @GetMapping("orders/start")
    public String startSyncOrders() {
        syncOrderService.syn();
        return "订单同步启动";
    }

    @GetMapping("orders/stop")
    public String stopSyncOrders() {
        syncOrderService.getIsRunning().set(false);
        return "订单同步关闭";
    }



    @GetMapping("other/start")
    public String startSyncOrdersOther() {
        syncOrderOther.start();
        return "订单杂项启动";
    }

    @GetMapping("other/stop")
    public String stopSyncOrdersOther() {
        syncOrderOther.getIsRunning().set(false);
        return "订单杂项关闭";
    }


    @GetMapping("link/stop")
    public String stopSyncLink() {
        syncOrderOther.getIsRunning().set(false);
        return "订单杂项关闭";
    }


}
