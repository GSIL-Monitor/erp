package com.stosz.order.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stosz.order.ext.model.OrdersWebInterfaceRecord;
import com.stosz.order.ext.model.OrdersWebInterfaceRecordDetail;
import com.stosz.order.service.OrdersWebInterfaceRecordDetailService;
import com.stosz.order.service.OrdersWebInterfaceRecordService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @auther carter
 * create time    2018-01-17
 * wms消息推送记录表
 */
@Controller("WmsPushListController")
@RequestMapping("/orders/base/wmsPushList")
public class WmsPushListController  extends AbstractController{


    @Autowired
    private OrdersWebInterfaceRecordService ordersWebInterfaceRecordService;

    @Autowired
    private OrdersWebInterfaceRecordDetailService ordersWebInterfaceRecordDetailService;

    /**
     * 搜索功能
     * @return
     */
    @RequestMapping("/find")
    @ResponseBody
    public RestResult find(OrdersWebInterfaceRecord param)
    {
        RestResult restResult = new RestResult();
        LocalDateTime createAtStart = param.getMinCreateAt();LocalDateTime createAtEnd=param.getMaxCreateAt() ;
        PageHelper.offsetPage(param.getStart(),param.getLimit());
        List<OrdersWebInterfaceRecord> ordersWebInterfaceRecordList = ordersWebInterfaceRecordService.findByCondition(param.getInterfaceTypeEnum(),param.getInterfaceNameEnum(),param.getObjectId(),param.getResponseResultEnum(),createAtStart,createAtEnd,param.getStart(),param.getLimit());

        PageInfo<OrdersWebInterfaceRecord> pageInfo = new PageInfo<>(ordersWebInterfaceRecordList);
        restResult.setItem(ordersWebInterfaceRecordList);
        restResult.setTotal((int)(pageInfo.getTotal()));

        return restResult;
    }


    @RequestMapping("/{id}/detail")
    @ResponseBody
    public RestResult detail(@PathVariable("id") Integer id ,OrdersWebInterfaceRecordDetail param)
    {
        RestResult restResult = new RestResult();
        PageHelper.offsetPage(param.getStart(),param.getLimit());
        List<OrdersWebInterfaceRecordDetail> ordersWebInterfaceRecordDetails = ordersWebInterfaceRecordDetailService.findDetailListById(id);
        PageInfo<OrdersWebInterfaceRecordDetail> pageInfo = new PageInfo<>(ordersWebInterfaceRecordDetails);
        restResult.setItem(ordersWebInterfaceRecordDetails);
        restResult.setTotal((int)(pageInfo.getTotal()));
        return restResult;
    }



    /**
     * 导出
     * @return
     */
    @RequestMapping("/export")
    @ResponseBody
    public RestResult export()
    {
        RestResult restResult = new RestResult();

        return restResult;
    }




    /**
     * 重新推送
     * @return
     */
    @RequestMapping("/rePush")
    @ResponseBody
    public RestResult rePush()
    {
        RestResult restResult = new RestResult();

        return restResult;
    }




}
