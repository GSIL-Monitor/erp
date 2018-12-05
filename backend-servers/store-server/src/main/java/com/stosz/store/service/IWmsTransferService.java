package com.stosz.store.service;

import com.stosz.store.ext.dto.request.TransferWmsReq;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/20]
 */
public interface IWmsTransferService {

    /**
     * 创建调拨单单推送到wms
     * @return 推送结果
     */
     void subCreateTranPlan(TransferWmsReq transferWmsReq);

}
