package com.stosz.order.web;

import com.google.common.collect.Lists;
import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.model.IFsmHistory;
import com.stosz.order.ext.dto.*;
import com.stosz.order.ext.enums.*;
import com.stosz.order.ext.model.*;
import com.stosz.order.service.AfterSaleService;
import com.stosz.order.service.OrderItemsService;
import com.stosz.order.util.ExcelUtils;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.plat.web.AbstractController;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 售后（退货，换货）
 */
@RestController
@RequestMapping("orders/aftersale")
public class AfterSaleController extends AbstractController {


    @Autowired
    private AfterSaleService afterSaleService;

    @Autowired
    private OrderItemsService orderItemsService;

//    @Resource
//    private FsmHistoryService orderChangeFsmHistoryService;

    @Autowired
    private FsmProxyService<OrdersRmaBill> orderChangeFsmProxyService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 退换货列表
     *
     * @param param
     * @return
     */
    @RequestMapping("find")
    public RestResult find(OrdersBillSearchParam param) {
        RestResult result = new RestResult();
        int total = afterSaleService.countBySearchParam(param);
        result.setTotal(total);
        if (total == 0) {
            result.setCode(RestResult.OK);
            return result;
        }
        List<OrdersRmaBill> ordersChanges = afterSaleService.findOrdersChangeBySearchParam(param);
        result.setItem(ordersChanges);
        result.setCode(RestResult.OK);
        result.setDesc("查询成功");
        return result;
    }


    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void export(OrdersBillSearchParam param, HttpServletResponse response) throws Exception {

        //构造最多导出两万条数据的查询条件
        param.setStart(0);
        param.setLimit(20000);

        //  改成和orders_link 表联查
        List<OrdersRmaBillDO> ordersRmaBillList = afterSaleService.findOrdersChangeBySearchParamWithOrdersLink(param);

        String[] headers = {"退货单编号", "订单流水号", "发货物流", "发货运单号", "收货人名称", "联系方式", "收货人地址", "退货商品", "退款总金额"};
        String[] fileds = {"id", "ordersId", "logisticName", "orderTrackingNo", "firstName", "telphone", "address", "itemList", "refundAmount"};
        ExcelUtils.exportExcelAndDownload("退货申请", "退货申请", headers, fileds, ordersRmaBillList, ExcelUtils.DATE_TIME_PATTERN, response);
    }

    /**
     * 订单中订单行列表，用于退换货
     *
     * @param orderId
     * @return
     */
    @GetMapping("order_item")
    public RestResult orderItemList(@RequestParam("orderId") Integer orderId) {
        RestResult result = new RestResult();
        List<OrdersItem> orderItemsList = orderItemsService.getByOrderId(orderId);
        result.setTotal(orderItemsList.size());
        result.setItem(orderItemsList);
        return result;
    }


    /**
     * 订单信息，用于退换货申请前查询
     *
     * @param orderId
     * @return
     */
    @GetMapping("preApplyInfo")
    public RestResult preApplyInfo(@RequestParam("orderId") Integer orderId, @RequestParam("rmaType") String rmaType) {
        RestResult result = new RestResult();
        RmaDetailDto detailDto = afterSaleService.findPreApplyInfoByOrderId(orderId, rmaType);
        result.setItem(detailDto);
        result.setCode(RestResult.OK);
        return result;
    }


