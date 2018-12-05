package com.stosz.purchase.service;

import com.stosz.BaseTest;
import com.stosz.purchase.ext.common.CreateReturnDetailDto;
import com.stosz.purchase.ext.common.CreateReturnDto;
import com.stosz.purchase.ext.enums.ReturnedTypeEnum;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/6]
 */
public class PurchaseReturnServiceTest extends BaseTest{

    @Resource
    private PurchaseReturnedService purchaseReturnedService;
    @Resource
    private PurchaseReturnedItemService purchaseReturnedItemService;
    @Resource
    private WmsPurchaseService wmsPurchaseService;

    @Test
    public void inseterReturnTest(){
        CreateReturnDto createReturnDto = new CreateReturnDto();
        createReturnDto.setPurchaseId(39);
        createReturnDto.setRefundAmount(new BigDecimal(100));
        createReturnDto.setRefundAddress("深圳");
        createReturnDto.setReturnType(ReturnedTypeEnum.INTRANSIT.ordinal());
        createReturnDto.setShippingId(1);
        CreateReturnDetailDto createReturnDetailDto = new CreateReturnDetailDto();
        createReturnDetailDto.setPurchaseItemId(27);
        createReturnDetailDto.setReturnedQty(2);
        List<CreateReturnDetailDto> createReturnDetailDtos=new ArrayList<>();
        createReturnDetailDtos.add(createReturnDetailDto);
        createReturnDto.setReturnDetails(createReturnDetailDtos);
        purchaseReturnedService.createPurchaseReturn(createReturnDto);
    }

    @Test
    public void cancelReturnTest(){
        purchaseReturnedService.cancelPurchaseReturned(22,"不退了","1");
    }
    @Test
    public void passReturnTest(){
        PurchaseReturned byId = purchaseReturnedService.getById(24);
        purchaseReturnedService.confirmFinanceRefund(1,"");
    }
    @Test
    public void wmsTest(){
        PurchaseReturned byId = purchaseReturnedService.getById(24);
        List<PurchaseReturnedItem> purchaseReturnedItems = purchaseReturnedItemService.queryListByReturnId(24);
        wmsPurchaseService.subCreatePurchaseReturnPlan(byId,purchaseReturnedItems);
    }
}  
