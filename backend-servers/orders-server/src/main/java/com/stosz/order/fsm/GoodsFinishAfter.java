package com.stosz.order.fsm;

import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.enums.*;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersRefundBill;
import com.stosz.order.ext.model.OrdersRmaBill;
import com.stosz.order.service.AfterSaleService;
import com.stosz.order.service.OrderService;
import com.stosz.tms.enums.ParcelOrderStateEnum;
import com.stosz.tms.service.ITransportFacadeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 
 * 退货/拒收通过 审核不通过的时候更新
 * @author liusl
 */
@Component
public  class GoodsFinishAfter extends IFsmHandler<OrdersRmaBill> {

    @Autowired
    private AfterSaleService afterSaleService;
    @Qualifier("orderFsmProxyService")
    @Autowired
    private FsmProxyService<Orders> ordersFsmProxyService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FsmProxyService<OrdersRefundBill> orderRefundFsmProxyService;
    @Autowired
    private ITransportFacadeService transportFacadeService;
    @Override
    public void execute(OrdersRmaBill ordersRmaBill, EventModel event) {

        String  changeTypeEnum = ordersRmaBill.getChangeTypeEnum().name();
        String  payMethodEnum = ordersRmaBill.getPayMethodEnum().name();

        if(changeTypeEnum.equals(ChangeTypeEnum.reject.name())){
            //1.如果是拒收,并且是在线支付的是需要生成退款单的拒收的货到付款不需要生成退款单
            if(payMethodEnum.equals(PayMethodEnum.cod.name())){
                //插入退款单申请记录数据
                OrdersRefundBill ordersRefundBill = afterSaleService.createRefundBillByRmaBill(ordersRmaBill);
                //更新转寄仓回写的数据(退款单号)
                ordersRmaBill.setRefundId(ordersRefundBill.getId().longValue());
                afterSaleService.updateSelective(ordersRmaBill);
                orderRefundFsmProxyService.processEvent(ordersRefundBill, OrderRefundEventEnum.createPre,OrderRefundEventEnum.createPre.display());
            }
            //拒收单 同步通知订单跟物流进行状态的更新
            Orders orders = orderService.findOrderById(ordersRmaBill.getOrdersId().intValue());
            ordersFsmProxyService.processEvent(orders, OrderEventEnum.matchNotSign,"拒收流程完成，订单状态更新为拒收");
            transportFacadeService.updateParcelStatusByOrderId(ordersRmaBill.getOrdersId().intValue(), ParcelOrderStateEnum.REJECT);
        }
        //2.如果是退货,并且退货原因不是丢件跟清关失败是需要生成退款单的
        String changeReasonEnum = ordersRmaBill.getChangeReasonEnum().name();
        if(changeTypeEnum.equals(ChangeTypeEnum.returned.name()) && StringUtils.isNotEmpty(changeReasonEnum) && (!changeReasonEnum.equals(ChangeReasonEnum.missing.name()) && !changeReasonEnum.equals(ChangeReasonEnum.unclearance.name()))){
            //插入退款单申请记录数据
            OrdersRefundBill ordersRefundBill = afterSaleService.createRefundBillByRmaBill(ordersRmaBill);
            //更新转寄仓回写的数据(退款单号)
            ordersRmaBill.setRefundId(ordersRefundBill.getId().longValue());
            afterSaleService.updateSelective(ordersRmaBill);
            //2.1 支付方式是货到付款
            if(payMethodEnum.equals(PayMethodEnum.cod)){
                orderRefundFsmProxyService.processEvent(ordersRefundBill, OrderRefundEventEnum.create,OrderRefundEventEnum.create.display());
            }else{
                //2.2 支付方式是预付款
                orderRefundFsmProxyService.processEvent(ordersRefundBill, OrderRefundEventEnum.createPre,OrderRefundEventEnum.createPre.display());
            }
        }
    }

}
