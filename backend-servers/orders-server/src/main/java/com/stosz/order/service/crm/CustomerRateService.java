package com.stosz.order.service.crm;

import com.stosz.crm.ext.model.CustomerOrderLog;
import com.stosz.crm.ext.model.SystemTimeDict;
import com.stosz.crm.ext.service.ICustomerRateService;
import com.stosz.olderp.ext.model.OldErpOrderLink;
import com.stosz.olderp.ext.service.IOldErpOrderService;
import com.stosz.order.mapper.crm.CustomerOrderLogMapper;
import com.stosz.order.mapper.crm.CustomersMapper;
import com.stosz.order.service.crm.task.CustomerCreditTask;
import com.stosz.order.util.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wangqian
 */
@Service
public class CustomerRateService implements ICustomerRateService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRateService.class);


    /**
     * 分段抓取DB数据，每次抓取的个数
     */
    private static final int SIZE = 5000;

    /**
     * 批量插入时，每次插入条数
     */
    private static final int INSERT_SIZE = 100;

    @Autowired
    private IOldErpOrderService oldErpOrderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomersMapper customersMapper;

    @Autowired
    private CustomerOrderLogMapper customerOrderLogMapper;



    /**
     * 用户评级
     * 拒收(16,24)
     * 退货(10,21)
     * 签收（9）
     *
     * 算法：从system_time_dict获取上次处理的时间作为开始时间（beginTime）（如果不存在则取无穷小，相当于全量更新），以当前时间作为结束时间（endTime）进行增量更新。
     * 取出（beginTime，endTime]的所有订单，每次查询SIZE条，然后将SIZE分批插入联系人表。
     * 因为订单状态会发生转变16-->24，所以:对于以前已经处理过的拒收或者退货订单即使现在有更新，也不会再次处理
     *    不会再次更新的处理方式：对于退货或者拒收订单编号将其插入customer_order_log表。对于此表中的订单编号将不再处理。
     *
     */
    @Override
    public void rate(){
        logger.info("================>用户评级程序开始");
        long startTime = System.currentTimeMillis();
        SystemTimeDict dict = customersMapper.findCrmCreditDict();

        //如果时间未设置，则进行全量更新
        LocalDateTime beginTime = Optional.ofNullable(dict)
                .map(SystemTimeDict::getLastTime)
                .orElse(LocalDateTime.of(1980,1,1,0,0));

        LocalDateTime endTime = LocalDateTime.now();

        logger.info("本次更新的时间范围从{} 到 {}", beginTime, endTime);

        Integer sum = oldErpOrderService.countOldErpOrderLinkInc(beginTime,endTime);
        if(sum == null || sum == 0){
            logger.info("暂时联系人信息，评级结束...");
            return;
        }
        //每次全量更新，不改变
//        customersMapper.updateCrmCreditDict(endTime);

        //每次查询SIZE条数据，并进行以INSERT_SIZE为大小分割成多个数组
        int fragment =(int)Math.ceil((double)sum / SIZE) ;
        for(int i = 0; i < fragment; i++){
            List<OldErpOrderLink> list = oldErpOrderService.findOldErpOrderLinkInc(i * SIZE , SIZE, beginTime, endTime);
            List<List<OldErpOrderLink>> splitLists = ListUtils.splitList(list,INSERT_SIZE);
            for(List<OldErpOrderLink> oLink: splitLists ){
                List<Integer> ids = oLink.stream().map(e->e.getIdOrder()).collect(Collectors.toList());
                List<CustomerOrderLog> logs = customerOrderLogMapper.findByOrderIds(ids);
                CustomerCreditTask task = new CustomerCreditTask(customerService,customerOrderLogMapper,oLink,logs);
                //多线程处理有问题，目前单线程处理。
                task.run();
            }
        }
        long minute = (System.currentTimeMillis()- startTime) / 1000;

        logger.info("用户评级结束，共处理了{}个联系人,耗时{}秒", sum, minute);
    }




}
