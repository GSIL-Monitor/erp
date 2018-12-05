package com.stosz.purchase.web.finance;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.BeanUtil;
import com.stosz.plat.web.AbstractController;
import com.stosz.purchase.ext.common.CreateReturnDto;
import com.stosz.purchase.ext.model.finance.FinancePay;
import com.stosz.purchase.ext.model.finance.Payable;
import com.stosz.purchase.ext.model.finance.Payment;
import com.stosz.purchase.ext.model.finance.PaymentItem;
import com.stosz.purchase.service.finance.PayableService;
import com.stosz.purchase.service.finance.PaymentItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2018/1/2 13:49
 */
@RestController
@RequestMapping("/purchase/finance/payable")
public class PayableController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PayableService payableService;

    @Resource
    private PaymentItemService paymentItemService;


    /**
     * 搜索
     */
    @RequestMapping(value = "/findList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RestResult findList(Payable payable) {

        logger.debug("应付款列表 req:goalBillNo={},changeBillNo={}", payable.getGoalBillNo(), payable.getChangeBillNo());
        RestResult result = new RestResult();

        int count = payableService.count(payable);
        result.setTotal(count);
        if (count == 0) {
            return result;
        }
        List<FinancePay> financePayList = payableService.findList(payable);

        result.setItem(financePayList);
        return result;
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/export", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RestResult export(Payable payable) {

        logger.debug("应付款 导出 req:{}", payable);
        RestResult result = new RestResult();


        return result;
    }

    /**
     * 显示统计信息
     */
    @RequestMapping(value = "/getStatistics", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RestResult getStatistics(Payable payable) {

        logger.debug("应付款 getStatistics req:{}", payable);
        RestResult result = new RestResult();

        return result;
    }

    /**
     * 查看详情信息
     */
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getDetail(@RequestBody Payable payable) {

        logger.debug("应付款 getDetail req:{}", payable);
        RestResult result = new RestResult();
      /*  List<PaymentItem> itemList = paymentItemService.getListByNoAndType(payable.getGoalBillNo(), payable.getGoalBillType());
        result.setItem(itemList);*/

        return result;
    }

    /**
     * 查看生成付款单详情信息
     */
    @RequestMapping(value = "/getResult", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getResult(Payment payment) {

        logger.debug("应付款 getResult req:{}", payment);
        RestResult result = new RestResult();
        List<PaymentItem> itemList = paymentItemService.getListByPaymentId(payment.getId());
        result.setItem(itemList);

        return result;
    }

    /**
     * 拒绝
     */
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    @ResponseBody
    public RestResult reject(@RequestBody List<Payable> payables) {

        logger.debug("应付款 reject req:{}", payables);
        RestResult result = new RestResult();

        payableService.reject(payables);

        return result;
    }

    /**
     * 生成
     */
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public RestResult generate(@RequestBody List<Payable> payables) {
        logger.debug("应付款 generate req:{}", payables);
        RestResult result = new RestResult();
        List<Payable> payableList = checkPayable(payables);
        if (!result.getCode().equals(RestResult.OK)) {
            return result;
        }
        Payment payment = payableService.generate(payableList);
        Payable payable = new Payable();
        payable.setPaymentId(payment.getId());
        List<FinancePay> financePayList = payableService.findList(payable);
        result.setItem(financePayList);
        return result;
    }

    /**
     * 校验 1、实付金额>应付金额 2、发生单据负责人需一致
     *
     * @param
     * @param payables
     * @return
     */
    private List<Payable> checkPayable(List<Payable> payables) {
        Set changeUserNameSet = new HashSet();
        Set partnerSet = new HashSet();
        List<Payable> newPayables=new ArrayList<>();
        for (Payable payable : payables) {
            Payable pay = payableService.getById(payable.getId());
            pay.setPayAmount(payable.getPayAmount());
            if (pay.getPayAmount().compareTo(pay.getAmount()) > 0) {
                Assert.isTrue(false,"实际支付金额不能大于应付金额");
            }
            changeUserNameSet.add(pay.getBuyerName());
            partnerSet.add(pay.getPartnerId());
            newPayables.add(pay);
        }

        if (changeUserNameSet.size() > 1) {
            Assert.isTrue(false,"发生单据负责人需一致");
        }
        if (partnerSet.size() > 1) {
            Assert.isTrue(false,"收款方需一致");
        }
        return newPayables;
    }

}
