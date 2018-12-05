package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.store.ext.dto.request.AddTakeStockReq;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.mapper.WmsMapper;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class TakeStockServiceTest extends BaseTest {

    @Resource
    private TakeStockService takeStockService;


    static void test() {

    }

    @Test
    public void insert() throws Exception {

        AddTakeStockReq addTakeStockReq = new AddTakeStockReq();
        String[] strs={"12312"};
        addTakeStockReq.setTrackings(Arrays.asList(strs));
        addTakeStockReq.setMemo("12341234");
        takeStockService.create(addTakeStockReq);
    }

    @Test
    public void query() throws Exception {


    }
}
