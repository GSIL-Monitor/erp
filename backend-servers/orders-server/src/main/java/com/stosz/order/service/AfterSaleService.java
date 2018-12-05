package com.stosz.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.stosz.admin.ext.enums.JobAuthorityRelEnum;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.JobAuthorityRel;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IJobAuthorityRelService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.dto.*;
import com.stosz.order.ext.enums.*;
import com.stosz.order.ext.model.*;
import com.stosz.order.mapper.order.*;
import com.stosz.order.util.ExcelUtils;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.Currency;
import com.stosz.product.ext.service.ICurrencyService;
import com.stosz.store.ext.service.ITransitStockService;
import com.stosz.tms.service.IShippingWayStoreService;
import com.stosz.tms.vo.StoreInfoResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class AfterSaleService extends OrderBaseService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private OrdersRmaBillMapper ordersRmaBillMapper;

    @Autowired
    private OrdersItemsMapper ordersItemsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersAdditionMapper ordersAdditionMapper;

    @Autowired
    private OrdersRmaBillItemMapper ordersRmaBillItemMapper;

    @Resource
    private ICurrencyService currencyService;

    @Autowired
    private OrdersLinkMapper ordersLinkMapper;

    @Autowired
    private FsmProxyService<OrdersRmaBill> orderChangeFsmProxyService;
    @Resource
    private ITransitStockService transitStockService;
    @Autowired
    private OrdersRmaBillItemsService ordersRmaBillItemsService;
    @Autowired
    private OrdersRefundService ordersRefundService;
    @Autowired
    private UserZoneService userZoneService;
    @Resource
    private IDepartmentService departmentService;
    @Resource
    private IJobAuthorityRelService jobAuthorityRelService;
    @Resource
    private IShippingWayStoreService shippingWayStoreService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * todo
     *
     * @param orderId
     * @param type
     */
    public void applyCheck(Integer orderId, String type) {
//        OrdersRmaBill ordersRmaBill = apply(param, ChangeTypeEnum.reject);
//        OrdersRmaStateEnum newRmaState = OrdersRmaStateEnum.fromName(param.getRmaState());
//        switch(newRmaState){
//            case auditPass:
//                orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.createReject, "");
//                break;
//            default:
//                throw new RuntimeException("参数异常，不支持该操作");
//        }
    }


    /**
     * @param param
     * @return
     * @author wangqian
     * @date 2017-11-28
     * 拒收
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void applyReject(OrderChangeApplyParam param) {
        OrdersRmaBill ordersRmaBill = apply(param, ChangeTypeEnum.reject);
        OrdersRmaStateEnum newRmaState = OrdersRmaStateEnum.fromName(param.getRmaState());
        String memo = param.getApplyMemo() == null ? "" : param.getApplyMemo();
        switch (newRmaState) {
            case auditPass:
                orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.createReject, memo);
                break;
            default:
                throw new RuntimeException("参数异常，不支持该操作");
        }
    }


    /**
     * @param param
     * @return
     * @author wangqian
     * @date 2017-11-28
     * 申请退货
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void applyReturn(OrderChangeApplyParam param) {
        OrdersRmaBill ordersRmaBill = apply(param, ChangeTypeEnum.returned);
        OrdersRmaStateEnum newRmaState = OrdersRmaStateEnum.fromName(param.getRmaState());
        String memo = param.getApplyMemo() == null ? "" : param.getApplyMemo();
        switch (newRmaState) {
            case draft:
                orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.create, memo);
                break;
            case waitAudit:
                orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.submitAudit, memo);
                break;
            default:
                throw new RuntimeException("参数异常，不支持该操作");
        }
    }


    /**
     * 更新
     *
     * @param param
     * @param param
     * @author wangqian
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void update(OrderChangeApplyParam param) {

        UserDto userDto = ThreadLocalUtils.getUser();

        OrdersRmaBill bill = ordersRmaBillMapper.getById(param.getRmaId());
        Assert.notNull(bill, "数据异常，找不到申请单信息");

        List<OrdersRmaBillItem> billItemList = ordersRmaBillItemMapper.getOrdersRmaItemByRmaId(param.getRmaId());
        Assert.notNull(billItemList, "数据异常，找不到申请单项信息");

        Orders orders = ordersMapper.getById(param.getOrderId());
        Assert.notNull(orders, "数据异常，找不到订单信息");


        boolean isOk = (bill.getRmaStatusEnum().equals(OrdersRmaStateEnum.draft) || bill.getRmaStatusEnum().equals(OrdersRmaStateEnum.reject));
        Assert.isTrue(isOk, "当前状态无法修改");

        applyCheck(orders, param);

        String str = createUpdateInfo(bill, billItemList, param);

        //修改申请单
        ordersRmaBillMapper.update(bill);

        //修改申请单项
        billItemList.stream().forEach(e -> {
            e.setOrdersRmaBillId(-1);
            ordersRmaBillItemMapper.update(e);
        });

        //写入申请单项
        List<OrdersItem> ois = ordersItemsMapper.getByOrderId(orders.getId());
        insertRmaBillItem(param, ois, bill, userDto);

        //只有退货申请时才支持修改操作
        OrdersRmaStateEnum newRmaState = OrdersRmaStateEnum.fromName(param.getRmaState());
        switch (newRmaState) {
            case draft:
                orderChangeFsmProxyService.processEvent(bill, OrderRmaBillEventEnum.update, str);
                break;
            case waitAudit:
                orderChangeFsmProxyService.processEvent(bill, OrderRmaBillEventEnum.submitAudit, str);
                break;
            default:
                throw new RuntimeException("参数异常，不支持该操作");
        }
    }


    /**
     * @param bill
     * @param billItemList
     * @param param
     * @return
     */
    private String createUpdateInfo(OrdersRmaBill bill, List<OrdersRmaBillItem> billItemList, OrderChangeApplyParam param) {
        StringBuilder sb = new StringBuilder();

        //处理图片
        if (param.getImgs() != null) {
            try {
                bill.setEvidenceImageJsonArray(new ObjectMapper().writeValueAsString(param.getImgs()));
            } catch (JsonProcessingException e) {
                logger.error("申请单图片地址解析错误: {}", param);
            }
        }

        if (org.springframework.util.StringUtils.hasText(param.getApplyMemo())) {
            sb.append("[备注:").append(param.getApplyMemo()).append("],");
        }

        ChangeReasonEnum newChangeReason = ChangeReasonEnum.fromName(param.getChangeReason());
        if (!bill.getChangeReasonEnum().equals(newChangeReason)) {
            sb.append("[换货原因:" + bill.getChangeReasonEnum().display() + "->" + newChangeReason.display() + "],");
            bill.setChangeReasonEnum(newChangeReason);
        }
        RmaSourceEnum newRmaSource = RmaSourceEnum.fromName(param.getRmaSource());
        if (!bill.getRmaSourceEnum().equals(newRmaSource)) {
            sb.append("[售后来源:" + bill.getRmaSourceEnum().display() + "->" + newRmaSource.display() + "],");
            bill.setRmaSourceEnum(newRmaSource);
        }
        RecycleGoodsEnum newRecycleGoods = RecycleGoodsEnum.valueOf(param.getRecycleGoods());
        if (!bill.getRecycleGoodsEnum().equals(newRecycleGoods)) {
            sb.append("[是否回收:" + bill.getRecycleGoodsEnum().display() + "->" + newRecycleGoods.display() + "],");
            bill.setRecycleGoodsEnum(newRecycleGoods);
        }
        OrdersRmaStateEnum newRmaState = OrdersRmaStateEnum.fromName(param.getRmaState());
        if (!bill.getRmaStatusEnum().equals(newRmaState)) {
            sb.append("[状态:" + bill.getRmaStatusEnum().display() + "->" + newRmaState.display() + "],");
//            bill.setRmaStatusEnum(newRmaState);
        }

        if (!bill.getChangeReasonOtherMemo().equals(param.getChangeReasonOtherMemo())) {
            sb.append("[其它原因由:" + bill.getChangeReasonOtherMemo() + "->" + param.getChangeReasonOtherMemo() + "],");
            bill.setChangeReasonOtherMemo(param.getChangeReasonOtherMemo());
        }
        if (!bill.getQuestionMemo().equals(param.getQuestionMemo())) {
            sb.append("[问题备注:" + bill.getQuestionMemo() + "->" + param.getQuestionMemo() + "],");
            bill.setQuestionMemo(param.getQuestionMemo());
        }
//        if (!bill.getApplyMemo().equals(param.getApplyMemo())) {
//            sb.append("申请备注[" + bill.getApplyMemo() + "]" + "修改成" + "[" + param.getApplyMemo() + "]");
//            bill.setApplyMemo(param.getQuestionMemo());
//        }
        if (!bill.getRefundAmount().equals(param.getAmount())) {
            sb.append("[退款金额" + bill.getRefundAmount() + "->" + param.getAmount() + "],");
            bill.setRefundAmount(param.getAmount());
        }
        if (!bill.getTrackingNo().equals(param.getTrackingNo())) {
            sb.append("运单号[" + bill.getTrackingNo() + "->" + param.getTrackingNo() + "],");
            bill.setTrackingNo(param.getTrackingNo());
        }

        //判断是否被修改过
        List<OrdersRmaBillItem> rmaBillItemList = billItemList.stream()
                .sorted(new Comparator<OrdersRmaBillItem>() {
                    @Override
                    public int compare(OrdersRmaBillItem o1, OrdersRmaBillItem o2) {
                        return o1.getOrdersItemId() - o2.getOrdersItemId();
                    }
                })
                .collect(Collectors.toList());

        List<OrderChangeApplyParam.OrderItemQty> orderItemQtyList = param.getOrderItemQtyList().stream()
                .sorted(new Comparator<OrderChangeApplyParam.OrderItemQty>() {
                    @Override
                    public int compare(OrderChangeApplyParam.OrderItemQty o1, OrderChangeApplyParam.OrderItemQty o2) {
                        return o1.getOrderItemId() - o2.getOrderItemId();
                    }
                })
                .collect(Collectors.toList());

        boolean itemQtyChange = false;
        if (rmaBillItemList.size() != orderItemQtyList.size()) {
            itemQtyChange = true;
        } else {
            for (int i = 0; i < orderItemQtyList.size(); i++) {
                if (!orderItemQtyList.get(i).getOrderItemId().equals(rmaBillItemList.get(i).getOrdersItemId()) || !orderItemQtyList.get(i).getQty().equals(rmaBillItemList.get(i).getOrdersItemApplyQty())) {
                    itemQtyChange = true;
                    break;
                }
            }
        }

        if (itemQtyChange == true) {
            sb.append("[订单项:");
            rmaBillItemList.stream().forEach(e -> {
                String srcStr = "(" + e.getOrdersItemId() + "*" + e.getOrdersItemApplyQty() + ")";
                sb.append(srcStr);
            });
            sb.append("->");
            orderItemQtyList.stream().forEach(e -> {
                String dstStr = "(" + e.getOrderItemId() + "*" + e.getQty() + ")";
                sb.append(dstStr);
            });
            sb.append("],");

        }
        return sb.toString();

    }


    /**
     * 退换货申请
     * 退货、拒收申请
     * <p>
     * 拒收与退货两种状态不能同时存在
     * 拒收只能申请一次
     * 退货可以申请多次
     *
     * @param param
     * @param type  操作类型
     * @author wangqian
     * @date 2017-11-28
     */
    private OrdersRmaBill apply(OrderChangeApplyParam param, ChangeTypeEnum type) {
        Orders orders = ordersMapper.getById(param.getOrderId());
        Assert.notNull(orders, "数据异常，找不到订单信息");

        OrdersAddition addition = ordersAdditionMapper.getByOrderId(param.getOrderId());
        Assert.notNull(addition, "数据异常，找不到订单项");

        // TODO: 2018/1/20  wangqian  此流程無法走通
        StoreInfoResponseVo storeVo = new StoreInfoResponseVo();
        storeVo.setId(11);
        storeVo.setName("香港葵兴仓");
//        StoreInfoResponseVo storeVo = shippingWayStoreService.findBackStore(orders.getLogisticsId());
//        Assert.notNull(storeVo, "无法获取到转寄仓，请联系物流管理员");

        applyCheck(orders, param);

        param.setChangeReasonOtherMemo(ofNullable(param.getChangeReasonOtherMemo()).orElse(""));
        Currency currency = ofNullable(currencyService.getByCurrencyCode(orders.getCurrencyCode())).orElse(new Currency());


        currency.setSymbolLeft(currency.getSymbolLeft() == null ? "" : currency.getSymbolLeft());
        currency.setSymbolRight(currency.getSymbolRight() == null ? "" : currency.getSymbolRight());
        UserDto userDto = ThreadLocalUtils.getUser();
        OrdersRmaBill ordersRmaBill = new OrdersRmaBill();
        ordersRmaBill.setMerchantOrderNo(orders.getMerchantOrderNo());
        ordersRmaBill.setTrackingNo(param.getTrackingNo());
        ordersRmaBill.setOrderTrackingNo(orders.getTrackingNo() == null ? "" : orders.getTrackingNo());
        ordersRmaBill.setLogisticsId(orders.getLogisticsId());
        ordersRmaBill.setLogisticName(orders.getLogisticsName());
        ordersRmaBill.setSeoDepartmentId(orders.getSeoDepartmentId());
        ordersRmaBill.setDepartmentUserInfo(orders.getDepartmentUserInfo());
        ordersRmaBill.setWebUrl(addition.getWebUrl());
        if (param.getImgs() != null) {
            try {
                ordersRmaBill.setEvidenceImageJsonArray(new ObjectMapper().writeValueAsString(param.getImgs()));
            } catch (JsonProcessingException e) {
                logger.error("申请单图片地址解析错误: {}", param);
            }
        }
        ordersRmaBill.setChangeTypeEnum(type);
        ordersRmaBill.setChangeReasonEnum(ChangeReasonEnum.fromName(param.getChangeReason()));
        ordersRmaBill.setChangeReasonOtherMemo(param.getChangeReasonOtherMemo());
        ordersRmaBill.setRmaStatusEnum(OrdersRmaStateEnum.start);
        ordersRmaBill.setPayMethodEnum(PayMethodEnum.fromId(orders.getPaymentMethod()));
        BigDecimal refoundAmount = new BigDecimal(0);
        if (ChangeTypeEnum.valueOf(param.getApplyType()) == ChangeTypeEnum.returned) {
            refoundAmount = param.getAmount();
        } else {
            if (PayMethodEnum.onlinePay.equals(PayMethodEnum.fromId(orders.getPaymentMethod()))) {
                //在线支付付款金额为订单金额
                refoundAmount = orders.getConfirmAmount() == null ? new BigDecimal(0) : orders.getConfirmAmount();
            } else {
                //货到付款金额为0
                refoundAmount = new BigDecimal(0);
            }
            param.setAmount(new BigDecimal(0));
        }
        ordersRmaBill.setRefundAmount(refoundAmount);
        ordersRmaBill.setOrderAmount(orders.getOrderAmount());
        ordersRmaBill.setCurrencyName(currency.getName());
        ordersRmaBill.setLeftSymbol(currency.getSymbolLeft());
        ordersRmaBill.setRightSymbol(currency.getSymbolRight());
        String amountShow = currency.getSymbolLeft() + param.getAmount() + currency.getSymbolRight();
        ordersRmaBill.setRefundAmountShow(amountShow);
        ordersRmaBill.setChangeWayEnum(PayMethodEnum.fromId(orders.getPaymentMethod()) == PayMethodEnum.cod ? ChangeWayEnum.logisticsBack : ChangeWayEnum.noSetting);
        ordersRmaBill.setRecycleGoodsEnum(RecycleGoodsEnum.valueOf(param.getRecycleGoods()));
        ordersRmaBill.setRmaSourceEnum(RmaSourceEnum.valueOf(param.getRmaSource()));
        ordersRmaBill.setApplyUserId(userDto.getId());
        ordersRmaBill.setApplyUserName(userDto.getLastName());
        ordersRmaBill.setApplyTime(LocalDateTime.now());
        ordersRmaBill.setApplyMemo(param.getApplyMemo());
        ordersRmaBill.setQuestionMemo(param.getQuestionMemo());
        ordersRmaBill.setCheckUserId(0);
        ordersRmaBill.setCheckUserName("");
        ordersRmaBill.setWarehouseId(storeVo.getId());
        ordersRmaBill.setWarehouseName(storeVo.getName());
        ordersRmaBill.setCheckMemo("");
        ordersRmaBill.setOrdersId(Long.valueOf(orders.getId()));
        ordersRmaBill.setChangeAt(LocalDateTime.now());
        ordersRmaBill.setCreateAt(LocalDateTime.now());
        ordersRmaBill.setCreatorId(userDto.getId());
        ordersRmaBill.setCreator(userDto.getLastName());
        ordersRmaBill.setZoneId(orders.getZoneId());
        ordersRmaBill.setRefundLogisticsId(orders.getLogisticsId());
        ordersRmaBill.setRefundLogisticName(orders.getLogisticsName());
        ordersRmaBillMapper.insert(ordersRmaBill);


        List<OrdersItem> ois = ordersItemsMapper.getByOrderId(orders.getId());
        insertRmaBillItem(param, ois, ordersRmaBill, userDto);


        return ordersRmaBill;
    }


    /**
     * 写入申请单项
     *
     * @param param
     * @param ordersItemList
     * @param ordersRmaBill
     * @param userDto
     */
    private void insertRmaBillItem(OrderChangeApplyParam param, List<OrdersItem> ordersItemList, OrdersRmaBill ordersRmaBill, UserDto userDto) {
        for (OrderChangeApplyParam.OrderItemQty oiq : param.getOrderItemQtyList()) {
            if (oiq.getQty() < 1) {//过滤掉申请数量小于1的
                continue;
            }
            OrdersRmaBillItem OrdersRmaBillItem = new OrdersRmaBillItem();
            Optional<OrdersItem> ordersItem = ordersItemList.stream().filter(oi -> oi.getId().equals(oiq.getOrderItemId())).findFirst();
            ordersItem.ifPresent(i -> {
                OrdersRmaBillItem.setOrdersRmaBillId(ordersRmaBill.getId());
                OrdersRmaBillItem.setSpu(i.getSpu());
                OrdersRmaBillItem.setSku(i.getSku());
                OrdersRmaBillItem.setAttrTitleArray(i.getAttrTitleArray());
                OrdersRmaBillItem.setProductId(i.getProductId().intValue());
                OrdersRmaBillItem.setProductTitle(i.getProductTitle());
                OrdersRmaBillItem.setProductImgUrl(i.getProductImageUrl());
                OrdersRmaBillItem.setOrdersItemId(i.getId());
                OrdersRmaBillItem.setOrdersItemQty(i.getQty());
                OrdersRmaBillItem.setOrdersItemReturnQty(i.getQty());
                OrdersRmaBillItem.setOrdersItemApplyQty(oiq.getQty());
                OrdersRmaBillItem.setSingleAmount(i.getSingleAmount());
                OrdersRmaBillItem.setTotalAmount(i.getTotalAmount());
                OrdersRmaBillItem.setDetectionResult("");
                OrdersRmaBillItem.setStorageLocation("");
                OrdersRmaBillItem.setInStorageNo("");
                OrdersRmaBillItem.setInQty(0);
                OrdersRmaBillItem.setCreateAt(LocalDateTime.now());
                OrdersRmaBillItem.setCreatorId(userDto.getId());
                OrdersRmaBillItem.setCreator(userDto.getLastName());
            });
            ordersRmaBillItemMapper.insert(OrdersRmaBillItem);
        }
    }


    /**
     * 申请校验
     *
     * @param orders
     * @param param
     */
    private void applyCheck(Orders orders, OrderChangeApplyParam param) {

        List<OrdersRmaBill> ordersRmaBillList = ordersRmaBillMapper.findOrdersRmaBillByOrderIdAndNotInState(orders.getId(), OrdersRmaStateEnum.cancel.ordinal());

        //该订单已有拒收申请 则无法申请
        ordersRmaBillList.stream().forEach(e -> {
            if (param.getRmaId() == null) {
                //创建申请单
                if (ChangeTypeEnum.reject.equals(e.getChangeTypeEnum())) {
                    throw new RuntimeException("该订单已有拒收申请");
                }
            } else {
                //编辑申请单
                if (ChangeTypeEnum.reject.equals(e.getChangeTypeEnum()) && !param.getRmaId().equals(e.getId())) {
                    throw new RuntimeException("该订单已有拒收申请");
                }
            }

        });
        ChangeTypeEnum type = ChangeTypeEnum.fromName(param.getApplyType());

        /**
         * 退款金额校验
         * 1、申请退货时，要扣除已申请的退款金额
         * 2、申请拒收时，退款金额为0
         */
        if (type.equals(ChangeTypeEnum.returned)) {
            //已申请退款订单金额（不包括当前申请单）
            BigDecimal sum = ordersRmaBillList.stream().filter(a -> a.getId() != param.getRmaId()).map(e -> e.getRefundAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
            //当前申请单+已申请退款订单金额
            BigDecimal total = sum.add(param.getAmount());
            if (total.subtract(orders.getConfirmAmount()).compareTo(BigDecimal.ZERO) > 0) {
                throw new RuntimeException("退款金额不能大于订单未退金额（未退金额含待提交及待退的金额）");
            }
        } else {//拒收
            if (param.getAmount().subtract(orders.getConfirmAmount()).compareTo(BigDecimal.ZERO) > 0) {
                throw new RuntimeException("退款金额不能大于订单未退金额（未退金额含待提交及待退的金额）");
            }
        }
    }


    /**
     * 查看退换货详情
     *
     * @param rmaId 退换货编号
     * @return
     */
    public RmaDetailDto findDetailByRmaBillId(Integer rmaId) {
        RmaDetailDto detailDto = new RmaDetailDto();
        OrdersRmaBill bill = ordersRmaBillMapper.getById(rmaId);
        Assert.notNull(bill, "数据异常，请联系管理员");
        Assert.notNull(bill, "申请单数据异常，请联系管理员");
        List<OrdersRmaBillItem> items = ordersRmaBillItemMapper.findByRmaId(rmaId);
        Assert.notNull(items, "数据异常，请联系管理员");
        Assert.notNull(items, "申请单项数据异常，请联系管理员");

        Orders orders = ordersMapper.getById(bill.getOrdersId().intValue());
        Assert.notNull(orders, "订单项数据异常，请联系管理员");

        RmaDetailDto.RmaInfo rmaInfo = new RmaDetailDto.RmaInfo();
        List<RmaDetailDto.RmaItemInfo> rmaItemInfoList = new ArrayList<>();


        rmaInfo.setRmaId(rmaId);

        rmaInfo.setConfirmAmount(orders.getConfirmAmount());
        rmaInfo.setOrderAmount(orders.getOrderAmount());

        rmaInfo.setOrderId(bill.getOrdersId().intValue());
        rmaInfo.setPurchaseAt(bill.getPurchaseAt());
        rmaInfo.setOrderAmount(bill.getOrderAmount());
//        rmaInfo.setOrderAmountShow(bill.getOrderAmount());
        rmaInfo.setLeftSymbol(bill.getLeftSymbol());
        rmaInfo.setRightSymbol(bill.getRightSymbol());
        rmaInfo.setPayMethod(bill.getPayMethodEnum());
        rmaInfo.setTrackingNo(bill.getTrackingNo());
        rmaInfo.setState(OrdersRmaStateEnum.fromName(bill.getState()));
        rmaInfo.setChangeType(bill.getChangeTypeEnum());
        rmaInfo.setRmaSource(bill.getRmaSourceEnum());
        rmaInfo.setRecycleGoods(bill.getRecycleGoodsEnum());
        rmaInfo.setChangeReason(bill.getChangeReasonEnum());
        rmaInfo.setChangeReasonOtherMemo(bill.getChangeReasonOtherMemo());
        rmaInfo.setQuestionMemo(bill.getQuestionMemo());
        rmaInfo.setChangeWay(bill.getChangeWayEnum());
        rmaInfo.setLogisticsName(bill.getLogisticName());
        rmaInfo.setApplyAmount(bill.getRefundAmount());
        rmaInfo.setApplyAccount(bill.getCustomerGetAccount());
        rmaInfo.setRmaStatus(bill.getRmaStatusEnum());
        rmaInfo.setApplyTime(bill.getApplyTime());
        rmaInfo.setOrderTrackingNo(bill.getOrderTrackingNo());
        rmaInfo.setId(bill.getId());
        rmaInfo.setRefundId(bill.getRefundId());
        rmaInfo.setCheckMemo(bill.getCheckMemo());

        //拒收
        if (ChangeTypeEnum.reject.equals(bill.getChangeTypeEnum())) {
            rmaInfo.setRecycleGoods(RecycleGoodsEnum.yes);
            rmaInfo.setChangeReason(ChangeReasonEnum.other);
        }


        String imgStr = bill.getEvidenceImageJsonArray();

        try {
            List<String> imgUrls = null;
            if (StringUtils.isNotBlank(imgStr)) {
                List<String> imgs = objectMapper.readValue(imgStr, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
                imgUrls = Optional.ofNullable(imgs).orElse(new ArrayList<>());
            }
            rmaInfo.setImgList(ofNullable(imgUrls).orElse(new ArrayList<>()));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        rmaInfo.setApplyMemo(bill.getApplyMemo());
        rmaInfo.setWebUrl(bill.getWebUrl());

        items.stream().forEach(e -> {
            RmaDetailDto.RmaItemInfo rmaItemInfo = new RmaDetailDto.RmaItemInfo();
            rmaItemInfo.setItemId(e.getOrdersItemId());
            rmaItemInfo.setSku(e.getSku());
            rmaItemInfo.setProductTitle(e.getProductTitle());
            rmaItemInfo.setOrdersItemQty(e.getOrdersItemQty());
            rmaItemInfo.setOrdersItemApplyQty(e.getOrdersItemApplyQty());
            rmaItemInfo.setOrdersItemReturnQty(e.getOrdersItemReturnQty());
            rmaItemInfo.setSingleAmountShow(e.getSingleAmount().toString());
            rmaItemInfo.setCurrencyName(bill.getCurrencyName());
            rmaItemInfo.setAttrTitleArray(e.getAttrTitleArray());
            rmaItemInfo.setInQty(e.getInQty());
            rmaItemInfo.setStorageLocation(e.getStorageLocation());
            rmaItemInfoList.add(rmaItemInfo);
        });

        detailDto.setRmaInfo(rmaInfo);
        detailDto.setRmaItemInfoList(rmaItemInfoList);

        return detailDto;
    }


    /**
     * 查看要退货的订单信息
     *
     * @param orderId 订单编号
     * @param rmaType 申请类型
     * @return
     */
    public RmaDetailDto findPreApplyInfoByOrderId(Integer orderId, String rmaType) {
        RmaDetailDto detailDto = new RmaDetailDto();
        Orders orders = ordersMapper.getById(orderId);
        Assert.notNull(orders, "订单数据异常，请联系管理员");
        List<OrdersItem> ordersItems = ordersItemsMapper.getByOrderId(orderId);
        Assert.notEmpty(ordersItems, "订单项数据，请联系管理员");
        OrdersLink ordersLink = ordersLinkMapper.getByOrderId(orderId);
        Assert.notNull(ordersLink, "订单联系人数据异常，请联系管理员");
        ChangeTypeEnum changeWay = ChangeTypeEnum.fromName(rmaType);
        Assert.notNull(changeWay, "非法请求");

        Currency currency = ofNullable(currencyService.getByCurrencyCode(orders.getCurrencyCode())).orElse(new Currency());
        currency.setSymbolLeft(currency.getSymbolLeft() == null ? "" : currency.getSymbolLeft());
        currency.setSymbolRight(currency.getSymbolRight() == null ? "" : currency.getSymbolRight());
        RmaDetailDto.RmaInfo rmaInfo = new RmaDetailDto.RmaInfo();
        rmaInfo.setOrderId(orderId);
        rmaInfo.setPurchaseAt(orders.getPurchaserAt());
        rmaInfo.setOrderAmount(orders.getOrderAmount());
        rmaInfo.setConfirmAmount(orders.getConfirmAmount());
        rmaInfo.setLeftSymbol(currency.getSymbolLeft());
        rmaInfo.setRightSymbol(currency.getSymbolRight());
        rmaInfo.setPayMethod(PayMethodEnum.fromId(orders.getPaymentMethod()));
        rmaInfo.setTrackingNo(orders.getTrackingNo());
        rmaInfo.setOrderTrackingNo(orders.getTrackingNo());
        rmaInfo.setWebUrl(orders.getOrderFrom());
        rmaInfo.setState(OrdersRmaStateEnum.start);
        rmaInfo.setChangeType(changeWay);

        //拒收
        if (changeWay.equals(ChangeTypeEnum.reject)) {
            rmaInfo.setRecycleGoods(RecycleGoodsEnum.yes);
            rmaInfo.setChangeReason(ChangeReasonEnum.other);
            if (PayMethodEnum.onlinePay.equals(PayMethodEnum.fromId(orders.getPaymentMethod()))) {
                //在线支付付款金额为订单金额
                rmaInfo.setApplyAmount(orders.getConfirmAmount() == null ? new BigDecimal(0) : orders.getConfirmAmount());
            } else {
                //货到付款金额为0
                rmaInfo.setApplyAmount(new BigDecimal(0));
            }
        } else {//退货

        }

        rmaInfo.setRecycleGoods(RecycleGoodsEnum.yes);
        rmaInfo.setRmaSource(RmaSourceEnum.system);
        rmaInfo.setQuestionMemo("");
        rmaInfo.setChangeWay(orders.getPaymentMethod() == PayMethodEnum.cod.ordinal() ? ChangeWayEnum.logisticsBack : ChangeWayEnum.noSetting);
        rmaInfo.setLogisticsName(orders.getLogisticsName());
        rmaInfo.setApplyAccount("");
        rmaInfo.setImgList(Lists.newArrayList());
        rmaInfo.setApplyMemo("");

        //退货申请项信息
        Map<Integer, OrderRmaItemReturnQty> countMap = countRmaItemReturnQty(orderId, null).stream().collect(Collectors.toMap(OrderRmaItemReturnQty::getId, Function.identity()));
        List<RmaDetailDto.RmaItemInfo> rmaItemInfoList = new ArrayList<>();
        ordersItems.stream().forEach(e -> {
            RmaDetailDto.RmaItemInfo rmaItemInfo = new RmaDetailDto.RmaItemInfo();
            rmaItemInfo.setItemId(e.getId());
            rmaItemInfo.setSku(e.getSku());
            rmaItemInfo.setProductTitle(e.getProductTitle());
            rmaItemInfo.setOrdersItemQty(e.getQty());
            rmaItemInfo.setOrdersItemReturnQty(countMap.get(e.getId()).getQty());
            rmaItemInfo.setOrdersItemApplyQty(countMap.get(e.getId()).getQty());
            rmaItemInfo.setSingleAmountShow(currency.getSymbolLeft() + e.getSingleAmount() + currency.getSymbolRight());
            rmaItemInfo.setCurrencyName(currency.getName());
            rmaItemInfoList.add(rmaItemInfo);
        });
        detailDto.setRmaInfo(rmaInfo);
        detailDto.setRmaItemInfoList(rmaItemInfoList);
        return detailDto;
    }


    /**
     * @param orderId
     * @return
     * @author wangqian
     * @date 2017-12-8
     */
    public List<OrdersRmaBill> findByOrderId(Integer orderId) {
        return ordersRmaBillMapper.findByOrderId(orderId);
    }

    /**
     * @param id
     * @param auditMemo
     * @param result
     * @author liushilei
     * 退货/拒收 审核
     */
    public RestResult approve(Integer id, String auditMemo, Boolean result) {
        Assert.notNull(id, "id不能为空");
        Assert.notNull(result, "审批结果不能为空");
        Assert.notNull(auditMemo, "审批备注不能为空");
        RestResult restResult = new RestResult();
        OrdersRmaBill ordersRmaBill = ordersRmaBillMapper.findOrdersChangeByOrderchangeId(id);
        ordersRmaBill.setCheckMemo(auditMemo);
        ordersRmaBill.setCheckUserId(MBox.getLoginUserId());
        ordersRmaBill.setCheckUserName(MBox.getLoginUserName());
        ordersRmaBill.setCheckTime(LocalDateTime.now());
        ordersRmaBill.setUpdateAt(LocalDateTime.now());
        String recycleGoods = ordersRmaBill.getRecycleGoodsEnum().name();
        try {
            if (result) {
                //商品是否需要寄回 需要寄回的则状态变为待收货，否则直接完成
                if (StringUtils.isNotBlank(recycleGoods) && recycleGoods.equals(RecycleGoodsEnum.no.name())) {
                    orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.changeAuditPassBut, OrderRmaBillEventEnum.changeAuditPassBut.display());
                } else if (StringUtils.isNotBlank(recycleGoods) && recycleGoods.equals(RecycleGoodsEnum.yes.name())) {
                    orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.changeAuditPass, OrderRmaBillEventEnum.changeAuditPass.display());
                }
            } else {
                orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.changeReject, OrderRmaBillEventEnum.changeReject.display());
            }
        } catch (Exception e) {
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("审核失败");
            throw new RuntimeException(e.getMessage());
        } finally {
            return restResult;
        }
    }

    /**
     * 通知转寄仓开始收货
     *
     * @param ordersRmaBill
     */
    public void notifyStockTakeGoods(OrdersRmaBill ordersRmaBill) {
        List<TransitStockDTO> transitStocks = new ArrayList<TransitStockDTO>();
        List<OrdersRmaBillItem> ordersRmaBillItems = ordersRmaBillItemsService.getOrdersRmaItemByRmaId(ordersRmaBill.getId());
        List<TransitItemDTO> transitItemList = Lists.newArrayList();
        ofNullable(ordersRmaBillItems).ifPresent(items -> {
            items.forEach(e -> {
                TransitItemDTO transitItemDTO = new TransitItemDTO();
                transitItemDTO.setOrderIdOld(ordersRmaBill.getOrdersId());
                transitItemDTO.setTrackingNoOld(ordersRmaBill.getTrackingNo());
                transitItemDTO.setSpu(e.getSpu());
                transitItemDTO.setSku(e.getSku());
                transitItemDTO.setQty(e.getOrdersItemApplyQty());
                transitItemDTO.setDeptId(ordersRmaBill.getSeoDepartmentId());
                transitItemDTO.setSingleAmount(e.getSingleAmount());
                transitItemDTO.setTotalAmount(e.getTotalAmount());
                transitItemDTO.setProductTitle(e.getProductTitle());
                transitItemDTO.setInnerTitle(e.getInnerTitle() == null ? "" : e.getInnerTitle());
                transitItemDTO.setAttrTitleArray(e.getAttrTitleArray());
                transitItemList.add(transitItemDTO);
            });
        });
        TransitStockDTO transitStockDTO = getTransitStockDTO(ordersRmaBill, transitItemList);
        transitStocks.add(transitStockDTO);
        try {
            logger.info("通知转寄仓进行入库操作:请求参数:" + new ObjectMapper().writeValueAsString(transitStockDTO));
        } catch (Exception e) {
            logger.error("参数转换错误:失败原因{},请求参数", e.getMessage(), transitStockDTO);
        }
        WmsResponse wmsResponse = transitStockService.notifyStockTakeGoods(transitStocks);
        try {
            logger.info("通知转寄仓结束:返回参数:" + new ObjectMapper().writeValueAsString(wmsResponse));
        } catch (Exception e) {
            logger.error("参数转换错误:失败原因{},返回参数", e.getMessage(), transitStockDTO);
        }
    }

    public TransitStockDTO getTransitStockDTO(OrdersRmaBill ordersRmaBill, List<TransitItemDTO> transitItemList) {
        TransitStockDTO transitStockDTO = new TransitStockDTO();
        transitStockDTO.setDeptId(ordersRmaBill.getSeoDepartmentId());
        transitStockDTO.setDeptName(ordersRmaBill.getDepartmentUserInfo());
        transitStockDTO.setWmsId(ordersRmaBill.getWarehouseId());
        transitStockDTO.setStockName(ordersRmaBill.getWarehouseName());
        transitStockDTO.setOrderIdOld(ordersRmaBill.getOrdersId());
        transitStockDTO.setSku(Joiner.on(",").join(transitItemList.stream().map(e -> e.getSku()).collect(Collectors.toList())));
        transitStockDTO.setInnerName(Joiner.on(",").join(transitItemList.stream().map(e -> e.getInnerTitle()).collect(Collectors.toList())));
        transitStockDTO.setTrackingNoOld(ordersRmaBill.getTrackingNo());
        transitStockDTO.setRmaId(ordersRmaBill.getId());
        transitStockDTO.setTransitItemList(transitItemList);
        return transitStockDTO;
    }

    /**
     * 更新RMA申请单数据
     *
     * @param ordersRmaBill
     */
    public void updateSelective(OrdersRmaBill ordersRmaBill) {
        ordersRmaBillMapper.updateSelective(ordersRmaBill);
    }

    /**
     * 根据RMA申请单生成退款单
     *
     * @param ordersRmaBill
     * @return
     */
    public OrdersRefundBill createRefundBillByRmaBill(OrdersRmaBill ordersRmaBill) {
        OrdersRefundBill ordersRefundBill = getOrdersRefundBillByRamBii(ordersRmaBill);
        ordersRefundService.save(ordersRefundBill);
        return ordersRefundBill;
    }

    private OrdersRefundBill getOrdersRefundBillByRamBii(OrdersRmaBill ordersRmaBill) {
        OrdersRefundBill ordersRefundBill = new OrdersRefundBill();
        ordersRefundBill.setOrdersAmount(ordersRmaBill.getOrderAmount());
        ordersRefundBill.setRefundMethodEnum(OrdersRefundMethodEnum.fromId(ordersRmaBill.getPayMethodEnum().ordinal()));
        ordersRefundBill.setOrdersId(ordersRmaBill.getOrdersId().intValue());
        ordersRefundBill.setOrdersRamBillId(ordersRmaBill.getId());
        ordersRefundBill.setRefundStatusEnum(OrdersRefundStatusEnum.start);
        ordersRefundBill.setRefundTypeEnum(OrdersRefundTypeEnum.fromId(ordersRmaBill.getChangeTypeEnum().ordinal()));
        ordersRefundBill.setRefundAmount(ordersRmaBill.getRefundAmount());
        ordersRefundBill.setSiteFrom(ordersRefundBill.getSiteFrom());
        ordersRefundBill.setLogisticsId(ordersRmaBill.getRefundLogisticsId());
        ordersRefundBill.setLogisticName(ordersRmaBill.getRefundLogisticName());
        ordersRefundBill.setOrdersPurchaseAt(ordersRefundBill.getOrdersPurchaseAt());
        ordersRefundBill.setCreatorId(ordersRefundBill.getCreatorId());
        ordersRefundBill.setCreator(ordersRefundBill.getCreator());
        ordersRefundBill.setCreateAt(LocalDateTime.now());
        return ordersRefundBill;
    }


    public List<OrderRmaItemReturnQty> countRmaItemReturnQty(Integer orderId) {
        return countRmaItemReturnQty(orderId, null);
    }


    /**
     * @param orderId 订单编号
     * @param rmaId   申请单编号，为空表示新建申请，不为空则表示在编辑（不为空时可退数量应该也包含正在编辑的申请单数量）
     * @return
     * @author wangqian
     * 计算订单每个订单项可退数量
     */
    public List<OrderRmaItemReturnQty> countRmaItemReturnQty(Integer orderId, Integer rmaId) {
        //1、订单编号 -> 所有可退数量
        List<OrdersItem> ordersItemList = ordersItemsMapper.getByOrderId(orderId);
        List<OrderRmaItemReturnQty> allReturnQty = ordersItemList.stream().map(e -> {
            OrderRmaItemReturnQty rmaQty = new OrderRmaItemReturnQty();
            rmaQty.setId(e.getId());
            rmaQty.setQty(e.getQty());
            return rmaQty;
        }).collect(Collectors.toList());


        //2、订单编号已经有效(状态不是取消)申请的且（不是当前编辑的申请单）退货单的对应的申请单编号
        List<OrdersRmaBill> ordersRmaBillList = ordersRmaBillMapper.findOrdersRmaBillByOrderIdAndNotInState(orderId, OrdersRmaStateEnum.cancel.ordinal());
        if (ordersRmaBillList == null || ordersRmaBillList.size() == 0) {
            return allReturnQty;
        }
        List<Integer> availableRmaIdList = ordersRmaBillList.stream()
                .filter(a -> {
                    // 创建申请单时
                    if (rmaId == null) {
                        return true;
                    }
                    //修改申请单，则将当前申请单占用的数量过滤掉
                    return !a.getId().equals(rmaId);
                })
                .map(b -> b.getId()).collect(Collectors.toList());

        //3、申请单编号对应的订单项编号已经申请数
        List<OrdersRmaBillItem> rmaBillItemList = ordersRmaBillItemMapper.findByRmaIds(availableRmaIdList);
        Map<Integer, Integer> applyCountMap = rmaBillItemList.stream().collect(Collectors.groupingBy(OrdersRmaBillItem::getOrdersItemId, Collectors.summingInt(OrdersRmaBillItem::getOrdersItemApplyQty)));

        //4、可退数量 =  1 - 3
//        List<OrderRmaItemReturnQty> canReturnQtyList = new ArrayList<>(4);
        allReturnQty.stream().forEach(e -> {
//            OrderRmaItemReturnQty rmaItemReturnQty = new OrderRmaItemReturnQty();
            Integer count = applyCountMap.get(e.getId());
            if (count != null) {
                Integer qty = Math.toIntExact(count);
                e.setQty(e.getQty() - qty);
            }
        });
        return allReturnQty;
    }

    /**
     * 查询出拒收的记录
     *
     * @param id
     * @return
     */
    public OrdersRmaBill findOrdersRmaBillByOrderId(Integer id) {
        return ordersRmaBillMapper.findOrdersRmaBillByOrderId(id);
    }

    /**
     * 取消申请单
     *
     * @param rmaId
     * @return
     */
    public RestResult cancelRmaBill(Integer rmaId) {
        Assert.notNull(rmaId, "请求参数有误");
        RestResult restResult = new RestResult();
        OrdersRmaBill ordersRmaBill = ordersRmaBillMapper.getById(rmaId);
        Assert.notNull(ordersRmaBill, "请求参数有误");
        try {
            orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.changeCancel, OrderRmaBillEventEnum.changeCancel.display());
        } catch (IllegalArgumentException e) {
            logger.error("取消申请单失败,失败原因{}，请求参数{}", e.getMessage(), rmaId);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc(e.getMessage());
        } catch (Exception e) {
            logger.error("取消申请单失败,失败原因{}，请求参数{}", e.getMessage(), rmaId);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("取消申请单失败");
        }
        return restResult;
    }

    /**
     * 更新运单号，如果是待发货 则需要发送wms收货
     *
     * @param rmaId
     * @return
     */
    public RestResult updateTrackingNo(Integer rmaId, String trackingNo) {
        Assert.notNull(rmaId, "请求参数有误");
        Assert.notNull(trackingNo, "请求参数有误");
        RestResult restResult = new RestResult();
        OrdersRmaBill ordersRmaBill = ordersRmaBillMapper.getById(rmaId);
        String oldTrackingNo = ordersRmaBill.getTrackingNo();
        //当用户有做运单号的修改的时候 才执行更新操作
        if (StringUtils.isNotBlank(trackingNo) && !trackingNo.equals(oldTrackingNo)) {
            try {
                ordersRmaBill.setTrackingNo(trackingNo);
                orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.updateTrackingNo, OrderRmaBillEventEnum.updateTrackingNo.display() + String.format(" [%s] -> [%s] ", oldTrackingNo, trackingNo));
            } catch (Exception e) {
                logger.error("更新运单号,失败原因{}，请求参数{}", e.getMessage(), rmaId);
                restResult.setCode(RestResult.FAIL);
                restResult.setDesc("更新寄回运单号失败");
            }
        }
        return restResult;
    }

    /**
     * 统计总的条数
     *
     * @param param
     * @return
     * @date 2017年11月28日
     * @author liusl
     */
    public int countBySearchParam(OrdersBillSearchParam param) {
        setPermission(param);
        return ordersRmaBillMapper.countBySearchParam(param);
    }

    /**
     * 根据查询条件查询退换货列表
     *
     * @param param
     * @return
     * @date 2017年11月28日
     * @author liusl
     */
    public List<OrdersRmaBill> findOrdersChangeBySearchParam(OrdersBillSearchParam param) {
        return ordersRmaBillMapper.findBySearchParam(param);
    }

    /**
     * 根据查询条件查询退换货列表-与orders_link表联查
     *
     * @param param
     * @return
     * @date 2017年11月28日
     * @author liusl
     */
    public List<OrdersRmaBillDO> findOrdersChangeBySearchParamWithOrdersLink(OrdersBillSearchParam param) {

        List<OrdersRmaBillDO> ordersRmaBillDOList = ordersRmaBillMapper.findBySearchParamWithOrdersLink(param);
        //批量查Item
        List<Integer> rmaIds = ordersRmaBillDOList.stream().map(e -> e.getId()).collect(Collectors.toList());

        Map<Integer, List<OrdersRmaBillItem>> rmaItemMap = ordersRmaBillItemMapper.findByRmaIds(rmaIds).stream().collect(Collectors.groupingBy(OrdersRmaBillItem::getOrdersRmaBillId));

        ordersRmaBillDOList.stream().forEach(e -> {
            //预付款时退款总金额为0
            if (PayMethodEnum.onlinePay == e.getPayMethodEnum()) {
                e.setRefundAmount(new BigDecimal(0));
            }
            e.setItemList(Optional.ofNullable(rmaItemMap.get(e.getId())).orElse(Collections.EMPTY_LIST));
        });

        return ordersRmaBillDOList;
    }


    /**
     * 设置权限
     *
     * @param param
     */
    private void setPermission(OrdersBillSearchParam param) {
        //用户
        UserDto userDto = ThreadLocalUtils.getUser();
        //地区权限
        List<UserZone> userZoneList = userZoneService.findEnableUserZoneByUserId(userDto.getId());
        List<Integer> zoneIds = userZoneList.stream().map(z -> z.getZoneId()).collect(Collectors.toList());
        param.setZoneIds(zoneIds);

        //权限类型（公司，部门，个人）
        JobAuthorityRel jobAuthorityRel = jobAuthorityRelService.getByUser(userDto.getId());

        //个人
        if (jobAuthorityRel.getJobAuthorityRelEnum() == JobAuthorityRelEnum.myself) {
            param.setCreatorId(userDto.getId());
        }

        //部门
        if (jobAuthorityRel.getJobAuthorityRelEnum() == JobAuthorityRelEnum.myDepartment) {
            //获取查询部门的所有子部门
            Department department = departmentService.get(userDto.getDeptId());
            if (department != null) {
                List<Department> departmentList = departmentService.findByNo(department.getDepartmentNo());
                List<Integer> depts = departmentList.stream().map(e -> e.getId()).collect(Collectors.toList());
                param.setDepartmentIds(depts);
            }

        }
    }

    /**
     * 查看凭证图片
     *
     * @param rmaId
     * @return
     */
    public List<String> findPhotos(Integer rmaId) {
        OrdersRmaBill bill = ordersRmaBillMapper.getById(rmaId);
        String imgStr = bill.getEvidenceImageJsonArray();
        try {
            if (StringUtils.isNotBlank(imgStr)) {
                List<String> imgs = objectMapper.readValue(imgStr, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
                return imgs;
            }else {
                return new ArrayList<String>();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 根据订单号查询是否有拒收单据
     *
     * @param id
     * @return
     */
    public Integer countOrdersRejectByOrderId(Integer id) {
        return ordersRmaBillMapper.countOrdersRejectByOrderId(id);
    }

    /**
     * 根据物流创建拒收申请单
     *
     * @param orderId
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createRmaBillByOrderId(Integer orderId) {
        Orders orders = ordersMapper.findOrderById(orderId);
        List<OrdersItem> ordersItems = ordersItemsMapper.getByOrderId(orderId);
        OrdersRmaBill ordersRmaBill = getOrdersRmaBill(orders);
        ordersRmaBillMapper.insert(ordersRmaBill);
        List<OrdersRmaBillItem> ordersRmaBillItems = getOrdersRmaBillItem(ordersRmaBill, ordersItems);
        ordersRmaBillItemMapper.batchInsert(ordersRmaBillItems);
        orderChangeFsmProxyService.processEvent(ordersRmaBill, OrderRmaBillEventEnum.createReject, "系统自动创建拒收单");
    }

    private List<OrdersRmaBillItem> getOrdersRmaBillItem(OrdersRmaBill ordersRmaBill, List<OrdersItem> ordersItems) {
        List<OrdersRmaBillItem> ordersRmaBillItems = Lists.newArrayList();
        ofNullable(ordersItems).ifPresent(items -> {
            items.stream().forEach(e -> {
                OrdersRmaBillItem ordersRmaBillItem = new OrdersRmaBillItem();
                ordersRmaBillItem.setOrdersRmaBillId(ordersRmaBill.getId());
                ordersRmaBillItem.setSpu(e.getSpu());
                ordersRmaBillItem.setSku(e.getSku());
                ordersRmaBillItem.setAttrTitleArray(StringUtils.isEmpty(e.getAttrTitleArray()) ? "" : e.getAttrTitleArray());
                ordersRmaBillItem.setProductId(e.getProductId().intValue());
                ordersRmaBillItem.setProductTitle(e.getProductTitle());
                ordersRmaBillItem.setOrdersItemId(e.getId());
                ordersRmaBillItem.setInnerTitle(e.getInnerTitle());
                ordersRmaBillItem.setOrdersItemQty(e.getQty());
                ordersRmaBillItem.setOrdersItemApplyQty(e.getQty());
                ordersRmaBillItem.setOrdersItemReturnQty(e.getQty());
                ordersRmaBillItem.setSingleAmount(e.getSingleAmount());
                ordersRmaBillItem.setTotalAmount(e.getTotalAmount());
                ordersRmaBillItem.setStorageLocation("");
                ordersRmaBillItem.setInStorageNo("");
                ordersRmaBillItem.setInQty(0);
                ordersRmaBillItem.setDetectionResult("良品");
                ordersRmaBillItem.setCreateAt(LocalDateTime.now());
                ordersRmaBillItem.setCreatorId(0);
                ordersRmaBillItem.setProductImgUrl(StringUtils.isEmpty(e.getProductImageUrl()) ? "" : e.getProductImageUrl());
                ordersRmaBillItem.setCreator("");
                ordersRmaBillItems.add(ordersRmaBillItem);
            });
        });
        return ordersRmaBillItems;
    }

    private OrdersRmaBill getOrdersRmaBill(Orders orders) {
        OrdersRmaBill ordersRmaBill = new OrdersRmaBill();
        ofNullable(orders).ifPresent(order -> {
            ordersRmaBill.setMerchantOrderNo(order.getMerchantOrderNo());
            ordersRmaBill.setTrackingNo(order.getTrackingNo());
            ordersRmaBill.setLogisticsId(order.getLogisticsId());
            ordersRmaBill.setRefundLogisticsId(order.getLogisticsId());
            ordersRmaBill.setRefundLogisticName(order.getLogisticsName());
            ordersRmaBill.setLogisticName(order.getLogisticsName());
            try {
                //todo 参数增加编译不过
                StoreInfoResponseVo storeInfoResponseVo = ofNullable(shippingWayStoreService.findBackStore(order.getLogisticsId(),0,0)).orElse(new StoreInfoResponseVo());
                ordersRmaBill.setWarehouseId(storeInfoResponseVo.getId() == null ? 0 : storeInfoResponseVo.getId());
                ordersRmaBill.setWarehouseName(storeInfoResponseVo.getName() == null ? "" : storeInfoResponseVo.getName());
            } catch (Exception e) {
                logger.error("获取物流信息失败，同时会生成没有仓库ID的申请单，失败原因:" + e.getMessage());
                ordersRmaBill.setWarehouseId(0);
                ordersRmaBill.setWarehouseName("");
            }
            Currency currency = ofNullable(currencyService.getByCurrencyCode(orders.getCurrencyCode())).orElse(new Currency());
            currency.setSymbolLeft(currency.getSymbolLeft() == null ? "" : currency.getSymbolLeft());
            currency.setSymbolRight(currency.getSymbolRight() == null ? "" : currency.getSymbolRight());
            String amountShow = "";
            if (currency != null) {
                amountShow = currency.getSymbolLeft() == null ? amountShow : currency.getSymbolLeft() + amountShow;
                amountShow = currency.getSymbolRight() == null ? amountShow : amountShow + currency.getSymbolRight();
            }
            ordersRmaBill.setRefundAmountShow(amountShow);
            ordersRmaBill.setCurrencyName(currency.getName());
            ordersRmaBill.setOrderAmount(order.getConfirmAmount());
            ordersRmaBill.setLeftSymbol(currency.getSymbolLeft());
            ordersRmaBill.setRightSymbol(currency.getSymbolRight());
            ordersRmaBill.setSeoDepartmentId(order.getSeoDepartmentId());
            ordersRmaBill.setDepartmentUserInfo(order.getDepartmentUserInfo());
            ordersRmaBill.setWebUrl(StringUtils.isEmpty(order.getOrderFrom()) ? "" : order.getOrderFrom());
            ordersRmaBill.setChangeTypeEnum(ChangeTypeEnum.reject);
            ordersRmaBill.setChangeReasonEnum(ChangeReasonEnum.other);
            ordersRmaBill.setChangeWayEnum(ChangeWayEnum.fromId(order.getPaymentMethod()));
            ordersRmaBill.setRecycleGoodsEnum(RecycleGoodsEnum.yes);
            ordersRmaBill.setRmaSourceEnum(RmaSourceEnum.system);
            ordersRmaBill.setChangeWayEnum(ChangeWayEnum.fromId(order.getPaymentMethod()));
            ordersRmaBill.setRmaStatusEnum(OrdersRmaStateEnum.start);
            ordersRmaBill.setPurchaseAt(order.getPurchaserAt() == null ? LocalDateTime.now() : order.getPurchaserAt());
            ordersRmaBill.setPayMethodEnum(PayMethodEnum.fromId(order.getPayState()));
            ordersRmaBill.setCheckMemo("");
            //拒收且付款类型为货到付款时，退款金额为0.非货到付款为订单金额
            if (order.getPaymentMethod().equals(PayMethodEnum.cod)) {
                ordersRmaBill.setRefundAmount(new BigDecimal("0"));
            } else {
                ordersRmaBill.setRefundAmount(order.getConfirmAmount());
            }
            ordersRmaBill.setApplyUserId(0);
            ordersRmaBill.setApplyUserName("");
            ordersRmaBill.setApplyTime(LocalDateTime.now());
            ordersRmaBill.setChangeReasonOtherMemo("拒收");
            ordersRmaBill.setQuestionMemo("");
            ordersRmaBill.setOrdersId(order.getId().longValue());
            ordersRmaBill.setChangeAt(LocalDateTime.now());
            ordersRmaBill.setCreateAt(LocalDateTime.now());
            ordersRmaBill.setCreatorId(0);
            ordersRmaBill.setCreator("");
            ordersRmaBill.setCheckUserId(0);
            ordersRmaBill.setCheckUserName("");
            ordersRmaBill.setOrderTrackingNo(order.getTrackingNo());
            ordersRmaBill.setZoneId(order.getZoneId());
            ordersRmaBill.setApplyMemo("");
            /*ordersRmaBill.setCustomerGetAccount();*/
        });
        return ordersRmaBill;
    }

    /**
     * 根据订单编号找出有退货记录的订单
     *
     * @param orderId
     * @return
     */
    public List<Integer> queryOrderByOrderIds(List<Integer> orderId) {
        return ordersRmaBillMapper.queryOrderByOrderIds(orderId);
    }

    /**
     * @param rmadId
     * @return
     */
    public OrdersRmaBill getById(Integer rmadId) {
        return ordersRmaBillMapper.getById(rmadId);
    }

    /**
     * 寄回运单导入
     *
     * @param content
     * @return
     * @throws IOException
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BatchImportResult _import(String content) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(content));

        BatchImportResult importResult = new BatchImportResult();

        int totalCount = 0, failCount = 0, successCount = 0;

        String ordersRmaBillId, trackingNo, orderId;


        Map<Integer, String> updateMap = new HashMap<>();

        String line = "";
        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty())
                continue;

            ++totalCount;

            String[] arr = line.split("\t");
            if (arr.length < 3) {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport("格式不正确", line));
                importResult.setHasWrong(true);
                continue;
            }


            try {

                Assert.hasText(arr[0], "退换货申请单号");
                Assert.hasText(arr[1], "订单流水号");
                Assert.hasText(arr[2], "寄回运单号");

                ordersRmaBillId = arr[0];
                orderId = arr[1];
                trackingNo = arr[2];

            } catch (Exception e) {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport("格式不正确", line));
                importResult.setHasWrong(true);
                continue;
            }

            //检查问题类型
            OrdersRmaBill ordersRmaBill = ordersRmaBillMapper.getById(Integer.valueOf(ordersRmaBillId));
            if (Objects.isNull(ordersRmaBill)) {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport(ordersRmaBillId, "退换货申请单号不存在"));
                continue;
            }

            if (!ordersRmaBill.getOrdersId().equals(Long.valueOf(orderId))) {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport(ordersRmaBillId, "申请单号与订单号无关联关系"));
                continue;
            }

            if (ordersRmaBill.getRmaStatusEnum() != OrdersRmaStateEnum.waitDelivery) {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport(ordersRmaBillId, "申请单状态不为[待寄回]状态，导入失败"));
                continue;
            }

            if (trackingNo.equals(ordersRmaBill.getTrackingNo())) {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport(ordersRmaBillId, "重复导入"));
            } else {
                Integer row = ordersRmaBillMapper.updateTrackingNo(Integer.valueOf(ordersRmaBillId), trackingNo);
                if (row > 0) {
                    ++successCount;
                    // 通知wms等待收货 TODO 待确定
                    this.notifyStockTakeGoods(ordersRmaBill);
                } else {
                    ++failCount;
                    importResult.setHasWrong(true);
                    importResult.getFailList().add(new BatchImportResult.FailedImport("保存失败", line));
                }
            }
        }

        importResult.setTotalCount(totalCount);
        importResult.setSuccessCount(successCount);
        importResult.setFailCount(failCount);

        return importResult;
    }

    /**
     * 批量创建
     *
     * @param file
     * @return
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RestResult bathApplyChange(MultipartFile file) {
        RestResult restResult = new RestResult();
        try {
            List<OrdersRmaBillImportDO> ordersRmaBills = ExcelUtils.importExcel(OrdersRmaBillImportDO.class, file);
            BatchImportResult importResult = validateData(ordersRmaBills);

            if (importResult.getHasWrong()) {
                restResult.setItem(importResult);
                restResult.setCode(RestResult.NOTICE);
                restResult.setDesc("格式不正确或数据错误");
            } else {
                restResult.setCode(RestResult.OK);
                restResult.setDesc("导入完成");
            }
        } catch (Exception e) {
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("导入失败");
            logger.error("导入失败:" + e.getMessage(), e);
        }
        return restResult;
    }

    /**
     * 进行数据的校验
     *
     * @param ordersRmaBills
     * @return
     */
    private BatchImportResult validateData(List<OrdersRmaBillImportDO> ordersRmaBills) {

        BatchImportResult importResult = new BatchImportResult();

        List<Integer> orderIds = ordersRmaBills.stream().map(e -> e.getOrderId()).collect(Collectors.toList());
        //查找出已存在的，非取消退货申请单
        List<Integer> existRecords = ordersRmaBillMapper.queryOrderByOrderIds(orderIds);
        //根据订单ID去订单表中找到能够匹配订单ID的记录
        List<Orders> realOrders = ordersMapper.findOrderByOrderIds(orderIds);
        if (CollectionUtils.isNullOrEmpty(realOrders)) {
            importResult.setHasWrong(true);
            return importResult;
        }

        // 接收可导入数据库的申请单
        List<OrdersRmaBill> qualifiedList = Lists.newArrayList();
        List<Integer> qualifiedOrderIds = Lists.newArrayList();
        // 接收数据错误的申请单
        List<OrdersRmaBillImportDO> faildList = Lists.newArrayList();

        Map<Integer, Orders> orderMap = realOrders.stream().collect(Collectors.toMap(Orders::getId, Function.identity()));
        Map<Long, OrdersAddition> ordersAdditionsMap = ordersAdditionMapper.queryByOrderIds(orderIds).stream().collect(Collectors.toMap(OrdersAddition::getOrdersId, Function.identity()));


        // TODO 去物流查仓库，批量


        ofNullable(ordersRmaBills).ifPresent(bills -> {
            for (int i = 0; i < bills.size(); i++) {

                OrdersRmaBillImportDO ordersRmaBillImport = bills.get(i);

                Integer orderIdImport = ordersRmaBillImport.getOrderId();
                String trackingNoImport = ordersRmaBillImport.getTrackingNo();

                Orders order = orderMap.get(orderIdImport);
                OrdersAddition ordersAddition = ordersAdditionsMap.get(Long.valueOf(orderIdImport));
                String trackingNoOld = order.getTrackingNo();


                if (Objects.isNull(orderIdImport) && StringUtils.isBlank(trackingNoImport)) {
                    ordersRmaBillImport.setErrorMsg("订单流水号或发货运单号必填一个");
                    faildList.add(ordersRmaBillImport);
                    importResult.setHasWrong(true);
                    continue;
                }

                // 订单号和要运单号都不为空的情况
                if (Objects.nonNull(orderIdImport) && StringUtils.isNotBlank(trackingNoImport)) {
                    if (!orderMap.containsKey(orderIdImport)) {
                        ordersRmaBillImport.setErrorMsg("订单流水号不存在");
                        faildList.add(ordersRmaBillImport);
                        importResult.setHasWrong(true);
                        continue;
                    }

                    if (!trackingNoImport.equals(trackingNoOld)) {
                        ordersRmaBillImport.setErrorMsg("订单流水号与运单号不匹配");
                        faildList.add(ordersRmaBillImport);
                        importResult.setHasWrong(true);
                        continue;
                    }
                }


                // 订单号不为空，运单号为空
                if (Objects.nonNull(orderIdImport) && StringUtils.isBlank(trackingNoImport)) {
                    if (!orderMap.containsKey(orderIdImport)) {
                        ordersRmaBillImport.setErrorMsg("订单流水号不存在");
                        faildList.add(ordersRmaBillImport);
                        importResult.setHasWrong(true);
                        continue;
                    }

                    // 订单中的运单号是空的说明没发货
                    if (StringUtils.isBlank(trackingNoOld)) {
                        ordersRmaBillImport.setErrorMsg("订单流水号与运单号不匹配");
                        faildList.add(ordersRmaBillImport);
                        importResult.setHasWrong(true);
                        continue;
                    }

                    ordersRmaBillImport.setTrackingNo(trackingNoOld);
                }

                // 订单号为空，运单号不为空
                if (Objects.isNull(orderIdImport) && StringUtils.isNotBlank(trackingNoImport)) {

                    // TODO 通过运单号查找订单,或者改为批量查？
                    order = ordersMapper.findByTrackingNo(trackingNoImport);
                    ordersAddition = ordersAdditionMapper.getByOrderId(order.getId());
                    if (Objects.isNull(order)) {
                        ordersRmaBillImport.setErrorMsg("运单号不存在");
                        faildList.add(ordersRmaBillImport);
                        importResult.setHasWrong(true);
                        continue;
                    }

                    ordersRmaBillImport.setOrderId(order.getId());
                    orderMap.put(order.getId(), order);
                }


                if (existRecords.contains(orderIdImport)) {
                    ordersRmaBillImport.setErrorMsg("订单已存在非取消退货申请单，请确认是否需要单独新建");
                    faildList.add(ordersRmaBillImport);
                    importResult.setHasWrong(true);
                    continue;
                }

                if (!RmaSourceEnum.isInclude(ordersRmaBillImport.getRmaSourceEnum())) {
                    ordersRmaBillImport.setErrorMsg("售后来源不能为空或不存在");
                    faildList.add(ordersRmaBillImport);
                    importResult.setHasWrong(true);
                    continue;
                }

                if (!ChangeReasonEnum.isInclude(ordersRmaBillImport.getChangeReasonEnum())) {
                    ordersRmaBillImport.setErrorMsg("退货原因不能为空或不存在");
                    faildList.add(ordersRmaBillImport);
                    importResult.setHasWrong(true);
                    continue;
                }

                if (ordersRmaBillImport.getChangeReasonEnum().equals("其他") && StringUtils.isBlank(ordersRmaBillImport.getChangeReasonOtherMemo())) {
                    ordersRmaBillImport.setErrorMsg("退货原因为其它时，退货说明不能为空");
                    faildList.add(ordersRmaBillImport);
                    importResult.setHasWrong(true);
                    continue;
                }

                if (!RecycleGoodsEnum.isInclude(ordersRmaBillImport.getRecycleGoodsEnum())) {
                    ordersRmaBillImport.setErrorMsg("是否回收不能为空或不存在");
                    faildList.add(ordersRmaBillImport);
                    importResult.setHasWrong(true);
                    continue;
                }


                ordersRmaBillImport.setSourceEnum(RmaSourceEnum.getFormEnum(ordersRmaBillImport.getRmaSourceEnum()));
                ordersRmaBillImport.setGoodsEnum(RecycleGoodsEnum.getFormEnum(ordersRmaBillImport.getRecycleGoodsEnum()));
                ordersRmaBillImport.setReasonEnum(ChangeReasonEnum.getFormEnum(ordersRmaBillImport.getChangeReasonEnum()));


                //  订单未确认收货，请与物流确认，非清关失败或丢件时，订单必须为签收状态。
                if (ordersRmaBillImport.getReasonEnum() != ChangeReasonEnum.unclearance || ordersRmaBillImport.getReasonEnum() != ChangeReasonEnum.missing) {
                    if (order.getOrderStatusEnum() != OrderStateEnum.sign) {
                        ordersRmaBillImport.setErrorMsg("订单未确认收货，请与物流确认。非清关失败或丢件时，订单必须为签收状态。");
                        faildList.add(ordersRmaBillImport);
                        importResult.setHasWrong(true);
                        continue;
                    }
                }

                qualifiedList.add(getOrdersRmaBill(ordersRmaBillImport, order, ordersAddition));
                qualifiedOrderIds.add(order.getId());
            }
        });

        // 批量插入合格的申请单
        if (!qualifiedList.isEmpty()) {

            ordersRmaBillMapper.batchInsert(qualifiedList);

            // 获取刚刚存入的申请单
            List<OrdersRmaBill> ordersRmaBillList = ordersRmaBillMapper.findByOrderIds(qualifiedOrderIds);

            List<OrdersItem> ordersItems = ordersItemsMapper.findByOrderIds(qualifiedOrderIds);
            List<OrdersRmaBillItem> ordersRmaBillItems = getOrdersRmaBillItems(ordersItems, ordersRmaBillList);


            ordersRmaBillItemMapper.batchInsert(ordersRmaBillItems);
        }

        // 解析失败的数据以json格式存入redis
        if (CollectionUtils.isNotNullAndEmpty(faildList)) {
            final String key = "rms_bill_import_failed_" + new Date().getTime();
            redisTemplate.opsForValue().set(key, JsonUtils.toJson(faildList), 2, TimeUnit.MINUTES);
            importResult.setFailListKey(key);
        }


        importResult.setTotalCount(ordersRmaBills.size());
        importResult.setSuccessCount(qualifiedList.size());
        importResult.setFailCount(faildList.size());
        return importResult;

    }

    private List<OrdersRmaBillItem> getOrdersRmaBillItems(List<OrdersItem> ordersItems, List<OrdersRmaBill> ordersRmaBillList) {
        List<OrdersRmaBillItem> ordersRmaBillItems = new ArrayList<>();
        ordersRmaBillList.stream().forEach(rmaBill -> {
            OrdersRmaBillItem ordersRmaBillItem = new OrdersRmaBillItem();
            ordersItems.stream().forEach(ordersItem -> {
                if (rmaBill.getOrdersId() == ordersItem.getOrdersId().intValue()) {

                    ordersRmaBillItem.setOrdersRmaBillId(rmaBill.getId());
                    ordersRmaBillItem.setSpu(ordersItem.getSpu());
                    ordersRmaBillItem.setSku(ordersItem.getSku());
                    ordersRmaBillItem.setAttrTitleArray(StringUtils.isBlank(ordersItem.getAttrTitleArray()) ? "" : ordersItem.getAttrTitleArray());
                    ordersRmaBillItem.setProductId(ordersItem.getProductId().intValue());
                    ordersRmaBillItem.setProductTitle(ordersItem.getProductTitle());
                    ordersRmaBillItem.setOrdersItemId(ordersItem.getId());
                    ordersRmaBillItem.setInnerTitle(ordersItem.getInnerTitle());
                    ordersRmaBillItem.setOrdersItemQty(ordersItem.getQty());
                    ordersRmaBillItem.setOrdersItemApplyQty(ordersItem.getQty());
                    ordersRmaBillItem.setOrdersItemReturnQty(ordersItem.getQty());
                    ordersRmaBillItem.setSingleAmount(ordersItem.getSingleAmount());
                    ordersRmaBillItem.setTotalAmount(ordersItem.getTotalAmount());
                    ordersRmaBillItem.setProductImgUrl(StringUtils.isBlank(ordersItem.getProductImageUrl()) ? "" : ordersItem.getProductImageUrl());

                    // 入库信息
                    ordersRmaBillItem.setDetectionResult("良品");
                    ordersRmaBillItem.setStorageLocation("");
                    ordersRmaBillItem.setInStorageNo("");
                    ordersRmaBillItem.setInQty(0);

                    ordersRmaBillItem.setCreateAt(LocalDateTime.now());
                    ordersRmaBillItem.setCreatorId(ThreadLocalUtils.getUser().getId());
                    ordersRmaBillItem.setCreator(ThreadLocalUtils.getUser().getLastName());

                    ordersRmaBillItems.add(ordersRmaBillItem);
                }
            });
        });
        return ordersRmaBillItems;
    }

    /**
     * 创建OrdersRmaBill
     *
     * @param ordersRmaBillImport
     * @param order
     * @return
     */
    private OrdersRmaBill getOrdersRmaBill(OrdersRmaBillImportDO ordersRmaBillImport, Orders order, OrdersAddition addition) {
        OrdersRmaBill ordersRmaBill = new OrdersRmaBill();

        ordersRmaBill.setOrdersId(Long.valueOf(order.getId()));
        ordersRmaBill.setMerchantOrderNo(order.getMerchantOrderNo());
        ordersRmaBill.setTrackingNo(order.getTrackingNo());
        ordersRmaBill.setOrderTrackingNo(order.getTrackingNo() == null ? "" : order.getTrackingNo());
        ordersRmaBill.setLogisticsId(order.getLogisticsId());
        ordersRmaBill.setLogisticName(order.getLogisticsName());
        ordersRmaBill.setOrderAmount(order.getOrderAmount());
        ordersRmaBill.setSeoDepartmentId(order.getSeoDepartmentId());
        ordersRmaBill.setDepartmentUserInfo(order.getDepartmentUserInfo());
        ordersRmaBill.setChangeTypeEnum(ChangeTypeEnum.returned);

        if (Objects.nonNull(addition) && StringUtils.isNotBlank(addition.getWebUrl()))
            ordersRmaBill.setWebUrl(addition.getWebUrl());//从addtion拿
        else ordersRmaBill.setWebUrl("");

// TODO 仓库得去物流查
//        ordersRmaBill.setWarehouseId();
//        ordersRmaBill.setWarehouseName();

        ordersRmaBill.setPayMethodEnum(PayMethodEnum.fromId(order.getPaymentMethod()));
        ordersRmaBill.setRmaStatusEnum(OrdersRmaStateEnum.draft);// TODO 这个状态暂时是“草稿”，后面应该改成“开始”然后通过状态机改变状态。
        ordersRmaBill.setRefundAmount(order.getConfirmAmount());
        Currency currency = ofNullable(currencyService.getByCurrencyCode(order.getCurrencyCode())).orElse(new Currency());
        currency.setSymbolLeft(currency.getSymbolLeft() == null ? "" : currency.getSymbolLeft());
        currency.setSymbolRight(currency.getSymbolRight() == null ? "" : currency.getSymbolRight());
        ordersRmaBill.setCurrencyName(currency.getName());
        ordersRmaBill.setLeftSymbol(currency.getSymbolLeft());
        ordersRmaBill.setRightSymbol(currency.getSymbolRight());
        String amountShow = currency.getSymbolLeft() + order.getConfirmAmount() + currency.getSymbolRight();
        ordersRmaBill.setRefundAmountShow(amountShow);
        ordersRmaBill.setApplyUserId(ThreadLocalUtils.getUser().getId());
        ordersRmaBill.setApplyUserName(ThreadLocalUtils.getUser().getLastName());
        ordersRmaBill.setApplyTime(LocalDateTime.now());
        try {
            StoreInfoResponseVo storeInfoResponseVo = ofNullable(shippingWayStoreService.findBackStore(order.getLogisticsId(),0,0)).orElse(new StoreInfoResponseVo());
            ordersRmaBill.setWarehouseId(storeInfoResponseVo.getId() == null ? 0 : storeInfoResponseVo.getId());
            ordersRmaBill.setWarehouseName(storeInfoResponseVo.getName() == null ? "" : storeInfoResponseVo.getName());
        } catch (Exception e) {
            logger.error("获取物流信息失败，同时会生成没有仓库ID的申请单，失败原因:" + e.getMessage());
            ordersRmaBill.setWarehouseId(0);
            ordersRmaBill.setWarehouseName("");
        }

        ordersRmaBill.setChangeAt(LocalDateTime.now());
        ordersRmaBill.setCreateAt(LocalDateTime.now());
        ordersRmaBill.setCreatorId(ThreadLocalUtils.getUser().getId());
        ordersRmaBill.setCreator(ThreadLocalUtils.getUser().getLastName());
        ordersRmaBill.setZoneId(order.getZoneId());


        ordersRmaBill.setChangeReasonEnum(ordersRmaBillImport.getReasonEnum());
        ordersRmaBill.setChangeReasonOtherMemo(ordersRmaBillImport.getChangeReasonOtherMemo() == null ? "" : ordersRmaBillImport.getChangeReasonOtherMemo());
        ordersRmaBill.setQuestionMemo("");
        ordersRmaBill.setCheckMemo("");
        ordersRmaBill.setApplyMemo(ordersRmaBillImport.getApplyMemo() == null ? "" : ordersRmaBillImport.getApplyMemo());

        ordersRmaBill.setRmaSourceEnum(ordersRmaBillImport.getSourceEnum());
        ordersRmaBill.setRecycleGoodsEnum(ordersRmaBillImport.getGoodsEnum());
        ordersRmaBill.setChangeWayEnum(ordersRmaBillImport.getWayEnum());
        //如果是物流自退 需要记录退款物流公司是哪家
        if (ordersRmaBillImport.getChangeWayEnum().equals(ChangeWayEnum.logisticsBack)) {
            ordersRmaBill.setRefundLogisticsId(order.getLogisticsId());
            ordersRmaBill.setRefundLogisticName(order.getLogisticsName());
        }

        return ordersRmaBill;
    }

    /**
     * 进行数据的转换操作
     *
     * @param orders
     * @return
     */
//    private List<OrdersRmaBill> getOrdersRmaBills(List<Orders> orders, List<OrdersRmaBillImportDO> ordersRmaBillImports) {
//        List<OrdersRmaBill> ordersRmaBills = orders.stream().map(order -> {
//            OrdersRmaBill ordersRmaBill = new OrdersRmaBill();
//            ordersRmaBill.setOrdersId(Long.valueOf(order.getId()));
//            ordersRmaBill.setMerchantOrderNo(order.getMerchantOrderNo());
//            ordersRmaBill.setTrackingNo(order.getTrackingNo());
//            ordersRmaBill.setOrderTrackingNo(order.getTrackingNo() == null ? "" : order.getTrackingNo());
//            ordersRmaBill.setLogisticsId(order.getLogisticsId());
//            ordersRmaBill.setLogisticName(order.getLogisticsName());
//            ordersRmaBill.setOrderAmount(order.getOrderAmount());
//            ordersRmaBill.setSeoDepartmentId(order.getSeoDepartmentId());
//            ordersRmaBill.setDepartmentUserInfo(order.getDepartmentUserInfo());
//            ordersRmaBill.setWebUrl(order.getOrderFrom());
//            ordersRmaBill.setChangeTypeEnum(ChangeTypeEnum.returned);
//            ofNullable(ordersRmaBillImports).ifPresent(inports -> {
//                inports.stream().forEach(e -> {
//                    // GOGO
//                    if (e.getOrderId() == ordersRmaBill.getOrdersId().intValue()) {
//                        ordersRmaBill.setChangeReasonEnum(e.getReasonEnum());
//                        ordersRmaBill.setChangeReasonOtherMemo(e.getChangeReasonOtherMemo());
//                        ordersRmaBill.setQuestionMemo(e.getQuestionMemo());
//                        ordersRmaBill.setRmaSourceEnum(e.getSourceEnum());
//                        ordersRmaBill.setRecycleGoodsEnum(e.getGoodsEnum());
//                        //如果是物流自退 需要记录退款物流公司是哪家
//                        if (e.getChangeWayEnum().equals(ChangeWayEnum.logisticsBack)) {
//                            ordersRmaBill.setRefundLogisticsId(order.getLogisticsId());
//                            ordersRmaBill.setRefundLogisticName(order.getLogisticsName());
//                        }
//                    }
//                });
//            });
//            ordersRmaBill.setPayMethodEnum(PayMethodEnum.fromId(order.getPaymentMethod()));
//            ordersRmaBill.setRmaStatusEnum(OrdersRmaStateEnum.start);
//            ordersRmaBill.setRefundAmount(order.getConfirmAmount());
//            Currency currency = ofNullable(currencyService.getByCurrencyCode(order.getCurrencyCode())).orElse(new Currency());
//            currency.setSymbolLeft(currency.getSymbolLeft() == null ? "" : currency.getSymbolLeft());
//            currency.setSymbolRight(currency.getSymbolRight() == null ? "" : currency.getSymbolRight());
//            ordersRmaBill.setCurrencyName(currency.getName());
//            ordersRmaBill.setLeftSymbol(currency.getSymbolLeft());
//            ordersRmaBill.setRightSymbol(currency.getSymbolRight());
//            String amountShow = currency.getSymbolLeft() + order.getConfirmAmount() + currency.getSymbolRight();
//            ordersRmaBill.setRefundAmountShow(amountShow);
//            ordersRmaBill.setApplyUserId(ThreadLocalUtils.getUser().getId());
//            ordersRmaBill.setApplyUserName(ThreadLocalUtils.getUser().getLastName());
//            ordersRmaBill.setApplyTime(LocalDateTime.now());
//            try {
//                StoreInfoResponseVo storeInfoResponseVo = ofNullable(shippingWayStoreService.findBackStore(order.getLogisticsId())).orElse(new StoreInfoResponseVo());
//                ordersRmaBill.setWarehouseId(storeInfoResponseVo.getId() == null ? 0 : storeInfoResponseVo.getId());
//                ordersRmaBill.setWarehouseName(storeInfoResponseVo.getName() == null ? "" : storeInfoResponseVo.getName());
//            } catch (Exception e) {
//                logger.error("获取物流信息失败，同时会生成没有仓库ID的申请单，失败原因:" + e.getMessage());
//                ordersRmaBill.setWarehouseId(0);
//                ordersRmaBill.setWarehouseName("");
//            }
//
//            ordersRmaBill.setChangeAt(LocalDateTime.now());
//            ordersRmaBill.setCreateAt(LocalDateTime.now());
//            ordersRmaBill.setCreatorId(ThreadLocalUtils.getUser().getId());
//            ordersRmaBill.setCreator(ThreadLocalUtils.getUser().getLastName());
//            ordersRmaBill.setZoneId(order.getZoneId());
//            return ordersRmaBill;
//        }).collect(Collectors.toList());
//        return ordersRmaBills;
//    }
}

