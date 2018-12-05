package com.stosz.purchase.mapper;

import com.stosz.BaseTest;
import com.stosz.purchase.ext.model.PurchaseRequired;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/18]
 */
public class PurchaseRequiredMapperTest extends BaseTest {

    @Resource
    private PurchaseRequiredMapper mapper;

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void getById() throws Exception {
    }

    @Test
    public void find() throws Exception {
        List<String> list = mapper.findSpu(new PurchaseRequired());
        list.forEach(System.out::println);
    }

    @Test
    public void findItem() throws Exception {
        List<String> spus = mapper.findSpu(new PurchaseRequired());
        spus.forEach(System.out::println);
        List<PurchaseRequired> list = mapper.findItem(new PurchaseRequired(),spus);
        logger.info("==========");
        list.forEach(System.out::println);
    }

    @Test
    public void queryList() throws Exception {
    }

    @Test
    public void queryListCount() throws Exception {
    }

    @Test
    public void queryListByParam() throws Exception {
    }

    @Test
    public void updatePurchaseDelay() throws Exception {
    }

    @Test
    public void updatePlanQty() throws Exception {
    }

    @Test
    public void updateSupplier() throws Exception {
    }

    @Test
    public void updateBuyer() throws Exception {
    }

    @Test
    public void updatePurchase() throws Exception {
    }

    @Test
    public void query() throws Exception {
    }

    @Test
    public void queryListByDetailSet() throws Exception {
    }

    @Test
    public void queryByIdList() throws Exception {
    }

    @Test
    public void updatePurchaseDelayById() throws Exception {
    }

    @Test
    public void updatePurchaseStatus() throws Exception {
    }

    @Test
    public void updateChangeStatus() throws Exception {
    }

}