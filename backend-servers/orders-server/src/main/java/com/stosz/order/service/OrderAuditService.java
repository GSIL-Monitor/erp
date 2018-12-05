package com.stosz.order.service;

import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.dto.OrderAuditParam;
import com.stosz.order.ext.enums.OperationEnum;
import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.mapper.order.OrdersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;


@Service
public class OrderAuditService {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private FsmProxyService<Orders> ordersFsmProxyService;


    /**
     * 批量审单
     * @param orderAuditParam
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void auditBatch(OrderAuditParam orderAuditParam){

        OperationEnum operationEnum = OperationEnum.fromId(orderAuditParam.getType());

        for(OrderAuditParam.AuditParam param: orderAuditParam.getAuditList())
        {
            Integer orderId = param.getOrderId();
            String memo = param.getMemo();
            switch (operationEnum)
            {
                case valid: validOrder(orderId, memo); break;
                case unContact: unContactOrder(orderId, memo); break;
                case malice:  maliceOrder(orderId, memo); break;
                case inComplete: incompleteOrder(orderId, memo); break;
                case repeat: repeatOrder(orderId, memo); break;
                default: throw new RuntimeException("操作类型未找到");
            }

        }
    }


    /**
     * 审单 - 有效订单
     * @param orderId
     * @param memo
     * @return
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void validOrder(Integer orderId, String memo){
        audit(orderId,OrderEventEnum.checkValid, memo,OperationEnum.valid);
    }

    /**
     * 审单 - 待联系
     * @param orderId
     * @param memo
     * @return
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void unContactOrder(Integer orderId, String memo){
        audit(orderId,OrderEventEnum.checkLinkInfo,memo,OperationEnum.unContact);
    }

    /**
     * 审单 - 无效订单 - 恶意下单
     * @param orderId
     * @param memo
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void maliceOrder(Integer orderId, String memo){
        audit(orderId,OrderEventEnum.checkInvalid,memo,OperationEnum.malice);
    }


    /**
     * 审单 - 无效订单 - 信息不完整
     * @param orderId
     * @return
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void incompleteOrder(Integer orderId, String memo){
        audit(orderId,OrderEventEnum.checkInvalid,memo,OperationEnum.inComplete);
    }


    /**
     * 审单 - 无效订单 - 重复订单
     * @param orderId
     * @param memo
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void  repeatOrder(Integer orderId, String memo){
        audit(orderId,OrderEventEnum.checkInvalid,memo,OperationEnum.repeat);
    }


    /**
     * 审单 - 无效订单 - 系统自动审单
     * @param orderId
     * @param memo
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void  autoInvalidOrder(Integer orderId, String memo){
        audit(orderId,OrderEventEnum.checkInvalid,memo,OperationEnum.crmAuto);
    }



    /**
     * 审单 - 撤回
     * @param orderId
     * @param memo
     */
    @Transactional(value = "orderTxManager", rollbackFor = Exception.class)
    public void  revoke(Integer orderId, String memo){
        Orders order = ordersMapper.getById(orderId);
        Assert.notNull(order,"找不到该订单编号");
        ordersFsmProxyService.processEvent(order, OrderEventEnum.revoke, memo);
        memo = StringUtils.hasText(memo) ? memo : order.getMemo();
        ordersMapper.updateMemoByOrderId(orderId,memo);
        logger.info("订单【id={}】已被撤回",orderId);
    }


    /**
     * 审单
     * @param orderId 订单编号
     * @param eventEnum 订单触发的事件
     * @param memo 备注
     * @param operationEnum 原因
     */
    private void audit(Integer orderId, OrderEventEnum eventEnum ,String memo, OperationEnum operationEnum){
        Orders order = ordersMapper.getById(orderId);
        Assert.notNull(order,"找不到该订单编号");
        ordersFsmProxyService.processEvent(order, eventEnum, memo);
        memo = StringUtils.hasText(memo) ? memo : order.getMemo();
        ordersMapper.updateForAuditByOrderId(orderId,operationEnum.ordinal(), LocalDateTime.now(),memo);
        logger.info("订单【id={}】已被被操作成【{}】",orderId,operationEnum.display());
    }

}
