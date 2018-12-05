package com.stosz.order.service;

import com.google.common.base.Joiner;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.crm.ext.enums.CodeTypeEnum;
import com.stosz.crm.ext.enums.CustomerCreditEnum;
import com.stosz.crm.ext.model.Customers;
import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.dto.FrontOrderDTO;
import com.stosz.order.ext.enums.*;
import com.stosz.order.ext.model.*;
import com.stosz.order.mapper.order.*;
import com.stosz.order.service.crm.CustomerService;
import com.stosz.order.task.AuditTask;
import com.stosz.order.util.IpAddressNameUtil;
import com.stosz.product.ext.model.Currency;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.ICurrencyService;
import com.stosz.product.ext.service.IProductService;
import com.stosz.product.ext.service.IProductSkuService;
import com.stosz.product.ext.service.IZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


/**
 * @author wangqian
 * 建站到订单系统，导入业务及推送业务
 */
@Service
public class FrontOrderService {

    private final Logger logger = LoggerFactory.getLogger(FrontOrderService.class);

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersAdditionMapper ordersAdditionMapper;

    @Autowired
    private OrdersItemsMapper ordersItemsMapper;

    @Autowired
    private OrdersLinkMapper ordersLinkMapper;

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductSkuService productSkuService;

    @Autowired
    private ICurrencyService currencyService;

    @Autowired
    private IZoneService zoneService;

    @Autowired
    private IUserService userService;

    @Autowired
    private BlackListMapper blackListMapper;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private OrderAuditService orderAuditService;

    @Autowired
    private CustomerService customerService;

    @Qualifier("orderFsmProxyService")
    @Autowired
    private FsmProxyService<Orders>  ordersFsmProxyService;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(FrontOrderDTO dto){
        logger.info("========>从建站拉取消息，订单为：{}", dto);
        long start = System.currentTimeMillis();

        //1、订单基本信息
        Orders orders = saveOrder(dto);


        //处理CRM
        Customers customers = handleCustomer(dto,orders);

        //2、订单联系人和CRM客户
        OrdersLink link = saveOrderLink(dto,orders,customers);



        //3、订单项
        List<OrdersItem> items = saveOrderItem(dto,orders);



        //4、订单附加信息
       /* OrdersAddition addition = */saveOrderAddition(dto,orders);


        //5、运算生成重复订单（异步会导致结果有一定误差）
        handleRepeatInfo(dto,orders,items);

        //6、运算生成黑名单信息，已放到订单处理了
//        handleBlackFiled(dto);

        //7、更新ip当天购买量
        handlerIpCount(dto);

        //8、 状态机创建订单
        ordersFsmProxyService.processEvent(orders,OrderEventEnum.create,"建站导入订单");

        //9、自动审单（异步）
        handleAutoAudit(orders,customers);

        long end  = System.currentTimeMillis();
        logger.info("======》已成功获取订单数据，订单编号为：{},  耗时：{}毫秒",dto.getOrder_id(),end - start);
    }

    private void handleAutoAudit(Orders orders, Customers customers) {
        if(CustomerCreditEnum.bad.ordinal() == customers.getLevelEnum()){
            executorService.submit(new AuditTask(orderAuditService,orders,customers));
        }
    }


