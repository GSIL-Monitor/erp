package com.stosz.purchase.service;

import com.stosz.BaseTest;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.ErrorGoodsItem;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author xiongchenyang
 * @version [1.0 , 2018/1/5]
 */
public class ErrorGoodsServiceTest extends BaseTest {

    @Resource
    private ErrorGoodsService service;

    @Test
    public void insert() throws Exception {
        ErrorGoods errorGoods = new ErrorGoods();
        errorGoods.setOriginalPurchaseId(39);
        errorGoods.setOriginalPurchaseNo("39");
        errorGoods.setBuDeptId(12);
        errorGoods.setSupplierId(5002);
        errorGoods.setBuDeptNo("09");
        errorGoods.setBuDeptName("第1事业部");
        errorGoods.setCreator("xcy|");
        errorGoods.setCreatorId(599);;
        List<ErrorGoodsItem> errorGoodsItemList = new ArrayList<>();
        ErrorGoodsItem errorGoodsItem = new ErrorGoodsItem();
        errorGoodsItem.setSpu("ST50090");
        errorGoodsItem.setOriginalSku("1062980");
        errorGoodsItem.setRealSku("1062982");
        errorGoodsItem.setDeptId(41);
        errorGoodsItem.setDeptNo("090101");
        errorGoodsItem.setDeptName("1部1营1组(吕鑫)");
        errorGoodsItem.setQuantity(20);
        errorGoodsItem.setCreator("xcy");
        errorGoodsItem.setCreatorId(599);
        errorGoodsItemList.add(errorGoodsItem);
        errorGoods.setErrorGoodsItemList(errorGoodsItemList);
        service.insert(errorGoods);
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void getById() throws Exception {
    }

    @Test
    public void find() throws Exception {
    }

    @Test
    public void processEvent() throws Exception {
    }

    @Test
    public void count() throws Exception {
    }

}