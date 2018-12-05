package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.mq.OccupyStockModel;
import com.stosz.order.service.OrderService;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther wangqian
 * @date 2017-11-30
 * 订单审核通过
 */
@Component
public class CheckValidAfter extends IFsmHandler<Orders> {

    @Autowired
    private OrderService orderService;
//
//    @Autowired
//    private OrderItemsService orderItemsService;

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    @Override
    public void execute(Orders orders, EventModel event) {

        //1,拿到订单的订单项信息；todo 配货算法待调整，先按照订单进行配货；
//        Optional.ofNullable(orderItemsService.getByOrderId(orders.getId()))
//                .ifPresent(ordersItems -> {
//                    if (StringUtils.hasLength(orders.getMemo())) {
//                        orderService.updateOrderMemo(orders.getId(), orders.getMemo());
//                    }
//                    logger.info("订单[id = {}]审核【通过】", orders.getId());
//
//                    ordersItems.forEach(ordersItem ->
//                    {
//                        OccupyStockModel occupyStockModel = new OccupyStockModel();
//                        occupyStockModel.setDept(orders.getBuDepartmentId());
//                        occupyStockModel.setSku(ordersItem.getSku());
//                        //2,产生一个配货消息到MQ
//                        rabbitMQPublisher.saveMessage(OccupyStockModel.MESSAGE_TYPE, RabbitMQConfig.method_insert, occupyStockModel);
//                    });
//                });




        //1,发起订单的配货
        //状态机:Orders instanceId:1 state:waitPurchase event:checkValid - processEvent error, 在EventHandler[Before, After]中不能包含修改实体状态的逻辑, 请检查配置 当前状态:waitPurchase 应该是:auditPass
        // occupyStockHandler.occupyStockByOrder(orders);

        OccupyStockModel occupyStockModel = new OccupyStockModel();
        occupyStockModel.setOrderId(orders.getId());

        rabbitMQPublisher.saveMessage(OccupyStockModel.MESSAGE_TYPE,occupyStockModel);

        //2,发送一个订单状态改变消息到建站系统；
        orderService.publishOrderStateMessage(orders, OrderStateEnum.fromName(event.getDstState()));
//        OrderStateModel orderStatusChangeMessage = new OrderStateModel();
//        orderStatusChangeMessage.setOrder_no(orders.getMerchantOrderNo());
//        /**订单状态 0 待审*******************/
//        orderStatusChangeMessage.setStatus(orders.getOrderStatusEnum().ordinal());
//        /**运单号*******************/
//        orderStatusChangeMessage.setTrack_number(orders.getTrackingNo());
//        /**物流公司名称*******************/
//        orderStatusChangeMessage.setLogistics_name(orders.getLogisticsName());
//        /**运单产生时间*******************/
//        orderStatusChangeMessage.setTrack_time(orders.getStateTime().toEpochSecond(ZoneOffset.UTC));
//        /**备注*******************/
//        orderStatusChangeMessage.setRemark(orders.getMemo());
//
//        rabbitMQPublisher.saveMessage(OrderStateModel.messageType, RabbitMQConfig.method_insert, orderStatusChangeMessage);

    }
}
