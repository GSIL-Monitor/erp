package com.stosz.order.fsm;

import com.google.common.collect.Lists;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.dto.WmsResponse;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.service.OrderService;
import com.stosz.order.service.outsystem.wms.IWmsOrderService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.plat.wms.bean.OrderCancelRequest;
import com.stosz.purchase.ext.model.OrderNotifyRequired;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.OrderHandleEnum;
import com.stosz.store.ext.service.IStockService;
import com.stosz.tms.dto.OrderStateInfo;
import com.stosz.tms.service.ITransportFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @auther carter
 * create time    2017-11-15
 *
 * 这里处理注意 订单是否已经配货，如果配货，需要发wms，否则不做处理
 *
 */
@Component
public class CustomerCancelBefore extends IFsmHandler<Orders> {


    @Autowired
    private IStockService iStockService;

    @Autowired
    private IWmsOrderService wmsOrderService;

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    @Autowired
    private OrderService orderService;

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private ITransportFacadeService transportFacadeService;

    @Override
    public void execute(Orders orders, EventModel event) {

        String srcStateString = event.getSrcState();

        OrderStateEnum orderStateEnum = OrderStateEnum.fromName(srcStateString);


        //原状态不同，处理逻辑不同；
        switch (orderStateEnum)
        {
            case start:
                justUpdateState(orderStateEnum);
                break;
            case waitAudit:
                justUpdateState(orderStateEnum);
                break;
            case waitContact:
                justUpdateState(orderStateEnum);
                break;
            case invalidOrder:
                canNotHappen(orderStateEnum);
                break;
            case orderCancel:
                canNotHappen(orderStateEnum);
                break;
            case auditPass:
                canNotHappen(orderStateEnum);
                break;
            case waitSpecifyLogistic:
                cancelStoreOccupy(orders,orderStateEnum);
                break;
            case waitPurchase:
                notifyPurchase(orders,orderStateEnum);
                break;
            case waitDeliver:
                notifyWmsAndCancelStoreOccupy(orders,orderStateEnum);
                break;
            case deliver:
                notifyCancelSiezeTms(orders,orderStateEnum);
                break;
            case sign:
                canNotHappen(orderStateEnum);
                break;
            case rejection:
                canNotHappen(orderStateEnum);
                break;
            /*case returned:
                todoInRMA(orderStateEnum);
                break;
            case exchange:
                todoInRMA(orderStateEnum);
                break;*/
           default:
               break;

        }


    }

    private void notifyCancelSiezeTms(Orders orders, OrderStateEnum orderStateEnum) {
        //通知物流开始抓取轨迹
        OrderStateInfo orderStateInfo = new OrderStateInfo();
        orderStateInfo.setOrderId(orders.getId());
        orderStateInfo.setOrderStateEnum(com.stosz.tms.enums.OrderStateEnum.orderCancel);
        orderStateInfo.setShippingWayCode(orders.getLogisticsName());
        orderStateInfo.setShippingWayName(orders.getLogisticsName());
        orderStateInfo.setTrackNo(orders.getTrackingNo());
        orderStateInfo.setWeight(orders.getWeight());
        transportFacadeService.notifyLogisticsIsFetch(orderStateInfo);
    }

    @Deprecated
    private void todoInRMA(OrderStateEnum orderStateEnum) {
        logger.info("订单状态是{},取消订单,在售后中心再进行处理;",orderStateEnum.display());
    }