    private Orders saveOrder(FrontOrderDTO dto){

        Currency currency = currencyService.getByCurrencyCode(dto.getCurrency_code());
        Zone zone = zoneService.getById(dto.getId_zone());
        User user = userService.getByLoginid(dto.getUsername());

        Orders orders = new Orders();
        orders.setMerchantOrderNo(dto.getOrder_id());
        orders.setOrderTypeEnum(OrderTypeEnum.normal.ordinal());
        orders.setZoneId(dto.getId_zone());
        orders.setZoneName(null!=zone?zone.getTitle():"");
        orders.setZoneCode(StringUtils.hasText(dto.getCode_zone()) ? dto.getCode_zone() : "");
        orders.setCurrencyCode(StringUtils.hasText(dto.getCurrency_code()) ? dto.getCurrency_code():"");
        orders.setOrderExchangeRate(currency == null? new BigDecimal(0): currency.getRateCny());
        orders.setOrderAmount(new BigDecimal(String.valueOf(dto.getGrand_total())));
        orders.setConfirmAmount(new BigDecimal(String.valueOf(dto.getGrand_total())));
        String amountShow = "";
        if(currency != null){
            if(StringUtils.hasText(currency.getSymbolLeft())){
                amountShow =  currency.getSymbolLeft() + dto.getGrand_total();
            }else if(StringUtils.hasText(currency.getSymbolRight())){
                amountShow =  dto.getGrand_total() + currency.getSymbolLeft();
            }else{
                amountShow = dto.getGrand_total() + "";
            }
        }
        orders.setAmountShow(amountShow);
        orders.setGoodsQty(dto.getNumber());
        orders.setIp(dto.getIp());
        //计算ip对应的地址
        orders.setIpName(IpAddressNameUtil.getNameByIp(dto.getIp()));
        //计算黑名单字段
        BlackList blackList = handleBlackFiled(dto);
        orders.setBlackFields(blackList == null ? "": blackList.getBlackTypeEnum().display());

        orders.setCustomerMessage(dto.getRemark());
        orders.setMemo("");
        orders.setDomain(dto.getWeb_url());
        orders.setSeoUserName(null!=user?user.getName():"");
        orders.setPurchaserAt(dto.getCreated_at());
        FrontOrderDTO.Web_Info webInfo = dto.getWeb_info();
        orders.setGetCode(StringUtils.hasText(webInfo.getVercode()) ? webInfo.getVercode():"");
        orders.setInputCode(StringUtils.hasText(webInfo.getIncode()) ? webInfo.getIncode():"");
        orders.setOrderStatusEnum(OrderStateEnum.start);



        //事业部部门编号
        Department parentDepartment = departmentService.findParentByDeptId(dto.getOa_id_department());
        orders.setBuDepartmentId(parentDepartment == null? -1 : parentDepartment.getId());

        //业务部  ;
        orders.setSeoDepartmentId(dto.getOa_id_department());
        Department department = departmentService.get(null!=user?user.getDepartmentId():0);
        String departmentUserInfo = department == null ? "" : department.getDepartmentName();
        orders.setDepartmentUserInfo(departmentUserInfo);

        TelCodeState telCodeState = TelCodeState.unValidate;
        if(StringUtils.hasText(webInfo.getIncode())){
            if(orders.getInputCode().trim().equals(orders.getGetCode().trim())){
                telCodeState =  TelCodeState.succ;
            }else{
                telCodeState = TelCodeState.fail;
            }
        }
        orders.setCodeType(telCodeState.ordinal());
        orders.setSeoUserId(dto.getId_users());
        orders.setCreateAt(LocalDateTime.now());
        orders.setUpdateAt(LocalDateTime.now());
        PayMethodEnum payMethod = PayMethodEnum.other;
        if(dto.getPayment_method() == 0){
            payMethod =  PayMethodEnum.cod;
        }else if(dto.getPayment_method() == 1){
            payMethod = PayMethodEnum.onlinePay;
        }
        orders.setPaymentMethod(payMethod.ordinal());
        orders.setOldId(0L);
        orders.setCreator("系统");
        orders.setCreatorId(0);

        orders.setMerchantEnum(MerchantEnum.fromId(dto.getMerchant_enum()));
        orders.setComboId(dto.getCombo_id());
        orders.setComboName(dto.getCombo_name());

        ordersMapper.insert(orders);
        return orders;
    }

    private OrdersLink saveOrderLink(FrontOrderDTO dto, Orders order,Customers customers){

        OrdersLink ordersLink = new OrdersLink();
        ordersLink.setFirstName(dto.getFirst_name() == null ? "" : dto.getFirst_name());
        ordersLink.setLastName(dto.getLast_name() == null ? "" : dto.getLast_name());
        ordersLink.setCountry(dto.getCountry());
        ordersLink.setTelphone(dto.getTel());
        ordersLink.setEmail(dto.getEmail() == null ? null : dto.getEmail());
        ordersLink.setProvince(dto.getProvince());
        ordersLink.setCity(dto.getCity());
        ordersLink.setArea(dto.getArea() == null ? "" : dto.getArea());
        ordersLink.setAddress(dto.getAddress());
        ordersLink.setZipcode(dto.getZipcode() == null ? "" : dto.getZipcode());
        ordersLink.setCustomerId(Long.valueOf(customers.getId()));
        ordersLink.setOrdersId(Long.valueOf(order.getId()));
        ordersLink.setCreateAt(LocalDateTime.now());
        ordersLink.setUpdateAt(LocalDateTime.now());
        ordersLink.setCreatorId(0);
        ordersLink.setCreator("系统");
        ordersLinkMapper.insert(ordersLink);
        return ordersLink;
    }


