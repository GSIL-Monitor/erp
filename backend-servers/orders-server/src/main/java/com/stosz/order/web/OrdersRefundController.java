package com.stosz.order.web;

import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.model.IFsmHistory;
import com.stosz.order.ext.dto.OrdersRefundDTO;
import com.stosz.order.ext.model.OrdersRefundBill;
import com.stosz.order.ext.model.OrdersRefundBillDO;
import com.stosz.order.service.OrdersRefundService;
import com.stosz.order.util.ExcelUtils;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @auther tangtao
 * create time  2018/1/3
 */
@RestController
@RequestMapping("orders/refund")
public class OrdersRefundController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(OrdersRefundController.class);

    @Autowired
    private OrdersRefundService ordersRefundService;

    @Autowired
    private FsmProxyService<OrdersRefundBill> ordersRefundBillFsmProxyService;


    @PostMapping("find")
    public RestResult find(OrdersRefundDTO ordersRefundDTO) {
        RestResult restResult = new RestResult();
        List<OrdersRefundBillDO> list = ordersRefundService.find(ordersRefundDTO);
        Integer count = ordersRefundService.count(ordersRefundDTO);
        restResult.setTotal(count);
        restResult.setItem(list);
        return restResult;
    }

    @PostMapping("export")
    public void export(OrdersRefundDTO ordersRefundDTO, HttpServletResponse response) {

        //构造最多导出两万条数据的查询条件
        ordersRefundDTO.setStart(0);
        ordersRefundDTO.setLimit(20000);
        List<OrdersRefundBillDO> dataSet = ordersRefundService.find(ordersRefundDTO);

        // TODO
        String[] headers = new String[]{"退款单编号", "订单流水号", "退货单号", "站点来源", "状态", "类型", "金额", "申请时间", "审核时间", "审核人", "备注"};
        String[] includeFields = new String[]{"id", "ordersId", "ordersRamBillId", "siteFrom", "refundStatusEnum", "refundTypeEnum", "refundAmount", "createAt", "auditTime", "auditUserName", "auditMemo"};

        try {
            ExcelUtils.exportExcelAndDownload("退款单", "退款单", headers, includeFields, dataSet, ExcelUtils.DATE_TIME_PATTERN, response);
        } catch (Exception e) {
            logger.error("导出问题件错误", e);
        }
    }


    @PostMapping("modify")
    public RestResult modify(OrdersRefundDTO ordersRefundDTO) {
        RestResult restResult = new RestResult();
        ordersRefundService.modify(ordersRefundDTO);
        return restResult;

    }


    @PostMapping("approve")
    public RestResult approve(Integer id, String auditMemo, Boolean result) {
        RestResult restResult = new RestResult();
        ordersRefundService.approve(id, auditMemo, result);
        return restResult;

    }

    @PostMapping("refunded")
    public RestResult refunded(Integer id, String refundSerialNumber) {
        RestResult restResult = new RestResult();
        ordersRefundService.refunded(id, refundSerialNumber);
        return restResult;

    }

    @GetMapping("fms")
    public RestResult fms(Integer id) {
        RestResult restResult = new RestResult();
        List<IFsmHistory> list = ordersRefundBillFsmProxyService.queryFsmHistory("OrdersRefundBill", id);
        restResult.setItem(list);
        return restResult;
    }


    @PostMapping("addFmsDemo")
    public RestResult addFmsDemo(Integer id, String memo) {
        RestResult restResult = new RestResult();
        OrdersRefundBill ordersRmaBill = ordersRefundService.findById(id);
        ordersRefundBillFsmProxyService.processLog(ordersRmaBill, memo);
        return restResult;
    }

}
