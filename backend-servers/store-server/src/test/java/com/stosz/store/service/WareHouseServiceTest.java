package com.stosz.store.service;

import com.google.common.collect.Lists;
import com.stosz.BaseTest;
import com.stosz.store.ext.dto.request.QueryQtyReq;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.dto.response.PurchaseQueryRes;
import com.stosz.store.ext.dto.response.StockChangeRes;
import com.stosz.store.ext.dto.response.StockRes;
import com.stosz.store.ext.model.Stock;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStockService;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class WareHouseServiceTest extends BaseTest {

    @Resource
    private StockService stockService;

    @Resource
    private WmsService wmsService;
    @Resource
    private IStockService iStockService;
    @Resource
    private SettlementMonthService settlementMonthService;


    static void test() {

    }

    @Test
    public void changedata() throws Exception {


        List<StockChangeReq> chages=new ArrayList();
        //carter 测试用到，
        List<Wms> query = wmsService.query(new Wms());
        for(Wms wms:query){
            StockChangeReq change = new StockChangeReq();
            change.setWmsId(wms.getId());
            change.setDeptId(63);
            change.setDeptNo("0a0301");
            change.setDeptName("2部1营1组(艾聪)");
            change.setSpu("ST10010T3");
            change.setSku("D0106381");
            change.setChangeQty(100);
            change.setType("purchase_in_stock");
            change.setVoucherNo("TK123466");
            change.setChangeAt(LocalDateTime.now());
            change.setAmount(new BigDecimal(10000));
            chages.add(change);
        }

        List<StockChangeRes> stockChangeRes = iStockService.purchaseChangeStockQty(chages);
        logger.info("{}",stockChangeRes.get(0).getCode());
    }

    @Test
    public void query() throws Exception {
        Stock stock = new Stock();
        QueryQtyReq queryQtyReq = new QueryQtyReq();

        queryQtyReq.setDeptId(1);
        queryQtyReq.setSku("1");

        List<StockRes> stockRes = iStockService.queryQty(queryQtyReq);
        logger.info(""+stockRes.size());

    }

    @Test
    public void test2() throws Exception {
        settlementMonthService.addSettlementMonth();
    }

    @Test
    public void test3() throws Exception {
        LinkedList<QueryQtyReq> queryQtyReqs = Lists.newLinkedList();
        QueryQtyReq queryQtyReq = new QueryQtyReq();
        queryQtyReq.setSku("K99999");
        queryQtyReq.setDeptId(8);
        queryQtyReq.setWmsId(6);
        queryQtyReqs.add(queryQtyReq);
        List<PurchaseQueryRes> purchaseQueryRes = iStockService.purchaseQuery(queryQtyReqs);
        for (PurchaseQueryRes item:purchaseQueryRes){

        }
    }
}