    private Customers handleCustomer(FrontOrderDTO dto,Orders orders){
        Customers customer = new Customers();
        customer.setState(UseableEnum.use.ordinal());
        customer.setOrderId(Long.valueOf(orders.getId()));
        customer.setTelphone(dto.getTel());
        customer.setFirstName(dto.getFirst_name() == null ? "" : dto.getFirst_name());
        customer.setLastName(dto.getLast_name() == null ? "" : dto.getLast_name());
        customer.setCountry(dto.getCountry() == null ? "":dto.getCountry());
        customer.setEmail(dto.getEmail() == null ? "" : dto.getEmail());
        customer.setAddress(dto.getAddress() == null ? "" : dto.getAddress());
        customer.setZoneId(dto.getId_zone());
        customer.setZoneCode("");
        customer.setZipcode(dto.getZipcode() == null ? "" : dto.getZipcode());
        customer.setLevelEnum(CustomerCreditEnum.normal.ordinal());
        customer.setProvince(dto.getProvince() == null ? "" : dto.getProvince());
        customer.setCity(dto.getCity() == null ? "" : dto.getCity());
        customer.setArea(dto.getArea() == null ? "" : dto.getArea());
        customer.setRejectQty(0);
        customer.setAcceptQty(0);
        customer.setSetLevelEnum(0);
        FrontOrderDTO.Web_Info webInfo = dto.getWeb_info();
        CodeTypeEnum codeTypeEnum = CodeTypeEnum.no;
        if(StringUtils.hasText(webInfo.getIncode())){
            if(webInfo.getVercode().trim().equals(webInfo.getIncode().trim())){
                codeTypeEnum = CodeTypeEnum.right;
            }else{
                codeTypeEnum = CodeTypeEnum.wrong;
            }
        }
        customer.setCodeType(codeTypeEnum.ordinal());
        customer.setCreateAt(LocalDateTime.now());
        customer.setUpdateAt(LocalDateTime.now());
        customer.setCreatorId(0);
        customer.setCreator("订单导出创建");
        customer.setMemo("");
        Customers newCustomer = customerService.queryCustomersForInsert(customer);
        return newCustomer;
    }

    private List<OrdersItem> saveOrderItem(FrontOrderDTO dto,Orders order){
        if(dto.getProducts() == null){
            return null;
        }
        List<OrdersItem> ordersItemList = new ArrayList<>(4);
        for (FrontOrderDTO.OrderData orderData: dto.getProducts()){
            Product product = productService.getProductInfoById(orderData.getId_product());
            String sku = productSkuService.getSkuByProductRelId(orderData.getId_product(),orderData.getAttrs());
            OrdersItem orderItem = new OrdersItem();
            orderItem.setOrdersId(Long.valueOf(order.getId()));
            orderItem.setSku(sku);
            orderItem.setSpu(product.getSpu());
            orderItem.setQty(orderData.getQty());
            orderItem.setActivityId(0);
            orderItem.setSingleAmount(new BigDecimal(orderData.getPromotion_price()));
            orderItem.setTotalAmount(new BigDecimal(orderData.getPrice()));
            orderItem.setForeignTotalAmount(orderData.getPrice_title());
            orderItem.setProductId(Long.valueOf(orderData.getId_product()));
            orderItem.setProductTitle(orderData.getProduct_title());
            orderItem.setForeignTitle(orderData.getSale_title());
            orderItem.setAttrTitleArray(Joiner.on("^").join(orderData.getAttrs_title()));
//            orderItem.setAttrNameArray(orderData.getAttrs_title().toString());
//            orderItem.setAttrValueArray(orderData.getAttrs().toString());
            orderItem.setItemStatusEnum(ItemStatusEnum.unAudit.ordinal());
            orderItem.setProductImageUrl(product.getMainImageUrl());
            orderItem.setCreateAt(LocalDateTime.now());
            orderItem.setUpdateAt(LocalDateTime.now());
            orderItem.setCreatorId(0);
            orderItem.setCreator("系统");
            ordersItemList.add(orderItem);
        }
        ordersItemsMapper.batchInsert(ordersItemList);
        return ordersItemList;
    }