    /**
     * 修改申请单
     *
     * @param param
     * @return
     */
    @PostMapping("update")
    public RestResult update(@RequestBody OrderChangeApplyParam param) {
        Assert.notNull(param, "请求参数有误");
        Assert.notNull(param.getRmaId(), "申请单号不能为空");
        Assert.notNull(param.getApplyType(), "请求参数有误");
        Assert.notNull(param.getOrderId(), "订单号不能为空");
        Assert.notNull(param.getRmaSource(), "退货来源不能为空");
        Assert.notNull(param.getChangeReason(), "退货原因不能为空");
        Assert.notNull(param.getRmaState(), "状态不能为空");
        Assert.notNull(param.getRecycleGoods(), "是否回收不能为空");
        Assert.notNull(param.getTrackingNo(), "运单号不能为空");
        Assert.notEmpty(param.getOrderItemQtyList(), "请求参数有误");

        param.setChangeReasonOtherMemo(Optional.ofNullable(param.getChangeReasonOtherMemo()).orElse(""));

        ChangeTypeEnum changeTypeEnum = ChangeTypeEnum.valueOf(param.getApplyType());

        if (changeTypeEnum.equals(ChangeTypeEnum.returned)) {
            Assert.isTrue(param.getAmount() != null, "金额不能为空");
        }
        RestResult result = new RestResult();
        afterSaleService.update(param);
        return result;
    }


    /**
     * 申请退换货
     *
     * @param param
     * @return
     */
    @PostMapping("add")
    public RestResult apply(@RequestBody OrderChangeApplyParam param) {
        Assert.notNull(param, "请求参数有误");
        Assert.notNull(param.getApplyType(), "请求参数有误");
        Assert.notNull(param.getOrderId(), "订单号不能为空");
        Assert.notNull(param.getRmaSource(), "退货来源不能为空");
        Assert.notNull(param.getChangeReason(), "退货原因不能为空");
        Assert.notNull(param.getRmaState(), "状态不能为空");
        Assert.notNull(param.getRecycleGoods(), "是否回收不能为空");
        Assert.notNull(param.getTrackingNo(), "运单号不能为空");
        Assert.notEmpty(param.getOrderItemQtyList(), "请求参数有误");

        ChangeTypeEnum changeTypeEnum = ChangeTypeEnum.valueOf(param.getApplyType());

        if (changeTypeEnum.equals(ChangeTypeEnum.returned)) {
            Assert.isTrue(param.getAmount() != null, "金额不能为空");
        }

        RestResult result = new RestResult();


        switch (changeTypeEnum) {
            case returned:
                afterSaleService.applyReturn(param);
                break;
            case reject:
                afterSaleService.applyReject(param);
                break;
            default:
                throw new RuntimeException("操作异常！请联系管理员！");
        }
        result.setDesc("申请成功");
        return result;
    }

    /**
     * 凭证图片
     *
     * @param rmaId
     * @return
     */
    @RequestMapping("voucher")
    public RestResult findPhotos(Integer rmaId) {
        RestResult result = new RestResult();
        List<String> imgs = afterSaleService.findPhotos(rmaId);
        PhotosAlbumDTO pad = new PhotosAlbumDTO();
        imgs.stream().forEach(e -> {
            Photo photo = new Photo();
            photo.setSrc(e);
            pad.getData().add(photo);
        });
        result.setItem(pad);
        result.setCode(RestResult.OK);
        result.setDesc("查询成功");
        return result;


    }

    /**
     * 审核通过
     *
     * @param rmaId
     * @param auditMemo
     * @param result
     * @return
     */
    @PostMapping("approve")
    public RestResult approve(Integer rmaId, String auditMemo, Boolean result) {
        return afterSaleService.approve(rmaId, auditMemo, result);
    }

    /**
     * 退换货详情
     *
     * @param rmaId
     * @return
     */
    @GetMapping("detail")
    public RestResult detail(@RequestParam("rmaId") Integer rmaId) {
        Assert.notNull(rmaId, "请求参数有误");
        RestResult result = new RestResult();
        RmaDetailDto detailDto = afterSaleService.findDetailByRmaBillId(rmaId);
        result.setItem(detailDto);
        result.setCode(RestResult.OK);
        return result;
    }

    /**
     * 取消拒收/退货
     *
     * @param rmaId
     * @return
     */
    @GetMapping("cancel")
    public RestResult cancel(@RequestParam("rmaId") Integer rmaId) {
        return afterSaleService.cancelRmaBill(rmaId);
    }

