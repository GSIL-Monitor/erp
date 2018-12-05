//package com.stosz.purchase;
//
//import com.stosz.BaseTest;
//import com.stosz.purchase.ext.model.Purchase;
//import com.stosz.purchase.service.PurchaseService;
//import com.stosz.store.ext.model.Wms;
//import com.stosz.store.ext.service.IStockService;
//import org.junit.Test;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author xiongchenyang
// * @version [1.0 , 2017/12/6]
// */
//public class PurchaseServiceTest extends BaseTest{
//
//    @Resource
//    private PurchaseService purchaseService;
//    @Resource
//    private IStockService stockService;
//
//    @Test
//    public void findTest(){
//        Purchase purchase = new Purchase();
//        List<Purchase> purchaseList = purchaseService.find(purchase);
//        logger.info(purchaseList);
//
//    }
//
//    @Test
//    public void findRpc(){
//        int[] ids={1,2,15,16};
//        List<Wms> list=warehouseService.findWmsByIds(ids);
//        logger.info("11:"+list);
//    }
//}
