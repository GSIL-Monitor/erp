package com.stosz.purchase.service;

import com.stosz.BaseTest;
import com.stosz.purchase.ext.model.finance.PayStateNotice;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author xiongchenyang
 * @version [1.0 , 2018/1/19]
 */
public class PurchaseServiceTest extends BaseTest{

    @Resource
    private PurchaseService purchaseService;

//    @Test
//    public void testPurchase(){
//        PayStateNotice payStateNotice = new PayStateNotice();
//        payStateNotice.setAmount();
//        payStateNotice.setBillNo();
//        payStateNotice.setBillType();
//        payStateNotice.setPayTime(LocalDateTime.now());
//        payStateNotice.setUserId(599);
//        payStateNotice.setUser("熊晨阳");
//        payStateNotice.setMemo("噼噼啪啪啪");
//        purchaseService.payEventWriteBack()
//    }
}  
