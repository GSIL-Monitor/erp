package com.stosz.store.wmsApi;

import com.google.common.collect.Lists;
import com.stosz.BaseTest;
import com.stosz.plat.utils.*;
import com.stosz.plat.wms.SendWmsUtils;
import com.stosz.plat.wms.model.WMSResponse;
import com.stosz.plat.wms.model.WmsConfig;
import com.stosz.store.ext.dto.request.TransferItemReq;
import com.stosz.store.ext.dto.request.TransferWmsReq;
import com.stosz.store.ext.dto.request.WmsReq;
import com.stosz.store.ext.dto.request.WmsTransferReq;
import com.stosz.store.ext.enums.TransferTypeEnum;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.mapper.WmsMapper;
import com.stosz.store.service.TransferItemService;
import com.stosz.store.service.TransferService;
import com.stosz.store.service.WmsService;
import com.stosz.store.service.WmsTransferService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class WmsAPITest extends BaseTest {

    @Resource
    private TransferService transferService;

    @Autowired
    private WmsService wmsService;

    @Autowired
    private TransferItemService transferItemService;

    @Resource
    private WmsTransferService wmsTransferService;

    @Autowired
    private IStockService iStockService;

    static void test() {

    }

    @Test
    public void addTransfer2Wms() throws Exception {
        Transfer transfer = transferService.findById(73);

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
        transferWmsReq.setFromGoodsOwner("1000");
        String inWmsSysCode = wmsService.findById(transfer.getInWmsId()).getWmsSysCode();
        transferWmsReq.setTargetWhcode(inWmsSysCode);
        transferWmsReq.setTargetGoodsOwner("1000");
        List<TransferItemReq> detailList = new ArrayList<>();
        List<TransferItem> transferItems = transferItemService.findByTranId(transfer.getId());
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
        logger.info("");

    }

    @Test
    public void testReq() {
        TransferWmsReq transferWmsReq = new TransferWmsReq();
        logger.info("req:", transferWmsReq);
    }

    @Test
    public void addTransfer2Wms2() throws Exception {
        Transfer transfer = transferService.findById(76);

        String type;

        if (TransferTypeEnum.normal2Normal == TransferTypeEnum.fromId(transfer.getType()))
            type = "01";
        else
            type = "02";

        TransferWmsReq transferWmsReq = new TransferWmsReq();
        transferWmsReq.setOrderCode(transfer.getId().toString() + "4");
//        transferWmsReq.setSourceType("04");
        transferWmsReq.setSourceType(type);
        String outWmsSysCode = wmsService.findById(transfer.getOutWmsId()).getWmsSysCode();
//        transferWmsReq.setFromWhcode(outWmsSysCode);
        transferWmsReq.setFromWhcode("T");
        transferWmsReq.setFromGoodsOwner("1000");
        String inWmsSysCode = wmsService.findById(transfer.getInWmsId()).getWmsSysCode();

        switch (type) {
            case "01":
                Assert.isTrue(StringUtils.isNotBlank(outWmsSysCode), "调出仓库编码不能为空");
            case "02":
                Assert.isTrue(StringUtils.isNotBlank(inWmsSysCode), "调入仓库编码不能为空");
                break;

        }

        transferWmsReq.setTargetWhcode(inWmsSysCode);
        transferWmsReq.setTargetGoodsOwner("1000");
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
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("head", transferWmsReq);

       /* String jsonContent = JsonUtil.toJson(transferWmsReq);
        String paramBySenderObj = getParamBySenderObj(jsonContent);
        logger.info("WMS创建调拨单接口调用 请求参数 request-->{}", paramBySenderObj);

        String result = RestClient.getClient().postForObject(wmsConfig.getSERVICE_URL(), paramBySenderObj, String.class);
        Assert.hasText(result, "未知原因导致取消订单接口返回结果为空，请与wms系统相关人员确认");
        logger.info("WMS创建调拨单接口调用结束 返回参数 response-->{}", result);*/
        logger.info("request:{}", transferWmsReq);
        String result = SendWmsUtils.sendWmsRequest(reqMap, WmsConfig.Service_subCreateTranPlan);
        logger.info("result:" + result);

        WMSResponse wmsResponse = JsonUtil.readValue(result, WMSResponse.class);
        logger.info("result:{}", wmsResponse);
        logger.info("success:{}", wmsResponse.isOK());
        logger.info("body:{}", wmsResponse.getBody());
        Assert.isTrue(wmsResponse.isOK(), wmsResponse.getBody());
    }


    @Test
    public void wmsReturn() {
        String result = "{\"key\":\"bgntest\",\"result_data\":[\"{\\\"transferNo\\\":\\\"107\\\",\\\"type\\\":\\\"1\\\",\\\"outWarehouseId\\\":\\\"T\\\",\\\"inWarehouseId\\\":\\\"Y\\\",\\\"shippingName\\\":\\\"\\\",\\\"shippingNo\\\":\\\"\\\",\\\"outTime\\\":\\\"2018-01-20 15:59:52\\\",\\\"remark\\\":\\\"\\\",\\\"data\\\":[{\\\"sku\\\":\\\"S010638000033\\\",\\\"transferQty\\\":\\\"30\\\",\\\"outSkuReceived\\\":\\\"30\\\"}]}\"]}";

        String rd = "{\"transferNo\":\"107\",\"type\":\"1\",\"outWarehouseId\":\"T\",\"inWarehouseId\":\"Y\",\"shippingName\":\"\",\"shippingNo\":\"\",\"outTime\":\"2018-01-20 15:59:52\",\"remark\":\"\",\"data\":[{\"sku\":\"S010638000033\",\"transferQty\":\"30\",\"outSkuReceived\":\"30\"}]}";
        WmsReq wmsReq = new WmsReq(); //JsonUtil.readValue(result, WmsReq.class);
        wmsReq.setKey("bgntest");
        wmsReq.setResult_data(rd);
        String resultData = wmsReq.getResult_data();
        WmsTransferReq wmsTransferReq = new WmsTransferReq();
        WmsTransferReq wmsTransfer = JsonUtil.readValue(resultData, wmsTransferReq.getClass());

        WMSResponse response = iStockService.wmsTransferRequest(wmsReq);
        logger.info("result:" + response);
    }

    @Test
    public void testFindId() {
        Transfer transfer = transferService.findById(107);
        logger.info("result:{}",transfer);
    }


}
