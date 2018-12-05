package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.store.ext.model.InvalidItem;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class InvalidItemServiceTest extends BaseTest {

    @Resource
    private InvalidItemService service;


    static void test() {

    }

    @Test
    public void insert() throws Exception {
        InvalidItem invalidItem = new InvalidItem();
        invalidItem.setInvalidId(1);
        invalidItem.setTrackingNoOld("111111");
        invalidItem.setTransitId(11112122);
        int result = service.insert(invalidItem);
        logger.info("result:" + result);
    }

    @Test
    public void query() throws Exception {

        boolean result = service.isInserted("1d23");
        logger.info("result:" + result);
    }

    @Test
    public void delete() throws Exception {

        service.deleteByInvalidId(1);
    }
}
