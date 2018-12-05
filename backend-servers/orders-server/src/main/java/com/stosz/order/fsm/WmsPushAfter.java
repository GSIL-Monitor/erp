package com.stosz.order.fsm;

import com.google.common.collect.Lists;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.enums.OrderHandleEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.service.OrderService;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.dto.response.StockChangeRes;
import com.stosz.store.ext.service.IStockService;
import com.stosz.tms.dto.OrderStateInfo;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.OrderStateEnum;
import com.stosz.tms.service.ITransportFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @auther carter
 * create time    2017-11-15
 *
 * wms回送出库通知
 *
 */
@Component
public class WmsPushAfter extends IFsmHandler<Orders> {

    @Resource
    private ITransportFacadeService transportFacadeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IStockService iStockService;


    @Override
    public void execute(Orders orders, EventModel event) {
        OrderStateInfo orderStateInfo = new OrderStateInfo();
        orderStateInfo.setOrderId(orders.getId());
        orderStateInfo.setOrderStateEnum(OrderStateEnum.deliver);
        orderStateInfo.setShippingWayCode(orders.getLogisticsName());
        orderStateInfo.setShippingWayName(orders.getLogisticsName());
        orderStateInfo.setTrackNo(orders.getTrackingNo());
        orderStateInfo.setWeight(orders.getWeight());
        //更新订单的发货时间与运单号
        orderService.updateOrder(orders);
        //通知物流开始抓取轨迹 begin
        try {
            TransportTrackResponse  response = transportFacadeService.notifyLogisticsIsFetch(orderStateInfo);
            logger.info("指派物流结束,返回参数："+JsonUtil.toJson(response));
        }catch (Exception e){
            //这里如果通知物流轨迹抓取失败，这里事务是不需要回滚的 后面可以做重新通知的功能。
            logger.error("通知物流进行物流轨迹抓取失败，失败原因:"+e.getMessage());
        }
        //通知物流开始抓取轨迹 end
        //通知仓库那边进行库存的扣减  begin
        List<StockChangeReq> stockChangeReqList =  getStockChangeReqList(orders);
        try{
            List<StockChangeRes> stockChangeRes= iStockService.orderChangeStockQty(stockChangeReqList);
            logger.info("仓库扣减成功,返回参数："+ JsonUtil.toJson(stockChangeRes));
        }catch (Exception e){
            //通知仓库出库失败，这里事务是不需要回滚的  后面可以做重新通知的功能。
            logger.error("通知仓库出库失败，失败原因:"+e.getMessage());
        }
        //通知仓库那边进行库存的扣减  end
        //发送状态通知到建站系统
        orderService.publishOrderStateMessage(orders, com.stosz.order.ext.enums.OrderStateEnum.fromName(event.getDstState()));
    }

    /**
     * 封装请求仓库的实体数据
     * @param orders
     * @return
     */
    public List<StockChangeReq> getStockChangeReqList(Orders orders ) {
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
            param.setType(OrderHandleEnum.order_delivered.name());
            /** 业务单号**/
            param.setVoucherNo(String.valueOf(ordersItem.getOrdersId()));
            /** 业务单号涉及金额**/
            param.setAmount(ordersItem.getTotalAmount());
            /*** 业务单号时间**/
            param.setChangeAt(LocalDateTime.now());
            stockChangeReqList.add(param);
        });
        return stockChangeReqList;
    }
}
