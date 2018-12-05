package com.stosz.purchase.service;

import com.google.common.collect.Lists;
import com.stosz.BaseTest;
import com.stosz.purchase.ext.common.CreateReturnDetailDto;
import com.stosz.purchase.ext.common.CreateReturnDto;
import com.stosz.purchase.ext.common.WmsWriteBackDto;
import com.stosz.purchase.ext.common.WriteBackItemDto;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import org.junit.Test;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/6]
 */
public class WmsApiServiceTest extends BaseTest{

    @Resource
    private WmsApiService wmsApiService;

    @Test
    public void purchaseInStockTest(){

        WmsWriteBackDto wmsWriteBackDto = new WmsWriteBackDto();
        wmsWriteBackDto.setWarehouseId("5");
        wmsWriteBackDto.setPurchaseNo("2341423");
        wmsWriteBackDto.setDepartmentId(3);
        wmsWriteBackDto.setTotalReceived("1212");
        wmsWriteBackDto.setInTime("2018-01-15 09:00:00");
        wmsWriteBackDto.setRemark("采购入库");
        wmsWriteBackDto.setType(1);
        LinkedList<WriteBackItemDto> writeBackItemDtos = Lists.newLinkedList();
        WriteBackItemDto writeBackItemDto = new WriteBackItemDto();
        writeBackItemDto.setSku("K88888");
        writeBackItemDto.setSkuReceived("100");
        writeBackItemDto.setPriceReceived("100");
        writeBackItemDtos.add(writeBackItemDto);
        wmsWriteBackDto.setData(writeBackItemDtos);
        wmsApiService.purchaseInStock(wmsWriteBackDto);
    }
}  
