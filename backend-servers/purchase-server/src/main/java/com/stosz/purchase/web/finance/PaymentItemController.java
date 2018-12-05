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
@RequestMapping("/purchase/finance/paymentItem")
public class PaymentItemController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PaymentItemService paymentItemService;

    /**
     * 付款单列表
     *
     * @param paymentItem
     */
    @RequestMapping(value = "/findList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RestResult findList(PaymentItem paymentItem) {
        logger.debug("付款列表 req:partner={},stare={}", paymentItem.getPartner(), paymentItem.getState());
        RestResult result = new RestResult();

        int count = paymentItemService.count(paymentItem);
        result.setTotal(count);
        if (count == 0) {
            return result;
        }
        List<PaymentItem> paymentItems = paymentItemService.query(paymentItem);
        result.setItem(paymentItems);
        return result;
    }


}
