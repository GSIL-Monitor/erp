package com.stosz.order.sync.mq.consumer;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.enums.WarehouseTypeEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.ext.model.ZoneWarehousePriority;
import com.stosz.order.ext.mq.OccupyStockModel;
import com.stosz.order.service.OrderService;
import com.stosz.order.service.ZoneWarehousePriorityService;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.dto.response.MatchPackRes;
import com.stosz.store.ext.dto.response.StockChangeRes;
import com.stosz.store.ext.enums.OrderHandleEnum;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.store.ext.service.ITransitStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @auther carter
 * create time    2017-11-09
 * 库存占用算法
 *
 * 场景：
 * 1，订单审核通过，触发配货；
 * 2，仓库库存增加，触发配货；
 *
 */
@Component
public class OccupyStockHandler extends AbstractHandler<OccupyStockModel> {

    private static final Logger logger = LoggerFactory.getLogger(OccupyStockHandler.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private ZoneWarehousePriorityService zoneWarehousePriorityService;

    @Autowired
    private FsmProxyService<Orders> ordersFsmProxyService;

    @Autowired
    private IStorehouseService storehouseService;
    @Autowired
    private IStockService stockService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private ITransitStockService transitStockService;

    @Override
    public boolean handleMessage(String method, OccupyStockModel dataItem) {



        Integer orderId = dataItem.getOrderId();
        if(null!= orderId && orderId >0)
        {   //订单审核通过之后，进行配货
            Orders orderById = orderService.findOrderById(orderId);
            if(orderById.getOrderStatusEnum() != OrderStateEnum.auditPass && orderById.getOrderStatusEnum() != OrderStateEnum.waitPurchase){
                logger.info("订单[id={}]当前状态无法进行锁库存操作",orderId);
                return false;
            }
            assignSingleOrder(orderById,null);
            return  true;
        }

        //1,根据sku,dept查询所有的订单；
        final String sku = dataItem.getSku();
        Integer dept = dataItem.getDept();

        if(Strings.isNullOrEmpty(sku)) {
            logger.warn("sku为空,{}",sku);
            return true;
        }

        if(null == dept || dept<1)
        {
            logger.warn("dept不合法,{}",dept);
            return true;
        }

        Integer canAssignQty = dataItem.getCanAssignQty();
        if(null==canAssignQty || canAssignQty<1)
        {
            logger.warn("canAssignQty不合法,{}",canAssignQty);
            return true;
        }

        //2,按照sku , dept 搜索到所有的审核通过，待采购的订单,按照时间递增；
        List<Orders> ordersList = orderService.findOrderBySkuDept(sku,dept);

        if (CollectionUtils.isNullOrEmpty(ordersList))
        {
            logger.warn("查询不到满足条件的订单");
            return true;
        }

        /////////////////////////////////////////////////////按照可用库存数量，限制配货的订单范围
        List<Orders> ordersListInCanAssignQty = Lists.newLinkedList();
        if(canAssignQty>0)
        {
            AtomicInteger atomicInteger = new AtomicInteger(canAssignQty);
            for (Orders ord:ordersList)
            {
                Integer qty = orderService.findOrdersItemByOrderId(ord.getId()).stream().filter(item -> sku.equals(item.getSku())).findFirst().get().getQty();
                if (atomicInteger.addAndGet(-1* qty)>=0)
                {
                    ordersListInCanAssignQty.add(ord);
                }else
                {
                    break;
                }
            }
            ordersList = ordersListInCanAssignQty;
        }


        //3，循环，进行库存占用
        for (Orders order : ordersList)
        {
            assignSingleOrder(order,dataItem.getWarehouseId());
        }
        return true;
    }

    /**
     * 给单个订单占用库存,如果传了仓库id,对指定仓库进行占用，否则，区域对应的所有仓库进行配货
     * @param order  订单对象
     * @param mqWarehouseId  仓库id
     */
    private boolean assignSingleOrder(Orders order,Integer mqWarehouseId) {
        Integer deptId = order.getSeoDepartmentId();
        Integer zoneId = order.getZoneId();
        List<ZoneWarehousePriority> zoneWarehousePriorityList = zoneWarehousePriorityService.findByZoneId(zoneId);

        if(null != mqWarehouseId && zoneWarehousePriorityList.stream().map(ZoneWarehousePriority::getWarehouseId).collect(Collectors.toSet()).contains(mqWarehouseId))
        {//如果传了仓库id,直接针对该仓库进行配货；
            zoneWarehousePriorityList = zoneWarehousePriorityList.stream().filter(item->mqWarehouseId.intValue() == item.getWarehouseId().intValue()).collect(Collectors.toList());
        }

        List<Integer> occupyWarehouseResultList = Lists.newLinkedList();

        for (ZoneWarehousePriority zoneWarehousePriority : zoneWarehousePriorityList)
        {
            //如果该区域禁售该spu，直接跳过；
            String forbidSpu = zoneWarehousePriority.getForbidSpu();
            if(orderContainForbidSpu(forbidSpu,order)) continue;

            Integer warehouseId = zoneWarehousePriority.getWarehouseId();
            Integer occupyResult = occupySingleWarehouse(deptId, order, warehouseId);
            occupyWarehouseResultList.add(occupyResult);
            if(occupyResult> 0) break;
        }
        //如果所有的仓库都没有库存，设置订单为待采购状态
        Integer sum = occupyWarehouseResultList.stream().mapToInt(item -> item.intValue()).sum();

        if (sum <= 0)
        {
           String memo = "订单:"+order.getId()+"对应的区域:"+order.getZoneName()+"的所有仓库都没有库存.";
           if(occupyWarehouseResultList.isEmpty())  memo = "订单对应的区域["+order.getZoneName()+"]没有设置配货仓库.";
            ordersFsmProxyService.processEvent(order, OrderEventEnum.lockStockFail,memo);
            return false;
        }

        return true;
    }

    /**
     * 该订单是否含有该区域禁售的spu
     * @param forbidSpu 禁售的spu
     * @param order 订单
     * @return
     */
    private boolean orderContainForbidSpu(String forbidSpu, Orders order) {

        List<OrdersItem> ordersItems = orderService.findOrdersItemByOrderId(order.getId());
        if(CollectionUtils.isNullOrEmpty(ordersItems)) return true;
        if(Strings.isNullOrEmpty(forbidSpu)) return false;

        List<String> forbidSpuList = Arrays.asList(forbidSpu.split(","));

        return ordersItems.stream()
                .filter(ordersItem -> !Strings.isNullOrEmpty(ordersItem.getSpu()))
                .anyMatch(ordersItem -> forbidSpuList.contains(ordersItem.getSpu()));

    }


    /**
     * 占用单个仓库的库存
     * @param deptId  部门id
     * @param order  订单
     * @param warehouseId  仓库id
     * @return
     */
    private Integer occupySingleWarehouse( Integer deptId, Orders order, Integer warehouseId) {
        //3,按照仓库查询库存，进行库存占用

        Integer orderId = order.getId();
        List<OrdersItem> orderItemsList = orderService.findOrdersItemByOrderId(orderId);

        Wms wms = storehouseService.wmsTransferRequest(warehouseId);
        WarehouseTypeEnum warehouseTypeEnum = getWareHouseType(wms);

        if(null == warehouseTypeEnum) {
            logger.error("仓库不存在或者不可用,{}",warehouseId);
            return  0;
        }


        boolean occupyStockSuccess = false;
        switch (warehouseTypeEnum)
        {
            case normal: occupyStockSuccess = selfBuildWarehouseOccupyStock(wms,deptId,orderItemsList,orderId); break;
            case forward:occupyStockSuccess = forwardWarehouseOccupyStock(wms,  deptId,orderItemsList,orderId,order.getCreateAt()); break;
            case third:logger.error("第三方仓库暂时无法库存占用",new IllegalArgumentException("第三方仓库暂时无法库存占用"));break;
        }

        if(occupyStockSuccess)
        {//通知状态机器修改状态
            ordersFsmProxyService.processEvent(order,OrderEventEnum.lockStockSuccess,warehouseTypeEnum.display()+"存占用成功");
        }

        return occupyStockSuccess?1:0;
    }



    /**
     * 占用转寄仓库库存
     * @param wms
     * @param deptId
     * @param orderItemsList
     * @return
     */
    private boolean forwardWarehouseOccupyStock(Wms wms, Integer deptId, List<OrdersItem> orderItemsList, Integer orderId, LocalDateTime localDateTime) {


        Department department = Optional.ofNullable(departmentService.get(deptId)).orElse(new Department());

        MatchPackRes matchPackRes = transitStockService.occupyStockQty(wms.getId(), deptId,department.getDepartmentName(), orderItemsList,orderId,localDateTime);
        //转寄仓匹配全单之后，返回 原来的订单号
        if(Optional.ofNullable(matchPackRes).isPresent())
        {
            logger.info("转寄仓库存占用成功,转寄仓id:{},转寄仓名称：{},原来的订单id：{},原来的运单号{},库位：{}，当前包裹信息：{}",matchPackRes.getId(),matchPackRes.getStockName()
                    ,matchPackRes.getOrderIdOld(),matchPackRes.getTrackingNoOld(),matchPackRes.getStorageLocal(),orderItemsList);

            orderService.updateOrderWarehouseInfo(wms.getId(),wms.getName(),WarehouseTypeEnum.fromId(wms.getType())
                    ,"转寄仓库存占用成功,转寄仓id:"+matchPackRes.getId()+",转寄仓名称："+matchPackRes.getStockName()+",原来的订单id："+matchPackRes.getOrderIdOld()+",原来的运单号"+matchPackRes.getTrackingNoOld()+",库位："+matchPackRes.getStorageLocal()
                    ,wms.getWmsSysCode(), orderId);

            return true;
        }
        return false;
    }


    /**
     * 自建仓库的库存占用逻辑
     * @return
     */
    private boolean selfBuildWarehouseOccupyStock(Wms wms, Integer deptId, List<OrdersItem> ordersItemList, Integer orderId) {

        Department department = Optional.ofNullable(departmentService.get(deptId)).orElse(new Department());

        List<StockChangeReq> stockChangeReqList = Lists.newLinkedList();
        for (OrdersItem ordersItem: ordersItemList)
        {
            StockChangeReq param = new StockChangeReq();
            /*** 仓库id**/
            param.setWmsId(wms.getId());
            /*** 部门id**/
            param.setDeptId(deptId);
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
            param.setType(OrderHandleEnum.order_undelivered.name());
            /** 业务单号**/
            param.setVoucherNo(String.valueOf(ordersItem.getOrdersId()));
            /** 业务单号涉及金额**/
            param.setAmount(ordersItem.getTotalAmount());
            /*** 业务单号时间**/
            param.setChangeAt(LocalDateTime.now());

            stockChangeReqList.add(param);
        }

        //单品站单个订单目前只有一个订单项；
        List<StockChangeRes> stockChangeResList = stockService.orderChangeStockQty(stockChangeReqList);
        logger.info("自建仓库库存占用结果:"+ stockChangeReqList);
        Boolean orderChangeStockQtyResult = Optional.ofNullable(stockChangeResList).orElse(Lists.newLinkedList()).stream().allMatch(item->true == item.getCode());

        if(orderChangeStockQtyResult)
        {
            logger.info("自建仓库存占用成功，包裹信息：{}",ordersItemList);
            //修改订单的仓库信息
            orderService.updateOrderWarehouseInfo(wms.getId(),wms.getName(),WarehouseTypeEnum.fromId(wms.getType()),"自建仓["+wms.getName()+"]库存占用成功",wms.getWmsSysCode(),orderId);
        }

        return orderChangeStockQtyResult;
    }


    /**
     * 得到仓库的类型
     * @param wms 仓库
     * @return
     */
    private WarehouseTypeEnum getWareHouseType(Wms wms) {
        final WarehouseTypeEnum[] warehouseTypeEnum = {null};
        Optional.ofNullable(wms)
                .ifPresent(item -> {
                    if(1==item.getState())
                    {
                        warehouseTypeEnum[0] = WarehouseTypeEnum.fromId(item.getType());
                    }
                });
        return warehouseTypeEnum[0];
    }


    @Override
    public Class getTClass() {
        return OccupyStockModel.class;
    }
}