    private void notifyWmsAndCancelStoreOccupy(Orders orders, OrderStateEnum orderStateEnum) {
        //通知wms取消出库
        OrderCancelRequest orderCancelRequest = new OrderCancelRequest();

        /*** 单号*/
        orderCancelRequest.setOrderId(String.valueOf(orders.getMerchantOrderNo()));
        /** 货主*/
        orderCancelRequest.setGoodsOwner(MBox.BGN_COMPANY_NO);
        /*** 备注*/
        orderCancelRequest.setMemo("取消订单");
        /**
         * 取消订单类型
         * JYCK:销售订单
         * XTRK:销售退货
         * CGRK:采购订单
         * CGTH:采购退货
         * TRAN:调拨计划
         */
        orderCancelRequest.setOrderType("JYCK");
        WmsResponse restResult =  wmsOrderService.subCancelOrder(orderCancelRequest);
        if(restResult.getCode().equals("OK")){
            cancelStoreOccupy(orders,orderStateEnum);
            /**
             * 通知物流停止物流轨迹抓取
             */
           /* OrderStateInfo orderStateInfo = new OrderStateInfo();
            OrderStateEnum orderStatusEnum = orders.getOrderStatusEnum();
            orderStateInfo.setOrderStateEnum(com.stosz.tms.enums.OrderStateEnum.fromName(orders.getOrderStatusEnum().name()));
            orderStateInfo.setOrderId(orders.getId());
            transportFacadeService.notifyLogisticsIsFetch(orderStateInfo);*/
        }else {
            logger.error("wms取消失败"+restResult.getDesc());
            throw new RuntimeException("wms取消失败");
        }
    }

    private void notifyPurchase(Orders orders, OrderStateEnum orderStateEnum) {
        //通知采购系统，某个订单的sku取消了；


        List<OrdersItem> ordersItemByOrderId = orderService.findOrdersItemByOrderId(orders.getId());

        ordersItemByOrderId.stream().forEach(ordersItem -> {

            OrderNotifyRequired orderNotifyRequired = new OrderNotifyRequired();
            orderNotifyRequired.setDeptId(orders.getSeoDepartmentId());
            orderNotifyRequired.setSku(ordersItem.getSku());
            orderNotifyRequired.setSpu(ordersItem.getSpu());
            orderNotifyRequired.setType(1);

            RabbitMQMessage rabbitMQMessage = new RabbitMQMessage()
                    .setMessageType(OrderNotifyRequired.MESSAGE_TYPE)
                    .setMethod(RabbitMQConfig.method_insert)
                    .setData(orderNotifyRequired);

            rabbitMQPublisher.saveMessage(rabbitMQMessage);
        });





    }

    private void cancelStoreOccupy(Orders orders, OrderStateEnum orderStateEnum) {
        // 取消库存占用

        List<StockChangeReq> stockChangeReqList = Lists.newLinkedList();

        List<OrdersItem> ordersItemByOrderId = orderService.findOrdersItemByOrderId(orders.getId());

        Department department = Optional.ofNullable(departmentService.get(orders.getSeoDepartmentId())).orElse(new Department());
        ordersItemByOrderId.stream().forEach(ordersItem -> {
            StockChangeReq param = new StockChangeReq();
            /*** 仓库id**/
            param.setWmsId(orders.getWarehouseId());
            /*** 部门id**/
            param.setDeptId(orders.getSeoDepartmentId());
            /*** 部门编号**/
            param.setDeptNo(department.getDepartmentNo());
            /*** 部门名称 **/
            param.setDeptName(department.getDepartmentName());
            /*** 产品sku**/
            param.setSku(ordersItem.getSku());
            /*** 产品spu **/
            param.setSpu(ordersItem.getSpu());
            /*** 改变数量（统一记正数）**/
            param.setChangeQty(ordersItem.getQty());
            /**操作类型**/
            param.setType(OrderHandleEnum.order_cancel.name());
            /** 业务单号**/
            param.setVoucherNo(String.valueOf(ordersItem.getOrdersId()));
            /** 业务单号涉及金额**/
            param.setAmount(ordersItem.getTotalAmount());
            /*** 业务单号时间**/
            param.setChangeAt(LocalDateTime.now());

            stockChangeReqList.add(param);


        });


        iStockService.orderChangeStockQty(stockChangeReqList);

    }

    private void canNotHappen(OrderStateEnum orderStateEnum) {
        logger.info("订单状态是{},取消订单,不可能发送;",orderStateEnum.display());
    }


    /**
     * 审核阶段，只需要修改状态，记录状态机历史即可；
     */
    private void justUpdateState(OrderStateEnum orderStateEnum){

        logger.info("订单状态是{},取消订单,啥也不做,只修改状态;",orderStateEnum.display());

    }




}
