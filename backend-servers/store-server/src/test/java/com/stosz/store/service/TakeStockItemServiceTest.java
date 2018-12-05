package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.store.ext.dto.request.AddTakeStockReq;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class TakeStockItemServiceTest extends BaseTest {

    @Autowired
    private TakeStockItemService itemService;


    static void test() {

    }

    @Test
    public void insertBat() throws Exception {

        AddTakeStockReq addTakeStockReq = new AddTakeStockReq();
        String[] strs = {"12312"};
        addTakeStockReq.setTrackings(Arrays.asList(strs));
        addTakeStockReq.setMemo("12341234");
        addTakeStockReq.setTakeStockId(1);
        itemService.batchInsert(addTakeStockReq);

    }

    @Test
    public void query() throws Exception {

    }
}
