package com.stosz.purchase.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.purchase.ext.common.CreateReturnDto;
import com.stosz.purchase.ext.enums.PurchaseState;
import com.stosz.purchase.ext.enums.ReturnedTypeEnum;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseReturnSpu;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.service.PurchaseReturnedService;
import com.stosz.purchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author feiheping
 * @version [1.0,2017年11月20日]
 */
@Controller
@RequestMapping("/purchase/returned")
public class PurchaseReturnedController extends AbstractController {

    @Autowired
    private PurchaseReturnedService purchaseReturnedService;

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "/getPurchaseDtoById", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getPurchaseById(Integer purchaseId){
        RestResult result = new RestResult();
        Assert.notNull(purchaseId,"传入的采购单id不允许为空！");
        List<PurchaseReturnSpu>  purchaseReturnSpus = purchaseReturnedService.find(purchaseId);
        result.setItem(purchaseReturnSpus);
        result.setCode(RestResult.OK);
        return result;
    }

    @RequestMapping("/createReturned")
    @ResponseBody
    public RestResult createReturned(String createReturnDtoStr) {
        ObjectMapper objectMapper =new ObjectMapper();
        CreateReturnDto createReturnDto = null;
        try {
            createReturnDto = objectMapper.readValue(createReturnDtoStr,CreateReturnDto.class);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        Assert.notNull(createReturnDto.getReturnType(),"采退类型必须选择");
        Assert.notNull(createReturnDto.getPurchaseId(), "采购单ID不能为空");
        Purchase purchase = purchaseService.getById(createReturnDto.getPurchaseId());
        Assert.notNull(purchase, "采购单ID[" + createReturnDto.getPurchaseId() + "]不存在");
        Assert.isTrue(purchase.getState().equals(PurchaseState.executing.name()) || purchase.getState().equals(PurchaseState.completed.name()),
                "必须是已付款的采购单才能退货");
        Assert.notEmpty(createReturnDto.getReturnDetails(), "采退明细不能为空");
        Assert.notNull(createReturnDto.getRefundAmount(), "采退金额不能为空");
        Assert.isTrue(createReturnDto.getRefundAmount().compareTo(new BigDecimal(0)) == 1, "采退金额必须大于0");
        if (createReturnDto.getReturnType().equals(ReturnedTypeEnum.INTHELIBRARY.name())) {// 在库采退
            Assert.hasText(createReturnDto.getRefundAddress(), "在库采退，退货地址不能为空");
        }

        purchaseReturnedService.createPurchaseReturn(createReturnDto);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.OK);
        restResult.setDesc("创建成功");
        return restResult;
    }

    @RequestMapping("queryList")
    @ResponseBody
    public RestResult queryList(PurchaseReturned purchaseReturned) {
        RestResult restResult = new RestResult();
        int count = purchaseReturnedService.queryCount(purchaseReturned);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }
        List<PurchaseReturned> purchaseReturneds = purchaseReturnedService.queryList(purchaseReturned);
        restResult.setItem(purchaseReturneds);
        return restResult;
    }

    /**
     * 取消采退
     * @return
     */
    @RequestMapping("cancelReturned")
    @ResponseBody
    public RestResult cancelReturned(Integer returnedId, String memo) {
        Assert.notNull(returnedId, "采退单号不能为空");
        purchaseReturnedService.cancelPurchaseReturned(returnedId, memo, MBox.getLoginid());
        RestResult restResult = new RestResult();
        restResult.setDesc(RestResult.NOTICE);
        restResult.setDesc("取消采退成功");
        return restResult;
    }

    @RequestMapping("confirmFinanceRefund")
    @ResponseBody
    public RestResult confirmFinanceRefund(Integer returnedId, String memo) {
        Assert.notNull(returnedId, "采退单号不能为空");
        RestResult restResult=new RestResult();
        PurchaseReturned returnedDto=new PurchaseReturned();
        returnedDto.setId(returnedId);
        returnedDto.setMemo(memo);
        UserDto user = ThreadLocalUtils.getUser();
        returnedDto.setRefundUserId(user.getId());
        returnedDto.setRefundUser(user.getLastName());
        purchaseReturnedService.confirmFinanceRefund(returnedId, memo);
        return restResult;
    }


    /**
     * 状态机历史记录
     * @param id
     * @return
     */
    @RequestMapping("/queryFsmHistory")
    @ResponseBody
    public RestResult queryFsmHistory(@RequestParam("purchaseItemId") Integer id,
                                      @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return purchaseReturnedService.queryHistory(id,start,limit);
    }

}
