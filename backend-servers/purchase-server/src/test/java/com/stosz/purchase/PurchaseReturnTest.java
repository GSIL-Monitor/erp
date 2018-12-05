package com.stosz.purchase;

import com.stosz.BaseTest;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.mapper.PurchaseReturnedMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/6]
 */
public class PurchaseReturnTest extends BaseTest{

    public static final Logger logger = LoggerFactory.getLogger(PurchaseReturnTest.class);
    @Resource
    private PurchaseReturnedMapper purchaseReturnedMapper;

    @Test
    public void findTest(){
        PurchaseReturned purchaseReturned = new PurchaseReturned();
        purchaseReturned.setPurchaseNo("36");
        List<PurchaseReturned> purchaseReturneds = purchaseReturnedMapper.queryList(purchaseReturned);
        logger.info(""+purchaseReturneds.size());

    }
}  
