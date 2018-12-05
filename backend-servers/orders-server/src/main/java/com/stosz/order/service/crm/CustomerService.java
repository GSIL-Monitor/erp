package com.stosz.order.service.crm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stosz.order.ext.enums.OrderStateEnum;

import com.stosz.crm.ext.CustomerSearchParam;
import com.stosz.crm.ext.enums.CustomerCreditEnum;
import com.stosz.crm.ext.model.CustomerApiLog;
import com.stosz.crm.ext.model.CustomerOrders;
import com.stosz.crm.ext.model.CustomerUpdateLog;
import com.stosz.crm.ext.model.Customers;
import com.stosz.order.ext.enums.TelCodeState;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.mapper.crm.CustomerApiLogMapper;
import com.stosz.order.mapper.crm.CustomerUpdateLogMapper;
import com.stosz.order.mapper.crm.CustomersMapper;
import com.stosz.order.service.OrderService;
import com.stosz.order.service.OrdersLinkService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerService{

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomersMapper customersMapper;

    @Autowired
    private IZoneService zoneServicee;

    @Autowired
    private CustomerUpdateLogMapper customerUpdateLogMapper;

    @Autowired
    private CustomerApiLogMapper customerApiLogMapper;


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrdersLinkService ordersLinkService;

    /**
     * 通过订单信息获取用户信息（通过手机号判断用户）
     * 1.当用户存在时直接返回用户信息
     * 2.当用户不存在时，由订单信息生成一条新的用户，并返回用户信息。
     * @param customer
     * @return
     */
    public Customers queryCustomersForInsert(Customers customer){

        Customers existCustomer = customersMapper.getByTelphone(customer.getTelphone());
        //用户不存在则创建用户
        if(existCustomer == null){
            customer.setRejectQty(0);
            customer.setAcceptQty(0);
            customer.setLevelEnum(CustomerCreditEnum.normal.ordinal());
            customer.setArea("");
            customer.setMemo("");
            //0 系统 1 用户
            customer.setSetLevelEnum(0);
            customer.setCreateAt(LocalDateTime.now());
            customer.setUpdateAt(LocalDateTime.now());
            try{
                customersMapper.insert(customer);
                logger.info("添加客户信息成功：{}",customer);
            }catch (DuplicateKeyException e){
               //并发查找未存在客户时。
                customer = customersMapper.getByTelphone(customer.getTelphone());
            }
            return customer;
        }
        return existCustomer;
    }

    public int add(Customers customers){
        customers.setCreatorId(MBox.getLoginUserId());
        customers.setCreateAt(LocalDateTime.now());
        Zone zone = zoneServicee.getByCode(customers.getZoneCode());
        if(zone != null){
            customers.setArea(zone.getTitle());
        }
        //添加时，拒收签收次数默认都为0
        customers.setRejectQty(0);
        customers.setAcceptQty(0);
        int count =  customersMapper.insert(customers);
        logger.info("添加客户信息成功：{}",customers);
        return count;
    }

    public void insertApiLog(CustomerApiLog log){
        customerApiLogMapper.insert(log);
    }

    public int update(Customers customers){
        int count = customersMapper.update(customers);
        logger.info("编辑客户信息成功：{}",customers);
        return count;
    }

    public void saveUpdateLog(CustomerUpdateLog param){
        customerUpdateLogMapper.insert(param);
        logger.info("保存更新日志成功：{}",param);
    }


    public List<CustomerUpdateLog> findUpdateLog(Integer id){
       return customerUpdateLogMapper.findByCustomerId(id);
    }

    public int delete(Integer customerId){
        Customers customers = customersMapper.getById(customerId);
        Assert.notNull(customers,"客户信息不存在");
        int count = customersMapper.delete(customerId);
        logger.info("删除客户信息成功：{}",customers);
        return count;
    }

    public int enable(Integer customerId){
        Customers customers = customersMapper.getById(customerId);
        Assert.notNull(customers,"客户信息不存在");
        customers.setState(customers.getState()==0 ? 1 : 0);
        customers.setUpdateAt(LocalDateTime.now());
        int count = customersMapper.update(customers);
        logger.info("客户状态修改成功：{}",customers);
        return count;
    }

    /**
     * 通过用户ID查找更新的记录
     * @param customerId
     * @return
     */
    public List<CustomerUpdateLog> findUpdateLogByCustomerId(Integer customerId){
        return customerUpdateLogMapper.findByCustomerId(customerId);
    }


    public RestResult findCustomers(CustomerSearchParam param){
        RestResult rs = new RestResult();
        PageHelper.offsetPage(param.getStart(),param.getLimit());
        List<Customers> lst = customersMapper.findCustomers(param);
        PageInfo<Customers> pageInfo = new PageInfo<>(lst);
        rs.setItem(lst);
        rs.setTotal((int) pageInfo.getTotal());
        rs.setDesc("客户列表查询成功");
        return rs;
    }

    public Customers getById(int id){
        return customersMapper.getById(id);
    }

    public Customers getByTelphone(String tel){
        return customersMapper.getByTelphone(tel);
    }

    public int insertBatchDuplicateKeyUpdate(List<Customers>list){
        if(list == null || list.size() == 0){
            return 0;
        }
        return customersMapper.insertBatchDuplicateKeyUpdate(list);
    }

    public RestResult findCustomerOrdersByTel(String tel,Integer start, Integer limit){
        RestResult result = new RestResult();

        PageHelper.offsetPage(start,limit);
        List<OrdersLink> ordersLinkList = ordersLinkService.findByTel(tel);
        PageInfo<OrdersLink> pageInfo = new PageInfo<>(ordersLinkList);

        List<Integer> orderIds = ordersLinkList.stream().map(e -> e.getOrdersId().intValue()).collect(Collectors.toList());
        Map<Integer,Orders> ordersMap = orderService.findOrderByOrderIds(orderIds).stream().collect(Collectors.toMap(Orders::getId, Function.identity()));
        List<CustomerOrders> customerOrdersList = new ArrayList<>();
        ordersLinkList.stream().forEach(link-> {
            CustomerOrders co = new CustomerOrders();
            co.setOrderId(link.getOrdersId().intValue());
            co.setFirstName(link.getFirstName());
            co.setLastName(link.getLastName());
            co.setTelphone(link.getTelphone());
            co.setAddress(link.getAddress());
//            TelCodeState.fromId(o.getCodeType())
            Orders order = ordersMap.get(link.getId());
            Optional.ofNullable(order).ifPresent( o -> {
                String state = Optional.ofNullable(o.getOrderStatusEnum())
                        .map(e -> e.display())
                        .orElse("");
                co.setState(state);

                String codeType = Optional.ofNullable(o.getCodeType())
                        .map(a -> TelCodeState.fromId(a))
                        .map(TelCodeState::display)
                        .orElse("");
                co.setCodeType(codeType);

            });
            customerOrdersList.add(co);
        });
        result.setTotal((int) pageInfo.getTotal());
        result.setItem(customerOrdersList);
        return result;
    }



}
