package com.stosz.order.sync.olderp;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.stosz.olderp.ext.model.ErpDomain;
import com.stosz.olderp.ext.model.ErpOrderInfo;
import com.stosz.olderp.ext.model.ErpOrderItem;
import com.stosz.olderp.ext.model.OldErpOrder;
import com.stosz.olderp.ext.service.IErpDomainService;
import com.stosz.olderp.ext.service.IErpOrderInfoService;
import com.stosz.olderp.ext.service.IErpOrderItemService;
import com.stosz.olderp.ext.service.IOldErpOrderService;
import com.stosz.order.ext.enums.BlackLevelEnum;
import com.stosz.order.ext.enums.BlackTypeEnum;
import com.stosz.order.ext.enums.ItemStatusEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersAddition;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.mapper.order.OrdersAdditionMapper;
import com.stosz.order.mapper.order.OrdersItemsMapper;
import com.stosz.order.mapper.order.OrdersLinkMapper;
import com.stosz.order.service.OrderService;
import com.stosz.order.sync.olderp.core.AbstractSycn;
import com.stosz.plat.utils.CommonUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.service.IProductService;
import com.stosz.product.ext.service.IProductSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @auther carter
 * create time    2017-11-14
 */
@Component
public class SyncOrderOther extends AbstractSycn<OldErpOrder> {

