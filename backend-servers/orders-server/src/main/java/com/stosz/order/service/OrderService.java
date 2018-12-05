package com.stosz.order.service;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.dto.*;
import com.stosz.order.ext.enums.*;
import com.stosz.order.ext.model.*;
import com.stosz.order.mapper.order.OrdersAdditionMapper;
import com.stosz.order.mapper.order.OrdersItemsMapper;
import com.stosz.order.mapper.order.OrdersLinkMapper;
import com.stosz.order.mapper.order.OrdersMapper;
import com.stosz.order.service.asyn.OrderAsynService;
import com.stosz.order.service.outsystem.wms.IWmsOrderService;
import com.stosz.plat.common.RestCallBackResult;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.enums.InterfaceNameEnum;
import com.stosz.plat.enums.InterfaceTypeEnum;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.product.ext.model.AttributeValue;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductSku;
import com.stosz.product.ext.service.IAttributeValueService;
import com.stosz.product.ext.service.ICurrencyService;
import com.stosz.product.ext.service.IProductService;
import com.stosz.product.ext.service.IProductSkuService;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.OrderHandleEnum;
import com.stosz.store.ext.service.IStockService;
import com.stosz.tms.service.ITransportFacadeService;
import com.stosz.tms.vo.TrackingTaskAndDetailInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class OrderService extends OrderBaseService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersItemsMapper ordersItemsMapper;

    @Autowired
    private OrdersAdditionMapper ordersAdditionMapper;


    @Autowired
    private OrdersWebInterfaceRecordService ordersWebInterfaceRecordService;


    @Autowired
    private OrdersLinkMapper ordersLinkMapper;

    @Resource
    private IProductService productService;

    @Qualifier("orderFsmProxyService")
    @Autowired
    private FsmProxyService<Orders> ordersFsmProxyService;

    @Resource
    private OrderAsynService orderAsynService;

    @Resource
    private IAttributeValueService attributeValueService;

    @Autowired
    private FsmHistoryService orderFsmHistoryService;

    @Autowired
    private ICurrencyService currencyService;

    @Autowired
    private AfterSaleService afterSaleService;
    @Autowired
    private IWmsOrderService wmsOrderService;
    @Resource
    private IStockService stockService;
    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;
    @Autowired
    private IProductSkuService productSkuService;
    @Value("${wms_writeBack_key}")
    private String secretKey;
    @Autowired
    private ITransportFacadeService transportFacadeService;


    /**
     * 订单列表页
     *
     * @param param
     * @return
     */
    public List<OrderDO> queryBySearchParam(OrderSearchParam param) {
        long start = System.currentTimeMillis();
        List<OrderDO> list = ordersMapper.findBySearchParam(param);
        printTimeCost(start, "查询订单表信息");

        List<Integer> orderIds = list.stream().map(o -> o.getId()).collect(Collectors.toList());
        start = System.currentTimeMillis();

       if(orderIds != null && orderIds.size() != 0){
           //获取联系人信息
           List<OrdersLink> ols = ordersLinkMapper.findByOrderIdList(orderIds.size() == 0 ? null : orderIds);
           //找到有退换货记录的订单id
           List<Integer> rmaRecords = afterSaleService.queryOrderByOrderIds(orderIds);
           Map<Long,List<OrdersLink>> orderLinkMap = ols.stream().collect(Collectors.groupingBy(OrdersLink::getOrdersId));
           list.stream().forEach(e -> {
               if(rmaRecords.contains(e.getId())){
                   e.setHasRmaRecord("yes");
               }
               List<OrdersLink> ordersLinkList = orderLinkMap.get(e.getId().longValue());
               OrdersLink l = Optional.ofNullable(ordersLinkList).isPresent() ? ordersLinkList.get(0) : new OrdersLink();
               e.setArea(l.getArea());
               e.setTelphone(l.getTelphone());
               e.setFirstName(l.getFirstName());
               e.setLastName(l.getLastName());
               e.setEmail(l.getEmail());
               e.setAddress(l.getAddress());
               e.setZipcode(l.getZipcode());
           });
           printTimeCost(start, "查询联系人信息");
       }


        start = System.currentTimeMillis();
        //获取订单行信
        List<OrdersItem> ois = ordersItemsMapper.findByOrderIds(orderIds);
        printTimeCost(start, "查询订单项信息");



        start = System.currentTimeMillis();
        //<orderId,List<OrdersItem>>
        Map<Long, List<OrdersItem>> orderItemsMap = ois.stream().collect(groupingBy(OrdersItem::getOrdersId));

        //订单列表页，标题展示
        list.stream().forEach(e -> {
            List<OrdersItem> ordersItems = Optional.ofNullable(orderItemsMap.get(Long.valueOf(e.getId()))).orElse(Lists.newLinkedList());
            //<spu, List<OrdersItem>>
            Map<String, List<OrdersItem>> spuMap = ordersItems.stream().collect(groupingBy(OrdersItem::getSpu));
            List<OrderItemSpuDTO> orderItemSpuDTOList = new ArrayList<>();
            for (String spu : spuMap.keySet()) {
                List<OrdersItem> oi = spuMap.get(spu);
                List<OrderItemSpuDTO.OrderItemSku> skuList = new ArrayList<>();
                if (oi != null && oi.size() != 0) {
                    for (OrdersItem o : oi) {
                        OrderItemSpuDTO.OrderItemSku sku = new OrderItemSpuDTO.OrderItemSku();
                        sku.setQty(o.getQty());
                        sku.setAttr(o.getAttrTitleArray());
                        sku.setSku(o.getSku());
                        skuList.add(sku);
                    }
                    orderItemSpuDTOList.add(new OrderItemSpuDTO(spu, oi.get(0).getProductTitle(), skuList));
                }

            }
            e.setSpuList(orderItemSpuDTOList);
        });

        printTimeCost(start, "组装订单标题信息");
        return list;
    }

    /**
     * 统计个数
     *
     * @param param
     * @return
     */
    public Integer countBySearchParam(OrderSearchParam param) {
        return ordersMapper.countBySearchParam(param);
    }

    /**
     * TODO
     * 更新订单信息
     *
     * @param order
     * @date 2017年11月23日
     * @author liusl
     */
    public void updateOrder(Orders order) {
        ordersMapper.update(order);
    }

    /**
     * 保存订单；
     *
     * @return
     */
    public Orders saveOrder(OrderSaveDto orderSaveDto) {

        //checkOrderSaveDto
        checkOrderSaveDto(orderSaveDto);

        Orders orders = new Orders();
        orders.setOrderStatusEnum(OrderStateEnum.start);
        final OrderSaveDto.OrderInfo orderInfo = orderSaveDto.getOrderInfo();
        orders.setMerchantOrderNo(orderInfo.getMerchantOrderNo());
        orders.setZoneCode(orderInfo.getZoneCode());
        orders.setCurrencyCode(orderInfo.getCurrencyCode());
        orders.setOrderExchangeRate(orderInfo.getOrderExchangeRate());
        orders.setOrderAmount(orderInfo.getOrderAmount());
        orders.setGoodsQty(orderInfo.getGoodsQty());
        orders.setIp(orderInfo.getIp());
        orders.setPaymentMethod(orderInfo.getPaymentMethod());
        orders.setCustomerMessage(orderInfo.getCustomerMeassage());
        orders.setPurchaserAt(orderInfo.getPurchaserAt());
        orders.setGetCode(orderInfo.getGetCode());
        orders.setInputCode(orderInfo.getInputCode());
        orders.setSeoUserId(orderInfo.getSeoUserId());

        //计算出seodepartId buDepartmentId; todo

        //计算出订单的标题，即order_title
        orders.setOrderTitle(getOrderTitle(orderSaveDto));

        ordersMapper.insert(orders);


        if (null != orders.getId() && orders.getId() > 0) {
            orderSaveDto.setOrderId(Long.valueOf(orders.getId()));
            ordersFsmProxyService.processEvent(orders, OrderEventEnum.create, "订单创建成功");
            orderAsynService.saveOrderSaveTask(orderSaveDto);
        }

        return orders;

    }

    /**
     * 保存退货订单；
     *
     * @return TODO
     */
    public Orders saveChangeOrder() {
        return null;
    }


    /**
     * 获取订单的标题；
     *
     * @return
     */
    private String getOrderTitle(OrderSaveDto orderSaveDto) {

        StringBuffer stringBuffer = new StringBuffer();

        orderSaveDto.getOrderItemInfoList().stream().forEach(orderItemInfo -> {

            Product product = productService.getBySpu(orderItemInfo.getSpu());
            stringBuffer.append(product.getTitle()).append("<hr>");

            List<AttributeValue> valueList = attributeValueService.getValueListBySku(orderItemInfo.getSpu());

            Optional.ofNullable(valueList).ifPresent(valueListItem -> stringBuffer.append(valueListItem.stream()
                    .map(AttributeValue::getTitle).sorted(Comparator.naturalOrder()).collect(Collectors.joining("<br>"))));

            stringBuffer.append("X").append(orderItemInfo.getQty());

        });

        return stringBuffer.toString();
    }

    /**
     * 做输入校验
     *
     * @param orderSaveDto
     */
    private void checkOrderSaveDto(OrderSaveDto orderSaveDto) {
        //todo
    }


    /**
     * 更新订单备注
     *
     * @param orderId
     * @param memo
     */
    public void updateOrderMemo(Integer orderId, String memo) {
        Orders orders = ordersMapper.getById(orderId);
        orders.setMemo(memo);
        ordersFsmProxyService.processLog(orders, memo);
        ordersMapper.updateMemoByOrderId(orderId, memo);
    }


    /**
     * 订单摘要
     *
     * @param orderId
     * @return
     */
    public OrderDetailDTO findOrderAbstractByOrderId(Integer orderId) {

        OrderDetailDTO dto = new OrderDetailDTO();

        //订单信息
        Orders orders = ordersMapper.getById(orderId);
        orders = orders == null ? new Orders() : orders;
        Assert.notNull(orders,"找不到该订单的信息");
        TrackingTaskAndDetailInfoVo trackingTaskAndDetailInfoVo = new TrackingTaskAndDetailInfoVo();
        try
        {
            trackingTaskAndDetailInfoVo = transportFacadeService.queryTaskAndDetailInvo(orders.getId());
            trackingTaskAndDetailInfoVo = trackingTaskAndDetailInfoVo==null?new TrackingTaskAndDetailInfoVo():trackingTaskAndDetailInfoVo;
        }catch (Exception e){
            logger.error("获取物流状态异常:失败原因:"+e.getMessage());
            trackingTaskAndDetailInfoVo = new TrackingTaskAndDetailInfoVo();
        }
        if(trackingTaskAndDetailInfoVo.getTrackingTask()!=null){
            orders.setTrackingStatusShow(Optional.ofNullable(trackingTaskAndDetailInfoVo.getTrackingTask().getTrackStatus()).orElse("--"));
        }

        //订单联系人
        OrdersLink ordersLink = ordersLinkMapper.getByOrderId(orderId);
        ordersLink = ordersLink == null ? new OrdersLink() : ordersLink;
        Assert.notNull(ordersLink,"找不到客户的信息");

        //订单行
        List<OrdersItem> items = ordersItemsMapper.getByOrderId(orderId);
        items = items == null ? new ArrayList<OrdersItem>() : items;
        Assert.notEmpty(items, "找不到订单项");
        Set<String> spus = new HashSet<String>();
        items.forEach(e -> {
        /*    e.setProductImageUrl(getProjectErpImageHttpPrefix() + "/" + e.getProductImageUrl());*/
            spus.add(e.getSpu());
        });
        try {
            List<Product> products = productService.findBySpuSet(spus);
            items.forEach(e -> {
                products.forEach(p -> {
                    if (p.getSpu().equals(e.getSpu())) {
                        List<ProductSku> skuList = p.getSkuList() == null ? new ArrayList<ProductSku>() : p.getSkuList();
                        List<OrderSkuAttribute> skuAttrs = new ArrayList<OrderSkuAttribute>();
                        skuList.forEach(s -> {
                            OrderSkuAttribute orderSkuAttribute = new OrderSkuAttribute();
                            orderSkuAttribute.setSku(s.getSku());
                            orderSkuAttribute.setAttributeValueTitle(s.getAttributeValueTitle());
                            skuAttrs.add(orderSkuAttribute);
                            if (s.getSku().equals(e.getSku())) {
                                e.setAttrTitleArray(s.getAttributeValueTitle());
                            }
                        });
                        e.setAttrList(skuAttrs);
                    }
                });
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //订单附加项
        OrdersAddition ordersAddition = ordersAdditionMapper.getByOrderId(orderId);
        ordersAddition = ordersAddition == null ? new OrdersAddition() : ordersAddition;
        if (ordersAddition != null) {
            com.stosz.product.ext.model.Currency currency = currencyService.getByCurrencyCode(orders.getCurrencyCode());
            currency= currency==null? new com.stosz.product.ext.model.Currency():currency;
            OrderInfoDTO orderInfoDto = convertToOrderInfoDTO(currency, orders, ordersAddition);
            dto.setOrderInfo(orderInfoDto);
        }
        //客户信息
        if (orders != null && ordersLink != null) {
            OrderLinkDTO orderLinkDto = convertToOrderLink(orders, ordersLink);
            dto.setOrderLink(orderLinkDto);
        }

        dto.setOrderItems(items);
        return dto;
    }

    /**
     * 将订单信息+联系人信息转换成订单客户详情信息
     *
     * @param orders
     * @param ordersLink
     * @return
     * @date 2017年11月27日
     * @author liusl
     */
    private OrderLinkDTO convertToOrderLink(Orders orders, OrdersLink ordersLink) {
        OrderLinkDTO orderLinkDTO = new OrderLinkDTO();
        BeanUtils.copyProperties(ordersLink, orderLinkDTO, StringUtils.EMPTY);
        orderLinkDTO.setLinkId(ordersLink.getId().longValue());
        orderLinkDTO.setGetCode(orders.getGetCode());
        orderLinkDTO.setInputCode(orders.getInputCode());
        orderLinkDTO.setCodeType(String.valueOf(orders.getCodeType()));
        orderLinkDTO.setCustomerMeassage(orders.getCustomerMessage());
        return orderLinkDTO;
    }

    /**
     * 将订单+订单附加信息转换为订单信息
     *
     * @param orders
     * @param ordersAddition
     * @return
     * @date 2017年11月27日
     * @author liusl
     */
    private OrderInfoDTO convertToOrderInfoDTO(com.stosz.product.ext.model.Currency currency, Orders orders, OrdersAddition ordersAddition) {
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        BeanUtils.copyProperties(orders, orderInfoDTO, StringUtils.EMPTY);
        orderInfoDTO.setOrderId(orders.getId());
        orderInfoDTO.setOrderFrom(ordersAddition.getOrderFrom());
        orderInfoDTO.setIpName(orders.getIpName());
        orderInfoDTO.setTrackingNo(orders.getTrackingNo());
        orderInfoDTO.setLogisticsName(orders.getLogisticsName());
        if (StringUtils.isNotEmpty(orders.getTrackingStatusShow())) {
            orderInfoDTO.setTrackingStatusShow(orders.getTrackingStatusShow());
        } else {
            orderInfoDTO.setTrackingStatusShow("---");
        }
        orderInfoDTO.setPaymentMethod(PayMethodEnum.fromId(orders.getPaymentMethod()).display());
        orderInfoDTO.setPayState(PayStateEnum.fromId(orders.getPayState()).display());
        orderInfoDTO.setDepartmentUserInfo(orders.getDepartmentUserInfo());
        orderInfoDTO.setAmountShow(orders.getAmountShow());
        orderInfoDTO.setSeoUserName(orders.getSeoUserName());
        orderInfoDTO.setWebUrl(ordersAddition.getWebUrl());
        orderInfoDTO.setMerchantEnum(orders.getMerchantEnum().display());
        orderInfoDTO.setComboName(orders.getComboName());
        if(Optional.ofNullable(currency).isPresent()){
            orderInfoDTO.setSymbolLeft(currency.getSymbolLeft());
            orderInfoDTO.setSymbolRight(currency.getSymbolRight());
            orderInfoDTO.setCurrencyName(currency.getName());
        }
        orderInfoDTO.setOrderStatusEnum(orders.getOrderStatusEnum().display());
        return orderInfoDTO;
    }

    /**
     * 按照sku , dept 搜索到所有的审核通过，待采购的订单,按照时间递增；
     *
     * @param sku
     * @param dept
     * @return
     */
    public List<Orders> findOrderBySkuDept(String sku, Integer dept) {
        return Optional.ofNullable(ordersMapper.findBySkuAndDept(sku, dept)).orElse(Lists.newLinkedList());
    }

    /**
     * todo 设置订单状态为已经占用了库存；
     *
     * @param orderId 订单id
     * @return
     */
    public boolean setOccupy(Integer orderId) {
        //moke
        logger.info("设置" + orderId + " 为占用库存状态成功");
        return true;
    }


    /**
     * 设置某个订单项目为库存占用状态
     *
     * @param ordersItemId 订单项id
     * @param qty          库存占用数量
     * @param warehouseId  仓库
     * @return
     */
    public boolean setOrderItemsOccupy(Integer ordersItemId, Integer warehouseId, Integer qty) {
        logger.info("设置订单项id:{}为占用库存状态成功,仓库：{},数量：", ordersItemId, warehouseId, qty);
        return Optional.ofNullable(ordersItemsMapper.setOrderItemsOccupy(ordersItemId, warehouseId, qty)).orElse(0) > 0;
    }

    /**
     * 查询出订单信息同时带上仓库ID
     *
     * @param orderId
     * @return
     * @date 2017年11月23日
     * @author liusl
     */
    public Orders findOrderById(Integer orderId) {
        return ordersMapper.findOrderById(orderId);
    }

    /**
     * 通过orderId 查询订单项信息；
     *
     * @param orderId 订单id
     * @return
     */
    public List<OrdersItem> findOrdersItemByOrderId(Integer orderId) {
        return Optional.ofNullable(ordersMapper.findOrdersItemByOrderId(orderId)).orElse(Lists.newLinkedList());
    }

    /**
     * 更新物流订单状态
     *
     * @param order
     * @date 2017年11月23日
     * @author liusl
     */
    public void updateOrderLogisticsStatus(Orders order) {
        ordersMapper.updateOrderLogisticsStatus(order);
    }

    /**
     * 订单取消
     *
     * @param orderId
     * @date 2017年11月27日
     * @author liusl
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void orderCancel(Integer orderId, String reasonType, String memo) throws Exception {
        Assert.notNull(orderId, "订单orderId不能为空非法参数请求");
        Orders orders = ordersMapper.getById(orderId);
        Assert.notNull(orders, "无法获取到订单信息,请确认该订单是否存在");
        OrderCancelReasonEnum orderCancelReasonEnum = OrderCancelReasonEnum.fromName(reasonType);
        ordersFsmProxyService.processEvent(orders, OrderEventEnum.cancel, "取消订单-" + orderCancelReasonEnum.display() + "\n" + memo == null ? "" : memo);


//        // TODO: 2017/12/21  写入备注和取消原因
//        Assert.notNull(orderId, "订单orderId不能为空非法参数请求");
//        Orders  orders = ordersMapper.getById(orderId);
//        Assert.notNull(orders, "无法获取到订单信息,请确认该订单是否存在");
//            String  orderStatus = orders.getOrderStatusEnum().toString();
//            //TODO调用状态机取消订单
//            if(orderStatus.equals("waitAudit") || orderStatus.equals("waitContact")){
//              //1.审核阶段，待审核、待联系，填写备注即可变为已取消状态
//                ordersFsmProxyService.processEvent(orders, OrderEventEnum.cancel, orders.getMemo() );
//            }else if(orderStatus.equals("waitPurchase")){
//              //2.采购阶段，待采购，填写备注即可变为已取消状态，并重新计算采购需求
//
//            }else if(orderStatus.equals("auditPass") || orderStatus.equals("waitSpecifyLogistic") ){
//              //3.配货阶段：
//                //2.1普通订单，待发货状态，则此时调用WMS取消订单接口，若WMS返回成功，则订单状态改为“已取消”，库存解锁，否则toast提示“取消失败，请复制此订单号联系技术部。”
//                //2.2转寄仓订单，填写备注后订单状态即可变为已取消状态；转寄仓库存状态变为“已入转寄仓”，已匹配的订单字段清除
//                Integer warehouseType =  orders.getWarehouseTypeEnum();
//                String warehouseTypeStr = WarehouseTypeEnum.fromId(warehouseType).toString();
//                if(warehouseTypeStr.equals("normal")){
//                    List<OrdersItem> items = orderItemsMapper.getByOrderId(orderId);
//
//                    List<StockChangeReq> reqs = convertToChangeWarehouseReq(orders,items);
//                    logger.info("调用WMS取消订单接口 请求参数 request-->", new ObjectMapper().writeValueAsString(reqs));
//
//                    Boolean isSucess = stockService.orderChangeStockQty(reqs);
//                    if(!isSucess){
//                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                        throw new Exception("调用wms取消接口异常");
//                    }else{
//                      //TODO  利用状态机更新状态
//                    }
//
//                }else{
//                    //TODO  利用状态机更新状态
//                }
//            }else if(orderStatus.equals("waitDeliver")){
//              //待发货状态需要用WMS“取消订单接口”推送给WMS，修改入库策略队列表对应数据
//                Map<String,Object> param = new HashMap<String,Object>();
//                param.put("orderCode",orders.getMerchantOrderNo());//取消单号
//                param.put("orderType","");//订单类型
//                param.put("goodsOwner","");//货主
//                logger.info("调用仓储WMS取消订单接口 请求参数 request-->", param.toString());
//                wmsOrderService.subCancelOrder(param);
//            }else{
//                Assert.isTrue(true, "订单状态为["+orders.getOrderStatusEnum().display()+"],不可取消");
//            }
    }

    /**
     * 订单转换成wms需要的数据
     *
     * @param order
     * @param orderItems
     * @return
     * @date 2017年11月27日
     * @author liusl
     */
    public List<StockChangeReq> convertToChangeWarehouseReq(Orders order, List<OrdersItem> orderItems) {
        List<StockChangeReq> changeWarehouseReqs = new ArrayList<StockChangeReq>();
        orderItems.forEach(e -> {
            StockChangeReq stockChangeReq = new StockChangeReq();
            stockChangeReq.setAmount(e.getTotalAmount());
            stockChangeReq.setChangeAt(order.getPurchaserAt());
            stockChangeReq.setDeptId(order.getSeoDepartmentId());
            stockChangeReq.setSku(e.getSku());
            stockChangeReq.setSpu(e.getSpu());
            stockChangeReq.setType(OrderHandleEnum.order_cancel.name());
            stockChangeReq.setChangeQty(e.getQty());
            stockChangeReq.setVoucherNo(order.getMerchantOrderNo());
            stockChangeReq.setWmsId(order.getWarehouseId());
            changeWarehouseReqs.add(stockChangeReq);
        });
        return changeWarehouseReqs;
    }


    /**
     * 通过订单编号集合来查找订单
     *
     * @param ids
     * @return
     * @date 2017-11-27
     * @author wangqian
     */
    public List<Orders> findOrderByOrderIds(List<Integer> ids) {
        return ordersMapper.findOrderByOrderIds(ids);
    }

    public List<Orders> findByOldIds(List<Integer> ids) {
        if (null == ids || ids.size() == 0) return new ArrayList<>();
        return ordersMapper.findByOldIds(ids);
    }

    /**
     * 通过订单编号(建站)集合来查找订单
     *
     * @param merchantOrderNo
     * @return
     * @date 2017-12-8
     * @author wangqian
     */
    public List<Orders> findOrderByMerchantOrderNos(List<String> merchantOrderNo) {
        return ordersMapper.findOrderByMerchantOrderNos(merchantOrderNo);
    }

    /**
     * 通过运单号集合来查找订单
     *
     * @param trackingIds
     * @return
     * @date 2017-11-27
     * @author wangqian
     */
    public List<Orders> findOrderByTrackingIds(List<String> trackingIds) {
        return ordersMapper.findOrderByTrackingIds(trackingIds);
    }

    /**
     * 编辑订单信息
     *
     * @param orderDetailDTO
     * @date 2017年11月28日
     * @author liusl
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void orderEdit(OrderDetailDTO orderDetailDTO) {
        OrderLinkDTO orderLinkDTO = orderDetailDTO.getOrderLink();
        List<OrdersItem> orderItems = orderDetailDTO.getOrderItems();
        Assert.notEmpty(orderItems, "订单项信息为空，非法的请求参数");
        OrderInfoDTO orderInfo = orderDetailDTO.getOrderInfo();
        Assert.notNull(orderInfo, "订单信息为空，非法的请求参数");
        Integer orderId = orderInfo.getOrderId();
        Orders orders = ordersMapper.getById(orderId);
        String state = orders.getState() == null ? "" : orders.getState();
        OrdersLink ordersLink = ordersLinkMapper.getById(orderLinkDTO.getLinkId().intValue());
        List<OrdersItem> oldItems = ordersItemsMapper.getByOrderId(orderId);
        OrdersAddition ordersAddition = ordersAdditionMapper.getByOrderId(orderId);
        Boolean hasRmaRecords = hasRmaRecords(orderId);
        if (StringUtils.isNotEmpty(state) && (state.equals("orderCancel") ||hasRmaRecords )) {
            orderId = copyOrderInfoDataAndUpdateEntity(orders, ordersLink, oldItems, ordersAddition);
            orderLinkDTO.setLinkId(ordersLink.getId().longValue());
        }
        Assert.isTrue(state.equals("waitAudit") || state.equals("waitContact") || state.equals("invalidOrder") || state.equals("orderCancel")|| hasRmaRecords, "只有订单状态为待审核、待联系、无效订单状态或者需要申请换货的订单才可以编辑");
        //比较原来的客户信息与现在的客户信息比较，写入记录表
        String orderLinkMssage = compareOrderLinkAndRerord(orderLinkDTO, ordersLink);
        //item修改记录
        String itemMssage = compareOrderItemsAndRerord(oldItems, orderItems);
        if (StringUtils.isNotEmpty(orderLinkMssage)) {
            //有修改才去修改
            ordersLinkMapper.updateOrderLink(orderLinkDTO);
        }
        //先删除订单的商品信息 然后再插入商品信息 同时需要更新订单表中的数量,总价，以及带符号的总价，备注字段
        for (OrdersItem e : orderItems) {
            e.setOrdersId(orderId.longValue());
            String imgUrl = e.getProductImageUrl().replace(getProjectErpImageHttpPrefix() + "/", "");
            e.setProductImageUrl(imgUrl);
        }
        if (StringUtils.isNotEmpty(itemMssage)) {
            //有修改才去修改
            ordersItemsMapper.deleteByOrderId(orderId);
            ordersItemsMapper.batchInsert(orderItems);
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("goodsQty", orderItems.size());
        param.put("orderId", orderId);
        param.put("confirmAmount", orderInfo.getConfirmAmount());
        String currencyCode = orders.getCurrencyCode();
        com.stosz.product.ext.model.Currency currency = currencyService.getByCurrencyCode(currencyCode);
        String amountShow = orderInfo.getConfirmAmount().toString();
        if (currency != null) {
            if (StringUtils.isNotEmpty(currency.getSymbolLeft())) {
                amountShow = currency.getSymbolLeft() + amountShow;
            } else if (StringUtils.isNotEmpty(currency.getSymbolRight())) {
                amountShow += currency.getSymbolRight();
            }
        }
        param.put("amountShow", amountShow);
        //备注的日志
        String memo = orderInfo.getMemo();
        if (StringUtils.isNotEmpty(memo)) {
            param.put("memo", memo);
            ordersFsmProxyService.processLog(orders, orderInfo.getMemo());
        }
        //修改的状态机日志
        String updateMsg = orderLinkMssage + itemMssage;
        BigDecimal orderInfoConfirmAmount = orderInfo.getConfirmAmount().setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal ordersConfirmAmount = orders.getConfirmAmount().setScale(2, BigDecimal.ROUND_DOWN);
        if (!ordersConfirmAmount.equals(orderInfoConfirmAmount)) {
            updateMsg += "*订单总价由" + orders.getConfirmAmount() + "修改为" + orderInfo.getConfirmAmount();
        }
        if (StringUtils.isNotEmpty(updateMsg)) {
            //记录状态机日志
            ordersFsmProxyService.processLog(orders, updateMsg);
        }
        ordersMapper.updateOrderByOrderId(param);
    }

    /**
     * 查找是否有退换货记录
     * @param orderId
     * @return
     */
    public boolean hasRmaRecords(Integer orderId){
        List<Integer> ids = Lists.newArrayList();
        ids.add(orderId);
        List<Integer> rmaRecordList = afterSaleService.queryOrderByOrderIds(ids);
        return !CollectionUtils.isNullOrEmpty(rmaRecordList);
    }
    /**
     * copy订单数据
     *
     * @param orders
     * @param ordersLink
     * @param oldItems
     * @param ordersAddition
     */
    protected Integer copyOrderInfoDataAndUpdateEntity(Orders orders, OrdersLink ordersLink, List<OrdersItem> oldItems, OrdersAddition ordersAddition) {
        //如果是订单取消状态或者有退换货记录的订单编辑就走内部下单（orderCancel）的逻辑  copy一份之前的数据然后走更新数据的逻辑
        orders.setPurchaserAt(LocalDateTime.now());
        orders.setOrderStatusEnum(OrderStateEnum.start);
        orders.setMerchantEnum(MerchantEnum.innererp);
        orders.setOrderTypeEnum(1);
        orders.setIp("127.0.0.1");
        orders.setIpName("订单系统");
        orders.setMemo("");
        orders.setInputCode("");
        orders.setGetCode("");
        orders.setBlackFields("");
        orders.setRepeatInfo("");
        orders.setCodeType(TelCodeState.succ.ordinal());
        orders.setIpOrderQty(0);
        orders.setOldId(0L);
        ordersMapper.insert(orders);
        Integer newOrderId = orders.getId();
        ordersLink.setOrdersId(newOrderId.longValue());
        ordersLink.setCreateAt(LocalDateTime.now());
        ordersLink.setCreatorId(ThreadLocalUtils.getUser().getId());
        ordersLink.setCreator(ThreadLocalUtils.getUser().getLastName());
        ordersLinkMapper.insert(ordersLink);
        oldItems.forEach(e -> {
            e.setOrdersId(newOrderId.longValue());
            e.setWarehouseId(0);
            e.setOccupyQty(0);
            e.setCreatorId(ThreadLocalUtils.getUser().getId());
            e.setCreator(ThreadLocalUtils.getUser().getLastName());
            e.setOccupyStockTime(null);
            e.setCreateAt(LocalDateTime.now());
            e.setItemStatusEnum(ItemStatusEnum.unAudit.ordinal());
        });
        ordersItemsMapper.batchInsert(oldItems);
        ordersAddition.setOrdersId(newOrderId.longValue());
        ordersAddition.setUserAgent("");
        ordersAddition.setIp("127.0.0.1");
        ordersAddition.setIpName("订单系统");
        ordersAddition.setCreateAt(LocalDateTime.now());
        ordersAddition.setCreatorId(ThreadLocalUtils.getUser().getId());
        ordersAddition.setCreator(ThreadLocalUtils.getUser().getLastName());
        ordersAdditionMapper.insert(ordersAddition);
        ordersFsmProxyService.processEvent(orders, OrderEventEnum.innerCreate, "内部创建订单");
        return orders.getId();
    }

    private String compareOrderItemsAndRerord(List<OrdersItem> oldItems, List<OrdersItem> orderItems) {
        if (oldItems.equals(orderItems)) {
            return "";
        }
        StringBuilder recordMsg = new StringBuilder("订单项由:*");
        oldItems.forEach(e -> {
            recordMsg.append("sku:" + e.getSku() + " 数量：" + e.getQty() + "*");
        });
        recordMsg.append("修改为:*");
        orderItems.forEach(e -> {
            recordMsg.append("sku:" + e.getSku() + " 数量：" + e.getQty() + "*");
        });
        return recordMsg.toString();
    }

    /**
     * 对比客户信息
     *
     * @param orderLinkDTO
     * @param ordersLink
     * @date 2017年11月29日
     * @author liusl
     */
    private String compareOrderLinkAndRerord(OrderLinkDTO orderLinkDTO, OrdersLink ordersLink) {
        StringBuilder recordMsg = new StringBuilder();
        String newfirstName = orderLinkDTO.getFirstName() == null ? "" : orderLinkDTO.getFirstName();
        String newLastName = orderLinkDTO.getLastName() == null ? "" : orderLinkDTO.getLastName();
        String newTelphone = orderLinkDTO.getTelphone() == null ? "" : orderLinkDTO.getTelphone();
        String newEmail = orderLinkDTO.getEmail() == null ? "" : orderLinkDTO.getEmail();
        String newProvince = orderLinkDTO.getProvince() == null ? "" : orderLinkDTO.getProvince();
        String newCity = orderLinkDTO.getCity() == null ? "" : orderLinkDTO.getCity();
        String newArea = orderLinkDTO.getArea() == null ? "" : orderLinkDTO.getArea();
        String newAddress = orderLinkDTO.getAddress() == null ? "" : orderLinkDTO.getAddress();
        String newZipcode = orderLinkDTO.getZipcode() == null ? "" : orderLinkDTO.getZipcode();

        String oldfirstName = ordersLink.getFirstName() == null ? "" : ordersLink.getFirstName();
        String oldLastName = ordersLink.getLastName() == null ? "" : ordersLink.getLastName();
        String oldTelphone = ordersLink.getTelphone() == null ? "" : ordersLink.getTelphone();
        String oldEwail = ordersLink.getEmail() == null ? "" : ordersLink.getEmail();
        String oldProvince = ordersLink.getProvince() == null ? "" : ordersLink.getProvince();
        String oldCity = ordersLink.getCity() == null ? "" : ordersLink.getCity();
        String oldArea = ordersLink.getArea() == null ? "" : ordersLink.getArea();
        String oldAddress = ordersLink.getAddress() == null ? "" : ordersLink.getAddress();
        String oldZipcode = ordersLink.getZipcode() == null ? "" : ordersLink.getZipcode();
        if (!newLastName.equals(oldLastName)) {
            recordMsg.append("姓从" + oldLastName + "改为" + newLastName + "*");
        }
        if (!newfirstName.equals(oldfirstName)) {
            recordMsg.append("名字从" + oldfirstName + "改为" + newfirstName + "*");
        }
        if (!newEmail.equals(oldEwail)) {
            recordMsg.append("邮箱从" + oldEwail + "改为" + newEmail + "*");
        }
        if (!newProvince.equals(oldProvince)) {
            recordMsg.append("省从" + oldProvince + "改为" + newProvince + "*");
        }
        if (!newCity.equals(oldCity)) {
            recordMsg.append("城市从" + oldCity + "改为" + newCity + "*");
        }
        if (!newTelphone.equals(oldTelphone)) {
            recordMsg.append("电话号码从" + oldTelphone + "改为" + newTelphone + "*");
        }
        if (!newArea.equals(oldArea)) {
            recordMsg.append("区域从" + oldArea + "改为" + newArea + "*");
        }
        if (!newAddress.equals(oldAddress)) {
            recordMsg.append("地址从" + oldAddress + "改为" + newAddress + "*");
        }
        if (!newZipcode.equals(oldZipcode)) {
            recordMsg.append("邮编从" + oldZipcode + "改为" + newZipcode + "*");
        }
        return recordMsg.toString();
    }

    public BatchQueryResultDTO batchQuery(Integer type, String content, OrderSearchParam param) {
        BatchQueryResultDTO dto = new BatchQueryResultDTO();
        switch (BatchQueryEnum.fromId(type)) {
            case tel: {
                String batchStr = Arrays.stream(content.split("[\\t \\n]+")).filter(s -> s.length() > 0).distinct().collect(Collectors.joining(", "));
                List<String> queryList = Arrays.stream(content.split("[\\t \\n]+")).filter(s -> s.length() > 0).distinct().collect(Collectors.toList());
                param.setTelBatch(batchStr);
                List<OrderDO> queryResultList = queryBySearchParam(param);
                Map<String,List<OrderDO>>foundList = queryResultList.stream().collect(Collectors.groupingBy(OrderDO::getTelphone));
                List<String> notFoundList = queryList.stream().filter(o -> foundList.get(o) == null).distinct().collect(Collectors.toList());
                dto.setFail(notFoundList);
                queryList.removeAll(notFoundList);
                dto.setSucc(queryList);
                break;
            }
            case orderId: {
                List<String> queryList = Arrays.stream(content.split("[\\t \\n]+")).filter(s -> s.length() > 0).distinct().collect(Collectors.toList());
                //获取到的订单
                List<Integer> orderIds = new ArrayList<>();
                try {
                    orderIds = queryList.stream().map(e -> Integer.valueOf(e)).collect(Collectors.toList());
                } catch (Exception e) {
                    throw new RuntimeException("订单号过长或者有非法字符");
                }
                String batchStr = Joiner.on(",").join(orderIds);
                param.setOrderIdBatch(batchStr);
                List<OrderDO> queryResultList = ordersMapper.findBySearchParam(param);

                Map<Integer,List<OrderDO>>foundList = queryResultList.stream().collect(Collectors.groupingBy(OrderDO::getId));
                List<Integer> notFoundList = orderIds.stream().filter(o -> foundList.get(o) == null).distinct().collect(Collectors.toList());
                dto.setFail(notFoundList);
                queryList.removeAll(notFoundList);
                dto.setSucc(queryList);

                break;
            }
            case trackingNo: {
                String batchStr = Arrays.stream(content.split("[\\t \\n]+")).filter(s -> s.length() > 0).distinct().collect(Collectors.joining(", "));
                List<String> queryList = Arrays.stream(content.split("[\\t \\n]+")).filter(s -> s.length() > 0).distinct().collect(Collectors.toList());
                param.setTrackingNoBatch(batchStr);
                List<OrderDO> queryResultList = ordersMapper.findBySearchParam(param);
                List<String> foundList = queryResultList.stream().map(o -> o.getTrackingNo()).collect(Collectors.toList());
                //过滤没有找到的运单编号
                List<String> notFoundList = queryList.stream().filter(o -> !foundList.contains(o)).collect(Collectors.toList());
                dto.setSucc(foundList);
                dto.setFail(notFoundList);
                break;
            }
            default:
                throw new RuntimeException("未知操作");

        }

        return dto;
    }

    /**
     * 销售订单出库接口
     *
     * @param result_data
     * @return
     */
    public RestCallBackResult orderWmsOut(String result_data, String key) {


        RestCallBackResult result = new RestCallBackResult();
        OrderOutDTO orderOutDTO = new OrderOutDTO();
        String message = "";
        try {
            orderOutDTO = JsonUtil.readValue(result_data, orderOutDTO.getClass());
        }catch (Exception e){
            message = "参数格式错误";
            result.setBody(message);
            result.setIsSuccess(RestCallBackResult.FAIL);
            return result;
        }




        if (StringUtils.isEmpty(key)) {
            message = "秘钥不能为空";
            result.setBody(message);
            result.setIsSuccess(RestCallBackResult.FAIL);
            return result;
        }
        if (StringUtils.isNotEmpty(key) && !key.equals(secretKey)) {
            message = "秘钥不匹配";
            result.setBody(message);
            result.setIsSuccess(RestCallBackResult.FAIL);
            return result;
        }
        Orders orders = ordersMapper.getOrderByOrderNo(orderOutDTO.getOrderNo());
        if (orders == null) {
            message = "订单编码不存在";
            result.setBody(message);
            result.setIsSuccess(RestCallBackResult.FAIL);
            return result;
        }

        //记录请求信息
        Integer recordDetailId = ordersWebInterfaceRecordService.addOrUpdateRequest(InterfaceTypeEnum.orders, InterfaceNameEnum.saleOutCallback,orders.getId(),"/orders/wms/api/order_out","result_data="+result_data+"&key="+key);
        result.setRecordDetailId(recordDetailId);



        Integer status = orderOutDTO.getOrderStatus();
        if (status == null || (status != null && status != 8)) {
            message = "订单状态不正确";
            result.setBody(message);
            result.setIsSuccess(RestCallBackResult.FAIL);
            return result;
        }
        List<OrderOutItemDTO> itemsDTOs = orderOutDTO.getData();
        if (CollectionUtils.isNullOrEmpty(itemsDTOs)) {
            message = "订单项数据不能为空";
            result.setBody(message);
            result.setIsSuccess(RestCallBackResult.FAIL);
            return result;
        }
        List<OrdersItem> orderItems = ordersItemsMapper.getByOrderId(orders.getId());
        if (orderItems.size() != itemsDTOs.size()) {
            message = "订单项数据条目数不匹配";
            result.setBody(message);
            result.setIsSuccess(RestCallBackResult.FAIL);
            return result;
        }
        /*for (OrderOutItemDTO itemsDTO : itemsDTOs) {
        Boolean isOk = false;
            for (OrdersItem item : orderItems) {
                if ((itemsDTO.getSku().equals(item.getSku())) && itemsDTO.getQuanitay() == item.getQty()) {
                    isOk = true;
                    break;
                } else {
                    isOk = false;
                }
            }
        }*/
            //ordersMapper.updateOrderStatus(order.
            //
            // ..s.getId(),status);
            orders.setTrackingNo(orderOutDTO.getShippingNo());
            orders.setDeliveryTime(LocalDateTime.now());
            ordersFsmProxyService.processEvent(orders, OrderEventEnum.wmsOutCallback, "wms出库回调接口成功");
            //2,发送一个订单状态改变消息到建站系统； 放到状态机的after事件里面做
            /*OrderStateModel orderStatusChangeMessage = new OrderStateModel();
            orderStatusChangeMessage.setOrder_no(orders.getMerchantOrderNo());
            *//**订单状态 0 待审*******************//*
            orderStatusChangeMessage.setStatus(orders.getOrderStatusEnum().ordinal());
            *//**运单号*******************//*
            orderStatusChangeMessage.setTrack_number(orders.getTrackingNo());
            *//**物流公司名称*******************//*
            orderStatusChangeMessage.setLogistics_name(orders.getLogisticsName());
            *//**运单产生时间*******************//*
            orderStatusChangeMessage.setTrack_time(orders.getStateTime().toEpochSecond(ZoneOffset.UTC));
            *//**备注*******************//*
            orderStatusChangeMessage.setRemark(orders.getMemo());
            rabbitMQPublisher.saveMessage(OrderStateModel.messageType, RabbitMQConfig.method_insert, orderStatusChangeMessage);*/


        return result;
    }

    /**
     * 根据建站域名查询建站的商品
     *
     * @param webDirectory
     * @return
     */
    public List<Product> findProductByWebUrl(String webDirectory) {
       /* List<Product> products = new ArrayList<Product>();
        Product product = new Product();
        product.setId(123513);
        product.setSpu("S0087788");
        product.setTitle("测试商品");
        product.setMainImageUrl("http://img-test.stosz.com//productNew/2017/11/201711200218240204.jpg");
        List<ProductSku> sus = new ArrayList<ProductSku>();
        ProductSku productSku = new ProductSku();
        productSku.setSku("S008785900001");
        //productSku.setAttributeValueTitle("白色-XL-小");
        sus.add(productSku);
        if(sus.size()==1){
            product.setSku(sus.get(0).getSku());
        }
        product.setSkuList(sus);
        products.add(product);
        return products;*/
        List<Product> products = productService.findByWebDirectory(webDirectory);
        Optional.ofNullable(products).ifPresent(orderItems ->orderItems.stream().forEach(e->{
                                    List<ProductSku> skuList= e.getSkuList()==null?new ArrayList<ProductSku>():e.getSkuList();
                                    if(skuList.size()==1){
                                        e.setSku(skuList.get(0).getSku());
                                    }
                                    e.setMainImageUrl(getProjectErpImageHttpPrefix()+"/"+e.getMainImageUrl());
                                }
                        )
                );
        return products;
    }

    /**
     * 根据订单查询状态机历史记录
     *
     * @param id
     * @return
     */
    public RestResult queryHistory(Integer id, Integer start, Integer limit) {
        return orderFsmHistoryService.queryHistory("Orders", id, start, limit);
    }

    public Integer updateOrderWarehouseInfo(Integer warehouseId, String warehouseName, WarehouseTypeEnum warehouseTypeEnum, String warehouseMemo, String wmsSysCode, Integer orderId) {
        wmsSysCode = Optional.ofNullable(wmsSysCode).orElse("");
        return ordersMapper.updateOrderWarehouseInfo(warehouseId, warehouseName, warehouseTypeEnum, warehouseMemo,wmsSysCode, orderId);
    }

    public List<DeptAssignQtyDto> findSkuNeedDetail(Set<Integer> zoneIdSet, String sku, HashSet<OrderStateEnum> orderStateEnums, Set<Integer> deptSet) {
        return ordersMapper.findSkuNeedDetail(zoneIdSet, sku, orderStateEnums, deptSet);
    }

    /**
     * 更新订单的运单号等信息
     *
     * @param transportRequest
     * @return
     */
    public TransportResponse updateOrderLogisticsInfo(TransportRequest transportRequest) {
        TransportResponse transportResponse = new TransportResponse();
        try {
            transportRequest.setTrackingNo(StringUtils.isEmpty(transportRequest.getTrackingNo())?"":transportRequest.getTrackingNo());
            ordersMapper.updateOrderLogisticsInfo(transportRequest);
            transportResponse.setCode(TransportResponse.SUCCESS);
            return transportResponse;
        } catch (Exception e) {
            transportResponse.setCode(TransportResponse.FAIL);
            transportResponse.setErrorMsg("运单会写失败，失败原因:" + e.getMessage());
            return transportResponse;
        }
    }

    /**
     * 根据运单号查询skuList
     *
     * @param trackingNos
     * @return
     */
    public List<OrdersItem> queryOrdersItems(List<String> trackingNos) {
        return ordersItemsMapper.queryOrdersItems(trackingNos);
    }

    /**
     * 根据订单号去拿订单信息
     *
     * @param ids
     * @return
     */
    public List<TransitOrderDTO> queryOrdersByOrderIds(List<String> ids) {
        logger.info("请求参数:"+ids);
        List<OrderDO> list = ordersMapper.queryOrdersByOrderIds(ids);
        Assert.notNull(list,"无数据");
        //获取订单行信息
        List<Integer> orderIds = list.stream().map(o -> o.getId()).collect(Collectors.toList());
        List<OrdersItem> ois = ordersItemsMapper.findByOrderIds(orderIds);
        //<orderId,List<OrdersItem>>
        Map<Long, List<OrdersItem>> orderItemsMap = ois.stream().collect(groupingBy(OrdersItem::getOrdersId));
        //订单列表页，标题展示
        list.stream().forEach(e -> {
            List<OrdersItem> ordersItems = Optional.ofNullable(orderItemsMap.get(Long.valueOf(e.getId()))).orElse(Lists.newLinkedList());
            //<spu, List<OrdersItem>>
            Map<String, List<OrdersItem>> spuMap = ordersItems.stream().collect(groupingBy(OrdersItem::getSpu));
            List<OrderItemSpuDTO> orderItemSpuDTOList = new ArrayList<>();
            for (String spu : spuMap.keySet()) {
                List<OrdersItem> oi = spuMap.get(spu);
                List<OrderItemSpuDTO.OrderItemSku> skuList = new ArrayList<>();
                if (oi != null && oi.size() != 0) {
                    for (OrdersItem o : oi) {
                        OrderItemSpuDTO.OrderItemSku sku = new OrderItemSpuDTO.OrderItemSku();
                        sku.setQty(o.getQty());
                        sku.setAttr(o.getAttrTitleArray());
                        sku.setSku(o.getSku());
                        skuList.add(sku);
                    }
                    orderItemSpuDTOList.add(new OrderItemSpuDTO(spu, oi.get(0).getProductTitle(),oi.get(0).getForeignTitle(),oi.get(0).getInnerTitle(), skuList));
                }
            }
            e.setSpuList(orderItemSpuDTOList);
        });
        return convertToTransitOrderDTO(list);
    }

    public List<TransitOrderDTO> convertToTransitOrderDTO(List<OrderDO> list) {
        List<TransitOrderDTO> transitOrders = new ArrayList<TransitOrderDTO>();
        list.forEach(e -> {
            TransitOrderDTO transitOrderDTO = new TransitOrderDTO();
            transitOrderDTO.setOrderId(e.getId().longValue());
            transitOrderDTO.setDeliveryTime(e.getDeliveryTime());
            transitOrderDTO.setAddress(e.getAddress());
            transitOrderDTO.setArea(e.getArea());
            transitOrderDTO.setProvince(e.getProvice());
            transitOrderDTO.setCity(e.getCity());
            transitOrderDTO.setCustomerMessage(e.getCustomerMessage());
            transitOrderDTO.setCustomerName(e.getLastName() + e.getFirstName());
            transitOrderDTO.setGoodQty(e.getGoodsQty());
            transitOrderDTO.setLogisticsName(e.getLogisticsName());
            transitOrderDTO.setMobile(e.getTelphone());
            transitOrderDTO.setOrderAmount(e.getOrderAmount());
            transitOrderDTO.setSpuList(e.getSpuList());
            transitOrderDTO.setZipCode(e.getZipcode());
            transitOrderDTO.setTrackingStatusShow(e.getTrackingStatusShow());
            transitOrderDTO.setZoneName(e.getZoneName());
            transitOrders.add(transitOrderDTO);
        });
        return transitOrders;
    }

    public List<TransitStockDTO> queryOrdersByTrackingNos(List<String> trackingNos) {
        return ordersMapper.queryOrdersByTrackingNos(trackingNos);
    }

    /**
     * 进行转寄状态的回滚
     *
     * @param orderId
     * @param status
     */
    public void transferCancel(Long orderId, OrderEventEnum status) {
        Orders orders = ordersMapper.getById(orderId.intValue());
        ordersFsmProxyService.processEvent(orders, status, "转寄盘点缺货");
    }

    public List<DeptOrderQtyTimeDto> findOrderSkuQtyDetail(Set<Integer> zoneIdSet, String sku, HashSet<OrderStateEnum> orderStateEnums, Set<Integer> deptSet) {
        return ordersMapper.findOrderSkuQtyDetail(zoneIdSet, sku, orderStateEnums,deptSet);
    }

    public boolean updateTmsInfo(Integer shippingWayId, String shippingName, String trackingNo, Integer orderId) {
        return Optional.ofNullable(ordersMapper.updateTmsInfo(shippingWayId, shippingName, trackingNo, orderId)).orElse(0) > 0;
    }

    public OrdersAddition getOrderAddition(Integer ordersId) {
        return Optional.ofNullable(ordersMapper.getOrderAddition(ordersId)).orElse(new OrdersAddition());
    }
    /**
     * 转换成订单的item
     * @param products
     * ..
     *
     *
     * @return
     */
    /*public List<OrdersItem> convertToOrdersItem(List<Product> products){
        List<OrdersItem> ordersItems = new ArrayList<OrdersItem>();
        products.forEach(e->{
            OrdersItem ordersItem = new OrdersItem();
            ordersItem.setSpu(e.getSpu());
            List<ProductSku> productSkus = e.getSkuList();
            List<String> skus = new ArrayList<String>();
            productSkus.forEach(m->{
                skus.add(m.getAttributeValueTitle());
            });
            ordersItem.setAttrList(skus);
            ordersItems.add(ordersItem);
        });
        return ordersItems;
    }*/

}
