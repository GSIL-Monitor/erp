package com.stosz.purchase.ext.service;


import com.stosz.purchase.ext.model.finance.PayStateNotice;

/**
 * 采购单对外接口
 *
 * @author xiongchenyang
 * @version [1.0 , 2018/1/10]
 */
public interface IPurchaseReturnedService {

    String url = "/purchase/remote/IPurchaseReturnedService";

    /**
     * 采购单支付后回调接口
     * @param payStateNotice 支付回调的消息实体
     * @return 回调结果
     */
    Boolean payEventWriteBack(PayStateNotice payStateNotice);
}