    private static final Logger logger = LoggerFactory.getLogger(SyncOrderOther.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdersLinkMapper ordersLinkMapper;
    @Autowired
    private OrdersItemsMapper ordersItemsMapper;
    @Autowired
    private OrdersAdditionMapper orderAddtionsMapper;

    @Resource
    private IOldErpOrderService oldErpOrderService;
    @Resource
    private IErpOrderInfoService erpOrderInfoService;
    @Resource
    private IErpDomainService erpDomainService;
    @Resource
    private IErpOrderItemService erpOrderItemService;
    @Resource
    private IProductService productService;
    @Resource
    private IProductSkuService productSkuService;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    public AtomicBoolean getIsRunning() {
        return isRunning;
    }


    /**
     * 按照id范围抓取
     *
     * @param idMin id最小值
     * @param idMax id最大值
     * @return
     */
    @Override
    protected List<OldErpOrder> fetchByIdRegion(int idMin, int idMax) {
        List<OldErpOrder> oldErpOrders = oldErpOrderService.fetchByIdRegion(idMin, idMax);
        return oldErpOrders;
    }


    @Override
    protected String dataDesc() {
        return "订单表关联信息（order_*）";
    }

    @Override
    protected int getOldMaxId() {
        return oldErpOrderService.getMaxId();
    }

    @Override
    protected int getNewMaxId() {
        return ordersLinkMapper.getNewMaxId();
    }

    private static BlackLevelEnum getBlackLevel(String levelString) {
        if ("1".equalsIgnoreCase(levelString)) return BlackLevelEnum.warning;
        if ("10".equalsIgnoreCase(levelString)) return BlackLevelEnum.blacklist;
        return BlackLevelEnum.other;
    }

    public void start() {
        if (!isRunning.get()) {
            long startTime = System.currentTimeMillis();
            try {
                isRunning.set(true);
                for (int i = 0; i < 3000; i++) {
                    setFetch_count(2000);
                    boolean finish = super.pullById();
                    if (!finish) {
                        break;
                    }
                    if (!isRunning.get()) {
                        break;
                    }
                }
            } catch (Exception e) {
                logger.error("同步订单附加信息出现错误：" + e.getMessage(),e);
            } finally {
                isRunning.set(false);
            }
            long endTime = System.currentTimeMillis();
            logger.info("订单杂项本次同步耗时（秒）：{}", (endTime - startTime) / 1000);
        }
    }

    /*@Scheduled(fixedRate = 30 * 1000)*/
    public void sycn() {
        setFetch_count(10000);
        super.pullById();
    }

    private BlackTypeEnum getBlackType(String field) {
        if (Strings.isNullOrEmpty(field)) return BlackTypeEnum.other;
        if ("email".equalsIgnoreCase(field) || field.contains("@")) return BlackTypeEnum.email;
        if ("ip".equalsIgnoreCase(field) || field.contains(".")) return BlackTypeEnum.ip;
        if ("phone".equalsIgnoreCase(field) || field.contains("phone")) return BlackTypeEnum.phone;
        return BlackTypeEnum.address;
    }

    @Override
    protected Integer batchInsert(List<OldErpOrder> oldErpOrders) {

        //新erp订单信息
        List<Integer> oldErpOrderIdList = oldErpOrders.stream().map(e -> e.getIdOrder()).collect(Collectors.toList());
        Map<Long, Orders> ordersMap = orderService.findByOldIds(oldErpOrderIdList).stream().collect(Collectors.toMap(Orders::getOldId, Function.identity(), (key1, key2) -> key2));


        if (ordersMap.isEmpty()) {
            logger.info("新erp已有orders表数据关联项已同步完毕！");
            return 0;
        }

        //ordersItem
        Map<Integer, List<ErpOrderItem>> erpOrderItemMap = erpOrderItemService.findByIds(oldErpOrderIdList).stream().collect(Collectors.groupingBy(ErpOrderItem::getIdOrder));

        //老erp OrderInfo
        Map<Integer, ErpOrderInfo> erpOrderInfoMap = erpOrderInfoService.findByIds(oldErpOrderIdList).stream().collect(Collectors.toMap(ErpOrderInfo::getIdOrder, Function.identity(), (key1, key2) -> key2));

        //域名
        List<Integer> oldErpDomainIdList = oldErpOrders.stream().map(e -> e.getIdDomain()).collect(Collectors.toList());
        Map<Integer, ErpDomain> erpDomainMap = erpDomainService.findByIds(oldErpDomainIdList).stream().collect(Collectors.toMap(ErpDomain::getIdDomain, Function.identity()));


        //orderLink
        OrderOther orderOther = new OrderOther();

        for (OldErpOrder oldErpOrder : oldErpOrders) {

            //order
            Integer idOrder_olderp = oldErpOrder.getIdOrder();
            //通过老erp orderId 获取新erp order

            Orders orders = ordersMap.get(Long.valueOf(idOrder_olderp));

            if (null != orders && orders.getId() > 0) {

                Long orderId_newerp = Long.valueOf(orders.getId());

                //ordersItem
                List<ErpOrderItem> erpOrderItemList = Optional.ofNullable(erpOrderItemMap.get(idOrder_olderp)).orElse(new ArrayList<>(0));


                if (erpOrderItemList.isEmpty()) {
                    logger.info("该订单没有订单项：{}", idOrder_olderp);
                } else {


//                StringBuffer stringBuffer = new StringBuffer();
                    //拼接订单的名称；
//                erpOrderItemList.stream().forEach(item -> stringBuffer.append(item.getProductTitle() + "X" + item.getQuantity()).append("<br>"));
//
//                if (!Strings.isNullOrEmpty(stringBuffer.toString())) {
//                    ordersMapper.updateTitle(stringBuffer.toString(), orderId_newerp);
//                }

                    // 批量
                    List<String> skuList = erpOrderItemList.stream().map(e -> e.getSku()).collect(Collectors.toList());
                    Map<String, String> attrValueTitleMap = productSkuService.findAttrValueTitleBySku(skuList);

                    List<Integer> productIdList = erpOrderItemList.stream().map(e -> e.getIdProduct()).collect(Collectors.toList());
                    Map<Integer, Product> productMap = productService.getByIds(productIdList).stream().collect(Collectors.toMap(Product::getId, Function.identity()));

                    //用于排除同一订单中不同订单项相同sku
                    Map<String, OrdersItem> singleSkuItemMap = new HashMap<>(erpOrderItemList.size());
                    for (ErpOrderItem erpOrderItem : erpOrderItemList) {

                        OrdersItem t = singleSkuItemMap.get(erpOrderItem.getSku());
                        if (Objects.nonNull(t)) {//有重复sku，进行合并(总价和数量累加)
                            t.setTotalAmount(erpOrderItem.getTotal().multiply(t.getTotalAmount()));
                            t.setQty(erpOrderItem.getQuantity() + t.getQty());
                            continue;
                        } else {
                            OrdersItem orderItems = new OrdersItem();


                            orderItems.setOrdersId(orderId_newerp);


                            orderItems.setQty(erpOrderItem.getQuantity());
                            orderItems.setActivityId(0);//todo
                            orderItems.setSingleAmount(erpOrderItem.getPrice());


                            orderItems.setProductTitle(erpOrderItem.getProductTitle());
                            orderItems.setForeignTitle(erpOrderItem.getSaleTitle());

                            orderItems.setTotalAmount(erpOrderItem.getTotal());// 订单项总价 需要计算
//                    orderItems.setForeignTotalAmount(String.valueOf(erpOrderItem.getTotal()));// 带符号的总价
//                    orderItems.setAttrNameArray(erpOrderItem.getAttrsTitle());
//                    orderItems.setAttrValueArray(erpOrderItem.getAttrs());
                            orderItems.setItemStatusEnum(ItemStatusEnum.unAudit.ordinal());//todo 订单项状态（配货用，暂时给默认值）


                            String attrValueTitle = attrValueTitleMap.get(erpOrderItem.getSku());
                            orderItems.setAttrTitleArray(attrValueTitle == null ? "" : attrValueTitle);


                            // 产品中心 product id跟老erp product id 一致
                            Product product = productMap.get(erpOrderItem.getIdProduct());

                            orderItems.setProductId(Long.valueOf(erpOrderItem.getIdProduct()));
                            orderItems.setProductImageUrl(product == null ? "" : product.getMainImageUrl());
                            orderItems.setSpu(product == null ? "" : product.getSpu());
                            orderItems.setSku(CommonUtils.getStringValue(erpOrderItem.getSku(), ""));
                            orderItems.setInnerTitle(null == product || null == product.getInnerName() ? "" : product.getInnerName());
                            orderItems.setCreateAt(oldErpOrder.getCreatedAt());
                            orderItems.setUpdateAt(oldErpOrder.getUpdatedAt());
                            orderItems.setCreatorId(orders.getCreatorId());
                            orderItems.setCreator(orders.getCreator());

                            singleSkuItemMap.put(erpOrderItem.getSku(), orderItems);
                        }
                    }

                    if (!singleSkuItemMap.entrySet().isEmpty())
                        orderOther.getOrderItemsList().addAll(singleSkuItemMap.values());
                }

                ErpOrderInfo erpOrderInfo = Optional.ofNullable(erpOrderInfoMap.get(idOrder_olderp)).orElse(new ErpOrderInfo());

                //ordersLink
                OrdersLink ordersLink = new OrdersLink();
                ordersLink.setFirstName(oldErpOrder.getFirstName());
                ordersLink.setLastName(oldErpOrder.getLastName());
                ordersLink.setCountry(oldErpOrder.getCountry());
                ordersLink.setTelphone(oldErpOrder.getTel());
                ordersLink.setEmail(oldErpOrder.getEmail());
                ordersLink.setProvince(oldErpOrder.getProvince());
                ordersLink.setCity(oldErpOrder.getCity());
                ordersLink.setArea(oldErpOrder.getArea());
                ordersLink.setAddress(oldErpOrder.getAddress());
                ordersLink.setZipcode(oldErpOrder.getZipcode());
                ordersLink.setBlackLevelEnum(getBlackLevel(erpOrderInfo.getBlacklistLevel()).ordinal());//新erp已废弃
                ordersLink.setBlackTypeEnum(getBlackType(erpOrderInfo.getBlacklistField()).ordinal());//
                ordersLink.setOrdersId(orderId_newerp);

                ordersLink.setCustomerId(0L);//todo  跟crm沟通后面进行更新

                ordersLink.setCreateAt(oldErpOrder.getCreatedAt());
                ordersLink.setUpdateAt(oldErpOrder.getUpdatedAt());
                ordersLink.setCreatorId(orders.getCreatorId());
                ordersLink.setCreator(orders.getCreator());

                ordersLink.setOldId(Long.valueOf(idOrder_olderp));
                orderOther.getOrdersLinkList().add(ordersLink);


                //orderAddition
                OrdersAddition ordersAddition = new OrdersAddition();
                ordersAddition.setBrowser("");//todo 解析
                ordersAddition.setEquipment("");//todo
                ordersAddition.setBrowseSecond(0);//todo
                ordersAddition.setUserAgent(erpOrderInfo.getUserAgent());
                ordersAddition.setOrderFrom(oldErpOrder.getHttpReferer());
                ordersAddition.setOrdersId(orderId_newerp);
                ordersAddition.setIp(erpOrderInfo.getIp());
                ordersAddition.setIpName(erpOrderInfo.getIpAddress());
                ordersAddition.setBlackLevelEnum(getBlackLevel(erpOrderInfo.getBlacklistLevel()).ordinal());//新erp已废弃
                ordersAddition.setBlackTypeEnum(getBlackType(erpOrderInfo.getBlacklistField()).ordinal());//


                ErpDomain erpDomain = Optional.ofNullable(erpDomainMap.get(oldErpOrder.getIdDomain())).orElse(new ErpDomain());

                // 切分"http://"
                ordersAddition.setWebUrl(formatUrl(erpDomain.getRealAddress()));// 二级域名
                ordersAddition.setWebDomain(formatUrl(erpDomain.getName()));//域名信

                ordersAddition.setCreateAt(oldErpOrder.getCreatedAt());
                ordersAddition.setUpdateAt(oldErpOrder.getUpdatedAt());
                ordersAddition.setCreatorId(orders.getCreatorId());
                ordersAddition.setCreator(orders.getCreator());
                orderOther.getOrdersAdditionList().add(ordersAddition);

            } else {
                logger.info("订单异常！新erp表中没有对应的old_id：{}", idOrder_olderp);
            }
        }

        int n1 = 0;

        if (!orderOther.getOrdersLinkList().isEmpty())
            n1 = ordersLinkMapper.batchInsert(orderOther.getOrdersLinkList());
        if (!orderOther.getOrdersAdditionList().isEmpty())
            n1 += orderAddtionsMapper.batchInsert(orderOther.getOrdersAdditionList());
        if (!orderOther.getOrderItemsList().isEmpty())
            n1 += ordersItemsMapper.batchInsert(orderOther.getOrderItemsList());

        return n1;
    }

    private String formatUrl(String str) {
        if (StringUtils.isNotBlank(str)) {
            String strNew;
            if (str.indexOf("http://") > -1) {
                strNew = StringUtils.substringAfter(str, "http://");
            } else {
                strNew = str;
            }
            if ('/' == strNew.charAt(strNew.length() - 1)) {
                strNew = strNew.substring(0, strNew.length() - 1);
            }
            return strNew;
        }
        return "";
    }

    class OrderOther {
        private List<OrdersLink> ordersLinkList = Lists.newLinkedList();
        private List<OrdersAddition> ordersAdditionList = Lists.newLinkedList();
        private List<OrdersItem> orderItemsList = Lists.newLinkedList();

        public List<OrdersItem> getOrderItemsList() {
            return orderItemsList;
        }

        public void setOrderItemsList(List<OrdersItem> orderItemsList) {
            this.orderItemsList = orderItemsList;
        }

        public List<OrdersLink> getOrdersLinkList() {
            return ordersLinkList;
        }

        public void setOrdersLinkList(List<OrdersLink> ordersLinkList) {
            this.ordersLinkList = ordersLinkList;
        }

        public List<OrdersAddition> getOrdersAdditionList() {
            return ordersAdditionList;
        }

        public void setOrdersAdditionList(List<OrdersAddition> ordersAdditionList) {
            this.ordersAdditionList = ordersAdditionList;
        }
    }

}