    private OrdersAddition saveOrderAddition(FrontOrderDTO dto, Orders order){
        OrdersAddition ordersAddition = new OrdersAddition();
        FrontOrderDTO.Web_Info webInfo = dto.getWeb_info();

        if(webInfo != null){
            ordersAddition.setEquipment(webInfo.getDevice() == null ? " ": webInfo.getDevice());
            FrontOrderDTO.HttpHeads httpHeads = webInfo.getHttpHeads();
            if(httpHeads != null){
                ordersAddition.setOrderFrom(httpHeads.getReferer() == null ? "" : httpHeads.getReferer());
            }
        }

        ordersAddition.setBrowser(dto.getUser_agent() == null ? "" :dto.getUser_agent());
        ordersAddition.setBrowseSecond(ordersAddition.getBrowseSecond());
        ordersAddition.setUserAgent(dto.getUser_agent() == null ? "" :dto.getUser_agent());
        ordersAddition.setOrdersId(Long.valueOf(order.getId()));
        ordersAddition.setIp(dto.getIp());
        ordersAddition.setIpName(order.getIpName());
        ordersAddition.setWebUrl(dto.getWebsite());
        ordersAddition.setWebDomain(dto.getWeb_url());
        ordersAddition.setCreateAt(LocalDateTime.now());
        ordersAddition.setUpdateAt(LocalDateTime.now());
        ordersAddition.setCreatorId(0);
        ordersAddition.setCreator("系统");
        ordersAdditionMapper.insert(ordersAddition);
        return ordersAddition;
    }

    //更新ip当天购买量
    private void handlerIpCount(FrontOrderDTO dto){
        List<Orders> ordersList = ordersMapper.findByIpAfterBetweenCreateAt(dto.getIp(), LocalDate.now().atTime(0,0,0),LocalDateTime.now());
        if(ordersList == null || ordersList.size() == 0){
            return ;
        }
        List<Integer> orderIds = ordersList.stream().map(e -> e.getId()).collect(Collectors.toList());
        ordersMapper.updateIpIOrderQtyByOrderId(orderIds, orderIds.size());
    }


   private void handleRepeatInfo(FrontOrderDTO dto,Orders orders ,List<OrdersItem> items){
       List<Orders> repeatOrder = ordersMapper.findByIpAndDomainBetweenCreateAt(dto.getIp(),dto.getWeb_url(),LocalDateTime.now().minusMonths(2),LocalDateTime.now());
       String repeatInfo = "";
       if(repeatOrder != null && repeatOrder.size() > 1){//不包括刚插入的
           repeatInfo = "域名+IP";
       }else{
           List<String> skuList = items.stream().map(i -> i.getSku()).collect(Collectors.toList());
           Map<String,Object> map = new HashMap<>();
//           @Param("tel") String tel, @Param("skuList") List<String> skuList, @Param("beginAt") LocalDateTime beginAt, @Param("endAt") LocalDateTime endAt
           map.put("tel",dto.getTel());
           map.put("skuList",skuList);
           map.put("beginAt", LocalDateTime.now().minusMonths(2));
           map.put("endAt",LocalDateTime.now());
           List<OrdersLink> ordersLinks = ordersLinkMapper.findByTelAndSkuListBetweenCreateAt(map);
           if(ordersLinks != null && ordersLinks.size() != 0){
               repeatInfo = "手机号+SKU";
           }
       }
       //更新当前订单的重复信息
       ordersMapper.updateRepeatInfoByOrderId(orders.getId(),repeatInfo);
    }

    private BlackList handleBlackFiled(FrontOrderDTO dto) {
        String name = Optional.of(dto.getFirst_name()).orElse("").trim() + Optional.ofNullable(dto.getLast_name()).orElse("").trim();
        return blackListMapper.findByIpOrTelOrEmailOrAddrOrName(dto.getIp(),dto.getTel(),dto.getEmail(),dto.getAddress(),name);
    }

}
