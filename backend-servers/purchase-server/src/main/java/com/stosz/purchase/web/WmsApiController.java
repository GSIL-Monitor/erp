package com.stosz.purchase.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.purchase.ext.common.WmsReturnDto;
import com.stosz.purchase.ext.common.WmsWriteBackDto;
import com.stosz.purchase.ext.common.WriteBackItemDto;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.service.PurchaseReturnedService;
import com.stosz.purchase.service.PurchaseService;
import com.stosz.purchase.service.WmsApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/25]
 */
@Controller
@RequestMapping("/purchase/wms/api")
public class WmsApiController {

    @Value("${purchase_writeBack_key}")
    private String writeBackKey;

    @Resource
    private WmsApiService wmsApiService;

    @Resource
    private PurchaseService purchaseService ;
    @Resource
    private PurchaseReturnedService purchaseReturnedService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 采购单入库回调/采购退货入库回调
     * @param key 密钥
     * @param result_data 信息
     * @return 回调结果
     */
    @ResponseBody
    @RequestMapping("/instockWriteBack")
    public String instockWriteBack(String key , String result_data) throws IOException {
        logger.info(result_data);
        WmsReturnDto result  = new WmsReturnDto();
        ObjectMapper objectMapper = new ObjectMapper();
        WmsWriteBackDto wmsWriteBackDto = objectMapper.readValue(result_data,WmsWriteBackDto.class);
        logger.info(""+wmsWriteBackDto);
        result.setIsSuccess("false");
        // 回写采购单，采购明细， 采购入库单，采购入库明细，库存表，调用库存接口，调用配货程序。
        if(StringUtils.isBlank(key) || !writeBackKey.equals(key)){
            result.setBody("fail,密钥错误！");
        }
        if(StringUtils.isBlank(wmsWriteBackDto.getPurchaseNo())){
            result.setBody("fail,采购单号不允许为空！");
        }
        if(wmsWriteBackDto.getWarehouseId() == null){
            result.setBody("fail,仓库编号不允许为空！");
        }
        if(wmsWriteBackDto.getInTime() == null){
            result.setBody("fail,到货时间不允许为空！");
        }
        if(StringUtils.isBlank(wmsWriteBackDto.getTotalReceived())){
            result.setBody("fail,入库总数量不允许为空！");
        }
        if(wmsWriteBackDto.getType() == null){
            result.setBody("fail,入库类型不允许为空！");
        }
        if(wmsWriteBackDto.getData() == null){
            result.setBody("fail,入库明细不允许为空！");
        }
        logger.info("回调的信息是{}，，，，{}",key,result_data);
        logger.info("实体内容{}",wmsWriteBackDto);
        Integer type = wmsWriteBackDto.getType();
        String purchaseNo = wmsWriteBackDto.getPurchaseNo();
        Purchase purchase = purchaseService.getByPurchaseNo(purchaseNo);
        if(purchase == null){
            result.setIsSuccess("false");
            result.setBody("fail,找不到对应的采购单！");
        }else if(type == 1){
            // 根据采购单返回的入库信息，传到订单系统，获取入库配货情况，调用仓储系统，写入库，该采购明细
            wmsApiService.purchaseInStock(wmsWriteBackDto);
        }else if (type == 2){
            // 采购退货入库
            List<PurchaseReturned> purchaseReturnedList = purchaseReturnedService.findReturnedByPurchaseNo(purchaseNo);
            if(CollectionUtils.isNotNullAndEmpty(purchaseReturnedList)&& purchaseReturnedList.size() ==1){
                List<WriteBackItemDto> writeBackItemDtoList = wmsWriteBackDto.getData();
                if (writeBackItemDtoList.isEmpty()) {
                    result.setBody("fail,没有回传入库明细！");
                    result.setIsSuccess("false");
                }
                wmsApiService.purchaseReturnWriteBack(wmsWriteBackDto,purchaseReturnedList.get(0).getId());
            }else{
                result.setIsSuccess("false");
                result.setBody("fail,找不到对应的采购退货单！");
            }
        }else{
            result.setIsSuccess("false");
            result.setBody("fail,入库类型无法识别！");
        }
        result.setBody("success,入库成功！");
        result.setIsSuccess("true");
        return result.toString();
    }

}  
