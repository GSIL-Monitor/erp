package com.stosz.purchase.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.stosz.BaseTest;
import com.stosz.purchase.ext.model.PurchaseRequired;
import com.stosz.purchase.ext.model.PurchaseRequiredSpu;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/18]
 */
public class PurchaseRequiredServiceTest extends BaseTest{

    @Resource
    private PurchaseRequiredService purchaseRequiredService;

    @Test
    public void find() throws Exception {
        List<PurchaseRequiredSpu> purchaseRequiredSpuList = purchaseRequiredService.find(new PurchaseRequired());
        purchaseRequiredSpuList.forEach(System.out::println);
    }

}