    /**
     * 更新运单号
     *
     * @param rmaId
     * @param trackingNo
     * @return
     */
    @PostMapping("updateTrackingNo")
    public RestResult updateTrackingNo(@RequestParam("rmaId") Integer rmaId, @Param("trackingNo") String trackingNo) {
        return afterSaleService.updateTrackingNo(rmaId, trackingNo);
    }

    @GetMapping("fms")
    public RestResult fms(Integer rmaId) {
        RestResult restResult = new RestResult();
        List<IFsmHistory> list = orderChangeFsmProxyService.queryFsmHistory("OrdersRmaBill", rmaId);
        restResult.setItem(list);
        return restResult;
    }

    @PostMapping("addFmsDemo")
    public RestResult addFmsDemo(Integer rmaId, String memo) {
        RestResult restResult = new RestResult();
        OrdersRmaBill ordersRmaBill = afterSaleService.getById(rmaId);
        orderChangeFsmProxyService.processLog(ordersRmaBill, memo);
        return restResult;
    }

    /**
     * 寄回运单批量导入
     *
     * @param content
     * @return
     */
    @PostMapping("import")
    public RestResult _import(String content, HttpServletRequest request) throws IOException {

        BatchImportResult result = afterSaleService._import(content);

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


    @RequestMapping(value = "/exportTemplate", method = RequestMethod.GET)
    public void exportTemplate(HttpServletResponse response) throws Exception {
        List<OrdersRmaBill> tempatelList = Lists.newArrayList();
        OrdersRmaBill ordersRmaBill = new OrdersRmaBill();
        ordersRmaBill.setOrdersId(2L);
        ordersRmaBill.setTrackingNo("211221");
        ordersRmaBill.setRmaSourceEnum(RmaSourceEnum.aftersalemail);
        ordersRmaBill.setChangeWayEnum(ChangeWayEnum.logisticsBack);
        ordersRmaBill.setChangeReasonEnum(ChangeReasonEnum.missing);
        ordersRmaBill.setChangeReasonOtherMemo("退货原因为其他的时候必填");
        ordersRmaBill.setRecycleGoodsEnum(RecycleGoodsEnum.yes);
        ordersRmaBill.setApplyMemo("七天无理由");
        tempatelList.add(ordersRmaBill);
        String[] headers = {"订单流水号", "发货运单号", "售后来源", "退款方式", "退货原因", "说明", "是否回收", "备注"};
        String[] fileds = {"ordersId", "trackingNo", "rmaSourceEnum", "changeWayEnum", "changeReasonEnum", "changeReasonOtherMemo", "recycleGoodsEnum", "applyMemo"};
        ExcelUtils.exportExcelAndDownload("退货申请导入模板", "退货申请", headers, fileds, tempatelList, ExcelUtils.DATE_TIME_PATTERN, response);
    }

    /**
     * 批量创建申请单
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/bathApplyChange", method = RequestMethod.POST)
    public RestResult bathApplyChange(@RequestParam(name = "file", required = true) MultipartFile file) {
        return afterSaleService.bathApplyChange(file);
    }


    /**
     * 批量创建申请单失败导出
     *
     * @param key redis key
     * @return
     */
    @RequestMapping(value = "/exportFiledImport", method = RequestMethod.POST)
    public void exportFiledImport(String key, HttpServletResponse response) throws Exception {
        String jsonStr = redisTemplate.opsForValue().get(key);
        List<OrdersRmaBillImportDO> importDOList = JsonUtils.jsonToList(jsonStr, OrdersRmaBillImportDO.class);

        String[] headers = {"订单流水号", "发货运单号", "售后来源", "退款方式", "退货原因", "说明", "是否回收", "备注", "导入失败原因"};
        String[] fileds = {"orderId", "trackingNo", "rmaSourceEnum", "changeWayEnum", "changeReasonEnum", "changeReasonOtherMemo", "recycleGoodsEnum", "applyMemo", "errorMsg"};
        ExcelUtils.exportExcelAndDownload("退货申请导入模板", "退货申请", headers, fileds, importDOList, ExcelUtils.DATE_TIME_PATTERN, response);
    }
}
