package com.stosz.purchase.web.finance;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.purchase.ext.model.finance.AmountAdjust;
import com.stosz.purchase.ext.model.finance.PaymentItem;
import com.stosz.purchase.service.finance.AmountAdjustService;
import com.stosz.purchase.service.finance.PaymentItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:  调整表
 * @Created Time:
 * @Update Time:2018/1/2 13:49
 */
@RestController
@RequestMapping("/purchase/finance/amountAdjust")
public class AmountAdjustController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AmountAdjustService amountAdjustService;

    @Resource
    private PaymentItemService paymentItemService;


    /**
     * 搜索
     */
    @RequestMapping(value = "/findList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RestResult findList(AmountAdjust adjust) {

        logger.debug("调整列表 req:adjust={}",adjust);
        RestResult result = new RestResult();

        int count = amountAdjustService.count(adjust);
        result.setTotal(count);
        if (count == 0) {
            return result;
        }
        List<AmountAdjust> list = amountAdjustService.query(adjust);

        result.setItem(list);
        return result;
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/export", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RestResult export(AmountAdjust adjust) {

        logger.debug("调整表 导出 req:{}", adjust);
        RestResult result = new RestResult();


        return result;
    }

    /**
     * 显示统计信息
     */
    @RequestMapping(value = "/getStatistics", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RestResult getStatistics(AmountAdjust adjust) {

        logger.debug("调整表 getStatistics req:{}", adjust);
        RestResult result = new RestResult();

        return result;
    }

    /**
     * 查看详情信息
     */
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getDetail(AmountAdjust adjust) {

        logger.debug("调整表 getDetail req:{}", adjust);
        RestResult result = new RestResult();
        List<PaymentItem> itemList = paymentItemService.getListByChangeNo(adjust.getId().toString());
        result.setItem(itemList);

        return result;
    }
    
}
