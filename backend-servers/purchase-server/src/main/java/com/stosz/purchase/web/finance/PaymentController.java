package com.stosz.purchase.web.finance;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.purchase.ext.model.finance.Payment;
import com.stosz.purchase.ext.model.finance.PaymentItem;
import com.stosz.purchase.service.finance.PaymentItemService;
import com.stosz.purchase.service.finance.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2018/1/2 13:49
 */
@RestController
@RequestMapping("/purchase/finance/payment")
public class PaymentController extends AbstractController{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PaymentService paymentService;
    @Resource
    private PaymentItemService paymentItemService;

//    /**
//     * 付款单列表
//     *
//     * @param payment
//     */
//    @RequestMapping(value = "/findList", method = {RequestMethod.POST, RequestMethod.GET})
//    @ResponseBody
//    public RestResult findList(Payment payment) {
//        logger.debug("付款列表 req:partner={},stare={}", payment.getPartner(), payment.getState());
//        RestResult result = new RestResult();
//
//        int count = paymentService.count(payment);
//        result.setTotal(count);
//        if (count == 0) {
//            return result;
//        }
//        List<Payment> paymentList = paymentService.query(payment);
//        result.setItem(paymentList);
//        return result;
//    }
//
//    /**
//     * 删除
//     *
//     * @param paymentId
//     */
//    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
//    @ResponseBody
//    public RestResult delete(Integer paymentId) {
//
//        logger.debug("付款单删除 req:paymentId={}", paymentId);
//        Assert.notNull(paymentId,"请选择付款单");
//        paymentService.delete(paymentId);
//
//        return new RestResult();
//    }
//
//    /**
//     * 付款单拒绝
//     *
//     * @param paymentIds
//     */
//    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
//    @ResponseBody
//    public RestResult reject(Integer[] paymentIds) {
//
//        logger.debug("应付款 refuse req:{}", paymentIds);
//        Assert.isTrue(paymentIds.length>0,"未选择付款单");
//        for(int i=0;i<paymentIds.length;i++){
//            Integer ids = paymentService.findByIds(paymentIds);
//            Assert.isTrue(ids==paymentIds.length,"存在未知付款单,请检查");
//        }
//        paymentService.refusePay(paymentIds);
//        RestResult result = new RestResult();
//
//        return result;
//    }
//
//    /**
//     * 付款单付款
//     *
//     * @param paymentIds
//     */
//    @RequestMapping(value = "/pay")
//    @ResponseBody
//    public RestResult pay(Integer[] paymentIds) {
//        logger.debug("应付款 pay req:{}", paymentIds);
//        Assert.isTrue(paymentIds.length>0,"未选择付款单");
//        for(int i=0;i<paymentIds.length;i++){
//            Integer ids = paymentService.findByIds(paymentIds);
//            Assert.isTrue(ids==paymentIds.length,"存在未知付款单,请检查");
//        }
//        paymentService.billPaid(paymentIds);
//        RestResult result = new RestResult();
//
//        return result;
//    }
//
//    /**
//     * 付款单详情
//     *
//     * @param paymentId
//     */
//    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
//    @ResponseBody
//    public RestResult getDetail(Integer paymentId) {
//
//        logger.debug("付款单 getDetail req:{}", paymentId);
//        RestResult result = new RestResult();
//        List<PaymentItem> itemList = paymentItemService.getListByPaymentId(paymentId);
//        result.setItem(itemList);
//        return result;
//    }

}
