package com.stosz.order.service;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.dto.*;
import com.stosz.order.ext.enums.OrderCancelReasonEnum;
import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.order.ext.enums.OrderRmaBillEventEnum;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.model.CombOrderItem;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.ext.model.OrdersRmaBill;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.order.mapper.order.OrdersItemsMapper;
import com.stosz.order.mapper.order.OrdersMapper;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.product.ext.service.IProductSkuService;
import com.stosz.tms.service.ITransportFacadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @auther carter
 * create time    2017-11-09
 */
@Service
public class OrderServiceImpl implements IOrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService orderService;
    @Autowired
    private ZoneWarehousePriorityService zoneWarehousePriorityService;

    @Resource
    private IProductSkuService productSkuService;
    @Qualifier("orderFsmProxyService")
    @Autowired
    private FsmProxyService<Orders> ordersFsmProxyService;
    @Resource
    private ITransportFacadeService transportFacadeService;

    @Qualifier("orderChangeFsmProxyService")
    @Autowired
    private FsmProxyService<OrdersRmaBill> orderChangeFsmProxyService;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrdersItemsMapper ordersItemsMapper;
    @Autowired
    private AfterSaleService afterSaleService;
    @Autowired
    private OrdersRmaBillItemsService ordersRmaBillItemsService;


    /**
     * SKU 查询到订单需求数，3日平均交易量，待审单数，输入参数:SKU 部门，返回实体OrderRequired
     *
     * @param deptId
     * @param sku
     * @return
     */
    public OrderRequiredResponse pullOrderReuired(Integer deptId, String sku) {
        OrderRequiredResponse orderRequired = new OrderRequiredResponse();


        Map<String, Object> param = new HashMap<>();
        param.put("deptId", deptId);
        param.put("sku", sku);

        // 待审数量
        param.put("states", OrderStateEnum.waitAudit.ordinal());

        Integer pendingSUM = ordersMapper.querySumByParam(param);
        orderRequired.setPendingOrderQty(Objects.isNull(pendingSUM) ? 0 : pendingSUM);

        // 待采购数量
        param.put("states", OrderStateEnum.waitPurchase.ordinal());

        Integer requiredSUM = ordersMapper.querySumByParam(param);
        orderRequired.setOrderRequiredQty(Objects.isNull(requiredSUM) ? 0 : requiredSUM);

        // 3日平均交易量
        // TODO 未做spu上线时间未达到3天的到处理
        /*  auditPass("审核通过"),waitSpecifyLogistic("待指定物流"), waitPurchase("待采购"),waitDeliver("待发货"),
        deliver("已发货"), sign("已签收"),  rejection("拒收"),returned("退货"),exchange("换货"),*/
        List<Integer> stateList = Arrays.asList(OrderStateEnum.auditPass.ordinal(), OrderStateEnum.waitSpecifyLogistic.ordinal(),
                OrderStateEnum.waitPurchase.ordinal(), OrderStateEnum.waitDeliver.ordinal(), OrderStateEnum.deliver.ordinal(),
                OrderStateEnum.sign.ordinal(), OrderStateEnum.rejection.ordinal()/*, OrderStateEnum.returned.ordinal(), OrderStateEnum.exchange.ordinal() TODO 待确认*/);

        String states = Joiner.on(",").join(stateList);

        String startTime = LocalDateTime.now().minusDays(3).toLocalDate().toString() + " 00:00:00";
        String endtTime = LocalDateTime.now().minusDays(1).toLocalDate().toString() + " 23:59:59";

        param.put("states", states);
        param.put("startTime", startTime);
        param.put("endtTime", endtTime);

        Integer daySUM = ordersMapper.querySumByParam(param);
        orderRequired.setAvgSaleQty(Objects.isNull(daySUM) ? 0 : (daySUM / 3));


        return orderRequired;
    }

    /**
     * 根据定单号查询订单信息  将数据封装给转寄仓用
     *
     * @param orderIds 定单号
     * @return
     */
    @Override
    public List<TransitOrderDTO> queryOrdersByOrderIds(List<String> orderIds) {
        List<TransitOrderDTO> transitStocks = orderService.queryOrdersByOrderIds(orderIds);
        return transitStocks;
    }

    /**
     * 根据物流给过来的订单号查询订单数据
     *
     * @param orderIds 订单号
     * @return
     */
    @Override
    public Map<Integer, List<CombOrderItem>> queryOrderByOrderIds(List<String> orderIds) {
        List<CombOrderItem> list = ordersMapper.queryOrderByOrderIds(orderIds);
        //获取订单行信息
        List<Integer> ids = list.stream().map(o -> o.getId()).collect(Collectors.toList());
        List<OrdersItem> ois = ordersItemsMapper.findByOrderIds(ids);
        Map<Long, List<OrdersItem>> itemMap = ois.stream().collect(Collectors.groupingBy(OrdersItem::getOrdersId));
        return getTransportRequest(list, itemMap);
    }

    private Map<Integer, List<CombOrderItem>> getTransportRequest(List<CombOrderItem> list, Map<Long, List<OrdersItem>> itemMap) {
        List<CombOrderItem> combOrderItems = new ArrayList<CombOrderItem>();
        list.stream().forEach(e -> {
            if (itemMap.containsKey(e.getId().longValue())) {
                CombOrderItem combOrderItem = new CombOrderItem();
                combOrderItem.setOrdersItems(itemMap.get(e.getId().longValue()));
                combOrderItems.add(combOrderItem);
            }
        });
        return combOrderItems.stream().collect(groupingBy(CombOrderItem::getId));
    }

    /**
     * 根据运单号查询订单信息
     *
     * @param trackingNos 运单号
     * @return
     */
    @Deprecated
    public List<TransitStockDTO> queryOrdersByTrackingNos(List<String> trackingNos) {
        return orderService.queryOrdersByTrackingNos(trackingNos);
    }

    /**
     * 运单号获取skuList
     *
     * @param trackingNos
     * @return
     */
    @Deprecated
    public List<TransitItemDTO> queryOrdersItems(List<String> trackingNos) {
        List<TransitItemDTO> transitItems = new ArrayList<TransitItemDTO>();
        List<OrdersItem> ordersItems = orderService.queryOrdersItems(trackingNos);
        ordersItems.forEach(e -> {
            TransitItemDTO transitItemDTO = new TransitItemDTO();
            transitItemDTO.setAttrTitleArray(e.getAttrTitleArray());
            transitItemDTO.setForeignTitle(e.getForeignTitle());
            transitItemDTO.setForeignTotalAmount(e.getForeignTotalAmount());
            transitItemDTO.setQty(e.getQty());
            transitItemDTO.setSingleAmount(e.getSingleAmount());
            transitItemDTO.setOrderIdOld(e.getOrdersId());
            transitItemDTO.setProductTitle(e.getProductTitle());
            transitItemDTO.setSku(e.getSku());
            transitItemDTO.setSpu(e.getSpu());
            transitItems.add(transitItemDTO);
        });
        return transitItems;
    }

    /**
     * 业务说明： 修改订单状态
     *
     * @param orderId 订单号id
     * @param status  转寄拣货盘亏
     * @return 按数据库成功返回1，失败0
     */
    @Override
    public WmsResponse transferCancel(Long orderId, OrderEventEnum status) {
        WmsResponse restResult = new WmsResponse();
        try {
            orderService.transferCancel(orderId, status);
        } catch (Exception e) {
            logger.error("转寄盘亏失败，失败原因{},参数{},{}",e.getMessage(),orderId,status);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc(e.getMessage());
        }
        return restResult;
    }

    /**
     * 转寄仓物流信息会写到订单表中
     *
     * @param orderId
     * @param trackingNo    运单号
     * @param logisticsId   物流id
     * @param logisticsName
     * @return
     */
    @Override
    public WmsResponse updateOrderStatusByTransit(String orderId, String trackingNo, String logisticsId, String logisticsName) {
        WmsResponse restResult = new WmsResponse();
        Orders orders = orderService.findOrderById(Integer.parseInt(orderId));
        try {
            ordersFsmProxyService.processEvent(orders, OrderEventEnum.forwardImportOk,OrderEventEnum.forwardImportOk.display());
        }catch (Exception e){
            logger.error("转寄仓物流信息会写到订单表，失败原因{}",e.getMessage());
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc(e.getMessage());
        }
        return  restResult;
    }

    /**
     * 业务说明： 修改订单状态 物流 （签收 拒收）
     *
     * @param transport 订单号状态
     * @return 按数据库成功返回1，失败0
     */
    @Override
    public TransportResponse updateOrderStatus(TransportDTO transport) {
        Orders orders = orderService.findOrderById(transport.getOrderId());
        TransportResponse transportResponse = new TransportResponse();
        try {
            if (transport.getOrderEventEnum() != null) {
                ordersFsmProxyService.processEvent(orders, transport.getOrderEventEnum(), transport.getOrderEventEnum().display());
                String orderStatus = orders.getState();
                if(orderStatus.equals(OrderStateEnum.rejection.name()) && OrderEventEnum.matchSign.name().equals(transport.getOrderEventEnum())){
                    //如果是先拒收 再签收 需要取消RMA申请单  如果申请单走到比较靠后的流程了 需要取消RMA流程
                    OrdersRmaBill ordersRmaBill = afterSaleService.findOrdersRmaBillByOrderId(orders.getId());
                    orderChangeFsmProxyService.processEvent(ordersRmaBill,OrderRmaBillEventEnum.changeCancel, OrderRmaBillEventEnum.changeCancel.display());
                }
                //如果抓取到拒收  如果没有生成拒收单则需要生成拒收单
                if(orderStatus.equals(OrderStateEnum.rejection.name())) {
                    Integer rejects = afterSaleService.countOrdersRejectByOrderId(orders.getId());
                    if(rejects==0){
                        afterSaleService.createRmaBillByOrderId(transport.getOrderId());
                    }
                }
            }
            //TODO
//            orders.setTrackingStatusShow(transport.getTrackingStatusShow() == null ? "" : transport.getTrackingStatusShow());
            orderService.updateOrderLogisticsStatus(orders);
            transportResponse.setCode(TransportResponse.SUCCESS);
            // TODO: 2018/1/19 wangqian 添加crm逻辑 
        } catch (Exception e) {
            logger.error("修改订单状态失败，失败原因{},参数{}",e.getMessage(),transport);
            transportResponse.setCode(TransportResponse.FAIL);
            transportResponse.setErrorMsg("修改订单状态失败，错误原因:" + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return transportResponse;
    }

    /** author liushilei
     * 转寄仓入库 更新申请单为已收货
     * @param rmdId  RMA申请单号
     * @param storageLocation  库位
     * @param inStorageNo  转寄仓ID
     * @return
     */
    @Override
    public WmsResponse updateOrderRmaStatus(Integer rmdId,String  storageLocation,Integer inStorageNo) {
        WmsResponse  restResult = new WmsResponse();
        OrdersRmaBill ordersRmaBill = afterSaleService.getById(rmdId);
        if(ordersRmaBill==null){
            restResult.setCode(WmsResponse.FAIL);
            restResult.setDesc("申请单"+rmdId+"不存在，请确认");
            return  restResult;
        }
        if(storageLocation==null){
            storageLocation="";
        }
        /**
         * 更新申请单中的明细信息（目前转寄仓就只有库位需要更新 后面扩展的话会加入入库数量）
         */
        ordersRmaBillItemsService.updateRmaBillItemByRmaId(ordersRmaBill.getId(),storageLocation);
        orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.warehousing,OrderRmaBillEventEnum.warehousing.display());
        return restResult;
    }

    @Override
    public Integer orderCancel(Long orderId) {
        try {
            orderService.orderCancel(orderId.intValue(), OrderCancelReasonEnum.other.display(),"RPC取消");
            return 1;
        } catch (Exception e) {

        }
        return 0;
    }

    /**
     * 业务说明：从订单这边判断，得到采购入库的货权归属
     *
     * @param wmsId                 仓库id
     * @param sku                   商品sku
     * @param purchaseQty           购买的数量
     * @param deptMaxPurchaseQtyMap 部门对应的采购需求数量   k= 部门id, v=本次采购的最大数
     * @return
     */
    @Override
    public List<DeptAssignQtyDto> getDeptAssignQtyDetail(Integer wmsId, String sku, Integer purchaseQty, Map<Integer, Integer> deptMaxPurchaseQtyMap) {

        List<DeptAssignQtyDto> deptAssignQtyDtoList = Lists.newLinkedList();
        if (null == wmsId || wmsId < 1) return deptAssignQtyDtoList;
        if (Strings.isNullOrEmpty(sku)) return deptAssignQtyDtoList;
        if (null == purchaseQty || purchaseQty < 1) return deptAssignQtyDtoList;
        //1,得到skp，仓库对应的区域集合；
        String spu = productSkuService.getSpuBySku(sku);
        if (Strings.isNullOrEmpty(spu)) return deptAssignQtyDtoList;
        Set<Integer> zoneIdSet = zoneWarehousePriorityService.findZoneSetByWarehouseIdAndSpu(wmsId, spu);

        if (CollectionUtils.isNullOrEmpty(deptMaxPurchaseQtyMap))
        {//如果没有传过来采购的部门id,直接返回空；
            return  deptAssignQtyDtoList;
        }

        //通过传过来的有采购单的部门，联合得到符合条件的订单,通过区域，sku ，订单状态为 已审核，待采购  找到对应的订单需求总数
        Set<Integer> deptSet = deptMaxPurchaseQtyMap.keySet();

        //按照sku,区域,部门id,找出订单状态为已经审核，待采购的订单项明细；
        List<DeptOrderQtyTimeDto> deptOrderQtyTimeDtoList = orderService.findOrderSkuQtyDetail(zoneIdSet, sku, Sets.newHashSet(OrderStateEnum.auditPass, OrderStateEnum.waitPurchase),deptSet);

        //查询得到的订单项为空，直接返回传过来的部门，最大采购数量；
        if(CollectionUtils.isNullOrEmpty(deptOrderQtyTimeDtoList))
            return deptMaxPurchaseQtyMap.entrySet().stream().map(item->{
            DeptAssignQtyDto deptAssignQtyDto = new DeptAssignQtyDto();
            deptAssignQtyDto.setDeptId(item.getKey());
            deptAssignQtyDto.setQty(item.getValue());
            return deptAssignQtyDto;

        }).collect(Collectors.toList());





        AtomicInteger leftNum = new AtomicInteger(purchaseQty);
        for (DeptOrderQtyTimeDto deptOrderQtyTimeDto : deptOrderQtyTimeDtoList)
        {
            Integer orderItemSeoDepartmentId = deptOrderQtyTimeDto.getDept();
            Integer orderItemNeedQty = deptOrderQtyTimeDto.getQty();
            Integer deptMaxPurchaseQty = deptMaxPurchaseQtyMap.get(orderItemSeoDepartmentId);

            int leftNumValue = leftNum.get();
            boolean  leftNumAllMatch =   leftNumValue >= orderItemNeedQty;
            boolean  deptMaxAllMatch =  deptMaxPurchaseQty >= orderItemNeedQty;


            //leftNum not match  , break;
            if (!leftNumAllMatch)
            {//如果剩下的采购入库的数量不是都满足该订单项的需求， 那就部分分配给该部门；
                if (leftNumValue >0)
                {//部分满足
                    if (deptMaxPurchaseQty >= leftNumValue)
                    {
                        deptOrderQtyTimeDto.setAssign(true);
                        deptOrderQtyTimeDto.setAssignQty(leftNumValue);

                        //扣减入库数量剩下的数量
                         leftNum.addAndGet(-1 * leftNumValue);
                        //扣减 部门最大采购数量 剩下数量
                        deptMaxPurchaseQtyMap.put(orderItemSeoDepartmentId,(deptMaxPurchaseQty - leftNumValue));

                    }

                    break;

                }


            }else
            {//如果剩下的采购入库的数量满足该订单项的需求， 那就分配给该部门；

                if(deptMaxAllMatch)
                {
                    deptOrderQtyTimeDto.setAssign(true);
                    deptOrderQtyTimeDto.setAssignQty(orderItemNeedQty);

                    //扣减入库数量剩下的数量
                    leftNum.addAndGet(-1 * orderItemNeedQty);
                    //扣减 部门最大采购数量 剩下数量
                    deptMaxPurchaseQtyMap.put(orderItemSeoDepartmentId,(deptMaxPurchaseQty - orderItemNeedQty));
                }else
                {
                    if (deptMaxPurchaseQty > 0)
                    {
                        deptOrderQtyTimeDto.setAssign(true);
                        deptOrderQtyTimeDto.setAssignQty(deptMaxPurchaseQty);

                        //扣减入库数量剩下的数量
                        leftNum.addAndGet(-1 * deptMaxPurchaseQty);
                        //扣减 部门最大采购数量 剩下数量
                        deptMaxPurchaseQtyMap.put(orderItemSeoDepartmentId,(deptMaxPurchaseQty - deptMaxPurchaseQty));
                    }
                    break;
                }
            }


        Map<Integer, List<DeptOrderQtyTimeDto>> collect = deptOrderQtyTimeDtoList.stream()
                .filter(item -> item.isAssign())
                .collect(Collectors.groupingBy(DeptOrderQtyTimeDto::getDept));

        deptAssignQtyDtoList = collect.entrySet().stream().map(entry -> {
            DeptAssignQtyDto deptAssignQtyDtoTemp = new DeptAssignQtyDto();
            deptAssignQtyDtoTemp.setDeptId(entry.getKey());
            deptAssignQtyDtoTemp.setQty(entry.getValue().stream().mapToInt(DeptOrderQtyTimeDto::getAssignQty).sum());

            return deptAssignQtyDtoTemp;

        }).collect(Collectors.toList());





//            if ( leftNumMatch && deptMaxMatch)
//            {//采购入库数量，和 部门最大采购数量，同时满足该订单项， 则设置该订单项为已经分配，并记录分配数量；
//                deptOrderQtyTimeDto.setAssign(true);
//                deptOrderQtyTimeDto.setAssignQty(orderItemNeedQty);
//
//                //扣减入库数量剩下的数量
//                leftNum.addAndGet(-1 * orderItemNeedQty);
//                //扣减 部门最大采购数量 剩下数量
//                deptMaxPurchaseQtyMap.put(orderItemSeoDepartmentId,(deptMaxPurchaseQty - orderItemNeedQty));
//
//            } else {
//
//                //处理不同的情况
//                if(!leftNumMatch)
//                {//如果采购入库数量剩下的不足
//                    if(leftNum.get() > 0)
//                    {
//                        //考虑部门最大采购剩下的数量
//                        if (deptMaxMatch)
//                        {
//
//                        }
//
//
//                        deptOrderQtyTimeDto.setAssign(true);
//                        deptOrderQtyTimeDto.setAssignQty(Math.abs(leftNum.get()-orderItemNeedQty));
//                    }
//                    break;
//                }



//            }


        }




        ////////////////////////////////////////////////////////////////////////////////


//        //3,如果都满足，直接全部分配；
//        Integer needTotal = deptAssignQtyDtoList.stream().mapToInt(DeptAssignQtyDto::getQty).sum();
//        if (purchaseQty >= needTotal)
//        {
//            return deptAssignQtyDtoList;
//        }
//
//        //否则，按照订单项的时间来，时间在前的先分配；
//        List<DeptOrderQtyTimeDto> deptOrderQtyTimeDtoList = orderService.findOrderSkuQtyDetail(zoneIdSet, sku, Sets.newHashSet(OrderStateEnum.auditPass, OrderStateEnum.waitPurchase));
//
//        AtomicInteger leftNum = new AtomicInteger(purchaseQty);
//        for (DeptOrderQtyTimeDto deptOrderQtyTimeDto : deptOrderQtyTimeDtoList) {
//
//            if (leftNum.addAndGet(-1 * deptOrderQtyTimeDto.getQty()) >= 0) {
//                deptOrderQtyTimeDto.setAssign(true);
//            } else {
//                deptOrderQtyTimeDto.setAssign(true);
//                deptOrderQtyTimeDto.setAssignQty(deptOrderQtyTimeDto.getQty() + leftNum.get());
//                break;
//            }
//
//
//        }
//
//        Map<Integer, List<DeptOrderQtyTimeDto>> collect = deptOrderQtyTimeDtoList.stream()
//                .filter(item -> item.isAssign())
//                .collect(Collectors.groupingBy(DeptOrderQtyTimeDto::getDept));
//
//
//        deptAssignQtyDtoList = collect.entrySet().stream().map(entry -> {
//            DeptAssignQtyDto deptAssignQtyDtoTemp = new DeptAssignQtyDto();
//            deptAssignQtyDtoTemp.setDeptId(entry.getKey());
//            deptAssignQtyDtoTemp.setQty(entry.getValue().stream().mapToInt(DeptOrderQtyTimeDto::getAssignQty).sum());
//
//            return deptAssignQtyDtoTemp;
//
//        }).collect(Collectors.toList());

        return deptAssignQtyDtoList;








    }

    /**
     * 回写运单号到订单表然后通知wms出库
     *
     * @param transportRequest
     * @return
     */
    @Override
    public TransportResponse updateOrderLogisticsInfo(TransportRequest transportRequest) {
        TransportResponse transportResponse = orderService.updateOrderLogisticsInfo(transportRequest);
        Orders orders = orderService.findOrderById(transportRequest.getOrderId());
        ordersFsmProxyService.processEvent(orders, OrderEventEnum.logisticResponseOk, "物流指派成功");
        return transportResponse;
    }

    /**
     * 物流重新通知我们这边的接口
     *
     * @param transportRequest
     * @return
     */
    @Override
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TransportResponse reAssignmentLogistics(TransportRequest transportRequest) {
        TransportResponse transportResponse = new TransportResponse();
        if (!Optional.ofNullable(transportRequest).isPresent()) {
            transportResponse.setCode(TransportResponse.FAIL);
            transportResponse.setErrorMsg("非法请求参数");
            return transportResponse;
        }
        String code = transportRequest.getCode();
        try {
            if (code.equals(TransportRequest.SUCCESS)) {
                Orders orders = orderService.findOrderById(transportRequest.getOrderId());
                ordersFsmProxyService.processEvent(orders, OrderEventEnum.logisticResponseOk, "物流指派成功");
            }
            orderService.updateOrderLogisticsInfo(transportRequest);
            transportResponse.setCode(TransportResponse.SUCCESS);
        } catch (Exception e) {
            logger.error("重新指派物流回调失败，失败原因{},参数{}",e.getMessage(),transportRequest);
            transportResponse.setCode(TransportResponse.FAIL);
            transportResponse.setErrorMsg("重新指派物流回调失败，失败原因：" + e.getMessage());
        }
        return transportResponse;
    }

    /**
     * 采购获取获取部门分配sku流水
     *
     * @param purchaseItemRq 具体逻辑待补充
     */
    @Override
    public List<PurchaseItemDTO> getPurchaseItem(List<PurchaseItemRq> purchaseItemRq) {
        List<PurchaseItemDTO> purchaseItemDTOList = Lists.newLinkedList();
        Optional.ofNullable(purchaseItemRq).ifPresent(purchaseItemRqList -> purchaseItemRqList.stream().forEach(purchaseItemRq1 -> {

            List<DeptAssignQtyDto> deptAssignQtyDetail = getDeptAssignQtyDetail(purchaseItemRq1.getWmsId(), purchaseItemRq1.getSku(), purchaseItemRq1.getPurchaseQty(), purchaseItemRq1.getDeptMaxQty());

            deptAssignQtyDetail.stream().forEach(deptAssignQtyDto -> {
                PurchaseItemDTO purchaseItemDTO = new PurchaseItemDTO();
                purchaseItemDTO.setSku(purchaseItemRq1.getSku());
                purchaseItemDTO.setDeptId(deptAssignQtyDto.getDeptId());
                purchaseItemDTO.setQty(deptAssignQtyDto.getQty());
                purchaseItemDTOList.add(purchaseItemDTO);
            });
        }));
        return purchaseItemDTOList;

    }

}
