package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.store.ext.dto.request.TransferItemReq;
import com.stosz.store.ext.dto.request.TransferWmsReq;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.ext.model.TransferItem;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class TransferServiceTest extends BaseTest {


    @Resource
    private TransferService transferService;
    @Resource
    private IWmsTransferService wmsTransferService;

    /**
     * 新建同仓调拨
     **/
    @Test
    public void insert() throws Exception {
        Transfer transfer = new Transfer();
        TransferItem transferItem = new TransferItem();
        // transitStock.setId((int) System.currentTimeMillis());
        transfer.setOutWmsId(8);
        transfer.setInWmsId(12);
        transfer.setType(1);
        transfer.setState("start");
        transferItem.setSku("K99999");
        transferItem.setOutDeptId(3);
        transferItem.setInDeptId(2);
        transfer.setTransferQty(66);
        transfer.setUpdateAt(LocalDateTime.now());
        transferItem.setCost(new BigDecimal(8.8));
        transferItem.setExpectedQty(66);
        transferService.oneWmsTransfer(transfer, transferItem);
        logger.info("{}", transfer.getId());
    }

    /**
     * 新建跨仓调拨
     **/
    @Test
    public void insert2Store() throws Exception {
        Transfer transfer = new Transfer();
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("K99999", 10);
        transfer.setOutWmsId(8);
        transfer.setInWmsId(12);
        transfer.setTransferQty(10);
        transfer.setUpdateAt(LocalDateTime.now());
        transferService.normalTransfer(hashMap, transfer);
        logger.info("{}", transfer.getId());
    }

    @Test
    public void submit() throws Exception {
        transferService.transferSubmit(35);
    }

    @Test
    public void pass() throws Exception {
        transferService.transferApprovePass(35);
    }

    @Test
    public void outDeptPass() throws Exception {
        transferService.outDeptTransPass(33);
    }

    @Test
    public void inDeptPass() throws Exception {
        transferService.inDeptTransPass(33);
    }

    @Test
    public void wmsTransfer() throws Exception {
        TransferWmsReq transferWmsReq = new TransferWmsReq();
        transferWmsReq.setOrderCode("YQH201712013");
        transferWmsReq.setSourceType("01");
        transferWmsReq.setFromWhcode("Y");
        transferWmsReq.setTargetWhcode("T");
        transferWmsReq.setFromGoodsOwner("1000");
        transferWmsReq.setTargetGoodsOwner("1000");
        List<TransferItemReq> transferItemReqs = new ArrayList<>();
        TransferItemReq transferItemReq = new TransferItemReq();
        transferItemReq.setSku("S009906500028");
        transferItemReq.setQty(1);
        transferItemReqs.add(transferItemReq);
        transferWmsReq.setDetailList(transferItemReqs);

        wmsTransferService.subCreateTranPlan(transferWmsReq);
    }

    @Test
    public void findByTrack() throws Exception {
        Transfer result = transferService.findByTrack("625028022212");
        logger.info("result:{}", result);
    }

}
