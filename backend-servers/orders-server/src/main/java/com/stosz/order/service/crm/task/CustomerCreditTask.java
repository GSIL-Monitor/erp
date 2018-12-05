package com.stosz.order.service.crm.task;


import com.stosz.crm.ext.enums.CustomerCreditEnum;
import com.stosz.crm.ext.enums.OrderStatusEnum;
import com.stosz.crm.ext.enums.StateEnum;
import com.stosz.crm.ext.model.CustomerOrderLog;
import com.stosz.crm.ext.model.Customers;
import com.stosz.olderp.ext.model.OldErpOrderLink;
import com.stosz.order.mapper.crm.CustomerOrderLogMapper;
import com.stosz.order.service.crm.CustomerService;
import com.stosz.order.util.OldErpOrderUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangqian
 * 客户评级任务
 */
public class CustomerCreditTask implements Runnable {

    /**
     * 未被处理的订单集合
     */
    private List<OldErpOrderLink> linkList;

    /**
     *  已经被处理过的订单集合
     */
    private  List<CustomerOrderLog> customerOrderLogList;

    private CustomerService customerService;

    private CustomerOrderLogMapper customerOrderLogMapper;

    public CustomerCreditTask(CustomerService customerService, CustomerOrderLogMapper customerOrderLogMapper, List<OldErpOrderLink> linkList, List<CustomerOrderLog> logs) {
        this.customerService = customerService;
        this.linkList = linkList;
        this.customerOrderLogList = logs;
        this.customerOrderLogMapper = customerOrderLogMapper;
    }

    @Override
    public void run() {

        //已有被处理过的恶意订单
        Set<Integer> badSet = customerOrderLogList.stream().filter(e->e.getState()==1).map(e->e.getOrderId()).collect(Collectors.toSet());

        //已被处理过的签收订单
        Set<Integer> goodSet = customerOrderLogList.stream().filter(e->e.getState()==0).map(e->e.getOrderId()).collect(Collectors.toSet());


        List<Customers> customersList = new LinkedList<>();
        //要新插入的恶意订单数
        List<CustomerOrderLog> customerOrderLogs = new LinkedList<>();
        for (OldErpOrderLink link : linkList) {
            Customers customers = new Customers();
            customers.setFirstName(link.getFirstName());
            customers.setLastName(link.getLastName());
            customers.setArea(link.getArea());
            customers.setCity(link.getCity());
            customers.setCountry(link.getCountry());
            customers.setEmail(link.getEmail());
            customers.setProvince(link.getProvince());
            customers.setTelphone(link.getTel());
            customers.setZoneId(link.getIdZone());
            customers.setAddress(link.getAddress());
            customers.setState(StateEnum.usable.ordinal());
            customers.setLevelEnum(CustomerCreditEnum.normal.ordinal());
            if (OldErpOrderUtil.getOrderType(link.getIdOrderStatus()) == OrderStatusEnum.sign) {
                if(goodSet.contains(link.getIdOrder())){
                    continue;
                }
                //签收
                customers.setAcceptQty(1);
                customers.setRejectQty(0);
                CustomerOrderLog log = new CustomerOrderLog();
                log.setOrderId(link.getIdOrder());
                log.setTel(link.getTel());
                log.setState(0);
                customerOrderLogs.add(log);
            } else if (OldErpOrderUtil.getOrderType(link.getIdOrderStatus()) == OrderStatusEnum.reject) {
                if(badSet.contains(link.getIdOrder())){
                    continue;
                }
                //拒签或者退货
                customers.setAcceptQty(0);
                customers.setRejectQty(1);
                // TODO: 2017/11/17  当是从到退货这个转变，需要把签收次数减1 ？

                CustomerOrderLog log = new CustomerOrderLog();
                log.setOrderId(link.getIdOrder());
                log.setTel(link.getTel());
                log.setState(1);
                customerOrderLogs.add(log);
            } else {
                //其它状态不处理，将其添加到信息中
                customers.setAcceptQty(0);
                customers.setRejectQty(0);
            }
            customersList.add(customers);

        }
        //将没被处理过的联系人批量插入客户表
        if(customersList != null && customersList.size() != 0){
           customerService.insertBatchDuplicateKeyUpdate(customersList);
        }

        //将新产生的新纪录的订单插入历史操作表【签收，拒签，拒收】
        if(customerOrderLogs != null && customerOrderLogs.size() != 0){
            customerOrderLogMapper.insertBatch(customerOrderLogs);
        }

    }

}
