package com.stosz.order.sync.olderp;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.olderp.ext.model.*;
import com.stosz.olderp.ext.service.*;
import com.stosz.order.ext.enums.*;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.mapper.order.OrdersMapper;
import com.stosz.order.sync.olderp.core.AbstractSycn;
import com.stosz.plat.utils.CommonUtils;
import com.stosz.plat.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @auther carter
 * create time    2017-11-14
 */
@Component
public class SyncOrderService extends AbstractSycn<Orders> {

    private static final Logger logger = LoggerFactory.getLogger(SyncOrderService.class);

    private final static Integer SIZE = 500;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private IOldErpOrderService oldErpOrderMapper;
    @Autowired
    private IErpOrderInfoService erpOrderInfoService;
    @Autowired
    private IErpOrderShippingService erpOrderShippingService;
    @Autowired
    private IOldErpCurrencyService oldErpCurrencyService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IErpDomainService erpDomainService;
    @Autowired
    private IErpWarehouseService erpWarehouseService;
    @Autowired
    private IUserService userService;
    //    @Autowired
//    private IZoneService zoneService;
    @Autowired
    private IOldErpZoneService oldErpZoneService;

    private static Map<Integer, OrderStateEnum> orderStateEnumMap = Maps.newHashMap();

    {
        orderStateEnumMap.put(1, OrderStateEnum.waitAudit);
        orderStateEnumMap.put(2, OrderStateEnum.waitAudit);
        orderStateEnumMap.put(3, OrderStateEnum.waitAudit);

        /*orderStateEnumMap.put(4, OrderStateEnum.waitDeliver);*/
        orderStateEnumMap.put(5, OrderStateEnum.waitDeliver);
        orderStateEnumMap.put(7, OrderStateEnum.waitDeliver);
        orderStateEnumMap.put(25, OrderStateEnum.waitDeliver);
        orderStateEnumMap.put(26, OrderStateEnum.waitDeliver);
        orderStateEnumMap.put(27, OrderStateEnum.waitDeliver);

        orderStateEnumMap.put(6, OrderStateEnum.waitPurchase);

        orderStateEnumMap.put(8, OrderStateEnum.deliver);
        orderStateEnumMap.put(18, OrderStateEnum.deliver);

        orderStateEnumMap.put(9, OrderStateEnum.sign);
        orderStateEnumMap.put(10, OrderStateEnum.sign);
        orderStateEnumMap.put(21, OrderStateEnum.sign);
        orderStateEnumMap.put(23, OrderStateEnum.sign);

        orderStateEnumMap.put(11, OrderStateEnum.invalidOrder);
        orderStateEnumMap.put(12, OrderStateEnum.invalidOrder);
        orderStateEnumMap.put(13, OrderStateEnum.invalidOrder);

        orderStateEnumMap.put(14, OrderStateEnum.orderCancel);
        /*orderStateEnumMap.put(15, OrderStateEnum.exchange);*/
        orderStateEnumMap.put(16, OrderStateEnum.orderCancel);

        orderStateEnumMap.put(17, OrderStateEnum.waitPurchase);


        /*orderStateEnumMap.put(22, OrderStateEnum.auditPass);*/

        /*orderStateEnumMap.put(24, OrderStateEnum.deliver); 已入转寄仓查上一个状态*/


    }

