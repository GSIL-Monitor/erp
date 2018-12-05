package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.order.ext.dto.TransitItemDTO;
import com.stosz.store.ext.model.InvalidItem;
import com.stosz.store.ext.model.TransitItem;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class TransitItemServiceTest extends BaseTest {

    @Resource
    private TransitItemService service;


    static void test() {

    }


    @Test
    public void query() throws Exception {


    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void insertBat() throws Exception {
        List<TransitItem> saveItemList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TransitItem item = new TransitItem();
            item.setTransitId(222);
            item.setSpu("spu9999");
            item.setSku("sku999999");
            item.setQty(9);
            item.setDeptId(1024);
            item.setTrackingNoOld("123456789");
            item.setOrderIdOld(11222233L);
            saveItemList.add(item);
        }

        int result = service.insertBat(saveItemList);
        logger.info("result:" + result);
    }

    @Test
    public void updateDeptBatch() throws Exception {
        List<TransitItem> saveItemList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TransitItem item = new TransitItem();
            item.setTransitId(222);
            item.setSpu("spu9999");
            item.setSku("sku999999");
            item.setQty(9);
            item.setDeptId(1024);
            item.setTrackingNoOld("123456789");
            item.setOrderIdOld(11222233L);
            item.setId(i);
            saveItemList.add(item);
        }

        Integer deptId=68;
        int result =  service.updateDeptBatch(saveItemList,deptId,"test部门");
        logger.info("result:" + result);
    }



}
