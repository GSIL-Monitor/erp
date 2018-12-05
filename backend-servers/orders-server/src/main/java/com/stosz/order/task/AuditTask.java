package com.stosz.order.task;

import com.stosz.crm.ext.model.Customers;
import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.service.OrderAuditService;
import org.springframework.beans.factory.annotation.Autowired;

public class AuditTask implements Runnable {

    private OrderAuditService orderAuditService;

    private Orders orders;
    private Customers customers;



    public AuditTask(OrderAuditService orderAuditService,Orders orders, Customers customers) {
        this.orders = orders;
        this.customers = customers;
        this.orderAuditService = orderAuditService;
    }

    @Override
    public void run() {
        orderAuditService.autoInvalidOrder(orders.getId(),"系统自动审为无效单");
    }
}
