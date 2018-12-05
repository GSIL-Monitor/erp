package com.stosz.order.web;

import com.google.common.collect.Lists;
import com.stosz.order.ext.dto.BatchImportResult;
import com.stosz.order.ext.dto.OrdersQuestionDto;
import com.stosz.order.ext.model.OrdersQuestionDO;
import com.stosz.order.service.OrdersQuestionService;
import com.stosz.order.service.UserZoneService;
import com.stosz.order.util.ExcelUtils;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @auther carter
 * create time    2017-12-06
 * 问题件控制器
 */
@Controller("OrdersQuestionController")
@RequestMapping("orders/manage/order_question")
public class OrdersQuestionController extends AbstractController {


    private static final Logger logger = LoggerFactory.getLogger(OrdersQuestionController.class);


    @Autowired
    private OrdersQuestionService ordersQuestionService;

    @Autowired
    private UserZoneService userZoneService;


    /**
     * 文档地址： https://www.showdoc.cc/buguniao?page_id=15668719
     *
     * @param ordersQuestionDto
     * @return
     */
    @RequestMapping(value = "find", method = RequestMethod.POST)
    @ResponseBody
    public RestResult find(OrdersQuestionDto ordersQuestionDto) {
        RestResult restResult = new RestResult();

        List<OrdersQuestionDO> ordersQuestions = ordersQuestionService.find(ordersQuestionDto);
        Integer count = ordersQuestionService.count(ordersQuestionDto);

        restResult.setTotal(count);
        restResult.setItem(ordersQuestions);
        restResult.setCode(RestResult.OK);
        restResult.setDesc("问题件查询成功！");

        return restResult;

    }


    /**
     * 文档地址：https://www.showdoc.cc/buguniao?page_id=15668776
     *
     * @param ordersQuestionDto
     * @param response
     */
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public void export(OrdersQuestionDto ordersQuestionDto, HttpServletResponse response) {

        //构造最多导出两万条数据的查询条件
        ordersQuestionDto.setPageIndex(0);
        ordersQuestionDto.setPageSize(20000);
        List<OrdersQuestionDO> ordersQuestions = ordersQuestionService.find(ordersQuestionDto);

        String[] headers = new String[]{"物流线路", "物流登记日期\n(客户反馈问题日期)", "运单号", "问题类型", "原因描述", "收件人", "收件电话", "品名", "处理结果"};
        String[] includeFields = new String[]{"logisticName", "logisticDeliveryTime", "transNo", "questionType", "questionDetail", "customerName", "customerPhone", "spuList", "dealStatusEnum"};

        Collection<OrdersQuestionDO> dataSet = Lists.newLinkedList(ordersQuestions);
        try {
            ExcelUtils.exportExcelAndDownload("问题件导出", "问题件", headers, includeFields, dataSet, ExcelUtils.DATE_PATTERN, response);
        } catch (Exception e) {
            logger.error("导出问题件错误", e);
        }

    }


    /**
     * 文档地址；https://www.showdoc.cc/buguniao?page_id=15668962
     *
     * @param content
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    @ResponseBody
    public RestResult _import(String content, HttpServletRequest request) throws IOException {

        BatchImportResult result = ordersQuestionService._import(content);

        RestResult restResult = new RestResult();

        restResult.setItem(result);

        if (result.getHasWrong()) {
            restResult.setCode(RestResult.NOTICE);
            restResult.setDesc("格式不正确，请直接从excel复制数据列");
        } else {
            restResult.setCode(RestResult.OK);
            restResult.setDesc("导入完成");
        }

        return restResult;
    }


    /**
     * 文档地址：https://www.showdoc.cc/buguniao?page_id=15668964
     *
     * @return
     */
    @RequestMapping(value = "deal", method = RequestMethod.POST)
    @ResponseBody
    public RestResult deal(String[] orderQuestionIdArray, String dealMemo, String dealEventEnumName) {
        RestResult restResult = new RestResult();

        int row = ordersQuestionService.deal(orderQuestionIdArray, dealMemo, dealEventEnumName);
        restResult.setItem(row);
        if (row > 0) {
            restResult.setCode(RestResult.OK);
            restResult.setDesc("处理完成！");
        } else {
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("处理失败！");
        }
        return restResult;
    }
}
