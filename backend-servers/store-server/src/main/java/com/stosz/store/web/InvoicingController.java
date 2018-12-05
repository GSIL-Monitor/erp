package com.stosz.store.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.store.ext.dto.request.InvoicingReq;
import com.stosz.store.ext.model.Invoicing;
import com.stosz.store.ext.model.PlanRecord;
import com.stosz.store.service.InvoicingService;
import com.stosz.store.service.PlanRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:仓库管理
 * @Created Time:2017/11/25 10:10
 * @Update Time:2017/11/25 10:10
 */
@RestController
@RequestMapping("/store/invoicing")
public class InvoicingController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InvoicingService invoicingService;

    @Autowired
    private PlanRecordService planRecordService;

    /**
     * 库存列表
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    @ResponseBody
    public RestResult findList(InvoicingReq invoicingReq) {
        RestResult result = new RestResult();
        Invoicing invoicing = new Invoicing();
        BeanUtils.copyProperties(invoicingReq, invoicing);

        /*int count = invoicingService.count(invoicing);
        result.setTotal(count);
        if (count == 0) {
            return result;
        }*/

        List<Invoicing> invoicings = invoicingService.query(invoicing);
        result.setItem(invoicings);
        return result;
    }

    /**
     * 获取排程列表
     */
    @RequestMapping(value = "/getPlanRecordList", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getPlanRecordList(PlanRecord planRecord) {

        List<PlanRecord> list = planRecordService.getPlanRecordList(planRecord);
        RestResult result = new RestResult();
        result.setItem(list);
        return result;
    }


}