    public AtomicBoolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(AtomicBoolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * 验证码类型
     */
    private static Map<Integer, TelCodeState> telCodeStateEnumMap = Maps.newHashMap();

    {
        telCodeStateEnumMap.put(1, TelCodeState.succ);
        telCodeStateEnumMap.put(2, TelCodeState.fail);
        telCodeStateEnumMap.put(3, TelCodeState.unValidate);
    }


    /*@Scheduled(fixedRate = 20 * 1000)*/
    public void sync() {
        setFetch_count(10000);
        super.pullById();
    }

    public void syn() {
        if (!isRunning.get()) {
            long startTime = System.currentTimeMillis();
            try {
                isRunning.set(true);
                for (int i = 0; i < 3000; i++) {
                    setFetch_count(8000);
                    boolean finish = super.pullById();
                    if(!finish){
                        break;
                    }
                    if(!isRunning.get()){
                        break;
                    }
                }
            } catch (Exception e) {
                logger.error("订单同步出现错误:"+e.getMessage(),e);
            } finally {
                isRunning.set(false);
            }
            long endTime = System.currentTimeMillis();
            logger.info("本次订单同步耗时（秒）：{}", (endTime - startTime) / 1000);
        }


    }


    /**
     * 按照id范围抓取
     *
     * @param idMin id最小值
     * @param idMax id最大值
     * @return
     */
    @Override
    protected List<Orders> fetchByIdRegion(int idMin, int idMax) {
        List<OldErpOrder> oldErpOrders = oldErpOrderMapper.fetchByIdRegion(idMin, idMax);

        //订单信息
        List<Integer> oldErpOrderIdList = oldErpOrders.stream().map(e -> e.getIdOrder()).collect(Collectors.toList());
        Map<Integer, List<ErpOrderInfo>> erpOrderInfoMap = erpOrderInfoService.findByIds(oldErpOrderIdList).stream().collect(Collectors.groupingBy(ErpOrderInfo::getIdOrder));
        //区域，数量少，直接查询所有
        Map<Integer, OldErpZone> oldErpZoneMap = oldErpZoneService.findAll().stream().collect(Collectors.toMap(OldErpZone::getId, Function.identity()));

        //货币，数量少，直接查询所有
        Map<String, OldErpCurrency> oldErpCurrencyMap = oldErpCurrencyService.findAll().stream().collect(Collectors.toMap(OldErpCurrency::getCode, Function.identity()));

        //部门，数量少,直接查询所有
        Map<Integer, List<Department>> departmentMap = Optional.of(departmentService.findAll().stream().filter(e -> e.getOldId() != null).collect(Collectors.groupingBy(Department::getOldId))).orElse(Maps.newHashMap());

        //域名
        List<Integer> oldErpDomainIdList = oldErpOrders.stream().map(e -> e.getIdDomain()).collect(Collectors.toList());
        Map<Integer, ErpDomain> erpDomainMap = erpDomainService.findByIds(oldErpDomainIdList).stream().collect(Collectors.toMap(ErpDomain::getIdDomain, Function.identity()));

        //物流
        Map<Integer, List<ErpOrderShipping>> erpOrderShippingListMap = erpOrderShippingService.findByOrderIds(oldErpOrderIdList).stream().collect(Collectors.groupingBy(ErpOrderShipping::getIdOrder));
        //已入转寄仓 并且有收款记录
        List<Integer> settlements =  erpOrderInfoService.findSettlements();
        //仓库
        List<Integer> oldErpWarehouseIds = oldErpOrders.stream().map(e -> e.getIdWarehouse()).collect(Collectors.toList());
        Map<Integer, ErpWarehouse> erpWarehouseMap = erpWarehouseService.findByIds(oldErpWarehouseIds).stream().collect(Collectors.toMap(ErpWarehouse::getIdWarehouse, Function.identity()));


        List<Orders> ordersList = oldErpOrders.
                stream()
                .parallel()
                .map(oldErpOrder -> {

                    Orders orders = new Orders();
                    orders.setOldId(Long.valueOf(oldErpOrder.getIdOrder()));
                    List<ErpOrderInfo> erpOrderInfoList = erpOrderInfoMap.get(oldErpOrder.getIdOrder());
                    orders.setMerchantOrderNo(String.valueOf(oldErpOrder.getIdIncrement()));
                    orders.setMerchantEnum(MerchantEnum.single);
                    orders.setOrderTypeEnum(OrderTypeEnum.normal.ordinal());//默认正常的订单
                    orders.setDeliveryTime(oldErpOrder.getDateDelivery());
                    OldErpZone zone = oldErpZoneMap.get(oldErpOrder.getIdZone());
                    orders.setZoneCode(zone == null ? " " : zone.getCode());
                    orders.setZoneName(zone == null ? " " : zone.getTitle());
                    orders.setZoneId(oldErpOrder.getIdZone());

                    /*orders.setInvalidReasonType(null);*/
                    orders.setOrderAmount(oldErpOrder.getPriceTotal());
                    orders.setConfirmAmount(orders.getOrderAmount());
                    orders.setCurrencyCode(oldErpOrder.getCurrencyCode());
                    OldErpCurrency oldErpCurrency = oldErpCurrencyMap.get(oldErpOrder.getCurrencyCode());
                    if (oldErpCurrency == null) {
                        orders.setAmountShow(orders.getOrderAmount().toString());
                    } else {
                        if (StringUtils.isNotBlank(oldErpCurrency.getSymbolLeft())) {
                            orders.setAmountShow(oldErpCurrency.getSymbolLeft() + orders.getOrderAmount());
                        } else {
                            orders.setAmountShow(orders.getOrderAmount() + oldErpCurrency.getSymbolRight());
                        }
                    }

                    orders.setOrderExchangeRate(new BigDecimal(oldErpCurrency == null || Strings.isNullOrEmpty(oldErpCurrency.getValue())? "1" : String.valueOf(CommonUtils.getIntValue(oldErpCurrency.getValue()))));

                    orders.setGoodsQty(oldErpOrder.getTotalQtyOrdered());
                    orders.setPaymentMethod(getPaymentMethod(oldErpOrder.getPaymentMethod()));
                    orders.setCustomerMessage(oldErpOrder.getRemark());
                    orders.setMemo(oldErpOrder.getComment());
                    orders.setPurchaserAt(oldErpOrder.getDatePurchase());
                    orders.setGetCode("");//todo
                    orders.setInputCode("");//todo
                    String status = oldErpOrder.getIdOrderStatus()==null?"":oldErpOrder.getIdOrderStatus().toString();
                    if(StringUtils.isNotBlank(status) && status.equals("11")){
                        orders.setInvalidReasonType(2);
                    }else if(StringUtils.isNotBlank(status) && status.equals("12")){
                        orders.setInvalidReasonType(3);
                    }else if(StringUtils.isNotBlank(status) && status.equals("13")){
                        orders.setInvalidReasonType(4);
                    }else if(StringUtils.isNotBlank(status) && status.equals("14")){
                        orders.setCancelReasonEnum(OrderCancelReasonEnum.customer);
                    }

                    orders.setOrderStatusEnum(getOrderStatusEnum(oldErpOrder.getIdOrderStatus()));
                    Department department = Optional.ofNullable(departmentMap.get(oldErpOrder.getIdDepartment())).orElse(Arrays.asList(new Department())).get(0);
                    orders.setSeoDepartmentId(department == null || null == department.getId() ? 0 : department.getId());
                    orders.setBuDepartmentId(department == null || null == department.getParentId() ? 0 : department.getParentId());
                    orders.setDepartmentUserInfo(department == null || null == department.getDepartmentName() ? "" : department.getDepartmentName());

                    // TODO: 2017/12/28 后续任务计算！
                    orders.setRepeatInfo(oldErpOrder.getOrderRepeat() > 0 ? "IP重复订单数量" + oldErpOrder.getOrderRepeat() : "");
                    orders.setIpOrderQty(oldErpOrder.getOrderRepeat());

                    if (erpOrderInfoList != null) {
                        erpOrderInfoList.stream().sorted(new Comparator<ErpOrderInfo>() {
                            @Override
                            public int compare(ErpOrderInfo o1, ErpOrderInfo o2) {
                                return o1.getId() - o2.getId();
                            }
                        }).findFirst().ifPresent(e -> {
                                    orders.setCodeType(getCodeType(e.getVerifyStatus()));
                                    orders.setIp(e.getIp());
                                    orders.setIpName(e.getIpAddress());
                                    orders.setBlackFields(e.getBlacklistField());
                                }
                        );
                    }


                    User user_Seo = getUserId(oldErpOrder.getIdUsers());
                    orders.setSeoUserId(null == user_Seo ? 0 : user_Seo.getId());
                    orders.setSeoUserName(user_Seo == null ? "" : user_Seo.getLastname());
                    orders.setCreateAt(oldErpOrder.getCreatedAt());
                    orders.setUpdateAt(oldErpOrder.getUpdatedAt());
                    User user = getUserId(oldErpOrder.getCreatorId());
                    orders.setCreatorId(null == user ? 0 : user.getId());
                    orders.setCreator(null == user ? "未知" : user.getLastname());
                    ErpDomain erpDomain = erpDomainMap.get(oldErpOrder.getIdDomain());
                    orders.setDomain(erpDomain == null ? "" : erpDomain.getName());
                    orders.setLogisticsId(oldErpOrder.getIdShipping());

                    List<ErpOrderShipping> orderShippingList = erpOrderShippingListMap.get(oldErpOrder.getIdOrder());
                    if (Optional.ofNullable(orderShippingList).isPresent()) {
                        Optional<ErpOrderShipping> e = orderShippingList.stream().sorted(Comparator.comparingInt(ErpOrderShipping::getId)).findFirst();
                        e.ifPresent(s -> {
                            //shipping_name
                            orders.setLogisticsName(s.getShippingName() == null ? "" : s.getShippingName());
                            //track_number
                            orders.setTrackingNo(s.getTrackNumber() == null ? "" : s.getTrackNumber());
                            //track_status_show
                            orders.setTrackingStatusShow(s.getSummaryStatusLabel() == null ? "" : s.getSummaryStatusLabel());
                            //暂时不用
                            orders.setTrackingStatus(0);

                        });
                    }
                    if(StringUtils.isNotBlank(status) && status.equals("24")){
                        //已入转寄仓 直接查找是否有结款记录，如果有则表示签收  如果没有则表示拒收
                        if(settlements.contains(orders.getId())){
                            orders.setState(OrderStateEnum.sign.name());
                        }else{
                            orders.setState(OrderStateEnum.rejection.name());
                        }
                    }
                    if(StringUtils.isNotBlank(status) && (status.equals("4") || status.equals("22"))){
                        if(StringUtils.isEmpty(orders.getTrackingNo())){
                            orders.setState(OrderStateEnum.waitSpecifyLogistic.name());
                        }else{
                            orders.setState(OrderStateEnum.waitDeliver.name());
                        }
                    }
                    if(StringUtils.isNotBlank(status) && (status.equals("23"))){
                        //如果有退货记录则为已签收，否则为已发货
                       int count = erpOrderInfoService.findRecords(oldErpOrder.getIdOrder());
                       if(count>0){
                           orders.setState(OrderStateEnum.sign.name());
                       }else{
                           orders.setState(OrderStateEnum.deliver.name());
                       }
                    }

                    orders.setWarehouseId(oldErpOrder.getIdWarehouse() == null ? 0 : oldErpOrder.getIdWarehouse());
                    ErpWarehouse erpWarehouse = erpWarehouseMap.get(oldErpOrder.getIdWarehouse());
                    orders.setWarehouseName(erpWarehouse == null ? "" : erpWarehouse.getTitle());
                    orders.setWarehouseTypeEnum(getWarehouseEnum(erpWarehouse));
                    orders.setWarehouseMemo("");//废弃
                    orders.setOrderFrom(oldErpOrder.getHttpReferer());
//                    orders.setInvalidReasonType(null);
                    return orders;


                }).collect(Collectors.toList());

        return ordersList;
    }


    private WarehouseTypeEnum getWarehouseEnum(ErpWarehouse erpWarehouse) {
        if (erpWarehouse == null) {
            return WarehouseTypeEnum.normal;
        }
        if (erpWarehouse.getForward() == null) {
            return WarehouseTypeEnum.normal;
        }
        if (erpWarehouse.getForward().equals(0)) {
            return WarehouseTypeEnum.normal;
        }
        return WarehouseTypeEnum.forward;
    }

    private Integer getCodeType(Integer verifyStatus) {
        return Optional.ofNullable(telCodeStateEnumMap.get(verifyStatus)).orElse(TelCodeState.unValidate).ordinal();
    }


    private User getUserId(Integer idUsers) {
        if (null == idUsers) return null;
        User byOldId = userService.getById(idUsers);
        return byOldId;
    }


    private OrderStateEnum getOrderStatusEnum(Integer idOrderStatus) {

//<tr><td>1</td><td>未处理</td></tr>
//<tr><td>2</td><td>待处理</td></tr>
//<tr><td>3</td><td>待审核</td></tr>
//<tr><td>4</td><td>未配货</td></tr>
//<tr><td>5</td><td>配货中</td></tr>
//<tr><td>6</td><td>缺货</td></tr>
//<tr><td>7</td><td>已配货</td></tr>
//<tr><td>8</td><td>配送中</td></tr>
//<tr><td>9</td><td>已签收</td></tr>
//<tr><td>10</td><td>已退货</td></tr>
//<tr><td>11</td><td>重复下单</td></tr>
//<tr><td>12</td><td>信息不完整</td></tr>
//<tr><td>13</td><td>恶意下单</td></tr>
//<tr><td>14</td><td>客户取消</td></tr>
//<tr><td>15</td><td>质量问题/产品破损</td></tr>
//<tr><td>16</td><td>拒收</td></tr>
//<tr><td>17</td><td>部分缺货</td></tr>
//<tr><td>18</td><td>已打包</td></tr>
//<tr><td>19</td><td>理赔</td></tr>
//<tr><td>21</td><td>退货入库</td></tr>
//<tr><td>22</td><td>已审核</td></tr>
//<tr><td>23</td><td>问题件</td></tr>
//<tr><td>24</td><td>已入转寄仓</td></tr>
//<tr><td>25</td><td>匹配转寄中</td></tr>
//<tr><td>26</td><td>已匹配转寄</td></tr>
//<tr><td>27</td><td>转寄完成</td></tr>


        return Optional.ofNullable(orderStateEnumMap.get(idOrderStatus)).orElse(OrderStateEnum.invalidOrder);
    }

    private Integer getPaymentMethod(String paymentMethod) {
        if ("cod".equalsIgnoreCase(paymentMethod)) return PayMethodEnum.cod.ordinal();
        return PayMethodEnum.onlinePay.ordinal();
    }

    @Override
    protected String dataDesc() {
        return "订单表（orders）";
    }

    @Override
    protected int getOldMaxId() {
        return oldErpOrderMapper.getMaxId();
    }

    @Override
    protected int getNewMaxId() {
        return ordersMapper.getMaxOldId();
    }

    @Override
    protected Integer batchInsert(List<Orders> items) {
        return ordersMapper.batchInsert(items);
    }
}
