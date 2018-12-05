package com.stosz.order.web;

import com.stosz.order.ext.dto.OrderAuditParam;
import com.stosz.order.service.OrderAuditService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


/**
 * 审单操作
 *
 */
@RestController
@RequestMapping("orders/audit")
public class OrderAuditController extends AbstractController {


    @Autowired
    private OrderAuditService orderAuditService;


    /**
     * 批量审单
     * @return
     */
    @PostMapping(value = "batch")
    public RestResult validOrder(@RequestBody OrderAuditParam orderAuditParam){
        RestResult result = new RestResult();
        Assert.notNull(orderAuditParam,"参数不能为空");
        Assert.notNull(orderAuditParam.getType(),"操作类型不能为空");
        Assert.notNull(orderAuditParam,"数据列表不能为空");
        Assert.notEmpty(orderAuditParam.getAuditList(),"参数不能为空");
        orderAuditService.auditBatch(orderAuditParam);
        result.setCode(RestResult.NOTICE);
        result.setDesc("操作成功!");
        return result;
    }


    /**
     * 审单 - 撤回
     * @return
     */
    @PostMapping("revoke")
    public RestResult revoke(@RequestParam("orderId") Integer orderId, @RequestParam("memo") String memo){
        RestResult result = new RestResult();
        Assert.notNull(orderId,"参数不能为空");
        Assert.notNull(memo,"参数不能为空");
        orderAuditService.revoke(orderId,memo);
        result.setCode(RestResult.NOTICE);
        result.setDesc("撤回操作成功!");
        return result;
    }



}
