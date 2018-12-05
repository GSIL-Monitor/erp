package com.stosz.store.fsm.transfer;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.store.ext.dto.request.TransferItemReq;
import com.stosz.store.ext.dto.request.TransferWmsReq;
import com.stosz.store.ext.enums.TransferTypeEnum;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.service.TransferItemService;
import com.stosz.store.service.WmsService;
import com.stosz.store.service.WmsTransferService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class ApprovePassAfter extends IFsmHandler<Transfer> {

    @Resource
    private WmsService wmsService;
    @Resource
    private TransferItemService transferItemService;
    @Resource
    private WmsTransferService wmsTransferService;

    @Override
    public void execute(Transfer transfer, EventModel event) {

        logger.debug("调拨单{}审核通过调用wms", transfer);

        switch (TransferTypeEnum.fromId(transfer.getType())) {

            case transit2Normal:
            case normal2Normal:
                this.addTransfer2Wms(transfer);
                break;
            case transit2Transit:
                break;
            default:
                break;
        }

    }

    /**
     * 通知wms
     *
     * @param transfer
     */
    private void addTransfer2Wms(Transfer transfer) {
        /**普通仓调wms type：01   转寄仓调wms type：02**/

        //请求wms新建调拨单
        String type;
        if (TransferTypeEnum.normal2Normal == TransferTypeEnum.fromId(transfer.getType()))
            type = "01";
        else
            type = "02";

        TransferWmsReq transferWmsReq = new TransferWmsReq();
        transferWmsReq.setOrderCode(transfer.getId().toString());
        transferWmsReq.setSourceType(type);
        String outWmsSysCode = wmsService.findById(transfer.getOutWmsId()).getWmsSysCode();
        transferWmsReq.setFromWhcode(outWmsSysCode);
        String inWmsSysCode = wmsService.findById(transfer.getInWmsId()).getWmsSysCode();
        switch (type) {
            case "01":
                Assert.isTrue(StringUtils.isNotBlank(outWmsSysCode), "调出仓库编码不能为空");
            case "02":
                Assert.isTrue(StringUtils.isNotBlank(inWmsSysCode), "调入仓库编码不能为空");
                break;
        }
        transferWmsReq.setTargetWhcode(inWmsSysCode);
        List<TransferItemReq> detailList = new ArrayList<>();
        List<TransferItem> transferItems = transferItemService.findByTranId(transfer.getId());
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(transferItems), "sku不能为空");
        for (TransferItem transferItem : transferItems) {
            if (StringUtils.isEmpty(transferItem.getTrackingNo())) {
                TransferItemReq transferItemReq = new TransferItemReq();
                transferItemReq.setSku(transferItem.getSku());
                transferItemReq.setQty(transferItem.getExpectedQty());
                detailList.add(transferItemReq);
            }
        }

        transferWmsReq.setDetailList(detailList);
        wmsTransferService.subCreateTranPlan(transferWmsReq);
    }

}