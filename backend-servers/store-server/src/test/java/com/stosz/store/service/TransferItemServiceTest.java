package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.ext.model.TransitItem;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class TransferItemServiceTest extends BaseTest {

    @Resource
    private TransferItemService service;

    @Resource
    private TransitStockService transitStockService;

    static void test() {

    }

    @Test
    public void findByTrack() throws Exception {
        String trackingNoOld = "1";
        List<TransferItem> result = service.findByTrack(trackingNoOld);

        int countIn = transitStockService.countByInStock(result);

        logger.info("result:" + result);
        logger.info("  count:" + countIn);
    }

    @Test
    public void insertBat() throws Exception {
        List<TransferItem> result = service.findByTranId(57);

        int countIn = transitStockService.countByInStock(result);

        logger.info("result:" + result);
        logger.info("  count:" + countIn);
        int insert=service.insertBat(result);
        logger.info("insert:" + insert);

    }


    @Test
    public void findBySkuAndTranId() throws Exception {

        TransferItem result =service.findBySkuAndTranId(111, "S010638000028");
        logger.info("result:" + result);
    }

    @Test
    public void findByTranId() throws Exception {

        List<TransferItem> result =service.findByTranId(111);
        logger.info("result:" + result);
    }

}
